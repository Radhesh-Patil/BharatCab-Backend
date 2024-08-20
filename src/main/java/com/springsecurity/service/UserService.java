package com.springsecurity.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.security.repository.UserRepo;
//import com.springsecurity.model.Users;
//
//@Service
//public class UserService {
//	
//	@Autowired
//	private UserRepo repo;
//	
//	@Autowired
//	AuthenticationManager authManager;
//	
//	@Autowired
//	JWTService jwtServ;
//	
//	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(/*strength=means 2^12*/12);
//	
//	public Users register(Users u) {
//		
//		u.setPassword(encoder.encode(u.getPassword()));
//		
//		return repo.save(u);
//	}
//
//	public String verifyUser(Users user) {
//		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//		
//		if(authentication.isAuthenticated()) {
//			return jwtServ.generateToken(user.getUsername());
//		}
//		
//		
//		return "Fail";
//	}
//}




import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cab.AllRepository.UserRegisterLoginRepo;
import com.cab.AllRepository.VehicleRepository;
import com.cab.Entity.UserPrincipal;
import com.cab.Entity.Users;
import com.cab.Entity.Vehicles;
import com.cab.dto.ApiResponse;
import com.cab.dto.LoginDTO;
import com.cab.dto.RegisterationDTO;
import com.cab.dto.UserDTO;
import com.cab.dto.VehicleDTO;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRegisterLoginRepo repo;
    
    @Autowired
    private VehicleRepository vehicleRepo;

    ModelMapper modelMapper=new ModelMapper();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ApiResponse register(RegisterationDTO u1) {
    	
    	Users o=repo.findByUserEmail(u1.getUserEmail());
    	if(o==null)
    	{
    		Users u= modelMapper.map(u1, Users.class);
            u.setUserPassword(encoder.encode(u1.getUserPassword()));
            repo.save(u);
            return new ApiResponse("User Registered");
    	}
    	else {
    		return new ApiResponse("Email already registered");
    	}
    	
    }

    public String verify(LoginDTO user) {
    	
    	
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserEmail());
        } else {
            return "fail";
        }
    }

	public ApiResponse addVehicle(VehicleDTO vehicle) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		int userid= currentUserPrincipal.getUser().getUserId();
		Optional<Users> users=repo.findById(userid);
		
		Vehicles v=new Vehicles();
		v.setUser(users.get());
		v.setVehicleCapicity(vehicle.getVehicleCapicity());
		v.setVehicleCompany(vehicle.getVehicleCompany());
		v.setVehicleModel(vehicle.getVehicleModel());
		v.setVehicleRegiNo(vehicle.getVehicleRegiNo());
		
		vehicleRepo.save(v);
		return new ApiResponse("Vehicle Added");
	
	}

	public List<Vehicles> getVehicleList() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
        Users u= currentUserPrincipal.getUser();
		
		List<Vehicles> vlist= vehicleRepo.findByUser(u);
		
		
		return vlist;
	}

	public UserDTO getuserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUserPrincipal = (UserPrincipal) authentication.getPrincipal(); 
        Users u= currentUserPrincipal.getUser();
        
        UserDTO user= new UserDTO();
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setUserEmail(u.getUserEmail());
        user.setUserLisenceNo(u.getUserLisenceNo());
		return user;
	}
}










