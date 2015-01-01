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
public class RecommendationComponentImpl implements RecommendationComponent {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationComponentImpl.class);

	@Autowired
	private TwitterComponent twitter;

	/**
	 * {@inherit-doc}
	 */
	@Override
	public List<UserWrapper> getFriendsStatistic(Distance distance) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getRecommendedBlogs() - start");
		}

		List<UserWrapper> result = new ArrayList<UserWrapper>();
		IDistance counter = DistanceFactory.getDistanceCounter(distance);

		ResponseList<Status> myFavorites = twitter.getFavoriteStatuses();
		Map<User,ResponseList<Status>> friendsFavorites = twitter.getFriendsFavoriteStatuses();

		for (Entry<User, ResponseList<Status>> entry : friendsFavorites.entrySet()) {
			User u = entry.getKey();
			UserWrapper wrap = new UserWrapper(u);

			double dist = counter.countDistance(myFavorites, entry.getValue());

			if (LOG.isInfoEnabled()) {
				final String info = "Distance based on %s measure is between reference user and user %s = %f";
				LOG.info(String.format(info, distance.name(), u.getName(), dist));
			}

			wrap.setDistance(dist);
			result.add(wrap);
		}

		Collections.sort(result, new Comparator<UserWrapper>() {
			public int compare(UserWrapper o1, UserWrapper o2) {
				return o2.getDistance().compareTo(o1.getDistance());
			}
		});

		return result;
	}

}
