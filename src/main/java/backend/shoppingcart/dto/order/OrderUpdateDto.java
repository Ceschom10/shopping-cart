package backend.shoppingcart.dto.order;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderUpdateDto {
	
	private int id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone="America/El_Salvador")
	private Calendar fecha;
	
	private String cliente;
	
	private double total;
	
	private List<DetailsUpdateDto> details;
}
