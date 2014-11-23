package cz.ucl.recom.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.tumblr.connect.TumblrConnectionFactory;
import org.springframework.stereotype.Component;

import cz.ucl.recom.services.TumblrConnectionService;
import cz.ucl.recom.services.TumblrConsumerConfiguration;

/**
 * 
 * @author Adam VILCKO
 */
@Component
public class TumblrConnectionServicImpl implements TumblrConnectionService {

	private static final Logger LOG = LoggerFactory.getLogger(TumblrConnectionServicImpl.class);

	@Autowired
	private TumblrConsumerConfiguration configuration;

	private void testConnection() {
		TumblrConnectionFactory factory = new TumblrConnectionFactory(configuration.getConsumerKey(), configuration.getConsumerSecret());
//		factory.createConnection();
		OAuthToken token;
//		token.
		ConnectionData cd;
//		factory.createConnection(data)
//		OAuth1ConnectionFactory<?> f2 = new OAuth1ConnectionFactory<Object>("", "", "");
		
	}
	
}
