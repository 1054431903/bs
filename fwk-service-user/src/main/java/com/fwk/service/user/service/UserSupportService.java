package com.fwk.service.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.user.dao.UserDao;
import com.fwk.service.user.entity.UserEXT;

@Service
public class UserSupportService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SupportService supportService;
	
	/**
	 * 根据用户id获取支撑id
	 * @param Integer:userid
	 * */
	public String getSupportIdByUserId(Object userid){
		String supportid = "";
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("id", userid);
		List<UserEXT> userEXTs = userDao.queryUserDetail(userMap);
		if(ObjectUtil.isNotBlank(userEXTs) && userEXTs.size() == 1){
			supportid = userEXTs.get(0).getSupportid();
		}
		return supportid;
	}
	
	/**
	 * 根据用户id获取支撑id
	 * @param Integer:userid
	 * */
	public Integer getUserIdBySupportId(String supportid){
		Integer userid = null;
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("supportid", supportid);
		List<UserEXT> userEXTs = userDao.queryUser(userMap);
		if(ObjectUtil.isNotBlank(userEXTs) && userEXTs.size() == 1){
			userid = userEXTs.get(0).getId();
		}
		return userid!=null ? Integer.parseInt(userid.toString()) : null;
	}
	
	/**
	 * 获取支撑的用户数据更新至平台数据库
	 * @param String:supportid
	 * */
	public void supportInsertToUser(String supportid){
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(SupportUtil.supportIdTo_id(supportid));
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		JSONArray resultJA = SupportUtil.supportGetItemData(result);
		if(ObjectUtil.isNotBlank(resultJA) && resultJA.size() >= 1){
			//获取数据库用户supportid对应的数据
			Map<String,Object> supportUserMap = new HashMap<String,Object>();
			supportUserMap.put("supportid", supportid);
			List<UserEXT> userEXTs = userDao.queryUser(supportUserMap);
			if(userEXTs.size() <= 1){// 不存在该用户
				UserEXT userEXT = new UserEXT();
				supportInfoToUserEXT(supportid,userEXT);
				userDao.insertUserSupport(userEXT);
			}
		}
	}
	
	/**
	 * 将支撑数据添加至一个已有UserEXT实体
	 * @param String:supportid
	 * */
	public void supportInfoToUserEXT(String supportid,UserEXT userEXT){
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(SupportUtil.supportIdTo_id(supportid));
		Object result = supportService.requestSupportGet(AllJsonRequestUrl.Tenant_staff, supportRequestParam);
		JSONArray resultJA = SupportUtil.supportGetItemData(result);
		if(ObjectUtil.isNotBlank(resultJA) && resultJA.size() >= 1){
			transJsonToUserEXT(JSONObject.parseObject(resultJA.getString(0)),userEXT);
		}
	}
	
	/**
	 * 设置登录用户的修改时间（默认为当前时间）
	 * */
	public void transJsonToUserEXTEdittime(JSONObject jsonObject,UserEXT userEXT) {
		if(ObjectUtil.isNotBlank(jsonObject.get("updatedOn"))) {
			JSONObject jo = JSONObject.parseObject(jsonObject.getString("updatedOn"));
			userEXT.setOperateredittime(jo.get("$numberLong")+"");
		}else {
			userEXT.setOperateredittime("0000000000000");
		}
	}
	
	/**
	 * 通过json对象转换成UserEXT对象
	 * */
	public void transJsonToUserEXT(JSONObject jsonObject,UserEXT userEXT){
		if(ObjectUtil.isNotBlank(jsonObject)){
			//设置Supportid
			if(ObjectUtil.isNotBlank(jsonObject.get("_id"))){
				JSONObject jo = JSONObject.parseObject(jsonObject.get("_id")+"");
				if(ObjectUtil.isNotBlank(jo.get("$oid"))){
					userEXT.setSupportid(jo.getString("$oid"));
				}
			}
			//设置手机号码
			if(ObjectUtil.isNotBlank(jsonObject.get("mobile"))){
				userEXT.setPhone(jsonObject.getString("mobile"));
			}
			//设置性别
			if(ObjectUtil.isNotBlank(jsonObject.get("gender"))){
				userEXT.setSex(jsonObject.getString("gender"));
			}
			//设置姓名
			if(ObjectUtil.isNotBlank(jsonObject.get("name"))){
				userEXT.setName(jsonObject.getString("name"));
			}
			//设置密码
			if(ObjectUtil.isNotBlank(jsonObject.get("password"))){
				userEXT.setPassword(jsonObject.getString("password"));
			}
			//设置可用状态
			if(ObjectUtil.isNotBlank(jsonObject.get("status"))){
				userEXT.setDelstate(jsonObject.getInteger("status"));
			}
			//设置出生日期
			if(ObjectUtil.isNotBlank(jsonObject.get("birthday"))){
				JSONObject jo = JSONObject.parseObject(jsonObject.getString("birthday"));
				userEXT.setBirthday(jo.getDate("$numberLong"));
			}
			//设置昵称
			if(ObjectUtil.isNotBlank(jsonObject.get("nickname"))){
				userEXT.setNickname(jsonObject.getString("nickname"));
			}
			//设置头像
			if(ObjectUtil.isNotBlank(jsonObject.get("avatar"))){
				userEXT.setPhotoURL(jsonObject.getString("avatar"));
			}
			//设置地址
			if(ObjectUtil.isNotBlank(jsonObject.get("address"))){
				userEXT.setAddresscode(SupportUtil.supportGetAddressCode(JSONObject.parseObject(jsonObject.getString("address"))));
				userEXT.setAddressdetail(JSONObject.parseObject(jsonObject.getString("address")).getString("street"));
			}
			//设置最后一次登录时间
			if(ObjectUtil.isNotBlank(jsonObject.get("loginOn"))){
				userEXT.setLastlogin(jsonObject.getDate("loginOn"));
			}
		}
	}

}
