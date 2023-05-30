package backend.shoppingcart.component.util;

/** Metodo date time util
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import backend.shoppingcart.component.exception.GenericException;

public class DateTimeUtil {
	
	public static final String PATTERN = "yyyy-MM-dd";
	
	private DateTimeUtil() {
		
	}

	
	public static java.sql.Date toSqlDate(Date date) {
		  
	    SimpleDateFormat format = new SimpleDateFormat(PATTERN);

        Date parsed = new Date();
		try {
			parsed = format.parse(format.format(date));
		} catch (ParseException e) {
			throw new GenericException();
		}
        
		try {
		return new java.sql.Date(parsed.getTime());
		}catch (Exception e) {
			throw new GenericException();
		}

	}
	
	public static java.sql.Time extractTime(Date date){
		return 	new Time(date.getTime());
	}
	
	public static String dateToString(Date date) {
		if(date == null) {
			return "";
		}else{
			return new SimpleDateFormat(PATTERN).format(date);
		}

	}
}
