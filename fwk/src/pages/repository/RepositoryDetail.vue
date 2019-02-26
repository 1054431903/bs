<template>
  <v-dialog :value="show"
            @input="close"
            scrollable
            max-width="1000px">
    <v-card v-if="show">
      <v-card-title>
        <v-flex xs1></v-flex>
        <v-flex xs10
                class="text-xs-center">
          <div class="headline repositoryDel-title"
               v-text="articleDetail.title"></div>
          <span class="grey--text">{{ articleDetail.channelName === '其他' ? '': articleDetail.channelName}}</span>
          <span class="grey--text">{{ articleDetail.category === '其他' ? '': articleDetail.category}}</span>
          <span class="grey--text">{{ articleDetail.releaseDate }}</span>
        </v-flex>
        <v-flex xs1
                class="text-xs-right">
          <v-icon size="30"
                  @click="close">close</v-icon>
        </v-flex>
      </v-card-title>
      <v-card-text style="padding-left:40px;padding-right:40px">
        <div v-html="articleDetail.description"
             style="color:#b0bec5;margin-bottom:20px">
        </div>
        <v-divider></v-divider>
        <div v-html="articleDetail.txt"
             style="margin-top:20px">
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { excuteApis } from '@/apis'
import moment from 'moment'
export default {
  name: 'repository-detail-dialog',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    repository_id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      articleDetail: {} // 文章详情
    }
  },
  watch: {
    show: function (newQuestion, oldQuestion) {
      this.getArticleDetail()
    }
  },
  methods: {
    /**
     * 获取文章详情
     * @param contentId 文章id
     * @param userId 用户id
     */
    async getArticleDetail (contentId, userId) {
      let param = {
        contentId: this.repository_id
      }
      await excuteApis(param, global.constant.repositoryApis, 'rent', 'queryArticleDetail').then(response => {
        if (response.data && response.data.data.result) {
          this.articleDetail = this.disposeData(response.data.data.result) // 处理响应数据
        }
      })
    },

    /**
     * 对响应的数据进行处理
     */
    disposeData (data) {
      if (data) {
        // 图片处理
        let rootUrl = 'http://220.164.82.48' // 图片根路径 ??
        if (data.txt) {
          data.txt = this.replaceImagePath(data.txt, rootUrl) // 图片路径替换
        }
        // 时间处理
        if (data.releaseDate) {
          data.releaseDate = moment(data.releaseDate).format('YYYY年MM月DD日')
          return data
        }
      }
      return {}
    },

    /**
     * 图片路径处理
     * @param fromStr 要替换的字符串
     * @param rootUrl 图片根路径
     * @return 替换以后的字符串
     */
    replaceImagePath (fromStr, rootUrl) {
      return fromStr.replace(/src="\/sgcms/g, 'src="' + rootUrl + '/sgcms').replace(/src="\/resources\/sgsq_cms/g, 'src="' + rootUrl + '/resources/sgsq_cms').replace(/href="\/resources\/sgsq_cms/g, 'href="' + rootUrl + '/resources/sgsq_cms')
    },
    /**
     * 关闭弹窗
     */
    close () {
      this.$emit('update:show', false)
    }
  }
}
</script>
<style scoped>
.repositoryDel-title {
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>

