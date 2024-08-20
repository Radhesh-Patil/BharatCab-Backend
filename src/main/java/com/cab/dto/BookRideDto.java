package com.cab.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRideDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private int id;  // booked ride id (optional if generated on the backend)

	@JsonProperty(access = Access.WRITE_ONLY)
	private int publishedRideId;  // ID of the published ride being booked
	
	private String source;  // Should be automatically filled from the published ride
	
	private String destination;
	
	private LocalDate rideDate;
	private LocalTime rideTime;
	private String vehicleName;
	private String vehicleNumber;

	private int seatsBooked;  // Number of seats to book
    @JsonProperty(access = Access.READ_ONLY)
    private double totalFare;  // Should be calculated on the backend
}
