package com.fwk.service.common.support;

import java.util.List;
import java.util.Map;

import org.rapidoid.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.ResultWrap;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportCategoryUtil;

@Service
public class SupportCategoryService {
	
	@Autowired
	private SupportService supportService;

	/**
	 * @Description: 查询品类
	 *
	 * @param requestMap
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月13日 下午2:10:19
	 */
	public ResultWrap queryCategoryInfoFromSupport(Map<String, Object> requestMap) {
		System.out.println("支撑平台-品类查询----SupportCategoryController----queryCategoryInfoFromSupport:" + requestMap);
		JSONObject filterJson = SupportCategoryUtil.packageFilterJson(requestMap);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		JSONObject sortJson = SupportCategoryUtil.packSortJson();
		supportRequestParam.setSortJson(sortJson);
		return supportService.getRequest(AllJsonRequestUrl.Base_category, supportRequestParam);
	}
	
	/**
	 * @Description: 按品类编号列表查询品类
	 *
	 * @param categoryIdList
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyuqiang
	 * @date: 2018年3月13日 下午2:10:14
	 */
	public ResultWrap queryCategoryInfoFromSupportByIds(List<String> categoryIdList){
		System.out.println("支撑平台-品类查询----SupportCategoryController----queryCategoryInfoFromSupportByIds:" + categoryIdList);
		JSONObject filterJson = SupportCategoryUtil.packIdListFilterJson(categoryIdList);
		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(filterJson);
		JSONObject sortJson = SupportCategoryUtil.packSortJson();
		supportRequestParam.setSortJson(sortJson);
		// 解决支撑平台不带分页参数时只返回500条记录的问题
		if (categoryIdList.size() > 500) {
			Object result = supportService.requestSupportGetNew(AllJsonRequestUrl.Base_category + "/count",
					supportRequestParam);
			int count = Integer.parseInt(result.toString().trim());
			supportRequestParam.setPageJson(new JSONObject().fluentPut("limit", count).fluentPut("skip", 0));
		}
		return supportService.getRequest(AllJsonRequestUrl.Base_category, supportRequestParam);
	}

}
