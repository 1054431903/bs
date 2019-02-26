import http from '@/http'

// 查询角色列表
export function queryRole (orgid) {
  const requestParamsV = {
    loginorgid: orgid
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/role/queryRole',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 保存角色
export function saveRole (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/role/saveRole',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 根据角色获取权限树
export function queryPermissionTree (requestParams) {
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/role/queryPermissionTree',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 根据登陆的组织或者部门查询用户列表
export function queryUserByOrgDepartment (orgid, departmentid, roleid, searchTxt, currentPage, pageSize) {
  const requestParamsV = {
    orgid: orgid,
    departmentid: departmentid,
    roleid: roleid,
    name: searchTxt,
    currentPage: currentPage, // 当前页
    pageSize: pageSize // 页大小

  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/user/queryUserByOrgDepartment',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// 下拉用户
export function queryAllUserByOrgDepartment (orgid, departmentid) {
  const requestParamsV = {
    orgid: orgid,
    departmentid: departmentid
  }
  return http({
    url: '/fwk-service-user/user/queryUserByOrgDepartment',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// =============================用户的编辑、移除组织、转交企业===========================================
// 查询用户个人信息 --
export function querySystemUserInforById (userid, orgid) {
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
  console.log(JSON.stringify(requestParams))
  return http({
    url: '/fwk-service-user/user/saveUser',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// 移交组织
export function turnOrg (loginuserid, userid, password, orgid) {
  const requestParamsV = {
    loginuserid: loginuserid,
    userid: userid,
    password: password,
    orgid: orgid
  }
  return http({
    url: '/fwk-service-user/org/turnOrg',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

// 移除/退出组织
export function removeUserFromOrg (userid, orgid) {
  const requestParamsV = {
    userid: userid,
    orgid: orgid
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/user/removeUserFromOrg',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
// =============================部门的树形结构、添加子部门、编辑子部门、删除子部门===========================================
/* *****************部门的树形结构***********************/
export function orgDepartUserTree (loginorgid, showtype) {
  const requestParamsV = {
    orgid: loginorgid,
    showtype: showtype,
    t: new Date().getTime()
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/pub/OrgDepartUserTree',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
/* *****************添加子部门、编辑子部门***********************/
export function saveDepartment (requestParams) {
  if (requestParams.parentFlag === '1') { // 组织下新增、编辑部门
    requestParams.parentid = ''
  }
  return http({
    url: '/fwk-service-user/depart/saveDepartment',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
/* ***************** 删除子部门***********************/
export function deleteDepartment (id, deptSupportid) {
  const requestParamsV = {
    id: id,
    deptSupportid: deptSupportid
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/depart/deleteDepartment',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
/* *****************查询部门信息***********************/
export function queryDepartmentDetail (departmentid) {
  const requestParamsV = {
    departmentid: departmentid
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/depart/queryDepartmentDetail',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
/* *****************查询角色信息***********************/
export function querySingleRole (orgid, id) {
  const requestParamsV = {
    loginorgid: orgid,
    id: id
  }
  console.log(JSON.stringify(requestParamsV))
  return http({
    url: '/fwk-service-user/role/queryRole',
    method: 'post',
    data: JSON.stringify(requestParamsV),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
