package backend.shoppingcart.component.util.date;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** Serializador de fechas
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
@Component
public class CustomZonedDateTimeSerializer 
    extends StdSerializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;
	@Value("${app.conf.date.format}")
	private String dateAppFormat;

    protected CustomZonedDateTimeSerializer() {
        this(null);
        
    }

    protected CustomZonedDateTimeSerializer(Class<ZonedDateTime> t) {
        super(t);
    }
    
    /**
     * 
     * Serializador
     *  
     * @param value valor de fecha de ingreso
     * @param gen json generator
     * @param provider serializer provider
     * @throws IOException input exception
     */
    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        var formatter = DateTimeFormatter.ofPattern(dateAppFormat);
        gen.writeString(formatter.format(value));
    }
    
}
