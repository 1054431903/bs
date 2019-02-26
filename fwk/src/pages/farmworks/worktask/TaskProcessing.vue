<template lang="pug">
div.mt-1()
  v-layout.mt-1(row)
    v-flex.pt-1(xs12)
      div(class="trendslabel")
        img(:src="`static/task/renwudongtai.png`" width="16px" height="19px" style="vertical-align:middle")
        span.ml-2 任务动态（{{tasknodes.length}}&nbsp;条）
  template(v-for="(node, index) in tasknodes")         
    v-layout.mt-2(row)
      div(class="dashline" v-if="index !== tasknodes.length - 1")
      v-flex(xs1)
        v-avatar(size="45")      
          img(:src="(node.getNodeperson())['avatar'] ? (node.getNodeperson())['avatar'] : `static/task/fuzenren.png`")
      v-flex.pl-3(xs11 class="nodecontent")
        v-layout(row)
          v-flex(xs6)
            span {{node.getNodeperson() ? (node.getNodeperson())['name'] || (node.getNodeperson())['mobile'] : ''}}
            span(style="color: rgba(0, 0, 0, .47);") &nbsp;&nbsp;{{(node.getNodesubtitle()) ? ('（' + node.getNodesubtitle() + '）') : null}}
          v-flex(xs6 text-xs-right)
            div(style="color: rgba(0, 0, 0, .47);") {{formatDateTime(node.getNodetime())}} 
        v-layout(row v-if="node.getNodetype() === 'examination'")               
          v-flex(xs12)
            div(v-html="node.getNodetypenote()")     
        v-layout(row)               
          v-flex(xs12)
            div() {{node.getNodecontent()}}
        v-layout(row v-if="node.getNodeaudios() && (node.getNodeaudios()).length > 0")
          v-flex(xs12)
            div(class="audioline")
              audio-player(:resourceList="node.getNodeaudios()")
        div(class="imageline")      
          v-layout(row v-if="node.getNodeimages() && (node.getNodeimages()).length > 0" wrap)
            template(v-for="(image, imgindex) in node.getNodeimages()")
              v-flex(xs4 @click="reviewImg(index, imgindex)" class="imgreview")
                v-img.mr-2.mb-2(:src="image.url" height="120" min-width="180")
        div(row v-if="node.getNodeattachments() && (node.getNodeattachments()).length > 0")
          template(v-for="(attachment, index) in node.getNodeattachments()")
            v-layout(row)
              v-flex(xs12 class="attachment")
                img(:src="getIcon(attachment.name)" width="auto" height="20" style="vertical-align: middle; margin-right: 10px;")
                a(:key="index"
                  :href="attachment.url"
                  v-text="attachment.name"
                  class="accessory"
                  target="_blank")
        v-layout(row v-if="node.getNodelocation()")
          v-flex(xs12 )
            v-card(class="mapcontainer" @click.native.stop="mapReview(index)")
              div(class="address title pl-1" v-if="map.address && map.address.title") {{ map.address.title }}
              v-subheader(class="addressdetail pl-1" v-if="map.address && map.address.subheader") {{map.address.subheader}}
              v-bmap(:location="node.getNodelocation()" :mapid="'node' + index" :address.sync="map.address") 
  v-layout(row, justify-center)
    v-modaldialog(
      :isShow.sync="carouselsDialog"
      :modalMaxWidth="800"
      :bodyHeight="600"
      title="图片预览"
      :showCloseBtn="true")
      div
        v-carousel(hide-delimiters :value="currentImgIndex")
          v-carousel-item(
            v-for="(item,i) in imglist"
            :key="i"
            :src="item.url")
  v-layout(row, justify-center)
    v-modaldialog(
      :isShow.sync="bmapDialog"
      :modalMaxWidth="800"
      :bodyHeight="600"
      title="位置预览"
      :showCloseBtn="true")
      v-card
        v-bmap(:location="map.location" 
              :mapid="map.mapid"
              :zoom="map.zoom"
              :ctrlnavigation="map.ctrlnavigation"
              :ctrlscale="map.ctrlscale"
              :ctrloverviewmap="map.ctrloverviewmap"
              :ctrlmaptype="map.ctrlmaptype")         
</template>

<script>
import { parseTime, getIconPath } from '@/utils'
import AudioPlayer from '@/components/AudioPlayer.vue'
import VBmap from './BMap.vue'

export default {
  name: 'v-taskprocessing',
  props: {
    tasknodes: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      carouselsDialog: false,
      imglist: [],
      currentImgIndex: 0,
      bmapDialog: false,
      map: {
        location: null,
        zoom: 18,
        mapid: 'reviewMap',
        ctrlnavigation: true,
        ctrlscale: true,
        ctrloverviewmap: true,
        ctrlmaptype: true,
        address: {
          title: '',
          subheader: ''
        }
      }
    }
  },
  computed: {},
  mounted () { },
  methods: {
    formatDateTime (longtime) {
      if (!longtime) return ''
      let datetime = new Date(Number(longtime))
      return parseTime(datetime, 'yyyy-MM-dd hh:mm')
    },
    getIcon (resourcename) {
      return getIconPath(resourcename)
    },
    reviewImg (index, imgindex) {
      this.imglist = this.tasknodes[index].getNodeimages()
      this.currentImgIndex = imgindex
      this.carouselsDialog = true
    },
    mapReview (index) {
      this.map.location = this.tasknodes[index].getNodelocation()
      this.bmapDialog = true
    }
  },
  created () { },
  components: {
    AudioPlayer,
    VBmap
  }
}
</script>
<style lang="scss">
.dashline {
  border: 1px dashed rgb(230, 223, 223);
  position: relative;
  left: 25px;
}
.mapcontainer {
  margin-top: 10px;
  width: 400px;
}
.nodecontent {
  min-height: 60px;
}
.attachment {
  line-height: 45px;
  background-color: #f5f5f5;
  margin-top: 5px;
  padding-left: 10px;
}
.audioline {
  margin-top: 5px;
}
.imageline {
  margin-top: 5px;
}
.imgreview {
  cursor: pointer;
}
.address {
  padding-top: 5px;
  font-size: 10px;
}
.addressdetail {
  height: 30px;
}
</style>
