import Vue from 'vue'
import Vuex from 'vuex'
import { MongoVuexHelper } from '@vehiclecloud/mongo-rest-client'

Vue.use(Vuex)

const store = new Vuex.Store({
  strict: true,
  state: {
    search: {
      keyword: null
    },
    config: {
      province: {
        _id: { $oid: '530000000000530000000000' },
        level: 2,
        name: '云南',
        code: '530000000000',
        center: { lat: 24.267, lng: 101.7554 }
      }
    },
    'base.area': {
      items: [],
      loaded: {},
      database: 'iss'
    },
  },
  ...MongoVuexHelper
})

export default store
