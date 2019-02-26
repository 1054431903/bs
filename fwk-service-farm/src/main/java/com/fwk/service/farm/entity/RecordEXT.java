package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFrecord;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.OrderByInfo;
import com.fwk.common.util.Page;
import com.fwk.service.common.entity.LoginUserDepartmentInfo;
import com.fwk.service.common.entity.ResourcehistoryEXT;

/**
 * 农事记录实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年1月30日 上午11:02:04
 *
 */
public class RecordEXT extends TFrecord {

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

	// 农事记录的地块列表
	private List<RecordparcelEXT> recordparcellist;
	// 农事记录的参人列表
	private List<RecordpartuserEXT> recordpartuserlist;
	// 农事记录的投入品列表
	private List<OutputEXT> outputlist;
	// 农事记录的附件列表
	private List<ResourcehistoryEXT> resourcehistorylist;
	// 农事类别名称
	private String farmtypename;
	// 地块的ID
	private Integer parcelid;
	// 地块的名称
	private String parcelidname;
	// 农事记录的资源文件列表
	private List<RecordresourceEXT> recordresourceEXTList;
	// 记录来源的名称
	private String sourcedictvaluename;
	// 参与人的姓名
	private String recordpartusernames;
	// 农事类别列表
	private List<RecordfarmtypeEXT> farmtypelist;
	// 农事类别名称
	private String farmtypenames;
	// 农事类别名称列表
	private List<String> farmtypenamelist;
	// 农事记录的音频资源文件资源文件列表（从农事记录的资源文件中抽取）
	private List<ResourcehistoryEXT> audioResourcehistorylist;
	// 农事记录的图片文件列表
	private List<ResourcehistoryEXT> imageResourcehistorylist;
	// 农事记录的其他未明确分类的文件列表
	private List<ResourcehistoryEXT> otherResourcehistorylist;
	// 该农事记录的所有投入品的名称
	private String recordoutputnames;
	// 用于排序的字段列表
	private List<OrderByInfo> orderbyinfolist;

	// 数据的创建人（数据权限）
	private String userpermisionscreateuserid;
	// 数据所属的组织（数据权限）
	private String userpermisionscreateuserorgid;
	private List<String> userpermisionscreateuserorgidlist;
	// 数据所属的部门（数据权限）
	private String userpermisionscreateuserdepartmentid;
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
	// 农事参与人
	private String recordpartuserids;
	// 是否已读的标志
	private Integer hasreaded;
	// 按权限查询的SQL条件
	private String permisionssqlwhere;
	// 农事记录的ID（每个ID之间以逗号分隔）
	private String recordids;
	
	//xx:排序方式,值：desc
	private String ordertype;
	//xx:是否允许有操作权限
	private boolean isallowoperate;
	//xx:地块id集
	private String parcelids;
	//xx:地块name集
	private String parcelnames;

	public String getParcelids() {
		return parcelids;
	}

	public void setParcelids(String parcelids) {
		this.parcelids = parcelids;
	}

	public String getParcelnames() {
		return parcelnames;
	}

	public void setParcelnames(String parcelnames) {
		this.parcelnames = parcelnames;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public boolean isIsallowoperate() {
		return isallowoperate;
	}

	public void setIsallowoperate(boolean isallowoperate) {
		this.isallowoperate = isallowoperate;
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

	public List<RecordparcelEXT> getRecordparcellist() {
		return recordparcellist;
	}

	public void setRecordparcellist(List<RecordparcelEXT> recordparcellist) {
		this.recordparcellist = recordparcellist;
	}

	public List<RecordpartuserEXT> getRecordpartuserlist() {
		return recordpartuserlist;
	}

	public void setRecordpartuserlist(List<RecordpartuserEXT> recordpartuserlist) {
		this.recordpartuserlist = recordpartuserlist;
	}

	public List<OutputEXT> getOutputlist() {
		return outputlist;
	}

	public void setOutputlist(List<OutputEXT> outputlist) {
		this.outputlist = outputlist;
	}

	public List<ResourcehistoryEXT> getResourcehistorylist() {
		return resourcehistorylist;
	}

	public void setResourcehistorylist(List<ResourcehistoryEXT> resourcehistorylist) {
		this.resourcehistorylist = resourcehistorylist;
	}

	public String getFarmtypename() {
		return farmtypename;
	}

	public void setFarmtypename(String farmtypename) {
		this.farmtypename = farmtypename;
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

	public List<RecordresourceEXT> getRecordresourceEXTList() {
		return recordresourceEXTList;
	}

	public void setRecordresourceEXTList(List<RecordresourceEXT> recordresourceEXTList) {
		this.recordresourceEXTList = recordresourceEXTList;
	}

	public String getSourcedictvaluename() {
		return sourcedictvaluename;
	}

	public void setSourcedictvaluename(String sourcedictvaluename) {
		this.sourcedictvaluename = sourcedictvaluename;
	}

	public String getRecordpartusernames() {
		return recordpartusernames;
	}

	public void setRecordpartusernames(String recordpartusernames) {
		this.recordpartusernames = recordpartusernames;
	}

	public List<RecordfarmtypeEXT> getFarmtypelist() {
		return farmtypelist;
	}

	public void setFarmtypelist(List<RecordfarmtypeEXT> farmtypelist) {
		this.farmtypelist = farmtypelist;
	}

	public String getFarmtypenames() {
		return farmtypenames;
	}

	public void setFarmtypenames(String farmtypenames) {
		this.farmtypenames = farmtypenames;
	}

	public List<String> getFarmtypenamelist() {
		return farmtypenamelist;
	}

	public void setFarmtypenamelist(List<String> farmtypenamelist) {
		this.farmtypenamelist = farmtypenamelist;
	}

	public List<ResourcehistoryEXT> getAudioResourcehistorylist() {
		return audioResourcehistorylist;
	}

	public void setAudioResourcehistorylist(List<ResourcehistoryEXT> audioResourcehistorylist) {
		this.audioResourcehistorylist = audioResourcehistorylist;
	}

	public List<ResourcehistoryEXT> getImageResourcehistorylist() {
		return imageResourcehistorylist;
	}

	public void setImageResourcehistorylist(List<ResourcehistoryEXT> imageResourcehistorylist) {
		this.imageResourcehistorylist = imageResourcehistorylist;
	}

	public List<ResourcehistoryEXT> getOtherResourcehistorylist() {
		return otherResourcehistorylist;
	}

	public void setOtherResourcehistorylist(List<ResourcehistoryEXT> otherResourcehistorylist) {
		this.otherResourcehistorylist = otherResourcehistorylist;
	}

	public String getRecordoutputnames() {
		return recordoutputnames;
	}

	public void setRecordoutputnames(String recordoutputnames) {
		this.recordoutputnames = recordoutputnames;
	}

	public List<OrderByInfo> getOrderbyinfolist() {
		return orderbyinfolist;
	}

	public void setOrderbyinfolist(List<OrderByInfo> orderbyinfolist) {
		this.orderbyinfolist = orderbyinfolist;
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

	public String getUserpermisionscreateuserdepartmentid() {
		return userpermisionscreateuserdepartmentid;
	}

	public void setUserpermisionscreateuserdepartmentid(String userpermisionscreateuserdepartmentid) {
		this.userpermisionscreateuserdepartmentid = userpermisionscreateuserdepartmentid;
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

	public String getRecordpartuserids() {
		return recordpartuserids;
	}

	public void setRecordpartuserids(String recordpartuserids) {
		this.recordpartuserids = recordpartuserids;
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

	public String getRecordids() {
		return recordids;
	}

	public void setRecordids(String recordids) {

		@SuppressWarnings("unchecked")
		List<Integer> recordIdList = ObjectUtil.convertToNumberList(recordids, Integer.class);

		this.setIdlist(recordIdList);

		this.recordids = recordids;
	}

}
