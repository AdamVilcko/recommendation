package cz.ucl.recom.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;
import cz.ucl.recom.component.RecommendationComponent;
import cz.ucl.recom.component.TwitterComponent;
import cz.ucl.recom.engine.Distance;
import cz.ucl.recom.engine.DistanceFactory;
import cz.ucl.recom.engine.IDistance;
import cz.ucl.recom.wrap.UserWrapper;

/**
 *
 * @author Adam VILCKO
 */
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class RecommendationComponentImpl implements RecommendationComponent {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationComponentImpl.class);

	@Autowired
	private TwitterComponent twitter;

	ResponseList<Status> referenceFavorites = null;

	List<UserWrapper> friendsStatistic = new ArrayList<UserWrapper>();

	/**
	 * {@inherit-doc}
	 */
	@Override
	public List<UserWrapper> getFriendsStatistic() {
		if (friendsStatistic.isEmpty()) {
			getFriendsStatistic(Distance.JACARD_DISTANCE);
		}

		return friendsStatistic;
	}

	/**
	 * {@inherit-doc}
	 */
	@Override
	public List<UserWrapper> getFriendsStatistic(Distance distance) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("getRecommendedBlogs(%s) - start", distance));
		}

		if (!friendsStatistic.isEmpty()) {
			return friendsStatistic;
		}

		if (referenceFavorites == null) {
			referenceFavorites = twitter.getFavoriteStatuses();
		}

		if (LOG.isInfoEnabled() && referenceFavorites != null) {
			int size = referenceFavorites.size();
			LOG.info(String.format("Size of reference favorite list: ", size));
		}

		Map<User,ResponseList<Status>> friendsFavorites = twitter.getFriendsFavoriteStatuses();
		countDistance(friendsFavorites, friendsStatistic, distance);
		sortResult(friendsStatistic);

		return friendsStatistic;
	}

	/**
	 * 
	 * @param distance
	 * @param userId
	 * @return
	 */
	public List<UserWrapper> getUserFriendsStatistic(Distance distance, Long userId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("getUserFriendsStatistic(%s, %f) - start", distance, userId));
		}

		List<UserWrapper> result = new ArrayList<UserWrapper>();
		Map<User,ResponseList<Status>> friendsFavorites = twitter.getFriendsFavoriteStatuses(userId);
		countDistance(friendsFavorites, result, distance);
		sortResult(result);

		return result;
	}

	/**
	 * 
	 * @param friendsFavorites
	 * @param result
	 * @param distance
	 */
	private void countDistance(Map<User,ResponseList<Status>> friendsFavorites, List<UserWrapper> result, Distance distance) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("countDistance(...) - start");
		}

		IDistance counter = DistanceFactory.getDistanceCounter(distance);

		if (referenceFavorites == null) {
			referenceFavorites = twitter.getFavoriteStatuses();
		}

		for (Entry<User, ResponseList<Status>> entry : friendsFavorites.entrySet()) {
			User u = entry.getKey();
			UserWrapper wrap = new UserWrapper(u);

			double dist = counter.countDistance(referenceFavorites, entry.getValue());

			if (LOG.isInfoEnabled()) {
				final String info = "Distance based on %s measure is between reference user and user %s = %f";
				LOG.info(String.format(info, distance.name(), u.getName(), dist));
			}

			wrap.setDistance(dist);
			wrap.setFavoriteStatuses(entry.getValue().size());
			result.add(wrap);
		}
	}

	/**
	 * Sort result list. First value has highest correlation
	 * with reference user.
	 *
	 * @param l List of UserWrapper objects.
	 */
	private void sortResult(List<UserWrapper> l) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("sortResult(...) - start");
		}

		Collections.sort(l, new Comparator<UserWrapper>() {
			public int compare(UserWrapper o1, UserWrapper o2) {
				return o2.getDistance().compareTo(o1.getDistance());
			}
		});
	}

}
