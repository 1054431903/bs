<template style="background-color:#fff">
  <v-layout row
            wrap
            v-if="tableFlag && tf">
    <v-flex xs12
            v-if="sensorTypeArr['61'] && f8">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData"
                 :settings="chartSettings"
                 :colors="['#A9E5E1']"
                 :grid="grid4"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['62'] && f1">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData1"
                 :settings="chartSettings1"
                 :colors="['#D6D7D9']"
                 :grid="grid5"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['63'] && f2">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData2"
                 :settings="chartSettings2"
                 :colors="['#A9E5E1']"
                 :grid="grid1"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['64'] && f7">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData7"
                 :settings="chartSettings7"
                 :yAxis="w_direction"
                 :colors="['#F5784E']"
                 :tooltip="tooltip"
                 :grid="grid3"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['67'] && f4">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData4"
                 :settings="chartSettings4"
                 :colors="['#95C4E3']"
                 :grid="grid2"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['69'] && f5">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData5"
                 :settings="chartSettings5"
                 :colors="['#C0C2C4']"
                 :grid="grid4"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['68'] && f6">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData6"
                 :settings="chartSettings6"
                 :colors="['#F9BBA9']"
                 :grid="grid1"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
    <v-flex xs12
            v-if="sensorTypeArr['66'] && f3">
      <v-card dark
              color="white"
              style="height:200px;max-width:1830px;">
        <ve-line :data="chartData3"
                 :settings="chartSettings3"
                 :colors="['#D599B0']"
                 :grid="grid1"
                 :legend-visible="false"></ve-line>
      </v-card>
    </v-flex>
  </v-layout>
  <!-- <div v-else-if="!tableFlag" style="position:absolute;left:0;right:0;bottom:0;top:0;z-index:9999;opacity:0.3;text-align:center;line-height:800px;"> -->
  <div v-else-if="!tableFlag"
       style="text-align:center;margin-top:11%;min-height:200px;">
    <img src="static/land/bk_weather/loading.gif" />
  </div>
  <div v-else
       style="text-align:center;margin-top:11%;min-height:200px;">
    <div style="text-align: center;">
      <img src="static/default.png"
           style="width:126px;height:89px;" />
    </div>
    <span style="font-size:16px;color:#999999;font-family:Microsoft Yahei;display:block">暂无历史天空视频数据</span>
  </div>
</template>
<script>
import BkCommon from './BkCommon'
function windfomat (direction) {
  switch (direction) {
    case 0:
      return '北风'
    case 45:
      return '东北风'
    case 90:
      return '东风'
    case 135:
      return '东南风'
    case 180:
      return '南风'
    case 225:
      return '西南风'
    case 270:
      return '西风'
    case 315:
      return '西北风'
    default:
      return ''
  }
}
export default {
  name: 'bk_report',
  mixins: [BkCommon],
  data () {
    return {
      w_direction: {
        name: '风向',
        axisLabel: {
          formatter: function (value) {
            let temp = []
            if (value === 0) {
              temp.push('北风')
            } else if (value === 45) {
              temp.push('东北风')
            } else if (value === 90) {
              temp.push('东风')
            } else if (value === 135) {
              temp.push('东南风')
            } else if (value === 180) {
              temp.push('南风')
            } else if (value === 225) {
              temp.push('西南风')
            } else if (value === 270) {
              temp.push('西风')
            } else if (value === 315) {
              temp.push('西北风')
            }
            return temp
          }
        },
        max: 315,
        interval: 45
      },
      tooltip: {
        trigger: 'axis',
        formatter: function (params, ticket, callback) {
          let tmp = params[0].name + '</br>风向:&nbsp&nbsp' + windfomat(params[0].value[1])
          return tmp
        }
      },
      tableFlag: false,
      devType: {},
      sensorTypeArr: {},
      sensorType: [],
      chartData: {},
      chartSettings: {},
      chartData1: {},
      chartSettings1: {},
      chartData2: {},
      chartSettings2: {},
      chartData3: {},
      chartSettings3: {},
      chartData4: {},
      chartSettings4: {},
      chartData5: {},
      chartSettings5: {},
      chartData6: {},
      chartSettings6: {},
      chartData7: {},
      chartSettings7: {},
      grid1: {},
      grid2: {},
      grid3: {},
      grid4: {},
      f1: false,
      f2: false,
      f3: false,
      f4: false,
      f5: false,
      f6: false,
      f7: false,
      f8: false,
      tf: false
    }
  },
  created () {
    let _this = this
    _this.devType = this.$store.state['base.device.type'].items.filter(value => value._id.$oid === _this.device.type.$oid)
    let ss = _this.devType[0].sensorTypes
    let sa = []
    if (ss && ss.length > 0) {
      ss.forEach(_s => {
        sa.push(_this.$store.state['base.sensor.type'].items.filter(_vl => _s.$oid === _vl._id.$oid)[0])
      })
      if (sa && sa.length > 0) {
        sa.forEach(_s => {
          _s.code === '61' ? _this.sensorTypeArr['61'] = true : _s.code === '62' ? _this.sensorTypeArr['62'] = true : _s.code === '63' ? _this.sensorTypeArr['63'] = true : _s.code === '64' ? _this.sensorTypeArr['64'] = true : _s.code === '65' ? _this.sensorTypeArr['65'] = true : _s.code === '66' ? _this.sensorTypeArr['66'] = true : _s.code === '67' ? _this.sensorTypeArr['67'] = true : _s.code === '68' ? _this.sensorTypeArr['68'] = true : _s.code === '69' ? _this.sensorTypeArr['69'] = true : _this.sensorTypeArr['70'] = true
        })
      }
    }
    this.chartSettings = {
      yAxisName: ['温度(℃)'],
      colors: ['#A7E5E2'],
      area: true
    }
    this.grid1 = {
      show: true,
      left: 30,
      top: 30,
      height: 150,
      right: 30
    }
    this.grid2 = {
      show: true,
      left: 30,
      top: 30,
      height: 150,
      right: 30
    }
    this.grid3 = {
      show: true,
      top: 30,
      height: 150,
      left: 30,
      right: 30
    }
    this.grid4 = {
      show: true,
      left: 30,
      top: 30,
      height: 150,
      right: 30
    }
    this.grid5 = {
      show: true,
      left: 30,
      top: 30,
      height: 150,
      right: 30
    }
    this.chartSettings1 = {
      yAxisName: ['湿度(%)'],
      area: true
    }
    this.chartSettings2 = {
      yAxisName: ['风速(m/s)'],
      area: true
    }
    this.chartSettings3 = {
      yAxisName: ['电量(%)'],
      area: true
    }
    this.chartSettings4 = {
      yAxisName: ['大气压(hPa)'],
      area: true
    }
    this.chartSettings5 = {
      yAxisName: ['每小时降雨量(mm)'],
      area: true
    }
    this.chartSettings6 = {
      yAxisName: ['紫外线指数(lux)'],
      area: true
    }
    this.chartSettings7 = {
      yAxisName: ['风向'],
      area: true
    }
  }
}
</script>
<style>
</style>
