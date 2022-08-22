package com.learning.reactiveprogramming.service;

import com.learning.reactiveprogramming.dao.CustomerDAO;
import com.learning.reactiveprogramming.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public List<Customer> loadAllCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customerList = customerDAO.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Total time: "+(end-start));
        return customerList;

    }


    public Flux<Customer> loadAllCustomersReative() {
        long start = System.currentTimeMillis();
        Flux<Customer> customerList = customerDAO.getCustomersReactive();
        long end = System.currentTimeMillis();
        System.out.println("Total time: "+(end-start));
        return customerList;
    }
}
