package backend.shoppingcart.service;

import backend.shoppingcart.dto.base.GenericResponseServiceDto;
import backend.shoppingcart.dto.fakes.RequestGetOneDto;
import backend.shoppingcart.dto.fakes.ResponseServiceKafestoreapi;

public interface IFakesService {

	public ResponseServiceKafestoreapi getProducts();
	public GenericResponseServiceDto<Object> getProductOne(RequestGetOneDto request);
}
