package com.gdpdemo.GDPSprint1Project.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gdpdemo.GDPSprint1Project.Admin;
import com.gdpdemo.GDPSprint1Project.Repository.AdminRepository;
import com.gdpdemo.GDPSprint1Project.service.AdminService;
@Service
public class AdminServiceImp implements AdminService{

	private AdminRepository adminRepo;
	
	public AdminServiceImp(AdminRepository adminRepo) {
		
		this.adminRepo = adminRepo;
	}

	@Override
	public List<Admin> getAllUsers() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}

}
