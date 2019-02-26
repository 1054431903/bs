<template>
  <div class="py-2">
    <v-container style="border:1px solid #D6D6D6;background-color: #fff; height: 400px">
      <v-layout row style="border-bottom: 2px solid #DDDDDD; margin-top: 0;margin-bottom: 50px">
        <v-flex xs12 sm12 md12 lg12 xl12 style="padding-bottom: 10px">
          <v-flex >编辑组织信息成功</v-flex>
        </v-flex>
      </v-layout>
      <v-layout row style="margin-top: 150px">
        <v-flex xs12 class="text-xs-center">
          <span>
            <v-icon >check_circle</v-icon>组织信息编辑完成，
          </span>

          <span @click="backOrg()" style="color: #3F51B5; cursor: pointer">立即查看</span>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        text: '组织信息编辑完成'
      }
    },
    methods: {
      backOrg () {
        this.$router.push({name: 'organizations'})
      }
    }
  }
</script>

<style scoped>

</style>
