package backend.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import backend.shoppingcart.component.exception.GenericException;
import backend.shoppingcart.component.util.ServiceFactory;
import backend.shoppingcart.component.util.constant.ConstantesGeneral;
import backend.shoppingcart.component.util.log.LogUtil;
import backend.shoppingcart.component.util.log.LogUtilImpl;
import backend.shoppingcart.component.util.log.LogUtil.TYPELOG;
import backend.shoppingcart.dto.base.GenericResponseDto;
import backend.shoppingcart.dto.order.OrderCreateDto;
import backend.shoppingcart.dto.order.OrderDeleteDto;
import backend.shoppingcart.dto.order.OrderGetDto;
import backend.shoppingcart.dto.order.OrderUpdateDto;
import backend.shoppingcart.service.IOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final String BACKEND_BREAKER = "OrderController";
	private LogUtil logs;
	private IOrderService orderService;

	public OrderController(IOrderService orderService) {
		this.orderService = orderService;
		this.logs = new LogUtilImpl(OrderController.class);
	}
	
	@GetMapping("/getAll")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Service get all orders", description = "Return information orders")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> getOrders() {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / getOrders() backend.shoppingcart.controller, ");

		var respuesta = orderService.getAll();

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PostMapping("/getOne")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Service a order", description = "Return information a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> get(@RequestBody @Valid OrderGetDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / get() backend.shoppingcart.controller, ");

		var respuesta = orderService.get(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PostMapping("")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Service create a order", description = "Create the order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> create(@RequestBody @Valid OrderCreateDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / create() backend.shoppingcart.controller, ");

		var respuesta = orderService.create(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PutMapping("")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Service update a order", description = "Update a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> update(@RequestBody @Valid OrderUpdateDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / update() backend.shoppingcart.controller, ");

		var respuesta = orderService.update(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PostMapping("/delete")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Servicio delete a order", description = "Delete a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> delete(@RequestBody @Valid OrderDeleteDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / delete() backend.shoppingcart.controller, ");

		var respuesta = orderService.delete(request);
		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	public ResponseEntity<?> fallbackList(UnrecognizedPropertyException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());

		if (ex.getCause() != null) {
			errors.add(ex.getCause().getMessage());
		}

		errors.add(ex.getClass().getName());

		throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "CircuiBreaker Peticion: ",
				"consumer.OrderController.error.invaliddata", errors);
	}

}
