import mongo from '@vehiclecloud/mongo-rest-client'
export function CTask (task) {
  this.id = task && task.hasOwnProperty('id') ? task.id : null
  this.approver = task && task.hasOwnProperty('approver') ? task.approver : null // 任务审批人
  this.content = task && task.hasOwnProperty('content') ? task.content : null // 任务内容
  this.creator = task && task.hasOwnProperty('creator') ? task.creator : null // 任务创建人
  this.endOn = task && task.hasOwnProperty('endOn') ? task.endOn : null // 任务结束时间
  this.examiner = task && task.hasOwnProperty('examiner') ? task.examiner : null // 任务考核人
  this.examination =
    task && task.hasOwnProperty('examination') ? task.examination : null // 任务考核结果
  this.farmlands =
    task && task.hasOwnProperty('farmlands') ? task.farmlands : null // 任务地块
  this.participators =
    task && task.hasOwnProperty('participators') ? task.participators : null // 任务执行人
  this.representative =
    task && task.hasOwnProperty('representative') ? task.representative : null // 任务负责人
  this.stage = task && task.hasOwnProperty('stage') ? task.stage : null // 任务所处阶段
  this.startOn = task && task.hasOwnProperty('startOn') ? task.startOn : null // 任务开始时间
  this.createdOn =
    task && task.hasOwnProperty('createdOn') ? task.createdOn : null // 任务创建时间
  this.updatedOn =
    task && task.hasOwnProperty('updatedOn') ? task.updatedOn : null // 任务修改时间
  this.tenant = task && task.hasOwnProperty('tenant') ? task.tenant : null // 所属组织
  this.finish = task && task.hasOwnProperty('finish') ? task.finish : null // 任务完成标记
  this.approval = task && task.hasOwnProperty('approval') ? task.approval : null // 审批状态
  this.approvalOn =
    task && task.hasOwnProperty('approvalOn') ? task.approvalOn : null // 审批时间
  this.approvalContent =
    task && task.hasOwnProperty('approvalContent') ? task.approvalContent : null // 审批内容
  this.finishOn = task && task.hasOwnProperty('finishOn') ? task.finishOn : null // 完成时间
  this.finishContent =
    task && task.hasOwnProperty('finishContent') ? task.finishContent : null // 完成内容
  this.examinationOn =
    task && task.hasOwnProperty('examinationOn') ? task.examinationOn : null // 考核时间
  this.examinationContent =
    task && task.hasOwnProperty('examinationContent')
      ? task.examinationContent
      : null // 考核内容
}

CTask.prototype.getId = function () {
  return this.id
}

CTask.prototype.setId = function (id) {
  this.id = id
}

CTask.prototype.getApprover = function () {
  return this.approver
}

CTask.prototype.setApprover = function (approver) {
  this.approver = approver
}

CTask.prototype.getContent = function () {
  return this.content
}

CTask.prototype.setContent = function (content) {
  this.content = content
}

CTask.prototype.getCreator = function () {
  return this.creator
}

CTask.prototype.setCreator = function (creator) {
  this.creator = creator
}

CTask.prototype.getEndOn = function () {
  return this.endOn
}

CTask.prototype.setEndOn = function (endOn) {
  this.endOn = endOn
}

CTask.prototype.getExaminer = function () {
  return this.examiner
}

CTask.prototype.setExaminer = function (examiner) {
  this.examiner = examiner
}

CTask.prototype.getExamination = function () {
  return this.examination
}

CTask.prototype.setExamination = function (examination) {
  this.examination = examination
}

CTask.prototype.getFarmlands = function () {
  return this.farmlands
}

CTask.prototype.setFarmlands = function (farmlands) {
  this.farmlands = farmlands
}

CTask.prototype.getParticipators = function () {
  return this.participators
}

CTask.prototype.setParticipators = function (participators) {
  this.participators = participators
}

CTask.prototype.getRepresentative = function () {
  return this.representative
}

CTask.prototype.setRepresentative = function (representative) {
  this.representative = representative
}

CTask.prototype.getStage = function () {
  return this.stage
}

CTask.prototype.setStage = function (stage) {
  this.stage = stage
}

CTask.prototype.getStartOn = function () {
  return this.startOn
}

CTask.prototype.setStartOn = function (startOn) {
  this.startOn = startOn
}

CTask.prototype.getCreatedOn = function () {
  return this.createdOn
}

CTask.prototype.setCreatedOn = function (createdOn) {
  this.createdOn = createdOn
}

CTask.prototype.getUpdatedOn = function () {
  return this.updatedOn
}

CTask.prototype.setUpdatedOn = function (updatedOn) {
  this.updatedOn = updatedOn
}

CTask.prototype.getTenant = function () {
  return this.tenant
}

CTask.prototype.setTenant = function (tenant) {
  this.tenant = tenant
}

CTask.prototype.getFinish = function () {
  return this.finish
}

CTask.prototype.setFinish = function (finish) {
  this.finish = finish
}

CTask.prototype.getApproval = function () {
  return this.approval
}

CTask.prototype.setApproval = function (approval) {
  this.approval = approval
}

CTask.prototype.getApprovalOn = function () {
  return this.approvalOn
}

CTask.prototype.setApprovalOn = function (approvalOn) {
  this.approvalOn = approvalOn
}

CTask.prototype.getApprovalContent = function () {
  return this.approvalContent
}

CTask.prototype.setApprovalContent = function (approvalContent) {
  this.approvalContent = approvalContent
}

CTask.prototype.getFinishOn = function () {
  return this.finishOn
}

CTask.prototype.setFinishOn = function (finishOn) {
  this.finishOn = finishOn
}

CTask.prototype.getFinishContent = function () {
  return this.finishContent
}

CTask.prototype.setFinishContent = function (finishContent) {
  this.finishContent = finishContent
}

CTask.prototype.getExaminationOn = function () {
  return this.examinationOn
}

CTask.prototype.setExaminationOn = function (examinationOn) {
  this.examinationOn = examinationOn
}

CTask.prototype.getExaminationContent = function () {
  return this.examinationContent
}

CTask.prototype.setExaminationContent = function (examinationContent) {
  this.examinationContent = examinationContent
}

CTask.prototype.add = function () {
  try {
    let task = {}
    let participators = []
    this.getParticipators().map((participator, index) => {
      participators.push({ $oid: participator.id })
    })
    let lands = []
    Object.assign(task, { participators: participators })
    this.getFarmlands().map(land => {
      lands.push({ $oid: land })
    })
    Object.assign(task, { farmlands: lands })
    this.getApprover() && this.getApprover().id
      ? Object.assign(task, { approver: { $oid: this.getApprover().id } })
      : null
    this.getExaminer() && this.getExaminer().id
      ? Object.assign(task, { examiner: { $oid: this.getExaminer().id } })
      : null
    Object.assign(task, { creator: { $oid: this.getCreator().supportid } })
    // Object.assign(task, { createdOn: new Date(this.getCreatedOn()).getTime() })
    Object.assign(task, { content: this.getContent() })
    Object.assign(task, { startOn: new Date(this.getStartOn()).getTime() })
    Object.assign(task, { endOn: new Date(this.getEndOn()).getTime() })
    this.getRepresentative()
      ? Object.assign(task, {
        representative: { $oid: this.getRepresentative() }
      })
      : null
    Object.assign(task, { tenant: { $oid: this.getTenant() } })
    Object.assign(task, { finish: this.getFinish() })
    Object.assign(task, { stage: this.getStage() })
    let item = mongo
      .db()
      .collection('task')
      .insert(task)
    return item
  } catch (err) {
    console.log(err)
    return false
  }
}
