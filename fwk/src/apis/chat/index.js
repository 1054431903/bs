/**
 * 农事交流相关api
 */

import http from '@/http'

/**
 * 获取地块
 */
export function getLand () {
  return http({
    url: '/fwk-service-farm/communion/queryparcellist',
    method: 'post',
    headers: { 'Content-Type': 'application/json' }
  })
}

/**
 * 添加农事交流
 * @param communioncontent 问题内容
 * @param location 发布人的地理位置
 * @param communionparcellist	地块列表
 * @param communioncategorylist	品类列表—来源于支撑平台
 * @param resourcehistorylist	附件列表
 *
 */
export function add (communioncontent, location, communionparcellist, communioncategorylist, resourcehistorylist) {
  const param = {
    communioncontent: communioncontent,
    location: location,
    communionparcellist: communionparcellist,
    communioncategorylist: communioncategorylist,
    resourcehistorylist: resourcehistorylist
  }
  return http({
    url: '/fwk-service-farm/communion/add',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}
