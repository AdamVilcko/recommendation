package cz.ucl.recom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import twitter4j.User;
import cz.ucl.recom.component.TwitterComponent;

/**
 *
 * @author Adam VILCKO
 */
@Controller
@RequestMapping("userdetail")
@SessionAttributes(types=User.class)
public class UserDetailController {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailController.class);

	@Autowired
	private TwitterComponent twitter;

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable int id, Model model) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("userDetail(...) - start");
		}

		if (model.containsAttribute("recom")) {
			System.out.println("YES!");
		}

		Long userId = new Long(id);
		User u = twitter.getUserDetail(userId);
		model.addAttribute("user", u);

		return "userdetail";
	}

}
