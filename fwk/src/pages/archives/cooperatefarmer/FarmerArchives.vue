<template lang="pug">
v-card.pt-0(class="page-content")
  v-flex(xs12 sm12 md12 lg12)
    v-layout(row)
      //- v-flex(xs12 sm12 md1 lg1)
      //-   v-select(
      //-       v-bind:items="provinces"
      //-       v-model="province"
      //-       item-text="name"
      //-       item-value="code"
      //-       label="省"
      //-       hide-details=true
      //-       single-line
      //-       no-data-text="无"
      //-       clearable
      //-       class="hideoverflow"
      //-   )
      //- v-flex.ml-1(xs12 sm12 md1 lg1)
      //-   v-select(
      //-       v-bind:items="cities"
      //-       v-model="city"
      //-       item-text="name"
      //-       item-value="code"
      //-       label="市"
      //-       hide-details=true
      //-       single-line
      //-       no-data-text="无"
      //-       clearable
      //-       class="hideoverflow"
      //-   )
      //- v-flex.ml-1(xs12 sm12 md1 lg1)
      //-   v-select(
      //-       v-bind:items="counties"
      //-       v-model="county"
      //-       item-text="name"
      //-       item-value="code"
      //-       label="县(区)"
      //-       hide-details=true
      //-       single-line
      //-       no-data-text="无"
      //-       clearable
      //-       class="hideoverflow"
      //-   )
      //- v-flex.ml-1(xs12 sm12 md1 lg1)
      //-   v-select(
      //-       v-bind:items="towns"
      //-       v-model="town"
      //-       item-text="name"
      //-       item-value="code"
      //-       label="镇(乡)"
      //-       hide-details=true
      //-       single-line
      //-       no-data-text="无"
      //-       clearable
      //-       class="hideoverflow"
      //-   )
      //- v-flex.ml-1(xs12 sm12 md1 lg1)
      //-   v-select(
      //-       v-bind:items="villages"
      //-       v-model="village"
      //-       item-text="name"
      //-       item-value="code"
      //-       label="村"
      //-       hide-details=true
      //-       single-line
      //-       no-data-text="无"
      //-       clearable
      //-       class="hideoverflow"
      //-   )
      v-flex(xs12 sm12 md10 lg5 xl5)
        v-areaselect(:curAddressCode.sync="curAreaCode")
      v-flex.ml-2(xs12 sm12 md4 lg4 xl4 class="text-xs-right")
        v-text-field(
            append-icon="search"
            v-model="keyword"
            label="输入农户编号、姓名、电话或当前合作状态进行搜索"
            title="输入农户编号、姓名、电话或当前合作状态进行搜索"
            single-line
            clearable
            hide-details
            @keyup.enter="fetchData()"
          )
      v-flex.pt-2(xs12 sm12 md1 lg1 xl1 class="text-sm-left")
        v-btn(color="primary" @click="fetchData()") 查询
      v-flex.pt-2(xs12 sm12 md1 lg5 xl5 class="text-sm-right" v-if="$authed('POST','farmerarchives/addfarmer')")
        v-btn(router, small fab dark color="red darken-2" title="新增合作农户" :to="{name: 'addfarmer'}",v-if="options.create !== false")
          v-icon add
  v-flex(xs12 sm12 md12 lg12)
    v-layout(row)
      v-flex(xs6 sm6 md6 lg6 class="text-sm-left") <span style="line-height: 50px;">合作农户:&nbsp;&nbsp;{{ pagination.total }}&nbsp;&nbsp;户</span>
      // v-flex(xs6 sm6 md6 lg6 class="text-sm-right" v-if="$authed('POST','farmerarchives/addfarmer')")
      //   v-btn(router, small fab dark color="red darken-2" title="新增合作农户" :to="{name: 'addfarmer'}",v-if="options.create !== false")
      //     v-icon add
  v-flex(xs12 sm12 md12 lg12)
    v-data-table(:headers='columns', :items='items', hide-actions, no-data-text="暂无数据",:loading="loading")
      template(slot='items', slot-scope='props')
        tr(style="cursor: pointer"  @click="showFarmArchiveInfo(props.item.id)")
          td(class="text-xs-left")
            template
              v-layout(class="avatar")
                v-avatar(size="35" class="grey lighten-4")
                  img(v-if="props.item.photoidurl" :src=`props.item.photoidurl`)
                  img(v-else src=`static/peasant/moren.png`)
                div(class="text-xs-left" style="width:50px")
                    div(class="peasantname" :title="props.item.name") &nbsp;&nbsp;{{ props.item.name }}
                    div &nbsp;&nbsp;{{ props.item.pid }}
          td(class="text-xs-left") {{ props.item.linkphone }}
          td(class="text-xs-left")
            template
              div(class="text-xs-left farmaddress hideoverflow" :title="props.item.address") &nbsp;&nbsp;{{ props.item.address }}
          //- td(class="text-xs-left")
          //-   template
          //-     div(class="text-xs-left orgidname hideoverflow" :title="props.item.orgidname") &nbsp;&nbsp;{{ props.item.orgidname }}
          td(class="text-xs-left") {{ props.item.cooperstatename }}
          td(v-if='actions !== false' class="text-xs-center hideoverflow")
            // v-btn(title="查看" v-if="$authed('GET','farmerarchives/viewfarmer')" fab small icon :to="{name: 'viewfarmer', query: {id: props.item.id}}")
            v-btn(title="查看" v-if="$authed('GET','farmerarchives/viewfarmer')" fab small icon @click.stop="showFarmArchiveInfo(props.item.id)")
              v-icon() remove_red_eye
            // v-btn(title="编辑" v-if="$authed('PUT','farmerarchives/editfarmer')" fab small icon :to="{name: 'editfarmer', query: {id: props.item.id}}")
            v-btn(title="编辑" v-if="$authed('PUT','farmerarchives/editfarmer')" fab small icon @click.stop="editFarmArchiveInfo(props.item.id)")
              v-icon() edit
            v-btn(title="删除" v-if="$authed('DELETE','farmerarchives/main')" fab small icon @click.stop="removefarmer(props.item)")
              v-icon(color="red darken-2") delete
  v-flex(xs12)
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
// import { deferInvoke } from '@/utils'
import { Toast } from '@/models/toast'

export default {
  data () {
    return {
      theme: 'primary',
      loading: false,
      columns: [
        {
          text: '农户',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        {
          text: '电话',
          align: 'left',
          sortable: false,
          value: 'linkphone'
        },
        {
          text: '地址',
          align: 'left',
          sortable: false,
          value: 'address'
        },
        // {
        //   text: '所属组织',
        //   align: 'center',
        //   sortable: false,
        //   value: 'orgidname'
        // },
        {
          text: '当前合作状态',
          align: 'left',
          sortable: false,
          value: 'cooperstatename'
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
      // totalPages: 1,
      // total: 0,
      isShowEdit: false,
      currentItem: false,
      items: [],
      provinces: [],
      province: null,
      cities: [],
      city: null,
      counties: [],
      county: null,
      towns: [],
      town: null,
      villages: [],
      village: null,
      keyword: null,
      addresscode: null,
      selectedItemId: 0,
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      toast: null,
      curAreaCode: '100000000000100000000000'
    }
  },
  watch: {
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    }
    // province: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(1, val)
    //   }
    //   this.city = null
    //   this.cities = []
    //   this.county = null
    //   this.counties = []
    //   this.town = null
    //   this.towns = []
    //   this.village = null
    //   this.villages = []
    //   this.addresscode = val
    // },
    // city: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(2, val)
    //   }
    //   this.county = null
    //   this.counties = []
    //   this.town = null
    //   this.towns = []
    //   this.village = null
    //   this.villages = []
    //   this.addresscode = val
    // },
    // county: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(3, val)
    //   }
    //   this.town = null
    //   this.towns = []
    //   this.village = null
    //   this.villages = []
    //   this.addresscode = val
    // },
    // town: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(4, val)
    //   }
    //   this.village = null
    //   this.villages = []
    //   this.addresscode = val
    // },
    // village: function (val, oldVal) {
    //   this.addresscode = val
    // },
    // keyword: deferInvoke(function (val) {
    //   this.keyword = val
    //   this.fetchData(0)
    // }),
    // addresscode: deferInvoke(function (val, oldVal) {
    //   if (val !== oldVal) {
    //     this.fetchData(0)
    //   }
    // }),
    // curAreaCode: function (val, oldval) {
    //   this.fetchData(0)
    // }
  },
  methods: {
    fetchData (flag) {
      // this.addresscode = this.village || this.town || this.county || this.city || this.province
      this.addresscode = this.curAreaCode
      let requestParams = Object.assign({ delstate: 1, pageSize: this.pagination.rowsPerPage || 10, currentPage: this.pagination.page || 1 }, { addresscode: this.addresscode, keyword: this.keyword })
      flag ? this.toast = new Toast(this, null, null) : null
      flag ? this.toast.show() : null
      excuteApis(requestParams, global.constant.archivesApis, 'peasant', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.items = res.data
          // this.totalPages = res.page.totalPage
          // this.total = res.page.total
          this.pagination.totalPages = res.page.totalPage
          this.pagination.total = res.page.total
        }
        flag ? this.toast.close() : null
      }, () => {
        flag ? this.toast.close() : null
      })
    },
    removefarmer (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = item.id
    },
    editfarmer (item) {
      this.$router.push({ path: 'editfarmer', query: { peasant: item } })
    },
    // next () {
    //   this.pagination.page++
    // },
    fetchPosition (level, parentCode) {
      let position = []
      let requestParams = { 'parentCode': parentCode }
      excuteApis(requestParams, global.constant.archivesApis, 'position', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          position = res.data
          switch (level) {
            case 0: // 省
              this.provinces = position
              break
            case 1: // 市
              this.cities = position
              break
            case 2: // 县
              this.counties = position
              break
            case 3: // 镇
              this.towns = position
              break
            case 4: // 村
              this.villages = position
              break
            default:
          }
        }
      })
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.archivesApis, 'peasant', 'delete').then(data => {
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
    showFarmArchiveInfo (archiveId) {
      this.$router.push({ name: 'viewfarmer', query: { id: archiveId } })
    },
    editFarmArchiveInfo (archiveId) {
      this.$router.push({ name: 'editfarmer', query: { id: archiveId } })
    }
  },
  computed: {},
  created () {
    this.theme = global.helper.ls.get('theme')
    this.fetchPosition(0, 0)
    this.fetchData(1)
    // this.curAreaCode = '110102010001110102010001'
    this.curAreaCode = '100000000000100000000000'
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
.peasantname {
  max-width: 100px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.farmaddress {
  max-width: 300px;
}
.orgidname {
  max-width: 100px;
}
.customArea {
  border: 1px solid red;
}
</style>
