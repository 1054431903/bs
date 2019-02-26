import Vue from 'vue'
import Router from 'vue-router'

import {
  group,
  r
} from '@vehiclecloud/commons/util/router-helpers'

// 系统首页
import Dashboard from '@/pages/Dashboard'
// 通用的用户、角色、权限设置页面
import TenantStaff from '@agrithings/common/pages/tenant/Staff'
import TenantRole from '@agrithings/common/pages/tenant/Role'
import TenantStaffRoles from '@agrithings/common/pages/tenant/StaffRoles'
// 项目功能页面
import ExpertTag from '@/pages/base/ExpertTag'
import Expert from '@/pages/tenant/Expert'
import CommonDiagnose from '@/pages/template/CommonDiagnose'
import PersonalDiagnose from '@/pages/template/PersonalDiagnose'
import AgriPush from '@/pages/message/AgriPush'
Vue.use(Router)

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    // 系统首页（必须存在）
    {
      path: '/',
      name: 'dashboard',
      component: Dashboard,
      meta: {
        title: '概况'
      }
    },
    group(
      null,
      '模板库',
      [
        r(CommonDiagnose, '诊断配方公共模板'),
        r(PersonalDiagnose, '诊断配方个人模板')
      ]
    ),
    group(
      null,
      '专家管理',
      [
        r(Expert, '专家信息')
      ]
    ),
    group(
      null,
      '标签管理',
      [
        r(ExpertTag, '标签信息')
      ]
    ),
    // 通用的用户、角色、权限设置页面
    group(
      null,
      '角色权限',
      [
        r(TenantStaff, '用户管理'),
        r(TenantRole, '角色管理'),
        r(TenantStaffRoles, '权限管理')
      ]
    ),
    group(
      null,
      '消息管理',
      [
        r(AgriPush, '神谷推送')
      ]
    )
  ]
})
