package com.incrcloud.rock.config.env.autoconfiguration;

import com.incrcloud.rock.config.env.RockConfigEnvPostProcessor;
import com.incrcloud.rock.environment.RockEnv;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * describe: AutoConfiguration
 *
 * @author sirk
 * @date 2020/11/26
 */
@Configuration
@EnableAsync
public class RockConfigEnvAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(RockEnv.class)
	public RockConfigEnvPostProcessor rockConfigEnvPostProcessor() {
		return new RockConfigEnvPostProcessor();
	}

}
