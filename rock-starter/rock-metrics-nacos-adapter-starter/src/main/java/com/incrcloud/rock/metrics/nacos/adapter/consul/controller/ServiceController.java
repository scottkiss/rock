package com.incrcloud.rock.metrics.nacos.adapter.consul.controller;

import com.incrcloud.rock.metrics.nacos.adapter.consul.model.ChangeItem;
import com.incrcloud.rock.metrics.nacos.adapter.consul.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import rx.Single;

import java.util.List;
import java.util.Map;

/**
 * describe: Service Controller
 *
 * @author sirk
 * @date 2020/11/13
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

	private static final String CONSUL_IDX_HEADER = "X-Consul-Index";

	private static final String QUERY_PARAM_WAIT = "wait";

	private static final String QUERY_PARAM_INDEX = "index";

	private final RegistrationService registrationService;

	@GetMapping(value = "/v1/catalog/services",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Single<ResponseEntity<Map<String, String[]>>> getServiceNames(
			@RequestParam(name = QUERY_PARAM_WAIT, required = false) String wait,
			@RequestParam(name = QUERY_PARAM_INDEX, required = false) Long index) {
		Assert.isTrue(wait == null, "param wait is not support now");
		Assert.isTrue(index == null, "param index is not support now");
		return registrationService.getServiceNames()
				.map(item -> createResponseEntity(item.getItem(), item.getChangeIndex()));

	}

	@GetMapping(value = "/v1/catalog/service/{appName}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Single<ResponseEntity<List<Map<String, Object>>>> getService(
			@PathVariable("appName") String appName,
			@RequestParam(name = QUERY_PARAM_WAIT, required = false) String wait,
			@RequestParam(name = QUERY_PARAM_INDEX, required = false) Long index) {
		Assert.isTrue(wait == null, "param wait is not support now");
		Assert.isTrue(index == null, "param index is not support now");
		Assert.isTrue(appName != null, "service name can not be null");
		return registrationService.getService(appName)
				.map(item -> createResponseEntity(item.getItem(), item.getChangeIndex()));
	}

	private MultiValueMap<String, String> createHeaders(long index) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CONSUL_IDX_HEADER, "" + index);
		return headers;
	}

	private <T> ResponseEntity<T> createResponseEntity(T body, long index) {
		return new ResponseEntity<>(body, createHeaders(index), HttpStatus.OK);
	}

}