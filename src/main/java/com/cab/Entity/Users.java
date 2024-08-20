package com.cab.Entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Users implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@JsonProperty(access = Access.READ_ONLY)
	private Integer userId;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="email",unique = true)
	private String userEmail;
	@Column(name="password")
	private String userPassword;
	@Column(name="mobile_no", length = 10)
	private String userMobileNo;
//	@Column(name="vehicle_regi_no")
//	private String vehicleRegiNo;
	@Column(name="lisence_no")
	private String userLisenceNo;
//	@Column(name="vehicle_name")
//	private String vehicleName;
//	@Column(name="vehicle_capicity")
//	private int vehicleCapicity;
	
	private LocalDate dateOfBirth;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<RidesBooked> bookedRides;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<RidesPublished> publishedRides;
	
	@OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Vehicles> vehicles;  // Added this line
	
//	@Lob //Large Binary Object
//	@Column(name="profile_image")
//	private byte[] profileImage;
	
	
	
	public Users(String fname, String lname, String email, String pass,LocalDate dob, String mobileNo, String userLisenceNo)
	{
		this.firstName=fname;
		this.lastName=lname;
		this.userEmail=email;
		this.userPassword=pass;
		this.userMobileNo=mobileNo;
//		this.profileImage=pimage;
//		this.vehicleRegiNo=null;
		this.userLisenceNo=null;
//		this.vehicleName=null;
//		this.vehicleCapicity=0;
		this.dateOfBirth=dob;
		this.userLisenceNo=userLisenceNo;
	}

	  @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        // If you have roles, return them here. For simplicity, returning null.
	        return null; // Replace with actual authorities if available
	    }

	    @Override
	    public String getPassword() {
	        return this.userPassword;
	    }

	    @Override
	    public String getUsername() {
	        return this.userEmail;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true; // Implement as needed
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true; // Implement as needed
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true; // Implement as needed
	    }

	    @Override
	    public boolean isEnabled() {
	        return true; // Implement as needed
	    }

	
	
	
}
