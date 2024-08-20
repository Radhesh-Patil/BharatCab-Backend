package com.cab.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cab.Entity.RidesBooked;
import com.cab.Entity.RidesPublished;
import com.cab.dto.AvailableMatchingRidesDTO;
import com.cab.dto.BookRideDto;
import com.cab.dto.PublishRideDTO;
import com.cab.exception.InsufficientSeatsException;
import com.springsecurity.service.RidesService;

@RestController
public class RidesController {

	@Autowired
	RidesService rideService;

	@PostMapping("/publish")
	public ResponseEntity<?> publishRide(@RequestBody PublishRideDTO pride) {
		return rideService.publishRide(pride);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllPublishedRides() {
		List<PublishRideDTO> l = rideService.getAllPublishedRides();
		if (l == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rides found");
		}
		return ResponseEntity.ok(l);
	}

	@PostMapping("/availableMatchingRides")
	public ResponseEntity<?> getRidesSpecified(@RequestBody AvailableMatchingRidesDTO rides) {
		List<PublishRideDTO> l = rideService.getRidesSpecified(rides);
		if (l == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rides found");
		}
		return ResponseEntity.ok(l);
	}

	@PostMapping("/book-ride")
	public ResponseEntity<?> bookRide(@RequestBody BookRideDto bkride) {
		
		
		try {
			return ResponseEntity.ok(rideService.bookARide(bkride));
		} catch (InsufficientSeatsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking Failed");
		}

	}
	
	
	@GetMapping("/ride-bookin-hisory")
	public ResponseEntity<?> rideBookinHistory(){
		List<RidesBooked> list=rideService.getRideBookingHistory();
		
		if(list==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rides");
		}
		else {
			return ResponseEntity.ok(list);
		}
	}
	
	@GetMapping("/ride-publish-hisory")
	public ResponseEntity<?> ridePublishHistory(){
		List<RidesPublished> list=rideService.getRidePublishHistory();
		
		if(list==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rides");
		}
		else {
			return ResponseEntity.ok(list);
		}
	}
}
