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
        v-radio-group(v-model="selectedstaff" class="clearpaddingtop")
          template(v-for="(staff, index) in searchedstaff")
            v-layout(row @mouseover="setCurStaff(staff)" v-bind:class="{isselected: curStaff && (curStaff.id === staff.id)}")
              v-flex(xs12)
                v-radio(
                  color="primary" 
                  :key="staff.id"
                  :title="staff.name"
                  :label="staff.name"
                  :value="staff.id")
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
</template>

<script>
export default {
  name: 'v-singleselectedstaff',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    issearch: {
      type: Boolean,
      default: false
    },
    staffs: {
      type: String,
      default: ''
    },
    stafflist: {
      type: [Array, Boolean],
      default: () => []
    },
    searchlabel: {
      type: String,
      default: '请输入名称进行搜索'
    }
  },
  data () {
    return {
      keywords: '',
      searchedstaff: [],
      selectedstaff: '',
      selectedStaffTitle: '',
      selectedStaffDialog: false,
      curStaff: null
    }
  },
  components: {},
  computed: {},
  mounted () { },
  watch: {
    selectedStaffDialog: function (v) {
      // 在此可能要重新触发查询
      this.keywords = ''
      this.selectedstaff = this.staffs
      this.searchedstaff = this.stafflist
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      this.selectedStaffDialog = v
    },
    title: function (v) {
      this.selectedStaffTitle = v
    },
    staffs: function (v) {
      this.selectedstaff = v
    },
    keywords: function (v) {
      this.searchStaff()
    }
  },
  methods: {
    save () {
      this.$emit('update:staffs', this.selectedstaff)
      console.log('执行人选择完成')
      this.selectedStaffDialog = false
    },
    cancel () {
      console.log('执行人选择取消')
      this.selectedStaffDialog = false
    },
    searchStaff () {
      let _this = this
      _this.searchedstaff = _this.stafflist.filter((el) => {
        return el.name.indexOf(_this.keywords.trim()) !== -1
      })
    },
    setCurStaff (staff) {
      this.curStaff = staff
    }
  },
  created () {
    this.selectedStaffDialog = this.visible
    this.selectedStaffTitle = this.title
    this.searchedstaff = this.stafflist
    this.selectedstaff = this.staffs
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
.clearpaddingtop {
  padding-top: 0px;
}
</style>
