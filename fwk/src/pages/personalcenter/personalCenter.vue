<template>
  <v-card class="page-content">
    <v-tabs
      color="white"
      light
      slider-color="primary"
      v-model="tabActive"
    >
      <v-tab style="margin-left: 0px;text-align: left"
        v-for="tab in tabs"
        :key="tab"
        :href="'#' + tab"
        ripple
      >
          {{ tab }}
      </v-tab>
      <v-tabs-items>

        <!-- 组织信息-->
        <!-- <v-tab-item :id="'组织信息'">
           <organizeInfo></organizeInfo>
        </v-tab-item> -->

        <!-- 个人资料 -->
        <v-tab-item :id="'个人资料'" :key="'个人资料'">
          <personalData></personalData>
        </v-tab-item>

        <!-- 账号设置 -->
        <v-tab-item :id="'账号设置'">
          <accountSettings></accountSettings>
        </v-tab-item>

      </v-tabs-items>
    </v-tabs>


  </v-card>
</template>

<script>
  import accountSettings from '@/pages/personalcenter/account/accountSettings'
  import organizeInfo from '@/pages/personalcenter/organize/organizeInfo'
  import personalData from '@/pages/personalcenter/person/personalData'
  export default {
    components: {
      accountSettings,
      organizeInfo,
      personalData
    },
    data () {
      return {
        // tabs: ['组织信息', '个人资料', '账号设置'],
        tabs: ['个人资料', '账号设置'],
        tabActive: null
      }
    },
    methods: {
      next () {
        this.tabActive = this.tabs[(this.tabs.indexOf(this.tabActive) + 1) % this.tabs.length]
      }
    },
    created () {
      let tabIndex = this.$route.query.tabIndex
      if (typeof (tabIndex) === 'undefined') {
        tabIndex = 0
      }
      this.tabActive = this.tabs[tabIndex]
    }
  }
</script>
<style scoped>
  .personTab {
    border-style: solid;
    border-width: 1px;
  }
  div.tabs__wrapper.tabs__wrapper--scrollable{
    margin-left: 0px !important;
  }
</style>

