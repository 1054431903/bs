package com.fwk.common.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		List<Class<?>> list = ClassTools.getAllClassFromClassPath();

		Method[] methods = null;
		RequestMapping requestMapping = null;
		for (Class<?> clazz : list) {
			methods = clazz.getDeclaredMethods();

			for (Method method : methods) {
				requestMapping = method.getAnnotation(RequestMapping.class);
				if (null != requestMapping) {
					System.out.println("[controller-URL]===> " + Arrays.toString(requestMapping.value()));
					System.out.println("[controller]===> " + clazz.getName() + "." + method.getName());
				}
			}
		}

	}
}
