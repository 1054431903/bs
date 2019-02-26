package com.fwk.service.schedule.service;

public interface NoticeService {
	
	public void noticeTaskNearTheStartByTaskIdAndStaffId(String taskId, String staffId) throws Exception;

    public void noticeTaskNearTheEndByTaskIdAndStaffId(String taskId, String staffId) throws Exception;
    
    public void noticePlanContentNearTheStartByPlanContentIdAndStaffId(String planContentId, String staffId) throws Exception;
    
    public void noticePlanContentNearTheEndByPlanContentIdAndStaffId(String planContentId, String staffId) throws Exception;

	public void notice(String messageId);
}
