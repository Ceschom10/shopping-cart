package backend.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import backend.shoppingcart.dao.IOrderDao;
import backend.shoppingcart.dto.base.GenericResponseServiceDto;
import backend.shoppingcart.dto.fakes.RequestGetOneDto;
import backend.shoppingcart.dto.fakes.ResponseGetKafestoreapiDto;
import backend.shoppingcart.dto.order.DetailsCreateDto;
import backend.shoppingcart.dto.order.DetailsUpdateDto;
import backend.shoppingcart.dto.order.OrderCreateDto;
import backend.shoppingcart.dto.order.OrderDeleteDto;
import backend.shoppingcart.dto.order.OrderGetDto;
import backend.shoppingcart.dto.order.OrderUpdateDto;
import backend.shoppingcart.mapper.OrderDetailsMapper;
import backend.shoppingcart.mapper.OrderMapper;
import backend.shoppingcart.model.Order;
import backend.shoppingcart.model.OrderDetails;
import backend.shoppingcart.service.IFakesService;
import backend.shoppingcart.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	IOrderDao orderDao;
	IFakesService fakesService;

	public OrderServiceImpl(IOrderDao orderDao, IFakesService fakesService) {
		this.orderDao = orderDao;
		this.fakesService = fakesService;
	}

	@Override
	public GenericResponseServiceDto<Object> getAll() {
		List<OrderUpdateDto> listDtoOrder = new ArrayList<>();
		List<Order> listOrder = new ArrayList<>();
		var response = new GenericResponseServiceDto<Object>();

		listOrder = orderDao.findAllWithOrderDetails();
		if (listOrder.isEmpty()) {
			response.setMessage("La lista esta vacia");
			response.setSuccess(false);
			response.setItem(listDtoOrder);
		} else {
			
			for (Order order : listOrder) {
				OrderUpdateDto orderDto = new OrderUpdateDto();
				orderDto.setCliente(order.getCliente());
				orderDto.setFecha(order.getFecha());
				orderDto.setId(order.getId());
				orderDto.setTotal(order.getTotal());
				
				List<DetailsUpdateDto> det = OrderDetailsMapper.INSTANCE.detailsToDetailsDto(order.getOrderDetails());
				orderDto.setDetails(det);
				listDtoOrder.add(orderDto);
			}
			
			listDtoOrder.sort(Comparator.comparing(OrderUpdateDto::getId));

			response.setMessage("Respuesta exitosa");
			response.setSuccess(true);
			response.setItem(listDtoOrder);
		}
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> get(OrderGetDto request) {
		OrderUpdateDto orderDto = new OrderUpdateDto();
		var response = new GenericResponseServiceDto<Object>();

		Optional<Order> orderOpt = orderDao.findByIdWithOrderDetails(request.getIdOrden());
		if (orderOpt.isEmpty()) {
			response.setMessage("La lista esta vacia");
			response.setSuccess(false);
		} else {
			
			Order order = orderOpt.get();
			orderDto = OrderMapper.INSTANCE.orderToOrderDto(order);
			
			List<DetailsUpdateDto> details = OrderDetailsMapper.INSTANCE.detailsToDetailsDto(order.getOrderDetails());
			
			orderDto.setDetails(details);
			order.setId(order.getId());
					
			response.setMessage("Respuesta exitosa");
			response.setSuccess(true);
			response.setItem(orderDto);
		}
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> create(OrderCreateDto request) {
		var response = new GenericResponseServiceDto<Object>();
		List<OrderDetails> listOrder = new ArrayList<>();
		double total = 0;
		double totalOrder = 0;
		try {
			Order order = new Order();
			order.setFecha(request.getFecha());
			order.setCliente(request.getCliente());
			
			if (request.getDetails() == null || request.getDetails().isEmpty()) {
				response.setSuccess(false);
				response.setMessage("Error la orden no trae detalles");
				return response;
			}

			for (DetailsCreateDto detailsOrderDto : request.getDetails()) {
				var responseCheckProduct = new GenericResponseServiceDto<Object>();
				RequestGetOneDto requestCheck = new RequestGetOneDto();
				requestCheck.setIdProducto(detailsOrderDto.getIdProducto());
				
				//Se obtienen los productos de la api por medio del id
				responseCheckProduct = fakesService.getProductOne(requestCheck);
				
				if (!responseCheckProduct.getSuccess()) {
					response.setSuccess(false);
					response.setMessage("El producto no existe "+requestCheck.getIdProducto());
					return response;
				}
				
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setCantidad(detailsOrderDto.getCantidad());
				ModelMapper modelMapper = new ModelMapper();
				ResponseGetKafestoreapiDto dtoProd = modelMapper.map(responseCheckProduct.getItem(), ResponseGetKafestoreapiDto.class);
				orderDetails.setIdProducto(dtoProd.getId());
				orderDetails.setPrecioUnitario(dtoProd.getPrice());
				total += dtoProd.getPrice()*detailsOrderDto.getCantidad();
				orderDetails.setSubtotal(total);
				orderDetails.setOrden(order);
				totalOrder += total;
				total = 0;
				listOrder.add(orderDetails);
			}
			order.setTotal(totalOrder);
			order.setOrderDetails(listOrder);
			orderDao.save(order);
			response.setSuccess(true);
			response.setMessage("Orden guardada exitosamente");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Ocurrio un error al guardar");
		}
		
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> update(OrderUpdateDto request) {
		var response = new GenericResponseServiceDto<Object>();
		List<OrderDetails> listOrder = new ArrayList<>();
		double total = 0;
		double totalOrder = 0;
		try {
			Optional<Order> orderOpt = orderDao.findById(request.getId());
			
			if (orderOpt.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("La orden no existe "+request.getId());
				return response;
			}
			
			Order order = orderOpt.get();
			
			for (DetailsUpdateDto detailsUpdateDto : request.getDetails()) {
				var responseCheckProduct = new GenericResponseServiceDto<Object>();
				RequestGetOneDto requestCheck = new RequestGetOneDto();
				requestCheck.setIdProducto(detailsUpdateDto.getIdProducto());
				
				//Se obtienen los productos de la api por medio del id
				responseCheckProduct = fakesService.getProductOne(requestCheck);
				
				if (!responseCheckProduct.getSuccess()) {
					response.setSuccess(false);
					response.setMessage("El producto no existe "+requestCheck.getIdProducto());
					return response;
				}
			
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setCantidad(detailsUpdateDto.getCantidad());
				ModelMapper modelMapper = new ModelMapper();
				ResponseGetKafestoreapiDto dtoProd = modelMapper.map(responseCheckProduct.getItem(), ResponseGetKafestoreapiDto.class);
				orderDetails.setIdProducto(dtoProd.getId());
				orderDetails.setPrecioUnitario(dtoProd.getPrice());
				total += dtoProd.getPrice()*detailsUpdateDto.getCantidad();
				orderDetails.setSubtotal(total);
				orderDetails.setId(detailsUpdateDto.getId());
				orderDetails.setOrden(order);
				totalOrder += total;
				total = 0;
				listOrder.add(orderDetails);
			}
			order.setTotal(totalOrder);
			order.setOrderDetails(listOrder);
			orderDao.save(order);
			response.setSuccess(true);
			response.setMessage("Orden guardada exitosamente");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Ocurrio un error al guardar");
		}
		
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> delete(OrderDeleteDto request) {
		var response = new GenericResponseServiceDto<Object>();

		Optional<Order> orderOpt = orderDao.findById(request.getIdOrden());
		if (orderOpt.isEmpty()) {
			response.setMessage("La orden no existe");
			response.setSuccess(false);
		} else {
			
			Order order = orderOpt.get();
			orderDao.delete(order);
					
			response.setMessage("Respuesta exitosa");
			response.setSuccess(true);
		}
		return response;
	}

}
