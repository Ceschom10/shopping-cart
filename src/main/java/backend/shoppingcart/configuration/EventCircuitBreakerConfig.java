package backend.shoppingcart.configuration;

import java.time.ZonedDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.shoppingcart.component.util.log.LogUtil;
import backend.shoppingcart.component.util.log.LogUtil.TYPELOG;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.AllArgsConstructor;


/** Configuracion del circuit breaker para el monitoreo de errores
 * @author Cesar Amaya
 * @version 1.0
 * @since 23/05/2023
*/
@Configuration
public class EventCircuitBreakerConfig {
   
    @AllArgsConstructor
    private class CircuitBreakerLogDto {
        private String name;
        private float rate;
        private ZonedDateTime creatioDate; 
        private long duration;
    }

    private LogUtil log;

    public EventCircuitBreakerConfig(LogUtil log) {
        this.log = log;
    }

    
    /** 
     * @return RegistryEventConsumer{@literal <}CircuitBreaker{@literal <} retorna el inicializador de eventos del tracer
     */
    @Bean
    public RegistryEventConsumer<CircuitBreaker> circuitBreakerEventConsumer() {
        
        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                    .onFailureRateExceeded(event -> 
                        log.write(TYPELOG.DEBUG, "CircuitBreaker Failure",
                        new CircuitBreakerLogDto(event.getCircuitBreakerName(),event.getFailureRate(),event.getCreationTime(),-1))
                    )
                    .onSlowCallRateExceeded(event -> log.write(TYPELOG.DEBUG, "CircuitBreaker Slow Call",
                        new CircuitBreakerLogDto(event.getCircuitBreakerName(),event.getSlowCallRate(),event.getCreationTime(),-1))
                    )
                    .onCallNotPermitted(event -> log.write(TYPELOG.DEBUG, "CircuitBreaker Call No Permited",
                        new CircuitBreakerLogDto(event.getCircuitBreakerName(),-1,event.getCreationTime(),-1))
                    )
                    .onError(event -> log.write(TYPELOG.DEBUG, "CircuitBreaker Error",
                        new CircuitBreakerLogDto(event.getCircuitBreakerName(),-1,event.getCreationTime(),event.getElapsedDuration().getSeconds()))
                    )
                    .onStateTransition(
                        event -> log.write(TYPELOG.DEBUG, "CircuitBreaker Transition",
                            new CircuitBreakerLogDto(event.getCircuitBreakerName()
                                +" State From "+event.getStateTransition().getFromState()
                                + "To "+event.getStateTransition().getToState()
                                ,-1,event.getCreationTime(),-1))
                    );
            }
            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                entryRemoveEvent.getRemovedEntry().getEventPublisher()
                    .onFailureRateExceeded(event -> log.write(TYPELOG.DEBUG, "Circuit breaker event removed",
                        event.getCircuitBreakerName()));
            }
            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                entryReplacedEvent.getNewEntry().getEventPublisher()
                    .onFailureRateExceeded(event -> log.write(TYPELOG.DEBUG, "Circuit breaker event replaced",
                        event.getCircuitBreakerName()));
            }
        };
    }
}
