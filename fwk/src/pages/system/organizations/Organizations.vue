<template>
  <v-container pa-0
               pb-4>
    <v-card class="page-content">
      <v-layout row>
        <v-flex xs12
                text-xs-right>
          <!-- <v-btn @click="handOver(loginorgid)">移交组织</v-btn> -->
          <!-- <v-btn @click="dissolve(loginorgid)">解散组织</v-btn> -->
          <v-btn @click="edit()"
                 color="primary"
                 v-if="$authed('PUT', 'organizations/main/organizations/editorg')">
            <v-icon>edit</v-icon>
            <span>编辑</span>
          </v-btn>
        </v-flex>
      </v-layout>
      <!--组织名称-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          组织名称
        </v-flex>
        <v-flex xs11>
          {{name}}
        </v-flex>
      </v-layout>
      <!--组织性质-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          组织性质
        </v-flex>
        <v-flex xs11>{{naturename}}</v-flex>
      </v-layout>
      <!--组织法人-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          组织法人
        </v-flex>
        <v-flex xs11>{{legal}}</v-flex>
      </v-layout>
      <!--成立时间-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          成立时间
        </v-flex>
        <v-flex xs11>{{eastablishtime}}</v-flex>
      </v-layout>
      <!--组织logo-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          组织logo
        </v-flex>
        <v-flex xs11
                v-if="logourl">
          <img :src="logourl"
               alt=""
               style="width: 120px; height: 120px;">
        </v-flex>
        <v-flex xs11
                v-else>
          <img alt=""
               style="width: 120px; height: 120px;">
        </v-flex>
      </v-layout>
      <!--注册资金-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          注册资金(万元)
        </v-flex>
        <v-flex xs11>{{regisfund}}</v-flex>
      </v-layout>
      <!--人员规模-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          人员规模
        </v-flex>
        <v-flex xs11>{{scalename}}</v-flex>
      </v-layout>
      <!--营业执照-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          营业执照
        </v-flex>
        <v-flex xs11
                v-if="permiturl">
          <img :src="permiturl"
               alt=""
               style="width: 200px; height: 200px;">
        </v-flex>
        <v-flex xs11
                v-else>
          <img alt=""
               style="width: 200px; height: 200px;">
        </v-flex>
      </v-layout>
      <!--注册地址-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          注册地址
        </v-flex>
        <v-flex xs11>{{allName}}&nbsp;&nbsp;{{addressdetail}}</v-flex>
      </v-layout>
      <!--组织简介-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          组织简介
        </v-flex>
        <v-flex xs11>
          {{introduce || '--'}}
        </v-flex>
      </v-layout>
      <!--业务范围-->
      <v-layout row
                py-2>
        <v-flex xs1
                class="titleColor">
          业务范围
        </v-flex>
        <v-flex xs11>
          {{scope || '--'}}
        </v-flex>
      </v-layout>

      <!--移交组织弹框-->
      <v-dialog v-model="dialogOver"
                persistent="persistent"
                max-width="50%">
        <v-card>
          <v-card-title style="border-bottom: 1px solid #ccc">移交组织</v-card-title>
          <v-card-text class="text-xs-center">
            <v-layout>
              <v-flex xs3
                      sm3
                      md3
                      lg3
                      xl3
                      class="text-xs-right"
                      style="margin-top: 4%">移交组织给</v-flex>
              <v-flex xs8
                      sm8
                      md8
                      lg8
                      xl8>
                <v-select :items="allUsers"
                          item-text="name"
                          item-value="id"
                          v-model="overTo"
                          single-line
                          label="请选择组织成员"></v-select>
              </v-flex>
            </v-layout>
            <v-layout>
              <v-flex xs3
                      sm3
                      md3
                      lg3
                      xl3
                      class="text-xs-right"
                      style="margin-top: 4%">当前用户登录密码：</v-flex>
              <v-flex xs8
                      sm8
                      md8
                      lg8
                      xl8>
                <v-text-field type="password"
                              single-line
                              :label="password"></v-text-field>
              </v-flex>
            </v-layout>
          </v-card-text>
          <v-card-actions style="padding: 30px 10px; text-align: center;display: block">
            <v-spacer></v-spacer>
            <v-btn @click="handOverSave(loginorgid, password, overTo)"
                   style="background-color:#B2AFB3;color: #fff">确认</v-btn>
            <v-btn @click.native="dialogOver = false"
                   style="background-color: #666666;color: #fff">取消</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!--解散组织弹框-->
      <v-dialog v-model="dialogDissolve"
                persistent="persistent"
                max-width="50%">
        <v-card>
          <v-card-title class="headline"></v-card-title>
          <v-card-text class="text-xs-center">{{over}}</v-card-text>
          <v-card-actions style="padding: 30px 10px; text-align: center;display: block">
            <v-spacer></v-spacer>
            <v-btn @click="dissolveSave(loginorgid)"
                   style="background-color:#B2AFB3;color: #fff">确认</v-btn>
            <v-btn @click.native="dissolveColse"
                   style="background-color: #666666;color: #fff">取消</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-container>
</template>

<script>
// import {
//   organizationInfo,
//   organizationDissolve,
//   orgAllUsers
// } from '@/apis/systems/organization'
import { excuteApis } from '@/apis'

export default {
  data () {
    return {
      // 当前登录用户的组织编号（示例）
      loginorgid: '',
      // 移交组织
      dialogOver: false,
      // 登录用户组织下的所有用户
      allUsers: [],
      // 移交组织给谁
      overTo: [],
      // 用户登录密码
      password: '请输入用户登录密码',
      // 解散组织
      dialogDissolve: false,
      // 解散组织提示信息
      over: '解散组织后组织所有信息都将被删除，并且无法撤销，确认删除？',
      // id: null, // 主键id
      name: null, // 组织名称
      naturename: null, // 组织性质
      legal: null, // 组织法人
      eastablishtime: null, // 成立时间
      logourl: null, // 企业logo图片地址
      regisfund: null, // 注册资金
      scalename: null, // 人员规模
      addressdetail: null, // 详细地址
      introduce: null, // 简介
      scope: null, // 业务范围
      permiturl: null, // 营业执照图片地址
      addressCode: null, // 最下级行政code
      province: null,
      city: null,
      county: null,
      parentCode: null,
      allName: null
    }
  },
  created () {
    this.loginorgid = global.helper.ls.get('user').loginorgid
    this.fetchData()
  },
  watch: {
    '$route': 'refreshRout' // 切换路由时
  },
  methods: {
    refreshRout () {
      this.loginorgid = global.helper.ls.get('user').loginorgid
      this.fetchData()
    },
    // 获取组织信息
    fetchData () {
      excuteApis(Object.assign({}, { loginorgid: this.loginorgid }), global.constant.userApisUrls, 'org', 'queryOrgByOperateid').then(data => {
        // organizationInfo(this.loginorgid).then(data => {
        let res = data.data
        if (res.flag === 1) { // 如果请求成功
          // this.id = res.data[0].id
          this.name = res.data[0].name
          this.naturename = res.data[0].naturename
          this.legal = res.data[0].legal
          this.eastablishtime = res.data[0].eastablishtime
          this.logourl = res.data[0].logourl || null
          this.regisfund = res.data[0].regisfund
          this.scalename = res.data[0].scalename
          this.addressdetail = res.data[0].addressdetail
          this.introduce = res.data[0].introduce
          this.scope = res.data[0].scope
          this.permiturl = res.data[0].permiturl || null
          this.addressCode = res.data[0].addressCode
          if (this.addressCode) {
            this.gainAddress(this.addressCode)
          }
          console.log(this.logourl)
          console.log(this.permiturl)
        } else {
          this.$toast.top(res.message)
        }
      })
    },
    // 获取行政地址
    gainAddress (code) {
      let requestParams = {}
      requestParams = code ? Object.assign(requestParams, { code: code }) : requestParams
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'querypositionbycode').then(data => {
        let resAddress = data.data
        if (resAddress.flag === 1 && resAddress.data) {
          this.allName = resAddress.data.allname
        }
      })
    },
    // 点击 “编辑” (传递参数：组织编号)
    edit () {
      this.$router.push({ name: 'editorg' })
    },
    // 移交组织
    handOver (loginorgid) {
      this.dialogOver = true
      // 发送请求，获取登录用户的组织下的所有用户
      excuteApis(Object.assign({}, { loginorgid: loginorgid }), global.constant.userApisUrls, 'user', 'queryUserByLoginUserOrg').then(data => {
        // orgAllUsers(loginorgid).then(data => {
        let resUsers = data.data
        if (resUsers.flag === 1) {
          this.allUsers = resUsers.data
          // console.log('该组织下的所有用户')
          // console.log(this.allUsers)
        } else {
          this.$toast.top(resUsers.message)
        }
      })
    },
    // 移交组织----确认
    handOverSave (overTo) {
      this.dialogOver = true
    },
    // 解散组织(传递参数:组织编号)
    dissolve (loginorgid) {
      this.dialogDissolve = true
    },
    // 解散组织中的确认
    dissolveSave (loginorgid) {
      this.dialogDissolve = false
      // 发送请求，解散组织
      excuteApis(Object.assign({}, { id: loginorgid }), global.constant.userApisUrls, 'org', 'deleteOrg').then(data => {
        // organizationDissolve(loginorgid).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.$toast.top(res.message)
        } else {
          this.$toast.top(res.message)
        }
      })
    },
    // 解散组织中的取消
    dissolveColse () {
      this.dialogDissolve = false
    }
  }
}
</script>

<style scoped>
.titleColor {
  color: rgba(0, 0, 0, 0.54);
}
.orgList {
  margin: 20px auto;
}
.text-position {
  line-height: 74px;
}
.orgText {
  padding-top: 10px;
}
.textCenter {
  padding: 25px 0;
}
#orgAlign {
  margin-top: 0.9rem;
}
</style>
