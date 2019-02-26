<template>
  <div class="_page">
    <div class="_loading" v-if="loading">
      <div class="_body">
        <img class="_image" src="loading_chart.gif"/>
        <div class="_text">数据加载中，请稍后...</div>
      </div>
    </div>
    <div v-else>
      <div class="panel-separator">&nbsp;</div>
      <div class="panel">
        <div class="view-table">
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">品类</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.categoryName?plantingDetails.categoryName:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">品种</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.varietyName?plantingDetails.varietyName:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">播/栽种时间</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.plantBeginTimeString?plantingDetails.plantBeginTimeString:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">预计生长周期</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.planGrowthcycleString?plantingDetails.planGrowthcycleString:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">种植面积</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.plantAreaString?plantingDetails.plantAreaString:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">植株数量</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.plantCountString?plantingDetails.plantCountString:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">株龄</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.plantAgeString?plantingDetails.plantAgeString:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">种植批次</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.plantBatch?plantingDetails.plantBatch:"--"}}&nbsp;
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">相关附件</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              <a style="margin-right: 10px;color: #02ad58;text-decoration:none;"
                 v-for="(attachment,index) in plantingDetails.attachments"
                 :key="index" href="javascript:void(0);"
                 :download="attachment.name"
                 @click="download(attachment.url)">
                {{attachment.name?attachment.name:'--'}}
              </a>
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">备注</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{plantingDetails.comment}}&nbsp;
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from './share.js'
export default {
  name: 'planting-details',
  mixins: [Share],
  data () {
    return {
      loading: false,
      plantingDetails: {}
    }
  },
  mounted () {
    // this.setIcon()
    this.load()
  },
  methods: {
    setIcon () {
      this.$store.commit('set', { name: 'app', k: 'acts', v: [{ icon: 'edit', fn: this.showFarmlandDetailsEdit }] })
      this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    },
    showFarmlandDetailsEdit () {
      this.$router.push({name: 'farmland-details_edit', query: this.$router.query})
    },
    async load () {
      try {
        this.loading = true
        let result = await this.queryParcelPlantDetails(this.$route.params._id)
        if (!result) return
        this.plantingDetails = result
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    async queryParcelPlantDetails (id) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        delstate: 1,
        parcelid: fwkParcelId,
        id: id
      }
      let response = await this.$http.get('fwk-service-parcel/parcelPlant/queryParcelPlant', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      return this.parseParcelPlantDetails(response.data.data[0])
    }
  }
}
</script>

<style lang="scss" scoped>
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
    height: 10px;
    background-color: rgb(246, 245, 246);
  }
  .panel {
    padding: 10px;
  }
  .view-table {
  }
  .view-table-row {
    padding: 5px 0;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: stretch;
  }
  .view-table-cell {
    margin: 0;
    padding: 0;
  }
  .view-table-cell-label {
    flex: none;
    width: 100px;
    text-align: right;
    padding-right: 10px;
    font-size: 13px;
    color: #999999;
  }
  .view-table-cell-content {
    flex: auto;
    font-size: 14px;
    color: #333333;
    word-break: break-all;
  }
</style>
