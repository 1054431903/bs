<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow.sync="isShowDialog"
                   :title="planContent.content||dialogTitle"
                   :showCloseBtn="true"
                   :modalMaxWidth="650"
                   :bodyHeight="200">
      <template slot="toolbarItem">
        <v-btn fab
               icon
               small
               title="编辑"
               style="margin-right:-10px;"
               :disabled="supportUserId !== creatorId"
               @click.stop="editPlanContent()">
          <v-icon>create</v-icon>
        </v-btn>
        <v-btn fab
               icon
               small
               title="复制"
               style="margin-right:-10px;"
               :disabled="supportUserId !== creatorId"
               @click.stop="copyPlanContent()">
          <v-icon>library_books</v-icon>
        </v-btn>
        <v-btn fab
               icon
               small
               title="删除"
               style="margin-right:-10px;"
               :disabled="supportUserId !== creatorId"
               @click.stop="deletePlanContent()">
          <v-icon color="red">delete</v-icon>
        </v-btn>
      </template>
      <v-layout row
                wrap
                mb-2>
        <v-flex xs2>
          内容描述：
        </v-flex>
        <v-flex xs10>
          {{planContent.remark}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                mb-2>
        <v-flex xs2>
          阶&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段：
        </v-flex>
        <v-flex xs10>
          {{planContent.stage}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                mb-2>
        <v-flex xs2>
          计划时间：
        </v-flex>
        <v-flex xs10
                v-if="planContent.startOnStr || planContent.endOnStr">
          {{planContent.startOnStr}} &nbsp;至&nbsp; {{ planContent.endOnStr }}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                mb-2>
        <v-flex xs2>
          所属计划：
        </v-flex>
        <v-flex xs10>
          {{planContent.planName}}
        </v-flex>
      </v-layout>
    </v-modaldialog>
    <v-modaldialog :isShow.sync="prompt.dialog"
                   :title="prompt.title"
                   :showCloseBtn="true"
                   :modalMaxWidth="400"
                   :bodyHeight="100">
      <div>{{ prompt.body }}</div>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions class="text-xs-right">
          <v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native.stop="Esc">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click.native.stop="Ok">确定</v-btn>
          </v-spacer>
        </v-card-actions>
      </template>
    </v-modaldialog>
    <v-farmerplancontentadd :plan-content-add-or-edit-dialog.sync="planContentAddOrEditDialog"
                            :plan-content-add-or-edit-dialog-title="planContentAddOrEditDialogTitle"
                            :plan-content-id="planContentId"></v-farmerplancontentadd>
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import moment from 'moment'
import VFarmerplancontentadd from './FarmerPlanContentAdd.vue'
export default {
  name: 'v-farmerplancontentview',
  props: {
    planContentViewDialog: {
      type: Boolean,
      default: false
    },
    planContentId: {
      type: String,
      default: ''
    }
  },
  components: {
    VFarmerplancontentadd
  },
  data () {
    return {
      isShowDialog: false,
      dialogTitle: '',
      planContentAddOrEditDialog: 'IS_NONE',
      planContentAddOrEditDialogTitle: '添加计划内容',
      planContent: {},
      // 当前计划创建人
      creatorId: null,
      prompt: {
        dialog: false,
        title: '计划内容删除提示',
        body: '计划内容删除后将无法恢复，是否确认删除？'
      }
    }
  },
  computed: {
    supportUserId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    }
  },
  watch: {
    planContentViewDialog (v) {
      this.isShowDialog = v
    },
    isShowDialog (v) {
      v ? this.queryPlanContentById(this.planContentId) : null
      this.$emit('update:planContentViewDialog', v)
    }
  },
  methods: {
    async queryPlanContentById (contentId) {
      this.planContent = {}
      if (!contentId) return null
      let filter = {}
      filter._id = { $oid: contentId }
      filter._removed = { $exists: false }
      let { items } = await mongo.db().collection('plan.content').find({ filter: filter })
      if (items.length > 0) {
        let tmpPlanContent = items[0]
        tmpPlanContent.content = tmpPlanContent.content ? tmpPlanContent.content : '--'
        tmpPlanContent.remark = tmpPlanContent.remark ? tmpPlanContent.remark : '--'
        tmpPlanContent.stage = tmpPlanContent.stage ? tmpPlanContent.stage : '--'
        // 格式化显示时间
        tmpPlanContent.startOnStr = tmpPlanContent.startOn ? moment(Number(tmpPlanContent.startOn.$numberLong)).format('YYYY-MM-DD') : '--'
        tmpPlanContent.endOnStr = tmpPlanContent.endOn ? moment(Number(tmpPlanContent.endOn.$numberLong)).format('YYYY-MM-DD') : '--'
        let planid = items[0].plan
        let planData = await mongo.db().collection('plan').find({ filter: { _id: planid, _removed: { $exists: false } } })
        if (planData.items.length > 0) {
          let plan = planData.items[0]
          tmpPlanContent.planName = plan.name ? plan.name : '--'
          this.creatorId = plan.creator ? plan.creator.$oid : null
        }
        this.planContent = tmpPlanContent
      }
    },
    Ok () {
      // 删除计划内容，并更新其所属计划的修改时间
      mongo.db().collection('plan.content').remove({ filter: { _id: { $oid: this.planContentId } } }).then(res => {
        if (!res.writeError && !res.writeConcernError) {
          this.prompt.dialog = false
          this.isShowDialog = false
          // 更新计划修改时间(由于当前mongorest封装的原因，无法修改数据的更新时间为指定值，故在此写成更新一个空属性来达到更新修改时间的目的)
          mongo.db().collection('plan').update({ filter: { _id: this.planContent.plan }, update: { $set: {} } }).then(data => {
            if (!res.writeError && !res.writeConcernError) {
              // 更新计划内容显示
              // this.queryPlanContentById(this.planContent.plan.$oid)
              // 更新计划年视图
              // this.$parent.getPlanContent(this.planContent.plan.$oid)
              this.$parent.getPlan()
            }
          })
        }
      })
    },
    Esc () {
      this.prompt.dialog = false
      this.isShowDialog = false
      // // 更新计划年视图
      // this.$parent.getPlan()
    },
    editPlanContent () {
      if (!this.planContent) return
      this.planContentAddOrEditDialog = 'IS_EDIT'
      this.planContentAddOrEditDialogTitle = '编辑计划内容'
    },
    copyPlanContent () {
      if (!this.planContent) return
      this.planContentAddOrEditDialog = 'IS_COPY'
      this.planContentAddOrEditDialogTitle = '复制计划内容'
    },
    deletePlanContent () {
      if (!this.planContent) return
      this.prompt.dialog = true
      this.prompt.title = '计划内容删除提示'
      this.prompt.body = '计划内容删除后将无法恢复，是否确认删除？'
    },
    // 子组件操作完成后调用的刷新数据的方法
    refreshParentData (planid) {
      if (!planid) return
      this.$parent.getPlanById(planid)
    }
  }
}
</script>
