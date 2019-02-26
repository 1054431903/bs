import { excuteApis } from '@/apis'

export function CSoil (soilInfo) {
  this.id = soilInfo && soilInfo.hasOwnProperty('id') ? soilInfo.id : null
  this.parcelid = soilInfo && soilInfo.hasOwnProperty('parcelid') ? soilInfo.parcelid : null
  this.samplnum = soilInfo && soilInfo.hasOwnProperty('samplnum') ? soilInfo.samplnum : null
  this.meastime = soilInfo && soilInfo.hasOwnProperty('meastime') ? soilInfo.meastime : null
  this.orgname = soilInfo && soilInfo.hasOwnProperty('orgname') ? soilInfo.orgname : null
  this.soilgenre = soilInfo && soilInfo.hasOwnProperty('soilgenre') ? soilInfo.soilgenre : null
  this.soilgenrename = soilInfo && soilInfo.hasOwnProperty('soilgenrename') ? soilInfo.soilgenrename : null
  this.nitrogen = soilInfo && soilInfo.hasOwnProperty('nitrogen') ? soilInfo.nitrogen : null
  this.phosphorus = soilInfo && soilInfo.hasOwnProperty('phosphorus') ? soilInfo.phosphorus : null
  this.potassium = soilInfo && soilInfo.hasOwnProperty('potassium') ? soilInfo.potassium : null
  this.phvalue = soilInfo && soilInfo.hasOwnProperty('phvalue') ? soilInfo.phvalue : null
  this.matter = soilInfo && soilInfo.hasOwnProperty('matter') ? soilInfo.matter : null
  this.validzn = soilInfo && soilInfo.hasOwnProperty('validzn') ? soilInfo.validzn : null
  this.validcu = soilInfo && soilInfo.hasOwnProperty('validcu') ? soilInfo.validcu : null
  this.validfe = soilInfo && soilInfo.hasOwnProperty('validfe') ? soilInfo.validfe : null
  this.validmn = soilInfo && soilInfo.hasOwnProperty('validmn') ? soilInfo.validmn : null
  this.validmo = soilInfo && soilInfo.hasOwnProperty('validmo') ? soilInfo.validmo : null
  this.valids = soilInfo && soilInfo.hasOwnProperty('valids') ? soilInfo.valids : null
  this.solubilityca = soilInfo && soilInfo.hasOwnProperty('solubilityca') ? soilInfo.solubilityca : null
  this.solubilitymg = soilInfo && soilInfo.hasOwnProperty('solubilitymg') ? soilInfo.solubilitymg : null
  this.salt = soilInfo && soilInfo.hasOwnProperty('salt') ? soilInfo.salt : null
  this.soiltexture = soilInfo && soilInfo.hasOwnProperty('soiltexture') ? soilInfo.soiltexture : null
  this.soiltexturename = soilInfo && soilInfo.hasOwnProperty('oiltexturename') ? soilInfo.soiltexturename : null
  this.attachments = soilInfo && soilInfo.hasOwnProperty('attachments') ? soilInfo.attachments : null
}

CSoil.prototype.getId = function () {
  return this.id
}

CSoil.prototype.setId = function (id) {
  this.id = id
}

CSoil.prototype.getParcelid = function () {
  return this.parcelid
}

CSoil.prototype.setParcelid = function (parcelid) {
  this.parcelid = parcelid
}

CSoil.prototype.getSamplnum = function () {
  return this.samplnum
}

CSoil.prototype.setSamplnum = function (samplnum) {
  this.samplnum = samplnum
}

CSoil.prototype.getMeastime = function () {
  return this.meastime
}

CSoil.prototype.setMeastime = function (meastime) {
  this.meastime = meastime
}

CSoil.prototype.getOrgname = function () {
  return this.orgname
}

CSoil.prototype.setOrgname = function (orgname) {
  this.orgname = orgname
}

CSoil.prototype.getSoilgenre = function () {
  return this.soilgenre
}

CSoil.prototype.setSoilgenre = function (soilgenre) {
  this.soilgenre = soilgenre
}

CSoil.prototype.getSoilgenrename = function () {
  return this.soilgenrename
}

CSoil.prototype.setSoilgenrename = function (soilgenrename) {
  this.soilgenrename = soilgenrename
}

CSoil.prototype.getNitrogen = function () {
  return this.nitrogen
}

CSoil.prototype.setNitrogen = function (nitrogen) {
  this.nitrogen = nitrogen
}

CSoil.prototype.getPhosphorus = function () {
  return this.phosphorus
}

CSoil.prototype.setPhosphorus = function (phosphorus) {
  this.phosphorus = phosphorus
}

CSoil.prototype.getPotassium = function () {
  return this.potassium
}

CSoil.prototype.setPotassium = function (potassium) {
  this.potassium = potassium
}

CSoil.prototype.getPhvalue = function () {
  return this.phvalue
}

CSoil.prototype.setPhvalue = function (phvalue) {
  this.phvalue = phvalue
}

CSoil.prototype.getMatter = function () {
  return this.matter
}

CSoil.prototype.setMatter = function (matter) {
  this.matter = matter
}

CSoil.prototype.getValidzn = function () {
  return this.validzn
}

CSoil.prototype.setValidzn = function (validzn) {
  this.validzn = validzn
}

CSoil.prototype.getValidcu = function () {
  return this.validcu
}

CSoil.prototype.setValidcu = function (validcu) {
  this.validcu = validcu
}

CSoil.prototype.getValidfe = function () {
  return this.validfe
}

CSoil.prototype.setValidfe = function (validfe) {
  this.validfe = validfe
}

CSoil.prototype.getValidmn = function () {
  return this.validmn
}

CSoil.prototype.setValidmn = function (validmn) {
  this.validmn = validmn
}

CSoil.prototype.getValidmo = function () {
  return this.validmo
}

CSoil.prototype.setValidmo = function (validmo) {
  this.validmo = validmo
}

CSoil.prototype.getValids = function () {
  return this.valids
}

CSoil.prototype.setValids = function (valids) {
  this.valids = valids
}

CSoil.prototype.getSolubilityca = function () {
  return this.solubilityca
}

CSoil.prototype.setSolubilityca = function (solubilityca) {
  this.solubilityca = solubilityca
}

CSoil.prototype.getSolubilitymg = function () {
  return this.solubilitymg
}

CSoil.prototype.setSolubilitymg = function (solubilitymg) {
  this.solubilitymg = solubilitymg
}

CSoil.prototype.getSalt = function () {
  return this.salt
}

CSoil.prototype.setSalt = function (salt) {
  this.salt = salt
}

CSoil.prototype.getSoiltexture = function () {
  return this.soiltexture
}

CSoil.prototype.setSoiltexture = function (soiltexture) {
  this.soiltexture = soiltexture
}

CSoil.prototype.getSoiltexturename = function () {
  return this.soiltexturename
}

CSoil.prototype.setSoiltexturename = function (soiltexturename) {
  this.soiltexturename = soiltexturename
}

CSoil.prototype.getAttachments = function () {
  return this.attachments
}

CSoil.prototype.setAttachments = function (attachments) {
  this.attachments = attachments
}

CSoil.prototype.getSoil = function () {
  return Object.assign({}, {
    id: this.getId(),
    parcelid: this.getParcelid(),
    samplnum: this.getSamplnum(),
    meastime: this.getMeastime(),
    orgname: this.getOrgname(),
    soilgenre: this.getSoilgenre(),
    soilgenrename: this.getSoilgenrename(),
    nitrogen: this.getNitrogen(),
    phosphorus: this.getPhosphorus(),
    potassium: this.getPotassium(),
    phvalue: this.getPhvalue(),
    matter: this.getMatter(),
    validzn: this.getValidzn(),
    validcu: this.getValidcu(),
    validfe: this.getValidfe(),
    validmn: this.getValidmn(),
    validmo: this.getValidmo(),
    valids: this.getValids(),
    solubilityca: this.getSolubilityca(),
    solubilitymg: this.getSolubilitymg(),
    salt: this.getSalt(),
    soiltexture: this.getSoiltexture(),
    soiltexturename: this.getSoiltexturename(),
    attachments: this.getAttachments()
  })
}

CSoil.prototype.querySoilById = async function () {
  // 根据测土信息ID查询测土信息
  let resolve = await excuteApis(Object.assign({}, {id: this.getId()}), global.constant.parcelApis, 'parcelMeasureSoil', 'query')
  return resolve
}

CSoil.prototype.querySoil = async function () {
  // 根据地块ID查询测土信息
  let resolve = await excuteApis(Object.assign({}, {parcelid: this.getParcelid()}), global.constant.parcelApis, 'parcelMeasureSoil', 'query')
  return resolve
}

CSoil.prototype.addSoil = async function () {
  // 增加测土信息
  let resolve = await excuteApis(this.getSoil(), global.constant.parcelApis, 'parcelMeasureSoil', 'add')
  return resolve
}

CSoil.prototype.editSoil = async function () {
  // 编辑测土信息
  let resolve = await excuteApis(this.getSoil(), global.constant.parcelApis, 'parcelMeasureSoil', 'update')
  return resolve
}

CSoil.prototype.deleteSoil = async function () {
  // 删除测土信息
  let resolve = await excuteApis(this.getSoil(), global.constant.parcelApis, 'parcelMeasureSoil', 'delete')
  return resolve
}
