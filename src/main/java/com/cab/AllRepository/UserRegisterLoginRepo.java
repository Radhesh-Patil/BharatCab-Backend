package com.cab.AllRepository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cab.Entity.Users;

@Repository
public interface UserRegisterLoginRepo extends JpaRepository<Users,Integer> {
	
	Users findByUserEmail(String email);
	Optional<Users> findByUserEmailAndUserPassword(String email, String password);
}
	