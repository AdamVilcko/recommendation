package cz.ucl.recom.component;

import java.util.List;

import cz.ucl.recom.engine.Distance;
import cz.ucl.recom.wrap.UserWrapper;

/**
 * Interface with business methods for getting
 * recommendation based on given parameters.
 *
 * @author Adam VILCKO
 */
public interface RecommendationComponent {

	/**
	 * Gets stored friends statistic if any. Otherwise
	 * it call Twitter web services and count statistic
	 * for newly loaded friends data. Its because an
	 * restriction for API calling between requests.
	 * Request limits  <a href="https://dev.twitter.com/rest/public/rate-limits">here</a>.
	 *
	 * @return List of recommended users.
	 */
	List<UserWrapper> getFriendsStatistic();

	/**
	 * Gets the recommended users for the given user.
	 *
	 * @return List of recommended users.
	 */
	List<UserWrapper> getFriendsStatistic(Distance distance);

	List<UserWrapper> getUserFriendsStatistic(Distance distance, Long userId);

}
