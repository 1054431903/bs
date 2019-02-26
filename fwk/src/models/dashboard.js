export function Dashboard () {
  this.lands = null
}

Dashboard.prototype.setLands = function (lands) {
  this.lands = lands
}

Dashboard.prototype.getLands = function () {
  return this.lands
}

Dashboard.prototype.staticLandsByCode = function (code) {
  let level = codeToLevel(code)
  let statLevel = level + 1
  let statInfo = this.lands.reduce(statCallBack, {level: statLevel})
  let needFilterFlag = level < 3 ? (level * 2) : (level * 2 + (level - 3) * 3)
  if (needFilterFlag) {
    let keys = Object.keys(statInfo)
    let preStr = code.substr(0, needFilterFlag)
    keys.forEach(key => {
      if (!key.startsWith(preStr) && key !== 'level') {
        delete statInfo[key]
      }
    })
    // 当行政区划等级大于4时，查询gis信息
    let gisStatLand = []
    if (statLevel > 4) {
      let gisInfo = []
      let gisLands = this.lands.filter((item) => {
        return item.addresscode.startsWith(preStr)
      })
      gisLands.forEach((gis) => {
        gisInfo.push(Object.assign({}, {lat: gis.latitude, lng: gis.longitude}))
      })
      console.log(gisInfo)
      gisStatLand.push(Object.assign(statInfo, {gis: gisInfo}))
      return gisStatLand
    }
  }
  return statInfo
}

function codeToLevel (code) {
  // 530100
  let c = formatCode(code, 12)
  let level = c.substr(0, 2) === '00' ? 0 : (c.substr(2, 2) === '00' ? 1 : (c.substr(4, 2) === '00' ? 2 : (c.substr(6, 3) === '000' ? 3 : (c.substr(9, 3) === '000') ? 4 : 5)))
  return level
}

function preCode (code, level) {
  var res = '000000'
  switch (level) {
    case 1:
      res = code.substr(0, 2)
      break
    case 2:
      res = code.substr(0, 4)
      break
    case 3:
      res = code.substr(0, 6)
      break
    case 4:
      res = code.substr(0, 9)
      break
    case 5:
      res = code.substr(0, 12)
      break
    default:
  }
  return res
}

function formatCode (code, length) {
  while (code.length < length) {
    code += '0'
  }
  return code.length >= length ? code.substr(0, length) : code
}

function statCallBack (m, item) {
  var no = item.addresscode
  var preno = ''
  if (m['level'] <= 3) {
    preno = formatCode(preCode(no, m['level']), 6)
  } else if (m['level'] <= 9) {
    preno = formatCode(preCode(no, m['level']), 9)
  } else {
    preno = no
  }
  m[preno] = (item.area || 0) + (m[preno] || 0)
  return m
}
