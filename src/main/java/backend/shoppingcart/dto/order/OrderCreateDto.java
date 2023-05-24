package backend.shoppingcart.dto.order;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderCreateDto {
	
	private Calendar fecha;
	
	private String cliente;
	
	private double total;
	
	private List<DetailsCreateDto> details;
}
