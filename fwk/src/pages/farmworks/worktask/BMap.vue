<template lang="pug">
  div(:id="mapid" class="bmapcontainer")
</template>

<script>
export default {
  name: 'v-bmap',
  props: {
    mapid: {
      type: String,
      default: 'allmap'
    },
    address: {
      type: Object,
      defalut: () => { }
    },
    location: {
      type: Object,
      default: () => { return Object.assign({}, { longitude: 116.3331398, latitude: 39.897445 }) }
    },
    zoom: {
      type: Number,
      defalut: 21
    },
    ctrlnavigation: {
      type: Boolean,
      defalut: false
    },
    ctrlscale: {
      type: Boolean,
      default: false
    },
    ctrloverviewmap: {
      type: Boolean,
      default: false
    },
    ctrlmaptype: {
      type: Boolean,
      defalut: false
    }
  },
  data () {
    return {
      map: null,
      geoc: null,
      point: null,
      icon: null,
      marker: null,
      title: '',
      subheader: '',
      province: '',
      city: ''
    }
  },
  methods: {
    getAddressName () {
      this.geoc = new window.BMap.Geocoder()
      this.geoc.getLocation(this.point, (rs) => {
        // let addComp = rs.addressComponents
        this.title = rs.surroundingPois.length ? rs.surroundingPois[0].title : ''
        this.province = rs.addressComponents['province']
        this.city = rs.addressComponents['city']
        this.subheader = this.province + this.city + (rs.surroundingPois.length ? rs.surroundingPois[0].address : '')
        this.$emit('update:address', Object.assign({}, { title: this.title, subheader: this.subheader }))
      })
    },
    setLocation (point, index) {
      this.icon = new window.BMap.Icon('/static/task/zuobiao.png',
        new window.BMap.Size(29, 39),
        {
          anchor: new window.BMap.Size(15, 39),
          imageOffset: new window.BMap.Size(0, 0 - index * 39)
        }
      )
      this.marker = new window.BMap.Marker(point, { icon: this.icon })
      this.map.addOverlay(this.marker)
    }
  },
  mounted () {
    this.map = new window.BMap.Map(this.mapid)
    this.point = new window.BMap.Point(this.location.longitude, this.location.latitude)
    this.ctrlnavigation ? this.map.centerAndZoom(this.point, 16) : this.map.centerAndZoom(this.point, 21)
    this.ctrlnavigation ? this.map.enableScrollWheelZoom() : null
    this.ctrlnavigation ? this.map.addControl(new window.BMap.NavigationControl()) : null
    this.ctrlscale ? this.map.addControl(new window.BMap.ScaleControl()) : null
    this.ctrloverviewmap ? this.map.addControl(new window.BMap.OverviewMapControl()) : null
    this.ctrlmaptype ? this.map.addControl(new window.BMap.MapTypeControl({ mapTypes: [window.BMAP_NORMAL_MAP, window.BMAP_HYBRID_MAP] })) : null
    this.getAddressName()
    this.setLocation(this.point, 0)
  },
  created () {
  }
}
</script>

<style lang="scss" scoped>
.mapcontainer .bmapcontainer {
  height: calc(100vh - 560px) !important;
}
.v-card__text .bmapcontainer {
  height: calc(100vh - 170px);
}
</style>
