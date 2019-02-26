package com.fwk.service.archives.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.constants.EDict;
import com.fwk.common.util.constants.EDictType;
import com.fwk.service.archives.dao.PeasantEXTMapper;
import com.fwk.service.archives.entity.PeasantEXT;
import com.fwk.service.common.entity.LoginUserInfo;
import com.fwk.service.common.entity.PositionEXT;
import com.fwk.service.common.entity.ResourcehistoryEXT;
import com.fwk.service.common.entity.SupportOrgEXT;
import com.fwk.service.common.service.CommonService;

/**
 * 档案管理模块 — 合作农户档案管理的业务逻辑处理类
 * 
 * @author wangjunwen
 * @date 2018年1月16日 上午9:24:32
 *
 */
@Service
public class PeasantService {

	/** 农户编号的格式化 */
	private static final DecimalFormat peasant_pid_format = new DecimalFormat("NH000000");

	// 档案管理模块 — 合作农户档案管理的数据库处理类
	@Autowired
	private PeasantEXTMapper mPeasantEXTMapper;

	// 公用的业务逻辑处理对象
	@Autowired
	private CommonService mCommonService;

	/**
	 * 新增合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	@Transactional // 使用事务控制
	public Integer add(PeasantEXT pPeasantEXT) throws Exception {

		// 数据添加的结果
		Integer resultAddOrUpdate = null;

		// 获取资源的上传历史
		ResourcehistoryEXT resourcehistoryEXT = mCommonService.addOrUpdateResource(pPeasantEXT.getPhotoid(),
				pPeasantEXT.getPhotoidurl(), null);

		if (null != resourcehistoryEXT) {
			// 设置照片地址
			pPeasantEXT.setPhotoid(resourcehistoryEXT.getId());
		}

		// 将数据添加到数据库
		resultAddOrUpdate = mPeasantEXTMapper.add(pPeasantEXT);

		if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
			throw new Exception("add Peasant failure !");
		} else {
			PeasantEXT paramPeasantEXT = new PeasantEXT();
			paramPeasantEXT.setId(pPeasantEXT.getId());

			pPeasantEXT = this.queryById(pPeasantEXT);

			// 设置农户编号
			if (this.setPeasantPid(pPeasantEXT)) {
				resultAddOrUpdate = this.update(pPeasantEXT);

				if ((null == resultAddOrUpdate) || (1 != resultAddOrUpdate.intValue())) {
					throw new Exception("update Peasant pid failure !");
				}
			}

		}

		return resultAddOrUpdate;
	}

	/**
	 * 批量新增合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:52:48
	 *
	 * @param list
	 *            合作农户档案对象列表
	 * @return
	 * @throws Exception
	 */
	public Integer batchAdd(List<PeasantEXT> list) throws Exception {
		if ((null == list) || (list.isEmpty())) {
			return null;
		}

		Integer resultAddOrUpdate = mPeasantEXTMapper.batchAdd(list);

		return resultAddOrUpdate;
	}

	/**
	 * 更新合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(PeasantEXT pPeasantEXT) throws Exception {

		// 数据添加或者更新的结果
		Integer addOrUpdateRsult = mPeasantEXTMapper.update(pPeasantEXT);

		if ((null == addOrUpdateRsult) || (1 != addOrUpdateRsult.intValue())) {
			throw new Exception("update Peasant failure !");
		}

		return addOrUpdateRsult;
	}

	/**
	 * 更新合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:53:11
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @param paramMap
	 *            客户端传过来的参数（只能更新这些值）
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer edit(PeasantEXT pPeasantEXT, final Map<String, Object> paramMap) throws Exception {

		final PeasantEXT originalPeasantEXT = this.queryById(pPeasantEXT);

		// 获取资源的上传历史
		ResourcehistoryEXT resourcehistoryEXT = mCommonService.addOrUpdateResource(originalPeasantEXT.getPhotoid(),
				pPeasantEXT.getPhotoidurl(), null);

		if ((null != resourcehistoryEXT) && (null != resourcehistoryEXT.getId())) {
			// 设置照片地址
			originalPeasantEXT.setPhotoid(resourcehistoryEXT.getId());
		}

		// 把客户端的值更新到数据库中查询到的对象上
		HTMLUtil.updateObject(pPeasantEXT, originalPeasantEXT, paramMap);

		return this.update(originalPeasantEXT);

	}

	/**
	 * 删除指定ID的合作农户档案
	 * 
	 * <pre>
	 * 	特别说明：有删除标记字段的统一使用逻辑删除
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:02:07
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	public Integer deletebyid(PeasantEXT pPeasantEXT) throws Exception {
		return mPeasantEXTMapper.deletebyid(pPeasantEXT);
	}

	/**
	 * 根据主键批量删除合作农户档案
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
		return mPeasantEXTMapper.batchdeletebyids(idList);
	}

	/**
	 * 查询出指定ID的合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午10:00:44
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	public PeasantEXT queryById(PeasantEXT pPeasantEXT) throws Exception {
		PeasantEXT paramPeasantEXT = new PeasantEXT();
		paramPeasantEXT.setId(pPeasantEXT.getId());

		List<PeasantEXT> pPeasantEXTList = this.queryList(paramPeasantEXT);

		return ((null != pPeasantEXTList) && (pPeasantEXTList.size() > 0) ? pPeasantEXTList.get(0) : null);
	}

	/**
	 * 检索合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 上午9:59:16
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	public List<PeasantEXT> queryList(PeasantEXT pPeasantEXT) throws Exception {
		List<PeasantEXT> peasantEXTList = mPeasantEXTMapper.queryList(pPeasantEXT);

		// 设置地址
		this.setAddress(peasantEXTList);

		// 设置合作农户的组织信息
		this.setPeasantOrgInfo(peasantEXTList);

		return peasantEXTList;
	}

	/**
	 * 检索合作农户档案的总记录数量
	 * 
	 * @author wangjunwen
	 * @date 2018年1月19日 下午1:09:02
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 * @return
	 * @throws Exception
	 */
	public Integer queryListTotalCount(PeasantEXT pPeasantEXT) throws Exception {
		return mPeasantEXTMapper.queryListTotalCount(pPeasantEXT);
	}

	/**
	 * 设置农户的编号
	 * 
	 * @author wangjunwen
	 * @date 2018年1月22日 下午5:04:12
	 *
	 * @param pPeasantEXT
	 *            合作农户档案对象
	 */
	public boolean setPeasantPid(PeasantEXT pPeasantEXT) {
		if ((null != pPeasantEXT) && (null != pPeasantEXT.getId())) {
			pPeasantEXT.setPid(peasant_pid_format.format(pPeasantEXT.getId()));
			return true;
		}

		return false;
	}

	/**
	 * 设置农户的地址
	 * 
	 * @author wangjunwen
	 * @date 2018年3月7日 下午3:48:24
	 *
	 * @param peasantEXTList
	 *            农户档案列表
	 * @throws Exception
	 */
	public void setAddress(final List<PeasantEXT> peasantEXTList) throws Exception {

		if ((null == peasantEXTList) || (peasantEXTList.isEmpty())) {
			return;
		}

		final Set<String> addresscodeSet = new HashSet<String>();

		for (PeasantEXT peasantEXT : peasantEXTList) {
			if (null != peasantEXT.getAddresscode()) {
				addresscodeSet.add(peasantEXT.getAddresscode());
			}
		}

		final List<PositionEXT> positionEXTList = new ArrayList<PositionEXT>();

		if (addresscodeSet.size() > 0) {
			PositionEXT tempPositionEXT = null;

			for (String adresscode : addresscodeSet) {
				tempPositionEXT = mCommonService.queryPositionByCode(adresscode);

				if (null != tempPositionEXT) {
					positionEXTList.add(tempPositionEXT);
				}
			}
		}

		for (PeasantEXT peasantEXT : peasantEXTList) {
			if (ObjectUtil.isBlank(peasantEXT.getAddresscode())) {
				peasantEXT.setAddress(ObjectUtil.toStringAndTrim(peasantEXT.getAddressdetail()));
			} else {
				for (PositionEXT positionEXT : positionEXTList) {
					if (positionEXT.getCode().equals(peasantEXT.getAddresscode())) {
						peasantEXT.setAddress((ObjectUtil.toStringAndTrim(positionEXT.getAllname())
								+ ObjectUtil.toStringAndTrim(peasantEXT.getAddressdetail())));

						break;
					}
				}
			}
		}

	}

	/**
	 * 设置合作农户的组织信息
	 * 
	 * @author wangjunwen
	 * @date 2018年3月29日 上午10:52:30
	 *
	 * @param peasantEXTList
	 *            合作农户列表
	 * @throws Exception
	 */
	public void setPeasantOrgInfo(final List<PeasantEXT> peasantEXTList) throws Exception {

		if ((null == peasantEXTList) || (peasantEXTList.isEmpty())) {
			return;
		}

		final Set<String> orgIdSet = new HashSet<String>();

		for (PeasantEXT peasantEXT : peasantEXTList) {
			orgIdSet.add(peasantEXT.getOrgid());
		}

		final List<SupportOrgEXT> supportOrgEXTList = new ArrayList<SupportOrgEXT>();

		SupportOrgEXT tempSupportOrgEXT = null;
		for (String supportOrgId : orgIdSet) {
			tempSupportOrgEXT = mCommonService.querySupportOrgBySupportid(supportOrgId);

			if (null != tempSupportOrgEXT) {
				supportOrgEXTList.add(tempSupportOrgEXT);
			}
		}

		if (supportOrgEXTList.size() > 0) {
			for (PeasantEXT peasantEXT : peasantEXTList) {
				for (SupportOrgEXT supportOrgEXT : supportOrgEXTList) {

					if (supportOrgEXT.getSupportid().equals(peasantEXT.getOrgid())) {
						peasantEXT.setOrgidname(supportOrgEXT.getName());
					}

				}
			}
		}

	}

	/**
	 * 导入合作农户档案
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午4:41:40
	 *
	 * @param allExcelDataList
	 *            导入的数据列表
	 * @param loginUserInfo
	 *            当前操作用户
	 * @return
	 * @throws Exception
	 */
	@Transactional // 支持事务控制
	public Integer importPeasant(final ArrayList<ArrayList<Object>> allExcelDataList, final LoginUserInfo loginUserInfo)
			throws Exception {
		List<PeasantEXT> peasantEXTList = this.convertToPeasant(allExcelDataList, loginUserInfo);

		Integer addResult = null;

		if ((null == peasantEXTList) || (peasantEXTList.isEmpty())) {
			throw new Exception("Excel 中无数据，导入失败。");
		}

		// 批量添加合作农户档案的熟练
		addResult = this.batchAdd(peasantEXTList);

		if ((null == addResult) || (peasantEXTList.size() != addResult.intValue())) {
			throw new Exception("batch add Peasant failure !");
		}

		Integer updateResult = null;

		for (PeasantEXT peasantEXT : peasantEXTList) {
			if (ObjectUtil.isBlank(peasantEXT.getPid())) {

				if (!(this.setPeasantPid(peasantEXT))) {
					throw new Exception("set Peasant.pid failure !");
				}

				updateResult = this.update(peasantEXT);

				if ((null == updateResult) || (1 != updateResult.intValue())) {
					throw new Exception("update Peasant failure !");
				}
			}
		}

		return addResult;
	}

	/**
	 * 将导入的数据转换为合作农事对象
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 下午4:22:18
	 *
	 * @param allExcelDataList
	 *            导入的数据列表
	 * @param loginUserInfo
	 *            当前操作用户
	 * @return
	 * @throws Exception
	 */
	public List<PeasantEXT> convertToPeasant(final ArrayList<ArrayList<Object>> allExcelDataList,
			final LoginUserInfo loginUserInfo) throws Exception {

		final List<PeasantEXT> peasantList = new ArrayList<PeasantEXT>();

		if ((null == allExcelDataList) || (allExcelDataList.isEmpty())) {
			return peasantList;
		}

		// 错误信息提示
		final StringBuffer errorMessageBuffer = new StringBuffer();

		PeasantEXT tempPeasantEXT = null;
		AtomicInteger index = null;

		BigDecimal linkphoneBigDecimal = null;

		Object value = null;
		StringBuffer addressBuffer = null;
		ArrayList<Object> list = null;

		// 空的数据行
		final ArrayList<Object> emptyRowDataList = new ArrayList<Object>();
		for (int i = 0; i < allExcelDataList.get(0).size(); i++) {
			emptyRowDataList.add(null);
		}

		final String emptyRowData = emptyRowDataList.toString();

		for (int i = 0; i < allExcelDataList.size(); i++) {
			list = allExcelDataList.get(i);

			// 排除空的数据行
			if (emptyRowData.equals(list.toString())) {
				continue;
			}

			tempPeasantEXT = new PeasantEXT();
			tempPeasantEXT.setOrgid(loginUserInfo.getLoginorgid());// 该合作农户档案属于当前操作者所在的组织

			index = new AtomicInteger(0);

			// 第一列（农户编号）：pid
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setPid(ObjectUtil.toStringAndTrim(value));
			}
			// 第二列（姓名）：name
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setName(ObjectUtil.toStringAndTrim(value).trim());
			} else {
				if (errorMessageBuffer.length() > 0) {
					errorMessageBuffer.append("\r\n");
				}
				errorMessageBuffer.append("第 " + (i + 2) + " 行的【姓名】不能为空。");
			}
			// 第三列（联系电话）：linkphone
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {

				try {
					linkphoneBigDecimal = new BigDecimal(String.valueOf(value));
					tempPeasantEXT.setLinkphone(linkphoneBigDecimal.toPlainString());
				} catch (Exception e) {
					tempPeasantEXT.setLinkphone(ObjectUtil.toStringAndTrim(value));
				}

			} else {
				if (errorMessageBuffer.length() > 0) {
					errorMessageBuffer.append("\r\n");
				}
				errorMessageBuffer.append("第 " + (i + 2) + " 行的【联系电话】不能为空。");
			}
			// 第四~九列地址（详细地址）：addressdetail
			addressBuffer = new StringBuffer();

			value = list.get(index.getAndIncrement());// 省
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			value = list.get(index.getAndIncrement());// 市
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			value = list.get(index.getAndIncrement());// 县
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			value = list.get(index.getAndIncrement());// 乡
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			value = list.get(index.getAndIncrement());// 村
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			value = list.get(index.getAndIncrement());// 详细地址
			addressBuffer.append(ObjectUtil.toStringAndTrim(value));

			if (ObjectUtil.isNotBlank(addressBuffer)) {
				tempPeasantEXT.setAddressdetail(ObjectUtil.toStringAndTrim(addressBuffer));
			} else {
				if (errorMessageBuffer.length() > 0) {
					errorMessageBuffer.append("\r\n");
				}
				errorMessageBuffer.append("第 " + (i + 2) + " 行的【详细地址】不能为空。");
			}

			// 第十列（性别）：sex
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setSex(EDict.getDictValue(EDictType.user_sex, ObjectUtil.toStringAndTrim(value)));

				if (ObjectUtil.isBlank(tempPeasantEXT.getSex())) {
					if (errorMessageBuffer.length() > 0) {
						errorMessageBuffer.append("\r\n");
					}
					errorMessageBuffer.append("第 " + (i + 2) + " 行的【性别】无效。");
				}
			}
			// 第十一列（身份证号）：identity
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setIdentity(ObjectUtil.toStringAndTrim(value));

				// 身份证号要么是18位，要么是15位
				if ((18 != tempPeasantEXT.getIdentity().length()) && (15 != tempPeasantEXT.getIdentity().length())) {
					if (errorMessageBuffer.length() > 0) {
						errorMessageBuffer.append("\r\n");
					}
					errorMessageBuffer.append("第 " + (i + 2) + " 行的【身份证号】无效。");
				}

				// 根据身份证号设置生日
				this.setBirthdayByIdentity(tempPeasantEXT);
			}
			// 第十二列（文化程度）：culture
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT
						.setCulture(EDict.getDictValue(EDictType.peasant_culture, ObjectUtil.toStringAndTrim(value)));

				if (ObjectUtil.isBlank(tempPeasantEXT.getCulture())) {
					if (errorMessageBuffer.length() > 0) {
						errorMessageBuffer.append("\r\n");
					}
					errorMessageBuffer.append("第 " + (i + 2) + " 行的【文化程度】无效。");
				}
			} else {
				if (errorMessageBuffer.length() > 0) {
					errorMessageBuffer.append("\r\n");
				}
				errorMessageBuffer.append("第 " + (i + 2) + " 行的【文化程度】不能为空。");
			}
			// 蒂十三列（合作状态）：cooperstate
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setCooperstate(
						EDict.getDictValue(EDictType.peasant_cooperstate, ObjectUtil.toStringAndTrim(value)));

				if (ObjectUtil.isBlank(tempPeasantEXT.getCooperstate())) {
					if (errorMessageBuffer.length() > 0) {
						errorMessageBuffer.append("\r\n");
					}
					errorMessageBuffer.append("第 " + (i + 2) + " 行的【当前合作状态】无效。");
				}
			} else {
				if (errorMessageBuffer.length() > 0) {
					errorMessageBuffer.append("\r\n");
				}
				errorMessageBuffer.append("第 " + (i + 2) + " 行的【当前合作状态】不能为空。");
			}
			// 第十四列（农户贫困状态）：ispoverty
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT
						.setIspoverty(EDict.getDictValue(EDictType.peasant_poverty, ObjectUtil.toStringAndTrim(value)));

				if (ObjectUtil.isBlank(tempPeasantEXT.getIspoverty())) {
					if (errorMessageBuffer.length() > 0) {
						errorMessageBuffer.append("\r\n");
					}
					errorMessageBuffer.append("第 " + (i + 2) + " 行的【是否贫困户】无效。");
				}
			}
			// 第十五列（备注）：explain
			value = list.get(index.getAndIncrement());
			if (ObjectUtil.isNotBlank(value)) {
				tempPeasantEXT.setExplain(ObjectUtil.toStringAndTrim(value));
			}

			// ---------- 需要系统自动设置的属性 ----------------
			this.setGenderByIdentity(tempPeasantEXT);// 设置性别

			// 添加到合作农户档案列表中
			peasantList.add(tempPeasantEXT);
		}

		if (errorMessageBuffer.length() > 0) {
			throw new Exception(errorMessageBuffer.toString());
		}

		return peasantList;
	}

	/**
	 * 根据身份证号自动设置出生日期
	 * 
	 * @author wangjunwen
	 * @date 2018年4月8日 上午10:12:24
	 *
	 * @param peasantEXT
	 *            PeasantEXT对象
	 * @return
	 */
	public boolean setBirthdayByIdentity(final PeasantEXT peasantEXT) {
		if (null == peasantEXT) {
			return false;
		}

		if (ObjectUtil.isNotBlank(peasantEXT.getBirthday())) {
			return true;
		}

		if (ObjectUtil.isNotBlank(peasantEXT.getIdentity())) {
			String identity = peasantEXT.getIdentity().trim();

			String birtrhday = null;// 生日

			if (18 == identity.length()) {
				// 18位身份证号: 530102195608056518
				birtrhday = identity.substring(6, 14);
			} else if (15 == identity.length()) {
				// 15位身份证号: 530102560805651
				birtrhday = identity.substring(6, 12);
				birtrhday = ("19" + birtrhday);
			}

			if (null != birtrhday) {
				StringBuffer birtrhdayBuffer = new StringBuffer(birtrhday);
				// 年、月、日中间以中划线分隔
				birtrhdayBuffer.insert(4, "-").insert(7, "-");

				Date birthdayDate = DateTimeUtil.parse(birtrhdayBuffer.toString());

				peasantEXT.setBirthday(birthdayDate);
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据身份证号自动设置性别
	 * 
	 * @author wangjunwen
	 * @date 2018年4月8日 上午10:12:24
	 *
	 * @param peasantEXT
	 *            PeasantEXT对象
	 * @return
	 */
	public boolean setGenderByIdentity(final PeasantEXT peasantEXT) {
		if (null == peasantEXT) {
			return false;
		}

		if (ObjectUtil.isNotBlank(peasantEXT.getSex())) {
			return true;
		}

		if (ObjectUtil.isNotBlank(peasantEXT.getIdentity())) {
			String identity = peasantEXT.getIdentity().trim();

			String genderNumber = null;// 代表性别是数字

			if (18 == identity.length()) {
				// 18位身份证号: 530102195608056518
				genderNumber = identity.substring(16, 17);
			} else if (15 == identity.length()) {
				// 15位身份证号: 530102560805651
				genderNumber = identity.substring(14, 15);
			}

			if ((null != genderNumber) && (genderNumber.matches("[0-9]"))) {

				int gender = Integer.valueOf(genderNumber);

				// 男单女双
				if (0 == gender % 2) {
					peasantEXT.setSex(EDict.user_sex_female.getDict().getDictValue());
				} else {
					peasantEXT.setSex(EDict.user_sex_male.getDict().getDictValue());
				}

				return true;
			}
		}

		return false;
	}

}
