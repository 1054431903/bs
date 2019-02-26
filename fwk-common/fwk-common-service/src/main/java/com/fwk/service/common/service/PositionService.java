package com.fwk.service.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.service.common.dao.PositionDao;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;

@Service
public class PositionService {
	
	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private SupportService supportService;

	public List<PositionEXT> queryPositionByParentCode(Map<String, Object> requestMap) {
		return positionDao.queryPositionByParentCode(requestMap);
	}
	
	public PositionEXT getPositionDetail(Map<String, Object> requestMap) {
		return positionDao.getPositionDetail(requestMap);
	}

	public int savePosition(Map<String, Object> saveMap) {
		return positionDao.savePosition(saveMap);
	}
	
	public int saveSupportPosition(Map<String, Object> saveMap){
		return positionDao.saveSupportPosition(saveMap);
	}
	
	private String valsql = "";

	public void synchPositionInfoToMysql() {
		/**
		 * 获取支撑行政区划数据 整理 至 mysql数据库
		 * */
		JSONObject jo = new JSONObject();
		jo.put("level", 6);
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(jo);
		
		Integer limit = 10000;
		
		for (int j = 0; j <= 70; j++) {
			JSONObject job1 = new JSONObject();
			job1.put("limit", limit);
			job1.put("skip", limit*j);
			System.out.println(job1);
			supportRequestParam.setPageJson(job1);
			
			Object areaList = supportService.requestSupportGet(AllJsonRequestUrl.Base_area, supportRequestParam);
			System.out.println(areaList);
			JSONObject jo1 = new JSONObject();
			jo1 = JSONObject.parseObject(areaList.toString());
			JSONArray ja = JSONArray.parseArray(jo1.getString("items"));
			Map<String,Object> supportPositionMap = new HashMap<String,Object>();
			for (int i = 0; i < ja.size(); i++) {
				System.out.println(i);
				supportPositionMap = new HashMap<String,Object>();
				//每条jsonobject
				JSONObject jo2 = JSONObject.parseObject(ja.getString(i));
				//获取id
				JSONObject joid = JSONObject.parseObject(jo2.getString("_id"));
				supportPositionMap.put("id", joid.getString("$oid"));
				//获取parentid
				JSONObject joparentid = JSONObject.parseObject(jo2.getString("parent"));
				supportPositionMap.put("parentid", joparentid.getString("$oid"));
				//获取name
				supportPositionMap.put("name", jo2.getString("name"));
				//获取code
				supportPositionMap.put("code", jo2.getString("code"));
				//获取level
				supportPositionMap.put("level", jo2.getString("level"));
				positionDao.saveSupportPosition(supportPositionMap);
//				if(ObjectUtil.isNotBlank(valsql)){
//					valsql += ",";
//				}
//				valsql += "("+"'"+joid.getString("$oid")+"'"+","+"'"+joparentid.getString("$oid")+"'"+","+"'"+jo2.getString("code")+"'"+","+"'"+jo2.getString("name")+"'"+","+"'"+jo2.getString("level")+"'"+")";
//				getByParentIdFromSupport(joid.getString("$oid")+"");
			}
//			System.out.println(valsql);
//			positionDao.saveSupportPositionVal(valsql);
		}
		
		
	}
	
	
	
	//通过父级编号查询下一级数据
	public void getByParentIdFromSupport(String parentid){
		JSONObject j = new JSONObject();
		j.put("$oid", parentid);
		
		JSONObject jo = new JSONObject();
		jo.put("parent", j);
		
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(jo);
		
		Object areaList = supportService.requestSupportGet(AllJsonRequestUrl.Base_area, supportRequestParam);
		System.out.println(areaList);
		JSONObject jo1 = new JSONObject();
		jo1 = JSONObject.parseObject(areaList.toString());
		JSONArray ja = JSONArray.parseArray(jo1.getString("items"));
		Map<String,Object> supportPositionMap = new HashMap<String,Object>();
		for (int i = 0; i < ja.size(); i++) {
			supportPositionMap = new HashMap<String,Object>();
			//每条jsonobject
			JSONObject jo2 = JSONObject.parseObject(ja.getString(i));
			//获取id
			JSONObject joid = JSONObject.parseObject(jo2.getString("_id"));
			supportPositionMap.put("id", joid.getString("$oid"));
			//获取parentid
			JSONObject joparentid = JSONObject.parseObject(jo2.getString("parent"));
			supportPositionMap.put("parentid", joparentid.getString("$oid"));
			//获取name
			supportPositionMap.put("name", jo2.getString("name"));
			//获取code
			supportPositionMap.put("code", jo2.getString("code"));
			//获取level
			supportPositionMap.put("level", jo2.getString("level"));
			positionDao.saveSupportPosition(supportPositionMap);
			
		}
	}
	
	/**
	 * 通过最底层postion code获取全部上级
	 * */
	public JSONObject getParentPositionByCode(String code){
		JSONObject jo = new JSONObject();
		getParentPositionByCodeJO(code,jo);
		return jo;
	}
	
	public void getParentPositionByCodeJO(String code,JSONObject jo){
		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("code", code);
		PositionEXT positionEXT = positionDao.getPositionDetail(requestMap);
		if(ObjectUtil.isNotBlank(positionEXT)){
			JSONObject oidjo = new JSONObject();
			oidjo.put("$oid", positionEXT.getCode());
			switch (positionEXT.getLevel()) {
			case "2":
				jo.put("province", oidjo);
				break;
			case "3":
				jo.put("city", oidjo);
				getParentPositionByCodeJO(positionEXT.getParentcode(),jo);
				break;
			case "4":
				jo.put("district", oidjo);
				getParentPositionByCodeJO(positionEXT.getParentcode(),jo);
				break;
			case "5":
				jo.put("town", oidjo);
				getParentPositionByCodeJO(positionEXT.getParentcode(),jo);
				break;
			case "6":
				jo.put("village", oidjo);
				getParentPositionByCodeJO(positionEXT.getParentcode(),jo);
				break;
			default:
				jo.put("province", oidjo);
				break;
			}
		}
	}
	
}
