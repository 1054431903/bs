package com.fwk.service.common.weather.moji;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;

@Component
public class RestTemplateUtil {

	@Autowired
	public RestTemplate restTemplate;

	public WeatherResponse sendPost(String url, WeatherRequestParam param, String headerName, String headerValue)
			throws RestClientException, URISyntaxException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(headerName, headerValue);
		JSONObject jsonObjectParam = (JSONObject) JSONObject.toJSON(param);
		MultiValueMap<String, String> multiValueMap = createMultiValueMap(jsonObjectParam);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(multiValueMap, headers);
		return restTemplate.exchange(new URI(url), HttpMethod.POST, requestEntity, WeatherResponse.class).getBody();
	}

	@SuppressWarnings("unchecked")
	private static MultiValueMap<String, String> createMultiValueMap(JSONObject params) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		for (String key : params.keySet()) {
			if (params.get(key) instanceof List) {
				for (Iterator<String> it = ((List<String>) params.get(key)).iterator(); it.hasNext();) {
					String value = it.next();
					map.add(key, value);
				}
			} else {
				if (ObjectUtil.isNotBlank(params.get(key))) {
					map.add(key, params.getString(key));
				}
			}
		}
		return map;
	}
}
