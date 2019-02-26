import Popup from './mixins/popup'

import { watchSetter } from '../util'

export default {
  name: 'c-marker',
  mixins: [Popup],
  props: {
    latlng: {
      type: Object, // {lat: Number, lng: Number}
      required: true
    },
    icon: Object, // { image: String, iconSize: [width, height], iconAnchor: [x, y], shadowSize: [width, height], popupAnchor: [x, y] }
    title: String,
    alt: String,
    clickable: {
      type: Boolean,
      default: true
    },
    draggable: {
      type: Boolean,
      default: false
    },
    keyboard: {
      type: Boolean,
      default: true
    },
    zIndexOffset: {
      type: Number,
      default: 0
    },
    opacity: {
      type: Number,
      default: 1
    },
    riseOnHover: {
      type: Boolean,
      default: false
    },
    riseOffset: {
      type: Number,
      default: 250
    }
  },
  watch: watchSetter('layer', [{ prop: 'latlng', method: 'setLatLng' }, 'zIndexOffset', 'opacity']),
  data () {
    return {
      defaultPopupProps: Object.assign({}, { offset: [0, -25] }, this.popupProps)
    }
  },
  methods: {
    create () {
      let opts = Object.assign(
        {},
        this.$props,
        { icon: this.$slots.icon ? new window.CDivIcon(Object.assign({}, this.icon, { html: this.$el.children[1].outerHTML })) : (this.icon && new window.CIcon(this.icon)) }
      )
      return new window.CMarker(this.latlng, opts)
    }
  }
}
