package cz.ucl.recom.services.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import cz.ucl.recom.services.RecommendationComponent;
import cz.ucl.recom.wrap.UserWrapper;

/**
 *
 * @author Adam VILCKO
 */
@Component
public class RecommendationComponentImpl implements RecommendationComponent {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationComponentImpl.class);


	@Autowired
	private Twitter twitter;

	/**
	 * {@inherit-doc}
	 */
	@Override
	public Set<UserWrapper> getRecommendedUsers() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("getRecommendedBlogs() - start");
		}

		Set<UserWrapper> notfollowed = new LinkedHashSet<UserWrapper>();


		for (UserWrapper wrap : notfollowed) {
			double intersection = intersection();
			double union = union();

			if (union == 0) {
				continue;
			}

			wrap.setDistance(intersection/union);
		}

		return notfollowed;
	}

	/**
	 * Intersection of two sets of liked posts by different users.
	 *
	 * @param userLikes Reference user likes.
	 * @param likes Supplementary likes of different user.
	 * @return Number of intersect liked posts.
	 */
	private double intersection(/*List<Post> userLikes, List<Post> likes*/) {
		double result = 0;

//		for (Post p1 : userLikes) {
//			for (Post p2 : likes) {
//				if (p1.getId().equals(p2.getId())) {
//					result++;
//				}
//			}
//		}

		return result;
	}

	/**
	 * Union of two sets of liked posts by different users.
	 *
	 * @param userLikes Reference user likes.
	 * @param likes Supplementary likes of different user.
	 * @return Number of union liked posts.
	 */
	private double union(/*List<Post> userLikes, List<Post> likes*/) {
		double result = 0;

//		userLoop : for (Post p1 : userLikes) {
//			for (Post p2 : likes) {
//				if (p1.getId().equals(p2.getId())) {
//					result++;
//					continue userLoop;
//				}
//
//				result++;
//			}
//
//			result++;
//		}

		return result;
	}

}
