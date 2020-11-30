package com.incrcloud.rock.discovery.env;

import com.incrcloud.rock.environment.RockEnv;
import com.incrcloud.rock.environment.context.RockEnvPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * describe: Nacos Env Metadata Register
 *
 * @author sirk
 * @date 2020/11/30
 */
public class NacosEnvMetadataRegister extends RockEnvPostProcessor {

	private static final String PROPERTY_SOURCE_NAME = "envProperties";

	private static final String KEY_CHAIN_TAG = "x-chain-tag";

	private static final String KEY_NACOS_CHAIN_TAG = "spring.cloud.nacos.discovery.metadata."
			+ KEY_CHAIN_TAG;

	@Override
	public void doProcess(ConfigurableEnvironment environment) {
		MutablePropertySources mutablePropertySources = environment.getPropertySources();
		Map map = new HashMap<>(1);
		map.put(KEY_NACOS_CHAIN_TAG, RockEnv.getInstance().getEnvTag());
		MapPropertySource mapPropertySource = new MapPropertySource(PROPERTY_SOURCE_NAME,
				map);
		mutablePropertySources.addLast(mapPropertySource);
	}

}
