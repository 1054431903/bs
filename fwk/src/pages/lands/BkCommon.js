import mongo from '@vehiclecloud/mongo-rest-client'
import d2t from '@/components/Dirction2Text'
function pre (v) {
  return String(v).length === 1 ? '0' + v : v
}
function winDirection (direction) {
  let b = direction.replace('"', '').replace('"', '')
  switch (b) {
    case 'N':
      return 0
    case 'NE':
      return 45
    case 'E':
      return 90
    case 'SE':
      return 135
    case 'S':
      return 180
    case 'SW':
      return 225
    case 'W':
      return 270
    case 'NW':
      return 315
    default:
      break
  }
}
export default {
  props: {
    device: {
      type: Object,
      required: true
    },
    start: null,
    end: null
  },
  data () {
    return {
      st: '',
      et: ''
    }
  },
  created () {
    this.list(+this.start.$numberLong, +this.end.$numberLong)
  },
  methods: {
    fmtDate (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}/${pre(d.getMonth() + 1)}/${pre(d.getDate())}`
    },
    fmtDay (time) {
      let d = new Date(+time)
      return `${pre(d.getMonth() + 1)}/${pre(d.getDate())} ${pre(d.getHours())}:${pre(d.getMinutes())}`
    },
    fmtTime (time) {
      let d = new Date(+time)
      return `${pre(d.getHours())}:${pre(d.getMinutes())}`
    },
    list: function (start, end) {
      let _this = this
      _this.tableFlag = false
      let wdcol = ['时间', '温度(°C)']
      let sdcol = ['时间', '湿度(%)']
      let dlcol = ['时间', '电量(%)']
      let wscol = ['时间', '风速(m/s)']
      let presscol = ['时间', '大气压(hPa)']
      let rrcol = ['时间', '每小时降雨量(mm)']
      let uvcol = ['时间', '紫外线指数(%)']
      let wdicol = ['时间', '风向(°)']
      let wdrows = []
      let sdrows = []
      let dlrows = []
      let wsrows = []
      let pressrows = []
      let rrrows = []
      let uvrows = []
      let wdirows = []
      let sst = new Date()
      sst.setTime(start)
      sst.setHours(0, 0, 0, 0)
      this.st = sst.getTime()
      this.et = end
      let sortFlag = 1
      _this.table ? sortFlag = -1 : sortFlag = 1
      let ps = mongo.db('iot').collection('tenant.report.device_access_data_summary').find({filter: {device: {'$oid': this.device._id.$oid}, period: 6, time: { '$gte': this.st, '$lte': this.et }}, sort: {'time': sortFlag}, limit: 12000})
      ps.then(items => {
        console.log(items.items.length)
        if (_this.table) {
          _this.table.items = []
          items.items.forEach(item => {
            _this.table.items.push({
              datetime: item.time.$numberLong,
              date: _this.fmtDate(item.time.$numberLong),
              time: _this.fmtTime(item.time.$numberLong),
              temperature: item.data['0:61'].v,
              humidity: item.data['0:62'].v,
              atmospheric: item.data['0:67'].v,
              wspeed: item.data['0:63'].v,
              wdirection: item.data['0:63'].v === 0 || item.data['0:64'].v === 9999 ? '-' : d2t.direction2Text(item.data['0:64'].v),
              electric: item.data['0:66'] == null ? '-' : item.data['0:66'].v
            })
          })
          _this.tableFlag = true
        } else {
          _this.f1 = false
          _this.f2 = false
          _this.f3 = false
          _this.f4 = false
          _this.f5 = false
          _this.f6 = false
          _this.f7 = false
          _this.f8 = false
          _this.tf = false
          items.items.forEach(item => {
            wdrows.push({
              '时间': this.fmtDay(item.time.$numberLong),
              '温度(°C)': item.data['0:61'].v
            })
            sdrows.push({
              '时间': this.fmtDay(item.time.$numberLong),
              '湿度(%)': item.data['0:62'].v
            })
            wsrows.push({
              '时间': this.fmtDay(item.time.$numberLong),
              '风速(m/s)': item.data['0:63'].v === 9999 ? 0 : item.data['0:63'].v
            })
            pressrows.push({
              '时间': this.fmtDay(item.time.$numberLong),
              '大气压(hPa)': item.data['0:67'].v
            })
            wdirows.push({
              '时间': this.fmtDay(item.time.$numberLong),
              '风向(°)': item.data['0:63'].v === 0 || item.data['0:64'].v === 9999 ? '-' : winDirection(item.data['0:64'].v)
            })
            if (item.data['0:69'] != null) {
              rrrows.push({
                '时间': this.fmtDay(item.time.$numberLong),
                '每小时降雨量(mm)': item.data['0:69'].v === 9999 ? 0 : item.data['0:69'].v
              })
            }
            if (item.data['0:68'] != null) {
              uvrows.push({
                '时间': this.fmtDay(item.time.$numberLong),
                '紫外线指数(%)': item.data['0:68'].v === 9999 ? 0 : item.data['0:68'].v
              })
            }
            if (item.data['0:66'] != null) {
              dlrows.push({
                '时间': this.fmtDay(item.time.$numberLong),
                '电量(%)': item.data['0:66'].v
              })
            }
          })
          if (wdirows.length > 0) {
            if (this.grid3) this.grid3.left = 8
            _this.f7 = true
          } else {
            if (this.grid3) this.grid3.left = 30
            _this.f7 = false
          }
          if (pressrows.length > 0) {
            if (this.grid2) this.grid2.left = 14
            _this.f4 = true
          } else {
            if (this.grid2) this.grid2.left = 30
            _this.f4 = false
          }
          if (sdrows.length > 0) {
            if (this.grid5) this.grid5.left = 24
            _this.f1 = true
          } else {
            if (this.grid5) this.grid5.left = 30
            _this.f1 = false
          }
          wsrows.length > 0 ? _this.f2 = true : _this.f2 = false
          dlrows.length > 0 ? _this.f3 = true : _this.f3 = false
          rrrows.length > 0 ? _this.f5 = true : _this.f5 = false
          uvrows.length > 0 ? _this.f6 = true : _this.f6 = false
          wdrows.length > 0 ? _this.f8 = true : _this.f8 = false
          _this.tf = _this.f1 || _this.f2 || _this.f3 || _this.f4 || _this.f5 || _this.f6 || _this.f7 || _this.f8
          this.chartData = {columns: wdcol, rows: wdrows}
          this.chartData1 = {columns: sdcol, rows: sdrows}
          this.chartData2 = {columns: wscol, rows: wsrows}
          this.chartData3 = {columns: dlcol, rows: dlrows}
          this.chartData4 = {columns: presscol, rows: pressrows}
          this.chartData5 = {columns: rrcol, rows: rrrows}
          this.chartData6 = {columns: uvcol, rows: uvrows}
          this.chartData7 = {columns: wdicol, rows: wdirows}
        }
        _this.tableFlag = true
      })
    }
  },
  watch: {
    'start' (value) {
      // let dds = new Date()
      // dds.setTime(start)
      // console.log(dds.toDateString())
      // let dde = new Date()
      // dde.setTime(end)
      // console.log(dde.toDateString())
      // let stopDate = new Date()
      // stopDate.setDate(stopDate.getDate())
      // stopDate.setHours(0, 0, 0, 0)
      // let stopTime = stopDate.getTime()
      // let minDate = JsonschemaData.longToDateStr(stopTime - Period.day * 30)
      // let maxDate = JsonschemaData.longToDateStr(stopTime)
      if (this.end) {
        this.list(value && +value.$numberLong, this.end && +this.end.$numberLong + (24 * 60 * 60 - 1) * 1000)
      }
    },
    'end' (value) {
      if (this.start) {
        this.list(this.start && +this.start.$numberLong, value && +value.$numberLong + (24 * 60 * 60 - 1) * 1000)
      }
    },
    'device' (v) {
      this.devType = this.$store.state['base.device.type'].items.filter(value => value._id.$oid === v.type.$oid)
      let ss = this.devType[0].sensorTypes
      let sa = []
      if (ss && ss.length > 0) {
        ss.forEach(_s => {
          sa.push(this.$store.state['base.sensor.type'].items.filter(_vl => _s.$oid === _vl._id.$oid)[0])
        })
        if (sa && sa.length > 0) {
          this.sensorTypeArr = {}
          sa.forEach(_s => {
            _s.code === '61' ? this.sensorTypeArr['61'] = true : _s.code === '62' ? this.sensorTypeArr['62'] = true : _s.code === '63' ? this.sensorTypeArr['63'] = true : _s.code === '64' ? this.sensorTypeArr['64'] = true : _s.code === '65' ? this.sensorTypeArr['65'] = true : _s.code === '66' ? this.sensorTypeArr['66'] = true : _s.code === '67' ? this.sensorTypeArr['67'] = true : _s.code === '68' ? this.sensorTypeArr['68'] = true : _s.code === '69' ? this.sensorTypeArr['69'] = true : this.sensorTypeArr['70'] = true
          })
        }
      }
      this.list(this.st && +this.st, this.et && (+this.et + (24 * 60 * 60 - 1) * 1000))
    }
  }
}
