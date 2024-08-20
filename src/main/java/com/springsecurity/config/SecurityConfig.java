package com.springsecurity.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.springsecurity.filter.JwtFilter;
//
//@Configuration  //inform SC that this is configuration 
//@EnableWebSecurity   //inform SC dont go with the default flow follow this 
//public class SecurityConfig {
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Autowired
//	private JwtFilter jwtFilter;
//	
//	@Bean  //Indicates that a method produces a bean to be managed by the Spring container. 
//		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//			
//		return	http.csrf(customizer ->customizer.disable())  //To disable csrf
//			.authorizeHttpRequests(request -> request
//					.requestMatchers("register","login") //skip this patterns from authentication
//					.permitAll()  //it means /register and /login will be permitted and rest will be authenticated
//					.anyRequest().authenticated())
//			.httpBasic(Customizer.withDefaults())
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//			.build();
//			
//			//.formLogin(Customizer.withDefaults()); //default formlogin
//			//http.httpBasic(Customizer.withDefaults()); //for postman
//			 
//		}
//		
////		@Bean
////		public UserDetailsService userDetailsService() {
////			
////			
////			
////			return new InMemoryUserDetailsManager();
////		}
////		
//		
//		@SuppressWarnings("deprecation")
//		@Bean
//		public AuthenticationProvider authenciationProvider() {
//			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////			provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//			provider.setPasswordEncoder(new BCryptPasswordEncoder(12/*strength*/)); //to allow user to login with normal pass
//			provider.setUserDetailsService(userDetailsService);
//			return provider;
//			
//		}
//		
//		@Bean
//		public AuthenticationManager authentiactionManager(AuthenticationConfiguration config) throws Exception {
//			return config.getAuthenticationManager();
//		}
//		
//		
//}



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                        .requestMatchers("login", "register").permitAll()
                        .anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("kiran")
//                .password("k@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("harsh")
//                .password("h@123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);


        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }


}
