package cz.ucl.recom.component;

import java.util.List;
import java.util.Map;

import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Adam VILCKO
 */
public interface TwitterComponent {

	/**
	 * Gets the User information from Twitter service for given user ID.
	 *
	 * @param userId ID of an user to whom the information will be returned.
	 * @return User information for the given user ID.
	 */
	User getUserDetail(Long userId);

	/**
	 * Gets time-line statuses for user with given ID.
	 *
	 * @param userId ID of an user to whom the statuses will be returned.
	 * @return User time-line statuses.
	 */
	List<Status> getUserTimeline(Long id);

	/**
	 * Gets the response list of user friends from Twitter API for the give user ID.
	 *
	 * @param userId ID of an user for whom the information will be returned.
	 * @return Response list of user friends.
	 */
	PagableResponseList<User> getUserFriends(Long userId);

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

	/**
	 * Gets friends with theirs favorite statuses for the given user ID.
	 *
	 * @param userId ID of an user for whom the information will be returned.
	 * @return Map with a user as a key and his favorite statuses as a value.
	 */
	Map<User, ResponseList<Status>> getFriendsFavoriteStatuses(Long userId);

}
