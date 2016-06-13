package com.nixmash.jangles.core;

import org.apache.commons.lang3.StringUtils;

public class JanglesUtils {

	public static String pluralize(String singular) {
		String plural = singular;
		int singularLength = StringUtils.length(singular);
		if (StringUtils.right(singular, 1) == "y")
			plural = StringUtils.left(singular, singularLength - 1) + "ies";
		else
			plural = singular + "s";
		return plural;
	}

	public static String lowerPluralize(String singular) {
		return StringUtils.uncapitalize(pluralize(singular));
	}

}
