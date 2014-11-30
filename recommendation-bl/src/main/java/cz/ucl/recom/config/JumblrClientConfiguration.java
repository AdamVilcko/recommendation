package cz.ucl.recom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tumblr.jumblr.JumblrClient;

/**
 * @author Adam VILCKO
 *
 */
@Configuration
public class JumblrClientConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(JumblrClientConfiguration.class);

	@Autowired
	private TumblrConsumerConfiguration configuration;

	@Bean
	public JumblrClient jumblrClient() {
		JumblrClient client = new JumblrClient(configuration.getConsumerKey(), configuration.getConsumerSecret());
		client.setToken(configuration.getToken(), configuration.getTokenSecret());

		LOG.info(client.user().getName());

		return client;
	}

}
