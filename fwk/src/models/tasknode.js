export function CTasknode (tasknode) {
  this.nodetime =
    tasknode && tasknode.hasOwnProperty('nodetime') ? tasknode.nodetime : null
  this.nodeperson =
    tasknode && tasknode.hasOwnProperty('nodeperson')
      ? tasknode.nodeperson
      : null
  this.nodesubtitle =
    tasknode && tasknode.hasOwnProperty('nodesubtitle')
      ? tasknode.nodesubtitle
      : null
  this.nodecontent =
    tasknode && tasknode.hasOwnProperty('nodecontent')
      ? tasknode.nodecontent
      : null
  this.nodeaudios =
    tasknode && tasknode.hasOwnProperty('nodeaudios')
      ? tasknode.nodeaudios
      : null
  this.nodeimages =
    tasknode && tasknode.hasOwnProperty('nodeimages')
      ? tasknode.nodeimages
      : null
  this.nodevideos =
    tasknode && tasknode.hasOwnProperty('nodevideos')
      ? tasknode.nodevideos
      : null
  this.nodeattachments =
    tasknode && tasknode.hasOwnProperty('nodeattachments')
      ? tasknode.nodeattachments
      : null
  this.nodelocation =
    tasknode && tasknode.hasOwnProperty('nodelocation')
      ? tasknode.nodelocation
      : null
  this.nodeindex =
    tasknode && tasknode.hasOwnProperty('nodeindex') ? tasknode.nodeindex : null
  this.multilinecontent =
    tasknode && tasknode.hasOwnProperty('multilinecontent')
      ? tasknode.multilinecontent
      : null
  this.nodetype =
    tasknode && tasknode.hasOwnProperty('nodetype') ? tasknode.nodetype : null

  this.nodetypenote =
    tasknode && tasknode.hasOwnProperty('nodetypenote')
      ? tasknode.nodetypenote
      : null
}

CTasknode.prototype.getNodetime = function () {
  return this.nodetime
}

CTasknode.prototype.setNodetime = function (nodetime) {
  this.nodetime = nodetime
}

CTasknode.prototype.getNodeperson = function () {
  return this.nodeperson
}

CTasknode.prototype.setNodeperson = function (nodeperson) {
  this.nodeperson = nodeperson
}

CTasknode.prototype.getNodesubtitle = function () {
  return this.nodesubtitle
}

CTasknode.prototype.setNodesubtitle = function (nodesubtitle) {
  this.nodesubtitle = nodesubtitle
}

CTasknode.prototype.getNodecontent = function () {
  return this.nodecontent
}

CTasknode.prototype.setNodecontent = function (nodecontent) {
  this.nodecontent = nodecontent
}

CTasknode.prototype.getNodeaudios = function () {
  return this.nodeaudios
}

CTasknode.prototype.setNodeaudios = function (nodeaudios) {
  this.nodeaudios = nodeaudios
}

CTasknode.prototype.getNodeimages = function () {
  return this.nodeimages
}

CTasknode.prototype.setNodeimages = function (nodeimages) {
  this.nodeimages = nodeimages
}

CTasknode.prototype.getNodevideos = function () {
  return this.nodevideos
}

CTasknode.prototype.setNodevideos = function (nodevideos) {
  this.nodevideos = nodevideos
}

CTasknode.prototype.getNodeattachments = function () {
  return this.nodeattachments
}

CTasknode.prototype.setNodeattachments = function (nodeattachments) {
  this.nodeattachments = nodeattachments
}

CTasknode.prototype.getNodelocation = function () {
  return this.nodelocation
}

CTasknode.prototype.setNodelocation = function (nodelocation) {
  this.nodelocation = nodelocation
}

CTasknode.prototype.getNodeindex = function () {
  return this.nodeindex
}

CTasknode.prototype.setNodeindex = function (nodeindex) {
  this.nodeindex = nodeindex
}

CTasknode.prototype.getMultilinecontent = function () {
  return this.multilinecontent
}

CTasknode.prototype.setMultilinecontent = function (multilinecontent) {
  this.multilinecontent = multilinecontent
}

CTasknode.prototype.getNodetype = function () {
  return this.nodetype
}

CTasknode.prototype.setNodetype = function (nodetype) {
  this.nodetype = nodetype
}

CTasknode.prototype.getNodetypenote = function () {
  return this.nodetypenote
}

CTasknode.prototype.setNodetypenote = function (nodetypenote) {
  this.nodetypenote = nodetypenote
}
