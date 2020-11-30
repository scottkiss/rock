package com.incrcloud.rock.environment.rpc.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * describe: Interceptor feign request set chain tag
 *
 * @author sirk
 * @date 2020/11/27
 */
public class EnvironmentInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String envChainTag = request == null ? StringUtils.EMPTY
				: request.getHeader("X-Chain-Tag");
		requestTemplate.header("X-Chain-Tag", envChainTag);
	}

}
