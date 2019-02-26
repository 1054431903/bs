<template>
  <div>
    <v-layout row
              v-if="isFilter && treedata.length > 0">
      <v-flex xs12
              sm12
              md12
              lg12
              xl12>
        <v-text-field append-icon="search"
                      @click:append="searchTreeData"
                      v-model="filterText"
                      label="输入名称关键词检索"
                      hide-details
                      @keyup.enter="searchTreeData"></v-text-field>
      </v-flex>
    </v-layout>
    <div class="my-1"></div>
    <v-layout row>
      <v-flex xs12
              sm12
              md12
              lg12
              xl12>
        <el-tree class="filter-tree"
                 :show-checkbox="isMultiSelect"
                 @check="checkedCallbackFn"
                 :data="treedata"
                 :props="defaultProps"
                 default-expand-all
                 :filter-node-method="filterNode"
                 node-key="id"
                 empty-text="暂无数据"
                 :expand-on-click-node="false"
                 :render-content="renderContent"
                 :ref="treeRef">
        </el-tree>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
const data = [{
  id: 1,
  label: '一级 1',
  children: [{
    id: 4,
    label: '二级 1-1',
    children: [{
      id: 9,
      label: '三级 1-1-1'
    }, {
      id: 10,
      label: '三级 1-1-2'
    }]
  }]
}, {
  id: 2,
  label: '一级 2',
  children: [{
    id: 5,
    label: '二级 2-1'
  }, {
    id: 6,
    label: '二级 2-2'
  }]
}, {
  id: 3,
  label: '一级 3',
  children: [{
    id: 7,
    label: '二级 3-1'
  }, {
    id: 8,
    label: '二级 3-2'
  }]
}]
export default {
  props: {
    treedata: {
      type: Array,
      default: () => { return data }
    },
    renderContent: {
      trype: Function,
      default: () => { }
    },
    isMultiSelect: {
      type: Boolean,
      default: false
    },
    isFilter: {
      type: Boolean,
      default: false
    },
    // clickCallbackFn: {
    //   type: Function,
    //   default: () => {}
    // },
    checkedCallbackFn: {
      type: Function,
      default: () => { }
    },
    iniDataState: {
      type: Array,
      default: () => []
    },
    resetDataState: {
      type: Boolean,
      default: false
    },
    treeRef: {
      type: String,
      default: 'eltree'
    },
    nodeChecked: {
      type: Object,
      default: () => { }
    }
  },
  data () {
    return {
      filterText: '',
      data: null,
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  watch: {
    treedata (val) {
      this.data = val
    },
    iniDataState (val) {
      if (this.isMultiSelect) {
        let rfname = this.treeRef
        this.$refs[rfname].setCheckedKeys(val)
      }
    },
    resetDataState (val) {
      if (val) {
        let initState = this.iniDataState
        let rfname = this.treeRef
        this.$refs[rfname].setCheckedKeys(initState)
      }
    },
    nodeChecked (v) {
      if (!v) return
      let rfname = this.treeRef
      this.$refs[rfname].setChecked(v['id'], v['state'])
    }
  },
  methods: {
    filterNode (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    searchTreeData () {
      let rfname = this.treeRef
      this.$refs[rfname].filter(this.filterText.trim())
    }
  },
  created () { }
}
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
