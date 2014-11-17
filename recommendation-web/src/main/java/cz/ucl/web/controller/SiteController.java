package cz.ucl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class SiteController {

	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", "Adam");
		return "index";
	}

}
