package client;

import dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

public class OrderClient {

    protected final RestTemplate rest;

    private static final String API_PREFIX = "/events/order";

    @Autowired
    public OrderClient(String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public ResponseEntity<Order> get(String path, Map<String, Object> parameters) {
        return makeAndSendRequest(path, parameters);
    }

    private ResponseEntity<Order> makeAndSendRequest(String path,
                                                     Map<String, Object> parameters) {
        HttpEntity<Order> requestEntity = new HttpEntity<>(new Order());

        ResponseEntity<Order> eventStoreResponse;
        try {
            if (parameters != null) {
                eventStoreResponse = rest.exchange(path, HttpMethod.GET, requestEntity, Order.class, parameters);
            } else {
                eventStoreResponse = rest.exchange(path, HttpMethod.GET, requestEntity, Order.class);
            }
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
        return prepareEventStoreResponse(eventStoreResponse);
    }

    private static ResponseEntity<Order> prepareEventStoreResponse(ResponseEntity<Order> response) {
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