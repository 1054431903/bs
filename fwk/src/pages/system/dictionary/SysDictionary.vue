<template >
    <v-card class="page-content">
      <!-- <div style="width:100px;height: 40px;background-color: #fff;text-align: center;line-height: 40px">档案</div> -->
      <v-data-table
        v-bind:headers="headers"
        :items="items"
        no-data-text="暂无数据"
        hide-actions class="elevation-1">
        <template slot="items" slot-scope="props" >
          <!--档案字段类型-->
          <td >
            <v-flex  class="text-xs-left text-sm-left text-md-left text-lg-left">
              {{ props.item.name }}
            </v-flex>
          </td>
          <!--编码-->
          <!--<td >-->
            <!--<v-flex class="text-xs-left text-sm-left text-md-left text-lg-left">-->
              <!--{{ props.item.code }}-->
            <!--</v-flex>-->
          <!--</td>-->
          <!--操作-->
          <td>
            <v-flex v-if="$authed('GET','sysdictionary/sortmanage')" class="text-xs-center text-sm-center text-md-center text-lg-center">
              <v-btn fab flat small @click="leavePage(props.item.name, props.item.code, props.item.id)">
                <v-icon>remove_red_eye</v-icon>
              </v-btn>
            </v-flex>
          </td>
        </template>
      </v-data-table>
    </v-card>
</template>
<script>
import { excuteApis } from '@/apis'
// import { querySystemDictType } from '@/apis/systems/dictionary'
export default {
  data () {
    return {
      /* 定义数据 */
      headers: [
        {
          text: '档案字段类型',
          align: 'left',
          sortable: false,
          value: 'name'
          // value:'calories'
        },
        // {
        //   text: '编码 ',
        //   align: 'left',
        //   sortable: false,
        //   value: 'coding'
        // },
        {
          text: '操作 ',
          align: 'center',
          sortable: false,
          value: 'fat'
        }
      ],
      items: []
    }
  },
  created () {  // vue 实例化后调用的函数
    this.fetchData() // 定义一个获取数据的方法（实例化后执行该方法）
  },
  methods: {
    // 获取后台数据
    fetchData () {
      // querySystemDictType().then(data => {
      //   let res = data.data
      //   if (res.flag === 1) {  // 如果请求成功，返回数据
      //     this.items = res.data
      //   } else {
      //     this.$toast.top('数据请求失败！')
      //   }
      // })
      let requestParams = {}
      excuteApis(requestParams, global.constant.systemApis, 'tsdictType', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.items = res.data
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
    },
    /* 点击“查看”跳转到类型详情页 */
    leavePage (name, code, id) {
      const router = this.$router  // 根路由对象
      // router.push({name: 'sortmanage', query: {code: code, name: name, parentid: id}})
      router.push({name: 'sortmanage'})
      this.$store.dispatch('dictionarynameMutations', {code: code, name: name, parentid: id})
    }
  }
}
</script>
<style>

</style>
