package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFplanWithBLOBs;
import com.fwk.common.util.Page;
import com.fwk.service.common.entity.LoginUserDepartmentInfo;

/**
 * 农事计划实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月23日 上午11:30:22
 *
 */
public class PlanEXT extends TFplanWithBLOBs {

	// 分页查询对象
	private Page page;
	// 排序的SQL语句
	private String orderBySQL;
	// 开始时间
	private String createdatestart;
	// 截至时间
	private String createdateend;
	// 业务主键列表
	private List<Integer> idlist;
	// 查询的关键字（记录编号、农户名称）
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 地块列表
	private List<PlanparcelEXT> planparcellist;
	// 参与人列表
	private List<PlanparticsEXT> planparticslist;
	// 提醒方式列表
	private List<PlanwarnEXT> planwarnlist;
	// 参与人的用户ID
	private Integer planparticsuserid;
	// 农事计划参与人的姓名
	private String planparticsusernames;
	// 农事计划的地块的ID
	private Integer parcelid;
	// 农事计划的地块的名称
	private String parcelidname;
	private String parcelidnames;
	// 开始时间的字符串
	private String begintimestr;
	// 结束时间的字符串
	private String endtimestr;

	// 数据的创建人（数据权限）
	private String userpermisionscreateuserid;
	// 数据所属的组织（数据权限）
	private String userpermisionscreateuserorgid;
	private List<String> userpermisionscreateuserorgidlist;
	// 数据所属的部门（数据权限）
	private List<String> userpermisionscreateuserdepartmentidlist;
	// 当前登录用户所在组织的部门信息
	private List<LoginUserDepartmentInfo> loginuserdepartmentinfolist;
	// 小于开始时间（用于查询开始时间小于此时间的数据）
	private Date lessthanbegintime;
	// 大于开始时间（用于查询开始时间大于此时间的数据）
	private Date greatthanbegintime;
	// 小于结束时间（用于查询结束时间小于此时间的数据）
	private Date lessthanendtime;
	// 大于结束时间（用于查询结束时间大于此时间的数据）
	private Date greatthanendtime;
	// 介于开始时间（用于查询开始时间介于此时间的数据-开始）
	private Date betweenstartbegintime;
	// 介于结束时间（用于查询开始时间介于此时间的数据-截止）
	private Date betweenendbegintime;
	// 介于结束时间（用于查询结束时间介于此时间的数据-开始）
	private Date betweenstartendtime;
	// 介于结束时间（用于查询结束时间介于此时间的数据-截止）
	private Date betweenendendtime;
	// 创建人的姓名
	private String createuseridname;
	// 所有参与人的用户ID
	private String planparticsuserids;
	// 是否已读的标志
	private Integer hasreaded;
	// 按权限查询的SQL条件
	private String permisionssqlwhere;
	//xx:可查询用户列表
	private List<Integer> userIds;

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getOrderBySQL() {
		return orderBySQL;
	}

	public void setOrderBySQL(String orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

	public String getCreatedatestart() {
		return createdatestart;
	}

	public void setCreatedatestart(String createdatestart) {
		this.createdatestart = createdatestart;
	}

	public String getCreatedateend() {
		return createdateend;
	}

	public void setCreatedateend(String createdateend) {
		this.createdateend = createdateend;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

	public List<PlanparcelEXT> getPlanparcellist() {
		return planparcellist;
	}

	public void setPlanparcellist(List<PlanparcelEXT> planparcellist) {
		this.planparcellist = planparcellist;
	}

	public List<PlanparticsEXT> getPlanparticslist() {
		return planparticslist;
	}

	public void setPlanparticslist(List<PlanparticsEXT> planparticslist) {
		this.planparticslist = planparticslist;
	}

	public List<PlanwarnEXT> getPlanwarnlist() {
		return planwarnlist;
	}

	public void setPlanwarnlist(List<PlanwarnEXT> planwarnlist) {
		this.planwarnlist = planwarnlist;
	}

	public Integer getPlanparticsuserid() {
		return planparticsuserid;
	}

	public void setPlanparticsuserid(Integer planparticsuserid) {
		this.planparticsuserid = planparticsuserid;
	}

	public Integer getParcelid() {
		return parcelid;
	}

	public void setParcelid(Integer parcelid) {
		this.parcelid = parcelid;
	}

	public String getParcelidname() {
		return parcelidname;
	}

	public void setParcelidname(String parcelidname) {
		this.parcelidname = parcelidname;
	}

	public String getParcelidnames() {
		return parcelidnames;
	}

	public void setParcelidnames(String parcelidnames) {
		this.parcelidnames = parcelidnames;
	}

	public String getPlanparticsusernames() {
		return planparticsusernames;
	}

	public void setPlanparticsusernames(String planparticsusernames) {
		this.planparticsusernames = planparticsusernames;
	}

	public String getBegintimestr() {
		return begintimestr;
	}

	public void setBegintimestr(String begintimestr) {
		this.begintimestr = begintimestr;
	}

	public String getEndtimestr() {
		return endtimestr;
	}

	public void setEndtimestr(String endtimestr) {
		this.endtimestr = endtimestr;
	}

	public String getUserpermisionscreateuserid() {
		return userpermisionscreateuserid;
	}

	public void setUserpermisionscreateuserid(String userpermisionscreateuserid) {
		this.userpermisionscreateuserid = userpermisionscreateuserid;
	}

	public String getUserpermisionscreateuserorgid() {
		return userpermisionscreateuserorgid;
	}

	public void setUserpermisionscreateuserorgid(String userpermisionscreateuserorgid) {
		this.userpermisionscreateuserorgid = userpermisionscreateuserorgid;
	}

	public List<String> getUserpermisionscreateuserorgidlist() {
		return userpermisionscreateuserorgidlist;
	}

	public void setUserpermisionscreateuserorgidlist(List<String> userpermisionscreateuserorgidlist) {
		this.userpermisionscreateuserorgidlist = userpermisionscreateuserorgidlist;
	}

	public List<String> getUserpermisionscreateuserdepartmentidlist() {
		return userpermisionscreateuserdepartmentidlist;
	}

	public void setUserpermisionscreateuserdepartmentidlist(List<String> userpermisionscreateuserdepartmentidlist) {
		this.userpermisionscreateuserdepartmentidlist = userpermisionscreateuserdepartmentidlist;
	}

	public List<LoginUserDepartmentInfo> getLoginuserdepartmentinfolist() {
		return loginuserdepartmentinfolist;
	}

	public void setLoginuserdepartmentinfolist(List<LoginUserDepartmentInfo> loginuserdepartmentinfolist) {
		this.loginuserdepartmentinfolist = loginuserdepartmentinfolist;
	}

	public Date getLessthanbegintime() {
		return lessthanbegintime;
	}

	public void setLessthanbegintime(Date lessthanbegintime) {
		this.lessthanbegintime = lessthanbegintime;
	}

	public Date getGreatthanbegintime() {
		return greatthanbegintime;
	}

	public void setGreatthanbegintime(Date greatthanbegintime) {
		this.greatthanbegintime = greatthanbegintime;
	}

	public Date getLessthanendtime() {
		return lessthanendtime;
	}

	public void setLessthanendtime(Date lessthanendtime) {
		this.lessthanendtime = lessthanendtime;
	}

	public Date getGreatthanendtime() {
		return greatthanendtime;
	}

	public void setGreatthanendtime(Date greatthanendtime) {
		this.greatthanendtime = greatthanendtime;
	}

	public Date getBetweenstartbegintime() {
		return betweenstartbegintime;
	}

	public void setBetweenstartbegintime(Date betweenstartbegintime) {
		this.betweenstartbegintime = betweenstartbegintime;
	}

	public Date getBetweenendbegintime() {
		return betweenendbegintime;
	}

	public void setBetweenendbegintime(Date betweenendbegintime) {
		this.betweenendbegintime = betweenendbegintime;
	}

	public Date getBetweenstartendtime() {
		return betweenstartendtime;
	}

	public void setBetweenstartendtime(Date betweenstartendtime) {
		this.betweenstartendtime = betweenstartendtime;
	}

	public Date getBetweenendendtime() {
		return betweenendendtime;
	}

	public void setBetweenendendtime(Date betweenendendtime) {
		this.betweenendendtime = betweenendendtime;
	}

	public String getCreateuseridname() {
		return createuseridname;
	}

	public void setCreateuseridname(String createuseridname) {
		this.createuseridname = createuseridname;
	}

	public String getPlanparticsuserids() {
		return planparticsuserids;
	}

	public void setPlanparticsuserids(String planparticsuserids) {
		this.planparticsuserids = planparticsuserids;
	}

	public Integer getHasreaded() {
		return hasreaded;
	}

	public void setHasreaded(Integer hasreaded) {
		this.hasreaded = hasreaded;
	}

	public String getPermisionssqlwhere() {
		return permisionssqlwhere;
	}

	public void setPermisionssqlwhere(String permisionssqlwhere) {
		this.permisionssqlwhere = permisionssqlwhere;
	}

}
