<template style="background-color:#fff">
 <div>
    <v-layout row>
      <v-flex class="mt-3" xs3 md3 lg3 style="max-width: 185px">
        <v-btn-toggle v-model="text" >
          <v-btn value="today" @click="getToday" flat style="border:solid 1px #ccc;border-right:none;height:30px;">
          今天
          </v-btn>
          <v-btn value="week" @click="getWork" flat style="border:solid 1px #ccc;border-right:none;height:30px;">
          近7天
          </v-btn>
          <v-btn value="month"  @click="getMonth" flat style="border:solid 1px #ccc;height:30px;">
          近30天
          </v-btn>
        </v-btn-toggle>
      </v-flex>
      <vc-schema-form :schema="search.schema" :ui-schema="search.uiSchema" v-model="search.params"></vc-schema-form>
      <v-flex class="mt-3 ml-5" xs2 md2 lg2>
        <div style="margin-left: 20px;max-width:35px;float:right">
          <img :src="liebiao_src"  @click="setLiebiaoSrc" style="cursor:pointer" />
        <small  :class="lc"> 列表</small>
        </div>
        <div style="max-width:35px;float:right">
          <img :src="tubiao_src" @click="setTubiaoSrc" style="cursor:pointer;margin-left: 8px;max-height: 21px;width: 25px;" />
          <small style="margin-left:6px;" :class="tc">图表</small>
        </div>
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs12>
        <bk_report v-if="table_report === 'report'" :device="device" :start="search.params.startTime" :end="search.params.stopTime"></bk_report>
        <bk_table v-if="table_report === 'table'" :device="device" :start="search.params.startTime" :end="search.params.stopTime"></bk_table>
      </v-flex>
    </v-layout>
  </div>
</template>
<script>
import BkReport from './BkReport'
import BkTable from './BkTable'
import Period from '@agrithings/common/util/period'
import { data as JsonschemaData } from '@vehiclecloud/json-schema'

const nowTime = new Date().getTime()
export default {
  name: 'bk_weather_detail',
  props: {
    device: {
      type: Object
    }
  },
  data () {
    let dateTime = new Date(new Date().toLocaleDateString()).getTime()
    let stopDate = new Date()
    stopDate.setDate(stopDate.getDate())
    stopDate.setHours(0, 0, 0, 0)
    let stopTime = stopDate.getTime()
    // var minDate = JsonschemaData.longToDateStr(stopTime - Period.day * 30)
    let maxDate = JsonschemaData.longToDateStr(stopTime)
    return {
      text: 'today',
      tubiao_src: '/static/land/list/tubiao1.png',
      liebiao_src: '/static/land/list/liebiao1.png',
      tc: 'small_color',
      lc: '',
      table_report: 'report',
      search: {
        schema: {
          type: 'object',
          properties: {
            startTime: { type: 'number', title: '开始', format: 'date' },
            stopTime: { type: 'number', title: '截止', format: 'date' }
          }
        },
        uiSchema: {
          'ui:title': '时段',
          'ui:class': { layout: true, row: true },
          startTime: { 'ui:props': (ctx, el, item) => ({ singleLine: true, hideDetails: true, min: JsonschemaData.longToDateStr(item.stopTime), max: JsonschemaData.longToDateStr(item.stopTime) }), 'ui:class': { 'mr-3': true } },
          stopTime: {
            'ui:props': (ctx, el, item) => ({ singleLine: true, hideDetails: true, min: JsonschemaData.longToDateStr(item.startTime), max: maxDate })
          }
        },
        params: {
          startTime: {$numberLong: String(dateTime)},
          stopTime: {$numberLong: String(dateTime)}
        }
      }
    }
  },
  methods: {
    getToday () {
      this.search.params.startTime = {'$numberLong': new Date(new Date().toLocaleDateString()).getTime()}
      this.search.params.stopTime = {'$numberLong': nowTime}
    },
    getWork () {
      this.search.params.startTime = {'$numberLong': nowTime - Period.week + Period.day}
      this.search.params.stopTime = {'$numberLong': nowTime}
    },
    getMonth () {
      this.search.params.startTime = {'$numberLong': nowTime - Period.day * 29}
      this.search.params.stopTime = {'$numberLong': nowTime}
    },
    setLiebiaoSrc () {
      this.table_report = 'table'
      this.lc = 'small_color'
      this.tc = ''
      this.liebiao_src = '/static/land/list/liebiao.png'
      this.tubiao_src = '/static/land/list/tubiao.png'
    },
    setTubiaoSrc () {
      this.table_report = 'report'
      this.tc = 'small_color'
      this.lc = ''
      this.tubiao_src = '/static/land/list/tubiao1.png'
      this.liebiao_src = '/static/land/list/liebiao1.png'
    }
  },
  created () {
    this.search.params.startTime = {'$numberLong': new Date(new Date().toLocaleDateString()).getTime()}
    this.search.params.stopTime = {'$numberLong': nowTime}
  },
  components: {
    bk_report: BkReport,
    bk_table: BkTable
  }
}
</script>
<style>
.small_color{
  color: #39BBB0;
}
</style>
