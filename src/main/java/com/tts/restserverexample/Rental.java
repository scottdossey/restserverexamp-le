package com.tts.restserverexample;

import java.util.Date;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min=2, max=30)
	private String customerFirstName;
	
	@Size(min=2, max=30)
	private String customerLastName;
	
	@Email
	@NotEmpty
	private String emailAddress;
	
	@CreditCardNumber
	private String creditCardNumber;
	
	@Digits(integer=3, fraction=2)
	private String cost;	
	
	@PastOrPresent
	private Date rentalDate;

	public Rental() {
		//Required for use of JPA.
	}
	
	public String getCustomerFirstName() {
		return customerFirstName;
	}

		
	public Rental(@Size(min = 2, max = 30) String customerFirstName, 
			      @Size(min = 2, max = 30) String customerLastName,
			      @Email String emailAddress,
			      @CreditCardNumber String creditCardNumber,
			      @Digits(integer = 3, fraction = 2) String cost,
			      @PastOrPresent Date rentalDate) 
	{
		super();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.emailAddress = emailAddress;
		this.creditCardNumber = creditCardNumber;
		this.cost = cost;
		this.rentalDate = rentalDate;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Rental [id=" + id + ", customerFirstName=" + customerFirstName + ", customerLastName="
				+ customerLastName + ", emailAddress=" + emailAddress + ", creditCardNumber=" + creditCardNumber
				+ ", cost=" + cost + ", rentalDate=" + rentalDate + "]";
	}
	
}
