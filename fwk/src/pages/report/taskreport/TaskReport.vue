<template>
  <v-card class="page-content">
    <v-container fluid
                 fill-height
                 grid-list-lg
                 pa-0>
      <v-layout row
                wrap
                ma-0>
        <v-flex xs12
                class="task_title">组织内部任务执行总览</v-flex>
        <v-flex xs6
                md3>
          <v-container fluid
                       grid-list-lg
                       pa-0>
            <v-layout row
                      wrap
                      ma-0>
              <v-flex xs12
                      px-0>
                <v-card style="height:106px;min-width:245px">
                  <v-layout row
                            ma-0>
                    <v-flex xs8
                            pa-0>
                      <v-card-title>
                        <div>
                          <div class="task_size">{{orgTaskReport.uncompleted}}</div>
                          <div class="task_title">待完成任务（条）</div>
                        </div>
                      </v-card-title>
                    </v-flex>
                    <v-flex xs4
                            pa-0
                            class="imgcenter">
                      <img src="../../../../static/dashboard/uncompleted.png" />
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <v-flex xs12
                      px-0>
                <v-card style="height:106px;min-width:245px">
                  <v-layout row
                            ma-0>
                    <v-flex xs8
                            pa-0>
                      <v-card-title>
                        <div>
                          <div class="task_size">{{orgTaskReport.completed}}</div>
                          <div class="task_title">已完成任务（条）</div>
                        </div>
                      </v-card-title>
                    </v-flex>
                    <v-flex xs4
                            pa-0
                            class="imgcenter">
                      <img src="../../../../static/dashboard/completed.png" />
                    </v-flex>
                  </v-layout>
                  <!-- <v-container fluid
                               fill-height>
                    <v-layout row>
                      <v-flex xs8>
                        <v-layout row
                                  wrap>
                          <v-flex xs12
                                  pa-0
                                  class="task_size">{{orgTaskReport.completed}}</v-flex>
                          <v-flex pa-0
                                  class="task_title">已完成任务（条）</v-flex>
                        </v-layout>
                      </v-flex>
                      <v-flex pa-0>
                        <img style="width:40px;height:40px"
                             src="../../../../static/dashboard/completed.png" />
                      </v-flex>
                    </v-layout>
                  </v-container> -->
                </v-card>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
        <v-flex xs6
                md3>
          <v-container fluid
                       grid-list-lg
                       pa-0>
            <v-layout row
                      wrap
                      ma-0>
              <v-flex xs12
                      px-0>
                <v-card style="height:106px;min-width:245px">
                  <v-layout row
                            ma-0>
                    <v-flex xs8
                            pa-0>
                      <v-card-title>
                        <div>
                          <div class="task_size">{{orgTaskReport.overdue}}</div>
                          <div class="task_title">已延期任务（条）</div>
                        </div>
                      </v-card-title>
                    </v-flex>
                    <v-flex xs4
                            pa-0
                            class="imgcenter">
                      <img src="../../../../static/dashboard/overdue.png" />
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
              <v-flex xs12
                      px-0>
                <v-card style="height:106px;min-width:245px">
                  <v-layout row
                            ma-0>
                    <v-flex xs8
                            pa-0>
                      <v-card-title>
                        <div>
                          <div class="task_size">{{orgTaskReport.all}}</div>
                          <div class="task_title">任务总数（条）</div>
                        </div>
                      </v-card-title>
                    </v-flex>
                    <v-flex xs4
                            pa-0
                            class="imgcenter">
                      <img src="../../../../static/dashboard/all.png" />
                    </v-flex>
                  </v-layout>
                </v-card>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
        <v-flex xs6
                md3>
          <v-card height="100%">
            <v-layout row
                      wrap
                      pa-0>
              <div style="height:20px;width:100%;margin-left:20px;font-family: 'Microsoft YaHei';font-size: 16px;color: #666666;">任务完成度</div>
            </v-layout>
            <v-card-text style="height:100%;padding:10px">
              <v-layout style="height:100%"
                        pa-0>
                <ve-gauge height="100%"
                          width="100%"
                          :data="completedData"
                          :settings="completedSettings"
                          :extend="completedExtend"></ve-gauge>
              </v-layout>
            </v-card-text>
          </v-card>
          <!-- <v-card height="100%">
            <v-layout row
                      style="height:100%">
              <ve-gauge height="100%"
                        width="100%"
                        :data="completedData"
                        :settings="completedSettings"
                        :extend="completedExtend"></ve-gauge>
            </v-layout>
          </v-card> -->
        </v-flex>
        <v-flex xs6
                md3>
          <v-card height="100%">
            <v-layout row
                      wrap
                      pa-0
                      style="height:100%">
              <div style="height:20px;width:100%;margin-left:20px;font-family: 'Microsoft YaHei';font-size: 16px;color: #666666;">任务延期率</div>
              <v-card-text style="height:100%;padding:10px">
                <v-layout style="height:100%"
                          pa-0>
                  <ve-gauge height="100%"
                            width="100%"
                            :data="overduedData"
                            :settings="overduedSettings"
                            :extend="overduedExtend"></ve-gauge>
                </v-layout>
              </v-card-text>
            </v-layout>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <v-spacer style="height:10px"></v-spacer>
    <v-container fluid
                 fill-height
                 grid-list-lg
                 pa-0>
      <v-layout row
                wrap
                ma-0>
        <v-flex xs12
                class="task_title">组织成员任务执行总览</v-flex>
        <v-flex>
          <v-spacer style="height:10px"></v-spacer>
          <!-- <v-card> -->
          <v-data-table :headers="headers"
                        :items="desserts"
                        must-sort
                        :pagination.sync="pagination"
                        class="elevation-1"
                        no-data-text="暂无数据"
                        rows-per-page-text="每页显示"
                        :rows-per-page-items="pageItems">
            <template slot="items"
                      slot-scope="props">
              <td class="text-xs-left">{{ props.item.name }}</td>
              <td class="text-xs-center">{{ props.item.all }}</td>
              <td class="text-xs-center">{{ props.item.uncompleted }}</td>
              <td class="text-xs-center">{{ props.item.overdue }}</td>
              <td class="text-xs-center">{{ props.item.completed }}</td>
              <td class="text-xs-center">{{ props.item.completedPercent }}</td>
              <td class="text-xs-center">{{ props.item.overduePercent }}</td>
            </template>
            <template slot="pageText"
                      slot-scope="props">
              共 {{ props.itemsLength }} 条，当前 {{ props.pageStart }} - {{ props.pageStop }} 条
            </template>
          </v-data-table>
          <!-- 分页 -->
          <!-- <v-flex class="paginationWrapper"
                  text-xs-center
                  v-show="true">
            <v-custompagination :pagination.sync="paginationStaff"></v-custompagination>
          </v-flex> -->
          <!-- </v-card> -->
        </v-flex>
      </v-layout>
    </v-container>
    <v-spacer style="height:10px"></v-spacer>
    <v-container fluid
                 fill-height
                 grid-list-lg
                 pa-0>
      <v-layout row
                wrap
                ma-0>
        <v-flex xs12
                class="task_title">组织内部每日新增及完成任务变化趋势</v-flex>
        <v-flex xs12>
          <form @submit.prevent="find"
                class="table-search">
            <v-layout row
                      align-baseline>
              <v-flex xs3
                      md3
                      lg3
                      style="max-width: 185px">
                <v-btn-toggle v-model="toggle_exclusive">
                  <v-btn @click="getWork"
                         flat
                         value="week">
                    近7天
                  </v-btn>
                  <v-btn @click="getMonth"
                         flat
                         value="month">
                    近30天
                  </v-btn>
                </v-btn-toggle>
              </v-flex>
              <v-flex xs3>
                <v-menu ref="startMenu"
                        :close-on-content-click="false"
                        v-model="startMenu"
                        :nudge-right="40"
                        :return-value.sync="startDate"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="200px">
                  <v-text-field slot="activator"
                                v-model="startDate"
                                label="开始时间"
                                readonly></v-text-field>
                  <v-date-picker v-model="startDate"
                                 no-title
                                 scrollable
                                 locale="zn">
                    <v-spacer></v-spacer>
                    <v-btn flat
                           color="primary"
                           @click="startMenu = false">取消</v-btn>
                    <v-btn flat
                           color="primary"
                           @click="$refs.startMenu.save(startDate)">确定</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-flex>
              <v-flex xs3>
                <v-menu ref="endMenu"
                        :close-on-content-click="false"
                        v-model="endMenu"
                        :nudge-right="40"
                        :return-value.sync="endDate"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="200px">
                  <v-text-field slot="activator"
                                v-model="endDate"
                                label="结束时间"
                                readonly></v-text-field>
                  <v-date-picker v-model="endDate"
                                 no-title
                                 scrollable
                                 :allowed-dates="allowedDates"
                                 locale="zn">
                    <v-spacer></v-spacer>
                    <v-btn flat
                           color="primary"
                           @click="endMenu = false">取消</v-btn>
                    <v-btn flat
                           color="primary"
                           @click="$refs.endMenu.save(endDate)">确定</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-flex>
              <!-- <v-flex class="search-form">
                <vc-schema-form tag="div"
                                :schema="search.schema"
                                :ui-schema="search.uiSchema"
                                v-model="params"></vc-schema-form>
              </v-flex> -->
              <v-btn type="submit"
                     small
                     color="primary"
                     class="ml-3 btn--search">查询</v-btn>
            </v-layout>
          </form>
        </v-flex>
        <v-flex xs12>
          <ve-line :data="chartDataLine"
                   :settings="chartSettings"
                   :extend="lineExtend"
                   :data-zoom="dataZoom"
                   :data-empty="dataEmpty"
                   :loading="bloading"></ve-line>
        </v-flex>
      </v-layout>
    </v-container>
  </v-card>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import 'v-charts/lib/style.css'
import moment from 'moment'
export default {
  data () {
    this.chartSettings = {
      stack: { '用户': ['访问用户', '下单用户'] },
      area: true
    }
    this.completedSettings = {
      dataType: {
        '完成度': 'percent'
      },
      seriesMap: {
        '完成度': {
          min: 0,
          max: 1
        }
      }
    }
    this.overduedSettings = {
      dataType: {
        '延期率': 'percent'
      },
      seriesMap: {
        '延期率': {
          min: 0,
          max: 1
        }
      }
    }
    this.dataZoom = [
      {
        type: 'slider',
        start: 0,
        end: 50,
        zoomLock: true
      }
    ]
    return {
      // startDate: new Date().toISOString().substr(0, 10),
      // endDate: new Date(new Date().setDate(new Date().getDate())).toISOString().substr(0, 10),
      startDate: moment().format('YYYY-MM-DD'),
      endDate: moment().format('YYYY-MM-DD'),
      startMenu: false,
      endMenu: false,
      pagination: {
        sortBy: 'completedPercent',
        descending: true
      },
      pageItems: [5, 10, 15, 20],
      toggle_exclusive: 'week',
      orgid: '',
      userid: '',
      orgTaskReport: {},
      completed: 0,
      overdue: 0,
      paginationStaff: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10,
        descending: false
      },
      chartDataLine: {
        columns: [],
        rows: []
      },
      bloading: true,
      dataEmpty: false,
      completedExtend: {
        series: {
          // 根据名字对应到相应的系列
          type: 'gauge',
          radius: '100%',
          center: ['50%', '60%'], // 仪表位置
          startAngle: 180,
          endAngle: 0,
          splitNumber: 1,
          axisLabel: {
            formatter: function (value) {
              return value * 100
            }
          },
          splitLine: {
            show: false
          },
          detail: {
            formatter: function (value) {
              return `任务完成度${(value * 100).toFixed(2)}%`
            },
            fontFamily: 'Microsoft YaHei',
            fontSize: '16',
            color: '#999999'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              width: 13, // 柱子的宽度
              'color': [
                [0, '#32c787'],
                [1, '#32c787']
              ]
            }
          }
        }
      },
      overduedExtend: {
        series: {
          // 根据名字对应到相应的系列
          type: 'gauge',
          radius: '100%',
          center: ['50%', '60%'], // 仪表位置
          startAngle: 180,
          endAngle: 0,
          splitNumber: 1,
          axisLabel: {
            formatter: function (value) {
              return value * 100
            }
          },
          splitLine: {
            show: false
          },
          detail: {
            formatter: function (value) {
              return `任务延期率${(value * 100).toFixed(2)}%`
            },
            fontFamily: 'Microsoft YaHei',
            fontSize: '16',
            color: '#999999'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              width: 13, // 柱子的宽度
              'color': [
                [0, '#f87895'],
                [1, '#f87895']
              ]
            }
          }
        }
      },
      lineExtend: {
        legend: {
          right: 0
        },
        // yAxis: {
        //   interval: 5
        // },
        xAxis: {
          axisLabel: {
            interval: 0
          }
        }
      },
      loading: false,
      completedData: {
        columns: [],
        rows: []
      },
      overduedData: {
        columns: [],
        rows: []
      },
      headers: [
        { text: '成员', align: 'left', sortable: false, value: 'name' },
        { text: '任务总数', align: 'center', sortable: false, value: 'all' },
        { text: '待完成', align: 'center', sortable: false, value: 'uncompleted' },
        { text: '已延期', align: 'center', sortable: false, value: 'overdue' },
        { text: '已完成', align: 'center', sortable: false, value: 'completed' },
        { text: '任务完成率', align: 'center', sortable: true, sort: 'desc', value: 'completedPercent' },
        { text: '任务延期率', align: 'center', sortable: false, value: 'overduePercent' }
      ],
      desserts: []
    }
  },
  watch: {
    'paginationStaff.page' (val) {
      this.queryTenantStaffTask()
    },
    'paginationStaff.rowsPerPage' (val) {
      this.queryTenantStaffTask()
    },
    startDate () {
      this.endDate = moment(this.startDate).format('YYYY-MM-DD')
    }
  },
  created () {
    this.orgid = global.helper.ls.get('user').loginorgid
    this.userid = global.helper.ls.get('user').id
    this.queryTenantTask()
    this.queryTenantStaffTask()
    this.toggle_exclusive = 'week'
    this.getWork()
  },
  methods: {
    allowedDates (val) {
      return val >= this.startDate
    },
    find () {
      this.toggle_exclusive = ''
      this.query()
    },
    getWork () {
      let start = moment().subtract('days', 6).startOf('day').valueOf()
      let end = moment().endOf('day').valueOf()
      this.query(start, end)
    },
    getMonth () {
      let start = moment().subtract('days', 29).startOf('day').valueOf()
      let end = moment().endOf('day').valueOf()
      this.query(start, end)
    },
    async query (start, end) {
      console.log(moment(this.startDate).startOf('day').format('YYYY-MM-DD HH:mm:ss').valueOf())
      console.log(moment(this.endDate).endOf('day').format('YYYY-MM-DD HH:mm:ss').valueOf())
      let startTime = moment(this.startDate).startOf('day').valueOf()
      let endTime = moment(this.endDate).endOf('day').valueOf()
      this.bloading = true
      let filterParam = {}
      let filter = {}
      filter.tenant = { $oid: this.orgid }
      filter.type = 'task.add.tenant'
      filter.statisticsOn = { $gt: start === undefined ? parseInt(startTime) : start, $lt: end === undefined ? parseInt(endTime) : end }
      filterParam.filter = filter
      filterParam.sort = { statisticsOn: 1 }

      let filterParam1 = {}
      let filter1 = {}
      filter1.tenant = { $oid: this.orgid }
      filter1.type = 'task.completed.tenant'
      filter1.statisticsOn = { $gt: start === undefined ? parseInt(startTime) : start, $lt: end === undefined ? parseInt(endTime) : end }
      filterParam1.filter = filter1
      filterParam1.sort = { statisticsOn: 1 }
      let { items: add } = await mongo.db().collection('statistics').find(filterParam)
      let { items: completed } = await mongo.db().collection('statistics').find(filterParam1)
      let row = []
      if (add.length > 0) {
        add.forEach(element => {
          let abc = { '日期': element.statisticsOn.$numberLong, '新增任务': element.add }
          row.push(abc)
        })
      }
      if (row.length > 0) {
        row.forEach(rowElement => {
          if (completed.length > 0) {
            completed.some(element => {
              if (rowElement['日期'] === element.statisticsOn.$numberLong) {
                rowElement['完成任务'] = element.completed
                return true
              } else {
                rowElement['完成任务'] = 0
              }
            })
          } else {
            rowElement['完成任务'] = 0
          }
          rowElement['日期'] = moment(parseInt(rowElement['日期'])).format('YYYY-MM-DD')
        })
      }
      if (row.length === 0) {
        this.dataEmpty = true
      } else {
        this.dataEmpty = false
      }
      this.bloading = false
      this.chartDataLine = {
        columns: ['日期', '新增任务', '完成任务'],
        rows: row
      }
    },
    async queryTenantTask () {
      let { items } = await mongo.db().collection('statistics').find({ filter: { 'tenant': { $oid: this.orgid }, type: 'task.tenant' }, sort: { createdOn: -1 } })
      if (items.length > 0) {
        this.orgTaskReport = items[0]
        this.completed = parseFloat(this.orgTaskReport.completed / this.orgTaskReport.all)
        this.overdue = parseFloat(this.orgTaskReport.overdue / this.orgTaskReport.all)
        this.completedData = {
          columns: ['type', 'value'],
          rows: [
            { type: '完成度', value: this.completed }
          ]
        }
        this.overduedData = {
          columns: ['type', 'value'],
          rows: [
            { type: '延期率', value: this.overdue }
          ]
        }
      } else {
        this.orgTaskReport.all = 0
        this.orgTaskReport.completed = 0
        this.orgTaskReport.uncompleted = 0
        this.orgTaskReport.overdue = 0
        this.completed = 0
        this.overdue = 0
        this.completedData = {
          columns: ['type', 'value'],
          rows: [
            { type: '完成度', value: this.completed }
          ]
        }
        this.overduedData = {
          columns: ['type', 'value'],
          rows: [
            { type: '延期率', value: this.overdue }
          ]
        }
      }
    },
    async queryTenantStaffTask () {
      this.desserts = []
      let filterParam = {}
      let filter = {}
      filter.tenant = { $oid: this.orgid }
      filter.type = 'task.tenantStaff'
      let hour = moment().hour()
      let hourStart = moment(hour, 'HH').startOf('hour').valueOf()
      let hourEnd = moment(hour, 'HH').endOf('hour').valueOf()
      filter.statisticsOn = { $gte: hourStart, $lte: hourEnd }
      filterParam.filter = filter
      filterParam.skip = (this.paginationStaff.page - 1) * this.paginationStaff.rowsPerPage
      filterParam.limit = this.paginationStaff.rowsPerPage
      let data = await mongo.db().collection('statistics').find(filterParam)
      this.paginationStaff.total = Number(data.total.$numberLong)
      let items = data.items
      items.forEach(element => {
        let user = {}
        mongo.db('iss').collection('tenant.staff').find({ filter: { '_id': { $oid: element.staff.$oid } } }).then(d => {
          if (d.items && d.items.length > 0) {
            user.name = d.items[0].name
          } else {
            user.name = '--'
          }
          user.completed = element.completed
          user.overdue = element.overdue
          user.uncompleted = element.uncompleted
          user.all = element.all
          user.completedPercent = `${(element.completed / element.all).toFixed(2)}%`
          user.overduePercent = `${(element.overdue / element.all).toFixed(2)}%`
          this.desserts.push(user)
        })
      })
    }
  }
}
</script>
<style scoped>
.box {
  border: 1px solid #c3c3c3;
}
.task_title {
  font-family: "Microsoft YaHei" !important;
  font-size: 16px;
  color: #999999;
}
.task_size {
  font-family: "DINPro-Bold";
  font-size: 30px;
  color: #666666;
}
.paginationWrapper {
  margin-top: 0px;
  margin-bottom: 0px;
}
.imgcenter {
  justify-content: center;
  align-items: center;
  display: -webkit-flex;
}
.search-form {
  max-width: 80%;
}
</style>

