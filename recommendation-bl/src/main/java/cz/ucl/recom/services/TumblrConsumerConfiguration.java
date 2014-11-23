/**
 * 
 */
package cz.ucl.recom.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Configuration for Tumblr API cosumer informations.
 * 
 * @author Adam VILCKO
 */
@Configuration
@PropertySources(value={@PropertySource("classpath:config/consumer.properties")})
public class TumblrConsumerConfiguration {

	/** Generated application consumer key. */
	@Value("${consumer.consumerKey}")
	private String consumerKey;

	/** Generated application consumer secret key. */
	@Value("${consumer.consumerSecret}")
	private String consumerSecret;

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

}
