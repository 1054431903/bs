<template>
  <v-popup-window ref="window"
                  @cancel="cancel"
                  :show-header="showHeader"
                  :show-title="showTitle"
                  :show-cancel="showCancel"
                  :show-confirm="showConfirm"
                  :show-separator="showSeparator"
                  :text-title="textTitle"
                  :text-confirm="textConfirm"
                  :text-cancel="textCancel"
                  :color-title="colorTitle"
                  :color-confirm="colorConfirm"
                  :color-cancel="colorCancel">
    <v-area-selector v-if="display" :data="data" :formatter="formatter" @confirm="confirm"></v-area-selector>
  </v-popup-window>
</template>

<script>
import VPopupWindow from '@/components/PopupWindow'
import VAreaSelector from '@/components/selector/AreaSelector'

const TEXT_TITLE = '请选择地址'
const TEXT_CONFIRM = '确定'
const TEXT_CANCEL = '取消'

const COLOR_TITLE = '#000000'
const COLOR_CONFIRM = 'rgb(77, 188, 209)'
const COLOR_CANCEL = '#999999'

const EVENT_SHOW = 'show'
const EVENT_HIDE = 'hide'

const EVENT_CONFIRM = 'confirm'
const EVENT_CANCEL = 'cancel'

export default {
  name: 'v-area-picker',
  components: {
    VPopupWindow,
    VAreaSelector
  },
  props: {
    formatter: {
      type: Function
    },
    data: {
      type: Array,
      default () {
        return []
      }
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
      default: false
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
      this.$refs.window.show()
      this.$emit(EVENT_SHOW)
    },
    hide () {
      this.display = false
      this.$refs.window.hide()
      this.$emit(EVENT_HIDE)
    },
    confirm (data) {
      this.display = false
      this.$refs.window.submit()
      this.$emit(EVENT_CONFIRM, data)
    },
    cancel () {
      this.display = false
      this.$emit(EVENT_CANCEL)
    }
  }
}
</script>

<style>

</style>
