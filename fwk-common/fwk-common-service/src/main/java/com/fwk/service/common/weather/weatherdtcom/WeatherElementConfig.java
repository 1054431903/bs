package com.fwk.service.common.weather.weatherdtcom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 天气要素的配置类
 * 
 * @author wangjunwen
 * @date 2018年2月13日 上午9:42:26
 *
 */
public class WeatherElementConfig {

	private WeatherElementConfig() {
	}

	/** 当前使用的语言 */
	private static final String language = "zh-cn";
	/** 默认使用的字符集 */
	private static final String default_charset = "UTF-8";

	/** 预警信号编号 */
	private static final Properties warning_signal_code_prop = new Properties();
	/** 预警信号等级编号 */
	private static final Properties warning_signal_level_code_prop = new Properties();
	/** 天气现象编号 */
	private static final Properties weather_phenomenon_code_prop = new Properties();
	/** 风向编号 */
	private static final Properties wind_direction_code_prop = new Properties();
	/** 风力编号 */
	private static final Properties wind_power_code_prop = new Properties();

	static {

		// ======== 加载预警信号编号的数据 ====================
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				MessageFormat.format("www_weatherdt_com/warning_signal_code-{0}.properties", language));
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
						Charset.forName(default_charset));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			warning_signal_code_prop.load(bufferedReader);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// ======== 加载预警信号等级编号的数据 ===================
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				MessageFormat.format("www_weatherdt_com/warning_signal_level_code-{0}.properties", language));
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
						Charset.forName(default_charset));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			warning_signal_level_code_prop.load(bufferedReader);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// ======== 加载天气现象编号的数据 ===================
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				MessageFormat.format("www_weatherdt_com/weather_phenomenon_code-{0}.properties", language));
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
						Charset.forName(default_charset));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			weather_phenomenon_code_prop.load(bufferedReader);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// ======== 加载风向编号的数据 ===================
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				MessageFormat.format("www_weatherdt_com/wind_direction_code-{0}.properties", language));
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
						Charset.forName(default_charset));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			wind_direction_code_prop.load(bufferedReader);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// ======== 加载风力编号的数据 ===================
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				MessageFormat.format("www_weatherdt_com/wind_power_code-{0}.properties", language));
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
						Charset.forName(default_charset));
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

			wind_power_code_prop.load(bufferedReader);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * 根据预警信号的编号获取预警信号的名称
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午9:48:48
	 *
	 * @param code
	 *            预警信号的编号
	 * @return
	 */
	public static String getWarningSignalByCode(String code) {
		return warning_signal_code_prop.getProperty(code);
	}

	/**
	 * 根据预警级别编号获取预警级别的名称
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午9:48:48
	 *
	 * @param code
	 *            预警级别编号
	 * @return
	 */
	public static String getWarningSignalLevelByCode(String code) {
		return warning_signal_level_code_prop.getProperty(code);
	}

	/**
	 * 根据天气现象的编号获取天气现象的名称
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午9:48:48
	 *
	 * @param code
	 *            天气现象编号
	 * @return
	 */
	public static String getWeatherPhenomenonByCode(String code) {
		return weather_phenomenon_code_prop.getProperty(code);
	}

	/**
	 * 根据风向编号获取风向的名称
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午9:48:48
	 *
	 * @param code
	 *            风向编号
	 * @return
	 */
	public static String getWindDirectionByCode(String code) {
		return wind_direction_code_prop.getProperty(code);
	}

	/**
	 * 根据风力编号获取风力的名称
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 上午9:48:48
	 *
	 * @param code
	 *            风力编号
	 * @return
	 */
	public static String getWindPowerByCode(String code) {
		return wind_power_code_prop.getProperty(code);
	}

}
