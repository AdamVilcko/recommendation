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
public class CosineDistance implements IDistance {

	private static final Logger LOG = LoggerFactory.getLogger(CosineDistance.class);

	/**
	 * {@inherit-doc}
	 */
	@Override
	public double countDistance(ResponseList<Status> referenceStatuses, ResponseList<Status> friendsStatuses) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("countDistance(...) - start");
		}

		return 0;
	}

}
