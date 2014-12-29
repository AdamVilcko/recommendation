package cz.ucl.recom.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import cz.ucl.recom.services.RecommendationComponent;
import cz.ucl.recom.wrap.UserWrapper;

/**
 * 
 * @author Adam VILCKO
 */
@Controller
@RequestMapping("/")
public class SiteController {

	private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private Twitter twitter;

	@Autowired
	private RecommendationComponent recommendation;

	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Getting web site index page. - START");
		}

		try {
			model.addAttribute("id", twitter.getId());
		} catch (IllegalStateException e) {
			final String err = "";
			LOG.error(err, e);
		} catch (TwitterException e) {
			final String err = "";
			LOG.error(err, e);
		}

		List<UserWrapper> recom = recommendation.getFriendsStatistic();
		model.addAttribute("recom", recom);

		return "index";
	}

}
