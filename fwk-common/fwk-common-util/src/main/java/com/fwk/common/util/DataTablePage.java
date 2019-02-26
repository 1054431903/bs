package com.fwk.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataTablePage {

	private Object sEcho;// 第几次查询 DataTable内置参数 回传的数据格式必须包含此参数，不然
							// DataTables插件无法渲染

	private Integer iTotalRecords;// 总记录数

	private Integer iTotalDisplayRecords;// 筛选出来的记录数 JQuery DataTables 要求的参数
											// 其实没用
	private Integer pageSize;// 每页页数

	private Integer beginPageIndex;// 开始显示记录的坐标 DataTable 会传递此参数

	private List aaData;// 结果集

	private Integer currentPage;// 当前是第几页

	public static Integer PAGESIZE = 10;// 默认十条

	public Object getsEcho() {
		return sEcho;
	}

	public void setsEcho(Object sEcho) {
		this.sEcho = sEcho;
	}

	public Integer getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalRecords;
	}

	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords == null ? 0 : iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.setiTotalRecords(iTotalDisplayRecords);
	}

	public Integer getPageSize() {
		if (null == pageSize || 0 == pageSize) {
			pageSize = PAGESIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getBeginPageIndex() {
		if (beginPageIndex == null) {
			beginPageIndex = 0;
		}
		return beginPageIndex;
	}

	public void setBeginPageIndex(Integer beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public Integer getPageCount() {
		return (getiTotalDisplayRecords() + getPageSize() - 1) / getPageSize();
	}

	public List getAaData() {
		if (null == aaData) {
			aaData = new ArrayList();
		}
		return aaData;
	}

	public void setAaData(List aaData) {
		this.aaData = aaData;
	}

	public Integer getCurrentPage() {
		if(currentPage == null){
			currentPage = 1;
		}
		if (getPageCount() >= currentPage) {
			return currentPage;
		}
		return getPageCount();
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartRow() {
		int start = ( getCurrentPage() - 1);
		
		return (start > 0 ? start : 0) * getPageSize();
	}

	public Map getPageInfo() {
		Map pageInfo = new HashMap();
		pageInfo.put("currentPage", getCurrentPage());
		pageInfo.put("pageSize", getPageSize());
		pageInfo.put("totalRecord", getiTotalRecords());
		return pageInfo;
	}

}
