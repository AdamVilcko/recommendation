package cz.ucl.recom.wrap;

import twitter4j.User;

/**
 *
 * @author Adam VILCKO
 */
public class UserWrapper {

	/**
	 * Constructor.
	 *
	 * @param blog that is going to be wrapped by this wrapper class.
	 */
	public UserWrapper(User user) {
		this.user = user;
	}

	/** Twitter user that is used for recommendation counting. */
	private final User user;

	/** Distance measure for a recommendation. */
	private Double distance = new Double(0);

	/** Number of favorite statuses returned by Twitter API. */
	private int favoriteStatuses;

	/**
	 * Getter for a user.
	 *
	 * @return Twitter user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Getter for an recommendation distance measure.
	 *
	 * @return Distance measure from fundamental blog.
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * Setter for an recommendation distance measure.
	 *
	 * @param distance
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * @return the favoriteStatuses
	 */
	public int getFavoriteStatuses() {
		return favoriteStatuses;
	}

	/**
	 * @param favoriteStatuses the favoriteStatuses to set
	 */
	public void setFavoriteStatuses(int favoriteStatuses) {
		this.favoriteStatuses = favoriteStatuses;
	}

}
