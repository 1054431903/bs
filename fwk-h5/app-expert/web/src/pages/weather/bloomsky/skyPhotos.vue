<template>
  <div>
    <v-container style="padding:0px"
                 v-if="faild">
      <v-flex text-xs-center
              style="color:rgb(170, 170, 170);font-size:16px;padding-top:50px">暂无数据</v-flex>
    </v-container>
    <v-layout row
              wrap
              style="padding:12px"
              v-else>
      <v-flex v-for="(item, index) in vedios"
              :key="index"
              xs4
              style="position:relative;padding:2px">
        <v-card xs4>
          <v-card-media style="width:100%;height:100px"
                        :src="options[index].poster"
                        @click="playVideo(item.data.v)">
            <!-- <video-player  class="vjs-custom-skin vjs-big-play-centered"
                    ref="videoPlayer"
                    :options="options[index]">
            </video-player> -->
            <v-container fill-height
                         fluid>
              <v-layout fill-height>
                <v-flex xs12
                        align-center
                        style="text-align:center">
                  <img src="iot/list/play.png"
                       style="width:50px;height:50px;border-radius:115px;" />
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-media>
          <v-card-title>
            {{fmt_Date(item.time.$numberLong)}}
          </v-card-title>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
function pre (v) {
  return String(v).length === 1 ? '0' + v : v
}
export default {
  name: 'skyphotos',
  components: { videoPlayer },
  data () {
    return {
      faild: false,
      showBigImg: false,
      num: 0,
      vedios: [],
      options: []
    }
  },
  created () {
    this.vedios = this.$route.query.photos
    this.options = this.$route.query.options
    if (this.vedios.length > 0) {
      this.faild = false
    } else {
      this.faild = true
    }
  },
  methods: {
    fmt_Date (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}-${pre(d.getMonth() + 1)}-${pre(d.getDate())}`
    },
    playVideo (video) {
      console.log(video)
      try {
        if (window.native && window.native.player) {
          window.native.player(JSON.stringify({
            url: video
          }))
          return
        } else {
          this.showMessageInfo('HTML播放未实现')
        }
      } catch (e) {
        console.debug(e)
        this.showMessageError(e.message)
      }
    }
  }
}
</script>
<style scoped>
.skyPhoto {
  position: absolute;
  width: auto;
  bottom: 2px;
  left: 2px;
  right: 2px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.2);
  text-align: center;
}
.imgMask {
  position: absolute;
  height: 100%;
  width: auto;
  top: 44px;
  left: 0;
  z-index: 100;
  background: rgba(0, 0, 0, 0.6);
}
.showImg {
  height: 100%;
  width: 800px;
  position: absolute;
  transform: translate(-50%, -50%);
  border: 10px solid #fff;
}
.bigImg {
  width: 100%;
  height: 100%;
}
.prev {
  position: absolute;
  top: 50%;
  color: #fff;
  left: 10px;
  width: 40px;
  z-index: 999;
  transform: translate(10px, -50%);
}
.next {
  width: 40px;
  transform: translate(10px, -50%);
  color: #fff;
  position: absolute;
  top: 50%;
  right: 40px;
  z-index: 999;
}
.vjs-poster {
  background-size: 100% 100px;
}
.video-js .vjs-big-play-button {
  /*
    播放按钮换成圆形
  */
  text-align: center;
  height: 2em;
  width: 2em;
  line-height: 2em;
  border-radius: 1em;
}
.vjs-big-play-centered .vjs-big-play-button {
  left: 65%;
}
.video-js {
  width: 113px;
}
</style>
