<template lang="pug">
  v-modaldialog(
    :isShow="addOrEditPlantDialog",
    :title="addOrEditDialogTitle",
    :modalMaxWidth="680",
    :bodyHeight="400")
    v-form(v-model="formValid" ref="productiveform" lazy-validation autocomplete="off")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="years"
            v-model="model.year"
            no-data-text="无"
            label="产出年份"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="output"
            v-model="model.output"
            label="产出物"
            disabled
          ) 
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="levels"
            v-model="model.level"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="产出物等级"
          )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="precategorys"
            v-model="model.category"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="预估单产类别"
          )    
      v-layout(row, wrap, justify-space-between)
        v-flex(xs12 sm12 md8 lg8 xl8)
          v-text-field(
            name="preoutput"
            v-model="model.preoutput"
            :rules = "rules.numbers"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'preoutput')"
            label="预估单产产量"
          )
        v-flex(xs12 sm12 md3 lg3 xl3)
          v-select(
            v-bind:items="units"
            v-model="model.preoutputunit"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="单位"
          )             
      v-layout(row, wrap, justify-space-between)
        v-flex(xs12 sm12 md8 lg8 xl8)
          v-text-field(
            name="preland"
            v-model="model.preland"
            :rules = "rules.numbers"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'preland')"
            label = "预估地块产量"
          )
        v-flex(xs12 sm12 md3 lg3 xl3)
          v-select(
            v-bind:items="units"
            v-model="model.prelandunit"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="单位"
            disabled
          )        
      v-layout(row, wrap, justify-space-between)
        v-flex(xs12 sm12 md8 lg8 xl8)
          v-text-field(
            name="actoutput"
            v-model="model.actoutput"
            :rules = "rules.numbers"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'actoutput')"
            label = "实际产量"
          )
        v-flex(xs12 sm12 md3 lg3 xl3)
          v-select(
            v-bind:items="units"
            v-model="model.actoutputunit"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            label="单位"
            disabled
          )        
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat, color="primary", @click.native.stop="cancel") 取消
        v-btn(color="primary", flat, @click.native.stop="save") 保存
</template>

<script>
// import { Toast } from '@/models/toast'
import Productive from './productive.js'

export default {
  name: 'v-productiveaddedit',
  mixins: [Productive],
  watch: {
    addOrEditDialog (v) {
      this.fetchPlant() // 获取种植信息
      this.disposeModalDialog(v)
    },
    'model.category' (v) {
      v ? this.prelandOutNumber(this.model.preoutput) : null
    },
    'model.preoutputunit' (v) {
      if (v) {
        this.model.prelandunit = v
        this.model.actoutputunit = v
      }
    },
    'model.preoutput' (v) {
      v ? this.prelandOutNumber(v) : (this.model.preland = null)
    }
  },
  methods: {
    save () {
      if (this.$refs.productiveform.validate()) {
        try {
          this.productive.setId(this.model.id || null)
          this.productive.setPlantid(this.model.plantid)
          this.productive.setYear(this.model.year)
          this.productive.setOutput(this.model.output)
          this.productive.setLevel(this.model.level)
          this.productive.setCategory(this.model.category)
          this.productive.setPreoutput(this.model.preoutput)
          this.productive.setPreoutputunit(this.model.preoutputunit)
          this.productive.setPreland(this.model.preland)
          this.productive.setPrelandunit(this.model.prelandunit)
          this.productive.setActoutput(this.model.actoutput)
          this.productive.setActoutputunit(this.model.actoutputunit)
          this.productive.saveProductive().then(response => {
            let res = response.data
            if (res.flag === 1) {
              this.$emit('update:addOrEditDialog', 'IS_NONE')
              // this.fetchPlantData()
            } else {
              this.toast.setContent('保存产量信息失败, 原因：' + res.message)
              this.toast.showAutoClose()
            }
          })
        } catch (err) {
          console.log(err)
          this.toast.setContent('保存产量信息失败, 网络异常')
          this.toast.showAutoClose()
        }
      }
    },
    // 根据表单不同的状态处理弹窗内容信息
    disposeModalDialog (v) {
      this.$vuetify.goTo(0, null)
      this.addOrEditPlantDialog = this.addOrEdit[v]
      if (v === 'IS_ADD') {
        this.addOrEditDialogTitle = '添加产量信息'
        this.$refs['productiveform'] ? this.$refs['productiveform'].reset() : null
        this.productive.setId(null)
        this.model.year = new Date().getFullYear()
        this.model.level = 'output-level-other'
        this.model.category = 'preestimate-per-mu-yeild'
        this.model.id = null
        this.model.plantid = this.plant.getId()
        this.model.output = (this.productive.getOutput() || this.plant.getBreedname())
        this.model.actoutput = null
        this.model.preoutput = null
        this.model.preland = null
        this.model.preoutputunit = null
        this.fetchPrecategorys()
        this.fetchUnits()
        this.fetchLevels()
        this.fetchYears()
      } else if (v === 'IS_EDIT') {
        this.addOrEditDialogTitle = '编辑产量信息'
        this.fetchPrecategorys()
        this.fetchUnits()
        this.fetchLevels()
        this.fetchYears()
        this.fetchProductive()
      } else {
        this.addOrEditDialogTitle = ''
      }
    },
    // 自动计算预估地块产量
    prelandOutNumber (v) {
      let category = this.model.category
      switch (category) {
        case 'preestimate-per-mu-yeild':
          // 亩产
          let mmum = Number(this.plant.getArea()) * Number(v)
          this.model.preland = mmum ? mmum.toFixed(2) : null
          break
        case 'preestimate-per-plant-yeild':
          // 株产
          let pnum = Number(this.plant.getNumber()) * Number(v)
          this.model.preland = pnum ? pnum.toFixed(2) : null
          break
        default:
      }
    },
    // 鼠标滚轮事件触发
    wheelScrollEvent (e, name) {
      e = e || window.event
      let flag = 0
      // 处理浏览器兼容性
      if (e.wheelDelta) { // IE、谷歌浏览器
        e.wheelDelta > 0 ? (flag = 1) : (flag = -1)
      } else if (e.detail) { // 火狐浏览器
        e.detailY > 0 ? (flag = 1) : (flag = -1)
      }
      flag > 0 && this.model ? (this.model[name] = Number(this.model[name]) + 1) : this.model[name] = Number(this.model[name]) + 1
    }
  }
}
</script>

<style scoped>
</style>
