package com.fwk.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//分页信息
public class Page {

	private static String requestParam = "request-param";

	private Integer pageSize = 10;// 每页数量
	private Integer currentPage = 1;// 当前页,默认1
	private Integer total = 0;// 总数，默认0
	private Integer totalPage = 0;// 总页数，默认0
	private Integer startRow;// 查询的开始位置

	public Page() {
	}
	
	public Page pageTotal(int total,HttpServletRequest request){
		Page page = new Page(request);
		page.setTotal(total);
		page.setTotalPage(total/page.getPageSize()+(total%page.getPageSize() > 0 ? 1 : 0));
		return page;
	}

	public Page(HttpServletRequest request) {
		try {
			this.pageSize = request.getParameter("pageSize") != null
					? Integer.parseInt(request.getParameter("pageSize") + "") : 10;
			this.currentPage = request.getParameter("currentPage") != null
					? Integer.parseInt(request.getParameter("currentPage")) : 1;

			Object requestMapObj = request.getAttribute(requestParam);
			if (requestMapObj != null) {
				Map<String, Object> pageMap = (Map<String, Object>) requestMapObj;
				if (pageMap.get("currentPage") != null) {
					this.currentPage = Integer.parseInt(pageMap.get("currentPage").toString());
				}
				if (pageMap.get("pageSize") != null) {
					this.pageSize = Integer.parseInt(pageMap.get("pageSize").toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
		// 设置总页数
		this.totalPage = (this.total / this.pageSize + ((this.total % this.pageSize) > 0 ? 1 : 0));
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置总页数
	 * 
	 * <pre>
	 * 该方法为空实现（为了符合标准的java bean，所以提供了所有属性的 getter 和 setter 方法）。
	 * 其值已经在 setTotal() 方法中实现{@link #setTotal}
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2017-07-21 10:13:13
	 *
	 * @param totalPage
	 */
	public void setTotalPage(Integer totalPage) {
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

}
