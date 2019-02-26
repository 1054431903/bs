<template>
  <v-container fluid style="padding: 0">
    <v-layout row style="height: 120px">
      <v-flex xs2></v-flex>
      <v-flex xs10>
        <img style="margin-top:36px" src="static/regAndLogin/logo.png" width="198px" height="48px"/>
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs12>
        <div style="background:url('static/regAndLogin/beijing.png');background-size:100%;height:height:100%;width:100%">
          <v-layout row>
            <v-flex xs6></v-flex>
            <v-flex xs5>
              <form ref="passwordForm" autocomplete="off" style="height:420px;width:400px;margin-top:20px;margin-bottom:20px;background-color:#fff;margin-right: 0px;margin-left: auto">
                <v-layout row justify-center style="height:60px;width:400px">
                  <v-flex xs12 style="padding-top:52px;text-align: center;font-size: 24px;">忘记密码</v-flex>
                </v-layout>
                <v-layout row justify-center style="height:120px;width:400px">
                  <img style="margin-top:90px;height:40px;width:40px" src="static/regAndLogin/chenggong.png" />
                  <!--<v-icon color="green" large right style="margin-top:77px;">check_circle</v-icon>-->
                </v-layout>
                <v-layout row style="height:36px;width:400px;margin-top:18px" justify-center>
                  <v-flex xs12 style="padding-top:18px;text-align: center;font-size: 17px;color: #333333">密码重置成功,3秒后自动跳转到登录页</v-flex>
                </v-layout>
                <v-layout row justify-center style="height:195px;width:400px">
                  <v-flex xs12 style="margin-top:56px;text-align: center;font-size: 18px;">
                    <router-link tag="div" style="cursor:pointer;color: #0277BD" :to="{ path: 'login' }">返回登录</router-link>
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
    <v-footer height="auto" class="white" fixed>
      <v-layout row wrap justify-center>
        <v-flex xs12 py-3 text-xs-center grey--text>
          云南神谷科技股份有限公司&nbsp;&nbsp;&nbsp;&nbsp;@版权所有&nbsp;&nbsp;&nbsp;&nbsp;Ver3.0.80424
        </v-flex>
      </v-layout>
    </v-footer>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        account: ''
      }
    },
    watch: {},
    methods: {},
    computed: {},
    created () {
      this.account = this.$route.query.account
      let router = this.$router
      setTimeout(function () {
        router.push({name: 'login', query: {account: this.account}})
      }, 3000) // 延迟3 秒
    }
  }
</script>
<style scoped>

</style>
