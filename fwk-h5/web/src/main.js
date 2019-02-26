import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

import locale from 'vee-validate/dist/locale/zh_CN'
import VeeValidate, { Validator } from 'vee-validate'

import Vuetify from 'vuetify'
import 'material-design-icons/iconfont/material-icons.css'
import 'vuetify/dist/vuetify.min.css'

import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'
import '@vehiclecloud/json-schema-vuetify/dist/json-schema-vuetify.min.css'
import '@vehiclecloud/commons/css/main.scss'

import { config as MongoConfig } from '@vehiclecloud/mongo-rest-client'

import CommonSchema from '@/schema/common'

Vue.config.productionTip = false

Validator.localize(locale.name, locale)

Vue.use(VeeValidate, {
  inject: false,
  locale: locale.name,
  strict: false
})

Vue.use(Vuetify)
Vue.use(JsonSchemaVuetify, { commonSchemas: [CommonSchema], Validator })

MongoConfig({
  iss: {
    database: 'iov',
    base: 'http://localhost:9000'
  },
  default: {
    database: 'fwk',
    base: 'http://localhost:9000'
  }
})

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
