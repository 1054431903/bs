<template>
  <v-container pa-0
               pb-4>
    <v-card class="page-content">
      <!-- <v-layout justify-space-between
              style="border-bottom: 1px solid #ddd;padding-bottom: 10px">
      <v-flex style="margin-top: 15px">编辑</v-flex>
      </v-layout> -->
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
            <v-text-field v-model="items.name"
                          label="请输入类别名称"
                          single-line
                          counter="20"
                          maxlength="20"
                          @focus="handInput(1)"
                          :rules="[() => !!items.name || '请输入名称']"></v-text-field>
          </v-flex>
        </v-layout>

        <v-layout row>
          <v-flex xs1
                  mt-4
                  text-xs-center>
            描述
          </v-flex>
          <v-flex xs7>
            <v-textarea v-model="items.descript"
                        label="请输入类别描述"
                        rows="3"
                        no-resize
                        type="text"
                        counter="200"
                        maxlength="200"
                        @focus="handInput(2)"></v-textarea>
            <span v-show="descriptText"
                  class="isFund">输入字数超出限制范围</span>
          </v-flex>
        </v-layout>
      </v-form>
      <v-layout row>
        <v-flex xs1></v-flex>
        <v-flex xs7>
          <v-btn @click="backPage(parentcode, sortName)"
                 style="background-color:#ffffff">取消</v-btn>
          <v-btn @click="updateData(parentcode, sortName)"
                 color="primary">保存</v-btn>
        </v-flex>
      </v-layout>
      <v-layout row>
        <v-flex xs12>
          <div style="height:400px;"></div>
        </v-flex>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
// import { querySystemDictTypeEdit, querySystemDictTypeDetailsUpdate } from '@/apis/systems/dictionary'
import { excuteApis } from '@/apis'
export default {
  data () {
    return {
      sortName: '',
      parentcode: '',
      parentid: null,
      items: {},
      typeItems: [],
      isName: false,
      nameText: false,
      descriptText: false
    }
  },
  watch: {
    'items.name': function (val, oldVal) {
      if (!val) {
        this.isName = true
      } else {
        this.isName = false
      }
      if (oldVal) {
        if (this.items.name.length >= 20) {
          this.nameText = true
        } else {
          this.nameText = false
        }
      }
    },
    'items.descript': function (val, oldVal) {
      if (oldVal) {
        if (this.items.descript.length >= 200) {
          this.descriptText = true
        } else {
          this.descriptText = false
        }
      }
    }
  },
  created () { // vue 实例化后调用的函数
    this.fetchData() // 定义一个获取数据的方法（实例化后执行该方法）
    // console.log('==========+++++++')
    // console.log(this.$route.query.items)
  },
  methods: {
    fetchData () {
      let reParams = {}
      excuteApis(reParams, global.constant.systemApis, 'tsdictType', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.typeItems = res.data
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
      let id = this.$route.query.id
      // 获取上一个页面传过来的字典名称
      let name = this.$route.query.name
      // 发送请求，取出匹配的数据
      // querySystemDictTypeEdit(id, name).then(data => {
      //   let res = data.data
      //   // console.log('============')
      //   // console.log(res)
      //   if (res.flag === 1) {
      //     this.items = res.data[0]
      //   }
      // })
      let requestParams = {}
      requestParams = id ? Object.assign(requestParams, { id: id }) : requestParams
      requestParams = name ? Object.assign(requestParams, { name: name }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.items = res.data[0]
          this.parentid = this.items.parentid
          console.log(this.items)
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
    },
    // 类别名称、描述超出字数范围提示
    handInput (event) {
      if (event === 1) {
        let length = this.items.name.length
        if (length >= 20) {
          this.nameText = true
        } else {
          this.nameText = false
        }
      } else if (event === 2) {
        let length = this.items.descript.length
        if (length >= 200) {
          this.descriptText = true
        } else {
          this.descriptText = false
        }
      }
    },
    // 取消
    backPage () {
      const router = this.$router
      router.push({ name: 'sysdictionary' })
    },
    /**
    * 验证表单
    */
    valdateForm () {
      return !this.$refs.form.validate()
    },
    // 点击 “保存”时，将用户修改的信息提交到后台
    updateData (parentcode, sortName) {
      let formHasErrors = this.valdateForm() // 验证表单
      if (formHasErrors) return
      let requestParams = {}
      requestParams = this.items.name ? Object.assign(requestParams, { name: this.items.name }) : requestParams
      requestParams = this.parentid ? Object.assign(requestParams, { parentid: this.parentid }) : requestParams
      requestParams = this.items.id ? Object.assign(requestParams, { id: this.items.id }) : requestParams
      requestParams = Object.assign(requestParams, { descript: this.items.descript })
      console.log(requestParams)
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'update').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.$toast.top('修改成功！')
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

