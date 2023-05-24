package backend.shoppingcart.service.consumer.base;



import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import backend.shoppingcart.component.exception.GenericException;
import backend.shoppingcart.component.util.log.LogUtil;
import backend.shoppingcart.component.util.log.LogUtil.TYPELOG;
import backend.shoppingcart.dto.fakes.ResponseGetKafestoreapiDto;
import io.netty.handler.timeout.WriteTimeoutException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** WebFlux para consumo de servicios
 * @author Javier Vanegas
 * @author Banco Cuscatlan
 * @version 2.0
 * @param <T>
 * @since 19/07/2022
*/
@Service
public class RxGenericConsumerImpl<T> implements RxGenericConsumer {
	
	@Value("${app.conf.rest.timeout}")
	private Duration restTimeOut;
    
    private WebClient.Builder builder;
    private Map<String,String> headers;
    private LogUtil log;

    public RxGenericConsumerImpl(WebClient.Builder builder, LogUtil log) {
        this.builder = builder;
        this.log = log;
    }

    
    /** 
     * @param baseUrl url base
     * @return WebClient
     */
    public WebClient initializer(String baseUrl){ 

        Map<String,String> headerHelper = getDefaultHeaders();          

        return builder.baseUrl(baseUrl).defaultHeaders(defaultHeaders -> {
            for (var entry : headerHelper.entrySet()) {
                defaultHeaders.set(entry.getKey(), entry.getValue());
            }
        }).build();
    }

    
    /** 
     * @param request generic request
     * @param baseUrl url base
     * @param url sufijo de la url
     * @param response clase generica que va retornar
     * @return Mono{@literal <}T{@literal >}
     */
    public <W,T> Mono<T> postOneByBody(W request, String baseUrl, String url, Class<T> response){
        
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(response).timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","error.time.to.create", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
        
    }
   
    /** 
     * @param request generic request
     * @param baseUrl url base
     * @param url sufijo de la url
     * @param response clase generica que va retornar
     * @return Flux{@literal <}T{@literal >}
     */
    public <W,T> Flux<T> postAnyByBody(W request, String baseUrl, String url, Class<T> response){
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(HttpStatus::isError, resp -> resp.createException())
            .bodyToFlux(response);
    }


    
    /** 
     * @param request generic request
     * @param baseUrl url base
     * @param url sufijo de la url
     * @param response clase generica que va retornar
     * @return Mono{@literal <}T{@literal >}
     */
    public <T> Mono<T> getOneByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response){
        
        WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> uriBuilder
            .path(url)
            .queryParams(request)
            .build()
            ).retrieve().bodyToMono(response);
    }

    
    /** 
     * @param request generic request
     * @param baseUrl url base
     * @param url sufijo de la url
     * @param response clase generica que va retornar
     * @return Flux{@literal <}T{@literal >}
     */
    public <T> Flux<T> getAnyByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response){
        WebClient client = initializer(baseUrl);
        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .queryParams(request)
                .build()
            ).retrieve()
            .bodyToFlux(response);
    }

    
    /** 
     * @param baseUrl url base
     * @param url sujijo de la url
     * @param response clase generia que se retorna
     * @param arguments argumentos genericos 
     * @return Mono{@literal <}T{@literal >}
     */
    public <T> Mono<T> getOneByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments){
        
        WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build(arguments)
            ).retrieve()
            .bodyToMono(response)
            .timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","generic.consumer.error.timeout", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
    }

    
    /** 
     * @param baseUrl url base
     * @param url sujijo de la url
     * @param response clase generia que se retorna
     * @param arguments argumentos genericos 
     * @return Flux{@literal <}T{@literal >}
     */
    public <T> Flux<T> getAnyByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments){
        WebClient client = initializer(baseUrl);
        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build(arguments)
            ).retrieve()
            .bodyToFlux(response);
    }

    
    /** 
     * @param headers seteo de encabezado de peticion
     */
    public void setDefaultHeaders(Map<String,String> headers){
        this.headers = headers;
    }

    
    /** 
     * @return Map{@literal <}String, String{@literal >}
     */
    private Map<String,String> getDefaultHeaders() {
        Map<String,String> defaultHeader = new HashMap<>();
        defaultHeader.put("Content-Type", "application/json");
        defaultHeader.put("Accept", "*");

        if (headers == null || headers.isEmpty())
            return defaultHeader;
        else
            return headers;
    }

    
    /** 
     * @param request generic request
     * @param baseUrl url base
     * @param url sufijo de la url
     * @param response clase generica que va retornar
     * @return Mono{@literal <}T{@literal >}
     */
    @Override
    public <T> Mono<T> postOneByFormBody(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response) {
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromFormData(request))
            .retrieve()
            .bodyToMono(response);
    }


	@Override
	public Mono<List<ResponseGetKafestoreapiDto>> getOneByQueryStringList(String baseUrl, String url) {
		WebClient client = initializer(baseUrl);
		return client.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ResponseGetKafestoreapiDto.class)
                .collectList();
	}


	@Override
	public <T> Mono<T> getOneByQueryStringOne(String baseUrl, String url, Class<T> response) {
		WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build()
            ).retrieve()
            .bodyToMono(response)
            .timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","generic.consumer.error.timeout", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
	}


}