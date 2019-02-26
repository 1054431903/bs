package com.fwk.service.common.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.entity.PositionLatLng;
import com.fwk.service.common.service.PositionLatLngService;

@Controller
@RequestMapping("/poslatlng")
public class PositionLatLngController {

	@Autowired
	private PositionLatLngService posLatLngService;

	/**
	 * 通过行政区划name查询经纬度
	 */
	@RequestMapping("/query")
	public void queryPosLatlngByName(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if (ObjectUtil.isNotBlank(requestMap.get("name"))) {
				List<PositionLatLng> posLatLng = getPosLatLng(requestMap);
				resultWrap.setData(posLatLng);
			} else {
				resultWrap.setParamIsnotEmpty("{name}");
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 查询行政区划经纬度
	 * 
	 * @param map
	 * map内必须存在name参数
	 */
	public List<PositionLatLng> getPosLatLng(Map<String, Object> requestMap) {
		if (ObjectUtil.isNotBlank(requestMap.get("name"))) {
			return posLatLngService.queryPosLatLngByName(requestMap);
		} else {
			return null;
		}
	}
}
