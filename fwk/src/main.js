import globalvar from './constant'
global.constant = globalvar

import Vue from 'vue'
import helper from './helper'
global.helper = helper
import config from './config'
import store from './store/'
global.store = store

import router from './router'
import i18n from './i18n/'
import Vuetify from 'vuetify'
// import 'material-design-icons/iconfont/material-icons.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(Vuetify, {
  theme: {
    primary: '#32c787',
    secondary: '#b0bec5',
    accent: '#8c9eff',
    error: '#b71c1c'
  }
})
// import './http'
import 'vuetify/src/stylus/main.styl'
import './styles/main.scss'
import 'babel-polyfill'

import App from './App.vue'
import VueTimeago from 'vue-timeago'
import {
  Tree,
  DatePicker
} from 'element-ui'
Vue.use(Tree)
Vue.use(DatePicker)

Vue.use(VueTimeago, {
  name: 'timeago', // component name, `timeago` by default
  locale: config.locale,
  locales: {
    en: require('vue-timeago/locales/en-US.json'),
    [config.locale]: require(`vue-timeago/locales/${config.locale}.json`)
  }
})

import Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.css'
Vue.component('dropzone', Dropzone)

import VueQuillEditor, {
  Quill
} from 'vue-quill-editor'
Vue.use(VueQuillEditor)
import ImageResize from 'quill-image-resize-module'
Quill.register('modules/imageResize', ImageResize)

// 图片上传缩放及裁剪
import 'vue-croppa/dist/vue-croppa.css'
import Croppa from 'vue-croppa'
Vue.use(Croppa)

import validator from 'Validator'
global.validator = validator

import vuelidate from 'vuelidate'
Vue.use(vuelidate)

import VeeValidate, {
  Validator
} from 'vee-validate'
import messagesCN from 'vee-validate/dist/locale/zh_CN'

Validator.localize('zh_CN', messagesCN)

Vue.use(VeeValidate)

import lodash from 'lodash'
import VueLodash from 'vue-lodash'
Vue.use(VueLodash, lodash)

import 'vue-tree-halower/dist/halower-tree.min.css'
import VTree from 'vue-tree-halower'
Vue.use(VTree)

// 引入v-chart组件
// import VeBar from 'v-charts/lib/bar'
import VCharts from 'v-charts'
Vue.use(VCharts)
// import veLine from 'v-charts/lib/line'
// Vue.component('ve-bar', VeBar)
// Vue.component('ve-line', veLine)

// 引入图像裁剪及上传组件
import VueCoreImageUpload from 'vue-core-image-upload'
Vue.component('vue-core-image-upload', VueCoreImageUpload)
import vueCropper from 'vue-cropper'

import VForm from './components/VForm'
import VGrid from './components/Grid.vue'
import VBread from './components/Breadcrumb.vue'
import V24hforecast from './components/24hforecast.vue'
import B24hforecast from './components/Bk24hforecast.vue'
import V4dforecast from './components/4dforecast.vue'
import Bk4dforecast from './components/Bk4dforecast.vue'
import VLandProfile from './components/LandProfile.vue'
import VAreaSelect from './components/AreaSelect.vue'
import VModalDialog from './components/ModalDialog.vue'
import VCustomDatePicker from './components/CustomDatePicker.vue'
import VImageCroppedUpload from './components/ImageCroppedUpload.vue'
import VElementTree from './components/ElementTree.vue'
import VCustomPagination from './components/CustomPagination.vue'
import VStaffSelected from './components/StaffSelected.vue'
// 全局引入Toast
import './components/toast/toast.css'
import Toast from './components/toast/index'
Vue.use(Toast)

import OAuth2 from '@vehiclecloud/oauth2-client'
import Oauth2Helper from '@agrithings/common/helper/oauth2'
import GrpcApi from '@vehiclecloud/grpc-websocket-client'

/**
 * tree
 */
import VJstree from 'vue-jstree'
Vue.use(VJstree)

/**
 * 全局 页面头部返回组件
 * 属性: backName: 按钮右侧文字
 */
import GoBack from '@/components/GoBack'

// Vue.component('ve-line', VeBar)
Vue.component('v-form', VForm)
Vue.component('v-grid', VGrid)
Vue.component('v-bread', VBread)
Vue.component('v-24hforecast', V24hforecast)
Vue.component('v-Bk24hforecast', B24hforecast)
Vue.component('v-4dforecast', V4dforecast)
Vue.component('v-bk4dforecast', Bk4dforecast)
Vue.component('v-landprofile', VLandProfile)
Vue.component('v-areaselect', VAreaSelect)
Vue.component('go-back', GoBack)
Vue.component('v-modaldialog', VModalDialog)
Vue.component('v-customdatepicker', VCustomDatePicker)
Vue.component('v-imagecroppedupload', VImageCroppedUpload)
Vue.component('v-imagecropper', vueCropper)
Vue.component('v-elementtree', VElementTree)
Vue.component('v-custompagination', VCustomPagination)
Vue.component('v-selectedstaff', VStaffSelected)

import conf from './conf'
import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@vehiclecloud/json-schema-vuetify/dist/json-schema-vuetify.min.css'
import '@vehiclecloud/commons/css/main.scss'
import mongo, {
  config as MongoConfig
} from '@vehiclecloud/mongo-rest-client'
import CommonSchema from '@agrithings/common/schema/common'

Vue.use(JsonSchemaVuetify, {
  commonSchemas: [CommonSchema],
  Validator
})

MongoConfig({
  iss: {
    database: conf.Mongo.iss.database,
    base: conf.Mongo.iss.base
  },
  iot: {
    database: conf.Mongo.iot.database,
    base: conf.Mongo.iot.base
  },
  default: {
    database: conf.Mongo.fwk.database,
    base: conf.Mongo.fwk.base
  }
})
let tk = localStorage.getItem('atk') ? JSON.parse(localStorage.getItem('atk')) : null
Vue.use(OAuth2, {
  conf: {
    cookieName: 'fwk-token',
    server: conf.OAuth2.server,
    clientId: conf.OAuth2.clientId,
    init: Oauth2Helper.initFnOf(store, mongo, 'iss'),
    authed: Oauth2Helper.authedFnOf(store),
    login (conf, initToken, to, from, next) {
      if (to.path === '/login') {
        return next()
      }
      next('/login')
    }
  }
})
if (tk) {
  OAuth2.initToken(tk)
  let pointArr = []
  let noPointArr = []
  let menuArr = []
  let roleArr = []
  let payload = atob(tk['access_token'].split('.')[1])
  let jwt = JSON.parse(payload)
  let tenant = ''
  if (global.helper.ls.get('user') && global.helper.ls.get('user').loginorgid) {
    tenant = global.helper.ls.get('user').loginorgid
  } else {
    let rolesKeys = jwt.roles && Object.keys(jwt.roles)
    tenant = rolesKeys && rolesKeys[0]
  }
  mongo
    .db()
    .collection('tenant.role')
    .find({
      filter: {
        status: 1,
        _id: {
          $in: jwt.roles[tenant].map(id => ({
            $oid: id
          }))
        },
        _removed: {
          $exists: false
        }
      }
    })
    .then(d => {
      if (d.items && d.items.length > 0 && d.items[0].authorities) {
        roleArr = d.items[0].authorities
        store.dispatch('init', {
          collection: 'sys.authority'
        })
        JSON.parse(localStorage.getItem('sys-auth')).forEach(value => {
          roleArr.forEach(e => {
            if (value._id.$oid === e.$oid && value.level !== 3) {
              if (value.level === 1) {
                noPointArr.push({
                  title: value.title,
                  items: [],
                  idx: value.idx,
                  id: e.$oid
                })
              } else {
                pointArr.push({
                  title: value.title,
                  icon: value.icon,
                  to: {
                    name: value.uri
                  },
                  idx: value.idx,
                  pid: !value.parent ? null : value.parent.$oid
                })
              }
            }
          })
        })
        if (pointArr.length > 0 || noPointArr.length > 0) {
          pointArr.forEach(v => {
            if (!v.pid) {
              menuArr.push(v)
            } else {
              if (noPointArr.length > 0) {
                for (let i = 0; i < noPointArr.length; i++) {
                  v.pid === noPointArr[i].id ? noPointArr[i].items.push(v) : ''
                }
              }
            }
          })
        }
        let tArr = [...menuArr, ...noPointArr]
        tArr.sort((a, b) => a.idx - b.idx)
        for (let j = 0; j < tArr.length; j++) {
          tArr[j].items && tArr[j].items.length > 0 ? tArr[j].items.sort((a, b) => a.idx - b.idx) : ''
        }
        store.commit('setMenu', tArr)
      }
    })
} else {
  router.push({
    name: 'login'
  })
}
GrpcApi.config(conf.Grpc.ws)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  store,
  router,
  render: h => h(App)
})
