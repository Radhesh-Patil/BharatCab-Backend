package com.cab.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vehicles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int vehicleId;
	@Column(unique = true)
	private String vehicleRegiNo;
	private String vehicleCompany;
	private String vehicleModel;
	private int vehicleCapicity;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    
    private Users user; // Reference to Users entity
}
