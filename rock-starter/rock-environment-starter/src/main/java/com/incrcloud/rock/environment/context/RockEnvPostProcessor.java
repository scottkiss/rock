package com.incrcloud.rock.environment.context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

/**
 * describe: Rock Env PostProcessor
 *
 * @author sirk
 * @date 2020/11/25
 */
public abstract class RockEnvPostProcessor extends RockEnvProcessor
		implements EnvironmentPostProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(RockEnvPostProcessor.class);

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
		if (StringUtils.equals(environment.getClass().getName(),
				StandardEnvironment.class.getName())) {
			LOG.info("Initialize rock environment...");
			// process rock environment
			process(environment);
		}
	}

}
