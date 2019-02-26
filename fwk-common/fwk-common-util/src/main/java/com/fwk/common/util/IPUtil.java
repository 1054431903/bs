package com.fwk.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.language.LanguageUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

//获取IP
public class IPUtil {

	private static String getAddressLanguageCN = "zh-CN";// 获取地点的语言----中文
	private static String getAddressLanguageEN = "en";// 获取地点的语言----英文

	// 被过滤的IP地址
	// private static String LoginErrorFilterIP = "0:0:0:0:0:0:0:1";
	//
	// public static String getIP(HttpServletRequest request){
	// return LoginErrorFilterIP.equals(request.getRemoteAddr()) ? "" :
	// request.getRemoteAddr();
	// }

	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	// 获取城市地点
	public static Map<String, Object> getAddress(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String country = "";
		String province = "";
		String city = "";
		try {
			String ip = getIP(request);
			InputStream inputStream = LanguageUtil.class.getResourceAsStream("/GeoLite2-City.mmdb");
			DatabaseReader reader = new DatabaseReader.Builder(inputStream).build();
			InetAddress inetAddress = InetAddress.getByName(ip);
			CityResponse response = reader.city(inetAddress);
			country = response.getCountry().getNames().get(getAddressLanguageCN);
			if (ObjectUtil.isNotBlank(country)) {
				if (country.equals("香港") || country.equals("澳门") || country.equals("台湾")) {
					map.put("country", "中国");
				} else {
					map.put("country", country);
				}
			}
			province = response.getMostSpecificSubdivision().getNames().get(getAddressLanguageCN) != null
					? response.getMostSpecificSubdivision().getNames().get(getAddressLanguageCN)
					: response.getMostSpecificSubdivision().getName();
			city = response.getCity().getNames().get(getAddressLanguageCN) != null
					? response.getCity().getNames().get(getAddressLanguageCN) : response.getCity().getName();
			if (ObjectUtil.isNotBlank(country) && country.equals("香港")) {
				province = "香港";
				city = "香港";
			}
			if (ObjectUtil.isNotBlank(country) && country.equals("台湾")) {
				province = "台湾";
			}
			if (ObjectUtil.isNotBlank(country) && country.equals("澳门")) {
				province = "澳门";
				city = "澳门";
			}
			if (ObjectUtil.isBlank(province) && ObjectUtil.isNotBlank(city)) {
				province = city;
			}
			if (ObjectUtil.isNotBlank(province)) {
				if (province.contains("省")) {
					province = province.replace("省", "");
				}
				if (province.contains("市")) {
					province = province.replace("市", "");
				}
				if (province.equals("闽")) {
					province = "福建";
				}
				map.put("province", province);
			}

			if (ObjectUtil.isNotBlank(city)) {
				if (city.contains("市")) {
					city = city.replace("市", "");
				}
				map.put("city", city);
			}
		} catch (Exception e) {
			map.put("country", country);
			map.put("province", province);
			map.put("city", city);
		}
		return map;
	}

	/**
	 * 获取网络信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月2日 下午1:20:23
	 *
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 * @throws Exception
	 */
	public static IPNetworkInfo getNetworkInfo(HttpServletRequest request) throws Exception {
		final String ip = getIP(request);

		final String requestUrl = ("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);

		URL url = new URL(requestUrl); // 把字符串转换为URL请求地址
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
		connection.connect();// 连接会话

		try (InputStream inputStream = connection.getInputStream();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

			byte[] buffer = new byte[1024];
			int readLength = -1;

			while ((-1 != (readLength = inputStream.read(buffer)))) {
				byteArrayOutputStream.write(buffer, 0, readLength);
			}

			String responseBody = new String(byteArrayOutputStream.toByteArray());

			if ((null != responseBody) && !((responseBody = responseBody.trim()).isEmpty())) {
				JSONObject jsonObject = JSONObject.parseObject(responseBody);

				if (new Integer(0).equals(jsonObject.get("code"))) {
					JSONObject dataJSONObject = (JSONObject) jsonObject.get("data");

					IPNetworkInfo ipNetworkInfo = dataJSONObject.toJavaObject(IPNetworkInfo.class);

					return ipNetworkInfo;
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			connection.disconnect();
		}

		return null;
	}

}
