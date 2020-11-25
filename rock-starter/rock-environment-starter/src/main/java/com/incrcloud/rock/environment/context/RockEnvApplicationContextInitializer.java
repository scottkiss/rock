package com.incrcloud.rock.environment.context;

import commons.RockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * describe: Rock Env ApplicationContextInitializer
 *
 * @author sirk
 * @date 2020/11/25
 */
public class RockEnvApplicationContextInitializer extends RockEnvProcessor
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger LOG = LoggerFactory
			.getLogger(RockEnvApplicationContextInitializer.class);

	@Override
	public void initialize(
			ConfigurableApplicationContext configurableApplicationContext) {
		if (!(configurableApplicationContext instanceof AnnotationConfigApplicationContext)) {
			try {
				LOG.info("Initialize rock context...");
			}
			catch (Exception e) {
				throw new RockException(e);
			}
		}
	}

}
