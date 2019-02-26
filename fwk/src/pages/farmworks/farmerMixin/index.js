
/**
 * 农事一些通用方法
 */
import { excuteApis } from '@/apis'
import { Toast } from '@/models/toast' // 菊花
export default {
  created () {
    this.toast = new Toast(this, null, null) // new 菊花
  },
  methods: {
    /**
     * 获取地块
     */
    getLand () {
      excuteApis({}, global.constant.parcelApis, 'parcel', 'query').then(data => {
        console.log('获取地块成功------')
        console.log(data)
        if (data.data && data.data.flag === 1 && data.data.data) {
          this.recordparcellistArr = data.data.data
        }
      })
    },

    /**
     * 显示菊花
     */
    showLoad () {
      this.toast.show()
    },

    /**
     * 关闭菊花
     */
    closeLoad () {
      this.toast.close()
    }
  }
}
