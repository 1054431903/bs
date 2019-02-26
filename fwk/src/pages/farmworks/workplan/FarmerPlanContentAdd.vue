<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow="isShowDialog"
                   :title="dialogTitle"
                   :modalMaxWidth="620"
                   :bodyHeight="620">
      <v-form v-model="plantContentFormValid"
              ref="planContentForm"
              lazy-validation
              autocomplete="off">
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-select label="所属计划"
                      v-model="curPlanId"
                      :items="planList"
                      item-text="name"
                      item-value="id"
                      disabled
                      single-line>
            </v-select>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="计划内容"
                          v-model="planContent.content"
                          counter="50"
                          maxlength="50"
                          required
                          :rules="rules.content">
            </v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-textarea label="内容描述"
                        v-model="planContent.remark"
                        counter="500"
                        maxlength="500">
            </v-textarea>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-autocomplete label="阶段"
                            v-model="stage"
                            :items="customStages"
                            :rules="rules.stage"
                            item-text="name"
                            item-value="name"
                            ref="stage"
                            combobox
                            clearable
                            single-line>
            </v-autocomplete>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <div style="color: rgba(0, 0, 0, 0.47);">颜色</div>
          </v-flex>
          <v-flex xs12>
            <v-divider></v-divider>
          </v-flex>
          <v-flex xs12
                  py-2>
            <template v-for="(color, index) in colors">
              <v-avatar size="30"
                        class="mr-2"
                        :color="color.name"
                        :key="index"
                        @click="selectColor(index)">
                <v-icon small
                        color="white"
                        v-if="selectedColor === color.hex">done</v-icon>
              </v-avatar>
            </template>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="开始时间"
                          v-model="startOnStr"
                          readonly
                          required
                          :rules="rules.startdatetime"
                          ref="startDateTime"
                          @click="selectedStartDateTime"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="结束时间"
                          v-model="endOnStr"
                          readonly
                          required
                          :rules="rules.enddatetime"
                          ref="endDateTime"
                          @click="selectedEndDateTime"></v-text-field>
          </v-flex>
        </v-layout>
      </v-form>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary"
                 flat
                 @click.native="cancel">
            取消
          </v-btn>
          <v-btn color="primary"
                 flat
                 @click.native="save">
            完成
          </v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>
    <v-datetimepicker :visible.sync="startdatetimepicker"
                      :datetime.sync="startdatetime"
                      :isend="false"
                      :showtime="false"
                      :bodyheight="410"></v-datetimepicker>
    <v-datetimepicker :visible.sync="enddatetimepicker"
                      :datetime.sync="enddatetime"
                      :isend="true"
                      :showtime="false"
                      :bodyheight="410"></v-datetimepicker>
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import moment from 'moment'
import VDatetimepicker from '../worktask/DateTimePickers.vue'
import { excuteApis } from '@/apis'
export default {
  name: 'v-farmerplancontentadd',
  props: {
    planContentAddOrEditDialog: {
      type: String,
      default: 'IS_NONE'
    },
    planContentAddOrEditDialogTitle: {
      type: String,
      default: ''
    },
    planContentId: {
      type: String,
      default: ''
    },
    planId: {
      type: String,
      default: ''
    }
  },
  components: {
    VDatetimepicker
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_COPY': true,
      'IS_NONE': false
    }
    return {
      isShowDialog: false,
      dialogTitle: '添加计划内容',
      addOrEdit: ADD_OR_EDIT,
      plantContentFormValid: true,
      planContent: {},
      planList: [],
      selectedColor: null,
      colors: [
        { hex: '0FC15E', name: 'color1' },
        { hex: 'E9D216', name: 'color2' },
        { hex: 'FD6691', name: 'color3' },
        { hex: 'FFAB19', name: 'color4' },
        { hex: 'FF473D', name: 'color5' },
        { hex: '6C777D', name: 'color6' },
        { hex: '94D201', name: 'color7' },
        { hex: '32D2EA', name: 'color8' },
        { hex: 'AF65E0', name: 'color9' },
        { hex: '3396FB', name: 'color10' },
        { hex: 'FE5F57', name: 'color11' },
        { hex: '7F57C1', name: 'color12' },
        { hex: '00B6C4', name: 'color13' },
        { hex: 'C18E6F', name: 'color14' },
        { hex: 'BCABA4', name: 'color15' }
      ],
      // 当前阶段名称
      stage: null,
      // 当前组织已创建过的阶段
      customStages: [],
      // 计划开始时间字符串
      startOnStr: null,
      // 计划结束时间字符串
      endOnStr: null,
      // 开始时间弹出框
      startdatetimepicker: false,
      // 结束时间弹出框
      enddatetimepicker: false,
      // 时间控件返回的计划开始时间(字符串: 2018-09-19 00:00)
      startdatetime: null,
      // 时间控件返回的计划结束时间(字符串: 2018-09-19 23:59)
      enddatetime: null,
      // 时间错误提示信息
      datetimeErrors: false,
      // 当前操作方式
      curOperate: 'IS_NONE',
      // 当前计划ID
      curPlanId: null,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        content: [
          (v) => !!v || '必填项',
          (v) => v && v.length <= 50 || '不超过50个字符'
        ],
        stage: [
          (v) => !v || (v.length <= 50) || '不超过50个字符'
        ],
        startdatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetime()) || '计划开始时间不能大于任务结束时间'
        ],
        enddatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetime()) || '计划结束时间不能小于任务开始时间'
        ]
      }
    }
  },
  watch: {
    planContentAddOrEditDialog (v) {
      this.curOperate = v
      this.disposeModalDialog(v)
    },
    startdatetime (v) {
      v ? this.startOnStr = moment(v, 'YYYY-MM-DD HH:mm').format('YYYY-MM-DD') : null
    },
    enddatetime (v) {
      v ? this.endOnStr = moment(v, 'YYYY-MM-DD HH:mm').format('YYYY-MM-DD') : null
    },
    datetimeErrors: function (v) {
      // this.$refs['planContentForm'].validate()
    }
  },
  computed: {
    tenantId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  methods: {
    save () {
      // 设置其失去焦点，v-model中才会有值
      this.$refs.stage.blur()
      // 将回调延迟到下次 DOM 更新循环之后执行
      this.$nextTick(() => {
        if (this.$refs['planContentForm'].validate()) {
          switch (this.curOperate) {
            case 'IS_ADD':
              let tmpPlanContent = {}
              tmpPlanContent.plan = { $oid: this.curPlanId }
              if (this.stage && this.stage.trim()) {
                tmpPlanContent.stage = this.stage.trim()
              }
              if (this.selectedColor) {
                tmpPlanContent.color = { $numberLong: parseInt(this.selectedColor, 16).toString() }
              }
              tmpPlanContent.startOn = { $numberLong: moment(this.startOnStr, 'YYYY-MM-DD').valueOf().toString() }
              tmpPlanContent.endOn = { $numberLong: moment(this.endOnStr, 'YYYY-MM-DD').hour(23).minute(59).second(59).valueOf().toString() }
              tmpPlanContent.content = this.planContent.content
              tmpPlanContent.remark = this.planContent.remark
              mongo.db().collection('plan.content').insert(tmpPlanContent).then(res => {
                console.log(res)
                // 发送一条推送消息给所有参与人
                excuteApis(res._id.$oid, global.constant.pusherApisUrls, 'planContent', 'execution').then(data => {
                  console.log(data)
                })
                // 更新计划的修改时间
                mongo.db().collection('plan').update({ filter: { _id: { $oid: this.curPlanId } }, update: { $set: {} } }).then()
                // 插入阶段信息
                this.saveStage(this.stage)
                // 更新计划年视图
                this.$parent.refreshData(this.curPlanId)
                this.$emit('update:planContentAddOrEditDialog', 'IS_NONE')
              })
              break
            case 'IS_EDIT':
              let filter = {}
              filter._id = this.planContent._id
              let setColumon = {}
              let unsetColumon = {}
              setColumon.content = this.planContent.content
              setColumon.startOn = { $numberLong: new Date(this.startOnStr).getTime().toString() }
              setColumon.endOn = { $numberLong: new Date(this.endOnStr).getTime().toString() }
              if (this.stage && this.stage.trim()) {
                setColumon.stage = this.stage.trim()
              } else {
                unsetColumon.stage = ''
              }
              if (this.planContent.remark && this.planContent.remark.trim()) {
                setColumon.remark = this.planContent.remark.trim()
              } else {
                unsetColumon.remark = ''
              }
              if (this.selectedColor) {
                setColumon.color = { $numberLong: parseInt(this.selectedColor, 16).toString() }
              } else {
                unsetColumon.color = ''
              }
              let update = Object.keys(unsetColumon).length === 0 ? { $set: setColumon } : { $set: setColumon, $unset: unsetColumon }
              mongo.db().collection('plan.content').update({ filter: filter, update: update }).then(res => {
                if (!res.writeError && !res.writeConcernError) {
                  // 更新计划的修改时间
                  mongo.db().collection('plan').update({ filter: { _id: this.planContent.plan }, update: { $set: {} } }).then(r => {
                    // 插入阶段信息
                    this.saveStage(this.stage)
                    // 刷新父页面数据
                    // this.$parent.queryPlanContentById(this.planContent._id.$oid)
                    // 刷新计划详情年视图的数据
                    this.$parent.refreshParentData(this.curPlanId)
                    // 关闭父页面弹窗
                    this.$parent.isShowDialog = false
                    // 关闭当前弹窗
                    this.$emit('update:planContentAddOrEditDialog', 'IS_NONE')
                  })
                }
              })
              break
            case 'IS_COPY':
              let tmpCopyPlanContent = {}
              tmpCopyPlanContent.plan = { $oid: this.curPlanId }
              if (this.stage && this.stage.trim()) {
                tmpCopyPlanContent.stage = this.stage.trim()
              }
              if (this.selectedColor) {
                tmpCopyPlanContent.color = { $numberLong: parseInt(this.selectedColor, 16).toString() }
              }
              tmpCopyPlanContent.startOn = { $numberLong: moment(this.startOnStr, 'YYYY-MM-DD').valueOf().toString() }
              tmpCopyPlanContent.endOn = { $numberLong: moment(this.endOnStr, 'YYYY-MM-DD').valueOf().toString() }
              tmpCopyPlanContent.content = this.planContent.content
              tmpCopyPlanContent.remark = this.planContent.remark
              mongo.db().collection('plan.content').insert(tmpCopyPlanContent).then(res => {
                // 更新计划的修改时间
                mongo.db().collection('plan').update({ filter: { _id: this.planContent.plan }, update: { $set: {} } }).then(r => {
                  // 插入阶段信息
                  this.saveStage(this.stage)
                  // 刷新父页面数据
                  // this.$parent.queryPlanContentById(this.planContent._id.$oid)
                  // 刷新计划详情年视图的数据
                  this.$parent.refreshParentData(this.curPlanId)
                  // 关闭父页面弹窗
                  this.$parent.isShowDialog = false
                  this.$emit('update:planContentAddOrEditDialog', 'IS_NONE')
                })
              })
              break
            default:
              this.$emit('update:planContentAddOrEditDialog', 'IS_NONE')
              break
          }
        }
      })
    },
    cancel () {
      // 关闭父页面弹窗
      this.$parent.isShowDialog = false
      this.$emit('update:planContentAddOrEditDialog', 'IS_NONE')
    },
    queryPlanById (id) {
      if (!id) return
      let filter = {}
      filter._id = { $oid: id }
      filter._removed = { $exists: false }
      return mongo.db().collection('plan').find({ filter: filter })
    },
    queryPlanContentById () {
      if (!this.planContentId) return
      let filter = {}
      filter._id = { $oid: this.planContentId }
      filter._removed = { $exists: false }
      return mongo.db().collection('plan.content').find({ filter: filter })
    },
    selectColor (i) {
      this.selectedColor = this.colors[i].hex
    },
    selectedStartDateTime () {
      this.startdatetime = this.formatDate(this.planContent.startOn)
      this.startdatetimepicker = true
    },
    selectedEndDateTime () {
      this.enddatetime = this.formatDate(this.planContent.endOn)
      this.enddatetimepicker = true
    },
    invalidDatetime: function () {
      let start = new Date(this.startOnStr || '1970-1-1')
      let end = new Date(this.endOnStr || '2118-1-1')
      let bool = start.getTime() <= end.getTime()
      this.datetimeErrors = bool
      return bool
    },
    saveStage (v) {
      if (v && v.trim()) {
        mongo.db().collection('plan.content.stage').find({ filter: { tenant: { $oid: this.tenantId }, name: v.trim() } }).then(r => {
          let item = r.items.length > 0 ? r.items[0] : null
          // 如果当前组织下已存在该阶段，则更新使用次数，方便排序，否则插入数据
          // 定义整数最大值 2^31-1
          let MAX_INTEGER = 2147483647
          if (item) {
            let times = item.times || 0
            times < MAX_INTEGER ? times++ : times
            mongo.db().collection('plan.content.stage').update({ filter: { _id: item._id }, update: { $set: { times: times } } })
          } else {
            mongo.db().collection('plan.content.stage').insert({ name: v.trim(), tenant: { $oid: this.tenantId }, times: 1 })
          }
        })
      }
    },
    formatDate (object) {
      if (!object || !object.$numberLong) return null
      return moment(Number(object.$numberLong)).format('YYYY-MM-DD')
    },
    // 十进制转16进制
    decToHex (dec) {
      let hex = (dec).toString(16)
      while (hex.length < 6) {
        hex = '0' + hex
      }
      return hex
    },
    disposeModalDialog (v) {
      switch (v) {
        case 'IS_NONE':
          // 清空变量
          this.$nextTick(() => {
            this.stage = null
            this.customStages = []
            this.startOnStr = null
            this.endOnStr = null
            this.startdatetime = null
            this.enddatetime = null
            this.selectedColor = null
            this.planContent = {}
          })
          break
        case 'IS_ADD':
          this.dialogTitle = '添加计划内容'
          // 清空变量
          this.$nextTick(() => {
            this.stage = null
            this.customStages = []
            this.startOnStr = null
            this.endOnStr = null
            this.startdatetime = null
            this.enddatetime = null
            // 设置默认颜色
            this.selectedColor = this.colors[0].hex
            this.planContent = {}
          })
          // 设置所属计划
          this.queryPlanById(this.planId).then(res => {
            this.planList = res.items.map((plan) => {
              return Object.assign(plan, { id: plan['_id']['$oid'] })
            })
            if (this.planList.length > 0) {
              this.curPlanId = this.planList[0]._id.$oid
            }
          })
          // 绑定阶段列表
          let filter = {}
          filter.tenant = { $oid: this.tenantId }
          filter._removed = { $exists: false }
          let sort = { times: -1 }
          mongo.db().collection('plan.content.stage').find({ filter: filter, sort: sort }).then(res => {
            this.customStages = res.items.map((item) => {
              return item.name
            })
          })
          break
        case 'IS_EDIT':
          this.dialogTitle = '编辑计划内容'
          // 清空变量
          this.$nextTick(() => {
            this.stage = null
            this.customStages = []
            this.startOnStr = null
            this.endOnStr = null
            this.startdatetime = null
            this.enddatetime = null
            this.selectedColor = null
            this.planContent = {}
          })
          // 根据当前计划内容ID查询计划内容
          this.queryPlanContentById().then(data => {
            if (data.items.length > 0) {
              this.planContent = data.items[0]
              this.selectedColor = this.planContent.color ? this.decToHex(Number(this.planContent.color.$numberLong)).toUpperCase() : this.colors[0].hex
              this.stage = this.planContent.stage ? this.planContent.stage.trim() : null
              this.startOnStr = this.formatDate(this.planContent.startOn)
              this.endOnStr = this.formatDate(this.planContent.endOn)
              // 绑定阶段列表
              let filter = {}
              filter.tenant = { $oid: this.tenantId }
              filter._removed = { $exists: false }
              let sort = { times: -1 }
              mongo.db().collection('plan.content.stage').find({ filter: filter, sort: sort }).then(res => {
                this.customStages = res.items.map((item) => {
                  return item.name
                })
              })
              // 查询计划
              this.queryPlanById(this.planContent.plan.$oid).then(res => {
                this.planList = res.items.map((plan) => {
                  return Object.assign(plan, { id: plan['_id']['$oid'] })
                })
                if (this.planList.length > 0) {
                  this.curPlanId = this.planList[0]._id.$oid
                }
              })
            }
          })
          break
        case 'IS_COPY':
          this.dialogTitle = '复制计划内容'
          // 清空变量
          this.$nextTick(() => {
            this.stage = null
            this.customStages = []
            this.startOnStr = null
            this.endOnStr = null
            this.startdatetime = null
            this.enddatetime = null
            this.selectedColor = null
            this.planContent = {}
          })
          // 根据当前计划内容ID查询计划内容
          this.queryPlanContentById().then(data => {
            if (data.items.length > 0) {
              this.planContent = data.items[0]
              this.selectedColor = this.planContent.color ? this.decToHex(Number(this.planContent.color.$numberLong)).toUpperCase() : this.colors[0].hex
              this.stage = this.planContent.stage ? this.planContent.stage.trim() : null
              this.startOnStr = this.formatDate(this.planContent.startOn)
              this.endOnStr = this.formatDate(this.planContent.endOn)
              // 绑定阶段列表
              let filter = {}
              filter.tenant = { $oid: this.tenantId }
              filter._removed = { $exists: false }
              let sort = { times: -1 }
              mongo.db().collection('plan.content.stage').find({ filter: filter, sort: sort }).then(res => {
                this.customStages = res.items.map((item) => {
                  return item.name
                })
              })
              // 查询计划
              this.queryPlanById(this.planContent.plan.$oid).then(res => {
                this.planList = res.items.map((plan) => {
                  return Object.assign(plan, { id: plan['_id']['$oid'] })
                })
                if (this.planList.length > 0) {
                  this.curPlanId = this.planList[0]._id.$oid
                }
              })
            }
          })
          break
        default:
          this.dialogTitle = ''
          break
      }
      this.isShowDialog = this.addOrEdit[v]
    }
  }
}
</script>
<style scoped>
.color1 {
  cursor: pointer;
  background-color: #0fc15e !important;
  border-color: #0fc15e !important;
}
.color2 {
  cursor: pointer;
  background-color: #e9d216 !important;
  border-color: #e9d216 !important;
}
.color3 {
  cursor: pointer;
  background-color: #fd6691 !important;
  border-color: #fd6691 !important;
}
.color4 {
  cursor: pointer;
  background-color: #ffab19 !important;
  border-color: #ffab19 !important;
}
.color5 {
  cursor: pointer;
  background-color: #ff473d !important;
  border-color: #ff473d !important;
}
.color6 {
  cursor: pointer;
  background-color: #6c777d !important;
  border-color: #6c777d !important;
}
.color7 {
  cursor: pointer;
  background-color: #94d201 !important;
  border-color: #94d201 !important;
}
.color8 {
  cursor: pointer;
  background-color: #32d2ea !important;
  border-color: #32d2ea !important;
}
.color9 {
  cursor: pointer;
  background-color: #af65e0 !important;
  border-color: #af65e0 !important;
}
.color10 {
  cursor: pointer;
  background-color: #3396fb !important;
  border-color: #3396fb !important;
}
.color11 {
  cursor: pointer;
  background-color: #fe5f57 !important;
  border-color: #fe5f57 !important;
}
.color12 {
  cursor: pointer;
  background-color: #7f57c1 !important;
  border-color: #7f57c1 !important;
}
.color13 {
  cursor: pointer;
  background-color: #00b6c4 !important;
  border-color: #00b6c4 !important;
}
.color14 {
  cursor: pointer;
  background-color: #c18e6f !important;
  border-color: #c18e6f !important;
}
.color15 {
  cursor: pointer;
  background-color: #bcaba4 !important;
  border-color: #bcaba4 !important;
}
</style>
