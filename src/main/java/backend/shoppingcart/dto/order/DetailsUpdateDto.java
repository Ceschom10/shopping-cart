package backend.shoppingcart.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailsUpdateDto {
	
	private int id;
	
	private int idProducto;
	
	private int cantidad;
	
	private double precioUnitario;
	
	private double subtotal;
}
