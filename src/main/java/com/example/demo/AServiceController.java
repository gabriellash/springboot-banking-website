package com.example.demo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AServiceController {

    @Autowired
    private ServiceRepository repository;

    EntityModel<Service> services;
    List<EntityModel<Service>> list;
    CollectionModel<EntityModel<Service>> collection;

    @GetMapping("/aservice/{id}")
    @ResponseBody
    public EntityModel<Service> one(@PathVariable int id) {
        Long lid = (long) id;
        Service service = repository.findById(lid).orElseThrow();
        services = EntityModel.of(service, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).one(id)).withSelfRel(), WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).all()).withRel("aservices"));
        return services;
    }

    @GetMapping("/aservice")
    @ResponseBody
    public  CollectionModel<EntityModel<Service>> all() {
        list = repository.findAll().stream().map(service -> EntityModel.of(service, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).one(service.getId())).withSelfRel(), WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).all()).withRel("aservices"))).collect(Collectors.toList());
        collection = CollectionModel.of(list, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).all()).withSelfRel());
       
        return collection;
    }
    @GetMapping("/aservices")
    public String AllthefoundServices() {
    	return "aservice";
    }
    @PostMapping("/process_chooseservice")
    public String chooseservice(@CookieValue(value="servicename") String servicename, HttpServletResponse response) {
    	Service service = repository.findByName(servicename);
    	
    	Cookie cookie = new Cookie("serviceid", String.valueOf(service.getId())); 
		response.addCookie(cookie);
    	
    	return "aservice2";
    }
    @PutMapping("/aservice/{id}")
    ModelAndView updateService( @RequestParam("servicename") String sname,  @RequestParam("serviceprice") String sprice, @PathVariable int id){
    	Long lid =(long) id;
    	Service updateservice = repository.findById(lid).map(service -> {
    		service.setName(sname);
    		service.setPrice(sprice);
    		return repository.save(service);
    	}).orElseGet(() -> {
    		return null;
    	});
    	EntityModel<Service> entityModel = EntityModel.of(updateservice, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).one(updateservice.getId())).withSelfRel(), WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AServiceController.class).all()).withRel("aservices"));
        ModelAndView modelandview = new ModelAndView("adminpage");
        modelandview.addObject("adminpage", entityModel);
        return modelandview;
    }
    
    @PostMapping("/process_updateservice")
    public String updatetheservice() {
    	return "Adminpage";
    }
    
    @DeleteMapping("/aservice/{id}")
    public ModelAndView deleteExample(@PathVariable Long id) {
       repository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Adminpage.html");
        modelAndView.addObject("message", "Example with ID " + id + " deleted successfully.");
        return modelAndView;
    }
    
}
