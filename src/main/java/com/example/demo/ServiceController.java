package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ServiceController {
	@Autowired
	private ServiceRepository repository;
	@Autowired
	private UserRepository urepo;
	@Autowired
	private CategoryRepository crepo;
	@Autowired
	private ServiceProviderRepository repo;
	@Autowired
	private OAccountRepository orepo;
	@Autowired
	private BuyServiceRepository bsrepo;
	public List<ServiceProvider> getAllServiceProvider() {
		   List<ServiceProvider> serviceprovider  = new ArrayList<>();
		   repo.findAll().forEach(serviceprovider::add);
		   return serviceprovider;
	   }
	
	public List<Category> getAllCategory(){
		List<Category> c = new ArrayList<>();
		crepo.findAll().forEach(c::add);
		return c;
	}
	@GetMapping("/addtheservice")
	public String addService(Model model) {
			//List<ServiceProvider> sp = getAllServiceProvider();
			List<ServiceProvider> serviceprovider = repo.findAll();
			List<Category> category = crepo.findAll();
			model.addAttribute("service", new Service());
			model.addAttribute("category", category);
			model.addAttribute("serviceprovider", serviceprovider);
			return "AddService";
			
		}

	@PostMapping("/process_addservice")
	public String ServiceAdded(Service service) {
		repository.save(service);
		return "Adminpage";
	}
	//@RequestMapping("/addtheservice")
	//public String addData(Model model) {
	//	List<ServiceProvider> sp = getAllServiceProvider();
	//	
	//			List<Category> c = getAllCategory();
	//			
	//			model.addAttribute("category", c);
	//			model.addAttribute("serviceprovider", sp);
	//			return "AddService";
	//}
	
	@Autowired
	private ServiceServ service;
	
	
	@GetMapping("/buyservice")
	public String buyservice(Model model, @CookieValue(value="thename") String servicename) {
		Service service = repository.findByName(servicename);
		model.addAttribute("service", service);
		return "BuyService";
	}
	
	
	
	@GetMapping("/services")
	public String home(Service shop, Model model, String keyword, HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		User user = urepo.findByEmail(email);
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
		  if(keyword!=null) {
			  List<Category> category = crepo.findAll();
		   List<Service> list = service.getByKeyword(keyword);
		   model.addAttribute("list", list);
		   model.addAttribute("category",category);
		  }else { 
		List<Service> services = repository.findAll();
		List<EntityModel<Service>> entityModels = services.stream().map(service -> EntityModel.of(service,WebMvcLinkBuilder.linkTo(ServiceController.class).slash("/services").slash(service.getId()).withSelfRel(),WebMvcLinkBuilder.linkTo(ServiceController.class).slash("/services").withRel("services"))).collect(Collectors.toList());
	//	CollectionModel<EntityModel<Service>> collectionModel = CollectionModel.of(entityModels,WebMvcLinkBuilder.linkTo(ServiceController.class).slash("/services").withRel("services"));
		CollectionModel<EntityModel<Service>> collectionModel = CollectionModel.of(entityModels,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServiceController.class).home(shop, model, keyword, session)).withSelfRel());
		
		List<Category> category = crepo.findAll();
		  List<Service> list = service.getAllServices();
		  model.addAttribute("collection", collectionModel);
		  model.addAttribute("list", list);
		  model.addAttribute("firstname",user.getFirstname());
		  model.addAttribute("category",category);
		  }
		  return "Homepage";
		 
	
	}
	/*public String getServices(Model model,@Param("keyword") String keyword) {
		List<Service> service = srepository.findAllServices(keyword);
		//model.addAttribute("service", service);
		model.addAttribute("keyword", keyword);
		return "Homepage";
	}*/
	@PostMapping("process_buyservice")
	public String processbuy(Model model,HttpServletResponse response,@RequestParam("vcode") String code, @RequestParam("vpin") String pin, @RequestParam("pricecost") String price,@CookieValue(value="thename") String servicename,@CookieValue(value="email") String email) {
		String p = price.replace("$", "");
		User user = urepo.findByEmail(email);
		double dollars = Double.parseDouble(p);
		OAccount oaccount = orepo.findBycp(code, pin);
		if(oaccount == null) {
			return "BuyService";
		}
		else if(oaccount.getDollar() < dollars) {
			Cookie cookie = new Cookie("priceerror", "Not%20enough%20credits"); 
			response.addCookie(cookie);
			Service service = repository.findByName(servicename);
			model.addAttribute("service", service);
			return "BuyService";
		}
		else {
			Cookie cookie = new Cookie("pricerror", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			BuyService bs = new BuyService();
			List<User> users = urepo.findAll();
			for(int i=0;i<users.size();i++) {
				if(users.get(i) == oaccount.getUser()) {
					bs.setUser(users.get(i));
				}
			}
			bs.setCost(price);
			bs.setServicename(servicename);
			String pattern = "MM/dd/yyyy HH:mm:ss";

			// Create an instance of SimpleDateFormat used for formatting 
			// the string representation of date according to the chosen pattern
			DateFormat df = new SimpleDateFormat(pattern);

			// Get the today date using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String todayAsString = df.format(today);
			bs.setDate(todayAsString);
			bsrepo.save(bs);
			oaccount.setDollar(oaccount.getDollar()-dollars);
			model.addAttribute("bitcoin", oaccount.getBitcoin());
			orepo.save(oaccount);
			model.addAttribute("dollar", oaccount.getDollar());
			List<Category> category = crepo.findAll();
			//session.setAttribute("email", user.getEmail());
			List<Service> list = service.getAllServices();
			model.addAttribute("firstname",user.getFirstname());
			  model.addAttribute("list", list);
			  model.addAttribute("category",category);
			return "Homepage";
		}
		
	}

}
