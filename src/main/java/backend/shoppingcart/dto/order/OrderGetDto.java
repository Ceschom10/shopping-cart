package backend.shoppingcart.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderGetDto {
	@NotNull(message = "message.request.idorden.null")
	@Min(value = 1, message = "message.request.idorden.min")
	private int idOrden;
}
