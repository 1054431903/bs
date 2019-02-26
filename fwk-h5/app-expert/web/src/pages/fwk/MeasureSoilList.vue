<template>
  <div class="main-container">
    <div class="scroller-container">
      <scroller class="scroller" ref="scroller" :on-refresh="refresh" :on-infinite="infinite" :noDataText="noDataText">
        <div class="separator">&nbsp;</div>
        <v-card class="list-item" v-for="(measureSoil,index) in measureSoilList" :key="index"
                :to="{name: 'fwk-measure_soil-detail', params: { _id: measureSoil.id}}">
          <div class="list-item-content">
            <div class="list-item-title">
              <span class="list-item-title-left">{{measureSoil.sampleNumber}}</span>
              <span>&nbsp;</span>
              <span class="list-item-title-right">{{measureSoil.orgName}}</span>
            </div>
            <div class="list-item-sub-title">
              <span class="list-item-sub-title-left">{{measureSoil.soilTextureName}}</span>
              <span>&nbsp;</span>
              <span class="list-item-sub-title-right">{{measureSoil.detectionTimeString}}</span>
            </div>
          </div>
          <div class="list-item-separator">&nbsp;</div>
        </v-card>
      </scroller>
    </div>
  </div>
</template>

<script>
import Share from './share'
import Moment from 'moment'
const NO_DATA_TEXT = '没有更多数据'
const LOAD_SCENE_INIT = 0
const LOAD_SCENE_GENERAL = 1
const LOAD_SCENE_FINISH = 2

export default {
  name: 'fwk-measure_soil',
  mixins: [Share],
  data () {
    return {
      noDataText: '',
      pageIndexStart: 1,
      pageSizeDefault: 10,
      measureSoilList: [],
      paging: {
        total: 0,
        pageSize: 10,
        totalPage: 0,
        pageIndex: 0
      }
    }
  },
  created () {},
  mounted () {},
  computed: {
    loadScene () {
      if (this.paging.pageIndex < this.pageIndexStart) {
        return LOAD_SCENE_INIT
      } else if (this.hasMore) {
        return LOAD_SCENE_GENERAL
      } else {
        return LOAD_SCENE_FINISH
      }
    },
    hasMore () {
      return this.measureSoilList && this.measureSoilList.length > 0 && this.measureSoilList.length < this.paging.total
    }
  },
  methods: {
    show () {
      this.noDataText = NO_DATA_TEXT
    },
    hide () {
      this.noDataText = ''
    },
    async refresh (done) {
      try {
        this.hide()
        let result = await this.queryMeasureSoilList()
        if (!result) throw new Error()
        this.paging = result.paging
        this.measureSoilList = result.data
        this.hasMore ? done(false) : done(true)
      } catch (e) {
        console.debug(e)
        this.showMessageError('加载数据失败')
        done(true)
      } finally {
        this.show()
      }
    },
    async infinite (done) {
      switch (this.loadScene) {
        case LOAD_SCENE_INIT: {
          this.refresh(done)
          break
        }
        case LOAD_SCENE_GENERAL: {
          try {
            this.hide()
            let result = await this.queryMeasureSoilListNext()
            if (!result) throw new Error()
            this.paging = result.paging
            if (result.data.length > 0) {
              this.measureSoilList.push(...result.data)
            }
            this.hasMore ? done(false) : done(true)
          } catch (e) {
            console.debug(e)
            this.showMessageError('加载数据失败')
            done(true)
          } finally {
            this.show()
          }
          break
        }
        case LOAD_SCENE_FINISH: {
          done(true)
          break
        }
      }
    },
    async queryMeasureSoilList () {
      return this.queryMeasureSoilListPaging(undefined, this.pageIndexStart, this.pageSizeDefault)
    },
    async queryMeasureSoilListNext () {
      return this.queryMeasureSoilListPaging(undefined, this.paging.pageIndex + 1, this.pageSizeDefault)
    },
    async queryMeasureSoilListPaging (sampleNumber, pageIndex, pageSize) {
      let staff = await this.queryStaff()
      if (!staff) return
      let tenant = await this.queryTenant()
      if (!tenant) return
      let fwkUserId = await this.queryFwkUserId()
      if (!fwkUserId) return
      let fwkParcelId = await this.queryFwkParcelId()
      if (!fwkParcelId) return
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        delstate: 1,
        parcelid: fwkParcelId,
        orderBySQL: 'meastime',
        ordertype: 'desc',
        currentPage: pageIndex,
        pageSize: pageSize
      }
      if (sampleNumber && sampleNumber.length > 0) {
        params.sampleNumber = sampleNumber
      }
      let response = await this.$http.get('fwk-service-parcel/parcelMeasureSoil/queryMeasureSoil', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      return this.parseMeasureSoilListPaging(response.data)
    },
    parseMeasureSoilListPaging (data) {
      let result = {
        paging: {
          total: data.page.total,
          pageSize: data.page.pageSize,
          pageTotal: data.page.totalPage,
          pageIndex: data.page.currentPage
        },
        data: []
      }
      if (data.data && (data.data instanceof Array)) {
        result.data = data.data.map(item => {
          let measureSoil = {}
          measureSoil.id = item.id
          measureSoil.fwkParcelId = item.parcelid
          measureSoil.sampleNumber = item.samplnum || ''
          measureSoil.orgName = item.orgname || ''
          measureSoil.soilTextureName = item.soiltexturename || ''
          measureSoil.detectionTime = new Date(item.meastime.replace(' ', 'T') + '.000+08:00')
          measureSoil.detectionTimeString = Moment(measureSoil.detectionTime).format('YYYY-MM-DD')
          return measureSoil
        })
      }
      return result
    }
  }
}
</script>

<style scoped>
  .separator {
    height: 10px;
    background-color: rgb(245, 246, 245);
  }
  .scroller {
    padding-top: 44px;
  }
  .list-item {
    border-radius:0 !important;
    box-shadow: none !important;
  }
  .list-item-content {
    padding: 5px 15px 10px 15px;
  }
  .list-item-title {
    word-wrap: break-word;
    color: #000000;
    font-size: 15px;
    line-height: 26px;
    overflow: auto;
  }
  .list-item-sub-title {
    word-wrap: break-word;
    color: #acacac;
    font-size: 13px;
    line-height: 24px;
    overflow: auto;
    margin-top: 5px;
  }
  .list-item-title-right {
    float: right;
    text-align: right;
  }
  .list-item-sub-title-right {
    float: right;
    text-align: right;
  }
  .list-item-separator {
     height: 1px;
     background-color: rgb(246, 245, 246);
  }
</style>
