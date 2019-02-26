<!--
    表单select v-model 只能绑定字符串?
-->
<template>
  <v-container fluid class="containerWrapper">
    <v-card class="page-content">
      <go-back backName="任务编辑"/>
      <!-- 编辑主体 -->
      <!-- 任务类别 -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>*&nbsp;任务类别</v-subheader>
        </v-flex>
        <v-flex xs5>
          <v-select
            label="请选择任务类别"
            v-model="type"
            :items="typeList"
            item-text="dictname"
            item-value="dictvalue"
            single-line
            :error-messages="errors.collect('type')"
            v-validate="'required'"
            data-vv-name="type"
            data-vv-as="任务列表"
          >
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 任务内容 -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>*&nbsp;任务内容</v-subheader>
        </v-flex>
        <v-flex xs5>
          <v-text-field
            label="请输入任务内容"
            v-model="describe"
            single-line
          >
          </v-text-field>
        </v-flex>
      </v-layout>

      <!-- 开始时间 -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>*&nbsp;开始时间</v-subheader>
        </v-flex>
        <v-flex xs5>
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
              id="begintime"
              slot="activator"
              label="请选择开始时间"
              v-model="begintime"
              append-icon="event"
              single-line
              readonly
              :error-messages="errors.collect('begintime')"
              v-validate="'required'"
              data-vv-name="begintime"
              data-vv-as="开始时间"
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

      <!-- 结束时间 -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>*&nbsp;结束时间</v-subheader>
        </v-flex>
        <v-flex xs5>
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
              slot="activator"
              label="请选择结束时间"
              v-model="endtime"
              append-icon="event"
              single-line
              readonly
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

      <!-- 参与人 ? -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>参与人</v-subheader>
        </v-flex>
        <v-flex xs5><take-part-tree @clickSubmit="clickSubmitToCopyList" ref="child" /></v-flex>
      </v-layout>

      <!-- 参与人的复选框 -->
      <v-layout row>
        <v-flex xs5 offset-xs3 class="checkbox-wrapper">
          <v-checkbox
            label="需要参与人确认的参与任务"
            v-model="isaffirmpartics"
          >
          </v-checkbox>
        </v-flex>
      </v-layout>

      <!-- 地点 ? -->
      <v-layout row v-show="type === 'task-invite'">
        <v-flex xs2 offset-xs1>
          <v-subheader>地点</v-subheader>
        </v-flex>
        <v-flex xs5>
          <v-text-field
            label="请输入约会地点"
            v-model="meetingplace"
            single-line
          >
          </v-text-field>
        </v-flex>
      </v-layout>
      <!-- 地块 ? -->
      <v-layout row v-show="type === 'task-work'">
        <v-flex xs2 offset-xs1>
          <v-subheader>地块</v-subheader>
        </v-flex>
        <v-flex xs5>
          <v-text-field
            disabled
            v-model="taskparcel"
          >
          </v-text-field>
        </v-flex>
      </v-layout>

      <!-- 抄送人 ? -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>抄送人</v-subheader>
        </v-flex>
        <v-flex xs5><take-part-tree @clickSubmit="clickSubmit" ref="child" /></v-flex>
      </v-layout>

      <!-- 备注 -->
      <v-layout row>
        <v-flex xs2 offset-xs1>
          <v-subheader>备注</v-subheader>
        </v-flex>
        <v-flex xs5>
          <v-text-field
            label="请输入备注信息"
            textarea
            v-model="remark"
            single-line
          >
          </v-text-field>
        </v-flex>
      </v-layout>

      <!-- 提醒 -->
      <v-layout row>

        <v-flex xs2 offset-xs1>
          <v-subheader>提醒</v-subheader>
        </v-flex>

        <v-flex xs2 >
          <v-select v-model="taskwarnlistType"
                    label="请选择提醒方式"
                    :items="taskwarnlistTypeArr"
                    item-text="dictname"
                    item-value="dictvalue"
                    single-line
          >
          </v-select>
        </v-flex>

        <span class="begintime-wrapper">结束时间前</span>

        <!-- 时间 -->
        <v-flex xs1 >
          <v-select v-model="taskwarnlistWarntimes"
                    label="请选择时间"
                    :items="TimeArr"
                    single-line
          >
          </v-select>
        </v-flex>

        <!-- 时间单位 -->
        <v-flex xs1 class="timeUnit-wrapper">
          <v-select v-model="taskwarnlistTimeunit"
                    label="请选择时间单位"
                    :items="taskwarnlistTimeunitArr"
                    item-text="dictname"
                    item-value="dictvalue"
                    single-line
          >
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 取消 保存 -->
      <v-layout row justify-center>
        <v-btn class="btnSize" @click="$router.go(-1)">取消</v-btn>
        <v-btn class="btnSize" @click="save" color="primary">保存</v-btn>
      </v-layout>
    </v-card>
  </v-container>


</template>


<script>
  import { excuteApis } from '@/apis'
  import TreePart from '@/components/TreePart'
  import FarmerMixin from '@/pages/farmworks/farmerMixin'
  export default {
    data () {
      return {
        typeList: [], // 任务类别列表
        type: null, // 任务类别
        describe: null, // 任务内容
        begintime: null, // 开始时间
        endtime: null, // 结束时间
        taskparticslist: [ {userid: 1}, {userid: 2} ], // 参与人列表
        isaffirmpartics: false, // 参与人的复选框
        meetingplace: null, // 地点
        taskparcel: null, // 地块
        taskcopylist: [ {userid: 1}, {userid: 2} ], // 抄送人列表
        remark: null, // 备注
        /* 关于提醒 */
        taskwarnlist: [], // 提醒方式
        taskwarnlistType: null,
        taskwarnlistTypeArr: [],
        taskwarnlistWarntimes: null,
        taskwarnlistTimeunit: null,
        taskwarnlistTimeunitArr: [],
        TimeArr: [5, 15, 30, 45],
        begintimeAppend: null, // 开始时间(时分)
        endtimeAppend: null // 结束时间(时分)
      }
    },
    mixins: [ FarmerMixin ],
    components: { TreePart },
    mounted () {
      this.getTaskdDetail()
      this.getData()
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
      cancel () {
      },

      /*
      * 保存
      * ????
      * */
      save () {
        // 获取数据时的一些处理
        this.taskwarnlist = [{ // 封装提醒的数据
          type: this.taskwarnlistType,
          warntimes: this.taskwarnlistWarntimes,
          timeunit: this.taskwarnlistTimeunit
        }]

        this.$validator.validateAll().then((result) => {
          if (result) {
            // 提交数据
            let param = {
              id: this.$route.query.id, // id
              type: this.type,	// 任务类别
              describe: this.describe,	// 任务内容
              begintime: this.formatDateTime(this.begintime, this.begintimeAppend),	// 开始时间
              endtime: this.formatDateTime(this.endtime, this.endtimeAppend),	// 结束时间
              taskparticslist: this.taskparticslist,	// 参与人列表
              isaffirmpartics: this.isaffirmpartics ? 1 : 0,	// 是否需要执行人确认参与任务
              remark: this.remark,	// 备注
              taskcopylist: this.taskcopylist,	// 抄送人列表
              taskwarnlist: this.taskwarnlist	// 提醒方式列表
            }
            excuteApis(param, global.constant.farmApiUrls, 'task', 'update').then(response => {
              if (response.data && response.data.flag === 1) {
                console.log('修改农事任务成功')
                // 页面跳转
                // this.$router.push({name: 'farmertasks'})
              } else {
                console.log('修改农事任务失败')
                // 弹窗
                // this.dialogMessage = true
              }
              console.log(response)
            }).catch((response) => {
              console.log('新增农事任务失败')
              console.log(response)
              // 弹窗
            })
          }
        })
      },

      formatDateTime (date, time) {
        if (!time) return date
        return date + ' ' + time
      },

      /**
       * 根据id获取农事任务详情
       */
      getTaskdDetail () {
        excuteApis({id: this.$route.query.id}, global.constant.farmApiUrls, 'task', 'querybyid').then(response => {
          console.log('获取农事任务详情成功')
          console.log(response)
          if (response.data && response.data.flag === 1 && response.data.data) {
            let taskDetail = response.data.data
            this.type = taskDetail.type // 类别
            this.describe = taskDetail.describe // 任务内容
            this.begintime = taskDetail.begintime // 开始时间
            this.endtime = taskDetail.endtime // 结束时间
            taskDetail.isaffirmpartics === 1 ? this.isaffirmpartics = true : this.isaffirmpartics = false // 复选框

            // 地点 和地块
            if (this.type === 'task-invite') {
              this.meetingplace = taskDetail.meetingplace
            } else {
              this.taskparcel = taskDetail.taskparcellist[0].parcelid
            }
            // 抄送人
            this.remark = taskDetail.remark // 备注
            this.taskwarnlistType = taskDetail.taskwarnlist[0].type // 提醒方式
            this.taskwarnlistWarntimes = taskDetail.taskwarnlist[0].warntimes // 结束时间前多长时间提醒
            this.taskwarnlistTimeunit = taskDetail.taskwarnlist[0].timeunit // 时间单位
          }
        }).catch((response) => {
          console.log('获取农事任务详情失败')
          console.log(response)
        })
      },

      /**
       * 获取后台数据
       */
      getData () {
        /* 获取任务提醒方式 */
        excuteApis({parentcode: 'plan-warntype'}, global.constant.systemApis, 'dict', 'query').then(response => {
          console.log('获取任务提醒方式成功')
          if (response.data && response.data.data && response.data.flag === 1) {
            this.taskwarnlistTypeArr = response.data.data
          }
          console.log(response)
        })

        /* 获取时间单位 */
        excuteApis({parentcode: 'farm-time-unit'}, global.constant.systemApis, 'dict', 'query').then(response => {
          console.log('获取时间单位成功')
          if (response.data && response.data.data && response.data.flag === 1) {
            this.taskwarnlistTimeunitArr = response.data.data
          }
          console.log(response)
        })

        /* 获取任务类别 */
        excuteApis({parentcode: 'task-type'}, global.constant.systemApis, 'dict', 'query').then(response => {
          console.log('获取任务类别成功')
          if (response.data && response.data.data && response.data.flag === 1) {
            this.typeList = response.data.data
            // this.type = response.data.data[0].dictvalue // 农事任务默认为第一项
          }
          console.log(response)
        })
      }
    }
  }
</script>
<style scoped>
  .finishTime-wrapper{
    margin-left: 15px;
    margin-right: 15px;
    line-height: 70px;
  }
  .begintime-wrapper{
    line-height: 80px;
    margin: 0 15px;
  }
  .timeUnit-wrapper{
    margin-left: 15px;
  }
  .containerWrapper{
    padding: 0;
    margin-bottom: 50px;
  }
</style>

