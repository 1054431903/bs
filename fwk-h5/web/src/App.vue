<template>
  <v-app id="app">
    <vc-navigation-drawer v-model="drawer.show" :items="drawer.items">
    </vc-navigation-drawer>
    <v-toolbar color="blue" dark app clipped-left fixed>
      <v-toolbar-title :style="$vuetify.breakpoint.smAndUp ? 'width: 300px; min-width: 250px' : 'min-width: 72px'" class="ml-0 pl-3">
        <v-toolbar-side-icon @click.stop="drawer.show = !drawer.show"></v-toolbar-side-icon>
        <span class="hidden-xs-only">农事服务系统</span>
      </v-toolbar-title>
      <div class="d-flex align-center" style="margin-left: auto">
        <v-btn icon>
          <v-icon>email</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>notifications</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>more_vert</v-icon>
        </v-btn>
        <v-btn icon large>
          <v-avatar tile>
            <v-icon>account_circle</v-icon>
          </v-avatar>
        </v-btn>
      </div>
    </v-toolbar>
    <v-content>
      <v-container fluid fill-height>
        <v-layout>
          <v-flex class="pb-4 mr-3" :class="{ 'ml-3': !drawer.show }">
            <v-breadcrumbs divider="/">
              <v-breadcrumbs-item v-for="item in breadcrumbs" :key="item.text" :disabled="item.disabled" :to="item.to">
                {{ item.text }}
              </v-breadcrumbs-item>
            </v-breadcrumbs>
            <router-view></router-view>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import VcNavigationDrawer from '@vehiclecloud/commons/components/VcNavigationDrawer'
import RouterBreadcrumbs from '@vehiclecloud/commons/mixins/router-breadcrumbs'

export default {
  name: 'app',
  mixins: [RouterBreadcrumbs],
  watch: {
    '$route' () {
      this.genBreadcrumbs()
      this.$store.commit('set', { name: 'search', k: 'keyword', v: null })
    }
  },
  created () {
    this.genBreadcrumbs()
  },
  data () {
    return {
      drawer: {
        show: null,
        items: [
          { title: '概况', icon: 'dashboard', to: { name: 'dashboard' }, exact: true },
          {
            title: '档案管理',
            items: [
              { title: '合作农户档案', icon: 'map', to: { name: 'tenant-farmer' } }
            ]
          }
        ]
      }
    }
  },
  components: {
    VcNavigationDrawer
  }
}
</script>
