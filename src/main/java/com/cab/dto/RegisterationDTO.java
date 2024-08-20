package com.cab.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RegisterationDTO {
	
	private String firstName;

	private String lastName;

	@NotEmpty
	@Email(message = "Invalid Email")
	private String userEmail;
	
	private String userPassword;

	
	private String userMobileNo;
	
	private String userLisenceNo;

	private LocalDate dateOfBirth;
//	private String profileImage;
}

