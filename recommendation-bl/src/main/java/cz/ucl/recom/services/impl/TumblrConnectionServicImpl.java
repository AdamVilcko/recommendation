package cz.ucl.recom.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tumblr.jumblr.JumblrClient;

import cz.ucl.recom.services.TumblrConnectionService;

/**
 * 
 * @author Adam VILCKO
 */
@Component
public class TumblrConnectionServicImpl implements TumblrConnectionService {

	private static final Logger LOG = LoggerFactory.getLogger(TumblrConnectionServicImpl.class);

	@Autowired
	private JumblrClient client;

	/**
	 * {@inherit-doc}
	 */
	@Override
	public void testConnection() {
		LOG.info(client.user().getName());
	}

}
