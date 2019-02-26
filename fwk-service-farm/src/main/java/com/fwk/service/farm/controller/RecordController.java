package com.fwk.service.farm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.ErrorUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.OrderByInfo;
import com.fwk.common.util.Page;
import com.fwk.common.util.PageUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.common.util.language.LanguageUtil;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.ParcelEXT;
import com.fwk.service.common.entity.UserDictEXT;
import com.fwk.service.common.service.CommonService;
import com.fwk.service.common.support.SupportUserService;
import com.fwk.service.farm.entity.RecordEXT;
import com.fwk.service.farm.entity.RecordfarmtypeEXT;
import com.fwk.service.farm.entity.RecordparcelEXT;
import com.fwk.service.farm.entity.RecordpartuserEXT;
import com.fwk.service.farm.service.RecordService;

/**
 * 档案管理模块 — 农事记录管理
 * 
 * @author wangjunwen
 * @date 2018年1月15日 下午5:15:18
 *
 */
@RestController
@RequestMapping("/record")
public class RecordController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

	// 档案管理模块 — 农事记录管理的业务逻辑处理对象
	@Autowired
	private RecordService mRecordService;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;
	
	@Autowired
	private SupportUserService supportUserService;

	/**
	 * 新增农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:19:02
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			//xx
//			if (ObjectUtil.isBlank(paramRecordEXT.getContent())) {// 农事内容
//				resultWrap.setParamIsnotEmpty("content");
//				return;
//			}
			// else if ((null == paramRecordEXT.getFarmtypelist()) ||
			// (paramRecordEXT.getFarmtypelist().isEmpty())) {// 农事类别
			// resultWrap.setParamIsnotEmpty("farmtypelist");
			// return;
			// }
//			else //xx
				if (ObjectUtil.isBlank(paramRecordEXT.getBegintime())) {// 开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			} else if (ObjectUtil.isBlank(paramRecordEXT.getRecordpartuserlist())) {// 参与人
				resultWrap.setParamIsnotEmpty("recordpartuserlist");
				return;
			} else if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {

				// 农事记录的农事类别列表
				if ((null != paramRecordEXT.getFarmtypelist())) {

					for (RecordfarmtypeEXT recordfarmtypeEXT : paramRecordEXT.getFarmtypelist()) {

						if (null == recordfarmtypeEXT.getFarmtype()) {// 农事类别的ID不能为空
							resultWrap.setParamIsnotEmpty("farmtypelist.farmtype");
							return;
						}
					}

					// 去重
					Iterator<RecordfarmtypeEXT> iterator = paramRecordEXT.getFarmtypelist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordfarmtypeEXT tempRecordfarmtypeEXT = null;
					while (iterator.hasNext()) {
						tempRecordfarmtypeEXT = iterator.next();

						if (finalList.contains(tempRecordfarmtypeEXT.getFarmtype())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordfarmtypeEXT.getFarmtype());
						}
					}

					finalList = null;

				}

				// 农事记录的地块列表
				if ((null != paramRecordEXT.getRecordparcellist())) {

					for (RecordparcelEXT recordparcelEXT : paramRecordEXT.getRecordparcellist()) {

						if (null == recordparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("recordparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<RecordparcelEXT> iterator = paramRecordEXT.getRecordparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordparcelEXT tempRecordparcelEXT = null;
					while (iterator.hasNext()) {
						tempRecordparcelEXT = iterator.next();

						if (finalList.contains(tempRecordparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// 农事记录的参人列表
				if ((null != paramRecordEXT.getRecordpartuserlist())) {

					for (RecordpartuserEXT recordpartuserEXT : paramRecordEXT.getRecordpartuserlist()) {

						if (null == recordpartuserEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("recordpartuserlist.userid");
							return;
						}
					}

					// 去重
					Iterator<RecordpartuserEXT> iterator = paramRecordEXT.getRecordpartuserlist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordpartuserEXT tempRecordpartuserEXT = null;
					while (iterator.hasNext()) {
						tempRecordpartuserEXT = iterator.next();

						if (finalList.contains(tempRecordpartuserEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordpartuserEXT.getUserid());
						}
					}

					finalList = null;

				}

			}

			// 农事记录来源-记录
			EDict farmSourceEDict = EDict.getEDictByType(EDictType.farm_source, paramRecordEXT.getSourcedictvalue());

			if ((null == farmSourceEDict)) {
				// 记录有来源于“农事记录”和“农事记录”的，默认来源于农事记录
				paramRecordEXT.setSourcedictvalue(EDict.farm_source_record.getDict().getDictValue());
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mRecordService.add(paramRecordEXT, loginUserInfo);

			if (addResult.intValue() > 0) {
				final List<RecordEXT> list = new ArrayList<RecordEXT>();

				// 添加成功之后把产品信息返回到客户端
				RecordEXT recordEXT = null;
				RecordEXT queryParamRecordEXT = null;
				for (Integer id : paramRecordEXT.getIdlist()) {
					queryParamRecordEXT = new RecordEXT();
					queryParamRecordEXT.setId(id);

					recordEXT = mRecordService.queryById(queryParamRecordEXT);
					list.add(recordEXT);
				}

				// 设置响应客户端的数据
				resultWrap.setData(list);

				resultWrap.setSaveSuccess();// 添加成功
			} else {
				resultWrap.setSaveFailure();// 添加失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "add", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 编辑农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:19:46
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> paramMap = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, paramMap);

			// 非空验证
			if (ObjectUtil.isBlank(paramRecordEXT.getBegintime())) {// 开始时间
				resultWrap.setParamIsnotEmpty("begintime");
				return;
			}

			// else if ((null == paramRecordEXT.getFarmtypelist()) ||
			// (paramRecordEXT.getFarmtypelist().isEmpty())) {// 农事类别
			// resultWrap.setParamIsnotEmpty("farmtypelist");
			// return;
			// }

			else if (ObjectUtil.isBlank(paramRecordEXT.getRecordpartuserlist())) {// 参与人
				resultWrap.setParamIsnotEmpty("recordpartuserlist");
				return;
			}
			else if ( null == paramRecordEXT.getId() && CollectionUtils.isEmpty(paramRecordEXT.getIdlist())) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}
//			else if ((null == paramRecordEXT.getId()) && (null == paramRecordEXT.getIdlist())
//					|| (ObjectUtil.isBlank(paramRecordEXT.getIdlist()) || paramRecordEXT.getIdlist().size() <= 0) && (ObjectUtil.isBlank(paramRecordEXT.getRecordids()))) {// 业务主键ID不能为空
//				resultWrap.setParamIsnotEmpty("id");
//				return;
//			} 
			else {

				// 农事记录的农事类别列表
				if ((null != paramRecordEXT.getFarmtypelist())) {

					for (RecordfarmtypeEXT recordfarmtypeEXT : paramRecordEXT.getFarmtypelist()) {

						if (null == recordfarmtypeEXT.getFarmtype()) {// 农事类别的ID不能为空
							resultWrap.setParamIsnotEmpty("farmtypelist.farmtype");
							return;
						}
					}

					// 去重
					Iterator<RecordfarmtypeEXT> iterator = paramRecordEXT.getFarmtypelist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordfarmtypeEXT tempRecordfarmtypeEXT = null;
					while (iterator.hasNext()) {
						tempRecordfarmtypeEXT = iterator.next();

						if (finalList.contains(tempRecordfarmtypeEXT.getFarmtype())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordfarmtypeEXT.getFarmtype());
						}
					}

					finalList = null;

				}

				// 农事记录的地块列表
				if ((null != paramRecordEXT.getRecordparcellist())) {

					for (RecordparcelEXT recordparcelEXT : paramRecordEXT.getRecordparcellist()) {

						if (null == recordparcelEXT.getParcelid()) {// 地块的ID不能为空
							resultWrap.setParamIsnotEmpty("recordparcellist.parcelid");
							return;
						}
					}

					// 去重
					Iterator<RecordparcelEXT> iterator = paramRecordEXT.getRecordparcellist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordparcelEXT tempRecordparcelEXT = null;
					while (iterator.hasNext()) {
						tempRecordparcelEXT = iterator.next();

						if (finalList.contains(tempRecordparcelEXT.getParcelid())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordparcelEXT.getParcelid());
						}
					}

					finalList = null;

				}

				// 农事记录的参人列表
				if ((null != paramRecordEXT.getRecordpartuserlist())) {

					for (RecordpartuserEXT recordpartuserEXT : paramRecordEXT.getRecordpartuserlist()) {

						if (null == recordpartuserEXT.getUserid()) {// 参与人的用户ID不能为空
							resultWrap.setParamIsnotEmpty("recordpartuserlist.userid");
							return;
						}
					}

					// 去重
					Iterator<RecordpartuserEXT> iterator = paramRecordEXT.getRecordpartuserlist().iterator();
					List<Integer> finalList = new ArrayList<Integer>();

					RecordpartuserEXT tempRecordpartuserEXT = null;
					while (iterator.hasNext()) {
						tempRecordpartuserEXT = iterator.next();

						if (finalList.contains(tempRecordpartuserEXT.getUserid())) {
							iterator.remove();
						} else {
							finalList.add(tempRecordpartuserEXT.getUserid());
						}
					}

					finalList = null;
				}

				// 投入品有新增的也有更新原来的

			}

			// 从数据库中检索出原来的农事记录
			final RecordEXT originalRecordEXT = mRecordService.queryById(paramRecordEXT);

			if ((null == originalRecordEXT)) {
				// 农事记录不存在
				resultWrap.error(LanguageUtil.getLanguageByKey("fwk-farm-record-object-is-not-exists"));
				return;
			}
			//xx，将地块列表传入查询出来的记录对象
			if(ObjectUtil.isNotBlank(paramRecordEXT.getRecordparcellist()) && paramRecordEXT.getRecordparcellist().size() >= 1) {
				originalRecordEXT.setRecordparcellist(paramRecordEXT.getRecordparcellist());
			}

			// 将数据保存到数据库并获取添加结果
			Integer addResult = mRecordService.edit(paramRecordEXT, originalRecordEXT, paramMap);

			if (addResult.intValue() > 0) {
				// 添加成功之后把产品信息返回到客户端
				RecordEXT recordEXT = mRecordService.queryById(paramRecordEXT);

				// 设置响应客户端的数据
				resultWrap.setData(recordEXT);

				resultWrap.setUpdateSuccess();// 更新成功
			} else {
				resultWrap.setUpdateFailure();// 更新失败
			}
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "update", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 删除农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月16日 上午9:22:16
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/deletebyid")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);

			// 非空验证
			if ((null == paramRecordEXT.getId())
					&& ((null == paramRecordEXT.getIdlist()) || (paramRecordEXT.getIdlist().isEmpty()))) {// 农资发放的ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 删除农资发放记录
			Integer deleteResult = null;

			if (((null == paramRecordEXT.getIdlist()) || (paramRecordEXT.getIdlist().isEmpty()))) {
				// 删除单条记录
				deleteResult = mRecordService.deletebyid(paramRecordEXT);
			} else {
				// 批量删除
				deleteResult = mRecordService.batchdeletebyids(paramRecordEXT.getIdlist());
			}

			if ((null != deleteResult) && (deleteResult.intValue() > 0)) {
				resultWrap.setDeleteSuccess();// 删除成功
			} else {
				resultWrap.setDeleteFailure();// 删除失败
			}

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryById", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查看农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:21:12
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querybyid")
	public void queryById(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if (null == paramRecordEXT.getId()) {// 业务主键ID不能为空
				resultWrap.setParamIsnotEmpty("id");
				return;
			}

			// 查询出指定ID的农资发放管理
			RecordEXT recordEXT = mRecordService.queryById(paramRecordEXT);

			// 该农事记录的参与人已读记录
			mRecordService.setRecordpartuserToReaded(recordEXT, loginUserInfo);

			// 设置响应客户端的数据
			resultWrap.setData(recordEXT);
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryById", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 检索农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querylist")
	public void queryList(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);
			
			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);
			
			//xx:通过关键字查询，通过类别名称获取类别list
			if(ObjectUtil.isNotBlank(map.get("farmtypenames"))) {
				
				UserDictEXT paramUserDictEXT = new UserDictEXT();
				paramUserDictEXT.setDelstate(1);

				if(ObjectUtil.isNotBlank(map.get("farmtypenames"))) {
					paramUserDictEXT.setName(map.get("farmtypenames")+"");
				}
				
				List<UserDictEXT> userDictEXTList = mCommonService.queryUserDictEXTList(paramUserDictEXT);
				
				List<RecordfarmtypeEXT> farmtypelist = new ArrayList<RecordfarmtypeEXT>();
				for (int i = 0; i < userDictEXTList.size(); i++) {
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename(userDictEXTList.get(i).getName());
					ext1.setFarmtype(userDictEXTList.get(i).getId());
					farmtypelist.add(ext1);
				}
				if(farmtypelist.size() <= 0) {
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename("东驰但是如果BUT");
					ext1.setFarmtype(0);
					farmtypelist.add(ext1);
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}else {
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}
				
			}
			
			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else if ((null != paramRecordEXT.getFarmtypelist()) && (paramRecordEXT.getFarmtypelist().size() > 0)) {
				UserDictEXT paramUserDictEXT = new UserDictEXT();
				paramUserDictEXT.setIdlist(new ArrayList<Integer>());

				for (RecordfarmtypeEXT recordfarmtypeEXT : paramRecordEXT.getFarmtypelist()) {
					paramUserDictEXT.getIdlist().add(recordfarmtypeEXT.getFarmtype());
				}

				List<UserDictEXT> userDictEXTList = mCommonService.queryUserDictEXTList(paramUserDictEXT);

				List<RecordfarmtypeEXT> recordfarmtypeEXTList = new ArrayList<RecordfarmtypeEXT>();

				if ((null != userDictEXTList) && (userDictEXTList.size() > 0)) {

					RecordfarmtypeEXT tempRecordfarmtypeEXT = null;

					for (UserDictEXT userDictEXT : userDictEXTList) {
						tempRecordfarmtypeEXT = new RecordfarmtypeEXT();
						tempRecordfarmtypeEXT.setFarmtype(userDictEXT.getId());
						tempRecordfarmtypeEXT.setFarmtypename(userDictEXT.getName());

						recordfarmtypeEXTList.add(tempRecordfarmtypeEXT);
					}
				}
				if(ObjectUtil.isNotBlank(recordfarmtypeEXTList) && recordfarmtypeEXTList.size() <= 0) {
					List<RecordfarmtypeEXT> farmtypelist = new ArrayList<RecordfarmtypeEXT>();
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename("东驰但是如果BUT");
					ext1.setFarmtype(0);
					farmtypelist.add(ext1);
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}else {
				// 按农事类别的列表查询
					paramRecordEXT.setFarmtypelist(recordfarmtypeEXTList);
				}
			}

			// 检查并设置排序的字段
			if ((null != paramRecordEXT.getOrderbyinfolist()) && (paramRecordEXT.getOrderbyinfolist().size() > 0)) {
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(paramRecordEXT);

				Iterator<OrderByInfo> orderByInfoIterator = paramRecordEXT.getOrderbyinfolist().iterator();

				OrderByInfo orderByInfo = null;
				while (orderByInfoIterator.hasNext()) {
					orderByInfo = orderByInfoIterator.next();
					if (!(jsonObject.containsKey(orderByInfo.getParemetername()))) {
						// 参数名称必须与实体类的字段相符，否则提示排序方式列表字段的值无效
						resultWrap.setParamInvalid("orderbyinfolist");
						return;
					}
				}

				final StringBuffer orderBySQLBuffer = new StringBuffer();

				for (int i = 0; i < paramRecordEXT.getOrderbyinfolist().size(); i++) {
					orderByInfo = paramRecordEXT.getOrderbyinfolist().get(i);

					if (i > 0) {
						// 多个排序字段之间以逗点分隔
						orderBySQLBuffer.append(" , ");
					}

					orderBySQLBuffer.append(orderByInfo.getParemetername()).append(" ")
							.append(orderByInfo.getEorderbysql().name());
				}

				// 设置排序的字段
				paramRecordEXT.setOrderBySQL(orderBySQLBuffer.toString());

			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramRecordEXT.setPage(page);
			}

			//xx:获取可查询用户的id集合
			loginUserInfo.setUserIds(supportUserService.getDepartUsersByLoginerId(map.get("loginerid")+"",map.get("loginorgid")+""));
			
			// 设置数据的查询条件
			mRecordService.setQueryPermisions(paramRecordEXT, loginUserInfo);

			// 将数据保存到数据库并获取添加结果
			List<RecordEXT> recordEXTList = mRecordService.queryList(paramRecordEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mRecordService.queryListTotalCount(paramRecordEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 将数据按客户端要求的格式做特殊处理
			if (!CollectionUtils.isEmpty(recordEXTList)) {

				for (RecordEXT recordEXT : recordEXTList) {
					// 按客户端显示格式的要求重新设置参与人
					recordEXT.setRecordpartusernames(
							ObjectUtil.toString(recordEXT.getRecordpartusernames()).replaceAll(",", "、"));
					// 设置记录是否未读
					if (CollectionUtils.isEmpty(recordEXT.getRecordpartuserlist())) {
						continue;
					}
					Integer hasReaded = recordEXT.getRecordpartuserlist().stream()
							.filter(f -> loginUserInfo.getId().equals(f.getUserid())).findFirst()
							.map(m -> Integer.valueOf(m.getHasreaded())).orElse(1);
					recordEXT.setHasreaded(hasReaded);
				}
			}
			
			// 设置响应客户端的数据
			resultWrap.setData(recordEXTList); 
			
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 获取地块
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/queryparcellist")
	public void queryParcelList(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			ParcelEXT paramParcelEXT = HTMLUtil.getMapToEntity(ParcelEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 该用户所登录的组织（该用户所属的组织）
				paramParcelEXT.setOrgid(loginUserInfo.getLoginorgid());
				// 该用户所登录的组织的部门（该用户组织的所属的部门）
				paramParcelEXT.setDepartmentidlist(loginUserInfo.getDepartmentsupportidlist());
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramParcelEXT.setPage(page);
			}

			// 获取地块
			List<ParcelEXT> parcelEXTList = mCommonService.queryParcelList(paramParcelEXT);

			// 设置响应客户端的数据
			resultWrap.setData(parcelEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryParcelList", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 检索显示到首页上的农事记录
	 * 
	 * @author wangjunwen
	 * @date 2018年1月15日 下午5:20:19
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querylisttohomepage")
	public void queryListToHomePage(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			} else {
				// 未删除
				paramRecordEXT.setDelstate(CommonConstantsUtil.IS_DELETE_FALSE);
			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramRecordEXT.setPage(page);
			}

			// 设置数据的查询条件
			mRecordService.setQueryPermisions(paramRecordEXT, loginUserInfo);

			// 将数据保存到数据库并获取添加结果
			List<RecordEXT> recordEXTList = mRecordService.queryList(paramRecordEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				Integer totalCount = mRecordService.queryListTotalCount(paramRecordEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 将数据按客户端要求的格式做特殊处理
			if ((null != recordEXTList) && (recordEXTList.size() > 0)) {

				final RecordEXT[] recordEXTArray = recordEXTList.toArray(new RecordEXT[recordEXTList.size()]);

				RecordEXT recordEXT = null;
				RecordEXT swapRecordEXT = null;

				for (int i = 0; i < recordEXTArray.length; i++) {

					recordEXT = recordEXTArray[i];

					for (int j = (i + 1); j < recordEXTArray.length; j++) {

						swapRecordEXT = recordEXTArray[j];

						// 取最新添加的几条记录，然后再按开始时间倒序排序
						if (swapRecordEXT.getBegintime().after(recordEXT.getBegintime())) {
							recordEXTArray[i] = swapRecordEXT;
							recordEXTArray[j] = recordEXT;
						}
					}
				}

				recordEXTList = Arrays.asList(recordEXTArray);
			}

			// 设置响应客户端的数据
			resultWrap.setData(recordEXTList);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryListToHomePage", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}

	/**
	 * 查询出在首页上显示的未读的农事记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午3:15:29
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/querycountnotreadrecordtohomepage")
	public void queryCountNotReadRecordToHomePage(HttpServletRequest request, HttpServletResponse response) {
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);

			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return;
			}

			Map<String, Object> countNotReadMap = mRecordService.queryCountNotReadRecordToHomePage(loginUserInfo);
			// xx:未读数量为0时，查询全部数据
			if (countNotReadMap != null && Integer.parseInt(countNotReadMap.get("totalcount").toString()) == 0) {
				countNotReadMap.put("totalcount", this.queryListCount(request, response));
			}

			// 设置响应客户端的数据
			resultWrap.setData(countNotReadMap);

		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());

			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryCountNotReadRecordToHomePage", e);
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
	
	/**
	 * 通过时间段查询当天是否存在  任务/计划
	 * @param begindate:开始日期
	 * @param enddate:结束日期
	 * @param operateid:操作人id
	 * @param loginorgid:当前组织supportid
	 * 
	 * @author xx
	 * */
	@RequestMapping("/queryExistPlanTaskEachDate")
	public void queryExistPlanTaskEachDate(HttpServletRequest request, HttpServletResponse response) {
		ResultWrap resultWrap = HTMLUtil.getDefaultWrap();
		// 获取查询参数列表
		final Map<String, Object> map = HTMLUtil.getPageParams(request);
		try {
			if (ObjectUtil.isBlank(map.get("loginorgid"))) {
				resultWrap.setParamIsnotEmpty("{loginorgid}");
				return;
			}
			if (ObjectUtil.isBlank(map.get("operateid"))) {
				resultWrap.setParamIsnotEmpty("{operateid}");
				return;
			}
			if (ObjectUtil.isBlank(map.get("begindate"))) {
				resultWrap.setParamIsnotEmpty("{begindate}");
				return;
			}
			if (ObjectUtil.isBlank(map.get("enddate"))) {
				resultWrap.setParamIsnotEmpty("{enddate}");
				return;
			}
			List<String> orgids = new ArrayList<String>();
			// supportUserService.getAllUnderDepartIdByUpId(map.get("loginorgid").toString(), orgids);
			orgids.add(map.get("loginorgid").toString());
			map.put("orgids", orgids);
			// List<String> tpdates = mRecordService.queryExistPlanTaskEachDate(map);
			List<Map<String, Object>> beginAndEndList = mRecordService.queryExistPlanTaskEachDate(map);
			if (beginAndEndList == null || beginAndEndList.isEmpty()) {
				resultWrap.setData(new ArrayList<>());
				return;
			}
			List<String> tpdates = new ArrayList<String>();
			for (Map<String, Object> beginAndEndMap : beginAndEndList) {
				String beginStr = (String) beginAndEndMap.get("begindate");
				String endStr = (String) beginAndEndMap.get("enddate");
				List<String> tmpList = DateTimeUtil.getBetweenDateList(beginStr, endStr);
				tmpList.removeAll(tpdates);
				tpdates.addAll(tmpList);
			}
			ObjectUtil.cutRepetListString(tpdates);
			resultWrap.setData(tpdates);
		} catch (Exception e) {
			e.printStackTrace();
			resultWrap.setData(null);
			resultWrap.setException(e.getMessage());
			return;
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);
		}
	}
	
	/**
	 * 获取已逾期任务的数量
	 * 
	 * */
	@RequestMapping("/queryOutPlanCntByToday")
	public void queryOutPlanCntByToday(HttpServletRequest request, HttpServletResponse response) {
		ResultWrap resultWrap = new ResultWrap();
		// 获取查询参数列表
		final Map<String, Object> map = HTMLUtil.getPageParams(request);
		if(ObjectUtil.isNotBlank(map.get("loginorgid")) && ObjectUtil.isNotBlank(map.get("operateid"))) {
			List<String> orgids = new ArrayList<String>();
			supportUserService.getAllUnderDepartIdByUpId(map.get("loginorgid").toString(), orgids);
			map.put("orgids", orgids);
			Integer outCnt = mRecordService.queryOutPlanCntByToday(map);
			resultWrap.setData(outCnt);
		}else {
			resultWrap.setParamIsnotEmpty("{loginorgid,operateid}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	/**
	 * 查询当前用户是否有权限进行编辑和删除
	 * @param operateid : 操作人id(如：123)
	 * 		userid：登录用户的支撑id
	 * 		id : 农事记录id
	 * */
	@RequestMapping("/checkIsOwenOperateAuthor")
	public void checkIsOwenOperateAuthor(HttpServletRequest request, HttpServletResponse response) {
		ResultWrap resultWrap = new ResultWrap();
		// 获取查询参数列表
		Map<String, Object> map = HTMLUtil.getPageParams(request);
		if(ObjectUtil.isNotBlank(map.get("loginorgid")) && ObjectUtil.isNotBlank(map.get("operateid")) && ObjectUtil.isNotBlank(map.get("id"))) {
			List<Integer> userids = supportUserService.getDepartUsersByLoginerId(map.get("loginerid").toString(), map.get("loginorgid").toString());
			if(ObjectUtil.isNotBlank(userids) && userids.size() >= 1) {
				map.put("userids", userids);
				if(mRecordService.checkIsOwenOperateAuthor(map) >= 1) {//有权限操作
					resultWrap.setData(true);
				}else {//无权限操作
					resultWrap.setData(false);
				}
			}else {
				resultWrap.setData(false);
			}
		}else {
			resultWrap.setParamIsnotEmpty("{loginorgid,operateid,id}");
		}
		HTMLUtil.sendWrap(response, resultWrap, request);
	}
	
	public Integer queryListCount(HttpServletRequest request, HttpServletResponse response) {
		Integer totalCount = 0;
		// 设置接口调用的统一返回对象，并将结果反馈给客户端
		ResultWrap resultWrap = new ResultWrap();

		try {
			// 获取查询参数列表
			final Map<String, Object> map = HTMLUtil.getPageParams(request);
			
			// 过滤客户端输入的值
			HTMLUtil.filterClientInputValue(map);

			// 将客户端传过来的值绑定到实体对象上
			RecordEXT paramRecordEXT = HTMLUtil.getMapToEntity(RecordEXT.class, map);
			
			//xx:通过关键字查询，通过类别名称获取类别list
			if(ObjectUtil.isNotBlank(map.get("farmtypenames"))) {
				
				UserDictEXT paramUserDictEXT = new UserDictEXT();
				paramUserDictEXT.setDelstate(1);

				if(ObjectUtil.isNotBlank(map.get("farmtypenames"))) {
					paramUserDictEXT.setName(map.get("farmtypenames")+"");
				}
				
				List<UserDictEXT> userDictEXTList = mCommonService.queryUserDictEXTList(paramUserDictEXT);
				
				List<RecordfarmtypeEXT> farmtypelist = new ArrayList<RecordfarmtypeEXT>();
				for (int i = 0; i < userDictEXTList.size(); i++) {
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename(userDictEXTList.get(i).getName());
					ext1.setFarmtype(userDictEXTList.get(i).getId());
					farmtypelist.add(ext1);
				}
				if(farmtypelist.size() <= 0) {
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename("东驰但是如果BUT");
					ext1.setFarmtype(0);
					farmtypelist.add(ext1);
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}else {
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}
				
			}
			
			// 获取当前操作的用户信息
			final LoginUserInfo loginUserInfo = mCommonService.queryLoginUserInfo(map);
			System.out.println(map);

			// 非空验证
			if ((null == loginUserInfo)) {
				// 操作人不存在（提示参数无效）
				resultWrap.setParamInvalid(HTMLUtil.common_request_param_operateid,
						HTMLUtil.common_request_param_loginorgid);
				return 0;
			} else if ((null != paramRecordEXT.getFarmtypelist()) && (paramRecordEXT.getFarmtypelist().size() > 0)) {
				UserDictEXT paramUserDictEXT = new UserDictEXT();
				paramUserDictEXT.setIdlist(new ArrayList<Integer>());

				for (RecordfarmtypeEXT recordfarmtypeEXT : paramRecordEXT.getFarmtypelist()) {
					paramUserDictEXT.getIdlist().add(recordfarmtypeEXT.getFarmtype());
				}

				List<UserDictEXT> userDictEXTList = mCommonService.queryUserDictEXTList(paramUserDictEXT);

				List<RecordfarmtypeEXT> recordfarmtypeEXTList = new ArrayList<RecordfarmtypeEXT>();

				if ((null != userDictEXTList) && (userDictEXTList.size() > 0)) {

					RecordfarmtypeEXT tempRecordfarmtypeEXT = null;

					for (UserDictEXT userDictEXT : userDictEXTList) {
						tempRecordfarmtypeEXT = new RecordfarmtypeEXT();
						tempRecordfarmtypeEXT.setFarmtype(userDictEXT.getId());
						tempRecordfarmtypeEXT.setFarmtypename(userDictEXT.getName());

						recordfarmtypeEXTList.add(tempRecordfarmtypeEXT);
					}
				}
				if(ObjectUtil.isNotBlank(recordfarmtypeEXTList) && recordfarmtypeEXTList.size() <= 0) {
					List<RecordfarmtypeEXT> farmtypelist = new ArrayList<RecordfarmtypeEXT>();
					RecordfarmtypeEXT ext1 = new RecordfarmtypeEXT();
					ext1.setFarmtypename("东驰但是如果BUT");
					ext1.setFarmtype(0);
					farmtypelist.add(ext1);
					paramRecordEXT.setFarmtypelist(farmtypelist);
				}else {
				// 按农事类别的列表查询
					paramRecordEXT.setFarmtypelist(recordfarmtypeEXTList);
				}
			}

			// 检查并设置排序的字段
			if ((null != paramRecordEXT.getOrderbyinfolist()) && (paramRecordEXT.getOrderbyinfolist().size() > 0)) {
				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(paramRecordEXT);

				Iterator<OrderByInfo> orderByInfoIterator = paramRecordEXT.getOrderbyinfolist().iterator();

				OrderByInfo orderByInfo = null;
				while (orderByInfoIterator.hasNext()) {
					orderByInfo = orderByInfoIterator.next();
					if (!(jsonObject.containsKey(orderByInfo.getParemetername()))) {
						// 参数名称必须与实体类的字段相符，否则提示排序方式列表字段的值无效
						resultWrap.setParamInvalid("orderbyinfolist");
						return 0;
					}
				}

				final StringBuffer orderBySQLBuffer = new StringBuffer();

				for (int i = 0; i < paramRecordEXT.getOrderbyinfolist().size(); i++) {
					orderByInfo = paramRecordEXT.getOrderbyinfolist().get(i);

					if (i > 0) {
						// 多个排序字段之间以逗点分隔
						orderBySQLBuffer.append(" , ");
					}

					orderBySQLBuffer.append(orderByInfo.getParemetername()).append(" ")
							.append(orderByInfo.getEorderbysql().name());
				}

				// 设置排序的字段
				paramRecordEXT.setOrderBySQL(orderBySQLBuffer.toString());

			}

			// 分页查询
			Page page = null;
			if (HTMLUtil.isPaginationQuery(request)) {
				page = PageUtil.getPage(map);
				paramRecordEXT.setPage(page);
			}

			//xx:获取可查询用户的id集合
			loginUserInfo.setUserIds(supportUserService.getDepartUsersByLoginerId(map.get("loginerid")+"",map.get("loginorgid")+""));
			
			// 设置数据的查询条件
			mRecordService.setQueryPermisions(paramRecordEXT, loginUserInfo);

			paramRecordEXT.setDelstate(1);
					
			// 将数据保存到数据库并获取添加结果
			List<RecordEXT> recordEXTList = mRecordService.queryList(paramRecordEXT);

			// 是分页查询时设置分页信息
			if ((null != page)) {

				// 查询出总记录数量
				totalCount = mRecordService.queryListTotalCount(paramRecordEXT);

				page.setTotal(totalCount);// 设置总的记录数量和总页数

				// 设置相应客户端的分页信息
				resultWrap.setPage(page);
			}

			// 将数据按客户端要求的格式做特殊处理
			if ((null != recordEXTList) && (recordEXTList.size() > 0)) {

				for (RecordEXT recordEXT : recordEXTList) {
					// 按客户端显示格式的要求重新设置参与人
					recordEXT.setRecordpartusernames(
							ObjectUtil.toString(recordEXT.getRecordpartusernames()).replaceAll(",", "、"));
				}
			}

			// 设置响应客户端的数据
			totalCount = recordEXTList == null ? 0 : recordEXTList.size();
		} catch (Exception e) {
			// 服务异常，请联系管理员
			resultWrap.setException(e.getMessage());
			// 异常日志记录
			ErrorUtil.writeLoggerError(logger, "queryList", e);
		}
		return totalCount;
	}
}
