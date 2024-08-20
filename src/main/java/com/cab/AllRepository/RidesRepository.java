package com.cab.AllRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.Entity.RidesPublished;

public interface RidesRepository extends JpaRepository<RidesPublished, Integer> {
	
}
