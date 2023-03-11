package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BankController {
	
	@Autowired
	private BankRepository repository ;
	
	public List<Bank> getAllBanks() {
		   List<Bank> bank  = new ArrayList<>();
		   repository.findAll().forEach(bank::add);
		   return bank;
	   }
	
	@RequestMapping("/showBank")
	 public String getAllBanks(Model model) 
	 {	
		List<Bank> bank = getAllBanks();
		model.addAttribute("bank", bank);
		return "Managebank";		
	 }	
	
	BankController(BankRepository repository){
		this.repository= repository;
	}

	@GetMapping("/connectBank")
	public String showBankForm(Model model) {
	model.addAttribute("bank", new Bank());
	return "bankForm";
	}
	
//	@RequestMapping("/showBank")
//    public String getAllBankss(Model model)
//    {
//		model.addAttribute("bank", repository.findAll());
//		return "banks";
//    }
	
	@PostMapping("/process_connectBank")
	public String processSignUp(Bank bank) {
		repository.save(bank);
		return "Adminpage";
	}
	
}
