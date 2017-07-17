package com.nicolasguo.express.util;

import org.apache.commons.lang3.StringUtils;

public class UUID {

	public static String generateUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isValid(String id) {
		if (StringUtils.isNotBlank(id) && id.length() == 32) {
			return true;
		}
		return false;
	}
}
