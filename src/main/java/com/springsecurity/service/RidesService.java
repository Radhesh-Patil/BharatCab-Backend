package com.springsecurity.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cab.AllRepository.RidesBookRepository;
import com.cab.AllRepository.RidesPublishedRepository;
import com.cab.AllRepository.RidesRepository;
import com.cab.AllRepository.UserRegisterLoginRepo;
import com.cab.AllRepository.VehicleRepository;
import com.cab.Entity.RidesBooked;
import com.cab.Entity.RidesPublished;
import com.cab.Entity.UserPrincipal;
import com.cab.Entity.Users;
import com.cab.Entity.Vehicles;
import com.cab.dto.AvailableMatchingRidesDTO;
import com.cab.dto.BookRideDto;
import com.cab.dto.PublishRideDTO;
import com.cab.exception.InsufficientSeatsException;

@Service
public class RidesService {

	@Autowired
	private RidesPublishedRepository ridesPublishedRepo;

	@Autowired
	private UserRegisterLoginRepo userRepo;

	@Autowired
	private RidesRepository ridesrepo;

	@Autowired
	private RidesBookRepository ridesBookedRepo;
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	
	// ModelMapper
	ModelMapper mapper = new ModelMapper();

	// Service for publishing a ride
	public ResponseEntity<?> publishRide(PublishRideDTO pride) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String useremail;

		if (principal instanceof UserDetails) {
			useremail = ((UserDetails) principal).getUsername();
		} else {
			useremail = principal.toString();
		}
		
		Users user = userRepo.findByUserEmail(useremail);

//		if(user == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ")
//		}

		LocalDate rideDate = pride.getRideDate();
		LocalTime rideTime = pride.getRideTime();

		List<RidesPublished> existingRides = ridesPublishedRepo.findByUserAndRideDateAndRideTime(user, rideDate,
				rideTime);

		if (!existingRides.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("You already have a ride scheduled for this date and time.");
		}
		
		
		Optional<Vehicles> op= vehicleRepo.findById(pride.getVehicleId());
		
		Vehicles vehi=op.get();
		
		RidesPublished newRide = new RidesPublished();
		newRide.setUser(user); // Set the user
		newRide.setSource(pride.getSource());
		newRide.setDestination(pride.getDestination());
		newRide.setRideDate(rideDate);
		newRide.setRideTime(rideTime);
		newRide.setVehicleName(vehi.getVehicleModel());
		newRide.setVehicleNumber(vehi.getVehicleRegiNo());
		newRide.setFarePerSeat(pride.getFarePerSeat());
		newRide.setSeatsAvailable(pride.getSeatsAvailable());

		ridesPublishedRepo.save(newRide);
		return ResponseEntity.status(HttpStatus.CREATED).body("Ride Published");
	}

	// Service for getting all rides published rides
	public List<PublishRideDTO> getAllPublishedRides() {

		List<RidesPublished> rides = ridesrepo.findAll();

		if (rides == null) {
			return null;
		}
		return rides.stream()
				.map(ride -> new PublishRideDTO(ride.getPublishedRideId(), ride.getSource(),
						ride.getDestination(),  ride.getRideDate(), ride.getRideTime(),
						ride.getVehicleName(), ride.getVehicleNumber(),ride.getFarePerSeat(), ride.getSeatsAvailable(),
						ride.isActive()))
				.collect(Collectors.toList());
	}

	// Service for getting Specified rides
	public List<PublishRideDTO> getRidesSpecified(AvailableMatchingRidesDTO ride) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
        
        int currentUserid=currentUserPrincipal.getUser().getUserId();
        
		String source = ride.getSource();
		String destination = ride.getDestination();
		LocalDate date = ride.getRideDate();

		List<RidesPublished> rideList = ridesPublishedRepo.findBySourceAndDestinationAndRideDate(source, destination,
				date);
		
		if (rideList == null) {
			return null;
		}
		return rideList.stream()
				.filter(rides->rides.getUser().getUserId()!=currentUserid)  ///Removing rides published by current user
				.map(rides -> mapper.map(rides, PublishRideDTO.class))
				.collect(Collectors.toList());

	}

	public RidesBooked bookARide(BookRideDto bkride) throws InsufficientSeatsException {
	    // Get the published ride by ID
	    int id = bkride.getPublishedRideId();
	    Optional<RidesPublished> pubrideOptional = ridesPublishedRepo.findById(id);
	    System.out.println("In book ride");

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
	    RidesPublished pubride = pubrideOptional.get();
	    
	 
	    
	    // Check if there are enough available seats for the booking
	    int seats = bkride.getSeatsBooked();
	    if (pubride.getSeatsAvailable() < seats) {
	        throw new InsufficientSeatsException("Not enough seats available for booking.");
	    }
	    
	    // Calculate the total fare
	    double fare = pubride.getFarePerSeat() * seats;
	    
	    // Create a new RidesBooked instance and populate it with data from the DTO and the published ride
	    RidesBooked bride = new RidesBooked();
	    bride.setSource(bkride.getSource());
	    
	    bride.setDestination(bkride.getDestination());
	    
	    bride.setRideDate(bkride.getRideDate());
	    bride.setRideTime(bkride.getRideTime());
	    bride.setVehicleName(bkride.getVehicleName());
	    bride.setVehicleNumber(bkride.getVehicleNumber());
	    bride.setTotalFare(fare);
	    bride.setUser(currentUserPrincipal.getUser());
	    bride.setSeatsBooked(seats);
	    
	    
	    // Save the booked ride
	    ridesBookedRepo.save(bride);
	    
	    // Update the available seats in the published ride
	    pubride.setSeatsAvailable(pubride.getSeatsAvailable() - seats);
	    ridesPublishedRepo.save(pubride);
	    
	    return bride;
	}

	public List<RidesBooked> getRideBookingHistory() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		int id=currentUserPrincipal.getUser().getUserId();
		
		List<RidesBooked> ridesbooked=ridesBookedRepo.findByUser_UserId(id);
		
        return ridesbooked;
	}

	public List<RidesPublished> getRidePublishHistory() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		int id=currentUserPrincipal.getUser().getUserId();
		
		List<RidesPublished> ridespublished=ridesPublishedRepo.findByUser_UserId(id);
		
        return ridespublished;
		
	}

	

}
