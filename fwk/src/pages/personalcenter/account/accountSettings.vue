<template>
  <v-card>
    <v-expansion-panel expand>
      <v-expansion-panel-content v-model="isExpandEditPwd">
        <div slot="header"
             class="headline">修改密码
          <v-subheader>修改密码时需要输入当前密码，如果您忘记当前密码，可以通过手机或邮箱重置您的密码</v-subheader>
        </div>

        <v-card class="grey lighten-3">
          <v-card-text>
            <form ref="passwordForm"
                  autocomplete="off">
              <v-layout row
                        wrap>
                <v-flex xs6>
                  <input type="password"
                         name="password1"
                         style="display:none" />
                  <!--是为了解决自动填充功能的，没有任何的实际意义-->
                  <v-text-field solo
                                full-width
                                label="旧密码"
                                v-model="user.oldpassword"
                                prepend-icon="lock"
                                :append-icon="e1 ? 'visibility' : 'visibility_off'"
                                @click:append="() => (e1 = !e1)"
                                :type="e1 ? 'text' : 'password'"
                                :counter="16"
                                maxlength="16"
                                :error-messages="errors.collect('oldpassword')"
                                v-validate="'required|min:8'"
                                data-vv-name="oldpassword"
                                data-vv-as="旧密码"
                                required
                                onkeyup="this.value=this.value.replace(/[\W]/g,'')"
                                autocomplete="off"
                                disableautocomplete></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row
                        wrap>
                <v-flex xs6>
                  <!--密码由数字或字母构成，且长度不小于8位-->

                  <v-text-field solo
                                full-width
                                label="新密码, 至少8个字符,只能输入数字或字母"
                                v-model="user.newpassword"
                                prepend-icon="lock"
                                :append-icon="e2 ? 'visibility' : 'visibility_off'"
                                @click:append="() => (e2 = !e2)"
                                :type="e2 ? 'text' : 'password'"
                                :counter="16"
                                maxlength="16"
                                minlength="8"
                                :error-messages="errors.collect('newpassword')"
                                data-vv-name="newpassword"
                                data-vv-as="新密码"
                                required
                                v-validate="'required'"
                                onkeyup="this.value=this.value.replace(/[\W]/g,'')"
                                @input="blurPassword"
                                autocomplete="off"
                                disableautocomplete></v-text-field>
                </v-flex>
                <!--<v-flex :color="primary">密码由数字或字母构成</v-flex>-->
              </v-layout>
              <v-layout row
                        wrap>
                <!--
                  <v-flex xs7 style="margin-top:-35px;color: #b71c1c;font-size: 12px;margin-left: 72px">
                    {{this.newpasswordTips}}
                  </v-flex>
                  -->
              </v-layout>
              <v-layout row
                        wrap>
                <v-flex xs1>
                  <v-btn color="primary"
                         @click="saveEditPassword">保存</v-btn>
                </v-flex>
              </v-layout>
            </form>
          </v-card-text>
        </v-card>
      </v-expansion-panel-content>
      <v-expansion-panel-content v-model="isExpandEditPhone">
        <div slot="header"
             class="headline">修改手机
          <v-subheader>您当前的手机号码是{{user.oldphone}}，修改手机时系统会以短信的形式发送验证码到新的手机号码上，然后输入验证码后完成修改</v-subheader>
        </div>
        <v-card class="grey lighten-3">
          <v-card-text>
            <form ref="phoneForm">
              <v-layout row
                        wrap>
                <v-flex xs7
                        style="margin-bottom:0px;color: red">{{this.errorPhoneTips}}</v-flex>
              </v-layout>
              <v-layout row
                        wrap>
                <v-flex xs6>
                  <v-text-field solo
                                full-width
                                label="手机号码"
                                hint="手机号码"
                                name="newphone"
                                maxlength="11"
                                v-model="user.newphone"
                                :error-messages="errors.collect('newphone')"
                                v-validate="'required|min:11'"
                                data-vv-name="newphone"
                                data-vv-as="手机号码"
                                @input="blurNewphoneChange"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row
                        wrap>
                <v-flex xs6>
                  <v-text-field solo
                                full-width
                                label="验证码"
                                hint="验证码"
                                v-model="user.identifycode"
                                :error-messages="errors.collect('identifycode')"
                                maxlength="6"
                                v-validate="'required|min:6'"
                                data-vv-name="identifycode"
                                data-vv-as="验证码"
                                required
                                @input="blurVerificodeChange"
                                :disabled="disabledText"
                                onkeyup="this.value=this.value.replace(/\D/g,'')"></v-text-field>
                </v-flex>
                <v-flex xs1>
                  <v-btn mt-0
                         color="primary"
                         @click="getIdentifycode"
                         :disabled="isDisabledIdentifycode">{{identifycodeBtnTxt}}</v-btn>
                </v-flex>
              </v-layout>
              <v-layout row
                        wrap>
                <v-flex xs1>
                  <v-btn color="primary"
                         @click="saveEditPhone"
                         :disabled="isDisabledSaveEditPhone">保存</v-btn>
                </v-flex>
              </v-layout>
            </form>
          </v-card-text>
        </v-card>
      </v-expansion-panel-content>
    </v-expansion-panel>
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
// import { queryFarmerAgreementsList } from '@/apis/archives/farmeragreements'
import { excuteApis } from '@/apis'
// import {checkVerificode, getIdentifycode, updatePassword, updatePhone, checkUserIsOwen} from '@/apis/personalcenter/personal'
export default {
  data () {
    return {
      e1: false, // 默认的密码框不给看
      e2: false, // 默认的密码框不给看
      user: {
        id: '',
        userid: '',
        oldpassword: '',
        newpassword: '',
        oldphone: '',
        newphone: '',
        identifycode: ''
      },
      isExpandEditPhone: false, // 是否折叠修改手机号的表单
      isExpandEditPwd: false, // 是否折叠修改密码的表单
      isDisabledIdentifycode: false, // 发送验证码的按钮是否可点击
      isDisabledSaveEditPhone: false,
      // show: true,
      count: 60,
      timer: null,
      identifycodeBtnTxt: '发送验证码',
      checkVerificodeFlag: false, // 验证码是否校验通过
      disabledText: true, // 验证码文本框是否可用
      errorPhoneTips: '',
      pColor: 'grey lighten-1',
      newpasswordTips: ''
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
    this.fetchData()
  },
  methods: {
    blurValidatorPwd () {
      let errors = this.$validator.errors.items
      if (errors.length > 0) {
        this.$toast.top('请根据错误提示修改正确后再保存！')
        return false
      }
      return true
    },
    blurPassword () {
      // let errorP = {field: 'newpassword', msg: this.newpasswordTips}
      // this.$validator.errors.items.pop(errorP)
      let errors = this.$validator.errors.items
      errors.splice(0, errors.length)
      if (this.user.newpassword) {
        let newpassword = this.user.newpassword
        let flag = /^[A-Za-z0-9]+$/.test(newpassword)
        if (!flag) {
          this.newpasswordTips = '密码只能输入数字或字母'
        } else {
          if (newpassword.length < 8 || newpassword.length > 16) {
            this.newpasswordTips = '密码为8-16位字母和数字'
          } else {
            this.newpasswordTips = ''
          }
        }
      } else {
        this.newpasswordTips = '新密码是必须的'
      }
      if (this.newpasswordTips) {
        let errorP = { field: 'newpassword', msg: this.newpasswordTips }
        this.$validator.errors.items.push(errorP)
      }
    },
    blurVerificodeChange (verificode) {
      if (verificode && verificode.length === 6) {
        this.checkVerificode()
      } else {
        this.isDisabledSaveEditPhone = true
      }
    },
    fetchData () {
      let globalUser = global.helper.ls.get('user')
      this.user.userid = globalUser.id
      this.user.id = globalUser.id
      this.user.oldphone = globalUser.phone
    },
    saveEditPassword () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['oldpassword', 'newpassword']).then((result) => {
        if (result && this.blurValidatorPwd()) { // 如果表单验证通过
          excuteApis(Object.assign({}, this.user), global.constant.userApisUrls, 'user', 'updatePassword').then(data => {
            // updatePassword(this.user).then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              this.user.staffLastUpdateTime = res.data.staffLastUpdateTime
              this.setCacheUserInfo(this.user)
              this.$toast.top(res.message)
              // this.isExpandEditPhone = false
              this.isExpandEditPwd = false
              this.user.oldpassword = ''
              this.user.newpassword = ''
              this.$validator.reset()
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              this.$toast.top(res.message)
            }
          }
          )
        }
      })
    },
    getIdentifycode () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['newphone']).then((result) => {
        if (result) { // 如果表单验证通过
          if (this.user.oldphone === this.user.newphone) {
            this.$toast.top('修改的手机号码跟当前的手机号一致，不需要修改！')
            return
          }
          excuteApis(Object.assign({}, { phone: this.user.newphone }), global.constant.userApisUrls, 'verificode', 'getIdentifycode').then(data => {
            // getIdentifycode(this.user).then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              this.disabledText = false
              // this.openTipsDialog('success', res.message)
              this.$toast.top(res.message)
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.count = 60
              this.timer = setInterval(() => {
                if (this.count > 0) {
                  this.count--
                  this.identifycodeBtnTxt = this.count + ' 秒'
                } else {
                  clearInterval(this.timer)
                  this.timer = null
                  this.isDisabledIdentifycode = false // 发送校验码的按钮可用
                  this.identifycodeBtnTxt = '发送验证码'
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
    checkVerificode () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['newphone', 'identifycode']).then((result) => {
        if (result) { // 如果表单验证通过
          excuteApis(Object.assign({}, { phone: this.user.newphone, verificode: this.user.identifycode }), global.constant.userApisUrls, 'verificode', 'checkVerificode').then(data => {
            // checkVerificode(this.user).then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              this.$toast.top(res.message)
              this.isDisabledIdentifycode = true // 发送校验码的按钮不可用
              this.checkVerificodeFlag = true // 校验是否通过为通过
              clearInterval(this.timer)
              this.disabledText = true // 校验通过后不让其编辑验证码框
              this.isDisabledSaveEditPhone = false
            } else {
              this.$toast.top(res.message)
              this.isDisabledSaveEditPhone = true
            }
          }
          )
        }
      }
      )
    },
    saveEditPhone () {
      /* 检测表单并处理 */
      this.$validator.validateAll(['newphone', 'identifycode']).then((result) => {
        if (result) { // 如果表单验证通过
          if (!this.checkVerificodeFlag) {
            // this.openTipsDialog('red lighten-1', '请先验证新的手机号')
            if (this.user.identifycode) {
              this.$toast.top('验证码错误')
            } else {
              this.$toast.top('请输入验证码')
            }
            return
          }
          excuteApis(Object.assign({}, { phone: this.user.newphone, userid: this.user.userid }), global.constant.userApisUrls, 'user', 'updatePhoneById').then(data => {
            // updatePhone(this.user).then(data => {
            let res = data.data
            console.log(data)
            if (res.flag === 1) {
              // this.openTipsDialog('success', res.message)
              this.isExpandEditPhone = false // 折叠修改手机的表单
              this.user.oldphone = this.user.newphone // 更新手机号
              this.user.staffLastUpdateTime = res.data.staffLastUpdateTime
              // 以下代码不能调整顺序
              this.setCacheUserInfo(this.user, 1)
              this.user.newphone = ''
              this.user.identifycode = ''
              this.checkVerificodeFlag = false // 校验是否通过恢复默认值
              clearInterval(this.timer)
              this.timer = null
              this.isDisabledIdentifycode = false // 发送验证码的按钮可用
              this.identifycodeBtnTxt = '发送验证码'
              this.disabledText = false // 验证码输入框可用
              this.$toast.top(res.message)
              this.$validator.reset()
            } else {
              // this.openTipsDialog('red lighten-1', res.message)
              this.$toast.top(res.message)
            }
          }
          )
        }
      })
    },
    // 赋值操作
    setCacheUserInfo (editUser, isUpdatePhone) {
      let user = global.helper.ls.get('user')
      if (isUpdatePhone && isUpdatePhone === '1') {
        user.phone = editUser.newphone
      }
      if (editUser.staffLastUpdateTime) {
        user.operateredittime = editUser.staffLastUpdateTime
      }
      this.$store.dispatch('cacheUserInfo', user)
    },
    blurNewphoneChange (inputPhone) {
      if (inputPhone) {
        let flag = inputPhone.length === 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(inputPhone)
        if (flag) {
          this.errorPhoneTips = ''
          if (this.user.oldphone === this.user.newphone) {
            this.$toast.top('修改的手机号码跟当前的手机号一致，不需要修改！')
            return
          }
          // 校验手机号是否存在
          this.checkUserIsOwen() // 1：存在，0：不存在 手机号码存在的情况下不允许修改
          this.blurPhoneChange()
        } else {
          this.errorPhoneTips = '请输入正确的手机号'
        }
      }
    },
    blurPhoneChange () {
      this.disabledText = false // 发送校验码的按钮可用
      this.user.identifycode = '' // 验证码重置为空
      this.checkVerificodeFlag = false // 没用验证通过
    },
    checkUserIsOwen () {
      excuteApis(Object.assign({}, { phone: this.user.newphone }), global.constant.userApisUrls, 'user', 'checkUserIsOwen').then(data => {
        // checkUserIsOwen(this.user.newphone).then(data => {
        let res = data.data
        if (res.flag === 1) { // 1：存在，0：不存在 手机号码存在的情况下不允许修改
          this.$toast.top('手机号已经存在')
          this.isDisabledIdentifycode = true
          return
        } else {
          this.isDisabledIdentifycode = false
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
    isExpandEditPhone: function (val, oldVal) {
      if (val) {
        this.$validator.reset()
      }
    },
    isExpandEditPwd: function (val, oldVal) {
      if (val) {
        this.$validator.reset()
        let errorP = { field: 'newpassword', msg: this.newpasswordTips }
        this.$validator.errors.items.pop(errorP)
      }
    }
  }
}
</script>

<style scoped>
.textlen {
  width: 10px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.headline {
  font-size: 14px !important;
  font-weight: 600;
  line-height: 32px !important;
  letter-spacing: normal !important;
}
</style>
