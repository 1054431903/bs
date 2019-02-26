package com.service.product;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;
import com.fwk.service.archives.entity.PeasantEXT;

public class TestMain {

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("birthday", "2012-10-01");

		// 将客户端传过来的值绑定到实体对象上
		PeasantEXT paramPeasantEXT = HTMLUtil.getMapToEntity(PeasantEXT.class, map);

		System.out.println(JSONObject.toJSON(paramPeasantEXT));
	}

}
