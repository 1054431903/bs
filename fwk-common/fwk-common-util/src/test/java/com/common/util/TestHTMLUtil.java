package com.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.common.util.model.BeanTestHTMLUtil;
import com.common.util.model.SubBeanTestHTMLUtil;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.HTMLUtil;

public class TestHTMLUtil {

	public static void main(String[] args) {
		BeanTestHTMLUtil beanTestHTMLUtil = new BeanTestHTMLUtil();

		beanTestHTMLUtil.setBirthday(DateTimeUtil.parse("1980-01-21"));
		beanTestHTMLUtil.setHobbys(new String[] { "唱歌", "跳舞", "游泳", "跑步" });
		beanTestHTMLUtil.setIds(new Integer[] { 10, 20, 30, 40 });
		beanTestHTMLUtil.setName("爱乐宝");

		SubBeanTestHTMLUtil subBeanTestHTMLUtil = new SubBeanTestHTMLUtil();
		List<String> groupIdList = new ArrayList<String>();
		groupIdList.addAll(Arrays.asList(new String[] { "abc123", "dsgs4575", "dsetf75442", "15gase5ds1fas" }));
		subBeanTestHTMLUtil.setGroupIdList(groupIdList);
		subBeanTestHTMLUtil.setSubIds(new String[] { UUID.randomUUID().toString(), UUID.randomUUID().toString(),
				UUID.randomUUID().toString(), UUID.randomUUID().toString() });
		subBeanTestHTMLUtil.setTeamId("asggwer545455KKJKKFHDSFJ");
		subBeanTestHTMLUtil.setTeamName("KUNMING . YUNNAN . CHINA");

		beanTestHTMLUtil.setSubBeanTestHTMLUtilList(new ArrayList<SubBeanTestHTMLUtil>());

		beanTestHTMLUtil.getSubBeanTestHTMLUtilList().add(subBeanTestHTMLUtil);

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(beanTestHTMLUtil);

		BeanTestHTMLUtil toBeanTestHTMLUtil = HTMLUtil.getMapToEntity(BeanTestHTMLUtil.class, jsonObject);

		JSONObject toJSONObject = JSONObject.parseObject(
				JSONObject.toJSONStringWithDateFormat(toBeanTestHTMLUtil, DateTimeUtil.DEFAULT_DATE_FORMAT));

		System.out.println("[转换结果]=====>" + toJSONObject);

	}

	public static void main_0(String[] args) {
		// 测试重新设置不符合标准的日期时间的范围
		testResetDateRange();
	}

	/**
	 * 测试重新设置不符合标准的日期时间的范围
	 * 
	 * @author wangjunwen
	 * @date 2017年9月21日 上午9:54:38
	 *
	 */
	public static void testResetDateRange() {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		System.out.println("========== 使用默认的参数名称重置日期 ===========");
		paramMap.put("createdatestart", "2017-09-21");
		paramMap.put("createdateend", "2017-09-21");

		HTMLUtil.resetDateRange(paramMap);

		System.out.println(paramMap);

		System.out.println("========= 重置起始时间和截至时间 ============");
		paramMap.put("dateStart", "2016-08-16");
		paramMap.put("dateEnd", "2016-08-25");

		HTMLUtil.resetDateRange(paramMap, "dateStart|dateEnd");

		System.out.println(paramMap);

		System.out.println("========= 只重置截至时间 ============");
		paramMap.put("dateStart", "2016-08-16");
		paramMap.put("dateEnd", "2016-08-25");

		HTMLUtil.resetDateRange(paramMap, "|dateEnd");

		System.out.println(paramMap);

		System.out.println("======== 只重置起始时间 =============");
		paramMap.put("dateStart", "2016-08-16");
		paramMap.put("dateEnd", "2016-08-25");

		HTMLUtil.resetDateRange(paramMap, "dateStart|");

		System.out.println(paramMap);

		System.out.println("======== 重置多个日期范围 =============");
		paramMap.put("dateStart1", "2016-05-13");
		paramMap.put("dateEnd1", "2016-05-13");

		paramMap.put("dateStart2", "2015-02-15");
		paramMap.put("dateEnd2", "2015-02-15");

		paramMap.put("dateStart3", "2014-03-18");
		paramMap.put("dateEnd3", "2014-03-19");

		HTMLUtil.resetDateRange(paramMap, "dateStart1|dateEnd1", "dateStart2|dateEnd2", "dateStart3|dateEnd3");

		System.out.println(paramMap);
	}
}
