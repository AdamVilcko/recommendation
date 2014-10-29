package cz.ucl.config;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Adam Vilcko
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		System.out.println("Lets inspect the beans provided by Spring Boot:");
		
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}
