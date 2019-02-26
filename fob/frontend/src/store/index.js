import Vue from 'vue'
import Vuex from 'vuex'
import {
  MongoVuexHelper
} from '@vehiclecloud/mongo-rest-client'

Vue.use(Vuex)

const store = new Vuex.Store({
  strict: true,
  state: {
    search: {
      keyword: null
    },
    config: {
      issServer: null
    },
    // 缓存的行政区域数据（保存在支撑平台）
    'base.area': {
      items: [],
      loaded: {},
      database: 'iss'
    },
    // 缓存的企业/组织信息（保存在支撑平台）
    'sys.tenant': {
      items: [],
      loaded: false,
      database: 'iss'
    },
    'base.category': {
      items: [],
      loaded: false,
      database: 'iss'
    },
    'base.category_tag': {
      items: [],
      loaded: false,
      database: 'iss'
    },
    // 缓存的用户信息（保存在支撑平台）
    'tenant.staff': {
      items: [],
      loaded: false,
      database: 'iss'
    },
    // 缓存的当前登录用户的权限信息（保存在业务系统） { items: [所有权限], map: { method: { uri: true } }}
    'sys.authority': {
      items: [],
      current: null,
      loaded: false
    },
    // 缓存的角色信息（保存在业务系统）
    'tenant.role': {
      items: [],
      loaded: false
    },
    // 缓存的用户角色信息（保存在业务系统）
    'tenant.staff_roles': {
      items: [],
      loaded: false
    },
    'base.expert_tag': {
      items: [],
      loaded: false
    },
    'template.common_diagnose': {
      items: [],
      loaded: false
    }
  },
  ...MongoVuexHelper
})

export default store
