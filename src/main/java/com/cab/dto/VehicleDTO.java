package com.cab.dto;

import com.cab.Entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
	private String vehicleRegiNo;
	private String vehicleCompany;
	private String vehicleModel;
	private int vehicleCapicity;
	@JsonProperty(access = Access.READ_ONLY)
	private Users user;
	
}
