
function mapOf (name, maxZoom, sate, sateLabel) {
  let layers = [new window.CTileLayer(2, maxZoom, sate, null, null)]
  if (sateLabel) layers.push(new window.CTileLayer(2, maxZoom, sateLabel, null, null))
  return new window.CMapType(layers, name)
}

export const types = {
  VECTORMAP: mapOf('矢量', 17, 'mapyn'),
  HIDDENMAP: mapOf('隐藏', 18, null),
  MIXMAP: mapOf('影像', 17, 'sateyn', 'satelabelyn'),
  CDMIXMAP: mapOf('成都影像', 18, 'satecd', 'satelabelcd'),
  YCMIXMAP: mapOf('延川影像', 18, 'sateyc', 'satelabelyc'),
  YCHMIXMAP: mapOf('延长影像', 18, 'sateych', 'satelabelych')
}

export function capitalize (str) {
  return str.charAt(0).toUpperCase() + str.slice(1)
}

export function watchSetter (targetName, props) {
  return props.reduce((r, prop) => {
    let conf = typeof prop === 'string' ? { prop } : prop
    r[conf.prop] = {
      handler: function (val) {
        if (!this[targetName]) return
        this[targetName][conf.method || `set${capitalize(conf.prop)}`](val)
      },
      deep: !!conf.deep
    }
    return r
  }, {})
}
