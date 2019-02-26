import http from '@/http'

export function queryDeptData (requestParams) {
  return http({
    url: 'http://10.88.20.55:5766/fwk-service-user/depart/queryDepartmentByLoginOrgId',
    method: 'post',
    params: {loginorgid: requestParams},
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function saveDept (dept) {
  return http({
    url: '',
    method: 'post',
    data: JSON.stringify(dept),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function delDept (dept) {
  return http({
    url: '/',
    method: 'post',
    data: JSON.stringify(dept),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function queryUserData (requestParams) {
  return http({
    url: '/',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function queryRoleData (requestParams) {
  return http({
    url: '',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function queryChargeData (requestParams) {
  return http({
    url: '',
    method: 'post',
    data: JSON.stringify(requestParams),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
