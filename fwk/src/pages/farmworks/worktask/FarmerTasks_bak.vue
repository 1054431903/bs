<template>
  <v-container fluid
               class="containerWrapper">
    <v-card class="page-content">
      <v-layout row
                wrap>
        <v-flex xs12>

          <!-- 顶部工具条 -->
          <v-layout row
                    wrap>
            <v-flex xs2
                    class="tool-wrapper">
              <v-select label="请选择任务状态"
                        v-model="status"
                        :items="statusList"
                        item-text="dictname"
                        item-value="dictvalue"
                        single-line>
              </v-select>
            </v-flex>

            <v-flex xs2
                    class="tool-wrapper">
              <v-select label="请选择任务类别"
                        v-model="type"
                        :items="typeList"
                        item-text="dictname"
                        item-value="dictvalue"
                        single-line>
              </v-select>
            </v-flex>

            <v-flex xs4
                    class="tool-wrapper">
              <v-text-field label="请输入任务内容或发布人进行搜索"
                            v-model="condition"
                            append-icon="fa_search"
                            single-line>
              </v-text-field>
            </v-flex>

            <v-flex xs4
                    class="text-xs-right tool-wrapper"
                    v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksAdd')">
              <v-btn class="btnMargin"
                     small
                     fab
                     dark
                     color="red darken-2"
                     title="新增农事任务"
                     :to="{name: 'farmertasksAdd'}">
                <v-icon>add</v-icon>
              </v-btn>
            </v-flex>
          </v-layout>

          <!-- 表格 -->
          <v-layout row
                    wrap>
            <v-flex xs12>
              <v-tabs v-model="active"
                      color="white"
                      light
                      slider-color="primary">
                <v-tab v-for="tab in tabs"
                       :key="tab"
                       :href="'#' + tab"
                       ripple
                       @click="clickTabBar(tab)">
                  {{ tab }}
                </v-tab>
                <!-- ? -->
                <v-tabs-items>
                  <div class="taskNumber">共有任务&nbsp;:&nbsp;
                    <span v-text="sumItems"></span>条</div>
                  <!-- 我参与的任务 -->
                  <v-tab-item :id="'我参与的任务'">
                    <v-data-table :headers="headerJoin"
                                  :items="joinTaskList"
                                  :total-items="pagination.totalItems"
                                  :pagination.sync="pagination"
                                  no-data-text="暂无数据"
                                  no-results-text="暂无数据"
                                  hide-actions
                                  class="tableStyle">
                      <template slot="items"
                                slot-scope="props">
                        <tr style="cursor: pointer"
                            @click="showTaskDetail(props.item.id)">
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.typename"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.describe"
                              :title="props.item.describe"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.endtime"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.taskstatusname || '未确认'"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.createuseridname || '暂无'"
                              :title="props.item.createuseridname"></td>
                          <td class="text-xs-left"
                              v-text="props.item.savedrecordlabel"></td>
                          <td class="text-xs-center operateMinWidth">
                            <v-btn small
                                   icon
                                   fab
                                   @click.stop="saveToRecord(props.item)"
                                   :disabled="!props.item.iscansavetorecord"
                                   title="保存"
                                   v-if="$authed('PUT', 'farmerrecords/main/farmerrecords/SaveTaskTofarmerrecords')">
                              <v-icon>save</v-icon>
                            </v-btn>
                            <!-- 我参与的任务 查看. -->
                            <!-- <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"  fab small icon :to="{name: 'farmertasksDetail', query: {id: props.item.id, select: 1}}" title="查看"><v-icon>remove_red_eye</v-icon></v-btn> -->
                            <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"
                                   fab
                                   small
                                   icon
                                   @click.stop="showTaskDetail(props.item.id)"
                                   title="查看">
                              <v-icon>remove_red_eye</v-icon>
                            </v-btn>
                          </td>
                        </tr>
                      </template>
                    </v-data-table>
                  </v-tab-item>

                  <!-- 我发布的任务 ? -->
                  <v-tab-item :id="'我发布的任务'">
                    <v-data-table :headers="headerSend"
                                  :items="sendTaskList"
                                  :total-items="pagination.totalItems"
                                  :pagination.sync="pagination"
                                  no-data-text="暂无数据"
                                  no-results-text="暂无数据"
                                  hide-actions
                                  class="tableStyle">
                      <template slot="items"
                                slot-scope="props">
                        <tr style="cursor: pointer"
                            @click="skipDetail(props.item, 2)">
                          <td class="text-xs-left"
                              v-text="props.item.typename"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.describe"
                              :title="props.item.describe"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.endtime"></td>
                          <td class="text-xs-left"
                              v-text="props.item.taskstatusname || '未确认'"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.taskparticsusernames"
                              :title="props.item.taskparticsusernames"></td>
                          <td class="text-xs-center operateMinWidth">
                            <v-btn fab
                                   small
                                   flat
                                   icon
                                   @click.stop="skipDetail(props.item, 2)"
                                   title="查看"
                                   v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')">
                              <v-icon>remove_red_eye</v-icon>
                            </v-btn>
                            <!-- <v-btn fab small flat icon :to="{name: 'farmertasksEdit', query: {id: props.item.id}}" title="编辑" v-if="$authed('PUT','farmertasks/main/farmertasks/farmertasksEdit')"><v-icon>edit</v-icon></v-btn> -->
                            <v-btn fab
                                   small
                                   flat
                                   icon
                                   @click.stop="editTaskDetail(props.item.id)"
                                   title="编辑"
                                   v-if="$authed('PUT','farmertasks/main/farmertasks/farmertasksEdit')">
                              <v-icon>edit</v-icon>
                            </v-btn>
                            <v-btn fab
                                   color="error"
                                   flat
                                   small
                                   icon
                                   @click.stop="openDialog(props.item.id)"
                                   title="删除"
                                   v-if="$authed('DELETE','farmertasks/main')">
                              <v-icon>delete</v-icon>
                            </v-btn>
                          </td>
                        </tr>
                      </template>
                    </v-data-table>
                  </v-tab-item>

                  <!-- 抄送我的任务 ? -->
                  <v-tab-item :id="'抄送我的任务'">
                    <v-data-table :headers="headerGiveMe"
                                  :items="giveTaskList"
                                  :total-items="pagination.totalItems"
                                  :pagination.sync="pagination"
                                  no-data-text="暂无数据"
                                  no-results-text="暂无数据"
                                  hide-actions
                                  class="tableStyle">
                      <template slot="items"
                                slot-scope="props">
                        <tr style="cursor: pointer"
                            @click="skipDetail(props.item, 3)">
                          <td class="text-xs-left"
                              v-text="props.item.typename"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.describe"
                              :title="props.item.describe"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.endtime"></td>
                          <td class="text-xs-left"
                              v-text="props.item.taskstatusname || '未确认'"></td>
                          <td class="text-xs-left"
                              v-text="props.item.createuseridname || '发布人'"></td>
                          <td class="text-xs-left hideoverflow"
                              v-text="props.item.taskparticsusernames"
                              :title="props.item.taskparticsusernames"></td>
                          <td class="text-xs-center">
                            <v-btn fab
                                   small
                                   icon
                                   @click.stop="skipDetail(props.item, 3)"
                                   title="查看"
                                   v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')">
                              <v-icon>remove_red_eye</v-icon>
                            </v-btn>
                          </td>
                        </tr>
                      </template>
                    </v-data-table>
                  </v-tab-item>

                  <!-- 分页 -->
                  <v-flex class="paginationWrapper"
                          text-xs-center
                          v-show="showPagination">
                    <!-- <v-pagination
                      v-model="pagination.page"
                      :length="pagination.totalPage"
                      circle
                    >
                    </v-pagination> -->
                    <v-custompagination :pagination.sync="pagination"></v-custompagination>
                  </v-flex>
                </v-tabs-items>
              </v-tabs>
            </v-flex>
          </v-layout>

        </v-flex>
      </v-layout>
    </v-card>

    <!-- 确认删除弹窗 -->
    <v-modaldialog :isShow="dialogDelete">
      <span slot
            v-text="'信息删除后将无法恢复,请谨慎操作,确认删除?'"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click="dialogDelete = false">取消</v-btn>
          <v-btn flat
                 color="primary"
                 @click="submitDelete(currentId)">确定</v-btn>
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
  data () {
    return {
      status: null, // 状态
      statusList: [], // 状态列表
      type: null, // 类别
      typeList: [], // 类别列表
      dialogDelete: false,
      condition: null, // 搜索框
      model1: null,
      model2: null,
      item1: [1, 2, 3],
      item2: [1, 2, 3],
      model3: null,
      tabs: ['我参与的任务', '我发布的任务', '抄送我的任务'],
      active: null,
      pagination: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10
      },
      selected: [],
      headers: [
        {
          text: '任务内容',
          align: 'center',
          sortable: false,
          value: 'id'
        },
        {
          text: '任务类别',
          align: 'center',
          sortable: false,
          value: 'id2'
        },
        {
          text: '结束时间',
          align: 'center',
          sortable: false,
          value: 'id2'
        },
        {
          text: '当前状态',
          align: 'center',
          sortable: false,
          value: 'id3'
        },
        {
          text: '发布人',
          align: 'center',
          sortable: false,
          value: 'id4'
        },
        {
          text: '是否存在农事',
          align: 'center',
          sortable: false,
          value: 'id5'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: 'id6'
        }
      ],
      // 我参加的农事任务表头
      headerJoin: [
        {
          text: '任务类别',
          align: 'left',
          sortable: false
        },
        {
          text: '任务内容',
          align: 'left',
          sortable: false
        },
        {
          text: '结束时间',
          align: 'left',
          sortable: false
        },
        {
          text: '任务状态',
          align: 'left',
          sortable: false
        },
        {
          text: '发布人',
          align: 'left',
          sortable: false
        },
        {
          text: '已存入农事',
          align: 'left',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      joinTaskList: [], // 我发布的任务列表数据
      // 我发布的任务表头
      headerSend: [
        {
          text: '任务类别',
          align: 'left',
          sortable: false
        },
        {
          text: '任务内容',
          align: 'left',
          sortable: false
        },
        {
          text: '结束时间',
          align: 'left',
          sortable: false
        },
        {
          text: '任务状态',
          align: 'left',
          sortable: false
        },
        {
          text: '参与人',
          align: 'left',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      sendTaskList: [], // 我发布的任务列表
      // 抄送我的任务表头
      headerGiveMe: [
        {
          text: '任务类别',
          align: 'left',
          sortable: false
        },
        {
          text: '任务内容',
          align: 'left',
          sortable: false
        },
        {
          text: '结束时间',
          align: 'left',
          sortable: false
        },
        {
          text: '任务状态',
          align: 'left',
          sortable: false
        },
        {
          text: '发布人',
          align: 'left',
          sortable: false
        },
        {
          text: '参与人',
          align: 'left',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      giveTaskList: [], // 抄送我的数据列表
      items: [
        {
          value: false,
          name: 'Frozen Yogurt',
          calories: 11159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          sodium: 87,
          calcium: '14%'
        },
        {
          value: false,
          name: 'Frozen Yogurt',
          calories: 11159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          sodium: 87,
          calcium: '14%'
        }
      ],
      currentId: 0, // 当前操作的数据的id
      currentTable: '我参与的任务', // 当前操作的表格
      sumItems: 0, // 共有任务几条
      statusFlag: false,
      typeFlag: false,
      conditionFlag: false,
      pageFlag: false,
      showPagination: true
    }
  },
  mixins: [FarmerMixin],
  computed: {
    currentSelect () {
      switch (this.currentTable) {
        case '我参与的任务':
          return 1

        case '我发布的任务':
          return 2

        case '抄送我的任务':
          return 3
        default:
          return 1
      }
    },

    // currentPage () {
    //   return this.pagination.page
    // },

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
     * 查询农事任务
     * @param select 查询谁
     * @param userid 用户id
     * @param taskstatus	任务状态
     * @param type	任务类别
     * @param keyword	任务内容、发布人
     * @param currentPage 当前页
     */
    queryTask (select, userid, taskstatus, type, keyword, currentPage) {
      this.showLoad()
      let param = {}
      if (select === 1) param.taskparticsuserid = userid
      if (select === 2) param.createuserid = userid
      if (select === 3) param.taskcopyuserid = userid
      param.taskstatus = taskstatus
      param.type = type
      param.keyword = keyword
      param.currentPage = this.pagination.page || 1
      param.pageSize = this.pagination.rowsPerPage || 10
      param.delstate = 1
      excuteApis(param, global.constant.farmApiUrls, 'task', 'querylist').then(response => {
        if (response.data && response.data.data && response.data.flag === 1) {
          if (response.data.page) {
            this.showPagination = true // 显示分页
            let page = response.data.page
            // this.pagination.totalItems = page.total // 总条目数
            // this.pagination.totalPage = page.totalPage // 总页数
            // this.pagination.page = page.currentPage // 当前页
            this.pagination.rowsPerPage = page.pageSize // 页大小
            this.sumItems = page.total // 共有几条任务条数
            this.pagination.total = page.total
            // 三个表格 根据selet 赋值
            if (select === 1) { // 我参与
              console.log('我参与的数据')
              console.log(response)
              this.joinTaskList = this.disposeTask(response.data.data)
            }
            if (select === 2) {
              console.log('我发布的数据')
              console.log(response)
              this.sendTaskList = this.disposeTask(response.data.data)
            }
            if (select === 3) {
              console.log('抄送我的数据')
              console.log(response)
              this.giveTaskList = this.disposeTask(response.data.data)
            }
          }
        } else {
          // 置空
          if (select === 1) this.joinTaskList = []
          if (select === 2) this.sendTaskList = []
          if (select === 3) this.giveTaskList = []
          this.showPagination = false // 隐藏分页
        }
      }).finally(() => {
        this.closeLoad()
      })
    },

    /**
     * 处理获取的农事任务数据
     * @param array
     */
    disposeTask (array) {
      if (!array || array.length === 0) return
      for (let list of array) {
        if (list.endtime) list.endtime = list.endtime.substring(0, 16)
      }
      return array
    },

    /**
     * 我参与的任务 保存到农事记录
     * ??? 这里还要传地块id过去 parcelids
     */
    saveToRecord (item) {
      console.log('保存到农事记录')
      console.log(item)
      this.$router.push({ name: 'farmerRecordsEdit', query: { taskId: item.id, recordId: item.recordeid, parcelids: item.parcelids } })
    },

    /**
     * 点击表头 赋值当前操作的表格
     */
    clickTabBar (tab) {
      this.currentTable = tab
    },

    /**
     * 打开弹窗
     */
    openDialog (id) {
      console.log('删除-------')
      this.dialogDelete = true
      this.currentId = id
    },

    /**
     * 我发布的任务 抄送我的任务 跳转任务详情
     * @param item object 当前任务对象
     * @param select int 表格标识
     */
    skipDetail (item, select) {
      console.log(item)
      console.log(select)
      this.$router.push({ name: 'farmertasksDetail', query: { id: item.id, partUserId: item.taskparticsuserids, select: select } })
    },

    /**
     * 根据id删除农事任务
     * @param id
     */
    submitDelete (id) {
      console.log('当前表格 = ' + this.currentSelect)
      console.log('当前id = ' + id)
      excuteApis({ id: id }, global.constant.farmApiUrls, 'task', 'deletebyid').then(response => {
        if (response.data && response.data.flag === 1) {
          console.log('删除任务成功')
          console.log(response)
          this.queryTask(this.currentSelect, this.userid) // 刷新
        } else {
        }
      })
      this.dialogDelete = false
    },

    /**
     * 获取服务数据
     */
    getServiceData () {
      /**
       * 任务状态
       */
      excuteApis({ dicttype: 'task-status', delstate: 1 }, global.constant.commonApis, 'common', 'querydictlist').then(response => {
        console.log('获取任务状态success')
        console.log(response)
        if (response.data && response.data.data && response.data.flag === 1) {
          let statusList = this.addJson({ dictname: '全部状态', dictvalue: '' }, response.data.data)
          this.statusList = statusList
        }
      })

      /**
       * 任务类别
       */
      excuteApis({ dicttype: 'task-type', delstate: 1 }, global.constant.commonApis, 'common', 'querydictlist').then(response => {
        console.log('获取任务类别成功')
        if (response.data && response.data.data && response.data.flag === 1) {
          let typeList = this.addJson({ dictname: '全部类别', dictvalue: '' }, response.data.data)
          this.typeList = typeList
        }
        console.log(response)
      })

      /**
       * 获取任务列表
       */
      this.queryTask(1, this.userid) // 我参与
    },

    /**
     * 添加json
     * @param json 要添加的数据
     * @param arr 添加到那个数组
     */
    addJson (json, arr) {
      let newArr = []
      newArr.push(json)
      for (let item of arr) {
        newArr.push(item)
      }
      return newArr
    },

    /**
     * 取消弹窗
     */
    callbackCancelFn () {
      this.dialogDelete = false
    },
    showTaskDetail (taskId) {
      this.$router.push({ name: 'farmertasksDetail', query: { id: taskId, select: 1 } })
    },
    editTaskDetail (taskId) {
      this.$router.push({ name: 'farmertasksEdit', query: { id: taskId } })
    }
  },
  watch: {
    /**
     * 根据条件查询任务 - 状态
     */
    status (status) {
      if (this.statusFlag) {
        this.statusFlag = false
        return
      }
      console.log('status = ' + status)
      this.queryTask(this.currentSelect, this.userid, status, this.type, this.condition)
    },

    /**
     * 根据条件查询任务 - 类别
     */
    type (type) {
      if (this.typeFlag) {
        this.typeFlag = false
        return
      }
      console.log('type = ' + type)
      this.queryTask(this.currentSelect, this.userid, this.status, type, this.condition)
    },

    /**
     * 根据条件查询任务 - 输入框
     */
    condition: debounce(600, function (condition) {
      if (this.conditionFlag) {
        this.conditionFlag = false
        return
      }
      console.log('输入框值改变 = ' + condition)
      this.queryTask(this.currentSelect, this.userid, this.status, this.type, condition)
    }),

    /**
     * 表格切换
     * 分页数大于1 切换表格触发两次请求 待解决
     */
    currentSelect (newVal) {
      // this.pagination = {page: 1, total: 0, rowsPerPage: 10} // 重置分页
      // this.pageFlag = true // 禁止触发分页改变
      if (this.status !== null) this.statusFlag = true
      if (this.type !== null) this.typeFlag = true
      if (this.condition !== null) this.conditionFlag = true
      // if (this.currentPage > 1) this.pageFlag = true
      this.queryTask(parseInt(newVal), this.userid)
      this.status = null
      this.type = null
      this.condition = null
    },

    /**
     * 分页
     */
    // currentPage (val) {
    //   // console.log('触发分页 = ' + val)
    //   // if (this.pageFlag) {
    //   //   this.pageFlag = false
    //   //   return
    //   // }
    //   console.log('分页改变, 当前页 = ' + val)
    //   this.queryTask(this.currentSelect, this.userid, this.status, this.type, this.condition, val)
    // },

    'pagination.page' (val) {
      this.queryTask(this.currentSelect, this.userid, this.status, this.type, this.condition, val)
    },
    'pagination.rowsPerPage' (val) {
      this.queryTask(this.currentSelect, this.userid, this.status, this.type, this.condition, val)
    }
  }
}
</script>
<style scoped>
.main {
  padding-left: 20px;
  padding-right: 20px;
}
.tool-wrapper:not(:last-child) {
  padding-right: 20px;
}
.paginationWrapper {
  margin-top: 20px;
  margin-bottom: 50px;
}
.btnMargin {
  margin-left: 0;
  margin-right: 0;
  margin-top: 13px;
}
.taskNumber {
  padding-top: 15px;
  padding-bottom: 15px;
}
.containerWrapper {
  padding: 0;
  margin-bottom: 50px;
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
.operateMinWidth {
  min-width: 230px;
}
.btnPadding {
  padding-bottom: 20px;
}
.btnSize {
  min-width: 107px;
}
</style>
