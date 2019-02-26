package com.fwk.service.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.SupportCategoryService;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.system.entity.VarietiesEXT;
import com.fwk.service.system.service.VarietiesService;

@Controller
@RequestMapping("/varieties")
public class VarietiesController {

	@Autowired
	private VarietiesService varietiesService;
	
	@Autowired
	private SupportCategoryService supportCategoryService;

	/**
	 * @Description: 保存品种信息
	 *
	 * @param response
	 * @param request
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyq
	 * @date: 2018年7月6日 下午1:23:31
	 */
	@RequestMapping("/saveVarieties")
	public void saveVarieties(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			VarietiesEXT varietiesEXT = HTMLUtil.getMapToEntity(VarietiesEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(varietiesEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}
			if (ObjectUtil.isBlank(varietiesEXT.getName())) {
				resultWrap.setParamIsnotEmpty("name");
				return;
			}
			if (ObjectUtil.isBlank(varietiesEXT.getCategoryid())) {
				resultWrap.setParamIsnotEmpty("categoryid");
				return;
			}
			varietiesService.saveVarieties(varietiesEXT);
			resultWrap.setSaveSuccess();
			resultWrap.setData(varietiesEXT);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 删除品种信息
	 *
	 * @param response
	 * @param request
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年7月6日 下午1:57:45
	 */
	@RequestMapping("/deleteVarieties")
	public void deleteVarieties(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			VarietiesEXT varietiesEXT = HTMLUtil.getMapToEntity(VarietiesEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(varietiesEXT.getId())) {
				resultWrap.setParamIsnotEmpty("id");
				return;
			}
			varietiesService.deleteVarieties(varietiesEXT.getId());
			resultWrap.setDeleteSuccess();
			resultWrap.setData(varietiesEXT);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 更新品种信息
	 *
	 * @param response
	 * @param request
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年7月6日 下午1:55:14
	 */
	@RequestMapping("/updateVarieties")
	public void updateVarieties(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			VarietiesEXT varietiesEXT = HTMLUtil.getMapToEntity(VarietiesEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(varietiesEXT.getId())) {
				resultWrap.setParamIsnotEmpty("id");
				return;
			}
			if (ObjectUtil.isBlank(varietiesEXT.getName())) {
				resultWrap.setParamIsnotEmpty("name");
				return;
			}
			if (ObjectUtil.isBlank(varietiesEXT.getCategoryid())) {
				resultWrap.setParamIsnotEmpty("categoryid");
				return;
			}
			varietiesService.updateVarieties(varietiesEXT);
			resultWrap.setUpdateSuccess();
			resultWrap.setData(varietiesEXT);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 查询品种信息
	 *
	 * @param response
	 * @param request
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年7月6日 下午1:54:56
	 */
	@RequestMapping("/queryVarieties")
	public void queryVarieties(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			VarietiesEXT varietiesEXT = HTMLUtil.getMapToEntity(VarietiesEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(varietiesEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}
			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(requestMap);
				varietiesEXT.setPage(page);
			}
			List<VarietiesEXT> list = varietiesService.queryVarieties(varietiesEXT);
			// 是分页查询时设置分页信息
			if ((null != page)) {
				// 查询出总记录数量
				Integer totalCount = varietiesService.queryVarietiesCount(varietiesEXT);
				page.setTotal(totalCount);// 设置总的记录数量和总页数
				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}
			//组装品类名称
			list.stream().forEach(item -> {
				if (ObjectUtil.isNotBlank(item.getCategoryid())) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", item.getCategoryid());
					ResultWrap tmpResultWrap = supportCategoryService.queryCategoryInfoFromSupport(map);
					if (tmpResultWrap.getFlag() != ConstantUtils.RESULTWRAP_FLAG_OK) {
						resultWrap.setFlag(tmpResultWrap.getFlag());
						resultWrap.setMessage("支撑平台-根据品类ID查询品类名称出错：" + tmpResultWrap.getMessage());
						return;
					}
					if (SupportUtil.supportResultItemIsNotEmpty(tmpResultWrap.getData())) {
						JSONArray supportData = SupportUtil.supportGetItemData(tmpResultWrap.getData());
						item.setCategoryname(supportData.getJSONObject(0).getString("name"));
					}
				}
			});
			
			resultWrap.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
}
