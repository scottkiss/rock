package com.incrcloud.rock.environment;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class RockEnv {

	private static final Logger LOG = LoggerFactory.getLogger(RockEnv.class);

	private static final RockEnv INSTANCE = new RockEnv();

	private RockEnv() {
		initGroup();
		initEnv();
		initEnvTag();
	}

	/**
	 * application id
	 */
	private String appId;

	/**
	 * group
	 */
	private String group;

	/**
	 * environment
	 */
	private String env;

	/**
	 * environment tag for multipart environments
	 */
	private String envTag;

	public static RockEnv getInstance() {
		return INSTANCE;
	}

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

	public String getGroup() {
		return this.group;
	}

	public String getEnv() {
		return this.env;
	}

	public String getEnvTag() {
		return this.envTag;
	}

	private String getServerPropertiesFilePath() {
		return SystemUtils.IS_OS_WINDOWS ? RockEnvConstant.SERVER_PROPERTIES_PATH_WINDOWS
				: RockEnvConstant.SERVER_PROPERTIES_PATH_LINUX;
	}

	private void initGroup() {
		try {
			this.group = getEnvPropValue(RockEnvConstant.GROUP_NAME);
		}
		catch (Exception e) {
			LOG.info("Initialize group failed,use default group");
		}
		if (StringUtils.isBlank(this.group)) {
			this.group = RockEnvConstant.DEFAULT_GROUP_VALUE;
			System.setProperty(RockEnvConstant.GROUP_NAME, this.group);
		}
		else {
			System.setProperty(RockEnvConstant.GROUP_NAME, this.group);
		}
		// for discovery metadata
		System.setProperty(RockEnvConstant.ROCK_GROUP_NAME, this.group);
	}

	private void initEnv() {
		try {
			this.env = getEnvPropValue(RockEnvConstant.ENV_NAME);
			LOG.info("Initialize env :{}", this.env);
		}
		catch (Exception e) {
			LOG.info("Initialize env failed,use dev environment");
		}
		if (StringUtils.isBlank(this.env)) {
			this.env = RockEnvironmentEnum.DEV.getEnv();
			System.setProperty(RockEnvConstant.ENV_NAME, this.env);
		}
		else {
			System.setProperty(RockEnvConstant.ENV_NAME, this.env);
		}
		// for discovery metadata
		System.setProperty(RockEnvConstant.ROCK_ENV_NAME, this.env);
	}

	private void initEnvTag() {
		try {
			this.envTag = getEnvPropValue(RockEnvConstant.ENV_TAG_NAME);
		}
		catch (Exception e) {
			LOG.info("Initialize env tag failed,use default env tag");
		}
		if (StringUtils.isBlank(this.envTag)) {
			this.envTag = RockEnvConstant.DEFAULT_ENV_TAG_VALUE;
			System.setProperty(RockEnvConstant.ENV_TAG_NAME, this.envTag);
		}
		else {
			System.setProperty(RockEnvConstant.ENV_TAG_NAME, this.envTag);
		}

	}

}
