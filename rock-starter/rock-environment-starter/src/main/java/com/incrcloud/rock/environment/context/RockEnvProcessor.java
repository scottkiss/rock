package com.incrcloud.rock.environment.context;

import com.incrcloud.rock.environment.RockEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * describe:Rock Environment Processor
 *
 * @author sirk
 * @date 2020/11/25
 */
public abstract class RockEnvProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(RockEnvProcessor.class);

	@Autowired
	private RockEnv rockEnv;

	public void process(ConfigurableEnvironment environment) {
		String env = rockEnv.getEnv();
		LOG.info("Current is {} environment", env);

	}

}
