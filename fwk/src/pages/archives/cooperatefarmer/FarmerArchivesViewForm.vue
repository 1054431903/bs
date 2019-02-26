<template lang="pug">
v-card.pt-2(class="page-content")
  v-layout(row)
    //- v-flex(xs6)
    //-   v-layout(row)
    //-     v-flex.pt-2(xs2)
    //-       v-btn(router, :to="{name: 'farmerarchives'}" small outline :color="theme" )
    //-         v-icon keyboard_backspace
    //-         span 返回
    //- v-flex.py-2(xs6 class="text-xs-right")
    //-   v-btn(router fab small icon :to="{name: 'editfarmer', query: {id: peasant.id}}" v-if="$authed('PUT','farmerarchives/editfarmer')" title="编辑")
    //-     v-icon(color="grey darken-1") edit
    //-   v-btn(fab small icon @click="removefarmer(peasant)" v-if="$authed('DELETE','farmerarchives/main')" title="删除")
    //-     v-icon(color="red darken-2") delete
    v-flex(xs12)
      go-back(:name="viewTitle")
        span(style="float: right")
          v-btn(router fab small icon :to="{name: 'editfarmer', query: {id: peasant.id}}" v-if="$authed('PUT','farmerarchives/editfarmer')" title="编辑")
            v-icon(color="grey darken-1") edit
          v-btn(fab small icon @click="removefarmer(peasant)" v-if="$authed('DELETE','farmerarchives/main')" title="删除")
            v-icon(color="red darken-2") delete
  v-layout(row wrap mt-2)
    v-flex(xs12 sm12 md3 lg2 xl2)
      v-avatar(size="110px" class="grey lighten-4")
        img(v-if="peasant.photoidurl" :src="peasant.photoidurl")
        img(v-else src="static/peasant/moren.png")
    v-flex.pr-2(xs12 sm12 md3 lg3 xl3 class="leftdividers")
      v-layout(row)
        v-flex(xs12 class="textlen") 编号:&nbsp;&nbsp;{{ peasant.pid }}
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 身份证号:&nbsp;&nbsp;
          span {{ peasant.identity }}
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 地址:&nbsp;&nbsp;
          span(:title="peasant.address") {{ peasant.address }}
      v-layout(row class="line-distance")
        v-flex.pb-2(xs12 class="textlen") 合作状态:&nbsp;&nbsp;{{ peasant.cooperstatename }}
    v-flex(xs12 sm12 md3 lg3 xl3)
      v-layout(row)
        v-flex(xs12 class="textlen") 姓名:&nbsp;&nbsp;{{ (peasant.name ? peasant.name : '--') + (peasant.linkphone ? '(' + peasant.linkphone + ')' : '')}}
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 出生日期:&nbsp;&nbsp;{{ getFormtedTime(peasant.birthday) }}
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 文化程度:&nbsp;&nbsp;{{ peasant.culturename }}
      v-layout(row class="line-distance")
        v-flex.pb-2(xs12 class="textlen") 其他说明:&nbsp;&nbsp;
          span(:title="peasant.explain") {{ peasant.explain }}
    v-flex(xs12 sm12 md3 lg3 xl3)
      v-layout(row)
        v-flex(xs12 class="textlen") 性别:&nbsp;&nbsp;{{ peasant.sexname }}
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 年龄:&nbsp;&nbsp;{{ peasant.age }}&nbsp;&nbsp;岁
      v-layout(row class="line-distance")
        v-flex(xs12 class="textlen") 是否贫困户:&nbsp;&nbsp;{{ peasant.ispovertyname }}
      v-layout(row class="line-distance")

  v-divider
  v-layout(row)
    v-flex.pa-2(xs12)
      span(class="taland") Ta的地块
  v-divider
  v-layout(row)
    v-flex.pa-2(xs12)
      span 地块：{{landTotal}}块，面积：{{landareasum}}亩
  v-layout(row)
    v-flex(xs12 sm12 md12 lg12 xl12)
      v-data-table(:headers='landcolumns', :items='landitems',hide-actions, no-data-text = "暂无数据")
        template(slot='items', slot-scope='props')
          tr
            td(class="text-xs-left hideoverflow") {{ props.item.parcelno }}
            td(class="text-xs-left hideoverflow") {{ props.item.name }}
            td(class="text-xs-left hideoverflow") {{ props.item.addressfullname }}
            td(class="text-xs-left hideoverflow") {{ props.item.area }}
            td
              template
                v-layout(row justify-center)
                  v-flex(xs12 sm12 md12 lg12 text-xs-center)
                    v-btn(title="查看", icon, fab, small, flat, :to="{name: 'viewland', query: {id: props.item.id}}" v-if="$authed('GET','lands/viewland')")
                      v-icon() remove_red_eye
  v-layout(row)
    v-flex(xs12 sm12 md12 lg12 class="text-xs-center")
      //- v-pagination(v-model='landPagination.page', :length='landTotalPages', circle, :color="theme")
      v-custompagination(:pagination.sync="landPagination")
  v-divider
  v-layout(row)
    v-flex.pa-2(xs12)
      span(class="taland") Ta的合作协议
  v-divider
  v-layout(row justify-space-between)
    v-flex.pa-2(xs2)
      span 合作记录：{{ agrTotal }}条
    v-flex(xs2 class="text-xs-right" v-if="$authed('POST','archives/farmeragreements/addagreement')")
      v-btn(router, outline, small, :color="theme", :to="{name: 'addagreement', query: {peasant: Object.assign({}, {id: peasant.id, name: peasant.name, linkphone: peasant.linkphone})}}")
        //- v-icon add
        span 添加合作协议
  v-layout(row)
    v-flex(xs12 sm12 md12 lg12 xl12)
      v-data-table(:headers='agreementcolumns', :items='agreementitems', hide-actions, no-data-text = "暂无数据")
        template(slot='items', slot-scope='props')
          tr
            td(class="text-xs-left hideoverflow") {{ props.item.ppid }}
            td(class="text-xs-left hideoverflow") {{ props.item.document }}
            td(class="text-xs-left hideoverflow") {{ getFormtedTime(props.item.begintime) }}
            td(class="text-xs-left hideoverflow") {{ getFormtedTime(props.item.endtime) }}
            td(class="text-xs-left hideoverflow") {{ props.item.actstatename }}
            td(class="text-xs-left hideoverflow") {{ getAgreementPeroid(props.item.begintime, props.item.endtime) }}
            td
              template
                v-layout(row justify-center)
                  v-flex(xs12 sm12 md12 lg12 text-xs-center)
                    v-btn(title="查看" v-if="$authed('GET','farmeragreements/main/farmeragreements/viewagreement')" icon, fab, small, flat, :to="{name: 'viewagreement', query: {id: props.item.id}}")
                      v-icon() remove_red_eye
  v-layout(row)
    v-flex(xs12 sm12 md12 lg12 class="text-xs-center")
      v-custompagination(:pagination.sync="agrPagination")
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
import { parseTime, diffTime } from '@/utils'
export default {
  data () {
    return {
      peasant: {
        id: '',
        pid: '',
        name: '',
        photoidurl: '',
        linkphone: '',
        addresscode: '',
        addressdetail: '',
        address: '',
        sex: '',
        sexname: '',
        ispoverty: '',
        ispovertyname: '',
        identity: '',
        age: '',
        birthday: '',
        culture: '',
        orgid: 1,
        orgidname: '',
        cooperstate: '',
        cooperstatename: '',
        explain: ''
      },
      land: {
        id: '',
        parcelno: '',
        name: '',
        addresscode: '',
        addressdetail: '',
        addressfullname: '',
        area: '',
        elevation: '',
        dependenceid: '',
        departmentid: '',
        belongpeasantid: '',
        introduce: '',
        dependencename: '',
        departmentname: '',
        belongpeasant: '',
        responsibleuser: '',
        responsibleusername: '',
        categoryid: '',
        longitude: '',
        latitude: '',
        weatherinfo: ''
      },
      orgid: '',
      landitems: [],
      landcolumns: [
        {
          text: '地块编号',
          align: 'left',
          sortable: false,
          value: 'landno'
        },
        {
          text: '地块名称',
          align: 'left',
          sortable: false,
          value: 'landname'
        },
        {
          text: '地块位置',
          align: 'left',
          sortable: false,
          value: 'address'
        },
        {
          text: '面积(亩)',
          align: 'left',
          sortable: false,
          value: 'areas'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: ''
        }
      ],
      agreementitems: [],
      agreementcolumns: [
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
          text: '协议开始时间',
          align: 'left',
          sortable: false,
          value: 'begintime'
        },
        {
          text: '协议结束时间',
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
          text: '协议合作时限',
          align: 'left',
          sortable: false,
          value: 'agreementperoid'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: ''
        }
      ],
      agrPagination: {
        page: 1,
        rowsPerPage: 5,
        total: 0
      },
      agrTotalPages: 0,
      agrTotal: 0,
      landPagination: {
        page: 1,
        rowsPerPage: 5,
        total: 0
      },
      landTotalPages: 0,
      landTotal: 0,
      landareasum: '0',
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      id: null
    }
  },
  computed: {
    viewTitle () {
      let title = ''
      this.peasant.name ? (title = this.peasant.name) : ''
      this.peasant.pid ? (title = title + '（' + this.peasant.pid + '）') : ''
      return title
    }
  },
  watch: {
    'agrPagination.page': function (val) {
      this.fetchAgreementData()
    },
    'agrPagination.rowsPerPage' (val) {
      this.fetchAgreementData()
    },
    'landPagination.page': function (val) {
      this.fetchPeasentLand()
    },
    'landPagination.rowsPerPage' (val) {
      this.fetchPeasentLand()
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.orgid = global.helper.ls.get('user').loginorgid
    this.id = this.$route.query.id
    this.fetchPeasentData()
  },
  methods: {
    fetchPeasentData () {
      excuteApis(Object.assign({}, {id: this.id}), global.constant.archivesApis, 'peasant', 'queryById').then(
        data => {
          let res = data.data
          if (res.flag === 1) {
            this.peasant = res.data
            if (this.peasant.id) {
              this.fetchPeasentLand()
              this.fetchAgreementData()
            }
          } else {
            console.log('合作农户档案查询失败.')
          }
        }
      )
    },
    fetchPeasentLand () {
      let requestParams = Object.assign({}, { belongpeasantid: this.id }, { orgid: this.orgid }, { pageSize: this.landPagination.rowsPerPage || 10, currentPage: this.landPagination.page || 1 })
      excuteApis(requestParams, global.constant.parcelApis, 'parcel', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.landitems = res.data
          // this.landTotal = res.page.total
          // this.landTotalPages = res.page.totalPage
          this.landPagination.page = res.page.currentPage
          this.landPagination.rowsPerPage = res.page.pageSize
          this.landPagination.total = res.page.total
          if (this.landitems.length > 0) {
            this.landareasum = this.landitems[0].parcelareasum || 0
            this.landTotal = this.landitems[0].parcelsum || 0
          }
        }
      })
    },
    fetchAgreementData () {
      let requestParams = Object.assign({ delstate: 1 }, { peasantid: this.id }, { pageSize: this.agrPagination.rowsPerPage || 10, currentPage: this.agrPagination.page || 1 })
      excuteApis(requestParams, global.constant.archivesApis, 'peasantprotocol', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.agreementitems = res.data
          this.agrTotal = res.page.total || 0
          // this.agrTotalPages = res.page.totalPage
          this.agrPagination.page = res.page.currentPage
          this.agrPagination.rowsPerPage = res.page.pageSize
          this.agrPagination.total = res.page.total
        }
      })
    },
    getFormtedTime (time) {
      if (!time) {
        return '--'
      }
      return parseTime(time, 'yyyy-MM-dd')
    },
    getAgreementPeroid (beginDateTime, endDateTime) {
      return diffTime(beginDateTime, endDateTime)
    },
    removefarmer (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = item.id
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.archivesApis, 'peasant', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.$router.go(-1)
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    Cancel () {
      this.prompt.dialog = false
    }
  }
}
</script>

<style scoped>
.textlen {
  width: 300px;
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
}
.line-distance {
  padding-top: 0.80rem
}
.taland {
  font-weight: 600;
}
.backbtn {
  cursor: pointer;
  height: 35px;
}
</style>
