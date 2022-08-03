package com.javatechie.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.javatechie.dao.CustomerDao;
import com.javatechie.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

	@Autowired
	private CustomerDao dao;

	public Mono<ServerResponse> loadCustomers(ServerRequest request) {

		Flux<Customer> customerList = dao.getCustomer();
		return ServerResponse.ok().body(customerList, Customer.class);
	}

	public Mono<ServerResponse> getCustomersStream(ServerRequest request) {

		Flux<Customer> customersStream = dao.getCustomersStream();
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(customersStream, Customer.class);
	}

	public Mono<ServerResponse> findCustomer(ServerRequest request) {
		int customerID = Integer.parseInt(request.pathVariable("input"));

//		dao.getCustomer().filter(c -> c.getId()== customerID).take(1).single();
		Mono<Customer> customerMono = dao.getCustomer().filter(c -> c.getId() == customerID).next();
		return ServerResponse.ok().body(customerMono, Customer.class);

	}
	
	public Mono<ServerResponse> saveCustomer(ServerRequest request){
		Mono<Customer> saveCustomer = request.bodyToMono(Customer.class);
		Mono<String> saveResponse = saveCustomer.map(dto -> dto.getId()+":"+dto.getName());
		
		return ServerResponse.ok().body(saveResponse,String.class);
	}
}
