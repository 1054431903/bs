import http from '@/http'

// ***************登陆***************
export function login (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/user/login',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// ***************获取验证码***************
export function getIdentifycode (requestParams) {
  const requestParamsV = {
    phone: requestParams.phone,
    islogin: 0
  }
  console.log(requestParamsV)
  return http({
    url: '/fwk-service-user/verificode/sendSMS',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// ***************验证验证码***************
export function checkVerificode (requestParams) {
  const requestParamsV = {
    phone: requestParams.phone,
    verificode: requestParams.identifycode,
    islogin: 0
  }
  console.log(requestParamsV)
  return http({
    url: '/fwk-service-user/verificode/checkVerificode',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// ***************注册***************
export function regist (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/user/regist',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// ***************密码重置***************
export function updatePasswordByPhone (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/user/updatePasswordByPhone',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// 查询组织性质和组织人员规模
export function getDictByTypeAndValue (dicttype, dictvalue) {
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

// ***************查询地址行政区划(省市县的下拉选项值)***************
export function queryPositionByParentCode (parentCode) {
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
// ***************完善组织信息***************
export function improveOrgInfo (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/org/saveOrg',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// 校验手机号是否存在，0 不存在，1，存在
export function checkUserIsOwen (phone) {
  const requestParamsV = {
    phone: phone,
    islogin: 0
  }
  console.log(requestParamsV)
  return http({
    url: '/fwk-service-user/user/checkUserIsOwen',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
