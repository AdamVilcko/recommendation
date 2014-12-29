package cz.ucl.recom.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.api.FavoritesResources;
import twitter4j.api.FriendsFollowersResources;
import cz.ucl.recom.services.RecommendationComponent;
import cz.ucl.recom.wrap.UserWrapper;

/**
 *
 * @author Adam VILCKO
 */
@Component
public class RecommendationComponentImpl implements RecommendationComponent {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationComponentImpl.class);

	/**
	 * Request rate limits for application authentication.
	 * {@link https://dev.twitter.com/rest/public/rate-limits}
	 */
	private static final int REQUEST_RATE_LIMIT = 88;


	@Autowired
	private Twitter twitter;

	/**
	 * {@inherit-doc}
	 */
	@Override
	public List<UserWrapper> getFriendsStatistic() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getRecommendedBlogs() - start");
		}

		List<UserWrapper> result = new ArrayList<UserWrapper>();

		ResponseList<Status> myFavorites = getReferenceFavoriteStatuses();

		Map<User,ResponseList<Status>> friendsFavorites = getFriendsFavoriteStatuses();
//		Set<User> friends = friendsFavorites.keySet();
//		Map<User,ResponseList<Status>> friendsFriendsFavorites = getFriendsFriendsFavoriteStatuses(friends);

		for (Entry<User, ResponseList<Status>> entry : friendsFavorites.entrySet()) {
			UserWrapper wrap = new UserWrapper(entry.getKey());

			double intersection = intersection(myFavorites, entry.getValue());
			double union = union(myFavorites, entry.getValue());

			if (union == 0) {
				continue;
			}

			BigDecimal bd = new BigDecimal(Double.toString(intersection/union));
			bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
			wrap.setDistance(bd.doubleValue());
			result.add(wrap);
		}

		Collections.sort(result, new Comparator<UserWrapper>() {
			public int compare(UserWrapper o1, UserWrapper o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		});

		return result;
	}

	/**
	 * 
	 */
	private ResponseList<Status> getReferenceFavoriteStatuses() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getReferenceFavoriteStatuses() - start");
		}

		FavoritesResources favResource = twitter.favorites();
		ResponseList<Status> favorites = null;

		try {
			favorites = favResource.getFavorites();
		} catch (TwitterException e) {
			final String err = "Twitter service or network is unavailable.";
			LOG.error(err, e);
		}

		return favorites;
	}

	/**
	 * 
	 * @return
	 */
	private Map<User, ResponseList<Status>> getFriendsFavoriteStatuses() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getFriendsFavoriteStatuses() - start");
		}

		Map<User, ResponseList<Status>> result = new LinkedHashMap<User, ResponseList<Status>>();

		Long myId = null;
		try {
			myId = twitter.getId();
		} catch (IllegalStateException e) {
			final String err = "ID could not be loaded because no credentials are supplied.";
			LOG.error(err, e);
		} catch (TwitterException e) {
			final String err = "ID could not be loaded because Twitter service is no available.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT) {
				return result;
			}
		}

		FriendsFollowersResources res = twitter.friendsFollowers();
		PagableResponseList<User> users = null;
		try {
			users = res.getFriendsList(myId, -1);
		} catch (TwitterException e) {
			final String err = "Followe list could not be loaded.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT) {
				return result;
			}
		}

		ListIterator<User> it = users.listIterator();
		while (it.hasNext()) {
			User u = it.next();
			ResponseList<Status> favorites = null;

			try {
				favorites = twitter.getFavorites(u.getId());
			} catch (TwitterException e) {
				final String err = "Followes favorite statuses could not be loaded.";
				LOG.error(err, e);

				if (e.getErrorCode() == REQUEST_RATE_LIMIT) {
					return result;
				}
			}

			result.put(u, favorites);
		}

		return result;
	}

	/**
	 * 
	 * @param friends
	 * @return
	 */
	private Map<User, ResponseList<Status>> getFriendsFriendsFavoriteStatuses(Set<User> friends) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getFriendsFollowersFavoriteStatuses() - start");
		}

		Map<User, ResponseList<Status>> result = new LinkedHashMap<User, ResponseList<Status>>();

		Iterator<User> it = friends.iterator();
		while (it.hasNext()) {
			User friend = it.next();

			PagableResponseList<User> fav = null;
			try {
				 fav = twitter.getFriendsList(friend.getId(), -1);
			} catch (TwitterException e) {
				final String err = "Friends list could not be loaded.";
				LOG.error(err, e);

				if (e.getErrorCode() == REQUEST_RATE_LIMIT) {
					return result;
				}
			}

			if (fav == null) {
				continue;
			}

			ListIterator<User> it2 = fav.listIterator();
			while (it2.hasNext()) {
				User friendFriend = it2.next();
				ResponseList<Status> favorites = null;

				try {
					favorites = twitter.getFavorites(friendFriend.getId());
				} catch (TwitterException e) {
					final String err = "Friends favorite statuses could not be loaded.";
					LOG.error(err, e);

					if (e.getErrorCode() == REQUEST_RATE_LIMIT) {
						return result;
					}
				}

				result.put(friendFriend, favorites);
			}
		}

		return result;
	}

	/**
	 * Intersection of two sets of liked posts by different users.
	 *
	 * @param referenceLikes Reference user likes.
	 * @param friendsLikes Supplementary likes of different user.
	 * @return Number of intersect liked posts.
	 */
	private double intersection(ResponseList<Status> referenceLikes, ResponseList<Status> friendsLikes) {
		double result = 0;

		for (Status p1 : referenceLikes) {
			for (Status p2 : friendsLikes) {
				final Long id1 = p1.getId();
				final Long id2 = p2.getId();

				if (id1.equals(id2)) {
					result++;
				}
			}
		}

		return result;
	}

	/**
	 * Union of two sets of liked posts by different users.
	 *
	 * @param referenceLikes Reference user likes.
	 * @param friendsLikes Supplementary likes of different user.
	 * @return Number of union liked posts.
	 */
	private double union(ResponseList<Status> referenceLikes, ResponseList<Status> friendsLikes) {
		double result = 0;

		userLoop : for (Status p1 : referenceLikes) {
			for (Status p2 : friendsLikes) {
				final Long id1 = p1.getId();
				final Long id2 = p2.getId();

				if (id1.equals(id2)) {
					result++;
					continue userLoop;
				}

				result++;
			}

			result++;
		}

		return result;
	}

}
