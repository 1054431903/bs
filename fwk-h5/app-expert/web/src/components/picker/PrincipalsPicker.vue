<template>
  <v-popup-window ref="window"
                  @cancel="cancel"
                  @confirm="confirm"
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
    <v-principals-selector ref="selector" v-if="display" :formatter="formatter"></v-principals-selector>
  </v-popup-window>
</template>

<script>
import VPopupWindow from '@/components/PopupWindow'
import VPrincipalsSelector from '@/components/selector/PrincipalsSelector'

const TEXT_TITLE = ''
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
  name: 'v-principals-picker',
  components: {
    VPopupWindow,
    VPrincipalsSelector
  },
  props: {
    formatter: {
      type: Function
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
  computed: {
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
    confirm () {
      this.display = false
      this.$emit(EVENT_CONFIRM, this.$refs.selector.items)
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
