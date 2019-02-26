<template>
  <div>
    <vc-data-table v-bind="table"
                   :pagination.sync="table.pagination">
      <template slot="items"
                slot-scope="{ item, index }">
        <td v-for="h in table.headers"
            :key="h.text"
            :class="{'yellow lighten-3': isException(item, h.sensorType) }">{{ h.td(item) }}</td>
      </template>
      <template slot="pageText"
                slot-scope="props">
        当前 {{ fmtDate(pagination.startTime) }}
      </template>
    </vc-data-table>
  </div>
</template>

<script>
import mongo from '@vehiclecloud/mongo-rest-client'

import GrpcApi from '@vehiclecloud/grpc-websocket-client'

import DeviceAccessData from './mixins/device-access-data'

import Period from '@agrithings/common/util/period'

import CommonSchema from '@/schema/common'

const AccessDataCodes = CommonSchema.definitions.accessCode.items.enum.reduce((r, e, i) => {
  r[e] = CommonSchema.definitions.accessCode.items.enumNames[i]
  return r
}, {})

const commonTableHeader = { sortable: false, align: 'left' }

// const accessStatusCodeMap = {
//   1: [1, 2, 3],
//   2: [1001, 1002],
//   3: [2001, 2002, 2003]
// }

const SensorAlarmContent = {}

function pre (v) {
  return String(v).length === 1 ? '0' + v : v
}

export default {
  name: 'ag-device-access-data-table',
  mixins: [DeviceAccessData],
  created () {
    this.dataApi = new GrpcApi.DataApiV1()
  },
  destroyed () {
    this.dataApi.close()
  },
  props: {
    skipAccessStatus: Number,
    markerAccessStatus: [Number, Boolean],
    showAccessCodes: {
      type: Boolean,
      default: false
    },
    sensorTypesInTable: Array
  },
  computed: {
    needFetchErrorData () {
      return this.skipAccessStatus || this.markerAccessStatus || this.showAccessCodes
    }
  },
  data () {
    return {
      dataApi: null,
      table: {
        headers: [],
        items: [], // from access
        pagination: { page: 1, rowsPerPage: 1 },
        pageInited: false,
        totalItems: 0,
        loading: false,
        rowsPerPageItems: [1]
      },
      pagination: {
        startTime: this.startTime,
        stopTime: this.stopTime
      },
      errorData: null // from mongo
    }
  },
  watch: {
    'table.pagination' (v) {
      if (!v || !v.totalItems) return
      if (!this.table.pageInited) {
        this.table.pagination.page = this.table.totalItems
        this.table.pageInited = true
      }
      this.initPagination()
      this.query()
    }
  },
  methods: {
    refresh () {
      (this.sensorTypesInTable || this.sensorTypes).forEach(st => {
        if (st.data.minWarning) SensorAlarmContent[st.code] = { 257: st.data.minWarning }
        if (st.data.maxWarning) SensorAlarmContent[st.code] ? SensorAlarmContent[st.code][258] = st.data.minWarning : SensorAlarmContent[st.code] = { 258: st.data.minWarning }
      })
      let headers = [
        { ...commonTableHeader, text: '日期', td: data => this.fmtDate(data.dataTime) },
        { ...commonTableHeader, text: '时间', td: data => this.fmtTime(data.dataTime) }
      ];
      (this.sensorTypesInTable || this.sensorTypes).forEach(st => {
        headers.push({ ...commonTableHeader, text: st.name + (st.data.unit ? `(${st.data.unit})` : ''), sensorType: st, td: data => (data.dataInfo._data[st.code] && data.dataInfo._data[st.code].v !== undefined) ? Number((+data.dataInfo._data[st.code].v).toFixed(2)) : '-' })
      })
      if (this.showAccessCodes) {
        // headers.push({ ...commonTableHeader, text: '状态', td: data => (this.errorData && this.errorData[data.dataTime] && this.errorData[data.dataTime].code && this.errorData[data.dataTime].code.filter(c => this.skipAccessStatus ? !(accessStatusCodeMap[this.skipAccessStatus].includes(c) || c === 2) : true).map(c => AccessDataCodes[c]).join(', ')) || '正常' })
        headers.push({
          ...commonTableHeader,
          text: '状态',
          td: data => {
            let status = []
            let item = this.errorData && this.errorData[data.dataTime]
            if (item && item.data && item.code) {
              Object.keys(item.data).forEach(stc => {
                if (item.data[stc].as === this.skipAccessStatus) return
                if (typeof this.markerAccessStatus === 'boolean' ? item.data[stc].as === 1 : item.data[stc].as !== this.markerAccessStatus) return

                status.push((SensorAlarmContent[stc] && SensorAlarmContent[stc][item.data[stc].status]) || AccessDataCodes[item.data[stc].ac])
              })
            }
            if (status.length === 0) {
              status.push('正常')
            } else {
              let noRepeatStatus = []
              status.forEach(s => {
                if (!noRepeatStatus.some(nrs => s === nrs)) noRepeatStatus.push(s)
              })
              status = noRepeatStatus
            }
            return status.join(', ')
          }
        })
      }
      console.log(222222222)
      this.query()
      console.log(333333333)
      this.table.headers = headers
      this.table.totalItems = Math.ceil((this.stopTime - this.startTime) / Period[2])
      this.initPagination()
    },
    initPagination () {
      this.pagination.startTime = this.startTime + (this.table.pagination.page - 1) * Period[2]
      this.pagination.stopTime = this.startTime + this.table.pagination.page * Period[2]
    },
    query () {
      console.log(444444444)
      this.table.loading = true
      this.table.items = []

      this.dataApi.queryDataList({
        deviceType: 'SENSOR_SHENGU',
        deviceId: this.device.code,
        startTime: this.pagination.startTime,
        stopTime: this.pagination.stopTime
      }, ({ dataList }) => {
        if (!dataList) {
          this.table.loading = false
          return
        }
        console.log(dataList)
        this.fetchErrorData().then(() => {
          dataList.forEach(d => {
            d.dataInfo._data = Object.keys(d.dataInfo.numDataList).reduce((r, k) => {
              let a = k.split(':')
              if (a.length > 4) return r // skip origin and calc value

              r[a[1]] = { v: d.dataInfo.numDataList[k] }
              return r
            }, {})
          })
          if (!this.skipAccessStatus) {
            this.table.items = dataList
          } else {
            this.table.items = dataList.filter(d =>
              !(this.errorData && this.errorData[d.dataTime] && this.errorData[d.dataTime].status.length === 1 && this.errorData[d.dataTime].status[0] === this.skipAccessStatus) // skip only contain the skip status
            )
          }
          this.table.loading = false
        })
      }, () => {
        this.table.loading = false
      })
    },
    fetchErrorData () {
      return !this.needFetchErrorData ? Promise.resolve() : mongo.db().collection('tenant.device_access_data').find({
        filter: {
          device: this.device._id,
          time: { $gte: this.pagination.startTime, $lte: this.pagination.stopTime }
        }
      }).then(({ items }) => {
        this.errorData = items.reduce((r, item) => {
          r[item.time.$numberLong] = item
          return r
        }, {})
      })
    },
    isException (item, sensorType) {
      return sensorType &&
        this.markerAccessStatus &&
        this.errorData &&
        this.errorData[item.dataTime] &&
        (typeof this.markerAccessStatus === 'boolean' || this.errorData[item.dataTime].status.includes(this.markerAccessStatus)) &&
        (typeof this.markerAccessStatus === 'boolean' ? this.errorData[item.dataTime].data[sensorType.code].as > 1 : this.errorData[item.dataTime].data[sensorType.code].as === this.markerAccessStatus)
    },
    fmtDate (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}/${pre(d.getMonth() + 1)}/${pre(d.getDate())}`
    },
    fmtTime (time) {
      let d = new Date(+time)
      return `${pre(d.getHours())}:${pre(d.getMinutes())}`
    }
  }
}
</script>

<style lang="scss">
</style>
