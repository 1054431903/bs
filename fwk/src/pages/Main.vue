<template>
  <v-app id="inspire">
    <vc-navigation-drawer v-model="drawer"
                          :items="menu"></vc-navigation-drawer>
    <v-toolbar app
               fixed
               clipped-left
               :class="theme"
               style="z-index:200">
      <v-toolbar-side-icon dark
                           @click.native.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title class="white--text">{{ $t('Application') }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-menu offset-y
              style="height:100%;z-index:99999">
        <v-btn fab
               dark
               icon
               class="primary"
               slot="activator">
          <v-icon dark>language</v-icon>
        </v-btn>
        <v-list>
          <v-list-tile v-for="lang in locales"
                       :key="lang"
                       @mouseover.native="changeLocale(lang)">
            <v-list-tile-title>{{lang}}</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
      <v-menu offset-y
              style="height:100%;z-index:99999">
        <v-btn fab
               dark
               icon
               class="primary"
               slot="activator">
          <v-icon dark>account_circle</v-icon>
        </v-btn>
        <v-list style="width:200px">
          <v-list-tile>
            <v-list-tile-title style="text-align:center;height:50px;font-size:12px;color:#757575"> {{user.name ? user.name : user.phone}} <br /> {{user.orgName}}</v-list-tile-title>
          </v-list-tile>
          <v-list-tile v-for="pubmenu in pubmenus"
                       :key="pubmenu.path"
                       @click="changePubmenu(pubmenu)"
                       v-if="(pubmenu.name ==='个人中心') || (pubmenu.name ==='切换组织') || pubmenu.name ==='退出登录'">
            <v-list-tile-title text-xs-center
                               style="text-align:center"> {{pubmenu.name}}</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>
    </v-toolbar>
    <v-content>
      <v-bread></v-bread>
      <router-view></router-view>
    </v-content>
    <v-modaldialog :isShow.sync="showChangeOrgInfoDialog"
                   :showCloseBtn="true"
                   title="切换组织"
                   :modalMaxWidth="800"
                   :bodyHeight="300">
      <changeOrgInfo :showDialog.sync="showChangeOrgInfoDialog"></changeOrgInfo>
    </v-modaldialog>
  </v-app>
</template>

<script>
import VcNavigationDrawer from '@vehiclecloud/commons/components/VcNavigationDrawer'
import { mapState } from 'vuex'
import changeOrgInfo from '@/pages/system/organizations/ChangeOrgInfo'
export default {
  data () {
    return {
      dark: false,
      theme: 'primary',
      user: {},
      mini: false,
      drawer: true,
      locales: ['en-US', 'zh-CN'],
      pubmenus: [
        { name: '个人中心', path: 'personalCenter' },
        { name: '切换组织', path: 'changeOrg' },
        { name: '退出登录', path: 'loginOut' }
      ],
      colors: ['primary', 'blue', 'green', 'purple', 'red'],
      showChangeOrgInfoDialog: false
    }
  },
  computed: {
    ...mapState(['message', 'menu', 'pageTitle'])
  },
  methods: {
    changeLocale (to) {
      global.helper.ls.set('locale', to)
      this.$i18n.locale = to
    },
    changePubmenu (pubmenu) {
      if (pubmenu.path === 'personalCenter') {
        this.$router.push({ name: pubmenu.path })
      }
      if (pubmenu.path === 'loginOut') {
        let user = null
        let menu = null
        let bread = []
        let orgLands = []
        localStorage.setItem('atk', null)
        localStorage.setItem('sys-auth', null)
        this.$store.commit('setUser', user)
        this.$store.commit('setMenu', menu)
        this.$store.commit('setBreadList', bread)
        this.$store.commit('setOrgLands', orgLands)
        // 清除农事记录模块缓存的数据(add by zhangyq on 2018-11-08)
        this.$store.commit('setAllFarmLands', [])
        this.$store.commit('setTenantFarmLands', [])
        this.$store.commit('setAllUsers', [])
        this.$store.commit('setTenantUsers', [])
        // end
        this.$router.push({ name: 'login' })
      }
      if (pubmenu.path === 'changeOrg') {
        // this.$router.push({name: 'changeOrgInfo'})
        this.showChangeOrgInfoDialog = true
      }
    },
    fetchMenu () {
      // fetch menu from server
      // this.$http.get('menu').then(({data}) => this.$store.commit('setMenu', data))
    }
  },
  created () {
    // this.fetchMenu()
    console.log('****************')
    global.helper.ls.set('theme', this.theme)
    this.user = global.helper.ls.get('user')
    this.$store.dispatch('getOrgLands')
    // 为农事记录模块提前缓存数据(add by zhangyq on 2018-11-08)
    this.$store.dispatch('getAllFarmLands')
    this.$store.dispatch('getTenantFarmLands', this.user.loginorgid)
    this.$store.dispatch('getAllUsers')
    this.$store.dispatch('getTenantUsers', this.user.loginorgid)
    // end
  },
  beforeUpdate: function () {
    if (global.helper.ls.get('user')) {
      this.user.orgName = global.helper.ls.get('user').orgName
    }
  },
  components: {
    VcNavigationDrawer,
    changeOrgInfo
  }
}
</script>

<style scoped>
.breadcrumbs {
  justify-content: left;
  padding-left: 0px;
}
.theme--light .navigation-drawer,
.application .theme--light.navigation-drawer {
  background-color: #fafafa;
}
.theme--light.application {
  background: #f3f3f3;
}
</style>

