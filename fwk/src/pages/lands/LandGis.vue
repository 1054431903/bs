<template>
  <div class="card--gis"
       style="margin-top:5px;">
    <div class="table-search"
         style="width:100%;float:right;margin-buttom:5px;">
      <div style="width: 400px;flex-shrink: 0;float:right;">
        <ul class="status-legend pr-4">
          <li class="status-3"
              :class="{ 'legend-active': devices.accessState && devices.accessState.status === 3 }"
              @click="devicesUpdateState(3)">故障</li>
          <li class="status-2"
              :class="{ 'legend-active': devices.accessState && devices.accessState.status === 2 }"
              @click="devicesUpdateState(2)">告警</li>
          <li class="status-4"
              :class="{ 'legend-active': devices.accessState && devices.accessState.code === 2003 }"
              @click="devicesUpdateState(null, 2003)">离线</li>
          <li class="status-1"
              :class="{ 'legend-active': devices.accessState && devices.accessState.status === 1 }"
              @click="devicesUpdateState(1)">正常</li>
        </ul>
      </div>
    </div>
    <c-map-layers v-bind="map.layers"
                  ref="mapLayers">
      <template>
        <c-polygon v-if="devices.farmland.gis"
                   :latlngs="devices.farmland.gis"
                   :stroke="true"
                   :fill="true"
                   fill-color="#229af0"
                   :fill-opacity="0.3"
                   :popup-event="null">
        </c-polygon>
        <c-marker-cluster>
          <c-marker v-for="device in devices.items"
                    :key="device._id.$oid"
                    v-if="device.lat && device.lng"
                    :title="device.name"
                    :latlng="{ lat: device.lat, lng: device.lng }"
                    :icon="{image: devicesMarkerIconOf(device), iconSize: [30, 'auto'], iconAnchor: [15, 45]}"
                    :popup-props="{offset: [0, -38]}"
                    @c-popupopen="devicesPopupopen(device)"
                    @c-popupclose="devicesPopupclose(device)"
                    :ref="devicesMarkerRefOf(device)">
            <ag-device-popup :device="device"></ag-device-popup>
            <div slot="icon"
                 c-slot
                 v-if="devicesDeviceTypeOf(device).gisIcon === 1"
                 class="div-icon"
                 :class="'div-icon-1-' + devicesIconStatusOf(device)">{{ devicesAccessDataOf(device, '3') }}</div>
          </c-marker>
        </c-marker-cluster>
        <ag-monitor-gis-devices-search :farmland="devices.farmland"
                                       v-model="devices.items"
                                       :access-state="devices.accessState"
                                       :current-device="devices.current"
                                       @update:currentDevice="devicesUpdateCurrent"
                                       ref="devicesSearch"></ag-monitor-gis-devices-search>
      </template>
      <div class="leaflet-bottom leaflet-right leaflet-icon leaflet-refresh">
        <div class="leaflet-bar leaflet-control">
          <a title="刷新"
             @click="refresh">
            <v-icon>autorenew</v-icon>
          </a>
        </div>
      </div>
    </c-map-layers>
  </div>
</template>
<script>
import CMapLayers from '@agrithings/cmap/CMapLayers'
import CMarkerCluster from '@agrithings/cmap/overlay/CMarkerCluster'
import CMarker from '@agrithings/cmap/overlay/CMarker'
import CPolygon from '@agrithings/cmap/overlay/CPolygon'
import { types } from '@agrithings/cmap/util'
import AgMonitorAgrithingsPopup from '@/pages/monitor/agrithings/Popup'
import AgBloomskyRainPopup from '@/pages/monitor/bloomsky/RainPopup'
import AgMonitorBloomskyPopup from '@/pages/monitor/bloomsky/Popup'
import AgMonitorPtzPopup from '@/pages/monitor/ptz/Popup'
import AgMonitorPowerViewPopup from '@/pages/monitor/powerview/Popup'
import AgMonitorGisDevicesSearch from '@/pages/monitor/components/DevicesSearch'
import { accessDataOf } from '@/pages/monitor/util/util'

const Devices = {
  created () {
    this.$store.dispatch('init', { collection: 'base.device.type' })
    this.devices.farmland = this.parcel
  },
  mounted () {
    this.showDevices()
  },
  watch: {
    'map.type' () {
      if (this.devices.current === null) return
      this.devices.current = null
    }
  },
  data () {
    return {
      curr_farmland: {},
      devices: {
        farmland: null,
        items: [],
        accessState: null,
        current: null,
        popupopened: false
      }
    }
  },
  methods: {
    showDevices () {
      this.devices.farmland = this.parcel
      this.devicesUpdateBounds()
      this.devices.accessState = { status: null, code: null }
      this.map.type = 'devices'
    },
    devicesUpdateBounds () {
      if (this.devices.farmland.gis) {
        this.map.layers.bounds = [
          [
            Math.min.apply(null, this.devices.farmland.gis.map(p => p.lat)),
            Math.min.apply(null, this.devices.farmland.gis.map(p => p.lng))
          ], [
            Math.max.apply(null, this.devices.farmland.gis.map(p => p.lat)),
            Math.max.apply(null, this.devices.farmland.gis.map(p => p.lng))
          ]
        ]
      }
    },
    devicesDeviceTypeOf (device) {
      return this.$store.state['base.device.type'].items.find(t => t._id.$oid === device.type.$oid)
    },
    devicesIconStatusOf (device) {
      if (!device.accessState) return 1
      if (device.accessState.code && device.accessState.code.includes(2003)) return 4 // offline
      return (device.accessState.status && device.accessState.status.length && device.accessState.status[0]) || 1
    },
    devicesMarkerIconOf (device) {
      let type = this.devicesDeviceTypeOf(device)
      let status = this.devicesIconStatusOf(device)
      return `/static/gis/icon/${type.gisIcon}/${status}.png`
    },
    devicesUpdateState (status, code = null) {
      this.devices.accessState = {
        status: this.devices.accessState.status === status ? null : status,
        code: this.devices.accessState.code === code ? null : code
      }
    },
    devicesMarkerRefOf (device) {
      return 'marker-' + device._id.$oid
    },
    devicesUpdateCurrent (v) {
      let ref = this.$refs[this.devicesMarkerRefOf(v)]
      ref.length && ref[0].popupOpen()
    },
    devicesPopupopen (device) {
      this.devices.popupopened = true
      this.devices.current = device
    },
    devicesPopupclose (device) {
      if (this.devices.current === device) {
        this.devices.popupopened = false
        this.devices.current = null
      }
    },
    devicesAccessDataOf: accessDataOf
  }
}

const providerPopup = {
  1: AgMonitorAgrithingsPopup,
  2: AgMonitorBloomskyPopup,
  3: AgMonitorPtzPopup,
  4: AgMonitorPowerViewPopup,
  5: AgBloomskyRainPopup
}

const AgDevicePopup = {
  name: 'ag-device-popup',
  functional: true,
  props: {
    device: {
      type: Object,
      required: true
    }
  },
  render (h, ctx) {
    let device = ctx.props.device
    let parent = ctx.parent
    if (!device) return null
    let type = parent.devicesDeviceTypeOf(device)
    if (!type) return null
    let provider = type.provider
    provider === 2 ? type.code === 105 ? provider = 5 : '' : ''
    return h(providerPopup[provider], { props: ctx.props })
  }
}

export default {
  name: 'land-gis',
  props: {
    parcel: {
      type: Object,
      required: true
    }
  },
  mixins: [Devices],
  data () {
    return {
      map: {
        layers: {
          mapType: types.YCMIXMAP,
          zoomControl: { position: 'bottomright' },
          center: { lat: 36.88, lng: 110.18 },
          zoom: 6,
          bounds: null
        }
      }
    }
  },
  methods: {
    refresh () {
      this.$refs.devicesSearch.search()
      this.devicesUpdateBounds()
    }
  },
  components: {
    CMapLayers,
    CMarkerCluster,
    CMarker,
    CPolygon,
    AgMonitorAgrithingsPopup,
    AgMonitorBloomskyPopup,
    AgBloomskyRainPopup,
    AgMonitorPtzPopup,
    AgDevicePopup,
    AgMonitorGisDevicesSearch,
    AgMonitorPowerViewPopup
  }
}
</script>

<style lang="scss">
.status-legend {
  list-style: none;
  display: flex;
  li {
    padding: 3px 10px;
    border-radius: 2px;
    flex: 1;
    cursor: pointer;
    &::before {
      content: ' ';
      width: 10px;
      height: 10px;
      display: inline-block;
      margin-right: 0.5rem;
      border-radius: 50%;
    }
    &.status-1 {
      &::before {
        background: #2298f2;
      }
    }
    &.status-2 {
      &::before {
        background: #faba52;
      }
    }
    &.status-3 {
      &::before {
        background: #f04643;
      }
    }
    &.status-4 {
      &::before {
        background: #acb1bf;
      }
    }
    &.legend-active {
      background-color: #f0f0f0;
    }
  }
}
.card--gis {
  width: 100%;
  padding: 0rem 1rem 1rem;
  .cmap-container {
    width: 100%;
    height: calc(100vh - 164px);
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
  .leaflet-bar {
    margin-left: 18px;
    border-radius: 0;
    background-color: #575859;
    padding: 0 5px;
    a {
      background-color: rgb(87, 88, 89);
      color: #ffffff;
      border-radius: 0;
      width: 20px;
      height: 30px;
      line-height: 28px;
    }
  }
  .leaflet-icon {
    bottom: 28px;
    margin-right: 1rem;
    .leaflet-bar {
      padding: 0 5px;
      a {
        width: 100%;
        .icon {
          font-size: 20px;
          color: #ffffff;
        }
      }
    }
  }
  .leaflet-refresh {
    margin-right: 30px;
    bottom: 98px;
  }
}
.leaflet-div-icon {
  background-color: inherit;
  border: none;
  .div-icon {
    height: 45px;
    background-size: 30px 40px;
    line-height: 28px;
    font-size: 11px;
    text-align: center;
    color: white;
    &.div-icon-1-1 {
      background-image: url(/static/gis/icon/1/1.png);
    }
    &.div-icon-1-2 {
      background-image: url(/static/gis/icon/1/2.png);
    }
    &.div-icon-1-3 {
      background-image: url(/static/gis/icon/1/3.png);
    }
    &.div-icon-1-4 {
      background-image: url(/static/gis/icon/1/4.png);
    }
  }
}
</style>
