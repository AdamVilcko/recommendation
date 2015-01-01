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
	 * Gets the recommended users for the given user.
	 *
	 * @return List of recommended users.
	 */
	List<UserWrapper> getFriendsStatistic(Distance distance);

}
