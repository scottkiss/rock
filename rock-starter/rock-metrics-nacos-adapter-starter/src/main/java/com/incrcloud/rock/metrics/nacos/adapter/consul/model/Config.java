package com.incrcloud.rock.metrics.nacos.adapter.consul.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * describe: config
 *
 * @author sirk
 * @date 2020/11/13
 */
@Getter
@Builder
public class Config {

	@JsonProperty("Datacenter")
	private String dataCenter;

}