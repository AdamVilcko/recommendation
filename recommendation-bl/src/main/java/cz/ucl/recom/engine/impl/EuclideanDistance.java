package cz.ucl.recom.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.ResponseList;
import twitter4j.Status;
import cz.ucl.recom.engine.IDistance;

/**
 *
 * @author Adam VILCKO
 */
public class EuclideanDistance implements IDistance {

	private static final Logger LOG = LoggerFactory.getLogger(EuclideanDistance.class);

	/**
	 * {@inherit-doc}
	 */
	@Override
	public double countDistance(ResponseList<Status> referenceStatuses, ResponseList<Status> friendsStatuses) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("countDistance(...) - start");
		}

		double result = 0;

		for (Status s1 : referenceStatuses) {
			for (Status s2 : friendsStatuses) {
				Long id1 = s1.getId();
				Long id2 = s2.getId();

				if (id1.equals(id2)) {
					break;
				}
			}
		}

		return result;
	}

}
