package backend.shoppingcart.dto.exception;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import backend.shoppingcart.component.util.date.CustomZonedDateTimeDeserializer;
import backend.shoppingcart.component.util.date.CustomZonedDateTimeSerializer;
import backend.shoppingcart.component.util.date.DateUtils;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

/**Respuesta generica de exepciones
 * @author Javier Vanegas
 * @author Banco Cuscatlan
 * @version 1.0
 * @since 18/01/2022
*/
@Builder
@Getter
public class ResponseErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String object;
    private HttpStatus status;
    private String field;
    private String errorCode;
    private String message;
    private List<String> errors;
    @JsonSerialize(using = CustomZonedDateTimeSerializer.class)
    @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
    @Builder.Default()
    private ZonedDateTime timestamp = DateUtils.nowUtcZoned();
}
