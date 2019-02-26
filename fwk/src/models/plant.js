import { excuteApis } from '@/apis'

export function CPlant (plantInfo) {
  this.id = (plantInfo && plantInfo.id) ? plantInfo.id : null
  this.parcelid = (plantInfo && plantInfo.parcelid) ? plantInfo.parcelid : null
  this.categoryid = (plantInfo && plantInfo.categoryid) ? plantInfo.categoryid : null
  this.categoryname = (plantInfo && plantInfo.categoryname) ? plantInfo.categoryname : null
  this.breed = (plantInfo && plantInfo.varieties) ? plantInfo.varieties : null
  this.breedname = (plantInfo && plantInfo.varietiesname) ? plantInfo.varietiesname : null
  this.batch = (plantInfo && plantInfo.growingbatch) ? plantInfo.growingbatch : null
  this.begintime = (plantInfo && plantInfo.plantbegintime) ? plantInfo.plantbegintime : null
  this.peroid = (plantInfo && plantInfo.preestimategrowthcycle) ? plantInfo.preestimategrowthcycle : null
  this.peroidunit = (plantInfo && plantInfo.preestimategrowthcycleunit) ? plantInfo.preestimategrowthcycleunit : null
  this.age = (plantInfo && plantInfo.plantage) ? plantInfo.plantage : null
  this.ageunit = (plantInfo && plantInfo.plantageunit) ? plantInfo.plantageunit : null
  this.area = (plantInfo && plantInfo.area) ? plantInfo.area : null
  this.number = (plantInfo && plantInfo.number) ? plantInfo.number : null
  this.numberunit = (plantInfo && plantInfo.unit) ? plantInfo.unit : null
  this.products = (plantInfo && plantInfo.products) ? plantInfo.products : []
  this.introduce = (plantInfo && plantInfo.introduce) ? plantInfo.introduce : null
  this.attachments = (plantInfo && plantInfo.attachments) ? plantInfo.attachments : []
}

CPlant.prototype.getId = function () {
  return this.id
}

CPlant.prototype.setId = function (id) {
  this.id = id
}

CPlant.prototype.getParcelid = function () {
  return this.parcelid
}

CPlant.prototype.setParcelid = function (parcelid) {
  this.parcelid = parcelid
}

CPlant.prototype.getCategoryid = function () {
  return this.categoryid
}

CPlant.prototype.setCategoryid = function (categoryid) {
  this.categoryid = categoryid
}

CPlant.prototype.getCategoryname = function () {
  return this.categoryname
}

CPlant.prototype.setCategoryname = function (categoryname) {
  this.categoryname = categoryname
}

CPlant.prototype.getBreed = function () {
  return this.breed
}

CPlant.prototype.setBreed = function (breed) {
  this.breed = breed
}

CPlant.prototype.getBreedname = function () {
  return this.breedname
}

CPlant.prototype.setBreedname = function (breedname) {
  this.breedname = breedname
}

CPlant.prototype.getBatch = function () {
  return this.batch
}

CPlant.prototype.setBatch = function (batch) {
  this.batch = batch
}

CPlant.prototype.getBegintime = function () {
  return this.begintime
}

CPlant.prototype.setBegintime = function (begintime) {
  this.begintime = begintime
}

CPlant.prototype.getPeroid = function () {
  return this.peroid
}

CPlant.prototype.setPeroid = function (peroid) {
  this.peroid = peroid
}

CPlant.prototype.getPeroidunit = function () {
  return this.peroidunit
}

CPlant.prototype.setPeroidunit = function (peroidunit) {
  this.peroidunit = peroidunit
}

CPlant.prototype.getAge = function () {
  return this.age
}

CPlant.prototype.setAge = function (age) {
  this.age = age
}

CPlant.prototype.getAgeunit = function () {
  return this.ageunit
}

CPlant.prototype.setAgeunit = function (ageunit) {
  this.ageunit = ageunit
}

CPlant.prototype.getArea = function () {
  return this.area
}

CPlant.prototype.setArea = function (area) {
  this.area = area
}

CPlant.prototype.getNumber = function () {
  return this.number
}

CPlant.prototype.setNumber = function (number) {
  this.number = number
}

CPlant.prototype.getNumberunit = function () {
  return this.numberunit
}

CPlant.prototype.setNumberunit = function (numberunit) {
  this.numberunit = numberunit
}

CPlant.prototype.getProducts = function () {
  return this.products
}

CPlant.prototype.setProducts = function (products) {
  this.products = products
}

CPlant.prototype.getIntroduce = function () {
  return this.introduce
}

CPlant.prototype.setIntroduce = function (introduce) {
  this.introduce = introduce
}

CPlant.prototype.getAttachments = function () {
  return this.attachments
}

CPlant.prototype.setAttachments = function (attachments) {
  this.attachments = attachments
}

CPlant.prototype.getPlant = function () {
  return Object.assign({}, {
    id: this.getId(),
    parcelid: this.getParcelid(),
    categoryid: this.getCategoryid(),
    categoryname: this.getCategoryname(),
    breed: this.getBreed(),
    breedname: this.getBreedname(),
    batch: this.getBatch(),
    begintime: this.getBegintime(),
    peroid: this.getPeroid(),
    peroidunit: this.getPeroidunit(),
    age: this.getAge(),
    ageunit: this.getAgeunit(),
    area: this.getArea(),
    number: this.getNumber(),
    numberunit: this.getNumberunit(),
    products: this.getProducts(),
    introduce: this.getIntroduce(),
    attachments: this.getAttachments()
  })
}

CPlant.prototype.getApisPlant = function () {
  return Object.assign({}, {
    id: this.getId(),
    varieties: this.getBreed(),
    categoryid: this.getCategoryid(),
    growingbatch: this.getBatch(),
    plantbegintime: this.getBegintime(),
    preestimategrowthcycle: this.getPeroid() || 0,
    preestimategrowthcycleunit: this.getPeroidunit(),
    plantage: this.getAge() || 0,
    plantageunit: this.getAgeunit(),
    area: this.getArea(),
    number: this.getNumber() || 0,
    unit: this.getNumberunit(),
    introduce: this.getIntroduce(),
    parcelid: this.getParcelid(),
    attachments: this.getAttachments()
  })
}

CPlant.prototype.queryPlantById = async function () {
  // 根据种植信息ID查询种植信息
  let resolve = await excuteApis(Object.assign({}, {id: this.getId()}), global.constant.parcelApis, 'parcelPlant', 'query')
  return resolve
}

CPlant.prototype.queryPlant = async function () {
  // 根据地块ID查询种植信息
  let resolve = await excuteApis(Object.assign({}, {parcelid: this.getParcelid()}), global.constant.parcelApis, 'parcelPlant', 'query')
  return resolve
}

CPlant.prototype.savePlant = async function () {
  let method = this.getId() ? 'update' : 'add'
  // 增加或编辑种植信息
  let resolve = await excuteApis(this.getApisPlant(), global.constant.parcelApis, 'parcelPlant', method)
  return resolve
}

CPlant.prototype.deletePlant = async function () {
  // 删除种植信息
  let resolve = await excuteApis(this.getApisPlant(), global.constant.parcelApis, 'parcelPlant', 'delete')
  return resolve
}
