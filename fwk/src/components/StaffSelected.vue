<template lang="pug">
div()
  v-modaldialog(
    :isShow="selectedStaffDialog",
    :title="selectedStaffTitle",
    :modalMaxWidth="400",
    :bodyHeight="300")
    div
      v-layout(row)
        v-flex(xs12)
          v-text-field(
            append-icon="search"
            v-model="keywords"
            :title="searchlabel"
            :label="searchlabel"
            @keyup.enter="searchStaff"
            @click:append="searchStaff"
          )
        //- v-flex(xs1)
        //-   v-btn(color="primary" @click="searchStaff") 查询
      div(v-if="searchedstaff && searchedstaff.length > 0")
        template(v-for="(staff, index) in searchedstaff")
          v-layout(row @mouseover="setCurStaff(staff)" v-bind:class="{isselected: curStaff && (curStaff.id === staff.id)}")
            v-flex(xs9)
              v-checkbox.pt-0.mt-0(color="primary" hide-details v-model="selectedstaff" :title="`${ appendKeyStafflabel(staff) }`" :label="`${ appendKeyStafflabel(staff) }`" :value="staff.id")
            v-flex(xs3 v-if="findEleInArray(staff.id, selectedstaff)")
              //- v-avatar(size="30")
              //-   img(src=`static/img/1.png` class="keyStaffSetImg" :title="(staff.id === keystaffs) ? '取消该负责人':'设置为负责人'" @click="setKeyStaff(staff)") 
              div(v-if="showKeystaffBtn" class="keyStaffLabel" @click="setKeyStaff(staff)") 设置为负责人   
      div(v-else)
        v-layout(row)
          v-flex(xs12)
            span 未搜索到任何数据，请重新输入姓名进行搜索
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 完成 
  v-modaldialog(
    :isShow="setKeyStaffDialog"
    title="提示信息"
    :modalMaxWidth="450")
    v-layout(row)
      v-flex(xs12)
        span {{ setKeyStaffDialogContent }}
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancelTips") 取消
        v-btn(flat color="primary" @click.native="saveSet") 确认             
</template>

<script>
import { findSingleElement } from '@/utils'
// import mongo from '@vehiclecloud/mongo-rest-client'
/**
 * 深拷贝数据
 */
function deepClone (obj) {
  let _obj = JSON.stringify(obj)
  let objClone = JSON.parse(_obj)
  return objClone
}
export default {
  name: 'v-selectedstaff',
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
    stafflist: {
      type: [Array, Boolean],
      default: () => []
    },
    staffs: {
      type: Array,
      default: () => []
    },
    keystaffs: {
      type: String,
      default: ''
    },
    searchlabel: {
      type: String,
      default: '请输入名称进行搜索'
    },
    showKeystaffBtn: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      keywords: '',
      searchedstaff: [],
      selectedstaff: [],
      cachaSelected: [],
      selectedStaffTitle: '',
      selectedStaffDialog: false,
      keystaffimgtitle: '设置为负责人',
      curStaff: null,
      keyStaffId: '',
      setKeyStaffDialog: false
    }
  },
  components: {},
  computed: {
    setKeyStaffDialogContent () {
      if (!this.curStaff || !this.keyStaffId) return ''
      let srcKeyStaff = this.stafflist.filter((el) => {
        return el.id === this.keyStaffId
      })
      return `确认将任务执行负责人从“${srcKeyStaff[0].name}”变更为：${this.curStaff.name}吗？`
    }
  },
  mounted () { },
  watch: {
    selectedStaffDialog: function (v) {
      // 在此要重新触发查询,否则会导致记录上次状态
      this.keywords = ''
      this.selectedstaff = this.staffs
      this.searchedstaff = this.stafflist
      v ? this.keyStaffId = this.keystaffs : null
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      this.selectedStaffDialog = v
    },
    title: function (v) {
      this.selectedStaffTitle = v
    },
    selectedstaff: function (v) {
      // 监听选择列表
      if (v.length !== 0) {
        !this.keystaffs ? this.keyStaffId = v[0] : null
        if (v.indexOf(this.keyStaffId) === -1) {
          this.keyStaffId = v[0]
        }
      }
    },
    staffs: function (v) {
      this.selectedstaff = v
    },
    stafflist: function (v) {
      this.searchedstaff = v
    },
    keywords: function (v) {
      this.searchStaff()
    }
  },
  methods: {
    save () {
      this.$emit('update:keystaffs', this.keyStaffId)
      this.$emit('update:staffs', this.selectedstaff)
      this.selectedStaffDialog = false
    },
    cancel () {
      this.selectedStaffDialog = false
    },
    searchStaff () {
      let _this = this
      this.cachaSelected = deepClone(_this.selectedstaff)
      _this.searchedstaff = _this.stafflist.filter((el) => {
        let name = el.name || el.mobile
        return name.indexOf(_this.keywords.trim()) !== -1
      })
      _this.selectedstaff = []
      _this.selectedstaff = this.cachaSelected
    },
    setKeyStaff (staff) {
      if (!staff) return
      this.setKeyStaffDialog = true
    },
    findEleInArray (el, els) {
      return findSingleElement(el, els) &&
        (this.curStaff && this.curStaff.id === el) &&
        (el !== this.keyStaffId) &&
        this.keyStaffId
    },
    appendKeyStafflabel (staff) {
      if (staff.id === this.keyStaffId && this.showKeystaffBtn) {
        return staff.name + '（负责人）'
      }
      return staff.name
    },
    setCurStaff (staff) {
      this.curStaff = staff
    },
    cancelTips () {
      this.setKeyStaffDialog = false
    },
    saveSet () {
      this.keyStaffId = this.curStaff.id
      this.setKeyStaffDialog = false
    }
  },
  created () {
    this.selectedStaffDialog = this.visible
    this.selectedStaffTitle = this.title
  }
}
</script>
<style scoped>
.keyStaffSetImg {
  cursor: pointer;
}
.isselected {
  background-color: #fafafa;
}
.keyStaffLabel {
  color: #32c787;
  line-height: 30px;
  cursor: pointer;
}
</style>
