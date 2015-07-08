package cz.ucl.recom.controller;

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
@RequestMapping("about")
public class AboutController {

	private static final Logger LOG = LoggerFactory.getLogger(AboutController.class);

	@RequestMapping(method=RequestMethod.GET)
	public String about(Model model) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("about() - start");
		}

		return "about";
	}

}
