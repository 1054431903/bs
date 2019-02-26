package com.fwk.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.ejb.access.EjbAccessException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fwk.common.util.annotation.ActionInfo;

/**
 * 通用的工具类
 * 
 * @author wangjunwen
 * @date 2017年9月13日 下午4:17:17
 *
 */
public class CommonsTools {

	/**
	 * 打印 application-log-action-name
	 * 
	 * @author wangjunwen
	 * @date 2017年9月13日 下午4:08:39
	 *
	 * @param clazzList
	 */
	public static void printApplicationLogActionName(List<Class<?>> clazzList) {

		final Map<Class<?>, Properties> applicationLogActionNameMap = new HashMap<Class<?>, Properties>();

		List<Class<?>> list = ClassTools.getAllClassFromClassPath();

		if (null == clazzList) {
			clazzList = new ArrayList<Class<?>>();
		}

		Method[] methods = null;
		RequestMapping requestMapping = null;
		ActionInfo actionInfo = null;
		Properties applicationLogActionName = null;

		for (Class<?> clazz : list) {

			methods = clazz.getDeclaredMethods();

			for (Method method : methods) {
				requestMapping = method.getAnnotation(RequestMapping.class);
				if (null != requestMapping) {
					System.out.println("\n\n");

					System.out.println("[controller]===> " + clazz.getName() + "." + method.getName());

					System.out.println("[controller-URL]===> value：" + Arrays.toString(requestMapping.value())
							+ "，path：" + Arrays.toString(requestMapping.path()) + "。");

					if (clazzList.contains(clazz)) {

						applicationLogActionName = applicationLogActionNameMap.get(clazz);

						if (null == applicationLogActionName) {
							applicationLogActionName = new Properties();
							applicationLogActionNameMap.put(clazz, applicationLogActionName);
						}
						actionInfo = method.getAnnotation(ActionInfo.class);

						if (null == actionInfo) {
							throw new EjbAccessException(
									clazz.getName() + "类的[" + method.getName() + "]方法必须添加[@ActionInfo]注解。");
						} else {
							System.out.println("[application-log-action-name]====> key："
									+ (actionInfo.operationKey() + method.getName()) + "，value："
									+ actionInfo.operationValue());

							applicationLogActionName.put((actionInfo.operationKey() + method.getName()),
									actionInfo.operationValue());
						}
					}
				}
			}
		} // 遍历所有的类 结束

		System.out.println("\n\n\n========================[application-log-action-name]====================");

		Entry<Class<?>, Properties> outterEntry = null;
		Entry<Object, Object> entry = null;

		for (Iterator<Entry<Class<?>, Properties>> outterIterator = applicationLogActionNameMap.entrySet()
				.iterator(); outterIterator.hasNext();) {

			outterEntry = outterIterator.next();

			System.out.println("#" + outterEntry.getKey().getSimpleName() + "");

			for (Iterator<Entry<Object, Object>> innerIterator = outterEntry.getValue().entrySet()
					.iterator(); innerIterator.hasNext();) {
				entry = innerIterator.next();
				System.out.println(entry.getKey() + "=" + entry.getValue());
			}

		}
	}

	/**
	 * 打印出所有请求的URL地址
	 * 
	 * @author wangjunwen
	 * @date 2017年9月14日 上午10:23:24
	 *
	 * @param prefixURL
	 *            请求地址的前缀（例如：http://10.88.20.66:8761/trc-service-enterprise）
	 * @param sufixURL
	 *            请求地址地址的后缀（可以拼接一些参数。例如：?operatorid=1&uid=123）
	 */
	public static void printAllRequestUrl(String prefixURL, String sufixURL) {

		// 请求地址的前缀
		prefixURL = ((null == prefixURL) ? "" : prefixURL.trim());

		// 请求地址地址的后缀
		sufixURL = ((null == sufixURL) ? "" : sufixURL.trim());

		List<Class<?>> list = ClassTools.getAllClassFromClassPath();

		System.out.println("===========================[request_url]========================");

		Method[] methods = null;
		RequestMapping requestMapping = null;
		RequestMapping controllerRequestMapping = null;

		List<String> pathOrValueList = null;
		String controllerPath = "";

		for (Class<?> clazz : list) {

			controllerPath = "";// 初始化

			controllerRequestMapping = clazz.getAnnotation(RequestMapping.class);
			if ((null != controllerRequestMapping)) {
				controllerPath = controllerRequestMapping.name();
				if (controllerPath.isEmpty()) {
					controllerPath = controllerRequestMapping.value()[0];
				}

				if (controllerPath.isEmpty()) {
					controllerPath = controllerRequestMapping.params()[0];
				}
			}

			methods = clazz.getDeclaredMethods();

			for (Method method : methods) {
				requestMapping = method.getAnnotation(RequestMapping.class);
				if (null != requestMapping) {
					pathOrValueList = new ArrayList<String>();

					pathOrValueList.addAll(Arrays.asList(requestMapping.value()));

					pathOrValueList.addAll(Arrays.asList(requestMapping.path()));

					for (String str : pathOrValueList) {
						System.out.println((prefixURL + controllerPath + str + sufixURL));
					}
				}
			} // 遍历所有的类 结束
		}
	}

}
