export function CInput (inputInfo) {
  this.id = inputInfo && inputInfo.id ? inputInfo.id : null
  this._id = inputInfo && inputInfo._id ? inputInfo._id : null
  // 投入品类别
  this.category = inputInfo && inputInfo.category ? inputInfo.category : null
  // 投入品类别名称
  this.categoryName =
    inputInfo && inputInfo.categoryName ? inputInfo.categoryName : null
  // 投入品名称
  this.name = inputInfo && inputInfo.name ? inputInfo.name : null
  // 投入品规格
  this.specification =
    inputInfo && inputInfo.specification ? inputInfo.specification : null
  // 投入品用量
  this.quantity = inputInfo && inputInfo.quantity ? inputInfo.quantity : null
  // 投入品用量单位
  this.quantityUnit =
    inputInfo && inputInfo.quantityUnit ? inputInfo.quantityUnit : null
  // 投入品用量单位名称
  this.quantityUnitName =
    inputInfo && inputInfo.quantityUnitName ? inputInfo.quantityUnitName : null
  // 投入品用量与单位名称合并的字符串
  this.quantityStr =
    inputInfo && inputInfo.quantityStr ? inputInfo.quantityStr : null
  // 投入品厂家
  this.vender = inputInfo && inputInfo.vender ? inputInfo.vender : null
  // 记录创建人
  this.creator = inputInfo && inputInfo.creator ? inputInfo.creator : null
}
