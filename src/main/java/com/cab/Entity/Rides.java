package com.cab.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Rides {
	
	private String source;
	private String destination;
	private LocalDate rideDate;
	private LocalTime rideTime;
	private String vehicleName;
	private String vehicleNumber;
}
