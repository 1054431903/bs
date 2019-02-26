<template>
  <v-layout row>
    <v-flex xs12>
      <el-date-picker
        v-bind:class="{'el-input__inner_error': message, 'el-input__inner_normal': !message}"
        :label="placeholdertext"
        local="zh-cn"
        :type="datetype"
        v-model="currentDate">
      </el-date-picker>
      <div v-if="message" class="input-group__details"><div class="input-group__messages input-group__error error--text">{{ message }}</div></div>
    </v-flex>
  </v-layout>
</template>

<script>
import { parseTime } from '@/utils'

export default {
  name: 'element-date-picker',
  props: {
    selectedDate: {
      type: String,
      default: ''
    },
    datetype: {
      type: String,
      default: 'date'
    },
    placeholdertext: {
      type: String,
      default: '请选择日期'
    },
    rules: {
      type: Array,
      default: () => [
        (v) => !!v || '必填项',
        (v) => this.checkTime(v) || '日期选择无效'
      ]
    }
  },
  data () {
    return {
      currentDate: '',
      message: ''
    }
  },
  watch: {
    currentDate (v) {
      if (this.isValid(v)) {
        this.$emit('update:selectedDate', parseTime(v))
      }
    },
    selectedDate (v) {
      this.currentDate = new Date(v)
    }
  },
  methods: {
    isValid (v) {
      this.message = ''
      let rules = this.rules
      if (rules && rules.length === 0) return true
      for (let i = 0; i < rules.length; i++) {
        let fn = rules[i]
        if (typeof fn(v) !== 'boolean') {
          this.message = fn(v)
          return false
        }
      }
      return true
    },
    checkTime (v) {
      let currentTime = parseTime(new Date(), 'yyyy-MM-dd')
      let selectTime = parseTime(v, 'yyyy-MM-dd')
      return selectTime <= currentTime
    }
  },
  created () {}
}
</script>

<style scoped>
.el-date-editor.el-input {
  width: 100%;
}
.el-input__inner_error {
  border: 0px solid #b71c1c;
  border-bottom-width: 0.5px;
}
.el-input__inner_normal {
  border: 0px solid rgb(169, 169, 169);
  border-bottom-width: 0.5px;
  border-radius: 0px;
  border-color: rgb(169, 169, 169);
}
</style>
