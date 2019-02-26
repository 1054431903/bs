package com.fwk.common.util;

import java.util.Map;

/**
 * 分页处理的工具类
 * 
 * @author wangjunwen
 * @date 2017-07-18 10:16:42
 *
 */
public class PageUtil {

	/**
	 * 获取 Page 对象 {@link #Page}，并设置分页查询所需的参数 startRow，参数 map 中必须已经包含了
	 * currentPage，pageSize 两个对象，并且必须为 Integer 类型
	 * 
	 * <pre>
	 * 	map 为  MyBatis 的查询参数，在该方法中已经设置了分页查询所需的参数。参数名分别为：
	 * 		startRow：从第几条记录开始（第一条为0，第二条为1，……，以此类推）
	 * 		pageSize：每页大小（原来的 map 中就已经存在）
	 * 	例如 MySQL 分页查询：SELECT * FROM tablename LIMIT #{startRow},#{pageSize}
	 * 
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2017-07-18 09:11:26
	 *
	 * @param map
	 *            分页所需的参数
	 * @return
	 */
	public static Page getPage(final Map<String, Object> map) {
		// 初始化分页查询的对象
		Page page = new Page();

		final Object currentPageParam = map.get("currentPage");
		final Object pageSizeParam = map.get("pageSize");

		if ((null != currentPageParam) && (currentPageParam instanceof Integer) && (null != pageSizeParam)
				&& (pageSizeParam instanceof Integer)) {

			page.setCurrentPage((Integer) currentPageParam);
			page.setPageSize((Integer) pageSizeParam);

			int startRow = ((page.getCurrentPage() - 1) * page.getPageSize());
			startRow = (startRow < 0 ? 0 : startRow);

			map.put("startRow", startRow);
			page.setStartRow(startRow);
		}

		return page;
	}

}
