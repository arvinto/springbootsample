package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ReservationRepository rr) {
		return args -> {
			Arrays.asList("Mike, Boy, Girl".split(",")).forEach( n -> rr.save(new Reservation(n)));

			rr.findAll().forEach(System.out::println);

			rr.findByReservationName("Boy").forEach(System.out::println);
		};
	}
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{

	Collection<Reservation> findByReservationName( @Param("rn") String rn);
}


@Entity
class Reservation{

	@javax.persistence.Id
	@GeneratedValue
	private Long id;

	private String reservationName;

	Reservation(){

	}

	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	public Long getId() {
		return id;
	}

	public String getReservationName() {
		return reservationName;
	}

	@Override
	public String toString() {
		return "Reservations{" +
				"id=" + id +
				", reservationName='" + reservationName + '\'' +
				'}';
	}
}