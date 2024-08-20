package com.cab.AllRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab.Entity.Users;
import com.cab.Entity.Vehicles;

public interface VehicleRepository extends JpaRepository<Vehicles, Integer>{
	List<Vehicles> findByUser(Users user);
}
