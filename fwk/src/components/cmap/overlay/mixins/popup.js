import EventsMixin from '../../mixins/events'

export default {
  mixins: [EventsMixin],
  inject: ['getLayers', 'addLayer', 'removeLayer'],
  props: {
    popupProps: Object, // { minWidth: 50, maxWidth: 500, autoPan: true, closeButton: true, offset: [0, 7], autoPanPadding: [5, 5], keepInView: false, className: "", zoomAnimation: true }
    popupEvent: {
      type: String,
      default: 'click'
    }
  },
  mounted () {
    this.createLayer()
  },
  destroyed () {
    this.removeLayer(this.layer)
  },
  data () {
    return {
      layer: null,
      _openPopup: false,
      _created: false
    }
  },
  methods: {
    createLayer () {
      this.layer = this.create()
      this._created = true
      if (!this.layer) return
      this.addLayer(this.layer)
      this.bindPopupEvent()
    },
    bindPopupEvent () {
      if (!this.$slots.default || !this.popupEvent) return

      let self = this
      this.addListener(this.layer, this.popupEvent, function (point) {
        self.popupOpen(point)
      })
    },
    popupOpen (point) {
      this._openPopup = false
      this.$forceUpdate()
      this.$nextTick(() => {
        this._openPopup = true
        this.$forceUpdate()
        this.getLayers().then(layers => {
          let popup = layers.openInfoWindow((point && point.latlng) || this.latlng, this.$el.children[0], this.defaultPopupProps || this.popupProps)
          let self = this
          let closeEvent = window.CEvent.addListener(popup, 'close', function () {
            window.CEvent.removeListener(closeEvent)
            self.popupClose()
          })
          this.$emit('c-popupopen')
        })
      })
    },
    popupClose () {
      this.$nextTick(() => {
        this.$emit('c-popupclose')
      })
    }
  },
  render (h) {
    let keys = Object.keys(this.$slots)
    if (!keys.length || (this._created && this.popupEvent && !this._openPopup)) return null

    let children = [this.$slots.default]
    keys.filter(k => k !== 'default').forEach(k => children.push(this.$slots[k]))
    return h('div', { style: { display: 'none' } }, children)
  }
}
