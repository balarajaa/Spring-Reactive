package com.learning.reactiveprogramming.handler;

import com.learning.reactiveprogramming.dao.CustomerDAO;
import com.learning.reactiveprogramming.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDAO customerDAO;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> customerList = customerDAO.getCustomerList();
        return ServerResponse.ok()
                .body(customerList,Customer.class);

    }

    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int customerId= Integer.valueOf( request.pathVariable("input"));
        // dao.getCustomerList().filter(c->c.getId()==customerId).take(1).single();
        Mono<Customer> customerMono = customerDAO.getCustomerList().filter(c -> c.getId() == customerId).next();
        return ServerResponse.ok().body(customerMono,Customer.class);
    }


    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse,String.class);
    }

}