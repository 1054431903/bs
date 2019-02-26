<template>
  <div id="chart-24h"
       class="chart">
    <div class="canvas c-gap-top-large">
      <svg version="1.2"
           baseProfile="tiny"
           class="aw-weather-24-chart-svg"
           style="width: 100%;">
        <g>
          <path stroke="#6d7b90"
                stroke-width="2"
                stroke-opacity="1"
                fill="none"
                :d="path"></path>
          <circle v-for="temperature in temperatures"
                  :cx="temperature.x"
                  :cy="temperature.y"
                  r="2"
                  stroke="grey"
                  stroke-width="1"
                  fill="white"
                  :key="temperature.id"></circle>
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x-10"
                :y="temperature.y-10"
                font-size="13">{{isFah ? calcuFah(temperature.forecast.temp) : temperature.forecast.temp}}{{isFah ? "℉" : "℃"}}</text>
          <text :x="temperatures[0].x - 90"
                :y="temperatures[0].y"
                font-size="13">温度</text>
        </g>
        <g>
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x+5"
                :y="temperatures[0].y+35"
                fill="rgb(0, 0, 0, 0.7)">{{temperature.forecast.rainrate}}</text>
          <text :x="temperatures[0].x - 90"
                :y="temperatures[0].y+35"
                font-size="13">降雨概率</text>
        </g>
        <g>
          <rect v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x-10"
                :y="temperatures[0].y+45"
                width="61.5"
                height="20"
                fill="#ced6d8"
                rx="5"
                ry="5" />
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x+10"
                :y="temperatures[0].y+60"
                fill="white">{{temperature.forecast.humidity}}%</text>
          <text :x="temperatures[0].x - 90"
                :y="temperatures[0].y+60"
                font-size="13">湿度</text>
        </g>
        <g>
          <rect v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x-10"
                :y="temperatures[0].y+70"
                width="61.5"
                height="20"
                fill="#95d6d9"
                rx="5"
                ry="5" />
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x+7"
                :y="temperatures[0].y+85"
                fill="white">{{temperature.forecast.pressure}}</text>
          <text :x="temperatures[0].x - 90"
                :y="temperatures[0].y+85"
                font-size="13">气压</text>
        </g>
        <g>
          <rect v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x-10"
                :y="temperatures[0].y+95"
                width="61.5"
                height="20"
                fill="#94b2b7"
                rx="5"
                ry="5" />
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x+7"
                :y="temperatures[0].y+110"
                fill="white">{{temperature.forecast.windforce}}</text>
          <text :x="temperatures[0].x - 90"
                :y="temperatures[0].y+110"
                font-size="13">风速</text>
        </g>
        <g>
          <text v-for="temperature in temperatures"
                :key="temperature.forecast.temp.time"
                :x="temperature.x+5"
                :y="temperatures[0].y+130">{{temperature.forecast.time}}</text>
        </g>
      </svg>
    </div>
  </div>
</template>

<script>
import { getSVGPathByCoordinate } from '@/utils'
// 温度值转换为坐标值比例系数
const RATIO = 1.66666665
// 零上温度曲线显示范围
const HEIGHT = 94.66666665
// 温度坐标初始偏移
const BASE = 31.5
export default {
  props: {
    isFah: {
      type: Boolean,
      default: false
    },
    forecast: {
      type: Object,
      default: []
    }
  },
  data () {
    return {
      // forecast: {}, // 预测数据
      temperatures: '' // 温度
    }
  },
  computed: {
    /**
     * 温度值转换为坐标值
     * @return {String}       SVG路径值
     */
    path () {
      var forecast = this.forecast
      var coordinate = []
      // let temp = Array.from(forecast.temp, val => val.temp)
      if (!forecast.temp) return
      // 构造各温度坐标数据
      for (let i = 0, left = BASE + 60; i < forecast.temp.length; i++) {
        coordinate[i] = {}
        Object.defineProperty(coordinate[i], 'id', { value: i })
        Object.defineProperty(coordinate[i], 'x', { value: left })
        Object.defineProperty(coordinate[i], 'y', { value: HEIGHT - forecast.temp[i].temp * RATIO - 40 })
        Object.defineProperty(coordinate[i], 'forecast', { value: forecast.temp[i] })
        left += BASE * 2
      }
      this.temperatures = coordinate
      return getSVGPathByCoordinate(coordinate)
    }
  },
  methods: {
    calcuFah (temp) {
      return temp * 9 / 5 + 32
    }
  },
  created () {
    // const that = this
    // console.log(that.forecast)
    // that.forecast = {
    //   temp: [
    //     {
    //       temp: 12,
    //       time: '00:00',
    //       rainrate: '10%',
    //       humidity: 44,
    //       pressure: 1012,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 8,
    //       time: '01:00',
    //       rainrate: '10%',
    //       humidity: 41,
    //       pressure: 1010,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 10,
    //       time: '02:00',
    //       rainrate: null,
    //       humidity: 41,
    //       pressure: 1010,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 5,
    //       time: '03:00',
    //       rainrate: null,
    //       humidity: 43,
    //       pressure: 1010,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 7,
    //       time: '04:00',
    //       rainrate: null,
    //       humidity: 51,
    //       pressure: 1008,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 8,
    //       time: '05:00',
    //       rainrate: null,
    //       humidity: 68,
    //       pressure: 1009,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 10,
    //       time: '06:00',
    //       rainrate: null,
    //       humidity: 84,
    //       pressure: 1010,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 15,
    //       time: '07:00',
    //       rainrate: null,
    //       humidity: 96,
    //       pressure: 1012,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 17,
    //       time: '08:00',
    //       rainrate: null,
    //       humidity: 98,
    //       pressure: 1012,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 20,
    //       time: '09:00',
    //       rainrate: null,
    //       humidity: 97,
    //       pressure: 1013,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 22,
    //       time: '10:00',
    //       rainrate: null,
    //       humidity: 92,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 25,
    //       time: '11:00',
    //       rainrate: null,
    //       humidity: 89,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 26,
    //       time: '12:00',
    //       rainrate: null,
    //       humidity: 91,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 25,
    //       time: '13:00',
    //       rainrate: null,
    //       humidity: 94,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 25,
    //       time: '14:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 21,
    //       time: '15:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 21,
    //       time: '16:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 20,
    //       time: '17:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 18,
    //       time: '18:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 15,
    //       time: '19:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 13,
    //       time: '20:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 5,
    //       time: '21:00',
    //       rainrate: null,
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 8,
    //       time: '22:00',
    //       rainrate: '55%',
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     },
    //     {
    //       temp: 10,
    //       time: '23:00',
    //       rainrate: '55%',
    //       humidity: 100,
    //       pressure: 1014,
    //       windforce: '微风'
    //     }]
    // }
    // 请求后台模拟预测的数据
    // that.$http.get('http://wthrcdn.etouch.cn/weather_mini?citykey=101010100').then((data) => {
    //   console.log(data.data.data)
    //   that.forecast = data.data.data
    // })
  }
}
</script>

<style lang="less" scoped>
#chart-24h {
  position: relative;
  height: 230px;
  width: 1630px;
  overflow: hidden;
  // color: red;
  background-color: white(233, 248, 145);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  .canvas {
    white-space: nowrap;
    overflow: auto;
    position: relative;
    height: 100%;
  }
  .chart-item {
    position: relative;
    vertical-align: top;
    // left: -5px;
    display: inline-block;
    text-align: center;
    width: 63px;
    box-sizing: border-box;
    .item-txt {
      font-size: 13px;
    }
    .weather-icons {
      margin-top: 5px;
      font-size: 24px;
      position: relative;
    }
    .item-num {
      position: relative;
    }
  }
  .aw-weather-24-chart-svg {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    // padding-left: 50px;
  }
}
.chart {
  overflow: hidden;
}
#chart-6d,
.chart .title {
  text-align: center;
}
</style>
