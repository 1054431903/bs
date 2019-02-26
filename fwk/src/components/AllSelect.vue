<template>
  <v-autocomplete :items="items"
                  v-model="selectItems"
                  label="请选择地块"
                  item-text="name"
                  item-value="id"
                  multiple
                  chips
                  :readonly="readonly">
  </v-autocomplete>
</template>

<script>
export default {
  name: 'all-selects',
  props: {
    itemsArr: {
      type: Array
    },
    typeName: {
      type: String
    },
    selects: { // 针对mounted的时候ref做的扩展
      type: Array,
      default: []
    },
    readonly: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      selectItems: [],
      items: []
    }
  },
  created () {
    console.log(this.selects)
    this.selectItems = this.selects || []
    let allSelects = [{ name: this.typeName, id: 'all' }]
    this.items = [...allSelects, ...this.itemsArr]
  },
  watch: {
    selects () {
      this.selectItems = this.selects
    },
    selectItems (curVal, oldVal) {
      this.selectAll(curVal, oldVal)
    }
  },
  methods: {
    selectAll (val, oldOptions) {
      let allValues = []
      // 保留所有值
      for (let item of this.items) {
        allValues.push(item)
      }
      // 用来储存上一次的值，可以进行对比
      const oldVal = oldOptions.length === 0 ? {} : oldOptions[0]
      // 若是全部选择
      if (val.includes('all')) {
        this.selectItems = allValues
      }
      // 取消全部选中上次有当前没有表示取消全选
      if (oldVal.id === 'all' && !val.includes('all')) {
        this.selectItems = []
      }
      // 点击非全部选中，需要排除全部选中以及当前点击的选项
      // 新老数据都有全部选中
      if (oldVal.id === 'all' && val.includes('all')) {
        const index = val.indexOf('all')
        val.splice(index, 1) // 排除全选选项
        this.selectItems = val
      }
      // 全选未选,但是其他选项全部选上,则全选选上,上次和当前都没有全选
      if (!oldVal.id === 'all' && !val.includes('all')) {
        if (val.length === this.items.length - 1) {
          this.selectItems = ['all'].concat(val)
        }
      }
      // 储存当前最后的结果作为下次的老数据
      oldOptions = this.selectItems
    }
  }
}
</script>
