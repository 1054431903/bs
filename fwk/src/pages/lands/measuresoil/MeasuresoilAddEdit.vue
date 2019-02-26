<template lang="pug">
  v-modaldialog(
    :isShow="isAddOrEditDialog",
    :title="addOrEditDialogTitle",
    :modalMaxWidth="680",
    :bodyHeight="400")
    v-form(v-model="addFormValid" ref="measureSoilform" lazy-validation autocomplete="off")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="samplnum"
            v-model="model.samplnum"
            counter="20"
            :rules = "rules.samplnum"
            label="样品编号"
            required
            maxlength="20"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-customdatepicker(
            pickerLabel="选择检测日期*"
            :selectedDate.sync="model.meastime"
            :singleLine="false"
            :rules="rules.meastime"
            :allowedDates="allowedDates")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="orgname"
            v-model="model.orgname"
            counter="30"
            :rules = "rules.orgname"
            label="检测机构"
            maxlength="30"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="qualities"
            v-model="model.soiltexture"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="土壤质地"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="soilgenre"
            v-model="model.soilgenre"
            counter="20"
            :rules = "rules.soilgenre"
            label="土壤类型"
            maxlength="20"
          ) 
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="phvalue"
            v-model="model.phvalue"
            :rules = "rules.numbers"
            type="number"
            label = "PH值"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="matter"
            v-model="model.matter"
            :rules = "rules.numbers"
            type="number"
            label="有机质(g/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="nitrogen"
            v-model="model.nitrogen"
            :rules = "rules.numbers"
            type="number"
            label="碱解氮(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="phosphorus"
            v-model="model.phosphorus"
            :rules = "rules.numbers"
            type="number"
            label = "有效磷(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="potassium"
            v-model="model.potassium"
            :rules = "rules.numbers"
            type="number"
            label = "速效钾(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="validzn"
            v-model="model.validzn"
            :rules = "rules.numbers"
            type="number"
            label = "有效Zn锌(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="validcu"
            v-model="model.validcu"
            :rules = "rules.numbers"
            type="number"
            label="有效Cu铜(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="validfe"
            v-model="model.validfe"
            :rules = "rules.numbers"
            type="number"
            label = "有效Fe铁(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="validmn"
            v-model="model.validmn"
            :rules = "rules.numbers"
            type="number"
            label = "有效mn锰(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="validmo"
            v-model="model.validmo"
            :rules = "rules.numbers"
            type="number"
            label = "有效mo钼(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="valids"
            v-model="model.valids"
            :rules = "rules.numbers"
            type="number"
            label="有效s硫(mg/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="solubilityca"
            v-model="model.solubilityca"
            :rules = "rules.numbers"
            type="number"
            label="水溶ca钙(g/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="solubilitymg"
            v-model="model.solubilitymg"
            :rules = "rules.numbers"
            type="number"
            label="水溶mg镁(g/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="salt"
            v-model="model.salt"
            :rules = "rules.numbers"
            type="number"
            label="水溶性盐量(g/kg)"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-dropzoneupload(:files.sync="model.attachments")
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat, color="primary", @click.native.stop="closeViewMeasureSoil") 取消
        v-btn(color="primary", flat, @click.native.stop="measureSoilSave") 保存
</template>

<script>
import { CSoil } from '@/models/soil'
import { excuteApis } from '@/apis'
import { Toast } from '@/models/toast'
import VDropzoneupload from '@/components/DropzoneUpload.vue'
import { parseTime } from '@/utils'

export default {
  name: 'v-soiladdedit',
  props: {
    parcelid: {
      type: Number,
      require: true,
      default: 0
    },
    soilid: {
      type: Number,
      require: true,
      default: 0
    },
    addOrEditDialog: {
      type: String,
      require: true,
      default: 'IS_NONE'
    }
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_NONE': false
    }
    return {
      soil: null,
      toast: null,
      addOrEdit: ADD_OR_EDIT,
      qualities: [],
      parceltypes: [],
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
      },
      isAddOrEditDialog: false,
      addOrEditDialogTitle: '',
      isListDialog: false,
      isViewDialog: false,
      addFormValid: false,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        samplnum: [
          (v) => !!v || '必填项',
          // (v) => !!v || v.length <= 20 || '长度不超过20个字符'
          (v) => this.sampleRule(v) || '长度不超过20个字符'
        ],
        meastime: [
          (v) => !!v || '必填项',
          // (v) => !!v || v.length <= 20 || '长度不超过20个字符'
          (v) => this.meastimeRule(v) || '检测日期选择无效,应不大于当前日期'
        ],
        orgname: [
          (v) => this.orgNameRule(v) || '长度不超过30个字符'
        ],
        soilgenre: [
          (v) => this.soilgenreRule(v) || '长度不超过20个字符'
        ],
        numbers: [
          (v) => this.numbersRule(v) || '请输入有效数字(小数保留2位),不超过1000000'
        ],
        breed: [
          (v) => this.breedRule(v) || '长度不超过20个字符'
        ],
        number: [
          (v) => this.numberRule(v) || '请输入有效整数,不超过1000000'
        ],
        introduce: [
          (v) => this.introduceRule(v) || '长度不超过500个字符'
        ]
      }
    }
  },
  watch: {
    addOrEditDialog (v) {
      this.isAddOrEditDialog = this.addOrEdit[v]
      v === 'IS_EDIT' ? this.addOrEditDialogTitle = '编辑测土记录' : null
      v === 'IS_EDIT' && this.soilid ? this.soil.setId(this.soilid) : null
      v === 'IS_EDIT' && this.soilid ? this.fetchSoil() : null
      if (v === 'IS_EDIT') return
      // 窗体变化为新增时清空已有表单
      this.addOrEditDialogTitle = '新增测土记录'
      this.$refs['measureSoilform'] ? this.$refs['measureSoilform'].reset() : null
      let date = new Date()
      this.model.meastime = parseTime(date, 'yyyy-MM-dd')
      this.model.attachments = []
    },
    parcelid (v) {
      this.soil.setParcelid(v)
    }
  },
  methods: {
    sampleRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    meastimeRule (v) {
      if (v) {
        let start = new Date(v)
        let end = new Date()
        return start <= end
      }
      return false
    },
    orgNameRule (v) {
      if (v) {
        return v.length <= 30
      }
      return true
    },
    soilgenreRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    numbersRule (v) {
      if (v) {
        return (v >= 0 && v <= 1000000) && ((/^\+?[1-9][0-9]*(\.\d{1,2})?$/.test(v)) || (/^0(\.\d{1,2})?$/.test(v)))
      }
      return true
    },
    numberRule (v) {
      if (v) {
        return (v >= 0 && v <= 1000000) && (/^\+?[1-9][0-9]*$/.test(v))
      }
      return true
    },
    breedRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    introduceRule (v) {
      if (v) {
        return v.length <= 500
      }
      return true
    },
    allowedDates (v) {
      return new Date(v) < new Date()
    },
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
    // fetchParceltypes () {
    //   let requestParams = {
    //     dicttype: 'parcel-type'
    //   }
    //   excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
    //     let res = data.data
    //     if (res.flag === 1 && res.data.length > 0) {
    //       this.parceltypes = res.data
    //     }
    //   })
    // },
    closeViewMeasureSoil () {
      this.$emit('update:addOrEditDialog', 'IS_NONE')
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
          this.soil.setNitrogen(s['nitrogen'] || null)
          this.soil.setPhosphorus(s['phosphorus'] || null)
          this.soil.setPotassium(s['potassium'] || null)
          this.soil.setPhvalue(s.hasOwnProperty('phvalue') ? s['phvalue'] : null)
          this.soil.setMatter(s.hasOwnProperty('matter') ? s['matter'] : null)
          this.soil.setValidzn(s.hasOwnProperty('validzn') ? s['validzn'] : null)
          this.soil.setValidcu(s.hasOwnProperty('validcu') ? s['validcu'] : null)
          this.soil.setValidfe(s.hasOwnProperty('validfe') ? s['validfe'] : null)
          this.soil.setValidmn(s.hasOwnProperty('validmn') ? s['validmn'] : null)
          this.soil.setValidmo(s.hasOwnProperty('validmo') ? s['validmo'] : null)
          this.soil.setValids(s.hasOwnProperty('valids') ? s['valids'] : null)
          this.soil.setSolubilityca(s.hasOwnProperty('solubilityca') ? s['solubilityca'] : null)
          this.soil.setSolubilitymg(s.hasOwnProperty('solubilitymg') ? s['solubilitymg'] : null)
          this.soil.setSalt(s.hasOwnProperty('salt') ? s['salt'] : null)
          this.soil.setSoiltexture(s.hasOwnProperty('soiltexture') ? s['soiltexture'] : null)
          this.soil.setAttachments(s['attachments'] || null)
          Object.assign(this.model, this.soil.getSoil())
        } else {
          console.log('获取种植信息失败.')
        }
      })
    },
    measureSoilSave () {
      this.model.parcelid = this.parcelid
      let method = this.addOrEditDialog === 'IS_EDIT' ? 'update' : 'add'
      this.addOrEditDialog === 'IS_ADD' ? (delete this.model.id) : null
      if (this.$refs.measureSoilform.validate()) {
        excuteApis(this.model, global.constant.parcelApis, 'parcelMeasureSoil', method).then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.$emit('update:addOrEditDialog', 'IS_NONE')
          } else {
            console.log('编辑测土记录保存失败')
          }
        })
      }
    }
  },
  created () {
    this.soil = new CSoil()
    this.soil.setId(this.soilid)
    this.toast = new Toast(this, null, null)
    this.fetchQualities()
    // this.fetchParceltypes()
  },
  components: {
    VDropzoneupload
  }
}
</script>

<style scoped>
.editAttachmentDisplay {
  line-height: 75px;
}
</style>
