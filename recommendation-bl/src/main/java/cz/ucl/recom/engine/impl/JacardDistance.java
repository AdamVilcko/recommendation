package cz.ucl.recom.engine.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.ResponseList;
import twitter4j.Status;
import cz.ucl.recom.engine.IDistance;

/**
 *
 * @author Adam VILCKO
 */
public class JacardDistance implements IDistance {

	private static final Logger LOG = LoggerFactory.getLogger(JacardDistance.class);

	/**
	 * {@inherit-doc}
	 */
	@Override
	public double countDistance(ResponseList<Status> referenceStatuses, ResponseList<Status> friendsStatuses) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("countDistance(...) - start");
		}

		double intersection = intersection(referenceStatuses, friendsStatuses);
		double union = union(referenceStatuses, friendsStatuses);

		BigDecimal bd = new BigDecimal(Double.toString(intersection/union));
		bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
		double result = bd.doubleValue();

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Result for Jacard index is %f", result));
		}

		return result;
	}

	/**
	 * Intersection of two sets of liked posts by different users.
	 *
	 * @param referenceLikes Reference user likes.
	 * @param friendsLikes Supplementary likes of different user.
	 * @return Number of intersect liked posts.
	 */
	private double intersection(ResponseList<Status> referenceLikes, ResponseList<Status> friendsLikes) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("intersection(...) - start"); 
		}

		double result = 0;

		for (Status p1 : referenceLikes) {
			for (Status p2 : friendsLikes) {
				final Long id1 = p1.getId();
				final Long id2 = p2.getId();

				if (id1.equals(id2)) {
					result++;
				}
			}
		}

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Intersercion is %f", result));
		}

		return result;
	}

	/**
	 * Union of two sets of liked posts by different users.
	 *
	 * @param referenceLikes Reference user likes.
	 * @param friendsLikes Supplementary likes of different user.
	 * @return Number of union liked posts.
	 */
	private double union(ResponseList<Status> referenceLikes, ResponseList<Status> friendsLikes) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("union(...) - start");
		}

		double result = 0;

		userLoop : for (Status p1 : referenceLikes) {
			for (Status p2 : friendsLikes) {
				final Long id1 = p1.getId();
				final Long id2 = p2.getId();

				if (id1.equals(id2)) {
					result++;
					continue userLoop;
				}

				result++;
			}

			result++;
		}

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Union is %f", result));
		}

		return result;
	}

}
