package com.fwk.service.farm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.SupportOrgEXT;
import com.fwk.service.common.service.CommonService;

/**
 * 农事管理模块的公共业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年4月16日 下午2:54:43
 *
 */
@Service
public class FarmCommonService {

	// 公共业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 设置数据的查询权限（数据权限为当前用户所拥有的所有组织和部门）
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 上午9:13:43
	 *
	 * @param loginUserInfo
	 *            当前操作的用户
	 * @param db_colum_particsuserids
	 *            数据库表的列名（所有参与人的ID）
	 */
	public String qetQueryPermisionsAllOrgAndDepartment(final LoginUserInfo loginUserInfo,
			final String db_colum_particsuserids) {
		if ((null == loginUserInfo)) {
			return "";
		}

		// 设置当前登录用户所拥有的组织和部门
		mCommonService.setLoginUserOwnerOrgAndDepartment(loginUserInfo);

		List<SupportOrgEXT> supportOrgEXTList = null;

		final StringBuffer permisionssqlwhereBuffer = new StringBuffer();

		// -------------- 设置查询数据权限 -----------
		// 组织管理员可以查看本组织以及所有的子组织的数据
		final List<String> userpermisionscreateuserorgidlist = new ArrayList<String>();

		if ((null != loginUserInfo.getOwnerorglist()) && (loginUserInfo.getOwnerorglist().size() > 0)) {

			for (SupportOrgEXT org : loginUserInfo.getOwnerorglist()) {
				if (org.isCurrentlonginuserisadmin()) {// 是组织的管理员，可以查看该组织以及所有子组织的数据
					userpermisionscreateuserorgidlist.add(org.getSupportid());

					// 根据父级获取所有的子级
					supportOrgEXTList = mCommonService.queryAllSubOrgOrDepartment(org.getSupportid());

					if ((null != supportOrgEXTList) && (supportOrgEXTList.size() > 0)) {
						for (SupportOrgEXT subOrg : supportOrgEXTList) {
							userpermisionscreateuserorgidlist.add(subOrg.getSupportid());
						}
					}
				}
			}
		}

		// 部门管理员可以查看本部门以及所有的子部门的数据
		final List<String> userpermisionscreateuserdepartmentidlist = new ArrayList<String>();

		if ((null != loginUserInfo.getOwnerdepartmentlist()) && (loginUserInfo.getOwnerdepartmentlist().size() > 0)) {
			for (SupportOrgEXT department : loginUserInfo.getOwnerdepartmentlist()) {
				if (department.isCurrentlonginuserisadmin()) {// 是部门的管理员，可以查看该部门以及所有子部门的数据
					userpermisionscreateuserdepartmentidlist.add(department.getSupportid());

					// 根据父级获取所有的子级
					supportOrgEXTList = mCommonService.queryAllSubOrgOrDepartment(department.getSupportid());

					if ((null != supportOrgEXTList) && (supportOrgEXTList.size() > 0)) {
						for (SupportOrgEXT subDepartment : supportOrgEXTList) {
							userpermisionscreateuserdepartmentidlist.add(subDepartment.getSupportid());
						}
					}
				}
			}
		}

		permisionssqlwhereBuffer.append(" AND ( ");// AND 开始

		// 管理员可以查看该管理的组织以及部门的所有子级的数据
		for (int i = 0; i < userpermisionscreateuserorgidlist.size(); i++) {

			permisionssqlwhereBuffer.append((i > 0 ? " OR " : ""))
					.append(" userpermisionscreateuserorgid LIKE CONCAT('%', '")
					.append(userpermisionscreateuserorgidlist.get(i)).append("', '%') ");
		}

		for (int i = 0; i < userpermisionscreateuserdepartmentidlist.size(); i++) {

			permisionssqlwhereBuffer.append((i > 0 ? " OR " : ""))
					.append(" userpermisionscreateuserdepartmentidlist LIKE CONCAT('%', '")
					.append(userpermisionscreateuserdepartmentidlist.get(i)).append("' ");
		}

		// 非管理员只能查看自己创建的或者参与的记录
		permisionssqlwhereBuffer.append((permisionssqlwhereBuffer.toString().contains(" OR ") ? " OR " : " "))
				.append(" userpermisionscreateuserid LIKE CONCAT('%', '").append(loginUserInfo.getId())
				.append("', '%') ").append(" OR ").append(db_colum_particsuserids).append(" LIKE CONCAT('%,', '")
				.append(loginUserInfo.getId()).append("', ',%') ");

		permisionssqlwhereBuffer.append(" ) ");// AND 结束

		return permisionssqlwhereBuffer.toString();
	}

	/**
	 * 设置数据的查询权限（数据权限只能在当前登录的组织下）
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 上午9:13:43
	 *
	 * @param loginUserInfo
	 *            当前操作的用户
	 * @param db_colum_particsuserids
	 *            数据库表的列名（所有参与人的ID）
	 */
	public String getQueryPermisionsOnlyLoginOrg(final LoginUserInfo loginUserInfo,
			final String db_colum_particsuserids) {
		if ((null == loginUserInfo)) {
			return "";
		}

		// 设置当前登录用户所拥有的组织和部门
		mCommonService.setLoginUserCurrentLoginOrgAndDepartment(loginUserInfo);

		List<SupportOrgEXT> supportOrgEXTList = null;

		StringBuffer permisionssqlwhereBuffer = new StringBuffer();

		// -------------- 设置查询数据权限 -----------

		// 部门管理员可以查看本部门以及所有的子部门的数据
		final List<String> userpermisionscreateuserdepartmentidlist = new ArrayList<String>();

		if ((null != loginUserInfo.getOwnerdepartmentlist()) && (loginUserInfo.getOwnerdepartmentlist().size() > 0)) {
			for (SupportOrgEXT department : loginUserInfo.getOwnerdepartmentlist()) {
				if (department.isCurrentlonginuserisadmin()) {// 是部门的管理员，可以查看该部门以及所有子部门的数据
					userpermisionscreateuserdepartmentidlist.add(department.getSupportid());

					// 根据父级获取所有的子级
					supportOrgEXTList = mCommonService.queryAllSubOrgOrDepartment(department.getSupportid());

					if ((null != supportOrgEXTList) && (supportOrgEXTList.size() > 0)) {
						for (SupportOrgEXT subDepartment : supportOrgEXTList) {
							userpermisionscreateuserdepartmentidlist.add(subDepartment.getSupportid());
						}
					}
				}
			}
		}

		// 只能查看当前登录的组织的数据
		permisionssqlwhereBuffer.append(" AND userpermisionscreateuserorgid LIKE CONCAT('%', '")
				.append(loginUserInfo.getLoginorgid()).append("', '%') ").append(" AND ( ");// AND
																							// 开始

		for (int i = 0; i < userpermisionscreateuserdepartmentidlist.size(); i++) {

			permisionssqlwhereBuffer.append((i > 0 ? " OR " : ""))
					.append(" userpermisionscreateuserdepartmentidlist = '")
					.append(userpermisionscreateuserdepartmentidlist.get(i)).append("' ");
		}

		// 非管理员只能查看自己创建的或者参与的记录
		permisionssqlwhereBuffer.append((permisionssqlwhereBuffer.toString().contains(" OR ") ? " OR " : " "))
				.append(" userpermisionscreateuserid LIKE CONCAT('%', '").append(loginUserInfo.getId())
				.append("', '%') ").append(" OR ").append(db_colum_particsuserids).append(" LIKE CONCAT('%,', '")
				.append(loginUserInfo.getId()).append("', ',%') ");

		//xx:拥有查询权限的用户id集合
		if(ObjectUtil.isNotBlank(loginUserInfo.getUserIds()) && loginUserInfo.getUserIds().size() > 0) {
			Object userids = "";
			for (int i = 0; i < loginUserInfo.getUserIds().size(); i++) {
				userids+=loginUserInfo.getUserIds().get(i)+",";
			}
			if(userids.toString().contains(",")) userids = userids.toString().substring(0, userids.toString().length()-1);
			permisionssqlwhereBuffer.append((permisionssqlwhereBuffer.toString().contains(" OR ") ? " OR " : " "))
			.append(" userpermisionscreateuserid in (")
			.append(userids)
			.append(")");
		}
		
		permisionssqlwhereBuffer.append(" ) ");// AND 结束

		return permisionssqlwhereBuffer.toString();
	}

}
