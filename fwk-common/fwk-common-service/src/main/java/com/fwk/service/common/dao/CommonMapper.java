package com.fwk.service.common.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.entity.PositionCustomer;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.entity.ResourceEXT;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.entity.SupportOrgEXT;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.entity.UserDictEXT;

/**
 * 公用数据库操作类
 * 
 * @author wangjunwen
 * @date 2017-08-07 13:15:08
 *
 */
public interface CommonMapper {

	/**
	 * 添加资源
	 * 
	 * @author wangjunwen
	 * @date 2017-08-07 13:16:35
	 *
	 * @param pResourceEXT
	 *            资源对象
	 * @return
	 * @throws Exception
	 */
	Integer addResource(ResourceEXT pResourceEXT) throws Exception;

	/**
	 * 更新资源
	 * 
	 * @author wangjunwen
	 * @date 2017-08-07 13:16:35
	 *
	 * @param pResourceEXT
	 *            资源对象
	 * @return
	 * @throws Exception
	 */
	Integer updateResource(ResourceEXT pResourceEXT) throws Exception;

	/**
	 * 添加上传资源历史
	 * 
	 * @author wangjunwen
	 * @date 2017-08-10 13:35:14
	 *
	 * @param pResourcehistoryEXT
	 *            资源上传历史对象
	 * @return
	 * @throws Exception
	 */
	Integer addResourcehistory(ResourcehistoryEXT pResourcehistoryEXT) throws Exception;

	/**
	 * 更新上传资源历史
	 * 
	 * @author wangjunwen
	 * @date 2017-08-11 13:51:13
	 *
	 * @param pResourcehistoryEXT
	 *            资源上传历史对象
	 * @return
	 * @throws Exception
	 */
	Integer updateResourcehistory(ResourcehistoryEXT pResourcehistoryEXT) throws Exception;

	/**
	 * 查询出指定ID的上传资源历史
	 * 
	 * @author wangjunwen
	 * @date 2017-08-11 17:25:50
	 *
	 * @param resourcehistoryIdList
	 *            上传资源历史ID
	 * @return
	 * @throws Exception
	 */
	List<ResourcehistoryEXT> quereyResourcehistoryEXTId(
			@Param(value = "resourcehistoryIdList") List<Long> resourcehistoryIdList) throws Exception;

	/**
	 * 根据上传历史的ID查询出上传的资源
	 * 
	 * @param resourcehistoryid
	 *            上传历史的ID
	 * @return
	 * @throws Exception
	 */
	ResourceEXT queryResourceByHistoryid(@Param(value = "resourcehistoryid") Integer resourcehistoryid)
			throws Exception;

	/**
	 * 根据上传历史的资源的ID查询出所有资源
	 * 
	 * @param pResourcehistoryEXT
	 *            资源上传历史
	 * @return
	 * @throws Exception
	 */
	List<ResourceEXT> queryResourceByHistoryResourceid(ResourcehistoryEXT pResourcehistoryEXT) throws Exception;

	/**
	 * 根据资源的ID查询出资源
	 * 
	 * @param resourceId
	 *            资源的ID
	 * @return
	 * @throws Exception
	 */
	ResourceEXT queryResourceById(@Param(value = "resourceId") Integer resourceId) throws Exception;

	/**
	 * 查询出当前的时间
	 * 
	 * @author wangjunwen
	 * @date 2017年11月23日 下午3:19:11
	 *
	 * @return
	 * @throws Exception
	 */
	Date queryCurrentDate() throws Exception;

	/**
	 * 查询出当前的第一天的日期
	 * 
	 * @author wangjunwen
	 * @date 2017年11月23日 下午3:27:08
	 *
	 * @return
	 * @throws Exception
	 */
	Date queryCurrentMonthFirstDate() throws Exception;

	/**
	 * 根据资源的ID查询出资源列表
	 * 
	 * @param resourceIds
	 *            资源的ID（中间以半角逗点分隔。例如：10,25,39）
	 * @return
	 * @throws Exception
	 */
	List<ResourceEXT> queryResourceByIds(@Param(value = "resourceIds") String resourceIds) throws Exception;

	/**
	 * 根据行政区划编号查询出行政区划的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年1月24日 下午3:17:00
	 *
	 * @param code
	 *            行政区划编号
	 * @return
	 * @throws Exception
	 */
	PositionEXT queryPositionByCode(String code) throws Exception;

	/**
	 * 根据行政区划编号查询出同一个级别的所有行政区划的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年1月24日 下午3:17:00
	 *
	 * @param code
	 *            行政区划编号
	 * @return
	 * @throws Exception
	 */
	List<PositionCustomer> queryAllSameParentPositionByCode(String code) throws Exception;

	/**
	 * 查询出用户自定义的字典
	 * 
	 * @author wangjunwen
	 * @date 2018年2月27日 上午11:36:42
	 *
	 * @param pUserDictEXT
	 *            用户自定义的字典对象
	 * @return
	 * @throws Exception
	 */
	List<UserDictEXT> queryUserDictEXTList(UserDictEXT pUserDictEXT) throws Exception;

	/**
	 * 根据用户的ID查询出当前登录的用户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月8日 下午4:22:10
	 *
	 * @param paramMap
	 *            查询参数
	 * @return
	 * @throws Exception
	 */
	LoginUserInfo queryLoginUserInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 获取地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月31日 下午4:42:32
	 *
	 * @param pParcelEXT
	 *            地块的实体对象
	 * @return
	 * @throws Exception
	 */
	List<ParcelEXT> queryParcelList(ParcelEXT pParcelEXT) throws Exception;

	/**
	 * 查询出农事平台的组织信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月29日 上午11:10:52
	 *
	 * @param pSupportOrgEXT
	 *            支撑平台的组织扩展对象
	 * @return
	 * @throws Exception
	 */
	List<SupportOrgEXT> querySupportOrgEXTList(SupportOrgEXT pSupportOrgEXT) throws Exception;

	/**
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 上午10:13:22
	 *
	 * @param SupportUserEXT
	 *            支撑平台的用户信息扩展对象
	 * @return
	 * @throws Exception
	 */
	List<SupportUserEXT> querySupportUserEXTList(SupportUserEXT pSupportUserEXT) throws Exception;
}
