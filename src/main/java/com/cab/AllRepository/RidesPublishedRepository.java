package com.cab.AllRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.Entity.Rides;
import com.cab.Entity.RidesPublished;
import com.cab.Entity.Users;
import java.time.LocalDate;
import java.time.LocalTime;

public interface RidesPublishedRepository extends JpaRepository<RidesPublished,Integer>{
	
	List<RidesPublished> findByUserAndRideDateAndRideTime(Users user,LocalDate rideDate, LocalTime rideTime);
	
	List<RidesPublished> findBySourceAndDestinationAndRideDate(String source, String destination, LocalDate date );

	List<RidesPublished> findByUser_UserId(int id);

}
