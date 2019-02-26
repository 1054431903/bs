package com.fwk.service.archives.entity;

import java.util.Date;
import java.util.List;

import com.fwk.common.entity.TApeasant;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.Page;
import com.fwk.service.common.entity.PositionEXT;

/**
 * 合作农户档案实体类
 * 
 * @author wangjunwen
 * @date 2018年1月22日 上午11:40:13
 *
 */
public class PeasantEXT extends TApeasant {

	// ============== 以下是重写父类的方法 ============================
	@Override
	public void setBirthday(Date birthday) {
		// 重新赋值到父类的属性上
		super.setBirthday(birthday);

		// 计算出年龄并赋值
		this.setAge(DateTimeUtil.computeRealAge(birthday));
	}

	// =============== 以下是私有属性 ================================

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
	// 行政区划对象
	private PositionEXT positionEXT;
	// 上次调用接口的时间
	private Date lastservicetime;

	// 年龄 数值
	private Integer age;
	// 农户照片
	private String photoidurl;
	// 农户地址（选择的地址 + 详细地址）
	private String address;
	// 性别（名称）
	private String sexname;
	// 是否贫困户（名称）
	private String ispovertyname;
	// 文化程度（名称）
	private String culturename;
	// 责任部门（名称） 字符串
	private String orgidname;
	// 当前合作状态（名称）
	private String cooperstatename;

	// ============ 以下是公开的 getter 和 setter 方法 ===================

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

	public PositionEXT getPositionEXT() {
		return positionEXT;
	}

	public void setPositionEXT(PositionEXT positionEXT) {
		this.positionEXT = positionEXT;
	}

	public Date getLastservicetime() {
		return lastservicetime;
	}

	public void setLastservicetime(Date lastservicetime) {
		this.lastservicetime = lastservicetime;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhotoidurl() {
		return photoidurl;
	}

	public void setPhotoidurl(String photoidurl) {
		this.photoidurl = photoidurl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getIspovertyname() {
		return ispovertyname;
	}

	public void setIspovertyname(String ispovertyname) {
		this.ispovertyname = ispovertyname;
	}

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getOrgidname() {
		return orgidname;
	}

	public void setOrgidname(String orgidname) {
		this.orgidname = orgidname;
	}

	public String getCooperstatename() {
		return cooperstatename;
	}

	public void setCooperstatename(String cooperstatename) {
		this.cooperstatename = cooperstatename;
	}

}
