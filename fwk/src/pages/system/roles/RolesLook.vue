<template>
  <v-card class="page-content">
    <!-- <v-container style="border:1px solid #D6D6D6;background-color: #fff;"> -->
      <v-layout row style="margin-top: 0;">
        <v-flex xs12 sm12 md12 lg12 xl12 text-xs-left>
          <span @click="back" style="cursor: pointer">
            <v-icon dark left color="primary">arrow_back</v-icon>查看
          </span>
        </v-flex>
        <v-flex xs12 sm12 md12 lg12 xl12 text-xs-right style="padding-bottom: 10px">
          <v-btn @click="deleteRole(id)">
            <v-icon>delete</v-icon>
            &nbsp;&nbsp;删除
          </v-btn>
          <v-btn @click="editRole()" style="color:#39BBB0 ">
            <v-icon>edit</v-icon>
            &nbsp;&nbsp;编辑
          </v-btn>
        </v-flex>
      </v-layout>

      <!--角色ID-->
      <v-layout row style="margin-bottom: 20px">
        <v-flex xs2 text-xs-right>角色ID：</v-flex>
        <v-flex xs9>{{id}}</v-flex>
      </v-layout>

      <!--角色名称-->
      <v-layout row style="margin: 20px 0">
        <v-flex xs2 text-xs-right text-sm-right text-md-right text-lg-right text-xl-right>角色名称：</v-flex>
        <v-flex xs9>{{rolename}}</v-flex>
      </v-layout>

      <!--权限配置-->
      <v-layout row>
        <v-flex xs2 text-xs-right text-sm-right text-md-right text-lg-right text-xl-right>权限配置：</v-flex>
        <v-flex xs9 >
          <v-data-table
            :items="items"
            hide-headers
            hide-actions
            no-data-text="暂无数据"
          >
            <template slot="items" slot-scope="props">
              <td>
                <v-icon>done</v-icon>
                {{props.item}}
              </td>
            </template>
          </v-data-table>
        </v-flex>
      </v-layout>

      <!--确认、取消-->
      <!-- <v-layout row>
        <v-flex xs12 class="text-xs-center">
          <v-btn @click="back">返回</v-btn>
        </v-flex>
      </v-layout> -->

      <!--删除弹框-->
      <v-dialog v-model="dialog" persistent="persistent" max-width="40%" max-hight="300">
        <v-card>
          <v-card-title class="headerline" style="display: block" >
              <v-icon @click="dialog=false" style="display: inline; float: right; cursor: pointer">highlight_off</v-icon>
          </v-card-title>
          <v-card-text class="text-xs-center">信息删除后将无法恢复，请谨慎操作，确定删除？</v-card-text>
          <v-card-actions style="padding: 30px auto;text-align: center;display: block">
            <v-btn @click="dialog=false">取消</v-btn>
            <v-btn @click="affirmDelete(id)" color="primary">确认</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    <!-- </v-container> -->
  </v-card>
</template>

<script>
  import { excuteApis } from '@/apis'
  export default {
    data () {
      return {
        id: null, // 角色id
        orgid: null, // 当前登录用户的组织id
        rolename: null,
        dialog: false,
        items: []
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        this.id = this.$route.query.id
        this.orgid = this.$route.query.orgid
        // 获取该角色拥有的权限配置项
        let requestParams = {}
        requestParams = this.id ? Object.assign(requestParams, {id: this.id}) : requestParams
        requestParams = this.orgid ? Object.assign(requestParams, {orgid: this.orgid}) : requestParams
        excuteApis(requestParams, global.constant.userApisUrls, 'role', 'queryRole').then(data => {
          let res = data.data
          if (res.flag === 1 && res.data && res.data.length > 0) {
            this.rolename = res.data[0].rolename
            if (res.data[0].permissionnames) {
              this.items = res.data[0].permissionnames.split(',')
            }
          }
        })
      },
      // 点击删除
      deleteRole () {
        this.dialog = true
      },
      // 确认删除角色
      affirmDelete (id) {
        let requestParams = {}
        requestParams = id ? Object.assign(requestParams, {id: id}) : requestParams
        excuteApis(requestParams, global.constant.userApisUrls, 'role', 'deleteRole').then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.dialog = false
            setTimeout(function () {
              alert(res.message)
            }, 200)
            this.$router.push({name: 'roles'})
          }
        })
      },
      // 点击编辑
      editRole () {
        this.$router.push({name: 'rolesedit', query: {id: this.id, orgid: this.orgid}})
      },
      // 点击返回
      back () {
        this.$router.push({name: 'roles'})
      }

    }
  }
</script>

<style scoped>

</style>
