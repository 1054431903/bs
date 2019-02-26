<template>
  <v-card class="page-content pt-0">
  <div pa-2>
    <v-flex xs12 sm12 md12 lg12>
      <v-card>
        <v-container>
          <v-layout>
            <div class="tips text-sx-center">个人资料编辑完成，
              <v-btn flat color="primary" class="btntips" :to="{name: 'personalCenter', query: {user: user,tabIndex: '0'}}">立即查看</v-btn>
            </div>
          </v-layout>
        </v-container>
      </v-card>
    </v-flex>
  </div>
  </v-card>
</template>

<script>
  export default {
    data () {
      return {
        user: {
          userid: '', // ID
          photoURL: '', // 头像
          account: '', // 用户名
          nickname: '', // 昵称
          phone: '', // 手机
          email: '', // 邮箱
          name: '',  // 姓名
          sex: '',   // 性别
          birthday: '', // 出生日期
          address: '', // 地址
          departmentid: '', // 归属部门
          departmentname: '', // 归属部门
          jobnumber: '' // 工号
        }
      }
    },
    watch: {},
    methods: {},
    computed: {},
    created () {
      this.user = this.$route.query.user
    }
  }
</script>
<style scoped>
  .tips {
    padding: 180px;
    margin-left: auto;
    margin-right: auto;
  }

  .btntips {
    left: -20px;
  }
</style>
