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
        <div style="background:url('static/regAndLogin/beijing.png');background-size:100%;height:height:100%;width:100%">
          <v-layout row>
            <v-flex xs6></v-flex>
            <v-flex xs5>
              <form ref="passwordForm"
                    autocomplete="off"
                    style="height:420px;width:400px;margin-top:20px;margin-bottom:20px;background-color:#fff;margin-right: 0px;margin-left: auto">
                <v-layout row>
                  <v-flex xs12
                          style="margin-top:20px;text-align: center;font-size: 24px;">忘记密码</v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:20px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <!-- <input type="password" name="password1"  style="display:none"/> -->
                    <v-text-field style="height:38px;width:340px;"
                                  solo
                                  placeholder="用户名/手机号"
                                  v-model="forgetPwdUser.phone"
                                  prepend-icon="phone"
                                  :color="aColor"
                                  maxlength="11"
                                  v-validate="'required|min:11'"
                                  data-vv-name="phone"
                                  data-vv-as="用户名/手机号"
                                  @input="inputPhoneChange"
                                  required
                                  autofocus
                                  clearable
                                  onkeyup="this.value=this.value.replace(/\D/g,'')"
                                  autocomplete="off"
                                  disableautocomplete></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:24px;"
                          justify-center>
                  <v-flex xs7
                          md7
                          lg7>
                    <v-text-field style="height:38px"
                                  solo
                                  label="验证码"
                                  v-model="forgetPwdUser.identifycode"
                                  prepend-icon="check_circle"
                                  color="grey lighten-1"
                                  v-validate="'required'"
                                  data-vv-name="identifycode"
                                  data-vv-as="验证码"
                                  required
                                  @blur="checkVerificode"
                                  :disabled="disabledText"
                                  onkeyup="this.value=this.value.replace(/\D/g,'')"
                                  autocomplete="off"
                                  disableautocomplete></v-text-field>
                  </v-flex>
                  <v-flex xs3
                          md3
                          lg3
                          text-xs-right>
                    <v-btn style="height:46px;width:107px;background-color: #2196f3;color: #fff;font-size: 14px;margin:0"
                           @click="getIdentifycode"
                           :disabled="isDisabledIdentifycode">
                      {{identifycodeBtnTxt}}
                    </v-btn>
                  </v-flex>
                  <!-- <v-flex></v-flex> -->
                </v-layout>
                <v-layout row
                          style="margin-top:24px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-text-field style="height:38px;width:340px;"
                                  solo
                                  label="8-16位字母和数字"
                                  hint="至少8个字符"
                                  v-model="forgetPwdUser.password"
                                  :color="pColor"
                                  prepend-icon="lock"
                                  :append-icon="e1 ? 'visibility' : 'visibility_off'"
                                  @click:append="() => (e1 = !e1)"
                                  :type="e1 ? 'text' : 'password'"
                                  :counter="16"
                                  minlength="8"
                                  maxlength="16"
                                  v-validate="'required|min:8'"
                                  data-vv-name="password"
                                  data-vv-as="密码"
                                  required
                                  @input="blurPassword"
                                  onkeyup="this.value=this.value.replace(/[\W]/g,'')"
                                  autocomplete="off"
                                  disableautocomplete></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row
                          justify-center
                          style="margin-top:20px;">
                  <v-flex xs10
                          md10
                          lg10
                          style="margin-bottom:0px;color: red">{{this.forgetPwdTips}}</v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:20px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-btn style="height:38px;width:340px;background-color: #2196f3;color: #fff;font-size: 17px;margin:0"
                           large
                           @click="resetPwd">完成
                    </v-btn>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:20px;">
                  <v-flex xs5></v-flex>
                  <v-flex xs7>
                    <router-link tag="div"
                                 style="cursor:pointer"
                                 :to="{ path: 'login' }">返回登录</router-link>
                  </v-flex>
                </v-layout>
              </form>
            </v-flex>
            <v-flex xs1></v-flex>
          </v-layout>
        </div>
      </v-flex>
    </v-layout>
    <!-- <v-layout row style="margin-top: 50px">
      <v-flex xs12>
        <div  style="text-align: center;font-family: 微软雅黑;font-size: 12px;font">云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有</div>
      </v-flex>
    </v-layout> -->
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
          云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有&nbsp;&nbsp;&nbsp;&nbsp;Ver3.0.80424
        </v-flex>
      </v-layout>
    </v-footer>
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
</template>

<script>
import { getIdentifycode, checkVerificode, updatePasswordByPhone, checkUserIsOwen } from '@/apis/regAndLogin/regAndLogin'
// import {excuteApis} from '@/apis'

export default {
  data () {
    return {
      valid: true,
      e1: false, // 默认的密码框不给看
      isDisabledIdentifycode: false, // 【获取验证码】的按钮是否可点击
      identifycodeBtnTxt: '获取验证码',
      checkVerificodeFlag: false, // 验证码是否校验通过
      disabledText: true, // 验证码文本框是否可用
      count: 60,
      timer: null,
      forgetPwdUser: { // 注册的用户
        phone: '',
        password: '',
        identifycode: ''
      },
      forgetPwdTips: '',
      aColor: 'grey lighten-1',
      pColor: 'grey lighten-1'
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
  methods: {
    inputPhoneChange () {
      if (this.forgetPwdUser.phone) {
        this.aColor = 'grey darken-4'
        let flag = this.forgetPwdUser.phone === 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(this.forgetPwdUser.phone)
        if (flag) {
          this.forgetPwdTips = ''
          // 校验手机号是否存在
          this.checkUserIsOwen() // 1：存在，0：不存在 手机号码存在的情况下不允许修改
          this.blurPhoneChange()
        } else {
          this.isDisabledIdentifycode = false
          this.forgetPwdTips = '请输入正确的手机号'
        }
      } else {
        this.aColor = 'grey lighten-1'
      }
    },
    blurValidatorPwd () {
      let errors = this.$validator.errors.items
      if (errors.length > 0) {
        this.forgetPwdTips = errors[0].msg
        this.$toast.top('请根据错误提示修改正确后再保存！')
        return false
      }
      return true
    },
    blurPhoneChange () {
      this.isDisabledIdentifycode = false // 发送校验码的按钮可用
      this.forgetPwdUser.identifycode = '' // 验证码重置为空
      this.checkVerificodeFlag = false // 没用验证通过
    },
    checkUserIsOwen () {
      checkUserIsOwen(this.forgetPwdUser.phone).then(data => {
        // excuteApis(Object.assign({}, {phone: this.forgetPwdUser.phone}), global.constant.userApisUrls, 'user', 'checkUserIsOwen').then(data => {
        let res = data.data
        if (res.flag === 0) { // 1：存在，0：不存在 手机号码不存在的情况下不允许修改
          // this.$toast.top('手机号不存在')
          this.forgetPwdTips = '手机号不存在'
          this.isDisabledIdentifycode = true
          return
        } else {
          this.isDisabledIdentifycode = false
        }
      })
    },
    blurPassword () {
      let errors = this.$validator.errors.items
      errors.splice(0, errors.length)
      if (this.forgetPwdUser.password) {
        if (this.forgetPwdUser.password.length >= 8) {
          this.pColor = 'grey darken-4'
        }
        let newpassword = this.forgetPwdUser.password
        let flag = /^[A-Za-z0-9]+$/.test(newpassword)
        if (!flag) {
          this.forgetPwdTips = '密码只能输入数字或字母'
        } else {
          if (newpassword.length < 6 || newpassword.length > 16) {
            this.forgetPwdTips = '密码为6-16位字母和数字'
          } else {
            this.forgetPwdTips = ''
          }
        }
      } else {
        this.pColor = 'grey lighten-1'
        this.forgetPwdTips = '新密码是必须的'
      }
      if (this.forgetPwdTips) {
        let errorP = { field: 'password', msg: this.forgetPwdTips }
        this.$validator.errors.items.push(errorP)
      }
    },
    // *****************************发送验证码************************
    getIdentifycode () {
      if (!this.forgetPwdUser.phone) {
        this.forgetPwdTips = '请输入手机号'
        return
      }
      /* 检测表单并处理 */
      this.$validator.validateAll(['phone']).then((result) => {
        if (result) { // 如果表单验证通过
          let phone = this.forgetPwdUser.phone
          let flag = phone.length === 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(phone)
          if (!flag) {
            this.forgetPwdTips = '请输入正确的手机号'
            return
          }
          getIdentifycode(this.forgetPwdUser).then(data => {
            // excuteApis(Object.assign({}, {phone: this.forgetPwdUser.phone}), global.constant.userApisUrls, 'verificode', 'getIdentifycode').then(data => {
            let res = data.data
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              // this.$toast.top(res.message)
              this.forgetPwdTips = res.message
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.count = 60
              this.timer = setInterval(() => {
                if (this.count > 0) {
                  this.count--
                  this.identifycodeBtnTxt = this.count + ' 秒'
                  this.disabledText = false
                } else {
                  clearInterval(this.timer)
                  this.timer = null
                  this.isDisabledIdentifycode = false // 发送校验码的按钮可用
                  this.identifycodeBtnTxt = '重新发送'
                }
              }, 1000)
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              // this.$toast.top(res.message)
              this.forgetPwdTips = res.message
            }
          }
          )
        }
      }
      )
    },
    // *****************************校验验证码************************
    checkVerificode () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['phone', 'identifycode']).then((result) => {
        if (result) { // 如果表单验证通过
          checkVerificode(this.forgetPwdUser).then(data => {
            // excuteApis(Object.assign({}, {phone: this.forgetPwdUser.phone}, {verificode: this.forgetPwdUser.verificode}), global.constant.userApisUrls, 'verificode', 'checkVerificode').then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              // this.$toast.top(res.message)
              // this.forgetPwdTips = res.message // bug 8038 校验通过后不提示
              this.forgetPwdTips = ''
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.checkVerificodeFlag = true // 校验是否通过为通过
              clearInterval(this.timer)
              this.disabledText = true // 校验通过后不让其编辑验证码框
              this.identifycodeBtnTxt = '获取验证码'
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              // this.$toast.top(res.message)
              this.forgetPwdTips = res.message
            }
          })
        }
      }
      )
    },
    // *****************************密码重置************************ Password Reset
    resetPwd () {
      if (!this.forgetPwdUser.phone) {
        this.forgetPwdTips = '请输入手机号！'
        return
      }
      if (!this.checkVerificodeFlag) {
        if (this.forgetPwdUser.identifycode) {
          // this.$toast.top('验证码错误！')
          this.forgetPwdTips = '验证码错误！'
        } else {
          // this.$toast.top('请校验验证码！')
          this.forgetPwdTips = '请输入验证码！'
        }
        return
      }
      if (!this.forgetPwdUser.password) {
        this.forgetPwdTips = '请输入新密码！'
        return
      }
      this.$validator.validateAll(['phone', 'password']).then((result) => {
        if (result && this.blurValidatorPwd()) { // 如果表单验证通过
          this.forgetPwdUser.islogin = 0
          updatePasswordByPhone(this.forgetPwdUser).then(data => {
            // excuteApis(Object.assign({}, this.forgetPwdUser), global.constant.userApisUrls, 'user', 'updatePasswordByPhone').then(data => {
            let res = data.data
            if (res.flag === 1) { // 密码重置成功
              // this.openTipsDialog('success', res.message)
              this.$toast.top(res.message)
              let router = this.$router
              let phone = this.forgetPwdUser.phone
              setTimeout(function () {
                router.push({ name: 'resetPwdSuc', query: { account: phone } })
              }, 2000) // 延迟2 秒
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              // this.$toast.top(res.message)
              this.forgetPwdTips = res.message
              this.disabledText = true // 验证码框可输入
              this.checkVerificodeFlag = false // 验证码还未验证
              this.isDisabledIdentifycode = false // 发送校验码的按钮可用
              this.forgetPwdUser.phone = ''
              this.forgetPwdUser.password = ''
              this.forgetPwdUser.identifycode = ''
            }
          })
        }
      })
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
    'forgetPwdUser.phone': function (newVal, oldVal) {
      if (!this.forgetPwdUser.phone) {
        this.forgetPwdUser.password = ''
      }
    }
  }
}
</script>
<style scoped>
</style>
