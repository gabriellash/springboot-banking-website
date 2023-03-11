package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	private CategoryRepository crepo;
	@Autowired
	private UserRepository repo;
	@Autowired
	private OAccountRepository orepo; 
@GetMapping("/signup")
	public String showSignup(Model model) {
	model.addAttribute("user", new User());
	return "Signup";
}
@PostMapping("/process_signup")
public String processSignUp(User user) {
	
	repo.save(user);
	int a = (int) (Math.random()*(999999-100000+1)+100000);
	int b = (int) (Math.random()*(9999-1000+1)+1000);
	OAccount oa = new OAccount();
	oa.setCode(a);
	oa.setPin(b);
	oa.setDollar(0);
	oa.setBitcoin(0);
	oa.setUser(user);
	orepo.save(oa);
	String code = Integer.toString(a);
	String pin = Integer.toString(b);
	String thebody = "Dear "+ user.getFirstname() + " " + user.getLastname() + ",\n Kindly note that the verification code for your Swift account is:  " + code + " and the pin is: "+ pin + ".\n Thank you!";

	senderService.sendEmail(user.getEmail(), "Verification Code and Pin", thebody);
	System.out.println(thebody);
	return "Login";
}
@GetMapping("/login")
	public String showLogIn() {
	return "Login";
}
@GetMapping("/")
public String showLogIns() {
return "Login";
}
@Autowired
private ServiceServ service;

@GetMapping("/admin")
public String returnAdmin() {
return "Adminpage";
}
@GetMapping("/userhome")
public String returnHomepage() {
return "Homepage";
}



@PostMapping("/process_login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session,Model model, HttpServletResponse response) {
		User user = repo.findByEP(email, password);
		if(user == null) {
			return "Login";
		}else if (email.toLowerCase().equals("admin@gmail.com")) {
			return "Adminpage";
		}else {
			List<OAccount> oas = orepo.findAll();
			OAccount oa = new OAccount();
			for(int i=0;i<oas.size();i++) {
				if(oas.get(i).getUser() == user) {
					oa.setDollar(oas.get(i).getDollar());
					oa.setBitcoin(oas.get(i).getBitcoin());
				}
			}
			double bitcoin = oa.getBitcoin();
			model.addAttribute("bitcoin", bitcoin);
			double dollar = oa.getDollar();
			model.addAttribute("dollar", dollar);
			Cookie cookie = new Cookie("email", user.getEmail()); 
			response.addCookie(cookie);
			List<Category> category = crepo.findAll();
			session.setAttribute("email", user.getEmail());
			List<Service> list = service.getAllServices();
			model.addAttribute("firstname",user.getFirstname());
			  model.addAttribute("list", list);
			  model.addAttribute("category",category);
			return "Homepage";
		}
}

@GetMapping("/logout")
public String logout(){
    return "Login";
}


}
