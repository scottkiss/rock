package com.incrcloud.rock.metrics.nacos.adapter.consul.service;

import com.incrcloud.rock.metrics.nacos.adapter.consul.model.ChangeItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import rx.Single;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * describe: Returns Services and List of Service with its last changed
 *
 * @author sirk
 * @date 2020/11/13
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

	private static final String[] NO_SERVICE_TAGS = new String[0];

	private static final BinaryOperator<String[]> MERGE_FUNCTION = (u, v) -> {
		throw new IllegalStateException("Duplicate key");
	};

	private final DiscoveryClient discoveryClient;

	public Single<ChangeItem<Map<String, String[]>>> getServiceNames() {
		return returnDeferred(
				() -> discoveryClient.getServices().stream().collect(Collectors.toMap(
						a -> a, b -> NO_SERVICE_TAGS, MERGE_FUNCTION, TreeMap::new)));

	}

	public Single<ChangeItem<List<Map<String, Object>>>> getService(String appName) {
		return returnDeferred(() -> {
			List<ServiceInstance> instances = discoveryClient.getInstances(appName);
			List<Map<String, Object>> instanceInfos = new ArrayList<>();

			if (instances == null) {
				return Collections.emptyList();
			}
			else {
				Set<ServiceInstance> instSet = new HashSet<>(instances);
				for (ServiceInstance instance : instSet) {
					// Instance information
					Map<String, Object> instanceInfo = new HashMap<>(9);
					// metadata information
					Map<String, String> metaInfo = new HashMap<>(1);
					metaInfo.put("management.port", "" + instance.getPort());
					instanceInfo.put("Address", instance.getHost());
					instanceInfo.put("Node", instance.getServiceId());
					instanceInfo.put("ServiceAddress", instance.getHost());
					instanceInfo.put("ServiceName", instance.getServiceId());
					instanceInfo.put("ServiceID",
							instance.getHost() + ":" + instance.getPort());
					instanceInfo.put("ServicePort", instance.getPort());
					instanceInfo.put("NodeMeta", Collections.emptyMap());
					instanceInfo.put("ServiceMeta", metaInfo);
					instanceInfo.put("ServiceTags", Collections.emptyList());
					instanceInfos.add(instanceInfo);
				}
				return instanceInfos;
			}
		});
	}

	private <T> Single<ChangeItem<T>> returnDeferred(Supplier<T> fn) {
		return Single.just(new ChangeItem<>(fn.get(), System.currentTimeMillis()));
	}

}