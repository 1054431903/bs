import http from '@/http'
// 组织管理用户信息
export function organizationInfo (loginorgid) {
  const getParams = new URLSearchParams()
  getParams.append('loginorgid', loginorgid)
  return http({
    url: '/fwk-service-user/org/queryOrgByOperateid',
    method: 'get',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

// 编辑组织管理用户信息
export function organizationEditInfo (orgInformation) {
  return http({
    url: '/fwk-service-user/org/saveOrg',
    method: 'post',
    data: JSON.stringify(orgInformation),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 编辑组织管理用户信息----查询组织性质和组织人员规模
export function organizationNature (dicttype, dictvalue) {
  const requestParams = {
    dicttype: dicttype,
    dictvalue: dictvalue
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

// 图片上传接口
export function croppaImageUpload (requestParams) {
  return http({
    url: '/fwk-service-archives/common/fileservice/base64/upload',
    method: 'post',
    data: requestParams
  })
}

// 编辑组织管理用户信息----查询地址行政区划(省市县的下拉选项值)
export function orgAddressDropdown (parentCode) {
  const getParams = new URLSearchParams()
  getParams.append('parentCode', parentCode)
  return http({
    url: '/fwk-service-system/position/queryPositionByParentCode',
    method: 'get',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

// 编辑组织管理用户信息----查询地址(组织注册地址)
export function organizationAddress (code) {
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

// 解散组织
export function organizationDissolve (loginorgid) {
  const getParams = new URLSearchParams()
  getParams.append('id', loginorgid)
  return http({
    url: '/fwk-service-user/org/deleteOrg',
    method: 'get',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

// 移交组织---获取登录用户的组织下的所有用户
export function orgAllUsers (loginorgid) {
  const requestParams = {
    loginorgid: loginorgid
  }
  return http({
    url: '/fwk-service-user/user/queryUserByLoginUserOrg',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 角色管理--获取权限项
export function queryPermission () {
  return http({
    url: 'http://10.88.20.66:5766/fwk-service-user/role/queryPermissionTree',
    method: 'post',
    data: JSON.stringify(),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
