<template>
  <div class="main-container">
    <scroller class="scroller" ref="scroller" :snapping="snapping" :on-refresh="refresh" :on-infinite="infinite" :noDataText="noDataText">
      <div class="panel-separator">&nbsp;</div>
      <div class="panel">
        <div class="view-list">
          <div class="view-list-item" v-for="(item,index) in plantingList" :key="index" @click="showPlantingDetails(item.id)">
            <div class="view-list-item-body">
              <div class="view-list-item-body-title">{{item.categoryName}}{{item.varietyName?`，${item.varietyName}`:''}}</div>
              <div class="view-list-item-body-count">{{item.plantCountString}}</div>
              <div class="view-list-item-body-time">{{item.plantBeginTimeString}}</div>
              <div class="view-list-item-body-cycle">{{item.planGrowthcycleString}}</div>
            </div>
            <div class="view-list-item-separator">&nbsp;</div>
          </div>
        </div>
      </div>
    </scroller>
  </div>
</template>

<script>
import Share from './share.js'
const NO_DATA_TEXT = '没有更多数据'
const LOAD_SCENE_INIT = 0
const LOAD_SCENE_GENERAL = 1
const LOAD_SCENE_FINISH = 2

export default {
  name: 'planting-list',
  mixins: [Share],
  data () {
    return {
      snapping: true,
      noDataText: '',
      plantingList: [],
      pageIndexStart: 1,
      pageSizeDefault: 10,
      paging: {
        total: 0,
        pageSize: 10,
        totalPage: 0,
        pageIndex: 0
      }
    }
  },
  mounted () {
    this.setIcon()
  },
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
      return this.plantingList && this.plantingList.length > 0 && this.plantingList.length < this.paging.total
    }
  },
  methods: {
    show () {
      this.noDataText = NO_DATA_TEXT
    },
    hide () {
      this.noDataText = ''
    },
    setIcon () {
      this.$store.commit('set', { name: 'app', k: 'acts', v: [{ src: 'planting/add_icon.png', w: 20, h: 20, fn: this.showPlantingAdd }] })
      this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    },
    showPlantingAdd () {
      this.$router.push({name: 'planting-add'})
    },
    showPlantingDetails (plantingId) {
      this.$router.push({name: 'planting-details', params: {_id: plantingId}})
    },
    async refresh (done) {
      try {
        this.hide()
        let result = await this.queryParcelPlantList()
        if (!result) throw new Error()
        this.paging = result.paging
        this.plantingList = result.data
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
            let result = await this.queryParcelPlantListNext()
            if (!result) throw new Error()
            this.paging = result.paging
            if (result.data.length > 0) {
              this.plantingList.push(...result.data)
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
    async queryParcelPlantList () {
      return this.queryParcelPlantListPaging(this.pageIndexStart, this.pageSizeDefault)
    },
    async queryParcelPlantListNext () {
      return this.queryParcelPlantListPaging(this.paging.pageIndex + 1, this.pageSizeDefault)
    }
  }
}
</script>

<style scoped>
  .scroller {
    padding-top: 44px;
  }
  .panel-separator {
    height: 10px;
    background-color: rgb(245, 246, 245);
  }
  .panel {
    background-color: #ffffff;
  }
  .view-list-item {
  }
  .view-list-item-body {
    padding: 10px;
    display: flex;
    flex-flow: row wrap;
    justify-content: space-between;
    align-content: center;
    align-items: stretch;
  }
  .view-list-item-body-title {
    flex: none;
    width: 75%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 16px;
    color: #333333;
  }
  .view-list-item-body-count {
    flex: none;
    width: 25%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 13px;
    color: #333333;
    text-align: end;
  }
  .view-list-item-body-time {
    flex: none;
    width: 75%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 12px;
    color: #999999;
  }
  .view-list-item-body-cycle {
    flex: none;
    width: 25%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 13px;
    color: #999999;
    text-align: end;
  }
  .view-list-item-separator {
    height: 1px;
    background-color: rgb(246, 245, 246);
  }
</style>
