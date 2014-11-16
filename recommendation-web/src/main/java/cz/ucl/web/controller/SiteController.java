package cz.ucl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

	@RequestMapping("/recom")
	public String getContent(Model model) {
		model.addAttribute("name", "Adam");
		return "index";
	}

}
