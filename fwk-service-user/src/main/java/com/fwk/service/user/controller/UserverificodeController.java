package com.fwk.service.user.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.service.PositionService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.util.SupportUserUtil;
import com.fwk.service.user.entity.UserEXT;
import com.fwk.service.user.entity.UserverificodeEXT;
import com.fwk.service.user.service.UserService;
import com.fwk.service.user.service.UserverificodeService;

/**
 * 用户验证码
 * */
@Controller
@RequestMapping("/verificode")
public class UserverificodeController {
	
	@Value("${smsouttime}")
	private String smsouttime;
	
	@Autowired
	private UserverificodeService userverificodeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SupportUserService supportUserService;

	/**
	 * 发送短信
	 * */
	@RequestMapping("/sendSMS")
	public void sendSms(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("phone"))){ 
			//验证是否存在该号码的用户
//			Map<String,Object> queryUserMap = new HashMap<String,Object>();
//			queryUserMap.put("phone", requestMap.get("phone"));
//			List<UserEXT> userEXTs = userService.queryUser(requestMap);
//			supportUserService.queryUserInfoToSupport(requestMap, resultWrap);
//			System.out.println(resultWrap.getData());
//			if(ObjectUtil.isBlank(resultWrap.getData()) || JSONArray.parseArray(resultWrap.getData().toString()).size() <= 0){
//				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-check-isexist-error"));
//				HTMLUtil.sendWrap(response, resultWrap, request);
//				return;
//			}
			//获取随机码
			String randomCode = ObjectUtil.getRandNum(6);
			Object[] obj = {randomCode,smsouttime};//手机号及验证码
			String content = LanguageUtil.getLanguageByKeyAndParam("fwk-user-send-verificode-content", obj);//发送内容
			JSONObject jo = userverificodeService.sendSMS(requestMap.get("phone")+"", content);
			if(ObjectUtil.isNotBlank(jo) && jo.get("msg").toString().toLowerCase().equals("ok")){
				Map<String,Object> saveMap = new HashMap<String,Object>();
				int result = 0;
				Date nowDate = new Date();
				nowDate.setMinutes(nowDate.getMinutes()+5);
				saveMap.put("validitytime", nowDate);
				saveMap.put("phone", requestMap.get("phone"));
				saveMap.put("content", randomCode);
				List<UserverificodeEXT> userverificodeEXTs = userverificodeService.queryVerificode(requestMap);
				if(ObjectUtil.isNotBlank(userverificodeEXTs) && userverificodeEXTs.size()>=1){//已存在该手机号的数据，执行修改
					saveMap.put("id", userverificodeEXTs.get(0).getId());
					result = userverificodeService.updateVerificode(saveMap);
				}else{//不存在，执行添加
					result = userverificodeService.insertVerificode(saveMap);
				}
				if(result >= 1){
					resultWrap.setMessage(LanguageUtil.getLanguageByKey("fwk-user-send-sms-success"));
				}else{
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-send-sms-failure-resend-again"));
				}
			}else{
				resultWrap.error(jo.get("msg")+"");
			}
		}else{
			resultWrap.setParamIsnotEmpty("{手机号}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 验证验证码的正确性
	 * */
	@RequestMapping("/checkVerificode")
	private void checkVerificode(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		if(ObjectUtil.isNotBlank(requestMap.get("phone")) && ObjectUtil.isNotBlank(requestMap.get("verificode"))){
			requestMap.put("content", requestMap.get("verificode"));
			List<UserverificodeEXT> userverificodeEXTs = userverificodeService.queryVerificode(requestMap);
			if(ObjectUtil.isNotBlank(userverificodeEXTs) && userverificodeEXTs.size() == 1){
				if(userverificodeEXTs.get(0).getValiditytime().before(new Date())){//验证时间小于当前时间，验证通过
					resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-check-verificode-time-out"));
				}else{
					Map<String,Object> updateVerificodeMap = new HashMap<String,Object>();
					updateVerificodeMap.put("id", userverificodeEXTs.get(0).getId());
					updateVerificodeMap.put("content", "success");
					userverificodeService.updateVerificode(updateVerificodeMap);
					resultWrap.msg(LanguageUtil.getLanguageByKey("fwk-user-check-verificode-success"));
				}
			}else{
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-user-check-verificode-failure"));
			}
		}else{
			resultWrap.setParamIsnotEmpty("{手机号及验证码}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	
}
