import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

import locale from 'vee-validate/dist/locale/zh_CN'
import VeeValidate, {
  Validator
} from 'vee-validate'

import Vuetify from 'vuetify'
import 'material-design-icons/iconfont/material-icons.css'
import 'vuetify/dist/vuetify.min.css'

import 'iview/dist/styles/iview.css'
import {
  Upload
} from 'iview'
import Toasted from 'vue-toasted'
import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'
import '@vehiclecloud/json-schema-vuetify/dist/json-schema-vuetify.min.css'
import '@vehiclecloud/commons/css/main.scss'

import mongo, {
  config as MongoConfig
} from '@vehiclecloud/mongo-rest-client'
import {
  config as RedisConfig
} from '@vehiclecloud/redis-rest-client'

import OAuth2 from '@vehiclecloud/oauth2-client'
import Oauth2Helper from '@agrithings/common/helper/oauth2'

import CommonSchema from '@agrithings/common/schema/common'

import conf from './conf'

import vueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
Vue.use(Toasted)
Vue.toasted.register('error', '文件大小不能超过5M', {
  type: 'error',
  icon: 'error_outline',
  position: 'top-center',
  duration: 2000
})
Vue.component('Upload', Upload)
Vue.use(vueQuillEditor)

Vue.config.productionTip = false

Validator.localize(locale.name, locale)

Vue.use(VeeValidate, {
  inject: false,
  locale: locale.name,
  strict: false
})

Vue.use(Vuetify)
Vue.use(JsonSchemaVuetify, {
  commonSchemas: [CommonSchema],
  Validator
})

// OAuth2组件的参数配置
Vue.use(OAuth2, {
  router,
  conf: {
    cookieName: conf.OAuth2.cookieName,
    server: conf.OAuth2.server,
    clientId: conf.OAuth2.clientId,
    init: Oauth2Helper.initFnOf(store, mongo, 'iss'),
    authed: Oauth2Helper.authedFnOf(store)
  }
})

// MongoRest组件的参数配置
MongoConfig(conf.Mongo)

// RedisRest组件的参数配置
RedisConfig(conf.Redis)

// FileServer组件的参数配置
JsonSchemaVuetify.config.fs(conf.FileServer.server)

// 支撑平台代理服务的参数配置
store.commit('set', {
  name: 'config',
  k: 'issServer',
  v: conf.IssServer.server
})
// 有赞商城代理服务的参数配置
store.commit('set', {
  name: 'config',
  k: 'yzServer',
  v: conf.YzServer.server
})
// 消息推送代理服务的参数配置
store.commit('set', {
  name: 'config',
  k: 'knServer',
  v: conf.KnServer.server
})

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
