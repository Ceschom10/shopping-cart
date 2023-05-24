package backend.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import backend.shoppingcart.dto.fakes.RequestGetOneDto;
import backend.shoppingcart.service.IFakesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/check")
public class KafesstoreapiController {

	private static final String BACKEND_BREAKER = "KafesstoreapiController";
	private LogUtil logs;
	private IFakesService fakesService;

	public KafesstoreapiController(IFakesService fakesService) {
		this.fakesService = fakesService;
		this.logs = new LogUtilImpl(KafesstoreapiController.class);
	}
	
	@GetMapping("/products")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Service get products", description = "Return information products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> getProducts() {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller KafesstoreapiController  / backend.shoppingcart.controller, ");

		var respuesta = fakesService.getProducts();

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PostMapping("/products/one")
	@CircuitBreaker(name = BACKEND_BREAKER, fallbackMethod = "fallbackList")
	@Operation(summary = "Servicio get a product", description = "Return information get a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> getProductsOne(@RequestBody @Valid RequestGetOneDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller KafesstoreapiController / getProductsOne() backend.shoppingcart.controller, ");

		var respuesta = fakesService.getProductOne(request);

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
				"consumer.KafesstoreapiController.error.invaliddata", errors);
	}

}
