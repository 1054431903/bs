<template>
  <v-card class="page-content pt-0">
    <v-layout class="row mb-1">
      <v-flex sm12
              md5
              lg5
              xl4>
        <v-areaselect :curAddressCode.sync="curAreaCode"
                      :showLevel="4"
                      :isReversal="isReversal"
                      @titleChanged="zidiyi($event)"> </v-areaselect>
      </v-flex>
      <v-flex sm12
              md2
              lg2
              xl2
              ml-1
              v-if="farmlands.items.length > 0">
        <v-autocomplete :items="farmlands.items"
                        v-model="farmlands.currentid"
                        item-text="name"
                        item-value="id"
                        single-line
                        hide-details
                        no-data-text="未找到数据"
                        label="输入地块名称搜索"
                        title="输入地块名称搜索"
                        clearable
                        append-icon="search"></v-autocomplete>
      </v-flex>
    </v-layout>
    <v-layout class="row gisview">
      <v-flex sm12>
        <c-map-layers v-bind="map.layers">
          <c-cartogram v-if="map.type === 'fourColor'">
            <c-cartogram-four-color v-model="fourColor.data"
                                    v-bind="fourColor.props">
              <cc-label v-for="p in fourColor.data"
                        :key="p.code"
                        :latlng="{lat: p.bunds[5], lng: p.bunds[4]}"
                        :label-color="fourColorLabelColor(p.code)"
                        className="cc-label"
                        @label-click="fourColorPolyClick(p.code)"
                        @label-mouseover="fourColorPolyMouseover(p.code)"
                        @label-mouseleave="fourColorPolyMouseleave(p.code)">
                <div>{{ p.name }}</div>
              </cc-label>
            </c-cartogram-four-color>
          </c-cartogram>
          <template v-else-if="map.type === 'farmlands'"
                    v-for="f in farmlands.items">
            <c-polygon v-if="(farmlands.currentid === null) || (farmlands.currentid === f.id)"
                       :key="f.id"
                       :latlngs="f.gis"
                       :stroke="true"
                       :fill="true"
                       fill-color="#229af0"
                       :fill-opacity="1"
                       :popup-event="null"
                       @polygon-mouseover="farmlandsLayerMouseover(f, 'p')"
                       @polygon-mouseleave="farmlandsLayerMouseleave('p')">
              <c-marker v-if="f.id === 165"
                        :key="f.id"
                        :latlng="{ lat: f.lat, lng: f.lng }"
                        :icon="{image: `./static/land/flag.png`, iconSize: [20, 'auto'], iconAnchor: [15, 45]}"
                        :popup-props="{offset: [0, -38]}"
                        :popup-event="null">
              </c-marker>
            </c-polygon>
          </template>
          <template v-if="farmlands.hover.item">
            <cc-label :latlng="{ lat: farmlands.hover.item.lat, lng: farmlands.hover.item.lng }"
                      @label-mouseover="farmlandsLayerMouseover(null, 'l1')"
                      @label-mouseleave="farmlandsLayerMouseleave('l1')">
              <div class="landinfo">
                <div class="landname">
                  <span :title="farmlands.hover.item.name">{{(farmlands.hover.item.name).length > 10 ? (farmlands.hover.item.name).substr(0, 8) + '...' : farmlands.hover.item.name}}</span>
                  <v-btn flat
                         color="primary"
                         :to="{name: 'viewland', query: {id: farmlands.hover.item.id}}"> 点击查看详情&nbsp;&nbsp;>> </v-btn>
                </div>
                <v-divider light></v-divider>
                <div class="landdetail">面积：{{farmlands.hover.item.area.toFixed(2)}}亩</div>
                <div class="landdetail">责任部门：<span>{{farmlands.hover.item.departmentname ? farmlands.hover.item.departmentname : '--'}}</span></div>
                <div class="landdetail">负责人：<span class="landdetailhide"
                        :title="showResponsibleUserInfo(farmlands.hover.item.responsibleuser)">{{showResponsibleUserInfo(farmlands.hover.item.responsibleuser)}}</span></div>
                <div class="landdetail">地块从属：<span class="landdetailhide"
                        :title="dependenceInfo(farmlands.hover.item)">{{ dependenceInfo(farmlands.hover.item) }}</span></div>
              </div>
            </cc-label>
          </template>
          <div class="leaflet-top leaflet-icon leaflet-report leaflet-back-right"
               v-if="!showReport">
            <div class="leaflet-control"
                 style="background-color: rgba(0, 0, 0, 0.7);">
              <a title="图表">
                <v-avatar size="25px">
                  <v-icon dark
                          @click="showReport = !showReport">list</v-icon>
                </v-avatar>
              </a>
            </div>
          </div>
          <div class="leaflet-bottom leaflet-icon leaflet-right leaflet-icon leaflet-back leaflet-back-right">
            <div class="leaflet-control"
                 @click="gisback"
                 v-if="this.director.length > 1"
                 style="padding: 5px 5px; background-color: rgba(0, 0, 0, 0.7);">
              <a title="返回">
                <v-avatar size="20px">
                  <v-icon dark>arrow_back</v-icon>
                </v-avatar>
              </a>
            </div>
            <div class="leaflet-control"
                 @click="gisrefresh"
                 style="padding: 5px 5px; background-color: rgba(0, 0, 0, 0.7);">
              <a title="刷新">
                <v-avatar size="20px">
                  <v-icon dark>autorenew</v-icon>
                </v-avatar>
              </a>
            </div>
          </div>
          <div class="leaflet-bottom leaflet-right reportbar"
               v-if="showReport">
            <div class="leaflet-control"
                 style="margin-top: 1px;">
              <a title="关闭"
                 @click="showReport = !showReport">
                <v-avatar size="23px">
                  <v-icon dark>close</v-icon>
                </v-avatar>
              </a>
            </div>
            <v-custom-bar :chartData="baritems"
                          :landinfo="Object.assign({}, {landsum: landTotalCount, landareas: landTotalArea})"></v-custom-bar>
          </div>
        </c-map-layers>
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-modaldialog :isShow="processing"
                     title="数据加载中..."
                     :modalMaxWidth="120"
                     :showTitle="false"
                     :bodyHeight="90">
        <div class="text-xs-center">
          <v-progress-circular indeterminate
                               color="primary"></v-progress-circular>
          <div>数据加载中...</div>
        </div>
      </v-modaldialog>
    </v-layout>
  </v-card>
</template>
<script>
import CMapLayers from '@agrithings/cmap/CMapLayers'
import CMarkerCluster from '@agrithings/cmap/overlay/CMarkerCluster'
import CMarker from '@agrithings/cmap/overlay/CMarker'
import CPolygon from '@agrithings/cmap/overlay/CPolygon'
import { types, mixMapTypeOfArea } from '@agrithings/cmap/util'
import CCartogram from './cartogram/c-cartogram'
import CCartogramFourColor from './cartogram/c-cartogram-four-color'
import CcLabel from './cartogram/cc-label'
import { excuteApis } from '@/apis'
import { isArray, centerOf, deAreasCode } from '@/utils'
import VCustomBar from './CustomBar'

import mongo from '@vehiclecloud/mongo-rest-client'

const FourColorStyle = {
  // 默认色
  Common: {
    poly: {
      color: '#444444',
      weight: 1,
      opacity: 0.8,
      fillColor: '#fafafa',
      fillOpacity: 0.8,
      labelColor: 'rgb(100,0,0)'
    },
    label: { color: '#000000' }
  },
  // 激活色
  Hover: {
    poly: {
      color: '#444444',
      weight: 1,
      opacity: 1,
      fillColor: 'rgba(255,215,0,1)',
      fillOpacity: 1,
      labelColor: 'rgb(100,0,0)'
    },
    label: { color: '#000000' }
  },
  // 热区色
  Active: {
    poly: [{
      color: '#444444',
      weight: 0,
      opacity: 0,
      // fillColor: '#05abf2',
      fillColor: 'rgba(245,161,0,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }, {
      color: '#444444',
      weight: 0,
      opacity: 0,
      fillColor: 'rgba(247,180,51,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }, {
      color: '#444444',
      weight: 0,
      opacity: 0,
      fillColor: 'rgba(249,199,102,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }, {
      color: '#444444',
      weight: 0,
      opacity: 0,
      fillColor: 'rgba(251,217,153,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }, {
      color: '#444444',
      weight: 0,
      opacity: 0,
      fillColor: 'rgba(253,236,204,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }, {
      color: '#444444',
      weight: 0,
      opacity: 0,
      fillColor: 'rgba(254,245,227,1)',
      fillOpacity: 1,
      labelColor: '#ffffff'
    }],
    label: { color: '#000000' }
  }
}

function simpleCode (code) {
  if (code.length < 12) return code
  return code.substr(6, 6) === '000000' ? code.substr(0, 6) : code.substr(0, 9)
}

function gisMixMapType (addresscode) {
  if (addresscode.startsWith('0')) return mixMapTypeOfArea()
  let curcode = deAreasCode(addresscode)
  let codes = []
  curcode['countycode'] ? codes.push(curcode['countycode'].substr(0, 12)) : null
  curcode['citycode'] ? codes.push(curcode['citycode'].substr(0, 12)) : null
  curcode['provincecode'] ? codes.push(curcode['provincecode'].substr(0, 12)) : null
  console.log(mixMapTypeOfArea(codes))
  return mixMapTypeOfArea(codes)
}

const FourColor = {
  created () { },
  data () {
    let self = this
    return {
      fourColor: {
        provincesPromise: null,
        data: null,
        counts: {
          map: {}
        },
        props: {
          maincode: '100000', // 110000
          params: {
            colors: {},
            fillopacity: 0
          },
          styles: {}, // {code: {count: 0, style: {}}}
          polyClick (e) {
            self.fourColorPolyClick(e.target.code)
          },
          polyMouseover (e) {
            self.fourColorPolyMouseover(e.target.code)
          },
          polyMouseleave (e) {
            self.fourColorPolyMouseleave(e.target.code)
          }
        }
      }
    }
  },
  watch: {
    'fourColor.data': function (v) {
      if (!v) return
      this.map.layers.bounds = [
        [
          Math.min.apply(null, v.map(d => d.bunds[1])),
          Math.min.apply(null, v.map(d => d.bunds[0]))
        ],
        [
          Math.max.apply(null, v.map(d => d.bunds[3])),
          Math.max.apply(null, v.map(d => d.bunds[2]))
        ]
      ]
      this.fourColorUpdateCounts(v)
    }
  },
  methods: {
    showForuColor () {
      this.fourColor.data = null
      this.fourColor.counts.map = {}
      this.fourColor.props.styles = {}
      // this.map.layers.mapType = types.YCMIXMAP
      this.map.type = 'fourColor'
    },
    fourColorPolyClick (oriCode) {
      if (!this.fourColor.counts.map[oriCode]) return
      this.fourColor.props.maincode = oriCode
      this.director.push(oriCode)
      let code = this.getCurAreaCode(oriCode)
      this.map.layers.mapType = gisMixMapType(code)
      oriCode.length === 9 ? this.getGisLand(oriCode) : this.showForuColor()
      this.curAreaCode = code
    },
    fourColorPolyMouseover (code) {
      let colorIndex = this.getColorIndex(this.landTotalArea, this.fourColor.counts.map[simpleCode(code)])
      if (colorIndex < 6) {
        this.$set(this.fourColor.props.styles, simpleCode(code), FourColorStyle.Hover.poly)
      }
    },
    fourColorPolyMouseleave (code) {
      let colorIndex = this.getColorIndex(this.landTotalArea, this.fourColor.counts.map[simpleCode(code)])
      if (colorIndex < 6) {
        let style = FourColorStyle.Active.poly[colorIndex]
        this.$set(this.fourColor.props.styles, simpleCode(code), style)
      }
    },
    fourColorLabelColor (code) {
      return this.fourColor.counts.map[simpleCode(code)] ? FourColorStyle.Active.label.color : FourColorStyle.Common.label.color
    },
    fourColorCenterOf (code) {
      let p = this.fourColor.data.find(d => d.code === code + '')
      if (!p) return null
      return { lat: p.bunds[5], lng: p.bunds[4] }
    },
    fourColorUpdateCounts (v) {
      let code = this.fourColor.props.maincode
      let addresscode = code.length === 6 ? (code === '100000' ? '100000000000100000000000' : (code + '000000' + code + '000000')) : (code.length === 9 ? (code + '000' + code + '000') : (code.length === 12 ? (code + code) : code))
      // let requestParams = Object.assign({}, {addresscode: code.length === 6 ? (code === '100000' ? '100000000000100000000000' : (code + '000000' + code + '000000')) : (code.length === 9 ? (code + '000' + code + '000') : (code.length === 12 ? (code + code) : code))})
      excuteApis(Object.assign({}, deAreasCode(addresscode)), global.constant.parcelApis, 'parcel', 'queryParcelAreaStatsByAddressCode').then(response => {
        let res = response.data
        if (res.flag === 1) {
          let data = res.data
          let lands = data.filter((item) => {
            return item.totalarea !== 0
          })
          // this.baritems = []
          this.chartData.rows = []
          this.landTotalArea = 0
          this.landTotalCount = 0
          lands.forEach((land) => {
            let maincode = this.fourColor.props.maincode
            if (land['addresscode']) {
              let code = maincode.substr(4, 2) === '00' ? parseInt(land['addresscode'].substr(0, 6)) : parseInt(land['addresscode'].substr(0, 9))
              this.fourColor.counts.map[code] = land['totalarea']
              let cd = Object.assign({}, { addressname: land.addressname, totalarea: parseFloat(land.totalarea.toFixed(2)) })
              this.chartData.rows.push(cd)
              this.landTotalArea += land.totalarea
              this.landTotalCount += land.totalnum
            }
          })
          this.baritems = this._.sortBy(this.chartData.rows, (o) => {
            return -o['totalarea']
          })
          this.landTotalArea = this.landTotalArea.toFixed(2)
          // 设置显示颜色
          v.forEach((ii) => {
            if (simpleCode(ii.code) === (Object.keys(this.fourColor.counts.map).find(function (value) {
              return value === simpleCode(ii.code)
            }))) {
              let colorIndex = this.getColorIndex(this.landTotalArea, this.fourColor.counts.map[simpleCode(ii.code)])
              this.$set(this.fourColor.props.styles, simpleCode(ii.code), FourColorStyle.Active.poly[colorIndex])
            } else {
              this.$set(this.fourColor.props.styles, simpleCode(ii.code), FourColorStyle.Common.poly)
            }
          })
        }
      })
    }
  }
}

// 经纬度最大跨度
// const MaxSize = { lat: 0.017303, lng: 0.041156 }
const Farmlands = {
  created () {
    this.farmlandsQueryCounts()
  },
  data () {
    return {
      farmlands: {
        items: [],
        currentid: null,
        currentcenter: null,
        hover: {
          item: null,
          latch: 0
        },
        counts: {
          items: [],
          map: {},
          promise: null
        },
        skipShow: false // skip show fn if polygon click
      }
    }
  },
  methods: {
    showFarmlands () {
      // this.map.layers.mapType = types.YCMIXMAP
      this.farmlandsUpdateBounds()
      this.map.type = 'farmlands'
      this.farmlandsQueryCounts()
    },
    farmlandsUpdateBounds () {
      let items = this.farmlands.items
      let bounds = [
        [
          Math.min.apply(null, items.map(f => Math.min.apply(null, f.gis.map(g => g.lat)))),
          Math.min.apply(null, items.map(f => Math.min.apply(null, f.gis.map(g => g.lng))))
        ], [
          Math.max.apply(null, items.map(f => Math.max.apply(null, f.gis.map(g => g.lat)))),
          Math.max.apply(null, items.map(f => Math.max.apply(null, f.gis.map(g => g.lng))))
        ]
      ]
      this.map.layers.bounds = bounds
    },
    async farmlandsQueryCounts () {
      // const code = this.fourColor.props.maincode
      // let requestParams = {}
      // let addresscode = code.length === 6 ? (code === '100000' ? '100000000000100000000000' : (code + '000000' + code + '000000')) : (code.length === 9 ? (code + '000' + code + '000') : (code.length === 12 ? (code + code) : code))
      // if (this.farmlands.currentid) {
      //   Object.assign(requestParams, {parcelid: this.farmlands.currentid})
      //   let curLand = this.farmlands.items.filter(item => {
      //     return item.id === this.farmlands.currentid
      //   })
      //   curLand.length > 0 ? (addresscode = curLand[0].addresscode) : null
      //   console.log(this.farmlands.items)
      // }
      // Object.assign(requestParams, deAreasCode(addresscode))
      // let response = await excuteApis(requestParams, global.constant.parcelApis, 'parcel', 'queryParcelAreaStatsByAddressCode')
      // let res = response.data
      // if (res.flag === 1) {
      //   let data = res.data
      //   let lands = data.filter((item) => {
      //     return item.totalarea !== 0
      //   })
      //   this.farmlands.counts.map = {}
      //   this.chartData.rows = []
      //   this.landTotalCount = 0
      //   this.landTotalArea = 0
      //   lands.forEach((land) => {
      //     if (land['addresscode']) {
      //       let cd = Object.assign({}, {addressname: land.addressname, totalarea: parseFloat(land.totalarea.toFixed(2))})
      //       this.chartData.rows.push(cd)
      //       this.landTotalArea += land.totalarea
      //       this.landTotalCount += 1
      //     }
      //   })
      //   this.baritems = this._.sortBy(this.chartData.rows, (o) => {
      //     return -o['totalarea']
      //   })
      //   this.landTotalArea = this.landTotalArea.toFixed(2)
      // }
      // this.processing = false
      const code = this.fourColor.props.maincode
      let requestParams = {}
      let addresscode = code.length === 6 ? (code === '100000' ? '100000000000100000000000' : (code + '000000' + code + '000000')) : (code.length === 9 ? (code + '000' + code + '000') : (code.length === 12 ? (code + code) : code))
      if (this.farmlands.currentid) {
        let data = this.farmlands.items
        let lands = data.filter((item) => {
          return item.totalarea !== 0 && item.id === this.farmlands.currentid
        })
        this.farmlands.counts.map = {}
        this.chartData.rows = []
        this.landTotalCount = 0
        this.landTotalArea = 0
        lands.forEach((land) => {
          if (land['area']) {
            let cd = Object.assign({}, { addressname: land.name, totalarea: parseFloat(land.area.toFixed(2)) })
            this.chartData.rows.push(cd)
            this.landTotalArea += cd.totalarea
            this.landTotalCount += 1
          }
        })
        this.baritems = this._.sortBy(this.chartData.rows, (o) => {
          return -o['totalarea']
        })
        this.landTotalArea = this.landTotalArea.toFixed(2)
      } else {
        Object.assign(requestParams, deAreasCode(addresscode))
        let response = await excuteApis(requestParams, global.constant.parcelApis, 'parcel', 'queryParcelAreaStatsByAddressCode')
        let res = response.data
        if (res.flag === 1) {
          let data = res.data
          let lands = data.filter((item) => {
            return item.totalarea !== 0
          })
          this.farmlands.counts.map = {}
          this.chartData.rows = []
          this.landTotalCount = 0
          this.landTotalArea = 0
          lands.forEach((land) => {
            if (land['addresscode']) {
              let cd = Object.assign({}, { addressname: land.addressname, totalarea: parseFloat(land.totalarea.toFixed(2)) })
              this.chartData.rows.push(cd)
              this.landTotalArea += land.totalarea
              this.landTotalCount += 1
            }
          })
          this.baritems = this._.sortBy(this.chartData.rows, (o) => {
            return -o['totalarea']
          })
          this.landTotalArea = this.landTotalArea.toFixed(2)
        }
      }
      this.processing = false
    },
    landsPopupopen (land) {
      this.landInfoIsOpen = true
      this.currentLandInfo = land
      this.showReport = false
    },
    farmlandsLayerMouseover (f, s) {
      if (f) this.farmlands.hover.item = f
      this.farmlands.hover.latch += 1
    },
    farmlandsLayerMouseleave (s) {
      this.farmlands.hover.latch -= 1
      if (this.farmlands.hover.latch) return

      setTimeout(() => {
        if (!this.farmlands.hover.latch) this.farmlands.hover.item = null
      }, 10)
    },
    farmlandsHoverReset () {
      this.farmlands.hover.latch = 0
      this.farmlands.hover.item = null
    }
  }
}

export default {
  name: 'farm-gis',
  mixins: [FourColor, Farmlands],
  data () {
    this.chartExtend = {
      series (v) {
        v.forEach(i => {
          i.barWidth = 20
        })
        return v
      }
    }
    return {
      map: {
        type: 'fourColor', // fourColor | farmlands | devices
        layers: {
          mapTypes: [types.MIXMAP, types.VECTORMAP],
          mapType: types.MIXMAP,
          zoomControl: { position: 'bottomright' },
          center: { lat: 25.040609, lng: 102.712251 },
          zoom: 5,
          bounds: null
        }
      },
      baritems: [],
      showReport: true,
      chartData: { rows: [] },
      chartSettings: null,
      baremphasis: null,
      landTotalCount: 0,
      landTotalArea: 0,
      director: [],
      curAreaCode: '100000000000100000000000',
      isReversal: false,
      landInfoIsOpen: false,
      currentLandInfo: null,
      processing: false
    }
  },
  watch: {
    curAreaCode: function (v) {
      let code = this.getMainCode(v)
      let maincode = this.fourColor.props.maincode
      if (code !== maincode) {
        this.fourColor.props.maincode = code
        if (code === '100000') {
          this.director = ['100000']
          this.curAreaCode = '100000000000100000000000'
        } else {
          this.director.push(code)
        }
        // 清空地块检索内容
        code.length > 6 ? this.getGisLand(code) : this.showForuColor()
        if (code.length < 8) {
          this.farmlands.items = []
          this.farmlands.currentid = null
        }
      }
    },
    currentLandInfo: function (v) {
      if (!v || this.landInfoIsOpen) return
      this.$refs[this.landsMarkerRefOf(v)][0].popupOpen()
    },
    'farmlands.currentid' (v) {
      if (v) {
        let curland = this.farmlands.items.filter(land => {
          return land.id === v
        })
        // this.farmlandsQueryCounts() // 按条件过滤地块统计信息
        if (curland && curland[0].gis && curland[0].gis.length > 0) {
          this.$set(this.map.layers, 'center', centerOf(curland[0].gis))
          this.$set(this.map.layers, 'zoom', 17)
        } else {
          alert('没有包含有效GIS信息的地块，请先完善地块GIS信息')
        }
      } else {
        this.$set(this.map.layers, 'zoom', 13)
      }
      this.farmlandsQueryCounts() // 按条件过滤地块统计信息
    },
    'farmlands.items' (v) {
      if (v && v.length > 0 && this.farmlands.currentid === null) {
        // this.initGisCenter()
      }
    }
  },
  computed: {},
  methods: {
    async getGisLand (code) {
      this.processing = true
      let addresscode = code + '000' + code + '000'
      this.map.layers.mapType = gisMixMapType(addresscode)
      this.farmlands.items = []
      let requestParams = {}
      Object.assign(requestParams, deAreasCode(addresscode))
      let response = await excuteApis(requestParams, global.constant.parcelApis, 'parcel', 'queryParcel')
      let res = response.data
      if (res.flag === 1) {
        let lands = res.data
        let gisItems = []
        lands.forEach((land) => {
          let item = Object.assign({}, { id: land.id, name: land.name, area: land.area, lat: land.latitude, lng: land.longitude, supportid: land.supportid })
          let gisItem = Object.assign(item, { gis: [] })
          // 查询地块轮廓
          // let gis = []
          // excuteApis(Object.assign({}, {parcelid: item.id}), global.constant.parcelApis, 'parcelCoord', 'query').then(gRes => {
          //   let gres = gRes.data
          //   if (gres.flag === 1) {
          //     let gdata = gres.data
          //     gdata.forEach((g) => {
          //       let gg = Object.assign({}, {lat: g.latitude, lng: g.longitude})
          //       gis.push(gg)
          //     })
          //     Object.assign(gisItem, {gis: gis})
          //     this.farmlands.items.push(gisItem)
          //   }
          // })
          // this.getGisCoords(gisItem)
          gisItems.push(gisItem)
        })
        this.getGisCoords(gisItems)
        this.showFarmlands()
        // this.initGisCenter()
      }
    },
    // async getGisCoords (gisItem) {
    //   let orgid = global.helper.ls.get('user').loginorgid
    //   let landid = [].concat(Object.assign({}, {$oid: gisItem.supportid}))
    //   let { items } = await (mongo.db('iss').collection('tenant.farmland').find({ filter: { tenant: { $oid: orgid }, _id: { $in: landid } } }))
    //   Object.assign(gisItem, {gis: items[0]['gis'] || []})
    //   this.farmlands.items.push(gisItem)
    // },
    async getGisCoords (gisItems) {
      let orgid = global.helper.ls.get('user').loginorgid
      let lands = []
      let gisLands = {}
      gisItems.forEach(item => {
        lands.push(Object.assign({}, { $oid: item.supportid }))
        gisLands[item.supportid] = item
      })
      // 当目标条目大于500条时，支撑只返回500条数据(此为支持的约束条件)
      let n = Math.ceil(lands.length / 500)
      while (n > 0) {
        let currentLands = lands.slice((n - 1) * 500, 500 * n)
        // let { items } = await (mongo.db('iss').collection('tenant.farmland').find({ filter: { tenant: { $oid: orgid }, _id: { $in: currentLands }, _removed: { $exists: false } } }))
        let { items } = await (mongo.db('iss').collection('tenant.farmland').find({ filter: { tenant: { $oid: orgid }, _id: { $in: currentLands } } }))
        items.forEach(item => {
          let supportid = item['_id']['$oid']
          Object.assign(gisLands[supportid], { gis: item['gis'] || [] })
          this.farmlands.items.push(gisLands[supportid])
        })
        n--
      }
    },
    initGisCenter () {
      let gislands = this.farmlands.items.filter(land => {
        return land.gis && land.gis.length > 0
      })
      if (gislands && gislands.length > 0) {
        this.$set(this.map.layers, 'center', centerOf(gislands[0].gis))
        this.$set(this.map.layers, 'zoom', 17)
      }
    },
    gisback () {
      this.director.pop()
      this.fourColor.props.maincode = this.director[this.director.length - 1]
      this.showForuColor()
      let code = this.getCurAreaCode(this.fourColor.props.maincode)
      // this.isReversal = true
      this.curAreaCode = code
      // 清空地块列表信息
      this.farmlands.items = []
    },
    gisrefresh () {
      let code = this.fourColor.props.maincode
      code.length > 6 ? this.farmlandsQueryCounts() : this.fourColorUpdateCounts(this.fourColor.data)
    },
    showResponsibleUserInfo (user) {
      let str = ''
      if (isArray(user)) {
        for (let i = 0; i < user.length; i++) {
          let username = user[i].name ? user[i].name : '--'
          username += user[i].phone ? '(' + user[i].phone + ')' : ''
          str += username + (i !== user.length - 1 ? ',' : '')
        }
      }
      return str || '--'
    },
    showBelongPeasantInfo (user) {
      let str = ''
      if (user) {
        let username = user.name
        username += user.linkphone ? '（' + user.linkphone + '）' : ''
        str += username
      }
      return str || '--'
    },
    dependenceInfo (f) {
      return (f.dependencename ? f.dependencename : '--') + '(权属人：' + this.showBelongPeasantInfo(f.belongpeasant) + ')'
    },
    getColorIndex (total, value) {
      let percent = value * 1.00 / total
      if (percent >= 0.5) {
        return 0
      } else if (percent >= 0.4) {
        return 1
      } else if (percent >= 0.3) {
        return 2
      } else if (percent >= 0.2) {
        return 3
      } else if (percent >= 0.1) {
        return 4
      } else if (percent > 0) {
        return 5
      } else {
        return 6
      }
    },
    getMainCode (v) {
      let code = v.substr(0, 9)
      let maincode = code.substr(6, 3) === '000' ? code.substr(0, 6) : code
      maincode = (maincode === '100000') ? '100000' : maincode
      return maincode
    },
    getCurAreaCode (code) {
      let maincode = code
      let addresscode = maincode.length === 12 ? (maincode + '' + maincode) : (maincode.length === 9 ? (maincode + '000' + maincode + '000') : (maincode.length === 6 ? (maincode + '000000' + maincode + '000000') : '100000000000100000000000'))
      return (addresscode === '100000000000100000000000') ? '100000000000100000000000' : addresscode
    },
    zidiyi (msg) {
      this.isReversal = msg
    },
    initFarmLand () {
      // 获取地块信息
      // let orgid = global.helper.ls.get('user').loginorgid
      // this.$store.state['tenant.farmland'].params = () => ({ sort: { name: 1 }, filter: { tenant: { $oid: orgid } } })
      // this.$store.dispatch('init', { collection: 'tenant.farmland' }).then(
      //   () => {
      //     let areas = this.$store.state['tenant.farmland'].items
      //     let codename = 'province'
      //     let addresscode = '120000000000120000000000'
      //     this.statAreas(addresscode, areas, codename)
      //   }
      // )
    },
    statAreas (addresscode, areas, codename) {
      let fareas = areas.filter(item => {
        return item.address && item.address[codename] && item.address[codename]['$oid'] === addresscode
      })
      console.log(fareas)
      // 归并
      fareas.reduce((r, area) => {
        let curaddress = area['address']['city']['$oid']
        // console.log(curaddress)
        if (!r[curaddress]) r[curaddress] = 0
        r[curaddress] += Number(area['acreage'])
        // console.log(r)
        return r
      }, {})
    }
  },
  created () {
    this.director.push('100000')
    this.initFarmLand()
  },
  components: {
    CMapLayers,
    CMarkerCluster,
    CMarker,
    CPolygon,
    CCartogram,
    CCartogramFourColor,
    CcLabel,
    VCustomBar
  }
}
</script>

<style lang="scss">
.page-content {
  .cmap-container {
    width: 100%;
    height: calc(100vh - 264px);
    .leaflet-popup {
      .leaflet-popup-close-button {
        width: 29px;
        height: 29px;
        right: -12px;
        top: -12px;
        border-radius: 50%;
        z-index: 2;
        color: #333333;
        background: #fff;
        font-size: 25px;
        font-weight: 100;
        padding: 7px 0;
      }
      .leaflet-popup-content-wrapper {
        padding: 0;
        .leaflet-popup-content {
          margin: 0;
          .flex strong {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            display: block;
            margin-right: 10px;
          }
        }
      }
    }
  }
  .leaflet-bottom {
    bottom: 1.2rem;
  }
  .leaflet-right {
    right: 1rem;
  }
  .leaflet-icon {
    bottom: 28px;
    margin-right: 1rem;
    .leaflet-bar {
      a {
        width: 100%;
        .icon {
          font-size: 20px;
          color: #ffffff;
        }
      }
    }
  }
  .leaflet-report {
    top: 65px;
  }
  .leaflet-back {
    bottom: 100px;
  }
  .leaflet-back-right {
    right: 1rem;
  }
  .leaflet-right {
    right: 1rem;
  }
  .reportbar {
    top: 65px;
    margin-right: 1rem;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 5px;
    width: 330px;
    height: 65%;
    z-index: 200;
    .bars {
      padding-top: 10px;
      padding-left: 0px;
      overflow-x: hidden;
      width: 310px;
      height: 70%;
      overflow-y: hidden;
      &:hover {
        overflow-y: auto;
      }
      &::-webkit-scrollbar {
        background: transparent;
        width: 4px;
      }
      &::-webkit-scrollbar-thumb {
        background-color: #bdbdbd;
      }
    }
  }
  .leaflet-bar {
    border-radius: 0;
    background-color: rgba(0, 0, 0, 0.7);
    padding: 0px 5px;
    margin-right: -3px;
    a {
      background-color: rgba(0, 0, 0, 0.7);
      color: #ffffff;
      border-radius: 0;
      width: 20px;
      height: 30px;
      line-height: 28px;
    }
  }
  .landinfo {
    width: 300px;
    background-color: white;
    text-align: left;
    padding: 15px 15px;
    padding-top: 10px;
    white-space: nowrap;
    .landname {
      font-size: 13px;
      height: 40px;
    }
    .landdetail {
      font-size: 13px;
      padding-top: 5px;
      padding-bottom: 5px;
      max-width: 500px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
  .divider {
    background-color: gray;
  }
  .disward {
    color: #666666;
  }
}
</style>
