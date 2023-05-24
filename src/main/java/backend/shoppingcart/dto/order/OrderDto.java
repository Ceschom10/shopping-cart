package backend.shoppingcart.dto.order;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDto {

	private int id;
	
	private Calendar fecha;
	
	private String cliente;
	
	private double total;
}
