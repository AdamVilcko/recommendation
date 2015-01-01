package cz.ucl.recom.engine;

import twitter4j.ResponseList;
import twitter4j.Status;

/**
 *
 * @author Adam VILCKO
 */
public interface IDistance {

	/**
	 * Counts distance between two users for theirs liked statuses.
	 *
	 * @param referenceStatuses List of statuses of reference user.
	 * @param friendsStatuses List of statuses of friends or followers of reference user.
	 */
	double countDistance(ResponseList<Status> referenceStatuses, ResponseList<Status> friendsStatuses);

}
