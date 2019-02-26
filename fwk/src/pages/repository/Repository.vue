
<template>
  <div>
    <v-card class="page-content"
            v-if="articles.length > 0">
      <v-layout row
                align-baseline>

        <!-- 搜索框 -->
        <v-flex xs4>
          <v-combobox placeholder="请输入关键字"
                      v-model="keyWord"
                      clearable
                      :items="items"
                      append-icon="search"
                      no-filter
                      :search-input.sync="searchKey">
          </v-combobox>
        </v-flex>
        <v-btn type="submit"
               small
               color="primary"
               class="ml-3 btn--search"
               @click="search">查询</v-btn>
      </v-layout>
      <div v-if="articles.length > 0 && searchArticles.length === 0 && isSearch">
        <v-layout row
                  v-for="(article, index) in articles"
                  :key="index">
          <v-flex xs12
                  class="v-card-repository">
            <v-card class="v-card-repository-new">
              <v-card-title class="v-card__title">
                <div class="repository-title"
                     @click="clickSkip(article.contentId)"
                     v-html="article.title"></div>
                <div>
                  <span class="grey--text"
                        v-if="article.channelName ? article.channelName.indexOf('其他') === -1 : false"
                        v-html="article.channelName"></span>
                  <span class="grey--text"
                        v-if="article.category ? article.category.indexOf('其他') === -1 : false"
                        v-html="article.category"></span>
                  <span class="grey--text">{{ article.releaseDate }}</span>
                </div>
              </v-card-title>
              <v-card-text class="repository-content v-card__text"
                           v-html="article.description"
                           py-2>
              </v-card-text>
              <v-container fluid
                           grid-list-sm
                           py-2
                           style="padding-left:20px">
                <v-layout>
                  <v-flex xs6
                          sm3
                          pb-2>
                    <v-img :src="article.typeImg"
                           v-if="article.typeImg"
                           aspect-ratio="1.7"></v-img>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-card>
          </v-flex>
        </v-layout>
        <!-- 点击加载更多 -->
        <v-layout mt-3>
          <v-flex xs12
                  text-xs-center
                  v-if="nomore">
            <v-btn outline
                   color="secondary">没有更多了</v-btn>
          </v-flex>
          <v-flex xs12
                  text-xs-center
                  v-else>
            <v-btn outline
                   color="primary"
                   @click="moreReponsitory">点击加载更多</v-btn>
          </v-flex>
        </v-layout>
      </div>
      <div v-else-if="articles.length > 0 && searchArticles.length > 0 && !isSearch">
        <!-- 共搜到结果几条 -->
        <v-layout row
                  py-3>
          <v-flex xs12>
            <span>为您搜索到&nbsp;:&nbsp;</span>
            <span style="color:#32c787">{{total}}</span>
            <span>篇相关知识</span>
          </v-flex>
        </v-layout>
        <v-layout row
                  v-for="(article, index) in searchArticles"
                  :key="index">
          <v-flex xs12
                  class="v-card-repository">
            <v-card class="v-card-repository-new">
              <v-card-title class="v-card__title">
                <div class="repository-title"
                     @click="clickSkip(article.contentId)"
                     v-html="article.title"></div>
                <div>
                  <span class="grey--text"
                        v-if="article.channelName ? article.channelName.indexOf('其他') === -1 : false"
                        v-html="article.channelName"></span>
                  <span class="grey--text"
                        v-if="article.category ? article.category.indexOf('其他') === -1 : false"
                        v-html="article.category"></span>
                  <span class="grey--text">{{ article.releaseDate }}</span>
                </div>
              </v-card-title>
              <v-card-text v-html="article.description"
                           class="repository-content v-card__text"
                           py-2>
              </v-card-text>
              <v-container fluid
                           grid-list-sm
                           pa-3>
                <v-layout>
                  <v-flex xs6
                          sm3>
                    <v-img :src="article.typeImg"
                           v-if="article.typeImg"
                           contain></v-img>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-card>
          </v-flex>
        </v-layout>
        <!-- 点击加载更多 -->
        <v-layout mt-3>
          <v-flex xs12
                  text-xs-center
                  v-if="nomore">
            <v-btn outline
                   color="secondary">没有更多了</v-btn>
          </v-flex>
          <v-flex xs12
                  text-xs-center
                  v-else>
            <v-btn outline
                   color="primary"
                   @click="moreReponsitory">点击加载更多</v-btn>
          </v-flex>
        </v-layout>
      </div>
      <v-card height="668px"
              v-else
              style="box-shadow: none">
        <v-container fluid
                     grid-list-lg
                     fill-height>
          <v-flex xs12>
            <v-img src="../../../static/no_data.png"
                   height="200px"
                   contain></v-img>
            <v-layout justify-center
                      pt-3
                      row
                      wrap>
              <v-flex xs12
                      class="fontStyle text-xs-center">很抱歉,未找到与“{{keyWord}}”相关的结果</v-flex>
              <v-subheader style="font-size:16px;height:30px">请修改或者尝试其他搜索词</v-subheader>
            </v-layout>
          </v-flex>
        </v-container>
      </v-card>

    </v-card>
    <v-card class="page-content"
            height="668px"
            v-else>
      <v-container fluid
                   grid-list-lg
                   fill-height>
        <v-flex xs12>
          <v-img src="../../../static/no_data.png"
                 height="125px"
                 contain></v-img>
          <v-layout justify-center
                    pt-3>
            <v-subheader class="fontStyle">暂无文章数据！</v-subheader>
            <!-- <span >暂无文章数据！</span> -->
          </v-layout>
        </v-flex>
      </v-container>
    </v-card>
    <repository-detail-dialog :show.sync="dialog"
                              :repository_id="repositoryId"></repository-detail-dialog>
  </div>
</template>

<script>
import throttle from 'throttle-debounce/throttle'
import { excuteApis } from '@/apis'
import FarmerMixin from '@/pages/farmworks/farmerMixin'
import RepositoryDetailDialog from './RepositoryDetail.vue'
import moment from 'moment'
export default {
  name: 'repository',
  mixins: [FarmerMixin],
  data () {
    return {
      dialog: false,
      repositoryId: null,
      articles: [], // 文章列表
      searchArticles: [], // 文章列表
      keyWord: null, // 输入框
      page: {
        curPage: 1,
        pageSize: 10
      }, // 自定义page信息
      total: 0,
      items: [],
      searchKey: '',
      nomore: false,
      isSearch: true
    }
  },
  mounted () {
    this.getArticle()
  },
  watch: {
    searchKey (val) {
      if (val) {
        let param = {
          prefix: val || ''
        }
        excuteApis(param, global.constant.repositoryApis, 'rent', 'queryCorrection').then(response => {
          let data = response.data.data
          if (data) {
            this.items = data
          } else {
            this.items = []
          }
        })
      } else {
        this.searchArticles = []
        this.getArticle()
      }
    }
  },
  methods: {
    /**
     * 跳转
     */
    clickSkip (id) {
      this.dialog = true
      this.repositoryId = id
    },

    /**
     * 获取知识库文章列表
     * @param keyWord 关键字
     * @param pageNumber 页数
     * @param more Boolean 加载更多
     */
    getArticle (keyWord, more) {
      this.isSearch = true
      this.showLoad()
      if (!more) {
        this.page.curPage = 1
      }
      let param = {
        curPage: this.page.curPage,
        pageSize: this.page.pageSize
      }
      excuteApis(param, global.constant.repositoryApis, 'rent', 'queryArticle').then(response => {
        if (response.data && response.data.data) {
          if (response.data.data.result) {
            if (response.data.data.result.length < this.page.pageSize) {
              this.nomore = true
            } else {
              this.nomore = false
            }
            if (more) { // 加载更多
              this.articles = [...this.articles, ...this.disposeData(response.data.data.result)]
            } else {
              this.articles = this.disposeData(response.data.data.result)
            }
          } else {
            if (more) {  // 加载更多
              this.articles = [...this.articles, ...this.disposeData(response.data.data)]
            } else {
              this.articles = this.disposeData(response.data.data)
            }
          }
        }
      }).finally(() => {
        this.closeLoad()
      })
    },

    /**
     * 加载更多
     */
    moreReponsitory: throttle(600, true, function () {
      ++this.page.curPage
      if (this.keyWord) {
        this.search(this.keyWord, true)
      } else {
        this.getArticle(this.keyWord, true)
      }
    }),

    /**
     * 处理响应数据(转换时间格式)
     * @param data 要处理的数据
     */
    disposeData (data) {
      this.page.total = data.length // 总条目数
      if (data && data.length > 0) {
        let newData = []
        for (let i = 0; i < data.length; ++i) {
          let list = data[i]
          // 图片处理
          let rootUrl = 'http://220.164.82.48' // 图片根路径 ??
          // 图片处理
          if (list.typeImg) {
            list.typeImg = this.replaceImagePath(list.typeImg, rootUrl) // 图片路径替换
          }
          // release_date转时间
          if (list.releaseDate) {
            list.releaseDate = moment(list.releaseDate).format('YYYY年MM月DD日')
          } else {
            list.releaseDate = '- -'
          }
          newData.push(list)
        }
        return newData
      }
      return []
    },
    /**
     * 图片路径处理
     * @param fromStr 要替换的字符串
     * @param rootUrl 图片根路径
     * @return 替换以后的字符串
     */
    replaceImagePath (fromStr, rootUrl) {
      return rootUrl + fromStr
    },
    search (keyWord, more) {
      if (!more) {
        this.page.curPage = 1
      }
      let param = {
        keywords: this.keyWord || '',
        curPage: this.page.curPage,
        pageSize: this.page.pageSize
      }
      excuteApis(param, global.constant.repositoryApis, 'rent', 'queryArticleBykeycord').then(response => {
        if (response.data && response.data.data.result) {
          this.total = response.data.data.total
          if (response.data.data.result.length < this.page.pageSize) {
            this.nomore = true
          } else {
            this.nomore = false
          }
          if (more) { // 加载更多
            this.searchArticles = [...this.searchArticles, ...this.disposeData(response.data.data.result)]
          } else {
            this.searchArticles = this.disposeData(response.data.data.result)
          }
        } else {
          if (more) {  // 加载更多
            this.searchArticles = [...this.searchArticles, ...this.disposeData(response.data.data)]
          } else {
            this.searchArticles = this.disposeData(response.data.data)
          }
        }
        console.log(this.searchArticles.length)
        this.isSearch = false
      }).finally(() => {
        this.closeLoad()
      })
    }
  },
  components: {
    RepositoryDetailDialog
  }
}
</script>

<style scoped>
.repository-title {
  font-size: 18px;
  margin-bottom: 10px;
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.repository-title:hover {
  color: #32c787;
  cursor: pointer;
}
.repository-content {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.fontStyle {
  font-size: 18px;
}

.v-card-repository {
  border: 1px solid #e1e1e1;
  margin-bottom: 1.42rem;
}
.v-card-repository-new {
  box-shadow: none;
}
.v-card-repository-new:hover {
  background-color: #f3f3f3;
}
.v-card__title {
  padding: 20px 20px 12px;
}
.v-card__text {
  padding: 0 20px 0;
}
</style>
