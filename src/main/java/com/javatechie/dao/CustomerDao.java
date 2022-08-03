package com.javatechie.dao;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.javatechie.dto.Customer;

import reactor.core.publisher.Flux;

@Component
public class CustomerDao {

	private static void sleepExecution(int i) {

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public List<Customer> getCustomers() {

		return IntStream.rangeClosed(1, 50)
				.peek(CustomerDao::sleepExecution) // Here we sleep 500 Milisecond think that db takes time to give data
				.peek(i -> System.out.println("Processing Count: " + i))
				.mapToObj(i -> new Customer(i, "Customer " + i))
				.collect(Collectors.toList());
	}
	
	public Flux<Customer> getCustomersStream(){
		return Flux.range(1,20)
				.delayElements(Duration.ofMillis(500))
				.doOnNext(i -> System.out.println("Processing Count Stream : " + i))
				.map(i -> new Customer(i,"Customer Stream "+i))
				.log();
	}
	
	public Flux<Customer> getCustomer(){
		return Flux.range(1, 20)
				.doOnNext(i -> System.out.println("Processing Count:: "+i))
				.map(i -> new Customer(i, "New customer "+i))
				.log();
	}
}
