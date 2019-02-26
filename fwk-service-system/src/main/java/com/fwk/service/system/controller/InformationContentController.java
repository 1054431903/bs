package com.fwk.service.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.system.entity.InformationContentEXT;
import com.fwk.service.system.service.InformationContentService;

@Controller
@RequestMapping("/informationContent")
public class InformationContentController {

	@Autowired
	private InformationContentService informationContentService;

	/**
	 * @Description: 增加信息发布内容
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月5日 下午2:08:50
	 */
	@RequestMapping("/saveInformationContent")
	public void saveInformationContent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationContentEXT informationContentEXT = HTMLUtil.getMapToEntity(InformationContentEXT.class,
					requestMap);
			// 非空验证
			if (ObjectUtil.isBlank(informationContentEXT.getInformationid())) {
				resultWrap.setParamIsnotEmpty("informationid");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			int result = 0;
			result = informationContentService.saveInformationContent(informationContentEXT);
			if (result > 0) {
				resultWrap.setSaveSuccess();
				resultWrap.setData(JSONObject.toJSON(informationContentEXT));
			} else {
				resultWrap.setSaveFailure();
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * @Description: 删除信息发布内容
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月5日 下午2:20:28
	 */
	@RequestMapping("/deleteInformationContent")
	public void deleteInformationContent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationContentEXT informationContentEXT = HTMLUtil.getMapToEntity(InformationContentEXT.class,
					requestMap);
			// 非空验证
			if (informationContentEXT.getId() == null
					&& (informationContentEXT.getIdlist() == null || informationContentEXT.getIdlist().isEmpty())) {
				resultWrap.setParamIsnotEmpty("{id,idlist}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			// 处需要删除的id
			if (informationContentEXT.getId() != null) {
				informationContentEXT.setIdlist(Arrays.asList(informationContentEXT.getId()));
			}
			informationContentService.deleteInformationContent(informationContentEXT);
			resultWrap.setDeleteSuccess();
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * @Description: 修改信息发布内容
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月5日 下午2:26:16
	 */
	@RequestMapping("/updateInformationContent")
	public void updateInformationContent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationContentEXT informationContentEXT = HTMLUtil.getMapToEntity(InformationContentEXT.class,
					requestMap);
			// 非空验证
			if (informationContentEXT.getId() == null || informationContentEXT.getInformationid() == null) {
				resultWrap.setParamIsnotEmpty("{id,informationid}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			int result = 0;
			result = informationContentService.updateInformationContent(informationContentEXT);
			if (result > 0) {
				resultWrap.setUpdateSuccess();
				resultWrap.setData(JSONObject.toJSON(informationContentEXT));
			} else {
				resultWrap.setUpdateFailure();
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * @Description: 查询信息发布内容
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月5日 下午2:32:58
	 */
	@RequestMapping("/queryInformationContent")
	public void queryInformationContent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationContentEXT informationContentEXT = HTMLUtil.getMapToEntity(InformationContentEXT.class,
					requestMap);
			// 非空验证
			if (informationContentEXT.getId() == null && informationContentEXT.getInformationid() == null) {
				resultWrap.setParamIsnotEmpty("{id,informationid}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(requestMap);
				informationContentEXT.setPage(page);
			}
			List<InformationContentEXT> list = informationContentService.queryInformationContent(informationContentEXT);
			// 是分页查询时设置分页信息
			if ((null != page)) {
				// 查询出总记录数量
				Integer totalCount = informationContentService.queryInformationContentCount(informationContentEXT);
				page.setTotal(totalCount);// 设置总的记录数量和总页数
				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}
			resultWrap.setData(JSONObject.toJSON(list));
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * @Description: 修改排序
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月6日 下午4:22:52
	 */
	@RequestMapping("/updateSort")
	public void updateSort(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 非空判断
			if (ObjectUtil.isBlank(requestMap.get("sourceid"))) {
				resultWrap.setParamIsnotEmpty("{sourceid}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			if (ObjectUtil.isBlank(requestMap.get("sourcesort"))) {
				resultWrap.setParamIsnotEmpty("{sourcesort}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			if (ObjectUtil.isBlank(requestMap.get("targetid"))) {
				resultWrap.setParamIsnotEmpty("{targetid}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			if (ObjectUtil.isBlank(requestMap.get("targetsort"))) {
				resultWrap.setParamIsnotEmpty("{targetsort}");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			InformationContentEXT sourceContent = new InformationContentEXT();
			sourceContent.setId(Integer.parseInt(requestMap.get("sourceid").toString()));
			sourceContent.setSort(Integer.parseInt(requestMap.get("sourcesort").toString()));
			InformationContentEXT targetContent = new InformationContentEXT();
			targetContent.setId(Integer.parseInt(requestMap.get("targetid").toString()));
			targetContent.setSort(Integer.parseInt(requestMap.get("targetsort").toString()));
			int result = 0;
			result = informationContentService.updateSort(sourceContent, targetContent);
			if (result > 1) {
				resultWrap.setUpdateSuccess();
			} else {
				resultWrap.setUpdateFailure();
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
}
