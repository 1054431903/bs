<template>
  <div class="page">
    <div class="page-body">
      <v-menu ref="selector" class="selector" content-class="selector" full-width flat transition="false">
        <div class="selector-head" slot="activator">
          <div class="selector-head-item"
               :class="{'bold': selector === indicatorSelector}"
               @click="switchSelector(indicatorSelector)">
            {{indicatorSelector.current().name}}
          </div>
          <div class="selector-head-item"
               :class="{'bold': selector === timeRangeSelector}"
               @click="switchSelector(timeRangeSelector)">
            {{timeRangeSelector.current().name}}
          </div>
        </div>
        <div class="selector-popup" :class="{'hidden': selector !== indicatorSelector}">
          <div v-for="(item,index) in indicatorSelector.items" :key="index">
            <div class="selector-popup-item-separator">&nbsp;</div>
            <div class="selector-popup-item"
                 :class="{'selector-popup-item-selected': indicatorSelector.current() === item}"
                 @click="switchSelectorItem(indicatorSelector, index)">
              <span class="selector-popup-item-title">{{item.name}}</span>
              <span class="selector-popup-item-mark"
                    :class="{'hidden': indicatorSelector.current() !== item}">√</span>
            </div>
          </div>
        </div>
        <div class="selector-popup" :class="{'hidden': selector !== timeRangeSelector}">
          <div v-for="(item,index) in timeRangeSelector.items" :key="index">
            <div class="selector-popup-item-separator">&nbsp;</div>
            <div class="selector-popup-item"
                 :class="{'selector-popup-item-selected': timeRangeSelector.current() === item}"
                 @click="switchSelectorItem(timeRangeSelector, index)">
              <span class="selector-popup-item-title">{{item.name}}</span>
              <span class="selector-popup-item-mark"
                    :class="{'hidden': timeRangeSelector.current() !== item}">√</span>
            </div>
          </div>
        </div>
      </v-menu>
      <div v-if="loading" class="loading">
        <div class="separator">&nbsp;</div>
        <div class="loading-body">
          <img style="width: 20px;height: 20px" src="loading_chart.gif"/>
          <div>数据加载中，请稍后...</div>
        </div>
      </div>
      <div v-else>
        <div class="chart" v-for="(chart,index) of charts" :key="index" v-if="chart.type === 0">
          <div class="chart-separator">&nbsp;</div>
          <div class="chart-body">
            <ve-line height="265px"
                     :title="chart.title"
                     :data="chart.data"
                     :settings="chart.settings"
                     :series="chart.series"
                     :tooltip-visible="true"
                     :legend-visible="false"
                     :data-zoom="chart.dataZoom"
                     :toolbox="chart.toolbox"
                     :colors="chart.colors"
                     :extend="chart.extend"
                     :data-empty="chart.isEmpty"
            >
            </ve-line>
          </div>
        </div>
      </div>
    </div>
    <v-dialog v-if="dialog.show" v-model="dialog.show" fullscreen class="mx-0 my-0 px-0 py-0">
      <div style="background-color: white" :style="dialog.chart.style">
        <ve-line v-if="chart"
                 height="100%"
                 :title="chart.title"
                 :data="chart.data"
                 :settings="chart.settings"
                 :series="chart.series"
                 :tooltip-visible="true"
                 :legend-visible="false"
                 :data-zoom="chart.dataZoom"
                 :toolbox="chart.toolbox"
                 :colors="chart.colors"
                 :extend="chart.extend"
                 :data-empty="chart.isEmpty"
        >
        </ve-line>
      </div>
    </v-dialog>
  </div>
</template>

<script>
import Share from './share'
import Moment from 'moment'
import 'echarts/lib/component/title'
import 'echarts/lib/component/dataZoom'
import 'v-charts/lib/style.css'
export default {
  name: 'iot-device-soil',
  mixins: [Share],
  data () {
    return {
      loading: false,
      dialog: {
        show: false,
        chart: {
          style: {
          }
        }
      },
      chart: null,
      device: null,
      selector: null,
      indicatorSelector: {
        index: 0,
        items: [{
          name: '全部指标'
        }],
        current () {
          return this.items[this.index]
        }
      },
      timeRangeSelector: {
        index: 0,
        items: [{
          name: '今天',
          days: 1
        }, {
          name: '最近7天',
          days: 7
        }, {
          name: '最近30天',
          days: 30
        }],
        current () {
          return this.items[this.index]
        }
      }
    }
  },
  watch: {
    'indicatorSelector.index' (nIndex, oIndex) {
      this.load()
    },
    'timeRangeSelector.index' (nIndex, oIndex) {
      this.load()
    },
    'orientation' (nValue, oValue) {
      let width = document.documentElement.clientWidth
      let height = document.documentElement.clientHeight
      if (width < height) {
        [width, height] = [height, width]
      }
      if (window.native && window.native.getStatusBarHeight) {
        height = height - window.native.getStatusBarHeight()
      }
      switch (nValue) {
        case 'portrait':
          this.dialog.chart.style = {
            position: 'relative',
            width: width + 'px',
            height: height + 'px',
            top: 0,
            left: height + 'px',
            transform: 'rotate(90deg)',
            transformOrigin: 'left top 0'
          }
          break
        case 'landscape':
          this.dialog.chart.style = {
            position: 'relative',
            width: width + 'px',
            height: height + 'px',
            top: 0,
            left: 0,
            transform: 'rotate(0deg)',
            transformOrigin: 'left top 0'
          }
          break
        default:
          break
      }
    }
  },
  created () {
    this.queryDevice(this.$route.params._id).then(item => {
      this.device = item
      if (this.device) {
        this.device.sensors.filter(sensor => {
          return sensor.chart
        }).forEach(sensor => {
          if (sensor.isIndicator) {
            if (!sensor.isCombineItem) {
              this.indicatorSelector.items.push({
                name: sensor.name,
                code: sensor.code
              })
            }
          }
        })
      }
      this.load()
    })
  },
  mounted () {

  },
  computed: {
  },
  methods: {
    switchSelector (selector) {
      if (this.selector !== selector) {
        this.selector = selector
      }
    },
    switchSelectorItem (selector, index) {
      if (selector.index !== index) {
        selector.index = index
      }
    },
    async load () {
      try {
        this.loading = true
        if (!this.device) {
          return
        }
        let id = this.device.id
        let days = this.timeRangeSelector.current().days
        let dateFormat = days > 1 ? 'MM-DD HH:mm' : 'HH:mm'
        let now = new Date()
        let beginTime = new Date(now.getFullYear(), now.getMonth(), now.getDate() - days + 1)
        let endTime = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1)
        let result = await this.queryDeviceSensorData(id, beginTime, endTime)
        let chartData = new Map()
        let emptyPoint = {}
        for (let cursorTime = beginTime.getTime(); cursorTime < endTime.getTime(); cursorTime += 60 * 60 * 1000) {
          chartData.set(Moment(new Date(cursorTime)).format(dateFormat), emptyPoint)
        }
        this.device.sensors.forEach(sensor => {
          if (sensor.chart) {
            if (sensor.chart.extend && sensor.chart.extend.xAxis) {
              sensor.chart.extend.xAxis.data = [...chartData.keys()]
            }
            sensor.chart.data.rows = []
            if (result) {
              result.forEach(item => {
                if (item.time) {
                  if (!sensor.isCombine) {
                    let data = item.data[sensor.code]
                    if (data && !(typeof data.v === 'undefined')) {
                      let point = {}
                      point.v = data.v
                      point.timestamp = item.time
                      for (let i = 0; i < 10; i++) {
                        point.time = Moment(point.timestamp.getTime() + 1000 * 60 * i).format(dateFormat)
                        if (chartData.has(point.time)) {
                          chartData.set(point.time, point)
                          break
                        }
                      }
                    }
                  } else {
                    let dataArray = sensor.subSensors.map(sensor => item.data[sensor.code])
                    let data = dataArray[0]
                    if (data && !(typeof data.v === 'undefined')) {
                      let point = {}
                      point.v = {
                        value: data.v,
                        values: dataArray.map(data => data.v),
                        toString () {
                          return this.v
                        }
                      }
                      point.timestamp = item.time
                      for (let i = 0; i < 10; i++) {
                        point.time = Moment(point.timestamp.getTime() + 1000 * 60 * i).format(dateFormat)
                        if (chartData.has(point.time)) {
                          chartData.set(point.time, point)
                          break
                        }
                      }
                    }
                  }
                }
              })
            }
            sensor.chart.data.rows = [...chartData.values()]
            console.debug(sensor.chart.data.rows)
            if (sensor.chart.type === 0) {
              sensor.chart.toolbox.feature.myFullScreen.onclick = () => {
                this.dialog.show = !this.dialog.show
                if (this.dialog.show) {
                  this.chart = sensor.chart
                } else {
                  this.chart = undefined
                }
              }
            }
            if (sensor.chart.type === 1) {
              sensor.chart.data.rows.forEach(image => {
                let img = document.createElement('img')
                img.src = image.v
                image.src = image.v
                image.w = img.naturalWidth
                image.h = img.naturalHeight
              })
            }
          }
        })
        this.charts = []
        let code = this.indicatorSelector.current().code
        this.indicatorSelector.items.filter(indicator => {
          return indicator.code && indicator.code.trim().length > 0
        }).map(indicator => {
          return this.device.sensors.find(sensor => sensor.code === indicator.code)
        }).filter(sensor => {
          if (code) {
            return code === sensor.code
          } else {
            return true
          }
        }).forEach((sensor, index) => {
          sensor.chart.colors = this.colors[index % this.colors.length]
          this.charts.push(sensor.chart)
        })
        this.charts.forEach(chart => {
          chart.isEmpty = !chart.data.rows.find(point => point !== emptyPoint)
          chart.data.rows[0] = {time: 0, v: 0}
          // if (chart.data.rows.length <= 0) {
          //   chart.data.rows.push()
          //   chart.isEmpty = true
          // } else {
          //   chart.isEmpty = false
          // }
        })
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    preview (index, images) {
      this.$preview.open(index, images)
    }
  }
}
</script>

<style scoped>
  .hidden {
    display: none;
  }
  .bold {
    font-weight: bold;
  }
  .separator {
    height: 10px;
    background-color: rgb(245, 246, 245);
  }
  .page {
    height: 100%;
    background-color: white;
  }
  .page-body {
    height: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
  }
  .selector {
    border-radius: 0;
    box-shadow: none;
    background-color: #ffffff;
  }
  .selector-head {
    display: flex;
    flex-flow: row nowrap;
  }
  .selector-head-item {
    flex: auto;
    height: 33px;
    width: 50%;
    line-height: 33px;
    font-size: 13px;
    color: rgb(102, 102, 102);
    text-align: center;
  }
  .selector-popup {
    margin: 33px 0px 0px 0px;
    background-color: #ffffff;
  }
  .selector-popup-item {
    padding: 0px 10px;
    font-size: 13px;
    line-height: 40px;
    display: flex;
    flex-flow: row nowrap;
  }
  .selector-popup-item-title {
    flex: auto;
  }
  .selector-popup-item-mark {
    flex: none;
    width: 15px;
  }
  .selector-popup-item-separator {
    height: 1px;
    background-color: rgb(221, 221, 221);
  }
  .selector-popup-item-selected {
    color: rgb(77, 190, 208);
  }
  .loading {
    flex: auto;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
  }
  .loading-body {
    flex: auto;
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-content: center;
    align-items: center;
  }
  .chart {
    background-color: rgb(255, 255, 255);
  }
  .chart-separator {
    height: 10px;
    background-color: rgb(245, 246, 245);
  }
  .chart-head {
    font-size: 14px;
    font-weight: bold;
    padding: 15px 0px 0px 0px;
  }
  .image-chart {
    background-color: #ffffff;
  }
  .image-chart-head {
    font-size: 18px;
    font-weight: bold;
    padding: 1px 0px 0px 5px;
  }
  .image-chart-body {
    padding: 0 0 0 15px;
  }
  .image-box {
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-content: center;
    align-items: center;
  }
  .image-box-item {
    flex: none;
    object-fit: cover;
    width: 94px;
    height: 94px;
    margin: 1px;
  }
  @media screen and (device-width: 320px) {
    .image-box-item {
      width: 94px;
      height: 94px;
      margin: 1px;
    }
  }
  @media screen and (device-width: 480px) and (orientation : landscape) {
    .image-box-item {
      width: 108px;
      height: 108px;
      margin: 2px;
    }
  }
  @media screen and (device-width: 568px) and (orientation : landscape) {
    .image-box-item {
      width: 103px;
      height: 103px;
      margin: 2px;
    }
  }
  @media screen and (device-width: 360px) {
    .image-box-item {
      width: 106px;
      height: 106px;
      margin: 2px;
    }
  }
  @media screen and (device-width: 640px) and (orientation : landscape) {
    .image-box-item {
      width: 116px;
      height: 116px;
      margin: 3px;
    }
  }
  @media screen and (device-width: 375px) {
    .image-box-item {
      width: 111px;
      height: 111px;
      margin: 2px;
    }
  }
  @media screen and (device-width: 667px) and (orientation : landscape) {
    .image-box-item {
      width: 121px;
      height: 121px;
      margin: 3px;
    }
  }
  @media screen and (device-width: 411px) {
    .image-box-item {
      width: 121px;
      height: 121px;
      margin: 3px;
    }
  }
  @media screen and (device-width: 731px) and (orientation : landscape) {
    .image-box-item {
      width: 132px;
      height: 132px;
      margin: 4px;
    }
  }
  @media screen and (device-width: 414px) {
    .image-box-item {
      width: 122px;
      height: 122px;
      margin: 3px;
    }
  }
  @media screen and (device-width: 736px) and (orientation : landscape) {
    .image-box-item {
      width: 133px;
      height: 133px;
      margin: 4px;
    }
  }
  @media screen and (device-width: 812px) and (orientation : landscape) {
    .image-box-item {
      width: 122px;
      height: 122px;
      margin: 4px;
    }
  }
  .image-box-item-title {
    position: relative;
    top: -26px;
    text-align: center;
    background: url(../../../assets/iot/device/timebg.png) repeat-x;
    color: white;
    height: 18px;
    line-height: 18px;
  }
  .image-box-item-content {
    display: inline-block;
    width: 100%;
    height: 100%;
  }
  .image-chart-empty {
    position: absolute;
    top: 0;
  }
</style>
