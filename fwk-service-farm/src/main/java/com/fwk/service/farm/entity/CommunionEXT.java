package com.fwk.service.farm.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TFcommunion;
import com.fwk.common.util.Page;
import com.fwk.service.common.entity.ResourcehistoryEXT;

/**
 * 农事交流的实体类的扩展类
 * 
 * @author wangjunwen
 * @date 2018年3月1日 上午10:05:55
 *
 */
public class CommunionEXT extends TFcommunion {
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
	// 查询的关键字
	private String keyword;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 农事交流的品类列表
	private List<CommunioncategoryEXT> communioncategorylist;
	// 农事交流的评论列表
	private List<CommunioncommentEXT> communioncommentlist;
	// 农事交流的地块列表
	private List<CommunionparcelEXT> communionparcellist;
	// 农事交流的资源文件列表
	private List<CommunionresourceEXT> communionresourcelist;
	// 农事交流的附件列表
	private List<ResourcehistoryEXT> resourcehistorylist;
	// 农事交流的音频资源文件资源文件列表（从农事交流的资源文件中抽取）
	private List<ResourcehistoryEXT> audioResourcehistorylist;
	// 农事交流的图片文件列表
	private List<ResourcehistoryEXT> imageResourcehistorylist;
	// 农事交流的其他未明确分类的文件列表
	private List<ResourcehistoryEXT> otherResourcehistorylist;
	// 增加或者减少的操作数（主要用于增加或者减少评论的数量）
	private Integer increaseordecreaseoperand;
	// 发布人的姓名
	private String createuseridname;
	// 品类名称
	private String categorynames;
	// 地块名称
	private String parcelidnames;
	// 发布人的昵称
	private String createuseridnickname;
	// 发布人的头像
	private String photourl;
	// 发布人所在的企业的名称
	private String orgname;
	// 是否可编辑
	private Boolean editable;
	// 支撑平台的用户ID
	private String usersupportid;
	// 用户当前登录的组织ID
	private String userloginorgid;
	// 排序号
	private Integer ordernoorgid;

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

	public List<CommunioncategoryEXT> getCommunioncategorylist() {
		return communioncategorylist;
	}

	public void setCommunioncategorylist(List<CommunioncategoryEXT> communioncategorylist) {
		this.communioncategorylist = communioncategorylist;
	}

	public List<CommunioncommentEXT> getCommunioncommentlist() {
		return communioncommentlist;
	}

	public void setCommunioncommentlist(List<CommunioncommentEXT> communioncommentlist) {
		this.communioncommentlist = communioncommentlist;
	}

	public List<CommunionparcelEXT> getCommunionparcellist() {
		return communionparcellist;
	}

	public void setCommunionparcellist(List<CommunionparcelEXT> communionparcellist) {
		this.communionparcellist = communionparcellist;
	}

	public List<CommunionresourceEXT> getCommunionresourcelist() {
		return communionresourcelist;
	}

	public void setCommunionresourcelist(List<CommunionresourceEXT> communionresourcelist) {
		this.communionresourcelist = communionresourcelist;
	}

	public List<ResourcehistoryEXT> getResourcehistorylist() {
		return resourcehistorylist;
	}

	public void setResourcehistorylist(List<ResourcehistoryEXT> resourcehistorylist) {
		this.resourcehistorylist = resourcehistorylist;
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

	public Integer getIncreaseordecreaseoperand() {
		return increaseordecreaseoperand;
	}

	public void setIncreaseordecreaseoperand(Integer increaseordecreaseoperand) {
		this.increaseordecreaseoperand = increaseordecreaseoperand;
	}

	public String getCreateuseridname() {
		return createuseridname;
	}

	public void setCreateuseridname(String createuseridname) {
		this.createuseridname = createuseridname;
	}

	public String getCategorynames() {
		return categorynames;
	}

	public void setCategorynames(String categorynames) {
		this.categorynames = categorynames;
	}

	public String getParcelidnames() {
		return parcelidnames;
	}

	public void setParcelidnames(String parcelidnames) {
		this.parcelidnames = parcelidnames;
	}

	public String getCreateuseridnickname() {
		return createuseridnickname;
	}

	public void setCreateuseridnickname(String createuseridnickname) {
		this.createuseridnickname = createuseridnickname;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getUsersupportid() {
		return usersupportid;
	}

	public void setUsersupportid(String usersupportid) {
		this.usersupportid = usersupportid;
	}

	public String getUserloginorgid() {
		return userloginorgid;
	}

	public void setUserloginorgid(String userloginorgid) {
		this.userloginorgid = userloginorgid;
	}

	public Integer getOrdernoorgid() {
		return ordernoorgid;
	}

	public void setOrdernoorgid(Integer ordernoorgid) {
		this.ordernoorgid = ordernoorgid;
	}

}
