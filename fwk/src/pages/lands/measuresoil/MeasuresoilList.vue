<template lang="pug">
v-modaldialog(
  :isShow.sync="isListDialog",
  title="测土记录",
  :modalMaxWidth="980",
  :bodyHeight="400",
  :showCloseBtn="true")
  v-layout(row)
    v-flex(xs6 text-xs-left) <span style="line-height: 40px; padding-left: 10px;">测土记录共有：{{ pagination.total }}&nbsp;&nbsp;条</span>
    v-flex(xs6 text-xs-right)
      v-btn(small icon dark color="red darken-2" title="添加测土记录" @click="addSoil" v-if="true")
        v-icon add
  v-layout(row)
    v-flex.pa-2(xs12)
      v-card(flat)
        v-data-table(:headers='columns' :items='measuresoils' hide-actions no-data-text = "暂无数据")
          template(slot='items', slot-scope='props')
            tr
              td(class="text-xs-left hideoverflow") {{ props.item.samplnum ? props.item.samplnum : '--' }}
              td(class="text-xs-left hideoverflow") {{ props.item.meastime ? props.item.meastime.substr(0,10) : '--' }}
              td(class="text-xs-left") 
                template
                  div(class="hideoverflow coclumnwidth_orgname" :title="props.item.orgname ? props.item.orgname : '--'") {{ props.item.orgname ? props.item.orgname : '--' }}
              td(class="text-xs-left hideoverflow") {{ props.item.soilgenre ? props.item.soilgenre : '--'}}
              td(class="text-xs-left hideoverflow") {{ props.item.hasOwnProperty('phvalue') ? props.item.phvalue : '--' }}
              td(class="text-xs-left hideoverflow") {{ props['item'].hasOwnProperty('matter') ? props.item.matter : '--' }}
              td(class="text-xs-center hideoverflow coclumnwidth_btn")
                v-btn(fab small icon title="查看" @click="viewSoil(props.item.id)" v-if="true")
                  v-icon remove_red_eye
                v-btn(fab small icon title="编辑" @click="editSoil(props.item.id)" v-if="true")
                  v-icon edit
                v-btn(fab small icon title="删除" @click="deleteSoil(props.item.id)" v-if="true")
                  v-icon(color="red darken-2") delete
  v-layout(row justify-center)                
    v-flex(xs12)
      v-custompagination(:pagination.sync="pagination")
  .py-1    
  v-divider    
  v-layout.py-1(row justify-space-between)
    v-flex(xs4)
      span(style="line-height: 35px;") 土壤健康指数（平均值）
    v-flex(xs3)
      v-select(
        v-bind:items="soilIndexs"
        v-model="soilIndex"
        item-value="code"
        item-text="name"
        hide-details=true
        single-line=true
        no-data-text="无"
      ) 
  v-layout(row, justify-center)
    div(v-if="chartData['rows'].length > 0")
      ve-line(:data="chartData" width="930px" height="300px" :legend-visible="showlegend" :settings="chartSettings")
    div(v-else)  
      v-subheader(class="noIndexData") 暂无指标数据      
  v-layout(row)
    v-soiladdedit(:addOrEditDialog.sync="isSoilAdd" :parcelid="parcelid" :soilid="soilid")
  v-layout(row)
    v-soilview(:isViewDialog.sync="isViewDialog" :soilid="soilid")
  v-layout(row, justify-center)
    v-modaldialog(
      :isShow="prompt.dialog",
      :title="prompt.title",
      :modalMaxWidth="290",
      :bodyHeight="80"
    )
      div {{ prompt.body }}
      template(slot="btnActions")
        v-divider
        v-card-actions
          v-spacer
          v-btn(flat, color="primary", @click.native.stop="operateCancel") 取消
          v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="operateOk") 确定  
</template>

<script>
import { CSoil } from '@/models/soil'
import VSoiladdedit from './MeasuresoilAddEdit.vue'
import VSoilview from './MeasuresoilView.vue'
import Measuresoil from './measuresoil.js'
import { excuteApis } from '@/apis'

const SOIL_INDEX = {
  'nitrogen': '碱解氮(mg/kg)',
  'phosphorus': '有效磷(mg/kg)',
  'potassium': '速效钾(mg/kg)',
  'phvalue': 'PH值',
  'matter': '有机质(g/kg)',
  'validzn': '有效Zn锌(mg/kg)',
  'validcu': '有效Cu铜(mg/kg)',
  'validfe': '有效Fe铁(mg/kg)',
  'validmn': '有效mn锰(mg/kg)',
  'validmo': '有效mo钼(mg/kg)',
  'valids': '有效s硫(g/kg)',
  'solubilityca': '水溶ca钙(g/kg)',
  'solubilitymg': '水溶mg镁(g/kg)',
  'salt': '水溶性盐总量(g/kg)'
}

export default {
  name: 'v-soillist',
  mixins: [Measuresoil],
  data () {
    this.chartSettings = { area: true }
    this.showlegend = false
    return {
      soilid: 0,
      measuresoils: [],
      soilIndexs: [],
      soilIndex: null,
      columns: [
        {
          text: '检测样本编号',
          align: 'left',
          sortable: false,
          value: 'samplnum'
        },
        {
          text: '检测日期',
          align: 'left',
          sortable: false,
          value: 'meastime'
        },
        {
          text: '检测机构',
          align: 'left',
          sortable: false,
          value: 'orgname'
        },
        {
          text: '土壤类型',
          align: 'left',
          sortable: false,
          value: 'soilgenre'
        },
        {
          text: 'PH值',
          align: 'left',
          sortable: false,
          value: 'phvalue'
        },
        {
          text: '有机质(g/kg)',
          align: 'left',
          sortable: false,
          value: 'matter'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      pagination: {
        total: 0,
        totalPage: 0,
        page: 1,
        rowsPerPage: 5
      },
      chartData: {
        columns: [],
        rows: []
      },
      isSoilAdd: 'IS_NONE',
      prompt: {
        data: null,
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      }
    }
  },
  watch: {
    listDialog (v) {
      this.isListDialog = v
      v ? this.fetchSoil() : null
    },
    isListDialog (v) {
      this.$emit('update:listDialog', v)
    },
    soilIndex (v) {
      this.setChart(v)
    },
    isSoilAdd (v) {
      this.isSoilAdd === 'IS_NONE' ? this.fetchSoil() : null
    },
    'pagination.page' (val) {
      this.fetchSoil()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchSoil()
    }
  },
  computed: {
    curveTitle () {
      let s = this.chartData.rows.reduce((sum, item) => {
        let key = Object.keys(item)[1]
        let value = sum + item[key]
        return value
      }, 0)
      let n = this.chartData.rows.length
      let title = s ? '（' + s / n + '）' : ''
      return '土壤健康指数' + title
    }
  },
  methods: {
    fetchSoil () {
      if (!this.parcelid) return
      const requestParams = Object.assign(
          {},
          {pageSize: this.pagination.rowsPerPage || 5},
          {currentPage: this.pagination.page || 1},
          {parcelid: this.parcelid}
        )
      this.measuresoils = []
      excuteApis(requestParams, global.constant.parcelApis, 'parcelMeasureSoil', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data && res.data.length > 0) {
          let resData = res.data
          for (let i = 0; i < resData.length; i++) {
            let soil = new CSoil(resData[i])
            let mstime = soil.getMeastime() // 处理采样时间
            let curSoil = Object.assign({}, {
              id: soil.getId(),
              samplnum: soil.getSamplnum(),
              parcelid: soil.getParcelid(),
              meastime: mstime ? mstime.substr(0, 10) : null,
              orgname: soil.getOrgname(),
              soilgenre: soil.getSoilgenre(),
              nitrogen: soil.getNitrogen(),
              phosphorus: soil.getPhosphorus(),
              potassium: soil.getPotassium(),
              phvalue: soil.getPhvalue(),
              matter: soil.getMatter(),
              validzn: soil.getValidzn(),
              validcu: soil.getValidcu(),
              validfe: soil.getValidfe(),
              validmn: soil.getValidmn(),
              validmo: soil.getValidmo(),
              valids: soil.getValids(),
              solubilityca: soil.getSolubilityca(),
              solubilitymg: soil.getSolubilitymg(),
              salt: soil.getSalt(),
              soiltexture: soil.getSoiltexture(),
              attachments: soil.getAttachments()
            })
            this.measuresoils.push(curSoil)
          }
          // 设置分页信息
          Object.assign(this.pagination,
            {
              currentPage: res['page']['currentPage'],
              pageSize: res['page']['pageSize'],
              total: res['page']['total'],
              totalPage: res['page']['totalPage']
            }
          )
        }
        this.fetchStatData()
      })
    },
    fetchStatData () {
      if (!this.parcelid) return
      this.soilIndex = null
      this.initIndexs()
      const requestParams = Object.assign({}, {parcelid: this.parcelid})
      excuteApis(requestParams, global.constant.parcelApis, 'parcelMeasureSoil', 'queryMeasureSoilStat').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data && res.data.length > 0) {
          let resData = res.data
          for (let i = 0; i < resData.length; i++) {
            this.getIndexsData(resData[i])
          }
          // 打印指标数据
          this.soilIndex = this.soilIndexs[0]['code']
        }
      })
    },
    getIndexsData (soil) {
      let ks = Object.keys(soil)
      for (let i = 0; i < ks.length; i++) {
        if (!SOIL_INDEX[ks[i]]) continue
        this.soilIndexs.forEach((item, index) => {
          let data = {}
          data['日期'] = soil['meastime']
          data[SOIL_INDEX[ks[i]]] = Number(soil[ks[i]])
          item['code'] === ks[i] ? this.soilIndexs[index]['data'].push(data) : null
        })
      }
    },
    initIndexs () {
      let ks = Object.keys(SOIL_INDEX)
      ks.forEach(k => {
        this.soilIndexs.push(Object.assign({}, {code: k, name: SOIL_INDEX[k], data: []}))
      })
    },
    setChart (v) {
      if (!v) {
        this.$set(this.chartData, 'columns', [])
        this.$set(this.chartData, 'rows', [])
      } else {
        this.soilIndexs.forEach((item, index) => {
          if (item['code'] === v) {
            this.$set(this.chartData, 'columns', ['日期', this.soilIndexs[index]['name']])
            this.$set(this.chartData, 'rows', this.soilIndexs[index]['data'])
          }
        })
      }
    },
    addSoil () {
      this.isSoilAdd = 'IS_ADD'
    },
    editSoil (sid) {
      this.soilid = sid
      this.isSoilAdd = 'IS_EDIT'
    },
    deleteSoil (sid) {
      this.soilid = sid
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.data = null
      this.prompt.dialog = true
    },
    viewSoil (sid) {
      this.soilid = sid
      this.isViewDialog = true
    },
    operateCancel () {
      this.prompt.dialog = false
      this.prompt.title = ''
      this.prompt.body = ''
      this.prompt.data = null
    },
    operateOk () {
      excuteApis(Object.assign({}, {id: this.soilid}), global.constant.parcelApis, 'parcelMeasureSoil', 'delete').then(response => {
        let res = response.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.fetchSoil()
        } else {
          this.prompt.body = '删除该记录失败.'
          this.prompt.ok = false
          console.log(res.message)
        }
      })
    }
  },
  created () {},
  components: {
    VSoiladdedit,
    VSoilview
  }
}
</script>

<style scoped>
.input-group {
  padding-top: 0px;
}
.chartIndex {
  /* width: 970px; */
  max-width:1830px;
  height: 600px;
  border: 1px solid red;
}
.noIndexData {
  padding: 40px 40px;
}
/* .coclumnwidth_btn {
  width: 220px;
}
*/
.coclumnwidth_orgname {
  width: 100px;
}
.hideoverflow {
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
}
</style>
