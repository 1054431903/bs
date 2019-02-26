import { excuteApis } from '@/apis'

export function CProductive (productive) {
  this.id = (productive && productive.id) ? productive.id : null
  this.plantid = (productive && productive.plantid) ? productive.plantid : null
  this.year = (productive && productive.outputyear) ? productive.outputyear : null
  this.output = (productive && productive.output) ? productive.output : null
  this.level = (productive && productive.outputlevel) ? productive.outputlevel : null
  this.levelname = (productive && productive.outputlevelname) ? productive.outputlevelname : null
  this.category = (productive && productive.preestimatecategory) ? productive.preestimatecategory : null
  this.categoryname = (productive && productive.preestimatecategoryname) ? productive.preestimatecategoryname : null
  this.preoutput = (productive && productive.preestimateoutput) ? productive.preestimateoutput : null
  this.preoutputunit = (productive && productive.preestimateoutputunit) ? productive.preestimateoutputunit : null
  this.preoutputunitname = (productive && productive.preestimateoutputunitname) ? productive.preestimateoutputunitname : null
  this.preland = (productive && productive.preestimatelandoutput) ? productive.preestimatelandoutput : null
  this.prelandunit = (productive && productive.preestimatelandoutputunit) ? productive.preestimatelandoutputunit : null
  this.prelandunitname = (productive && productive.preestimatelandoutputunitname) ? productive.preestimatelandoutputunitname : null
  this.actoutput = (productive && productive.actualoutput) ? productive.actualoutput : null
  this.actoutputunit = (productive && productive.actualoutputunit) ? productive.actualoutputunit : null
  this.actoutputunitname = (productive && productive.actualoutputunitname) ? productive.actualoutputunitname : null
}

CProductive.prototype.getId = function () {
  return this.id
}

CProductive.prototype.setId = function (id) {
  this.id = id
}

CProductive.prototype.getPlantid = function () {
  return this.plantid
}

CProductive.prototype.setPlantid = function (plantid) {
  this.plantid = plantid
}

CProductive.prototype.getYear = function () {
  return this.year
}

CProductive.prototype.setYear = function (year) {
  this.year = year
}

CProductive.prototype.getOutput = function () {
  return this.output
}

CProductive.prototype.setOutput = function (output) {
  this.output = output
}

CProductive.prototype.getLevel = function () {
  return this.level
}

CProductive.prototype.setLevel = function (level) {
  this.level = level
}

CProductive.prototype.getLevelname = function () {
  return this.levelname
}

CProductive.prototype.setLevelname = function (levelname) {
  this.levelname = levelname
}

CProductive.prototype.getCategory = function () {
  return this.category
}

CProductive.prototype.setCategory = function (category) {
  this.category = category
}

CProductive.prototype.getCategoryname = function () {
  return this.categoryname
}

CProductive.prototype.setCategoryname = function (categoryname) {
  this.categoryname = categoryname
}

CProductive.prototype.getPreoutput = function () {
  return this.preoutput
}

CProductive.prototype.setPreoutput = function (preoutput) {
  this.preoutput = preoutput
}

CProductive.prototype.getPreoutputunit = function () {
  return this.preoutputunit
}

CProductive.prototype.setPreoutputunit = function (preoutputunit) {
  this.preoutputunit = preoutputunit
}

CProductive.prototype.getPreoutputunitname = function () {
  return this.preoutputunitname
}

CProductive.prototype.setPreoutputunitname = function (preoutputunitname) {
  this.preoutputunitname = preoutputunitname
}

CProductive.prototype.getPreland = function () {
  return this.preland
}

CProductive.prototype.setPreland = function (preland) {
  this.preland = preland
}

CProductive.prototype.getPrelandunit = function () {
  return this.prelandunit
}

CProductive.prototype.setPrelandunit = function (prelandunit) {
  this.prelandunit = prelandunit
}

CProductive.prototype.getPrelandunitname = function () {
  return this.prelandunitname
}

CProductive.prototype.setPrelandunitname = function (prelandunitname) {
  this.prelandunitname = prelandunitname
}

CProductive.prototype.getActoutput = function () {
  return this.actoutput
}

CProductive.prototype.setActoutput = function (actoutput) {
  this.actoutput = actoutput
}

CProductive.prototype.getActoutputunit = function () {
  return this.actoutputunit
}

CProductive.prototype.setActoutputunit = function (actoutputunit) {
  this.actoutputunit = actoutputunit
}

CProductive.prototype.getActoutputunitname = function () {
  return this.actoutputunitname
}

CProductive.prototype.setActoutputunitname = function (actoutputunitname) {
  this.actoutputunitname = actoutputunitname
}

CProductive.prototype.getProductive = function () {
  return Object.assign({}, {
    id: this.getId(),
    plantid: this.getPlantid(),
    year: this.getYear(),
    output: this.getOutput(),
    level: this.getLevel(),
    levelname: this.getLevelname(),
    category: this.getCategory(),
    categoryname: this.getCategoryname(),
    preoutput: this.getPreoutput(),
    preoutputunit: this.getPreoutputunit(),
    preoutputunitname: this.getPreoutputunitname(),
    preland: this.getPreland(),
    prelandunit: this.getPrelandunit(),
    prelandunitname: this.getPrelandunitname(),
    actoutput: this.getActoutput(),
    actoutputunit: this.getActoutputunit(),
    actoutputunitname: this.getActoutputunitname()
  })
}

CProductive.prototype.getApisProductive = function () {
  return Object.assign({}, {
    id: this.getId(),
    plantid: this.getPlantid(),
    outputyear: this.getYear(),
    output: this.getOutput(),
    outputlevel: this.getLevel(),
    preestimatecategory: this.getCategory(),
    preestimateoutput: this.getPreoutput(),
    preestimateoutputunit: this.getPreoutputunit(),
    preestimatelandoutput: this.getPreland(),
    preestimatelandoutputunit: this.getPrelandunit(),
    actualoutput: this.getActoutput(),
    actualoutputunit: this.getActoutputunit()
  })
}

CProductive.prototype.queryProductiveById = async function () {
  // 根据产量信息ID查询产量信息
  let resolve = await excuteApis(Object.assign({}, {id: this.getId(), plantid: this.getPlantid()}), global.constant.parcelApis, 'parcelPlantYield', 'query')
  return resolve
}

CProductive.prototype.queryProdcutive = async function () {
  // 根据查询产量信息
  let resolve = await excuteApis(Object.assign({}, {plantid: this.getPlantid()}), global.constant.parcelApis, 'parcelPlantYield', 'query')
  return resolve
}

CProductive.prototype.saveProductive = async function () {
  // 增加或编辑保存产量信息
  let method = this.getId() ? 'update' : 'add'
  let resolve = await excuteApis(this.getApisProductive(), global.constant.parcelApis, 'parcelPlantYield', method)
  return resolve
}

CProductive.prototype.deleteProductive = async function () {
  // 删除产量信息
  let requestParams = {}
  this.getId() ? Object.assign(requestParams, {id: this.getId()}) : null
  !(this.getId()) && this.getPlantid() ? Object.assign(requestParams, {id: this.getPlantid()}) : null
  let resolve = await excuteApis(requestParams, global.constant.parcelApis, 'parcelPlantYield', 'delete')
  return resolve
}
