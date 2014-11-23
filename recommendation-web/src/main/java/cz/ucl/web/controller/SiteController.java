package cz.ucl.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Adam VILCKO
 */
@Controller
@RequestMapping("/")
public class SiteController {
	
	private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);

	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Getting web site index page. - START");
		}
		
		model.addAttribute("name", "Adam");
		return "index";
	}

}
