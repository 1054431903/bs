<template>
  <div class="_page">
    <div class="_panel"
         v-for="item in items"
         :key="item.id"
         v-if="item.display">
      <div class="_banner">&nbsp;</div>
      <div class="_head"
           @click="item.collapsed = !item.collapsed">
        <div class="_icon"
             :class="item.iconClass">&nbsp;</div>
        <div class="_title">{{item.name}}({{item.devices.length || 0}})</div>
        <div class="_arrow"
             :class="{'_collapsed': item.collapsed}">&nbsp;</div>
      </div>
      <div class="_separator">&nbsp;</div>
      <div class="_body"
           :class="{'hidden': item.collapsed}">
        <div class="_device"
             v-if="item.displayMode === 1"
             @click="showDetail(item, device)"
             v-for="(device, index) in item.devices"
             :key="device.id">
          <div class="_separator"
               v-if="index != 0">&nbsp;</div>
          <div class="_box">
            <div class="_detail">
              <div class="_title">
                <div class="_name">{{device.name}}({{device.code || '--'}})</div>
                <div class="_power"
                     v-if="device.powerSensor"
                     :class="device.powerSensor.className" style="visibility: hidden">
                  <div class="_content">
                    <span class="_value">
                      {{typeof device.powerSensor.value === 'number' ? device.powerSensor.value.toFixed(0) :(typeof device.powerSensor.value === 'string' ? device.powerSensor.value : '--')}}{{device.powerSensor.unit}}
                    </span>
                    <img class="_icon"
                         :src="device.powerSensor.iconUrl" />
                  </div>
                </div>
                <div class="_power"
                     v-else>&nbsp;</div>
              </div>
              <div class="_sensors"
                   v-if="Array.isArray(device.sensors) && device.sensors.length > 0">
                <div class="_sensor"
                     v-for="(sensor, index) in device.sensors"
                     :key="index"
                     v-if="!sensor.hidden"
                     :class="sensor.className"
                     :style="{
                        'padding': sensor.extral ? '0' : '',
                        'margin-right': sensor.extral ? '0px' :(sensor.x === 2 ? '0px' : '10px'),
                        'width': sensor.extral ? 'auto' : '31%',
                        'flex': sensor.extral ? 'auto' : 'none',
                        }">
                  <div class="_standard"
                       v-if="sensor.standard">
                    <div class="_content">
                      <div class="_value">
                        {{typeof sensor.value === 'number' ? sensor.value.toFixed(2) :(typeof sensor.value === 'string' ? sensor.value : '--')}}
                        <span class="_unit">{{sensor.unit}}</span>
                      </div>
                      <div class="_name">{{sensor.name ? sensor.name : '--'}}</div>
                    </div>
                    <div class="_icon">
                      <img v-if="sensor.iconUrl"
                           :src="sensor.iconUrl" />
                    </div>
                  </div>
                  <div class="_extral"
                       v-if="sensor.extral">
                    <div class="_content"
                         v-for="(child, index) in sensor.children"
                         :key="index"
                         v-if="index < 3">
                      <img class="_icon"
                           :src="child.iconUrl" />
                      <span class="_name">{{child.name}}：</span>
                      <span class="_value">
                        {{typeof child.value === 'number' ? child.value.toFixed(2) :(typeof child.value === 'string' ? child.value : '--')}}
                      </span>
                      <span class="_unit">{{child.unit}}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="_bottom">
                <span class="_location">{{device.location ? device.location : '--'}}</span>
                <span class="_holder">&nbsp;</span>
                <span class="_datetime">{{device.lastSignalTimeString ? device.lastSignalTimeString : '--'}}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="_panel4x4"
             v-if="item.displayMode === 2">
          <div class="_holder"
               v-for="(device) in item.devices"
               :key="device.id">
            <div class="_device"
                 @click="showDetail(item, device)"
                 :style="{'background-image': 'url(iot/list/video_cover.png)'}">
              <div class="_top">&nbsp;</div>
              <div class="_center">
                <img src="iot/list/play.png">
              </div>
              <div class="_bottom">
                <div class="_name">
                  {{device.name}}
                </div>
                <div v-if="device.pictureCount" class="_img">
                  <img src="iot/list/img_icon.png" width="24" height="21"/>
                </div>
                <div class="_icon">
                  <div>{{device.label}}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from '@/share/index.js'
import mongo from '@vehiclecloud/mongo-rest-client'
import Moment from 'moment'

export default {
  name: 'iot-list_view',
  mixins: [Share],
  data () {
    return {
      categoryId: undefined,
      items: [],
      weather: {
        id: 1,
        name: '微气象站',
        iconClass: '_weather',
        router: 'iot-device-weather',
        display: true,
        displayMode: 1,
        collapsed: false,
        devices: []
      },
      soil: {
        id: 2,
        name: '土壤检测',
        iconClass: '_soil',
        router: 'iot-device-soil',
        display: true,
        displayMode: 1,
        collapsed: false,
        devices: []
      },
      water: {
        id: 3,
        name: '水体检测',
        iconClass: '_water',
        router: 'iot-device-water',
        display: false,
        displayMode: 1,
        collapsed: false,
        devices: []
      },
      graphic: {
        id: 4,
        name: '视频图像',
        iconClass: '_graphic',
        router: 'iot-device-graphic',
        display: true,
        displayMode: 2,
        collapsed: false,
        devices: []
      },
      controller: {
        id: 5,
        name: '控制设备',
        iconClass: '_controller',
        router: 'iot-device-controller',
        display: false,
        displayMode: 1,
        collapsed: false,
        devices: []
      }
    }
  },
  mounted () {
    this.load()
  },
  methods: {
    async showDetail (item, device) {
      if (item.id === 4) {
        try {
          let code = Number(device.typeCode)
          switch (code) {
            case 401: {
              if (window.native.playerPowerView) {
                window.native.playerPowerView(JSON.stringify({
                  deviceId: device.id,
                  deviceSerial: device.code,
                  deviceName: device.name,
                  cameraNo: 1,
                  isBall: this.isControllable(device)
                }))
              }
              break
            }
            case 402:
            case 405: {
              if (window.native && window.native.player) {
                window.native.player(JSON.stringify({
                  deviceId: device.id,
                  deviceSerial: device.code,
                  deviceName: device.name,
                  cameraNo: 1,
                  isBall: this.isControllable(device)
                }))
              }
              break
            }
            default: {
              this.showMessageInfo('暂时不支持该类型设备的查看')
              break
            }
          }
        } catch (e) {
          this.showMessageInfo(e.message)
        }
      }
      // this.$router.push({ name: item.router, params: { _id: device.id } })
    },
    async load () {
      let farmland = await this.queryFarmland()
      if (farmland) {
        this.$store.commit('set', { name: 'app', k: 'title', v: farmland.name })
      }
      this.categoryId = this.$route.params.categoryId
      switch (this.categoryId) {
        case 1: {
          this.items = [this.weather]
          let weathers = await this.queryWeather(farmland._id.$oid)
          this.weather.devices = weathers
          break
        }
        case 2: {
          this.items = [this.soil]
          let soils = await this.querySoil(farmland._id.$oid)
          this.soil.devices = soils
          break
        }
        case 3: {
          this.items = [this.water]
          break
        }
        case 4: {
          this.items = [this.graphic]
          let graphics = await this.queryGraphic(farmland._id.$oid)
          this.graphic.devices = graphics
          break
        }
        case 5: {
          this.items = [this.controller]
          break
        }

        default: {
          this.items = [this.weather, this.soil, this.water, this.graphic, this.controller]
          let weathers = await this.queryWeather(farmland._id.$oid)
          this.weather.devices = weathers
          let soils = await this.querySoil(farmland._id.$oid)
          this.soil.devices = soils
          let graphics = await this.queryGraphic(farmland._id.$oid)
          this.graphic.devices = graphics
        }
      }
    },
    // 气象站类
    async queryWeather (farmlandId) {
      let query = this.buildQuery({
        farmlandId: farmlandId,
        tags: [1],
        dataLoaders: []
      })
      let data = await mongo.db('iot').collection('tenant.device').aggregate(query)
      return this.parseWeather(data)
    },
    // 土壤检测类
    async querySoil (farmlandId) {
      let query = this.buildQuery({
        farmlandId: farmlandId,
        tags: [2],
        dataLoaders: []
      })
      let data = await mongo.db('iot').collection('tenant.device').aggregate(query)
      return this.parseSoil(data)
    },
    // 图像视频类
    async queryGraphic (farmlandId) {
      let query = this.buildQuery({
        farmlandId: farmlandId,
        tags: [4],
        dataLoaders: []
      })
      let data = await mongo.db('iot').collection('tenant.device').aggregate(query)
      return this.parseGraphic(data)
    },
    async queryDevicePictureCount (device) {
      try {
        let result = await mongo.db('iot').collection('tenant.device_powerview_data').aggregate([{
          $match: {
            _removed: {
              $exists: false
            },
            device: {
              $oid: device.id
            }
          }
        }, {
          $count: 'count'
        }])
        console.log(result)
        return result[0].count
      } catch (e) {
        return 0
      }
    },
    buildQuery ({ farmlandId, typeIds, tags, dataLoaders }) {
      let queryBuilder = []
      queryBuilder.push({
        $match: {
          _removed: {
            $exists: false
          },
          status: 1
        }
      })
      queryBuilder.push({
        $match: {
          farmland: {
            $oid: farmlandId
          }
        }
      })
      if (Array.isArray(typeIds)) {
        queryBuilder.push({
          $match: {
            type: {
              $in: typeIds.map(typeId => ({ $oid: typeId }))
            }
          }
        })
      }
      queryBuilder.push({
        $lookup: {
          from: 'base.device.type',
          localField: 'type',
          foreignField: '_id',
          as: 'type'
        }
      })
      queryBuilder.push({
        $unwind: {
          path: '$type',
          preserveNullAndEmptyArrays: true
        }
      })
      if (Array.isArray(tags)) {
        queryBuilder.push({
          $match: {
            'type.tag': {
              $in: tags
            }
          }
        })
      }
      queryBuilder.push({
        $lookup: {
          from: 'base.sensor.type',
          localField: 'type.sensorTypes',
          foreignField: '_id',
          as: 'sensorTypes'
        }
      })
      if (Array.isArray(dataLoaders)) {
        queryBuilder.concat(dataLoaders)
      }
      return queryBuilder
    },
    async parseWeather (data) {
      return this.parse(data)
    },
    async parseSoil (data) {
      return this.parse(data)
    },
    async parseGraphic (data) {
      let result = await this.parse(data)
      // 不视频图像类设备的传感器
      result.forEach(item => {
        item.sensors = []
      })
      return result
    },
    async parse (data) {
      console.debug(data)
      if (!Array.isArray(data)) {
        return []
      }
      let result = []
      for (let item of data) {
        let device = {}
        device.id = item._id.$oid
        device.name = (item.name || '--')
        device.code = (item.code || '--')
        device.typeCode = item.type.code
        device.typeTag = item.type.tag
        device.typeName = item.type.name
        device.location = item.location
        device.avatarUrl = this.chooseDeviceAvatarUrl(device)
        device.label = this.chooseDeviceLabel(device)
        if (Number(device.typeTag) === 4) {
          device.pictureCount = await this.queryDevicePictureCount(device)
        } else {
          device.pictureCount = 0
        }
        device.signal = item.accessState
        if (device.signal) {
          device.active = true
          device.lastSignalTime = Number((device.signal.time || {}).$numberLong)
          if (device.lastSignalTime) {
            device.lastSignalTimeString = Moment(device.lastSignalTime).format('YYYY-MM-DD HH:mm')
          }
          device.status = device.signal.status
          device.statusCode = device.signal.code
          if ((device.statusCode || []).find(item => item === 2003)) {
            device.offline = true
          } else {
            device.offline = false
          }
        } else {
          device.active = false
        }
        device.sensors = item.sensorTypes.map(sensorType => {
          let sensor = {}
          sensor.code = sensorType.code
          sensor.name = sensorType.name
          sensor.unit = sensorType.data.unit
          sensor.standard = true
          sensor.hidden = false
          sensor.valueType = this.chooseSensorValueType(sensor)
          if (device.signal && device.signal.data) {
            let sensorData
            if (!sensorData) {
              sensorData = device.signal.data[sensor.code]
            }
            if (!sensorData) {
              sensorData = device.signal.data['0:' + sensor.code]
            }
            if (!sensorData) {
              sensorData = device.signal.data['1:' + sensor.code]
            }
            if (sensorData) {
              sensor.data = sensorData
              sensor.status = sensorData.status
              sensor.as = sensorData.as
              sensor.ac = sensorData.ac
            }
          }
          if (device.offline) {
            sensor.data = {} // 离线以后不显示数据
            sensor.stateSuffix = 'offline'
          } else {
            switch (sensor.as) {
              case 2: {
                sensor.stateSuffix = 'warn'
                break
              }
              case 3: {
                sensor.stateSuffix = 'fault'
                break
              }
              default: {
                sensor.stateSuffix = 'normal'
                break
              }
            }
          }
          // sensor.stateSuffix = 'offline'
          sensor.className = `_state-${sensor.stateSuffix}`
          return sensor
        }).map(sensor => {
          sensor.value = this.convertSensorValue(sensor)
          sensor.iconUrl = this.chooseSensorIconUrl(sensor)
          return sensor
        }).filter(sensor => {
          return sensor.valueType === 0
        })
        // 提取电量传感器
        device.powerSensor = device.sensors.find(sensor => (sensor.code === '1' || sensor.code === '66'))
        device.sensors = device.sensors.filter(sensor => !(sensor.code === '1' || sensor.code === '66'))
        // 二次排序
        let fixedSensors = []// 空气温度（61）、空气湿度（62）、土壤温度（48）、土壤湿度（49）、土壤温度（12）、土壤湿度（10）、土壤EC（11）
        device.sensors = device.sensors.filter(sensor => {
          switch (sensor.code) {
            case '61': {
              fixedSensors[0] = sensor
              return false
            }
            case '62': {
              fixedSensors[1] = sensor
              return false
            }
            case '48': {
              fixedSensors[2] = sensor
              return false
            }
            case '49': {
              fixedSensors[3] = sensor
              return false
            }
            case '12': {
              fixedSensors[4] = sensor
              return false
            }
            case '10': {
              fixedSensors[5] = sensor
              return false
            }
            case '11': {
              fixedSensors[6] = sensor
              return false
            }
            default: {
              return true
            }
          }
        })
        device.sensors = [...fixedSensors.filter(item => item), ...device.sensors.sort((s1, s2) => {
          return Number(s2) - Number(s1)
        })]
        if (device.typeCode === 105) {
          console.debug(device)
          let sensor = device.sensors.find(sensor => sensor.code === '60')
          console.debug(sensor)
          let c = []
          if (sensor.data && sensor.data.c) {
            c = sensor.data.c
          }
          device.sensors.push({
            extral: true,
            children: [{
              name: '昨日累计降雨量',
              iconUrl: 'iot/list/rain.png',
              value: c['2'],
              unit: 'mm'
            }, {
              name: '最近一小时降雨量',
              iconUrl: 'iot/list/rain.png',
              value: c['1'],
              unit: 'mm'
            }, {
              name: '最近一小时降雨强度',
              iconUrl: 'iot/list/rain.png',
              value: c['3']
            }]
          })
        }
        if (device.sensors && device.sensors.length > 0) {
          if (device.sensors.length % 3 === 0) {
            if (device.sensors[device.sensors.length - 1].extral) {
              let extral = device.sensors[device.sensors.length - 1]
              device.sensors[device.sensors.length - 1] = {
                className: '_state-hidden'
              }
              device.sensors.push(extral)
            }
          } else {
            if (!device.sensors[device.sensors.length - 1].extral) {
              for (let rem = device.sensors.length % 3; rem < 3; rem++) {
                device.sensors.push({
                  className: '_state-hidden'
                })
              }
            }
          }
        }
        device.sensors.forEach((item, index) => {
          item.x = index % 3
          item.y = Math.floor(index / 3)
        })
        result.push(device)
      }
      return result
    },
    convertSensorValue (sensor) {
      if (sensor.data) {
        switch (sensor.code) {
          case '64': {
            switch (sensor.data.v) {
              case 'E':
                return '东风'
              case 'S':
                return '南风'
              case 'W':
                return '西风'
              case 'N':
                return '北风'
              case 'NE':
                return '东北风'
              case 'NW':
                return '西北风'
              case 'SE':
                return '东南风'
              case 'SW':
                return '西南风'
              case 'STATIC':
                return '无风'
              default:
                return '--'
            }
          }
          default: {
            return sensor.data.v
          }
        }
      }
    },
    chooseSensorValueType (sensor) {
      switch (sensor.code) {
        case '1':
        case '10':
        case '11':
        case '12':
        case '48':
        case '49':
        case '60':
        case '61':
        case '62':
        case '63':
        case '64':
        case '66':
        case '67':
        case '68':
        case '69': {
          return 0
        }
        case '65': {
          return 1
        }
      }
    },
    chooseSensorIconUrl (sensor) {
      let stateSuffix = sensor.stateSuffix
      switch (stateSuffix) {
        case 'warn': {
          stateSuffix = 'fault'
          break
        }
        case 'fault': {
          stateSuffix = 'warn'
          break
        }
      }
      switch (sensor.code) {
        case '1': { // 电量
          return `iot/list/electric_${stateSuffix}.png`
        }
        case '10': { // 土壤湿度
          return `iot/list/water_content_${stateSuffix}.png`
        }
        case '11': { // 土壤EC
          return `iot/list/ec_${stateSuffix}.png`
        }
        case '12': { // 土壤温度
          return `iot/list/soil_temperature_${stateSuffix}.png`
        }
        case '48': { // 土壤温度
          return `iot/list/soil_temperature_${stateSuffix}.png`
        }
        case '49': { // 土壤湿度
          return `iot/list/water_content_${stateSuffix}.png`
        }
        case '60': { // SPOT雨量计
          return `iot/list/rainfall_${stateSuffix}.png`
        }
        case '61': { // 空气温度
          return `iot/list/temperature_${stateSuffix}.png`
        }
        case '62': { // 空气湿度
          return `iot/list/wet_${stateSuffix}.png`
        }
        case '63': { // 风速
          return `iot/list/wind_speed_${stateSuffix}.png`
        }
        case '64': { // 风向
          return `iot/list/wind_direction_${stateSuffix}.png`
        }
        case '65': { // 天空图片
          return undefined
        }
        case '66': { // 电量bloomsky
          return `iot/list/electric_${stateSuffix}.png`
        }
        case '67': { // 大气压
          return `iot/list/pressure_${stateSuffix}.png`
        }
        case '68': { // 紫外线指数
          return `iot/list/ultraviolet_${stateSuffix}.png`
        }
        case '69': { // 降雨率
          return `iot/list/rainfall_rate_${stateSuffix}.png`
        }
        default: {
          return undefined
        }
      }
    },
    chooseDeviceLabel (device) {
      if (device.typeTag === 4) {
        let code = Number(device.typeCode)
        switch (code) {
          case 401:
          case 402: {
            return '球'
          }
          case 405: {
            return '枪'
          }
          default: {
            return '视'
          }
        }
      }
    },
    chooseDeviceAvatarUrl (sensor) {
      let code = Number(sensor.typeCode)
      switch (code) {
        case 101: case 102: case 103: case 105: case 106:
        case 201: case 202: case 203: case 204:
        case 402: case 405: {
          return `iot/list/device_type_photo_${code}.jpg`
        }
        default: {
          if (code >= 100 && code < 200) {
            return 'iot/list/device_type_photo_100_def.jpg'
          }
          if (code >= 200 && code < 300) {
            return 'iot/list/device_type_photo_200_def.jpg'
          }
          if (code >= 400 && code < 500) {
            return 'iot/list/device_type_photo_400_def.jpg'
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped="scoped">
._page {
  margin: 0;
  padding: 0;
  background-color: white;
  > ._panel {
    > ._banner {
      height: 10px;
      background-color: #f6f5f6;
    }
    > ._head {
      display: flex;
      flex-flow: row nowrap;
      justify-content: flex-start;
      align-content: center;
      align-items: stretch;
      height: 44px;
      padding: 10px;
      > ._icon {
        flex: none;
        width: 20px;
        height: 20px;
      }
      > ._icon._weather {
        background-image: url(../../../assets/iot/list/qix40.png);
        background-size: 20px 20px;
      }
      > ._icon._soil {
        background-image: url(../../../assets/iot/list/tur40.png);
        background-size: 20px 20px;
      }
      > ._icon._graphic {
        background-image: url(../../../assets/iot/list/ship40.png);
        background-size: 20px 20px;
      }
      > ._title {
        flex: auto;
        font-size: 14px;
        font-weight: bold;
        color: #333;
        margin: 0px 0px 0px 10px;
      }
      > ._arrow {
        flex: none;
        width: 13px;
        height: 7px;
        margin: auto 5px;
        background-image: url(../../../assets/iot/list/down.png);
        background-size: 13px 7px;
      }
      > ._arrow._collapsed {
        transform: rotate(180deg);
      }
    }
    > ._separator {
      background-color: #f6f5f6;
      height: 1px;
    }
    > ._body {
      padding: 0;
      > ._device {
        > ._separator {
          background-color: #f6f5f6;
          height: 10px;
        }
        > ._box {
          display: flex;
          flex-flow: row nowrap;
          justify-content: flex-start;
          align-content: center;
          align-items: stretch;
          padding: 0 10px;
          > ._detail {
            flex: auto;
            > ._title {
              margin: 10px 0px 0px 0px;
              display: flex;
              flex-flow: row nowrap;
              > ._name {
                flex: auto;
                font-size: 13px;
                font-weight: normal;
                color: #333;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: normal;
                word-break: break-all;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 2;
              }
              > ._power {
                flex: none;
                padding: 0 0 0 0px;
                width: 60px;
                overflow: hidden;
                > ._content {
                  display: flex;
                  flex-flow: row nowrap;
                  overflow: hidden;
                  justify-content: flex-end;
                  overflow: hidden;
                  > ._value {
                    text-align: right;
                    width: 50px;
                    font-size: 12px;
                    line-height: 19px;
                    word-break: break-all;
                    word-wrap: break-word;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  }
                  > img {
                    width: 19px;
                    height: 19px;
                  }
                }
              }
              > ._power._state-offline {
                > ._content {
                  > ._value {
                    color: #333333;
                  }
                  > ._name {
                    color: #666666;
                  }
                }
              }
              > ._power._state-normal {
                > ._content {
                  > ._value {
                    color: #333333;
                  }
                  > ._name {
                    color: #666666;
                  }
                }
              }
              > ._power._state-warn {
                > ._content {
                  > ._value {
                    color: rgb(255, 75, 65);
                  }
                  > ._name {
                    color: rgb(255, 75, 65);
                  }
                }
              }
              > ._power._state-fault {
                > ._content {
                  > ._value {
                    color: rgb(255, 171, 25);
                  }
                  > ._name {
                    color: rgb(255, 171, 25);
                  }
                }
              }
            }
            > ._separator {
              background-color: #f6f5f6;
              height: 1px;
            }
            > ._sensors {
              display: flex;
              flex-flow: row wrap;
              justify-content: space-between;
              margin: 10px 0 0 0;
              > ._sensor {
                flex: none;
                display: flex;
                flex-flow: row nowrap;
                justify-content: space-between;
                width: 31%;
                height: 55px;
                margin: 0 0 10px 0;
                padding: 5px;
                border-radius: 4px;
                > ._standard {
                  flex: auto;
                  display: flex;
                  flex-flow: row nowrap;
                  justify-content: space-between;
                  overflow: hidden;
                  > ._content {
                    flex: auto;
                    display: flex;
                    flex-flow: column nowrap;
                    justify-content: center;
                    align-items: flex-start;
                    overflow: hidden;
                    > ._value {
                      font-size: 18px;
                      width: 100%;
                      word-break: break-all;
                      word-wrap: break-word;
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                      > ._unit {
                        font-size: 12px;
                      }
                    }
                    @media screen and (max-width: 360px) {
                      > ._value {
                        font-size: 15px;
                        > ._unit {
                          font-size: 10px;
                        }
                      }
                    }
                    @media screen and (max-width: 320px) {
                      > ._value {
                        font-size: 13px;
                        > ._unit {
                          font-size: 8px;
                        }
                      }
                    }
                    > ._name {
                      font-size: 12px;
                      width: 100%;
                      word-break: break-all;
                      word-wrap: break-word;
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;
                    }
                  }
                  > ._icon {
                    flex: none;
                    width: 20px;
                    display: flex;
                    flex-flow: row nowrap;
                    justify-content: center;
                    align-items: center;
                    align-content: center;
                    overflow: hidden;
                    > img {
                      width: 20px;
                      height: 20px;
                    }
                  }
                }
                > ._extral {
                  flex: auto;
                  display: flex;
                  flex-flow: column nowrap;
                  justify-content: space-between;
                  font-size: 10px;
                  line-height: 18px;
                  overflow: hidden;
                  > ._content {
                    overflow: hidden;
                    > ._icon {
                      width: 5px;
                      height: 8px;
                    }
                    > ._name {
                      padding: 0 0 0 5px;
                    }
                    > ._value {
                      font-size: 12px;
                      font-weight: bold;
                    }
                  }
                }
              }
              > ._sensor._state-offline {
                background-color: #f6f5f6;
                > ._standard {
                  > ._content {
                    > ._value {
                      color: #333333;
                      > ._unit {
                        color: #333333;
                      }
                    }
                    > ._name {
                      color: #666666;
                    }
                  }
                }
              }
              > ._sensor._state-normal {
                background-color: #f6f5f6;
                > ._standard {
                  > ._content {
                    > ._value {
                      color: #333333;
                      > ._unit {
                        color: #333333;
                      }
                    }
                    > ._name {
                      color: #666666;
                    }
                  }
                }
              }
              > ._sensor._state-warn {
                background-color: #f6f5f6;
                > ._standard {
                  > ._content {
                    > ._value {
                      color: rgb(255, 75, 65);
                      > ._unit {
                        color: rgb(255, 75, 65);
                      }
                    }
                    > ._name {
                      color: rgb(255, 75, 65);
                    }
                  }
                }
              }
              > ._sensor._state-fault {
                background-color: #f6f5f6;
                > ._standard {
                  > ._content {
                    > ._value {
                      color: rgb(255, 171, 25);
                      > ._unit {
                        color: rgb(255, 171, 25);
                      }
                    }
                    > ._name {
                      color: rgb(255, 171, 25);
                    }
                  }
                }
              }
              > ._sensor._state-hidden {
                visibility: hidden;
              }
            }
            > ._bottom {
              margin-bottom: 5px;
              font-size: 11px;
              font-weight: normal;
              color: #999;
              word-break: break-all;
              word-wrap: break-word;
              overflow: auto;
              > ._location {
                display: inline-block;
                word-break: break-all;
                word-wrap: break-word;
                overflow: auto;
              }
              > ._datetime {
                text-align: right;
                display: inline-block;
                word-break: break-all;
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }
            }
          }
        }
      }
      > ._panel4x4 {
        display: flex;
        flex-flow: row wrap;
        justify-content: space-between;
        > ._holder {
          flex: 1 1 49%;
          max-width: 49%;
          max-height: 49%;
          padding-bottom: 49%;
          margin-bottom: 2%;
          background: #02ad58;
          position: relative;
          > ._device {
            position: absolute;
            width: 100%;
            height: 100%;
            background-size: 100%;
            display: flex;
            flex-flow: column nowrap;
            > ._top {
              flex: none;
              height: 30px;
            }
            > ._center {
              flex: auto;
              display: flex;
              flex-flow: column nowrap;
              justify-content: center;
              align-items: center;
              > img {
                width: 40px;
                height: 40px;
              }
            }
            > ._bottom {
              flex: none;
              display: flex;
              flex-flow: row nowrap;
              height: 30px;
              padding: 0 5px;
              background-color: rgba(0, 0, 0, 0.25);
              > ._name {
                flex: auto;
                font-size: 13px;
                line-height: 30px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                color: white;
              }
              > ._icon {
                flex: none;
                width: 21px;
                display: flex;
                flex-flow: column nowrap;
                justify-content: center;
                margin: 0px 0px 0px 5px;
                > div {
                  display: inline-block;
                  line-height: 17px;
                  text-align: center;
                  color: #ffffff;
                  background-color: #fe5f57;
                  padding: 2px;
                  border-radius: 3px;
                }
              }
              > ._img {
                flex: none;
                width: 24px;
                display: flex;
                flex-flow: column nowrap;
                justify-content: center;
                margin: 0px 0px 0px 5px;
                > img {
                  display: inline-block;
                  text-align: center;
                  padding: 0px;
                  border-radius: 3px;
                }
              }
            }
          }
        }
      }
    }
  }
}
.hidden {
  display: none;
}
</style>
