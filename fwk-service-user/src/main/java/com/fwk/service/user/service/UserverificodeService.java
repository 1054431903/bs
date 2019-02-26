package com.fwk.service.user.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.SmsApi;
import com.fwk.service.user.dao.UserverificodeDao;
import com.fwk.service.user.entity.UserverificodeEXT;

@Service
public class UserverificodeService {
	
	@Autowired
	private UserverificodeDao userverificodeDao;
	
	/**
	 * 发送短信
	 * */
	public JSONObject sendSMS(String phone,String content){
		JSONObject resultJO = new JSONObject();
		try {
			String result = SmsApi.sendSms(content, phone);
			if(ObjectUtil.isNotBlank(result)){
				resultJO = JSONObject.parseObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
			resultJO = new JSONObject();
		}
		return resultJO;
	}
	
	/**
	 * 保存验证码至数据库
	 * */
	public int insertVerificode(Map<String,Object> saveMap){
		return userverificodeDao.insertVerificode(saveMap);
	}

	public List<UserverificodeEXT> queryVerificode(
			Map<String, Object> requestMap) {
		return userverificodeDao.queryVerificode(requestMap);
	}

	public int updateVerificode(Map<String, Object> saveMap) {
		return userverificodeDao.updateVerificode(saveMap);
	}
	
	/**
	 * 验证验证码
	 * */
	
}
