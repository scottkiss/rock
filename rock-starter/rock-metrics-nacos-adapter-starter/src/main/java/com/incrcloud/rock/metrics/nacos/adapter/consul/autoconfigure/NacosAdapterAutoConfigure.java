package com.incrcloud.rock.metrics.nacos.adapter.consul.autoconfigure;

import com.incrcloud.rock.metrics.nacos.adapter.consul.controller.AgentController;
import com.incrcloud.rock.metrics.nacos.adapter.consul.controller.ServiceController;
import com.incrcloud.rock.metrics.nacos.adapter.consul.service.RegistrationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.Assert;

/**
 * describe: NacosAdapterAutoConfigure
 *
 * @author sirk
 * @date 2020/11/16
 */
@Configuration
@EnableAsync
public class NacosAdapterAutoConfigure {

	@Bean
	@ConditionalOnMissingBean
	public AgentController agentController() {
		return new AgentController();
	}

	@Bean
	@ConditionalOnMissingBean
	public ServiceController serviceController(RegistrationService registrationService) {
		return new ServiceController(registrationService);
	}

	@Bean
	@ConditionalOnMissingBean
	public RegistrationService registrationService(DiscoveryClient discoveryClient) {
		Assert.isTrue(discoveryClient instanceof DiscoveryClient,
				"Instance Registry must be of type" + DiscoveryClient.class.getName());
		return new RegistrationService(discoveryClient);
	}

}