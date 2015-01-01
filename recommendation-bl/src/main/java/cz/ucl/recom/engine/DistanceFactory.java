package cz.ucl.recom.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.ucl.recom.engine.impl.CosineDistance;
import cz.ucl.recom.engine.impl.EuclideanDistance;
import cz.ucl.recom.engine.impl.JacardDistance;

/**
 *
 * @author Adam VILCKO
 */
public class DistanceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(DistanceFactory.class);

	/**
	 * Factory method that initialize wanted distance counter.
	 *
	 * @param distance Enumeration of possible distance measure counters.
	 * @return IDistance instance of wanted measure counter.
	 */
	public static IDistance getDistanceCounter(Distance distance) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("getDistanceCounter(%s) - start", distance));
		}

		switch (distance) {
			case JACARD_DISTANCE:
				return new JacardDistance();
			case COSINE_DISTANCE:
				return new CosineDistance();
			case EUCLIDEAN_DISTANCE:
				return new EuclideanDistance();
			default:
				return new JacardDistance();
		}
	}

}
