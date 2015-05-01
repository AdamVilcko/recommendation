package cz.ucl.recom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Adam VILCKO
 */
@Configuration
public class TwitterConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(TwitterConfiguration.class);

	@Bean
	public Twitter twitter() {
		if (LOG.isTraceEnabled()) {
			LOG.trace("twitter() - start");
		}

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("generate_your_own");
		cb.setOAuthConsumerSecret("generate_your_own");
		cb.setOAuthAccessToken("generate_your_own");
		cb.setOAuthAccessTokenSecret("generate_your_own");
		TwitterFactory tweet = new TwitterFactory(cb.build());
		Twitter twitter = tweet.getInstance();
		return twitter;
	}

}
