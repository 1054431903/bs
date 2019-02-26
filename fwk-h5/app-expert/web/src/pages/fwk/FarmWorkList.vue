<template>
  <div class="main-container">
    <div class="search">
      <div class="search-container">
        <div class="search-container-icon">
          <div class="search-container-icon-img">&nbsp;</div>
        </div>
        <div class="search-container-text">
          <input class="search-container-text-input" type="string" placeholder="请输入关键字" v-model="searchText">
        </div>
      </div>
    </div>
    <div class="scroller-container">
      <scroller class="scroller" ref="scroller" :on-refresh="refresh" :on-infinite="infinite" :noDataText="noDataText">
        <v-card class="list-item" v-for="(farmWork,index) in farmWorkList" :key="index"
                :to="{name: 'fwk-farm_work-detail', params: { _id: farmWork.id}}">
          <div class="list-item-separator">&nbsp;</div>
          <div class="list-item-title">
            <span class="list-item-title-icon" :class="{'list-item-title-icon-1': runMode === 1}">&nbsp;</span>
            <span class="list-item-title-content">{{farmWork.createTimeString}}</span>
          </div>
          <div class="list-item-content">
            {{farmWork.content}}
          </div>
          <div class="list-item-bottom">
            <span class="list-item-bottom-label" :class="{'list-item-bottom-label-1': runMode === 1}" v-if="farmWork.typeList && farmWork.typeList.length > 0" v-for="(type,index) in farmWork.typeList" :key="index">{{type}}</span>
          </div>
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
  name: 'fwk-farm_work',
  mixins: [Share],
  props: {

  },
  data () {
    return {
      noDataText: '',
      searchText: null,
      searchTimer: null,
      pageIndexStart: 1,
      pageSizeDefault: 10,
      farmWorkList: [],
      paging: {
        total: 0,
        pageSize: 10,
        totalPage: 0,
        pageIndex: 0
      }
    }
  },
  created () {

  },
  mounted () {
    this.setIcon()
  },
  watch: {
    searchText () {
      if (this.searchTimer) {
        clearTimeout(this.searchTimer)
        this.searchTimer = null
      }
      this.searchTimer = setTimeout((target) => {
        target.$refs.scroller.triggerPullToRefresh()
      }, 500, this)
    }
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
      return this.farmWorkList && this.farmWorkList.length > 0 && this.farmWorkList.length < this.paging.total
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
        let result = await this.queryFarmWorkList()
        if (!result) throw new Error()
        this.paging = result.paging
        this.farmWorkList = result.data
        this.hasMore ? done(false) : done(true)
      } catch (e) {
        console.debug(e)
        this.showMessageError('加载数据失败')
        done(true)
      } finally {
        this.show()
      }
    },
    async setIcon () {
      if (this.runMode === 1) {
        this.$store.commit('set', { name: 'app', k: 'acts', v: [{ src: 'fwk/add_icon.png', w: 20, h: 20, fn: this.showfarmWorkAdd }] })
        this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
      }
    },
    async showfarmWorkAdd () {
      try {
        window.native.addFarmRecord(this.queryIssFarmlandId())
      } catch (e) {
        this.showMessageError(e.message)
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
            let result = await this.queryFarmWorkListNext()
            if (!result) throw new Error()
            this.paging = result.paging
            if (result.data.length > 0) {
              this.farmWorkList.push(...result.data)
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
    async queryFarmWorkList () {
      return this.queryFarmWorkListPaging(this.searchText, this.pageIndexStart, this.pageSizeDefault)
    },
    async queryFarmWorkListNext () {
      return this.queryFarmWorkListPaging(this.searchText, this.paging.pageIndex + 1, this.pageSizeDefault)
    },
    async queryFarmWorkListPaging (searchText, pageIndex, pageSize) {
      return this.parseFarmWorkListPaging((await this.queryFwkFarmWorkListPaging(searchText, pageIndex, pageSize)))
    },
    parseFarmWorkListPaging (data) {
      console.debug(data)
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
          let farmWork = {}
          farmWork.id = item.id
          farmWork.content = item.content
          farmWork.typeList = item.farmtypenames ? item.farmtypenames.split(',') : []
          farmWork.createTime = new Date(item.creattime.replace(' ', 'T') + '.000+08:00')
          farmWork.createTimeString = Moment(farmWork.createTime).format('YYYY-MM-DD')
          return farmWork
        })
      }
      console.debug(result)
      return result
    }
  }
}
</script>

<style scoped>
  .main-container {

  }
  .scroller {
    padding-top: 94px;
    background-color: rgb(247, 247, 249);
  }
  .list-item {
    background-color: #ffffff;
    margin: 0;
    padding: 0;
    border-radius:0 !important;
    box-shadow: none !important;
  }
  .list-item-title {
    padding: 5px 15px 5px 15px;
    vertical-align: center;
  }
  .list-item-title-icon {
    width: 5px;

  }
  .list-item-title-icon-1 {
    background-color: rgb(2, 173, 88);
  }
  .list-item-title-content {
    font-size: 13px;
    color: #acacac;
    padding: 0 0 0 5px;
  }
  .list-item-content {
    padding: 0 25px 0 25px;
    font-size: 15px;
    line-height: 24px;
    color: #3e3d3d;
  }
  .list-item-bottom {
    padding: 7px 15px 5px 15px;
  }
  .list-item-bottom-label {
    display: inline-block;
    margin: 0px 5px 5px 0;
    background-color: #ecf8fa;
    color: #50bed1;
    font-size: 13px;
    padding: 2px 5px;
    text-align: center;
    border-radius: 4px;
  }
  .list-item-bottom-label-1 {
    background-color: rgba(2, 173, 88, .1);
    color: rgb(2, 173, 88);
  }
  .list-item-separator {
     height: 1px;
     background-color: rgb(246, 245, 246);
  }
  .search {
    background-color: rgb(247, 247, 249);
    position: absolute;
    z-index: 9999;
    width: 100%;
  }
  .search-container {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-content: stretch;
    align-items: stretch;
    background: white;
    height: 30px;
    padding: 0 12px;
    margin: 10px;
    border-radius: 24px;
  }
  .search-container-icon {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-content: center;
    align-items: center;
  }
  .search-container-icon-img {
    width: 16px;
    height: 16px;
    background: url(../../assets/fwk/search.png) no-repeat center;
    background-size: 100%;
  }
  .search-container-text {
    padding: 0 0 0 8px;
    flex: auto;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-content: stretch;
    align-items: stretch;
  }
  .search-container-text-input {
    width: 100%;
    border: none;
    outline: none;
  }
  .search-container-text-input::-webkit-input-placeholder {
    font-size: 14px;
    color: #999999;
  }
</style>
