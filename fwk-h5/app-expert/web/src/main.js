import '@babel/polyfill'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import {
  Vuetify,
  VApp,
  VBtn,
  VTabs,
  VMenu,
  VIcon,
  VSubheader,
  VCard,
  VList,
  VGrid,
  VDialog,
  VToolbar,
  VTextField,
  VDivider,
  Transitions,
  VBadge
} from 'vuetify/es5/components'
import 'vuetify/src/stylus/app.styl'
import 'material-design-icons/iconfont/material-icons.css'
import '@/styles/main.scss'
import VCharts from 'v-charts'
import VueScroller from 'vue-scroller'
import { config as MongoConfig } from '@vehiclecloud/mongo-rest-client'
import conf from './conf'
import AwesomePicker from 'vue-awesome-picker'
import Toasted from 'vue-toasted'
import VuePicturePreview from '@/components/picture_preview'

Vue.config.productionTip = false

Vue.use(Vuetify, {
  components: {
    VApp,
    VBtn,
    VTabs,
    VMenu,
    VIcon,
    VSubheader,
    VCard,
    VList,
    VGrid,
    VToolbar,
    VDialog,
    VTextField,
    VDivider,
    Transitions,
    VBadge
  }
})

Vue.use(VueScroller)
Vue.use(VuePicturePreview)

// MongoRest组件的参数配置
MongoConfig(conf.Mongo)

Vue.use(VCharts)
Vue.use(AwesomePicker)
Vue.use(Toasted)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
