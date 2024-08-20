package com.cab.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableMatchingRidesDTO {
	
	private String source;
	private String destination;
	private LocalDate rideDate;
}
