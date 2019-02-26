<template>
  <div class="_page">
    <div class="_loading"
         v-if="loading">
      <div class="_body">
        <img class="_image"
             src="loading_chart.gif" />
        <div class="_text">数据加载中，请稍后...</div>
      </div>
    </div>
    <div v-else>
      <div class="panel-separator">&nbsp;</div>
      <div class="panel">
        <div class="view-table">
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块名称</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.name?farmlandDetails.name:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块类型</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.typeName?farmlandDetails.typeName:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">占地面积</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.occupiedAreaString?farmlandDetails.occupiedAreaString:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块位置</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.addressName?farmlandDetails.addressName:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">详细地址</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.addressDetails?farmlandDetails.addressDetails:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块编号</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.code?farmlandDetails.code:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块海拔</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.altitudeString?farmlandDetails.altitudeString:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块经纬度</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.coordinateString?farmlandDetails.coordinateString:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块负责人</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              <span v-for="(principal,index) in farmlandDetails.principals"
                    :key="index">
                {{principal.name}}{{principal.phone?('(' + principal.phone + ")"):""}}&nbsp;
              </span>
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块从属</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.belongName?farmlandDetails.belongName:'--'}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块权属人</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.ownerName?farmlandDetails.ownerName:'--'}}{{farmlandDetails.ownerPhone?('('+farmlandDetails.ownerPhone+')'):""}}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">地块保险</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.insuranceName?farmlandDetails.insuranceName:'--' }}
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">相关附件</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              <a style="margin-right: 10px;color: #02ad58;text-decoration:none;"
                 v-for="(attachment,index) in farmlandDetails.attachments"
                 :key="index"
                 href="javascript:void(0);"
                 :download="attachment.name"
                 @click="download(attachment.url)">
                {{attachment.name?attachment.name:'--'}}
              </a>
            </div>
          </div>
          <div class="view-table-row">
            <div class="view-table-cell view-table-cell-label">
              <span class="view-table-cell-label-span">其它说明</span>
            </div>
            <div class="view-table-cell view-table-cell-content">
              {{farmlandDetails.comment}}
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
  name: 'farmland-details',
  mixins: [Share],
  data () {
    return {
      loading: false
    }
  },
  mounted () {
    this.setIcon()
    this.load()
  },
  methods: {
    setIcon () {
      this.$store.commit('set', { name: 'app', k: 'acts', v: [{ src: 'farmland/editor.png', w: 20, h: 20, fn: this.showFarmlandDetailsEdit }] })
      this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    },
    showFarmlandDetailsEdit () {
      this.$router.push({ name: 'farmland-details_edit', query: this.$route.query })
    },
    async load () {
      try {
        this.loading = true
        let result = await this.queryParcel()
        if (!result) return
        this.farmlandDetails = result
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
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
      width: 20px;
      height: 20px;
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
.view-table-row {
  padding: 5px 0;
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-start;
  align-items: flex-start;
  align-content: center;
}
.view-table-cell {
  margin: 0;
  padding: 0;
}
.view-table-cell-label {
  flex: none;
  width: 80px;
  text-align: right;
  padding-right: 10px;
  font-size: 13px;
  color: #999999;
}
.view-table-cell-content {
  flex: auto;
  width: 70%;
  font-size: 14px;
  color: #333333;
  word-break: break-all;
}
</style>
