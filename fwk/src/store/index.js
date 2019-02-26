import Vue from 'vue'
import Vuex from 'vuex'
import menu from '../menu'
// import config from '../config'
import { excuteApis } from '@/apis'
import { Dashboard } from '@/models/dashboard'
import mongo, { MongoVuexHelper } from '@vehiclecloud/mongo-rest-client'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    pageTitle: 'Home',
    menu: menu,
    user: {},
    message: {
      type: null,
      body: null
    },
    config: {
      province: {
        _id: {
          $oid: '530000000000530000000000'
        },
        level: 2,
        name: '云南',
        code: '530000000000',
        center: {
          lat: 24.267,
          lng: 101.7554
        }
      }
    },
    search: {
      keyword: null
    },
    breadListState: [],
    // 系统中所有地块，包括已删除的
    allFarmLands: [],
    // 组织下的所有地块
    tenantFarmLands: [],
    // 系统中所有用户，包括已删除的
    allUsers: [],
    // 组织下所有用户
    tenantUsers: [],
    lands: [],
    orgLands: [],
    maincode: '100000',
    staticCode: '100000000000100000000000',
    staticLand: [],
    dictionaryname: {},
    staticLandGis: [],
    staticLevel: 1,
    planYear: null,
    events: [],
    viewLand: {
      currentTab: 'weather'
    },
    listLand: {
      pagination: null,
      curAreaCode: null,
      keywords: null,
      ownership: null
    },
    'base.area': {
      items: [],
      loaded: {},
      database: 'iss'
    },
    'sys.tenant': {
      items: [],
      loaded: false,
      database: 'iss',
      params: {
        sort: {
          name: 1
        }
      }
    },
    'tenant.staff': {
      items: [],
      current: null, // 当前登录用户信息
      token: null, // 当前登录用户oauth token
      jwt: null, // 当前登录录用oauth token jwt
      tenant: null, // 当前登录用户激活的组织
      loaded: false,
      database: 'iss'
    },
    'tenant.farmland': {
      allItems: [],
      items: [],
      loaded: false,
      database: 'iss'
    },
    'base.sensor.type': {
      items: [],
      loaded: false,
      database: 'iot'
    },
    'base.device.type': {
      items: [],
      loaded: false,
      database: 'iot'
    },
    'base.device.param_type': {
      items: [],
      loaded: false,
      database: 'iot'
    },
    'tenant.device': {
      items: [],
      loaded: false,
      database: 'iot'
    },
    'sys.authority': {
      items: [],
      current: {
        items: [],
        map: {}
      }, // 当前登录用户的权限 { items: [所有权限], map: { method: { uri: true } }}
      loaded: false
    },
    'tenant.role': {
      items: [],
      loaded: false
    },
    'tenant.staff_roles': {
      items: [],
      loaded: false
    },
    // 各详情页状态管理
    viewDetail: {
      // 地块详情页面 landDetail
      // 农户档案详情页 peasentDetail
      // 农户合作协议详情页状态管理 agreementDetail
      // 物候期管理规程详情页状态管理 sopDetail
      // 字典详情页状态管理 dictionaryDetail
      pageNames: [
        'landDetail',
        'peasentDetail',
        'agreementDetail',
        'sopDetail',
        'dictionaryDetail',
        'farmworkrecord'
      ],
      currentPageName: '',
      backFlag: 0, // 0 直接进入; 1 点击返回按钮进入
      urlParams: null, // 通过url传参
      psotParams: null, // 通过post传参
      routername: null // 当前路由状态
    }
  },
  mutations: {
    ...MongoVuexHelper.mutations,
    setMenu (state, data) {
      state.menu = data
    },
    setPageTitle (state, data) {
      state.pageTitle = data
    },
    showMessage (state, type, body) {
      state.message = {
        type,
        body
      }
    },
    setUser (state, user) {
      state.user = user
      global.helper.ls.set('user', user)
    },
    setBreadList (state, list) {
      state.breadListState = list
      global.helper.ls.set('breadListState', list)
    },
    setLands (state, data) {
      state.lands = data
    },
    setAllFarmLands (state, data) {
      state.allFarmLands = data
    },
    setTenantFarmLands (state, data) {
      state.tenantFarmLands = data
    },
    setAllUsers (state, data) {
      state.allUsers = data
    },
    setTenantUsers (state, data) {
      state.tenantUsers = data
    },
    setOrgLands (state, data) {
      state.orgLands = data
      global.helper.ls.set('orgLands', data)
    },
    setStaticCode (state, data) {
      state.staticCode = data
    },
    setStaticLand (state, data) {
      state.staticLand = data
    },
    setStaticLevel (state, data) {
      state.staticLevel = data
    },
    setMianCode (state, data) {
      state.maincode = data
    },
    setStaticLandGis (state, data) {
      state.staticLandGis = data
    },
    setDictionaryname (state, data) {
      state.dictionaryname = data
    }
  },
  actions: {
    ...MongoVuexHelper.actions,
    checkAuth ({ commit }) {
      let data = {
        user: global.helper.ls.get('user'),
        token: global.helper.ls.get('token')
      }
      commit('setAuth', data)
    },
    checkPageTitle ({ commit, state }, path) {
      for (let k in state.menu) {
        if (state.menu[k].href === path) {
          commit('setPageTitle', state.menu[k].title)
          break
        }
      }
    },
    cacheUserInfo ({ commit, state }, user) {
      commit('setUser', user)
    },
    breadListMutations ({ commit, state }, list) {
      commit('setBreadList', list)
    },
    dictionarynameMutations ({ commit, state }, list) {
      commit('setDictionaryname', list)
    },
    getLands ({ commit, state }, code) {
      let requestParams = {
        addresscode: code
      }
      excuteApis(
        requestParams,
        global.constant.parcelApis,
        'parcel',
        'query'
      ).then(response => {
        let res = response.data
        if (res.flag === 1) {
          commit('setLands', res.data)
        } else {
          commit('setLands', [])
        }
      })
    },
    // 根据行政区划统计地块信息getStaticLand
    getStaticLand ({ commit, state }, code) {
      let dashboard = new Dashboard()
      let requestParams = Object.assign(
        {},
        {
          addresscode:
            code.length === 6
              ? code === '100000'
                ? '100000000000100000000000'
                : code + '000000' + code + '000000'
              : code.length === 9
              ? code + '000' + code + '000'
              : code.length === 12
              ? code + code
              : code
        }
      )
      excuteApis(
        requestParams,
        global.constant.parcelApis,
        'parcel',
        'queryParcelAreaStatsByAddressCode'
      ).then(response => {
        let res = response.data
        if (res.flag === 1) {
          let lands = res.data
          dashboard.setLands(lands)
        } else {
          dashboard.setLands([])
        }
        commit('setStaticLand', dashboard.getLands())
      })
    },
    // 根据组织底下的地块
    getOrgLands ({ commit, state }) {
      excuteApis({}, global.constant.parcelApis, 'parcel', 'queryParcel').then(
        data => {
          console.log('获取地块成功------')
          if (data.data && data.data.flag === 1 && data.data.data) {
            if (data.data.flag === 1) {
              let lands = data.data.data
              commit('setOrgLands', lands)
            } else {
              commit('setOrgLands', [])
            }
          }
        }
      )
    },
    // 设置当前gis行政区及级别
    setMainCode ({ commit, state }, maincode) {
      console.log('===maincode')
      console.log(maincode)
      console.log('maincode======')
      commit('setMianCode', maincode)
    },

    // 获取所有地块，包括已删除的
    async getAllFarmLands ({ commit }) {
      let filter = {}
      let project = {}
      project.name = 1
      project.tenant = 1
      let data = await mongo
        .db('iss')
        .collection('tenant.farmland')
        .aggregate([{ $match: filter }, { $project: project }])
      commit('setAllFarmLands', data)
      // mongo
      //   .db('iss')
      //   .collection('tenant.farmland')
      //   .aggregate([{ $match: filter }, { $project: project }])
      //   .then(data => {
      //     commit('setAllFarmLands', data)
      //   })
    },
    // 获取组织下的所有地块
    async getTenantFarmLands ({ commit }, tenant) {
      let filter = {}
      filter.tenant = {
        $oid: tenant
      }
      filter._removed = {
        $exists: false
      }
      let project = {}
      project.name = 1
      project.tenant = 1
      let data = await mongo
        .db('iss')
        .collection('tenant.farmland')
        .aggregate([{ $match: filter }, { $project: project }])
      commit('setTenantFarmLands', data)
      // mongo
      //   .db('iss')
      //   .collection('tenant.farmland')
      //   .aggregate([{ $match: filter }, { $project: project }])
      //   .then(data => {
      //     commit('setTenantFarmLands', data)
      //   })
    },
    // 获取所有用户，包括已删除的
    async getAllUsers ({ commit }) {
      let data = await mongo
        .db('iss')
        .collection('tenant.staff')
        .aggregate([{ $match: {} }])
      commit('setAllUsers', data)
      // mongo
      //   .db('iss')
      //   .collection('tenant.staff')
      //   .aggregate([ { $match: {} } ])
      //   .then(data => {
      //     commit('setAllUsers', data)
      //   })
    },
    // 获取组织下所有用户
    async getTenantUsers ({ commit }, tenant) {
      let match = { _id: { $oid: tenant } }
      let graphLookup = {
        from: 'sys.tenant',
        startWith: '$_id',
        connectFromField: '_id',
        connectToField: 'parent',
        as: 'children'
      }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [
        { $match: match },
        { $graphLookup: graphLookup },
        { $unwind: unwind },
        { $project: project }
      ]
      // 查询当前组织下所有部门
      let deps = await mongo
        .db('iss')
        .collection('sys.tenant')
        .aggregate(aggregate)
      let allTenantId = deps.map(dep => {
        return dep._id
      })
      allTenantId.push({ $oid: tenant })
      // 查询当前组织及其下所有部门下的人员
      let filter = {}
      filter.tenants = { $in: allTenantId }
      filter._removed = { $exists: false }
      let users = await mongo
        .db('iss')
        .collection('tenant.staff')
        .aggregate([{ $match: filter }])
      commit('setTenantUsers', users)
      // mongo
      //   .db('iss')
      //   .collection('sys.tenant')
      //   .aggregate(aggregate)
      //   .then(deps => {
      //     let allTenantId = deps.map(dep => {
      //       return dep._id
      //     })
      //     allTenantId.push({ $oid: tenant })
      //     // 查询当前组织及其下所有部门下的人员
      //     let filter = {}
      //     filter.tenants = { $in: allTenantId }
      //     filter._removed = { $exists: false }
      //     mongo
      //       .db('iss')
      //       .collection('tenant.staff')
      //       .aggregate([{ $match: filter }])
      //       .then(users => {
      //         commit('setTenantUsers', users)
      //       })
      //   })
    }
  }
})

export default store
