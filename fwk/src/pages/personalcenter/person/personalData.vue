<template>
  <v-card>
    <v-card-text>
      <v-layout row style="margin-top: 0;margin-right: 80px">
        <v-flex xs12 sm12 md12 lg12 xl12 text-xs-right>
          <v-btn color="primary" :to="{name: 'personalDataEdit', query: {user: user}}">编辑</v-btn>
        </v-flex>
      </v-layout>
      <v-layout align-center row spacer slot="header">
        <v-container fluid grid-list-lg>
          <v-layout row>
            <v-flex xs2>
              <v-avatar size="180px" class="grey lighten-4;margin-left: 30px;" slot="activator">
                <img v-if="user.photoURL" :src="user.photoURL" width="180" height="180"/>
                <img v-else src="static/peasant/moren.png"  width="180" height="180"/>
              </v-avatar>
            </v-flex>
            <v-flex xs10>
              <v-layout row justify-center>
                <v-flex xs1>&nbsp;</v-flex>
                <v-flex xs2 text-xs-center class="textlen">用户名：</v-flex>
                <v-flex xs2 text-xs-left>{{user.account}}</v-flex>
                <v-flex xs2 text-xs-center class="textlen">昵&nbsp;&nbsp;&nbsp;&nbsp;称：</v-flex>
                <v-flex xs4 text-xs-left>{{user.nickname}}</v-flex>
                <v-flex xs1>&nbsp;</v-flex>
              </v-layout>
              <v-layout row justify-center>
                <v-flex xs1>&nbsp;</v-flex>
                <v-flex xs2 text-xs-center class="textlen">手&nbsp;&nbsp;&nbsp;&nbsp;机：</v-flex>
                <v-flex xs2 text-xs-left>{{user.phone}}</v-flex>
                <v-flex xs2 text-xs-center class="textlen">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</v-flex>
                <v-flex xs4 text-xs-left>{{user.email}}</v-flex>
                <v-flex xs1>&nbsp;</v-flex>
              </v-layout>
              <v-layout row justify-center>
                <v-flex xs1>&nbsp;</v-flex>
                <v-flex xs2 text-xs-center class="textlen">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</v-flex>
                <v-flex xs2 text-xs-left>{{user.name}}</v-flex>
                <v-flex xs2 text-xs-center class="textlen">性&nbsp;&nbsp;&nbsp;&nbsp;别：</v-flex>
                <v-flex xs4 text-xs-left>{{user.sexDisText}}</v-flex>
                <v-flex xs1>&nbsp;</v-flex>
              </v-layout>
              <v-layout row justify-center>
                <v-flex xs1>&nbsp;</v-flex>
                <v-flex xs2 text-xs-center class="textlen">出生日期：</v-flex>
                <v-flex xs2 text-xs-left>{{user.birthday}}</v-flex>
                <v-flex xs2 text-xs-center class="textlen">地&nbsp;&nbsp;&nbsp;&nbsp;址：</v-flex>
                <v-flex xs4 text-xs-left>{{user.address}}</v-flex>
                <v-flex xs1>&nbsp;</v-flex>
              </v-layout>
              <v-layout row justify-center>
                <v-flex xs1>&nbsp;</v-flex>
                <v-flex xs2 text-xs-center class="textlen">所属组织/部门：</v-flex>
                <v-flex xs2 text-xs-left>{{user.orgName || user.departmentname}}</v-flex>
                <v-flex xs2 text-xs-center class="textlen">工&nbsp;&nbsp;&nbsp;&nbsp;号：</v-flex>
                <v-flex xs4 text-xs-left>{{user.jobnumber}}</v-flex>
                <v-flex xs1>&nbsp;</v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
          <!--
          <v-snackbar
            :timeout="toast.timeout"
            :color="toast.color"
            :multi-line="toast.mode === 'multi-line'"
            :vertical="toast.mode === 'vertical'"
            v-model="toast.show"
            right
          >
            {{ toast.msg }}
            <v-btn dark flat @click.native="toast.show = false">Close</v-btn>
          </v-snackbar>
          -->
        </v-container>
      </v-layout>
    </v-card-text>
  </v-card>
</template>
<script>
  import {excuteApis} from '@/apis'
  // import {querypositionbycode} from '@/apis/personalcenter/personal'
  // import { personalInfo, querypositionbycode } from '@/apis/personalcenter/personal'
  export default {
    data () {
      return {
        globalUser: null, // 用户信息
        user: {
          id: null, // ID
          userid: null, // ID
          loginorgid: null, // ID
          photoURL: 'static/peasant/moren.png', // 头像
          account: '', // 用户名
          nickname: '', // 昵称
          phone: '', // 手机
          email: '', // 邮箱
          name: '',  // 姓名
          sex: '',   // 性别
          sexDisText: '',   // 性别
          birthday: '', // 出生日期
          addresscode: '',
          addressdetail: '', // 地址
          departmentid: '', // 归属部门
          departmentname: '', // 归属部门
          orgName: '', // 所属组织
          jobnumber: '', // 工号
          address: '' // 行政区划地址+详细地址
        }
        /*
        toast: {
          show: false,
          color: '',
          mode: 'vertical',
          timeout: 6000,
          msg: ''
        }
        */
      }
    },
    created () {
      this.globalUser = global.helper.ls.get('user')
      // let editUser = this.$route.query.user
      // if (typeof (editUser) !== 'undefined') {
      //   this.globalUser = editUser
      // }
      // console.log(this.globalUser)
      // this.fetchData()
    },
    methods: {
      refreshRout () {
        this.globalUser = global.helper.ls.get('user')
        this.fetchData()
      },
      fetchData () {
        if (this.globalUser) { // 如果请求成功
          // this.id = res.data[0].id
          this.user.id = this.globalUser.id
          this.user.userid = this.globalUser.id
          this.user.loginorgid = this.globalUser.loginorgid
          this.user.photoURL = this.globalUser.photoURL
          this.user.account = this.globalUser.account
          this.user.nickname = this.globalUser.nickname
          this.user.phone = this.globalUser.phone
          this.user.email = this.globalUser.email
          this.user.name = this.globalUser.name
          this.user.sex = this.globalUser.sex
          this.user.birthday = this.globalUser.birthday
          this.user.birthday = this.formatDateYYYYMMDD(this.user.birthday)
          this.user.addresscode = this.globalUser.addresscode
          this.user.addressdetail = this.globalUser.addressdetail
          this.user.departmentid = this.globalUser.departmentid
          this.user.departmentname = this.globalUser.departmentname
          this.user.orgName = this.globalUser.orgName
          this.user.jobnumber = this.globalUser.jobnumber
          console.log(this.user)
          // 行政区划地址
          if (this.user.addresscode) {
            excuteApis(Object.assign({}, {code: this.user.addresscode}), global.constant.archivesApis, 'common', 'querypositionbycode').then(data => {
            // querypositionbycode(this.user.addresscode).then(data => {
              let res = data.data
              console.log(res.data)
              if (res.flag === 1 && res.data != null) { // 如果请求成功
                this.user.address = res.data.allname + ' ' + this.user.addressdetail
              } else {
                this.user.address = this.user.addressdetail
              }
            })
          }
          // 查询用户的性别          显示的文本
          if (this.user.sex) {
            let sexRequestParams = {
              dicttype: 'user-sex',
              dictvalue: this.user.sex
            }
            excuteApis(sexRequestParams, global.constant.archivesApis, 'common', 'query').then(data => {
              let res = data.data
              if (res.flag === 1 && res.data.length > 0) {
                this.user.sexDisText = res.data[0].dictname
              }
            })
          } else {
            // this.openTipsDialog('red lighten-1', '没有获得用户的数据')
            // this.$toast.top('没有获得用户的数据')
          }
        }
      },
      // 点击 “编辑” (传递参数：组织编号)
      edit (user) {
        this.$router.push({name: 'personalDataEdit', query: {user: user}})
      },
      /*
      openTipsDialog (color, msg) {
        this.toast.color = color
        this.toast.msg = msg
        this.toast.show = true
      }
      */
      formatDateYYYYMMDD (value) {
        if (value) {
          return new Date(value).format('yyyy-MM-dd')
        } else {
          return ''
        }
      }
    },
    watch: {
      globalUser: function (val, oldVal) {
        if (val) {
          this.fetchData()
        }
      },
      '$route': 'refreshRout' // 切换路由时
    }
  }
</script>

<style scoped>
  .textlen {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
</style>
