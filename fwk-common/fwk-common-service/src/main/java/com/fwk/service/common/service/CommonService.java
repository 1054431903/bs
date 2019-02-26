package com.fwk.service.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.service.common.dao.CommonMapper;
import com.fwk.service.common.entity.LoginUserDepartmentInfo;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.LoginUserOrgInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.entity.PositionCustomer;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.entity.ResourceEXT;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.entity.SupportOrgEXT;
import com.fwk.service.common.entity.SupportUserEXT;
import com.fwk.service.common.entity.UserDictEXT;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportRequestParam;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.common.support.entity.AllJsonRequestUrl;
import com.fwk.service.common.support.util.SupportUtil;
import com.fwk.service.common.weather.weatherdtcom.MeteorologicalStation;
import com.fwk.service.common.weather.weatherdtcom.OutWeatherInfo;
import com.fwk.service.common.weather.weatherdtcom.WwwWeatherdtComService;

/**
 * 公用的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2017-08-04 15:00:53
 *
 */
@Service
public class CommonService {

	/** 组织 */
	private static final Integer TYP_ORG = new Integer(1);
	/** 部门 */
	private static final Integer TYP_DEPARTMENT = new Integer(2);

	// 公用数据库操作对象
	@Autowired
	private CommonMapper mCommonMapper;

	// 公共天气服务的业务逻辑处理类
	@Autowired
	private WwwWeatherdtComService mWwwWeatherdtComService;

	// 支撑平台-组织和部门操作
	@Autowired
	private SupportOrgService mSupportOrgService;

	// 支撑平台-用户操作对象
	@Autowired
	private SupportUserService mSupportUserService;

	// 调用支撑平台数据处理对象
	@Autowired
	private SupportService mSupportService;

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
	public Integer addResource(ResourceEXT pResourceEXT) throws Exception {
		return mCommonMapper.addResource(pResourceEXT);
	}

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
	public Integer updateResource(ResourceEXT pResourceEXT) throws Exception {
		return mCommonMapper.updateResource(pResourceEXT);
	}

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
	public Integer addResourcehistory(ResourcehistoryEXT pResourcehistoryEXT) throws Exception {
		return mCommonMapper.addResourcehistory(pResourcehistoryEXT);
	}

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
	public Integer updateResourcehistory(ResourcehistoryEXT pResourcehistoryEXT) throws Exception {
		return mCommonMapper.updateResourcehistory(pResourcehistoryEXT);
	}

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
	public List<ResourcehistoryEXT> quereyResourcehistoryEXTId(List<Long> resourcehistoryIdList) throws Exception {
		return mCommonMapper.quereyResourcehistoryEXTId(resourcehistoryIdList);
	}

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
	public ResourcehistoryEXT quereyResourcehistoryEXTId(Long resourcehistoryId) throws Exception {
		List<Long> resourcehistoryIdList = new ArrayList<Long>();
		resourcehistoryIdList.add(resourcehistoryId);

		List<ResourcehistoryEXT> resourcehistoryEXTList = mCommonMapper
				.quereyResourcehistoryEXTId(resourcehistoryIdList);

		return ((null == resourcehistoryEXTList) || (0 == resourcehistoryEXTList.size()) ? null
				: resourcehistoryEXTList.get(0));
	}

	/**
	 * 根据上传历史的ID查询出上传的资源
	 * 
	 * @param resourcehistoryid
	 *            上传历史的ID
	 * @return
	 * @throws Exception
	 */
	public ResourceEXT queryResourceByHistoryid(Integer resourcehistoryid) throws Exception {
		return mCommonMapper.queryResourceByHistoryid(resourcehistoryid);
	}

	/**
	 * 根据上传历史的资源的ID查询出所有资源
	 * 
	 * @param pResourcehistoryEXT
	 *            资源上传历史
	 * @return
	 * @throws Exception
	 */
	public List<ResourceEXT> queryResourceByHistoryResourceid(ResourcehistoryEXT pResourcehistoryEXT) throws Exception {
		return mCommonMapper.queryResourceByHistoryResourceid(pResourcehistoryEXT);
	}

	/**
	 * 根据资源的ID查询出资源
	 * 
	 * @param resourceId
	 *            资源的ID
	 * @return
	 * @throws Exception
	 */
	public ResourceEXT queryResourceById(Integer resourceId) throws Exception {
		return mCommonMapper.queryResourceById(resourceId);
	}

	/**
	 * 查询出当前的时间
	 * 
	 * @author wangjunwen
	 * @date 2017年11月23日 下午3:19:11
	 *
	 * @return
	 * @throws Exception
	 */
	public Date queryCurrentDate() throws Exception {
		return mCommonMapper.queryCurrentDate();
	}

	/**
	 * 查询出当前月的第一天的日期
	 * 
	 * @author wangjunwen
	 * @date 2017年11月23日 下午3:27:08
	 *
	 * @return
	 * @throws Exception
	 */
	public Date queryCurrentMonthFirstDate() throws Exception {
		return mCommonMapper.queryCurrentMonthFirstDate();
	}

	/**
	 * 根据资源的ID查询出资源列表
	 * 
	 * @param resourceIds
	 *            资源的ID（中间以半角逗点分隔。例如：10,25,39）
	 * @return
	 * @throws Exception
	 */
	public List<ResourceEXT> queryResourceByIds(String resourceIds) throws Exception {
		return mCommonMapper.queryResourceByIds(resourceIds);
	}

	/**
	 * 添加或者修改资源和资源的上传历史
	 * 
	 * <pre>
	 *		<ul>
	 * 			<li>删除资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史添加时设置为 null，修改时设置为真实的上传历史ID
	 * 	resourcepath：资源的URL地址设置为 null
	 * 			<li>添加或者修改资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史设置为 null
	 * 	resourcepath：资源的URL地址是必须的
	 * 	resourcename：资源名称没有就设置为 null
	 * 		</ul>
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 上午9:21:10
	 *
	 * @param resourcehistoryId
	 *            上传资源的历史
	 * @param resourcepath
	 *            资源的URL地址
	 * @param resourcename
	 *            资源名称
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public ResourcehistoryEXT addOrUpdateResource(final Integer resourcehistoryId, final String resourcepath,
			final String resourcename) throws Exception {
		return this.addOrUpdateResource(resourcehistoryId, resourcepath, resourcename,
				CommonConstantsUtil.DEFAULT_RESOURCE_ORDER_NO);
	}

	/**
	 * 添加或者修改资源和资源的上传历史
	 * 
	 * <pre>
	 * 		<ul>
	 * 			<li>删除资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史是必须的
	 * 	resourcepath：资源的URL地址设置为 null
	 * 			<li>添加或者修改资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史添加时设置为 null，修改时设置为真实的上传历史ID
	 * 	resourcepath：资源的URL地址是必须的
	 * 	resourcename：资源名称没有就设置为 null
	 * 	orderno：排序号自定义
	 * 		</ul>
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 上午9:21:10
	 *
	 * @param resourcehistoryId
	 *            上传资源的历史
	 * @param resourcepath
	 *            资源的URL地址
	 * @param resourcename
	 *            资源名称
	 * @param orderno
	 *            排序号
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public ResourcehistoryEXT addOrUpdateResource(final Integer resourcehistoryId, final String resourcepath,
			String resourcename, Integer orderno) throws Exception {
		return this.addOrUpdateResource(resourcehistoryId, resourcepath, resourcename, orderno, null);
	}

	/**
	 * 添加或者修改资源和资源的上传历史
	 * 
	 * <pre>
	 * 		<ul>
	 * 			<li>删除资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史是必须的
	 * 	resourcepath：资源的URL地址设置为 null
	 * 			<li>添加或者修改资源的调用方式</li>
	 * 	resourcehistoryId：上传资源的历史添加时设置为 null，修改时设置为真实的上传历史ID
	 * 	resourcepath：资源的URL地址是必须的
	 * 	resourcename：资源名称没有就设置为 null
	 * 	orderno：排序号自定义
	 * 		</ul>
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 上午9:21:10
	 *
	 * @param resourcehistoryId
	 *            上传资源的历史
	 * @param resourcepath
	 *            资源的URL地址
	 * @param resourcename
	 *            资源名称
	 * @param orderno
	 *            排序号
	 * @param timelength
	 *            音、视频的时长（单位：秒）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public ResourcehistoryEXT addOrUpdateResource(final Integer resourcehistoryId, final String resourcepath,
			String resourcename, Integer orderno, Integer timelength) throws Exception {

		if (ObjectUtil.isBlank(resourcehistoryId) && ObjectUtil.isBlank(resourcepath)) {
			return null;
		}

		// 重置不符合规范的资源名称
		resourcename = (null == resourcename ? "" : resourcename);

		// 重置不符合规范的排序号
		orderno = (null == orderno ? 0 : orderno);

		Integer resultAddOrUpdate = null;

		// 上传资源历史
		ResourcehistoryEXT resourcehistoryEXT = null;
		// 上传的资源
		ResourceEXT resourceEXT = null;

		if (ObjectUtil.isNotBlank(resourcehistoryId)) {
			resourcehistoryEXT = this.quereyResourcehistoryEXTId(resourcehistoryId.longValue());
		}

		if ((null != resourcehistoryEXT)) {

			// 资源访问地址为空，默认将资源引用设置为 null（当前没有资源引用）
			if (ObjectUtil.isBlank(resourcepath)) {
				resourcehistoryEXT.setCurrentresource(null);// 设置当前使用的资源为 null

				// 更新资源的历史
				resultAddOrUpdate = this.updateResourcehistory(resourcehistoryEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Resourcehistory failure !");
				}
			} else {
				// 是否使用了历史的资源
				List<ResourceEXT> originalResourceEXTList = this.queryResourceByHistoryResourceid(resourcehistoryEXT);

				if ((null != originalResourceEXTList) && (originalResourceEXTList.size() > 0)) {

					for (ResourceEXT originalResourceEX : originalResourceEXTList) {

						// 只要资源的访问地址相同，证明是使用了历史资源
						if ((ObjectUtil.toString(originalResourceEX.getResourcepath())
								.equals(ObjectUtil.toString(resourcepath)))) {

							// 资源的URL地址相同，资源名称不同时需要更新资源名
							if (ObjectUtil.isNotBlank(resourcename)
									&& (!(ObjectUtil.toString(originalResourceEX.getResourcename())
											.equals(ObjectUtil.toString(resourcename)))
											|| !(ObjectUtil.toString(originalResourceEX.getTimelength())
													.equals(ObjectUtil.toString(timelength))))) {

								// 资源名称有更新
								if (!(ObjectUtil.toString(originalResourceEX.getResourcename())
										.equals(ObjectUtil.toString(resourcename)))) {

									originalResourceEX.setResourcename(resourcename);// 设置资源名称
								}

								// 时长有更新
								if (!(ObjectUtil.toString(originalResourceEX.getTimelength())
										.equals(ObjectUtil.toString(timelength)))) {

									originalResourceEX.setTimelength(timelength);// 设置音、视频的时长（单位：秒）
								}

								// 更新资源
								resultAddOrUpdate = this.updateResource(originalResourceEX);

								if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
									throw new Exception("update Resource failure !");
								} else {
									break;// 更新了资源直接跳出 for 循环
								}
							}

							// 如果使用的是历史资源，那么判断资源历史中当前使用的资源是否与该资源相同，如果不相同就需要将资源历史中当前使用的资源与该资源保持一致
							if (!(ObjectUtil.toString(resourcehistoryEXT.getCurrentresource())
									.equals(ObjectUtil.toString(originalResourceEX.getId())))
									|| !(orderno.equals(resourcehistoryEXT.getOrderno()))) {

								// 更新当前引用的资源的ID
								if (!(ObjectUtil.toString(resourcehistoryEXT.getCurrentresource())
										.equals(ObjectUtil.toString(originalResourceEX.getId())))) {
									resourcehistoryEXT.setCurrentresource(originalResourceEX.getId());// 当前使用的资源
								}

								// 更新排序号
								if (!(orderno.equals(resourcehistoryEXT.getOrderno()))) {
									resourcehistoryEXT.setOrderno(orderno);// 设置排序号
								}

								// 更新资源上传的历史
								resultAddOrUpdate = this.updateResourcehistory(resourcehistoryEXT);

								if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
									throw new Exception("update Resourcehistory failure !");
								}
							}

							// 只要资源的访问地址相同，证明是使用了历史资源
							resourceEXT = originalResourceEX;

							break;
						}

					} // for 循环结束

				}

				// 资源的历史纪录中没有可引用的资源时新增资源，并更资源的当前引用资源以及引用资源的历史记录
				if (null == resourceEXT) {
					resourceEXT = new ResourceEXT();
					resourceEXT.setResourcepath(resourcepath);// 访问资源的地址
					resourceEXT.setResourcename(resourcename);// 资源名称
					resourceEXT.setTimelength(timelength);// 设置音、视频的时长（单位：秒）

					// 添加资源
					resultAddOrUpdate = this.addResource(resourceEXT);

					if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
						throw new Exception("add Resource failure !");
					} else {
						resourcehistoryEXT.setCurrentresource(resourceEXT.getId());// 当前使用的资源

						// 设置使用资源的历史
						if (ObjectUtil.isBlank(resourcehistoryEXT.getResourceid())) {
							resourcehistoryEXT.setResourceid(resourceEXT.getId().toString());// 使用资源的历史
						} else {
							String resourceIds = (ObjectUtil.toString(resourcehistoryEXT.getResourceid()) + ","
									+ resourceEXT.getId());
							resourcehistoryEXT.setResourceid(resourceIds);// 使用资源的历史
						}

						// 更新资源上传的历史
						resultAddOrUpdate = this.updateResourcehistory(resourcehistoryEXT);

						if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
							throw new Exception("update Resourcehistory failure !");
						}
					}
				}
			}
		} else {// 新增资源以及资源的上传记录
			resourceEXT = new ResourceEXT();
			resourceEXT.setResourcepath(resourcepath);// 访问资源的地址
			resourceEXT.setResourcename(resourcename);// 资源名称
			resourceEXT.setTimelength(timelength);// 设置音、视频的时长（单位：秒）

			// 添加资源
			resultAddOrUpdate = this.addResource(resourceEXT);

			if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
				throw new Exception("add Resource failure !");
			} else {
				resourcehistoryEXT = new ResourcehistoryEXT();
				resourcehistoryEXT.setManame(ObjectUtil.toString(resourcename));// 资源名称
				resourcehistoryEXT.setOrderno(orderno);// 排序号
				resourcehistoryEXT.setCurrentresource(resourceEXT.getId());// 当前使用的资源
				resourcehistoryEXT.setResourceid(resourceEXT.getId().toString());// 使用资源的历史记录

				// 更新资源上传的历史
				resultAddOrUpdate = this.addResourcehistory(resourcehistoryEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("add Resourcehistory failure !");
				} else {
					// 返回之前先赋值
					resourcehistoryEXT.setResourcepath(resourcepath);
					resourcehistoryEXT.setResourcename(resourcename);
				}
			}
		}

		return resourcehistoryEXT;
	}

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
	public PositionEXT queryPositionByCode(Long code) throws Exception {
		if (null == code) {
			return null;
		}

		return this.queryPositionByCode(String.valueOf(code));
	}

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
	public PositionEXT queryPositionByCode(String code) throws Exception {

		// 最终要返回的行政区划对象
		final PositionEXT positionEXT = mCommonMapper.queryPositionByCode(code);

		if ((null != positionEXT)) {
			// 行政区划的全称
			final StringBuffer allnameBuffer = new StringBuffer();
			allnameBuffer.insert(0, positionEXT.getName());

			int level = Integer.parseInt(positionEXT.getLevel());

			PositionEXT tempPositionEXT = new PositionEXT();
			tempPositionEXT.setParentcode(positionEXT.getParentcode());

			EDict eDict = null;
			// 排除掉国家级的行政区划
			for (int i = (level - 1); i > 1; i--) {
				tempPositionEXT = mCommonMapper.queryPositionByCode(tempPositionEXT.getParentcode());

				if ((null == tempPositionEXT)) {
					break;
				}

				// 设置行政区划的全称
				allnameBuffer.insert(0, tempPositionEXT.getName());

				eDict = EDict.getEDictByType(EDictType.position_level, tempPositionEXT.getLevel());

				if (EDict.position_level_nation.equals(eDict)) {// 国家

					positionEXT.setNationname(tempPositionEXT.getName());
					positionEXT.setNationcode(tempPositionEXT.getCode());

				} else if (EDict.position_level_province.equals(eDict)) {// 省

					positionEXT.setProvincename(tempPositionEXT.getName());
					positionEXT.setProvincecode(tempPositionEXT.getCode());

				} else if (EDict.position_level_city.equals(eDict)) {// 市

					positionEXT.setCityname(tempPositionEXT.getName());
					positionEXT.setCitycode(tempPositionEXT.getCode());

				} else if (EDict.position_level_county.equals(eDict)) {// 区/县

					positionEXT.setCountyname(tempPositionEXT.getName());
					positionEXT.setCountycode(tempPositionEXT.getCode());

				} else if (EDict.position_level_town.equals(eDict)) {// 乡/镇

					positionEXT.setTownname(tempPositionEXT.getName());
					positionEXT.setTowncode(tempPositionEXT.getCode());

				} else if (EDict.position_level_village.equals(eDict)) {// 村委会

					positionEXT.setVillagename(tempPositionEXT.getName());
					positionEXT.setVillagecode(tempPositionEXT.getCode());

				}
			}

			positionEXT.setAllname(allnameBuffer.toString());
		}

		return positionEXT;
	}

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
	public List<PositionCustomer> queryAllSameParentPositionByCode(String code) throws Exception {
		return mCommonMapper.queryAllSameParentPositionByCode(code);
	}

	/**
	 * 获取像数据字典一样的行政区划地址
	 * 
	 * @author wangjunwen
	 * @date 2018年1月26日 下午12:36:40
	 *
	 * @param code
	 *            行政区划编号
	 * @return
	 * @throws Exception
	 */
	public List<List<PositionCustomer>> querypositionlistasdictionary(String code) throws Exception {
		List<List<PositionCustomer>> positionList = new ArrayList<List<PositionCustomer>>();

		List<PositionCustomer> childPositionList = null;

		while (true) {
			childPositionList = this.queryAllSameParentPositionByCode(code);
			if ((null != childPositionList) && (childPositionList.size() > 0)) {
				code = childPositionList.get(0).getParentcode();

				positionList.add(0, childPositionList);
			} else {
				break;
			}
		}

		return positionList;
	}

	/**
	 * 根据行政区划查询公共天气
	 * 
	 * @author wangjunwen
	 * @date 2018年2月13日 下午2:11:22
	 *
	 * @param positionEXT
	 *            行政区划对象
	 * @return
	 * @throws Exception
	 */
	public OutWeatherInfo queryPublicWeather(PositionEXT positionEXT) throws Exception {
		// 设置查询参数
		final MeteorologicalStation paramMeteorologicalStation = new MeteorologicalStation();
		paramMeteorologicalStation.setAreaProvince(positionEXT.getProvincename());
		paramMeteorologicalStation.setAreaCity(positionEXT.getCityname());
		paramMeteorologicalStation.setAreaCounty(positionEXT.getCountyname());
		paramMeteorologicalStation.setAreaTown(positionEXT.getTownname());

		// 获取天气信息
		OutWeatherInfo outWeatherInfo = mWwwWeatherdtComService.getWeather(paramMeteorologicalStation);

		return outWeatherInfo;
	}

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
	public List<UserDictEXT> queryUserDictEXTList(UserDictEXT pUserDictEXT) throws Exception {
		return mCommonMapper.queryUserDictEXTList(pUserDictEXT);
	}

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
	public LoginUserInfo queryLoginUserInfo(Map<String, Object> paramMap) throws Exception {

		// 获取操作者的ID
		String userId = ObjectUtil.toString(paramMap.get(HTMLUtil.common_request_param_operateid));
		userId = userId.trim();

		final LoginUserInfo paramLoginUserInfo = HTMLUtil.getMapToEntity(LoginUserInfo.class, paramMap);
		paramLoginUserInfo.setId((userId.matches("\\d+") ? Integer.parseInt(userId) : null));// 设置用户的ID

		if ((null == paramLoginUserInfo.getId()) || (null == paramLoginUserInfo.getLoginorgid())) {
			return null;
		}

		LoginUserInfo loginUserInfo = mCommonMapper.queryLoginUserInfo(paramMap);

		if ((null == loginUserInfo)) {
			return null;
		} else {
			loginUserInfo.setLoginorgid(paramLoginUserInfo.getLoginorgid());
		}

		// ------------- 设置当前用户所登录的组织 ----------------------------------------
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("id", loginUserInfo.getSupportid());

		List<Map<String, Object>> orgList = mSupportOrgService.queryOrgByOperateid(requestMap);

		if ((null != orgList) && (orgList.size() > 0)) {
			LoginUserOrgInfo tempLoginUserOrgInfo = null;

			for (Map<String, Object> map : orgList) {
				tempLoginUserOrgInfo = HTMLUtil.getMapToEntity(LoginUserOrgInfo.class, map);

				if (paramLoginUserInfo.getLoginorgid().equals(tempLoginUserOrgInfo.getSupportid())) {
					loginUserInfo.setLoginuserorginfo(tempLoginUserOrgInfo);
				}
			}
		}

		// ----------------- 设置当前登录的用户所在的部门 -------------------------------
		JSONObject tempJSONObject = null;
		JSONArray tempJSONArray = null;

		if ((null != loginUserInfo)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", loginUserInfo.getSupportid());

			tempJSONObject = mSupportOrgService.queryUserDeptBySupportId(map);
		}

		if (null != tempJSONObject) {
			// 获取组织
			tempJSONArray = tempJSONObject.getJSONArray("tenants");
		} else {
			return null;
		}

		List<String> orgIdOrDepartmentIdList = null;

		if ((null != tempJSONArray) && (tempJSONArray.size() > 0)) {

			orgIdOrDepartmentIdList = new ArrayList<String>();

			for (int i = 0; i < tempJSONArray.size(); i++) {
				tempJSONObject = tempJSONArray.getJSONObject(i);

				orgIdOrDepartmentIdList.add(tempJSONObject.getString("$oid"));
			}
		}

		if ((null != orgIdOrDepartmentIdList)) {

			List<LoginUserDepartmentInfo> loginUserDepartmentInfoList = new ArrayList<LoginUserDepartmentInfo>();

			LoginUserDepartmentInfo tempLoginUserDepartmentInfo = null;

			for (String id : orgIdOrDepartmentIdList) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);

				map = mSupportOrgService.queryDepartmentDetail(map);

				if ((null != map) && (map.size() > 0)) {

					tempLoginUserDepartmentInfo = HTMLUtil.getMapToEntity(LoginUserDepartmentInfo.class, map);

					loginUserDepartmentInfoList.add(tempLoginUserDepartmentInfo);
				}
			}

			loginUserInfo.setDepartmentsupportidlist(orgIdOrDepartmentIdList);

			loginUserInfo.setLoginuserdepartmentinfolist(loginUserDepartmentInfoList);
		}

		return loginUserInfo;
	}

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
	public List<ParcelEXT> queryParcelList(ParcelEXT pParcelEXT) throws Exception {
		return mCommonMapper.queryParcelList(pParcelEXT);
	}

	/**
	 * 根据支撑平台的组织ID获取组织信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月29日 上午11:37:48
	 *
	 * @param supportOrgId
	 *            支撑平台的组织ID
	 * @return
	 * @throws Exception
	 */
	public SupportOrgEXT querySupportOrgBySupportid(String supportOrgId) throws Exception {

		if (ObjectUtil.isBlank(supportOrgId)) {
			return null;
		}

		SupportOrgEXT finalSupportOrgEXT = null;

		SupportOrgEXT paramSupportOrgEXT = new SupportOrgEXT();
		paramSupportOrgEXT.setSupportid(supportOrgId);

		List<SupportOrgEXT> supportOrgEXTList = mCommonMapper.querySupportOrgEXTList(paramSupportOrgEXT);

		JSONObject orgInfoJSONObject = null;

		if ((null != supportOrgEXTList) && (supportOrgEXTList.size() > 0)) {
			orgInfoJSONObject = (JSONObject) JSONObject.toJSON(supportOrgEXTList.get(0));
		} else {
			orgInfoJSONObject = new JSONObject();
		}

		// 从支撑平台获取组织信息
		JSONObject supportOrgJSONObject = mSupportOrgService.getSupportTenantById(supportOrgId);
		if (null == supportOrgJSONObject) {
			supportOrgJSONObject = new JSONObject();
		}

		String supportOrgLogo = null;
		if (supportOrgJSONObject.keySet().size() > 0) {
			supportOrgLogo = supportOrgJSONObject.getString("logo");
			supportOrgJSONObject.remove("logo");// 排除数据类型不一致的.
		}

		for (String key : orgInfoJSONObject.keySet()) {
			// 排除掉扩展的信息（如果农事系统扩展的信息与支撑平台的相同，那么需要保留农事系统的扩展信息）
			supportOrgJSONObject.remove(key);
		}

		orgInfoJSONObject = orgInfoJSONObject.fluentPutAll(supportOrgJSONObject);

		if (orgInfoJSONObject.keySet().size() > 0) {
			finalSupportOrgEXT = orgInfoJSONObject.toJavaObject(SupportOrgEXT.class);
		}

		if (null != finalSupportOrgEXT) {
			finalSupportOrgEXT.setSupportid(supportOrgId);// 设置支撑平台的组织ID

			// 如果农事平台的组织的 Logo 为空，那么使用支撑平台的
			if (null == finalSupportOrgEXT.getLogourl()) {
				finalSupportOrgEXT.setLogourl(supportOrgLogo);
			}

			// ------------------ 设置组织所在地 ------------------
			String registeredAddressJson = JSONObject.toJSONString(orgInfoJSONObject.get("registeredAddress"));

			if (!("null".equals(registeredAddressJson))) {

				String provinceCode = null;// 省份
				String city = null;// 地市
				String district = null;// 区县
				String street = null;// 详细地址

				if (registeredAddressJson.startsWith("{")) {// 是对象
					JSONObject registeredAddressJSONObject = (JSONObject) orgInfoJSONObject.get("registeredAddress");

					provinceCode = ((JSONObject) registeredAddressJSONObject.get("province")).getString("$oid");

					city = ((JSONObject) registeredAddressJSONObject.get("city")).getString("$oid");

					district = ((JSONObject) registeredAddressJSONObject.get("district")).getString("$oid");

					street = registeredAddressJSONObject.getString("street");

				} else if (registeredAddressJson.startsWith("[")) {// 是数组
					JSONArray registeredAddressJSONArray = (JSONArray) orgInfoJSONObject.get("registeredAddress");

					JSONObject addressJSONObject = null;
					for (int i = 0; i < registeredAddressJSONArray.size(); i++) {
						addressJSONObject = registeredAddressJSONArray.getJSONObject(i);

						if (1 == registeredAddressJSONArray.size()) {
							provinceCode = ((JSONObject) addressJSONObject.get("province")).getString("$oid");

							city = ((JSONObject) addressJSONObject.get("city")).getString("$oid");

							city = ((JSONObject) addressJSONObject.get("city")).getString("$oid");

							street = addressJSONObject.getString("street");

						} else {
							if (null == provinceCode) {
								provinceCode = ((JSONObject) addressJSONObject.get("province")).getString("$oid");
							}

							if (null == city) {
								city = ((JSONObject) addressJSONObject.get("city")).getString("$oid");
							}

							if (null == district) {
								city = ((JSONObject) addressJSONObject.get("city")).getString("$oid");
							}

							if (null == street) {
								street = addressJSONObject.getString("street");
							}
						}
					}
				}

				String addressCode = null;// 组织的地址的最后一级行政区划编号

				if ((null != district)) {// 区县
					addressCode = district;
				} else if ((null != city)) {// 地市
					addressCode = city;
				} else if ((null != provinceCode)) {// 省份
					addressCode = provinceCode;
				}

				// 设置组织的地址
				if (null != addressCode) {
					PositionEXT tempPositionEXT = this.queryPositionByCode(addressCode);

					if (null != tempPositionEXT) {
						finalSupportOrgEXT.setAddressscodedetail(
								(ObjectUtil.toString(tempPositionEXT.getAllname()) + ObjectUtil.toString(street)));
					}
				} else {
					finalSupportOrgEXT.setAddressscodedetail(ObjectUtil.toString(street));
				}
			}

		}

		return finalSupportOrgEXT;
	}

	/**
	 * 获取支撑平台上的用户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 上午10:28:18
	 *
	 * @param pSupportUserEXT
	 *            支撑平台是用户信息的扩展对象
	 * @return
	 * @throws Exception
	 */
	public SupportUserEXT querySupportUserEXTByIdOrSupportid(SupportUserEXT pSupportUserEXT) throws Exception {

		if ((ObjectUtil.isBlank(pSupportUserEXT.getId())) && (ObjectUtil.isBlank(pSupportUserEXT.getSupportid()))) {
			return null;
		}

		SupportUserEXT innerSupportUserEXT = null;

		try {
			// --------- 获取农事系统内的用户信息 ------------------
			SupportUserEXT paramSupportUserEXT = new SupportUserEXT();
			paramSupportUserEXT.setId(pSupportUserEXT.getId());
			paramSupportUserEXT.setSupportid(pSupportUserEXT.getSupportid());

			List<SupportUserEXT> list = mCommonMapper.querySupportUserEXTList(paramSupportUserEXT);

			if ((null != list) && (list.size() > 0)) {
				innerSupportUserEXT = list.get(0);
				paramSupportUserEXT.setSupportid(innerSupportUserEXT.getSupportid());
			}

			// ---------- 获取支撑平台上的用户信息 -------------------
			SupportUserEXT outerSupportUserEXT = null;
			if (ObjectUtil.isNotBlank(paramSupportUserEXT.getSupportid())) {
				JSONObject supportUserEXTJSONObject = mSupportUserService
						.queryUserDetailBySupportid(paramSupportUserEXT.getSupportid());

				if ((null != supportUserEXTJSONObject)) {

					JSONObject birthday = supportUserEXTJSONObject.getJSONObject("birthday");

					if (null != birthday) {
						String birthdayDate = birthday.getString("$numberLong");

						if (birthdayDate.matches("\\d+")) {
							supportUserEXTJSONObject.put("birthday", new Date(Long.valueOf(birthdayDate)));
						} else {
							supportUserEXTJSONObject.put("birthday", DateTimeUtil.parse(birthdayDate));
						}

					}

					final String supportid = supportUserEXTJSONObject.getJSONObject("_id").getString("$oid");
					supportUserEXTJSONObject.remove("_id");

					outerSupportUserEXT = JSONObject.toJavaObject(supportUserEXTJSONObject, SupportUserEXT.class);

					outerSupportUserEXT.setSupportid(supportid);
				}
			}

			// 既未获取到农事系统内部的用户信息，又未获取到支撑平台上的用户信息，那么直接返回一个 null 对象
			if ((null == innerSupportUserEXT) && (null == outerSupportUserEXT)) {
				return null;
			}

			// 对用户信息的再加工处理然后返回给调用方（农事系统内部的用户信息属性与支撑平台一致的，那么优先使用农事系统内的属性值）
			if (null == innerSupportUserEXT) {
				innerSupportUserEXT = new SupportUserEXT();

				if (null != outerSupportUserEXT) {
					innerSupportUserEXT.setSupportid(outerSupportUserEXT.getSupportid());
				}
			}

			if (null == outerSupportUserEXT) {
				outerSupportUserEXT = new SupportUserEXT();
				outerSupportUserEXT.setSupportid(innerSupportUserEXT.getSupportid());
			}

			// 拷贝属性值
			this.selectivityCopyProperties(innerSupportUserEXT, outerSupportUserEXT);
		} catch (Exception e) {
			// 忽略异常
			innerSupportUserEXT = null;
			e.printStackTrace();
		}

		return innerSupportUserEXT;
	}

	/**
	 * 获取支撑平台上的用户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 上午10:28:18
	 *
	 * @param userSupportidList
	 *            支撑平台的用户ID列表
	 * @return
	 * @throws Exception
	 */
	private List<SupportUserEXT> querySupportUserEXTByIdOrSupportidList(List<String> userSupportidList)
			throws Exception {

		if ((null == userSupportidList) || (userSupportidList.isEmpty())) {
			return null;
		} else {

			// 去重
			final Set<String> userSupportidSet = new HashSet<String>();
			userSupportidSet.addAll(userSupportidList);

			userSupportidList = new ArrayList<String>();
			userSupportidList.addAll(userSupportidSet);

			// 再把空的排除掉
			Iterator<String> userIdIterator = userSupportidList.iterator();
			while (userIdIterator.hasNext()) {
				if (ObjectUtil.isBlank(userIdIterator.next())) {
					userIdIterator.remove();
				}
			}

			if (userSupportidList.isEmpty()) {
				return null;
			}
		}

		JSONArray supportUserEXTJSONArray = mSupportUserService.queryUserDetailBySupportidList(userSupportidList);

		if ((null == supportUserEXTJSONArray) || (0 == supportUserEXTJSONArray.size())) {
			return null;
		}

		List<SupportUserEXT> list = new ArrayList<SupportUserEXT>();

		SupportUserEXT outerSupportUserEXT = null;
		JSONObject supportUserEXTJSONObject = null;
		JSONObject birthday = null;
		String birthdayDate = null;
		String supportid = null;

		for (int i = 0; i < supportUserEXTJSONArray.size(); i++) {

			supportUserEXTJSONObject = supportUserEXTJSONArray.getJSONObject(i);

			birthday = supportUserEXTJSONObject.getJSONObject("birthday");

			if (null != birthday) {
				birthdayDate = birthday.getString("$numberLong");

				if (birthdayDate.matches("\\d+")) {
					supportUserEXTJSONObject.put("birthday", new Date(Long.valueOf(birthdayDate)));
				} else {
					supportUserEXTJSONObject.put("birthday", DateTimeUtil.parse(birthdayDate));
				}

			}

			supportid = supportUserEXTJSONObject.getJSONObject("_id").getString("$oid");
			supportUserEXTJSONObject.remove("_id");

			outerSupportUserEXT = JSONObject.toJavaObject(supportUserEXTJSONObject, SupportUserEXT.class);

			outerSupportUserEXT.setSupportid(supportid);

			list.add(outerSupportUserEXT);
		}

		return list;
	}

	/**
	 * 农事系统内的用户信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月31日 上午10:28:18
	 *
	 * @param userIdList
	 *            农事系统内的用户ID列表
	 * @return
	 * @throws Exception
	 */
	private List<SupportUserEXT> querySupportUserEXTByUserIdList(List<Integer> userIdList) throws Exception {

		if ((null == userIdList) || (userIdList.isEmpty())) {
			return null;
		} else {

			// 去重
			final Set<Integer> userIdSet = new HashSet<Integer>();
			userIdSet.addAll(userIdList);

			userIdList = new ArrayList<Integer>();
			userIdList.addAll(userIdSet);

			// 再把空的排除掉
			Iterator<Integer> userIdIterator = userIdList.iterator();
			while (userIdIterator.hasNext()) {
				if (ObjectUtil.isBlank(userIdIterator.next())) {
					userIdIterator.remove();
				}
			}

			if (userIdList.isEmpty()) {
				return null;
			}
		}

		SupportUserEXT paramSupportUserEXT = new SupportUserEXT();
		paramSupportUserEXT.setIdlist(userIdList);

		List<SupportUserEXT> list = mCommonMapper.querySupportUserEXTList(paramSupportUserEXT);

		return list;
	}

	/**
	 * 根据农事系统内部的用户的ID查询出用户信息（系统内部的用户信息和支撑平台的用户信息数据合并）
	 * 
	 * @author wangjunwen
	 * @date 2018年4月12日 上午9:52:00
	 *
	 * @param userIdList
	 *            农事系统内部的用户的ID
	 * @return
	 * @throws Exception
	 */
	public Map<String, SupportUserEXT> querySupportUserEXTByUseridList(List<Integer> userIdList) throws Exception {
		Map<String, SupportUserEXT> supportUserEXTMap = new HashMap<String, SupportUserEXT>();
		if ((null == userIdList) || (userIdList.isEmpty())) {
			return supportUserEXTMap;
		}

		// 农事系统内部的用户信息
		List<SupportUserEXT> innerSupportUserEXTList = this.querySupportUserEXTByUserIdList(userIdList);

		if ((null == innerSupportUserEXTList) || (innerSupportUserEXTList.isEmpty())) {
			return supportUserEXTMap;
		}

		Set<String> supportidSet = new HashSet<String>();

		for (SupportUserEXT innerSupportUserEXT : innerSupportUserEXTList) {
			supportidSet.add(innerSupportUserEXT.getSupportid());
		}

		List<String> userSupportidList = new ArrayList<String>();
		userSupportidList.addAll(supportidSet);

		// 支撑平台的用户信息
		List<SupportUserEXT> outerSupportUserEXTList = this.querySupportUserEXTByIdOrSupportidList(userSupportidList);

		// 最终合并的数据
		supportUserEXTMap = this.selectivityCopyProperties(innerSupportUserEXTList,
				outerSupportUserEXTList);

		return supportUserEXTMap;
	}

	/**
	 * 选择性赋值
	 * 
	 * <pre>
	 * 	优先使用农事系统中的属性值，如果农事系统中的属性值为空，那么拷贝支撑平台中的属性值
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午1:31:05
	 *
	 * @param innerSupportUserEXTList
	 *            农事系统内的用户信息列表
	 * @param outerSupportUserEXTList
	 *            支撑平台上的用户信息列表
	 */
	private Map<String, SupportUserEXT> selectivityCopyProperties(final List<SupportUserEXT> innerSupportUserEXTList,
			final List<SupportUserEXT> outerSupportUserEXTList) {
		final Map<String, SupportUserEXT> supportUserEXTMap = new HashMap<String, SupportUserEXT>();
		if (((null == innerSupportUserEXTList) || (innerSupportUserEXTList.isEmpty()))
				&& ((null == outerSupportUserEXTList) || (outerSupportUserEXTList.isEmpty()))) {
			return supportUserEXTMap;
		}

		// final Map<String, SupportUserEXT> supportUserEXTMap = new HashMap<String, SupportUserEXT>(); 上移到方法的第一行

		if (((null != innerSupportUserEXTList) && !(innerSupportUserEXTList.isEmpty()))
				&& ((null == outerSupportUserEXTList) || (outerSupportUserEXTList.isEmpty()))) {

			for (SupportUserEXT innerSupportUserEXT : innerSupportUserEXTList) {
				supportUserEXTMap.put(ObjectUtil.toString(innerSupportUserEXT.getId()), innerSupportUserEXT);
			}

		} else if (((null == innerSupportUserEXTList) || (innerSupportUserEXTList.isEmpty()))
				&& ((null != outerSupportUserEXTList) && !(outerSupportUserEXTList.isEmpty()))) {

			for (SupportUserEXT outerSupportUserEXT : outerSupportUserEXTList) {
				supportUserEXTMap.put(outerSupportUserEXT.getSupportid(), outerSupportUserEXT);
			}

		} else {
			SupportUserEXT innerSupportUserEXT = null;
			SupportUserEXT outerSupportUserEXT = null;
			boolean isCopyValue = false;
			for (int i = 0; i < innerSupportUserEXTList.size(); i++) {

				innerSupportUserEXT = innerSupportUserEXTList.get(i);

				for (int j = 0; j < outerSupportUserEXTList.size(); j++) {

					outerSupportUserEXT = outerSupportUserEXTList.get(j);

					if (ObjectUtil.toString(outerSupportUserEXT.getSupportid())
							.equals(ObjectUtil.toString(innerSupportUserEXT.getSupportid()))) {
						break;
					}
				}

				// 拷贝属性值
				isCopyValue = this.selectivityCopyProperties(innerSupportUserEXT, outerSupportUserEXT);

				if (isCopyValue) {
					supportUserEXTMap.put(ObjectUtil.toString(innerSupportUserEXT.getId()), innerSupportUserEXT);
				} else {
					supportUserEXTMap.put(outerSupportUserEXT.getSupportid(), outerSupportUserEXT);
				}
			}

		}

		return supportUserEXTMap;
	}

	/**
	 * 拷贝属性值
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午2:08:09
	 *
	 * @param innerSupportUserEXT
	 *            农事系统内部的用户信息
	 * @param outerSupportUserEXT
	 *            支撑平台的用户信息
	 */
	public boolean selectivityCopyProperties(final SupportUserEXT innerSupportUserEXT,
			final SupportUserEXT outerSupportUserEXT) {

		if ((null == innerSupportUserEXT) || (null == outerSupportUserEXT)) {
			return false;
		} else if (!(ObjectUtil.toString(innerSupportUserEXT.getSupportid())
				.equals(ObjectUtil.toString(outerSupportUserEXT.getSupportid())))) {
			return false;
		} else {
			// 只有支撑平台上的用户ID与农事系统内的用户的支撑ID一致才能拷贝
			outerSupportUserEXT.setIscopyvalue(true);
		}

		// 以下这些值必须引用支撑平台的值
		innerSupportUserEXT.setName(outerSupportUserEXT.getName());// 姓名
		innerSupportUserEXT.setNickname(outerSupportUserEXT.getNickname());// 昵称
		innerSupportUserEXT.setBirthday(outerSupportUserEXT.getBirthday());// 生日
		innerSupportUserEXT.setPhotourl(outerSupportUserEXT.getAvatar());// 头像地址
		innerSupportUserEXT.setPhone(outerSupportUserEXT.getMobile());// 手机号码

		// 昵称为空时，默认使用手机号码作为昵称
		if (ObjectUtil.isBlank(innerSupportUserEXT.getNickname())) {
			innerSupportUserEXT.setNickname(innerSupportUserEXT.getPhone());
		}

		// 姓名为空时，默认使用昵称作为姓名
		if (ObjectUtil.isBlank(innerSupportUserEXT.getName())) {
			innerSupportUserEXT.setName(innerSupportUserEXT.getNickname());
		}

		// 如果姓名还是为 null，那么默认设置为空字符串
		if (null == innerSupportUserEXT.getName()) {
			innerSupportUserEXT.setName("");
		}

		return true;
	}

	/**
	 * 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午12:44:37
	 *
	 * @param orgOrDepartmentIdList
	 *            支撑平台的部门或者组织的ID列表
	 * @param usersupportid
	 *            支撑平台的用户的ID
	 * @return
	 */
	public JSONArray queryOrgOrDepartmentDetailBySupportidList(List<String> orgOrDepartmentIdList,
			final String usersupportid) {

		if ((null == orgOrDepartmentIdList) || (orgOrDepartmentIdList.isEmpty())) {
			return null;
		}

		// 请求示例：http://47.104.162.6:9000/api/mongo/iov/sys.tenant?filter={"_removed":null,"_id":{$in:[{"$oid":"5abb5a2e46e0fb00013b70b7"},{"$oid":"5abc7f2b46e0fb00013b70bf"}]}}
		final JSONArray parammJSONArray = new JSONArray();
		JSONObject paramJSONObject = null;
		for (int i = 0; i < orgOrDepartmentIdList.size(); i++) {
			if (ObjectUtil.isBlank(orgOrDepartmentIdList.get(i))) {
				continue;
			}

			paramJSONObject = new JSONObject();
			paramJSONObject.put("$oid", orgOrDepartmentIdList.get(i));
			parammJSONArray.add(i, paramJSONObject);
		}

		if (0 == parammJSONArray.size()) {
			return null;
		}

		JSONObject toidJSONObject = new JSONObject();
		toidJSONObject.put("$in", parammJSONArray);
		JSONObject tidJSONObject = new JSONObject();
		tidJSONObject.put("_id", toidJSONObject);

		JSONArray orJSONArray = new JSONArray();
		orJSONArray.add(tidJSONObject);// 组织或者部门的ID

		JSONObject managerJSONObject = new JSONObject();
		JSONObject managerOidJSONObject = new JSONObject();
		managerOidJSONObject.put("$oid", usersupportid);
		managerJSONObject.put("manager", managerOidJSONObject);
		orJSONArray.add(managerJSONObject);// 组织或者部门的管理员的ID

		JSONObject orJSONObject = new JSONObject();
		orJSONObject.put("$or", orJSONArray);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(orJSONObject);

		JSONArray resultJSONArray = null;
		Object result = mSupportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			resultJSONArray = SupportUtil.supportGetItemData(result);
		}

		return resultJSONArray;
	}

	/**
	 * 根据支撑平台的组织或者部门的ID查询出所有直属的子级组织或者部门
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午12:44:37
	 *
	 * @param parentOrgOrDepartmentId
	 *            支撑平台的部门或者组织的父级ID
	 * @return
	 */
	public JSONArray queryAllSubOrgOrDepartmentDetailBySupportidList(final String parentOrgOrDepartmentId) {

		if (ObjectUtil.isBlank(parentOrgOrDepartmentId)) {
			return null;
		}

		// 请求示例：http://47.104.162.6:9000/api/mongo/iov/sys.tenant?filter={"_removed":null,"parent":{"$oid":"5abc7f2b46e0fb00013b70bf"}}
		JSONObject toidJSONObject = new JSONObject();
		toidJSONObject.put("$oid", parentOrgOrDepartmentId);
		JSONObject tidJSONObject = new JSONObject();
		tidJSONObject.put("parent", toidJSONObject);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(tidJSONObject);

		JSONArray resultJSONArray = null;
		Object result = mSupportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			resultJSONArray = SupportUtil.supportGetItemData(result);
		}

		return resultJSONArray;
	}

	/**
	 * 设置当前登录用户所拥有的组织和部门
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:13:44
	 *
	 * @param loginUserInfo
	 *            用户对象
	 */
	public void setLoginUserOwnerOrgAndDepartment(final LoginUserInfo loginUserInfo) {

		if ((null == loginUserInfo) || (ObjectUtil.isBlank(loginUserInfo.getSupportid()))) {
			return;
		}

		try {
			List<SupportOrgEXT> supportOrgEXTList = this.queryOwnerOrgList(loginUserInfo.getSupportid());

			if ((null == supportOrgEXTList) || (supportOrgEXTList.isEmpty())) {
				return;
			} else {
				// 当前登录的用户所拥有的组织
				loginUserInfo.setOwnerorglist(new ArrayList<SupportOrgEXT>());
				// 当前登录的用户所拥有的部门
				loginUserInfo.setOwnerdepartmentlist(new ArrayList<SupportOrgEXT>());
			}

			for (SupportOrgEXT supportOrgEXT : supportOrgEXTList) {

				if (TYP_ORG.equals(supportOrgEXT.getType())) {// 组织

					if (loginUserInfo.getSupportid().equals(supportOrgEXT.getManageruserid())) {
						// 当前登录的用户就是管理员
						supportOrgEXT.setCurrentlonginuserisadmin(true);
					}

					loginUserInfo.getOwnerorglist().add(supportOrgEXT);// 添加到组织列表

				} else if (TYP_DEPARTMENT.equals(supportOrgEXT.getType())) {// 部门

					if (loginUserInfo.getSupportid().equals(supportOrgEXT.getManageruserid())) {
						// 当前登录的用户就是管理员
						supportOrgEXT.setCurrentlonginuserisadmin(true);
					}

					loginUserInfo.getOwnerdepartmentlist().add(supportOrgEXT);// 添加到部门列表

				}
			}

		} catch (Exception e) {
			// 忽略异常
		}

	}

	/**
	 * 设置当前登录用户所拥有的组织和部门（当前登录的组织和组织下的部门）
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:13:44
	 *
	 * @param loginUserInfo
	 *            用户对象
	 */
	public void setLoginUserCurrentLoginOrgAndDepartment(final LoginUserInfo loginUserInfo) {

		if ((null == loginUserInfo) || (ObjectUtil.isBlank(loginUserInfo.getSupportid()))
				|| (ObjectUtil.isBlank(loginUserInfo.getLoginorgid()))) {
			return;
		}

		try {
			List<SupportOrgEXT> supportOrgEXTList = this.queryOwnerOrgList(loginUserInfo);

			if ((null == supportOrgEXTList) || (supportOrgEXTList.isEmpty())) {
				return;
			} else {
				// 当前登录的用户所拥有的组织
				loginUserInfo.setOwnerorglist(new ArrayList<SupportOrgEXT>());
				// 当前登录的用户所拥有的部门
				loginUserInfo.setOwnerdepartmentlist(new ArrayList<SupportOrgEXT>());
			}

			for (SupportOrgEXT supportOrgEXT : supportOrgEXTList) {

				if (TYP_ORG.equals(supportOrgEXT.getType())) {// 组织

					if (loginUserInfo.getSupportid().equals(supportOrgEXT.getManageruserid())) {
						// 当前登录的用户就是管理员
						supportOrgEXT.setCurrentlonginuserisadmin(true);
					}

					loginUserInfo.getOwnerorglist().add(supportOrgEXT);// 添加到组织列表

				} else if (TYP_DEPARTMENT.equals(supportOrgEXT.getType())) {// 部门

					if (loginUserInfo.getSupportid().equals(supportOrgEXT.getManageruserid())) {
						// 当前登录的用户就是管理员
						supportOrgEXT.setCurrentlonginuserisadmin(true);
					}

					loginUserInfo.getOwnerdepartmentlist().add(supportOrgEXT);// 添加到部门列表

				}
			}

		} catch (Exception e) {
			// 忽略异常
		}

	}

	/**
	 * 根据用户ID查询出该用户所拥有的所有组织和部门
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:00:21
	 *
	 * @param userSupportid
	 *            支撑平台上的用户ID
	 * @return
	 * @throws Exception
	 */
	public List<SupportOrgEXT> queryOwnerOrgList(final String userSupportid) throws Exception {

		if ((ObjectUtil.isBlank(userSupportid))) {
			return null;
		}

		List<String> userSupportidList = new ArrayList<String>();
		userSupportidList.add(userSupportid);

		// 获取支撑平台上的用户信息
		List<SupportUserEXT> supportUserEXTList = this.querySupportUserEXTByIdOrSupportidList(userSupportidList);

		if ((null == supportUserEXTList) || (supportUserEXTList.isEmpty())) {
			return null;
		}

		SupportUserEXT supportUserEXT = supportUserEXTList.get(0);
		if ((null == supportUserEXT) || (null == supportUserEXT.getTenantlist())
				|| (supportUserEXT.getTenantlist().isEmpty())) {
			return null;
		}

		List<SupportOrgEXT> orgOrDepartmentList = this.queryOrgOrDepartmentList(supportUserEXT.getTenantlist(),
				userSupportid);

		return orgOrDepartmentList;
	}

	/**
	 * 根据用户ID查询出该用户所拥有的所有组织和部门
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:00:21
	 *
	 * @param userSupportid
	 *            支撑平台上的用户ID
	 * @return
	 * @throws Exception
	 */
	public List<SupportOrgEXT> queryOwnerOrgList(final LoginUserInfo loginUserInfo) throws Exception {

		if ((null == loginUserInfo) || (ObjectUtil.isBlank(loginUserInfo.getLoginorgid()))) {
			return null;
		}

		List<String> userSupportidList = new ArrayList<String>();
		userSupportidList.add(loginUserInfo.getSupportid());

		// 获取支撑平台上的用户信息
		List<SupportUserEXT> supportUserEXTList = this.querySupportUserEXTByIdOrSupportidList(userSupportidList);

		if ((null == supportUserEXTList) || (supportUserEXTList.isEmpty())) {
			return null;
		}

		SupportUserEXT supportUserEXT = supportUserEXTList.get(0);
		if ((null == supportUserEXT) || (null == supportUserEXT.getTenantlist())
				|| (supportUserEXT.getTenantlist().isEmpty())) {
			return null;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentid", loginUserInfo.getLoginorgid());
		List<Map<String, Object>> childList = mSupportOrgService.queryDepartment(paramMap);// 获取部

		final List<String> tenantlist = new ArrayList<String>();
		tenantlist.add(loginUserInfo.getLoginorgid());// 设置组织的ID

		String departmentId = null;// 部门的ID
		for (String tenantId : supportUserEXT.getTenantlist()) {
			for (Map<String, Object> map : childList) {
				departmentId = ObjectUtil.toString(map.get("supportid"));

				if (ObjectUtil.toString(tenantId).equals(departmentId)) {
					tenantlist.add(departmentId);
					break;
				}
			}
		}

		List<SupportOrgEXT> orgOrDepartmentList = this.queryOrgOrDepartmentList(tenantlist,
				loginUserInfo.getSupportid());

		return orgOrDepartmentList;
	}

	/**
	 * 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:43:22
	 *
	 * @param orgOrDepartmentIdList
	 *            组织或者部门的ID列表
	 * @return
	 */
	public Map<String, SupportOrgEXT> getOrgOrDepartmentList(List<String> orgOrDepartmentIdList) {

		List<SupportOrgEXT> supportOrgEXTList = this.queryOrgOrDepartmentList(orgOrDepartmentIdList);

		if ((null == supportOrgEXTList) || (supportOrgEXTList.isEmpty())) {
			return null;
		}

		Map<String, SupportOrgEXT> map = new HashMap<String, SupportOrgEXT>();
		for (SupportOrgEXT supportOrgEXT : supportOrgEXTList) {
			map.put(supportOrgEXT.getSupportid(), supportOrgEXT);
		}

		return map;
	}

	/**
	 * 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:43:22
	 *
	 * @param orgOrDepartmentIdList
	 *            组织或者部门的ID列表
	 * @return
	 */
	public List<SupportOrgEXT> queryOrgOrDepartmentList(List<String> orgOrDepartmentIdList) {
		return this.queryOrgOrDepartmentList(orgOrDepartmentIdList, null);
	}

	/**
	 * 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:43:22
	 *
	 * @param orgOrDepartmentIdList
	 *            组织或者部门的ID列表
	 * @param usersupportid
	 *            支撑平台的用户的ID
	 * @return
	 */
	public List<SupportOrgEXT> queryOrgOrDepartmentList(List<String> orgOrDepartmentIdList,
			final String usersupportid) {

		if ((null == orgOrDepartmentIdList) || (orgOrDepartmentIdList.isEmpty())) {
			return null;
		}

		// 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
		JSONArray jsonArray = null;

		if (null == usersupportid) {
			jsonArray = this.queryOrgOrDepartmentDetailBySupportidList(orgOrDepartmentIdList);
		} else {
			jsonArray = this.queryOrgOrDepartmentDetailBySupportidList(orgOrDepartmentIdList, usersupportid);
		}

		if ((null == jsonArray) || (jsonArray.isEmpty())) {
			return null;
		}

		List<SupportOrgEXT> list = new ArrayList<SupportOrgEXT>();

		SupportOrgEXT tempSupportOrgEXT = null;
		JSONObject supportOrgEXTJSONObject = null;
		for (int i = 0; i < jsonArray.size(); i++) {

			try {
				supportOrgEXTJSONObject = ((JSONObject) jsonArray.get(i));
				supportOrgEXTJSONObject.remove("logo");
				tempSupportOrgEXT = JSONObject.toJavaObject(supportOrgEXTJSONObject, SupportOrgEXT.class);

				if (null != tempSupportOrgEXT) {
					list.add(tempSupportOrgEXT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 异常就忽略掉
			}

		}

		return list;
	}

	/**
	 * 获取指定的组织或者部门下的所有子级
	 * 
	 * @author wangjunwen
	 * @date 2018年4月15日 下午1:54:59
	 *
	 * @param orgOrDepartmentSupportid
	 *            支撑平台上的组织或者部门的ID
	 * @return
	 * @throws Exception
	 */
	public List<SupportOrgEXT> queryAllSubOrgOrDepartment(final String orgOrDepartmentSupportid) {
		try {
			return this.queryAllSubOrgOrDepartment(orgOrDepartmentSupportid, null);
		} catch (Exception e) {
			// 忽略异常
		}

		return null;
	}

	/**
	 * 获取指定的组织或者部门下的所有子级
	 * 
	 * @author wangjunwen
	 * @date 2018年4月13日 下午4:00:21
	 *
	 * @param orgOrDepartmentSupportid
	 *            支撑平台上的组织或者部门的ID
	 * @param outSupportOrgEXTList
	 *            查询出的组织或者部门列表
	 * @return
	 * @throws Exception
	 */
	private List<SupportOrgEXT> queryAllSubOrgOrDepartment(final String orgOrDepartmentSupportid,
			List<SupportOrgEXT> outSupportOrgEXTList) throws Exception {

		if ((ObjectUtil.isBlank(orgOrDepartmentSupportid))) {
			return null;
		} else {
			if ((null == outSupportOrgEXTList)) {
				outSupportOrgEXTList = new ArrayList<SupportOrgEXT>();
			}
		}

		JSONArray jsonArray = null;

		SupportOrgEXT tempSupportOrgEXT = null;

		while (true) {
			jsonArray = this.queryAllSubOrgOrDepartmentDetailBySupportidList(orgOrDepartmentSupportid);

			if ((null == jsonArray) || (jsonArray.isEmpty())) {
				break;
			}

			for (int i = 0; i < jsonArray.size(); i++) {
				try {
					tempSupportOrgEXT = JSONObject.toJavaObject(((JSONObject) jsonArray.get(i)), SupportOrgEXT.class);
					if (null != tempSupportOrgEXT) {
						outSupportOrgEXTList.add(tempSupportOrgEXT);

						// 递归调用
						return this.queryAllSubOrgOrDepartment(tempSupportOrgEXT.getSupportid(), outSupportOrgEXTList);
					}
				} catch (Exception e) {
					// 异常就忽略掉
				}

			}
		}

		return outSupportOrgEXTList;
	}

	/**
	 * 根据支撑平台组织或者部门的ID查询出对象的组织或者部门的信息
	 * 
	 * @author wangjunwen
	 * @date 2018年4月11日 下午12:44:37
	 *
	 * @param orgOrDepartmentIdList
	 *            支撑平台的部门或者组织的ID列表
	 * @return
	 */
	public JSONArray queryOrgOrDepartmentDetailBySupportidList(List<String> orgOrDepartmentIdList) {

		if ((null == orgOrDepartmentIdList) || (orgOrDepartmentIdList.isEmpty())) {
			return null;
		}

		// 请求示例：http://47.104.162.6:9000/api/mongo/iov/sys.tenant?filter={"_removed":null,"_id":{$in:[{"$oid":"5abb5a2e46e0fb00013b70b7"},{"$oid":"5abc7f2b46e0fb00013b70bf"}]}}
		final JSONArray parammJSONArray = new JSONArray();
		JSONObject paramJSONObject = null;
		for (int i = 0; i < orgOrDepartmentIdList.size(); i++) {
			if (ObjectUtil.isBlank(orgOrDepartmentIdList.get(i))) {
				continue;
			}

			paramJSONObject = new JSONObject();
			paramJSONObject.put("$oid", orgOrDepartmentIdList.get(i));
			parammJSONArray.add(i, paramJSONObject);
		}

		if (0 == parammJSONArray.size()) {
			return null;
		}

		JSONObject toidJSONObject = new JSONObject();
		toidJSONObject.put("$in", parammJSONArray);
		JSONObject tidJSONObject = new JSONObject();
		tidJSONObject.put("_id", toidJSONObject);

		SupportRequestParam supportRequestParam = new SupportRequestParam();
		supportRequestParam.setFilterJson(tidJSONObject);

		JSONArray resultJSONArray = null;
		Object result = mSupportService.requestSupportGet(AllJsonRequestUrl.Sys_tenant, supportRequestParam);
		if (SupportUtil.supportResultItemIsNotEmpty(result)) {
			resultJSONArray = SupportUtil.supportGetItemData(result);
		}

		return resultJSONArray;
	}

}
