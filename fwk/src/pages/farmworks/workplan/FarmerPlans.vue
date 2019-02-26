<!--
      日历组件

      组件属性
        :show-date="showDate" 默认情况下显示的日期
        :disable-past="disablePast"
        :disable-future="disableFuture"
        :enable-drag-drop="true" 是否可以拖动
        locale
        startingDayOfWeek 每周开始的第一天 0-6 星期天开始
        month-name-format="numeric" 月份名称的格式
        weekday-name-format  周名称的格式
        showEventTimes 显示任务的开始时间 和结束时间
        displayPeriodUom 要显示的时间类型 year month week
        displayPeriodCount 显示的时间个数

      events 属性
        startDate: 事件开始于日历上的日期 日期对象 或 '2003-01-01 00:00:00'字符串格式
        endDate: 事件结束于日历上的日期。默认设置为和startDate相同的日期
        title: 日历上显示的事件的名称
        id: 事件的唯一标识符。默认设置为随机生成的字符串
        url: 与事件关联的url。如果提供了，单击该事件打开url。如果没有提供，则事件为unlinked
        classes: 一个字符串，其中包含任何额外的css类

      组件方法
        click-date(date)   点击日期
        click-event(event) 点击日期上面的事件
        drag-start(event)  当用户开始拖动事件时触发
        drag-enter-date(event, date) 当事件拖到日期时触发
        drag-leave-date(event, date) 当事件被拖出日期而不丢弃它时
        drag-over-date(event, date) ..

-->
<template>
  <v-container fluid class="containerWrapper">
    <v-card class="page-content">
      <v-layout row>
        <v-flex xs12>
          <calendar-view
            :events="events"
            :starting-day-of-week="1"
            :show-date="showDate"
            @show-date-change="setShowDate"
            @click-event="clickEvent"
            :display-period-uom="displayPeriodUom"
            @addPlan="addPlan"
            @cutMonth="cutMonth"
            @cutWeek="cutWeek"
            @cutYear="cutYear"
            @inputChange="inputChange"
            @currentDate="currentDate"
            @changeTime="changeTime"
          />
        </v-flex>
      </v-layout>
    </v-card>
    <!-- 添加、编辑农事计划弹出框 -->
    <v-dialog v-model="planDialog" width="700" persistent>
      <form>
        <v-card>
          <v-card-title>
            <span v-text="operate === 'add' ? '添加农事计划' : '编辑农事计划'"></span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <!-- * 计划内容 -->
              <v-layout row justify-center>
                <v-flex xs4 class="labelMargin">
                  <v-subheader>*&nbsp;计划内容</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    ref="describe"
                    v-model="describe"
                    label="请填写计划内容"
                    single-line
                    :rules="describeRules"
                    maxlength="100"
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>

              <!-- * 开始时间 -->
              <v-layout row justify-center>
                <v-flex xs4>
                  <v-subheader class="labelMargin">*&nbsp;开始时间</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-menu
                    lazy
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    full-width
                    :nudge-right="40"
                    max-width="290px"
                    min-width="290px"
                  >
                    <v-text-field
                      ref="begintime"
                      slot="activator"
                      label="请选择开始时间"
                      v-model="begintime"
                      append-icon="event"
                      single-line
                      readonly
                      :rules="begintimeRules"
                    ></v-text-field>
                    <v-date-picker
                      locale="zh-cn"
                      v-model="begintime"
                      :allowed-dates="begintimeLimit"
                      no-title
                      scrollable
                      actions
                    >
                      <template slot-scope="{ save, cancel }">
                        <v-card-actions class="datePadding">
                          <v-spacer></v-spacer>
                          <v-btn @click="cancel">取消</v-btn>
                          <v-btn @click="save" color="primary">确定</v-btn>
                        </v-card-actions>
                      </template>
                    </v-date-picker>
                  </v-menu>
                </v-flex>
              </v-layout>

              <!-- * 结束时间 -->
              <v-layout row justify-center>
                <v-flex xs4>
                  <v-subheader class="labelMargin">*&nbsp;结束时间</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-menu
                    lazy
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    full-width
                    :nudge-right="40"
                    max-width="290px"
                    min-width="290px"
                  >
                    <v-text-field
                      ref="endtime"
                      slot="activator"
                      label="请选择结束时间"
                      v-model="endtime"
                      append-icon="event"
                      single-line
                      readonly
                      :rules="endtimeRules"
                    ></v-text-field>
                    <v-date-picker
                      locale="zh-cn"
                      v-model="endtime"
                      :allowed-dates="endtimeLimit"
                      no-title
                      scrollable
                      actions
                    >
                      <template slot-scope="{ save, cancel }">
                        <v-card-actions class="datePadding">
                          <v-spacer></v-spacer>
                          <v-btn @click="cancel">取消</v-btn>
                          <v-btn @click="save" color="primary">确定</v-btn>
                        </v-card-actions>
                      </template>
                    </v-date-picker>
                  </v-menu>
                </v-flex>
              </v-layout>

              <!-- * 参与人 -->
              <v-layout row justify-center>
                <v-flex xs4>
                  <v-subheader class="labelMargin">*&nbsp;参与人</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <tree-part
                    ref="treePart"
                    :isValDate="true"
                    @clickNode="clickNode"
                    :editUser="editUser"
                    :isDialog="isDialog"
                  />
                </v-flex>
              </v-layout>
              <!-- <v-layout row justify-center>
                <v-flex xs12>
                  <AllSelect></AllSelect>
                </v-flex>
              </v-layout> -->
              <!-- 地块 -->
              <v-layout row justify-center>
                <v-flex xs4>
                  <v-subheader class="labelMargin">地块</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-select
                    v-model="planparcellist"
                    :items="planparcellistArr"
                    item-text="name"
                    item-value="id"
                    label="全部地块(可多选或不选)"
                    multiple
                    single-line
                  >
                  </v-select>
                </v-flex>
              </v-layout>

              <!-- 备注 -->
              <v-layout row justify-center>
                <v-flex xs4>
                  <v-subheader class="labelMargin">备注</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    v-model="remark"
                    label="请输入备注信息"
                    single-line
                    textarea
                    rows="3"
                    maxlength="500"
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>

              <!-- 提醒 -->
              <v-layout row justify-center>
                <v-flex xs2>
                  <v-subheader class="labelMargin">提醒</v-subheader>
                </v-flex>

                <v-flex xs2>
                  <v-select
                            v-model="taskwarnlistType"
                            label="方式"
                            :items="taskwarnlistTypeArr"
                            item-text="dictname"
                            item-value="dictvalue"
                            single-line
                  >
                  </v-select>
                </v-flex>

                <v-flex xs2 class=""><v-subheader class="labelMargin">结束时间前</v-subheader></v-flex>

                <!-- 时间 -->
                <v-flex xs2>
                  <v-select v-model="taskwarnlistWarntimes"
                            label="时间"
                            :items="TimeArr"
                            single-line
                  >
                  </v-select>
                </v-flex>

                <!-- 时间单位 -->
                <v-flex xs2 class="timeUnit-wrapper">
                  <v-select v-model="taskwarnlistTimeunit"
                            label="单位"
                            :items="taskwarnlistTimeunitArr"
                            item-text="dictname"
                            item-value="dictvalue"
                            single-line
                  >
                  </v-select>
                </v-flex>

              </v-layout>


            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-layout row class="toolBottom">
              <v-flex xs12 text-xs-center>
                <v-spacer></v-spacer>
                <v-btn @click="cancelPlan" class="btnSize">取消</v-btn>
                <v-btn @click="submitPlan" color="primary" class="btnSize" :disabled="saveDisabled">
                  确认
                  <v-progress-circular
                    v-show="progressShow"
                    class="progressWrapper"
                    size="23"
                    indeterminate
                    color="primary"
                  />
                </v-btn>
              </v-flex>
            </v-layout>
          </v-card-actions>
        </v-card>
      </form>
    </v-dialog>

    <!-- 详情 农事计划弹出框 -->
    <v-dialog v-model="planDetailDialog" width="700">
      <form>
        <v-card>
          <v-card-title class="planDetail-title">
            <v-layout row>
              <v-flex xs3>
                <span class="planDetail-text">农事计划详情</span>
              </v-flex>
              <v-flex xs9 text-xs-right>
                <v-btn @click="openplanDeleteDialog" class="btnSize" color="primary" outline><v-icon color="primary">delete</v-icon>删除</v-btn>
                <v-btn @click="openPlanDialog" color="primary" class="btnSize" outline>
                  <v-icon color="primary">edit</v-icon>
                  <span>编辑</span>
                  <v-progress-circular
                    v-show="progressShowOpenPlan"
                    class="progressShowOpenPlan"
                    size="23"
                    indeterminate
                    color="primary"
                  />
                </v-btn>
                <v-icon class="clearDialog" @click="planDetailDialog = false">clear</v-icon>
              </v-flex>
            </v-layout>
          </v-card-title>

          <v-card-text>
            <v-container class="planDetail-wrapper">

              <!-- 计划内容 -->
              <v-layout row >
                <v-flex xs12>
                  <span>计划内容&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.describe || '- -'"></span>
                </v-flex>
              </v-layout>

              <!-- 开始时间 -->
              <v-layout row >
                <v-flex xs12>
                  <span>开始时间&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.begintime || '- -'">开始时间</span>
                </v-flex>
              </v-layout>

              <!-- 结束时间 -->
              <v-layout row >
                <v-flex xs12>
                  <span>结束时间&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.endtime || '- -'">结束时间</span>
                </v-flex>
              </v-layout>

              <!-- 发布人 -->
              <v-layout row >
                <v-flex xs12>
                  <span>发布人&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.createuseridname || '- -'">发布人</span>
                </v-flex>
              </v-layout>

              <!-- 参与人 -->
              <v-layout row >
                <v-flex xs12>
                  <span>参与人&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.planparticsusernames || '- -'">参与人</span>
                </v-flex>
              </v-layout>

              <!-- 地块 -->
              <v-layout row >
                <v-flex xs12>
                  <span>地块&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.parcelidnames || '- -'">
                    地块
                  </span>
                </v-flex>
              </v-layout>

              <!-- 备注 -->
              <v-layout row >
                <v-flex xs12>
                  <span>备注&nbsp;:&nbsp;</span>
                  <span v-text="planDetail.remark || '- -'">asdfadfs</span>
                </v-flex>
              </v-layout>

            </v-container>
          </v-card-text>
        </v-card>
      </form>
    </v-dialog>

    <!-- 删除 农事计划弹出框 -->
    <v-dialog v-model="planDeleteDialog" width="700">
      <v-card>
          <v-card-text>
            <v-container>
              <v-layout row >
                <v-flex xs12 text-xs-center><span>信息删除后将无法恢复,请谨慎操作,确认删除?</span></v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions class="btnPadding">
            <v-layout row>
              <v-flex xs12 text-xs-center>
                <v-spacer></v-spacer>
                <v-btn @click.native="cancelPlanDelete" class="btnSize">取消</v-btn>
                <v-btn @click.native="planDelete"
                       color="primary"
                       class="btnSize"
                >
                  确认
                </v-btn>
              </v-flex>
            </v-layout>
          </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 消息弹出框 -->
    <v-dialog v-model="serviceMessageDialog" width="700">
      <v-card>
        <v-card-text>
          <v-container>
            <v-layout row >
              <v-flex xs12 text-xs-center><span v-text="serviceMessage"></span></v-flex>
            </v-layout>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-layout row>
            <v-flex xs12 text-xs-center>
              <v-spacer></v-spacer>
              <v-btn @click.native="serviceMessageDialog = false" color="primary">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>
<script>
  import CalendarView from '@/pages/farmworks/workplan/calendar/CalendarView'
  import TreePart from '@/components/TreePart'
  import FarmerMixin from '@/pages/farmworks/farmerMixin'
  import AllSelect from '@/components/AllSelect'
  import { excuteApis } from '@/apis'
  import { parseTime } from '@/utils'
  export default {
    name: 'farmerPlans',
    mixins: [ FarmerMixin ],
    components: { CalendarView, TreePart, AllSelect },
    data () {
      return {
        isDialog: false,
        editUser: [], // 编辑时的参与人
        describeRules: [() => !!this.describe || '请填写计划内容'],
        begintimeRules: [() => !!this.begintime || '请选择开始时间'],
        endtimeRules: [() => !!this.endtime || '请选择结束时间'],
        displayPeriodUom: 'month',
        showDate: new Date(),
        events: [], // 任务列表
        planDialog: false, // 农事计划弹出框
        planDetailDialog: false, // 查看农事计划详情弹出框
        planDeleteDialog: false, // 删除农事计划弹出框
        serviceMessageDialog: false, // 后台服务消息弹出康
        /**
         * 业务相关字段
         */
        describe: null, // 计划内容
        begintime: null, // 开始时间
        endtime: null, // 结束时间
        remark: null, // 备注
        planparcellist: null, // 地块列表
        planwarnlist: [], // 提醒方式列表

        currentPlan: null, // 当前操作的plan
        operate: 'add', // 用以区分 当前是新增还是编辑
        /**
         * 页面渲染相关字段
         */
        planparcellistArr: [],
        planDetail: {}, // 农事计划详情
        serviceMessage: null, // 后台服务消息
        progressShow: false, // 转圈
        saveDisabled: false, // 保存按钮是否不可用
        progressShowOpenPlan: false, // 编辑按钮
        /**
         * 提醒
         */
        taskwarnlist: [], // 提醒方式
        taskwarnlistType: null,
        taskwarnlistTypeArr: [],
        taskwarnlistWarntimes: 0, // 提醒时间
        taskwarnlistTimeunit: null,
        taskwarnlistTimeunitArr: [],
        TimeArr: [5, 15, 30, 45]
      }
    },
    mounted () {
      this.getServiceData()
    },
    computed: {
      /**
       * userid
       */
      userid () {
        return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
      },

      /**
       * 需验证的表单字段
       */
      planForm () {
        return {
          describe: this.describe,
          begintime: this.begintime,
          endtime: this.endtime
        }
      }
    },
    methods: {
      /**
       * 限定开始时间
       */
      begintimeLimit (val) {
        if (this.endtime === null) {
          return true
        } else {
          return val <= this.endtime
        }
      },

      /**
       * 限定结束时间
       */
      endtimeLimit (val) {
        if (this.begintime === null) {
          return true
        } else {
          return val >= this.begintime
        }
      },

      /**
       * 打开添加农事计划弹窗
       */
      addPlan () {
        this.resetPlan() // 清除上次留下来的字段
        this.resetForm() // 重置表单校验规则
        this.$refs.treePart.clearValdate() // 清除 tree校验规则
        this.$refs.treePart.clearSelectUser() // 清除tree 以勾选数据
        this.operate = 'add' // 标示 新增
        this.planDialog = true // 打开弹窗
      },

      /**
       * 取消弹窗
       */
      cancelPlan () {
        this.planDialog = false
      },

      /**
       * 确认 添加、修改农事计划 main
       */
      submitPlan () {
        this.valdateForm() // 验证表单
        let treeHasError = this.$refs.treePart.valdateForm()
        if (this.formHasErrors || treeHasError) return
        this.solveRuleBug() // 解决bug用的
        // 地块列表数据处理
        let planparcellist = []
        if (this.planparcellist) {
          for (let list of this.planparcellist) {
            planparcellist.push({parcelid: list})
          }
        }

        // 提醒方式列表数据处理
        if (this.taskwarnlistType && this.taskwarnlistWarntimes && this.taskwarnlistTimeunit) {
          this.planwarnlist = [] // 清空
          this.planwarnlist.push({
            type: this.taskwarnlistType, //	提醒方式
            warntimes: this.taskwarnlistWarntimes, // 计划开始前多长时间提醒
            timeunit: this.taskwarnlistTimeunit // 时间单位
          })
        }

        this.saveDisabled = true // 在服务响应之前禁用返回按钮
        this.progressShow = true // 显示转圈
        if (this.operate === 'add') {
          let param = {
            describe: this.describe, // 计划内容
            begintime: this.begintime, //	开始时间
            endtime: this.endtime, //	结束时间
            planparticslist: this.partuserlist, //	参与人列表
            remark: this.remark, //	备注
            planparcellist: planparcellist, //	地块列表
            planwarnlist: this.planwarnlist //	提醒方式列表
          }
          excuteApis(param, global.constant.farmApiUrls, 'plan', 'add').then(response => {
            console.log('新增农事计划成功')
            console.log(response)
            if (response.data.flag === 1) {
              this.getPlan(this.userid, this.userid, null)
              this.planDialog = false
            } else {
              this.setMessage('服务异常,请重试')
            }
          }).catch((response) => {
            console.log('新增农事计划失败')
            console.log(response)
            this.setMessage('服务异常,请重试')
          }).finally(() => {
            this.saveDisabled = false
            this.progressShow = false
          })
        } else {
          let param = {
            id: this.planDetail.id, // id
            describe: this.describe, // 计划内容
            begintime: this.begintime, //	开始时间
            endtime: this.endtime, //	结束时间
            planparticslist: this.partuserlist, //	参与人列表
            remark: this.remark, //	备注
            planparcellist: planparcellist, //	地块列表
            planwarnlist: this.planwarnlist //	提醒方式列表
          }
          excuteApis(param, global.constant.farmApiUrls, 'plan', 'update').then(response => {
            console.log('修改农事计划成功')
            console.log(response)
            if (response.data.flag === 1) {
              this.getPlan(this.userid, this.userid)
            } else {
              this.setMessage('服务异常,请重试')
            }
          }).catch(() => {
            this.setMessage('服务异常,请重试')
          }).finally(() => {
            this.saveDisabled = false
            this.progressShow = false
            this.planDialog = false
          })
        }
        // 恢复校验规则
        this.resetRule()
      },

      /**
       * 验证表单
       */
      valdateForm () {
        this.formHasErrors = false // 表单是否有错误
        Object.keys(this.planForm).forEach(f => {
          if (!this.planForm[f]) this.formHasErrors = true
          this.$refs[f].validate(true)
        })
      },

      /**
       * 将表单重置到验证之前 resetform
       */
      resetForm () {
        this.formHasErrors = false
        Object.keys(this.planForm).forEach(f => {
          this.$refs[f].reset()
        })
      },

      /**
       * 表单已经验证通过 错误信息会闪现解决方法
       * 方法: 所有验证规则置空
       */
      solveRuleBug () {
        this.describeRules = []
        this.begintimeRules = []
        this.endtimeRules = []
      },

      /**
       * 恢复验证规则
       */
      resetRule () {
        this.describeRules = [() => !!this.describe || '请填写计划内容']
        this.begintimeRules = [() => !!this.begintime || '请选择开始时间']
        this.endtimeRules = [() => !!this.endtime || '请选择结束时间']
      },

      /**
       * 设置弹窗消息
       */
      setMessage (message) {
        this.serviceMessage = message
        this.serviceMessageDialog = true
      },

      /**
       * 打开删除农事计划弹窗
       */
      openplanDeleteDialog () {
        this.planDetailDialog = false
        this.planDeleteDialog = true
      },

      /**
       * 确认删除农事计划
       */
      planDelete () {
        excuteApis({id: this.planDetail.id}, global.constant.farmApiUrls, 'plan', 'deletebyid').then(response => {
          console.log(response)
          if (response.data && response.data.flag === 1) {
            console.log('删除成功')
            this.getPlan(this.userid, this.userid)
          }
        }).catch((response) => {
          console.log(response)
        }).finally(() => {
          this.planDeleteDialog = false
        })
      },

      /**
       * 取消删除农事计划
       */
      cancelPlanDelete () {
        this.planDeleteDialog = false
      },

      /**
       * 农事计划详情(点击任务弹出)
       */
      clickEvent (event) {
        this.planDetail = event // 详情赋值
        this.planDetailDialog = true // 打开弹窗
        this.$refs.treePart.clearSelectUser() // 清除tree 已勾选数据 resetCount
        this.$refs.treePart.resetCount() // tree count次数置0
        excuteApis({id: this.planDetail.id}, global.constant.farmApiUrls, 'plan', 'querybyid').then(response => {
          console.log('获取农事计划详情成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            let plan = response.data.data
            // 数据处理
            // 地块
            plan.newPlanparcellist = []
            if (plan.planparcellist) {
              for (let list of plan.planparcellist) {
                plan.newPlanparcellist.push(list.parcelid)
              }
            }
            this.describe = plan.describe // 计划内容
            this.begintime = plan.begintime.substring(0, 11) //	开始时间
            this.endtime = plan.endtime.substring(0, 11) //	结束时间
            this.editUser = plan.planparticslist // 参与人
            this.isDialog = true // 是弹窗
            this.remark = plan.remark //	备注
            this.planparcellist = plan.newPlanparcellist //	地块列表
            if (plan.planwarnlist && plan.planwarnlist.length > 0) {
              this.taskwarnlistType = plan.planwarnlist[0].type //	提醒方式
              this.taskwarnlistWarntimes = plan.planwarnlist[0].warntimes // 计划开始前多长时间提醒
              this.taskwarnlistTimeunit = plan.planwarnlist[0].timeunit // 时间单位
            }
          }
        })
      },

      /**
       * 编辑农事计划
       */
      openPlanDialog () {
        this.operate = 'edit' // 标示编辑
        this.planDetailDialog = false // 打开编辑窗口
        this.planDialog = true
      },

      /**
       * 切换时间触发的函数
       */
      setShowDate (d) {
        this.showDate = d
      },

      /**
       * 切换日期
       */
      cutMonth () {
        this.displayPeriodUom = 'month'
        let begintime = new Date(this.showDate.getFullYear(), this.showDate.getMonth(), 1)
        let endtime = new Date(this.showDate.getFullYear(), this.showDate.getMonth() + 1, 0)
        this.begintime = parseTime(begintime, 'yyyy-MM-dd hh:mm:ss')
        this.endtime = parseTime(endtime, 'yyyy-MM-dd hh:mm:ss')
        console.log('开始时间 = ' + this.begintime)
        console.log('结束时间 = ' + this.endtime)
        this.getPlan(this.userid, this.userid, null, this.begintime, this.endtime)
      },
      cutWeek () {
        this.displayPeriodUom = 'week'
        let nowTime = this.showDate.getTime()
        let oneDayTime = 24 * 60 * 60 * 1000
        let day = this.showDate.getDay()
        console.log('day = ' + day)
        if (day === 0) day = 7
        let mondayTime = nowTime - (day - 1) * oneDayTime
        let sundayTime = nowTime + (7 - day) * oneDayTime
        let weekBegin = new Date(mondayTime)
        let weekEnd = new Date(sundayTime)
        this.begintime = parseTime(new Date(weekBegin.getFullYear(), weekBegin.getMonth(), weekBegin.getDate(), 0), 'yyyy-MM-dd hh:mm:ss')
        this.endtime = parseTime(new Date(weekEnd.getFullYear(), weekEnd.getMonth(), weekEnd.getDate(), 0), 'yyyy-MM-dd hh:mm:ss')
        console.log('开始时间 = ' + this.begintime)
        console.log('结束时间 = ' + this.endtime)
        this.getPlan(this.userid, this.userid, null, this.begintime, this.endtime)
      },
      cutYear () {
        this.displayPeriodUom = 'year'
        let begintime = new Date(this.showDate.getFullYear(), 0)
        let endtime = new Date(this.showDate.getFullYear() + 1, 0)
        this.begintime = parseTime(begintime, 'yyyy-MM-dd hh:mm:ss')
        this.endtime = parseTime(endtime, 'yyyy-MM-dd hh:mm:ss')
        console.log('开始时间 = ' + this.begintime)
        console.log('结束时间 = ' + this.endtime)
        this.getPlan(this.userid, this.userid, null, this.begintime, this.endtime)
      },

      /**
       * 按条件查询农事计划
       */
      inputChange (param) {
        console.log('输入框值改变')
        console.log(param)
        this.getPlan(this.userid, this.userid, param.keyword, param.begintime, param.endtime)
      },

      /**
       * 切换时间获取农事计划
       */
      changeTime (date) {
        this.begintime = parseTime(date.begintime, 'yyyy-MM-dd hh:mm:ss')
        this.endtime = parseTime(date.endtime, 'yyyy-MM-dd hh:mm:ss')
        console.log('开始时间 = ' + this.begintime)
        console.log('结束时间 = ' + this.endtime)
        this.getPlan(this.userid, this.userid, null, this.begintime, this.endtime)
      },

      /**
       * 获取后台服务数据
       */
      getServiceData () {
        this.getPlan(this.userid, this.userid, null, this.begintime, this.endtime) // 获取农事计划
        /**
         * 获取地块
         */
        excuteApis({}, global.constant.parcelApis, 'parcel', 'query').then(res => {
          console.log('获取地块成功------')
          console.log(res)
          if (res.data && res.data.flag === 1 && res.data.data) {
            this.planparcellistArr = res.data.data
          }
        })

        /**
         * 获取任务提醒方式
         */
        excuteApis({dicttype: 'plan-warntype', delstate: 1}, global.constant.commonApis, 'common', 'querydictlist').then(response => {
          console.log('获取任务提醒方式成功')
          if (response.data && response.data.data && response.data.flag === 1) {
            this.taskwarnlistTypeArr = response.data.data
          }
        })

        /**
         * 获取时间单位
         */
        excuteApis({dicttype: 'farm-time-unit', delstate: 1}, global.constant.commonApis, 'common', 'querydictlist').then(response => {
          console.log('获取任务提醒方式成功')
          console.log('获取时间单位成功')
          if (response.data && response.data.data && response.data.flag === 1) {
            this.taskwarnlistTimeunitArr = response.data.data
          }
        })
      },

      /**
       * 获取农事计划 main
       */
      getPlan (createuserid, planparticsuserid, keyword, begintime, endtime) {
        this.showLoad()
        let param = {
          createuserid: createuserid,
          planparticsuserid: planparticsuserid,
          keyword: keyword,
          begintime: begintime,
          endtime: endtime,
          delstate: 1
        }
        excuteApis(param, global.constant.farmApiUrls, 'plan', 'querylist').then(res => {
          console.log('获取农事计划成功')
          console.log(res)
          if (res.data && res.data.data && res.data.flag === 1) {
            let plan = res.data.data
            let newPlan = []
            for (let list of plan) {
              list.title = list.describe
              list.begintime = list.begintime.substring(0, 11)
              list.startDate = list.begintime
              list.endtime = list.endtime.substring(0, 11)
              list.endDate = list.endtime
              list.classes = 'primary' // 颜色
              newPlan.push(list)
              console.log(newPlan)
            }
            this.events = newPlan
            this.fromHome(this.events) // 从首页过来时的处理
          } else {
            this.events = [] // 置空
          }
        }).finally(() => {
          this.closeLoad()
        })
      },

      /**
       * 重置添加计划表单
       */
      resetPlan () {
        this.describe = null // 计划内容
        this.begintime = null //	开始时间
        this.endtime = null //	结束时间
        this.partuserlist = null //	参与人列表
        this.remark = null //	备注
        this.planparcellist = [] //	地块列表
        this.planwarnlist = [] //	提醒方式列表
        this.taskwarnlistType = null //	提醒方式
        this.taskwarnlistWarntimes = null // 计划开始前多长时间提醒
        this.taskwarnlistTimeunit = null // 时间单位
      },

      /**
       * 页面加载完获取当前时间(开始 结束)
       */
      currentDate (param) {
        this.begintime = this.timeToString(param.begintime)
        this.endtime = this.timeToString(param.endtime)
      },

      /**
       * 时间转字符串
       */
      timeToString (time) {
        let year = time.getFullYear()
        let month = time.getMonth() + 1
        let day = time.getDate()
        let hour = time.getHours()
        let minute = time.getMinutes()
        let second = time.getSeconds()
        if (month < 10) {
          month = '0' + month
        }
        if (day < 10) {
          day = '0' + day
        }
        if (hour < 10) {
          hour = '0' + hour
        }
        if (minute < 10) {
          minute = '0' + minute
        }
        if (second < 10) {
          second = '0' + second
        }
        return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second
      },

      /**
       * 从首页过来时的处理
       * @param planList 从其中找到对应数据
       */
      fromHome (planList) {
        let homeFlag = sessionStorage.getItem('homeFlag')
        if (!homeFlag || homeFlag !== 'true') return
        let id = parseInt(this.$route.query.id) // id
        for (let list of planList) {
          if (list.id === id) {
            sessionStorage.removeItem('homeFlag')
            this.clickEvent(list)
            break
          }
        }
      },

      /**
       * 点击tree时获取已经选择的用户
       */
      clickNode (node) {
        this.partuserlist = [] // 清空用户列表
        if (!node || node.length === 0) return
        for (let user of node) {
          this.partuserlist.push({userid: user.id})
        }
      }
    }
  }
</script>
<style scoped>
  .calendar-view.period-week,
  .calendar-view.period-month.periodCount-1 {
    height: 45vw;
  }
  .calendar .event.birthday {
    background-color: #e0f0e0;
    border-color: #d7e7d7;
  }
  .planMain{
    position: relative;
  }
  .addButton{
    position: absolute;
    z-index: 2;
    top: 0;
    right: 0;
  }
  .timeUnit-wrapper{
    margin-left: 15px;
  }
  .searchWrapper{
    position: absolute;
    z-index: 2;
    top: -15px;
    right: 150px;
  }
  .planDetail-wrapper .layout{
    margin-bottom: 15px;
  }
  .planDetail-title{
    border-bottom: 1px solid #CCC;
  }
  .planDetail-text{
    line-height: 50px;
  }
  .clearDialog{
    margin-left: 20px;
    cursor: pointer;
  }
  .main{
    padding: 20px;
    margin-bottom: 50px;
  }
  .containerWrapper{
    padding: 0;
    margin-bottom: 50px;
  }
  .btnColor{
    color: #FFF;
  }
  .toolBottom{
    padding-bottom: 20px;
  }
  .finishTimeWrapper{
    align-items: center;
    margin-top: 32px;
    color: rgba(0,0,0,0.54);
  }
  .datePadding{
    padding-bottom: 20px;
    padding-right: 20px;
  }
  .btnSize{
    min-width: 107px;
  }
  .btnPadding{
    padding-bottom: 20px;
  }
  .colorWhite {
    color: #FFF;
  }
  .progressWrapper{
    position: absolute;
    left: 73px
  }
  .progressShowOpenPlan{
    position: absolute;
    left: 80px;
  }
  .test{
    color: red;
  }
  .labelMargin {
    margin-top: 0.9rem
  }
</style>
