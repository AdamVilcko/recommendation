package cz.ucl.recom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Custom Spring application context initializer to be able to extend some Spring core features.
 *
 * @author Adam Vilcko
 */
public class SpringContextInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	private static final Logger LOG = LoggerFactory.getLogger(SpringContextInitializer.class);

    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        // place additional configuration here
    	LOG.info("Initalizing the application...");
    }

}
