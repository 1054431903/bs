import { excuteApis } from '@/apis'
// import { enAreasCode } from '@/utils'
export function CLand (landInfo) {
  this.id = (landInfo && landInfo.id) ? landInfo.id : ''
  this.supportid = (landInfo && landInfo.supportid) ? landInfo.supportid : ''
  this.name = (landInfo && landInfo.name) ? landInfo.name : ''
  this.addresscode = (landInfo && landInfo.addresscode) ? landInfo.addresscode : ''
  this.addressdetail = (landInfo && landInfo.addressdetail) ? landInfo.addressdetail : ''
  this.elevation = (landInfo && landInfo.elevation) ? landInfo.elevation : ''
  this.dependenceid = (landInfo && landInfo.dependenceid) ? landInfo.dependenceid : 'parcel-self'
  this.departmentid = (landInfo && landInfo.departmentid) ? landInfo.departmentid : ''
  this.belongpeasantid = (landInfo && landInfo.belongpeasantid) ? landInfo.belongpeasantid : ''
  this.introduce = (landInfo && landInfo.introduce) ? landInfo.introduce : ''
  this.area = (landInfo && landInfo.area) ? landInfo.area : ''
  this.responsibleuserids = (landInfo && landInfo.responsibleuserids) ? landInfo.responsibleuserids : ''
  this.longitude = (landInfo && landInfo.longitude) ? landInfo.longitude : ''
  this.latitude = (landInfo && landInfo.latitude) ? landInfo.latitude : ''
  this.coords = (landInfo && landInfo.coords) ? landInfo.coords : ''
  this.provincecode = (landInfo && landInfo.provincecode) ? landInfo.provincecode : ''
  this.citycode = (landInfo && landInfo.citycode) ? landInfo.citycode : ''
  this.countycode = (landInfo && landInfo.countycode) ? landInfo.countycode : ''
  this.towncode = (landInfo && landInfo.towncode) ? landInfo.towncode : ''
  this.villagecode = (landInfo && landInfo.villagecode) ? landInfo.villagecode : ''
  this.attachments = (landInfo && landInfo.attachments) ? landInfo.attachments : []
  this.insurance = (landInfo && landInfo.insurance) ? landInfo.insurance : 'parcel-insurance-uninsured'
  this.type = (landInfo && landInfo.type) ? landInfo.type : 'parcel-type-farmland'

  this.setSupportid = setSupportid
  this.getSupportid = getSupportid
  this.setId = setId
  this.getId = getId
  this.setName = setName
  this.getName = getName
  this.setAddresscode = setAddresscode
  this.getAddresscode = getAddresscode
  this.setAddressdetail = setAddressdetail
  this.getAddressdetail = getAddressdetail
  this.setElevation = setElevation
  this.getElevation = getElevation
  this.setDependenceid = setDependenceid
  this.getDependenceid = getDependenceid
  this.setDepartmentid = setDepartmentid
  this.getDepartmentid = getDepartmentid
  this.setBelongpeasantid = setBelongpeasantid
  this.getBelongpeasantid = getBelongpeasantid
  this.setIntroduce = setIntroduce
  this.getIntroduce = getIntroduce
  this.setArea = setArea
  this.getArea = getArea
  this.setResponsibleuserids = setResponsibleuserids
  this.getResponsibleuserids = getResponsibleuserids
  this.setLongitude = setLongitude
  this.getLongitude = getLongitude
  this.setLatitude = setLatitude
  this.getLatitude = getLatitude
  this.setCoords = setCoords
  this.getCoords = getCoords
  this.getProvincecode = getProvincecode
  this.setProvincecode = setProvincecode
  this.getCitycode = getCitycode
  this.setCitycode = setCitycode
  this.getCountycode = getCountycode
  this.setCountycode = setCountycode
  this.getTowncode = getTowncode
  this.setTowncode = setTowncode
  this.getVillagecode = getVillagecode
  this.setVillagecode = setVillagecode
  this.getAttachments = getAttachments
  this.setAttachments = setAttachments
  this.getInsurance = getInsurance
  this.setInsurance = setInsurance
  this.getType = getType
  this.setType = setType

  this.getLand = getLand
  this.addLand = addLand
  this.editLand = editLand
  this.deleteLand = deleteLand
  this.drawLand = drawLand
  this.landProfile = landProfile
}

function setSupportid (supportid) {
  this.supportid = supportid
}

function getSupportid () {
  return this.supportid
}

function setId (id) {
  this.id = id
}

function getId () {
  return this.id
}

function setName (name) {
  this.name = name
}

function getName () {
  return this.name
}

function setAddresscode (addresscode) {
  this.addresscode = addresscode
}

function getAddresscode () {
  return this.addresscode
}

function setAddressdetail (addressdetail) {
  this.addressdetail = addressdetail
}

function getAddressdetail () {
  return this.addressdetail
}

function setElevation (elevation) {
  this.elevation = elevation
}

function getElevation () {
  return this.elevation
}

function setDependenceid (dependenceid) {
  this.dependenceid = dependenceid
}

function getDependenceid () {
  return this.dependenceid
}

function setDepartmentid (departmentid) {
  this.departmentid = departmentid
}

function getDepartmentid () {
  return this.departmentid
}

function setBelongpeasantid (belongpeasantid) {
  this.belongpeasantid = belongpeasantid
}

function getBelongpeasantid () {
  return this.belongpeasantid
}

function setIntroduce (introduce) {
  this.introduce = introduce
}

function getIntroduce () {
  return this.introduce
}

function setArea (area) {
  this.area = area
}

function getArea () {
  return this.area
}

function setResponsibleuserids (responsibleuserids) {
  this.responsibleuserids = responsibleuserids
}

function getResponsibleuserids () {
  return this.responsibleuserids
}

function setLongitude (longitude) {
  this.longitude = longitude
}

function getLongitude () {
  return this.longitude
}

function setLatitude (latitude) {
  this.latitude = latitude
}

function getLatitude () {
  return this.latitude
}

function setCoords (coords) {
  this.coords = coords
}

function getCoords () {
  return this.coords
}

function getProvincecode () {
  return this.provincecode
}

function setProvincecode (provincecode) {
  this.provincecode = provincecode
}

function getCitycode () {
  return this.citycode
}

function setCitycode (citycode) {
  this.citycode = citycode
}

function getCountycode () {
  return this.countycode
}

function setCountycode (countycode) {
  this.countycode = countycode
}

function getTowncode () {
  return this.towncode
}

function setTowncode (towncode) {
  this.towncode = towncode
}

function getVillagecode () {
  return this.villagecode
}

function setVillagecode (villagecode) {
  this.villagecode = villagecode
}

function getAttachments () {
  return this.attachments
}

function setAttachments (attachments) {
  this.attachments = attachments
}

function getInsurance () {
  return this.insurance
}

function setInsurance (insurance) {
  this.insurance = insurance
}

function getType () {
  return this.type
}

function setType (type) {
  this.type = type
}

function getLand () {
  return Object.assign({}, {
    id: this.getId(),
    supportid: this.getSupportid(),
    name: this.getName(),
    addresscode: this.getAddresscode(),
    addressdetail: this.getAddressdetail(),
    elevation: this.getElevation() || 0,
    dependenceid: this.getDependenceid(),
    departmentid: this.getDepartmentid(),
    belongpeasantid: this.getBelongpeasantid(),
    introduce: this.getIntroduce(),
    area: this.getArea(),
    responsibleuserids: this.getResponsibleuserids() || [],
    longitude: this.getLongitude(),
    latitude: this.getLatitude(),
    coords: this.getCoords(),
    provincecode: this.getProvincecode(),
    citycode: this.getCitycode(),
    countycode: this.getCountycode(),
    towncode: this.getTowncode(),
    villagecode: this.getVillagecode(),
    attachments: this.getAttachments(),
    insurance: this.getInsurance(),
    type: this.getType()
  })
}

async function addLand () {
  // 增加地块
  let resolve = await excuteApis(this.getLand(), global.constant.parcelApis, 'parcel', 'add')
  return resolve
}

async function editLand () {
  // 编辑地块
  let resolve = await excuteApis(this.getLand(), global.constant.parcelApis, 'parcel', 'update')
  return resolve
}

async function deleteLand () {
  // 编辑地块
  let resolve = await excuteApis(this.getLand(), global.constant.parcelApis, 'parcel', 'delete')
  return resolve
}

async function drawLand () {
  // 绘制地块
  let repuestParams = Object.assign({}, {coords: this.getCoords()})
  let resolve = await excuteApis(repuestParams, global.constant.parcelApis, 'parcelCoord', 'updateBatch')
  return resolve
}

async function landProfile () {
  // 查询地块轮廓
  let repuestParams = Object.assign({}, {parcelid: this.getId(), supportid: this.getSupportid()})
  let resolve = await excuteApis(repuestParams, global.constant.parcelApis, 'parcelCoord', 'query')
  return resolve
}
