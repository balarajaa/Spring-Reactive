package com.learning.reactiveprogramming.dao;

import com.learning.reactiveprogramming.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDAO {

    private static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1,10)
                .peek(CustomerDAO::sleepExecution)
                .peek(i -> System.out.println("Process Count: "+i))
                .mapToObj(i -> new Customer(i,"customer"+i))
                .collect(Collectors.toList());

    }

    public Flux<Customer> getCustomersReactive() {

        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count : "+i))
                .map(i -> new Customer(i, "customer+i"));
    }

    public Flux<Customer> getCustomerList() {

        return Flux.range(1,50)
                .doOnNext(i -> System.out.println("Processing count : "+i))
                .map(i -> new Customer(i, "customer+i"));
    }
}
