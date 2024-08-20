package com.cab.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Correct import
import org.springframework.web.bind.annotation.RestController;

import com.cab.Entity.Vehicles;
import com.cab.dto.ApiResponse;
import com.cab.dto.LoginDTO;
import com.cab.dto.RegisterationDTO;
import com.cab.dto.VehicleDTO;
import com.cab.exception.UserCustomException;
import com.springsecurity.service.UserService;

@RestController

public class UserRegisterLoginController {

    @Autowired
    private UserService re;
    
    @PostMapping("/register")
    public ResponseEntity<?> userReg(@RequestBody RegisterationDTO u) throws UserCustomException {
        // Log the incoming request data
        System.out.println("Received Registration Request: " + u);
        
        // Call the service layer to handle registration
        ApiResponse response = re.register(u);
        
        // Log the response before sending it back
        System.out.println("Registration Response: " + response);

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO u) {
    	
    	String token = re.verify(u);
    	if (token != null) {
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid credentials"));
        }
        
    }
    
    @GetMapping("/userdetails")
    public ResponseEntity<?> getUserDetails(){
    	return ResponseEntity.ok(re.getuserDetails());
    }
    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody  VehicleDTO vehicle){
    	return ResponseEntity.ok(re.addVehicle(vehicle)) ;
    }

    @GetMapping("/vehicle-list")
    public ResponseEntity<?> vehicleList(){
    	List<Vehicles> listvehicle= re.getVehicleList();
    
    	if(listvehicle!=null) {
    		return ResponseEntity.ok(listvehicle);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No vehicles registered");
    	}
    }
    
    
  
    
}
