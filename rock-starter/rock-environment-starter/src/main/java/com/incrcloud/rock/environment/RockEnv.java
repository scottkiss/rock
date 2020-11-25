package com.incrcloud.rock.environment;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * describe: Rock environment domain
 *
 * @author sirk
 * @date 2020/11/25
 */
public class RockEnv implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(RockEnv.class);

	/**
	 * application id
	 */
	private String appId;

	/**
	 * domain
	 */
	private String domain;

	/**
	 * zone
	 */
	private String zone;

	/**
	 * environment
	 */
	private String env;

	private String getEnvPropValue(String key) throws Exception {
		String value = System.getProperty(key);
		if (!StringUtils.isBlank(value)) {
			value = value.trim();
		}
		else {
			value = System.getenv(key.toUpperCase());
			if (!StringUtils.isBlank(value)) {
				value = value.trim();
			}
			else {
				Properties properties = new Properties();
				String path = getServerPropertiesFilePath();
				File file = new File(path);
				if (file.exists() && file.canRead()) {
					try (FileInputStream fileInputStream = new FileInputStream(file);
							InputStreamReader inputStreamReader = new InputStreamReader(
									fileInputStream)) {
						properties.load(inputStreamReader);
					}
				}

				value = properties.getProperty(key);
				if (!StringUtils.isBlank(value)) {
					value = value.trim();
				}
			}
		}

		return value;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getZone() {
		return this.zone;
	}

	public String getEnv() {
		return this.env;
	}

	private String getServerPropertiesFilePath() {
		return SystemUtils.IS_OS_WINDOWS ? RockEnvConstant.SERVER_PROPERTIES_PATH_WINDOWS
				: RockEnvConstant.SERVER_PROPERTIES_PATH_LINUX;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initDomain();
		initZone();
		initEnv();
	}

	private void initDomain() {
		try {
			this.domain = getEnvPropValue(RockEnvConstant.DOMAIN_NAME);
		}
		catch (Exception e) {
			LOG.info("Initialize domain failed, use default domain={}",
					RockEnvConstant.DEFAULT_DOMAIN_VALUE);
		}

		if (StringUtils.isBlank(this.domain)) {
			this.domain = RockEnvConstant.DEFAULT_DOMAIN_VALUE;
		}
		System.setProperty(RockEnvConstant.DOMAIN_NAME, this.domain);
		// for discovery metadata
		System.setProperty(RockEnvConstant.ROCK_DOMAIN_NAME, this.domain);
	}

	private void initZone() {
		try {
			this.zone = getEnvPropValue(RockEnvConstant.ZONE_NAME);
		}
		catch (Exception e) {
			LOG.info("Initialize zone failed,use empty zone");
		}
		if (StringUtils.isBlank(this.zone)) {
			System.setProperty(RockEnvConstant.ZONE_NAME, StringUtils.EMPTY);
		}
		else {
			System.setProperty(RockEnvConstant.ZONE_NAME, this.zone);
		}
		// for discovery metadata
		System.setProperty(RockEnvConstant.ROCK_ZONE_NAME, this.zone);
	}

	private void initEnv() {
		try {
			this.env = getEnvPropValue(RockEnvConstant.ENV_NAME);
		}
		catch (Exception e) {
			LOG.info("Initialize env failed,use dev environment");
		}
		if (StringUtils.isBlank(this.env)) {
			System.setProperty(RockEnvConstant.ENV_NAME,
					RockEnvironmentEnum.DEV.getEnv());
		}
		else {
			System.setProperty(RockEnvConstant.ENV_NAME, this.env);
		}
		// for discovery metadata
		System.setProperty(RockEnvConstant.ROCK_ENV_NAME, this.env);
	}

}