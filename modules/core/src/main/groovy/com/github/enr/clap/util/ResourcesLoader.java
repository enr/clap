package com.github.enr.clap.util;

import static com.github.enr.clap.vendor.guava.base.Preconditions.checkArgument;

import java.net.URL;

public class ResourcesLoader {
	/**
	 * Guava 14.0.1 com.google.common.io.Resources
	 */
	public static URL getResource(Class<?> contextClass, String resourceName) {
		URL url = contextClass.getResource(resourceName);
		checkArgument(url != null, "resource %s relative to %s not found.",
				resourceName, contextClass.getName());
		return url;
	}
	/**
	 * Guava 14.0.1 com.google.common.io.Resources
	 */
	public static URL getResource(String resourceName) {
		URL url = ResourcesLoader.class.getClassLoader().getResource(resourceName);
		checkArgument(url != null, "resource %s not found.", resourceName);
		return url;
	}
}
