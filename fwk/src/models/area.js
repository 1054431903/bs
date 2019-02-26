import { excuteApis } from '@/apis'
export function Area (code) {
  this.country = '100000000000100000000000'
  this.province = ''
  this.provinces = []
  this.city = ''
  this.cities = []
  this.county = ''
  this.counties = []
  this.town = ''
  this.towns = []
  this.village = ''
  this.villages = []
  this.setPositon = setPositon
  this.clearPosition = clearPosition
}

Area.prototype.getProvinces = function () {
  return this.provinces
}

Area.prototype.setProvinces = function (provinces) {
  this.provinces = provinces
}

Area.prototype.getProvince = function () {
  return this.province
}

Area.prototype.setProvince = function (province) {
  this.province = province
}

Area.prototype.getCities = function () {
  return this.cities
}

Area.prototype.setCities = function (cities) {
  this.cities = cities
}

Area.prototype.getCity = function () {
  return this.city
}

Area.prototype.setCity = function (city) {
  this.city = city
}

Area.prototype.getCounties = function () {
  return this.counties
}

Area.prototype.setCounties = function (counties) {
  this.counties = counties
}

Area.prototype.getCounty = function () {
  return this.county
}

Area.prototype.setCounty = function (county) {
  this.county = county
}

Area.prototype.getTowns = function () {
  return this.towns
}

Area.prototype.setTowns = function (towns) {
  this.towns = towns
}

Area.prototype.getTown = function () {
  return this.town
}

Area.prototype.setTown = function (town) {
  this.town = town
}

Area.prototype.getVillages = function () {
  return this.villages
}

Area.prototype.setVillages = function (villages) {
  this.villages = villages
}

Area.prototype.getVillage = function () {
  return this.village
}

Area.prototype.setVillage = function (village) {
  this.village = village
}

Area.prototype.getChildren = async function (code) {
  let requestParams = {'parentCode': code}
  let resolve = await excuteApis(requestParams, global.constant.archivesApis, 'position', 'query')
  return resolve
}

Area.prototype.getParent = function (code) {
  let requestParams = {'code': code}
  excuteApis(requestParams, global.constant.archivesApis, 'common', 'reversalQueryPosition').then(data => {
    let res = data.data
    if (res.flag === 1) {
      let positions = res.data
      let level = positions.length
      switch (level) {
        case 1: // 省
          this.provinces = positions[0]
          this.setPositon(1, code)
          break
        case 2: // 市
          this.provinces = positions[0]
          this.cities = positions[1]
          this.setPositon(2, code)
          break
        case 3: // 县
          this.provinces = positions[0]
          this.cities = positions[1]
          this.counties = positions[2]
          this.setPositon(3, code)
          break
        case 4: // 镇
          this.provinces = positions[0]
          this.cities = positions[1]
          this.counties = positions[2]
          this.towns = positions[3]
          this.setPositon(4, code)
          break
        case 5: // 村
          this.provinces = positions[0]
          this.cities = positions[1]
          this.counties = positions[2]
          this.towns = positions[3]
          this.villages = positions[4]
          this.setPositon(5, code)
          break
        default:
      }
    }
  })
}

// 设置当前所选择的行政区
function setPositon (level, code) {
  let curPos = null
  switch (level) {
    case 5:
      curPos = this._.find(this.villages, ['code', code])
      this.village = curPos.code
      curPos = this._.find(this.towns, ['code', curPos.parentcode])
      this.town = curPos.code
      curPos = this._.find(this.counties, ['code', curPos.parentcode])
      this.county = curPos.code
      curPos = this._.find(this.cities, ['code', curPos.parentcode])
      this.city = curPos.code
      curPos = this._.find(this.provinces, ['code', curPos.parentcode])
      this.province = curPos.code
      break
    case 4:
      curPos = this._.find(this.towns, ['code', code])
      this.town = curPos.code
      curPos = this._.find(this.counties, ['code', curPos.parentcode])
      this.county = curPos.code
      curPos = this._.find(this.cities, ['code', curPos.parentcode])
      this.city = curPos.code
      curPos = this._.find(this.provinces, ['code', curPos.parentcode])
      this.province = curPos.code
      // get villages
      this.getChildren(this.towns).then(res => {
        let data = res.data
        if (data && data.length > 0) {
          this.setVillages(data)
        }
      })
      break
    case 3:
      curPos = this._.find(this.counties, ['code', code])
      this.county = curPos.code
      curPos = this._.find(this.cities, ['code', curPos.parentcode])
      this.city = curPos.code
      curPos = this._.find(this.provinces, ['code', curPos.parentcode])
      this.province = curPos.code
      // get towns
      this.getChildren(this.county).then(res => {
        let data = res.data
        if (data && data.length > 0) {
          this.setTowns(data)
        }
      })
      break
    case 2:
      curPos = this._.find(this.cities, ['code', code])
      this.city = curPos.code
      curPos = this._.find(this.provinces, ['code', curPos.parentcode])
      this.province = curPos.code
      // get counties
      this.getChildren(this.city).then(res => {
        let data = res.data
        if (data && data.length > 0) {
          this.setCounties(data)
        }
      })
      break
    case 1:
      curPos = this._.find(this.provinces, ['code', code])
      this.province = curPos.code
      // get citys
      this.getChildren(this.province).then(res => {
        let data = res.data
        if (data && data.length > 0) {
          this.setCities(data)
        }
      })
      break
    default:
  }
}

 // 清空下级行政区
function clearPosition (level) {
  switch (level) {
    case 2:
      this.province = ''
      break
    case 3:
      this.cities = []
      this.city = ''
      this.counties = []
      this.county = ''
      this.towns = []
      this.town = ''
      this.villages = []
      this.village = ''
      break
    case 4:
      this.counties = []
      this.county = ''
      this.towns = []
      this.town = ''
      this.villages = []
      this.village = ''
      break
    case 5:
      this.towns = []
      this.town = ''
      this.villages = []
      this.village = ''
      break
    case 6:
      this.villages = []
      this.village = ''
      break
    default:
  }
}
