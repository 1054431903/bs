<template>
  <v-card style="margin-left: 0px">
    <!--
       <v-layout row style="margin-top: 0;margin-right: 20px">
         <v-flex xs10 sm10 md10 lg10 xl10 text-xs-right>
           <v-btn @click="exitOrg(org.belonguserid)">退出组织</v-btn>
         </v-flex>
       </v-layout>
        -->
    <!--组织名称-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>组织名称：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.name}}</v-flex>
    </v-layout>
    <!--组织性质-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>组织性质：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.natureDisText}}</v-flex>
    </v-layout>
    <!--组织法人-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>组织法人：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.legal}}</v-flex>
    </v-layout>
    <!--成立时间-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>成立时间：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.eastablishtime}}</v-flex>
    </v-layout>
    <!--组织logo-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>
        组织logo：
      </v-flex>
      <v-flex xs2
              sm2
              md2
              lg92
              xl2>
        <div class="imgDiv">
          <img :src="org.logourl"
               width="200"
               height="200" />
        </div>
      </v-flex>
    </v-layout>
    <!--注册资金-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>注册资金：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.regisfund}}&nbsp;&nbsp;&nbsp;&nbsp;(万元)</v-flex>
    </v-layout>
    <!--人员规模-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>人员规模：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.scaleDisText}}</v-flex>
    </v-layout>
    <!--营业执照-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>
        营业执照：
      </v-flex>
      <v-flex xs2
              sm2
              md2
              lg92
              xl2>
        <div class="imgDiv">
          <img :src="org.permiturl"
               width="200"
               height="200" />
        </div>
      </v-flex>
    </v-layout>
    <!--注册地址-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>注册地址：
      </v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5
              style="margin-left: 10px">{{org.addressdetail}}</v-flex>
    </v-layout>
    <!--组织简介-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>组织简介：</v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5>
        <v-textarea style="margin-left: -5px;padding-top: 0px;margin-top: -3px"
                    label="组织简介"
                    v-model="org.introduce"
                    full-width
                    single-line
                    readonly="readonly"></v-textarea>
        <!--<v-text-field type="text" readonly="readonly" multi-line v-model="org.introduce" single-line label="请输入组织简介" ></v-text-field>-->
      </v-flex>
    </v-layout>
    <!--业务范围-->
    <v-layout>
      <v-flex xs1
              sm1
              md1
              lg1
              xl1
              text-xs-left
              text-sm-left
              text-md-left
              text-lg-left
              text-xl-left>业务范围：</v-flex>
      <v-flex xs5
              sm5
              md5
              lg5
              xl5>
        <v-textarea style="margin-left: -5px;padding-top: 0px;margin-top: -3px"
                    label="业务范围"
                    v-model="org.scope"
                    full-width
                    single-line
                    readonly="readonly"></v-textarea>

        <!--<v-text-field type="text" readonly="readonly" multi-line v-model="org.scope" single-line label="请输入业务范围" ></v-text-field>-->
      </v-flex>
    </v-layout>
    <!--退出组织弹框 -->
    <v-dialog v-model="dialogExitOrg"
              max-width="50%">
      <v-card>
        <v-card-title class="headline"
                      style="margin: 0;padding: 0;">
          <v-toolbar :color="theme">
            <v-toolbar-title class="white--text">退出组织</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text class="text-xs-center">{{over}}</v-card-text>
        <v-card-actions style="padding: 30px 10px; text-align: center;display: block">
          <v-spacer></v-spacer>
          <v-btn color="primary"
                 @click="exitOrgSave(org.belonguserid,org.id,globalUser.id)"
                 style="background-color:#B2AFB3;color: #fff">确认
          </v-btn>
          <v-btn @click="dissolveColse(dialogExitOrg = false)"
                 style="background-color: #666666;color: #fff">取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
  </v-card>
</template>
<script>
import { excuteApis } from '@/apis'
// import {querypositionbycode, organizationInfo, exitOrg} from '@/apis/personalcenter/personal'
// import {organizationInfo, exitOrg} from '@/apis/personalcenter/personal'
export default {
  data () {
    return {
      theme: 'primary',
      globalUser: null, // 用户信息
      org: {  // 当前登录用户的组织编号（示例）
        loginorgid: null,
        id: null,
        belonguserid: null, //  企业的负责人
        name: null, // 组织名称
        nature: null, // 组织性质
        natureDisText: null, // 组织性质
        legal: null, // 组织法人
        eastablishtime: null, // 成立时间
        logourl: null, // 企业logo图片地址
        regisfund: null, // 注册资金
        scale: null, // 人员规模
        scaleDisText: null, // 人员规模
        permiturl: null, // 营业执照图片地址
        addressdetail: null, // 详细地址
        introduce: null, // 简介
        scope: null, // 业务范围
        addressCode: null // 详细地址
      },

      // 退出组织提示信息
      dialogExitOrg: false,
      over: '退出组织后将删除您与组织的关系，且相关记录将被清空，无法恢复，确认退出组织？'
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
    // this.fetchData()
  },
  methods: {
    // 获取组织信息
    fetchData () {
      if (this.globalUser) {
        excuteApis({}, global.constant.userApisUrls, 'org', 'queryOrgByOperateid').then(data => {
          // organizationInfo(this.globalUser.loginorgid).then(data => {
          let res = data.data
          console.log(res.data)
          if (res.flag === 1 && res.data.length > 0) { // 如果请求成功
            this.org.loginorgid = this.globalUser.loginorgid
            this.org.id = res.data[0].id
            this.org.name = res.data[0].name
            this.org.nature = res.data[0].nature
            this.org.legal = res.data[0].legal
            this.org.eastablishtime = res.data[0].eastablishtime
            this.org.logourl = res.data[0].logourl
            this.org.regisfund = res.data[0].regisfund
            this.org.scale = res.data[0].scale
            this.org.addressdetail = res.data[0].addressdetail
            this.org.permiturl = res.data[0].permiturl
            this.org.introduce = res.data[0].introduce
            this.org.scope = res.data[0].scope
            // console.log(res.data)
            this.org.belonguserid = res.data[0].belonguserid
            this.org.addressCode = res.data[0].addressCode
            // 查询组织的性质显示的文本
            let natureRequestParams = {
              dicttype: 'org-nature',
              dictvalue: this.org.nature
            }
            excuteApis(natureRequestParams, global.constant.archivesApis, 'common', 'query').then(data => {
              let res = data.data
              if (res.flag === 1 && res.data.length > 0) {
                this.org.natureDisText = res.data[0].dictname
              }
            })
            // 人员规模
            let scaleRequestParams = {
              dicttype: 'org-scale',
              dictvalue: this.org.scale
            }
            excuteApis(scaleRequestParams, global.constant.archivesApis, 'common', 'query').then(data => {
              let res = data.data
              if (res.flag === 1 && res.data.length > 0) {
                this.org.scaleDisText = res.data[0].dictname
              }
            })
            // 详细地址
            // 行政区划地址
            excuteApis(Object.assign({}, { code: this.org.addressCode }), global.constant.archivesApis, 'common', 'querypositionbycode').then(data => {
              // querypositionbycode(this.org.addressCode).then(data => {
              let res = data.data
              console.log(res.data)
              if (res.flag === 1 && res.data != null) { // 如果请求成功
                this.org.addressdetail = res.data.allname + ' ' + this.org.addressdetail
              } else {
                this.org.addressdetail = this.org.addressdetail
              }
            })
          } else {
            this.$toast.top('没有获得组织信息')
            // this.openTipsDialog('red lighten-1', '没有获得组织信息')
          }
        })
      }
    },
    // 退出组织(传递参数:登陆账号)
    exitOrg (belonguserid) {
      if (this.globalUser.id === belonguserid) {
        this.over = '您是组织的负责人，先要完成移交组织后方可退出。是否到管理后台完成组织移交或解散组织？'
      }
      this.dialogExitOrg = true
    },
    // 退出组织中的确认
    exitOrgSave (belonguserid, orgid, userid) {
      this.dialogExitOrg = false

      if (this.globalUser.id === belonguserid) { // 管理员退出
        this.$router.push({ name: 'organizations' })
      } else { // 普通人员退出
        // 发送请求，退出组织
        excuteApis(Object.assign({}, { userid: userid }), global.constant.userApisUrls, 'user', 'removeUserFromOrg').then(data => {
          // exitOrg(orgid, userid).then(data => {
          let res = data.data
          if (res.flag === 1) {
            // this.openTipsDialog('success', res.message)
            this.$toast.top(res.message)
            let router = this.$router
            setTimeout(function () {
              router.push({ name: 'login' })
            }, 2000) // 延迟2 秒
          } else {
            // this.openTipsDialog('red lighten-1', res.message)
            this.$toast.top(res.message)
          }
          console.log(data)
        })
      }
    }
    /*
    openTipsDialog (color, msg) {
      this.toast.color = color
      this.toast.msg = msg
      this.toast.show = true
    }
    */
  },
  watch: {
    globalUser: function (val, oldVal) {
      if (val) {
        this.fetchData()
      }
    }
  }
}
</script>

<style scoped>
.layout {
  margin: 20px auto;
}
.imgDiv {
  display: inline-block;
  position: relative;
  margin-left: 10px;
}
</style>
