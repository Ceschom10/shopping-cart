package backend.shoppingcart.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailsCreateDto {
	
	private int idProducto;
	
	private int cantidad;
	
	private double precioUnitario;
	
	private double subtotal;
}
