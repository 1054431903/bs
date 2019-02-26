<template>
  <v-container fluid class="containerWrapper">

    <v-layout row class="main">

      <!-- 左边内容 -->
      <v-flex xs9 class="mainLeft">
        <v-card>
          <!-- 标题 -->
          <v-layout row>
            <v-flex><div class="chatDetai-caption" v-text="askDetail.communioncontent"></div></v-flex>
          </v-layout>

          <!-- 图片 -->
          <v-layout row wrap class="pictureWrapper">
            <v-flex xs12
                    v-for="(list, index) in askDetail.resourcehistorylist"
                    :key="index"
                    text-xs-center
            >
              <img :src="list.resourcepath" />
            </v-flex>
          </v-layout>

          <!-- 信息 -->
          <v-layout row class="infoWrapper">
            <v-flex xs3>
              <v-icon>room</v-icon>
              <span v-text="askDetail.location"></span>
            </v-flex>
            <v-flex xs3 v-if="askDetail.parcelidnames">
              <v-icon>edit</v-icon>
              <span v-text="askDetail.parcelidnames"></span>
            </v-flex>
            <v-flex xs3 v-if="askDetail.categorynames">
              <v-icon>local_offer</v-icon>
              <span v-text="askDetail.categorynames"></span>
            </v-flex>
            <v-flex xs3>
              <span v-text="askDetail.creattime"></span>
            </v-flex>
          </v-layout>
        </v-card>

        <!-- 评论 -->
        <v-card>

          <v-layout row>
            <v-flex xs12 class="chatComment-wrapper">

              <!-- 输入框 -->
              <v-layout row>
                <v-flex xs12>
                  <v-layout row>
                    <v-flex xs12>
                      <v-text-field placeholder="请输入评论内容(120字以内)"
                                    v-model="commentcontent"
                                    textarea
                                    maxlength="120"
                      >
                      </v-text-field>
                    </v-flex>
                  </v-layout>

                  <v-layout row>
                    <v-flex xs12 text-xs-right>
                      <v-btn @click="submitComment(askDetail.id)"
                             color="primary"
                             class="btnColor"
                      >
                        发送
                      </v-btn>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>

              <!-- 标题 -->
              <v-layout row>
                <v-flex xs12>
                  <h3>评论&nbsp;(&nbsp;<span v-text="askDetail.commentcount"></span>&nbsp;)</h3>
                </v-flex>
              </v-layout>

              <!-- list -->
              <v-layout row wrap v-for="(list, index) in commentList" :key="index" class="commentList">

                <!-- 头像 和 名字等 -->
                <v-flex xs12 class="photoWrapper">
                  <!-- 头像 -->
                  <div>
                    <v-avatar size="52px"><img :src="list.photourl || 'static/peasant/moren.png'" /></v-avatar>
                  </div>
                  <!-- 名字、vip标识 -->
                  <div class="photoRight">
                    <div>
                      <span v-if="askDetail.createuserid === list.commentuserid" v-text="'楼主'"></span>
                      <span v-else v-text="list.commentuseridname"></span>
                      <v-icon v-if="false">delete</v-icon><!-- vip标识 -->
                      <span v-text="list.creattime" class="commentTime"></span>
                    </div>
                    <div v-if="false">三七专家</div><!-- vip标识 -->
                  </div>
                </v-flex>

                <!-- 评论内容 -->
                <v-flex xs12 class="commentContent" v-text="list.commentcontent">这是我的评论内容</v-flex>

                <!-- 回复、评论 Text -->
                <v-flex xs6 text-xs-left class="replyText" @click="openInput(list, 1)">回复</v-flex>
                <v-flex xs6 class="commentText">
                  <span @click="clickComment(list)">
                    评论&nbsp;(&nbsp;
                    <span v-text="list.commentreversioncount"></span>
                    &nbsp;)
                  </span>
                  <span class="deleteComment" @click="deleteCommentContent(list.id)" v-if="userid === list.commentuserid">删除</span>
                </v-flex>

                <!-- 回复框 -->
                <v-flex xs12 v-if="list.id === currentComment.id">
                  <v-layout row>
                    <v-flex xs6 wrap class="commentInput">
                      <v-text-field label="写下您的回复" v-model="reversionContent" single-line></v-text-field>
                    </v-flex>
                    <v-flex xs1>
                      <v-btn class="submitComment" color="primary" @click="replyComment(list)">回复</v-btn>
                    </v-flex>
                  </v-layout>
                </v-flex>

                <!-- 回复 评论的回复 wrapper -->
                <v-layout row wrap class="replyToReply-wrapper" v-if="list.id===clickCommentId" v-for="(replyList, index) in commentToReplyList" :key="index">

                  <!-- 头像 和 名字等 -->
                  <v-flex xs12 class="photoWrapper">
                    <!-- 头像 -->
                    <div>
                      <v-avatar size="52px" style="border:1px solid #CCC;"><img :src="replyList.photourl || 'static/peasant/moren.png'" /></v-avatar>
                    </div>
                    <!-- 名字、vip标识 -->
                    <div class="photoRight">
                      <div>
                        <span v-if="askDetail.createuserid === replyList.reversionuserid" v-text="'楼主'"></span>
                        <span v-else v-text="replyList.reversionuseridname"></span>
                        <v-icon v-if="false">delete</v-icon>
                        <span v-text="replyList.creattime" class="commentTime"></span>
                      </div>
                      <div v-if="false">三七专家</div>
                      <div></div>
                    </div>
                  </v-flex>

                  <!-- 回复内容 -->
                  <v-flex xs9 class="commentContent">
                    <!-- 谁回复谁 -->
                    <span v-if="replyList.parentid">
                      <span v-if="askDetail.createuserid === replyList.reversionuserid" v-text="'楼主'"></span>
                      <span v-else v-text="replyList.reversionuseridname"></span>
                      <span>回复</span>
                      <span v-if="askDetail.createuserid === replyList.toreversionuserid" v-text="'楼主'"></span>
                      <span v-else v-text="replyList.toreversionuseridname"></span>&nbsp;:&nbsp;
                    </span>
                    <span v-text="replyList.reversioncontent"></span>
                  </v-flex>

                  <!-- 回复、评论 Text -->
                  <v-flex xs6 text-xs-left class="replyText" @click="openInput(replyList, 2)">回复</v-flex>
                  <v-flex xs6 class="commentText"><span @click="deleteReversionContent(replyList)" v-if="userid === replyList.reversionuserid">删除</span></v-flex>

                  <!-- 回复框 ??? -->
                  <v-layout row wrap v-if="replyList.id === currentReplyComment.id">
                    <v-flex xs6 class="commentInput">
                      <v-text-field label="写下您的回复" v-model="reversionContentToReply" single-line></v-text-field>
                    </v-flex>
                    <v-flex xs1>
                      <v-btn class="submitComment" color="primary" @click="replyCommentToReversion(replyList)">回复</v-btn>
                    </v-flex>
                  </v-layout>
                </v-layout>

                <!--
                    查看更多回复
                    显示条件: 回复数大于10条时显示
                    ??
                -->
                <v-flex xs12 text-xs-center v-if="false">
                  <div class="moreComment" @click="clickMoreReply()">查看更多回复</div>
                </v-flex>

              </v-layout>

              <!--
                  查看更多评论
                  显示条件: 评论数大于指定数目时显示
                  ???
              -->
              <!--<v-layout row wrap v-if="true">
                <v-flex xs12 text-xs-center>
                  <div class="moreComment" @click="clickMoreComment()">查看更多评论</div>
                </v-flex>
              </v-layout>-->

            </v-flex>
          </v-layout>

        </v-card>

      </v-flex>


      <!-- 右边头像 -->
      <v-flex xs3 text-xs-center>
        <v-card style="height:100%;padding-top:30px;">
          <div>
            <v-avatar class="rightPhoto" size="100px"><img :src="askDetail.photourl || 'static/peasant/moren.png'" /></v-avatar>
            <div v-text="askDetail.createuseridname" class="createuseridname"></div>
            <div v-text="askDetail.orgname || '暂无'" class="orgname"></div>
          </div>
        </v-card>
      </v-flex>
    </v-layout>

    <!-- 消息弹出框 -->
    <v-dialog v-model="dialogMessage" width="500">
      <v-card>
        <v-card-text>
          <v-layout row wrap text-xs-center>
            <v-flex xs12 v-text="messageResponse"></v-flex>
          </v-layout>
        </v-card-text>

        <v-card-actions class="btnPadding">
          <v-layout row wrap text-xs-center>
            <v-flex xs12>
              <v-btn @click="dialogMessage = false" color="primary" class="btnSize">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>
<script>
  import { excuteApis } from '@/apis'
  import throttle from 'throttle-debounce/throttle'
  import FarmerMixin from '@/pages/farmworks/farmerMixin'
  export default {
    name: 'chatDetail',
    mixins: [ FarmerMixin ],
    data () {
      return {
        dialogMessage: false,
        messageResponse: null,
        askDetail: {}, // 交流详情
        commentcontent: null, // 评论内容
        currentComment: {}, // 当前正在回复的评论
        currentReplyComment: {}, // 当前正在回复的评论
        currentReply: {}, // 当前正在回复 评论的回复
        currentCommentToReply: {}, // 当前展开的回复的评论详情
        commentList: [],
        reversionContent: null, // 回复的内容
        reversionContentToReply: null, // 回复评论的回复内容
        commentToReplyList: [], // 评论的回复
        clickCommentId: null // 点击评论, 赋值给它
      }
    },
    computed: {
      /**
       * userid
       */
      userid () {
        return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
      }
    },
    mounted () {
      this.getDetail()
      this.getServiceData()
    },
    methods: {
      /**
       * 获取农事交流详情
       */
      getDetail () {
        this.showLoad()
        excuteApis({ id: this.$route.query.id }, global.constant.farmApiUrls, 'communion', 'querybyid').then(response => {
          console.log('获取详情success')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.askDetail = response.data.data
          }
        }).finally(() => {
          this.closeLoad()
        })
      },

      /**
       * 新增评论 commentcontent
       */
      submitComment (communionid) {
        if (!this.commentcontent) {
          this.openMessage('请输入评论内容')
          return
        }

        let param = {
          communionid: communionid, // id
          commentcontent: this.commentcontent // 评论内容
        }
        excuteApis(param, global.constant.farmApiUrls, 'communion', 'commentcontentadd').then(response => {
          console.log(response)
          if (response.data.flag === 1) {
            this.getDetail() // 详情
            this.getComment(this.$route.query.id) // 刷新数据
            this.commentcontent = '' // 清空文本框
          } else {
            // 评论失败
          }
        })
      },

      /**
       * 消息弹出框内容设置
       */
      openMessage (content) {
        this.messageResponse = content
        this.dialogMessage = true
      },

      /**
       * 获取农事交流评论
       * @param communionid id
       */
      getComment (communionid) {
        excuteApis({ communionid: communionid, currentPage: 1, pageSize: 1, delstate: 1 }, global.constant.farmApiUrls, 'communion', 'querylistbycommunionid').then(response => {
          console.log('获取农事交流评论成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.commentList = response.data.data
          }
        })
      },

      /**
       * 点击 评论 获取 评论回复
       */
      clickComment (comment) {
        console.log(comment)
        this.getCommentToReply(comment.id)
      },

      /**
       * 点击展开回复框
       * number 1(回复评论) 2(回复评论的回复)
       */
      openInput (comment, number) {
        if (number === 1) {
          this.currentComment = comment
          this.currentReplyComment = {}
        } else {
          this.currentReplyComment = comment
          this.currentComment = {}
        }
      },

      /**
       * 回复评论
       */
      replyComment (item) {
        console.log('item')
        console.log(item)
        let param = {
          communionid: item.communionid, // 农事交流的id
          communioncommentid: item.id, // 农事交流评论的id
          reversioncontent: this.reversionContent // 回复的内容
        }
        excuteApis(param, global.constant.farmApiUrls, 'communion', 'commentreversionadd').then(response => {
          console.log(response)
          if (response.data.flag === 1) {
            this.getComment(this.$route.query.id) // 更新评论
            this.getCommentToReply(item.id) // 更新评论的回复
            this.reversionContent = null // 清空输入框
            this.currentComment = {} // 关闭输入框
          }
        }).catch((response) => {
          console.log(response)
        })
      },

      /**
       * 回复评论的回复
       */
      replyCommentToReversion (reply) {
        console.log('reply')
        console.log(reply)
        let param = {
          communionid: reply.communionid, // 农事交流的id
          communioncommentid: reply.communioncommentid, // 农事交流评论的id
          parentid: reply.id, // 农事交流评论的回复id
          reversioncontent: this.reversionContentToReply // 回复 评论回复 的内容
        }
        excuteApis(param, global.constant.farmApiUrls, 'communion', 'commentreversionadd').then(response => {
          console.log('新增评论回复的回复成功')
          console.log(response)
          if (response.data.flag === 1) {
            this.getCommentToReply(reply.id) // 更新评论的回复
            this.currentReplyComment = {} // 关闭输入框
            this.reversionContentToReply = null // 清空输入框
          }
        }).catch((response) => {
          console.log(response)
        })
      },

      /**
       * 删除评论
       */
      deleteCommentContent (id) {
        console.log('id = ' + id)
        excuteApis({ id: id }, global.constant.farmApiUrls, 'communion', 'commentdeletebyid').then(response => {
          console.log('删除评论成功')
          console.log(response)
          if (response.data.flag === 1) {
            this.getComment(this.$route.query.id) // 更新评论
          } else {
            // 删除失败处理 ?
          }
        }).catch(() => {
          // 服务异常处理 ?
        })
      },

      /**
       * 删除评论回复
       */
      deleteReversionContent (reply) {
        console.log('reply = ')
        console.log(reply)
        excuteApis({ id: reply.id }, global.constant.farmApiUrls, 'communion', 'reversiondeletebyid').then(response => {
          console.log('删除评论的回复成功')
          console.log(response)
          if (response.data.flag === 1) {
            this.getComment(this.$route.query.id)
            this.getCommentToReply(reply.communioncommentid) // 更新评论的回复
          } else {
            // 删除失败处理 ?
          }
        }).catch(() => {
          // 服务异常处理 ?
        })
      },

      /**
       * 查询某个农事交流的评论的所有回复
       * @param communioncommentid 农事交流的评论的ID
       */
      getCommentToReply (communioncommentid) {
        excuteApis({ communioncommentid: communioncommentid, delstate: 1 }, global.constant.farmApiUrls, 'communion', 'querylistbycommunioncommentid').then(response => {
          console.log('获取评论回复成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.clickCommentId = communioncommentid
            this.commentToReplyList = response.data.data
          }
        }).catch((response) => {
          console.log('获取评论回复失败')
          console.log(response)
        })
      },

      /**
       * 查看更多评论
       */
      clickMoreComment: throttle(1000, true, function () {
        console.log('查看更多评论')
      }),

      /**
       * 查看更多回复
       */
      clickMoreReply: throttle(1000, true, function () {
        console.log('查看更多回复')
      }),

      /**
       * 获取服务数据
       */
      getServiceData () {
        this.getComment(this.$route.query.id)
      }
    }
  }
</script>
<style scoped>
  .chatDetai-caption{
    padding-left: 30px;
    padding-top: 30px;
    font-size: 16px;
  }
  .mainLeft{
    margin-right: 15px;
  }
  .pictureWrapper img{
    width: 50%;
    height:auto;
    padding: 30px;
  }
  .infoWrapper{
    padding-left: 30px;
    padding-right: 30px;
    padding-bottom: 50px;
  }
  .infoName,.infoAddress{
    margin-left: 10px;
  }
  .infoWrapper>.flex{
    line-height: 30px;
  }
  .chatComment-wrapper{
    margin-top: 20px;
    margin-bottom: 50px;
    padding-left: 30px;
    padding-right: 30px;
  }
  .chatComment-wrapper>.layout{
    margin-bottom: 30px;
  }
  .commentText{
    cursor: pointer;
    color: #406599;
  }
  .commentTime{
    margin-left: 30px;
    color: #999;
  }
  .commentInfo{
    margin-top: 20px;
  }
  .replyText,.commentText{
    cursor: pointer;
    color: #406599;
    margin-top: 15px;
  }
  .replyText{
    padding-left: 62px;
  }
  .replyWrapper{
    margin-top: 10px;
  }
  .replyBtn{
    margin-top: 13px;
  }
  .commentReplyDetail{
    margin-top: 20px;
  }
  .photourl{
    height: 10px;
    width: 10px;
  }
  .btnColor{
    color: #FFF;
    width: 107px;
  }
  .containerWrapper{
    padding: 0;
    margin-bottom: 100px;
  }
  .photoWrapper>div{
    float: left;
  }
  .photoRight{
    padding-left: 10px;
  }
  .commentContent{
    color: #000;
    font-size: 16px;
    padding-left: 62px;
  }
  .commentInput{
    padding-left: 62px;
  }
  .submitComment{
    margin-top: 13px;
  }
  .replyToReply-wrapper{
    padding-left: 62px;
    margin-top: 20px;
  }
  .commentList{
    border-top: 1px solid #CCC;
    padding-top: 20px;
  }
  .hideoverflow {
    word-wrap: break-word;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .deleteComment{
    margin-left: 50px;
  }
  .moreComment{
    margin-top: 50px;
    padding: 7px 0 7px 0;
    color: #406599;
    cursor: pointer;
    background-color: #f4f5f6;
  }
  .main{
    padding: 0 30px 0 30px;
  }
  .btnPadding{
    padding-bottom: 20px;
  }
  .btnSize{
    min-width: 107px;
  }
  .createuseridname{
    font-size: 20px;
    margin-top: 5px;
    margin-bottom: 5px;
  }
</style>
