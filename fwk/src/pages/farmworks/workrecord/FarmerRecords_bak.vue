<template>
  <v-container fluid
               class="containerWrapper">
    <v-card class="page-content">
      <!-- 顶部工具条 -->
      <v-layout row
                wrap>
        <v-flex xs12>

          <v-layout row
                    wrap
                    class="toolWrapper">
            <v-flex xs3>
              <v-autocomplete label="请选择地块"
                              v-model="id"
                              :items="parcellist"
                              item-text="name"
                              item-value="id"
                              single-line>
              </v-autocomplete>
            </v-flex>
            <v-flex xs5
                    class="query-farmerRecords">
              <v-text-field label="请输入农事内容查询"
                            v-model="condition"
                            append-icon="fa_search"
                            single-line>
              </v-text-field>
            </v-flex>
            <v-flex xs4
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
      <v-layout class="recordSum">
        <v-flex xs12>
          <span>农事记录&nbsp;:&nbsp;</span>
          <span v-text="sumTotal"></span>
          <span>条</span>
        </v-flex>
      </v-layout>

      <!-- 表格 -->
      <v-layout row
                wrap>
        <v-flex xs12
                sm12
                md12
                lg12>
          <v-data-table v-model="selected"
                        :headers="headers"
                        :items="recordList"
                        :total-items="pagination.totalItems"
                        :pagination.sync="pagination"
                        no-data-text="暂无数据"
                        no-results-text="暂无数据"
                        hide-actions
                        class="tableStyle">
            <template slot="items"
                      slot-scope="props">
              <tr style="cursor: pointer"
                  @click="recordDetail(props.item.id)">
                <!-- 开始时间 -->
                <td must-sort
                    class="text-xs-left hideoverflow"
                    v-text="props.item.begintime || '- -'"
                    :title="props.item.begintime"></td>
                <!-- 农事类别 -->
                <td class="text-xs-left hideoverflow"
                    v-text="props.item.farmtypenames || '- -'"
                    :title="props.item.farmtypenames"></td>
                <!-- 农事内容 -->
                <td class="text-xs-left hideoverflow"
                    v-text="props.item.content || '- -'"
                    :title="props.item.content"></td>
                <!-- 地块 -->
                <td class="text-xs-left hideoverflow"
                    v-text="props.item.parcelnames || '- -'"
                    :title="props.item.parcelnames"></td>
                <!-- 实施人 -->
                <td class="text-xs-left hideoverflow"
                    v-text="props.item.recordpartusernames || '- -'"
                    :title="props.item.recordpartusernames"></td>

                <!-- 记录来源 -->
                <td class="text-xs-left"
                    v-text="props.item.sourcedictvaluename || '- -'"
                    :title="props.item.sourcedictvaluename"></td>

                <!-- 操作 -->
                <td class="text-xs-center operateMinWidth">
                  <v-btn v-if="$authed('GET','farmerrecords/main/farmerrecords/farmerrecordsDetail')"
                         @click.stop="recordDetail(props.item.id)"
                         fab
                         small
                         icon
                         title="查看">
                    <v-icon>remove_red_eye</v-icon>
                  </v-btn>
                  <v-btn v-if="$authed('PUT','farmerrecords/main/farmerrecords/farmerrecordsEdit')"
                         @click.stop="editRecord(props.item.id)"
                         fab
                         small
                         icon
                         title="编辑"
                         :disabled="parseInt(userid) === parseInt(props.item.userpermisionscreateuserid) ? false : true">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn v-if="$authed('DELETE','farmerrecords/main')"
                         @click.stop="openDeleteDialog(props.item.id)"
                         color="error"
                         fab
                         small
                         flat
                         icon
                         title="删除"
                         :disabled="parseInt(userid) === parseInt(props.item.userpermisionscreateuserid) ? false : true">
                    <v-icon>delete</v-icon>
                  </v-btn>
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-flex>
        <v-flex xs12
                text-xs-center
                class="paginationWrapper"
                v-show="sumTotal">
          <!-- <v-pagination v-model="pagination.page" :length="pagination.totalPage" circle color="teal" :total-visible="7"></v-pagination> -->
          <v-custompagination :pagination.sync="pagination"></v-custompagination>
        </v-flex>
      </v-layout>
    </v-card>

    <!-- 确认删除弹窗 -->
    <v-modaldialog :isShow="dialogDelete">
      <span slot
            v-text="message"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click="dialogDelete = false">取消</v-btn>
          <v-btn flat
                 color="primary"
                 @click="callbackOkFn">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>

  </v-container>
</template>
<script>
import debounce from 'throttle-debounce/debounce'
import { excuteApis } from '@/apis'
import FarmerMixin from '@/pages/farmworks/farmerMixin'
export default {
  name: 'farmerRecords',
  data () {
    return {
      message: '信息删除后将无法恢复,请谨慎操作,确认删除?',
      id: null, // 地块
      parcellist: [], // 地块列表
      condition: null, // 关键字搜索查询
      dialogDelete: false, // 确认删除弹框
      currentId: null, // 当前删除操作的记录id
      progressShow: false, // 转圈 显示/隐藏
      disabled: false, // 按钮不可用状态
      headers: [
        {
          text: '执行时间',
          align: 'left',
          sortable: false
        },
        {
          text: '农事类别',
          align: 'left',
          sortable: false
        },
        {
          text: '农事内容',
          align: 'left',
          sortable: false
        },
        {
          text: '地块',
          align: 'left',
          sortable: false
        },
        {
          text: '实施人',
          align: 'left',
          sortable: false
        },
        {
          text: '记录来源',
          align: 'left',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ], // 农事记录表头
      recordList: [], // 农事记录列表
      page: {}, // 关于记录page信息
      pagination: {
        rowsPerPage: 10,  // 每页显示几条数据
        page: 1, // 当前页
        total: 0
        // sortBy: 'column', // 按什么排序
        // descending: false, // 递减
        // totalItems: 100 // 总条目数
      },
      date2: null,
      date: null,
      search: '',
      selected: []
    }
  },
  mixins: [FarmerMixin],
  computed: {
    // currentPage () {
    //   return this.pagination.page
    // },

    /**
     * 总页数
     */
    sumTotal () {
      return this.pagination.totalItems
    },

    /**
     * 用户id
     */
    userid () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
    }
  },
  mounted () {
    this.getServiceData()
  },
  methods: {
    /**
     * 新增农事记录
     */
    addRecord () {
      this.$router.push({ name: 'farmerRecordsAdd' }) // 跳转
    },

    /**
     * 编辑农事记录
     */
    editRecord (id) {
      this.$router.push({ name: 'farmerRecordsEdit', query: { id: id } }) // 跳转
    },

    /**
     * 打开删除弹框
     */
    openDeleteDialog (id) {
      this.currentId = id
      this.dialogDelete = true
    },

    /**
     * 删除农事记录
     */
    deleteRecord () {
      let param = { id: this.currentId }
      excuteApis(param, global.constant.farmApiUrls, 'record', 'deletebyid').then(res => {
        console.log('删除农事记录成功')
        console.log(res)
        if (res.data && res.data.flag === 1) {
          this.getRecordList(this.id, this.keyword)
        } else {
          this.setMessage('农事记录删除失败')
        }
      }).catch((response) => {
        console.log('删除农事记录失败')
        console.log(response)
        this.setMessage('服务异常,请重试')
      })
    },

    /**
     * 查看农事记录
     */
    recordDetail (id) {
      this.$router.push({ name: 'farmerRecordsDetail', query: { id: id } })
    },

    /**
     * 导入记录
     */
    addRecordFromOut () {
      const router = this.$router
      router.push({ name: 'farmerRecordsEdit' })
    },

    /**
     * 获取农事记录
     */
    getRecordList (parcelid, keyword, currentPage, pageSize) {
      this.showLoad() // 显示菊花
      let param = {
        parcelid: parcelid,
        keyword: keyword,
        currentPage: this.pagination.page || 1,
        pageSize: this.pagination.rowsPerPage || 15, // 页大小
        delstate: 1
      }
      excuteApis(param, global.constant.farmApiUrls, 'record', 'querylist').then(res => {
        console.log('农事记录')
        console.log(res)
        if (res.data && res.data.flag === 1 && res.data.data) {
          // 分页处理
          if (res.data.page) {
            let page = res.data.page
            this.pagination.totalItems = page.total // 总条目数
            // this.pagination.totalPage = page.totalPage // 总页数
            this.pagination.total = page.total
            this.pagination.page = page.currentPage // 当前页
            this.pagination.rowsPerPage = page.pageSize // 页大小
          }
          this.recordList = this.disposeRecord(res.data.data)
        } else {
          this.recordList = [] // 清空列表
          this.pagination.totalItems = 0 // 隐藏分页条
        }
      }).finally(() => {
        this.closeLoad() // 关闭菊花
      })
    },

    /**
     * 处理获取的农事记录数据
     * @param array
     */
    disposeRecord (array) {
      if (!array || array.length === 0) return
      for (let list of array) {
        if (list.begintime) list.begintime = list.begintime.substring(0, 10)
      }
      return array
    },

    /**
     * 获取后台数据
     */
    getServiceData () {
      /* 获取地块 */
      // excuteApis({}, global.constant.parcelApis, 'parcel', 'queryParcel').then(data => {
      //   console.log('获取地块成功------')
      //   console.log(data)
      //   if (data.data && data.data.flag === 1 && data.data.data) {
      //     this.parcellist = [{ id: '', name: '全部地块' }, ...data.data.data]
      //   }
      // })
      // this.parcellist = [{ id: '', name: '全部地块' }, ...this.$store.state.orgLands]
      if (global.helper.ls.get('orgLands')) {
        this.parcellist = [{ id: '', name: '全部地块' }, ...global.helper.ls.get('orgLands')]
      } else {
        this.parcellist = [{ id: '', name: '全部地块' }]
      }
      /* 获取农事记录 */
      this.getRecordList()
    },

    /**
     * 取消弹窗
     */
    callbackCancelFn () {
      this.dialogDelete = false
    },

    /**
     * 确认弹窗
     */
    callbackOkFn () {
      console.log('确认弹窗')
      this.dialogDelete = false // 关闭弹框
      this.deleteRecord()
    },

    /**
     * 设置弹窗消息并弹出
     */
    setMessage (text) {
      this.message = text
      this.dialogDelete = true
    }
  },
  watch: {
    /**
     * 按条件查询农事记录 输入框
     */
    condition: debounce(600, function (val) {
      this.getRecordList(this.id, val)
    }),

    /**
     * 按条件查询农事记录 下拉框
     */
    id (val) {
      this.getRecordList(val, this.condition)
    },

    /**
     * 分页
     */
    // currentPage (val) {
    //   console.log('页数改变: ' + val)
    //   this.getRecordList(this.id, this.condition, val)
    // },
    'pagination.page' (val) {
      this.getRecordList(this.id, this.condition, val)
    },
    'pagination.rowsPerPage' (val) {
      this.getRecordList(this.id, this.condition, val)
    }
  }
}
</script>
<style scoped>
.container {
  padding-top: 0;
}
.query,
.edit,
.delete {
  cursor: pointer;
  margin-right: 5px;
}
.recordSum {
  margin-bottom: 20px;
}
.main {
  padding-left: 20px;
  padding-right: 20px;
  margin-bottom: 50px;
}
.paginationWrapper {
  margin-top: 20px;
  margin-bottom: 20px;
}
.btnSize {
  width: 107px;
}
.progressWrapper {
  position: absolute;
  margin-left: 5px;
}
.btnColor {
  color: #fff;
}
.containerWrapper {
  padding: 0;
}
.btnAdd {
  margin-top: 13px;
  margin-right: 0;
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
.btnBottom {
  padding-bottom: 20px;
}
</style>
