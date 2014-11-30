package cz.ucl.recom.config;

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

	/** Token id. */
	@Value("${consumer.token}")
	private String token;

	/** Token secret. */
	@Value("${consumer.tokenSecret}")
	private String tokenSecret;

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}
	
	/**
	 * Setter for the consumerKey.
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * Setter for the consumerSecret.
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

}
