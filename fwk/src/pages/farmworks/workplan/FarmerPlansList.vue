<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow.sync="isShowDialog"
                   title="计划列表"
                   :showCloseBtn="true"
                   :modalMaxWidth="1000"
                   :bodyHeight="490">
      <v-layout row
                wrap>
        <v-flex xs2>
          <v-autocomplete label="适用品类"
                          v-model="categoryId"
                          :items="curTenantCategoryList"
                          item-text="name"
                          item-value="id"
                          no-data-text="未过滤到匹配的数据"
                          multiple
                          clearable
                          single-line
                          hide-details>

          </v-autocomplete>
        </v-flex>
        <v-flex xs5
                ml-4>
          <v-text-field label="请输入计划名称进行搜索"
                        v-model="keywords"
                        append-icon="search"
                        clearable
                        single-line
                        hide-details
                        @keyup.enter="search"></v-text-field>
        </v-flex>
        <v-flex xs2
                mt-2
                ml-1>
          <v-btn color="primary"
                 @click="search">
            查询
          </v-btn>
        </v-flex>
        <v-flex x3
                mt-2
                text-xs-right
                v-if="$authed('POST','farmworks/farmerplans')">
          <v-btn small
                 fab
                 dark
                 color="red darken-2"
                 @click="addPlan"
                 title="新建计划">
            <v-icon>add</v-icon>
          </v-btn>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12>
          <span>共有计划：&nbsp;{{ sumItems }} &nbsp;个</span>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                py-2>
        <v-flex xs12>
          <v-data-table :headers="tableHeader"
                        :items="dataItems"
                        no-data-text="暂无数据"
                        no-results-text="未查询到符合条件的记录"
                        must-sort
                        hide-actions
                        disable-initial-sort
                        class="tableStyle">
            <template slot="items"
                      slot-scope="props">
              <tr style="cursor: pointer;"
                  @click="selectPlan(props.item.id)">
                <td class="text-xs-left hideoverflow"
                    style="max-width:200px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                    :title="props.item.name">
                  {{ props.item.name }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.categoryName">
                  {{ props.item.categoryName }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.creatorName">
                  {{ props.item.creatorName }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.updateTimeStr">
                  {{ props.item.updateTimeStr }}
                </td>
                <td class="text-xs-center operateMinWidth">
                  <v-btn fab
                         small
                         icon
                         :disabled="props.item.creatorId !== supportUserId"
                         title="编辑"
                         @click.stop="editPlan(props.item.id)"
                         v-if="$authed('PUT','farmworks/farmerplans')">
                    <v-icon>create</v-icon>
                  </v-btn>
                  <v-btn fab
                         small
                         icon
                         :disabled="props.item.creatorId !== supportUserId"
                         title="复制"
                         @click.stop="copyPlan(props.item.id)"
                         v-if="$authed('POST','farmworks/farmerplanscopy')">
                    <v-icon>library_books</v-icon>
                  </v-btn>
                  <v-btn fab
                         small
                         icon
                         :disabled="props.item.creatorId !== supportUserId"
                         title="删除"
                         @click.stop="deletePlan(props.item.id)"
                         v-if="$authed('DELETE','farmworks/farmerplans')">
                    <v-icon color="red">delete</v-icon>
                  </v-btn>
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12
                text-xs-center
                v-show="showPagination">
          <v-custompagination :pagination.sync="pagination"></v-custompagination>
        </v-flex>
      </v-layout>
      <v-farmerplansadd :visible.sync="addOrEditPlanDialog"
                        :title="addOrEditPlanDialogTitle"
                        :categorylist="categorylist"
                        :userlist="userlist"
                        :planid="curPlanId"
                        :refresh-fn="refreshData">
      </v-farmerplansadd>
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
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import { Toast } from '@/models/toast'
import moment from 'moment'
import VFarmerplansadd from './FarmerPlansAdd.vue'
export default {
  name: 'v-farmerplanslist',
  props: {
    // 当前窗口显示状态
    planListDialog: {
      type: Boolean,
      default: false
    },
    // 当前组织下所有人员对象列表
    userlist: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 当前系统所有品类列表
    categorylist: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  components: {
    VFarmerplansadd
  },
  data () {
    return {
      isShowDialog: false,
      // 当前页面品类下拉框中选择的品类ID列表
      categoryId: [],
      // 当前组织下所有计划选择过的品类列表(去重后)
      curTenantCategoryList: [],
      keywords: null,
      sumItems: 0,
      dataItems: [],
      tableHeader: [
        {
          text: '计划名称',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        {
          text: '适用品类',
          align: 'left',
          sortable: true,
          value: 'categoryName'
        },
        {
          text: '发布人',
          align: 'left',
          sortable: true,
          value: 'creatorName'
        },
        {
          text: '更新时间',
          align: 'left',
          sortable: true,
          value: 'updateTimeStr'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      showPagination: true,
      pagination: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 5
      },
      prompt: {
        dialog: false,
        title: '计划删除提示',
        body: '计划删除后将无法恢复，是否确认删除？'
      },
      curPlanId: null,
      addOrEditPlanDialog: 'IS_NONE',
      addOrEditPlanDialogTitle: '',
      // 是否删除操作
      isdelete: false
    }
  },
  watch: {
    planListDialog (v) {
      this.isShowDialog = v
    },
    isShowDialog (v) {
      if (v) {
        this.categoryId = []
        this.keywords = null
        this.pagination.page = 1
        this.pagination.rowsPerPage = 5
        this.queryCategoryInTenant()
        this.queryPlanList()
      }
      this.$emit('update:planListDialog', v)
    },
    categoryId (v) {
      this.categoryId = v
    },
    'pagination.page' (val) {
      this.queryPlanList()
    },

    'pagination.rowsPerPage' (val) {
      this.queryPlanList()
    }
  },
  created () {
    this.toast = new Toast(this, null, null)
  },
  mounted () {
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
    search () {
      this.isdelete = false
      this.queryPlanByCondition()
    },
    queryPlanByCondition () {
      this.pagination.page = 1
      this.pagination.rowsPerPage = 5
      this.queryPlanList()
    },
    addPlan () {
      this.addOrEditPlanDialog = 'IS_ADD'
      this.addOrEditPlanDialogTitle = '新建计划'
      this.isdelete = false
    },
    selectPlan (planid) {
      this.isShowDialog = false
      this.$parent.getPlanById(planid)
      this.isdelete = false
    },
    editPlan (planId) {
      if (!planId) return
      this.curPlanId = planId
      this.addOrEditPlanDialog = 'IS_EDIT'
      this.addOrEditPlanDialogTitle = '编辑计划'
      this.isdelete = false
    },
    copyPlan (planId) {
      if (!planId) return
      this.curPlanId = planId
      this.addOrEditPlanDialog = 'IS_COPY'
      this.addOrEditPlanDialogTitle = '复制计划'
      this.isdelete = false
    },
    deletePlan (planId) {
      if (!planId) return
      this.curPlanId = planId
      this.prompt.dialog = true
      this.prompt.title = '计划删除提示'
      this.prompt.body = '计划删除后将无法恢复，是否确认删除？'
      this.isdelete = true
    },
    refreshData () {
      this.queryPlanByCondition()
      this.queryCategoryInTenant()
    },
    Esc () {
      this.prompt.dialog = false
    },
    Ok () {
      // 删除当前计划，并把当前计划下的计划内容也一起删除
      mongo.db().collection('plan').remove({ filter: { _id: { $oid: this.curPlanId } } }).then(res => {
        if (!res.writeError && !res.writeConcernError) {
          this.prompt.dialog = false
          // 置空品类过滤条件
          this.categoryId = []
          this.keywords = null
          // 更新计划列表
          this.queryPlanByCondition()
          // 更新当前组织下的品类列表
          this.queryCategoryInTenant()
          // 更新计划年视图
          this.$parent.refreshAfterDelete(this.curPlanId)
          // 删除计划内容(暂时不删除计划内容，只要把计划删除了，它下面的计划内容也看不到)
          // mongo.db().collection('plan.content').remove({ filter: { plan: { $oid: this.curPlanId } }, multi: true }).then(response => {
          //   if (!response.writeError && !response.writeConcernError) {
          //     // 更新计划年视图
          //     // this.$parent.getPlanById(this.curPlanId)
          //     this.$parent.refreshAfterDelete(this.curPlanId)
          //   }
          // })
        }
      })
    },
    async queryPlanList () {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.tenant = { $oid: this.tenantId }
        filter.participators = { $in: [{ $oid: this.supportUserId }] }
        if (this.keywords && this.keywords.trim() !== '') {
          filter.name = { $regex: this.escapeCharacter(this.keywords.trim()) }
        }
        if (this.categoryId.length > 0) {
          let categorys = this.categoryId.map((item) => { return { $oid: item } })
          filter.category = { $in: categorys }
        }
        filterParam.filter = filter
        filterParam.sort = { updatedOn: -1 }
        filterParam.skip = (this.pagination.page - 1) * this.pagination.rowsPerPage
        filterParam.limit = this.pagination.rowsPerPage
        let data = await mongo.db().collection('plan').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.pagination.total = Number(data.total.$numberLong)
        this.bindPlan(items)
      } catch (err) {
        console.log(err)
      } finally {
        this.toast.close()
      }
    },
    async queryCategoryInTenant () {
      let filter = {}
      filter.tenant = { $oid: this.tenantId }
      filter.category = { '$exists': true }
      filter._removed = { '$exists': false }
      let projection = {}
      projection.category = 1
      let items = await mongo.db().collection('plan').aggregate([{ $match: filter }, { $project: projection }])
      let tmpCategoryIds = items.map((item) => {
        return item.category.$oid
      })
      // ID去重
      let categoryIds = [...new Set(tmpCategoryIds)]
      // 获取品类对象
      this.curTenantCategoryList = this.categorylist.filter(item => {
        return this._.find(categoryIds, (id) => {
          return item._id.$oid === id
        })
      })
      console.log(this.curTenantCategoryList)
    },
    // 绑定计划列表数据
    async bindPlan (items) {
      this.dataItems = []
      for (let item of items) {
        item.id = item._id.$oid
        item.categoryName = item.category ? this.queryCategoryNameById(item.category.$oid) : '--'
        item.creatorName = item.creator ? await this.queryUserNameById(item.creator.$oid) : '--'
        item.creatorId = item.creator ? item.creator.$oid : ''
        item.updateTimeStr = item.updatedOn ? moment(Number(item.updatedOn.$numberLong)).format('YYYY-MM-DD HH:mm') : '--'
        this.dataItems.push(item)
      }
      // 如果是删除操作，且删除完成后当前计划列表为空，则把计划列表弹窗也关闭
      if (this.isdelete) {
        if (this.dataItems.length === 0) {
          this.isShowDialog = false
        }
      }
    },
    // 按ID查询品类名称
    queryCategoryNameById (id) {
      if (!id) return
      let category = this._.find(this.categorylist, (item) => {
        return item._id.$oid === id
      })
      return category ? category.name : '--'
    },
    // 按ID查询用户名
    async queryUserNameById (id) {
      if (!id) return
      let user = this._.find(this.userlist, (item) => {
        return item._id.$oid === id
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
    }
  }
}
</script>
<style scoped>
.tableStyle td {
  max-width: 200px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.operateMinWidth {
  min-width: 200px;
}
</style>
