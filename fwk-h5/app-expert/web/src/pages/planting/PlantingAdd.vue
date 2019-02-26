<template>
  <div class="main-container">
    <div class="panel-separator">&nbsp;</div>
    <div class="panel">
      <div class="add-table">
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required">*</span>
            <span class="add-table-cell-label-span">品类</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.categoryPicker)">
            <input class="add-table-cell-input" placeholder="请选择品类" readonly type="text" v-model="category" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="categoryPicker" :color-confirm="themeColor" :data="categoryPickerData" :formatter="categoryPickerFormatter" @confirm="categoryPickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required">*</span>
            <span class="add-table-cell-label-span">品种</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.varietyPicker)">
            <input class="add-table-cell-input" placeholder="请选择品种" readonly type="text" v-model="variety" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="varietyPicker" :color-confirm="themeColor" :data="varietyPickerData" :formatter="varietyPickerFormatter" @confirm="varietyPickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required">*</span>
            <span class="add-table-cell-label-span">播/栽种时间</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.plantBeginTimePicker)">
            <input class="add-table-cell-input" placeholder="请选择播/栽种时间" readonly type="text" v-model="plantBeginTime" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="plantBeginTimePicker" :color-confirm="themeColor" type="date" @confirm="plantBeginTimePickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required hidden">*</span>
            <span class="add-table-cell-label-span">预计生长周期</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.planGrowthcyclePicker)">
            <input class="add-table-cell-input" placeholder="请选择预计生长周期" readonly type="text" v-model="planGrowthcycle" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="planGrowthcyclePicker" :color-confirm="themeColor" :data="planGrowthcyclePickerData" :formatter="planGrowthcyclePickerFormatter" @confirm="planGrowthcyclePickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required">*</span>
            <span class="add-table-cell-label-span">种植面积</span>
          </div>
          <div class="add-table-cell add-table-cell-content">
            <input class="add-table-cell-input" placeholder="请输入种植面积" type="number" min="0" max="1000000" v-model="plantArea" />
          </div>
        </div>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required hidden">*</span>
            <span class="add-table-cell-label-span">植株数量</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.plantCountPicker)">
            <input class="add-table-cell-input" placeholder="请选择植株数量" readonly type="text" v-model="plantCount" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="plantCountPicker" :color-confirm="themeColor" :data="plantCountPickerData" :formatter="plantCountPickerFormatter" @confirm="plantCountPickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required hidden">*</span>
            <span class="add-table-cell-label-span">株龄</span>
          </div>
          <div class="add-table-cell add-table-cell-content" @click="showPicker($refs.plantAgePicker)">
            <input class="add-table-cell-input" placeholder="请选择株龄" readonly type="text" v-model="plantAge" />
          </div>
          <div class="add-table-cell add-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="plantAgePicker" :color-confirm="themeColor" :data="plantAgePickerData" :formatter="plantAgePickerFormatter" @confirm="plantAgePickerConfirm"></v-picker>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required hidden">*</span>
            <span class="add-table-cell-label-span">种植批次</span>
          </div>
          <div class="add-table-cell add-table-cell-content">
              <input class="add-table-cell-input" placeholder="请输入种植批次" type="text" v-model="plantBatch" />
          </div>
        </div>
        <div class="add-table-row-separator">&nbsp;</div>
        <div class="add-table-row add-table-row-padding1">
          <div class="add-table-cell add-table-cell-label">
            <span class="add-table-cell-label-required hidden">*</span>
            <span class="add-table-cell-label-span">备注</span>
          </div>
          <div class="add-table-cell add-table-cell-content">
            &nbsp;
          </div>
        </div>
        <div class="add-table-row add-table-row-padding2">
          <div class="add-table-cell add-table-cell-comment">
            <textarea class="add-table-cell-textarea" placeholder="请输入备注" rows="5" v-model="comment"></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from './share.js'
import Moment from 'moment'
import VPicker from '@/components/picker/Picker'
export default {
  name: 'planting-add',
  mixins: [Share],
  components: {
    VPicker
  },
  data () {
    return {
      categorys: [],
      varieties: [],
      planting: {
        categoryId: undefined,
        categoryName: undefined,
        varietyId: undefined,
        varietyName: undefined,
        plantBeginTime: undefined,
        plantArea: undefined,
        plantAge: undefined,
        plantAgeUnit: undefined,
        planGrowthcycle: undefined,
        planGrowthcycleUnit: undefined,
        plantBatch: undefined,
        plantCount: undefined,
        plantCountUnit: undefined
      },
      categoryPickerData: [[]],
      categoryPickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      },
      varietyPickerData: [[]],
      varietyPickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      },
      planGrowthcyclePickerData: [(() => {
        let array = []
        for (let i = 1; i < 100; i++) {
          array.push(i)
        }
        return array
      })(), ['年', '月', '日']],
      planGrowthcyclePickerFormatter (item, itemIndex, wheelIndex) {
        return item
      },
      plantCountPickerData: [
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        ['株', '棚', '颗']
      ],
      plantCountPickerFormatter (item, itemIndex, wheelIndex) {
        return item
      },
      plantAgePickerData: [(() => {
        let array = []
        for (let i = 1; i < 100; i++) {
          array.push(i)
        }
        return array
      })(), ['年', '月', '日']],
      plantAgePickerFormatter (item, itemIndex, wheelIndex) {
        return item
      }
    }
  },
  computed: {
    category: {
      get () {
        return this.planting.categoryName
      },
      set ({id, name} = {}) {
        this.planting.categoryId = id
        this.planting.categoryName = name
      }
    },
    variety: {
      get () {
        return this.planting.varietyName
      },
      set ({id, name} = {}) {
        this.planting.varietyId = id
        this.planting.varietyName = name
      }
    },
    plantBeginTime: {
      get () {
        if (this.planting.plantBeginTime instanceof Date) {
          return Moment(this.planting.plantBeginTime).format('YYYY-MM-DD')
        }
      },
      set (value) {
        this.planting.plantBeginTime = value
      }
    },
    plantArea: {
      get () {
        return this.planting.plantArea
      },
      set (value) {
        let input = Math.abs(Number(value))
        if (input > 1000000) {
          input = 1000000
        }
        this.planting.plantArea = input
      }
    },
    planGrowthcycle: {
      get () {
        if (this.planting.planGrowthcycle) {
          if (this.planting.planGrowthcycleUnit) {
            return this.planting.planGrowthcycle.toString().concat(this.planting.planGrowthcycleUnit)
          } else {
            return this.planting.planGrowthcycle
          }
        }
      },
      set ([value, unit]) {
        this.planting.planGrowthcycle = value
        this.planting.planGrowthcycleUnit = unit
      }
    },
    plantCount: {
      get () {
        if (typeof this.planting.plantCount === 'number') {
          if (this.planting.plantCountUnit) {
            return this.planting.plantCount.toString().concat(this.planting.plantCountUnit)
          } else {
            return this.planting.plantCount
          }
        }
      },
      set ([value, unit]) {
        this.planting.plantCount = value
        this.planting.plantCountUnit = unit
      }
    },
    plantAge: {
      get () {
        if (typeof this.planting.plantAge === 'number') {
          if (this.planting.plantAgeUnit) {
            return this.planting.plantAge.toString().concat(this.planting.plantAgeUnit)
          } else {
            return this.planting.plantAge
          }
        }
      },
      set ([value, unit]) {
        this.planting.plantAge = value
        this.planting.plantAgeUnit = unit
      }
    },
    plantBatch: {
      get () {
        return this.planting.plantBatch
      },
      set (value) {
        this.planting.plantBatch = value
      }
    },
    comment: {
      get () {
        return this.planting.comment
      },
      set (value) {
        this.planting.comment = value
      }
    }
  },
  mounted () {
    this.setIcon()
    this.load()
  },
  methods: {
    setIcon () {
      this.$store.commit('set', { name: 'app', k: 'acts', v: [{ text: '确定', fn: this.done }] })
      this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    },
    showPicker (picker) {
      if (picker && typeof picker.show === 'function') {
        picker.show()
      }
    },
    check (model) {
      if (!model.categoryId) {
        this.showMessageError('请选择品类')
        return
      }
      if (!model.varietyId) {
        this.showMessageError('请选择品种')
        return
      }
      if (!model.plantBeginTime) {
        this.showMessageError('请选择播/栽种时间')
        return
      }
      if (!(typeof model.plantArea === 'number')) {
        this.showMessageError('请输入种植面积')
        return
      }
      if (!Number.isInteger(model.plantArea)) {
        model.plantArea = Number((Math.round(model.plantArea * 100) / 100).toFixed(2))
      }
      if (model.plantBatch && !model.plantBatch.match(/^[\u4E00-\u9FA5A-Za-z0-9_-]{0,50}$/)) {
        this.showMessageError('种植批次必须输入中英文字符但不包括-、_以外的特殊符号,长度不超50')
        return
      }
      return true
    },
    done () {
      if (!this.check(this.planting)) return
      this.addPlanting(this.planting).then(result => {
        this.planting = {}
        this.showMessageSuccess('添加种植记录成功')
        setTimeout(() => {
          this.$router.back(-1)
        }, 500)
      }).catch(error => {
        this.showMessageError(error.message)
      })
    },
    categoryPickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.category = item.value
      this.variety = undefined
      this.varietyPickerData = [this.varieties.filter(variety => !variety.isDeleted && variety.categoryId === item.value.id)]
    },
    varietyPickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.variety = item.value
    },
    plantBeginTimePickerConfirm (items) {
      console.debug(items)
      this.plantBeginTime = new Date(
        Number(items[0].value.replace('年', '')),
        Number(items[1].value.replace('月', '')) - 1,
        Number(items[2].value.replace('日', ''))
      )
    },
    planGrowthcyclePickerConfirm (items) {
      console.debug(items)
      this.planGrowthcycle = [Number(items[0].value), items[1].value]
    },
    plantCountPickerConfirm (items) {
      console.debug(items)
      this.plantCount = [
        Number(items[0].value) * 10000 + Number(items[1].value) * 1000 + Number(items[2].value) * 100 + Number(items[3].value) * 10 + Number(items[4].value) * 1,
        items[5].value
      ]
    },
    plantAgePickerConfirm (items) {
      console.debug(items)
      this.plantAge = [Number(items[0].value), items[1].value]
    },
    async load () {
      this.categorys = await this.queryCategoryList()
      if (!this.categorys) return
      this.categoryPickerData = [this.categorys]
      this.varieties = await this.queryVarietyList()
      if (!this.varieties) return
      this.varietyPickerData = [this.varieties.filter(item => !item.isDeleted)]
    },
    async addPlanting (planting) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong,
        orgid: tenant._id.$oid
      }
      let data = {
        parcelid: fwkParcelId,
        categoryid: planting.categoryId,
        varieties: planting.varietyId,
        plantbegintime: Moment(planting.plantBeginTime).format('YYYY-MM-DD'),
        preestimategrowthcycle: planting.planGrowthcycle,
        preestimategrowthcycleunit: planting.planGrowthcycleUnit,
        plantage: planting.plantAge,
        plantageunit: planting.plantAgeUnit,
        area: planting.plantArea,
        number: planting.plantCount,
        unit: planting.plantCountUnit,
        growingbatch: planting.plantBatch,
        introduce: planting.comment
      }
      let response = await this.$http.post('/fwk-service-parcel/parcelPlant/saveParcelPlant', data, {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(response.data.message)
      }
      if (Array.isArray(response.data.data)) {
        return this.parseVarietyList(response.data.data)
      }
      return []
    }
  }
}
</script>

<style scoped>
  .hidden {
    visibility: hidden;
  }
  .readonly {
    color: #999999 !important;
  }
  .main-container {
    background-color: #ffffff;
  }
  .panel-separator {
    height: 10px;
    background-color: rgb(246, 245, 246);
  }
  .panel {
    margin: 0;
    padding: 0;
  }
  .add-table {
  }
  .add-table-row-separator {
    height: 1px;
    background-color: rgb(245, 246, 245);
  }
  .add-table-row {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: center;
  }
  .add-table-row-padding1 {
    padding: 10px 10px 10px 5px;
  }
  .add-table-row-padding2 {
    padding: 0px 10px 10px 10px;
  }
  .add-table-cell {
    margin: 0;
    padding: 0;
  }
  .add-table-cell-label {
    flex: none;
    width: 100px;
  }
  .add-table-cell-label-required {
    color: red;
  }
  .add-table-cell-label-span {
    text-align: left;
    padding-right: 10px;
    padding-left: 4px;
    font-size: 13px;
    color: #333333;
  }
  .add-table-cell-content {
    flex: auto;
    font-size: 14px;
    text-align: right;
  }
  .add-table-cell-comment {
    flex: auto;
    font-size: 14px;
    text-align: left;
  }
  .add-table-cell-input {
    width: 100%;
    text-align: right;
    border: 0px;
    border: none;
    outline: none;
  }
  .add-table-cell-input::-webkit-input-placeholder {
    font-size: 14px;
    color: #999999;
    text-align: right;
  }
  .add-table-cell-textarea {
    width: 100%;
    border: 0px;
    border: none;
    outline: none;
    padding-left: 5px;
  }
  .add-table-cell-textarea::-webkit-input-placeholder {
    font-size: 14px;
    color: #999999;
  }
  .select-dialog {
    position: absolute;
    top: 0;
    left: 0;
    background:rgba(0, 0, 0, .3);
    width: 100%;
    height: 100%;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-content: center;
    align-items: flex-end;
  }
  .select-dialog-content {
    width: 100%;
    background: #ffffff;
  }
</style>
