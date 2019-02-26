/**
 * 农事相关接口
 */

import http from '@/http'

/**
 * 获取地块 农事记录中
 */
export function getLand () {
  return http({
    url: '/fwk-service-farm/record/queryparcellist',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: {operateid: 1}
  })
}

/**
 * 获取地块 农事任务中
 */
export function getLandToTask () {
  return http({
    url: '/fwk-service-farm/task/queryparcellist',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: {operateid: 1}
  })
}

/**
 * 获取参与人
 */
export function getJoinPeople () {
  return http({
    url: 'http://10.88.20.66:5766/fwk-service-user/user/queryUserAndDepartByLoginUserOrg?loginorgid=5a912101a1de180001ada670',
    // http://10.88.20.55:5766/fwk-service-user/user/queryUserAndDepartByLoginUserOrg?loginorgid=1

    method: 'get',
    headers: { 'Content-Type': 'application/json' }
  })
}

/**
 * 农事记录管理相关接口
 */
/**
 * 新增农事记录
 * @param content 农事内容 必填
 * @param farmtypelist 农事类别 必填
 * @param begintime 开始时间 必填
 * @param endtime 结束时间
 * @param cost 投入成本
 * @param remark 备注
 * @param recordparcellist 地块列表
 * @param recordpartuserlist 参与人列表
 * @param outputlist 投入品记录列表
 * @param resourcehistorylist 附件列表
 * @param sourcedictvalue 记录来源
 * @param operateid
 */
export function addRecord (content, farmtypelist, begintime, endtime, cost, remark, recordparcellist, recordpartuserlist, outputlist, resourcehistorylist, sourcedictvalue, operateid) {
  const param = {
    content: content,
    farmtypelist: farmtypelist,
    begintime: begintime,
    endtime: endtime,
    cost: cost,
    remark: remark,
    recordparcellist: recordparcellist,
    recordpartuserlist: recordpartuserlist,
    outputlist: outputlist,
    resourcehistorylist: resourcehistorylist,
    sourcedictvalue: sourcedictvalue,
    operateid: operateid
  }
  return http({
    url: '/fwk-service-farm/record/add',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 查询农事记录
 * @param parcelid	地块的ID
 * @param keyword	农事内容、参与人
 * @param currentPage 当前页
 * @param pageSize 页大小
 * delstate 记录删除状态
 */
export function queryRecord (parcelid, keyword, currentPage, pageSize) {
  const param = {
    parcelid: parcelid || null,
    keyword: keyword || null,
    currentPage: currentPage || 1, // 当前页
    pageSize: pageSize || 15, // 页大小
    delstate: 1
  }
  return http({
    url: '/fwk-service-farm/record/querylist',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 根据id修改农事记录
 * @param id id
 * @param content 农事内容
 * @param farmtype 农事类别 必填
 * @param begintime 开始时间
 * @param endtime 结束时间
 * @param cost 投入成本
 * @param remark 备注
 * @param recordparcellist 地块列表
 * @param recordpartuserlist 参与人列表
 * @param outputlist 投入品记录列表
 * @param resourcehistorylist 附件列表
 * @param operateid
 */
export function updateRecord (id, content, farmtype, begintime, endtime, cost, remark, recordparcellist, recordpartuserlist, outputlist, resourcehistorylist, operateid) {
  const param = {
    id: id,
    content: content,
    farmtype: farmtype,
    begintime: begintime,
    endtime: endtime,
    cost: cost,
    remark: remark,
    recordparcellist: recordparcellist,
    recordpartuserlist: recordpartuserlist,
    outputlist: outputlist,
    resourcehistorylist: resourcehistorylist,
    operateid: operateid
  }
  return http({
    url: '/fwk-service-farm/record/update',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 根据id查询单个农事记录
 * @param id	id
 */
export function queryById (id) {
  const param = {
    id: id
  }
  return http({
    url: '/fwk-service-farm/record/querybyid',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 根据id查删除农事记录
 * @param id	id
 */
export function deleteById (id) {
  const param = {
    id: id
  }
  return http({
    url: '/fwk-service-farm/record/deletebyid',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 农事任务管理相关接口
 */
/**
 * 添加农事任务
   @param type	任务类别
   @param describe	任务内容
   @param begintime	开始时间
   @param endtime	结束时间
   @param taskparticslist	参与人列表
   @param isaffirmpartics	是否需要执行人确认参与任务
   @param remark	备注
   @param meetingplace	约会地点
   @param taskparcellist	地块列表
   @param taskcopylist	抄送人列表
   @param taskwarnlist	提醒方式列表
 */
export function taskAdd (type, describe, begintime, endtime, taskparticslist, isaffirmpartics, remark, meetingplace, taskparcellist, taskcopylist, taskwarnlist) {
  const param = {
    type: type,
    describe: describe,
    begintime: begintime,
    endtime: endtime,
    taskparticslist: taskparticslist,
    isaffirmpartics: isaffirmpartics,
    remark: remark,
    meetingplace: meetingplace,
    taskparcellist: taskparcellist,
    taskcopylist: taskcopylist,
    taskwarnlist: taskwarnlist,
    operateid: 1 // 操作人编号(userid)
  }
  return http({
    url: '/fwk-service-farm/task/add',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 查询农事任务
 * @param select 查询谁 taskparticsuserid(我参与的) createuserid(我发布的) taskcopyuserid(抄送我的)
 * @param userid 用户id
 * @param taskstatus	任务状态
 * @param type	任务类别
 * @param keyword	任务内容、发布人
 * @param currentPage 当前页
 * @param pageSize 页大小
 */
export function queryTask (select, userid, taskstatus, type, keyword, currentPage, pageSize) {
  const param = {}
  switch (select) {
    // taskparticsuserid(我参与的)
    case 1:
      param.taskparticsuserid = userid || null
      break
    // createuserid(我发布的)
    case 2:
      param.createuserid = userid || null
      break
    // taskcopyuserid(抄送我的)
    case 3:
      param.taskcopyuserid = userid || null
      break
  }
  param.taskstatus = taskstatus || null // 任务状态
  param.type = type || null // 任务类别
  param.keyword = keyword || null // 任务内容、发布人
  param.currentPage = currentPage || 1 // 当前页
  param.pageSize = pageSize || 15 // 页大小
  param.delstate = 1
  param.operateid = 1
  return http({
    url: '/fwk-service-farm/task/querylist',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 根据id查看农事任务
 * @param id
 */
export function queryTaskDetail (id) {
  const param = {
    id: id,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/task/querybyid',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 根据id删除农事任务
 * @param id
 */
export function deleteTask (id) {
  const param = { id: id }
  return http({
    url: '/fwk-service-farm/task/deletebyid',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 修改农事任务
 @param id
 @param type	任务类别
 @param describe	任务内容
 @param begintime	开始时间
 @param endtime	结束时间
 @param taskparticslist	参与人列表
 @param isaffirmpartics	是否需要执行人确认参与任务
 @param remark	备注
 @param taskcopylist	抄送人列表
 @param taskwarnlist	提醒方式列表
 */
export function taskQuery (id, type, describe, begintime, endtime, taskparticslist, isaffirmpartics, remark, taskcopylist, taskwarnlist) {
  const param = {
    id: id,
    type: type,
    describe: describe,
    begintime: begintime,
    endtime: endtime,
    taskparticslist: taskparticslist,
    isaffirmpartics: isaffirmpartics,
    remark: remark,
    taskcopylist: taskcopylist,
    taskwarnlist: taskwarnlist
  }
  return http({
    url: '/fwk-service-farm/task/update',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 确认农事任务
 * @param taskid	农事任务的ID
 * @param userid	农事参与用户的ID
 * @param taskparticsstatus	参与农事任务的状态
 */
export function taskConfirm (taskid, userid, taskparticsstatus) {
  const param = {
    taskid: taskid,
    userid: userid,
    taskparticsstatus: taskparticsstatus
  }
  return http({
    url: '/fwk-service-farm/task/confirmtask',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 农事任务保存到农事记录
 * @param taskid	农事任务的ID
 * @param recordeid	农事记录的ID
 */
export function saveToRecord (taskid, recordeid) {
  const param = {
    taskid: taskid,
    recordeid: recordeid,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/task/savetorecord',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 添加农事任务的评论
 * @param taskid	农事任务的ID
 * @param userid	农事参与用户的ID
 * @param content 评论的内容
 */
export function addTaskComment (taskid, userid, content) {
  const param = {
    taskid: taskid,
    userid: userid,
    content: content
  }
  return http({
    url: '/fwk-service-farm/task/addtaskcomment',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 查看任务动态
 * @param taskid 农事任务的ID
 * @param userid 农事参与用户的ID
 * @param content 评论的内容
 */
export function queryTaskDynamic (taskid, userid, content) {
  const param = {
    taskid: taskid,
    userid: userid,
    content: content,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/task/querytaskdynamic',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 完成农事任务
 * @param taskid 农事任务 id
 * @param recordeid 农事记录id
 */
export function updateasFinish (taskid, recordeid) {
  const param = {
    taskid: taskid,
    recordeid: recordeid,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/task/updateasfinish',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

/**
 * 农事计划相关接口
 */

/**
 * 获取地块 农事计划中
 */
export function getLandToPlan () {
  return http({
    url: '/fwk-service-farm/plan/queryparcellist',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {operateid: 1}
  })
}

/**
 * 新增农事计划
 * @param describe	计划内容 必填
 * @param begintime	开始时间 必填
 * @param endtime	结束时间 必填
 * @param planparticslist	参与人列表 必填
 * @param remark	备注
 * @param planparcellist	地块列表
 * @param planwarnlist	提醒方式列表
 */
export function addPlan (describe, begintime, endtime, planparticslist, remark, planparcellist, planwarnlist) {
  const param = {
    describe: describe,
    begintime: begintime,
    endtime: endtime,
    planparticslist: planparticslist,
    remark: remark,
    planparcellist: planparcellist,
    planwarnlist: planwarnlist,
    operateid: 1 // 操作人编号(userid)
  }
  return http({
    url: '/fwk-service-farm/plan/add',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 修改农事计划
 * @param id id
 * @param describe	计划内容 必填
 * @param begintime	开始时间 必填
 * @param endtime	结束时间 必填
 * @param planparticslist	参与人列表 必填
 * @param remark	备注
 * @param planparcellist	地块列表
 * @param planwarnlist	提醒方式列表
 */
export function updatePlan (id, describe, begintime, endtime, planparticslist, remark, planparcellist, planwarnlist) {
  const param = {
    id: id,
    describe: describe,
    begintime: begintime,
    endtime: endtime,
    planparticslist: planparticslist,
    remark: remark,
    planparcellist: planparcellist,
    planwarnlist: planwarnlist,
    operateid: 1 // 操作人编号(userid)
  }
  return http({
    url: '/fwk-service-farm/plan/update',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 查询农事计划
 * @param planparticsuserid	计划参与人的ID
 * @param createuserid	计划发布人的ID
 * @param keyword	计划内容
 * @param begintime	开始时间
 * @param endtime	结束时间
 * @param currentPage 当前页
 * @param pageSize 页大小
 */
export function queryPlan (planparticsuserid, createuserid, keyword, begintime, endtime, currentPage, pageSize) {
  const param = {
    planparticsuserid: planparticsuserid,
    createuserid: createuserid,
    keyword: keyword,
    begintime: begintime,
    endtime: endtime,
    currentPage: currentPage || 1,
    pageSize: pageSize || 10,
    delstate: 1,
    operateid: 1 // 操作人编号(userid)
  }
  return http({
    url: '/fwk-service-farm/plan/querylist',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 删除农事计划
 * @param id
 */
export function deletePlan (id) {
  const param = {id: id}
  return http({
    url: '/fwk-service-farm/plan/deletebyid',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 根据id查看农事计划
 * @param id
 */
export function getplanDetail (id) {
  const param = {id: id}
  return http({
    url: '/fwk-service-farm/plan/querybyid',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 以下是 农事交流相关接口
 */

/**
 * 查询地块 - 农事交流
 */
export function queryParcelList () {
  return http({
    url: '/fwk-service-farm/communion/queryparcellist',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: {operateid: 1}
  })
}

/**
 * 新增农事交流
 * communioncontent	问题内容
 * communioncategorylist	品类列表—来源于支撑平台
 * communionparcellist	地块列表
 * resourcehistorylist	附件列表
 * location	发布人的地理位置
 */
export function addAsk (communioncontent, communioncategorylist, communionparcellist, resourcehistorylist, location) {
  const param = {
    communioncontent: communioncontent,
    communioncategorylist: communioncategorylist,
    communionparcellist: communionparcellist,
    resourcehistorylist: resourcehistorylist,
    location: location,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/communion/add',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 查询农事交流
 * @param keyword	提问内容、发布人、品类、地块、地点
 * @param currentPage
 */
export function queryAsk (keyword, currentPage) {
  const param = {
    keyword: keyword || null,
    currentPage: currentPage || 1,
    delstate: 1
  }
  return http({
    url: '/fwk-service-farm/communion/querylist',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 查看农事交流详情
 * @param id id
 */
export function queryAskDetail (id) {
  const param = { id: id }
  return http({
    url: '/fwk-service-farm/communion/querybyid',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 以下是 农事交流评论 相关接口
 */
/**
 * 新增农事交流评论
 * @param communionid	农事交流的ID
 * @param commentcontent	评论的内容
 */
export function addComment (communionid, commentcontent) {
  const param = {
    communionid: communionid,
    commentcontent: commentcontent,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/communion/commentcontent/add',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 新增农事交流评论的回复
 * @param communionid	农事交流的ID
 * @param communioncommentid 农事交流的评论的ID
 * @param reversioncontent	回复的内容
 * @param parentid 农事交流评论的回复的ID 是回复 评论的回复 时必填
 */
export function addCommentToReply (communionid, communioncommentid, reversioncontent, parentid) {
  const param = {
    communionid: communionid,
    communioncommentid: communioncommentid,
    reversioncontent: reversioncontent,
    parentid: parentid,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/communion/commentreversion/add',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 查询农事交流评论
 * @param communionid 农事交流的ID
 */
export function queryComment (communionid) {
  const param = {
    communionid: communionid,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/communion/commentcontent/querylistbycommunionid',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 查询某个农事交流的评论的所有回复
 * @param communioncommentid	农事交流的评论的ID
 */
export function queryCommentToReply (communioncommentid) {
  const param = {
    communioncommentid: communioncommentid,
    operateid: 1
  }
  return http({
    url: '/fwk-service-farm/communion/commentcontentreversion/querylistbycommunioncommentid',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

