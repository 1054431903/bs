import axios from 'axios'
import config from './config'
import { MessageBox } from 'element-ui'

import store from './store/'
import router from './router'

// enable mock
// import mock from './mock'
// if (!config.debug.mock) {
//   mock.restore()
// }
let flag = 0

const http = axios.create({
  baseURL: config.api,
  timeout: 30000
  // headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
})
http.interceptors.request.use(function (request) {
  return request
}, function (error) {
  // Do something with request error
  return Promise.reject(error)
})
http.interceptors.response.use(function (response) {
  const request = response.config
  if (response.data.flag === 5 && flag === 0) {
    flag = 1
    MessageBox.alert('用户信息已变更,请重新登录！', '提示', {
      confirmButtonText: '确定',
      callback: action => {
        let user = null
        let bread = []
        store.commit('setUser', user)
        store.commit('setBreadList', bread)
        router.push({name: 'login'})
        flag = 0
      }
    })
  }
  if (config.debug.http) {
    console.log(
      '>>>', request.method.toUpperCase(), request.url, request.params,
      '\n   ', response.status, response.data
    )
  }
  return response
}, function (error) {
  if (config.debug.http) {
    let { response, config: request } = error
    if (request) {
      console.log(
        '>>>', request.method.toUpperCase(), request.url, request.params,
        '\n   ', response.status, response.data
      )
    }
  }
  // Do something with response error
  return Promise.reject(error)
})
export default http
