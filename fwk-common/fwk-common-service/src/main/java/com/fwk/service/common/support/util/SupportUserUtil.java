package com.fwk.service.common.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.service.PositionService;

/**
 * 支撑用户对象
 * */
@Component
public class SupportUserUtil {
	
	@Autowired
	private PositionService positionService;

	/**
	 * 封装用户基础数据（依照支撑平台表信息进行封装）
	 * requestMap:基础数据map对象
	 * isedit：是否是修改操作（修改操作需要另外一种json格式：
	 * 如：{filter:{"_id":{"$oid":"5a94f137b3cb1f000130f879"}},update:{$set:{password:"RgE9VuGQlGOk7dUxz7ku/bjtZ9irZ/nMZjeOtOEO9DE=$cXdlcnR5$100000111111", mobile: 1528812187811111}}}）
	 * objects ：不需要转换的属性
	 * */
	public JSONObject packUserInfoToSupportTenantStaff(Map<String,Object> requestMap,boolean isedit,Object... objects){
		JSONObject resultJson = new JSONObject();
		//移除不需要对应的属性
		if(ObjectUtil.isNotBlank(objects)){
			for (Object object : objects) {
				requestMap.remove(object);
			}
		}
		if(isedit){//是修改操作
			encryFilterIDJson(requestMap, resultJson);
			JSONObject jo = new JSONObject();
			encryBaseJson(requestMap,jo);
//			setTenantsInfo(requestMap,jo);
			JSONObject setjo = new JSONObject();
			setjo.put("$set", jo);
			resultJson.put("update", setjo);
		}else{//非修改操作
			encryIDJson(requestMap, resultJson);
			encryBaseJson(requestMap,resultJson);
//			setTenantsInfo(requestMap,resultJson);
		}
		System.out.println(resultJson);
		return resultJson;
	}
	
	/**
	 * 封装json通过用户id删除组织/部门id
	 * */
	public JSONObject packUserDeleteTenantByUserId(String tenantid,String id){
		JSONObject resultJson = new JSONObject();
		
		//filterJson
		JSONObject oidJO = new JSONObject();
		oidJO.put("$oid", id);
		JSONObject idJO = new JSONObject();
		idJO.put("_id", oidJO);
		
		resultJson.put("filter", idJO);
		
		//updateJson
		JSONObject toidJO = new JSONObject();
		toidJO.put("$oid", tenantid);
		
		JSONObject tenantsJO = new JSONObject();
		tenantsJO.put("tenants", toidJO);
		
		JSONObject pullJO = new JSONObject();
		pullJO.put("$pull", tenantsJO);
		
		
		resultJson.put("update", pullJO);
		
		return resultJson;
	}
	
	/**
	 * 封装json通过用户id添加组织/部门id
	 * */
	public JSONObject packUserAddTenantByUserId(String tenantid,String id){
		JSONObject resultJson = new JSONObject();
		
		//filterJson
		JSONObject oidJO = new JSONObject();
		oidJO.put("$oid", id);
		JSONObject idJO = new JSONObject();
		idJO.put("_id", oidJO);
		
		resultJson.put("filter", idJO);
		
		//updateJson
		JSONObject toidJO = new JSONObject();
		toidJO.put("$oid", tenantid);
		
		JSONObject tenantsJO = new JSONObject();
		tenantsJO.put("tenants", toidJO);
		
		JSONObject pushJO = new JSONObject();
		pushJO.put("$push", tenantsJO);
		
		
		resultJson.put("update", pushJO);
		
		return resultJson;
	}
	
	
	/**
	 * 封装基础属性
	 * */
	public void encryBaseJson(Map<String,Object> requestMap,JSONObject requestJson){
		//组织编号
//		if(ObjectUtil.isNotBlank(requestMap.get("orgid"))){
//			JSONObject jo = new JSONObject();
//			jo.put("$oid", requestMap.get("orgid"));
//			requestJson.put("tenants", jo);
//		}
		//账号转手机号
		if(ObjectUtil.isNotBlank(requestMap.get("account"))){
			requestJson.put("mobile", requestMap.get("account"));
		}
		//手机号
		if(ObjectUtil.isNotBlank(requestMap.get("phone"))){
			requestJson.put("mobile", requestMap.get("phone"));
		}
		//性别
		if(ObjectUtil.isNotBlank(requestMap.get("sex"))){
			requestJson.put("gender", Integer.parseInt(requestMap.get("sex").toString()));
		}
		//名称
		if(ObjectUtil.isNotBlank(requestMap.get("name"))){
			requestJson.put("name", requestMap.get("name"));
		}
		//密码
		if(ObjectUtil.isNotBlank(requestMap.get("password"))){
			requestJson.put("password", requestMap.get("password"));
		}
		//状态
		if(ObjectUtil.isNotBlank(requestMap.get("status"))){
			requestJson.put("status", requestMap.get("status"));
		}
		//生日
		if(ObjectUtil.isNotBlank(requestMap.get("birthday"))){
			JSONObject birJO = new JSONObject();
			try {
				Date d = new SimpleDateFormat("yyyy-MM-dd").parse(requestMap.get("birthday").toString());
				birJO.put("$numberLong", d.getTime()+"");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			requestJson.put("birthday", birJO);
		}
		//昵称
		if(ObjectUtil.isNotBlank(requestMap.get("nickname"))){
			requestJson.put("nickname", requestMap.get("nickname"));
		}
		//头像地址
		if(ObjectUtil.isNotBlank(requestMap.get("photourl"))){
			requestJson.put("avatar", requestMap.get("photourl"));
		}
		//是否删除头像地址
		if(ObjectUtil.isNotBlank(requestMap.get("isdelphotourl"))){
			requestJson.put("avatar", "");
		}
		//行政区划
		if(ObjectUtil.isNotBlank(requestMap.get("addresscode"))){
			JSONObject addressJO = positionService.getParentPositionByCode(requestMap.get("addresscode").toString());
			if(ObjectUtil.isNotBlank(requestMap.get("addressdetail"))) {
				addressJO.put("street", requestMap.get("addressdetail"));
			}
			requestJson.put("address", addressJO);
		}
		//最后一次登录时间
		if(ObjectUtil.isNotBlank(requestMap.get("lastLogin"))){
			requestJson.put("loginOn", requestMap.get("lastLogin"));
		}
		//删除状态
		if(ObjectUtil.isNotBlank(requestMap.get("_removed"))){
			requestJson.put("_removed", requestMap.get("_removed"));
		}
		//不等于
		if(ObjectUtil.isNotBlank(requestMap.get("noid"))){
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", requestMap.get("noid"));
			JSONObject nejo = new JSONObject();
			nejo.put("$ne", oidjo);
			requestJson.put("_id", nejo);
		}
	}
	
	/**
	 * 封装IDjson：filter
	 * */
	public static void encryFilterIDJson(Map<String,Object> requestMap,JSONObject requestJson){
		Object _id = "";
		if(ObjectUtil.isNotBlank(requestMap.get("supportid"))){
			_id = requestMap.get("supportid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("userid"))){
			_id = requestMap.get("userid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("id"))){
			_id = requestMap.get("id");
		}
		if(ObjectUtil.isNotBlank(_id)){
			JSONObject filterjo = new JSONObject();
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", _id);
			filterjo.put("_id", oidjo);
			requestJson.put("filter", filterjo);
		}
	}
	
	/**
	 * 封装IDjson
	 * */
	public static void encryIDJson(Map<String,Object> requestMap,JSONObject requestJson){
		Object _id = "";
		if(ObjectUtil.isNotBlank(requestMap.get("supportid"))){
			_id = requestMap.get("supportid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("userid"))){
			_id = requestMap.get("userid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("id"))){
			_id = requestMap.get("id");
		}
		if(ObjectUtil.isNotBlank(_id)){
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", _id);
			requestJson.put("_id", oidjo);
		}
	}
	
	/**
	 * 设置组织信息(未完)
	 * */
	public static void setTenantsInfo(Map<String,Object> requestMap,JSONObject requestJson){
		System.out.println("requestMap:"+requestMap);
		System.out.println("requestJson:"+requestJson);
		Object _id = "";
		if(ObjectUtil.isNotBlank(requestMap.get("supportid"))){
			_id = requestMap.get("supportid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("userid"))){
			_id = requestMap.get("userid");
		}else if(ObjectUtil.isNotBlank(requestMap.get("id"))){
			_id = requestMap.get("id");
		}
		if(ObjectUtil.isNotBlank(_id)){//_id不为空，修改
			
		}else{//_id为空，添加
			
		}
	}
	
}
