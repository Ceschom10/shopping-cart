package backend.shoppingcart.dto.fakes;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestGetOneDto {
	@NotNull(message = "message.request.idproducto")
	private int idProducto;
}
