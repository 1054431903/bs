import Vue from 'vue'
import Vuex from 'vuex'

import { MongoVuexHelper } from '@vehiclecloud/mongo-rest-client'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    app: {
      mode: 0,
      lon: null,
      lat: null,
      landid: null,
      title: null,
      btnType: false,
      acts: [] // { icon: String, fn: Function }
    },
    queryParams: {
      mode: 0,
      orgid: null,
      loginorgid: null,
      operateid: null,
      loginerid: null,
      operateredittime: null,
      landid: null,
      parcelid: null
    },
    init: {
      runMode: 0,
      issStaffId: null,
      issTenantId: null,
      issFarmlandId: null,
      fwkUserId: null,
      fwkParcelId: null
    },
    fwkUser: null,
    fwkParcel: null,
    'tenant.farmland': {
      item: null,
      loaded: false,
      database: 'iss'
    },
    'tenant.staff': {
      item: null,
      loaded: false,
      database: 'iss'
    },
    'sys.tenant': {
      item: null,
      loaded: false,
      database: 'iss'
    }
  },
  ...MongoVuexHelper
})
