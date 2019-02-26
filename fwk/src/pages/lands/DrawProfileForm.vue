<template lang="pug">
  div.pl-2.pr-2
    v-card
      v-layout.pr-3(row)
        v-flex.pt-2(xs1)
          v-btn(@click="backLandAddForm")
            v-icon arrow_back
            span &nbsp;&nbsp;返回
        v-flex(xs10)
          span(class="positionLabel") 当前地块位置:{{addresscodename}}
        //-   v-select(
        //-     v-bind:items="provinces"
        //-     v-model="province"
        //-     item-text="name"
        //-     item-value="code"
        //-     label="省"
        //-     single-line
        //-     no-data-text="无"
        //-     auto
        //-     class="selected"
        //-   )
        //- v-flex(xs2)
        //-   v-select(
        //-     v-bind:items="cities"
        //-     v-model="city"
        //-     item-text="name"
        //-     item-value="code"
        //-     label="市"
        //-     single-line
        //-     no-data-text="无"
        //-     auto
        //-     class="selected"
        //-   )
        //- v-flex(xs2)
        //-   v-select(
        //-     v-bind:items="counties"
        //-     v-model="county"
        //-     item-text="name"
        //-     item-value="code"
        //-     label="县(区)"
        //-     single-line
        //-     no-data-text="无"
        //-     auto
        //-     class="selected"
        //-   )
        //- v-flex(xs2)
        //-   v-select(
        //-     v-bind:items="towns"
        //-     v-model="town"
        //-     item-text="name"
        //-     item-value="code"
        //-     label="镇(乡)"
        //-     single-line
        //-     no-data-text="无"
        //-     auto
        //-     class="selected"
        //-   )
        //- v-flex(xs2)
        //-   v-select(
        //-     v-bind:items="villages"
        //-     v-model="village"
        //-     item-text="name"
        //-     item-value="code"
        //-     label="村"
        //-     single-line
        //-     no-data-text="无"
        //-     class="selected"
        //-   )
        //- v-flex.pt-2(xs1)
        //-   v-btn(@click="addresscode ? " color="primary")
        //-     span &nbsp;&nbsp;定位
      v-layout(row)      
        c-map-layers(v-bind="map" class="currentMap")
        //- c-polygon(:latlngs.sync="polygon.latlngs" :editing="polygon.editing")
        //-   div polygon测试
  //- <div>
  //-   <v-btn @click="polygon.editing = !polygon.editing">edit polygon</v-btn>
  //-   <c-map-layers v-bind="map" style="width: 100%; height: 670px;">
  //-     <c-marker-cluster>
  //-       <c-marker v-bind="markers[0]">
  //-         <div>
  //-           <strong>制作单位</strong><br />
  //-           <strong>名称:</strong>中国测绘科学研究院<br />
  //-           <strong>时间:</strong>2013年8月13日<br />
  //-         </div>
  //-       </c-marker>
  //-       <c-marker v-bind="markers[1]">
  //-         <div>测试</div>
  //-       </c-marker>
  //-       <c-polyline v-bind="polyline">
  //-         <div>line测试</div>
  //-       </c-polyline>
  //-     </c-marker-cluster>
  //-     <c-label v-bind="labels[0]">
  //-       <div>Label测试</div>
  //-     </c-label>
  //-     <c-polygon :latlngs.sync="polygon.latlngs" :editing="polygon.editing">
  //-       <div>polygon测试</div>
  //-     </c-polygon>
  //-     <c-circle v-bind="circle">
  //-       <div>circle测试</div>
  //-     </c-circle>
  //-     <c-rectangle v-bind="rectangle">
  //-       <div>rectangle测试</div>
  //-     </c-rectangle>
  //-   </c-map-layers>
  //- </div>
</template>

<script>
import CMapLayers from '@/components/cmap/CMapLayers'
import CMarkerCluster from '@/components/cmap/overlay/CMarkerCluster'
import CMarker from '@/components/cmap/overlay/CMarker'
import CLabel from '@/components/cmap/overlay/CLabel'
import CPolyline from '@/components/cmap/overlay/CPolyline'
import CPolygon from '@/components/cmap/overlay/CPolygon'
import CCircle from '@/components/cmap/overlay/CCircle'
import CRectangle from '@/components/cmap/overlay/CRectangle'

export default {
  data () {
    return {
      parcel: {
        id: null,
        name: null,
        addresscode: null,
        addressdetail: null,
        area: null,
        elevation: 0,
        dependenceid: null,
        departmentid: null,
        belongpeasantid: null,
        introduce: null,
        coords: null
      },
      provinces: [],
      province: 0,
      provincename: '',
      cities: [],
      city: 0,
      cityname: '',
      counties: [],
      county: 0,
      countyname: '',
      towns: [],
      town: 0,
      townname: '',
      villages: [],
      village: 0,
      villagename: '',
      addresscode: null,
      addresscodename: '',
      curLandPosition: null,
      curCenterLatLng: null,
      map: {
        center: { lat: 33, lng: 100 },
        zoom: 12,
        mapType: null,
        scaleControl: true,
        overviewControl: false,
        zoomControl: false,
        largeMapControl: false,
        mapTypeControl: false,
        latLngControl: false
      },
      polygon: {
        latlngs: [
          { lat: 31, lng: 99 },
          { lat: 32, lng: 100 },
          { lat: 33, lng: 98 }
        ],
        editing: false
      }
    }
  },
  watch: {},
  methods: {
    backLandAddForm () {
      this.$router.go(-1)
    }
  },
  computed: {},
  mounted () {},
  created () {},
  components: {
    CMapLayers,
    CMarkerCluster,
    CMarker,
    CLabel,
    CPolyline,
    CPolygon,
    CCircle,
    CRectangle
  }
}
</script>

<style scoped>
.currentMap{
  width: 100%;
  height: 666px;
}
.positionLabel {
  line-height: 65px;
}
</style>
