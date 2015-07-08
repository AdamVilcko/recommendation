package cz.ucl.recom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import cz.ucl.recom.component.RecommendationComponent;
import cz.ucl.recom.engine.Distance;
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

		List<Status> statuses = null;
		if (!model.containsAttribute("statuses")) {
			try {
				statuses = twitter.getUserTimeline();
			} catch (TwitterException e) {
				final String err = "User statuses could not be loaded.";
				LOG.error(err, e);
			}
		}

		if (statuses != null) {
			model.addAttribute("statuses", statuses);
		}

		if (!model.containsAttribute("recom")) {
			List<UserWrapper> recom = recommendation.getFriendsStatistic(Distance.JACARD_DISTANCE);
			model.addAttribute("recom", recom);
		}

		return "index";
	}

}
