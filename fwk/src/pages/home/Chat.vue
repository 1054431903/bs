<template>
  <v-container class="chatWrapper" fluid>

    <!-- 搜索、提问 -->
    <v-card class="chatTool-wrapper">
      <v-layout class="toolWrapper">
        <v-flex xs5>
          <v-text-field
            placeholder="搜索您感兴趣的内容"
            v-model="keyword"
            append-icon="fa_search"
          >
          </v-text-field>
        </v-flex>
        <v-flex xs7 text-xs-right>
          <v-btn class="toolBtn btnSize" :to="{name: 'ask'}" color="primary">提问</v-btn>
        </v-flex>
      </v-layout>
    </v-card>


    <!-- list -->
    <v-layout row class="charList" v-for="(list,index) in chatList" :key="index" v-show="chatList">
      <v-flex xs9>
        <v-card class="chatCard">

          <!-- 标题 ok -->
          <v-layout row>
            <v-flex xs9>
            <div class="chatCaption overflowhidden"
                 @click="skipDetail(list.id)"
                 v-text="list.communioncontent"
                 :title="list.communioncontent"
            >
            </div>
            </v-flex>
            <v-flex xs3 text-xs-right v-text="list.creattime"></v-flex>
          </v-layout>

          <!-- 图片 高度问题 ? -->
          <v-layout row wrap class="imgWrapper">
            <v-flex xs3
                    v-for="(list,index) in list.imageResourcehistorylist"
                    :key="index"
            >
              <img :src="list.resourcepath" />
            </v-flex>
          </v-layout>

          <!-- 头像、地址 -->
          <v-layout row class="infoWrapper">
            <v-flex xs5>
              <v-avatar size="36px"><img :src="list.photourl" /></v-avatar>
              <span class="infoName" v-text="list.createuseridnickname"></span>
              <span class="infoAddress">(&nbsp;<span v-text="list.orgname">&nbsp;</span>&nbsp;)</span>
            </v-flex>
            <v-flex xs2 v-if="list.communioncategorylist.length > 0">
              <v-icon>local_offer</v-icon>
              <!-- 品类列表 -->
              <span v-if="list.communioncategorylist.length > 0">
                <span v-for="(list2, index2) in list.communioncategorylist">
                  <span v-text="list2.categoryname"></span>
                  <span v-if="(index2 + 1) !== list.communioncategorylist.length">、</span>
                </span>
              </span>
              <span v-else>- -</span>
            </v-flex>
            <v-flex xs3 text-xs-right v-if="list.parcelidnames">
              <v-icon>toys</v-icon>
              <!-- ?? -->
              <span v-text="list.parcelidnames">梁家河村果园</span>
            </v-flex>
            <v-flex xs3 text-xs-right v-if="list.location">
              <v-icon>room</v-icon>
              <span v-text="list.location"></span>
            </v-flex>
          </v-layout>

        </v-card>
      </v-flex>
    </v-layout>

    <v-layout row v-show="chatList.length === 0">
      <v-flex xs9 text-xs-center>
        <v-card class="noData">
          <span>暂无数据</span>
        </v-card>
      </v-flex>
    </v-layout>


    <v-layout>
      <v-flex xs12 class="pageMargin">
        <v-pagination v-model="pagination.page" :length="pagination.totalPage" circle color="teal"></v-pagination>
      </v-flex>
    </v-layout>

  </v-container>
</template>
<script>
  import debounce from 'throttle-debounce/debounce'
  import { excuteApis } from '@/apis'
  import FarmerMixin from '@/pages/farmworks/farmerMixin'
  export default {
    name: 'chat',
    data () {
      return {
        keyword: null, // 输入框
        chatList: [], // 问题列表
        pagination: {
          page: 1 // 当前页
        }
      }
    },
    mixins: [ FarmerMixin ],
    computed: {
      currentPage () {
        return this.pagination.page
      }
    },
    mounted () {
      this.getAsk()
    },
    methods: {
      /**
       * 跳转问题详情
       */
      skipDetail (id) {
        console.log('跳转问题详情')
        this.$router.push({name: 'chatDetail', query: {id: id}})
      },

      /**
       * 获取农事交流
       */
      getAsk (keyword, currentPage) {
        this.showLoad()
        let param = {
          keyword: keyword,
          currentPage: currentPage || 1,
          pageSize: 5
        }
        excuteApis(param, global.constant.farmApiUrls, 'communion', 'querylist').then(response => {
          console.log('获取农事交流success')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            if (response.data.page) {
              let page = response.data.page
              this.pagination.totalItems = page.total // 总条目数
              this.pagination.totalPage = page.totalPage // 总页数
              this.pagination.page = page.currentPage // 当前页
            }
            this.chatList = this.disposeData(response.data.data)
          }
        }).finally(() => {
          this.closeLoad()
        })
      },

      /**
       * 处理农事交流的数据(头像默认处理)
       * @param array array
       * @return new array
       */
      disposeData (array) {
        if (array.length > 0) {
          let newArray = []
          for (let list of array) {
            if (!list.photourl) list.photourl = 'static/peasant/moren.png'
            newArray.push(list)
          }
          return newArray
        }
        return []
      }
    },
    watch: {
      /**
       * 根据条件查询农事交流
       */
      keyword: debounce(600, function (keyword) {
        console.log('keyword = ' + keyword)
        this.getAsk(keyword)
      }),

      /**
       * 分页
       */
      currentPage (currentPage) {
        console.log('val = ' + currentPage)
        this.getAsk(this.keyword, currentPage)
      }
    }
  }
</script>
<style scoped>
  #caption{
    line-height: 24px;
    font-size: 16px;
  }
  .chatTool-wrapper{
    margin-bottom: 20px;
  }
  .toolBtn{
    margin-top: 12px;
  }
  .charList{
    margin-bottom: 20px;
  }
  .chatCard{
    padding: 20px;
  }
  .imgWrapper{
    margin-top: 15px;
  }
  .imgWrapper>.flex:not(:last-child){
    margin-right: 20px;
  }
  .imgWrapper img{
    width: 100%;
    height: 200px;
  }
  .infoName,.infoAddress{
    margin-left: 10px;
  }
  .infoWrapper{
    margin-top: 10px;
  }
  .infoWrapper>.flex{
    line-height: 30px;
  }
  .chatWrapper{
    margin-bottom: 50px;
    padding: 0 30px 0 30px;
  }
  .chatCaption{
    cursor: pointer;
  }
  .toolWrapper{
    padding: 20px;
  }
  .btnSize{
    width: 107px;
    color: #FFF;
  }
  .noData{
    padding-top: 20px;
    padding-bottom: 20px;
    margin-bottom: 30px;
  }
  .pageMargin{
    margin-left: -10px;
  }
  .overflowhidden{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 16px;
    font-weight: bold;
  }
</style>
