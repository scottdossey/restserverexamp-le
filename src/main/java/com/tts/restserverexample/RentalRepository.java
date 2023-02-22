package com.tts.restserverexample;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental,Long> {

	//We are adding a method to search by email address
	//essentially this will select * from rental where emailAddress=argument
	//Now we are using Spring Data JPA, so we don't have to actually
	//write an SQL query in SQL.
	//Instead we add a method to the repository that has a specific name.
	List<Rental> findByEmailAddress(String emailAddress);

	
	@Override
	List<Rental> findAll();
 }
