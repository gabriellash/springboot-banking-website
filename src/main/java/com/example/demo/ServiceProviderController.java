package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServiceProviderController {
	@Autowired
	private ServiceProviderRepository repo;

@GetMapping("/addserviceproviderform")
public String addServiceProivder(Model model) {
	model.addAttribute("serviceprovider", new ServiceProvider());
	return "AddServiceProvider";
}

@PostMapping("/process_addserviceprovider")
public String processSignUp(ServiceProvider sp) {
	repo.save(sp);
	return "Adminpage";

}
}
