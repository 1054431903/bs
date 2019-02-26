package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFtaskWithBLOBs;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.Page;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserDepartmentInfo;

/**
 * 农事任务实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年2月7日 下午2:38:37
 *
 */
public class TaskEXT extends TFtaskWithBLOBs {

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
	private List<TaskparcelEXT> taskparcellist;
	// 参与人列表
	private List<TaskparticsEXT> taskparticslist;
	// 抄送人列表
	private List<TaskcopyEXT> taskcopylist;
	// 提醒方式列表
	private List<TaskwarnEXT> taskwarnlist;
	// 任务类别名称
	private String typename;
	// 任务状态名称
	private String taskstatusname;
	// 发布人的姓名
	private String createuseridname;
	// 发布人的昵称
	private String createuseridnickname;
	// 是否已存入农事
	private String savedrecordlabel = LanguageUtil.getLanguageByKey("fwk-farm-task-yesno-no");
	// 是否可存入农事记录
	private Boolean iscansavetorecord = false;
	// 所有参与人的姓名
	private String taskparticsusernames;

	// 任务参与人的ID（查询：我参与的任务）
	private Integer taskparticsuserid;
	// 任务抄送人的ID（查询：抄送我的任务）
	private Integer taskcopyuserid;
	// 任务发布人的ID（查询：我发布的任务，参数名：createuserid，已在父类中）

	// 农事参与人的参与状态
	private String taskparticsstatus;
	// 农事参与人的参与状态名称
	private String taskparticsstatusname;
	// 农事参与人的农事任务状态
	private String taskparticstaskstatus;
	// 农事参与人的农事任务状态名称
	private String taskparticstaskstatusname;

	// 农事任务的地块的ID
	private Integer parcelid;
	// 农事任务的地块的名称
	private String parcelidname;
	private String parcelidnames;
	private String parcelids;
	// 动态数量
	private Integer dynamiccount;

	// 农事参与人的确认参与状态
	private String confirmtaskparticsstatus;
	// 农事参与人的确认参与状态名称
	private String confirmtaskparticsstatusname;

	// 数据的创建人（数据权限）
	private String userpermisionscreateuserid;
	// 数据所属的组织（数据权限）
	private String userpermisionscreateuserorgid;
	private List<String> userpermisionscreateuserorgidlist;
	// 数据所属的部门（数据权限）
	private List<String> userpermisionscreateuserdepartmentidlist;
	// 当前登录用户所在组织的部门信息
	private List<LoginUserDepartmentInfo> loginuserdepartmentinfolist;
	// 任务状态列表
	private List<String> taskstatuslist;
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
	// 农事记录的ID
	private String recordeid;
	// 所有农事任务参与人
	private String taskparticsuserids;
	// 开始时间的字符串
	private String begintimestr;
	// 结束时间的字符串
	private String endtimestr;
	// 抄送人的用户ID
	private String taskcopyuserids;
	// 是否已读的标志
	private Integer hasreaded;
	// 我参与的任务的状态
	private String taskparticsusertaskstatus;
	// 我参与的任务的状态列表
	private List<String> taskparticsusertaskstatuslist;
	// 创建人对应的支撑平台的用户ID
	private String createusersupportid;
	// 该数据在查询结果中所在的行号
	private Integer rownumberfromsearchresult;
	// 按权限查询的SQL条件
	private String permisionssqlwhere;
	private List<Integer> recordeidlist;
	//xx:获取null状态的任务
	private String nulldatestate;

	public String getNulldatestate() {
		return nulldatestate;
	}

	public void setNulldatestate(String nulldatestate) {
		this.nulldatestate = nulldatestate;
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

	public List<TaskparcelEXT> getTaskparcellist() {
		return taskparcellist;
	}

	public void setTaskparcellist(List<TaskparcelEXT> taskparcellist) {

		if ((null != taskparcellist) && (taskparcellist.size() > 0)) {
			StringBuffer parcelIdBuffer = new StringBuffer();

			for (int i = 0; i < taskparcellist.size(); i++) {
				if (i > 0) {
					parcelIdBuffer.append(",");
				}

				//parcelIdBuffer.append(taskparcellist.get(i).getId());
				parcelIdBuffer.append(taskparcellist.get(i).getParcelid());
			}

			this.setParcelids(parcelIdBuffer.toString());
		}

		this.taskparcellist = taskparcellist;
	}

	public List<TaskparticsEXT> getTaskparticslist() {
		return taskparticslist;
	}

	public void setTaskparticslist(List<TaskparticsEXT> taskparticslist) {
		this.taskparticslist = taskparticslist;
	}

	public List<TaskcopyEXT> getTaskcopylist() {
		return taskcopylist;
	}

	public void setTaskcopylist(List<TaskcopyEXT> taskcopylist) {
		this.taskcopylist = taskcopylist;
	}

	public List<TaskwarnEXT> getTaskwarnlist() {
		return taskwarnlist;
	}

	public void setTaskwarnlist(List<TaskwarnEXT> taskwarnlist) {
		this.taskwarnlist = taskwarnlist;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTaskstatusname() {
		return taskstatusname;
	}

	public void setTaskstatusname(String taskstatusname) {
		this.taskstatusname = taskstatusname;
	}

	public String getCreateuseridname() {
		return createuseridname;
	}

	public void setCreateuseridname(String createuseridname) {
		this.createuseridname = createuseridname;
	}

	public String getCreateuseridnickname() {
		return createuseridnickname;
	}

	public void setCreateuseridnickname(String createuseridnickname) {
		this.createuseridnickname = createuseridnickname;
	}

	public String getSavedrecordlabel() {
		return savedrecordlabel;
	}

	public void setSavedrecordlabel(String savedrecordlabel) {
		this.savedrecordlabel = savedrecordlabel;
	}

	public Boolean getIscansavetorecord() {
		return iscansavetorecord;
	}

	public void setIscansavetorecord(Boolean iscansavetorecord) {
		this.iscansavetorecord = iscansavetorecord;
	}

	public String getTaskparticsusernames() {
		return taskparticsusernames;
	}

	public void setTaskparticsusernames(String taskparticsusernames) {
		this.taskparticsusernames = taskparticsusernames;
	}

	public Integer getTaskparticsuserid() {
		return taskparticsuserid;
	}

	public void setTaskparticsuserid(Integer taskparticsuserid) {
		this.taskparticsuserid = taskparticsuserid;
	}

	public Integer getTaskcopyuserid() {
		return taskcopyuserid;
	}

	public void setTaskcopyuserid(Integer taskcopyuserid) {
		this.taskcopyuserid = taskcopyuserid;
	}

	public String getTaskparticsstatus() {
		return taskparticsstatus;
	}

	public void setTaskparticsstatus(String taskparticsstatus) {
		this.taskparticsstatus = taskparticsstatus;
	}

	public String getTaskparticsstatusname() {
		return taskparticsstatusname;
	}

	public void setTaskparticsstatusname(String taskparticsstatusname) {
		this.taskparticsstatusname = taskparticsstatusname;
	}

	public String getTaskparticstaskstatus() {
		return taskparticstaskstatus;
	}

	public void setTaskparticstaskstatus(String taskparticstaskstatus) {
		this.taskparticstaskstatus = taskparticstaskstatus;
	}

	public String getTaskparticstaskstatusname() {
		return taskparticstaskstatusname;
	}

	public void setTaskparticstaskstatusname(String taskparticstaskstatusname) {
		this.taskparticstaskstatusname = taskparticstaskstatusname;
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

	public String getParcelids() {
		return parcelids;
	}

	public void setParcelids(String parcelids) {
		this.parcelids = parcelids;
	}

	public Integer getDynamiccount() {
		return dynamiccount;
	}

	public void setDynamiccount(Integer dynamiccount) {
		this.dynamiccount = dynamiccount;
	}

	public String getConfirmtaskparticsstatus() {
		return confirmtaskparticsstatus;
	}

	public void setConfirmtaskparticsstatus(String confirmtaskparticsstatus) {
		this.confirmtaskparticsstatus = confirmtaskparticsstatus;
	}

	public String getConfirmtaskparticsstatusname() {
		return confirmtaskparticsstatusname;
	}

	public void setConfirmtaskparticsstatusname(String confirmtaskparticsstatusname) {
		this.confirmtaskparticsstatusname = confirmtaskparticsstatusname;
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

	public List<String> getTaskstatuslist() {
		return taskstatuslist;
	}

	public void setTaskstatuslist(List<String> taskstatuslist) {
		this.taskstatuslist = taskstatuslist;
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

	public String getRecordeid() {
		return recordeid;
	}

	public void setRecordeid(String recordeid) {

		@SuppressWarnings("unchecked")
		List<Integer> recordIdList = ObjectUtil.convertToNumberList(recordeid, Integer.class);

		this.setRecordeidlist(recordIdList);

		this.recordeid = recordeid;
	}

	public String getTaskparticsuserids() {
		return taskparticsuserids;
	}

	public void setTaskparticsuserids(String taskparticsuserids) {
		this.taskparticsuserids = taskparticsuserids;
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

	public String getTaskcopyuserids() {
		return taskcopyuserids;
	}

	public void setTaskcopyuserids(String taskcopyuserids) {
		this.taskcopyuserids = taskcopyuserids;
	}

	public Integer getHasreaded() {
		return hasreaded;
	}

	public void setHasreaded(Integer hasreaded) {
		this.hasreaded = hasreaded;
	}

	public String getTaskparticsusertaskstatus() {
		return taskparticsusertaskstatus;
	}

	public void setTaskparticsusertaskstatus(String taskparticsusertaskstatus) {
		this.taskparticsusertaskstatus = taskparticsusertaskstatus;
	}

	public List<String> getTaskparticsusertaskstatuslist() {
		return taskparticsusertaskstatuslist;
	}

	public void setTaskparticsusertaskstatuslist(List<String> taskparticsusertaskstatuslist) {
		this.taskparticsusertaskstatuslist = taskparticsusertaskstatuslist;
	}

	public String getCreateusersupportid() {
		return createusersupportid;
	}

	public void setCreateusersupportid(String createusersupportid) {
		this.createusersupportid = createusersupportid;
	}

	public Integer getRownumberfromsearchresult() {
		return rownumberfromsearchresult;
	}

	public void setRownumberfromsearchresult(Integer rownumberfromsearchresult) {
		this.rownumberfromsearchresult = rownumberfromsearchresult;
	}

	public String getPermisionssqlwhere() {
		return permisionssqlwhere;
	}

	public void setPermisionssqlwhere(String permisionssqlwhere) {
		this.permisionssqlwhere = permisionssqlwhere;
	}

	public List<Integer> getRecordeidlist() {
		return recordeidlist;
	}

	public void setRecordeidlist(List<Integer> recordeidlist) {
		this.recordeidlist = recordeidlist;
	}

}
