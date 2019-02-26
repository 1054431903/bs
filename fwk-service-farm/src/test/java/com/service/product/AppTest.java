package com.service.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.service.farm.entity.FWKSchedule;

/**
 * Unit test for simple App.
 */
public class AppTest {

	public static void main(String[] args) {

		List<FWKSchedule> list = new ArrayList<FWKSchedule>();

		FWKSchedule fwk1 = new FWKSchedule();
		fwk1.setId(1);
		fwk1.setEndtime(DateTimeUtil.parse("2018-05-12 10:24:35", DateTimeUtil.DEFAULT_DATETIME_FORMAT));
		list.add(fwk1);

		fwk1 = new FWKSchedule();
		fwk1.setId(2);
		fwk1.setEndtime(DateTimeUtil.parse("2018-03-12 10:24:35", DateTimeUtil.DEFAULT_DATETIME_FORMAT));
		list.add(fwk1);

		fwk1 = new FWKSchedule();
		fwk1.setId(3);
		fwk1.setEndtime(DateTimeUtil.parse("2018-06-12 21:14:15", DateTimeUtil.DEFAULT_DATETIME_FORMAT));
		list.add(fwk1);

		fwk1 = new FWKSchedule();
		fwk1.setId(4);
		fwk1.setEndtime(DateTimeUtil.parse("2018-02-26 12:00:35", DateTimeUtil.DEFAULT_DATETIME_FORMAT));
		list.add(fwk1);

		System.out.println("排序前：\r\n" + JSONObject.toJSON(list));

		Collections.sort(list);
		System.out.println("排序后：\r\n" + JSONObject.toJSON(list));

	}

}
