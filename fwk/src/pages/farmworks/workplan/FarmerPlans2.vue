<template>
  <v-container fluid
               pa-0
               pb-4>
    <v-layout v-if="isLoading">
    </v-layout>
    <v-layout v-else-if="isHasPlan"
              row>
      <v-flex xs12>
        <v-card class="page-content mb-2"
                flat>
          <v-layout row>
            <v-flex xs2>
              <div class="text-xs-center hideoverflow primary btn-planList"
                   v-ripple
                   :title="this.latestPlan.name"
                   @click="showPlanList">
                {{this.latestPlan.name}}
              </div>
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs12>
              <v-layout row>
                <v-flex xs2>
                  <v-subheader class="pl-0">适用品类：{{this.latestPlan.categoryName}}</v-subheader>
                </v-flex>
                <v-flex xs3>
                  <v-subheader class="pl-0">发布人：{{this.latestPlan.creatorName}}</v-subheader>
                </v-flex>
                <v-flex xs7>
                  <v-subheader class="pl-0">更新时间：{{this.latestPlan.updatedOnStr}}</v-subheader>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
        </v-card>
        <v-card class="page-content">
          <v-layout row
                    wrap>
            <v-flex class="toolWrapper"
                    xs4
                    text-xs-left>
              <v-btn-toggle v-model="text">
                <v-btn flat
                       @click="cutYear"
                       value="year">
                  年
                </v-btn>
                <v-btn flat
                       @click="cutMonth"
                       value="month">
                  月
                </v-btn>
                <v-btn flat
                       @click="cutWeek"
                       value="week">
                  周
                </v-btn>
                <v-btn flat
                       @click="cutDay"
                       value="day">
                  日
                </v-btn>
              </v-btn-toggle>
            </v-flex>
            <v-flex xs3
                    text-xs-center
                    class="titleWrapper">
              <v-layout row
                        justify-center>
                <v-icon @click="prev">keyboard_arrow_left</v-icon>
                <h2>{{title}}</h2>
                <v-icon @click="next">keyboard_arrow_right</v-icon>
              </v-layout>
            </v-flex>
            <v-flex xs3
                    offset-xs1>
              <v-text-field v-model="keyword"
                            label="输入计划内容进行搜索"
                            append-icon="search"
                            clearable
                            single-line />
            </v-flex>
            <v-flex xs1
                    class="addWrapper"
                    text-xs-right>
              <v-btn small
                     fab
                     dark
                     color="red darken-2"
                     @click="addPlanContent"
                     title="添加计划内容"
                     v-if="supportUserId === curCreator">
                <v-icon>add</v-icon>
              </v-btn>
            </v-flex>
            <v-flex xs12
                    v-if="$authed('GET','farmworks/farmerplans')">
              <div v-if="!viewType">
                <full-calendar ref="calendar"
                               :events="events"
                               :config="config"
                               @event-selected="clickOtherViewPlan" />
              </div>
              <div v-if="viewType">
                <year-view :events="events"
                           @my-event="updateTitle"
                           :mountType="mountedType"
                           @event-view="clickPlan"
                           ref="yearView"
                           v-if="isShowPlanContent"></year-view>
              </div>
            </v-flex>
            <!-- <v-flex xs12
                    v-else>
              <full-calendar ref="calendar"
                             :events="events"
                             :config="config" />
            </v-flex> -->
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <v-layout v-else
              row>
      <v-flex xs12>
        <v-card class="page-content">
          <v-layout class="defaultbox">
            <v-flex xs12
                    class="child">
              <img src="static/plan/default.png"
                   style="width:73px;height:82px;" />
              <span style="font-size:14px;height:20px;color:#999999;font-family:Microsoft Yahei;display:block">暂无计划数据！</span>
              <v-btn color="primary"
                     style="border-radius: 4px;width: 90px;margin-top: 10px;"
                     @click.stop="addPlan"
                     v-if="$authed('POST','farmworks/farmerplans')">点击添加</v-btn>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>
    <v-farmerplansadd :visible.sync="addOrEditPlanDialog"
                      :title="addOrEditPlanDialogTitle"
                      :categorylist="categorys"
                      :userlist="users"
                      :planid="curPlanId"
                      :isdefaultenter="true"
                      :refresh-fn="refreshData"></v-farmerplansadd>
    <v-farmerplanslist :planListDialog.sync="isShowPlanListDialog"
                       :categorylist="categorys"
                       :userlist="users"></v-farmerplanslist>
    <v-farmerplancontentview :plan-content-view-dialog.sync="isShowPlanContentViewDialog"
                             :plan-content-id="curPlanContentId"></v-farmerplancontentview>
    <v-farmerplancontentadd :plan-content-add-or-edit-dialog.sync="planContentAddOrEditDialog"
                            :plan-content-add-or-edit-dialog-title="planContentAddOrEditDialogTitle"
                            :plan-id="curPlanId"></v-farmerplancontentadd>

  </v-container>
</template>
<script>
import { FullCalendar } from 'vue-full-calendar'
import debounce from 'throttle-debounce/debounce'
import YearView from '@/pages/farmworks/workplan/calendar/YearView'
import moment from 'moment'
import mongo from '@vehiclecloud/mongo-rest-client'
import { Toast } from '@/models/toast'
import VFarmerplansadd from './FarmerPlansAdd.vue'
import VFarmerplanslist from './FarmerPlansList.vue'
import VFarmerplancontentview from './FarmerPlanContentView.vue'
import VFarmerplancontentadd from './FarmerPlanContentAdd.vue'
import { MessageBox } from 'element-ui'
export default {
  data: function () {
    let that = this
    return {
      title: '',
      mountedType: 0,
      isShowPlanContent: false,
      text: 'year',
      isYearView: true,
      viewType: true,
      isLoading: true,
      isHasPlan: false,
      keyword: null,
      that: null,
      events: [],
      eventsMonth: [
        {
          'begintime': '2018-07-10 00:00:00',
          'begintimestr': '2018-7-10 12:00:00',
          'createuserid': 262,
          'describe': '计划内容1',
          'endtime': '2018-07-20 00:00:00',
          'endtimestr': '2018-7-20 12:00:00',
          'id': 127,
          'title': '计划内容1',
          'allDay': true,
          'color': 'red'
        },
        {
          'begintime': '2018-08-10 00:00:00',
          'begintimestr': '2018-8-10 12:00:00',
          'createuserid': 262,
          'describe': '计划内容二',
          'endtime': '2018-08-10 00:00:00',
          'endtimestr': '2018-8-10 12:00:00',
          'id': 128,
          'title': '计划内容二',
          'color': 'yellow'
        },
        {
          'begintime': '2018-08-14 00:00:00',
          'begintimestr': '2018-8-14 12:00:00',
          'createuserid': 262,
          'describe': '计划内容三',
          'endtime': '2018-08-31 00:00:00',
          'endtimestr': '2018-8-31 12:00:00',
          'id': 129,
          'title': '计划内容三',
          'color': 'red'
        },
        {
          'begintime': '2018-08-24 00:00:00',
          'begintimestr': '2018-8-24 12:00:00',
          'createuserid': 382,
          'describe': '计划内容四',
          'endtime': '2018-08-24 00:00:00',
          'endtimestr': '2018-8-24 12:00:00',
          'hasreaded': 1,
          'id': 130,
          'title': '计划内容四',
          'color': 'orange'
        },
        {
          'begintime': '2017-08-24 00:00:00',
          'begintimestr': '2017-8-24 12:00:00',
          'createuserid': 382,
          'describe': '跨年一',
          'endtime': '2018-08-24 00:00:00',
          'endtimestr': '2018-8-24 12:00:00',
          'hasreaded': 1,
          'id': 131,
          'title': '跨年一',
          'color': '#32c787'
        },
        {
          'begintime': '2017-06-24 00:00:00',
          'begintimestr': '2017-6-24 12:00:00',
          'createuserid': 382,
          'describe': '跨年二',
          'endtime': '2019-08-24 00:00:00',
          'endtimestr': '2019-8-24 12:00:00',
          'hasreaded': 1,
          'id': 132,
          'title': '跨年二',
          'color': 'green'
        }
      ],
      /**
       * 日历组件配置项
       */
      config: {
        locale: 'zh_CN', // 中文
        defaultView: 'month', // 默认视图 - 月
        // businessHours: true,
        header: { left: '', center: '', right: '' },
        editable: false,
        eventClick: (event) => {
          that.clickOtherViewPlan(event)
        },
        viewRender: function (view) {
          if (view.type === 'month' && that.isYearView === false) {
            let title = view.title.split(' ')
            let calendarDate = that.$refs.calendar.fireMethod('getDate')
            let calendarMonth = calendarDate.month() + 1
            that.title = title[1] + '年' + calendarMonth + '月'
          } else if (that.isYearView === false) {
            that.title = view.title
          }
          // let calendar = that.$refs.calendar.fireMethod('getDate')
          // switch (view.type) {
          //   case 'month':
          //     that.begintime = calendar.startOf('month').format('YYYY-MM-DD')
          //     that.endtime = calendar.endOf('month').format('YYYY-MM-DD')
          //     break
          //   case 'basicWeek':
          //     that.begintime = calendar.startOf('week').format('YYYY-MM-DD')
          //     that.endtime = calendar.endOf('week').format('YYYY-MM-DD')
          //     break
          //   case 'basicDay':
          //     that.begintime = calendar.startOf('day').format('YYYY-MM-DD')
          //     that.endtime = calendar.endOf('day').format('YYYY-MM-DD')
          //     break
          // }
        }
      },
      curPlanId: null,
      latestPlan: {},
      curCreator: null,
      plans: [],
      categorys: [],
      users: [],
      addOrEditPlanDialog: 'IS_NONE',
      addOrEditPlanDialogTitle: '',
      isShowPlanListDialog: false,
      isShowPlanContentViewDialog: false,
      plantContentViewDialogTitle: '',
      curPlanContentId: null,
      planContentAddOrEditDialog: 'IS_NONE',
      planContentAddOrEditDialogTitle: ''

    }
  },
  watch: {
    keyword: debounce(600, function (val) {
      this.keyword = val
      this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
    })
  },
  components: {
    FullCalendar,
    YearView,
    VFarmerplansadd,
    VFarmerplanslist,
    VFarmerplancontentview,
    VFarmerplancontentadd
  },
  created () {
    this.toast = new Toast(this, null, null)
  },
  mounted () {
    console.log(1111111)
    this.isValidUser()
    this.isYearView = true
    this.viewType = true
    this.title = `${moment().year()}年`
    this.$store.state.planYear = null
    this.getCategory()
    this.getUserInfo()
    this.getPlan()
    this.setBackground()
    console.log(this.$refs.yearView)
  },
  updated () {
    this.setBackground()
  },
  computed: {
    supportUserId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    },
    tenantId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  methods: {

    // 判断当前登录用户是否有效
    isValidUser () {
      let user = global.helper.ls.get('user')
      console.log(user)
      if (!user) return this.showMessageBox()
      mongo.db('iss').collection('tenant.staff').find({ filter: { _id: { $oid: user.supportid } } }).then(res => {
        if (res.items.length > 0) {
          let tmpUser = res.items[0]
          let dbUpdateTime = Number(tmpUser.updatedOn.$numberLong)
          let curUpdateTime = Number(user.operateredittime)
          if (dbUpdateTime > curUpdateTime) {
            this.showMessageBox()
          }
        } else {
          this.showMessageBox()
        }
      })
    },
    showMessageBox () {
      MessageBox.alert('用户信息已变更,请重新登录！', '提示', {
        confirmButtonText: '确定',
        callback: action => {
          let user = null
          let bread = []
          this.$store.commit('setUser', user)
          this.$store.commit('setBreadList', bread)
          this.$router.push({ name: 'login' })
        }
      })
    },
    // 获取品类信息
    async getCategory () {
      let filter = {}
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let allCategorys = await mongo.db('iss').collection('base.category').aggregate([{ $match: filter }])
      this.categorys = allCategorys.map((item) => {
        return Object.assign(item, { id: item['_id'].$oid })
      })
    },

    // 获取用户信息
    async getUserInfo () {
      let match = { _id: { $oid: this.tenantId } }
      let graphLookup = { from: 'sys.tenant', startWith: '$_id', connectFromField: '_id', connectToField: 'parent', as: 'children' }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [{ $match: match }, { $graphLookup: graphLookup }, { $unwind: unwind }, { $project: project }]
      // 查询当前组织下所有部门
      let allDep = await mongo.db('iss').collection('sys.tenant').aggregate(aggregate)
      let allTenantId = allDep.map((dep) => {
        return dep._id
      })
      allTenantId.push({ $oid: this.tenantId })
      // 查询当前组织及其下所有部门下的人员
      let filter = {}
      filter.tenants = { $in: allTenantId }
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let allUser = await mongo.db('iss').collection('tenant.staff').aggregate([{ $match: filter }])
      this.users = allUser.map((item) => {
        return Object.assign(item, { id: item['_id'].$oid, name: item['name'] || item['mobile'] })
      })
    },

    /**
     * 获取当前用户在其所属组织下创建或参与的最新一条计划
     */
    async getPlan () {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.tenant = { $oid: this.tenantId }
        filter.participators = { $in: [{ $oid: this.supportUserId }] }
        filterParam.sort = { updatedOn: -1 }
        filterParam.skip = 0
        filterParam.limit = 1
        filterParam.filter = filter
        let { items } = await mongo.db().collection('plan').find(filterParam)
        this.isHasPlan = items.length > 0
        this.isLoading = false
        if (this.categorys.length === 0) {
          await this.getCategory()
        }
        if (this.users.length === 0) {
          await this.getUserInfo()
        }
        if (items.length > 0) {
          this.bindPlan(items)
          this.mountedType = 1
          this.getPlanContent(items[0]._id.$oid, this.keyword)
        }
      } catch (err) {
        console.log(err)
        this.isHasPlan = false
      } finally {
        this.toast.close()
      }
    },

    async getPlanById (id) {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.tenant = { $oid: this.tenantId }
        filter._id = { $oid: id }
        filterParam.filter = filter
        let { items } = await mongo.db().collection('plan').find(filterParam)
        this.isHasPlan = items.length > 0
        this.isLoading = false
        if (this.categorys.length === 0) {
          await this.getCategory()
        }
        if (this.users.length === 0) {
          await this.getUserInfo()
        }
        if (items.length > 0) {
          this.bindPlan(items)
          this.mountedType = 1
          await this.getPlanContent(items[0]._id.$oid, this.keyword)
        }
      } catch (err) {
        console.log(err)
        this.isHasPlan = false
      } finally {
        this.toast.close()
      }
    },

    // 按计划ID查询当前计划下所包含的计划内容
    async getPlanContent (planId, keywords) {
      if (!planId) {
        this.events = []
        return
      }
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.plan = { $oid: planId }
        if (keywords && keywords.trim() !== '') {
          filter.content = { $regex: this.escapeCharacter(keywords.trim()) }
        }
        filterParam.filter = filter
        filterParam.sort = { updatedOn: -1 }
        let { items } = await mongo.db().collection('plan.content').find(filterParam)
        let planContents = items
        let newPlanContents = []
        for (let plantContent of planContents) {
          let tmpPlanContent = {}
          tmpPlanContent.id = plantContent._id.$oid
          tmpPlanContent.title = plantContent.content
          tmpPlanContent.allDay = true
          // 年视图
          tmpPlanContent.begintime = moment(Number(plantContent.startOn.$numberLong)).format('YYYY-MM-DD')
          tmpPlanContent.endtime = moment(Number(plantContent.endOn.$numberLong)).format('YYYY-MM-DD')
          // 月视图
          tmpPlanContent.start = moment(Number(plantContent.startOn.$numberLong)).format('YYYY-MM-DD')
          tmpPlanContent.end = moment(Number(plantContent.endOn.$numberLong)).add(1, 'days').format('YYYY-MM-DD')
          tmpPlanContent.color = plantContent.color ? `#${this.decToHex(Number(plantContent.color.$numberLong))}` : '#0FC15E'
          newPlanContents.push(tmpPlanContent)
        }
        this.isShowPlanContent = true
        this.events = newPlanContents
        console.log(this.events)
      } catch (err) {
        console.log(err)
        this.events = []
      } finally {
        this.toast.close()
      }
    },

    // 绑定计划数据
    async bindPlan (planArray) {
      this.plans = []
      this.latestPlan = {}
      for (let plan of planArray) {
        plan.id = plan._id.$oid
        plan.categoryName = plan.category ? this.getCategoryNameById(plan.category.$oid) : '--'
        plan.creatorName = plan.creator ? await this.getUserNameById(plan.creator.$oid) : '--'
        plan.updatedOnStr = plan.updatedOn ? moment(Number(plan.updatedOn.$numberLong)).format('YYYY-MM-DD HH:mm') : '--'
        this.plans.push(plan)
      }
      if (this.plans.length > 0) {
        this.latestPlan = this.plans[0]
        this.curCreator = this.plans[0].creator.$oid
      }
    },

    showPlanList () {
      this.isShowPlanListDialog = true
    },

    addPlan () {
      this.addOrEditPlanDialog = 'IS_ADD'
      this.addOrEditPlanDialogTitle = '新建计划'
    },
    addPlanContent () {
      this.curPlanId = this.latestPlan._id.$oid
      this.planContentAddOrEditDialog = 'IS_ADD'
      this.planContentAddOrEditDialogTitle = '添加计划内容'
    },

    editPlan (planId) {
      if (!planId) return
      this.curPlanId = planId
      this.addOrEditPlanDialog = 'IS_EDIT'
      this.addOrEditPlanDialogTitle = '编辑计划'
    },
    copyPlan (planId) {
      if (!planId) return
      this.curPlanId = planId
      this.addOrEditPlanDialog = 'IS_COPY'
      this.addOrEditPlanDialogTitle = '复制计划'
    },
    // 点击年视图中的计划内容
    clickPlan (id) {
      this.curPlanContentId = id
      this.isShowPlanContentViewDialog = true
      this.plantContentViewDialogTitle = '计划内容'
    },
    // 点击除年视图以外的其他视图中的计划内容
    clickOtherViewPlan (e) {
      this.curPlanContentId = e.id
      this.isShowPlanContentViewDialog = true
      this.plantContentViewDialogTitle = e.title
    },

    // 根据ID获取用户名称
    async getUserNameById (id) {
      let user = this.users.find(function (elem) {
        return elem._id.$oid === id
      })
      // return user ? user.name : '--'
      if (user) return user.name
      // 处理当用户被切换过组织后查询不到的情况
      let filter = {}
      filter._id = { $oid: id }
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let res = await mongo.db('iss').collection('tenant.staff').aggregate([{ $match: filter }])
      return res.length > 0 ? res[0].name : '--'
    },

    // 根据ID获取品类名称
    getCategoryNameById (id) {
      let category = this.categorys.find(function (elem) {
        return elem._id.$oid === id
      })
      return category ? category.name : '--'
    },

    // 按计划ID刷新计划年视图
    refreshData (id) {
      if (!id) return
      this.getPlanById(id)
    },

    // 计划列表界面删除计划后刷新计划年视图
    refreshAfterDelete (id) {
      id === this.latestPlan._id.$oid ? this.getPlan() : null
    },

    // 十进制转16进制
    decToHex (dec) {
      let hex = (dec).toString(16)
      while (hex.length < 6) {
        hex = '0' + hex
      }
      return hex
    },

    // 转义字符串：判断字符串中是否包含正则表达式需要转义的特殊字符，如果存在，则加上转义字符后返回
    escapeCharacter (str) {
      if (!str) return
      let result = []
      let tmpStrs = str.toString().split('')
      let specialChars = ['$', '(', ')', '*', '+', '.', '?', '\\', '/', '^', '{', '}', '|']
      for (let char of tmpStrs) {
        let tmp = specialChars.find((item) => {
          return item === char
        })
        result.push(tmp ? '\\' + char : char)
      }
      return result.join('')
    },

    updateTitle (data) {
      this.title = `${data}年` // 改变了父组件的值
    },

    /**
     * 日期往前
     */
    prev () {
      if (this.isYearView === true) {
        this.mountedType = 1
        this.$refs.yearView.prev()
        this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
      } else {
        this.$refs.calendar.fireMethod('prev')
        this.setBackground()
        this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
      }
    },

    /**
     * 日期往后
     */
    next () {
      if (this.isYearView === true) {
        this.mountedType = 1
        this.$refs.yearView.next()
        this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
      } else {
        this.$refs.calendar.fireMethod('next')
        this.setBackground()
        this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
      }
    },
    /**
     * 切换 年 视图
     */
    cutYear () {
      this.keyword = null
      this.isYearView = true
      this.viewType = true
      this.mountedType = 1
      this.$store.state.planYear = null
      this.title = `${moment().year()}年`
      this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
    },

    /**
     * 切换 月 视图
     */
    cutMonth () {
      this.events = []
      this.keyword = null
      this.isYearView = false
      this.viewType = false
      this.$store.state.planYear = null
      this.$nextTick(() => {
        this.$refs.calendar.fireMethod('changeView', 'month')
        this.$refs.calendar.fireMethod('rerenderEvents')
      })
      this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
    },

    /**
     * 切换 周 视图
     */
    cutWeek () {
      this.events = []
      this.keyword = null
      this.isYearView = false
      this.viewType = false
      this.$store.state.planYear = null
      this.$nextTick(() => {
        this.$refs.calendar.fireMethod('changeView', 'basicWeek')
      })
      this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
    },

    /**
     * 切换 日 视图
     */
    cutDay () {
      this.events = []
      this.keyword = null
      this.isYearView = false
      this.viewType = false
      this.$store.state.planYear = null
      this.$nextTick(() => {
        this.$refs.calendar.fireMethod('changeView', 'basicDay')
      })
      this.getPlanContent(this.latestPlan._id.$oid, this.keyword)
    },

    /**
     * 切换月份时设置背景图片
     */
    setBackground () {
      if (this.$refs.calendar) {
        let calendarDate = this.$refs.calendar.fireMethod('getDate')
        let calendarMonth = calendarDate.month()
        let abc = document.getElementsByClassName('fc-toolbar')
        abc.data_calendar_month = calendarMonth
        this.$refs.calendar.$el.children[0].setAttribute('data-calendar-month', calendarMonth)
      }
    }
  }
}
</script>
<style scoped>
@import "~fullcalendar/dist/fullcalendar.css";
.toolWrapper > .icon:not(:first-child) {
  margin-left: 10px;
}
.icon {
  cursor: pointer;
}
.toolWrapper {
  padding-top: 20px;
}
.addWrapper {
  padding-top: 5px;
}
.titleWrapper {
  padding-top: 13px;
}
.defaultbox {
  position: relative;
  height: 650px;
  margin: 20px auto;
}
.defaultbox .child {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  margin: auto;
  text-align: center;
  width: 300px;
  height: 200px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.btn-planList {
  width: 298px;
  color: white;
  height: 31px;
  line-height: 31px;
  padding-left: 5px;
  cursor: pointer;
  font-weight: bold;
  border-radius: 4px;
  -moz-user-select: none;
  -khtml-user-select: none;
  user-select: none;
  box-shadow: 1px 1px 5px #888888;
}
</style>
