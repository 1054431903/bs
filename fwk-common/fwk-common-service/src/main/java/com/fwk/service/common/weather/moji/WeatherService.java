package com.fwk.service.common.weather.moji;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherService {

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	public WeatherInfo getWeather(WeatherRequestParam param, String weatherCategorys)
			throws RestClientException, IllegalArgumentException, IllegalAccessException, URISyntaxException {
		WeatherInfo weatherInfo = new WeatherInfo();
		List<String> weatherCategoryList = Arrays.asList(weatherCategorys.split(","));
		if (!apiConfig.getWeartherCategorys().containsAll(weatherCategoryList)) {
			ResponseInfo rc = new ResponseInfo();
			rc.setC("3");
			rc.setP("weatherCategorys 参数不正确，请确认！");
			weatherInfo.setRc(rc);
			return weatherInfo;
		}
		for (String weatherCategory : weatherCategoryList) {
			String url = apiConfig.getUrl() + weatherCategory;
			System.out.println(url);
			WeatherResponse response = restTemplateUtil.sendPost(url, param, apiConfig.getHeaderName(),
					apiConfig.getHeaderValue());
			switch (response.getCode()) {
			//请求成功
			case "0":
				// 拷贝非空属性值
				WeatherInfoUtils.copyPropertiesIgnoreNull(response.getData(), weatherInfo);
				weatherInfo.setRc(response.getRc());
				break;
			//请求出错，token异常
			case "1":
				response.setMsg("请求出错,token异常");
				response.getRc().setP("请求出错,token异常");
				weatherInfo.setRc(response.getRc());
				break;
			//请求出错，签名错误
			case "2":
				response.setMsg("请求出错,签名错误");
				response.getRc().setP("请求出错,签名错误");
				weatherInfo.setRc(response.getRc());
				break;
			//请求出错，经纬度超限
			case "10":
				response.setMsg("请求出错,经纬度超限");
				response.getRc().setP("请求出错,经纬度超限");
				weatherInfo.setRc(response.getRc());
				break;
			//请求出错，系统错误
			default:
				response.setMsg("请求出错,系统错误");
				response.getRc().setP("请求出错,系统错误");
				weatherInfo.setRc(response.getRc());
				break;
			}
		}
		return weatherInfo;
	}

}
