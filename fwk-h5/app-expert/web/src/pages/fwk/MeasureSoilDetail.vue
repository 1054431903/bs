<template>
  <div class="_page">
    <div class="_loading" v-if="loading">
      <div class="_body">
        <img class="_image" src="loading_chart.gif"/>
        <div class="_text">数据加载中，请稍后...</div>
      </div>
    </div>
    <div v-else>
      <div class="top-separator"></div>
      <div class="top-container">
        <div class="top-container-left">
          <div class="indicator">检测信息</div>
        </div>
        <div class="top-container-right">
          <div class="report-table">
            <div class="report-table-row">
              <div class="report-table-row-icon icon-02">&nbsp;</div>
              <div class="report-table-row-label">检测样本编号</div>
              <div class="report-table-row-content">{{measureSoilDetail.sampleNumber}}&nbsp;</div>
            </div>
            <div class="report-table-row">
              <div class="report-table-row-icon icon-03">&nbsp;</div>
              <div class="report-table-row-label">采样日期</div>
              <div class="report-table-row-content">{{measureSoilDetail.sampleTimeString}}&nbsp;</div>
            </div>
            <div class="report-table-row">
              <div class="report-table-row-icon icon-04">&nbsp;</div>
              <div class="report-table-row-label">检测机构</div>
              <div class="report-table-row-content">{{measureSoilDetail.orgName}}&nbsp;</div>
            </div>
            <div class="report-table-row">
              <div class="report-table-row-icon icon-05">&nbsp;</div>
              <div class="report-table-row-label">土壤质地</div>
              <div class="report-table-row-content">{{measureSoilDetail.soilTextureName}}&nbsp;</div>
            </div>
          </div>
        </div>
      </div>
      <div class="routine-container">
        <div class="routine-table">
          <div class="routine-table-cell" v-for="(item, index) in measureSoilDetail.routineItems" :key="index">
            <div class="routine-table-cell-main">
              <div class="routine-table-cell-main-head" v-if="item.unit">{{item.unit}}</div>
              <div class="routine-table-cell-main-head" v-else>&nbsp;</div>
              <div class="routine-table-cell-main-body">{{item.valueString}}</div>
            </div>
            <div class="routine-table-cell-bottom">{{item.name}}</div>
          </div>
        </div>
      </div>
      <div class="panel-separator">&nbsp;</div>
      <div class="extension-container">
        <div class="extension-table">
          <div class="extension-table-cell" v-for="(item, index) in measureSoilDetail.extensionItems" :key="index">
            <div class="extension-table-cell-head">
              <span class="extension-table-cell-head-title">{{item.valueString}}</span>
              <span class="extension-table-cell-head-subtitle" v-if="item.unit">{{item.unit}}</span>
              <span class="extension-table-cell-head-subtitle" v-else>--</span>
            </div>
            <div class="extension-table-cell-body">
              <span class="extension-table-cell-body-label">{{item.symbol}}</span>
            </div>
            <div class="extension-table-cell-bottom">{{item.name}}</div>
          </div>
        </div>
      </div>
      <div class="panel-separator">&nbsp;</div>
      <div class="attachments-container">
        <div class="attachments-table">
          <div class="attachments-table-row">
            <div class="attachments-table-cell view-table-cell-label">
              <span class="attachments-table-cell-label-span">附件</span>
            </div>
            <div class="attachments-table-cell attachments-table-cell-content">
              <a style="margin-right: 10px;color: #02ad58;text-decoration:none;word-wrap:break-word;"
                 v-for="(attachment,index) in measureSoilDetail.attachments"
                 :key="index"
                 href="javascript:void(0);"
                 @click="download(attachment.url)">
                {{attachment.name?attachment.name:'--'}}
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from './share'
import Moment from 'moment'

export default {
  name: 'fwk-measure_soil-detail',
  mixins: [Share],
  props: {

  },
  data () {
    return {
      loading: false,
      measureSoilDetail: {

      }
    }
  },
  created () {

  },
  mounted () {
    this.load()
  },
  methods: {
    async load () {
      try {
        this.loading = true
        let result = await this.queryMeasureSoilDetail(this.$route.params._id)
        if (!result) return
        this.measureSoilDetail = result
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    async queryMeasureSoilDetail (id) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        id: id
      }
      let response = await this.$http.get('/fwk-service-parcel/parcelMeasureSoil/queryMeasureSoil', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (!(response.data.data && (response.data.data instanceof Array))) {
        throw new Error(JSON.stringify(response.data))
      }
      return this.parseMeasureSoilDetail(response.data.data[0])
    },
    parseMeasureSoilDetail (data) {
      console.debug(data)
      let result = {}
      if (data instanceof Object) {
        result.id = data.id
        result.fwkParcelId = data.parcelid
        result.sampleNumber = data.samplnum || '--'
        result.orgName = data.orgname || '--'
        result.soilTextureName = data.soiltexturename || '--'
        result.detectionTime = new Date(data.meastime.replace(' ', 'T') + '.000+08:00')
        result.detectionTimeString = Moment(result.detectionTime).format('YYYY-MM-DD')
        // 暂时显示同一个时间
        result.sampleTime = new Date(data.meastime.replace(' ', 'T') + '.000+08:00')
        result.sampleTimeString = Moment(result.sampleTime).format('YYYY-MM-DD')
        // 常规5项
        result.routineItems = []
        result.routineItems.push({
          name: 'PH值',
          value: this.parseValue(data.phvalue),
          valueString: this.parseValueString(data.phvalue),
          unit: null
        })
        result.routineItems.push({
          name: '有机质',
          value: this.parseValue(data.matter),
          valueString: this.parseValueString(data.matter),
          unit: 'g/kg'
        })
        result.routineItems.push({
          name: '有效磷',
          value: this.parseValue(data.phosphorus),
          valueString: this.parseValueString(data.phosphorus),
          unit: 'mg/kg'
        })
        result.routineItems.push({
          name: '速效钾',
          value: this.parseValue(data.potassium),
          valueString: this.parseValueString(data.potassium),
          unit: 'mg/kg'
        })
        result.routineItems.push({
          name: '碱解氮',
          value: this.parseValue(data.nitrogen),
          valueString: this.parseValueString(data.nitrogen),
          unit: 'mg/kg'
        })
        // 扩展9项
        result.extensionItems = []
        result.extensionItems.push({
          name: '有效Zn',
          value: this.parseValue(data.validzn),
          valueString: this.parseValueString(data.validzn),
          unit: 'mg/kg',
          symbol: 'Zn'
        })
        result.extensionItems.push({
          name: '有效Cu',
          value: this.parseValue(data.validcu),
          valueString: this.parseValueString(data.validcu),
          unit: 'mg/kg',
          symbol: 'Cu'
        })
        result.extensionItems.push({
          name: '有效Mn',
          value: this.parseValue(data.validmn),
          valueString: this.parseValueString(data.validmn),
          unit: 'mg/kg',
          symbol: 'Mn'
        })
        result.extensionItems.push({
          name: '有效Mo',
          value: this.parseValue(data.validmo),
          valueString: this.parseValueString(data.validmo),
          unit: 'mg/kg',
          symbol: 'Mn'
        })
        result.extensionItems.push({
          name: '有效Fe',
          value: this.parseValue(data.validfe),
          valueString: this.parseValueString(data.validfe),
          unit: 'mg/kg',
          symbol: 'Fe'
        })
        result.extensionItems.push({
          name: '有效S',
          value: this.parseValue(data.valids),
          valueString: this.parseValueString(data.valids),
          unit: 'mg/kg',
          symbol: 'S'
        })
        result.extensionItems.push({
          name: '水溶Ca',
          value: this.parseValue(data.solubilityca),
          valueString: this.parseValueString(data.solubilityca),
          unit: 'g/kg',
          symbol: 'Ca'
        })
        result.extensionItems.push({
          name: '水溶Mg',
          value: this.parseValue(data.solubilitymg),
          valueString: this.parseValueString(data.solubilitymg),
          unit: 'g/kg',
          symbol: 'Mg'
        })
        result.extensionItems.push({
          name: '水溶性盐',
          value: this.parseValue(data.salt),
          valueString: this.parseValueString(data.salt),
          unit: 'g/kg',
          symbol: 'Salt'
        })
        // 附件
        result.attachments = []
        result.attachments.push(...(data.attachments || []).map(item => {
          return {
            id: item.id,
            name: item.resourcename,
            url: item.resourcepath,
            isDeleted: item.delstate === 1 ? 0 : 1
          }
        }).filter(item => !item.isDeleted))
      }
      console.debug(result)
      return result
    },
    parseValue (value) {
      if (typeof value === 'number') {
        return value
      }
      if (typeof value === 'string') {
        let val = value.trim()
        if (val.length > 0) {
          return Number(val)
        }
      }
      return NaN
    },
    parseValueString (value) {
      let val = this.parseValue(value)
      if (Number.isNaN(val)) {
        return '--'
      } else {
        let valString = val.toString()
        if (valString.length > 6) {
          return '......'
        } else {
          return valString
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .icon-02 {
    background: url(../../assets/fwk/02-img.png) no-repeat center;
  }
  .icon-03 {
    background: url(../../assets/fwk/03-img.png) no-repeat center;
  }
  .icon-04 {
    background: url(../../assets/fwk/04-img.png) no-repeat center;
  }
  .icon-05 {
    background: url(../../assets/fwk/05-img.png) no-repeat center;
  }
  ._loading {
    flex: auto;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
    > ._body {
      flex: auto;
      display: flex;
      flex-flow: column nowrap;
      justify-content: center;
      align-content: center;
      align-items: center;
      > ._image {
        width: 20px;height: 20px
      }
      > .text {
      }
    }
  }
  ._page {
    height: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
  }
  .panel-separator {
    background-color: rgb(246, 245, 246);
    height: 10px;
  }
  .top-separator {
    height: 1px;
    background-color: rgba(255, 255, 255, 0.4);
  }
  .top-container {
    margin: 0;
    padding: 15px;
    background: linear-gradient(150deg, #8ce3e9, #46b9ce);
    display: flex;
    flex-flow: row nowrap;
  }
  .top-container-left {
    flex: none;
  }
  .top-container-right {
    flex: auto;
    display: flex;
    flex-flow: column wrap;
    justify-content: center;
  }
  .indicator {
    float: right;
    width: 25px;
    margin: 0px 25px 0px 10px;
    padding: 30px 0px 10px 0px;
    border-radius: 30px;
    background: #fcc85b url(../../assets/fwk/01-img.png) no-repeat center 10px;
    background-size: 17px 17px;
    text-align: center;
    font-size: 15px;
    color: #ffffff;
  }
  .report-table {
    padding: 0px 0px;
    font-size: 13px;
    color: #ffffff;
  }
  .report-table-row {
    display: flex;
    align-items: flex-start;
  }
  .report-table-row-icon {
    flex: none;
    padding: 5px 0px 5px 0px;
    width: 13px;
    background-size: 13px 13px;
  }
  .report-table-row-label {
    flex: none;
    padding: 5px 10px 5px 10px;
    width: 100px;
  }
  .report-table-row-content {
    flex: auto;
    padding: 5px 0px 5px 0px;
  }
  .routine-container {
    background: #ffffff url(../../assets/fwk/07-img.png) repeat-x bottom left;
    background-size: 375px 85px;
  }
  .routine-table {
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-content: center;
    align-items: center;
    padding: 0 0 15px 0;
  }
  .routine-table-cell {
    flex: none;
    width: 59px;
    margin: 15px 15px 0px 15px;
    text-align: center;
  }
  @media screen and (device-width: 320px) {
    .routine-table-cell {
      margin: 15px 11px 0px 10px;
    }
  }
  @media screen and (device-width: 360px) {
    .routine-table-cell {
      margin: 15px 16px 0px 15px;
    }
  }
  @media screen and (device-width: 375px) {
    .routine-table-cell {
      margin: 15px 17px 0px 17px;
    }
  }
  @media screen and (device-width: 411px) {
    .routine-table-cell {
      margin: 15px 12px 0px 11px;
    }
  }
  @media screen and (device-width: 414px) {
    .routine-table-cell {
      margin: 15px 12px 0px 11px;
    }
  }
  .routine-table-cell-main {
    width: 59px;
    height: 59px;
    background: url(../../assets/fwk/06-img.png) no-repeat center;
    background-size: 59px 59px;

  }
  .routine-table-cell-main-head {
    padding: 5px 0px 5px 0px;
    font-size: 10px;
    color: #acacac;
  }
  .routine-table-cell-main-body {
    font-size: 12px;
    color: #47bacf;

  }
  .routine-table-cell-bottom {
    font-size: 12px;
    color: #000000;
  }
  .extension-container {
    background-color: #ffffff;
    padding: 0px 10px 15px 10px;
  }
  .extension-table {
    display: flex;
    flex-flow: row wrap;
    justify-content: flex-start;
    align-content: center;
    align-items: center;
  }
  .extension-table-cell {
    flex: none;
    width: 85px;
    margin: 20px 0px 0px 0px;
    text-align: center;
  }
  @media screen and (device-width: 320px) {
    .extension-table-cell {
      margin: 20px 8px 0px 7px;
    }
  }
  @media screen and (device-width: 360px) {
    .extension-table-cell {
      margin: 20px 0px 0px 0px;
    }
  }
  @media screen and (device-width: 375px) {
    .extension-table-cell {
      margin: 20px 2px 0px 1px;
    }
  }
  @media screen and (device-width: 411px) {
    .extension-table-cell {
      margin: 20px 6px 0px 6px;
    }
  }
  @media screen and (device-width: 414px) {
    .extension-table-cell {
      margin: 20px 7px 0px 6px;
    }
  }
  .extension-table-cell-head-title {
    display: inline-block;
    margin: 0px;
    padding: 0px;
    font-size: 13px;
    color: #000000;
  }
  .extension-table-cell-head-subtitle {
    display: inline-block;
    margin: 0px;
    padding: 0px;
    font-size: 10px;
    color: rgb(172, 172, 172);
  }
  .extension-table-cell-body-label {
    display: inline-block;
    width: 45px;
    background-color: #7fdbe4;
    font-size: 16px;
    color: #ffffff;
    border-radius: 5px;
  }
  .extension-table-cell-bottom {
    font-size: 12px;
    color: rgb(62, 61, 61);
  }
  .attachments-container {
    background-color: #ffffff;
    padding: 0px 20px;
  }
  .attachments-table {
    background: rgb(255, 255, 255);
    padding: 15px 0 15px 0;
  }
  .attachments-table-row {
    padding: 5px 0;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: center;
  }
  .attachments-table-cell {
    margin: 0;
    padding: 0;
  }
  .attachments-table-cell-label-span {
    flex: none;
    width: 80px;
    text-align: right;
    padding-right: 10px;
    font-size: 13px;
    color: #999999;
  }
  .attachments-table-cell-content {
    flex: auto;
    width: 70%;
    font-size: 14px;
    color: #333333;
  }
</style>
