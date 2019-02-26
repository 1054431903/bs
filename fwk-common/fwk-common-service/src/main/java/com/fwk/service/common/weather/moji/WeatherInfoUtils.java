package com.fwk.service.common.weather.moji;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class WeatherInfoUtils {

	/**
	 * @Description:
	 *               <p>
	 *               拷贝非空对象属性值
	 *               </P>
	 *
	 * @param source
	 *            源对象(需要拷贝的对象)
	 * @param target
	 *            目标对象(拷贝完成以后的对象)
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月17日 下午2:16:05
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	/**
	 * @Description:
	 *               <p>
	 *               获取到对象中属性为null的属性名
	 *               </P>
	 *
	 * @param source
	 *            要拷贝的对象
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月17日 下午2:16:23
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
		List<String> emptyName = Arrays.asList(propertyDescriptors).stream().filter(propertyDescriptor -> {
			return beanWrapper.getPropertyValue(propertyDescriptor.getName()) == null;
		}).map(propertyDescriptor -> {
			return propertyDescriptor.getName();
		}).collect(Collectors.toList());
		String[] result = new String[emptyName.size()];
		return emptyName.toArray(result);
	}
}
