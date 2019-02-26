import axios from 'axios'
import Conf from '@/conf'

// enable mock
// import mock from './mock'
// if (!config.debug.mock) {
//   mock.restore()
// }
const http = axios.create({
  baseURL: Conf['FWK']['fwkServer'],
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
  // const request = response.config
  // console.log(
  //   '>>>', request.method.toUpperCase(), request.url, request.params,
  //   '\n   ', response.status, response.data
  // )
  return response
}, function (error) {
  let { response, config: request } = error
  if (request) {
    try {
      console.log(
        '>>>', request.method.toUpperCase(), request.url, request.params,
        '\n   ', response.status || '', response.data || ''
      )
    } catch (e) {

    }
  }
  // Do something with response error
  return Promise.reject(error)
})
export default http
