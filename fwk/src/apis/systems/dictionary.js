import http from '@/http'

// 公共字典 queryCommonDict
// 地块从属：parcel-dependenc
// 用户性别：user-sex
// 农户贫困状态：peasant-poverty
// 文化程度：peasant-culture
// 合作状态：peasant_cooperstate
// 农户协议状态：peasant-protocolstate
// 农事计划提醒方式：plan-warntype
// 农事任务类别：task-type
// 产出量单位：productive-quantityunit
// 组织性质：org-nature
// 组织规模：org-scale

// 系统字典 querySystemDict
// 农资类别：agriculturaltype
// 农资规格单位：standunit
// 农资数量单位：numberunit

export function queryCommonDict (dicttype) {
  const requestParams = {
    dicttype: dicttype,
    delstate: 1
  }
  return http({
    url: '/fwk-service-archives/common/dictservice/querydictlist',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 系统字典类型管理
export function querySystemDictType () {
  return http({
    url: '/fwk-service-system/tsdictType/queryTSdictType',
    method: 'get',
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

/**
 * 获取全国行政区划
 * @param parentcode 父级id
 */
export function queryPositionByParentCode (parentcode) {
  const getParams = new URLSearchParams()
  getParams.append('parentCode', parentcode)
  return http({
    url: '/fwk-service-system/position/queryPositionByParentCode',
    method: 'get',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

/**
 * 逆向查询行政区划
 * @param code
 */
export function reversalQueryPosition (code) {
  const getParams = new URLSearchParams()
  getParams.append('code', code)
  return http({
    url: '/fwk-service-archives/common/querypositionlistasdictionary',
    method: 'get',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

// 系统字典，类型详情
export function querySystemDictTypeDetails (parentcode, currentPage, parentid) {
  const requestParams = {
    parentcode: parentcode,
    currentPage: currentPage,
    parentid: parentid
  }
  return http({
    url: '/fwk-service-system/dict/queryTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }

  })
}

// 系统字典类型管理-----类别 ----添加
export function querySystemDictTypeDetailsAdd (name, descript, parentcode, parentid, userid) {
  const requestParams = {
    name: name,
    descript: descript,
    parentcode: parentcode,
    parentid: parentid,
    userid: userid
  }
  return http({
    url: '/fwk-service-system/dict/saveTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 系统字典类型管理-----类别 ----编辑（修改）
export function querySystemDictTypeDetailsUpdate (name, id, descript) {
  const requestParams = {
    name: name,
    id: id,
    descript: descript
  }
  return http({
    url: '/fwk-service-system/dict/updateTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 系统字典，类别管理，编辑
export function querySystemDictTypeEdit (id, name) {
  const requestParams = {
    id: id,
    name: name
  }
  return http({
    url: '/fwk-service-system/dict/queryTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }

  })
}

// 系统字典类型管理-----类别 ----删除
export function querySystemDictTypeDetailsDelete (id) {
  const requestParams = {
    id: id
  }
  return http({
    url: '/fwk-service-system/dict/deleteTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function querySystemDict (parentcode) {
  const requestParams = {
    parentcode: parentcode
  }
  return http({
    url: '/fwk-service-system/dict/queryTSdict',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
