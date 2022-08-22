package com.learning.reactiveprogramming.handler;

import com.learning.reactiveprogramming.dao.CustomerDAO;
import com.learning.reactiveprogramming.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {

    @Autowired
    private CustomerDAO customerDAO;

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customersReactive = customerDAO.getCustomersReactive();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersReactive,Customer.class);


    }
}
