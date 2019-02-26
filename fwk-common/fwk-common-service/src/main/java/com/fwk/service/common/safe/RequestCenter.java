package com.fwk.service.common.safe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.HttpClientUtils;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportUtil;

import jxl.write.DateTime;

@Component
public class RequestCenter extends HandlerInterceptorAdapter{
	
	@Autowired
	private SupportUserService supportUserService;
	
	/**
	 * 拦截所有请求进行验证
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {	
		boolean isResult = true;
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		Map<String,Object> requestMap = HTMLUtil.getPageParams(request);
		if(ObjectUtil.isBlank(requestMap.get("islogin"))) {//登录接口不验证
			//验证当前登录用户的数据是否存在变更
			if(ObjectUtil.isNotBlank(requestMap.get("loginerid"))) {
				if(ObjectUtil.isNotBlank(requestMap.get("operateredittime"))) {
					JSONObject userJO = supportUserService.queryUserDetailBySupportid(requestMap.get("loginerid")+"");
					//updatedOn 不为空则存在变更
					if(ObjectUtil.isNotBlank(userJO) && userJO.keySet().size() >= 1 ) {
						if(ObjectUtil.isNotBlank(userJO.get("updatedOn"))) {
							JSONObject jo = JSONObject.parseObject(userJO.getString("updatedOn"));
							//登录时的修改时间小于当前支撑内的时间，则需要重新登录
							if(JSONObject.parseObject(userJO.get("_id")+"").get("$oid").equals(requestMap.get("loginerid"))) {
								if(!requestMap.get("operateredittime").toString().equals(jo.getString("$numberLong"))) {
									System.out.println("请求参数："+requestMap+",用户支撑数据："+userJO);
//									resultWrap.setLoginAgain("请求参数："+requestMap.get("loginerid")+","+requestMap.get("operateredittime")+",用户支撑数据："+JSONObject.parseObject(userJO.get("_id")+"").get("$oid")+","+userJO.getString("name")+","+userJO.getString("mobile")+","+JSONObject.parseObject(userJO.get("updatedOn")+"").get("$numberLong")+"，用户信息已被变更，");
									resultWrap.setLoginAgain("用户信息已被变更，");
									HTMLUtil.sendWrap(response, resultWrap, request);
									return false;
								}
							}
						}
					}else {
						resultWrap.setLoginAgain("不存在该用户，");
						HTMLUtil.sendWrap(response, resultWrap, request);
						return false;
					}
				}else {
					resultWrap.setLoginAgain("参数：operateredittime 不能为空，");
					HTMLUtil.sendWrap(response, resultWrap, request);
					return false;
				}
			}else {
				resultWrap.setLoginAgain("参数：loginerid 不能为空，");
				HTMLUtil.sendWrap(response, resultWrap, request);
				return false;
			}
		}
		//验证登录用户的企业信息是否变更
		if(ObjectUtil.isNotBlank(requestMap.get("loginorgid"))) {
			
		}
		//验证当前用户的权限
		
		return isResult;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		System.out.println("==---------------postHandle-------------==");
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(request.getRemoteAddr());
		System.out.println("==---------------afterCompletion-------------==");
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("==---------------afterConcurrentHandlingStarted-------------==");
	}
}
