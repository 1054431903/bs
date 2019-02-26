<template lang="pug">
v-card.pt-0(class="page-content")
  v-flex(xs12 sm12 md12 lg12)
    v-layout(row)
      v-flex(xs2)  
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
      v-flex.ml-4(xs2)
        v-select(
            v-bind:items="publishstates"
            v-model="ispub"
            item-text="name"
            item-value="id"
            label="选择发布状态"
            hide-details=true
            single-line
            no-data-text="无"
            clearable
            class="hideoverflow"
        )
      v-flex.ml-4(xs2)
        v-text-field(
            append-icon="search"
            v-model="keyword"
            label="输入标题关键词查询"
            single-line
            clearable
            hide-details
            @keyup.enter="fetchData()"
          )
      v-flex.ml-3.pa-1(xs1)
        v-btn(color="primary" @click="fetchData()" title="查询") 查询
  v-flex(xs12 sm12 md12 lg12 xl12)
    v-layout(row)
      v-flex(xs6 sm6 md6 lg6 class="text-sm-left") <span style="line-height: 50px;">物候期管理规程:&nbsp;&nbsp;{{ pagination.total }}&nbsp;&nbsp;条</span>
      v-flex(xs6 sm6 md6 lg6 class="text-sm-right" v-if="$authed('POST','system/infopublish/sopadd')")
        v-btn(router, small fab dark color="red darken-2" title="新增规程" :to="{name: 'sopadd'}")
          v-icon add
  v-flex(xs12 sm12 md12 lg12 xl12) 
    v-data-table(:headers='columns', :items='informations', hide-actions, no-data-text = "暂无数据" :loading="loading")
      template(slot='items', slot-scope='props')
        tr(style="cursor: pointer" @click="showSopInfo(props.item.id)")
          td(class="text-xs-left")
            template
              div(class="avatar")
                v-avatar(size="45px" class="grey lighten-4")
                  img(v-if="props.item.resourcepath" :src=`props.item.resourcepath`)
                  img(v-else src=`static/sop/moren.png`)
          td(class="text-xs-left hideoverflow") <div class="itemtitle" :title="props.item.title">{{ props.item.title ? props.item.title : '--' }}</div>
          td(class="text-xs-left hideoverflow") {{ props.item.categoryname ? props.item.categoryname : '--' }}
          td(class="text-xs-left hideoverflow") {{ props.item.pubusername ? props.item.pubusername : '--' }}
          td(class="text-xs-left hideoverflow") {{ getFormtedTime(props.item.pubtime) }}
          td(class="text-xs-center hideoverflow")
            template
              v-switch(:title="props.item.ispub ? '已发布' : '未发布'" v-model="props.item.ispub" :color="theme" hide-details @change="informationPublish(props.item)" v-if="$authed('PUT','system/infopublish/soppublish')")                       
          td(v-if='actions !== false' class="text-xs-center hideoverflow")
            v-btn(title="查看",v-if="$authed('GET','system/infopublish/sopview')" fab small icon @click.stop="showSopInfo(props.item.id)")
              v-icon() remove_red_eye
            v-btn(title="编辑", v-if="$authed('PUT','system/infopublish/sopedit')" fab small icon @click.stop="editSopInfo(props.item.id)")
              v-icon() edit
            v-btn(title="复制", v-if="$authed('POST','system/infopublish/sopcopy')" fab small icon @click.stop="copyInformation(props.item)")
              v-icon() content_copy
            v-btn(title="删除", v-if="$authed('DELETE','system/infopublish/sopdelete')" fab small icon @click.stop="removeInformation(props.item)")
              v-icon(color="red darken-2") delete
  v-flex(xs12 sm12 md12 lg12 v-if="pagination.total > 0") 
    v-layout(row,justify-center)
      v-flex(xs12 sm12 md8 lg8)
        v-custompagination(:pagination.sync="pagination")
  v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
    div {{prompt.body}}
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat, color="primary", @click.native.stop="Cancel") 取消
        v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定                          
</template>

<script>

import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'
import { Toast } from '@/models/toast'

export default {
  data () {
    return {
      keyword: '',
      loading: false,
      columns: [
        {
          text: '封面',
          align: 'left',
          sortable: false,
          value: 'resourcepath'
        },
        {
          text: '标题',
          align: 'left',
          sortable: false,
          value: 'title'
        },
        {
          text: '品类',
          align: 'left',
          sortable: false,
          value: 'categoryname'
        },
        {
          text: '发布人',
          align: 'left',
          sortable: false,
          value: 'pubusername'
        },
        {
          text: '发布时间',
          align: 'left',
          sortable: false,
          value: 'pubtime'
        },
        {
          text: '取消发布/发布',
          align: 'left',
          sortable: false,
          value: 'ispub'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      informations: [],
      pagination: {
        page: 1,
        total: 0,
        rowsPerPage: 10
      },
      totalPages: 0,
      total: 0,
      options: {
        create: true,
        update: true,
        delete: true,
        copy: true
      },
      actions: {},
      categoryid: '',
      producttypes: [],
      ispub: null,
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
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      theme: 'primary'
    }
  },
  watch: {
    ispub: function (val, oldVal) {
      if (this.pagination.page !== 1 && this.totalPages > 1) {
        this.pagination.page = 1
      } else {
        val ? this.fetchData() : null
      }
    },
    categoryid: function (val, oldVal) {
      if (this.pagination.page !== 1 && this.totalPages > 1) {
        this.pagination.page = 1
      } else {
        !val ? this.fetchData() : null
      }
    },
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    }
  },
  methods: {
    showSopInfo (sopid) {
      this.$router.push({ name: 'sopview', query: { id: sopid } })
    },
    editSopInfo (sopid) {
      this.$router.push({ name: 'sopedit', query: { id: sopid } })
    },
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
      this.toast = new Toast(this, null, null)
      let requestParams = { pageSize: this.pagination.rowsPerPage || 10, currentPage: this.pagination.page || 1 }
      requestParams = Object.assign(requestParams, { ispub: this.ispub })
      requestParams = Object.assign(requestParams, { categoryid: this.categoryid })
      requestParams = Object.assign(requestParams, { title: this.keyword })
      this.toast.show()
      excuteApis(requestParams, global.constant.systemApis, 'information', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.informations = res.data
          // this.totalPages = res.page.totalPage
          // this.total = res.page.total
          this.pagination.page = res.page.currentPage
          this.pagination.total = res.page.total
          this.pagination.rowsPerPage = res.page.pageSize
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    removeInformation (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = item.id
    },
    copyInformation (item) {
      let requestParams = { id: item.id }
      excuteApis(requestParams, global.constant.systemApis, 'information', 'copy').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.fetchData()
        }
      })
    },
    informationPublish (item) {
      let requestParams = {}
      requestParams = Object.assign(requestParams, { puborgid: global.helper.ls.get('user').loginorgid }, { id: item.id }, { ispub: item.ispub ? 1 : 0 }, { pubuserid: global.helper.ls.get('user').supportid })
      excuteApis(requestParams, global.constant.systemApis, 'information', 'publish').then(data => {
        let res = data.data
        if (res.flag !== 1) { } else {
          this.fetchData()
        }
      })
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.systemApis, 'information', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.fetchData()
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    Cancel () {
      this.prompt.dialog = false
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
    this.theme = global.helper.ls.get('theme')
    this.fetchProductType()
    this.fetchData()
  }
}
</script>
<style scoped>
.avatar {
  justify-content: left;
}
.layout {
  margin: 0px auto;
}
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.itemtitle {
  width: 180px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  /* border: 1px solid red; */
}
</style>
