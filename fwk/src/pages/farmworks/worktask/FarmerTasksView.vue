<template lang="pug">
div()
  v-modaldialog(
    :isShow.sync="taskViewDialog",
    :title="content||taskViewTitle",
    :modalMaxWidth="800",
    :bodyHeight="600",
    :showCloseBtn="true")
    template(slot="toolbarItem")
      div(style="color: rgb(50, 199, 135);" v-if="!statelabel") {{ taskstatus ? '（状态：' + statename + '）' : null }}        
    div(class="titlelabel" v-if="statelabel")
      img(:src="statelabel" width="94" height="78")
    div()
      v-layout.pb-1(row)
        v-flex(xs8)
          v-layout(row)
            v-flex(xs12 class="taskinfo") 任务创建人：{{creator && (creator['name'] || creator['mobile']) ? creator['name'] || creator['mobile'] : null}}
          v-layout.pb-1(row)
            v-flex(xs12 class="taskinfo hiddentips") 任务执行人：<span :title="participatornames.join('，')">{{participatornames[0]}}<span v-show="participatornames.length > 0" style="color: rgb(50, 199, 135);">（负责人）</span><span v-show="participatornames.length > 1">，{{participatornames.slice(1,).join('，')}}</span></span>
          v-layout.pb-1(row class="taskinfo")
            v-flex(xs12) 任务时间：{{ startTime }} 至 {{ endTime }}
          v-layout.pb-1(row, nowrap)
            v-flex(xs12 class="taskinfo hiddentips") 任务地块：<span :title="farmlands || '--'"> {{ farmlands || '--' }} </span>
        v-flex(xs4)
          v-layout(row)
            v-flex(xs12 text-xs-right)
              v-btn(color="primary" v-if="authBtn(task, seldo, 'finish')" @click.native="toDo('finish')") 中止任务
              v-btn(color="primary" v-if="authBtn(task, seldo, 'accept')" @click.native="toDo('accept')") 审批通过
              v-btn(v-if="authBtn(task, seldo, 'reject')" @click.native="toDo('reject')") 审批不通过
              v-btn(color="primary" v-if="authBtn(task, seldo, 'done')" @click.native="toDo('done')") 完成任务
              v-btn(color="primary" v-if="authBtn(task, seldo, 'check')" @click.native="toDo('check')") 考核任务   
    div.mt-1()
      v-divider
    div
      v-taskprocessing(:tasknodes='tasknodes')
    template(slot="btnActions")
      v-divider
      div.ml-2.mr-2(class="messagearea")
        v-layout(row, wrap)
          v-flex(xs12)
            v-textarea.pt-0(
              class="textareaborder"
              placeholder="说点什么（500字内）"
              v-model="trend.content"
              rows="1"
              auto-grow
              hide-details
              @focus="focusEvent"
              maxlength="500")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            div(row v-for="(item, index) in trend.attachments" :key="item.resourcepath")
              div(class="attach" @mouseenter="getSelectedAttach(index)" @mouseleave="leaveSelectedAttach")
                img(:src="getIcon(item.resourcename)" width="auto" height="20" style="vertical-align: middle; margin-right: 10px;")
                a(:href="item.resourcepath" target="_blank") {{ item.resourcename }}&nbsp;&nbsp;
                v-btn(title="删除" fab small icon @click="removeResourece(item)" v-if="selectedAttach === item.resourcepath")
                  v-icon(color="red darken-2") delete    
        v-layout(row v-if="isediting")
          v-flex(xs6)
            v-minidropzoneupload(:files.sync="trend.attachments")
          //- v-flex(xs6)   
          v-flex(xs6 class="sendarea")
            div(style="float: right")
              v-btn(flat @click.native="sendcancel" small) 取消
              v-btn(flat color="primary" @click.native="sendok" small) 发送
        v-layout(row, justify-center)
          v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
            div {{prompt.body}}
            template(slot="btnActions")
              v-divider
              v-card-actions
                v-spacer
                v-btn(flat, color="primary", @click.native.stop="back") 取消
                v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定      
        v-farmertaskstodo(:visible.sync="todoDialog" :curtask="task")
        v-farmertasksexamination(:visible.sync="examinationTaskDialog"
                              title="任务考核"
                              :curtask="task"
                              :refreshFn="disposeDialog")
</template>

<script>
import VFarmertaskstodo from './FarmerTasksToDo.vue'
import VTaskprocessing from './TaskProcessing.vue'
import { CTask } from '@/models/task.js'
import { CTasknode } from '@/models/tasknode.js'
import Task from './Task.js'
import Tasknode from './Tasknode.js'
import { parseTime, getIconPath } from '@/utils'
import VMinidropzoneupload from './DropzoneUpload.vue'
import mongo from '@vehiclecloud/mongo-rest-client'
import { Toast } from '@/models/toast'
import VFarmertasksexamination from './FarmerTasksExamination.vue'

export default {
  name: 'v-farmertasksview',
  mixins: [Task, Tasknode],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    taskid: {
      type: String,
      default: ''
    },
    // taskstatus: {
    //   type: String,
    //   default: ''
    // },
    seldo: {
      type: String,
      default: ''
    }
  },
  data () {
    let task = new CTask()
    const TODOS = {
      finish: 'IS_FINISH',
      accept: 'IS_ACCEPT',
      reject: 'IS_REJECT',
      done: 'IS_DONE',
      check: 'IS_CHECK'
    }
    return {
      todos: TODOS,
      todoDialog: 'IS_NONE',
      taskViewTitle: '',
      taskViewDialog: false,
      task: task,
      // taskstages: [],
      tasknodes: [],
      trend: {
        content: '',
        attachments: []
      },
      showattachment: false,
      selectedAttach: null,
      selectedItem: null,
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      taskstatus: null,
      statename: null,
      statelabel: null,
      examinationTaskDialog: false,
      isediting: false
    }
  },
  computed: {
    creator () {
      let creator = this.task.getCreator()
      return creator
    },
    participatornames () {
      let participators = this.task.getParticipators()
      let representative = this.task.getRepresentative()
      let names = []
      if (participators && participators.length > 0) {
        participators.map((participator, index) => {
          if (participator['_id']['$oid'] !== representative['$oid']) {
            names.push(participator.name || participator.mobile)
          } else {
            names.unshift(participator.name || participator.mobile)
          }
        })
      }
      return names
    },
    content () {
      let content = this.task.getContent()
      return content
    },
    startTime () {
      let start = this.task.getStartOn()
      // console.log(start)
      let startDateTime = parseTime((new Date(Number(start))), 'yyyy-MM-dd hh:mm')
      return startDateTime
    },
    endTime () {
      let end = this.task.getEndOn()
      // console.log(end)
      let endDateTime = parseTime((new Date(Number(end))), 'yyyy-MM-dd hh:mm')
      return endDateTime
    },
    farmlands () {
      let ls = this.task.getFarmlands()
      if (!ls || ls.length === 0) return ''
      let lands = []
      ls.map((item, index) => {
        let lsname = item.name + '（' + item.code + '）'
        lands.push(lsname)
      })
      return lands.join('，')
    },
    taskbusyness () {
      let creator = this.task.getCreator()
      let participators = this.task.getParticipators()
      if (creator && participators) {
        this.taskBusynessLine()
        this.hasnodes = true
        return this.tasknodes
      } else {
        return []
      }
    }
    // ,
    // isediting () {
    //   return this.trend.content.length > 0 || this.trend.attachments.length > 0
    // }
  },
  mounted () { },
  watch: {
    taskViewDialog: function (v) {
      this.isediting = false
      this.trend.attachments = []
      this.trend.content = ''
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      v ? this.disposeDialog() : null
      this.taskViewDialog = v
    },
    title: function (v) {
      this.taskViewTitle = v
    },
    todoDialog: function (v) {
      v === 'IS_NONE' ? this.disposeDialog() : null
    }
  },
  methods: {
    focusEvent () {
      this.isediting = true
    },
    toDo (doname) {
      console.log(doname)
      if (doname === 'check') {
        this.examinationTaskDialog = true
      } else {
        this.todoDialog = this.todos[doname]
      }
    },
    getIcon (rescourcename) {
      return getIconPath(rescourcename)
    },
    save () {
      this.taskViewDialog = false
    },
    cancel () {
      this.taskViewDialog = false
    },
    disposeDialog () {
      // 根据任务ID获取任务详情
      if (!this.taskid) return
      this.task.setId(this.taskid)
      this.queryTaskInfoById(this.taskid).then(tasklist => {
        let task = tasklist[0]
        this.task.setContent(task.content)
        let cusers = this._.find(this.staffs, { id: task['creator']['$oid'] })
        this.task.setCreator(cusers)
        this.task.setCreatedOn(task['createdOn']['$numberLong'])
        let participators = []
        task['participators'].map(pid => {
          let pusers = this._.find(this.staffs, { id: pid['$oid'] })
          participators.push(pusers)
        })
        this.task.setParticipators(participators)
        // 查询任务开始时间、结束时间
        this.task.setStartOn(task['startOn']['$numberLong'])
        this.task.setEndOn(task['endOn']['$numberLong'])
        // 查询任务地块信息
        let farmlands = []
        this.queryLandInfoByIds(task['farmlands']).then(lands => {
          lands.map(land => {
            farmlands.push(land)
          })
          this.task.setFarmlands(farmlands)
        })
        // 查询任务负责人信息
        this.task.setRepresentative(task['representative'])
        // 设置任务当前所处阶段
        this.task.setStage(task['stage'])
        // 设置任务完成标记
        this.task.setFinish(task['finish'])
        // 设置任务审批人
        this.task.setApprover(task['approver'])
        // 设置任务审批状态
        this.task.setApproval(task['approval'])
        // 设置任务考核人
        this.task.setExaminer(task['examiner'])
        // 设置任务考核结果
        this.task.setExamination(task['examination'])
        // 设置任务审批时间
        this.task.setApprovalOn(task['approvalOn'] ? task['approvalOn']['$numberLong'] : null)
        // 设置任务审批内容
        this.task.setApprovalContent(task['approvalContent'])
        // 设置任务完成时间
        this.task.setFinishOn(task['finishOn'] ? task['finishOn']['$numberLong'] : null)
        // 设置完成任务内容
        this.task.setFinishContent(task['finishContent'])
        // 设置任务考核时间
        this.task.setExaminationOn(task['examinationOn'] ? task['examinationOn']['$numberLong'] : null)
        // 设置完成考核任务内容
        this.task.setExaminationContent(task['examinationContent'])
        // 获取任务状态信息
        this.convertStatus()
        // 获取任务动态时间轴
        this.taskBusynessLine()
      })
    },
    async taskBusynessLine () {
      let tasknodes = []
      let tasknode = new CTasknode()
      // 任务创建
      // tasknode.setNodeperson(this.task.getCreator())
      // tasknode.setNodetime(this.task.getCreatedOn())
      // tasknode.setNodesubtitle('创建任务')
      // tasknode.setNodeindex(1)
      // tasknode.setMultilinecontent(false)
      // // 含副标题，创建任务
      // tasknodes.push(tasknode)
      // 任务审批
      // let contents = null
      // if (this.task.getApprovalOn()) {
      //   tasknode = new CTasknode()
      //   contents = this.task.getApprovalContent()
      //   let ausers = this._.find(this.staffs, { id: (this.task.getApprover())['$oid'] })
      //   tasknode.setNodeperson(ausers)
      //   tasknode.setNodetime(this.task.getApprovalOn())
      //   tasknode.setNodecontent(contents ? contents['content'] : null)
      //   tasknode.setNodeaudios(contents ? contents['audios'] : null)
      //   tasknode.setNodeimages(contents ? contents['images'] : null)
      //   tasknode.setNodevideos(contents ? contents['videos'] : null)
      //   tasknode.setNodelocation(contents ? contents['location'] : null)
      //   tasknode.setNodeattachments(contents ? contents['attachments'] : null)
      //   let check = this.task.getApproval()
      //   tasknode.setNodesubtitle(check === 'accepted' ? '审批通过' : '审批不通过')
      //   tasknode.setNodeindex(2)
      //   tasknode.setMultilinecontent((contents && contents['content']) || (contents && contents['audios']) || (contents && contents['images']) || (contents && contents['videos']) || (contents && contents['location']) || (contents && contents['attachments']))
      //   tasknodes.push(tasknode)
      // }
      // // 任务完成(含正常完成、人为中止)
      // if (this.task.getFinishOn()) {
      //   tasknode = new CTasknode()
      //   let stage = this.task.getStage()
      //   if (stage === 'completion') {
      //     let rusers = this._.find(this.staffs, { id: (this.task.getRepresentative())['$oid'] })
      //     tasknode.setNodeperson(rusers)
      //     tasknode.setNodesubtitle('完成任务')
      //   } else {
      //     tasknode.setNodeperson(this.task.getCreator())
      //     tasknode.setNodesubtitle('中止任务')
      //   }
      //   contents = this.task.getFinishContent()
      //   tasknode.setNodetime(this.task.getFinishOn())
      //   tasknode.setNodecontent(contents ? contents['content'] : null)
      //   tasknode.setNodeaudios(contents ? contents['audios'] : null)
      //   tasknode.setNodeimages(contents ? contents['images'] : null)
      //   tasknode.setNodevideos(contents ? contents['videos'] : null)
      //   tasknode.setNodelocation(contents ? contents['location'] : null)
      //   tasknode.setNodeattachments(contents ? contents['attachments'] : null)
      //   tasknode.setNodeindex(3)
      //   tasknode.setMultilinecontent((contents && contents['content']) || (contents && contents['audios']) || (contents && contents['images']) || (contents && contents['videos']) || (contents && contents['location']) || (contents && contents['attachments']))
      //   tasknodes.push(tasknode)
      // }
      // // 任务考核
      // if (this.task.getExaminationOn()) {
      //   tasknode = new CTasknode()
      //   contents = this.task.getExaminationContent()
      //   let eusers = this._.find(this.staffs, { id: (this.task.getExaminer())['$oid'] })
      //   tasknode.setNodeperson(eusers)
      //   tasknode.setNodetime(this.task.getExaminationOn())
      //   tasknode.setNodecontent(contents ? contents['content'] : null)
      //   tasknode.setNodeaudios(contents ? contents['audios'] : null)
      //   tasknode.setNodeimages(contents ? contents['images'] : null)
      //   tasknode.setNodevideos(contents ? contents['videos'] : null)
      //   tasknode.setNodelocation(contents ? contents['location'] : null)
      //   tasknode.setNodeattachments(contents ? contents['attachments'] : null)
      //   tasknode.setNodesubtitle('考核了任务')
      //   tasknode.setNodeindex(4)
      //   tasknode.setMultilinecontent((contents && contents['content']) || (contents && contents['audios']) || (contents && contents['images']) || (contents && contents['videos']) || (contents && contents['location']) || (contents && contents['attachments']))
      //   tasknodes.push(tasknode)
      // }
      // 互动
      let trends = await this.getTasktrendsByTaskid(this.task.getId())
      trends.map(trend => {
        tasknode = new CTasknode()
        let tusers = this._.find(this.staffs, { id: trend['creator']['$oid'] })
        tasknode.setNodeperson(tusers)
        tasknode.setNodetime(trend['createdOn']['$numberLong'])
        tasknode.setNodetype(trend['type'])
        let typenotehtml = this.getTypeNoteTypeHtml(trend['type'], trend['approval'] || trend['evaluation'] || '')
        tasknode.setNodetypenote(typenotehtml)
        tasknode.setNodecontent(trend['content'])
        tasknode.setNodeaudios(trend['audios'])
        tasknode.setNodeimages(trend['images'])
        tasknode.setNodevideos(trend['videos'])
        tasknode.setNodelocation(trend['location'])
        tasknode.setNodeattachments(trend['attachments'])
        let subtitle = this.getNodesubtitle(trend['type'], trend['approval'] || trend['evaluation'] || '')
        tasknode.setNodesubtitle(subtitle)
        tasknode.setNodeindex(0)
        tasknode.setMultilinecontent((trend && trend['content']) || (trend && trend['audios']) || (trend && trend['images']) || (trend && trend['videos']) || (trend && trend['location']) || trend['attachments'])
        tasknodes.push(tasknode)
      })
      // 倒序
      this.tasknodes = this._.sortBy(tasknodes, [(o) => { return o.getNodetime() }])
      this._.reverse(this.tasknodes)
    },
    getTypeNoteTypeHtml (type, typenote) {
      let note = ''
      switch (type) {
        case 'creation':
          note = '创建任务'
          break
        case 'approval':
          if (typenote === 'accepted') {
            note = '审批不通过'
          } else if (typenote === 'rejected') {
            note = '审批通过'
          } else {
            note = ''
          }
          break
        case 'suspension':
          note = '中止任务'
          break
        case 'completion':
          note = '完成任务'
          break
        case 'examination':
          let star = Number(typenote)
          let i = 0
          let stardom = []
          while (i < 5) {
            let domhongxing = '<span><img src="/static/task/hongxing.png" width="15" height="15" style="margin-right: 5px;"/></span>'
            let dombaixing = '<span><img src="/static/task/baixing.png" width="15" height="15" style="margin-right: 5px;" /></span>'
            stardom.push(i + 1 <= star ? domhongxing : dombaixing)
            i += 1
          }
          let label = ''
          if (star === 5) {
            label = '<span>好</span>'
          } else if (star >= 3 && star < 5) {
            label = '<span>中</span>'
          } else {
            label = '<span>差</span>'
          }
          stardom.push(label)
          note = stardom.join('')
          break
        case 'trends':
          note = ''
          break
      }
      return note
    },
    getNodesubtitle (type, typenote) {
      let note = ''
      switch (type) {
        case 'creation':
          note = '创建任务'
          break
        case 'approval':
          if (typenote === 'accepted') {
            note = '审批通过'
          } else if (typenote === 'rejected') {
            note = '审批不通过'
          } else {
            note = ''
          }
          break
        case 'suspension':
          note = '中止任务'
          break
        case 'completion':
          note = '完成任务'
          break
        case 'examination':
          note = '考核了任务'
          break
        case 'trends':
          note = ''
          break
      }
      return note
    },
    getCurtaskState (stage, finish) {
      // let state = ''
      // this.taskstages = []
      // this.taskstages.push('CREATE') // 初始化第一阶段为创建阶段
      // if (stage === 'approval') {
      //   // 审批阶段
      //   let approval = this.task.getApproval()
      //   finish ? (approval === 'rejected' ? (state = 'APPROVE_REJECTED') : (state = 'FINISHED')) : (state = 'UNAPPROVE')
      //   this.taskstages.push(state)
      // } else if (stage === 'execution') {
      //   // 执行阶段
      //   let stime = this.task.getStartOn()
      //   let etime = this.task.getEndOn()
      //   let now = (new Date()).getTime()
      //   finish ? (state = 'FINISHED') : (stime > now ? (state = 'READIED') : (etime < now ? (state = 'OVERDUE') : (state = 'UNDER_WAY')))
      //   if (this.task.getApproval()) {
      //     this.taskstages.push('APPROVAL')
      //     this.taskstages.push(state)
      //   } else {
      //     this.taskstages.push(state)
      //   }
      // } else if (stage === 'completion') {
      //   // 完成阶段
      //   let examination = this.task.getExamination()
      //   let examiner = this.task.getExaminer()
      //   examiner ? (examination ? (state = 'CHECKED') : (state = 'UNCHECKED')) : (state = 'DONE')
      //   if (this.task.getApproval()) {
      //     this.taskstages.push('APPROVAL')
      //     this.taskstages.push('EXECUTION')
      //     this.taskstages.push(state)
      //   } else {
      //     this.taskstages.push('EXECUTION')
      //     this.taskstages.push(state)
      //   }
      // }
      // return state
    },
    sendcancel () {
      this.trend.content = ''
      this.trend.attachments = []
      this.isediting = false
    },
    getSelectedAttach (index) {
      this.selectedAttach = this.trend.attachments[index].resourcepath
    },
    leaveSelectedAttach () {
      this.selectedAttach = null
    },
    removeResourece (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItem = item
    },
    Ok () {
      // this._.remove(this.trend.attachments, data => {
      //   return data.resourcename === this.selectedItem.resourcename
      // })
      let index = this._.findIndex(this.trend.attachments, data => {
        return data.resourcename === this.selectedItem.resourcename
      })
      this.trend.attachments.splice(index, 1)
      this.prompt.dialog = false
    },
    back () {
      this.prompt.dialog = false
    },
    sendok () {
      // 发送互动内容
      if (!this.taskid) return
      let tipscontent = ''
      if (this.trend.content.length > 0 || this.trend.attachments.length > 0) {
        let trend = {}
        Object.assign(trend, { task: { $oid: this.taskid } })
        Object.assign(trend, { creator: { $oid: this.user.supportid } })
        Object.assign(trend, { type: 'trends' })
        Object.assign(trend, { content: this.trend.content })
        let attachments = []
        this.trend.attachments.map(attachment => {
          attachments.push(Object.assign({}, {
            name: attachment.resourcename,
            url: attachment.resourcepath
          }))
        })
        attachments.length > 0 ? (Object.assign(trend, { attachments: attachments })) : null
        try {
          mongo.db().collection('task.trends').insert(trend).then(res => {
            let id = res['_id']['$oid']
            id ? this.disposeDialog() : null
            if (id) {
              this.isediting = false
              this.trend.attachments = []
              this.trend.content = ''
            }
          })
        } catch (err) {
          tipscontent = '互动内容发送失败'
          this.toast.close()
          this.toast.setContent(tipscontent)
          this.toast.showAutoClose()
        }
        // let tipscontent = '该文件已上传，请重新选择'
        //   self.toast.close()
        //   self.toast.setContent(tipscontent)
        //   self.toast.showAutoClose()
      } else {
        tipscontent = '所发送的互动内容不能为空'
        this.toast.close()
        this.toast.setContent(tipscontent)
        this.toast.showAutoClose()
      }
    },
    authBtn (task, seldo, btnname) {
      if (!this.taskstatus) return false
      // 定义不同tab可操作按钮集
      let btns = {
        selexecution: ['done'],
        selcreate: ['finish'],
        selapproval: ['accept', 'reject'],
        selcheck: ['check']
      }
      let taskstate = global.constant['TASK_STATE']
      let taskstages = global.constant['TASK_STAGES']
      let taskstatename = taskstate[this.taskstatus]['name']
      let todos = taskstages[taskstatename]['TODO']
      // 根据任务状态及可操作按钮集求交集
      let intersection = todos.filter(v => btns[seldo].includes(v))
      // 操作者宿主权限（即是否与操作者适配）
      // let isparticipator = (this._.filter(task.getParticipators(), { '$oid': this.user.supportid })).length > 0
      let isrepresentative = task.getRepresentative() && ((task.getRepresentative())['$oid'] === this.user.supportid)
      let isapprover = task.getApprover() && ((task.getApprover())['$oid'] === this.user.supportid)
      let isexaminer = task.getExaminer() && ((task.getExaminer())['$oid'] === this.user.supportid)
      let isowner = ((seldo === 'selexecution') && isrepresentative) || (seldo === 'selcreate') ||
        ((seldo === 'selapproval') && isapprover) || ((seldo === 'selcheck') && isexaminer)
      // 操作者可操作权限(由系统分配，统一写在dom中)
      return intersection.includes(btnname) && isowner
    },
    // 转换状态
    // convertStatus () {
    //   let curTime = (new Date()).getTime()
    //   let startOn = this.task.getStartOn()
    //   let endOn = this.task.getEndOn()
    //   let isHigherStart = curTime > startOn
    //   let isHigherEnd = curTime > endOn
    //   let finish = this.task.getFinish()
    //   let stage = this.task.getStage()
    //   // let examiner = this.task.getExaminer()
    //   let examination = this.task.getExamination()
    //   // let approver = this.task.getApprover()
    //   let approval = this.task.getApproval()
    //   let statusname = '未知状态'
    //   switch (this.seldo) {
    //     case 'selexecution':
    //       // 任务完成的情况
    //       statusname = (stage === 'completion') ? (examination ? '已考核' : '已完成')
    //         : (finish ? '已中止' : (isHigherStart ? (isHigherEnd ? '已逾期' : '进行中') : '即将开始'))
    //       this.taskstatus = statusname
    //       break
    //     case 'selcreate':
    //       statusname = (stage === 'completion') ? (examination ? '已考核' : '已完成')
    //         : (finish ? (stage === 'approval' ? '审批不通过' : '已中止') : (stage === 'approval' ? '待审批' : (isHigherStart ? (isHigherEnd ? '已逾期' : '进行中') : '即将开始')))
    //       this.taskstatus = statusname
    //       break
    //     case 'selapproval':
    //       statusname = (approval === 'accepted') ? '审批通过' : (approval === 'rejected' ? '审批不通过' : '待审批')
    //       this.taskstatus = statusname
    //       break
    //     case 'selcheck':
    //       statusname = examination ? '已考核' : '待考核'
    //       this.taskstatus = statusname
    //       break
    //     default:
    //       this.taskstatus = '未知状态'
    //   }
    //   let taskstate = global.constant['TASK_STATE']
    //   let taskstages = global.constant['TASK_STAGES']
    //   let taskstatename = taskstate[this.taskstatus]['name']
    //   this.statename = taskstages[taskstatename]['NAME']
    //   this.statelabel = taskstages[taskstatename]['LABEL']
    // }
    convertStatus (task) {
      let curTime = (new Date()).getTime()
      let finish = this.task.getFinish()
      let stage = this.task.getStage()
      let examiner = this.task.getExaminer()
      let examination = this.task.getExamination()
      let startOn = this.task.getStartOn()
      let endOn = this.task.getEndOn()
      let approver = this.task.getApprover()
      let approval = this.task.getApproval()
      let statusname = '未知状态'
      switch (this.seldo) {
        case 'selexecution':
          // 任务完成的情况
          if (finish) {
            if (stage !== 'completion') {
              statusname = '已中止'
            } else {
              // 不存在考核人的情况
              if (!examiner) {
                statusname = '已完成'
              } else {
                if (examination === true) {
                  statusname = '已考核'
                } else {
                  // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                  statusname = '已完成'
                }
              }
            }
          } else { // 任务未完成的情况
            if (curTime < startOn) {
              statusname = '即将开始'
            }
            if (curTime > endOn) {
              statusname = '已逾期'
            }
            if (curTime >= startOn && curTime <= endOn) {
              statusname = '进行中'
            }
          }
          break
        case 'selcreate':
          // 存在审批人的情况
          if (approver) {
            // 不存在审批状态
            if (!approval) {
              if (finish) {
                statusname = '已中止'
              } else {
                statusname = '待审批'
              }
            } else {
              if (approval === 'rejected') {
                statusname = '审批不通过'
              } else {
                // 任务完成的情况
                if (finish) {
                  if (stage !== 'completion') {
                    statusname = '已中止'
                  } else {
                    if (!examiner) {
                      statusname = '已完成'
                    } else {
                      if (examination === true) {
                        statusname = '已考核'
                      } else {
                        // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                        statusname = '已完成'
                      }
                    }
                  }
                } else {
                  if (curTime < startOn) {
                    statusname = '即将开始'
                  }
                  if (curTime > endOn) {
                    statusname = '已逾期'
                  }
                  if (curTime >= startOn && curTime <= endOn) {
                    statusname = '进行中'
                  }
                }
              }
            }
          } else { // 不存在审批人的情况
            // 任务完成
            if (finish) {
              if (stage !== 'completion') {
                statusname = '已中止'
              } else {
                if (!examiner) {
                  statusname = '已完成'
                } else {
                  if (examination === true) {
                    statusname = '已考核'
                  } else {
                    // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                    statusname = '已完成'
                  }
                }
              }
            } else {  // 任务未完成
              if (curTime < startOn) {
                statusname = '即将开始'
              }
              if (curTime > endOn) {
                statusname = '已逾期'
              }
              if (curTime >= startOn && curTime <= endOn) {
                statusname = '进行中'
              }
            }
          }
          break
        case 'selapproval':
          // 不存在审批状态的情况
          if (!approval) {
            statusname = '待审批'
          } else { // 存在审批状态的情况分为：审批通过、审批不通过
            if (approval === 'accepted') {
              statusname = '审批通过'
            }
            if (approval === 'rejected') {
              statusname = '审批不通过'
            }
          }
          break
        case 'selcheck':
          if (finish === true && examination === true && stage === 'completion') {
            statusname = '已考核'
          }
          if (finish === true && !examination && stage === 'completion') {
            statusname = '待考核'
          }
          break
        default:
          break
      }
      this.taskstatus = statusname
      let taskstate = global.constant['TASK_STATE']
      let taskstages = global.constant['TASK_STAGES']
      let taskstatename = taskstate[this.taskstatus]['name']
      this.statename = taskstages[taskstatename]['NAME']
      this.statelabel = taskstages[taskstatename]['LABEL']
    }
  },
  created () {
    this.taskViewDialog = this.visible
    this.taskViewTitle = this.title
    this.toast = new Toast(this, null, null)
  },
  components: {
    VTaskprocessing,
    VMinidropzoneupload,
    VFarmertaskstodo,
    VFarmertasksexamination
  }
}
</script>
<style scoped>
.titlelabel {
  position: absolute;
  right: 60px;
  top: 20px;
  z-index: 1000;
}
.hiddentips {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  max-width: 600px;
}
.bottom {
  vertical-align: middle;
}
.messagearea {
  border: 1px solid rgb(245, 238, 238);
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 5px 5px;
  background-color: #f5f5f5;
  /* max-height: 300px; 
  overflow-y: auto;
  overflow-x: hidden; */
}
/* .messagearea::-webkit-scrollbar {
  background: transparent;
  width: 5px;
}
.messagearea::-webkit-scrollbar-thumb {
  background-color: #bdbdbd;
} */
.sendarea {
  height: 35px;
}
.attach {
  border: 1px solid rgb(216, 210, 210);
  margin-top: 5px;
  padding: 10px 10px;
  width: 773px;
}
.attach:hover {
  /* background: rgb(248, 245, 245); */
  cursor: pointer;
}
.delBtn {
  float: right;
}
.taskinfo {
  line-height: 28px;
}
</style>
