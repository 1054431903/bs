<template lang="pug">
  v-modaldialog(
    :isShow.sync="selectedPickerTimeDialog"
    :title="selectedPickerTimeTitle"
    :showTitle="false"
    :modalMaxWidth="325"
    :bodyHeight="bodyheight")
    div
      v-date-picker(v-model="date" type="date" locale="cn-zh")
      v-layout(row justify-center v-if="showtime")
        v-flex(xs1) 
          div(class="hour") 时
        v-flex(xs3 justify-center)
          v-select(
            v-bind:items="hours"
            v-model="hour"
            single-line
            hide-details
            bottom)
        .ml-2   
        v-flex(xs1)
          div(class="hour") 分 
        v-flex(xs3 justify-center)
          v-select(
            v-bind:items="minutes"
            v-model="minute"
            single-line
            hide-details
            bottom)
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 完成             
</template>

<script>
import { parseTime } from '@/utils'
export default {
  name: 'v-datetimepicker',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '选择日期时间'
    },
    datetime: {
      type: String,
      default: () => { return (new Date()).toLocaleString() }
    },
    isend: {
      type: Boolean,
      default: false
    },
    showtime: {
      type: Boolean,
      default: true
    },
    bodyheight: {
      type: Number,
      default: 455
    }
  },
  data () {
    return {
      date: '',
      selectedPickerTimeTitle: '选择日期时间',
      selectedPickerTimeDialog: false,
      hour: '00',
      minute: '00'
    }
  },
  components: {},
  computed: {
    hours () {
      let hs = []
      for (let i = 0; i < 24; i++) {
        let h = i < 10 ? ('0' + String(i)) : String(i)
        hs.push(h)
      }
      return hs
    },
    minutes () {
      let ms = []
      for (let i = 0; i < 60; i++) {
        let m = i < 10 ? ('0' + String(i)) : String(i)
        ms.push(m)
      }
      return ms
    }
  },
  mounted () { },
  watch: {
    selectedPickerTimeDialog: function (v) {
      v ? this.initDateTime(this.datetime) : null
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      this.selectedPickerTimeDialog = v
    },
    title: function (v) {
      this.selectedPickerTimeTitle = v
    }
  },
  methods: {
    save () {
      let selectedDateTime = this.date === '' ? new Date() : new Date(this.date)
      let datetime = parseTime(selectedDateTime, 'yyyy-MM-dd')
      this.$emit('update:datetime', datetime + ' ' + this.hour + ':' + this.minute)
      this.selectedPickerTimeDialog = false
    },
    cancel () {
      console.log('执行人选择取消')
      this.selectedPickerTimeDialog = false
    },
    initDateTime (v) {
      console.log(this.isend)
      console.log(this.datetime)
      let now = new Date()
      this.datetime ? null : this.date = parseTime(now, 'yyyy-MM-dd 00:00')
      // 时间为空时默认时分)
      if (!this.isend) {
        this.hour = '00'
        this.minute = '00'
      } else {
        this.hour = '23'
        this.minute = '59'
      }
      if (!v) return
      this.date = v.substr(0, 10)
      this.hour = v.substr(11, 2) || (this.isend ? '23' : '00')
      this.minute = v.substr(14, 2) || (this.isend ? '59' : '00')
    }
  },
  created () {
    this.selectedPickerTimeDialog = this.visible
    this.selectedPickerTimeTitle = this.title
  }
}
</script>
<style scoped>
.hour {
  padding-top: 18px;
  line-height: 30px;
}
</style>
