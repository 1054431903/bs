<template>
  <div id="forecast-4d"
       v-resize="onResize">
    <div v-for="(i, index) in [1, 2, 3, 4, 5, 6]"
         :key="index">
      <div :style="{marginLeft: i * 230 + 'px'}"
           class="dashline"></div>
    </div>
    <div class="canvas">
      <svg version="1.2"
           baseProfile="tiny"
           class="aw-weather-7-chart-svg">
        <g>
          <path stroke="#f1ab4f"
                stroke-width="2"
                stroke-opacity="1"
                fill="none"
                :d="path('tempDay')"></path>
          <circle v-for="(temperature, index) in temperatures['tempDay']"
                  :key="index+'-label4'"
                  :cx="getX(temperature.x, index)"
                  :cy="temperature.y"
                  r="4"
                  stroke="#f1ab4f"
                  stroke-width="1"
                  fill="#f1ab4f"></circle>
          <text v-for="(temperature, index) in temperatures['tempDay']"
                :key="index+'-label3'"
                :x="getX(temperature.x, index) - 10"
                :y="temperature.y - 20"
                font-size="13">{{temperature.forecast}}℃</text>
          <text :x="temperatures['tempDay'][0].x - 80"
                :y="temperatures['tempDay'][0].y + 3"
                font-size="13">最高气温</text>
        </g>
      </svg>
    </div>
    <div class="canvas">
      <svg version="1.2"
           baseProfile="tiny"
           class="aw-weather-7-chart-svg">
        <g>
          <path stroke="#79c2fc"
                stroke-width="2"
                stroke-opacity="1"
                fill="none"
                :d="path('tempNight')"></path>
          <circle v-for="(temperature, index) in temperatures['tempNight']"
                  :cx="getX(temperature.x, index)"
                  :cy="temperature.y"
                  r="4"
                  stroke="#79c2fc"
                  stroke-width="1"
                  fill="#79c2fc"
                  :key="index+'-label2'"></circle>
          <text v-for="(temperature, index) in temperatures['tempNight']"
                :key="index+'-label1'"
                :x="getX(temperature.x, index) - 10"
                :y="temperature.y - 20"
                font-size="13">{{temperature.forecast}}℃</text>
          <text :x="temperatures['tempNight'][0].x - 80"
                :y="temperatures['tempNight'][0].y - 10"
                font-size="13">最低气温</text>
        </g>
      </svg>
    </div>
    <table class="forecasttable">
      <tr class="forecasttable__tr">
        <td class="forecasttable__td"
            v-for="i in [0, 1, 2, 3, 4, 5, 6]"
            :key="i">
          <div class="imgContainer">
            <img :src="dayforecast[i]['dayiconpath']"
                 style="margin-top: 10px;" />
          </div>
        </td>
      </tr>
      <tr class="forecasttable__tr">
        <td class="forecasttable__td"
            v-for="i in [0, 1, 2, 3, 4, 5, 6]"
            :key="i">
          <div v-if="i === 0">
            今天&nbsp;&nbsp;{{dayforecast[i]['predictDate'].substr(5, 2) + '月' + dayforecast[0]['predictDate'].substr(8, 2) + '日'}}
          </div>
          <div v-else-if="i === 1">
            明天&nbsp;&nbsp;{{dayforecast[i]['predictDate'].substr(5, 2) + '月' + dayforecast[i]['predictDate'].substr(8, 2) + '日'}}
          </div>
          <div v-else>
            {{ chineseDay(dayforecast[i].predictDate) }}&nbsp;&nbsp;{{dayforecast[i]['predictDate'].substr(5, 2) + '月' + dayforecast[i]['predictDate'].substr(8, 2) + '日'}}
          </div>
        </td>
      </tr>
      <tr class="forecasttable__tr">
        <td class="forecasttable__td"
            v-for="i in [0, 1, 2, 3, 4, 5, 6]"
            :key="i">
          <div>
            {{dayforecast[i].conditionDay}}
          </div>
        </td>
      </tr>
      <tr class="forecasttable__tr">
        <td class="forecasttable__td"
            v-for="i in [0, 1, 2, 3, 4, 5, 6]"
            :key="i">
          <div class="windlevel">
            {{dayforecast[i].windDirDay}}&nbsp;&nbsp;{{dayforecast[i].windLevelDay}}级
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { getChinesesDay, getSVGPathByCoordinate } from '@/utils'
// 温度值转换为坐标值比例系数
const RATIO = 1.66666665
// 零上温度曲线显示范围
const HEIGHT = 94.66666665
// 温度坐标初始偏移
const BASE = 81
export default {
  props: {
    isFah: {
      type: Boolean,
      default: false
    },
    dayforecast: {
      type: Array,
      default: [{
        predictDate: '2018-03-21',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-22',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-23',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-24',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-25',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-26',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }, {
        predictDate: '2018-03-22',
        conditionDay: '晴',
        tempDay: '20',
        tempNight: '9',
        windDirDay: '北风',
        windLevelDay: '1',
        dayiconpath: ''
      }] // 未来七天天气
    }
  },
  data () {
    return {
      temperatures: { tempDay: null, tempNight: null },
      windowSize: {
        x: 0,
        y: 0
      }
    }
  },
  computed: {},
  watch: {
    windowSize: function (v) {
      console.log(v.x + '===' + v.y)
    }
  },
  mouted () {
    this.onResize()
  },
  methods: {
    onResize () {
      this.windowSize = { x: window.innerWidth, y: window.innerHeight }
    },
    chineseDay (date) {
      try {
        date = new Date(date)
      } catch (err) {
        console.log(err)
        return date
      }
      return getChinesesDay(date)
    },
    path (day) {
      var forecast = this.dayforecast
      var coordinate = []
      // let temp = Array.from(forecast.temp, val => val.temp)
      if (!forecast || forecast.length < 0) return
      // 构造各温度坐标数据
      for (let i = 0, left = BASE; i < forecast.length; i++) {
        coordinate[i] = {}
        Object.defineProperty(coordinate[i], 'id', { value: i })
        Object.defineProperty(coordinate[i], 'x', { value: left })
        Object.defineProperty(coordinate[i], 'y', { value: HEIGHT - parseInt(forecast[i][day]) * RATIO - 50 })
        Object.defineProperty(coordinate[i], 'forecast', { value: forecast[i][day] })
        left += BASE * 3
        console.log(coordinate[i])
      }
      this.temperatures[day] = coordinate
      return getSVGPathByCoordinate(coordinate)
    },
    getX (tx, index) {
      let x = tx
      switch (index) {
        case 0:
          x = x + 35
          break
        case 1:
          x = x + 20
          break
        case 2:
          x = x + 5
          break
        case 3:
          x = x - 5
          break
        case 4:
          x = x - 12
          break
        case 5:
          x = x - 25
          break
        case 6:
          x = x - 25
          break
        default: x
      }
      return x
    }
  },
  created () {
    // console.log(this.dayforecast)
  }
}
</script>

<style lang="less" scoped>
#forecast-4d {
  position: relative;
  height: 330px;
  width: 1630px;
  background-color: white(233, 248, 145);
  .canvas {
    // border: 1px solid red;
    height: 95px;
    width: 1630px;
  }
  .aw-weather-7-chart-svg {
    // border: 1px solid red;
    height: 95px;
    width: 1630px;
  }
  .forecasttable {
    text-align: center;
    width: 1630px;
    //  width: 100%;
  }
  .forecasttable__td {
    // width: 140px;
    color: #333333;
    font-size: 16px;
  }
  .windlevel {
    color: rgba(0, 0, 0, 0.54);
    font-size: 14px;
  }
  .dashline {
    position: absolute;
    font-size: 100px;
    z-index: 99999;
    border: 1px dashed rgb(236, 233, 233);
    height: 330px;
  }
  .imgContainer {
    background-color: #03a9f4 !important;
    border-color: #03a9f4 !important;
    border-radius: 50%;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    height: 71px;
    width: 71px;
    margin-left: 75px;
    margin-right: 75px;
  }
}
</style>
