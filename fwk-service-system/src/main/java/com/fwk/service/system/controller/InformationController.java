package com.fwk.service.system.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.support.SupportCategoryService;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.system.entity.InformationEXT;
import com.fwk.service.system.entity.InformationResourceEXT;
import com.fwk.service.system.service.InformationContentService;
import com.fwk.service.system.service.InformationResourceService;
import com.fwk.service.system.service.InformationService;

@Controller
@RequestMapping("/information")
public class InformationController {

	@Autowired
	private InformationService informationService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private InformationResourceService informationResourceService;

	@Autowired
	private InformationContentService informationContentService;

	@Autowired
	private SupportUserService supportUserService;

	@Autowired
	private SupportCategoryService supportCategoryService;
	
	@Autowired
	private SupportOrgService supportOrgService;

	/**
	 * @Description: 新增信息发布
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月1日 下午4:54:43
	 */
	@RequestMapping("/saveInformation")
	public void saveInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);

			// 非空验证
			if (ObjectUtil.isBlank(informationEXT.getTitle())) {
				resultWrap.setParamIsnotEmpty("title");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getCategoryid())) {
				resultWrap.setParamIsnotEmpty("categoryid");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}

			int result = 0;
			result = informationService.saveInformation(informationEXT);
			// 存储资源图片
			if (ObjectUtil.isNotBlank(informationEXT.getResourcepath())) {
				ResourcehistoryEXT resourcehistoryEXT = commonService.addOrUpdateResource(null,
						informationEXT.getResourcepath(), "SOP信息发布-概要信息封面图片");
				if (ObjectUtil.isNotBlank(resourcehistoryEXT)) {
					InformationResourceEXT informationResourceEXT = new InformationResourceEXT();
					informationResourceEXT.setResourceid(resourcehistoryEXT.getId());
					informationResourceEXT.setInformationid(informationEXT.getId());
					informationResourceService.saveInformationResource(informationResourceEXT);
					informationEXT.setResourceid(resourcehistoryEXT.getId());
					informationEXT.setResourcepath(resourcehistoryEXT.getResourcepath());
				}
			}
			if (result > 0) {
				resultWrap.setSaveSuccess();
				resultWrap.setData(JSONObject.toJSON(informationEXT));
			} else {
				resultWrap.setSaveFailure();
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 查询发布的信息
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月1日 下午4:55:15
	 */
	@RequestMapping("/queryInformation")
	public void queryInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(informationEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(requestMap);
				informationEXT.setPage(page);
			}

			List<InformationEXT> list = informationService.queryInformation(informationEXT);
			list.parallelStream().forEach(tmpInformationEXT -> {
				// 插入品类名称
				if (ObjectUtil.isNotBlank(tmpInformationEXT.getCategoryid())) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", tmpInformationEXT.getCategoryid());
					ResultWrap result = supportCategoryService.queryCategoryInfoFromSupport(map);
					if (SupportUtil.supportResultItemIsNotEmpty(result.getData())) {
						JSONArray items = SupportUtil.supportGetItemData(result.getData());
						String categoryName = ((JSONObject) items.get(0)).getString("name");
						tmpInformationEXT.setCategoryname(categoryName);
					}
				}

				// 插入发布用户名称
				if (ObjectUtil.isNotBlank(tmpInformationEXT.getPubuserid())) {
					Map<String, Object> map = new HashMap<>();
					map.put("supportid", tmpInformationEXT.getPubuserid());
					ResultWrap tmpResultWrap = HTMLUtil.getDefaultWrap();
					supportUserService.queryUserInfoToSupportNew(map, tmpResultWrap);
					if (tmpResultWrap.getData() != null && !"".equals(tmpResultWrap.getData())) {
						JSONArray items = (JSONArray) tmpResultWrap.getData();
						if (!items.isEmpty()) {
							tmpInformationEXT.setPubusername(items.getJSONObject(0).getString("name"));
						}
					}
				}
				
				//插入发布用户所属组织名
				if (ObjectUtil.isNotBlank(tmpInformationEXT.getPuborgid())) {
					JSONObject orgJson = supportOrgService.getSupportTenantByIdNew(tmpInformationEXT.getPuborgid());
					if (orgJson != null && !orgJson.isEmpty()) {
						tmpInformationEXT.setPuborgname(orgJson.getString("name"));
					}
				}
			});

			// 是分页查询时设置分页信息
			if ((null != page)) {
				// 查询出总记录数量
				Integer totalCount = informationService.queryInformationCount(informationEXT);
				page.setTotal(totalCount);// 设置总的记录数量和总页数
				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}
			resultWrap.setData(list);
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 删除发布的信息
	 *
	 *               <pre>
	 *	删除单条记录的时候只传id
	 *  批量删除时需要传入idlist
	 *               </pre>
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月1日 下午5:43:30
	 */
	@RequestMapping("/deleteInformation")
	public void deleteInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);
			// 非空验证
			if (informationEXT.getId() == null
					&& (informationEXT.getIdlist() == null || informationEXT.getIdlist().isEmpty())) {
				resultWrap.setParamIsnotEmpty("{id,idlist}");
				return;
			}
			// 处需要删除的id
			if (informationEXT.getId() != null) {
				informationEXT.setIdlist(Arrays.asList(informationEXT.getId()));
			}
			informationService.deleteInformation(informationEXT);
			resultWrap.setDeleteSuccess();
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 更新信息发布
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月1日 下午5:50:15
	 */
	@RequestMapping("/updateInformation")
	public void updateInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);

			// 非空判断
			if (informationEXT.getId() == null) {
				resultWrap.setParamIsnotEmpty("id");
				return;
			}
			if (StringUtils.isEmpty(informationEXT.getTitle())) {
				resultWrap.setParamIsnotEmpty("title");
				return;
			}
			if (informationEXT.getCategoryid() == null) {
				resultWrap.setParamIsnotEmpty("categoryid");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}

			informationService.updateInformation(informationEXT);
			// 判断是否需要更新图片资源
			if (ObjectUtil.isNotBlank(informationEXT.getResourcepath())) {
				ResourcehistoryEXT resourcehistoryEXT = null;
				// 如果当前资源编号已经存在，则只更新它的资源路径，否则先看数据库中是否存在对应资源记录，如不存在，则新增资源记录，如存在，则更新资源编号
				if (ObjectUtil.isNotBlank(informationEXT.getResourceid())) {
					resourcehistoryEXT = commonService.addOrUpdateResource(informationEXT.getResourceid(),
							informationEXT.getResourcepath(), "SOP信息发布-概要信息封面图片");
				} else {
					InformationResourceEXT informationResourceEXT = new InformationResourceEXT();
					informationResourceEXT.setInformationid(informationEXT.getId());
					List<InformationResourceEXT> listResource = informationResourceService
							.queryInformationResource(informationResourceEXT);
					if (listResource.isEmpty()) {
						resourcehistoryEXT = commonService.addOrUpdateResource(null, informationEXT.getResourcepath(),
								"SOP信息发布-概要信息封面图片");
						if (ObjectUtil.isNotBlank(resourcehistoryEXT)) {
							informationResourceEXT.setResourceid(resourcehistoryEXT.getId());
							informationResourceEXT.setInformationid(informationEXT.getId());
							informationResourceService.saveInformationResource(informationResourceEXT);
							informationEXT.setResourceid(resourcehistoryEXT.getId());
							informationEXT.setResourcepath(resourcehistoryEXT.getResourcepath());
						}
					} else {
						commonService.addOrUpdateResource(listResource.get(0).getResourceid(),
								informationEXT.getResourcepath(), "SOP信息发布-概要信息封面图片");
					}
				}
			} else {
				InformationResourceEXT informationResourceEXT = new InformationResourceEXT();
				informationResourceEXT.setInformationid(informationEXT.getId());
				List<InformationResourceEXT> listResource = informationResourceService
						.queryInformationResource(informationResourceEXT);
				if (!listResource.isEmpty()) {
					informationResourceService.deleteInformationResource(listResource.get(0));
				}
			}
			resultWrap.setUpdateSuccess();
			resultWrap.setData(JSONObject.toJSON(informationEXT));
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 信息发布
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月5日 上午11:18:06
	 */
	@RequestMapping("/publishInformation")
	public void publishInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(informationEXT.getId())
					&& (informationEXT.getIdlist() == null || informationEXT.getIdlist().isEmpty())) {
				resultWrap.setParamIsnotEmpty("{id,idlist}");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getIspub())) {
				resultWrap.setParamIsnotEmpty("{ispub}");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getPubuserid())) {
				resultWrap.setParamIsnotEmpty("pubuserid");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getPuborgid())) {
				resultWrap.setParamIsnotEmpty("puborgid");
				return;
			}
			if (ObjectUtil.isBlank(informationEXT.getOrgid())) {
				resultWrap.setParamIsnotEmpty("orgid");
				return;
			}
			// 处理需要发布的id
			if (ObjectUtil.isNotBlank(informationEXT.getId())) {
				informationEXT.setIdlist(Arrays.asList(informationEXT.getId()));
			}
			// 如果是取消发布，则需要清空发布信息
			if (informationEXT.getIspub() == CommonConstantsUtil.IS_PUB_FALSE) {
				informationEXT.setPubtime(null);
				informationEXT.setPubuserid(null);
				informationEXT.setPuborgid(null);
			} else {
				informationEXT.setPubtime(new Date());
			}
			informationService.publishInformation(informationEXT);
			resultWrap.setUpdateSuccess();
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 复制信息发布记录
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyuqiang
	 * @date: 2018年2月6日 上午11:26:06
	 */
	@RequestMapping("/copyInformation")
	public void copyInformation(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 将客户端传过来的值绑定到实体对象上
			InformationEXT informationEXT = HTMLUtil.getMapToEntity(InformationEXT.class, requestMap);
			// 非空判断
			if (ObjectUtil.isBlank(informationEXT.getId())) {
				resultWrap.setParamIsnotEmpty("{id}");
				return;
			}
			// 复制记录
			int sourceInformationId = informationEXT.getId();
			List<InformationEXT> listInformation = informationService.queryInformation(informationEXT);
			if (listInformation.size() > 0) {
				informationEXT = listInformation.get(0);
			}
			informationService.copyInformation(informationEXT);
			// 如果被复制的记录是已经发布的，则新复制的记录需要修改为未发布
			if (informationEXT.getIspub() == CommonConstantsUtil.IS_PUB_TRUE) {
				informationEXT.setIspub(CommonConstantsUtil.IS_PUB_FALSE);
				informationEXT.setPubtime(null);
				informationEXT.setPubuserid(null);
				informationEXT.setPuborgid(null);
				informationEXT.setIdlist(Arrays.asList(informationEXT.getId()));
				informationService.publishInformation(informationEXT);
			}
			int targetInfomationId = informationEXT.getId();
			informationResourceService.copyInformationResource(sourceInformationId, targetInfomationId);
			informationContentService.copyInformationContent(sourceInformationId, targetInfomationId);
			resultWrap.setSaveSuccess();
			resultWrap.setData(JSONObject.toJSON(informationEXT));
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
}
