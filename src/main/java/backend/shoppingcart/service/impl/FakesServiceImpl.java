package backend.shoppingcart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import backend.shoppingcart.constants.ConstantesOtpLogin;
import backend.shoppingcart.dto.base.GenericResponseServiceDto;
import backend.shoppingcart.dto.fakes.RequestGetOneDto;
import backend.shoppingcart.dto.fakes.ResponseGetKafestoreapiDto;
import backend.shoppingcart.dto.fakes.ResponseServiceKafestoreapi;
import backend.shoppingcart.service.IFakesService;
import backend.shoppingcart.service.consumer.base.RxGenericConsumer;

@Service
public class FakesServiceImpl implements IFakesService{
	
	private RxGenericConsumer consumer;
	
	public FakesServiceImpl(RxGenericConsumer consumer) {
		this.consumer = consumer;
	}

	private List<ResponseGetKafestoreapiDto> servicioGetKafestoreapi() {

		Map<String, String> headers = new HashMap<>();
		headers.put(ConstantesOtpLogin.NCONTENTTYPE, ConstantesOtpLogin.APPLICATIONJSON);
		headers.put(ConstantesOtpLogin.ACCEPTLANGUAGE, ConstantesOtpLogin.LANGUAGE);

		consumer.setDefaultHeaders(headers);

		return consumer.getOneByQueryStringList(ConstantesOtpLogin.URL_BASE_FAKESTOREAPI, "/products")
				.block();
	}
	
	private ResponseGetKafestoreapiDto servicioGetKafestoreapiOne(RequestGetOneDto request) {

		Map<String, String> headers = new HashMap<>();
		headers.put(ConstantesOtpLogin.NCONTENTTYPE, ConstantesOtpLogin.APPLICATIONJSON);
		headers.put(ConstantesOtpLogin.ACCEPTLANGUAGE, ConstantesOtpLogin.LANGUAGE);

		consumer.setDefaultHeaders(headers);

		return consumer.getOneByQueryStringOne(ConstantesOtpLogin.URL_BASE_FAKESTOREAPI, "/products/"+request.getIdProducto(), ResponseGetKafestoreapiDto.class)
				.block();
	}

	@Override
	public ResponseServiceKafestoreapi getProducts() {
		ResponseServiceKafestoreapi respuesta = new ResponseServiceKafestoreapi();
		List<ResponseGetKafestoreapiDto> responseGetKafes = servicioGetKafestoreapi();
		if (responseGetKafes == null) {
			respuesta.setSuccess(false);
			respuesta.setMessage("No se encontraron productos");
		}else {
			respuesta.setItem(responseGetKafes);
			respuesta.setMessage("Proceso exitoso");
			respuesta.setSuccess(true);
		}
		return respuesta;
	}

	@Override
	public GenericResponseServiceDto<Object> getProductOne(RequestGetOneDto request) {
		var response = new GenericResponseServiceDto<Object>();
		ResponseGetKafestoreapiDto responseGetKafes = servicioGetKafestoreapiOne(request);
		if (responseGetKafes == null) {
			response.setSuccess(false);
			response.setMessage("No se encontraron productos");
		}else {
			response.setItem(responseGetKafes);
			response.setMessage("Proceso exitoso");
			response.setSuccess(true);
		}
		return response;
	}
}
