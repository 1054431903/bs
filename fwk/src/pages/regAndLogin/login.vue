<template>
  <v-container fluid
               style="padding: 0">
    <v-layout row
              style="height: 120px">
      <v-flex xs2></v-flex>
      <v-flex xs10>
        <img style="margin-top:36px"
             src="static/regAndLogin/logo.png"
             width="198px"
             height="48px" />
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs12>
        <div style="background:url('static/regAndLogin/beijing.png');background-size:100%;height:100%;width:100%">
          <v-layout row>
            <v-flex xs6></v-flex>
            <v-flex xs5>
              <v-form disableautocomplete
                      autocomplete="off"
                      style="height:420px;width:400px;margin-top:20px;margin-bottom:20px;background-color:#fff;margin-right: 0px;margin-left: auto"
                      ref="form"
                      @keyup.native.enter="submit">
                <v-layout row>
                  <v-flex xs12
                          style="margin-top:30px;text-align: center;font-size: 24px;color: #666">用户登录</v-flex>
                  <input type="password"
                         name="password1"
                         style="display:none" />
                  <!--是为了解决自动填充功能的，没有任何的实际意义-->
                </v-layout>
                <v-layout row
                          style="margin-top:30px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-text-field solo
                                  placeholder="用户名/手机/邮箱"
                                  v-model="loginUser.account"
                                  prepend-icon="perm_identity"
                                  :color="aColor"
                                  v-validate="'required|min:1'"
                                  data-vv-name="account"
                                  data-vv-as="用户名/手机/邮箱"
                                  @input="blurAccountChange"
                                  required
                                  autofocus
                                  clearable
                                  onkeyup="this.value=this.value.replace(/\D/g,'')"
                                  autocomplete="off"
                                  disableautocomplete></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-text-field solo
                                  placeholder="6-16位字母和数字"
                                  v-model="loginUser.password"
                                  prepend-icon="lock"
                                  :color="pColor"
                                  :append-icon="e1 ? 'visibility' : 'visibility_off'"
                                  @click:append="() => (e1 = !e1)"
                                  :type="e1 ? 'text' : 'password'"
                                  v-validate="'required|min:6'"
                                  data-vv-name="password"
                                  data-vv-as="密码"
                                  @input="blurPasswordChange"
                                  required
                                  autocomplete="off"
                                  disableautocomplete></v-text-field>
                    <small v-if="isError"
                           style="color:red">{{errorMsg}}</small>
                  </v-flex>
                </v-layout>
                <v-layout row
                          justify-center>
                  <v-flex row
                          xs5>
                    <v-checkbox style="float:left"
                                v-model="isRememberMe"
                                color="blue"
                                label="记住我"
                                hide-details />
                    <!-- <div style="color:#707070;float:left;width:50px;font-size: 14px;line-height:30px">
                      记住我
                    </div> -->
                  </v-flex>
                  <v-flex xs5
                          style="color:#f13325;justify-content:center;align-items:center;display:-webkit-flex;"
                          text-xs-right>
                    <div>{{message}}</div>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:10px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-btn style="height:38px;width:340px;background-color: #2196f3;font-size: 17px;color: #fff;margin:0"
                           large
                           @click="submit"
                           :disabled="loginDisabled">登录
                      <v-progress-circular v-show="progressShow"
                                           class="progressWrapper"
                                           size="35"
                                           indeterminate
                                           color="#E3F2FD" />
                    </v-btn>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:20px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <!-- <v-subheader style="margin-left:30px" :to="forgetPwd">忘记密码</v-subheader> -->
                    <router-link tag="div"
                                 style="cursor:pointer;color:#707070;"
                                 :to="{ path: 'forgetPwd' }">忘记密码</router-link>
                  </v-flex>
                  <!-- <v-flex xs5 md5 lg5 text-xs-right grey--text>
                      还没有账号，现在<router-link :to="{ path: 'register' }">注册</router-link>
                  </v-flex> -->
                </v-layout>
              </v-form>
            </v-flex>
            <v-flex xs1></v-flex>
          </v-layout>
        </div>
      </v-flex>
    </v-layout>
    <v-footer height="auto"
              class="white"
              fixed>
      <v-layout row
                wrap
                justify-center>
        <v-flex xs12
                py-3
                text-xs-center
                grey--text>
          云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有&nbsp;&nbsp;&nbsp;&nbsp;Ver3.3
        </v-flex>
      </v-layout>
    </v-footer>
  </v-container>
</template>

<script>
// import {login} from '@/apis/regAndLogin/regAndLogin'
import { excuteApis } from '@/apis'
import axios from 'axios'
import OAuth2 from '@vehiclecloud/oauth2-client'
import conf from '@/conf'
import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  data () {
    return {
      valid: true,
      e1: false, // 默认的密码框不给看
      isRememberMe: false,
      message: '',
      aColor: 'grey lighten-1',
      pColor: 'grey lighten-1',
      loginUser: { // 登陆的用户
        account: '', //
        password: '' //
      },
      loginDisabled: false,
      progressShow: false, // 转圈
      isError: false,
      errorMsg: '',
      tmpTenant: null
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
    // this.loginUser.account = this.$route.query.account
    this.getCookie()
  },
  methods: {
    blurAccountChange () {
      if (this.loginUser.account) {
        this.aColor = 'grey darken-4'
      } else {
        this.aColor = 'grey lighten-1'
        this.pColor = 'grey lighten-1'
      }
      this.message = ''
    },
    blurPasswordChange () {
      if (this.loginUser.password) {
        this.pColor = 'grey darken-4'
      } else {
        this.pColor = 'grey lighten-1'
      }
      this.message = ''
    },
    findLoginTenant () {
      excuteApis({ phone: this.loginUser.account, islogin: 1 }, global.constant.userApisUrls, 'user', 'getDefaultOrgByPhone').then(data => {
        console.log(data.data.data)
        if (data.data.data) {
          this.tmpTenant = data.data.data.loginorgid
        } else {
          this.errorMsg = data.data.message
        }
      }).catch(err => {
        console.log(err)
      })
    },
    submit () {
      let _this = this
      this.$validator.validateAll(['account', 'password']).then((result) => {
        _this.$store.dispatch('init', { collection: 'sys.authority' })
        _this.progressShow = true // 显示转圈
        _this.loginDisabled = true
        _this.isError = false
        if (result) { // 如果表单验证通过
          // _this.findLoginTenant()
          excuteApis({ phone: this.loginUser.account, islogin: 1 }, global.constant.userApisUrls, 'user', 'getDefaultOrgByPhone').then(data => {
            console.log(data.data.data)
            if (data.data.data) {
              console.log(111111111)
              this.tmpTenant = data.data.data.loginorgid
              excuteApis({ account: _this.loginUser.account, islogin: 1 }, global.constant.userApisUrls, 'user', 'initPermission').then(data => {
                let resParams = 'grant_type=password&client_id=' + conf.OAuth2.clientId + '&client_secret=1&username=' + _this.loginUser.account + '&password=' + _this.loginUser.password
                axios.post(conf.OAuth2.server + '/api/oauth2/token', resParams, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }).then(data => {
                  if (data.status === 200) {
                    _this.isError = false
                    if (data.error) {
                      _this.isError = true
                      _this.errorMsg = data.error_description ? data.error_description : '请检查用户名或密码是否正确'
                      _this.progressShow = false
                      _this.loginDisabled = false
                      return
                    }
                    _this.isRememberMe ? _this.setCookie(this.loginUser.account, this.loginUser.password, 7) : _this.clearCookie()
                    let tk = {
                      'access_token': data.data.access_token
                    }
                    let payload = atob(data.data.access_token.split('.')[1])
                    let jwt = JSON.parse(payload)
                    if (Object.keys(jwt.roles).length === 0) {
                      _this.isError = true
                      _this.errorMsg = '您暂无系统使用权限,请联系企业管理员开通'
                      _this.progressShow = false
                      _this.loginDisabled = false
                      return
                    }
                    OAuth2.initToken(tk)
                    localStorage.setItem('atk', JSON.stringify(tk))
                    let pointArr = []
                    let noPointArr = []
                    let menuArr = []
                    let roleArr = []
                    let tenant = ''
                    console.log(333333333333)
                    console.log(_this.tmpTenant)
                    if (!_this.tmpTenant) {
                      _this.isError = true
                      _this.progressShow = false
                      _this.loginDisabled = false
                      return
                    }
                    tenant = _this.tmpTenant
                    console.log(tenant)
                    console.log(jwt.roles)
                    // if (_this.tmpTenant) {
                    //   tenant = _this.tmpTenant
                    // } else {
                    //   let rolesKeys = jwt.roles && Object.keys(jwt.roles)
                    //   console.log(rolesKeys)
                    //   console.log(rolesKeys[0])
                    //   tenant = rolesKeys && rolesKeys[0]
                    // }
                    OAuth2.cookies.set('fwk-token-tenant', tenant)
                    if (!jwt.roles[tenant]) {
                      _this.isError = true
                      _this.errorMsg = '您暂无系统使用权限,请联系企业管理员开通'
                      _this.progressShow = false
                      _this.loginDisabled = false
                      return
                    }
                    mongo.db().collection('tenant.role').find({
                      filter: { status: 1, _id: { $in: jwt.roles[tenant].map(id => ({ $oid: id })) }, '_removed': { '$exists': false } }
                    }).then(d => {
                      if (d.items && d.items.length > 0 && d.items[0].authorities) {
                        d.items.forEach(e => {
                          roleArr.push(...e.authorities)
                        })
                        let res = new Map()
                        let tmp = roleArr.filter(e => !res.has(e.$oid) && res.set(e.$oid, 1))
                        roleArr = tmp
                        localStorage.setItem('sys-auth', JSON.stringify(_this.$store.state['sys.authority'].items))
                        _this.$store.state['sys.authority'].items.forEach(value => {
                          roleArr.forEach(e => {
                            if (value._id.$oid === e.$oid && value.level !== 3) {
                              if (value.level === 1) {
                                noPointArr.push({ title: value.title, items: [], idx: value.idx, id: e.$oid })
                              } else {
                                pointArr.push({ title: value.title, icon: value.icon, to: { name: value.uri }, idx: value.idx, pid: !value.parent ? null : value.parent.$oid })
                              }
                            }
                          })
                        })
                        if (pointArr.length > 0 || noPointArr.length > 0) {
                          pointArr.forEach(v => {
                            if (!v.pid) {
                              menuArr.push(v)
                            } else {
                              if (noPointArr.length > 0) {
                                for (let i = 0; i < noPointArr.length; i++) {
                                  v.pid === noPointArr[i].id ? noPointArr[i].items.push(v) : ''
                                }
                              }
                            }
                          })
                        }
                        let tArr = [...menuArr, ...noPointArr]
                        tArr.sort((a, b) => a.idx - b.idx)
                        for (let j = 0; j < tArr.length; j++) {
                          tArr[j].items && tArr[j].items.length > 0 ? tArr[j].items.sort((a, b) => a.idx - b.idx) : ''
                        }
                        this.$store.commit('setMenu', tArr)
                        excuteApis({ account: _this.loginUser.account, password: _this.loginUser.password, islogin: 1 }, global.constant.userApisUrls, 'user', 'login').then(data => {
                          this.$store.dispatch('cacheUserInfo', data.data.data)
                          let user = data.data.data
                          console.log(44444444444)
                          console.log(user)
                          // if (user.loginorgid && user.loginorgid !== '') { // 跳转到主页
                          for (let i = 0; i < tArr.length; i++) {
                            console.log(tArr[i])
                            if (tArr[i].items) {
                              _this.$router.push({ name: tArr[i].items[0].to.name })
                              return
                            } else {
                              if (tArr[i].to) {
                                _this.$router.push({ name: tArr[i].to.name })
                                return
                              }
                            }
                          }
                          // } else { // 跳转到完善组织信息的页面
                          // this.$router.push({name: 'improveOrgInfo'})
                          // }
                        }).catch(err => {
                          console.log(err)
                          this.isError = true
                          this.errorMsg = data.error_description ? data.error_description : '查询用户数据失败,请检查用户名或密码是否正确'
                          this.progressShow = false
                          this.loginDisabled = false
                        })
                      } else {
                        this.isError = true
                        this.errorMsg = '您暂无系统使用权限,请联系企业管理员开通'
                        this.progressShow = false
                        this.loginDisabled = false
                      }
                    })
                  } else {
                    if (data.error) {
                      this.isError = true
                      this.errorMsg = data.error_description ? data.error_description : '请检查用户名或密码是否正确'
                      this.progressShow = false
                      this.loginDisabled = false
                    }
                  }
                }, err => {
                  console.log(err)
                  this.isError = true
                  this.errorMsg = '请检查用户名或密码是否正确!'
                  this.progressShow = false
                  this.loginDisabled = false
                })
              })
            } else {
              console.log(2222222222)
              this.errorMsg = data.data.message
              // this.isError = true
              // this.progressShow = false
              // this.loginDisabled = false
            }
          }).catch(err => {
            console.log(err)
          })
        } else {
          if (this.loginUser.account && this.loginUser.account) {
            if (this.loginUser.account.length > 0 && this.loginUser.password.length > 0) {
              this.message = '用户名和密码错误'
              this.progressShow = false
              this.loginDisabled = false
            } else {
              this.progressShow = false
              this.loginDisabled = false
              this.message = '请输入用户名和密码'
            }
          } else {
            this.progressShow = false
            this.loginDisabled = false
            this.message = '请输入用户名和密码'
          }
        }
      })
    },
    // 设置cookie
    setCookie (account, password, exdays) {
      var exdate = new Date() // 获取时间
      exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays) // 保存的天数
      // 字符串拼接cookie
      window.document.cookie = 'account' + '=' + account + ';path=/;expires=' + exdate.toGMTString()
      window.document.cookie = 'password' + '=' + password + ';path=/;expires=' + exdate.toGMTString()
    },
    // 读取cookie
    getCookie () {
      if (document.cookie.length > 0) {
        var arr = document.cookie.split(';') // 这里显示的格式需要切割一下自己可输出看下
        for (var i = 0; i < arr.length; i++) {
          var arr2 = arr[i].split('=') // 再次切割
          // 判断查找相对应的值
          if (arr2[0].trim() === 'account') {
            this.loginUser.account = arr2[1] // 保存到保存数据的地方
          } else if (arr2[0].trim() === 'password') {
            this.loginUser.password = arr2[1]
            this.isRememberMe = true
          }
        }
      }
    },
    // 清除cookie
    clearCookie () {
      this.setCookie('', '', -1) // 修改2值都为空，天数为负1天就好了
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
    'loginUser.account': function (newVal, oldVal) {
      if (!this.loginUser.account) {
        this.loginUser.password = ''
      }
    }
  }
}
</script>
<style scoped>
.progressWrapper {
  position: absolute;
  left: 200px;
}
</style>
