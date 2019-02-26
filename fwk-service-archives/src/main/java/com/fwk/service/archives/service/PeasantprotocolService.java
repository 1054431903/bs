package com.fwk.service.archives.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.constants.CommonConstantsUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.service.archives.dao.PeasantprotocolEXTMapper;
import com.fwk.service.archives.entity.PeasantEXT;
import com.fwk.service.archives.entity.PeasantprotocolEXT;
import com.fwk.service.archives.entity.PeasantprotocolresourceEXT;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.service.CommonService;

/**
 * 档案管理模块 — 农户合作协议管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PeasantprotocolService {

	/** 农户编号的格式化 */
	private static final DecimalFormat peasantprotocol_ppid_format = new DecimalFormat("XY000000");

	// 档案管理模块 — 农户合作协议管理的数据库处理类
	@Autowired
	private PeasantprotocolEXTMapper mPeasantprotocolEXTMapper;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	// 农户合作协议资源管理的业务逻辑处理对象
	@Autowired
	private PeasantprotocolresourceService mPeasantprotocolresourceService;

	/**
	 * 新增农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		// 如果客户端未设置合作协议的执行状态，那么后台自动设置合作协议的状态
		if (ObjectUtil.isBlank(pPeasantprotocolEXT.getActstate())) {
			this.setPeasantprotocolActstatus(pPeasantprotocolEXT);
		}

		// 将数据添加到数据库
		resultAddOrUpdate = mPeasantprotocolEXTMapper.add(pPeasantprotocolEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Peasantprotocol failure !");
		} else {

			// 添加协议文件的资源
			if ((null != pPeasantprotocolEXT.getResourcehistorylist())
					&& !(pPeasantprotocolEXT.getResourcehistorylist().isEmpty())) {

				PeasantprotocolresourceEXT peasantprotocolresourceEXT = null;

				// 获取资源的上传历史
				ResourcehistoryEXT resourcehistoryEXT = null;

				for (int i = 0; i < pPeasantprotocolEXT.getResourcehistorylist().size(); i++) {
					resourcehistoryEXT = pPeasantprotocolEXT.getResourcehistorylist().get(i);

					resourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
							resourcehistoryEXT.getResourcename(), i);

					if ((null != resourcehistoryEXT) && (null != resourcehistoryEXT.getId())) {

						peasantprotocolresourceEXT = new PeasantprotocolresourceEXT();
						peasantprotocolresourceEXT.setProtocolid(pPeasantprotocolEXT.getId());
						peasantprotocolresourceEXT.setResourceid(resourcehistoryEXT.getId());

						// 保存协议文件的资源
						resultAddOrUpdate = mPeasantprotocolresourceService.add(peasantprotocolresourceEXT);

						if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
							throw new Exception("add Peasantprotocolresource failure !");
						}
					}
				}

			} // 添加协议文件的资源结束

			PeasantprotocolEXT paramPeasantprotocolEXT = new PeasantprotocolEXT();
			paramPeasantprotocolEXT.setId(pPeasantprotocolEXT.getId());

			pPeasantprotocolEXT = this.queryById(pPeasantprotocolEXT);

			// 设置编号
			if (this.setPeasantprotocolPpid(pPeasantprotocolEXT)) {
				resultAddOrUpdate = this.update(pPeasantprotocolEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Peasantprotocol ppid failure !");
				}
			}
		}

		return resultAddOrUpdate;
	}

	/**
	 * 设置农户合作协议状态
	 * 
	 * @author wangjunwen
	 * @date 2018年1月30日 下午5:27:31
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @throws Exception
	 */
	private void setPeasantprotocolActstatus(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {
		if (null == pPeasantprotocolEXT) {
			return;
		}

		Date currentDate = mCommonService.queryCurrentDate();

		final String dateFormatStr = "yyyyMMdd";// 统一日期格式

		final long nowtime = Long.valueOf(DateTimeUtil.format(currentDate, dateFormatStr));
		final long begintime = Long.valueOf(DateTimeUtil.format(pPeasantprotocolEXT.getBegintime(), dateFormatStr));
		final long endtime = Long.valueOf(DateTimeUtil.format(pPeasantprotocolEXT.getEndtime(), dateFormatStr));

		// 如果数据库系统的当前时间在协议执行开始时间之前，那么默认设置为：未开始
		if (nowtime < begintime) {
			pPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_no_start.getDict().getDictValue());
		} else if ((nowtime > endtime)) {
			// 如果数据库系统的当前时间在协议执行截止时间之后，那么默认设置为：已完成
			pPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_finish.getDict().getDictValue());
		} else if ((nowtime > begintime) && (nowtime < endtime)) {
			// 如果数据库系统的当前时间大于协议执行开始时间并且小于协议执行截止时间，那么默认设置为：进行中
			pPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_continue.getDict().getDictValue());
		}
	}

	/**
	 * 更新农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {

		// 如果客户端未设置合作协议的执行状态，那么后台自动设置合作协议的状态
		if ((null != pPeasantprotocolEXT)) {
			this.setPeasantprotocolActstatus(pPeasantprotocolEXT);
		}

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPeasantprotocolEXTMapper.update(pPeasantprotocolEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Peasantprotocol failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(PeasantprotocolEXT paramPeasantprotocolEXT, final Map<String, Object> paramMap)
			throws Exception {

		// 根据合作农户协议的ID查询出农事合作协议对象
		PeasantprotocolEXT originalPeasantprotocolEXT = this.queryById(paramPeasantprotocolEXT);

		if ((null == originalPeasantprotocolEXT) || !(new Integer(CommonConstantsUtil.IS_DELETE_FALSE)
				.equals(originalPeasantprotocolEXT.getDelstate()))) {

			ResultWrap resultWrap = new ResultWrap();

			// 农户合作协议不存在
			resultWrap.setParamInvalid("id");

			throw new Exception(resultWrap.getMessage());
		}

		// 编辑农户合作协议的资源文件
		editPeasantprotocolresourceEXT(paramPeasantprotocolEXT, originalPeasantprotocolEXT, paramMap);

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(paramPeasantprotocolEXT, originalPeasantprotocolEXT, paramMap);

		return this.update(originalPeasantprotocolEXT);

	}

	/**
	 * 编辑合作农户协议的资源文件
	 * 
	 * @author wangjunwen
	 * @date 2018年2月1日 下午2:41:53
	 *
	 * @param paramPeasantprotocolEXT
	 *            从客户端传过来的合作农户协议对象
	 * @param originalPeasantprotocolEXT
	 *            从数据库中检索出来的合作农户协议对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	private boolean editPeasantprotocolresourceEXT(final PeasantprotocolEXT paramPeasantprotocolEXT,
			final PeasantprotocolEXT originalPeasantprotocolEXT, final Map<String, Object> paramMap) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = null;

		if ((null == paramPeasantprotocolEXT.getResourcehistorylist())) {
			paramPeasantprotocolEXT.setResourcehistorylist(new ArrayList<ResourcehistoryEXT>());
		}

		if (null == originalPeasantprotocolEXT.getPeasantprotocolresourceEXTList()) {
			originalPeasantprotocolEXT.setPeasantprotocolresourceEXTList(new ArrayList<PeasantprotocolresourceEXT>());
		}

		// 从客户端传过来的资源文件列表
		Iterator<ResourcehistoryEXT> paramResourcehistoryEXTIterator = paramPeasantprotocolEXT.getResourcehistorylist()
				.iterator();

		// 资源文件的排序号
		final AtomicInteger resourceOrderNo = new AtomicInteger(CommonConstantsUtil.DEFAULT_RESOURCE_ORDER_NO);

		ResourcehistoryEXT savedResourcehistoryEXT = null;
		PeasantprotocolresourceEXT addPeasantprotocolresourceEXT = null;

		ResourcehistoryEXT paramResourcehistoryEXT = null;
		while (paramResourcehistoryEXTIterator.hasNext()) {
			paramResourcehistoryEXT = paramResourcehistoryEXTIterator.next();

			for (PeasantprotocolresourceEXT originalPeasantprotocolresourceEXT : originalPeasantprotocolEXT
					.getPeasantprotocolresourceEXTList()) {

				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(
						originalPeasantprotocolresourceEXT.getResourceid(), paramResourcehistoryEXT.getResourcepath(),
						paramResourcehistoryEXT.getResourcename(), resourceOrderNo.getAndIncrement());

				if (savedResourcehistoryEXT.getId().equals(originalPeasantprotocolresourceEXT.getResourceid())) {

					// 当前上传的资源与原来的资源一致，所以需要保留
					originalPeasantprotocolEXT.getPeasantprotocolresourceEXTList()
							.remove(originalPeasantprotocolresourceEXT);

				} else {

					// 当前上传的资源没有在原来的合作农户协议资源引用中，那么新增合作农户协议的资源引用
					addPeasantprotocolresourceEXT = new PeasantprotocolresourceEXT();
					addPeasantprotocolresourceEXT.setProtocolid(originalPeasantprotocolEXT.getId());// 设置合作农户协议的ID
					addPeasantprotocolresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置合作农户协议资源上传历史的ID

					addOrUpdateRsult = mPeasantprotocolresourceService.add(addPeasantprotocolresourceEXT);

					if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
						throw new Exception("update Peasantprotocolresource failure !");
					}

				}

				// 当前投入品原来就已经存在，把他移除掉，最终新增不存在的投入品，删除原来已存在并且不需要保留的投入品
				paramResourcehistoryEXTIterator.remove();

				break;
			}
		}

		// 删除不需要保留的合作农户协议资源（历史的合作农户协议资源已更新）
		if (originalPeasantprotocolEXT.getPeasantprotocolresourceEXTList().size() > 0) {

			List<Integer> idList = new ArrayList<Integer>();

			for (PeasantprotocolresourceEXT obj : originalPeasantprotocolEXT.getPeasantprotocolresourceEXTList()) {
				idList.add(obj.getId());// 需要刪除掉的合作农户协议资源
			}

			addOrUpdateRsult = mPeasantprotocolresourceService.batchdeletebyids(idList);

			if ((null == addOrUpdateRsult) || (idList.size() != addOrUpdateRsult.intValue())) {
				throw new Exception("batch delete Peasantprotocolresource failure !");
			}

		}

		// 当前的资源记录数量大于合作农户协议的历史资源数量时添加新的合作农户协议资源
		if (paramPeasantprotocolEXT.getResourcehistorylist().size() > 0) {

			for (ResourcehistoryEXT resourcehistoryEXT : paramPeasantprotocolEXT.getResourcehistorylist()) {
				// 添加资源到历史资源中
				savedResourcehistoryEXT = mCommonService.addOrUpdateResource(null, resourcehistoryEXT.getResourcepath(),
						resourcehistoryEXT.getResourcename(), resourceOrderNo.incrementAndGet(),
						resourcehistoryEXT.getTimelength());

				// 添加合作农户协议资源
				addPeasantprotocolresourceEXT = new PeasantprotocolresourceEXT();
				addPeasantprotocolresourceEXT.setProtocolid(originalPeasantprotocolEXT.getId());// 设置合作农户协议的ID
				addPeasantprotocolresourceEXT.setResourceid(savedResourcehistoryEXT.getId());// 设置合作农户协议资源上传历史的ID

				addOrUpdateRsult = mPeasantprotocolresourceService.add(addPeasantprotocolresourceEXT);

				if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
					throw new Exception("update Peasantprotocolresource failure !");
				}
			}

		}

		return true;
	}

	/**
	 * 删除指定ID的农户合作协议
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {
		return mPeasantprotocolEXTMapper.deletebyid(pPeasantprotocolEXT);
	}

	/**
	 * 根据主键批量删除农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 下午3:34:49
	 *
	 * @param idList
	 *            业务主键列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchdeletebyids(List<Integer> idList) throws Exception {
		return mPeasantprotocolEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	public PeasantprotocolEXT queryById(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {
		PeasantprotocolEXT paramPeasantprotocolEXT = new PeasantprotocolEXT();
		paramPeasantprotocolEXT.setId(pPeasantprotocolEXT.getId());

		List<PeasantprotocolEXT> list = this.queryList(paramPeasantprotocolEXT);

		if ((null != list) && (list.size() > 0)) {
			PeasantprotocolresourceEXT paramPeasantprotocolresource = new PeasantprotocolresourceEXT();

			List<Integer> protocolidlist = new ArrayList<Integer>();
			for (PeasantprotocolEXT peasantprotocolEXT : list) {
				protocolidlist.add(peasantprotocolEXT.getId());

				peasantprotocolEXT.setResourcehistorylist(new ArrayList<ResourcehistoryEXT>());

				peasantprotocolEXT.setPeasantprotocolresourceEXTList(new ArrayList<PeasantprotocolresourceEXT>());
			}

			paramPeasantprotocolresource.setProtocolidlist(protocolidlist);

			List<PeasantprotocolresourceEXT> peasantprotocolresourceEXTList = mPeasantprotocolresourceService
					.queryList(paramPeasantprotocolresource);

			if ((null != peasantprotocolresourceEXTList) && (peasantprotocolresourceEXTList.size() > 0)) {

				ResourcehistoryEXT tempResourcehistoryEXT = null;

				for (PeasantprotocolEXT peasantprotocolEXT : list) {

					for (PeasantprotocolresourceEXT peasantprotocolresourceEXT : peasantprotocolresourceEXTList) {
						if (peasantprotocolEXT.getId().equals(peasantprotocolresourceEXT.getProtocolid())) {
							tempResourcehistoryEXT = new ResourcehistoryEXT();

							tempResourcehistoryEXT.setResourcepath(peasantprotocolresourceEXT.getResourcepath());
							tempResourcehistoryEXT.setResourcename(peasantprotocolresourceEXT.getResourcename());

							peasantprotocolEXT.getResourcehistorylist().add(tempResourcehistoryEXT);

							peasantprotocolEXT.getPeasantprotocolresourceEXTList().add(peasantprotocolresourceEXT);
						}
					}
				}
			}

			return list.get(0);
		}

		return null;
	}

	/**
	 * 检索农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	public List<PeasantprotocolEXT> queryList(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {

		// 查询出所有符合条件的农户合作协议
		List<PeasantprotocolEXT> list = mPeasantprotocolEXTMapper.queryList(pPeasantprotocolEXT);

		if ((null != list) && (list.size() > 0)) {

			List<PeasantprotocolEXT> copyList = new ArrayList<PeasantprotocolEXT>();
			PeasantprotocolEXT copyPeasantprotocolEXT = null;
			for (PeasantprotocolEXT peasantprotocolEXT : list) {
				copyPeasantprotocolEXT = new PeasantprotocolEXT();
				BeanUtils.copyProperties(peasantprotocolEXT, copyPeasantprotocolEXT);
				copyList.add(copyPeasantprotocolEXT);

				// 重新设置状态
				this.setPeasantprotocolActstate(copyPeasantprotocolEXT);
			}

			Iterator<PeasantprotocolEXT> copyListIterator = copyList.iterator();
			AtomicInteger index = new AtomicInteger(0);
			PeasantprotocolEXT tempPeasantprotocolEXT = null;
			while (copyListIterator.hasNext()) {
				copyPeasantprotocolEXT = copyListIterator.next();

				tempPeasantprotocolEXT = list.get(index.getAndIncrement());

				if (ObjectUtil.toString(copyPeasantprotocolEXT.getActstate())
						.equals(ObjectUtil.toString(tempPeasantprotocolEXT.getActstate()))) {
					copyListIterator.remove();
					continue;
				} else {
					// 需要更新状态的农户合作协议
					tempPeasantprotocolEXT.setActstate(copyPeasantprotocolEXT.getActstate());
					tempPeasantprotocolEXT.setActstatename(copyPeasantprotocolEXT.getActstatename());
				}
			}

			// 异步更新数据
			this.asynchronizationUpdate(copyList);
		}

		return list;
	}

	/**
	 * 检索农户合作协议的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PeasantprotocolEXT pPeasantprotocolEXT) throws Exception {
		return mPeasantprotocolEXTMapper.queryListTotalCount(pPeasantprotocolEXT);
	}

	/**
	 * 设置农户的编号
	 * 
	 * @author wangjunwen
	 * @date 2018年1月22日 下午5:04:12
	 *
	 * @param pPeasantprotocolEXT
	 *            农户合作协议对象
	 */
	public boolean setPeasantprotocolPpid(PeasantprotocolEXT pPeasantprotocolEXT) {
		if ((null != pPeasantprotocolEXT) && (null != pPeasantprotocolEXT.getId())) {
			pPeasantprotocolEXT.setPpid(peasantprotocol_ppid_format.format(pPeasantprotocolEXT.getId()));
			return true;
		}

		return false;
	}

	/**
	 * 查询出合作农户
	 * 
	 * @author wangjunwen
	 * @date 2018年1月29日 下午4:01:40
	 *
	 * @param pPeasantEXT
	 *            合作农户对象
	 * @return
	 * @throws Exception
	 */
	public List<PeasantEXT> queryPeasantList(PeasantEXT pPeasantEXT) throws Exception {
		return mPeasantprotocolEXTMapper.queryPeasantList(pPeasantEXT);
	}

	/**
	 * 设置农户合作协议的执行状态
	 * 
	 * @author wangjunwen
	 * @date 2018年3月21日 上午9:40:21
	 *
	 * @param paramPeasantprotocolEXT
	 *            农户合作协议对象
	 * @throws Exception
	 */
	public void setPeasantprotocolActstate(final PeasantprotocolEXT paramPeasantprotocolEXT) throws Exception {

		// 协议的开始时间和截止时间都不能为空才可设置协议的执行状态
		if ((null == paramPeasantprotocolEXT) || (null == paramPeasantprotocolEXT.getBegintime())
				|| (null == paramPeasantprotocolEXT.getEndtime())) {

			return;

		}

		// 获取当前时间
		Date currentDate = mCommonService.queryCurrentDate();

		// 协议执行状态：系统根据协议开始时间和协议结束时间自行判断，协议执行状态分为‘进行中’‘已完成’‘未开始’
		if (paramPeasantprotocolEXT.getBegintime().getTime() == paramPeasantprotocolEXT.getEndtime().getTime()) {

			// 起止日期相同，则状态为‘已完成’（2018年3月30日 确认）
			paramPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_finish.getDict().getDictValue());
			paramPeasantprotocolEXT.setActstatename(EDict.peasant_protocolstate_finish.getDict().getDictName());

		} else if (currentDate.after(paramPeasantprotocolEXT.getBegintime())
				&& currentDate.before(paramPeasantprotocolEXT.getEndtime())) {

			// 当前日期大于协议开始时间且小于结束时间之间，则状态为‘进行中’
			paramPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_continue.getDict().getDictValue());
			paramPeasantprotocolEXT.setActstatename(EDict.peasant_protocolstate_continue.getDict().getDictName());

		} else if (currentDate.after(paramPeasantprotocolEXT.getEndtime())) {

			// 当前日期大于协议结束日期，则状态为‘已完成’
			paramPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_finish.getDict().getDictValue());
			paramPeasantprotocolEXT.setActstatename(EDict.peasant_protocolstate_finish.getDict().getDictName());

		} else if (currentDate.before(paramPeasantprotocolEXT.getBegintime())) {

			// 当前日期小于协议开始日期，则状态为‘未开始’
			paramPeasantprotocolEXT.setActstate(EDict.peasant_protocolstate_no_start.getDict().getDictValue());
			paramPeasantprotocolEXT.setActstatename(EDict.peasant_protocolstate_no_start.getDict().getDictName());

		}
	}

	//
	//
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ------------ 分隔符 -----------
	// 以下部分为异步执行的方法
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 异步更新农户合作协议
	 * 
	 * @author wangjunwen
	 * @date 2018年2月12日 上午9:41:36
	 *
	 * @param updateTaskList
	 *            农户合作协议列表
	 * @return
	 */
	public void asynchronizationUpdate(List<PeasantprotocolEXT> updateTaskList) {

		// 使用线程执行需要更新的农户合作协议
		PeasantprotocolThread peasantprotocolThread = (this.new PeasantprotocolThread(this, updateTaskList));

		Thread thread = new Thread(peasantprotocolThread);
		thread.start();

	}

	/**
	 * 农户合作协议的线程执行类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月11日 下午12:35:38
	 *
	 */
	private class PeasantprotocolThread implements Runnable {

		public PeasantprotocolThread(PeasantprotocolService peasantprotocolService,
				List<PeasantprotocolEXT> updatePeasantprotocolList) {
			super();
			this.peasantprotocolService = peasantprotocolService;
			this.updatePeasantprotocolList = updatePeasantprotocolList;
		}

		private PeasantprotocolService peasantprotocolService;

		// 需要更新状态的合作协议
		private List<PeasantprotocolEXT> updatePeasantprotocolList;

		@Override
		public void run() {

			if ((null != peasantprotocolService) && (null != updatePeasantprotocolList)
					|| (updatePeasantprotocolList.size() > 0)) {
				for (PeasantprotocolEXT PeasantprotocolEXT : updatePeasantprotocolList) {
					try {
						peasantprotocolService.update(PeasantprotocolEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
