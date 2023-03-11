package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
	private PaymentRepository repository ;
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private BitcoinTransferRepository btrepo;
	@Autowired
	private BankRepository brepository ;
	@Autowired
	private OAccountRepository orepo;
	@Autowired
	private CategoryRepository crepo;
	public List<Bank> getAllBanks() {
		   List<Bank> bank  = new ArrayList<>();
		   brepository.findAll().forEach(bank::add);
		   return bank;
	   }
	
	
	int myAmount;
	
	@RequestMapping("/topup")
	 public String getAllBanks(Model model) 
	 {	
		List<Bank> bank = getAllBanks();
		model.addAttribute("payment", new Payment());
		model.addAttribute("bank", bank);
		return "topup";		
	 }	
	@Autowired
	private ServiceServ service;
	@PostMapping("/process_topup")
	public String processTopup(Payment payment,HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		User user = repo.findByEmail(email);
		
		payment.setUser(user);
		repository.save(payment);
		double addeddollar = payment.getAmount();
		OAccount om = new OAccount();
		List<OAccount> oas = orepo.findAll();
		for(int i=0;i<oas.size();i++) {
			if(oas.get(i).getUser() == user) {
				 om = oas.get(i);
				
			}
		}
		addeddollar += om.getDollar();
		om.setDollar(addeddollar);
		orepo.save(om);
		
		OAccount oa = new OAccount();
		for(int i=0;i<oas.size();i++) {
			if(oas.get(i).getUser() == user) {
				oa.setDollar(oas.get(i).getDollar());
				oa.setBitcoin(oas.get(i).getBitcoin());
			}
		}
		model.addAttribute("bitcoin", oa.getBitcoin());
		double dollar = oa.getDollar();
		List<Category> category = crepo.findAll();
		model.addAttribute("dollar", dollar);
		List<Service> list = service.getAllServices();
		model.addAttribute("firstname",user.getFirstname());
		  model.addAttribute("list", list);
		  model.addAttribute("category",category);
		return "Homepage";
	}
	@PostMapping("/process_bitcoinpurchase")
	public String ProcessBitcoinPurchase(Model model, @RequestParam("vcode") String vcode, @RequestParam("vpin") String vpin, @RequestParam("selleremail") String selleremail,@RequestParam("btcamount") String btcamount,@RequestParam("usdamount") String usdamount, @RequestParam("exchange") String exchange,@CookieValue(value="email") String email) {
		OAccount selleroaccount = orepo.findByEmail(selleremail);
		OAccount buyeroaccount = orepo.findBycp(vcode, vpin);
		User user = repo.findByEmail(email);
		double doublebtcamount = Double.parseDouble(btcamount);
		double doubleusdamount = Double.parseDouble(usdamount);
		double exchanges = Double.parseDouble(exchange);
		BitcoinTransfer btold = btrepo.findbyValue(selleremail, doublebtcamount);
		if(buyeroaccount == null) {
			return "BitcoinPurchase";
		}
		else {
			if( buyeroaccount.getDollar() < doubleusdamount) {
				return "BitcoinPurchase";
			}
			else {
				double sellerdollars = selleroaccount.getDollar();
				sellerdollars += doubleusdamount;
				selleroaccount.setDollar(sellerdollars);
				double sellerbitcoins = selleroaccount.getBitcoin();
				sellerbitcoins -= doublebtcamount;
				selleroaccount.setBitcoin(sellerbitcoins);
				orepo.save(selleroaccount);
				double buyerdollars = buyeroaccount.getDollar();
				buyerdollars -= doubleusdamount;
				buyeroaccount.setDollar(buyerdollars);
				double buyerbitcoins = buyeroaccount.getBitcoin();
				buyerbitcoins += doublebtcamount;
				buyeroaccount.setBitcoin(buyerbitcoins);
				orepo.save(buyeroaccount);
				btold.setBtctousd(exchanges);
				btold.setBuyeremail(email);
				btold.setUsdamount(doubleusdamount);
				String pattern = "MM/dd/yyyy HH:mm:ss";

				// Create an instance of SimpleDateFormat used for formatting 
				// the string representation of date according to the chosen pattern
				DateFormat df = new SimpleDateFormat(pattern);

				// Get the today date using Calendar object.
				Date today = Calendar.getInstance().getTime();        
				// Using DateFormat format method we can create a string 
				// representation of a date with the defined format.
				String todayAsString = df.format(today);
				btold.setDate(todayAsString);
				btrepo.save(btold);
				model.addAttribute("bitcoin", buyeroaccount.getBitcoin());
				List<Category> category = crepo.findAll();
				//session.setAttribute("email", user.getEmail());
				List<Service> list = service.getAllServices();
				model.addAttribute("firstname",user.getFirstname());
				  model.addAttribute("list", list);
				  model.addAttribute("category",category);
				  double dollar = buyeroaccount.getDollar();
					model.addAttribute("dollar", dollar);
				
				return "Homepage";
			}
		}
		
		
	}
	
	@GetMapping("/sellcrypto")
	public String SellingCrypto(Model model) {
		
		
		return "SellBitcoins";
	}
	
	@GetMapping("/buycrypto")
	public String BuyingCrypto(Model model, @CookieValue(value="email") String email ) {
		List<BitcoinTransfer>  btransfer = btrepo.findByEmail(email);
		model.addAttribute("requests", btransfer);
		
	
		return "BuyBitcoins";
	}
	
	@GetMapping("/bitcoinpurchase")
	public String BitcoinPurchase(Model model) {
		return "BitcoinPurchase";
	}
	@PostMapping("/process_sellcrypto")
	public String CryptoSell(Model model, @RequestParam("bitcoinamount") String btcamount,@CookieValue(value="email") String email, @RequestParam("vcode") String code,@RequestParam("vpin") String pin) {
		OAccount oaccount = orepo.findBycp(code, pin);
		System.out.println(oaccount.getBitcoin());
		User user = repo.findByEmail(email);
		if(oaccount == null) {
			return "SellBitcoins";
		}
		else {
		BitcoinTransfer bt = new BitcoinTransfer();
		double btc = Double.parseDouble(btcamount);
		if(oaccount.getBitcoin() < btc) {
			return "SellBitcoins";
		}
		else {
			bt.setBtcamount(btc);
			bt.setSelleremail(email);
			btrepo.save(bt);
			List<Category> category = crepo.findAll();
			//session.setAttribute("email", user.getEmail());
			List<Service> list = service.getAllServices();
			model.addAttribute("firstname",user.getFirstname());
			  model.addAttribute("list", list);
			  model.addAttribute("category",category);
			  double dollar = oaccount.getDollar();
			  model.addAttribute("bitcoin", oaccount.getBitcoin());
				model.addAttribute("dollar", dollar);
			
			return "Homepage";
		}
		
	}
	}

}
