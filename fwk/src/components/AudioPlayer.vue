<template>
  <div>
    <audio ref="audio">
      <source :src="curAudioPath"
              type="audio/mp3">
      <source :src="curAudioPath"
              type="audio/mpeg">
      <source :src="curAudioPath"
              type="audio/ogg">
      <embed :src="curAudioPath"
             height="50"
             width="100">
    </audio>
    <v-layout row
              class="audiorowdistance"
              v-if="resourceList && resourceList.length > 0">
      <v-flex xs3
              v-for="(resource, index) in resourceList"
              :key="index"
              class="audioface"
              v-bind:style="curAudioRef === `audio${index}` ? audioActiveCss : audioNoActiveCss"
              :ref="`audio${index}`"
              @click="playAudio(index)">
        <v-layout row
                  justify-space-between>
          <v-flex xs2>
            <img v-if="!isPlaying || curAudioRef !== `audio${index}`"
                 height="10px"
                 width="10px"
                 :src="processTips" />
            <div class="wifi-symbol"
                 v-if="isPlaying && curAudioRef === `audio${index}`">
              <div class="wifi-circle first"></div>
              <div class="wifi-circle second"></div>
              <div class="wifi-circle third"></div>
            </div>
          </v-flex>
          <v-flex xs4
                  v-if="durationTime && curAudioRef === `audio${index}`">
            <span>{{durationTime}}<sup>'"</sup></span>
          </v-flex>
          <v-flex xs2
                  style="padding-right:0px"
                  v-if="isEdit">
            <v-icon @click.stop="edit(index)">cancel</v-icon>
          </v-flex>
        </v-layout>
        <!-- <span style="float: right;padding-right: 10px;">{{currentTime}}</span> -->
      </v-flex>
    </v-layout>
    <span v-if="!resourceList || resourceList.length === 0">- -</span>
  </div>
</template>

<script>
export default {
  name: 'audio-player',
  props: {
    resourceList: {
      type: Array,
      default: () => []
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      lastAudioRef: null,
      curAudioRef: null,
      curAudioPath: null,
      processTips: 'static/yuyin/yuyin.png',
      audioActiveCss: {
        backgroundImage: `url('static/yuyin/yuyinkuangdianji.png')`
      },
      audioNoActiveCss: {
        backgroundImage: `url('static/yuyin/yuyinkuang.png')`
      },
      isPlaying: false,
      currentTime: null,
      durationTime: null
    }
  },
  watch: {
    resourceList () {
      return this.resourceList
    }
  },
  methods: {
    edit (index) {
      if (index >= 0) this.resourceList.splice(index, 1)
      this.$emit('update:resourceList', this.resourceList)
    },
    playAudio (index) {
      this.isPlaying = !this.isPlaying
      this.curAudioRef = `audio${index}`
      this.curAudioPath = this.resourceList[index].resourcepath || this.resourceList[index].url
      this.$refs['audio'].src = this.curAudioPath
      this.isPlaying ? this.$refs['audio'].play() : this.$refs['audio'].pause()
      this.lastAudioRef = this.curAudioRef
    },
    addEventListeners () {
      const self = this
      self.$refs['audio'].addEventListener('timeupdate', self._currentTime)
      self.$refs['audio'].addEventListener('canplay', self._durationTime)
    },
    removeEventListeners () {
      const self = this
      self.$refs['audio'].removeEventListener('timeupdate', self._currentTime)
      self.$refs['audio'].removeEventListener('canplay', self._durationTime)
    },
    _currentTime () {
      const self = this
      self.currentTime = parseInt(self.$refs.audio.currentTime)
    },
    _durationTime () {
      const self = this
      self.durationTime = parseInt(self.$refs.audio.duration)
    }
  },
  mounted () {
    this.addEventListeners()
  },
  beforeDestroyed () {
    this.removeEventListeners()
  }
}
</script>

<style scoped>
.audioface {
  padding-left: 20px;
  padding-bottom: 5px;
  padding-top: 5px;
  padding-right: 5px;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  cursor: pointer;
}
.audiorowdistance {
  margin-bottom: 5px;
}
.wifi-symbol {
  margin-top: 6px;
  width: 9px;
  height: 9px;
  box-sizing: border-box;
  overflow: hidden;
  transform: rotate(135deg);
}
.wifi-circle {
  border: 2px solid #999999;
  border-radius: 50%;
  position: absolute;
}
.first {
  width: 2px;
  height: 2px;
  background: #cccccc;
  top: 7px;
  left: 7px;
}
.second {
  width: 10px;
  height: 10px;
  top: 3px;
  left: 3px;
  animation: fadeInOut 1s infinite 0.2s;
}
.third {
  width: 14px;
  height: 14px;
  top: 0px;
  left: 0px;
  animation: fadeInOut 1s infinite 0.4s;
}
@keyframes fadeInOut {
  0% {
    opacity: 0; /*初始状态 透明度为0*/
  }
  100% {
    opacity: 1; /*结尾状态 透明度为1*/
  }
}
</style>
