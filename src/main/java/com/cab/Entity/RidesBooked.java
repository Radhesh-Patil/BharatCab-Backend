//package com.cab.Entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class RidesBooked extends Rides{
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int bookedRideId;
//	private int publishedRideId;
//	private int seatsBooked;
//	private double totalFare;
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private Users user;
//
//}


package com.cab.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RidesBooked extends Rides {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookedRideId;
    
   
    private int seatsBooked;
    private double totalFare;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    // New ManyToOne relationship with RidesPublished
//    @ManyToOne
//    @JoinColumn(name = "published_ride_id", referencedColumnName = "publishedRideId")
//    private RidesPublished publishedRide;
//    
//    public RidesBooked(int ridePublishedId, String source,String sourceLocation,String destination,
//    		String destinationLocation,LocalDate rideDate,LocalTime rideTime,String vehicleName) {
//    	
//    }
}
