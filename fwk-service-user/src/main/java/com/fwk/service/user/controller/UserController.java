package com.fwk.service.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.ObjectId;
import com.fwk.service.common.support.util.SupportOrgDepartUtil;
import com.fwk.service.common.support.util.SupportUserPasswordUtil;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.bo.UserSupportBO;
import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.entity.OrgDepartUserDefine;
import com.fwk.service.user.entity.OrgEXT;
import com.fwk.service.user.entity.PermissionEXT;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.entity.UserOrgDepartEXT;
import com.fwk.service.user.service.DepartmentService;
import com.fwk.service.user.service.OrgService;
import com.fwk.service.user.service.PermissionService;
import com.fwk.service.user.service.RoleService;
import com.fwk.service.user.service.UserService;
import com.fwk.service.user.service.UserSupportService;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/user")
public class UserController {

	/** 日志记录 **/
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserSupportService userSupportService;

	@Autowired
	private SupportUserService supportUserService;

	@Autowired
	private UserSupportBO userSupportBO;

	@Autowired
	private SupportOrgService supportOrgService;

	@Autowired
	private SupportService supportService;

	@Autowired
	private SupportOrgDepartUtil supportOrgDepartUtil;

	/**
	 * @Description: 登录(313) 重构登录服务
	 *
	 * @param request
	 * @param response
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyq
	 * @date: 2018年6月21日 下午4:27:20
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			// 参数验证
			if (ObjectUtil.isBlank(requestMap.get("account"))) {
				resultWrap.setParamIsnotEmpty("{account}");
				return;
			}
			if (ObjectUtil.isBlank(requestMap.get("password"))) {
				resultWrap.setParamIsnotEmpty("{password}");
				return;
			}
			// 是否请求支撑平台
			if (!supportService.isUserSupport) {
				UserEXT userEXT = userService.login(requestMap);
				if (ObjectUtil.isBlank(userEXT)) {
					// 验证错误，登录失败
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
					resultWrap.setData(null);
					return;
				}
				// 验证密码
				String pwdCode = supportUserService.encryPassword(requestMap.get("password")) + "";
				// 密码验证不通过,登录失败
				if (!SupportUserPasswordUtil.passwordMatches(pwdCode, userEXT.getPassword())) {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
					resultWrap.setData(null);
					return;
				}
				// 登录成功后设置默认登录企业,如果为空则设置默认登录的企业
				if (ObjectUtil.isBlank(userEXT.getLoginorgid())) {
					setLoginOrgId(userEXT, resultWrap);
				} else {
					Map<String, Object> orgMap = new HashMap<String, Object>();
					orgMap.put("id", userEXT.getLoginorgid());
					if (orgService.queryOrg(orgMap).size() <= 0) {
						setLoginOrgId(userEXT, resultWrap);
					}
				}
				userEXT.setPassword("");
				resultWrap.setData(userEXT);
				return;
			}
			// 请求支撑数据
			supportUserService.supportUserLogin(requestMap, resultWrap);
			if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
				resultWrap.error("请求支撑数据出错！");
				resultWrap.setData(null);
				return;
			}
			JSONArray ja = (JSONArray) resultWrap.getData();
			if (ja.isEmpty()) {
				resultWrap.error("用户信息不存在！");
				resultWrap.setData(null);
				return;
			}
			UserEXT userEXT = new UserEXT();
			List<UserEXT> userEXTs = userService
					.queryUserDetailBySupportId(ja.getJSONObject(0).getJSONObject("_id").getString("$oid"));
			JSONObject resultJO = ja.getJSONObject(0);
			if (userEXTs.isEmpty()) {
				System.out.println("不存在该用户");
				userSupportService.transJsonToUserEXT(resultJO, userEXT);
				userService.insertUserSupport(userEXT);
			} else {
				System.out.println("存在该用户");
				userEXT = userEXTs.get(0);
				userSupportService.transJsonToUserEXT(resultJO, userEXT);
				// 设置上次登录时间
				userSupportService.transJsonToUserEXTEdittime(resultJO, userEXT);
			}
			// 更新组织与用户关系
			if (CollectionUtils.isEmpty(resultJO.getJSONArray("tenants"))) {
				userEXT.setLoginorgid("");
				resultWrap.setData(userEXT);
				return;
			}
			// 设置登录用户的登录企业id
			JSONArray tenantsJA = resultJO.getJSONArray("tenants");
			List<String> tenantsIds = tenantsJA.stream().map(m -> {
				return ((JSONObject) m).getString("$oid");
			}).collect(Collectors.toList());
			List<String> oids = supportOrgDepartUtil.batchGetParentTenants(tenantsIds);
			// List<String> oids = new ArrayList<>();
			// for (Object obj : tenantsJA) {
			// String oid = ((JSONObject) obj).getString("$oid");
			// // 处理用户挂在部门下的情况
			// String parentOid = supportOrgDepartUtil.supportGetTenantParent(oid);
			// oids.add(parentOid);
			// }
			if (ObjectUtil.isBlank(userEXT.getLoginorgid())) {
				userEXT.setLoginorgid(oids.get(0));
			}
			if (!oids.contains(userEXT.getLoginorgid())) {
				userEXT.setLoginorgid(oids.get(0));
			}
			// 设置用户所属部门ID及名称
			// for (String curTenantId : tenantsIds) {
			// // 处理用户挂在部门下的情况
			// String parentOrgid =
			// supportOrgDepartUtil.supportGetTenantParent(curTenantId);
			// if (parentOrgid.equals(userEXT.getLoginorgid())) {
			// userEXT.setDepartmentid(curTenantId);
			// departmentName =
			// supportOrgService.getSupportTenantById(curTenantId).getString("name");
			// userEXT.setDepartmentname(departmentName);
			// break;
			// }
			// }
			String departmentName = "";
			if (tenantsIds.contains(userEXT.getLoginorgid())) {
				userEXT.setDepartmentid(userEXT.getLoginorgid());
				departmentName = supportUserService.getOrgName(userEXT.getLoginorgid());
				userEXT.setDepartmentname(departmentName);
			} else {
				for (String curTenantId : tenantsIds) {
					// 处理用户挂在部门下的情况
					String parentOrgid = supportOrgDepartUtil.supportGetTenantParent(curTenantId);
					if (parentOrgid.equals(userEXT.getLoginorgid())) {
						userEXT.setDepartmentid(curTenantId);
						departmentName = supportOrgService.getSupportTenantById(curTenantId).getString("name");
						userEXT.setDepartmentname(departmentName);
						break;
					}
				}
			}
			// 更新最后登录组织id到数据库
			if (ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
				Map<String, Object> userUpdateMap = new HashMap<String, Object>();
				userUpdateMap.put("loginorgid", userEXT.getLoginorgid());
				userUpdateMap.put("id", userEXT.getId());
				userService.updateUser(userUpdateMap);
			}
			// 设置登录企业名称
			// userEXT.setOrgName(supportUserService.getOrgName(userEXT.getLoginorgid()));
			if (userEXT.getLoginorgid().equals(userEXT.getDepartmentid())) {
				userEXT.setOrgName(departmentName);
			} else {
				userEXT.setOrgName(supportUserService.getOrgName(userEXT.getLoginorgid()));
			}
			resultWrap.setData(userEXT);
			//记录当前用户登录的组织信息（2018年11月06日，周立山做消息推送时需要用到用户登录的组织信息，因此添加该方法。）
			saveLoginStatusToFwk(userEXT);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setException(e.getMessage());
			resultWrap.setData(null);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	@SuppressWarnings("serial")
	private void saveLoginStatusToFwk(UserEXT userEXT) {
		// @formatter:off
		Map<String, Object> map = new LinkedHashMap<String, Object>() {{
			put("filter", new LinkedHashMap<String, Object>() {{
				put("_id", new LinkedHashMap<String, Object>() {{
					put("$oid", userEXT.getSupportid());
				}});
			}});
			put("update", new LinkedHashMap<String, Object>() {{
				put("$set", new LinkedHashMap<String, Object>() {{
					put("loginTenant", new LinkedHashMap<String, Object>() {{
						put("$oid", userEXT.getLoginorgid());
					}});
					put("loginTime", new Date().getTime());
					put("createdOn",new Date().getTime());
					put("updatedOn",new Date().getTime());
				}});
			}});
			put("upsert", true);
		}};
		// @formatter:on
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setDataJson(JSONObject.parseObject(JSONObject.toJSONString(map)));
		supportService.putRequest(AllJsonRequestUrl.Tenant_staff, supportRequestParam, true);
	}

	/**
	 * 登录(313)
	 */
	@RequestMapping("/loginBak")
	public void loginBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("account")) && ObjectUtil.isNotBlank(requestMap.get("password"))) {
			// 请求支撑平台
			if (supportService.isUserSupport) {// 返回true,则调用支撑平台
				// 请求支撑数据
				supportUserService.supportUserLogin(requestMap, resultWrap);
				// 登录成功
				if (resultWrap.getFlag() == 1) {
					if (ObjectUtil.isNotBlank(resultWrap.getData())) {
						JSONArray ja = JSONArray.parseArray(resultWrap.getData().toString());
						if (ja.size() >= 1) {
							UserEXT userEXT = new UserEXT();
							Map<String, Object> supportUserMap = new HashMap<String, Object>();
							supportUserMap.put("supportid",
									JSONObject
											.parseObject(JSONObject.parseObject(ja.get(0).toString()).getString("_id"))
											.getString("$oid"));
							List<UserEXT> userEXTs = userService.queryUser(supportUserMap);
							JSONObject resultJO = JSONObject.parseObject(ja.get(0).toString());
							if (userEXTs.size() >= 1) {// t_u_users 存在该用户
								System.out.println("存在该用户");
								userEXT = userEXTs.get(0);
								userSupportService.transJsonToUserEXT(resultJO, userEXT);
								// 设置上次登录时间
								userSupportService.transJsonToUserEXTEdittime(resultJO, userEXT);
							} else {// t_u_users 不存在该用户
								System.out.println("不存在该用户");
								userSupportService.transJsonToUserEXT(resultJO, userEXT);
								userService.insertUserSupport(userEXT);
							}

							// 更新组织与用户关系
							if (ObjectUtil.isNotBlank(resultJO) && ObjectUtil.isNotBlank(resultJO.get("tenants"))) {
								if (JSONArray.parseArray(resultJO.get("tenants").toString()).size() >= 1) {
									// 设置登录用户的登录企业id
									JSONArray tenantsJA = JSONArray.parseArray(resultJO.get("tenants").toString());
									List<String> oids = new ArrayList<String>();
									for (int i = 0; i < tenantsJA.size(); i++) {
										JSONObject jo = JSONObject.parseObject(tenantsJA.get(i).toString());
										oids.add(supportOrgDepartUtil.supportGetTenantParent(jo.getString("$oid")));
									}
									if (oids.size() >= 1) {
										if (ObjectUtil.isNotBlank(userEXT)
												&& ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
											if (!oids.contains(userEXT.getLoginorgid())) {
												// 设置登录企业id
												if (oids.size() >= 1)
													userEXT.setLoginorgid(oids.get(0));
											}
										} else {
											// 设置登录企业id
											if (oids.size() >= 1)
												userEXT.setLoginorgid(oids.get(0));
										}
									}

									for (int i = 0; i < tenantsJA.size(); i++) {
										JSONObject jo = JSONObject.parseObject(tenantsJA.get(i).toString());
										String orgid = supportOrgDepartUtil
												.supportGetTenantParent(jo.getString("$oid"));
										if (ObjectUtil.isNotBlank(orgid)
												&& ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
											if (orgid.equals(userEXT.getLoginorgid())) {
												userEXT.setDepartmentid(jo.getString("$oid"));
												userEXT.setDepartmentname(supportOrgService
														.getSupportTenantById(jo.getString("$oid")).getString("name"));
												break;
											}
										}
									}
									if (ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
										Map<String, Object> userUpdateMap = new HashMap<String, Object>();
										userUpdateMap.put("loginorgid", userEXT.getLoginorgid());
										userUpdateMap.put("id", userEXT.getId());
										userService.updateUser(userUpdateMap);
									}
									// userEXT.setDepartmentname(departmentname);
									// 设置登录企业名称
									userEXT.setOrgName(supportUserService.getOrgName(userEXT.getLoginorgid()));
								} else {
									userEXT.setLoginorgid("");
								}
							} else {
								userEXT.setLoginorgid("");
							}
							resultWrap.setData(userEXT);
						}
					}
				}

				// resultWrap.setData(JSONObject.parse(SupportUserController.queryUserInfoToSupport(requestMap)+""));
				// resultWrap.setData(SupportUserController.insertUserInfoToSupport(requestMap));
				// resultWrap.setData(SupportUserController.editUserInfoToSupport(requestMap));
				// resultWrap.setData(SupportUserController.deleteUserInfoToSupport(requestMap));

				// positionService.synchPositionInfoToMysql();//同步行政区划数据
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			UserEXT userEXT = userService.login(requestMap);
			if (ObjectUtil.isNotBlank(userEXT)) {// 手机或邮箱验证成功
				// 验证密码
				String pwdCode = supportUserService.encryPassword(requestMap.get("password")) + "";
				// 密码验证通过
				if (SupportUserPasswordUtil.passwordMatches(requestMap.get("password").toString(),
						userEXT.getPassword())) {
					// 登录成功后设置默认登录企业
					if (ObjectUtil.isBlank(userEXT.getLoginorgid())) {// 如果为空则设置默认登录的企业
						setLoginOrgId(userEXT, resultWrap);
					} else {
						Map<String, Object> orgMap = new HashMap<String, Object>();
						orgMap.put("id", userEXT.getLoginorgid());
						if (orgService.queryOrg(orgMap).size() <= 0) {
							setLoginOrgId(userEXT, resultWrap);
						}
					}
					userEXT.setPassword("");
					resultWrap.setData(userEXT);
				} else {// 密码验证不通过,登录失败
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
				}
			} else {// 验证错误，登录失败
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("{account,password}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 登录(313)
	 */
	@RequestMapping("/loginsupport")
	public void loginsupport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("account"))) {
			// 请求支撑平台
			if (supportService.isUserSupport) {// 返回true,则调用支撑平台
				// 请求支撑数据
				supportUserService.supportUserLogin(requestMap, resultWrap);
				// 登录成功
				if (resultWrap.getFlag() == 1) {
					if (ObjectUtil.isNotBlank(resultWrap.getData())) {
						JSONArray ja = JSONArray.parseArray(resultWrap.getData().toString());
						if (ja.size() >= 1) {
							UserEXT userEXT = new UserEXT();
							Map<String, Object> supportUserMap = new HashMap<String, Object>();
							supportUserMap.put("supportid",
									JSONObject
											.parseObject(JSONObject.parseObject(ja.get(0).toString()).getString("_id"))
											.getString("$oid"));
							List<UserEXT> userEXTs = userService.queryUser(supportUserMap);
							JSONObject resultJO = JSONObject.parseObject(ja.get(0).toString());
							if (userEXTs.size() >= 1) {// t_u_users 存在该用户
								System.out.println("存在该用户");
								userEXT = userEXTs.get(0);
								userSupportService.transJsonToUserEXT(resultJO, userEXT);
								// 设置上次登录时间
								userSupportService.transJsonToUserEXTEdittime(resultJO, userEXT);
							} else {// t_u_users 不存在该用户
								System.out.println("不存在该用户");
								userSupportService.transJsonToUserEXT(resultJO, userEXT);
								userService.insertUserSupport(userEXT);
							}

							// 更新组织与用户关系
							if (ObjectUtil.isNotBlank(resultJO) && ObjectUtil.isNotBlank(resultJO.get("tenants"))) {
								if (JSONArray.parseArray(resultJO.get("tenants").toString()).size() >= 1) {
									// 设置登录用户的登录企业id
									JSONArray tenantsJA = JSONArray.parseArray(resultJO.get("tenants").toString());
									List<String> oids = new ArrayList<String>();
									for (int i = 0; i < tenantsJA.size(); i++) {
										JSONObject jo = JSONObject.parseObject(tenantsJA.get(i).toString());
										oids.add(supportOrgDepartUtil.supportGetTenantParent(jo.getString("$oid")));
									}
									if (oids.size() >= 1) {
										if (ObjectUtil.isNotBlank(userEXT)
												&& ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
											if (!oids.contains(userEXT.getLoginorgid())) {
												// 设置登录企业id
												if (oids.size() >= 1)
													userEXT.setLoginorgid(oids.get(0));
											}
										} else {
											// 设置登录企业id
											if (oids.size() >= 1)
												userEXT.setLoginorgid(oids.get(0));
										}
									}

									for (int i = 0; i < tenantsJA.size(); i++) {
										JSONObject jo = JSONObject.parseObject(tenantsJA.get(i).toString());
										String orgid = supportOrgDepartUtil
												.supportGetTenantParent(jo.getString("$oid"));
										if (ObjectUtil.isNotBlank(orgid)
												&& ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
											if (orgid.equals(userEXT.getLoginorgid())) {
												userEXT.setDepartmentid(jo.getString("$oid"));
												userEXT.setDepartmentname(supportOrgService
														.getSupportTenantById(jo.getString("$oid")).getString("name"));
												break;
											}
										}
									}
									if (ObjectUtil.isNotBlank(userEXT.getLoginorgid())) {
										Map<String, Object> userUpdateMap = new HashMap<String, Object>();
										userUpdateMap.put("loginorgid", userEXT.getLoginorgid());
										userUpdateMap.put("id", userEXT.getId());
										userService.updateUser(userUpdateMap);
									}
									// userEXT.setDepartmentname(departmentname);
									// 设置登录企业名称
									userEXT.setOrgName(supportUserService.getOrgName(userEXT.getLoginorgid()));
								} else {
									userEXT.setLoginorgid("");
								}
							} else {
								userEXT.setLoginorgid("");
							}
							resultWrap.setData(userEXT);
						}
					}
				}

				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			UserEXT userEXT = userService.login(requestMap);
			if (ObjectUtil.isNotBlank(userEXT)) {// 手机或邮箱验证成功
				// 验证密码
				String pwdCode = supportUserService.encryPassword(requestMap.get("password")) + "";
				// 密码验证通过
				if (SupportUserPasswordUtil.passwordMatches(requestMap.get("password").toString(),
						userEXT.getPassword())) {
					// 登录成功后设置默认登录企业
					if (ObjectUtil.isBlank(userEXT.getLoginorgid())) {// 如果为空则设置默认登录的企业
						setLoginOrgId(userEXT, resultWrap);
					} else {
						Map<String, Object> orgMap = new HashMap<String, Object>();
						orgMap.put("id", userEXT.getLoginorgid());
						if (orgService.queryOrg(orgMap).size() <= 0) {
							setLoginOrgId(userEXT, resultWrap);
						}
					}
					userEXT.setPassword("");
					resultWrap.setData(userEXT);
				} else {// 密码验证不通过,登录失败
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
				}
			} else {// 验证错误，登录失败
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-failure"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("{account,password}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 设置登录者的默认组织编号
	 */
	public void setLoginOrgId(UserEXT userEXT, ResultWrap resultWrap) {
		Map<String, Object> queryLoginOrgMap = new HashMap<String, Object>();
		queryLoginOrgMap.put("operateid", userEXT.getId());
		List<OrgEXT> orgEXTs = orgService.queryOrgByOperateid(queryLoginOrgMap);
		Map<String, Object> updateUserMap = new HashMap<String, Object>();
		updateUserMap.put("id", userEXT.getId());
		if (ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() > 0) {
			userEXT.setLoginorgid(orgEXTs.get(0).getId().toString());
			// 设置用户的默认登录企业编号
			updateUserMap.put("loginorgid", orgEXTs.get(0).getId());
		} else {
			userEXT.setLoginorgid(null);
			updateUserMap.put("loginorgid", "");
		}
		int result = userService.updateUser(updateUserMap);
		if (result <= 0) {
			resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-login-org-default-failure"));
		}
	}

	/**
	 * 注册(313)
	 */
	@RequestMapping("/regist")
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("password")) && ((ObjectUtil.isNotBlank(requestMap.get("email"))
				|| ObjectUtil.isNotBlank(requestMap.get("phone"))))) {
			// 是否使用支撑平台
			if (supportService.isUserSupport) {
				// 验证手机号码是否存在
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("phone", requestMap.get("phone"));
				supportUserService.queryUserInfoToSupport(userMap, resultWrap);
				if (!ObjectUtil.isNotBlank(resultWrap.getData())) {// 不存在用户，继续实现注册功能
					ResultWrap resultWrap2 = supportUserService.insertUserInfoToSupport(requestMap);
					if (resultWrap2.getFlag() >= 1) {// 支撑数据注册成功
						// 将数据添加至平台mysql内
						if (ObjectUtil.isNotBlank(resultWrap2.getData())) {
							JSONObject dataJson = JSONObject.parseObject(resultWrap2.getData().toString());
							if (ObjectUtil.isNotBlank(dataJson.getString("_id"))) {
								userSupportService.supportInsertToUser(SupportUtil.supportGetJsonOid(dataJson));
								resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-registe-success"));
							}
						}
					} else {
						resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-failure"));
					}
				} else {// 存在该用户，注册失败
					resultWrap.setData(null);
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-username-exist"));
				}
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			if (checkUsername(requestMap) <= 0) {
				requestMap.put("password", supportUserService.encryPassword(requestMap.get("password")));
				int result = registe(requestMap);
				if (result >= 1) {
					resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-registe-success"));
				} else {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-failure"));
				}
			} else {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-username-exist"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("用户名、密码");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 注册用户(实现)
	 */
	private int registe(Map<String, Object> requestMap) {
		return userService.registe(requestMap);
	}

	/**
	 * 验证用户名是否存在(手机、邮箱)
	 */
	private int checkUsername(Map<String, Object> requestMap) {
		Map<String, Object> requestMap1 = new HashMap<String, Object>();
		if (ObjectUtil.isNotBlank(requestMap.get("email"))) {
			requestMap1.put("email", requestMap.get("email"));
		}
		if (ObjectUtil.isNotBlank(requestMap.get("phone"))) {
			requestMap1.put("phone", requestMap.get("phone"));
		}
		return userService.queryCheckUserExist(requestMap1);
	}

	/**
	 * 查询部门或组织下的所有用户(313)
	 */
	@RequestMapping("/queryUserByOrgDepartment")
	public void queryUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("orgid"))) {
			List<JSONObject> listJo = new ArrayList<JSONObject>();
			if (ObjectUtil.isNotBlank(requestMap.get("departmentid"))) {// 获取该部门下的所有部门id
				// 如果是平台id，则查询supportid
				// Map<String,Object> queryDepartmentDetailMap = new HashMap<String,Object>();
				// queryDepartmentDetailMap.put("id", requestMap.get("departmentid"));
				// List<DepartmentEXT> departmentEXT =
				// departmentService.queryDepartment(queryDepartmentDetailMap);
				// String departmendid = "";
				// if(ObjectUtil.isNotBlank(departmentEXT) && departmentEXT.size() >=
				// 1){//通过前端传值id获取部门信息，若不为空则取supportid
				// departmendid = departmentEXT.get(0).getSupportid();
				// }else{//若部门信息为空，则直接传值
				// departmendid = requestMap.get("departmentid").toString();
				// }
				listJo = supportOrgService.getSupportTenantsId(requestMap.get("departmentid").toString().trim());
			} else {// 获取该组织下的所有部门id
				listJo = supportOrgService.getSupportTenantsId(requestMap.get("orgid").toString().trim());
			}
			// 设置过滤条件
			JSONObject inJO = new JSONObject();
			inJO.put("$in", listJo);
			JSONObject tenantsJO = new JSONObject();
			tenantsJO.put("tenants", inJO);

			if (ObjectUtil.isNotBlank(requestMap.get("name"))) {

				JSONArray tja = new JSONArray();

				JSONObject regexNJO = new JSONObject();
				regexNJO.put("$regex", requestMap.get("name").toString().trim());
				JSONObject regexNameJO = new JSONObject();
				regexNameJO.put("name", regexNJO);

				tja.add(regexNameJO);

				JSONObject jom = new JSONObject();
				JSONObject regexMJO = new JSONObject();
				jom.put("$regex", requestMap.get("name").toString().trim());
				regexMJO.put("mobile", jom);

				tja.add(regexMJO);

				tenantsJO.put("$or", tja);
			}

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(tenantsJO);

			if (ObjectUtil.isNotBlank(requestMap.get("currentPage"))) {
				Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
				resultWrap.setPage(new Page().pageTotal(SupportUtil.supportGetItemData(result).size(), request));
			}

			// 设置分页条件
			JSONObject pageJO = SupportUtil.supportSetPage(requestMap);
			supportRequestParam.setPageJson(pageJO);
			List<UserEXT> userEXTs = new ArrayList<UserEXT>();
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray ja = SupportUtil.supportGetItemData(result);
				for (int i = 0; i < ja.size(); i++) {
					JSONObject supportJO = ja.getJSONObject(i);
					UserEXT userEXT = userSupportBO.ConformSupportToUser(new UserEXT(), supportJO);
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("supportid", userEXT.getSupportid());
					List<UserEXT> userEXTs2 = userService.queryUser(userMap);
					if (userEXTs2.size() <= 0) {
						userService.insertUserSupport(userEXT);
					} else {
						userEXT = userSupportBO.ConformSupportToUser(userEXTs2.get(0), supportJO);
					}
					// 显示部门
					// getDepartmentNameByUserId
					userEXT.setDepartmentname(
							supportUserService.getDepartmentNameByUserId(userEXT.getSupportid(), listJo));
					// userEXT = userSupportBO.setSupportInfoByUser(userEXT,
					// resultWrap,requestMap.get("orgid").toString());
					// 获取角色名称及id
					userEXT.setRolename(supportUserService.getUserRoleNameBySupport(userEXT.getSupportid()));
					userEXT.setRoleid(supportUserService.getUserRoleIdBySupport(userEXT.getSupportid()));
					userEXT.setOrgName(
							supportOrgService.getSupportTenantById(requestMap.get("orgid") + "").getString("name"));
					userEXTs.add(userEXT);
				}
			}
			// List<UserEXT> userEXTs = userService.queryUserBuOrgDepartment(requestMap);
			// resultWrap.setPage(new
			// Page().pageTotal(userService.queryUserBuOrgDepartmentCount(requestMap),
			// request));
			resultWrap.setData(userEXTs);
		} else {
			resultWrap.setParamIsnotEmpty("{orgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取该部门下的部门id
	 * 
	 * @param departmentId:部门编号
	 */
	private List<Integer> departmentIds = new ArrayList<Integer>();

	private void getDepartmentIds(Integer departmentId) {
		departmentIds.add(departmentId);
		Map<String, Object> queryDepartmentMap = new HashMap<String, Object>();
		queryDepartmentMap.put("parentid", departmentId);
		List<DepartmentEXT> departmentEXTs = departmentService.queryDepartment(queryDepartmentMap);
		for (int i = 0; i < departmentEXTs.size(); i++) {
			// departmentIds+= departmentEXTs.get(i).getId()+",";
			departmentIds.add(departmentEXTs.get(i).getId());
		}
	}

	/**
	 * 保存用户
	 */
	@RequestMapping("/saveUser")
	public void saveUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		// 企业logo图片
		if (ObjectUtil.isNotBlank(requestMap.get("photoURL"))) {
			try {
				ResourcehistoryEXT resourcehistoryEXT1 = commonService.addOrUpdateResource(
						ObjectUtil.isNotBlank(requestMap.get("photo")) ? Integer.parseInt(requestMap.get("photo") + "")
								: null,
						requestMap.get("photoURL") + "", "用户头像");
				if (ObjectUtil.isNotBlank(resourcehistoryEXT1)) {
					requestMap.put("photo", resourcehistoryEXT1.getId());
					requestMap.put("photourl", resourcehistoryEXT1.getResourcepath());
				}
			} catch (Exception e) {
				resultWrap.setException(e.getMessage());
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
		}
		// 验证用户是否存在
		if (ObjectUtil.isNotBlank(requestMap.get("phone")) && ObjectUtil.isNotBlank(requestMap.get("userid"))) {
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("phone", requestMap.get("phone"));
			userMap.put("noid", userSupportService.getSupportIdByUserId(requestMap.get("userid")));
			if (userMap.keySet().size() >= 1) {
				supportUserService.queryUserInfoToSupport(userMap, resultWrap);
				if (ObjectUtil.isNotBlank(resultWrap.getData())) {
					resultWrap.setData(null);
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-phone-exist"));
					HTMLUtil.sendWrap(response, resultWrap, request);
					return;
				}
			}
		}

		int result = 0;
		if (ObjectUtil.isNotBlank(requestMap.get("userid"))) {// userid不为空执行修改
			requestMap.put("id", requestMap.get("userid"));
			requestMap.put("supportid", userSupportService.getSupportIdByUserId(requestMap.get("userid")));
			Object obj = supportUserService.editUserInfoToSupport(requestMap);
			String staffLastUpdateTime = SupportUtil.getSupportUpdateDate(obj);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("staffLastUpdateTime", staffLastUpdateTime);
			resultWrap.setData(JSON.toJSON(dataMap));

			result = userService.updateUser(requestMap);
			if (result <= 0) {
				ResultWrap resultWrap2 = supportUserService.insertUserInfoToSupport(requestMap);
				if (ObjectUtil.isNotBlank(resultWrap2.getData()) && resultWrap2.getFlag() == 1) {
					JSONObject dataJson = JSONObject.parseObject(resultWrap2.getData().toString());
					requestMap.put("supportid", SupportUtil.supportGetJsonOid(dataJson));
					result = userService.insertUser(requestMap);
				}
			}
		} else {// userid为空执行新增
			ResultWrap resultWrap2 = supportUserService.insertUserInfoToSupport(requestMap);
			if (ObjectUtil.isNotBlank(resultWrap2.getData()) && resultWrap2.getFlag() == 1) {
				JSONObject dataJson = JSONObject.parseObject(resultWrap2.getData().toString());
				requestMap.put("supportid", SupportUtil.supportGetJsonOid(dataJson));
				result = userService.insertUser(requestMap);
			}
		}
		// 保存用户权限
		if (ObjectUtil.isNotBlank(requestMap.get("roleid")) && ObjectUtil.isNotBlank(requestMap.get("id"))) {
			String[] roles = requestMap.get("roleid").toString().split(",");
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("userid", requestMap.get("id"));
			roleService.deleteUserRole(roleMap);
			for (int i = 0; i < roles.length; i++) {
				Map<String, Object> saveRoleMap = new HashMap<String, Object>();
				saveRoleMap.put("userid", requestMap.get("id"));
				saveRoleMap.put("roleid", roles[i]);
				roleService.insertUserRole(saveRoleMap);
			}
		} else if (ObjectUtil.isBlank(requestMap.get("roleid")) && ObjectUtil.isNotBlank(requestMap.get("id"))) {
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("userid", requestMap.get("id"));
			roleService.deleteUserRole(roleMap);
		}
		// 是否是部门负责人
		if (ObjectUtil.isNotBlank(requestMap.get("isdepartmentbelong"))
				&& ObjectUtil.isNotBlank(requestMap.get("departmentid"))) {
			// 获取部门对应的组织编号
			Map<String, Object> queryDepartMap = new HashMap<String, Object>();
			queryDepartMap.put("id", requestMap.get("departmentid"));
			List<DepartmentEXT> departmentEXTs = departmentService.queryDepartment(queryDepartMap);
			String orgid = "";
			if (ObjectUtil.isNotBlank(departmentEXTs) && departmentEXTs.size() >= 1) {
				DepartmentEXT departmentEXT = departmentEXTs.get(0);
				orgid = departmentEXT.getOrgid() + "";
			}
			if (ObjectUtil.isNotBlank(orgid)) {// orgid不为空则添加用户/部门/组织的关系
				Map<String, Object> saveUserOrgDepartMap = new HashMap<String, Object>();
				saveUserOrgDepartMap.put("userid", requestMap.get("id"));
				// 通过userid获取当前用户的组织部门关系
				List<UserOrgDepartEXT> userOrgDepartEXTs = userService.queryUserOrgDepart(saveUserOrgDepartMap);
				saveUserOrgDepartMap.put("orgid", Integer.parseInt(orgid));
				saveUserOrgDepartMap.put("departmentid", queryDepartMap.get("id"));
				if (ObjectUtil.isNotBlank(userOrgDepartEXTs) && userOrgDepartEXTs.size() >= 1) {
					saveUserOrgDepartMap.put("id", userOrgDepartEXTs.get(0).getId());
					userService.updateUserOrgDepart(saveUserOrgDepartMap);
				} else {
					userService.insertUserOrgDepart(saveUserOrgDepartMap);
				}
			}
			Boolean isbelong = Boolean.parseBoolean(requestMap.get("isdepartmentbelong") + "");
			if (isbelong) {
				Map<String, Object> upDepartBelong = new HashMap<String, Object>();
				upDepartBelong.put("id", requestMap.get("departmentid"));
				upDepartBelong.put("chargeuserid", requestMap.get("id"));
				departmentService.updateDepartment(upDepartBelong);
			}
		}
		if (result >= 1) {
			resultWrap.setActSuccess();
		} else {
			resultWrap.setActFailure();
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePassword")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("userid")) && ObjectUtil.isNotBlank(requestMap.get("oldpassword"))
				&& ObjectUtil.isNotBlank(requestMap.get("newpassword"))) {
			if (requestMap.get("oldpassword").equals(requestMap.get("newpassword"))) {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-update-password-repeat"));
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}
			if (supportService.isUserSupport) {
				// 验证用户编号与密码是否匹配
				String supportid = userSupportService.getSupportIdByUserId(requestMap.get("userid"));
				if (ObjectUtil.isNotBlank(supportid)) {
					Map<String, Object> supportMap = new HashMap<String, Object>();
					supportMap.put("supportid", supportid);
					supportUserService.queryUserInfoToSupport(supportMap, resultWrap);
					if (resultWrap.getFlag() == 1 && ObjectUtil.isNotBlank(resultWrap.getData())) {
						// 通过supportid获取数据成功
						JSONArray resultJA = JSONArray.parseArray(resultWrap.getData().toString());
						resultWrap.setData(null);
						if (resultJA.size() >= 1) {
							JSONObject jo = resultJA.getJSONObject(0);
							// 密码匹配成功，执行修改
							if (SupportUserPasswordUtil.passwordMatches(
									requestMap.get("oldpassword").toString().toCharArray(), jo.getString("password"))) {
								supportMap.put("password", requestMap.get("newpassword"));
								Object editResult = supportUserService.editUserInfoToSupport(supportMap);
								if (ObjectUtil.isNotBlank(editResult)) {
									String staffLastUpdateTime = SupportUtil.getSupportUpdateDate(editResult);
									Map<String, Object> dataMap = new HashMap<String, Object>();
									dataMap.put("staffLastUpdateTime", staffLastUpdateTime);
									resultWrap.setData(JSON.toJSON(dataMap));

									Map<String, Object> saveUserMap = new HashMap<String, Object>();
									saveUserMap.put("id", requestMap.get("userid"));
									saveUserMap.put("password",
											supportUserService.encryPassword(requestMap.get("newpassword")));
									userService.updateUser(saveUserMap);
								}
							} else {
								resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-update-old-password-failure"));
							}
						} else {
							resultWrap.setUpdateFailure();
						}
					} else {
						resultWrap.setUpdateFailure();
					}
				} else {
					resultWrap.setSupportNoInfoActFailure();
				}

				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			Map<String, Object> queryUserMap = new HashMap<String, Object>();
			queryUserMap.put("id", requestMap.get("userid"));
			// queryUserMap.put("password",
			// SupportUserController.encryPassword(requestMap.get("password")));

			List<UserEXT> userEXTs = userService.queryUser(queryUserMap);
			if (ObjectUtil.isNotBlank(userEXTs) && userEXTs.size() >= 1) {
				// 匹配成功修改密码
				Map<String, Object> updateUserPassword = new HashMap<String, Object>();
				updateUserPassword.put("id", requestMap.get("userid"));
				String newpassword = requestMap.get("newpassword").toString();
				updateUserPassword.put("password", supportUserService.encryPassword(newpassword));
				int result = userService.updateUser(updateUserPassword);
				if (result >= 1) {
					resultWrap.setUpdateSuccess();
				} else {
					resultWrap.setUpdateFailure();
				}
			} else {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-update-password-failure"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("{新密码、旧密码}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 根据手机号码修改密码(313)
	 */
	@RequestMapping("/updatePasswordByPhone")
	public void updatePasswordByPhone(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("phone")) && ObjectUtil.isNotBlank(requestMap.get("password"))) {
			// 修改支撑用户密码
			Map<String, Object> suppottMap = new HashMap<String, Object>();
			suppottMap.put("phone", requestMap.get("phone"));
			supportUserService.queryUserInfoToSupport(suppottMap, resultWrap);
			if (ObjectUtil.isNotBlank(resultWrap) && resultWrap.getFlag() == 1
					&& ObjectUtil.isNotBlank(resultWrap.getData())) {
				JSONArray resultJA = JSONArray.parseArray(resultWrap.getData().toString());
				if (resultJA.size() == 1) {
					String supportid = JSONObject.parseObject(resultJA.getJSONObject(0).getString("_id"))
							.getString("$oid");
					if (ObjectUtil.isNotBlank(supportid)) {
						suppottMap.put("supportid", supportid);
						suppottMap.put("password", requestMap.get("password"));
						Object result = supportUserService.editUserInfoToSupport(suppottMap);
						if (ObjectUtil.isNotBlank(result)) {
							resultWrap.setUpdateSuccess();
						} else {
							resultWrap.setUpdateFailure();
						}
					} else {
						resultWrap.setUpdateFailure();
					}
				} else {
					resultWrap.setUpdateFailure();
				}
			} else {
				resultWrap.setUpdateFailure();
			}

			// Object encryPassword =
			// SupportUserController.encryPassword(requestMap.get("password"));
			// requestMap.put("password", encryPassword);
			//
			// //通过phone查询支撑id
			// Map<String,Object> userMap = new HashMap<String,Object>();
			// userMap.put("phone", requestMap.get("phone"));
			// List<UserEXT> userEXTs = userService.queryUser(userMap);
			// if(ObjectUtil.isNotBlank(userEXTs) && userEXTs.size() >= 1){//平台有该用户数据
			// if(userEXTs.size() == 1){//只存在一条该数据
			// Map<String,Object> supportUserMap = new HashMap<String,Object>();
			// supportUserMap.put("supportid", userEXTs.get(0).getSupportid());
			// supportUserMap.put("", value);
			//
			// JSONObject jsonObject = new JSONObject();
			// userSupportBO.ConformUserToSupport(userEXTs.get(0), jsonObject);
			// SupportRequestParam supportRequestParam = new SupportRequestParam();
			// supportRequestParam.setDataJson(jsonObject);
			// SupportController.requestSupportGet(AllJsonRequestUrl.Tenant_staff,
			// supportRequestParam);
			//
			// int result = userService.updatePasswordByPhone(requestMap);
			// if(result >= 1){
			// resultWrap.setUpdateSuccess();
			// }else{
			// resultWrap.setUpdateFailure();
			// }
			// }else{//数据多条（用户的手机号码只能存在一条）
			// resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-update-password-phone-repeat"));
			// }
			// }else{//平台没有该数据，从支撑获取
			//
			// }
		} else {
			resultWrap.setParamIsnotEmpty("{phone,password}");
		}
		resultWrap.setData(null);
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 查询单个用户信息
	 */
	@RequestMapping("/queryUserInfoById")
	public void queryUserInfoById(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("userid")) && ObjectUtil.isNotBlank(requestMap.get("orgid"))) {
			requestMap.put("id", requestMap.get("userid"));
			UserEXT userEXT = new UserEXT();
			List<UserEXT> userEXTs = userService.queryUserDetail(requestMap);
			if (userEXTs.size() >= 1) {
				userEXT = userEXTs.get(0);
				userEXT = userSupportBO.setSupportInfoByUser(userEXT, resultWrap, requestMap.get("orgid").toString());
			}
			resultWrap.setData(userEXT);
		} else {
			resultWrap.setParamIsnotEmpty("{userid,orgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 移除/退出组织
	 */
	@RequestMapping("/removeUserFromOrg")
	public void removeUserFromOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("orgid")) && ObjectUtil.isNotBlank(requestMap.get("userid"))) {
			String supportid = userSupportService.getSupportIdByUserId(requestMap.get("userid"));
			Map<String, Object> orgMap = new HashMap<String, Object>();
			orgMap.put("supportid", requestMap.get("orgid"));
			List<OrgEXT> orEXTs = orgService.queryOrg(orgMap);
			String tenantid = "";
			if (orEXTs.size() >= 1) {
				tenantid = orEXTs.get(0).getSupportid();
			}
			if (ObjectUtil.isBlank(tenantid)) {
				resultWrap.setSupportNoInfoActFailure();
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			// 监测当前用户是否是组织负责人（支撑）
			if (supportUserService.isTenantManagerBySupportid(tenantid, supportid)) {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-belong-not-alow-remove"));
				resultWrap.setStatusCode(ConstantUtils.RESULTWRAP_STATECODE_OUT_ENTERPRISE);
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			// 监测当前用户是否是组织负责人
			// Map<String,Object> queryBelongOrg = new HashMap<String,Object>();
			// queryBelongOrg.put("id", requestMap.get("orgid"));
			// queryBelongOrg.put("belonguserid", requestMap.get("userid"));
			// List<OrgEXT> orgEXTs = orgService.queryOrg(queryBelongOrg);
			// if(ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() >= 1){
			// resultWrap.error(LanguageUtil.getLanguageByKey("fwk-org-belong-not-alow-remove"));
			// HTMLUtil.sendWrap(response, resultWrap, request);
			// return;
			// }
			// 操作支撑数据

			boolean isresult = supportUserService.deleteTenantsBySupportid(tenantid, supportid);
			if (!isresult) {
				resultWrap.setActFailure();
				HTMLUtil.sendWrap(response, resultWrap, request);
				return;
			}

			// int result = userService.removeUserFromOrg(requestMap);
			// if(result >= 1){
			// resultWrap.setActSuccess();
			// }else{
			// resultWrap.setActFailure();
			// }
		} else {
			resultWrap.setParamIsnotEmpty("{orgid,userid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取当前登录人的所属组织下的所有用户(313)
	 */
	@RequestMapping("/queryUserByLoginUserOrg")
	public void queryUserByLoginUserOrg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("loginorgid"))) {
			// 获取组织下的所有部门 [{"$oid":"5a912101a1de180001ada670"},
			// {"$oid":"5aa72ea046e0fb00013b6ecf"}]
			List<JSONObject> jsonObjects = supportOrgService
					.getSupportTenantsId(requestMap.get("loginorgid").toString());
			//
			JSONObject inJO = new JSONObject();
			inJO.put("$in", jsonObjects);
			JSONObject tenantsJO = new JSONObject();
			tenantsJO.put("tenants", inJO);

			SupportRequestParam supportRequestParam = new SupportRequestParam();
			supportRequestParam.setFilterJson(tenantsJO);
			List<UserEXT> userEXTs = new ArrayList<UserEXT>();
			Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
			System.out.println(result);
			if (SupportUtil.supportResultItemIsNotEmpty(result)) {
				JSONArray ja = SupportUtil.supportGetItemData(result);
				for (int i = 0; i < ja.size(); i++) {
					JSONObject supportJO = ja.getJSONObject(i);
					UserEXT userEXT = userSupportBO.ConformSupportToUser(new UserEXT(), supportJO);
					userEXTs.add(userEXT);
				}
			}

			// Map<String, Object> queryUserMap = new HashMap<String,Object>();
			// queryUserMap.put("orgid", requestMap.get("loginorgid"));
			// List<UserEXT> userEXTs = userService.queryUsersByOrgId(queryUserMap);
			resultWrap.setData(userEXTs);
		} else {
			resultWrap.setParamIsnotEmpty("{loginorgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取当前登录人的组织下的所有部门及全部用户（树形结构）（313）
	 */
	@RequestMapping("/queryUserAndDepartByLoginUserOrg")
	public void queryDepartmentByLoginOrgId(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		List<OrgDepartUserDefine> orgDepartUserDefines = new ArrayList<OrgDepartUserDefine>();
		if (ObjectUtil.isNotBlank(requestMap.get("loginorgid"))) {
			// 通过登录的组织id获取组织及其所有部门
			List<JSONObject> tenantsList = supportOrgService
					.getSupportTenantsId(requestMap.get("loginorgid").toString());
			for (int i = 0; i < tenantsList.size(); i++) {
				JSONObject tenantJO = supportOrgService.getSupportTenantById(tenantsList.get(i).getString("$oid"));
				if (ObjectUtil.isBlank(tenantJO) || tenantJO.keySet().size() <= 0) {
					continue;
				}
				OrgDepartUserDefine orgDepartUserDefine = new OrgDepartUserDefine();
				orgDepartUserDefine.setId(JSONObject.parseObject(tenantJO.getString("_id")).getString("$oid"));
				orgDepartUserDefine.setName(tenantJO.getString("name"));
				// 获取组织下的用户信息
				List<JSONObject> listJo = new ArrayList<JSONObject>();
				// 获取组织/部门下的所有部门
				// listJo =
				// supportOrgService.getSupportTenantsId(JSONObject.parseObject(tenantJO.getString("_id")).getString("$oid"));
				// 修改数据重复问题（2018.4.11-HC要求修改）
				listJo.add(JSONObject.parseObject(tenantJO.getString("_id")));
				// 设置过滤条件
				JSONObject inJO = new JSONObject();
				inJO.put("$in", listJo);
				JSONObject tenantsJO = new JSONObject();
				tenantsJO.put("tenants", inJO);
				SupportRequestParam supportRequestParam = new SupportRequestParam();
				supportRequestParam.setFilterJson(tenantsJO);
				// 设置分页条件
				List<UserEXT> userEXTs = new ArrayList<UserEXT>();
				Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
				if (SupportUtil.supportResultItemIsNotEmpty(result)) {
					JSONArray resultJA = SupportUtil.supportGetItemData(result);
					for (int j = 0; j < resultJA.size(); j++) {
						UserEXT userEXT = userSupportBO.ConformSupportToUser(new UserEXT(), resultJA.getJSONObject(j));
						userEXTs.add(userEXT);
					}
				}
				orgDepartUserDefine.setChildren(userEXTs);
				orgDepartUserDefines.add(orgDepartUserDefine);
			}

			// 获取组织信息
			// Map<String,Object> queryOrgMap = new HashMap<String,Object>();
			// queryOrgMap.put("id", requestMap.get("loginorgid"));
			// List<OrgEXT> orgEXTs = orgService.queryOrg(queryOrgMap);
			// if(ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() == 1){
			// //获取组织下的用户信息
			// OrgDepartUserDefine orgDepartUserDefine = new OrgDepartUserDefine();
			// orgDepartUserDefine.setId(orgEXTs.get(0).getId().toString());
			// orgDepartUserDefine.setName(orgEXTs.get(0).getName());
			// Map<String,Object> queryUserMap = new HashMap<String,Object>();
			// queryUserMap.put("orgid", orgEXTs.get(0).getId());
			// orgDepartUserDefine.setChildren(userService.queryUsersByOrgId(queryUserMap));
			// orgDepartUserDefines.add(orgDepartUserDefine);
			// //获取组织下的部门信息
			// Map<String,Object> queryDepartMap = new HashMap<String,Object>();
			// queryDepartMap.put("orgid",orgEXTs.get(0).getId());
			// List<DepartmentEXT> departmentEXTs =
			// departmentService.queryDepartment(queryDepartMap);
			// if(ObjectUtil.isNotBlank(departmentEXTs) && departmentEXTs.size() >= 1){
			// //获取部门的用户信息
			// for (int i = 0; i < departmentEXTs.size(); i++) {
			// OrgDepartUserDefine orgDepartUserDefineDepart = new OrgDepartUserDefine();
			// orgDepartUserDefineDepart.setId(departmentEXTs.get(i).getId().toString());
			// orgDepartUserDefineDepart.setName(departmentEXTs.get(i).getName());
			// Map<String,Object> queryUserBydepartMap = new HashMap<String,Object>();
			// queryUserBydepartMap.put("departmentid", departmentEXTs.get(i).getId());
			// orgDepartUserDefineDepart.setChildren(userService.queryUserBuOrgDepartment(queryUserBydepartMap));
			// orgDepartUserDefines.add(orgDepartUserDefineDepart);
			// }
			// }
			// }
		} else {
			resultWrap.setParamIsnotEmpty("{loginorgid}");
		}
		resultWrap.setData(orgDepartUserDefines);
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	// 修改日期2018-2-11
	// @RequestMapping("/queryUserAndDepartByLoginUserOrg")
	// public void queryDepartmentByLoginOrgId(HttpServletRequest
	// request,HttpServletResponse response){
	// Map<String,Object> requestMap = HTMLUtil.getPageParams(request);
	// ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
	// if(ObjectUtil.isNotBlank(requestMap.get("loginorgid"))){
	// Map<String,Object> queryOrgMap = new HashMap<String,Object>();
	// queryOrgMap.put("id", requestMap.get("loginorgid"));
	// List<OrgEXT> orgEXTs = orgService.queryOrg(queryOrgMap);
	// if(ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() > 0){
	// for (int i = 0; i < orgEXTs.size(); i++) {
	// OrgEXT orgEXT = orgEXTs.get(i);
	// orgEXT.setUserChildren(getUserByDepartment(orgEXT.getId(),null));
	// orgEXT.setUserCount(getUserByDepartmentCount(orgEXT.getId(),null));
	//
	// requestMap.put("orgid", requestMap.get("loginorgid"));
	// requestMap.put("parentid", ObjectUtil.toString(0));
	// //获取组织下的所有部门
	// List<DepartmentEXT> departmentEXTs =
	// departmentService.queryDepartment(requestMap);
	// // 创建子节点
	// this.buildChildNode(departmentEXTs);
	//
	// orgEXT.setChildren(departmentEXTs);
	// }
	//
	// //整理数据返回给前台
	// resultWrap.setData(orgEXTs);
	// }
	//
	// }else{
	// resultWrap.setParamIsnotEmpty("{loginorgid}");
	// }
	// HTMLUtil.sendWrap(response, resultWrap, request);
	// }

	/**
	 * 创建部门子节点
	 * 
	 * @author wangjunwen
	 * @date 2018年1月30日 上午9:07:09
	 *
	 * @param pDepartmentEXTList
	 *            部门列表
	 */
	public void buildChildNode(List<DepartmentEXT> pDepartmentEXTList) {
		if ((null != pDepartmentEXTList) && (pDepartmentEXTList.size() > 0)) {
			for (DepartmentEXT pDepartmentEXT : pDepartmentEXTList) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("parentid", pDepartmentEXT.getId().toString());
				List<DepartmentEXT> childDepartmentEXTList = departmentService.queryDepartment(paramMap);
				// 设置子节点
				pDepartmentEXT.setChildren(childDepartmentEXTList);
				pDepartmentEXT.setUserChildren(getUserByDepartment(null, pDepartmentEXT.getId()));
				pDepartmentEXT.setUserCount(getUserByDepartmentCount(null, pDepartmentEXT.getId()));
				// 再进一步递归子节点的子节点
				buildChildNode(childDepartmentEXTList);
			}
		}
	}

	/**
	 * 获取部门对应的用户
	 */
	public List<UserEXT> getUserByDepartment(Integer orgid, Integer departmentID) {
		List<UserEXT> userEXTs = new ArrayList<UserEXT>();
		Map<String, Object> queryUserByDepart = new HashMap<String, Object>();
		if (ObjectUtil.isNotBlank(orgid)) {
			queryUserByDepart.put("orgid", orgid);
		}
		if (ObjectUtil.isNotBlank(departmentID)) {
			queryUserByDepart.put("departmentid", departmentID);
		}
		userEXTs = userService.getUserByDepartment(queryUserByDepart);
		return userEXTs;
	}

	/**
	 * 获取部门对应的用户数量
	 */
	public Integer getUserByDepartmentCount(Integer orgid, Integer departmentID) {
		List<UserEXT> userEXTs = new ArrayList<UserEXT>();
		Map<String, Object> queryUserByDepart = new HashMap<String, Object>();
		if (ObjectUtil.isNotBlank(orgid)) {
			queryUserByDepart.put("orgid", orgid);
		}
		if (ObjectUtil.isNotBlank(departmentID)) {
			queryUserByDepart.put("departmentid", departmentID);
		}
		userEXTs = userService.getUserByDepartment(queryUserByDepart);
		return userEXTs.size();
	}

	/**
	 * 修改手机号码
	 */
	@RequestMapping("/updatePhoneById")
	public void updatePhoneById(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("userid")) && ObjectUtil.isNotBlank(requestMap.get("phone"))) {
			// 获取支撑id
			Object supportid = userSupportService.getSupportIdByUserId(requestMap.get("userid"));
			if (ObjectUtil.isNotBlank(supportid)) {
				// 查询支撑是否存在该手机号码
				Map<String, Object> supportUserPhoneMap = new HashMap<String, Object>();
				supportUserPhoneMap.put("noid", supportid);
				supportUserPhoneMap.put("phone", requestMap.get("phone"));
				supportUserService.queryUserInfoToSupport(supportUserPhoneMap, resultWrap);
				if (ObjectUtil.isNotBlank(resultWrap.getData())) {
					resultWrap.setData(null);
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-registe-phone-exist"));
					HTMLUtil.sendWrap(response, resultWrap, request);
					return;
				}
				// 修改支撑用户手机号码数据
				requestMap.put("supportid", supportid);
				Object resultEdit = supportUserService.editUserInfoToSupport(requestMap);
				if (ObjectUtil.isNotBlank(resultEdit)) {
					String staffLastUpdateTime = SupportUtil.getSupportUpdateDate(resultEdit);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("staffLastUpdateTime", staffLastUpdateTime);
					resultWrap.setData(JSON.toJSON(dataMap));
					resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-update-phone-success"));
				} else {
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-update-phone-failure"));
				}
			} else {
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-message-support-no-info-act-failure"));
			}
		} else {
			resultWrap.setParamIsnotEmpty("{手机号码}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取登录用户的权限树结构
	 */
	@RequestMapping("/queryPermissionByLoginUser")
	public void queryPermissionByLoginUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("loginuserid"))) {
			// 查询一级导航
			requestMap.put("rootid", 0);
			requestMap.put("typecode", "NAVIGATE");
			List<PermissionEXT> permissionEXTs = permissionService.queryPermissionByLoginUser(requestMap);
			// 查询全部下一级内容
			neatenPermissionTree(permissionEXTs);

			resultWrap.setData(permissionEXTs);
		} else {
			resultWrap.setParamIsnotEmpty("{loginuserid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	// 整理权限导航树
	private void neatenPermissionTree(List<PermissionEXT> permissionEXTs) {
		if (ObjectUtil.isNotBlank(permissionEXTs) && permissionEXTs.size() > 0) {
			for (int i = 0; i < permissionEXTs.size(); i++) {
				Map<String, Object> queryPermissionMap = new HashMap<String, Object>();
				queryPermissionMap.put("rootid", permissionEXTs.get(i).getId());
				queryPermissionMap.put("typecode", "NAVIGATE");
				List<PermissionEXT> exts = permissionService.queryPermissionByLoginUser(queryPermissionMap);

				neatenPermissionTree(exts);

				permissionEXTs.get(i).setChildren(exts);
			}
		}
	}

	/**
	 * 获取用户所有功能点（平级结构）
	 */
	@RequestMapping("/queryPermissionFunctionByLoginUser")
	public void queryPermissionFunctionByLoginUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("loginuserid"))) {
			requestMap.put("typecode", "FUNCTION");
			List<PermissionEXT> permissionEXTs = permissionService.queryPermissionByLoginUser(requestMap);
			resultWrap.setData(permissionEXTs);
		} else {
			resultWrap.setParamIsnotEmpty("{loginuserid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 验证手机号存在
	 */
	@RequestMapping("/checkUserIsOwen")
	public void checkUserIsOwen(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		Map<String, Object> queryUserMap = new HashMap<String, Object>();
		queryUserMap.put("phone", requestMap.get("phone"));
		supportUserService.queryUserInfoToSupport(requestMap, resultWrap);
		if (ObjectUtil.isNotBlank(resultWrap.getData())
				&& JSONArray.parseArray(resultWrap.getData().toString()).size() >= 1) {
			resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-check-isexist-true"));
		} else {
			resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-check-isexist-failure"));
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 验证手机号不存在
	 */
	@RequestMapping("/checkUserIsNotOwen")
	public void checkUserIsNotOwen(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		Map<String, Object> queryUserMap = new HashMap<String, Object>();
		queryUserMap.put("phone", requestMap.get("phone"));
		supportUserService.queryUserInfoToSupport(requestMap, resultWrap);
		System.out.println(resultWrap.getData());
		if (ObjectUtil.isBlank(resultWrap.getData())
				|| JSONArray.parseArray(resultWrap.getData().toString()).size() <= 0) {
			resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-check-isexist-failure"));
		} else {
			resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-check-isexist-true"));
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * @Description: 通过手机号获取已登录过的组织信息
	 */
	@RequestMapping("/getDefaultOrgByPhone")
	public void getDefaultOrgByPhone(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if (ObjectUtil.isBlank(requestMap.get("phone"))) {
				resultWrap.setParamIsnotEmpty("{phone}");
				return;
			}
			supportUserService.queryUserInfoToSupportNew(requestMap, resultWrap);
			if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
				resultWrap.setException("获取支撑平台用户信息出错！");
				resultWrap.setData(null);
				return;
			}
			// 用户信息不存在
			if (ObjectUtil.isBlank(resultWrap.getData())) {
				resultWrap.setActSuccess();
				resultWrap.setMessage("支撑平台用户信息不存在！");
				resultWrap.setData(null);
				return;
			}
			// 获取支撑用户信息
			JSONArray supportUserInfoArray = (JSONArray) resultWrap.getData();
			String userSupportId = supportUserInfoArray.getJSONObject(0).getJSONObject("_id").getString("$oid");
			// 获取支撑用户所属组织
			JSONArray userOrgArray = supportUserInfoArray.getJSONObject(0).getJSONArray("tenants");
			if (CollectionUtils.isEmpty(userOrgArray)) {
				resultWrap.setActSuccess();
				resultWrap.setData(null);
				resultWrap.setMessage("用户所属组织不存在！");
				return;
			}
			List<String> userOrgIds = new ArrayList<>();
			for (Object obj : userOrgArray) {
				// 如果当前所属组织为部门，则需要找他上一级组织
				String orgId = ((JSONObject) obj).getString("$oid");
				String parentOrgId = supportOrgDepartUtil.supportGetTenantParent(orgId);
				userOrgIds.add(parentOrgId);
			}
			// 查询农事系统用户信息
			List<UserEXT> userList = userService.queryUserDetailBySupportId(userSupportId);
			if (CollectionUtils.isEmpty(userList)) {
				resultWrap.setActSuccess();
				// 如果农事系统用户信息不存在，则把其所属组织的第一个组织ID赋给他
				UserEXT tmpUserEXT = new UserEXT();
				tmpUserEXT.setLoginorgid(userOrgIds.get(0));
				resultWrap.setData(tmpUserEXT);
				return;
			}
			UserEXT resultUserExt = userList.get(0);
			if (ObjectUtil.isBlank(resultUserExt.getLoginorgid())) {
				resultUserExt.setLoginorgid(userOrgIds.get(0));
			}
			if (!userOrgIds.contains(resultUserExt.getLoginorgid())) {
				resultUserExt.setLoginorgid(userOrgIds.get(0));
			}
			// 更新最后登录组织id到数据库
			Map<String, Object> userUpdateMap = new HashMap<String, Object>();
			userUpdateMap.put("loginorgid", resultUserExt.getLoginorgid());
			userUpdateMap.put("id", resultUserExt.getId());
			userService.updateUser(userUpdateMap);

			resultWrap.setActSuccess();
			resultWrap.setData(resultUserExt);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setData(null);
			resultWrap.setException(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * @Description: 初始化用户权限
	 *
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @author: zhangyq
	 * @date: 2018年6月13日 下午3:03:44
	 */
	@RequestMapping("/initPermission")
	public void initPermission(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		requestMap.remove("password");
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		try {
			if (ObjectUtil.isBlank(requestMap.get("account"))) {
				resultWrap.setParamIsnotEmpty("{account}");
				return;
			}
			supportUserService.queryUserInfoToSupportNew(requestMap, resultWrap);
			if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
				resultWrap.setException("获取用户信息出错！");
				return;
			}
			// 用户信息不存在
			if (ObjectUtil.isBlank(resultWrap.getData())) {
				resultWrap.setActFailure();
				resultWrap.setMessage("用户信息不存在！");
				return;
			}
			// 获取支撑用户信息
			JSONArray supportUserInfoArray = (JSONArray) resultWrap.getData();
			// 获取用户所属组织
			JSONArray userOrgArray = supportUserInfoArray.getJSONObject(0).getJSONArray("tenants");
			if (ObjectUtil.isBlank(userOrgArray) || userOrgArray.isEmpty()) {
				resultWrap.setActFailure();
				resultWrap.setMessage("用户所属组织不存在！");
				return;
			}
			List<String> userOrgIds = userOrgArray.stream().map(item -> {
				return ((JSONObject) item).getString("$oid");
			}).collect(Collectors.toList());

			// 获取用户ID
			String supportUserId = supportUserInfoArray.getJSONObject(0).getJSONObject("_id").getString("$oid");

			// 获取系统所有权限
			resultWrap = supportService.getRequest(AllJsonRequestUrl.Sys_authority, new SupportRequestParam(), true);
			if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
				resultWrap.setException("获取系统权限信息出错！");
				resultWrap.setData(null);
				return;
			}
			JSONArray fwkAuthoritiesArray = SupportUtil.supportGetItemData(resultWrap.getData());
			List<JSONObject> fwkAuthorities = fwkAuthoritiesArray.stream().map(item -> {
				return ((JSONObject) item).getJSONObject("_id");
			}).collect(Collectors.toList());
			// 存储当前账号是其所属组织负责人的组织ID
			List<String> currUserIsManagerOfOrgIds = new ArrayList<String>();
			// 循环当前账号所属组织并判断当前组织的负责人是否是该账号，不是负责人，则无需初始化权限，是则进行下一点验证
			List<Map<String, Object>> supportOrgList = supportOrgService.queryOrgByIds(userOrgIds);
			// 存在组织信息，且该组织存在负责人并判断当前用户是否为所属组织的负责人,只需要给负责人初始化权限
			currUserIsManagerOfOrgIds = supportOrgList.stream().filter(
					f -> ObjectUtil.isNotBlank(f.get("manager")) && Objects.equals(f.get("manager"), supportUserId))
					.map(m -> {
						return m.get("supportid").toString();
					}).collect(Collectors.toList());
			if (currUserIsManagerOfOrgIds.isEmpty()) {
				resultWrap.setActSuccess();
				resultWrap.setData(null);
				return;
			}
			// 继续验证农事系统中对应组织是否有角色权限等信息。无相关信息则需要初始化
			for (String orgId : currUserIsManagerOfOrgIds) {
				// 先判断组织是否有超级管理员角色
				JSONObject roleFilterJson = new JSONObject();
				roleFilterJson.put("name", "超级管理员");
				roleFilterJson.put("status", 1);
				roleFilterJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
				SupportRequestParam supportRequestParamForQueryRole = new SupportRequestParam();
				supportRequestParamForQueryRole.setFilterJson(roleFilterJson);
				resultWrap = supportService.getRequest(AllJsonRequestUrl.Tenant_role, supportRequestParamForQueryRole,
						true);
				if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
					resultWrap.setException("获取超级管理员角色信息出错！");
					resultWrap.setData(null);
					return;
				}
				JSONArray roleArray = SupportUtil.supportGetItemData(resultWrap.getData());
				// 指定角色ID
				String roleId = new ObjectId().toString();
				// 无超级管理员角色则新增
				if (roleArray.isEmpty()) {
					// 新建超级管理员角色
					JSONObject roleDataJson = new JSONObject();
					roleDataJson.put("_id", new JSONObject().fluentPut("$oid", roleId));
					roleDataJson.put("name", "超级管理员");
					roleDataJson.put("status", 1);
					roleDataJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
					roleDataJson.put("authorities", fwkAuthorities);
					roleDataJson.put("remark", "初始化权限生成的角色");
					SupportRequestParam supportRequestParamForAddRole = new SupportRequestParam();
					supportRequestParamForAddRole.setDataJson(roleDataJson);
					resultWrap = supportService.postRequest(AllJsonRequestUrl.Tenant_role,
							supportRequestParamForAddRole, true);
					if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
						resultWrap.setException("添加超级管理员角色信息出错！");
						resultWrap.setData(null);
						return;
					}
				} else {
					roleId = roleArray.getJSONObject(0).getJSONObject("_id").getString("$oid");
				}
				// 验证当前用户是否与组织及角色关联
				JSONObject filterJson = new JSONObject();
				filterJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
				filterJson.put("staff", new JSONObject().fluentPut("$oid", supportUserId));
				SupportRequestParam supportRequestParam = new SupportRequestParam();
				supportRequestParam.setFilterJson(filterJson);
				resultWrap = supportService.getRequest(AllJsonRequestUrl.Tenant_staff_roles, supportRequestParam, true);
				if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
					resultWrap.setException("查询企业用户角色关联信息出错！");
					resultWrap.setData(null);
					return;
				}
				JSONArray tenantStaffRoleArray = SupportUtil.supportGetItemData(resultWrap.getData());

				if (tenantStaffRoleArray.isEmpty()) {
					// 添加角色与企业及人员关系
					JSONObject tenantStaffRoleDataJson = new JSONObject();
					// 指定关系表ID
					String shipOid = new ObjectId().toString();
					tenantStaffRoleDataJson.put("_id", new JSONObject().fluentPut("$oid", shipOid));
					tenantStaffRoleDataJson.put("staff", new JSONObject().fluentPut("$oid", supportUserId));
					tenantStaffRoleDataJson.put("tenant", new JSONObject().fluentPut("$oid", orgId));
					tenantStaffRoleDataJson.put("roles", Arrays.asList(new JSONObject().fluentPut("$oid", roleId)));
					SupportRequestParam supportRequestParamForShip = new SupportRequestParam();
					supportRequestParamForShip.setDataJson(tenantStaffRoleDataJson);
					resultWrap = supportService.postRequest(AllJsonRequestUrl.Tenant_staff_roles,
							supportRequestParamForShip, true);
					if (resultWrap.getFlag() == ConstantUtils.RESULTWRAP_FLAG_ERROR) {
						// 添加关系信息出错时，回滚刚刚插入的角色信息
						JSONObject tmpJson = new JSONObject();
						tmpJson.put("filter",
								new JSONObject().fluentPut("_id", new JSONObject().fluentPut("$oid", roleId)));
						SupportRequestParam tmpRequestParam = new SupportRequestParam();
						tmpRequestParam.setDataJson(tmpJson);
						supportService.deleteRequest(AllJsonRequestUrl.Tenant_role, tmpRequestParam, true);
						tmpJson.put("filter",
								new JSONObject().fluentPut("_id", new JSONObject().fluentPut("$oid", shipOid)));
						tmpRequestParam.setDataJson(tmpJson);
						supportService.deleteRequest(AllJsonRequestUrl.Tenant_staff_roles, tmpRequestParam, true);
						resultWrap.setException("添加企业成员角色关系信息出错！");
						resultWrap.setData(null);
						return;
					}
				}
			}
			resultWrap.setActSuccess();
			resultWrap.setData(null);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			resultWrap.setActFailure();
			resultWrap.setData(null);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

}
