<template lang="pug">
  v-modaldialog(
    :isShow.sync="isView",
    title="查看测土记录",
    :modalMaxWidth="970",
    :bodyHeight="480",
    :showCloseBtn="true")
    v-card-text
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") 样品编号:&nbsp;&nbsp;<span :title="model.samplnum">{{ showObjValue(model, 'samplnum', '--') }}</span>
        v-flex.pa-2(xs3 class="hideoverflow") 检测日期:&nbsp;&nbsp;<span :title="model.meastime">{{ showObjValue(model, 'meastime', '--') }}</span>
        v-flex.pa-2(xs3 class="hideoverflow") 检测机构:&nbsp;&nbsp;<span :title="model.orgname">{{ showObjValue(model, 'orgname', '--') }}</span>
        v-flex.pa-2(xs3 class="hideoverflow") 土壤质地:&nbsp;&nbsp;<span :title="model.soiltexturename">{{ showObjValue(model, 'soiltexturename', '--') }}</span>
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") 土壤类型:&nbsp;&nbsp;<span :title="model.soilgenre">{{ showObjValue(model, 'soilgenre', '--') }}</span>
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") PH值:&nbsp;&nbsp;{{ model.hasOwnProperty('phvalue') ? model.phvalue : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有机质(g/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('matter') ? model.matter : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 碱解氮(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('nitrogen') ? model.nitrogen : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效磷(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('phosphorus') ? model.phosphorus : '--' }}
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") 速效钾(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('potassium') ? model.potassium : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效Zn锌(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('validzn') ? model.validzn : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效Cu铜(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('validcu') ? model.validcu : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效Fe铁(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('validfe') ? model.validfe : '--' }}
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") 有效mn锰(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('validmn') ? model.validmn : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效mo钼(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('validmo') ? model.validmo : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 有效s硫(mg/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('valids') ? model.valids : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 水溶ca钙(g/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('solubilityca') ? model.solubilityca : '--' }}
      v-layout.py-1(row)
        v-flex.pa-2(xs3 class="hideoverflow") 水溶mg镁(g/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('solubilitymg') ? model.solubilitymg : '--' }}
        v-flex.pa-2(xs3 class="hideoverflow") 水溶性盐量(g/kg):&nbsp;&nbsp;{{ model.hasOwnProperty('salt') ? model.salt : '--' }}
      v-layout.py-1(row)
        v-flex.pa-2(xs12) 附件:
          span.px-3(v-for="attachment in model.attachments" :key="attachment.id")
            a(:href="attachment.resourcepath" target="_blank") {{attachment.resourcename}}
</template>

<script>
import { CSoil } from '@/models/soil'
import { excuteApis } from '@/apis'
import { getObjValue } from '@/utils'
export default {
  name: 'v-soilview',
  props: {
    isViewDialog: {
      type: Boolean,
      require: true,
      default: false
    },
    soilid: {
      type: Number,
      require: true,
      default: 0
    }
  },
  data () {
    return {
      isView: false,
      soil: null,
      toast: null,
      qualities: [],
      model: {
        id: '',
        samplnum: '',
        parcelid: '',
        meastime: '',
        orgname: '',
        soilgenre: '',
        soilgenrename: '',
        nitrogen: '',
        phosphorus: '',
        potassium: '',
        phvalue: '',
        matter: '',
        validzn: '',
        validcu: '',
        validfe: '',
        validmn: '',
        validmo: '',
        valids: '',
        solubilityca: '',
        solubilitymg: '',
        salt: '',
        soiltexture: '',
        soiltexturename: '',
        attachments: []
      }
    }
  },
  watch: {
    soilid (v) {
      this.soil.setId(v)
    },
    isViewDialog (v) {
      this.isView = v
      v ? this.fetchSoil() : null
    },
    isView (v) {
      this.$emit('update:isViewDialog', v)
    }
  },
  methods: {
    fetchQualities () {
      let requestParams = {
        dicttype: 'soil-texture'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.qualities = res.data
        }
      })
    },
    fetchSoil () {
      let id = this.soil.getId()
      if (!id) return
      this.soil.querySoilById().then(response => {
        let res = response.data
        if (res.flag === 1) {
          let s = res.data[0]
          let mstime = s['meastime'] || null // 处理采样时间
          this.soil.setSamplnum(s['samplnum'] || null)
          this.soil.setMeastime(mstime ? mstime.substr(0, 10) : null)
          this.soil.setOrgname(s['orgname'] || null)
          this.soil.setSoilgenre(s['soilgenre'] || null)
          this.soil.setSoilgenrename(s['soilgenrename'] || null)
          this.soil.setNitrogen(s.hasOwnProperty('nitrogen') ? s.nitrogen : null)
          this.soil.setPhosphorus(s.hasOwnProperty('phosphorus') ? s.phosphorus : null)
          this.soil.setPotassium(s.hasOwnProperty('potassium') ? s.potassium : null)
          this.soil.setPhvalue(s.hasOwnProperty('phvalue') ? s.phvalue : null)
          this.soil.setMatter(s.hasOwnProperty('matter') ? s.matter : null)
          this.soil.setValidzn(s.hasOwnProperty('validzn') ? s.validzn : null)
          this.soil.setValidcu(s.hasOwnProperty('validcu') ? s.validcu : null)
          this.soil.setValidfe(s.hasOwnProperty('validfe') ? s.validfe : null)
          this.soil.setValidmn(s.hasOwnProperty('validmn') ? s.validmn : null)
          this.soil.setValidmo(s.hasOwnProperty('validmo') ? s.validmo : null)
          this.soil.setValids(s.hasOwnProperty('valids') ? s.valids : null)
          this.soil.setSolubilityca(s.hasOwnProperty('solubilityca') ? s.solubilityca : null)
          this.soil.setSolubilitymg(s.hasOwnProperty('solubilitymg') ? s.solubilitymg : null)
          this.soil.setSalt(s.hasOwnProperty('salt') ? s.salt : null)
          this.soil.setSoiltexture(s.hasOwnProperty('soiltexture') ? s.soiltexture : null)
          this.soil.setSoiltexturename(s.hasOwnProperty('soiltexturename') ? s.soiltexturename : null)
          this.soil.setAttachments(s['attachments'] || null)
          Object.assign(this.model, this.soil.getSoil())
        } else {
          console.log('获取种植信息失败.')
        }
      })
    },
    // 显示对象属性值
    showObjValue (obj, key, defalutValue) {
      return getObjValue(obj, key, defalutValue)
    }
  },
  created () {
    this.soil = new CSoil()
    this.soil.setId(this.soilid)
    this.fetchQualities()
  }
}
</script>

<style scoped>
.editAttachmentDisplay {
  line-height: 75px;
}
.hideoverflow {
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
}
</style>
