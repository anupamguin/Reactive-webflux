package com.javatechie;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

	@Test
	public void monoTest() {
		Mono<?> monoString = Mono.just("Anupam Guin")
				.then(Mono.error(new RuntimeException("Exx...")))
				.log();
		monoString.subscribe(System.out::println,(error)-> System.out.println(error.getMessage()));
	}
	
	@Test
	public void fluxTest() {
		Flux<?> fluxString = Flux.just("Anupam","Akash","Dinu","Apu")
				.concatWith(Flux.just("GopiKanta"))
				.concatWithValues("Tripti")
				.log();
		fluxString.subscribe(System.out::println);
	}
}
