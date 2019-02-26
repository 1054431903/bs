import Vue from 'vue'
import Router from 'vue-router'

import { group, r } from '@vehiclecloud/commons/util/router-helpers'

import Dashboard from '@/pages/Dashboard'

import TenantFarmer from '@/pages/tenant/Farmer'

Vue.use(Router)

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard,
      meta: {
        title: '概况'
      }
    },
    group(
      '/',
      '档案管理',
      [
        r(TenantFarmer, '合作农户档案')
      ]
    )
  ]
})
