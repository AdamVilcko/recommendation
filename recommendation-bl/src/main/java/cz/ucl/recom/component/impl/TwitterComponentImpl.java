package cz.ucl.recom.component.impl;

import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

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
import cz.ucl.recom.component.TwitterComponent;

/**
 *
 * @author Adam VILCKO
 */
@Component
public class TwitterComponentImpl implements TwitterComponent {

	private static final Logger LOG = LoggerFactory.getLogger(TwitterComponentImpl.class);

	/**
	 * Request rate limits for application authentication.
	 * Request limits  <a href="https://dev.twitter.com/rest/public/rate-limits">here</a>.
	 */
	private static final int REQUEST_RATE_LIMIT_ERROR_CODE = 88;

	/** Public Twitter API interface. */
	@Autowired
	private Twitter twitter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserDetail(Long userId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("getUserDetail(%f) - start", userId));
		}

		User result = null;
		try {
			result = twitter.showUser(userId);
		} catch (TwitterException e) {
			final String err = "User detail could not be loaded.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
				return result;
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PagableResponseList<User> getUserFriends(Long userId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("getUserFriends(%f) - start", userId));
		}

		PagableResponseList<User> result = null;
		try {
			result = twitter.getFriendsList(userId, -1);
		} catch (TwitterException e) {
			final String err = "Friends list could not be loaded.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
				return result;
			}
		}

		return result;
	}

	/**
	 * {@inherit-doc}
	 */
	@Override
	public ResponseList<Status> getFavoriteStatuses() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getFavoriteStatuses() - start");
		}

		FavoritesResources favResource = twitter.favorites();
		ResponseList<Status> favorites = null;

		try {
			favorites = favResource.getFavorites();
		} catch (TwitterException e) {
			final String err = "Favorite statuses could not be loaded.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
				return favorites;
			}
		}

		return favorites;
	}

	/**
	 * {@inherit-doc}
	 */
	@Override
	public Map<User, ResponseList<Status>> getFriendsFavoriteStatuses() {
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

			if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
				return result;
			}
		}

		result.putAll(getFriendsFavoriteStatuses(myId));

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<User, ResponseList<Status>> getFriendsFavoriteStatuses(Long userId) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getFriendsFavoriteStatuses() - start");
		}

		Map<User, ResponseList<Status>> result = new LinkedHashMap<User, ResponseList<Status>>();

		FriendsFollowersResources res = twitter.friendsFollowers();
		PagableResponseList<User> users = null;
		try {
			users = res.getFriendsList(userId, -1);
		} catch (TwitterException e) {
			final String err = "Friends list could not be loaded.";
			LOG.error(err, e);

			if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
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
				final String err = "Friends favorite statuses could not be loaded.";
				LOG.error(err, e);

				if (e.getErrorCode() == REQUEST_RATE_LIMIT_ERROR_CODE) {
					return result;
				}
			}

			result.put(u, favorites);
		}

		return result;
	}

}
