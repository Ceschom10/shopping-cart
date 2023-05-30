package backend.shoppingcart.dto.order;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderCreateDto {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone="America/El_Salvador")
	private Calendar fecha;
	
	@NotBlank(message = "message.request.idorden.null")
	@NotNull(message = "message.request.idorden.null")
	@Size(max=100, message = "message.request.idorden.size")
	private String cliente;
	
	private double total;
	
	private List<DetailsCreateDto> details;
}
