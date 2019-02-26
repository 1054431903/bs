package com.fwk.service.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.entity.DepartmentEXT;
import com.fwk.service.user.entity.OrgEXT;
import com.fwk.service.user.service.DepartmentService;
import com.fwk.service.user.service.OrgService;

/**
 * 部门管理
 */
@Controller
@RequestMapping("/depart")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private OrgService orgService;
	@Autowired
	private SupportOrgService supportOrgService;

	/**
	 * 获取当前登录者的组织下的所有部门（树形结构）
	 */
	// @RequestMapping("/queryDepartmentByLoginOrgId")
	public void queryDepartmentByLoginOrgIdBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("loginorgid"))) {
			Map<String, Object> queryOrgMap = new HashMap<String, Object>();
			queryOrgMap.put("id", requestMap.get("loginorgid"));
			List<OrgEXT> orgEXTs = orgService.queryOrg(queryOrgMap);
			if (ObjectUtil.isNotBlank(orgEXTs) && orgEXTs.size() > 0) {
				for (int i = 0; i < orgEXTs.size(); i++) {
					OrgEXT orgEXT = orgEXTs.get(i);
					requestMap.put("orgid", requestMap.get("loginorgid"));
					requestMap.put("parentid", ObjectUtil.toString(0));
					// 获取组织下的所有部门
					List<DepartmentEXT> departmentEXTs = departmentService.queryDepartment(requestMap);
					// 创建子节点
					this.buildChildNodeBak(departmentEXTs);

					orgEXT.setChildren(departmentEXTs);
				}

				// 整理数据返回给前台
				resultWrap.setData(orgEXTs);
			}

		} else {
			resultWrap.setParamIsnotEmpty("{loginorgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 获取当前登录者的组织下的所有部门（树形结构）
	 */
	@RequestMapping("/queryDepartmentByLoginOrgId")
	public void queryDepartmentByLoginOrgId(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("loginorgid"))) {
			List<Map<String, Object>> orgList = null;
			// 查询农事的组织ID
			OrgEXT orgEXT = orgService.queryOrgBySupportId(String.valueOf(requestMap.get("loginorgid")));
			if (orgEXT != null) {
				Map<String, Object> queryOrgMap = new HashMap<String, Object>();
				queryOrgMap.put("id", requestMap.get("loginorgid"));
				orgList = supportOrgService.queryOrg(queryOrgMap);
				buildChildNode(orgList, requestMap.get("loginorgid"), orgEXT.getId());
			}

			// 整理数据返回给前台
			resultWrap.setData(orgList);
		} else {
			resultWrap.setParamIsnotEmpty("{loginorgid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 创建子节点
	 * 
	 * @author wangjunwen
	 * @date 2018年1月30日 上午9:07:09
	 *
	 * @param pDepartmentEXTList
	 *            部门列表
	 */
	private void buildChildNode(List<Map<String, Object>> list, Object issOrgid, Object fwkOrgid) {
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<String, Object> map : list) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				String supportid = String.valueOf(map.get("supportid"));
				paramMap.put("parentid", supportid);
				List<Map<String, Object>> childList = supportOrgService.queryDepartment(paramMap);// 获取部门下的所有部门
				int sign = Integer.parseInt(String.valueOf(map.get("sign")));
				map.put("sign", sign);// 支撑type 1：组织，2部门，农事sign 1：组织，2部门
				map.put("children", childList);// 设置子节点
				// 再进一步递归子节点的子节点
				map.put("orgid", fwkOrgid);
				// 查询支撑的id
				if (CommonConstantsUtil.sign_org == sign) {// 当前是组织
					orgService.correspondSupportRecord(map);
					OrgEXT orgEXT = orgService.queryOrgBySupportId(supportid);
					if (orgEXT != null) {
						map.put("chargeuserid", orgEXT.getBelonguserid() == null ? "" : orgEXT.getBelonguserid());//
						map.put("id", orgEXT.getId());//
					}
				} else if (CommonConstantsUtil.sign_dept == sign) {// 当前是部门
					Object parentid = map.get("parentid");
					Integer fwkParentid = null;
					if (ObjectUtil.isNotBlank(parentid)) {
						map.remove("parentid");// 现将支撑的parentid移除，字符串类型，后增加农事的parentid
												// int类型

						Map<String, Object> parentParamMap = new HashMap<String, Object>();
						parentParamMap.put("id", parentid);
						List<Map<String, Object>> parentList = supportOrgService.queryDepartment(parentParamMap);
						if (!CollectionUtils.isEmpty(parentList)) {
							Map<String, Object> parentMap = parentList.get(0);
							int parentSign = Integer.parseInt(String.valueOf(parentMap.get("sign")));
							if (CommonConstantsUtil.sign_dept == parentSign) {// 上级是部门
								DepartmentEXT parentDeptEXT = departmentService.queryOrgBySupportId(parentid);
								if (parentDeptEXT != null) {// 上级部门不为空
									map.put("parentid", parentDeptEXT.getId());
									fwkParentid = parentDeptEXT.getId();
								}
							}
						}
					}
					departmentService.correspondSupportRecord(map);
					DepartmentEXT departmentEXT = departmentService.queryOrgBySupportId(supportid);
					if (departmentEXT != null) {
						map.put("chargeuserid", departmentEXT.getChargeuserid() == null ? "" : departmentEXT.getChargeuserid());//
						map.put("id", departmentEXT.getId());//
					}
					map.put("parentid", fwkParentid);// 现将支撑的parentid移除，字符串类型，后增加农事的parentid
														// int类型

				}
				// 设置子节点
				buildChildNode(childList, issOrgid, fwkOrgid);
			}
		}
	}

	/**
	 * 创建子节点
	 * 
	 * @author wangjunwen
	 * @date 2018年1月30日 上午9:07:09
	 *
	 * @param pDepartmentEXTList
	 *            部门列表
	 */
	public void buildChildNodeBak(List<DepartmentEXT> pDepartmentEXTList) {
		if ((null != pDepartmentEXTList) && (pDepartmentEXTList.size() > 0)) {
			for (DepartmentEXT pDepartmentEXT : pDepartmentEXTList) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("parentid", pDepartmentEXT.getId().toString());
				List<DepartmentEXT> childDepartmentEXTList = departmentService.queryDepartment(paramMap);
				// 设置子节点
				pDepartmentEXT.setChildren(childDepartmentEXTList);
				// 再进一步递归子节点的子节点
				buildChildNodeBak(childDepartmentEXTList);
			}
		}
	}

	/**
	 * 查询部门详情
	 */
	// @RequestMapping("/queryDepartmentDetail")
	public void queryDepartmentDetailBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("departmentid"))) {
			Map<String, Object> queryDepartmentDetailMap = new HashMap<String, Object>();
			queryDepartmentDetailMap.put("id", requestMap.get("departmentid"));
			DepartmentEXT departmentEXT = departmentService.queryDepartmentDetail(queryDepartmentDetailMap);
			resultWrap.setData(departmentEXT);
		} else {
			resultWrap.setParamIsnotEmpty("{departmentid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 查询部门详情
	 */
	@RequestMapping("/queryDepartmentDetail")
	public void queryDepartmentDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("departmentid"))) {
			Map<String, Object> queryDepartmentDetailMap = new HashMap<String, Object>();
			queryDepartmentDetailMap.put("id", requestMap.get("departmentid"));
			DepartmentEXT departmentEXT = departmentService.queryDepartmentDetail(queryDepartmentDetailMap);
			if (departmentEXT != null) {
				String supportid = departmentEXT.getSupportid();
				if (StringUtils.isNotBlank(supportid)) {
					requestMap.put("id", supportid);
					Map<String, Object> deptMap = supportOrgService.queryDepartmentDetail(requestMap);
					if (!CollectionUtils.isEmpty(deptMap)) {
						deptMap.put("chargeuserid", departmentEXT.getChargeuserid() == null ? "" : departmentEXT.getChargeuserid());
						deptMap.put("edittime", departmentEXT.getEdittime() == null ? "" : departmentEXT.getEdittime());
						deptMap.put("id", departmentEXT.getId());
						// 在支撑里找到所属的组织，然后根据对应的记录找到农事的组织
						Map<String, Object> orgMap = supportOrgService.queryDeptBelongOrg(deptMap.get("supportid"));
						if (CollectionUtils.isEmpty(orgMap)) {
							deptMap.put("orgid", "");
						} else {
							Object orgid = orgMap.get("supportid");
							OrgEXT orgEXT = orgService.queryOrgBySupportId(orgid);
							deptMap.put("orgid", orgEXT == null ? "" : orgEXT.getId());
						}
						// 根据支撑的parentid，找到农事的parentid
						DepartmentEXT parentDeptEXT = departmentService.queryOrgBySupportId(deptMap.get("parentid"));
						deptMap.put("parentid", parentDeptEXT == null ? "" : parentDeptEXT.getId());
					}
					resultWrap.setData(deptMap);
					// 保存对应的记录 id--》supportid
					// if (!CollectionUtils.isEmpty(deptMap)) {
					// departmentService.correspondSupportRecord(deptMap);
					// }
				}

			}

		} else {
			resultWrap.setParamIsnotEmpty("{departmentid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 新增子部门
	 */
	// @RequestMapping("/saveDepartment")
	public void saveDepartmentBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("orgid")) && ObjectUtil.isNotBlank(requestMap.get("name"))) {
			if (ObjectUtil.isBlank(requestMap.get("parentid"))) {
				requestMap.put("parentid", 0);
			}
			if (ObjectUtil.isBlank(requestMap.get("id"))) {// id为空，添加
				int result = departmentService.saveDepartment(requestMap);
				if (result >= 1) {
					resultWrap.setSaveSuccess();
				} else {
					resultWrap.setSaveFailure();
				}
			} else {// id不为空，修改
				int result = departmentService.updateDepartment(requestMap);
				if (result >= 1) {
					resultWrap.setUpdateSuccess();
				} else {
					resultWrap.setUpdateFailure();
				}
			}
		} else {
			resultWrap.setParamIsnotEmpty("{组织编号,名称}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 新增子部门
	 */
	@RequestMapping("/saveDepartment")
	public void saveDepartment(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("orgid")) && ObjectUtil.isNotBlank(requestMap.get("name"))) {
			/*
			 * if (ObjectUtil.isBlank(requestMap.get("parentid"))) {
			 * requestMap.put("parentid", 0); }
			 */
			Object issParentId = null;
			Object parentid = requestMap.get("parentid");
			Object orgId = requestMap.get("orgid");// 为登陆的组织id，为支撑的tenant的id
			if (ObjectUtil.isBlank(parentid)) {// 翻译成支撑的
												// parentid，农事的parentid为空，支撑的parentid为orgid
				// Map<String, Object> requestOrgMap = new HashMap<String,
				// Object>();
				// requestOrgMap.put("id", orgId);
				// List<OrgEXT> orgList = orgService.queryOrg(requestOrgMap);
				// if (!CollectionUtils.isEmpty(orgList)) {
				// OrgEXT orgEXT = orgList.get(0);
				// issParentId = orgEXT.getSupportid();
				// }
				issParentId = orgId;
			} else {
				Map<String, Object> requestDeptMap = new HashMap<String, Object>();
				requestDeptMap.put("id", parentid);
				List<DepartmentEXT> deptList = departmentService.queryDepartment(requestDeptMap);
				if (!CollectionUtils.isEmpty(deptList)) {
					DepartmentEXT departmentEXT = deptList.get(0);
					issParentId = departmentEXT.getSupportid();
				}
				// issParentId = parentid;
			}

			if (ObjectUtil.isBlank(requestMap.get("id"))) {// id为空，添加
				if (!ObjectUtil.isBlank(issParentId)) {// 上级部门有对应的记录
					requestMap.put("parentid", issParentId);
					requestMap.put("type", CommonConstantsUtil.TENANT_TYPE_DEPT);
					ResultWrap resultObj = supportOrgService.addDepartment(requestMap);
					if (ObjectUtil.isNotBlank(resultObj) && resultObj.getStatusCode() == HttpStatus.SC_OK) {
						// 设置支撑的ID
						JSONObject dataJson = JSONObject.parseObject(resultObj.getData().toString());
						String supportid = SupportUtil.supportGetJsonOid(dataJson);
						OrgEXT orgEXT = orgService.queryOrgBySupportId(orgId);
						if (orgEXT != null) {
							requestMap.put("orgid", orgEXT.getId());
						}
						requestMap.put("parentid", parentid);
						requestMap.put("supportid", supportid);
						departmentService.saveDepartment(requestMap);
						resultWrap.setSaveSuccess();
					} else {
						resultWrap.setSaveFailure();
					}
				} else {
					resultWrap.setSaveFailure();
				}

			} else {// id不为空，修改
				if (!ObjectUtil.isBlank(issParentId)) {// 上级部门有对应的记录
					requestMap.put("parentid", issParentId);
					Map<String, Object> queryDept = new HashMap<String, Object>();
					Object id = requestMap.get("id");// 根据农事的Id查询支撑的Id
					queryDept.put("id", id);
					List<DepartmentEXT> deptList = departmentService.queryDepartment(queryDept);
					if (!CollectionUtils.isEmpty(deptList)) {
						DepartmentEXT departmentEXT = deptList.get(0);
						String supportid = departmentEXT.getSupportid();
						requestMap.put("id", supportid);// 换成支撑的Id进行更新
						Object resultObj = supportOrgService.updateDepartment(requestMap);
						if (ObjectUtil.isNotBlank(resultObj) && String.valueOf(resultObj).indexOf("请求失败") == -1) {
							// requestMap.put("supportid", resultObj);
							requestMap.put("id", id);// 还原为农事的id
							departmentService.updateDepartment(requestMap);
							resultWrap.setUpdateSuccess();
						} else {
							resultWrap.setUpdateFailure();
						}
					}
				} else {
					resultWrap.setUpdateFailure();
				}
			}
		} else {
			resultWrap.setParamIsnotEmpty("{组织编号,名称}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 删除部门
	 */
	// @RequestMapping("/deleteDepartment")
	public void deleteDepartmentBak(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			requestMap.put("delstate", 0);
			int result = departmentService.updateDepartment(requestMap);
			if (result >= 1) {
				resultWrap.setDeleteSuccess();
			} else {
				resultWrap.setDeleteFailure();
			}
		} else {
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}

	/**
	 * 删除部门
	 */
	@RequestMapping("/deleteDepartment")
	public void deleteDepartment(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		// 判断该部门下是否有成员
		if (ObjectUtil.isBlank(requestMap.get("deptSupportid"))) {
			resultWrap.setParamIsnotEmpty("{deptSupportid}");
			HTMLUtil.sendWrap(response, resultWrap, request);
			return;
		}
		Integer staffCount = supportOrgService.queryStaffCountByDeptSupportId(requestMap);
		if (staffCount > 0) {
			resultWrap.setDeleteFailure();
			resultWrap.setMessage("该部门下有成员，不允许删除!");
			HTMLUtil.sendWrap(response, resultWrap, request);
			return;
		}
		if (ObjectUtil.isNotBlank(requestMap.get("id"))) {
			// 将农事的id转换为支撑的id
			Map<String, Object> queryDept = new HashMap<String, Object>();
			Object id = requestMap.get("id");// 根据农事的Id查询支撑的Id
			queryDept.put("id", id);
			List<DepartmentEXT> deptList = departmentService.queryDepartment(queryDept);
			if (!CollectionUtils.isEmpty(deptList)) {
				DepartmentEXT departmentEXT = deptList.get(0);
				String supportid = departmentEXT.getSupportid();
				if (StringUtils.isNotBlank(supportid)) {// 转化为支撑的id
					requestMap.put("id", supportid);
					requestMap.put("delstate", CommonConstantsUtil.IS_DELETE_TRUE);
					Object resultObj = supportOrgService.deleteDepartment(requestMap);
					if (ObjectUtil.isNotBlank(resultObj) && String.valueOf(resultObj).indexOf("请求失败") == -1) {
						// requestMap.put("id", id);// 还原为农事的id
						// departmentService.updateDepartment(requestMap);
						resultWrap.setDeleteSuccess();
					} else {
						resultWrap.setDeleteFailure();
					}
				} else {
					resultWrap.setDeleteFailure();
				}
			} else {
				resultWrap.setDeleteFailure();
			}

		} else {
			resultWrap.setParamIsnotEmpty("{id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
}
