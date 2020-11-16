package com.incrcloud.rock.metrics.nacos.adapter.consul.controller;

import com.incrcloud.rock.metrics.nacos.adapter.consul.model.Agent;
import com.incrcloud.rock.metrics.nacos.adapter.consul.model.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * describe: Agent Controller
 *
 * @author sirk
 * @date 2020/11/13
 */
@Controller
@RequiredArgsConstructor
public class AgentController {

	@GetMapping(value = "/v1/agent/self", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Agent getNodes() {
		String dataCenter = null;
		Config config = Config.builder()
				.dataCenter(Optional.ofNullable(dataCenter).orElse("default")).build();
		return Agent.builder().config(config).build();
	}

}