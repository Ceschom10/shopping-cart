package backend.shoppingcart.component.util.date;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Deserializador de fechas en zoned utc
  * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
@Component
public class CustomZonedDateTimeDeserializer 
    extends StdDeserializer<ZonedDateTime> {



	private static final long serialVersionUID = 1L;
@Value("${app.conf.date.format}")
private String dateAppFormat;


    protected CustomZonedDateTimeDeserializer() {
        this(null);
    }
    
    protected CustomZonedDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    
    /** 
     * 
     * Deserializador
     * 
     * @param jsonParser json parser
     * @param ctxt json context
     * @return ZonedDateTime zoned date time de java
     * @throws IOException Input exception
     */
    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, 
        DeserializationContext ctxt)
            throws IOException {
        
        var formatter = DateTimeFormatter.ofPattern(dateAppFormat);
        String date = jsonParser.getText();
        return ZonedDateTime.parse(date,formatter);
    }
    
}
