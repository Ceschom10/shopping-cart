package backend.shoppingcart.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import backend.shoppingcart.dto.order.OrderUpdateDto;
import backend.shoppingcart.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	List<OrderUpdateDto> orderListToOrderDtoList(List<Order> orders);
	OrderUpdateDto orderToOrderDto(Order order);
}
