<template style="background-color:#fff">
  <div class="card--table pt-3 px-0" style="min-height:500px;">
    <v-data-table v-if="tableFlag" :headers="table.headers" :items="bkdata" rows-per-page-text="每页" :rows-per-page-items="table.pagination" class="elevation-1" no-data-text="未找到相应数据." no-results-text="没有匹配的数据.">
      <template slot="items" slot-scope="props">
        <td>{{ formatTime(props.item.datetime) }}</td>
        <td>{{ props.item.temperature }}</td>
        <td>{{ props.item.humidity }}</td>
        <td>{{ props.item.atmospheric }}</td>
        <td>{{ props.item.wspeed }}</td>
        <td>{{ props.item.wdirection }}</td>
        <td>{{ props.item.electric }}</td>
      </template>
      <template slot="pageText" slot-scope="props">
        共&nbsp;{{ props.itemsLength }}&nbsp;条 &nbsp;&nbsp;当前&nbsp;{{ props.pageStart }}-{{ props.pageStop }}&nbsp;条
      </template>
    </v-data-table>
    <div v-else style="text-align:center;margin-top:11%;">
      <img src="static/land/bk_weather/loading.gif"/>
    </div>
  </div>
</template>
<script>
import BkCommon from './BkCommon'
import { parseTime } from '@/utils'
export default {
  name: 'bk_table',
  mixins: [BkCommon],
  data () {
    return {
      table: {
        headers: [
          {
            text: '日期时间',
            align: 'left',
            value: 'datetime'
          },
          // { text: '时间', value: 'time' },
          { text: '温度(℃)', value: 'temperature' },
          { text: '湿度(%)', value: 'humidity' },
          { text: '压强(hPa)', value: 'atmospheric' },
          { text: '风速(m/s)', value: 'wspeed' },
          { text: '风向', value: 'wdirection', sortable: false },
          { text: '电量(%)', value: 'electric' }
        ],
        items: [],
        pagination: [{'text': '10条', 'value': 10}, {'text': '15条', 'value': 15}, {'text': '20条', 'value': 20}, {'text': 'All', 'value': -1}],
        totalItems: 0,
        loading: false,
        rowsPerPageItems: [1]
      },
      pagination: {
        startTime: null,
        stopTime: null
      },
      tableFlag: false
    }
  },
  computed: {
    bkdata: function () {
      return this.table.items
    }
  },
  methods: {
    formatTime (datetime) {
      let dt = new Date()
      dt.setTime(datetime)
      let dtstr = parseTime(dt, 'yyyy-MM-dd hh:mm:ss')
      return dtstr.substr(0, 16)
    },
    sortDesc (items, index, bool) {
      if (index === 'date' || index === 'time') {
        if (!bool) {
          return items.sort((a, b) => {
            return b.datetime - a.datetime
          })
        } else {
          return items.sort((a, b) => {
            return a.datetime - b.datetime
          })
        }
      } else {
        if (!bool) {
          return items.sort((a, b) => {
            return b[index] - a[index]
          })
        } else {
          return items.sort((a, b) => {
            return a[index] - b[index]
          })
        }
      }
    }
  }
}
</script>
<style>
</style>
