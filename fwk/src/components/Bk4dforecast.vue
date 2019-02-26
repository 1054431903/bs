<template>
  <div id="forecast-4d">
    <div v-if="dayforecast && dayforecast.length > 0">
      <div v-for="(i, index) in [1, 2, 3, 4]"
           :key="index">
        <div :style="{marginLeft: i * 320 + 'px'}"
             class="dashline"></div>
      </div>
      <div class="canvas">
        <svg version="1.2"
             baseProfile="tiny"
             class="aw-weather-24-chart-svg">
          <g>
            <path stroke="#f1ab4f"
                  stroke-width="2"
                  stroke-opacity="1"
                  fill="none"
                  :d="day"></path>
            <circle v-for="(temperature, index) in temperatures['tempDay']"
                    :key="index"
                    :cx="getX(temperature.x, index)"
                    :cy="temperature.y"
                    r="4"
                    stroke="#f1ab4f"
                    stroke-width="1"
                    fill="#f1ab4f"></circle>
            <text v-for="(temperature, index) in temperatures['tempDay']"
                  :x="getX(temperature.x, index) - 10"
                  :key="index"
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
             class="aw-weather-24-chart-svg">
          <g>
            <path stroke="#79c2fc"
                  stroke-width="2"
                  stroke-opacity="1"
                  fill="none"
                  :d="night"></path>
            <circle v-for="(temperature, index) in temperatures['tempNight']"
                    :key="index"
                    :cx="getX(temperature.x, index)"
                    :cy="temperature.y"
                    r="4"
                    stroke="#79c2fc"
                    stroke-width="1"
                    fill="#79c2fc"></circle>
            <text v-for="(temperature, index) in temperatures['tempNight']"
                  :key="index"
                  :x="getX(temperature.x, index) - 10"
                  :y="temperature.y - 20"
                  font-size="13">{{temperature.forecast}}℃</text>
            <text :x="temperatures['tempNight'][0].x - 80"
                  :y="temperatures['tempNight'][0].y + 3"
                  font-size="13">最低气温</text>
          </g>
        </svg>
      </div>
      <table class="forecasttable">
        <tr class="forecasttable__tr">
          <td class="forecasttable__td"
              v-for="(i, index) in dayforecast"
              :key="index"
              v-if='index <= 4'>
            <div class="imgBkContainer">
              <img :src="i['dayiconpath']"
                   style="margin-top:13px;" />
            </div>
          </td>
        </tr>
        <tr class="forecasttable__tr">
          <td class="forecasttable__td"
              v-for="(i, index) in dayforecast"
              :key="index"
              v-if='index <= 4'>
            <div v-if="index === 0">
              今天&nbsp;&nbsp;{{i['predictDate'].substr(5, 2) + '月' + dayforecast[0]['predictDate'].substr(8, 2) + '日'}}
            </div>
            <div v-else-if="index === 1">
              明天&nbsp;&nbsp;{{i['predictDate'].substr(5, 2) + '月' + i['predictDate'].substr(8, 2) + '日'}}
            </div>
            <div v-else>
              {{ chineseDay(i.predictDate) }}&nbsp;&nbsp;{{i['predictDate'].substr(5, 2) + '月' + i['predictDate'].substr(8, 2) + '日'}}
            </div>
          </td>
        </tr>
        <tr class="forecasttable__tr">
          <td class="forecasttable__td"
              v-for="(i, index) in dayforecast"
              :key="index"
              v-if='index <= 4'>
            <div>
              {{i.conditionDay}}
            </div>
          </td>
        </tr>
        <tr class="forecasttable__tr">
          <td class="forecasttable__td"
              v-for="(i, index) in dayforecast"
              :key="index"
              v-if='index <= 4'>
            <div class="windlevel">
              {{i.windDirDay}}&nbsp;&nbsp;{{i.windLevelDay}}米/秒
            </div>
          </td>
        </tr>
      </table>
    </div>
    <div v-else
         class="nodata">
      <div style="text-align: center;">
        <img src="static/default.png"
             style="width:126px;height:89px;" />
      </div>
      <span style="font-size:16px;color:#999999;font-family:Microsoft Yahei;display:block">暂无未来5天预报数据</span>
    </div>
  </div>
</template>

<script>
import { getChinesesDay, getSVGPathByCoordinate } from '@/utils'
// 温度值转换为坐标值比例系数
const RATIO = 1.66666665
// 零上温度曲线显示范围
const HEIGHT = 94.66666665
// 温度坐标初始偏移
const BASE = [80, 80, 80, 81]
export default {
  props: {
    dayforecast: {
      type: Array
    }
  },
  data () {
    return {
      temperatures: { tempDay: null, tempNight: null },
      day: '',
      night: ''
    }
  },
  computed: {},
  watch: {
    dayforecast (v) {
      this.path()
    }
  },
  created () {
    this.path()
  },
  methods: {
    chineseDay (date) {
      try {
        date = new Date(date)
      } catch (err) {
        console.log(err)
        return date
      }
      return getChinesesDay(date)
    },
    path () {
      let forecast = this.dayforecast
      let coordinate = []
      let coordinateNight = []
      if (!forecast || forecast.length < 0) return
      // 构造各温度坐标数据
      for (let i = 0, left = BASE[0], tLen = forecast.length; i < tLen; i++) {
        coordinate[i] = {}
        coordinateNight[i] = {}
        Object.defineProperty(coordinate[i], 'id', { value: i })
        Object.defineProperty(coordinate[i], 'x', { value: left })
        Object.defineProperty(coordinate[i], 'y', { value: HEIGHT - forecast[i]['tempDay'] * RATIO })
        Object.defineProperty(coordinate[i], 'forecast', { value: forecast[i]['tempDay'] })
        Object.defineProperty(coordinateNight[i], 'id', { value: i })
        Object.defineProperty(coordinateNight[i], 'x', { value: left })
        Object.defineProperty(coordinateNight[i], 'y', { value: HEIGHT - forecast[i]['tempNight'] * RATIO })
        Object.defineProperty(coordinateNight[i], 'forecast', { value: forecast[i]['tempNight'] })
        left += BASE[i] * 4.5
      }
      this.temperatures['tempDay'] = coordinate
      this.day = getSVGPathByCoordinate(coordinate)
      this.temperatures['tempNight'] = coordinateNight
      this.night = getSVGPathByCoordinate(coordinateNight)
    },
    getX (tx, index) {
      let x = tx
      switch (index) {
        case 0:
          x = x + 80
          break
        case 1:
          x = x + 40
          break
        case 2:
          x = x + 5
          break
        case 3:
          x = x - 32
          break
        case 4:
          x = x - 72
          break
        case 5:
          x = x - 16
          break
        case 6:
          x = x - 25
          break
        default: x
      }
      return x
    }
  }
}
</script>

<style lang="less" scoped>
#forecast-4d {
  position: relative;
  height: 330px;
  width: 1630px;
  // overflow-x: auto;
  // color: red;
  background-color: white(233, 248, 145);
  .canvas {
    // border: 1px solid red;
    height: 95px;
    width: 1630px;
  }
  .aw-weather-24-chart-svg {
    // border: 1px solid red;
    height: 95px;
    width: 1630px;
  }
  .forecasttable {
    text-align: center;
    width: 1630px;
  }
  .windlevel {
    color: rgba(0, 0, 0, 0.54);
  }
  .dashline {
    position: absolute;
    font-size: 100px;
    z-index: 99999;
    border: 1px dashed rgb(236, 233, 233);
    height: 330px;
  }
  .imgBkContainer {
    background-color: #03a9f4 !important;
    border-color: #03a9f4 !important;
    border-radius: 50%;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    height: 71px;
    width: 71px;
    margin-left: 119px;
  }
  .nodata {
    line-height: 330px;
    text-align: center;
  }
}
</style>
