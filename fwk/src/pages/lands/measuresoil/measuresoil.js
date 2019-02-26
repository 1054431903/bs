// import { Toast } from '@/models/toast'
// import { excuteApis } from '@/apis'
export default {
  props: {
    parcelid: {
      type: Number,
      require: true,
      default: 0
    },
    listDialog: {
      type: Boolean,
      require: true,
      default: false
    }
  },
  data () {
    return {
      toast: null,
      theme: 'primary',
      isAddOrEditDialog: false,
      addOrEditDialogTitle: '',
      isListDialog: false,
      isViewDialog: false,
      addFormValid: false
    }
  },
  watch: {
    parcelid (v) {
      console.log(v)
    }
  },
  methods: {},
  created () {
    // this.toast = new Toast(this, null, null)
    this.theme = global.helper.ls.get('theme')
  }
}

