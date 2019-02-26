<template style="background-color:#fff">
  <div class="pt-4 px-4 "
       style="background-color:#fff;height:600px;width:100%">
    <v-layout row
              wrap>
      <div style="width:100%;background-color:#fff;"
           class="pt-3">
        <div v-if="len"
             style="float:left;z-index:-1"
             v-for="(item, index) in device"
             :key="index"
             class="cardHover">
          <div style="width:216px;box-shadow:none;position:relative;"
               class="py-3 px-3">
            <img style="width:184px;height:184px;"
                 src="/static/img/video_img.png" />
            <img src="/static/img/video.png"
                 style="width:50px;height:50px;position:absolute;top:82px;left:82px;border-radius:115px;cursor:pointer;"
                 @click="openDialog(item)" />
            <div primary-title>
              <div style="width:100%;text-align:center;font-family:Microsoft Yahei;font-size:14px;color:#333333">
                <h4>{{fmt_Date(item.time.$numberLong)}}</h4>
              </div>
            </div>
          </div>
        </div>
        <div v-if="!len"
             style="text-align:center;"
             :class="clz">
          <div style="text-align: center;">
            <img src="static/default.png"
                 style="width:126px;height:89px;" />
          </div>
          <span style="font-size:16px;color:#999999;font-family:Microsoft Yahei;display:block">暂无历史天空视频数据</span>
        </div>
      </div>
      <v-dialog v-model="dialog"
                max-width="631">
        <v-card>
          <v-card-text>
            <!-- <ali-player
                  @play="played"
                  :source="source"
                  autoplay
                  ref="player"
                  height='560px'
                  width='565px'
                >
                </ali-player> -->
            <video :src="source"
                   controls="controls"
                   height="600px"
                   width="600px"
                   autoplay
                   poster="/static/img/video_img.png">
              您的浏览器展示不支持该视频播放
            </video>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-layout>
  </div>
</template>
<script>
// import VueAliplayer from 'vue-aliplayer'
function pre (v) {
  return String(v).length === 1 ? '0' + v : v
}
export default {
  name: 'bk_vedio',
  props: {
    device: {
      type: Array,
      required: true
    },
    clz: {
      type: String
    }
  },
  data () {
    return {
      dialog: false,
      maxDate: 0,
      veido_date: null,
      source: '',
      vedios: [],
      len: 0
    }
  },
  watch: {
    device (v) {
      this.len = v.length > 0
    }
  },
  methods: {
    openDialog (item) {
      this.dialog = true
      this.source = item.data.v.startsWith('http') ? item.data.v : 'http://' + item.data.v
      // this.$refs.player.instance.loadByUrl(this.source)
    },
    // played () {
    //   console.log('play callback')
    // },
    // play: function () {
    //   const player = this.$refs.player.instance
    //   player && player.play()
    // },
    // pause: function () {
    //   const player = this.$refs.player.instance
    //   player && player.pause()
    // },
    // replay: function () {
    //   const player = this.$refs.player.instance
    //   player && player.replay()
    // },
    fmt_Date (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}-${pre(d.getMonth() + 1)}-${pre(d.getDate())}`
    }
  },
  created () { }
  // components: {
  //   'ali-player': VueAliplayer
  // }
}
</script>
<style>
.wqx {
  margin-top: 6%;
}
.gis {
  margin-top: 25%;
}
.cardHover :hover {
  background-color: #f0f0f0;
}
</style>

