import Share from '@/share/index.js'
import Moment from 'moment'
import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  mixins: [Share],
  data () {
    return {
      orientation: undefined,
      rotateEvent: 'onorientationchange' in window ? 'orientationchange' : 'resize',
      rotateStylePortrait: null,
      rotateStyleLandscape: null,
      colors: [
        ['#06B6C0'],
        ['#1CB569'],
        ['#1A81D0'],
        ['#FF473D'],
        ['#FFD93E']
      ],
      charts: []
    }
  },
  created () {
    this.estimateRotate()
    this.estimateRotateStyle()
  },
  mounted () {
    this.rotateListenerEnable()
  },
  beforeDestroy () {
    this.rotateListenerDisable()
  },
  methods: {
    estimateRotate () {
      let orientation = window.orientation
      if (orientation !== undefined) {
        switch (orientation) {
          case 90:
          case -90:
            // 横屏
            this.orientation = 'landscape'
            break
          default:
            // 竖屏
            this.orientation = 'portrait'
            break
        }
      } else {
        let width = document.documentElement.clientWidth
        let height = document.documentElement.clientHeight
        console.debug(width, height)
        if (width < height) {
          this.orientation = 'portrait'
        } else {
          this.orientation = 'landscape'
        }
      }
    },
    estimateRotateStyle () {
      let width = document.documentElement.clientWidth
      let height = document.documentElement.clientHeight
      if (width < height) {
        [width, height] = [height, width]
      }
      this.rotateStylePortrait = {
        position: 'relative',
        width: width + 'px',
        height: height + 'px',
        top: 0,
        left: height + 'px',
        transform: 'rotate(90deg)',
        transformOrigin: 'left top 0'
      }
      this.rotateStyleLandscape = {
        position: 'relative',
        width: width + 'px',
        height: height + 'px',
        top: 0,
        left: 0,
        transform: 'none',
        transformOrigin: 'left top 0'
      }
    },
    rotateListener () {
      this.estimateRotate()
    },
    rotateListenerEnable () {
      window.addEventListener(this.rotateEvent, this.rotateListener, true)
    },
    rotateListenerDisable () {
      window.removeEventListener(this.rotateEvent, this.rotateListener, true)
    },
    async queryDevice (id) {
      let data = await mongo.db('iot').collection('tenant.device').aggregate([{
        $match: {
          _removed: {
            $exists: false
          },
          _id: {
            $oid: id
          }
        }
      }, {
        $lookup: {
          from: 'base.device.type',
          localField: 'type',
          foreignField: '_id',
          as: 'type'
        }
      }, {
        $unwind: {
          path: '$type',
          preserveNullAndEmptyArrays: true
        }
      }, {
        $lookup: {
          from: 'base.sensor.type',
          localField: 'type.sensorTypes',
          foreignField: '_id',
          as: 'sensorTypes'
        }
      }, {
        $lookup: {
          from: 'base.device.param_type',
          localField: 'type.paramTypes',
          foreignField: '_id',
          as: 'paramTypes'
        }
      }])
      return this.parseDevice(data[0])
    },
    async queryDeviceSensorData (id, beginTime, EndTime, period = 1) {
      let result = await mongo.db('iot').collection('tenant.report.device_access_data_summary').find({
        filter: {
          _removed: {
            $exists: false
          },
          device: {
            $oid: id
          },
          period: period,
          time: {
            $gte: beginTime.getTime(),
            $lt: EndTime.getTime()
          }
        },
        sort: {
          time: 1
        },
        limit: 10000
      })
      return this.parseDeviceSensorData(result)
    },
    parseDevice (data) {
      console.debug(data)
      if (!data) {
        return
      }
      let result = {}
      result.id = data._id.$oid
      result.name = data.name
      result.code = data.code
      result.typeCode = data.type.code
      result.isControllable = this.isControllable(result)
      result.params = (data.paramTypes || []).map(paramType => {
        let param = {}
        param.id = paramType._id.$oid
        param.code = paramType.code
        param.name = paramType.name
        param.type = paramType.type
        param.value = (data.params || {})[param.id]
        return param
      })
      result.sensors = (data.sensorTypes || []).map(sensorType => {
        let sensor = {}
        sensor.code = sensorType.code
        sensor.name = sensorType.name
        sensor.xName = sensor.name
        sensor.yName = sensor.name
        sensor.unit = sensorType.data.unit || ''
        sensor.isIndicator = this.isIndicator(sensor)
        sensor.isOrdinary = true
        return sensor
      }).filter(sensor => {
        return sensor.isIndicator
      }).map(sensor => {
        sensor.chart = this.choiceChart(sensor)
        return sensor
      }).filter(sensor => {
        return sensor.chart
      }).sort((sensor1, sensor2) => {
        if (sensor1.chart.type !== sensor2.chart.type) {
          return sensor1.chart.type - sensor2.chart.type
        } else {
          return sensor1.code > sensor2.code ? 1 : 0
        }
      })
      // 合成指标
      let sensor63 = result.sensors.find(sensor => sensor.code === '63')
      let sensor64 = result.sensors.find(sensor => sensor.code === '64')
      if (sensor63 && sensor64) {
        sensor63.isCombineItem = true
        sensor64.isCombineItem = true
        let sensor = {}
        sensor.code = 'M63-64'
        sensor.name = '风速风向'
        sensor.xName = sensor.name
        sensor.yName = sensor.name
        sensor.unit = sensor63.unit
        sensor.isIndicator = true
        sensor.isCombine = true
        sensor.subSensors = [sensor63, sensor64]
        sensor.chart = this.choiceChart(sensor)
        result.sensors.push(sensor)
        result.sensors = result.sensors.sort((sensor1, sensor2) => {
          if (sensor1.chart.type !== sensor2.chart.type) {
            return sensor1.chart.type - sensor2.chart.type
          } else {
            return sensor1.code > sensor2.code ? 1 : 0
          }
        })
      }
      // 指标替换
      let sensor65 = result.sensors.find(sensor => sensor.code === '65')
      if (sensor65) {
        sensor65.isReplaceItem = true
        let sensor = {}
        sensor.code = 'R65'
        sensor.name = '天空视频'
        sensor.xName = sensor.name
        sensor.yName = sensor.name
        sensor.unit = undefined
        sensor.isIndicator = true
        sensor.isReplace = true
        sensor.chart = this.choiceChart(sensor)
        result.sensors.push(sensor)
        result.sensors = result.sensors.sort((sensor1, sensor2) => {
          if (sensor1.chart.type !== sensor2.chart.type) {
            return sensor1.chart.type - sensor2.chart.type
          } else {
            return sensor1.code > sensor2.code ? 1 : 0
          }
        })
      }
      console.debug(result)
      return result
    },
    parseDeviceSensorData (data) {
      console.debug(data)
      let result = data.items.filter(item => {
        return item.time && item.data
      }).map(item => {
        let signal = {}
        signal.time = new Date(Number(item.time.$numberLong))
        signal.data = {}
        for (let key in item.data) {
          let target = item.data[key]
          if (target.times && target.v) {
            let signalData = {}
            signalData.times = target.times > 1 ? Number(target.times.toFixed(0)) : 1
            if (typeof target.v === 'number') {
              signalData.o = target.v
              signalData.v = Number((target.v / signalData.times).toFixed(2))
            } else {
              signalData.o = target.v
              signalData.v = target.v
            }
            let keys = key.split(':')
            switch (keys.length) {
              case 1: {
                signal.data[keys[0]] = signalData
                break
              }
              case 2: {
                signal.data[keys[1]] = signalData
                break
              }
              case 3: {
                signal.data[keys[1]] = signalData
                break
              }
              default: {
                signal.data[key] = signalData
              }
            }
          }
        }
        return signal
      })
      console.debug(result)
      return result
    },
    isIndicator (sensor) {
      switch (sensor.code) {
        default: {
          return true
        }
      }
    },
    defineValue (sensor) {
      let define = {
        visible: true,
        title: sensor.name,
        type: 0,
        xLabel: 'time',
        xName: sensor.xName,
        yLabel: 'v',
        yName: sensor.yName,
        yAxisName: null,
        yUnit: sensor.unit.trim(),
        fullScreen: sensor.fullScreen,
        formatter: items => {
          let name = items[0].name
          let value = items[0].value
          if (typeof value !== 'number') {
            value = '--'
          }
          return `${name}<br/>${define.yName}：${value}${define.yUnit}`
        }
      }
      if (define.title && define.yUnit && define.yUnit.length > 0) {
        define.title += `[${define.yUnit}]`
      }
      return define
    },
    defineCombine (sensor) {
      let define = {
        visible: true,
        title: sensor.name,
        type: 0,
        xLabel: 'time',
        xName: sensor.xName,
        yLabel: 'v',
        yName: sensor.yName,
        yAxisName: null,
        yUnit: sensor.unit.trim(),
        fullScreen: sensor.fullScreen
      }
      if (define.title && define.yUnit && define.yUnit.length > 0) {
        define.title += `[${define.yUnit}]`
      }
      define.formatter = (points) => {
        let point = points[0]
        let time = point.time
        if (point.data) {
          let speed = point.data.v
          let direction = (point.data.values[1] || '').toString().trim()
          // E/S/W/N/NE/NW/SW/SE
          switch (direction) {
            case 'E':
              direction = '东风'
              break
            case 'S':
              direction = '南风'
              break
            case 'W':
              direction = '西风'
              break
            case 'N':
              direction = '北风'
              break
            case 'NE':
              direction = '东北风'
              break
            case 'NW':
              direction = '西北风'
              break
            case 'SE':
              direction = '东南风'
              break
            case 'SW':
              direction = '西南风'
              break
            case 'STATIC':
              speed = 0
              direction = '无风'
              break
            default:
              direction = '未知'
          }
          return `${Moment(time).format('MM/DD HH:mm')}<br/>${direction} ${speed}${define.yUnit}`
        } else {
          return `${Moment(time).format('MM/DD HH:mm')}<br/>-- --${define.yUnit}`
        }
      }
      return define
    },
    defineImage (sensor) {
      let define = {
        visible: true,
        title: sensor.name,
        type: 1
      }
      return define
    },
    defineVideo (sensor) {
      let define = {
        visible: true,
        title: sensor.name,
        type: 2
      }
      return define
    },
    choiceChart (sensor) {
      if (!sensor) {
        return
      }
      if (!sensor.isIndicator) {
        return
      }
      switch (sensor.code) {
        case '1': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '10': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '11': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '12': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '48': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '49': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '60': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '61': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '62': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '63': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '64': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '66': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '67': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '68': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case '69': {
          let define = this.defineValue(sensor)
          return this.lineChartDefine(define)
        }
        case 'M63-64': {
          let define = this.defineCombine(sensor)
          return this.lineChartDefine(define)
        }
        case '65': {
          let define = this.defineImage(sensor)
          return this.imageChartDefine(define)
        }
        case 'R65': {
          let define = this.defineVideo(sensor)
          return this.videoChartDefine(define)
        }
      }
    },
    lineChartDefine ({visible, title, type, xLabel, xName, yLabel, yName, yAxisName, yUnit, formatter, fullScreen}) {
      let chart = {
        visible: visible,
        title: {
          text: title
        },
        type: type,
        isEmpty: true,
        data: {
          columns: [xLabel, yLabel],
          rows: []
        },
        judgeWidth: true,
        dataZoom: {
          type: 'slider',
          start: 0,
          end: 100
        },
        settings: {
          metrics: [yLabel],
          dimension: [xLabel],
          labelMap: {
            [yLabel]: yName,
            [xLabel]: xName
          },
          area: true,
          yAxisName: [yAxisName]
        },
        grid: {
          show: true
        },
        toolbox: {
          showTitle: false,
          itemSize: 16,
          feature: {
            myFullScreen: {
              icon: 'image://iot/device/flip.png',
              onclick: fullScreen || function () {
              }
            }
          }
        },
        extend: {
          series: {
            connectNulls: false,
            showSymbol: true,
            symbol: 'emptyCircle',
            showAllSymbol: 'auto'
          },
          tooltip: {
            formatter: formatter || '{c}' + yUnit + '<br/>{b}'
          },
          xAxis: {
            splitLine: {
              show: true
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              show: false
            }
          },
          yAxis: {
            splitLine: {
              show: true
            }
          }
        }
      }
      return chart
    },
    imageChartDefine ({visible, title, type}) {
      let chart = {
        visible: visible,
        title: {
          text: title
        },
        type: type,
        isEmpty: true,
        data: {
          rows: []
        }
      }
      return chart
    },
    videoChartDefine ({visible, title, type}) {
      let chart = {
        visible: visible,
        title: {
          text: title
        },
        type: type,
        isEmpty: true,
        data: {
          rows: []
        }
      }
      return chart
    }
  }
}
