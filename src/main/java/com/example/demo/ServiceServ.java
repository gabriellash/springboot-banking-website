package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.ServiceRepository;

@Service
public class ServiceServ {
	
	@Autowired
	private ServiceRepository rep;

	//public List<com.example.demo.Service> findAllServices(String keyword);
	
	public List<com.example.demo.Service> getAllServices(){
		  List<com.example.demo.Service> list =  (List<com.example.demo.Service>)rep.findAll();
		  return list;
		 }
		 
		 /*
		  * TODO: Get Shop By keyword
		  */
		 public List<com.example.demo.Service> getByKeyword(String keyword){
		  return rep.findByKeyword(keyword);
		 }
		 
	
}
