<template lang="pug">
v-card.pt-0(class="page-content")
  v-flex(xs12 sm12 md12 lg12)
    v-layout(row)      
      v-flex(xs12 sm12 md2 lg2 xl2)
        v-autocomplete(
            v-bind:items="producttypes"
            v-model="categoryid"
            item-text="name"
            item-value="id"
            label="选择品类"
            hide-details=true
            single-line
            no-data-text="无"
            clearable
            
            class="hideoverflow"
        )
      v-flex.ml-1(xs12 sm12 md3 lg3 xl3)
        v-text-field(
            append-icon="search"
            v-model="keyword"
            label="输入规程名称或发布人查询"
            single-line
            hide-details
            clearable
            @keyup.enter="fetchData()"
          )
      v-flex.pa-1(xs12 sm12 md1 lg1 xl1)
        v-btn(color="primary" title="查询" @click="fetchData()") 查询    
  v-flex.pt-2.pb-2(xs12 sm12 md12 lg12 xl12)
    v-layout(row)
      v-flex(xs12 sm12 md2 lg2 class="text-xs-left")
        span 物候期管理规程：&nbsp;&nbsp;{{ pagination.total }}&nbsp;&nbsp;个
  v-flex(xs12 sm12 md12 lg12 xl12)
    v-card(v-if="informations.length !== 0" flat)
      v-layout(row wrap)
        v-flex(xs2 v-for="information in informations" :key="information.id" class="photoborder mr-2 mb-2" @click="viewSopDetail(information)")
          v-img(:src="information.resourcepath"  height="200" min-width="200" v-if="information.resourcepath")
            v-layout.mt-2.ml-1
              v-flex(xs12 align-end)
                span(class="white--text producttypelabel" v-text="getProductTypeName(information.categoryid)")
          v-img(src=`static/sop/face.png`  height="200" min-width="200" v-else)
            v-layout.mt-2.ml-1
              v-flex(xs12 align-end)
                span(class="white--text producttypelabel" v-text="getProductTypeName(information.categoryid)")
          div.mt-1.ml-1
            v-layout
              v-flex(xs12 class="text-xs-left") <div class="infortitle" :title="information.title"> {{ information.title }} </div>
            v-layout
              v-flex(xs6 class="text-xs-left") {{ getFormtedTime(information.pubtime) }}
              v-flex(xs6 class="text-xs-right") <div class="pubuname"> {{ information.pubusername }} </div>
      
  v-flex(xs12 sm12 md12 lg12 xl12 v-if="informations.length > 0")
    v-layout(row justify-center)
      v-flex(xs11 sm11 md8 lg8)
        v-custompagination(:pagination.sync="pagination") 
  v-flex(xs12 sm12 md12 lg12 xl12 v-if="informations.length === 0") 
    v-card
      v-container(fluid)
        v-layout(row)
          div(class="tips text-sx-center") {{ '暂无数据' }}    
</template>

<script>
import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'
import { Toast } from '@/models/toast'

export default {
  data () {
    return {
      keyword: '',
      pages: 0,
      informations: [],
      pagination: {
        total: 0,
        page: 1,
        rowsPerPage: 15
      },
      // total: 0,
      // totalPage: 0,
      categoryid: '',
      currentPage: null,
      pageSize: null,
      producttypes: [],
      publishstates: [
        {
          id: 1,
          name: '已发布'
        },
        {
          id: 0,
          name: '待发布'
        }
      ],
      toast: null
    }
  },
  watch: {
    categoryid: function (val, oldVal) {
      if (!val) this.fetchData()
    },
    // keyword: deferInvoke(function (val) {
    //   this.keyword = val.trim()
    //   this.fetchData()
    // }),
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    }
  },
  methods: {
    fetchProductType () {
      excuteApis(null, global.constant.commonApiUrls, 'category', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.producttypes = this.producttypes.concat(res.data)
        } else {
          this.producttypes = []
        }
      })
    },
    fetchData () {
      this.toast = new Toast(this, '加载数据...', '')
      let requestParams = {}
      requestParams = Object.assign(requestParams, { pageSize: this.pagination.rowsPerPage || 15, currentPage: this.pagination.page || 1 })
      this.categoryid ? (requestParams = Object.assign(requestParams, { categoryid: this.categoryid })) : null
      this.keyword ? (requestParams = Object.assign(requestParams, { keywords: this.keyword.trim() })) : null
      requestParams = Object.assign(requestParams, { ispub: 1 })
      this.toast.show()
      excuteApis(requestParams, global.constant.systemApis, 'information', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.informations = res.data
          // this.totalPage = res.page.totalPage
          // this.total = res.page.total
          this.pagination.total = res.page.total
          this.pagination.totalPage = res.page.totalPage
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    getProductTypeName (categoryid) {
      let type = this._.find(this.producttypes, ['id', categoryid])
      return type ? type.name : ''
    },
    viewSopDetail (information) {
      this.$router.push({ name: 'sopview', query: { id: information.id, isview: true, backFlag: 1 } })
    },
    getFormtedTime (time) {
      if (time) {
        return parseTime(time, 'yyyy-MM-dd')
      } else {
        return '--'
      }
    }
  },
  computed: {},
  created () {
    this.fetchProductType()
    this.fetchData()
  }
}
</script>
<style scoped>
.layout {
  margin: 0px auto;
}
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.infortitle {
  max-width: 180px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.pubuname {
  max-width: 135px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 5px;
}
.producttypelabel {
  background-color: rgb(61, 60, 60);
  border-radius: 5px 5px 5px 5px;
  padding: 5px 5px;
}
.tips {
  padding: 100px;
  margin-left: auto;
  margin-right: auto;
}
.photoborder {
  border: 1px solid rgb(211, 206, 206);
  cursor: pointer;
  min-width: 200px;
}
</style>
