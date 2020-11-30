package com.incrcloud.rock.config.env;

import com.incrcloud.rock.environment.RockEnv;
import com.incrcloud.rock.environment.context.RockEnvPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * describe: RockConfigEnvPostProcessor
 *
 * @author sirk
 * @date 2020/11/25
 */
public class RockConfigEnvPostProcessor extends RockEnvPostProcessor implements Ordered {

	private static final Logger LOG = LoggerFactory
			.getLogger(RockConfigEnvPostProcessor.class);

	@Override
	public void doProcess(ConfigurableEnvironment environment) {
		RockEnv rockEnv = RockEnv.getInstance();
		setIfNull("spring.cloud.nacos.config.namespace", rockEnv.getEnv(), environment);
		setIfNull("spring.cloud.nacos.config.group", rockEnv.getGroup(), environment);
		LOG.info("set namespace:{}", rockEnv.getEnv());
	}

	private void setIfNull(String key, String value,
			ConfigurableEnvironment environment) {
		if (environment.getProperty(key) == null && System.getProperty(key) == null
				&& System.getenv(key.toUpperCase()) == null) {
			System.setProperty(key, value);
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
