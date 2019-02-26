package com.fwk.common.util.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 字典的工具类
 * 
 * @author wangjunwen
 * @date 2017-07-28 15:47:12
 *
 */
public class DictUtil {

	/* TODO static 块 【开始】 **********************************************/
	private static Map<EDictType, List<Dict>> dictMap = new HashMap<EDictType, List<Dict>>();
	public static final Map<EDictType, List<Dict>> dictTypeMap = dictMap;
	static {
		EDictType eDictType = null;
		List<Dict> dictList = null;
		Iterator<Dict> iterator = getAllDict().iterator();
		Dict dict = null;
		while (iterator.hasNext()) {
			dict = iterator.next();

			eDictType = EDictType.valueOf(dict.getDictType());

			dictList = dictMap.get(eDictType);
			if (null == dictList) {
				dictList = new ArrayList<Dict>();
				dictList.add(dict);

				dictMap.put(eDictType, dictList);
			} else {
				dictList.add(dict);
			}
		}

		// 以字典分类进行排序
		for (List<Dict> list : dictMap.values()) {
			Collections.sort(list);
		}

		dictMap = Collections.unmodifiableMap(dictMap);
	}

	/* TODO static 块 【结束】 **********************************************/

	/**
	 * 获取某个分类的所有字典
	 * 
	 * @author wangjunwen
	 * @date 2017-07-31 13:33:20
	 *
	 * @param eDictType
	 *            字典类型
	 * @return
	 */
	public List<Dict> getDictByDictType(EDictType eDictType) {
		return dictMap.get(eDictType);
	}

	/**
	 * 获取所有字典
	 * 
	 * @author wangjunwen
	 * @date 2017-07-29 14:00:23
	 *
	 * @return
	 */
	public static List<Dict> getAllDict() {
		return EDict.DICT_lIST;
	}

}
