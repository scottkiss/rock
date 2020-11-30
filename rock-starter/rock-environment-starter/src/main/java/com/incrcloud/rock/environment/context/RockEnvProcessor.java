package com.incrcloud.rock.environment.context;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * describe:Rock Environment Processor
 *
 * @author sirk
 * @date 2020/11/25
 */
public abstract class RockEnvProcessor {

	public void process(ConfigurableEnvironment environment) {
		doProcess(environment);
	}

	/**
	 * do process
	 * @param environment
	 * @return void
	 */
	public abstract void doProcess(ConfigurableEnvironment environment);

}