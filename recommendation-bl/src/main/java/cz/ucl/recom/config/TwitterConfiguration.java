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
		cb.setOAuthConsumerKey("rVj1KCrvD2WnaQvWZLd2NzAVm");
		cb.setOAuthConsumerSecret("no4mnQw5nNFXvkdKle7xgWq4FbSRJy9vIBav3NHmYWoEeAc0v2");
		cb.setOAuthAccessToken("441062401-rzk7qXca0IpfF2s5okTtaJ3xxeoJfwMLfmZVffbp");
		cb.setOAuthAccessTokenSecret("BSmOzjNiCNW52RrNWKpd7VF7OP9uITZOf1bzfuTJ5Z24l");
		TwitterFactory tweet = new TwitterFactory(cb.build());
		Twitter twitter = tweet.getInstance();
		return twitter;
	}

}
