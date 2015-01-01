package cz.ucl.recom.component;

import java.util.Map;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Adam VILCKO
 */
public interface TwitterComponent {

	/**
	 * Gets the User information from Twitter service for given user id.
	 *
	 * @param userId ID of an user for whom the information will be returned.
	 * @return User information for the given user ID.
	 */
	User getUserDetail(Long userId);

	/**
	 * Gets the favorite statuses for the reference users. These statuses
	 * are then reference in comparison with favorite statuses of other users.
	 *
	 * @return
	 */
	ResponseList<Status> getFavoriteStatuses();

	/**
	 * Gets favorite statuses of reference user friends.
	 *
	 * @return Map with reference user friends as a key and ResponseList of theirs favorite statuses.
	 */
	Map<User, ResponseList<Status>> getFriendsFavoriteStatuses();

}
