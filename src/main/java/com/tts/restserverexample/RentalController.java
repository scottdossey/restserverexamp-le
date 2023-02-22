package com.tts.restserverexample;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

//First thing lets do is talk about 
//the @Controller annotation

//The @RestController annotation works
//exactly like the @Controller annotation
//except it adds @ResponseBody automatically to
//all of the controller endpoints

@RestController
public class RentalController {
	@Autowired
	private RentalRepository repository;

	//What actually happens when you
	//return an object from a controller,
	//is it passed through something called the 
	//ViewResolver....and the ViewResolver basically looks
	//at the object type returned, and tries to dispatch 
	//that object to a handler in Spring Boot called a View...
	//Thymeleaf templates are one such handler...
	//so if whe Thymeleaf installed, and we return a string,
	//the ViewResolver resolves that to send the return object
	//to Thymeleaf so it knows what template to load.
	@GetMapping("/demo")
	//@ResponseBody //use this annotation to return
	              //the return object directly as JSON
	public ResponseEntity<Rental> getDemoRental() {
		Rental myRental = new Rental("Remo", 
				                     "Williams",
				                     "remo@gmail.com",
				                     "4929456050956337",
				                     "200.79",
				                     new Date()
				                    );
		//What we would like to do is just return
		//the myRental object in JSON format.
		
		//The way we can do that is by telling 
		//SpringBoot that we want the return object to 
		//be displayed as a direct JSON object...
		//and we can do that by adding an annotation to
		//the controller (@ResponseBody)
		return new ResponseEntity<>(myRental, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/rentals")
	public ResponseEntity<List<Rental>> getRentals() {
		List<Rental> rentals = repository.findAll();
		return new ResponseEntity<>(rentals, HttpStatus.OK);		
	}
	
	
	/* We want to write an API that takes in a Rental object
	 * as JSON, and then stores it in the database.
	 * 
	 * We want an endpoint of "/rentals",
	 * and we want creation events to be post requests.
	 */
	@PostMapping("/rentals")
	public ResponseEntity<Void> createRental(@RequestBody @Valid Rental rental,
			                                 BindingResult bindingResult) {
		//Now let's write something to save this to the database.
		//Let's validate that the email address is unique.
		//IN order to check that email address is unique,
		//we need to have some way of querying the Rental table
		//via the RentalRepository if an email address exists.
		
		//If we look at CrudRepository--that method does not currently
		//exist--we have things findById, we have method to save an entry...
		//there's a whole bunch of options, but nothing to findByEmail.
		//We have to add a query to our RentalRepository.
		if(repository.findByEmailAddress(rental.getEmailAddress()).size() > 0) {
			//We know that someone already has that email address.
			bindingResult.rejectValue("emailAddress", "error.email", "Email address is already taken");
		}
		
		
		if(bindingResult.hasErrors()) {
			//This returns error code 400 if the data is improperly formatted.
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			repository.save(rental);
			//This returns error 201, which we use when we create an object.			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}		
	}
	
	
	@GetMapping(path="/rentals/{id}")
	public ResponseEntity<Rental> getRentalById(@PathVariable Long id){
		Optional<Rental> rentalBox=repository.findById(id);
		if(rentalBox.isPresent()) {
			return new ResponseEntity<>(rentalBox.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path="/rentals/{id}")
	public ResponseEntity<Rental> updateRental(@PathVariable Long id, 
											   @RequestBody @Valid Rental rental,
											   BindingResult bindingResult) {
		Optional<Rental> rentalBox=repository.findById(id);
		if(rentalBox.isPresent()) {
			if(!bindingResult.hasErrors()) {
								
				Rental current=rentalBox.get();
				current.setCustomerFirstName(rental.getCustomerFirstName());
				current.setCustomerLastName(rental.getCustomerLastName());
				current.setCustomerLastName(rental.getCustomerLastName());
				current.setCreditCardNumber(rental.getCreditCardNumber());
				current.setEmailAddress(rental.getEmailAddress());
				current.setRentalDate(rental.getRentalDate());
				current.setCost(rental.getCost());
				repository.save(current);
				return new ResponseEntity<>(current, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	
}
