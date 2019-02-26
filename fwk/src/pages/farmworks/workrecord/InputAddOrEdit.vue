<template>
  <v-modaldialog :isShow="addOrEditInputDialog"
                 :title="addOrEditDialogTitle"
                 :modalMaxWidth="650"
                 :bodyHeight="400">
    <v-form v-model="inputFormValid"
            ref="inputForm"
            lazy-validation
            autocomplete="off">
      <v-layout row
                wrap>
        <v-flex xs12>
          <v-autocomplete label="投入品类别"
                          v-model="curCategory"
                          :items="inputCategorys"
                          item-text="name"
                          item-value="code"
                          no-data-text="暂无数据"
                          clearable
                          return-object
                          :rules="rules.required">
          </v-autocomplete>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12>
          <v-text-field label="投入品名称"
                        v-model="inputName"
                        :rules="rules.name"
                        counter="50"
                        maxlength="50">

          </v-text-field>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12>
          <v-text-field label="投入品规格"
                        v-model="inputSpecification"
                        counter="20"
                        maxlength="20">

          </v-text-field>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs7>
          <v-text-field label="投入品用量"
                        v-model="inputQuantity"
                        :rules="rules.inputQuantity">

          </v-text-field>
        </v-flex>
        <v-flex xs1></v-flex>
        <v-flex xs4>
          <v-autocomplete label="单位"
                          v-model="curQuantityUnit"
                          :items="quantityUnits"
                          item-text="name"
                          item-value="code"
                          no-data-text="暂无数据"
                          clearable
                          return-object
                          :rules="rules.required">
          </v-autocomplete>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12>
          <v-text-field label="投入品厂家"
                        v-model="inputVender"
                        counter="50"
                        maxlength="50">

          </v-text-field>
        </v-flex>
      </v-layout>
    </v-form>
    <template slot="btnActions">
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary"
               flat
               @click.native="cancel">
          取消
        </v-btn>
        <v-btn color="primary"
               flat
               @click.native="save">
          完成
        </v-btn>
      </v-card-actions>
    </template>
  </v-modaldialog>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import { CInput } from '@/models/input'
export default {
  name: 'v-inputaddoredit',
  props: {
    visible: {
      type: String,
      default: 'IS_NONE'
    },
    addinput: {
      type: Object,
      default: () => { }
    },
    // 待编辑的投入品对象
    editinput: {
      type: Object,
      default: () => { }
    },
    // 编辑完成后的投入品对象
    editedinput: {
      type: Object,
      default: () => { }
    }
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_NONE': false
    }
    return {
      // 当前操作窗口类型
      curOperate: 'IS_NONE',
      // 表格校验
      inputFormValid: false,
      // 添加或编辑投入品弹窗
      addOrEditInputDialog: false,
      // 标题
      addOrEditDialogTitle: '添加投入品',
      // 状态常量
      addOrEdit: ADD_OR_EDIT,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        name: [
          (v) => !!v && !!(v.trim()) || '必填项'
        ],
        inputQuantity: [
          (v) => !!v || '必填项',
          (v) => {
            let reg = /^\d+(\.\d+)?$/
            return reg.test(v) || '输入错误,请输入正确的数字'
          },
          (v) => (v && Number(v) <= 10000) || '范围为：0~10000'
        ]
      },
      // 当前投入品类别对象
      curCategory: null,
      // 当前投入品单位对象
      curQuantityUnit: null,
      // 投入品类别列表
      inputCategorys: [],
      // 投入品用量单位列表
      quantityUnits: [],
      // 投入品名称
      inputName: null,
      // 投入品规格
      inputSpecification: null,
      // 投入品用量
      inputQuantity: null,
      // 投入品厂家
      inputVender: null
    }
  },
  watch: {
    visible (v) {
      this.curOperate = v
      this.disposeModalDialog(v)
    }
  },
  computed: {
    user () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    },
    tenant () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  mounted () {
    this.getInputCategorys()
    this.getQuantityUnits()
  },
  methods: {
    save () {
      if (this.$refs['inputForm'].validate()) {
        switch (this.curOperate) {
          case 'IS_ADD':
            let addInputModel = new CInput()
            addInputModel.id = new Date().getTime()
            addInputModel.category = this.curCategory.code
            addInputModel.categoryName = this.curCategory.name
            addInputModel.name = this.inputName
            addInputModel.specification = this.inputSpecification && this.inputSpecification.trim() ? this.inputSpecification.trim() : null
            addInputModel.quantity = Number(Number(this.inputQuantity).toFixed(2))
            addInputModel.quantityUnit = this.curQuantityUnit.code
            addInputModel.quantityUnitName = this.curQuantityUnit.name
            addInputModel.quantityStr = `${Number(Number(this.inputQuantity).toFixed(2))}${this.curQuantityUnit.name}`
            addInputModel.vender = this.inputVender && this.inputVender.trim() ? this.inputVender.trim() : null
            addInputModel.creator = { $oid: this.user }
            this.$emit('update:inputitem', addInputModel)
            break
          case 'IS_EDIT':
            let editInputModel = new CInput(this.editinput)
            editInputModel.category = this.curCategory.code
            editInputModel.categoryName = this.curCategory.name
            editInputModel.name = this.inputName
            editInputModel.specification = this.inputSpecification && this.inputSpecification.trim() ? this.inputSpecification.trim() : null
            editInputModel.quantity = Number(Number(this.inputQuantity).toFixed(2))
            editInputModel.quantityUnit = this.curQuantityUnit.code
            editInputModel.quantityUnitName = this.curQuantityUnit.name
            editInputModel.quantityStr = `${Number(Number(this.inputQuantity).toFixed(2))}${this.curQuantityUnit.name}`
            editInputModel.vender = this.inputVender && this.inputVender.trim() ? this.inputVender.trim() : null
            editInputModel.creator = { $oid: this.user }
            this.$emit('update:editedinput', editInputModel)
            break
          default:
            break
        }
        this.$emit('update:visible', 'IS_NONE')
      }
    },
    cancel () {
      this.$emit('update:visible', 'IS_NONE')
    },
    getInputCategorys () {
      let match = {}
      match._removed = { $exists: false }
      match.tenant = { $oid: this.tenant }
      let sort = { sort: 1 }
      mongo.db().collection('inputCategory').aggregate([{ $match: match }, { $sort: sort }]).then(res => {
        this.inputCategorys = res
      })
    },
    getQuantityUnits () {
      let match = {}
      match._removed = { $exists: false }
      match.dict = 'input-quantity-unit'
      let sort = { sort: 1 }
      mongo.db().collection('dict').aggregate([{ $match: match }, { $sort: sort }]).then(res => {
        this.quantityUnits = res
      })
    },
    disposeModalDialog (v) {
      this.addOrEditInputDialog = this.addOrEdit[v]
      switch (v) {
        case 'IS_NONE':
          this.addOrEditDialogTitle = ''
          break
        case 'IS_ADD':
          this.addOrEditDialogTitle = '添加投入品'
          this.curCategory = null
          this.inputName = null
          this.inputSpecification = null
          this.inputQuantity = null
          this.inputQuantityUnit = null
          this.inputVender = null
          this.curQuantityUnit = null
          break
        case 'IS_EDIT':
          this.addOrEditDialogTitle = '编辑投入品'
          this.curCategory = this.inputCategorys.find(elem => { return elem.code === this.editinput.category }) || null
          this.inputName = this.editinput.name
          this.inputSpecification = this.editinput.specification
          this.inputQuantity = this.editinput.quantity
          this.curQuantityUnit = this.quantityUnits.find(elem => { return elem.code === this.editinput.quantityUnit }) || null
          this.inputVender = this.editinput.vender
          break
        default:
          this.addOrEditDialogTitle = ''
          break
      }
    }
  }
}
</script>

