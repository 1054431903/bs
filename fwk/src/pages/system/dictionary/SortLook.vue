<template>
  <v-container pa-0
               pb-4>
    <v-card class="page-content pt-4">
      <!-- <v-layout row
              wrap>
      <go-back :name="viewTitle"></go-back>
    </v-layout> -->
      <v-layout row
                wrap>
        <v-flex xs1
                py-2
                text-xs-center
                class="titleColor">
          类型
        </v-flex>
        <v-flex xs11
                py-2>
          {{typeName}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs1
                py-2
                text-xs-center
                class="titleColor">
          名称
        </v-flex>
        <v-flex xs11>
          {{name}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs1
                py-2
                text-xs-center
                class="titleColor">
          创建时间
        </v-flex>
        <v-flex xs11
                py-2>
          {{creattime}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs1
                py-2
                text-xs-center
                class="titleColor">
          创建人
        </v-flex>
        <v-flex xs11
                py-2>
          {{userName}}
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs1
                py-2
                text-xs-center
                class="titleColor">
          描述
        </v-flex>
        <v-flex xs11>
          {{descript}}
        </v-flex>
      </v-layout>
      <v-layout row>
        <v-fle xs12
               py-2>
          <div style="height:400px;"></div>
        </v-fle>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
import { excuteApis } from '@/apis'

export default {
  data () {
    return {
      id: '',
      sortName: '',
      parentcode: '',
      parentid: null,
      name: '',
      typeName: '',
      descript: '',
      creattime: '',
      userName: ''
    }
  },
  created () { // 创建vue实例后调用的方法
    this.fetchData() // 定义获取数据的方法
    // console.log(this.$route.query)
  },
  computed: {
    viewTitle () {
      let title = '字典详情'
      this.name ? (title = this.name) : ''
      return title
    }
  },
  methods: {
    fetchData () {
      this.id = this.$route.query.id
      this.sortName = this.$route.query.sortName
      this.parentcode = this.$route.query.parentcode
      this.parentid = this.$route.query.parentid
      this.name = this.$route.query.name
      this.typeName = this.$route.query.typeName
      this.descript = this.$route.query.descript
      this.creattime = this.$route.query.creattime
      let requestParams = {}
      requestParams = this.parentcode ? Object.assign(requestParams, { parentcode: this.parentcode }) : requestParams
      requestParams = this.id ? Object.assign(requestParams, { id: this.id }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data && res.data.length > 0) {
          this.userName = res.data[0].userName
          this.parentid = res.data[0].parentid
        }
      })
    },
    // 点击“编辑”跳转
    editPage (id, name, sortName, parentcode) {
      // const router = this.$router
      this.$router.push({ name: 'sortedit', query: { id: id, parentid: this.parentid, name: name, sortName: sortName, parentcode: parentcode } })
      // console.log(this.$router)
    },
    // 返回到角色管理首页
    back () {
      this.$router.push({ name: 'sysdictionary' })
    }
  }
}
</script>

<style scoped>
.titleColor {
  color: rgba(0, 0, 0, 0.54);
}
</style>
