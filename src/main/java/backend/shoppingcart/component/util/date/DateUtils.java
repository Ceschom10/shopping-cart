package backend.shoppingcart.component.util.date;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import backend.shoppingcart.component.exception.GenericException;

/** Utilidades para manejo de fechas del api
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
@Component
public class DateUtils {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private DateUtils() {}  
    
    /** 
     * Retorno de fecha estandar UTC
     * 
     * @return ZonedDateTime
     */
    public static ZonedDateTime nowUtcZoned() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
    
    public static Date fecha() {
    	try {
            TimeZone timeZone = TimeZone.getTimeZone("America/El_Salvador");
            SimpleDateFormat formatterWithTimeZone = new SimpleDateFormat(DATE_FORMAT);
            formatterWithTimeZone.setTimeZone(timeZone);
           
            Calendar fecha = Calendar.getInstance();
            Date newDate = fecha.getTime();
           
            String sDate = formatterWithTimeZone.format(newDate);
           
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
           
            return formatter.parse(sDate);
    	}catch (Exception e) {
			List<String> errors = new ArrayList<>();
			errors.add(e.getMessage());
			throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "component.util.date fecha","bancocuscatlan.cashtransfer.service.impl",errors);	
		}
	}
}
