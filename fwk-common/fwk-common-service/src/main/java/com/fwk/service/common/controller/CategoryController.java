package com.fwk.service.common.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.entity.CategoryEXT;
import com.fwk.service.common.support.SupportCategoryService;
import com.fwk.service.common.support.util.SupportCategoryUtil;
import com.fwk.service.common.support.util.SupportUtil;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private SupportCategoryService supportCategoryService;

	/**
	 * @Description: 获取品类
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年3月13日 下午1:53:13
	 */
	@RequestMapping("/queryCategory")
	public void queryCategory(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			resultWrap = supportCategoryService.queryCategoryInfoFromSupport(requestMap);
			if (resultWrap.getFlag() != ConstantUtils.RESULTWRAP_FLAG_OK) {
				return;
			}
			if (!SupportUtil.supportResultItemIsNotEmpty(resultWrap.getData())) {
				resultWrap.setData(null);
				resultWrap.setActSuccess();
				return;
			}
			Map<String, CategoryEXT> map = new ConcurrentHashMap<String, CategoryEXT>();
			JSONArray items = SupportUtil.supportGetItemData(resultWrap.getData());
			items.parallelStream().forEach(item -> {
				CategoryEXT categoryEXT = new CategoryEXT();
				SupportCategoryUtil.transJSONObjectToCategoryEXT((JSONObject) item, categoryEXT);
				map.put(categoryEXT.getId(), categoryEXT);
			});
			resultWrap.setData(map.values());
			resultWrap.setActSuccess();
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
}
