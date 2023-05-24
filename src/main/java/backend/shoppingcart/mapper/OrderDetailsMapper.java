package backend.shoppingcart.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import backend.shoppingcart.dto.order.DetailsUpdateDto;
import backend.shoppingcart.model.OrderDetails;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {
OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

	List<DetailsUpdateDto> detailsToDetailsDto(List<OrderDetails> orderDetails);
	DetailsUpdateDto detailsToDetailsDto(OrderDetails orderDetails);
}
