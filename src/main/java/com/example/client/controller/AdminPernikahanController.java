package com.example.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.client.model.Pernikahan;
import com.example.client.service.AdminPernikahanService;

@Controller
public class AdminPernikahanController {

	@Autowired
	private AdminPernikahanService service; 
	int currentID = 0;
	
	
	@RequestMapping(value = "/adminsave_pernikahan", method = RequestMethod.POST)
	public String savePernikahan(RedirectAttributes redirectAttributes, 
	@RequestParam("pernikahan_status") String pernikahan_status, 
	@RequestParam("pernikahan_waktu") String pernikahan_waktu,
    ModelMap modelMap) {

		Pernikahan pernikahan = new Pernikahan();

		pernikahan=service.get(currentID);

		pernikahan.setPernikahan_status(pernikahan_status);
		pernikahan.setPernikahan_waktu(pernikahan_waktu);
		
		service.save(pernikahan,currentID);
		System.out.println(currentID);

		return "redirect:/adminpernikahan.html";
	}

	@RequestMapping("/adminpernikahan.html")
	public String viewDaftarPernikahan(Model model) {
		List listpernikahan = service.listAll();
		model.addAttribute("listpernikahan", listpernikahan);
		currentID=0;
		return "adminpernikahan";
	}

    
	@RequestMapping("/adminedit_pernikahan.html/{pernikahan_id}")
	public ModelAndView showEditPernikahanPage(@PathVariable(name = "pernikahan_id") int pernikahan_id) {
		ModelAndView mav = new ModelAndView("adminedit_pernikahan.html");
		Pernikahan pernikahan = service.get(pernikahan_id);
		currentID = pernikahan_id;
		mav.addObject("pernikahan", pernikahan);
		return mav;
    }
}