<template lang="pug">
div()
  v-modaldialog(
    :isShow="selectedLandDialog",
    :title="selectedLandTitle",
    :modalMaxWidth="620",
    :bodyHeight="300")
    v-layout(row)
      v-flex(xs6 class="arealeft")
        v-layout(row)
          v-flex(xs12 class="tiplabel") 可选地块（{{landlist.length}}块）
        v-divider
        v-layout(row)
          v-flex.pl-2.pr-3.pb-2(xs12)
            v-text-field(
              append-icon="search"
              @click:append="searchData"
              v-model="filterText"
              label="请输入地块名称/编号进行搜索"
              hide-details
              @keyup.enter="searchData")
        v-layout(row)
          v-flex.pl-2(xs12)
            v-checkbox(v-model="selectedAll" label="全部" hide-details ref="all" :indeterminate="indeterminate")
        template(v-for="(land, index) in searchedland")
          v-layout(row)
            v-flex.pl-4(xs12)
              v-checkbox.pt-0.mt-0(v-model="selected" :label="land.name" :value="land._id.$oid" hide-details :ref="land._id.$oid" :title="land.name")  
      v-flex(xs6 class="arearight")
        v-layout(row)
          v-flex(xs12 class="tiplabel") 已选地块（{{selected.length}}块）
        v-divider     
        div(class="selectedarea")
          template(v-for="(landid, index) in selected")
            v-layout(row @mouseover="setCurLand(landid)" v-bind:class="{isselected: curLandid === landid}")
              v-flex(xs10)
                div(class="landname" :title="getLandNameById(landid)") {{ getLandNameById(landid) }}
              v-flex.py-1(xs2 text-xs-center v-if="findEleInArray(landid)")
                v-avatar(size="18" class="grey lighten-1")
                  img(src=`static/sop/ic_close_white_24px.svg` class="keyLandSetImg" title="点击移除" @click="removeLand(landid)")            
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 完成            
</template>

<script>
export default {
  name: 'v-selectedland',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    multiselected: {
      type: Boolean,
      default: false
    },
    issearch: {
      type: Boolean,
      default: false
    },
    lands: {
      type: Array,
      default: () => []
    },
    landlist: {
      type: Array,
      default: () => []
    }
  },
  data () {
    const SELECTED_STATE = {
      ALL_SELECTED: 1,
      ALL_UNSELECTED: 0,
      PART_SELECTED: -1
    }
    return {
      searchedland: [],
      selectedLandTitle: '',
      selectedLandDialog: false,
      curLandid: null,
      selected: [],
      selectedAll: false,
      filterText: '',
      cacheSelected: [],
      selectedAllState: SELECTED_STATE['ALL_UNSELECTED'],
      indeterminate: false
    }
  },
  components: {},
  computed: {
    disabled () {
      return this.indeterminate
    }
  },
  mounted () { },
  watch: {
    selectedLandDialog: function (v) {
      this.selected = this.lands
      this.filterText = ''
      // this.selectedAll = null
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      this.selectedLandDialog = v
    },
    title: function (v) {
      this.selectedLandTitle = v
    },
    landlist: function (v) {
      // console.log(v)
      this.searchedland = v
    },
    selected: function (v, o) {
      let counter = this.isCurAllSelected()
      if (counter === this.searchedland.length) {
        this.selectedAllState = 1
      } else if (this.searchedland.length > counter && counter !== 0) {
        this.selectedAllState = 0
      } else {
        this.selectedAllState = -1
      }
      this.selectedAllState === 0 ? (this.indeterminate = true) : (this.indeterminate = false)
      if (this.selectedAllState === 1) {
        this.selectedAll = true
      } else if (this.selectedAllState === -1) {
        this.selectedAll = false
      }
    },
    selectedAll: function (v, o) {
      let isall = v
      // 如果处于半选状态
      if (this.indeterminate) {
        isall = true
      }
      this.selectedAllFn(isall)
    },
    filterText: function (v) {
      this.searchData()
    }
  },
  methods: {
    save () {
      this.$emit('update:lands', this.selected)
      this.selectedLandDialog = false
    },
    cancel () {
      this.selectedLandDialog = false
    },
    setCurLand (landid) {
      this.curLandid = landid
    },
    findEleInArray (el) {
      return this.curLandid === el
    },
    removeLand (landid) {
      let pos = this.selected.indexOf(landid)
      this.selected.splice(pos, 1)
    },
    selectedAllFn (v) {
      this.searchedland.map((land) => {
        let landid = land['_id'].$oid
        if (v) {
          this.selected.indexOf(landid) === -1 ? this.selected.push(landid) : null
        } else {
          let pos = this.selected.indexOf(landid)
          this.selected.indexOf(landid) !== -1 ? this.selected.splice(pos, 1) : null
        }
      })
    },
    searchData () {
      let keywords = this.filterText.trim()
      if (keywords !== '' && keywords) {
        // this.selected.map(item => {
        //   let index = this._.findIndex(this.cacheSelected, item)
        //   console.log(index)
        //   index < 0 ? this.cacheSelected.push(item) : null
        // })
        this.searchedland = this.landlist.filter((item) => {
          let name = item.name
          return name.indexOf(keywords) !== -1
        })
      } else {
        this.searchedland = this.landlist
      }
    },
    getLandNameById (landid) {
      let lan = this._.find(this.landlist, { _id: { $oid: landid } })
      return lan.name
    },
    isCurAllSelected () {
      let flag = 0
      this.searchedland.map(land => {
        let landid = land['_id']['$oid']
        flag += (this.selected.indexOf(landid) !== -1) ? 1 : 0
      })
      return flag
    }
  },
  created () {
    this.selectedLandDialog = this.visible
    this.selectedLandTitle = this.title
  }
}
</script>
<style scoped>
.arealeft {
  border: 1px solid rgb(235, 228, 228);
  /* padding: 15px 15px; */
  margin-right: 2px;
}
.arearight {
  border: 1px solid rgb(235, 228, 228);
  /* padding: 15px 15px; */
  margin-left: 2px;
}
.keyLandSetImg {
  cursor: pointer;
}
.isselected {
  background-color: #fafafa;
}
.clearpaddingtop {
  padding-top: 0px;
}
.selectedarea {
  margin-top: 15px;
}
.delimg {
  padding-top: 5px;
}
.tiplabel {
  padding: 15px 15px;
  color: #32c787;
}
.labelborder {
  /* border-bottom-color: #fafafa; */
  border-bottom: 1px solid red;
}
.landname {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  /* max-width: 10px; */
  line-height: 35px;
  padding-left: 10px;
  /* border: 1px solid red; */
}
</style>
