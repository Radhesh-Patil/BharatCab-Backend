package com.springsecurity.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.springsecurity.model.UserPrincipal;
//import com.springsecurity.model.Users;
//import com.security.repository.UserRepo;
//
//@Service  // Mark as a service so that Spring can manage it
//public class MyUserDetailsService implements UserDetailsService {
//    
//    @Autowired
//    private UserRepo repo;  // Inject the user repository
//    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Find the user by username
//        Users user = repo.findByUsername(username);
//        
//        // If the user is not found, throw an exception
//        if (user == null) {
//        	System.out.println("UserNot Found");
//            throw new UsernameNotFoundException("User with username '" + username + "' not found");
//        }
//        
//        // Return the user details object
//        return new UserPrincipal(user);
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cab.AllRepository.UserRegisterLoginRepo;
import com.cab.Entity.UserPrincipal;
import com.cab.Entity.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRegisterLoginRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUserEmail(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        
        return new UserPrincipal(user);
    }
}