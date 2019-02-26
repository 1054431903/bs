<template>
  <v-dialog fullscreen scrollable hide-overlay transition="dialog-bottom-transition" v-model="display">
    <div class="mask" @click="hide()"></div>
    <div class="window" v-show="display" :class="{'fullscreen':fullscreen}">
      <div class="window-header" v-if="showHeader">
        <slot name="header">
          <div class="btn-cancel" :style="{ color: colorCancel }">
            <span v-if="showCancel" @click="cancel">{{textCancel}}</span>
          </div>
          <div class="txt-title" :style="{ color: colorTitle }">
            <span v-if="showTitle">{{textTitle}}</span>
          </div>
          <div class="btn-submit" :style="{ color: colorConfirm }">
            <span v-if="showConfirm" @click="submit">{{textConfirm}}</span>
          </div>
        </slot>
      </div>
      <div class="separator" v-if="showSeparator"></div>
      <div class="window-panel">
        <div class="window-panel-content">
          <slot></slot>
        </div>
      </div>
    </div>
  </v-dialog>
</template>

<script>
const TEXT_TITLE = ''
const TEXT_CONFIRM = '确定'
const TEXT_CANCEL = '取消'

const COLOR_TITLE = '#000000'
const COLOR_CONFIRM = 'rgb(77, 188, 209)'
const COLOR_CANCEL = '#999999'

const EVENT_CONFIRM = 'confirm'
const EVENT_CANCEL = 'cancel'

const EVENT_SHOW = 'show'
const EVENT_HIDE = 'hide'
export default {
  name: 'v-popup-window',
  props: {
    fullscreen: {
      type: Boolean,
      default: true
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    showTitle: {
      type: Boolean,
      default: true
    },
    showCancel: {
      type: Boolean,
      default: true
    },
    showConfirm: {
      type: Boolean,
      default: true
    },
    showSeparator: {
      type: Boolean,
      default: true
    },
    textTitle: {
      type: String,
      default: TEXT_TITLE
    },
    textConfirm: {
      type: String,
      default: TEXT_CONFIRM
    },
    textCancel: {
      type: String,
      default: TEXT_CANCEL
    },
    colorTitle: {
      type: String,
      default: COLOR_TITLE
    },
    colorConfirm: {
      type: String,
      default: COLOR_CONFIRM
    },
    colorCancel: {
      type: String,
      default: COLOR_CANCEL
    }
  },
  data () {
    return {
      display: false
    }
  },
  methods: {
    show () {
      this.display = true
      this.$emit(EVENT_SHOW)
    },
    hide () {
      this.display = false
      this.$emit(EVENT_HIDE)
    },
    submit () {
      this.display = false
      this.$emit(EVENT_CONFIRM)
    },
    cancel () {
      this.display = false
      this.$emit(EVENT_CANCEL)
    }
  }
}
</script>

<style lang="scss" scoped>
  /* fade */
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

  .fade-enter-active, .fade-leave-active {
    transition: all .3s ease;
  }

  /* slide */
  .slide-enter, .slide-leave-to {
    opacity: 0.5;
    transform: translate3d(0, 100%, 0)
  }

  .slide-enter-active, .slide-leave-active {
    transition: all .3s ease;
  }

  .fullscreen {
    height: 100%;
  }

  .mask {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 9999;
    background: rgba(0, 0, 0, .2);
  }

  .window {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    z-index: 10000;
    background: #fff;
    border-radius: 0px;
    display: flex;
    flex-flow: column nowrap;
    justify-content: flex-start;
    align-content: stretch;
    align-items: stretch;
  }

  .window-header {
    flex: none;
    position: relative;
    height: 44px;
    line-height: 44px;
    font-size: 14px;
    color: #333;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-content: stretch;
    align-items: stretch;

    div {
      display: inline-block;
      text-align: center;
      vertical-align: center;
    }

    .txt-title {
      font-size: 16px;
      flex: auto;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .btn-submit {
      flex: none;
      width: 60px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .btn-cancel {
      flex: none;
      width: 60px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

  .window-panel {
    flex: auto;
    position: relative;
    overflow: hidden;
    display: flex;
    flex-flow: column nowrap;
    justify-content: flex-start;
    align-content: stretch;
    align-items: stretch;

    .window-panel-content {
      flex: auto;
      overflow: scroll;
    }
  }
  .separator {
    flex: none;
    width:100%;
    height:1px;
    border-bottom: 1px solid #ebebeb;
    transform: scaleY(.5);
  }
</style>
