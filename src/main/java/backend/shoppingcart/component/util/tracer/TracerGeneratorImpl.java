package backend.shoppingcart.component.util.tracer;


import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;


/** Interfaz del generador de spans para el tracer
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
@Component
public class TracerGeneratorImpl implements TracerGenerator {

    
    /** 
     * @param customSpan nombre del componente asignarle el traceId
     * @return SpanBuilder inicializador de spanIds
     */
    @Scope("singleton")
    private SpanBuilder traceInitializer(String customSpan) {
        Tracer tracer = GlobalTracer.get();
        return  tracer.buildSpan(customSpan)
        .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_SERVER);
    }
    
    /** 
     * @param customSpan identificador en jaeger para busquedas
     * @param component nombre de componente a asignarle el spanId
     * @param tags etiquetas de busqueda para el tracing en jaeger
     * @return String retorna el span id generado
     */
    public String traceSpan(String customSpan, String component, Map<String,String> tags){
        
        Span span = traceInitializer(customSpan).start();
        Tags.COMPONENT.set(span, component);

        if (tags!=null ){
            for (Map.Entry<String, String> pair : tags.entrySet()) {
                span.setTag(pair.getKey(), pair.getValue());  
            }
        }            
        
        span.finish();

        return span.context().toSpanId();
    }
}
