<template>
  <div class="page">
    <div class="page-body">
      <v-menu ref="selector" class="selector" content-class="selector" full-width flat transition="false">
        <div class="selector-head" slot="activator">
          <div class="selector-head-item"
               :class="{'bold': selector === indicatorSelector}"
               @click="switchSelector(indicatorSelector)">
            <span>{{indicatorSelector.current().name}}&nbsp;</span>
            <img v-show="selector !== indicatorSelector" style="width: 7px;height: 4px;" src="../../../assets/iot/device/drop-down.png"/>
            <img v-show="selector === indicatorSelector" style="width: 7px;height: 4px;" src="../../../assets/iot/device/drop-down01.png"/>
          </div>
          <div class="selector-head-item"
               :class="{'bold': selector === timeRangeSelector}"
               @click="switchSelector(timeRangeSelector)">
            <span>{{timeRangeSelector.current().name}}&nbsp;</span>
            <img v-show="selector !== indicatorSelector" style="width: 7px;height: 4px;" src="../../../assets/iot/device/drop-down.png"/>
            <img v-show="selector === indicatorSelector" style="width: 7px;height: 4px;" src="../../../assets/iot/device/drop-down01.png"/>
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
          <div class="chart-body" style="padding: 0 5px;">
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
        <div class="video-chart" v-for="(chart,index) of charts" :key="index" v-if="chart.type === 2">
          <div class="chart-separator">&nbsp;</div>
          <div style="position: relative;">
            <div class="video-chart-head">天空视频</div>
            <div class="video-chart-body" v-show="!chart.isEmpty">
              <div class="video-box">
                <div class="video-box-item" v-for="(video, index) in chart.data.rows" :key="index" v-if="video && video.v">
                  <div class="video-box-item-container" @click="playVideo(video)" :style="{'background-image': 'url(iot/list/video_cover.png)'}">
                    <div class="video-box-item-container-top">&nbsp;</div>
                    <div class="video-box-item-container-center">
                      <img class="video-box-item-container-center-icon" src="iot/list/play.png">
                    </div>
                    <div class="video-box-item-container-bottom">{{video.timeString}}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="v-charts-data-empty" style="height: 265px" v-show="chart.isEmpty">暂无数据</div>
          </div>
        </div>
      </div>
    </div>
    <v-dialog v-if="dialog.show" v-model="dialog.show" fullscreen class="mx-0 my-0 px-0 py-0">
      <div style="background-color: white">
        <div :style="dialog.chart.style">
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
      </div>
    </v-dialog>
  </div>
</template>

<script>
import Share from './share'
import Moment from 'moment'
import mongo from '@vehiclecloud/mongo-rest-client'
import 'echarts/lib/component/title'
import 'echarts/lib/component/dataZoom'
import 'v-charts/lib/style.css'
const emptyPoint = {}
export default {
  name: 'iot-device-weather',
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
            if (!(sensor.isCombineItem || sensor.isReplaceItem)) {
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
    playVideo (video) {
      try {
        if (window.native && window.native.player) {
          window.native.player(JSON.stringify({
            url: video.v
          }))
          return
        } else {
          this.showMessageInfo('HTML播放未实现')
        }
      } catch (e) {
        console.debug(e)
        this.showMessageError(e.message)
      }
    },
    async load () {
      try {
        this.loading = true
        if (!(this.indicatorSelector.current().code === 'R65')) {
          await this.loadIndicator()
        }
        if (!this.indicatorSelector.current().code || this.indicatorSelector.current().code === 'R65') {
          await this.loadVideo()
        }
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
        this.charts.filter(chart => chart.type === 0).forEach(chart => {
          chart.isEmpty = !chart.data.rows.find(point => point !== emptyPoint)
          chart.data.rows[0] = {time: 0, v: 0}
        })
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    queryParam () {
      let id = this.device.id
      let days = this.timeRangeSelector.current().days
      let now = new Date()
      let beginTime = new Date(now.getFullYear(), now.getMonth(), now.getDate() - days + 1)
      let endTime = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1)
      return {id, days, now, beginTime, endTime}
    },
    async loadIndicator () {
      if (!this.device) {
        return
      }
      let {id, days, beginTime, endTime} = this.queryParam()
      let dateFormat = days > 1 ? 'MM-DD HH:mm' : 'HH:mm'
      let result = await this.queryDeviceSensorData(id, beginTime, endTime, 6)
      let chartData = new Map()
      for (let cursorTime = beginTime.getTime(); cursorTime < endTime.getTime(); cursorTime += 5 * 60 * 1000) {
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
                if (sensor.isOrdinary) {
                  let data = item.data[sensor.code]
                  if (data && !(typeof data.v === 'undefined')) {
                    let point = {}
                    point.v = data.v
                    point.timestamp = item.time
                    for (let i = 0; i < 5; i++) {
                      point.time = Moment(point.timestamp.getTime() + 1000 * 60 * i).format(dateFormat)
                      if (chartData.has(point.time)) {
                        chartData.set(point.time, point)
                        break
                      }
                    }
                  }
                }
                if (sensor.isCombine) {
                  let dataArray = sensor.subSensors.map(sensor => item.data[sensor.code])
                  let data = dataArray[0]
                  if (data && !(typeof data.v === 'undefined')) {
                    let point = {}
                    // point.v =  data.v
                    point.v = {
                      v: data.v,
                      values: dataArray.map(data => data.v),
                      toString () {
                        return this.v
                      }
                    }
                    point.timestamp = item.time
                    for (let i = 0; i < 5; i++) {
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
          if (sensor.chart.type === 0) {
            sensor.chart.data.rows = [...chartData.values()]
            sensor.chart.toolbox.feature.myFullScreen.onclick = () => {
              if (this.dialog.show) {
                this.setPortrait()
                setTimeout(() => {
                  this.dialog.show = false
                  this.chart = undefined
                }, 200)
              } else {
                this.setLandScape()
                setTimeout(() => {
                  this.dialog.show = true
                  this.chart = sensor.chart
                }, 200)
              }
            }
          }
        }
      })
    },
    setLandScape () {
      if (window.native && window.native.setLandScape) {
        try {
          window.native.setLandScape()
        } catch (e) {}
      }
    },
    setPortrait () {
      if (window.native && window.native.setPortrait) {
        try {
          window.native.setPortrait()
        } catch (e) {}
      }
    },
    async loadVideo () {
      if (!this.device) {
        return
      }
      let {id, beginTime, endTime} = this.queryParam()
      let dateFormat = 'YYYY-MM-DD'
      let result = await mongo.db('iot').collection('tenant.device_bloomsky_video_data').find({filter: {'device': {'$oid': id}, time: {'$gt': beginTime.getTime(), '$lt': endTime.getTime()}}, sort: {time: -1}})
      console.debug(result)
      if (!result) return
      let sensor = this.device.sensors.find(item => item.code === 'R65')
      if (!sensor) return
      result.items.forEach(item => {
        let video = {}
        video.id = item._id
        video.deviceId = item.device.$oid
        video.time = new Date(Number(item.time.$numberLong))
        if (item.data.v.match(/^http(s)?:\/\/.*/i)) {
          video.v = item.data.v
        } else {
          video.v = `http://${item.data.v}`
        }
        video.timeString = Moment(video.time).format(dateFormat)
        sensor.chart.data.rows.push(video)
      })
      sensor.chart.isEmpty = sensor.chart.data.rows.length <= 0
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
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-content: center;
    align-items: center;
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

  .chart-body {
    padding: 0;
    margin: 0;
  }
  .video-chart {
    background-color: #ffffff;
  }
  .video-chart-head {
    font-size: 18px;
    font-weight: bold;
    padding: 1px 0px 0px 10px;
  }
  .video-chart-body {
    margin: 0 10px;
  }
  .video-box {
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-content: center;
    align-items: center;
  }
  .video-box-item {
    flex: 0 0 31.3%;
    background-color: #02ad58;
    padding-bottom: 31.3%;
    margin: 1%;
    position: relative;
  }
  .video-box-item-container {
    width: 100%;
    height: 100%;
    position: absolute;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    word-wrap: break-word;
    white-space: nowrap;
    background-size: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
  }
  .video-box-item-container-top {
    flex: none;
    height: 20px;
    line-height: 20px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    word-wrap: break-word;
    white-space: nowrap;
  }
  .video-box-item-container-center {
    flex: auto;
    display: flex;
    flex-flow: column nowrap;
    justify-content: center;
    align-items: center;
  }
  .video-box-item-container-center-icon {
    width: 40px;
    height: 40px;
  }
  .video-box-item-container-bottom {
    flex: none;
    height: 20px;
    line-height: 20px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    word-wrap: break-word;
    white-space: nowrap;
    font-size: 13px;
    color: #ffffff;
    background-color: rgba(0, 0, 0, .25);
  }
</style>
