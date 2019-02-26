<template lang="pug">
v-card.pt-0(class="page-content")
  v-flex(xs12)
    v-layout(row justify-start)
      v-flex(xs5 sm5 md5 lg5 xl5)
        v-text-field(
            append-icon="search"
            v-model="keyword"
            label="输入记录编号、协议编号、协议执行状态或合作农户进行搜索"
            title="输入记录编号、协议编号、协议执行状态或合作农户进行搜索"
            single-line
            clearable
            hide-details
            @keyup.enter="fetchData()"
          )
      v-flex.pt-2(xs12 sm12 md1 lg1 xl1 class="text-sm-left")
        v-btn(color="primary" @click="fetchData()") 查询
      v-flex.pt-2(xs9 sm9 md10 lg10 xl10 class="text-xs-right" v-if="$authed('POST','farmeragreements/main/farmeragreements/addagreement')")
        v-btn(router,small fab dark color="red darken-2" title="新增合作协议" :to="{name: 'addagreement'}",v-if="options.create !== false")
          v-icon add    
  v-flex(xs12)
    v-layout(row)
      v-flex(xs6) <span style="line-height: 50px;">合作农户:&nbsp;&nbsp;{{ pagination.total }}&nbsp;户</span>
      // v-flex(xs6 class="text-xs-right" v-if="$authed('POST','farmeragreements/main/farmeragreements/addagreement')")
      //   v-btn(router,small fab dark color="red darken-2" title="新增合作协议" :to="{name: 'addagreement'}",v-if="options.create !== false")
      //     v-icon add
  v-flex(xs12)
    v-data-table(:headers='columns', :items='items', hide-actions, no-data-text = "暂无数据" :loading="loading")
      template(slot='items', slot-scope='props')
        tr(style="cursor: pointer" @click="showAgreementInfo(props.item.id)")
          td(class="text-xs-left") {{ props.item.ppid }}
          td(class="text-xs-left hideoverflow") {{ props.item.document }}
          td(class="text-xs-left hideoverflow") {{ getFormtedTime(props.item.begintime) }}
          td(class="text-xs-left hideoverflow") {{ getFormtedTime(props.item.endtime) }}
          td(class="text-xs-left hideoverflow") {{ props.item.actstatename }}
          td(class="text-xs-left hideoverflow") {{ props.item.peasantidname + '(' + props.item.peasantidlinkphone + ')' }}
          td(v-if='actions !== false' class="text-xs-center hideoverflow")
            // v-btn(title="查看" v-if="$authed('GET','farmeragreements/main/farmeragreements/viewagreement')" fab small icon :to="{name: 'viewagreement', query: {id: props.item.id}}")
            v-btn(title="查看" v-if="$authed('GET','farmeragreements/main/farmeragreements/viewagreement')" fab small icon @click.stop="showAgreementInfo(props.item.id)")
              v-icon() remove_red_eye
            // v-btn(title="编辑" v-if="$authed('PUT','farmeragreements/main/farmeragreements/editagreement')" fab small icon :to="{name: 'editagreement', query: {id: props.item.id}}")
            v-btn(title="编辑" v-if="$authed('PUT','farmeragreements/main/farmeragreements/editagreement')" fab small icon @click.stop="editAgreementInfo(props.item.id)")
              v-icon() edit
            v-btn(title="删除",v-if="$authed('DELETE','farmeragreements/main')" fab small icon @click.stop="removefarmerAgreement(props.item)")
              v-icon(color="red darken-2") delete
  v-flex(xs12)
    v-layout(row justify-center)
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
// import { parseTime, deferInvoke } from '@/utils'
import { parseTime } from '@/utils'
import { Toast } from '@/models/toast'

export default {
  data () {
    return {
      loading: false,
      columns: [
        {
          text: '记录编号',
          align: 'left',
          sortable: false,
          value: 'ppid'
        },
        {
          text: '协议文件编号',
          align: 'left',
          sortable: false,
          value: 'document'
        },
        {
          text: '协议执行开始时间',
          align: 'left',
          sortable: false,
          value: 'begintime'
        },
        {
          text: '协议执行截止时间',
          align: 'left',
          sortable: false,
          value: 'endtime'
        },
        {
          text: '协议执行状态',
          align: 'left',
          sortable: false,
          value: 'actstatename'
        },
        {
          text: '合作农户',
          align: 'left',
          sortable: false,
          value: 'peasantidname'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      options: {
        create: true,
        update: true,
        delete: true
      },
      actions: {},
      pagination: {
        total: 0,
        page: 1,
        rowsPerPage: 10
      },
      // totalPages: 0,
      // total: 0,
      isShowEdit: false,
      currentItem: false,
      items: [],
      provinces: [],
      province: 0,
      cities: [],
      city: 0,
      counties: [],
      county: 0,
      towns: [],
      town: 0,
      villages: [],
      village: 0,
      keyword: null,
      addresscode: '',
      selectedItemId: 0,
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      }
    }
  },
  watch: {
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    },
    'pagination.sortBy' (val) {
      this.fetchData()
    },
    'pagination.descending' (val) {
      this.fetchData()
    }
    // keyword: deferInvoke(function (val) {
    //   this.keyword = val
    //   this.fetchData()
    // })
  },
  methods: {
    fetchData () {
      let requestParams = {}
      if (this.keyword) {
        requestParams = { keyword: this.keyword }
      }
      requestParams = Object.assign({ delstate: 1, pageSize: this.pagination.rowsPerPage || 10, currentPage: this.pagination.page || 1 }, requestParams)
      this.toast = new Toast(this, null, null)
      this.toast.show()
      excuteApis(requestParams, global.constant.archivesApis, 'peasantprotocol', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.items = res.data
          // this.totalPages = res.page.totalPage
          // this.total = res.page.total
          this.pagination.totalPages = res.page.totalPage
          this.pagination.total = res.page.total
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    removefarmerAgreement (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = item.id
    },
    next () {
      this.pagination.page++
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.archivesApis, 'peasantprotocol', 'delete').then(data => {
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
      return parseTime(time, 'yyyy-MM-dd')
    },
    showAgreementInfo (agreementId) {
      this.$router.push({ name: 'viewagreement', query: { id: agreementId } })
    },
    editAgreementInfo (agreementId) {
      this.$router.push({ name: 'editagreement', query: { id: agreementId } })
    }
  },
  computed: {},
  created () {
    this.theme = global.helper.ls.get('theme')
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
.selected {
  width: 100px;
}
</style>
