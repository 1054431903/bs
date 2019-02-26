<template>
  <v-container pa-0
               pb-4>
    <v-card class="page-content">
      <!-- <div style="height: 40px;border-bottom: 1px solid #ddd;">添加</div> -->
      <v-form ref="form">
        <v-layout row>
          <v-flex xs1
                  mt-4
                  text-xs-center>
            *类型
          </v-flex>
          <v-flex xs7>
            <v-select v-bind:items="typeItems"
                      v-model="parentid"
                      item-text="name"
                      item-value="id"
                      label="选择类型"
                      single-line
                      no-data-text="无"
                      clearable
                      ref="select"
                      :rules="[() => !!parentid || '请选择类型']"></v-select>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs1
                  mt-4
                  text-xs-center>
            *名称
          </v-flex>
          <v-flex xs7>
            <v-text-field v-model="name"
                          label="请输入名称"
                          single-line
                          counter="20"
                          maxlength="20"
                          :rules="[() => !!name || '请输入名称']"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs1
                  mt-4
                  text-xs-center>
            描述
          </v-flex>
          <v-flex xs7>
            <v-textarea v-model="descript"
                        label="请输入描述"
                        no-resize
                        rows="3"
                        type="text"
                        counter="200"
                        maxlength="200"></v-textarea>
          </v-flex>
        </v-layout>
      </v-form>
      <v-layout>
        <v-flex xs1></v-flex>
        <v-flex xs7>
          <v-btn style="background-color:#fff"
                 @click="backPage(parentcode, sortName)">取消</v-btn>
          <v-btn @click="saveData"
                 color="primary">保存</v-btn>
        </v-flex>

      </v-layout>
      <v-layout row>
        <v-fle xs12>
          <div style="height:400px;"></div>
        </v-fle>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
// import { querySystemDictTypeDetailsAdd } from '@/apis/systems/dictionary'
import { excuteApis } from '@/apis'
export default {
  data () {
    return {
      parentcode: '',
      sortName: '',
      typeItems: [],
      parentid: '',
      name: '', // 用户输入的名称
      descript: '',
      user: null,
      isName: false,
      nameText: false,
      descriptText: false
    }
  },
  watch: {
    parentid: function (val, oldVal) {
      this.parentcode = this.$refs.select.selectedItem.code
    }
    // descript: function (val, oldVal) {
    //   if (this.descript.length >= 200) {
    //     this.descriptText = true
    //   } else {
    //     this.descriptText = false
    //   }
    // }
  },
  created () { // vue 实例化后调用的函数
    this.fetchData() // 定义一个获取数据的方法（实例化后执行该方法）
  },
  methods: {
    fetchData () {
      this.user = global.helper.ls.get('user') // 获取当前登录用户信息
      console.log(this.user)
      let reParams = {}
      excuteApis(reParams, global.constant.systemApis, 'tsdictType', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.typeItems = res.data
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
    },
    // 点击 “取消”时，返回到类别管理页面
    backPage (parentcode) {
      const router = this.$router
      router.push({ name: 'sysdictionary' })
    },
    /**
       * 验证表单
       */
    valdateForm () {
      return !this.$refs.form.validate()
    },
    // 点击“保存”，获取用户输入的信息并提交到后台
    saveData () {
      console.log(this.$refs.select.selectedItem)
      let formHasErrors = this.valdateForm() // 验证表单
      if (formHasErrors) return
      let requestParams = {}
      requestParams = this.name ? Object.assign(requestParams, { name: this.name }) : requestParams
      requestParams = this.descript ? Object.assign(requestParams, { descript: this.descript }) : requestParams
      requestParams = this.parentcode ? Object.assign(requestParams, { parentcode: this.parentcode }) : requestParams
      requestParams = this.parentid ? Object.assign(requestParams, { parentid: this.parentid }) : requestParams
      requestParams = this.user.supportid ? Object.assign(requestParams, { userid: this.user.supportid }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'save').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.$toast.top(res.message)
          this.$router.push({ name: 'sysdictionary' })
        } else {
          this.$toast.top(res.message)
        }
      })
    }
  }
}
</script>

<style scoped>
.isFund,
.nameMust {
  color: red;
  display: flex;
  margin-top: -20px;
}
</style>
