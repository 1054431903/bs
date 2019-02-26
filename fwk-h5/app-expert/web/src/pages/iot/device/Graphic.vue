<template>
  <div class="page">
    <div class="page-init" v-if="loading">
      <img style="width: 20px;height: 20px" src="loading.gif"/>
    </div>
    <div class="page-main" v-else :style="rotateStyle">
      <div id="player-container" class="page-main-player">
        <v-video-player ref="player"
                        :options="playerOptions"
                        :playsinline="true"
                        @loadeddata="onPlayerLoadeddata"
                        @statechanged="playerStateChanged"
                        @ready="playerReadied"
                        @canplay="onPlayerCanplay"
                        @canplaythrough="onPlayerCanplaythrough">
        </v-video-player>
      </div>
      <div class="page-main-baffle">&nbsp;</div>
      <div class="page-main-console">
        <div class="page-main-console-head">
          <div class="page-main-console-head-left">
            <div class="page-main-console-btn-back" @click="back">&nbsp;</div>
          </div>
          <div class="page-main-console-head-center">
            <div class="page-main-console-btn-up" v-if="isPlay && device.isControllable" @click="turnUp">&nbsp;</div>
          </div>
          <div class="page-main-console-head-right">&nbsp;</div>
        </div>
        <div class="page-main-console-body">
          <div class="page-main-console-body-left">
            <div class="page-main-console-btn-left" v-if="isPlay && device.isControllable" @click="turnLeft">&nbsp;</div>
          </div>
          <div class="page-main-console-body-center">
            <div class="page-main-console-btn-play"  v-if="isReady && !isLoading" @click="play">&nbsp;</div>
            </div>
          <div class="page-main-console-body-right">
            <div class="page-main-console-btn-right" v-if="isPlay && device.isControllable" @click="turnRight">&nbsp;</div>
          </div>
        </div>
        <div class="page-main-console-foot">
          <div class="page-main-console-foot-left">&nbsp;</div>
          <div class="page-main-console-foot-center">
            <div class="page-main-console-btn-down" v-if="isPlay && device.isControllable" @click="turnDown">&nbsp;</div>
          </div>
          <div class="page-main-console-foot-right">
            <div class="page-main-console-lbl-datetime">{{datetimeString}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Conf from '@/conf'
import Share from './share'
import Moment from 'moment'
import 'video.js/dist/video-js.css'
import {videoPlayer as VueVideoPlayer} from 'vue-video-player'
import 'videojs-contrib-hls'
const BACK_TIMEOUT = 2000
export default {
  name: 'iot-device-graphic',
  mixins: [Share],
  components: {
    'v-video-player': VueVideoPlayer
  },
  data () {
    return {
      loading: false,
      device: null,
      rotateStyle: null,
      datetimeInterval: null,
      datetimeString: null,
      isPlay: false,
      isReady: false,
      isLoading: false,
      playerOptions: {
        // 自动播放
        autoplay: false,
        inactivityTimeout: 15 * 1000,
        // 循环
        loop: false,
        // 静音
        muted: false,
        // 自动拉伸
        fluid: false,
        // 拉伸模式下的显示比例
        get aspectRatio () {
          let container = document.getElementById('player-container')
          if (container) {
            let contentWidth = window.getComputedStyle(container).width
            let contentHeight = window.getComputedStyle(container).height
            let ratio = `${contentWidth.replace('px', '')}:${contentHeight.replace('px', '')}`
            return ratio
          } else {
            return undefined
          }
        },
        nativeTextTracks: false,
        nativeAudioTracks: false,
        nativeVideoTracks: false,
        autoSetup: false,
        language: 'zh-CN',
        preload: 'metadata',
        sources: [{
          type: 'application/x-mpegURL',
          src: undefined
        }],
        controls: false,
        playbackRates: [],
        // poster: "/static/images/author.jpg",
        notSupportedMessage: '此视频暂无法播放，请稍后再试',
        techOrder: ['html5']
      },
      ys7Server: (Conf.YS7 || {}).ys7Server || '',
      ys7AppKey: (Conf.YS7 || {}).ys7AppKey || '',
      ys7AppSecret: (Conf.YS7 || {}).ys7AppSecret || '',
      ys7AccessToken: null
    }
  },
  created () {
    this.load()
  },
  mounted () {
    this.datetimeRefresherEnable()
  },
  beforeDestroy () {
    this.datetimeRefresherDisable()
  },
  watch: {
    'orientation' (nValue, oValue) {
      switch (nValue) {
        case 'portrait':
          if (this.rotateStyle !== this.rotateStylePortrait) {
            this.rotateStyle = this.rotateStylePortrait
          }
          break
        case 'landscape':
          if (this.rotateStyle !== this.rotateStyleLandscape) {
            this.rotateStyle = this.rotateStyleLandscape
          }
          break
        default:
          break
      }
    }
  },
  computed: {
    liveAddress () {
      if (this.device && this.device.liveAddress) {
        return this.device.liveAddress
      } else {
        return this.defaultCamera ? this.defaultCamera.liveAddress.hlsHd : undefined
      }
    },
    defaultCamera () {
      if (this.device && this.device.CameraList && this.device.CameraList.length > 0) {
        return this.device.CameraList[0]
      }
    }
  },
  methods: {
    async playerStateChanged (event) {
    },
    async onPlayerLoadeddata (event) {
    },
    async playerReadied (event) {
      this.isReady = true
      this.isLoading = true
      setTimeout(() => {
        this.$refs.player.player.play()
      }, 1000)
    },
    async onPlayerCanplay (event) {
      this.isPlay = true
    },
    async onPlayerCanplaythrough (event) {
    },
    play () {
      this.$refs.player.player.play()
    },
    back (timeout) {
      setTimeout(() => {
        this.$router.go(-1)
      }, timeout)
    },
    turnUp () {
      this.sendTurnCommand(0)
    },
    turnDown () {
      this.sendTurnCommand(1)
    },
    turnLeft () {
      this.sendTurnCommand(2)
    },
    turnRight () {
      this.sendTurnCommand(3)
    },
    datetimeRefresherEnable () {
      this.datetimeInterval = setInterval(() => {
        this.datetimeString = Moment(new Date()).format('YYYY.MM.DD HH:mm')
      }, 1000)
    },
    datetimeRefresherDisable () {
      clearInterval(this.datetimeInterval)
    },
    extractLiveAddress (device) {
      device.liveAddress = ((device.params || []).find(item => item.code === 'dfdf' || item.code === 'spdz' || item.code === 'sjsp') || {}).value
      if ((device.liveAddress || '').match(/^rtmp:\/\/rtmp.open.ys7.com\/openlive\/.*(\.hd)?$/i)) {
        device.liveAddress = device.liveAddress.replace('rtmp://rtmp.', 'http://hls.').concat('.hd.m3u8')
      } else if ((device.liveAddress || '').match(/^http:\/\/(hls).open.ys7.com\/openlive\/.*(\.hd)?\.m3u8$/i)) {
        device.liveAddress = device.liveAddress
      } else {
        device.liveAddress = undefined
      }
      return device.liveAddress
    },
    async load () {
      try {
        this.loading = true
        let result
        result = await this.loadDevice()
        if (!result) throw new Error('加载设备信息失败')
        this.extractLiveAddress(result)
        result = await this.loadYs7AccessToken()
        if (!result) throw new Error('获取萤石云访问权限失败')
        result = await this.loadDeviceCameraList()
        if (!result) throw new Error('获取设备摄像头信息失败')
        result = await this.loadDeviceCameraLiveAddress()
        if (!result) throw new Error('获取设备摄像头播放地址失败')
        console.debug(this.liveAddress)
        this.playerOptions.sources[0].src = this.liveAddress
      } catch (e) {
        this.showMessageError(e.message)
        this.back(BACK_TIMEOUT)
      } finally {
        this.loading = false
      }
    },
    async loadDevice () {
      this.device = await this.queryDevice(this.$route.params._id)
      return this.device
    },
    async loadYs7AccessToken () {
      let requestUrl = this.ys7Server.concat('api/lapp/token/get')
      let response = await this.$http.post(requestUrl, undefined, {
        params: {
          appKey: this.ys7AppKey,
          appSecret: this.ys7AppSecret
        }
      })
      if (response.data.code !== '200') {
        console.debug(response.data)
        return
      }
      this.ys7AccessToken = response.data.data.accessToken
      console.debug(this.ys7AccessToken)
      return this.ys7AccessToken
    },
    async loadDeviceCameraList () {
      let requestUrl = this.ys7Server.concat('api/lapp/device/camera/list')
      let response = await this.$http.post(requestUrl, undefined, {
        params: {
          accessToken: this.ys7AccessToken,
          deviceSerial: this.device.code
        }
      })
      if (response.data.code !== '200') {
        console.debug(response.data)
        return
      }
      this.device.CameraList = this.parseCameraList(response.data.data).sort((item1, item2) => {
        return item1.channelNo - item2.channelNo
      })
      console.debug(this.device.CameraList)
      return this.device.CameraList
    },
    async loadDeviceCameraLiveAddress () {
      for (let camera of this.device.CameraList) {
        let requestUrl = this.ys7Server.concat('api/lapp/live/address/get')
        let response = await this.$http.post(requestUrl, undefined, {
          params: {
            accessToken: this.ys7AccessToken,
            source: this.device.code.concat(':').concat(camera.channelNo)
          }
        })
        if (response.data.code !== '200') {
          console.debug(response.data)
          return
        }
        camera.liveAddress = this.parseCameraLiveAddress(response.data.data)
        console.debug(camera)
      }
      return this.device.CameraList
    },
    async sendTurnCommand (direction, speed = 1) {
      await this.sendTurnStopCommand()
      await this.sendTurnStartCommand(direction, speed)
      await this.sendTurnStopCommand()
    },
    async sendTurnStartCommand (direction, speed) {
      let requestUrl = this.ys7Server.concat('/api/lapp/device/ptz/start')
      let response = await this.$http.post(requestUrl, undefined, {
        params: {
          accessToken: this.ys7AccessToken,
          deviceSerial: this.device.code,
          channelNo: this.defaultCamera.channelNo,
          direction: direction,
          speed: speed
        }
      })
      console.debug(response)
      if (response.data.code !== '200') {
        throw new Error(JSON.stringify(response.data))
      }
    },
    async sendTurnStopCommand () {
      let requestUrl = this.ys7Server.concat('api/lapp/device/ptz/stop')
      let response = await this.$http.post(requestUrl, undefined, {
        params: {
          accessToken: this.ys7AccessToken,
          deviceSerial: this.device.code,
          channelNo: this.defaultCamera.channelNo
        }
      })
      console.debug(response)
      if (response.data.code !== '200') {
        throw new Error(JSON.stringify(response.data))
      }
    },
    parseCameraList (data) {
      if (!Array.isArray(data)) {
        return []
      }
      return data.map(item => this.parseCamera(item))
    },
    parseCamera (data) {
      if (!(typeof data === 'object')) {
        return
      }
      let camera = {}
      camera.channelNo = data.channelNo
      camera.channelName = data.channelName
      camera.status = data.status
      if (camera.channelNo) {
        return camera
      }
    },
    parseCameraLiveAddress (data) {
      if (!(Array.isArray(data) && data.length > 0)) {
        return
      }
      return {
        hls: data[0].hls,
        hlsHd: data[0].hlsHd,
        rtmp: data[0].rtmp,
        rtmpHd: data[0].rtmpHd
      }
    }
  }
}
</script>

<style scoped>
  .page {
    z-index: 100;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: white;
  }
  .page-init {
    z-index: 101;
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    background-color: black;
  }
  .page-main {
    z-index: 101;
  }
  .page-main-player {
    z-index: 102;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .page-main-baffle {
    z-index: 103;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    color: white;
  }
  .page-main-console {
    z-index: 104;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    color: white;
    display: flex;
    flex-flow: column nowrap;
  }
  .page-main-console-head {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: stretch;
    align-content: center;
    height: 25%;
  }
  .page-main-console-head-left {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: flex-start;
    width: 30%;
  }
  .page-main-console-head-center {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: flex-start;
    align-content: flex-start;
    width: 40%;
  }
  .page-main-console-head-right {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    align-items: flex-start;
    align-content: flex-start;
    width: 30%;
  }
  .page-main-console-body {
    flex: auto;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: stretch;
    align-content: center;
    height: 50%;
  }
  .page-main-console-body-left {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: center;
    align-content: center;
    width: 30%;
  }
  .page-main-console-body-center {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
    align-content: center;
    width: 40%;
  }
  .page-main-console-body-right {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    align-items: center;
    align-content: center;
    width: 30%;
  }
  .page-main-console-foot {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: stretch;
    align-content: center;
    height: 25%;
  }
  .page-main-console-foot-left {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: flex-end;
    align-content: flex-end;
    width: 30%;
  }
  .page-main-console-foot-center {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: flex-end;
    align-content: flex-end;
    width: 40%;
  }
  .page-main-console-foot-right {
    flex: none;
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    align-items: flex-end;
    align-content: flex-end;
    width: 30%;
  }
  .page-main-console-btn-back {
    display: inline-block;
    margin: 10px;
    width: 48px;
    height: 48px;
    background: rgba(255,255,255,.25) url(../../../assets/iot/device/left.png) no-repeat center center;
    background-size: 24px 32px;
    border-radius: 100%;
  }
  .page-main-console-btn-play {
    flex: none;
    display: inline-block;
    width: 100px;
    height: 100px;
    background-size: 100px 100px;
    background: url(../../../assets/iot/device/video.png) no-repeat center center;;
  }
  .page-main-console-btn-stop {
    background-color: aqua;
  }
  .page-main-console-lbl-datetime {
    background-color: azure;
    margin: 10px;
    padding: 0px 5px;
    float: right;
    background-color: rgba(255,255,255,.25);
    border-radius: 8px;
  }
  .page-main-console-btn-up {
    width: 120px;
    height: 40px;
    background: rgba(34, 34, 34, .3) url(../../../assets/iot/device/up.png) no-repeat center center;
    border-radius: 0 0 8px 8px;
  }
  .page-main-console-btn-left {
    width: 40px;
    height: 120px;
    background: rgba(34, 34, 34, .3) url(../../../assets/iot/device/left.png) no-repeat center center;
    border-radius: 0 8px 8px 0;
  }
  .page-main-console-btn-right {
    width: 40px;
    height: 120px;
    background: rgba(34, 34, 34, .3) url(../../../assets/iot/device/right.png)  no-repeat center center;
    border-radius: 8px 0 0 8px;
  }
  .page-main-console-btn-down {
    width: 120px;
    height: 40px;
    background: rgba(34, 34, 34, .3) url(../../../assets/iot/device/down.png) no-repeat center center;
    border-radius: 8px 8px 0 0;
  }
</style>
