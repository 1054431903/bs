<template>
  <v-container fluid>
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
        <div style="background:url('static/regAndLogin/beijing.png');background-size:100%;height:500px;width:100%">
          <v-layout row>
            <v-flex xs6></v-flex>
            <v-flex xs5>
              <v-form style="height:432px;width:400px;margin-top:34px;background-color:#fff;margin-right: 0px;margin-left: auto"
                      ref="form">
                <v-layout row>
                  <v-flex xs12
                          style="margin-top:20px;text-align: center;font-size: 24px;">注册</v-flex>
                </v-layout>
                <v-layout row
                          justify-center
                          style="margin-top:20px;">
                  <v-flex xs10
                          md10
                          lg10
                          style="margin-bottom:0px;color: red">{{this.errorPhoneTips}}</v-flex>
                </v-layout>
                <v-layout row
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-text-field style="height:38px;width:340px;"
                                  solo
                                  label="手机号"
                                  hint="手机号"
                                  v-model="regUser.phone"
                                  prepend-icon="phone"
                                  color="grey lighten-1"
                                  :counter="11"
                                  maxlength="11"
                                  :error-messages="errors.collect('phone')"
                                  v-validate="{required: true, regex: /(13|14|15|17|18)[0-9]{1}\d{8}/ }"
                                  data-vv-name="phone"
                                  data-vv-as="手机号"
                                  required
                                  @change="inputPhoneChange"
                                  onkeyup="this.value=this.value.replace(/\D/g,'')"></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:25px;"
                          justify-center>
                  <v-flex xs5
                          md5
                          lg5>
                    <v-text-field style="height:38px;width:250px"
                                  solo
                                  label="验证码"
                                  v-model="regUser.identifycode"
                                  :prepend-icon="identifycodeIcon"
                                  color="grey lighten-1"
                                  :counter="8"
                                  :error-messages="errors.collect('identifycode')"
                                  v-validate="'required'"
                                  data-vv-name="identifycode"
                                  data-vv-as="验证码"
                                  required
                                  @blur="checkVerificode"
                                  :disabled="disabledText"></v-text-field>
                  </v-flex>
                  <v-flex xs5
                          md5
                          lg5
                          text-xs-right>
                    <v-btn small
                           light
                           style="height:44px;width:15px;background-color: #2196f3;font-size: 14spx;color:#fff;margin:0"
                           @click="getIdentifycode"
                           :disabled="isDisabledIdentifycode">
                      {{identifycodeBtnTxt}}
                    </v-btn>
                  </v-flex>
                  <!-- <v-flex></v-flex> -->
                </v-layout>
                <v-layout row
                          style="margin-top:10px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-text-field style="height:38px;width:340px;"
                                  solo
                                  label="8-16位字母和数字"
                                  hint="至少8个字符"
                                  v-model="regUser.password"
                                  color="grey lighten-1"
                                  prepend-icon="lock"
                                  :append-icon="e1 ? 'visibility' : 'visibility_off'"
                                  @click:append="() => (e1 = !e1)"
                                  :type="e1 ? 'text' : 'password'"
                                  :counter="8"
                                  minlength="8"
                                  :error-messages="errors.collect('password')"
                                  v-validate="'required|min:8|alpha_num'"
                                  data-vv-name="password"
                                  data-vv-as="密码"
                                  required
                                  @change="passwordChange"></v-text-field>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-top:20px;"
                          justify-center>
                  <v-flex xs10
                          md10
                          lg10>
                    <v-btn style="height:38px;width:340px;background-color: #2196f3;color:#fff;font-size: 17px;margin:0"
                           large
                           @click="register"
                           :disabled=!checkVerificodeFlag>完成
                    </v-btn>
                  </v-flex>
                </v-layout>
                <v-layout row
                          style="margin-bottom:-20px;margin-top: 20px">
                  <v-flex xs12
                          style="margin-left:30px;font-size: 10px;color: red">{{this.agreeShenguProtocolTips}}</v-flex>
                </v-layout>
                <v-layout row
                          style="">
                  <v-flex xs7>
                    <v-subheader style="margin-left:30px;font-size: 10px;">
                      <v-checkbox style="margin-top:22px;"
                                  label=""
                                  v-model="agreeShenguProtocol"
                                  color="blue">
                      </v-checkbox>&nbsp;&nbsp;我已阅读并同意<a href="#">神谷用户协议</a>
                    </v-subheader>
                  </v-flex>
                  <v-flex xs5>
                    <v-subheader right>已有账号，<a href="#/login">返回登陆</a></v-subheader>
                  </v-flex>
                </v-layout>
                <!-- <v-layout row style="margin-top:20px;" justify-center>
                    <v-flex  xs6 md6 lg6 style="font-size: 11px;">
                        <v-flex left>
                          <v-checkbox
                            v-model="agreeShenguProtocol"
                            hide-details
                          >
                          </v-checkbox >
                        </v-flex>
                        <v-flex xs5 md5 lg5 left >
                          我已阅读并同意<router-link :to="{ path: '' }">《神谷用户协议》</router-link>
                        </v-flex>
                    </v-flex>
                    <v-flex xs4 md4 lg4 text-xs-right>
                      已有账号，<router-link :to="{ path: 'login' }">返回登陆</router-link>
                    </v-flex>
                  </v-layout> -->
              </v-form>
            </v-flex>
            <v-flex xs1></v-flex>
          </v-layout>
        </div>
      </v-flex>
    </v-layout>
    <!-- <v-layout row style="margin-top: 50px">
        <v-flex xs12>
          <div style="text-align: center;font-family: 微软雅黑;font-size: 12px;font">云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有</div>
        </v-flex>
      </v-layout> -->
    <v-footer height="auto"
              class="white">
      <v-layout row
                wrap
                justify-center>
        <v-flex xs12
                py-3
                text-xs-center
                grey--text>
          云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有&nbsp;&nbsp;&nbsp;&nbsp;Ver3.0.80320_qa
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
import { excuteApis } from '@/apis'
// import {getIdentifycode, checkVerificode, regist} from '@/apis/regAndLogin/regAndLogin'
export default {
  data () {
    return {
      valid: true,
      e1: false, // 默认的密码框不给看
      isDisabledIdentifycode: false, // 【获取验证码】的按钮是否可点击
      identifycodeBtnTxt: '获取验证码',
      checkVerificodeFlag: false, // 验证码是否校验通过
      disabledText: false, // 验证码文本框是否可用
      count: 60,
      timer: null,
      agreeShenguProtocol: false,
      agreeShenguProtocolTips: '',
      // identifycodeIcon: 'check_circle',
      identifycodeIcon: 'check_circle',
      errorPhoneTips: '',
      regUser: { // 注册的用户
        phone: '',
        password: '',
        identifycode: ''
      }
    }
  },
  created () {

  },
  methods: {
    inputPhoneChange (inputPhone) {
      if (inputPhone) {
        let flag = inputPhone.length === 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(inputPhone)
        if (flag) {
          this.errorPhoneTips = ''
        } else {
          this.errorPhoneTips = '请输入正确的手机号'
        }
      }
    },
    // *****************************发送验证码************************
    getIdentifycode () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['phone']).then((result) => {
        if (result) { // 如果表单验证通过
          excuteApis(Object.assign({}, { phone: this.regUser.phone }), global.constant.userApisUrls, 'verificode', 'getIdentifycode').then(data => {
            // getIdentifycode(this.regUser).then(data => {
            let res = data.data
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              this.$toast.top(res.message)
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.timer = setInterval(() => {
                if (this.count > 0) {
                  this.count--
                  this.identifycodeBtnTxt = this.count + ' 秒'
                } else {
                  clearInterval(this.timer)
                  this.timer = null
                  this.isDisabledIdentifycode = false // 发送校验码的按钮可用
                  this.identifycodeBtnTxt = '获取验证码'
                }
              }, 1000)
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              this.$toast.top(res.message)
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
          excuteApis(Object.assign({}, { phone: this.regUser.phone }, { verificode: this.regUser.verificode }), global.constant.userApisUrls, 'verificode', 'checkVerificode').then(data => {
            // checkVerificode(this.regUser).then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              // this.openTipsDialog('sucess', res.message)
              this.$toast.top(res.message)
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.checkVerificodeFlag = true // 校验是否通过为通过
              clearInterval(this.timer)
              this.disabledText = true // 校验通过后不让其编辑验证码框
              this.identifycodeBtnTxt = '获取验证码'
              this.checkVerificodeFlag = true
              this.identifycodeIcon = 'check_circle' // 验证通过
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              this.$toast.top(res.message)
              this.identifycodeIcon = 'cancel' // 验证不通过
            }
          }
          )
        }
      }
      )
    },
    // *****************************注册************************
    register () {
      this.$validator.validateAll(['phone', 'password']).then((result) => {
        if (result) { // 如果表单验证通过
          if (!this.agreeShenguProtocol) {
            this.agreeShenguProtocolTips = '您是否同意神谷用户协议'
            return
          }
          excuteApis(Object.assign({}, this.regUser, { verificode: this.regUser.verificode }), global.constant.userApisUrls, 'user', 'regist').then(data => {
            // regist(this.regUser).then(data => {
            let res = data.data
            if (res.flag === 1) { // 注册成功跳转到登陆页面
              // this.openTipsDialog('success', '注册成功')
              this.$toast.top(res.message)
              let router = this.$router
              let phone = this.regUser.phone
              setTimeout(function () {
                router.push({ name: 'login', query: { account: phone } })
              }, 2000) // 延迟2 秒
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              this.$toast.top(res.message)
              this.disabledText = false // 验证码框可输入
              this.checkVerificodeFlag = false // 验证码还未验证
              this.isDisabledIdentifycode = false // 发送校验码的按钮可用
              this.regUser.phone = ''
              this.regUser.password = ''
              this.regUser.identifycode = ''
            }
          })
        }
      })
    }
  },
  watch: {
    agreeShenguProtocol: function (val, oldVal) {
      if (val) {
        this.agreeShenguProtocolTips = ''
      } else {
        this.agreeShenguProtocolTips = '您是否同意神谷用户协议'
      }
    }
  }
}
</script>
<style scoped>
/*
    div.input-group__input i.material-icons icon input-group__prepend-icon
  */
</style>
