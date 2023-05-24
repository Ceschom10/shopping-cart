package backend.shoppingcart.dto.fakes;


import java.util.List;

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
public class ResponseServiceKafestoreapi {

	private Boolean success;
	private String message;
	private List<ResponseGetKafestoreapiDto> item;

}
