<template lang="pug">
div
  v-modaldialog(
    :isShow="addOrEditDialog",
    :title="addOrEditTitle",
    :modalMaxWidth="900",
    :bodyHeight="400")
    v-form(v-model="taskFormValid" ref="taskForm" lazy-validation autocomplete="off")
      v-layout(row, wrap)
        v-flex(xs12)
          v-text-field(
            label="任务内容"
            v-model="task.content"
            counter="500"
            maxlength="500"
            required
            :rules="rules.content")
      .py-1        
      v-layout(row, wrap)
        v-flex(xs12)
          div(:class="{isValid: isExcutionStaffValid, isInvalid: !isExcutionStaffValid}") 任务执行人
        v-flex.py-2(xs12)
          v-layout(row, wrap)
            template(v-for="(excstaff, index) in excutestaffslist")
              v-flex.mb-2(xs1 class="staff")
                div(:title="excstaff.name" @mouseenter="getSelectedEStaff(index)" @mouseleave="curEStaffId = null")
                  v-avatar(size="50")
                    img(:src="excstaff.avatar || `/static/task/fuzenren.png`" @click="selectedExcuteStaff")
                    div(style="position: absolute; right: 0; top: -5px;" @click="removeEStaff(index)" v-if="curEStaffId === excstaff.id")
                      v-avatar(class="grey" size="15px")
                        img(class="zengjia" :src="`static/sop/ic_close_white_24px.svg`")
                    div(style="position: absolute; right: 0; bottom: -5px;" v-if="index === 0")
                      img(:src="`/static/task/fuzeren-biaoji.png`" class="badgeimg")     
            v-flex(xs1)        
              v-avatar(size="50")
                img(class="zengjia" :src="`/static/task/zengjia.png`" @click="selectedExcuteStaff")
        //- v-flex(x12)
        //-   div(:class="{isValid: isExcutionStaffValid, isInvalid: !isExcutionStaffValid, isValidBorder: isExcutionStaffValid, isInvalidBorder: !isExcutionStaffValid}") <span v-if="!isExcutionStaffValid"> 必填项 </span>&nbsp;&nbsp;
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            label="任务开始时间"
            v-model="task.startOn"
            readonly
            required
            :rules="rules.startdatetime"
            ref="startDateTime"
            @click="selectedStartDateTime"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            label="任务结束时间"
            v-model="task.endOn"
            readonly
            required
            :rules="rules.enddatetime"
            ref="endDateTime"
            @click="selectedEndDateTime"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            label="任务地块"
            v-model="landsname"
            readonly
            :title="landsname"
            @click="selectedLand"
          ) 
      v-layout(row, wrap)
        v-flex(xs12)
          div 任务审批人&nbsp;&nbsp;<span class="label">如果任务审批后才能分配到相关执行人，则请选择审批人，否则任务审批人可置空</span>
        v-flex.py-2(xs12)
          v-layout(row)
            v-flex(xs1 class="staff" v-if="approvalStaffsInfo && approvalStaffsInfo.id")
              div(@mouseenter="curAStaffId = approvalStaffs" @mouseleave="curAStaffId = null" :title="approvalStaffsInfo.name")
                v-avatar(size="50")
                  img(:src="approvalStaffsInfo.avatar || `/static/task/fuzenren.png`" @click="selectedApproveStaff")
                  div(v-show="curAStaffId" style="position: absolute; right: 0; top: -5px;" @click="removeAStaff")
                    v-avatar(class="grey" size="15px")
                      img(class="zengjia" :src="`static/sop/ic_close_white_24px.svg`")
            v-flex(xs1 v-else) 
              v-avatar(size="50")  
                img(class="zengjia" :src="`/static/task/zengjia.png`" @click="selectedApproveStaff") 
      .py-1            
      v-layout(row, wrap)
        v-flex(xs12)
          div 任务考核人&nbsp;&nbsp;<span class="label">如果任务完成后需要进行质量考核，则请选择考核人，否则任务考核人可置空</span>
        v-flex.py-2(xs12)
          v-layout(row)
            v-flex(xs1 class="staff" v-if="examineStaffsInfo && examineStaffsInfo.id")
              div(@mouseenter="curExStaffId = examineStaffs" @mouseleave="curExStaffId = null" :title="examineStaffsInfo.name")
                v-avatar(size="50")
                  img(class="zengjia" :src="examineStaffsInfo.avatar || `/static/task/fuzenren.png`" @click="selectedExamineStaff")
                  div(v-show="curExStaffId" style="position: absolute; right: 0; top: -5px;" @click="removeExStaff")
                    v-avatar(class="grey" size="15px")
                      img(class="zengjia" :src="`static/sop/ic_close_white_24px.svg`")
            v-flex(xs1 v-else)
              img(class="zengjia" :src="`/static/task/zengjia.png`" @click="selectedExamineStaff")      
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 完成
  v-selectedstaff(
    :visible.sync="selectedExcutedStaffDialog" 
    title="选择执行人" 
    :issearch="true" 
    :keystaffs.sync="keystaffs" 
    :staffs.sync="excutestaffs"
    searchlabel="请输入执行人姓名进行搜索"
    :stafflist = "staffsoption"
    :multiselected="true")  
  v-singleselectedstaff(
    :visible.sync="selectedApproveStaffDialog" 
    title="选择审批人" 
    :issearch="true" 
    :staffs.sync="approvalStaffs"
    searchlabel="请输入审批人姓名进行搜索"
    :stafflist = "staffsoption"
  )
  v-singleselectedstaff(
    :visible.sync="selectedExamineStaffDialog"
    title="选择考核人" 
    :issearch="true" 
    :staffs.sync="examineStaffs"
    searchlabel="请输入考核人姓名进行搜索"
    :stafflist = "staffsoption"
  )
  v-selectedland(
    :visible.sync="selectedLandDialog" 
    title="选择地块" 
    :issearch="true" 
    :lands.sync="lands"
    :multiselected="true"
    :landlist="landsoption"
  )
  v-datetimepicker(
    :visible.sync="startdatetimepicker"
    :datetime.sync="startdatetime"
    :isend="false"
  )
  v-datetimepicker(
    :visible.sync="enddatetimepicker"
    :datetime.sync="enddatetime"
    :isend="true"
  )
  v-layout(row, justify-center)
    v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
      div {{prompt.body}}
      template(slot="btnActions")
        v-divider
        v-card-actions
          v-spacer
          v-btn(flat, color="primary", @click.native.stop="back") 取消
          v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定           
</template>

<script>
import { CTask } from '@/models/task'
import VSelectedstaff from './StaffSelected.vue'
import VSingleselectedstaff from './StaffSelectedSingle.vue'
import VSelectedland from './LandSelected.vue'
import VDatetimepicker from './DateTimePickers.vue'
import mongo from '@vehiclecloud/mongo-rest-client'
import { parseTime } from '@/utils'
import { Toast } from '@/models/toast'
import { excuteApis } from '@/apis'
export default {
  name: 'v-farmtasksadd',
  props: {
    visible: {
      type: String,
      default: 'IS_NOE'
    },
    title: {
      type: String,
      default: ''
    },
    curtask: {
      type: Object,
      default: () => { }
    }
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_COPY': true,
      'IS_NONE': false
    }
    let task = new CTask()
    return {
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '保存成功',
        ok: true
      },
      addOrEditDialog: false,
      addOrEdit: ADD_OR_EDIT,
      addOrEditTitle: '创建任务',
      taskFormValid: true,
      task: task,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        content: [
          (v) => !!v || '必填项',
          (v) => v && v.length <= 500 || '不超过500个字符'
        ],
        startdatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetime()) || '任务开始时间不能大于任务结束时间'
        ],
        enddatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetime()) || '任务结束时间不能小于任务开始时间'
        ]
      },
      selectedExcutedStaffDialog: false, // 选择执行人弹窗
      keystaffs: '',
      excutestaffs: [],
      approvalStaffs: '',
      examineStaffs: '',
      selectedApproveStaffDialog: false, // 选择审批人弹窗
      selectedExamineStaffDialog: false, // 选择考核人弹窗
      selectedLandDialog: false, // 选择地块
      lands: [], // 已选地块
      user: null,
      startdatetimepicker: false, // 开始时间弹出框
      enddatetimepicker: false, // 结束时间弹出框
      startdatetime: null, // 任务开始时间
      enddatetime: null, // 任务结束时间
      stafflist: [], // 人员列表
      excutestaffslist: [],
      curEStaffId: null, // 当前聚焦的执行人
      approvalStaffsInfo: null, // 审批人信息
      curAStaffId: null, // 当前聚焦的审批人
      examineStaffsInfo: null, // 考核人信息
      curExStaffId: null, // 当前聚焦的考核
      landlist: [], // 地块列表
      datetimeErrors: false, // 时间错误提示信息
      isExcutionStaffValid: true, // 任务执行人必选标识
      isSaveClick: false, // 是否已经点击表单完成(保存)按钮
      tipscontent: '' // 提示信息
    }
  },
  computed: {
    landsname () {
      let name = ''
      if (this.lands.length > 0) {
        this.lands.map((land) => {
          let l = this._.find(this.landlist, { _id: { $oid: land } })
          name += l.name + '，'
        })
      }
      return name.substring(0, name.length - 1)
    },
    landsoption () {
      if (this.landlist.length > 0) {
        return this.landlist.filter(item => {
          // return item.hasOwnProperty('_removed') === false && (item.hasOwnProperty('tenant') && item['$oid'] === this.user['loginorgid'])
          return item.hasOwnProperty('_removed') === false
        })
      } else {
        return []
      }
    },
    staffsoption () {
      if (this.stafflist.length > 0) {
        return this.stafflist.filter(item => {
          return item.hasOwnProperty('_removed') === false
        })
      } else {
        return []
      }
    }
  },
  mounted () { },
  watch: {
    visible: function (v) {
      this.disposeModalDialog(v)
    },
    user: function (v) {
      if (!v) return
      this.queryUserInfoByTenants()
      this.queryLandInfoByTenants()
    },
    excutestaffs: function (v) {
      let es = []
      // 提取执行人信息
      for (let i = 0; i < v.length; i++) {
        es.push(this.getStaffInfoById(v[i]))
      }
      this.excutestaffslist = es
      this.isExcutionStaffValid = (this.excutestaffslist.length > 0 || !this.isSaveClick)
    },
    keystaffs: function (v) {
      if (!v || v === this.excutestaffslist[0].id) return
      // leader 易主
      let keystaffPos = this._.findIndex(this.excutestaffslist, { id: v })
      let cacheKeystaff = this.excutestaffslist[keystaffPos]
      this.excutestaffslist[keystaffPos] = this.excutestaffslist[0]
      this.excutestaffslist[0] = cacheKeystaff
    },
    approvalStaffs: function (v) {
      if (!v) return
      this.approvalStaffsInfo = this.getStaffInfoById(v)
    },
    examineStaffs: function (v) {
      if (!v) return
      this.examineStaffsInfo = this.getStaffInfoById(v)
    },
    enddatetime: function (v) {
      if (!v) return
      // v.substr(11, 5) === '00:00' ? (v = v.substr(0, 10) + ' 23:59') : v
      this.task.endOn = v
    },
    startdatetime: function (v) {
      this.task.startOn = v
    },
    datetimeErrors: function (v) {
      // this.$refs['taskForm'].validate()
    }
  },
  methods: {
    save () {
      this.isSaveClick = true
      this.isExcutionStaffValid = this.excutestaffslist.length > 0
      if (this.$refs['taskForm'].validate() && this.isExcutionStaffValid) {
        this.task.setRepresentative(this.keystaffs)
        this.task.setParticipators(this.excutestaffslist)
        this.task.setFarmlands(this.lands)
        this.task.setApprover(this.approvalStaffsInfo)
        this.task.setExaminer(this.examineStaffsInfo)
        this.task.setCreator(this.user)
        this.task.setCreatedOn((new Date()).getTime())
        this.task.setTenant(this.user.loginorgid)
        this.bussinessLogic(this.startdatetime, this.enddatetime, this.approvalStaffsInfo)
        this.task.add().then(response => {
          if (response['_id']['$oid']) {
            // 存储task.trends, { upsert: true } 参数无效
            // let trendsfilter = Object.assign({}, { task: { $oid: response['_id']['$oid'] }, type: 'creation' })
            let trendsupdate = Object.assign({}, { task: { $oid: response['_id']['$oid'] }, type: 'creation', content: this.task.getContent(), creator: { $oid: this.task.getCreator().supportid } })
            // mongo.db().collection('task.trends').update({ filter: trendsfilter, update: { $set: trendsupdate }, upsert: true }).then(response => {
            //   this.$emit('update:visible', 'IS_NONE')
            // })
            mongo.db().collection('task.trends').insert(trendsupdate).then(response => {
              this.$emit('update:visible', 'IS_NONE')
            })
            // 消息推送
            // 如果有审批人
            if (this.task.getApprover()) {
              excuteApis(response['_id']['$oid'], global.constant.pusherApisUrls, 'task', 'approval').then(data => {
                console.log(data)
              })
            } else {
              // 如果没审批人
              excuteApis(response['_id']['$oid'], global.constant.pusherApisUrls, 'task', 'execution').then(data => {
                console.log(data)
              })
            }
          } else {
            this.prompt.dialog = true
            this.prompt.title = '错误提示信息'
            this.prompt.body = '保存失败'
            this.prompt.ok = false
          }
        })
      }
    },
    cancel () {
      this.$emit('update:visible', 'IS_NONE')
    },
    selectedExcuteStaff () {
      if (this.stafflist.length > 0) {
        this.selectedExcutedStaffDialog = true
      } else {
        this.tipscontent = '暂无可选人员'
        this.toast.close()
        this.toast.setContent(this.tipscontent)
        this.toast.showAutoClose()
      }
    },
    selectedApproveStaff () {
      if (this.stafflist.length > 0) {
        this.selectedApproveStaffDialog = true
      } else {
        this.tipscontent = '暂无可选人员'
        this.toast.close()
        this.toast.setContent(this.tipscontent)
        this.toast.showAutoClose()
      }
    },
    selectedExamineStaff () {
      if (this.stafflist.length > 0) {
        this.selectedExamineStaffDialog = true
      } else {
        this.tipscontent = '暂无可选人员'
        this.toast.close()
        this.toast.setContent(this.tipscontent)
        this.toast.showAutoClose()
      }
    },
    selectedLand () {
      if (this.landlist.length > 0) {
        this.selectedLandDialog = true
      } else {
        this.tipscontent = '暂无可选地块'
        this.toast.close()
        this.toast.setContent(this.tipscontent)
        this.toast.showAutoClose()
      }
    },
    selectedStartDateTime () {
      this.startdatetime = this.task.startOn
      this.startdatetimepicker = true
    },
    selectedEndDateTime () {
      this.enddatetime = this.task.endOn
      this.enddatetimepicker = true
    },
    async queryUserInfoByTenants () {
      // 含部门下的人员
      let match = { _id: { $oid: this.user.loginorgid } }
      let graphLookup = { from: 'sys.tenant', startWith: '$_id', connectFromField: '_id', connectToField: 'parent', as: 'children' }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [{ $match: match }, { $graphLookup: graphLookup }, { $unwind: unwind }, { $project: project }]
      // 查询当前组织下所有部门
      let allDep = await mongo.db('iss').collection('sys.tenant').aggregate(aggregate)
      let allTenantId = allDep.map((dep) => {
        return dep._id
      })
      allTenantId.push({ $oid: this.user.loginorgid })
      // 查询当前组织及其下所有部门下的人员
      let filter = {}
      filter.tenants = { $in: allTenantId }
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let allUser = await mongo.db('iss').collection('tenant.staff').aggregate([{ $match: filter }])
      this.stafflist = allUser.map((item) => {
        return Object.assign(item, { id: item['_id'].$oid, name: item['name'] || item['mobile'] })
      })
      // let filter = {}
      // let orgid = [].concat({ $oid: this.user.loginorgid })
      // filter.tenants = Object.assign({}, { $in: orgid })
      // filter._removed = { '$exists': false }
      // let itemCount = await mongo.db('iss').collection('tenant.staff').count({ filter: filter })
      // let { items } = await mongo.db('iss').collection('tenant.staff').find({ filter: filter, limit: itemCount })
      // this.stafflist = items.map((item) => {
      //   return Object.assign(item, { id: item['_id'].$oid, name: item['name'] || item['mobile'] })
      // })
    },
    getStaffInfoById (staffId) {
      if (!staffId) return
      let staff = this._.find(this.stafflist, (staff) => {
        return staff.id === staffId && !staff.hasOwnProperty('_removed')
      })
      return staff ? Object.assign({}, { id: staff.id, name: staff.name, avatar: staff.avatar }) : null
    },
    getSelectedEStaff (index) {
      this.curEStaffId = this.excutestaffslist[index].id
    },
    removeEStaff (index) {
      let curEStaffId = this.excutestaffslist[index].id
      let pos = this._.indexOf(this.excutestaffs, curEStaffId)
      this.excutestaffs.splice(pos, 1)
      // 如果移除的是leader则另寻leader(如无人可选则置空)
      if (curEStaffId === this.keystaffs) {
        this.excutestaffs.length > 0 ? this.keystaffs = this.excutestaffs[0] : this.keystaffs = null
      }
    },
    removeAStaff () {
      this.approvalStaffsInfo = null
      this.approvalStaffs = null
    },
    removeExStaff () {
      this.examineStaffsInfo = null
      this.examineStaffs = null
    },
    async queryLandInfoByTenants () {
      // console.log(this.user.loginorgid)
      let match = { _id: { $oid: this.user.loginorgid } }
      let graphLookup = { from: 'sys.tenant', startWith: '$_id', connectFromField: '_id', connectToField: 'parent', as: 'children' }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [{ $match: match }, { $graphLookup: graphLookup }, { $unwind: unwind }, { $project: project }]
      // 查询当前组织下所有部门
      let allDep = await mongo.db('iss').collection('sys.tenant').aggregate(aggregate)
      let allTenantId = allDep.map((dep) => {
        return dep._id
      })
      allTenantId.push({ $oid: this.user.loginorgid })
      // 查询当前组织及其下所有部门下的地块
      let filter = {}
      filter.tenant = { $in: allTenantId }
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let projection = { code: 1, name: 1, _removed: 1, tenant: 1 }
      let items = await mongo.db('iss').collection('tenant.farmland').aggregate([{ $match: filter }, { $project: projection }])
      items.map(item => {
        let name = item['name'] + '（' + item['code'] + '）'
        Object.assign(item, { name: name })
      })
      this.landlist = items
      // let filter = {}
      // filter.tenant = Object.assign({}, { $oid: this.user.loginorgid })
      // filter._removed = { '$exists': false }
      // let itemCount = await mongo.db('iss').collection('tenant.farmland').count({ filter: filter })
      // let { items } = await mongo.db('iss').collection('tenant.farmland').find({ filter: filter, projection: { name: 1, code: 1 }, limit: itemCount })
      // items.map(item => {
      //   let name = item['name'] + '（' + item['code'] + '）'
      //   Object.assign(item, { name: name })
      // })
      // this.landlist = items
    },
    invalidDatetime: function () {
      let start = new Date(this.task.startOn || '1970-1-1')
      let end = new Date(this.task.endOn || '2118-1-1')
      let bool = start.getTime() <= end.getTime()
      this.datetimeErrors = bool
      return bool
    },
    bussinessLogic (sdatetime, edatetime, approver) {
      if (approver && approver.id) { // 进入审批环节
        this.task.setStage('approval')
        this.task.setFinish(false)
      } else {
        this.task.setStage('execution')
        this.task.setFinish(false)
      }
    },
    async queryTaskInfoByTaskId () {
      if (!this.curtask) return
      let taskid = this.curtask['id']
      let filter = {}
      filter._id = Object.assign({}, { $oid: taskid })
      filter._removed = { '$exists': false }
      let { items } = await mongo.db().collection('task').find({ filter: filter })
      return items
    },
    async disposeModalDialog (v) {
      // 刷新人员及地块列表
      // this.queryLandInfoByTenants()
      await this.queryUserInfoByTenants()
      this.isSaveClick = false
      this.addOrEditDialog = this.addOrEdit[v]
      if (v === 'IS_ADD') {
        this.addOrEditTitle = '新建任务'
        // this.$nextTick(() => {
        this.isExcutionStaffValid = true
        this.excutestaffs = []
        this.keystaffs = ''
        this.task.content = null
        this.task.startOn = null
        this.task.endOn = null
        this.lands = []
        this.approvalStaffs = null
        this.approvalStaffsInfo = null
        this.examineStaffs = null
        this.examineStaffsInfo = null
        // })
      } else if (v === 'IS_COPY') {
        this.addOrEditTitle = '复制任务'
        // 根据被复制任务ID查询任务相关信息
        this.queryTaskInfoByTaskId().then(tasklist => {
          let task = tasklist[0]
          this.task.content = task['content']
          let sdate = new Date(Number(task['startOn']['$numberLong']))
          this.task.startOn = parseTime(sdate, 'yyyy-MM-dd hh:mm')
          let edate = new Date(Number(task['endOn']['$numberLong']))
          this.task.endOn = parseTime(edate, 'yyyy-MM-dd hh:mm')
          this.excutestaffs = []
          this.excutestaffslist = []
          task['participators'].map((participator) => {
            let staffId = participator['$oid']
            let staffInfo = this.getStaffInfoById(staffId)
            staffInfo ? this.excutestaffs.push(staffId) : null
            staffInfo ? this.excutestaffslist.push(staffInfo) : null
          })
          let representative = this.getStaffInfoById(task['representative']['$oid'])
          if (representative) {
            this.keystaffs = task['representative']['$oid']
          } else {
            if (this.excutestaffs.length > 0) {
              this.keystaffs = this.excutestaffs[0]
            } else {
              this.keystaffs = null
            }
          }
          this.lands = []
          if (task['farmlands']) {
            task['farmlands'].map((farmland) => {
              // 检测地块是否在列表中(预防地块权限迁移)，并剔除非法地块
              let curland = this._.find(this.landlist, { _id: { $oid: farmland['$oid'] } })
              curland && !curland.hasOwnProperty('_removed') ? this.lands.push(farmland['$oid']) : null
            })
          }
          task['approver'] ? (this.approvalStaffs = task['approver']['$oid']) : (this.approvalStaffs = null)
          this.approvalStaffs ? (this.approvalStaffsInfo = this.getStaffInfoById(this.approvalStaffs)) : (this.approvalStaffsInfo = null)
          task['examiner'] ? (this.examineStaffs = task['examiner']['$oid']) : (this.examineStaffs = null)
          this.examineStaffs ? (this.examineStaffsInfo = this.getStaffInfoById(this.examineStaffs)) : this.examineStaffsInfo = null
        })
      } else {
        this.addOrEditTitle = ''
      }
    }
  },
  created () {
    this.user = global.helper.ls.get('user')
    this.toast = new Toast(this, null, null)
  },
  components: {
    VSelectedstaff,
    VSingleselectedstaff,
    VSelectedland,
    VDatetimepicker
  }
}
</script>
<style scoped>
.zengjia {
  cursor: pointer;
}
.staff {
  width: 130px;
}
.label {
  color: rgba(0, 0, 0, 0.47);
}
.delstaff {
  cursor: pointer;
}
.isValid {
  color: rgba(0, 0, 0, 0.47);
}
.isInvalid {
  color: #b71c1c !important;
}
.isValidBorder {
  border-top: 1px solid rgba(0, 0, 0, 0.47);
}
.isInvalidBorder {
  border-top: 1px solid #b71c1c;
}
</style>
