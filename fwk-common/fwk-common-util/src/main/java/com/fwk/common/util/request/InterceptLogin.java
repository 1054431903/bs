package com.fwk.common.util.request;

import java.util.HashMap;
import java.util.Map;

//登录验证信息
public class InterceptLogin {

	public static Map<String, Object> interceptNeedLoginMap = new HashMap<String, Object>();

	static {
		// key:项目名-方法名，value:是否需要验证登录
		interceptNeedLoginMap.put("system-login", false);
		interceptNeedLoginMap.put("system-saveHomeView", false);
		interceptNeedLoginMap.put("system-sendNoteToUsernameByVerificode", false);
		interceptNeedLoginMap.put("system-checkVirifiCode", false);
		interceptNeedLoginMap.put("system-changeUserPassword", false);
		interceptNeedLoginMap.put("product-queryProductionByTraceNoOrFakeNo", false);// 根据追溯码或者验证码查询产品信息
		interceptNeedLoginMap.put("product-queryAllShowHomePageByProduction", false);// 首页广告列表
		interceptNeedLoginMap.put("product-queryProductionByIdForPageHomeAdvertisement", false);// 首页广告产品的产品档案
		interceptNeedLoginMap.put("product-queryAllProductionByChoice", false);// 精选产品列表
		interceptNeedLoginMap.put("product-queryProductionByIdForChoice", false);// 精选产品的产品档案
		interceptNeedLoginMap.put("enterprise-queryEnterpriseInfoById", false);// 查看企业信息
		interceptNeedLoginMap.put("product-queryProductionCountByEnterpriseId", false);// 查询企业拥有的产品数量
		interceptNeedLoginMap.put("product-queryUserCenterProduction", false);// 用户中心的追溯档案列表
		interceptNeedLoginMap.put("product-queryFakeArchivesHistory", false);// 验证码历史
		interceptNeedLoginMap.put("product-printFakeNoQRCodeByFakeArchivesHistory", false);// 根据添加验证码的历史打印验证码
		interceptNeedLoginMap.put("product-queryEnterpriseProductionById", false);// 查看企业的产品档案详情
		interceptNeedLoginMap.put("product-queryAllProductionByEnterprise", false);// 查看企业的全部产品
		interceptNeedLoginMap.put("product-queryProductionType", false);// 查询产品类型
	}

}
