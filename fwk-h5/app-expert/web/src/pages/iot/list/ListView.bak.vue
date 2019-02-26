<template>
  <div class="page">
    <div class="panel" v-for="item in items" :key="item.id" v-if="item.display">
      <div class="panel-banner">&nbsp;</div>
      <div class="panel-head" @click="item.collapsed = !item.collapsed">
        <div class="panel-head-icon" :class="item.iconClass">&nbsp;</div>
        <div class="panel-head-title">{{item.name}}({{item.devices.length || 0}})</div>
        <div class="panel-head-arrow" :class="{'panel-head-arrow-collapsed': item.collapsed}">&nbsp;</div>
      </div>
      <div class="panel-separator">&nbsp;</div>
      <div class="panel-body" :class="{'hidden': item.collapsed}">
        <!--
        <v-card flat v-for="(device, index) in item.devices" :key="device.id" :to="{ name: item.router, params: { _id: device.id}}">
        -->
        <v-card flat v-for="(device, index) in item.devices" :key="device.id">
          <div @click="showDetail(item, device)">
            <div class="panel-body-separator" v-if="index != 0">&nbsp;</div>
            <div class="panel-body-box">
              <div class="panel-body-box-action">
                <div class="panel-body-box-action-avatar">
                  <img class="panel-body-box-action-avatar-main" v-if="device.avatarUrl"
                       :src="device.avatarUrl"
                       :class="{'panel-body-box-action-avatar-main-offline': device.offline}"/>
                  <img class="panel-body-box-action-avatar-main" v-else style="visibility: hidden"/>
                  <div class="panel-body-box-action-avatar-notice" v-if="device.offline"/>
                </div>
              </div>
              <div class="panel-body-box-detail">
                <div class="panel-body-box-detail-title">{{device.name}}({{device.code || '--'}})</div>
                <div class="panel-body-box-detail-labels">
                <span class="panel-body-box-detail-labels-type">
                  {{device.typeName}}
                </span>
                  <span class="panel-body-box-detail-labels-location">
                  {{device.location?`${device.location}`:''}}
                </span>
                </div>
                <div v-if="Array.isArray(device.sensors) && device.sensors.length > 0">
                  <div class="panel-body-box-detail-separator"></div>
                  <div class="panel-body-box-detail-datetime">{{device.lastSignalTimeString}}</div>
                  <div class="panel-body-box-detail-sensors" v-if="Array.isArray(device.sensors)">
                    <span class="sensor"
                          v-for="(sensor, index) in device.sensors" :key="index"
                          v-if="sensor.valueType === 0"
                          :class="sensor.className">
                      {{sensor.label}}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </v-card>
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
        iconClass: 'panel-head-icon-weather',
        router: 'iot-device-weather',
        display: true,
        collapsed: false,
        devices: []
      },
      soil: {
        id: 2,
        name: '土壤检测',
        iconClass: 'panel-head-icon-soil',
        router: 'iot-device-soil',
        display: true,
        collapsed: false,
        devices: []
      },
      water: {
        id: 3,
        name: '水体检测',
        iconClass: 'panel-head-icon-water',
        router: 'iot-device-water',
        display: false,
        collapsed: false,
        devices: []
      },
      graphic: {
        id: 4,
        name: '视频图像',
        iconClass: 'panel-head-icon-graphic',
        router: 'iot-device-graphic',
        display: true,
        collapsed: false,
        devices: []
      },
      controller: {
        id: 5,
        name: '控制设备',
        iconClass: 'panel-head-icon-controller',
        router: 'iot-device-controller',
        display: false,
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
      if (item.id === 4 && window.native && window.native.player) {
        try {
          window.native.player(JSON.stringify({
            deviceSerial: device.code,
            cameraNo: 1,
            isBall: this.isControllable(device)
          }))
          return
        } catch (e) {
          this.showMessageInfo(e.message)
        }
      }
      this.$router.push({name: item.router, params: {_id: device.id}})
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
    buildQuery ({farmlandId, typeIds, tags, dataLoaders}) {
      let queryBuilder = []
      queryBuilder.push({
        $match: {
          _removed: {
            $exists: false
          }
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
              $in: typeIds.map(typeId => ({$oid: typeId}))
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
        device.typeName = item.type.name
        device.location = item.location
        device.avatarUrl = this.chooseDeviceAvatarUrl(device)
        device.signal = item.accessState
        if (device.signal) {
          device.lastSignalTime = Number((device.signal.time || {}).$numberLong)
          if (device.lastSignalTime) {
            device.lastSignalTimeString = Moment(device.lastSignalTime).format('YYYY-MM-DD HH:mm')
          }
          device.status = device.signal.status
          device.statusCode = device.signal.code
          if ((device.statusCode || []).find(item => item === 2003)) {
            device.offline = true
          }
        }
        device.sensors = item.sensorTypes.map(sensorType => {
          let sensor = {}
          sensor.code = sensorType.code
          sensor.name = sensorType.name
          sensor.unit = sensorType.data.unit
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
            switch (sensor.as) {
              case 1: {
                sensor.className = 'sensor-state-normal'
                break
              }
              case 2: {
                sensor.className = 'sensor-state-alarm'
                break
              }
              case 3: {
                sensor.className = 'sensor-state-fault'
                break
              }
              default: {
                sensor.className = 'sensor-state-normal'
              }
            }
          }
          return sensor
        }).map(sensor => {
          sensor.value = this.convertSensorValue(sensor)
          return sensor
        }).map(sensor => {
          sensor.label = sensor.name
          switch (typeof sensor.value) {
            case 'number': {
              sensor.label += sensor.value.toFixed(2)
              sensor.label += sensor.unit || ''
              break
            }
            case 'string': {
              sensor.label += sensor.value
              sensor.label += sensor.unit || ''
              break
            }
            default: {
              sensor.label += '--'
            }
          }
          return sensor
        })
        result.push(device)
      }
      console.debug(result)
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
                return '未知'
            }
          }
          default : {
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
    chooseDeviceAvatarUrl (sensor) {
      let code = Number(sensor.typeCode)
      switch (code) {
        case 101:case 102:case 103:case 105:case 106:
        case 201:case 202:case 203:case 204:
        case 402:case 405: {
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

<style scoped="scoped">
  .page {
    margin: 0;
    padding: 0;
  }
  .panel {
    background-color: white;
  }
  .panel-banner {
    height: 10px;
    background-color: #f6f5f6;
  }
  .panel-head {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-content: center;
    align-items: stretch;
    height: 44px;
    padding: 10px;
  }
  .panel-head-icon {
    flex: none;
    width: 20px;
    height: 20px;
  }
  .panel-head-icon-weather {
    background-image: url(../../../assets/iot/list/qix40.png);
    background-size: 20px 20px;
  }
  .panel-head-icon-soil {
    background-image: url(../../../assets/iot/list/tur40.png);
    background-size: 20px 20px;

  }
  .panel-head-icon-graphic {
    background-image: url(../../../assets/iot/list/ship40.png);
    background-size: 20px 20px;
  }

  .panel-head-title {
    flex: auto;
    font-size: 14px;
    font-weight: bold;
    color: #333;
    margin: 0px 0px 0px 10px;
  }
  .panel-head-arrow {
    flex: none;
    width: 13px;
    height: 7px;
    margin: auto 5px;
    background-image: url(../../../assets/iot/list/down.png);
    background-size: 13px 7px;
  }
  .panel-head-arrow-collapsed {
    transform:rotate(180deg);
  }
  .panel-separator {
    background-color: rgb(246, 245, 246);
    height: 1px;
  }
  .panel-body {
    padding: 0px 10px;
  }
  .hidden {
    display: none;
  }
  .panel-body-box {
    margin: 12px 0px 0px 0px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-content: center;
    align-items: stretch;
  }
  .panel-body-separator {
    background-color: #eeeeee;
    height: 1px;
  }
  .panel-body-box-action {
    flex: none;
    width: 44px;
  }
  .panel-body-box-action-avatar {
    width: 44px;
    height: 44px;
  }
  .panel-body-box-action-avatar-main {
    width: 44px;
    height: 44px;
    -moz-border-radius: 100%;
    -webkit-border-radius: 100%;
    -o-border-radius: 100%;
    -ms-border-radius: 100%;
    border-radius: 100%;

  }
  .panel-body-box-action-avatar-notice {
    position: relative;
    width: 15px;
    height: 15px;
    background: url(../../../assets/iot/list/fault.png);
    background-size: 15px 15px;
    border-radius: 100%;
    top: -50px;
    left: 30px;
  }
  .panel-body-box-detail {
    flex: auto;
    margin: 0px 0px 0px 20px;
  }
  .panel-body-box-detail-title {
    margin: 0px 0px 0px 0px;
    font-size: 16px;
    font-weight: bold;
    color:  #333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
  }
  .panel-body-box-detail-labels {
    margin: 6px 0px 12px 0px;
    font-size: 13px;
    font-weight: normal;
    color: #999;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
  }
  .panel-body-box-detail-labels-type {
  }
  .panel-body-box-detail-labels-location {
    padding-left: 20px;
  }
  .panel-body-box-detail-separator {
    background-color: #eeeeee;
    height: 1px;
  }
  .panel-body-box-detail-datetime {
    margin: 5px 0px 5px 0px;
    font-size: 11px;
    font-weight: normal;
    color: #999;
  }
  .panel-body-box-detail-sensors {
    margin: 0px 0px 10px 0px;
  }
  .sensor {
    display: inline-block;
    margin: 0px 5px 5px 0px;
    padding: 2px 5px 2px 5px;
    line-height: 15px;
    font-size: 11px;
    font-weight: normal;
    border-radius: 34px;
  }
  .sensor-state-normal {
    color: #666666;
    background-color: #eaeaea;
  }
  .sensor-state-alarm {
    color: #ff473d;
    background-color: #ffeceb;
  }
  .sensor-state-fault {
    color: #ed702d;
    background-color: #ffeddc;
  }
</style>
