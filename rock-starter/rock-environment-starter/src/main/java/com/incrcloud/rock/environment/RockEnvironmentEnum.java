package com.incrcloud.rock.environment;

/**
 * @title: RockEnvironmentEnum
 * @projectName rock
 * @description: EnvironmentEnum
 * @author sirk
 * @date 2020/11/25
 */
public enum RockEnvironmentEnum {

	DEV("dev"), FAT("fat"), UAT("uat"), PRO("pro");

	private String env;

	private RockEnvironmentEnum(String env) {
		this.env = env;
	}

	public static RockEnvironmentEnum getEnvEnum(String value) {
		for (RockEnvironmentEnum rockEnvironmentEnum : RockEnvironmentEnum.values()) {
			if (rockEnvironmentEnum.getEnv().equalsIgnoreCase(value)) {
				return rockEnvironmentEnum;
			}
		}
		throw new IllegalArgumentException("No matched type with value=" + value);
	}

	public String getEnv() {
		return env;
	}

}
