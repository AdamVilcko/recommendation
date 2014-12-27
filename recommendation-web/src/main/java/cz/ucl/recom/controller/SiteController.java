package cz.ucl.recom.controller;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.api.FavoritesResources;
import twitter4j.api.FriendsFollowersResources;
import cz.ucl.recom.services.RecommendationComponent;

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

		FavoritesResources favResource = twitter.favorites();
		ResponseList<Status> favorites = null;
		try {
			favorites = favResource.getFavorites();
		} catch (TwitterException e) {
			final String err = "";
			LOG.error(err, e);
		}

		FriendsFollowersResources res = twitter.friendsFollowers();
		ResponseList<Status> respList = null;
		try {
			long myId = twitter.getId();
			PagableResponseList<User> users = res.getFollowersList(myId, -1);
			ListIterator<User> it = users.listIterator();

			while (it.hasNext()) {
				User u = it.next();
				twitter.getFollowersIDs(u.getId(), -1);
				respList = twitter.getFavorites(u.getId());
			}
		} catch (TwitterException e) {
			final String err = "";
			LOG.error(err, e);
		}

		model.addAttribute("favorites", favorites);

		return "index";
	}

}
