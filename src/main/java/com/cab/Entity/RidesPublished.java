package com.cab.Entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor

@Entity
public class RidesPublished extends Rides {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer publishedRideId;
	private double farePerSeat;
	private int seatsAvailable;
	private boolean isActive=true;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	
	public RidesPublished( double farePerSeat, int seatsAvailable, boolean isActive,
			Users user) {
		super();
		this.farePerSeat = farePerSeat;
		this.seatsAvailable = seatsAvailable;
		this.isActive = isActive;
		this.user = user;
	}
	
}
