package client;

import dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

public class OrderEventClient {

    protected final RestTemplate rest;

    private static final String API_PREFIX = "/events";

    @Autowired
    public OrderEventClient(String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build();
    }

    public ResponseEntity<OrderEvent> post(String path, @Nullable Map<String, Object> parameters,
                                           OrderEvent body) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body);
    }

    public ResponseEntity<OrderEvent> get(String path, Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters);
    }

    private ResponseEntity<OrderEvent> makeAndSendRequest(HttpMethod get, String path, Map<String, Object> parameters) {
        HttpEntity<OrderEvent> requestEntity = new HttpEntity<>(new OrderEvent());

        ResponseEntity<OrderEvent> eventStoreResponse;
        try {
            if (parameters != null) {
                eventStoreResponse = rest.exchange(path, get, requestEntity, OrderEvent.class, parameters);
            } else {
                eventStoreResponse = rest.exchange(path, get, requestEntity, OrderEvent.class);
            }
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
        return prepareEventStoreResponse(eventStoreResponse);
    }

    private ResponseEntity<OrderEvent> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters,
                                                          OrderEvent body) {
        HttpEntity<OrderEvent> requestEntity = new HttpEntity<>(body);

        ResponseEntity<OrderEvent> eventStoreResponse;
        try {
            if (parameters != null) {
                eventStoreResponse = rest.exchange(path, method, requestEntity, OrderEvent.class, parameters);
            } else {
                eventStoreResponse = rest.exchange(path, method, requestEntity, OrderEvent.class);
            }
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
        return prepareEventStoreResponse(eventStoreResponse);
    }

    private static ResponseEntity<OrderEvent> prepareEventStoreResponse(ResponseEntity<OrderEvent> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

}