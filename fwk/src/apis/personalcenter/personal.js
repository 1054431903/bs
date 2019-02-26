/**
 * 个人中心相关接口
 */
import http from '@/http'

// 组织管理用户信息

// 退出组织
export function exitOrg (orgid, userid) {
  const getParams = new URLSearchParams()
  getParams.append('orgid', orgid)
  getParams.append('userid', userid)
  return http({
    url: '/fwk-service-user/user/removeUserFromOrg',
    method: 'post',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
// 查询用户当前登陆组织的接口 --
export function organizationInfo (loginorgid) {
  const getParams = new URLSearchParams()
  getParams.append('loginorgid', loginorgid)
  return http({
    url: '/fwk-service-user/org/queryOrgByOperateid',
    method: 'post',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
// 查询用户个人信息 --
export function personalInfo (userid, orgid) {
  const getParams = new URLSearchParams()
  getParams.append('userid', userid)
  getParams.append('loginorgid', orgid)
  return http({
    url: '/fwk-service-user/user/queryUserInfoById',
    method: 'post',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
// 编辑个人信息
export function personalDataEdit (requestParams) {
  console.log(requestParams)
  return http({
    url: '/fwk-service-user/user/saveUser',
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

// 获取当前登录人的组织下的所有部门（树形结构）
export function queryDepartmentByLoginOrgId (loginorgid) {
  const requestParams = {
    loginorgid: loginorgid
  }
  return http({
    url: '/fwk-service-user/depart/queryDepartmentByLoginOrgId',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 根据行政区划代码回去行政区划信息 --
export function querypositionbycode (code) {
  const getParams = new URLSearchParams()
  getParams.append('code', code)
  return http({
    url: '/fwk-service-user/common/querypositionbycode',
    method: 'post',
    params: getParams,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
// 修改密码
export function updatePassword (requestParams) {
  const requestParamsV = {
    userid: requestParams.userid,
    oldpassword: requestParams.oldpassword,
    newpassword: requestParams.newpassword
  }
  console.log(requestParamsV)
  return http({
    url: '/fwk-service-user/user/updatePassword',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// 获取验证码
export function getIdentifycode (requestParams) {
  const requestParamsV = {
    // userid: requestParams.userid,
    // oldphone: requestParams.oldphone,
    phone: requestParams.newphone
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

// 验证验证码
export function checkVerificode (requestParams) {
  const requestParamsV = {
    phone: requestParams.newphone,
    verificode: requestParams.identifycode
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

// 修改手机号
export function updatePhone (requestParams) {
  const requestParamsV = {
    userid: requestParams.userid,
    // oldphone: requestParams.oldphone,
    phone: requestParams.newphone
  }
  console.log(requestParamsV)
  return http({
    url: '/fwk-service-user/user/updatePhoneById',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 校验手机号是否存在
export function checkUserIsOwen (phone) {
  const requestParamsV = {
    phone: phone
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
