package com.javatechie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.dao.CustomerDao;
import com.javatechie.dto.Customer;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	@Autowired
	public CustomerDao customerDao;

	public List<Customer> loadAllCustomers() { // Normal Traditional Approach

		long start = System.currentTimeMillis();
		List<Customer> customers = customerDao.getCustomers();
		long end = System.currentTimeMillis();
		System.err.println("Total Execution Time: " + (end - start));
		return customers;
	}

	public Flux<Customer> loadAllCustomerUsingStream() {
		long start = System.currentTimeMillis();
		Flux<Customer> customers = customerDao.getCustomersStream();
		long end = System.currentTimeMillis();
		System.err.println("Total Execution Time in Stream : " + (end - start));
		return customers;
	}
}
