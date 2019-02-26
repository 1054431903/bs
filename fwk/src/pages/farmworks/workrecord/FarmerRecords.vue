<template>
  <v-container fluid
               pa-0
               pb-4>
    <v-card class="page-content">
      <!-- 顶部工具条 -->
      <v-layout row
                wrap>
        <v-flex xs12>
          <v-layout row
                    wrap
                    class="toolWrapper">
            <v-flex xs5>
              <v-text-field label="请输入农事内容查询"
                            v-model="keywords"
                            clearable
                            append-icon="search"
                            single-line
                            @keyup.enter="queryByCondition">
              </v-text-field>
            </v-flex>
            <v-flex xs2
                    mt-2>
              <v-btn color="primary"
                     @click="queryByCondition">查询</v-btn>
            </v-flex>
            <v-flex xs5
                    text-xs-right
                    v-if="$authed('POST','farmerrecords/main/farmerrecords/farmerrecordsAdd')">
              <v-btn @click="addRecord"
                     small
                     fab
                     dark
                     color="red darken-2"
                     class="btnAdd"
                     title="新增农事记录">
                <v-icon>add</v-icon>
              </v-btn>
            </v-flex>
          </v-layout>

        </v-flex>
      </v-layout>

      <!-- 记录条数 -->
      <v-layout>
        <v-flex xs12
                mb-3>
          <span>共有农事记录：&nbsp;{{ sumItems }} &nbsp;条</span>
        </v-flex>
      </v-layout>

      <!-- 表格 -->
      <v-layout row
                wrap>
        <v-flex xs12
                sm12
                md12
                lg12>
          <v-data-table :headers="headers"
                        :items="records"
                        must-sort
                        disable-initial-sort
                        no-data-text="暂无数据"
                        no-results-text="暂无数据"
                        hide-actions
                        class="tableStyle">
            <template slot="items"
                      slot-scope="props">
              <tr style="cursor: pointer"
                  @click="showRecordDetail(props.item.id)">
                <td class="text-xs-left hideoverflow"
                    :title="props.item.startOnStr">
                  {{ props.item.startOnStr }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.content">
                  {{ props.item.content }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.farmlandName">
                  {{ props.item.farmlandName }}
                </td>
                <td class="text-xs-left hideoverflow"
                    :title="props.item.participatorsName">
                  {{ props.item.participatorsName }}
                </td>
                <!-- 操作 -->
                <td class="text-xs-center operateMinWidth">
                  <v-btn v-if="$authed('GET','farmerrecords/main/farmerrecords/farmerrecordsDetail')"
                         @click.stop="showRecordDetail(props.item.id)"
                         fab
                         small
                         icon
                         title="查看">
                    <v-icon>visibility</v-icon>
                  </v-btn>
                  <v-btn v-if="$authed('PUT','farmerrecords/main/farmerrecords/farmerrecordsEdit')"
                         @click.stop="editRecord(props.item.id)"
                         :disabled="userid !== props.item.creatorId"
                         fab
                         small
                         icon
                         title="编辑">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn v-if="$authed('DELETE','farmerrecords/main')"
                         @click.stop="deleteRecord(props.item.id)"
                         :disabled="userid !== props.item.creatorId"
                         fab
                         small
                         icon
                         title="删除">
                    <v-icon color="red">delete</v-icon>
                  </v-btn>
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-flex>
        <v-flex xs12
                text-xs-center
                class="paginationWrapper"
                v-show="showPagination">
          <v-custompagination :pagination.sync="pagination"></v-custompagination>
        </v-flex>
      </v-layout>
    </v-card>

    <!-- 确认删除弹窗 -->
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
    <v-farmerrecordsadd :visible.sync="addRecordDialog"
                        :title="addRecordDialogTitle">
    </v-farmerrecordsadd>
    <v-farmerrecordsedit :visible.sync="editRecordDialog"
                         :title="editRecordDialogTitle"
                         :recordid="curRecordId"
                         @callback="refreshData">
    </v-farmerrecordsedit>
    <v-farmerrecordsdetail :visible.sync="viewRecordDialog"
                           :title="viewRecordDialogTitle"
                           :id="curRecordId"
                           @callback="refreshData">
    </v-farmerrecordsdetail>
  </v-container>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import { Toast } from '@/models/toast'
import moment from 'moment'
import { MessageBox } from 'element-ui'
import VFarmerrecordsadd from './FarmerRecordsAdd.vue'
import VFarmerrecordsedit from './FarmerRecordsEdit.vue'
import VFarmerrecordsdetail from './FarmerRecordsDetail.vue'
export default {
  name: 'farmerRecords',
  data () {
    return {
      keywords: null,
      sumItems: 0,
      records: [],
      headers: [
        {
          text: '实施时间',
          align: 'left',
          sortable: true,
          value: 'startOnStr'
        },
        {
          text: '农事内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '实施地块',
          align: 'left',
          sortable: false,
          value: 'farmlandName'
        },
        {
          text: '实施人',
          align: 'left',
          sortable: false,
          value: 'participatorsName'
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
        page: 1,
        rowsPerPage: 10
      },
      prompt: {
        dialog: false,
        title: '农事记录删除提示',
        body: '农事记录删除后将无法恢复，是否确认删除？'
      },
      // 当前组织下所有用户
      // tenantUsers: [],
      // 系统中所有用户包括已删除的用户
      allUsers: [],
      // 当前企业下的地块
      // tenantFarmlands: [],
      // 所有地块：包括已删除，未分配组织的地块
      allFarmlands: [],
      addRecordDialog: false,
      addRecordDialogTitle: '新建农事记录',
      editRecordDialog: false,
      editRecordDialogTitle: '编辑农事记录',
      viewRecordDialog: false,
      viewRecordDialogTitle: '农事记录详情',
      curRecordId: null
    }
  },
  components: {
    VFarmerrecordsadd,
    VFarmerrecordsedit,
    VFarmerrecordsdetail
  },
  computed: {
    userid () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    },
    tenant () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  created () {
    this.toast = new Toast(this, null, null)
    // 缓存当前企业下的地块
    this.$store.dispatch('getTenantFarmLands', this.tenant)
    // 缓存当前企业下的用户
    this.$store.dispatch('getTenantUsers', this.tenant)
  },
  watch: {
    'pagination.page' (val) {
      this.queryList()
    },
    'pagination.rowsPerPage' (val) {
      this.queryList()
    }
  },
  mounted () {
    this.isValidUser()
    this.allFarmlands = this.$store.state.allFarmLands
    this.allUsers = this.$store.state.allUsers
    this.queryBaseData()
  },
  methods: {
    // 判断当前登录用户是否有效
    isValidUser () {
      let user = global.helper.ls.get('user')
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

    queryByCondition () {
      this.pagination.page = 1
      this.pagination.rowsPerPage = 10
      this.queryList()
    },
    async queryList () {
      this.toast.show()
      try {
        let filterParam = {}
        let filter = {}
        filter.tenant = { $oid: this.tenant }
        if (this.keywords && this.keywords.trim() !== '') {
          filter.content = { $regex: this.escapeCharacter(this.keywords.trim()) }
        }
        filterParam.filter = filter
        filterParam.sort = { startOn: -1 }
        filterParam.skip = (this.pagination.page - 1) * this.pagination.rowsPerPage
        filterParam.limit = this.pagination.rowsPerPage
        let data = await mongo.db().collection('record').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.pagination.total = Number(data.total.$numberLong)
        await this.bindData(items)
      } catch (err) {
        console.log(err)
        this.toast.setContent('查询数据出错，请联系系统管理员！')
      } finally {
        this.toast.close()
      }
    },

    addRecord () {
      this.addRecordDialog = true
      this.addRecordDialogTitle = '新增农事记录'
    },

    editRecord (id) {
      if (!id) return
      this.curRecordId = id
      this.editRecordDialog = true
      this.editRecordDialogTitle = '编辑农事记录'
    },

    deleteRecord (id) {
      if (!id) return
      this.curRecordId = id
      this.prompt.dialog = true
    },

    showRecordDetail (id) {
      if (!id) return
      this.curRecordId = id
      this.viewRecordDialog = true
    },

    Esc () {
      this.prompt.dialog = false
    },

    Ok () {
      mongo.db().collection('record').remove({ filter: { _id: { $oid: this.curRecordId } } }).then(res => {
        if (!res.writeError && !res.writeConcernError) {
          this.prompt.dialog = false
          this.keywords = null
          this.queryList()
        }
      })
    },

    // 绑定记录列表数据
    bindData (items) {
      this.records = []
      for (let item of items) {
        item.id = item._id.$oid
        item.creatorId = item.creator ? item.creator.$oid : ''
        item.startOnStr = item.startOn ? moment(Number(item.startOn.$numberLong)).format('YYYY-MM-DD') : '--'
        item.farmlandName = item.farmland ? this.queryFarmlandNameById(item.farmland.$oid) : '--'
        item.participatorsName = item.participators ? this.queryParticipatorsNameByIds(item.participators) : '--'
        this.records.push(item)
      }
    },

    queryFarmlandNameById (id) {
      let land = this.allFarmlands.find((elem) => {
        return elem._id.$oid === id
      })
      return land ? land.name || '--' : '--'
    },

    queryParticipatorsNameByIds (ids) {
      let names = ids.map(id => {
        let user = this.allUsers.find(elem => {
          return elem._id.$oid === id.$oid
        })
        return user ? user.name || user.mobile : null
      }).filter(elem => {
        return elem
      })
      return names.join(',') || '--'
    },

    async queryBaseData () {
      this.toast.show()
      if (!this.allUsers || this.allUsers.length === 0) {
        this.allUsers = await this.getAllUsers()
      }
      if (!this.allFarmlands || this.allFarmlands.length === 0) {
        this.allFarmlands = await this.getAllFarmLands()
      }
      this.toast.close()
      this.queryList()
    },

    refreshData () {
      this.queryList()
    },
    async getAllUsers () {
      let data = await mongo.db('iss').collection('tenant.staff').aggregate([{ $match: {} }])
      this.$store.commit('setAllUsers', data)
      return data
    },
    async getAllFarmLands () {
      let filter = {}
      let project = {}
      project.name = 1
      project.tenant = 1
      let data = await mongo.db('iss').collection('tenant.farmland').aggregate([{ $match: filter }, { $project: project }])
      this.$store.commit('setAllFarmLands', data)
      return data
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
.paginationWrapper {
  margin-top: 20px;
  margin-bottom: 20px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.tableStyle td {
  max-width: 250px;
}
.toolWrapper .flex:not(:last-child) {
  padding-right: 20px;
}
.operateMinWidth {
  min-width: 230px;
}
</style>
