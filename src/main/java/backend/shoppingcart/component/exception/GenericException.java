package backend.shoppingcart.component.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import backend.shoppingcart.dto.exception.BaseErrorDto;
import lombok.Getter;
import lombok.ToString;

/**
 * Custom exception
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
 */
@Component
@Getter
@ToString
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final BaseErrorDto baseError;

	/*
	 * Constructor vacio
	 */
	public GenericException() {
		super("error.genericexception.nocontrolled");
		this.baseError = BaseErrorDto.builder().httpHeaders(new HttpHeaders())
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).field("").build();
	}

	public GenericException(String message) {
		super(message);
		this.baseError = BaseErrorDto.builder().httpHeaders(new HttpHeaders())
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).field("").build();
	}

	/*
	 * Constructor
	 * 
	 * @param httpHeaders HttpHeaders
	 * 
	 * @param httpStatus HttpStatus
	 * 
	 * @param field String
	 * 
	 * @param message String
	 */
	public GenericException(HttpStatus httpStatus, HttpHeaders httpHeaders, String field, String message,
			List<String> errors) {
		super(message);
		this.baseError = BaseErrorDto.builder().httpHeaders(httpHeaders).httpStatus(httpStatus).field(field)
				.errors(errors).build();
	}

	/*
	 * Constructor
	 * 
	 * @param httpStatus HttpStatus
	 * 
	 * @param field String
	 * 
	 * @param message String
	 */
	public GenericException(HttpStatus httpStatus, String field, String message, List<String> errors) {
		super(message);
		this.baseError = BaseErrorDto.builder().httpHeaders(new HttpHeaders()).httpStatus(httpStatus).field(field)
				.errors(errors).build();
	}

}
