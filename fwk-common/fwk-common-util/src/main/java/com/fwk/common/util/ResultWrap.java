package com.fwk.common.util;

import com.fwk.common.util.language.LanguageUtil;

/**
 * 返回值处理包装
 * 
 * @author xiexiang
 * @date 2017-07-18 10:03:29
 *
 */
public class ResultWrap {

	private Integer flag;
	private String message;
	private Object data;
	private Integer statusCode;
	private Page page;
	private String exception;// 异常信息
	// 服务当前时间 ：格式（yyyy-MM-dd HH:mm:ss）
	private String servicecurrenttime = DateTimeUtil.getNowDateTimeString();;

	/**
	 * 操作成功
	 */
	public void setActSuccess() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_OK;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-act-success");
	}

	/**
	 * 操作失败
	 */
	public void setActFailure() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-act-failure");
	}
	
	/**
	 * 重新登录
	 * */
	public void setLoginAgain(String msg) {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_LOGIN_AGAIN;
		this.message = LanguageUtil.getLanguageByKeyAndParam("fwk-message-login-again",msg);
	}

	public void setSupportNoInfoActFailure() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-support-no-info-act-failure");
	}

	/**
	 * 添加成功
	 */
	public void setSaveSuccess() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_OK;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-save-success");
	}

	/**
	 * 添加失败
	 */
	public void setSaveFailure() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-save-failure");
	}

	/**
	 * 修改成功
	 */
	public void setUpdateSuccess() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_OK;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-update-success");
	}

	/**
	 * 修改失败
	 */
	public void setUpdateFailure() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-update-failure");
	}

	/**
	 * 删除成功
	 */
	public void setDeleteSuccess() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_OK;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-delete-success");
	}

	/**
	 * 删除失败
	 */
	public void setDeleteFailure() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-delete-failure");
	}

	/**
	 * id不能为空
	 */
	public void setIDIsnotEmpty() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-id-not-empty");
	}

	/**
	 * 参数不能为空
	 */
	public void setParamIsnotEmpty(String param) {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKeyAndParam("fwk-message-param-not-empty", param);
	}

	/**
	 * 参无效
	 */
	public void setParamInvalid(String... params) {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;

		StringBuffer paramBuffer = new StringBuffer();
		if ((null != params) && (params.length > 0)) {
			paramBuffer.append(" [ ");

			for (int i = 0; i < params.length; i++) {

				if (i > 0) {
					paramBuffer.append(" , ");
				}

				paramBuffer.append(params[i]);
			}

			paramBuffer.append(" ] ");
		}
		this.message = LanguageUtil.getLanguageByKeyAndParam("fwk-message-param-invalid", paramBuffer.toString());
	}

	public String getException() {
		return exception;
	}

	/**
	 * 服务异常
	 */
	public void setException(String exception) {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-service-exception");
		this.exception = exception;
	}

	/**
	 * 数据重复
	 */
	public void setDateRepit() {
		this.flag = ConstantUtils.RESULTWRAP_FLAG_ERROR;
		this.message = LanguageUtil.getLanguageByKey("fwk-message-save-date-repit");
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public boolean success() {
		return 0 == getFlag() - 1;
	}

	/**
	 * 操作失败
	 * 
	 * @author wangjunwen
	 * @date 2017年8月29日 下午1:40:36
	 *
	 * @param message
	 *            操作失败的提示信息
	 */
	public void error(String message) {
		this.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
		this.setMessage(message);
	}

	/**
	 * 超时未操作
	 */
	public void loginOut(String message) {
		this.setFlag(2);
		this.setMessage(message);
	}

	/**
	 * 操作失败
	 * 
	 * <pre>
	 * 已默认设置了操作失败的提示信息
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2017年8月29日 下午1:41:05
	 *
	 */
	public void error() {
		this.setFlag(ConstantUtils.RESULTWRAP_FLAG_ERROR);
		this.setMessage(LanguageUtil.getLanguageByKey("fws-message-failure"));
	}

	/**
	 * 操作成功
	 */
	public void msg(String message) {
		this.setFlag(ConstantUtils.RESULTWRAP_FLAG_OK);
		this.setMessage(message);
	}

	public ResultWrap() {
		this.setFlag(ConstantUtils.RESULTWRAP_FLAG_OK);
		this.setMessage(LanguageUtil.getLanguageByKey("act-message-success"));
	}

	public String getServicecurrenttime() {
		return servicecurrenttime;
	}

	public void setServicecurrenttime(String servicecurrenttime) {
		this.servicecurrenttime = servicecurrenttime;
	}

}
