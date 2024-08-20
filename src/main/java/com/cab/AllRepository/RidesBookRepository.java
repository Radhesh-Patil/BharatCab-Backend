package com.cab.AllRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.Entity.RidesBooked;

public interface RidesBookRepository extends JpaRepository<RidesBooked, Integer>{
	
	 List<RidesBooked> findByUser_UserId(int userId);
}
