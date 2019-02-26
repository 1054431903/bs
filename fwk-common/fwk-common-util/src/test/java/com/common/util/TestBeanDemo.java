package com.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.HTMLUtil;

public class TestBeanDemo {

	public static void main(String[] args) {
		BeanDemo beanDemo = getInstanceBeanDemo();

		Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSON(beanDemo).toString());

		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.putAll(map);

		System.out.println(treeMap);

		System.out.println(getTreeMap());

	}

	public static Map<String, Object> getTreeMap() {
		Map<String, Object> map = new TreeMap<String, Object>();

		// private String string;
		map.put("string", "这是 BeanDemo 的实例");

		// private int intt;
		map.put("intt", "100000");

		// private Integer integer;
		map.put("integer", "100001");

		// private long longg;
		map.put("longg", "200000");

		// private Long longgg;
		map.put("longgg", "200001");

		// private double doublee;
		map.put("doublee", "100000.012");

		// private Double doubleee;
		map.put("doubleee", "100000.123");

		// private Date date;
		map.put("date", "2018-02-02 14:03:25");

		// private int[] intarry;
		map.put("intarry", Arrays.toString(new int[] { 1, 0, 0, 0, 0, 0 }));

		// private Integer[] integerArray;
		map.put("integerArray", Arrays.toString(new int[] { 1, 0, 0, 0, 0, 1 }));

		// private List<Integer> integerlist;
		map.put("integerlist", Arrays.toString(new int[] { 1, 0, 0, 0, 0, 1, 0, 0, 0 }));

		// private ArrayList<Integer> integerarraylist;
		map.put("integerarraylist", Arrays.toString(new int[] { 1, 0, 0, 0, 0, 1, 0, 0, 1 }));

		// private long[] longarry;
		map.put("longarry", Arrays.toString(new long[] { 2, 0, 0, 0, 0, 2, 0, 0, 0 }));

		// private Long[] longArray;
		map.put("longArray", Arrays.toString(new long[] { 2, 0, 0, 0, 0, 2, 0, 0, 1 }));

		// private List<Long> longlist;
		map.put("longlist", Arrays.toString(new long[] { 2, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0 }));

		// private ArrayList<Long> longarraylist;
		map.put("longarraylist", Arrays.toString(new long[] { 2, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 2 }));

		// private double[] doublearry;
		map.put("doublearry", Arrays.toString(new double[] { 1.2, 1.3, 1.4, 1.5, 1.6 }));

		// private Double[] doubleArray;
		map.put("doubleArray", Arrays.toString(new double[] { 2.2, 2.3, 2.4, 2.5, 2.6 }));

		// private List<Double> doublelist;
		map.put("doublelist", Arrays.toString(new double[] { 2.201, 2.301, 2.401, 2.501, 2.601 }));

		// private ArrayList<Double> doublearraylist;
		map.put("doublearraylist", Arrays.toString(new double[] { 3.201, 3.301, 3.401, 3.501, 3.601 }));

		// private String[] stringarray;
		map.put("stringarray", Arrays.toString(new String[] { "北京", "上海", "天津", "重庆" }));

		// private List<String> stringlist;
		map.put("stringlist", Arrays.toString(new String[] { "香港", "澳门", "台湾" }));

		// private ArrayList<String> stringarraylist;
		map.put("stringarraylist", Arrays.toString(new String[] { "广西", "广东", "浙江", "福建", "安徽", "江苏" }));

		// private Date[] datearray;
		map.put("datearray", Arrays.toString(new String[] { "2017-01-20", "2018-01-01", "2018-01-02 10:20:21" }));

		// private List<Date> datelist;
		map.put("datelist", Arrays
				.toString(new String[] { "2018-02-21", "2018-10-19", "2018-10-02 21:20:30", "2018-08-11 20:20:30" }));

		// private ArrayList<Date> datearraylist;
		map.put("datearraylist", Arrays.toString(new String[] { "2017-11-01", "2016-09-25", "2018-08-15 10:20:30",
				"2018-09-11 15:16:17", "2018-12-25 10:09:23" }));

		// List<SubBeanDemo> subBeanDemoList = new ArrayList<SubBeanDemo>();

		Map<String, Object> subMap1 = new HashMap<String, Object>();

		// private String name;
		subMap1.put("name", "张三");
		// private Integer age;
		subMap1.put("age", 21);
		// private String[] hobbys;
		subMap1.put("hobbys", Arrays.toString(new String[] { "游泳", "唱歌", "打荣耀", "泡吧" }));
		// private List<Double> costs;
		subMap1.put("costs", Arrays.toString(new Double[] { 10.1, 21.45, 38.76, 24.98 }));

		Map<String, Object> subMap2 = new HashMap<String, Object>();

		// private String name;
		subMap2.put("name", "李四");
		// private Integer age;
		subMap2.put("age", 19);
		// private String[] hobbys;
		subMap2.put("hobbys", Arrays.toString(new String[] { "泡吧", "打篮球", "睡懒觉" }));
		// private List<Double> costs;
		subMap2.put("costs", Arrays.toString(new Double[] { 20.1, 31.45, 48.76, 524.98 }));

		Map<String, Object> subMap3 = new HashMap<String, Object>();

		// private String name;
		subMap3.put("name", "无名");
		// private Integer age;
		subMap3.put("age", 99);
		// private String[] hobbys;
		subMap3.put("hobbys", Arrays.toString(new String[] { "唱歌", "打荣耀", "爬山" }));
		// private List<Double> costs;
		subMap3.put("costs", Arrays.toString(new Double[] { 90.1, 81.45, 78.76, 64.98 }));

		List<Map<String, Object>> subBeanDemoList = new ArrayList<Map<String, Object>>();
		subBeanDemoList.add(subMap1);
		subBeanDemoList.add(subMap2);
		subBeanDemoList.add(subMap3);

		map.put("subBeanDemoList", JSONObject.toJSONString(subBeanDemoList));

		return map;
	}

	public static BeanDemo getInstanceBeanDemo() {

		BeanDemo beanDemo = HTMLUtil.getMapToEntity(BeanDemo.class, getTreeMap());

		return beanDemo;
	}
}
