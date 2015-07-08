package cz.ucl.recom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.Status;
import twitter4j.User;
import cz.ucl.recom.component.RecommendationComponent;
import cz.ucl.recom.component.TwitterComponent;
import cz.ucl.recom.engine.Distance;
import cz.ucl.recom.wrap.UserWrapper;

/**
 *
 * @author Adam VILCKO
 */
@Controller
@RequestMapping("userdetail")
public class UserDetailController {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailController.class);

	@Autowired
	private TwitterComponent twitter;

	@Autowired
	private RecommendationComponent recommendation;

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable int id, Model model) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("userDetail(...) - start");
		}

		Long userId = new Long(id);
		List<UserWrapper> friendsStat = recommendation.getFriendsStatistic();
		for (UserWrapper uw : friendsStat) {
			if (userId.equals(uw.getUser().getId())) {
				model.addAttribute("user", uw.getUser());
				break;
			}
		}

		List<Status> statuses = null;
		if (!model.containsAttribute("statuses")) {
			statuses = twitter.getUserTimeline(userId);
		}

		if (statuses != null) {
			model.addAttribute("statuses", statuses);
		}

		if (!model.containsAttribute("user")) {
			User u = twitter.getUserDetail(userId);
			model.addAttribute("user", u);
		}

		List<UserWrapper> unknownUsers = recommendation.getUserFriendsStatistic(Distance.JACARD_DISTANCE, userId);
		model.addAttribute("recom", unknownUsers);

		return "userdetail";
	}

}
