/**
 * 经纬度转换为svg路径
 * @param {Array} latlngs 经纬度坐标
 * @return {String} 坐标路径
 */
function latLngToCoordi (latlngs, size) {
  let base = size || 180
  try {
    if (latlngs && latlngs.length > 1) {
      let pairLatlng = latlngs.map(item => [item.lng, 90 - item.lat])
      let maxLng = Math.max.apply(this, pairLatlng.map(item => {
        return item[0]
      }))
      let minLng = Math.min.apply(this, pairLatlng.map(item => item[0]))
      let maxLat = Math.max.apply(this, pairLatlng.map(item => item[1]))
      let minLat = Math.min.apply(this, pairLatlng.map(item => item[1]))
      let lngScale = base / (maxLng - minLng)
      let latScale = base / (maxLat - minLat)
      let scale = Math.min(lngScale, latScale)
      // 计算y轴平移距离
      let my = (base - (maxLat - minLat) * scale) / 2
      let pathArr = pairLatlng.map(item => [(item[0] - minLng) * scale, (item[1] - minLat) * scale + my]) // 等比例缩放
      // 根据容器当前位置缩放
      return 'M' + pathArr.map(p => p.join(' ')).join(' L') + ' Z'
    } else {
      throw new Error('该坐标点集不满足绘图条件')
    }
  } catch (err) {
    console.log('' + err)
  }
}
export { latLngToCoordi }
