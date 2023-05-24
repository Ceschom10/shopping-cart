package backend.shoppingcart.service;

import backend.shoppingcart.dto.base.GenericResponseServiceDto;
import backend.shoppingcart.dto.order.OrderCreateDto;
import backend.shoppingcart.dto.order.OrderDeleteDto;
import backend.shoppingcart.dto.order.OrderGetDto;
import backend.shoppingcart.dto.order.OrderUpdateDto;

public interface IOrderService {
	public GenericResponseServiceDto<Object> get(OrderGetDto request);
	public GenericResponseServiceDto<Object> getAll();
	public GenericResponseServiceDto<Object> create(OrderCreateDto rquest);
	public GenericResponseServiceDto<Object> update(OrderUpdateDto request);
	public GenericResponseServiceDto<Object> delete(OrderDeleteDto request);
}
