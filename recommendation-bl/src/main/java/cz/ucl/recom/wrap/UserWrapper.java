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
	private double distance = 0;

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
	public double getDistance() {
		return distance;
	}

	/**
	 * Setter for an recommendation distance measure.
	 *
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
