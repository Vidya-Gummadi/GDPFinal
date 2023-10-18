package com.gdpdemo.GDPSprint1Project.serviceImpl;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gdpdemo.GDPSprint1Project.Home;
import com.gdpdemo.GDPSprint1Project.Repository.HomeRepository;
import com.gdpdemo.GDPSprint1Project.security.SecurityConfig;
import com.gdpdemo.GDPSprint1Project.service.DemoService;
@Service
public class DemoServiceImp implements DemoService{
	
	
	 

@Autowired
HomeRepository homeRepository;


 @Autowired private BCryptPasswordEncoder passwordEncoder;
 
	@Override
	
	public void save(Home user) {
		
		  String hashedPassword = passwordEncoder.encode(user.getPassword());
		  user.setPassword(hashedPassword); user.setRePassword(hashedPassword);
		  Home saved = homeRepository.save(user);
		/*
		 * String originalInput = user.getPassword(); Base64 base64 = new Base64();
		 * String encodedString = new String(base64.encode(originalInput.getBytes()));
		 * user.setPassword(encodedString); user.setRePassword(encodedString); Home
		 saved = homeRepository.save(user);
		 */
		
	}
	@Override
	public boolean retrieve(Home user,String password) {
		 return passwordEncoder.matches(password, user.getPassword());
		 
		 
	}
}