<template lang="pug">
  v-modaldialog(
    :isShow="addOrEditPlantDialog",
    :title="addOrEditDialogTitle",
    :modalMaxWidth="900",
    :bodyHeight="400")
    v-form(v-model="plantFormValid" ref="plantForm" lazy-validation autocomplete="off")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="producttypes"
            v-model="model.categoryid"
            item-text="name"
            item-value="id"
            label="作物品类"
            no-data-text="无"
            class="hideoverflow"
            :rules="rules.required"
          )   
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-select(
            v-bind:items="productkinds"
            v-model="model.breed"
            item-text="name"
            item-value="id"
            label="作物品种"
            no-data-text="无"
            class="hideoverflow"
          )
          //  v-select(
          //   v-bind:items="productkinds"
          //   v-model="model.breed"
          //   item-text="name"
          //   item-value="id"
          //   label="作物品种"
          //   no-data-text="无"
          //   class="hideoverflow"
          //   :rules="rules.required"
          // )
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="batch"
            v-model="model.batch"
            :rules="rules.batch"
            counter="50"
            maxlength="50"
            label="种植批次"
          )    
      v-layout(row)
        v-flex(xs12 sm12 md4 lg4 xl4)
          v-customdatepicker(
            pickerLabel="播/栽种时间"
            :selectedDate.sync="model.begintime"
            :singleLine="false"
            :rules="rules.required")
        v-flex.pl-2(xs12 sm12 md3 lg3 xl3)
          v-text-field(
            name="peroid"
            v-model="model.peroid"
            type="number"
            :rules="rules.peroid"
            @mousewheel="wheelScrollEvent($event, 'peroid')"
            label="预计生长周期"
          )
        v-flex.pl-1(xs12 sm12 md1 lg1 xl1)
          v-select(
            v-bind:items="peroidunits"
            v-model="model.peroidunit"
            label="单位"
            hide-details=true
            no-data-text="无"
            class="hideoverflow"
            :rules="rules.required"
          )
        v-flex.pl-2(xs12 sm12 md3 lg3 xl3)
          v-text-field(
            name="age"
            v-model="model.age"
            :rules="rules.age"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'age')"
            label="株龄"
          )
        v-flex.pl-1(xs12 sm12 md1 lg1 xl1)
          v-select(
            v-bind:items="ageunits"
            v-model="model.ageunit"
            label="单位"
            hide-details=true
            no-data-text="无"
            class="hideoverflow"
            :rules="rules.required"
          )   
      v-layout(row)
        v-flex(xs12 sm12 md8 lg8 xl8)
          v-text-field(
            name="area"
            v-model="model.area"
            :rules="rules.numbers"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'area')"
            label="种植面积(亩)"
          )
        v-flex.pl-2(xs12 sm12 md3 lg3 xl3)
          v-text-field(
            name="number"
            v-model="model.number"
            :rules="rules.number"
            type="number"
            @mousewheel="wheelScrollEvent($event, 'number')"
            label="植株数量"
          )
        v-flex.pl-1(xs12 sm12 md1 lg1 xl1)
          v-select(
            v-bind:items="numberunits"
            v-model="model.numberunit"
            label="单位"
            hide-details=true
            no-data-text="无"
            class="hideoverflow"
            :rules="rules.required"
          )
      v-layout(row, justify-space-between, v-if="model.id")
        v-flex(xs3 sm3 md3 lg3 xl3 text-xs-left)
          span(style="line-height: 45px;") 产量信息
        v-flex(xs3 sm3 md3 lg3 xl3 text-xs-right)
          v-btn(small icon dark color="red darken-2" title="添加产量信息" @click="addProductive")
            v-icon add
      v-layout(row, justify-space-between, v-if="model.id")
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-data-table(:headers='productcolumns' :items='model.products' hide-actions no-data-text = "暂无数据")
              template(slot='items', slot-scope='props')
                tr
                  td(class="text-xs-left hideoverflow") {{ props.item.year ? props.item.year : '--' }}
                  td(class="text-xs-left hideoverflow") {{ props.item.output ? props.item.output : '--' }}
                  td(class="text-xs-left hideoverflow") {{ props.item.levelname ? props.item.levelname : '--' }}
                  td(class="text-xs-left hideoverflow") {{ showPreoutput(props.item.preoutput, props.item.preoutputunitname, props.item.category)}}
                  td(class="text-xs-left hideoverflow") {{ props.item.preland ? (props.item.preland + ' ' + props.item.prelandunitname) : '--' }}
                  td(class="text-xs-left hideoverflow") {{ props.item.actoutput ? (props.item.actoutput  + ' ' + props.item.actoutputunitname) : '--' }}
                  td(class="text-xs-center hideoverflow")
                    v-btn(fab small icon title="查看" @click="viewProductive(props.item.id)")
                      v-icon remove_red_eye
                    v-btn(fab small icon title="编辑" @click="editProductive(props.item.id)")
                      v-icon edit
                    v-btn(fab small icon title="删除" @click="deleteProductive(props.item.id)")
                      v-icon(color="red darken-2") delete
      .py-2                
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-dropzoneupload(:files.sync="model.attachments")              
      .py-1
      v-layout(row)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-textarea(
            class="notes"
            name="introduce"
            v-model="model.introduce"
            :rules="rules.introduce"
            counter="500"
            maxlength="500"
            label="备注"
          )
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 保存 
    v-layout(row)
      v-productiveaddedit(:curplant="model" :productiveid="productiveid" :addOrEditDialog.sync="addOrEditProductiveDialog")
    v-layout(row)
      v-productiveview(:curplant="model" :productiveid="productiveid" :viewDialog.sync="viewProductiveDialog")
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
import Plant from './plant.js'
import VProductiveaddedit from './productive/ProductiveAddEdit.vue'
import VProductiveview from './productive/ProductiveView.vue'
import VDropzoneupload from '@/components/DropzoneUpload.vue'
export default {
  name: 'v-plantaddedit',
  mixins: [Plant],
  watch: {
    addOrEditDialog (v) {
      this.disposeModalDialog(v)
    }
  },
  methods: {
    save () {
      if (this.$refs.plantForm.validate()) {
        try {
          this.plant.setParcelid(this.parcelid)
          this.plant.setCategoryid(this.model.categoryid)
          this.plant.setBreed(this.model.breed)
          this.plant.setBatch(this.model.batch)
          this.plant.setBegintime(this.model.begintime)
          this.plant.setPeroid(this.model.peroid)
          this.plant.setPeroidunit(this.model.peroidunit)
          this.plant.setAge(this.model.age)
          this.plant.setAgeunit(this.model.ageunit)
          this.plant.setArea(this.model.area)
          this.plant.setNumber(this.model.number)
          this.plant.setNumberunit(this.model.numberunit)
          this.plant.setIntroduce(this.model.introduce)
          this.plant.setAttachments(this.model.attachments)
          this.plant.savePlant().then(response => {
            let res = response.data
            if (res.flag === 1) {
              this.plantapis = res.data
              // 如果是新增种植新则提示继续添加产量信息，否则直接关闭编辑表单
              if (this.plant.getId()) {
                this.$emit('update:addOrEditDialog', 'IS_NONE')
              } else {
                this.operateFlag = 'other'
                this.prompt.title = '提示信息'
                this.prompt.body = '种植信息保存成功，是否追加产量信息？'
                this.prompt.ok = true
                this.prompt.dialog = true
              }
            } else {
              this.toast.setContent('保存种植信息失败, 原因：' + res.message)
              this.toast.showAutoClose()
            }
          })
        } catch (err) {
          console.log(err)
          this.toast.setContent('保存种植信息失败, 网络异常')
          this.toast.showAutoClose()
        }
      }
    },
    saveBeforeAddOrEdit (afterop, productiveid) {
      if (this.$refs.plantForm.validate()) {
        try {
          this.plant.setParcelid(this.parcelid)
          this.plant.setCategoryid(this.model.categoryid)
          this.plant.setBreed(this.model.breed)
          this.plant.setBatch(this.model.batch)
          this.plant.setBegintime(this.model.begintime)
          this.plant.setPeroid(this.model.peroid)
          this.plant.setPeroidunit(this.model.peroidunit)
          this.plant.setAge(this.model.age)
          this.plant.setAgeunit(this.model.ageunit)
          this.plant.setArea(this.model.area)
          this.plant.setNumber(this.model.number)
          this.plant.setNumberunit(this.model.numberunit)
          this.plant.setIntroduce(this.model.introduce)
          this.plant.setAttachments(this.model.attachments)

          // 设置品类名称
          let breed = this.productkinds.filter(item => {
            return item.id === this.model.breed
          })
          breed.length > 0 ? (this.model.breedname = breed[0].name) : null
          this.plant.setBreedname(this.model.breedname)

          this.plant.savePlant().then(response => {
            let res = response.data
            if (res.flag === 1) {
              this.plantapis = res.data
              this.productiveid = productiveid
              this.addOrEditProductiveDialog = afterop
            } else {
              this.toast.setContent('保存种植信息失败, 原因：' + res.message)
              this.toast.showAutoClose()
            }
          })
        } catch (err) {
          this.toast.setContent('保存种植信息失败, 网络异常')
          this.toast.showAutoClose()
        }
      }
    },
    operateOk () {
      if (this.operateFlag === 'other') {
        this.setPlant(this.plantapis)
        this.setProductives(this.plantapis['yields'])
        this.plant.setId(this.plantapis['id'])
        this.plant.setProducts(this.products)
        this.plant.setBreedname(this.model.breedname) // 设置品类
        Object.assign(this.model, this.plant.getPlant())
        this.$set(this.model, 'id', this.plantapis.id)
        this.prompt.dialog = false
        this.addOrEditDialogTitle = '编辑种植信息'
        this.addProductive()
      } else {
        this.productive.setId(this.productiveid)
        this.productive.deleteProductive().then(response => {
          let res = response.data
          if (res.flag === 1) {
            this.fetchPlant()
          } else {
            console.log('删除产量信息失败.')
          }
          this.prompt.dialog = false
        })
        this.prompt.dialog = false
      }
    },
    operateCancel () {
      if (this.operateFlag === 'other') {
        this.prompt.dialog = false
        this.$emit('update:addOrEditDialog', 'IS_NONE')
      } else {
        this.prompt.dialog = false
      }
    },
    // 根据表单不同的状态处理弹窗内容信息
    disposeModalDialog (v) {
      this.addOrEditPlantDialog = this.addOrEdit[v]
      if (v === 'IS_ADD') {
        this.addOrEditDialogTitle = '添加种植信息'
        this.plant.setId(null)
        this.model.id = null
        this.model.categoryid = null
        this.model.categoryname = null
        this.model.breed = null
        this.model.breedname = null
        this.model.batch = null
        this.model.begintime = null
        this.model.peroid = null
        this.model.age = null
        this.model.area = null
        this.model.number = null
        this.model.introduce = null
        this.model.attachments = []
        this.model.products = []
        this.$refs['plantForm'] ? this.$refs['plantForm'].reset() : null
        this.model.peroidunit = '年'
        this.model.ageunit = '年'
        this.model.numberunit = '株'
        this.productkinds = []
        // this.fetchProductKind()
        this.fetchProductType()
        this.fetchPrecategorys()
      } else if (v === 'IS_EDIT') {
        this.addOrEditDialogTitle = '编辑种植信息'
        // this.fetchProductKind()
        this.fetchProductType()
        this.fetchPrecategorys()
        this.fetchPlant()
      } else {
        this.addOrEditDialogTitle = ''
      }
    },
    // 点击添加产量信息触发
    addProductive () {
      // this.productiveid = 0
      // this.addOrEditProductiveDialog = 'IS_ADD'
      this.saveBeforeAddOrEdit('IS_ADD', 0)
    },
    // 点击编辑产量信息触发
    editProductive (productiveid) {
      // this.productiveid = productiveid
      // this.addOrEditProductiveDialog = 'IS_EDIT'
      this.saveBeforeAddOrEdit('IS_EDIT', productiveid)
    },
    // 点击删除产量信息触发
    deleteProductive (productiveid) {
      this.productiveid = productiveid
      this.operateFlag = 'tips'
      this.prompt.title = '提示信息信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.ok = true
      this.prompt.dialog = true
    },
    // 点击查看产量信息触发
    viewProductive (productiveid) {
      this.productiveid = productiveid
      this.viewProductiveDialog = true
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
  },
  components: {
    VProductiveaddedit,
    VProductiveview,
    VDropzoneupload
  }
}

</script>

<style scoped>
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.notes {
  /* padding-top: 45px; */
  height: 300px;
  overflow-y: auto;
}
</style>
