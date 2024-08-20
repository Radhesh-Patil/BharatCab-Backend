package com.cab.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PublishRideDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private int Id;  //Published Ride id
	private String source;
	private String destination;
	private LocalDate rideDate;
	private LocalTime rideTime;
	@JsonProperty(access = Access.READ_ONLY)
	private String vehicleName;
	@JsonProperty(access = Access.READ_ONLY)
	private String vehicleNumber;
	@JsonProperty(access = Access.WRITE_ONLY)
	private int vehicleId;
	private double farePerSeat;
	private int seatsAvailable;
	@JsonProperty(access = Access.READ_ONLY)
	private boolean isActive;
//	private int uid;
	
	public PublishRideDTO(int id, String source, String destination, LocalDate rideDate, LocalTime rideTime,
			String vehicleName, String vehicleNumber, double farePerSeat, int seatsAvailable, boolean isActive) {
		super();
		Id = id;
		this.source = source;
		this.destination = destination;
		this.rideDate = rideDate;
		this.rideTime = rideTime;
		this.vehicleName = vehicleName;
		this.vehicleNumber = vehicleNumber;
		this.farePerSeat = farePerSeat;
		this.seatsAvailable = seatsAvailable;
		this.isActive = isActive;
//		this.uid = uid;
	}
}
