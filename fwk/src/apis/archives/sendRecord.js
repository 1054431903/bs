/**
 * 农资发放相关接口
 */
import http from '@/http'

/**
 * 新增农资发放记录
 * @param nameid 农资名称编号
 * @param type 农资类别
 * @param specific 规格
 * @param specificunit 规格单位
 * @param num 数量
 * @param numunit 数量单位
 * @param drowuserid 领取人
 * @param drowtime 领取时间
 * @param drowaddresscode 领取地点行政区划编号
 * @param drowaddressdetail 领取地详细地址
 * @param explain 其他说明(选填)
 * @return 包含请求服务的函数
 * @ahtuor huchuan
 */
export function addRecord (nameid, type, specific, specificunit, num, numunit, drowuserid, drowtime, drowaddresscode, drowaddressdetail, explain) {
  const param = {
    nameid: nameid,
    type: type,
    specific: specific,
    specificunit: specificunit,
    num: num,
    drowuserid: drowuserid,
    drowtime: drowtime,
    drowaddresscode: drowaddresscode,
    drowaddressdetail: drowaddressdetail
  }
  if (explain) param.explain = explain
  return http({
    url: '/fwk-service-archives/medicinesend/add',
    method: 'post',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 档案管理模块 相关接口
 */

/**
 * 检索(查询)农资名称
 * @param delstate 记录删除状态(选填 1: 未删除; 0: 已删除)
 * @param name 农资名称(选填)
 */
export function queryAgricname (delstate, name) {
  const param = {}
  if (delstate) param.delstate = delstate
  if (name) param.name = name
  return http({
    url: '/fwk-service-archives/agricname/querylist',
    method: 'get',
    headers: {'Content-Type': 'application/json'},
    data: param
  })
}

/**
 * 获取领取人列表
 * @param operateid
 */
export function queryUserByLoginUserOrg (operateid) {
  const param = { operateid: operateid }
  return http({
    url: '/fwk-service-user/user/queryUserByLoginUserOrg',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: param
  })
}

