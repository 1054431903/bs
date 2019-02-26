package com.fwk.service.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.DictEXT;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.service.DictService;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.entity.OrgEXT;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.service.OrgService;
import com.fwk.service.user.service.UserService;

/**
 * 组织管理
 */
@Controller
@RequestMapping("/org")
public class OrgController {

	@Autowired
	private OrgService orgService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Autowired
	private SupportOrgService supportOrgService;

	@Autowired
	private SupportUserService supportUserService;
	
	@Autowired
	private DictService dictService;

	/**
	 * 根据当前登录人查询组织信息（组织所属人id）
	 */
	// @RequestMapping("/queryOrgByOperateid")
	public void queryOrgByOperateidBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (requestMap.get("loginorgid") != null) {
			if (!requestMap.get("loginorgid").equals("")) {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("id", requestMap.get("loginorgid"));
				List<OrgEXT> orgEXT = orgService.queryOrg(queryMap);
				resultWrap.setData(orgEXT);
			} else {
				resultWrap.setData(null);
			}
		} else {
			resultWrap.setParamIsnotEmpty("{当前登录人最后一次登录时的企业id（loginorgid）}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 根据当前登录人查询组织信息（组织所属人id）
	 */
	@RequestMapping("/queryOrgByOperateid")
	public void queryOrgByOperateid(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (requestMap.get("loginorgid") != null) { // 登录的组织id是支撑的组织id
			if (!requestMap.get("loginorgid").equals("")) {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("id", requestMap.get("loginorgid"));
				// List<OrgEXT> orgEXT = orgService.queryOrg(queryMap); 
				List<Map<String, Object>> orgList = supportOrgService.queryOrg(queryMap);
				// 拼接支撑没有的参数，id[组织id]，belonguserid[组织所属人]，permit[permiturl,营业执照]
				assembleOrg(orgList);
				resultWrap.setData(orgList);
			} else {
				resultWrap.setData(null);
			}
		} else {
			resultWrap.setParamIsnotEmpty("{当前登录人最后一次登录时的企业id（loginorgid）}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	private void assembleOrg(List<Map<String, Object>> orgList) {
		if (CollectionUtils.isEmpty(orgList)) {
			return;
		}
		for (Map<String, Object> orgMap : orgList) {
			Object supportid = orgMap.get("supportid");
			Object scale = orgMap.get("scale");
			Object nature = orgMap.get("nature");
			OrgEXT orgEXT = orgService.queryOrgBySupportId(supportid);
			if (orgEXT != null) {// 有就组装
				orgMap.put("id", orgEXT.getId());
				orgMap.put("belonguserid", orgEXT.getBelonguserid());
				orgMap.put("permit", orgEXT.getPermit() == null ? "" : orgEXT.getPermit());
				orgMap.put("logo", orgEXT.getLogo() == null ? "" : orgEXT.getLogo());
				// orgMap.put("naturename", orgEXT.getNaturename() == null ? "" : orgEXT.getNaturename());
				// orgMap.put("scalename", orgEXT.getScalename() == null ? "" : orgEXT.getScalename());
			} else {// 没有就新增
				orgService.saveOrg(orgMap);
				// 去查
				OrgEXT addOrgEXT = orgService.queryOrgBySupportId(supportid);
				if (addOrgEXT != null) {
					orgMap.put("id", addOrgEXT.getId());
				}
				// orgMap.put("belonguserid", "");
				orgMap.put("permit", "");
				orgMap.put("logo", "");
				// orgMap.put("naturename", "");
				// orgMap.put("scalename", "");
			}
			if (ObjectUtil.isNotBlank(scale)) {// 人员的规模
				// 将客户端传过来的值绑定到实体对象上
				Map<String,Object> map = new HashMap<>();
				map.put("dicttype", "org-scale");
				map.put("dictvalue", scale);
				DictEXT paramDictEXT = HTMLUtil.getMapToEntity(DictEXT.class, map);
				try {
					List<DictEXT> dictList = dictService.queryList(paramDictEXT);
					if(!CollectionUtils.isEmpty(dictList)){
						orgMap.put("scalename", dictList.get(0).getDictname());
					}
					
				} catch (Exception e) {
					
				}
				
			}
			if (ObjectUtil.isNotBlank(nature)) {// 公司的性质
				// 将客户端传过来的值绑定到实体对象上
				Map<String,Object> map = new HashMap<>();
				map.put("dicttype", "org-nature");
				map.put("dictvalue", nature);
				DictEXT paramDictEXT = HTMLUtil.getMapToEntity(DictEXT.class, map);
				try {
					List<DictEXT> dictList = dictService.queryList(paramDictEXT);
					if(!CollectionUtils.isEmpty(dictList)){
						orgMap.put("naturename", dictList.get(0).getDictname());
					}
					
				} catch (Exception e) {
					
				}
				
			}

		}
	}

	/**
	 * 获取登录人的全部组织
	 */
	// @RequestMapping("/queryOrgAllByOperateid")
	public void queryOrgAllByOperateidBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (requestMap.get("userid") != null) {
			List<OrgEXT> orgEXTs = orgService.queryOrgAllByOperateid(requestMap);
			resultWrap.setData(orgEXTs);
		} else {
			resultWrap.setParamIsnotEmpty("{当前登录人的编号}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取登录人的全部组织
	 */
	@RequestMapping("/queryOrgAllByOperateid")
	public void queryOrgAllByOperateid(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<Map<String, Object>> orgList = null;
		if (requestMap.get("userid") != null) { // 登录的userid是农事的用户id
			// List<OrgEXT> orgEXTs = orgService.queryOrgAllByOperateid(requestMap);
			// 现将农事的用户userid转换成支撑的id
			requestMap.put("id", requestMap.get("userid"));
			List<UserEXT> userList = userService.queryUser(requestMap);
			if (!CollectionUtils.isEmpty(userList)) {
				UserEXT userEXT = userList.get(0);
				String supportid = userEXT.getSupportid();
				if (StringUtils.isNotBlank(supportid)) {
					requestMap.put("id", supportid);
					orgList = supportOrgService.queryOrgAllByOperateid(requestMap);
					// 拼接支撑没有的参数，id[组织id]，belonguserid[组织所属人]，permit[permiturl,营业执照]
					assembleOrg(orgList);
				}

			}
			resultWrap.setData(orgList.stream().distinct().collect(Collectors.toList()));
		} else {
			resultWrap.setParamIsnotEmpty("{当前登录人的编号}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 查询组织信息
	 */
	@RequestMapping("/queryOrg")
	public void queryOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<OrgEXT> orgEXTs = queryOrg(requestMap);
		if (ObjectUtil.isNotBlank(orgEXTs)) {
			resultWrap.setData(orgEXTs);
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 执行组织查询
	 */
	private List<OrgEXT> queryOrg(Map<String, Object> requestMap) {
		return orgService.queryOrg(requestMap);
	}

	/**
	 * 保存组织信息
	 */
	// @RequestMapping("/saveOrg")
	public void saveOrgBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("name"))) {
			Map<String, Object> queryNameMap = new HashMap<String, Object>();
			queryNameMap.put("name", requestMap.get("name"));
			if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
				queryNameMap.put("noid", requestMap.get("id"));
			}
			if (orgService.queryOrg(queryNameMap).size() > 0) {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-name-repeat"));
			} else {
				try {
					// 企业logo图片
					ResourcehistoryEXT resourcehistoryEXT1 = commonService.addOrUpdateResource(null, requestMap.get("logourl") + "",
							"企业logo");
					if (ObjectUtil.isNotBlank(resourcehistoryEXT1)) {
						requestMap.put("logo", resourcehistoryEXT1.getId());
					}
					// 企业permit营业执照图片
					ResourcehistoryEXT resourcehistoryEXT2 = commonService.addOrUpdateResource(null, requestMap.get("permiturl") + "",
							"营业执照");
					if (ObjectUtil.isNotBlank(resourcehistoryEXT2)) {
						requestMap.put("permit", resourcehistoryEXT2.getId());
					}
					int result = 0;
					if (ObjectUtil.isNotBlank(requestMap.get("id"))) {// id不为空，修改
						result = orgService.updateOrg(requestMap);
						if (result >= 1) {
							resultWrap.setUpdateSuccess();
						} else {
							resultWrap.setUpdateFailure();
						}
					} else {// 添加
						result = orgService.saveOrg(requestMap);
						if (result >= 1) {
							// 添加组织与用户关系
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("orgid", requestMap.get("id"));
							map.put("userid", requestMap.get("userid"));
							userService.insertUserOrgDepart(map);
							resultWrap.setSaveSuccess();
						} else {
							resultWrap.setSaveFailure();
						}
					}
				} catch (Exception e) {
					resultWrap.setException(e.getMessage());
				}
			}
		} else {
			resultWrap.setParamIsnotEmpty("{企业名称}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 保存组织信息
	 */
	@RequestMapping("/saveOrg")
	public void saveOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if (ObjectUtil.isBlank(requestMap.get("name"))) {
				resultWrap.setParamIsnotEmpty("{企业名称}");
				return;
			}
			// 企业logo图片
			ResourcehistoryEXT resourcehistoryEXT1 = commonService.addOrUpdateResource(null, requestMap.get("logourl") + "", "企业logo");
			if (ObjectUtil.isNotBlank(resourcehistoryEXT1)) {
				requestMap.put("logo", resourcehistoryEXT1.getResourcepath());
			}
			// 企业permit营业执照图片
			ResourcehistoryEXT resourcehistoryEXT2 = commonService.addOrUpdateResource(null, requestMap.get("permiturl") + "", "营业执照");
			if (ObjectUtil.isNotBlank(resourcehistoryEXT2)) {
				requestMap.put("permit", resourcehistoryEXT2.getResourcepath());
			}
			if (ObjectUtil.isNotBlank(requestMap.get("scale"))) {
				requestMap.put("scale", Integer.valueOf(requestMap.get("scale").toString()));
			}
			if (ObjectUtil.isNotBlank(requestMap.get("nature"))) {
				requestMap.put("nature", Integer.valueOf(requestMap.get("nature").toString()));
			}
			Object eastablishtime = requestMap.get("eastablishtime");
			if (ObjectUtil.isNotBlank(requestMap.get("eastablishtime"))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse((String) requestMap.get("eastablishtime"));
				Long time = date.getTime();
				requestMap.put("eastablishtime", time);
			}
			String supportid = null;
			if (ObjectUtil.isBlank(requestMap.get("id"))) {
				//验证名称唯一
				if (SupportUtil.supportResultItemIsNotEmpty(supportOrgService.queryOrgByName(requestMap.get("name").toString()))) {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-name-repeat"));
					return;
				} 
				requestMap.put("type", CommonConstantsUtil.TENANT_TYPE_ORG);
				ResultWrap resultObj = supportOrgService.addOrg(requestMap);
				if (ObjectUtil.isBlank(resultObj) || resultObj.getStatusCode() != HttpStatus.SC_OK) {
					resultWrap.setSaveFailure();
					return;
				}
				// 设置支撑的ID
				JSONObject dataJson = JSONObject.parseObject(resultObj.getData().toString());
				supportid = SupportUtil.supportGetJsonOid(dataJson);
				requestMap.put("supportid", supportid);
				if (ObjectUtil.isNotBlank(resourcehistoryEXT1)) {
					requestMap.put("logo", resourcehistoryEXT1.getId());
				}
				if (ObjectUtil.isNotBlank(resourcehistoryEXT2)) {
					requestMap.put("permit", resourcehistoryEXT2.getId());
				}
				requestMap.put("eastablishtime", eastablishtime);
				// 新增组织，跟当前用户和组织进行数据绑定
				String staffLastUpdateTime = "";
				if (ObjectUtil.isNotBlank(requestMap.get("userid"))) {
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("id", requestMap.get("userid"));
					UserEXT userEXT = new UserEXT();
					List<UserEXT> userEXTs = userService.queryUserDetail(userMap);
					if (userEXTs.size() >= 1) {
						userEXT = userEXTs.get(0);
						Object obj = supportOrgService.addUserToOrg(supportid, userEXT.getSupportid());									
						staffLastUpdateTime = SupportUtil.getSupportUpdateDate(obj);
					}
				}
				orgService.saveOrg(requestMap);
				resultWrap.setSaveSuccess();
				Map<String, Object> orgMap = new HashMap<String, Object>();
				orgMap.put("orgid", supportid);
				orgMap.put("staffLastUpdateTime", staffLastUpdateTime);
				resultWrap.setData(JSON.toJSON(orgMap));
			}else { // id不为空，修改
				//验证当前修改的组织名称是否已经存在
				Object data = supportOrgService.queryOrgByName(requestMap.get("name").toString());
				if (SupportUtil.supportResultItemIsNotEmpty(data)) {
					JSONArray jsonArray = SupportUtil.supportGetItemData(data);
					String supportTenantId = jsonArray.getJSONObject(0).getJSONObject("_id").getString("$oid");
					if (!Objects.equals(supportTenantId, requestMap.get("orgid"))) {
						resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-name-repeat"));
						return;
					}
				}
				Map<String, Object> queryDept = new HashMap<String, Object>();
				Object id = requestMap.get("id");// 根据农事的Id查询支撑的Id
				queryDept.put("id", id);
				List<OrgEXT> deptList = orgService.queryOrg(queryDept);
				if (!CollectionUtils.isEmpty(deptList)) {
					OrgEXT orgEXT = deptList.get(0);
					supportid = orgEXT.getSupportid();
					requestMap.put("id", supportid);// 换成支撑的Id进行更新
					Object resultObj = supportOrgService.updateOrg(requestMap);
					if (ObjectUtil.isNotBlank(resultObj) && String.valueOf(resultObj).indexOf("请求失败") == -1) {
						// requestMap.put("supportid", resultObj);
						requestMap.put("id", id);// 还原为农事的id
						if (ObjectUtil.isNotBlank(resourcehistoryEXT1)) {
							requestMap.put("logo", resourcehistoryEXT1.getId());
						}
						if (ObjectUtil.isNotBlank(resourcehistoryEXT2)) {
							requestMap.put("permit", resourcehistoryEXT2.getId());
						}
						requestMap.put("eastablishtime", eastablishtime);
						orgService.updateOrg(requestMap);
						resultWrap.setUpdateSuccess();
					} else {
						resultWrap.setUpdateFailure();
					}
				}
			}
		} catch (Exception e) {
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 解散/删除 组织信息
	 */
	@RequestMapping("/deleteOrg")
	public void deleteOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			requestMap.put("delstate", 0);
			int result = orgService.updateOrg(requestMap);
			if (result >= 1) {
				resultWrap.setActSuccess();
			} else {
				resultWrap.setActFailure();
			}
		} else {
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 移交组织
	 */
	@RequestMapping("/turnOrg")
	public void turnOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("userid")) && ObjectUtil.isNotBlank(requestMap.get("orgid"))
				&& ObjectUtil.isNotBlank(requestMap.get("password")) && ObjectUtil.isNotBlank(requestMap.get("loginuserid"))) {
			// 验证用户的id与密码是否匹配
			Map<String, Object> checkUserPassword = new HashMap<String, Object>();
			checkUserPassword.put("id", requestMap.get("loginuserid"));
			// String pwdCode = EncoderUtil.transStrToMd5(logger, requestMap.get("password").toString());
			String pwdCode = supportUserService.encryPassword(requestMap.get("password")) + "";
			checkUserPassword.put("password", pwdCode);
			List<UserEXT> userEXTs = userService.queryUser(checkUserPassword);
			if (userEXTs != null && userEXTs.size() > 0) {// 密码正确，通过验证，执行组织移交
				// 判断转交人id是否是企业负责人
				Map<String, Object> queryOrgMap = new HashMap<String, Object>();
				queryOrgMap.put("belonguserid", requestMap.get("loginuserid"));
				queryOrgMap.put("id", requestMap.get("orgid"));
				if (orgService.queryOrg(queryOrgMap).size() >= 1) {// 转交人是企业负责人，执行转交企业
					// 转交企业
					Map<String, Object> turnOrgMap = new HashMap<String, Object>();
					turnOrgMap.put("belonguserid", requestMap.get("userid"));
					turnOrgMap.put("id", requestMap.get("orgid"));
					int result = orgService.updateOrg(turnOrgMap);
					if (result >= 1) {
						resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-org-turn-success"));
					} else {
						resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-turn-failure"));
					}
				} else {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-turn-not-belonguserid-failure"));
				}
			} else {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-turn-password-error"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("{userid,orgid,password}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

}
