import Vue from 'vue'
import Router from 'vue-router'

import { r } from '@vehiclecloud/commons/util/router-helpers'

import Farmland from '@/pages/Farmland'
import FarmlandDetails from '@/pages/farmland/FarmlandDetails'
import FarmlandDetailsEdit from '@/pages/farmland/FarmlandDetailsEdit'
import PublicWeather from '@/pages/weather/pweather/PublicWeather'
import BloomSky from '@/pages/weather/bloomsky/bloomSky'
import SkyPhotos from '@/pages/weather/bloomsky/skyPhotos'
import WeatherSwitch from '@/pages/weather/WeatherSwitch'

import FwkMeasureSoilList from '@/pages/fwk/MeasureSoilList'
import FwkMeasureSoilDetail from '@/pages/fwk/MeasureSoilDetail'
import FwkFarmWorkList from '@/pages/fwk/FarmWorkList'
import FwkFarmWorkDetail from '@/pages/fwk/FarmWorkDetail'

import IotGis from '@/pages/iot/gis/Gis'
import IotListView from '@/pages/iot/list/ListView'
import IotDeviceWeather from '@/pages/iot/device/Weather'
import IotDeviceSoil from '@/pages/iot/device/Soil'
import IotDeviceWater from '@/pages/iot/device/Water'
import IotDeviceGraphic from '@/pages/iot/device/Graphic'
import IotDeviceController from '@/pages/iot/device/Controller'

import PlantingAdd from '@/pages/planting/PlantingAdd'
import PlantingList from '@/pages/planting/PlantingList'
import PlantingDetails from '@/pages/planting/PlantingDetails'

Vue.use(Router)

export default new Router({
  routes: [
    r(Farmland, '地块信息', '/'),
    r(FarmlandDetails, '地块信息'),
    r(FarmlandDetailsEdit, '地块信息'),
    r(PublicWeather, '公共天气', '/public/weather'),
    r(BloomSky, '微气象', '/bloomsky'),
    r(SkyPhotos, '天空图片详情'),
    r(WeatherSwitch, '天气切换'),
    r(FwkMeasureSoilList, '测土报告'),
    r(FwkMeasureSoilDetail, '测土信息详情', '/fwk/measure_soil/:_id'),
    r(FwkFarmWorkList, '农事记录'),
    r(FwkFarmWorkDetail, '农事记录详情', '/fwk/farm_work/:_id'),
    r(IotGis, '地块详细数据'),
    r(IotListView, '物联网列表视图'),
    r(IotDeviceWeather, '微气象详情', '/iot/device/weather/:_id'),
    r(IotDeviceSoil, '土壤检测详情', '/iot/device/soil/:_id'),
    r(IotDeviceWater, '水体检测详情', '/iot/device/water/:_id'),
    r(IotDeviceGraphic, '视频图像详情', '/iot/device/graphic/:_id'),
    r(IotDeviceController, '控制设备详情', '/iot/device/controller/:_id'),
    r(PlantingAdd, '添加种植信息'),
    r(PlantingList, '种植信息'),
    r(PlantingDetails, '种植信息详情', '/planting/details/:_id')
  ]
})
