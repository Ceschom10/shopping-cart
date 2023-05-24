package backend.shoppingcart.component.util.tracer;

import java.util.Map;

/** Interfaz del generador de spans para el tracer
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
public interface TracerGenerator {
    public String traceSpan(String customSpan, String component, Map<String,String> tags);
}
