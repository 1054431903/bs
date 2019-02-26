<template>
  <v-container fluid
               class="homeWrapper">
    <!-- top -->
    <v-layout row
              class="topPage-wrapper">
      <v-flex xs4
              class="topPage1"
              @click="farmStore"
              v-if="$authed('GET','farmStore/main')">
        <v-layout row>
          <v-flex xs3
                  class="nongzi"></v-flex>
          <v-flex xs9
                  class="topPage-text">
            <div class="text1">农资商城</div>
            <div class="text2">发现更多便宜的农资产品</div>
          </v-flex>
        </v-layout>
      </v-flex>

      <v-flex xs4
              class="topPage2"
              @click="skipChat"
              v-if="$authed('GET','chat/main')">
        <v-layout row>
          <v-flex xs3
                  class="nongshi"></v-flex>
          <v-flex xs9
                  class="topPage-text">
            <div class="text1">农事交流区</div>
            <div class="text2">与全世界农友一起交流农事问题</div>
          </v-flex>
        </v-layout>
      </v-flex>

      <v-flex xs4
              class="topPage3"
              @click="skipRepository"
              v-if="$authed('GET','repository')">
        <v-layout row>
          <v-flex xs3
                  class="nongye"></v-flex>
          <v-flex xs9
                  class="topPage-text">
            <div class="text1">农业知识库</div>
            <div class="text2">更全&nbsp;&nbsp;更优质的顶尖农业知识</div>
          </v-flex>
        </v-layout>
      </v-flex>

    </v-layout>

    <!-- center -->
    <v-layout row
              v-if="$authed('GET','farmerrecords')">
      <v-flex xs12>
        <v-card class="centerWrapper">

          <!-- 新增记录 -->
          <v-layout class="recordwrapper">
            <v-flex xs6>
              <h3 class="record-caption">农事记录</h3>
            </v-flex>
            <v-flex xs6
                    text-xs-right
                    v-if="$authed('POST', 'farmerrecords/main/farmerrecords/farmerrecordsAdd')">
              <v-btn :to="{name: 'farmerRecordsAdd'}"
                     id="addBtn"
                     small
                     fab
                     dark
                     color="red darken-2"
                     title="新增农事记录">
                <v-icon>add</v-icon>
              </v-btn>
            </v-flex>
          </v-layout>

          <!-- 记录列表 -->
          <v-layout row
                    v-for="(record, index) in records"
                    :key="record.id"
                    v-show="records.length > 0">
            <v-flex xs12
                    class="recordDetails"
                    @click="recordLook(record.id)">
              <!-- record main -->
              <v-layout row
                        wrap
                        class="recordMain">
                <v-flex xs2>
                  <div class="cycle"></div>
                  <div class="time"
                       v-text="record.begintime || '- -'"></div>
                </v-flex>
                <v-flex xs3>
                  <div class="hideoverflow"
                       v-text="record.content || '- -'"
                       :title="record.content || '- -'"></div>
                </v-flex>
                <v-flex xs4
                        offset-xs1>
                  <v-icon>room</v-icon>
                  <span v-text="record.parcelidname || '- -'"></span>
                </v-flex>
              </v-layout>

              <!-- 竖线 -->
              <v-layout row
                        v-if="records.length !== (index + 1)">
                <v-flex xs12>
                  <div class="line"></div>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
          <!-- 记录缺省 -->
          <v-layout row
                    v-show="records.length === 0">
            <v-flex xs12
                    text-xs-center
                    v-text="'暂无数据'"></v-flex>
          </v-layout>

          <!-- 更多 -->
          <v-layout row>
            <v-flex xs12>
              <v-layout row
                        class="moreWrapper">
                <v-flex xs12><span class="more"
                        @click="moreRecord">更多 <v-icon>keyboard_arrow_right</v-icon></span></v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout>

    <!-- bottom -->
    <!-- <v-layout row class="pageBottom">
      <v-flex xs6 v-if="$authed('GET','farmertasks')">
        <v-card class="bottomWrapper">
          <v-layout class="recordwrapper">
            <v-flex xs6><h3 class="record-caption">任务</h3></v-flex>
            <v-flex xs6 text-xs-right class=" moreVertical">
              <span class="more" @click="moreTask">更多<v-icon>keyboard_arrow_right</v-icon></span>
            </v-flex>
          </v-layout>

          <v-layout row v-for="(task, index) in tasks" :key="index" @click="taskLook(task.id)" class="taskList" v-show="tasks.length > 0">
            <v-flex xs8 class="listCycle">
              <div :style="task.taskstatus === 'task-status-overdue' ? 'background-color: red;' : 'background-color: #4cabf4;'"></div>
              <div class="task-text" v-text="task.describe"></div>
            </v-flex>
            <v-flex xs4 text-xs-right v-text="task.endtime"></v-flex>
          </v-layout>
          <v-layout row v-show="tasks.length === 0">
            <v-flex xs12 text-xs-center v-text="'暂无数据'"></v-flex>
          </v-layout>

        </v-card>
      </v-flex>

      <v-flex xs6 v-if="$authed('GET','farmerplans')">
        <v-card class="bottomWrapper">
          <v-layout class="recordwrapper">
            <v-flex xs6><h3 class="record-caption">本月农事计划</h3></v-flex>
            <v-flex xs6 text-xs-right class="moreVertical">
              <span class="more" @click="morePlan">更多<v-icon>keyboard_arrow_right</v-icon></span>
            </v-flex>
          </v-layout>
          <v-layout row v-for="(plan, index) in plans" :key="index" @click="planLook(plan.id)" class="planList" v-show="plans.length > 0">
            <v-flex xs8 class="listCycle">
              <div></div>
              <div class="plan-text" v-text="plan.describe"></div>
            </v-flex>
            <v-flex xs4 text-xs-right v-text="plan.begintime"></v-flex>
          </v-layout>
          <v-layout row v-show="plans.length === 0">
            <v-flex xs12 text-xs-center v-text="'暂无数据'"></v-flex>
          </v-layout>
        </v-card>
      </v-flex>
    </v-layout> -->
  </v-container>
</template>
<script>
import { excuteApis } from '@/apis'
import FarmerMixin from '@/pages/farmworks/farmerMixin'
export default {
  name: 'home',
  data () {
    return {
      records: [], // 农事记录
      recordTime: [],
      tasks: [], // 任务
      plans: [], // 农事计划
      delstateTrue: 1, // 农事计划---删除状态，1为未删除
      isRefreshed: 0 // 是否需要刷新该页面（切换组织后，要查询新组织的相关农事记录等）
    }
  },
  mixins: [FarmerMixin],
  mounted () {
    this.getServiceData()
  },
  computed: {
    userid () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
    }
  },
  methods: {
    /**
     * 农资商城
     */
    farmStore () {
      window.open('https://h5.youzan.com/v2/feature/7jZRShmOGV')
    },

    /**
     * 农事记录详情
     */
    recordLook (id) {
      this.$router.push({ name: 'farmerRecordsDetail', query: { id: id } })
    },

    /**
     * 农事记录 查看更多
     */
    moreRecord () {
      this.$router.push({ name: 'farmerrecords' })
    },

    /**
     * 农事任务详情
     */
    taskLook (id) {
      this.$router.push({ name: 'farmertasksDetail', query: { id: id, select: 1 } })
    },

    /**
     * 农事任务 查看更多
     */
    moreTask () {
      this.$router.push({ name: 'farmertasks' })
    },

    /**
     * 农事计划详情
     */
    planLook (id) {
      sessionStorage.setItem('homeFlag', 'true') // 弹窗标识
      this.$router.push({ name: 'farmerplans', query: { id: id } })
    },

    /**
     * 农事计划 查看更多
     */
    morePlan () {
      this.$router.push({ name: 'farmerplans' })
    },

    /**
     * 跳转农业知识库
     */
    skipRepository () {
      this.$router.push({ name: 'repository' })
    },

    /**
     * 跳转农事交流区
     */
    skipChat () {
      this.$router.push({ name: 'chat' })
    },

    /**
     * 页面数据获取
     * 农事记录
     */
    getServiceData () {
      this.records = []
      this.tasks = []
      this.plans = []
      // this.showLoad()
      /* 农事记录 */
      let paramRerocd = {
        currentPage: 1, // 当前页
        pageSize: 5, // 页大小
        delstate: 1
      }
      excuteApis(paramRerocd, global.constant.farmApiUrls, 'record', 'querylist').then(response => {
        if (response.data && response.data.data && response.data.flag === 1) {
          for (let i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].begintime) response.data.data[i].begintime = response.data.data[i].begintime.substring(0, 10)
            if (response.data.data[i].endtime) response.data.data[i].endtime = response.data.data[i].endtime.substring(0, 10)
          }
          this.records = response.data.data
        }
      })

      /* 农事任务 */
      let paramTask = {
        taskparticsuserid: this.userid,
        currentPage: 1,
        pageSize: 5,
        delstate: 1
      }
      excuteApis(paramTask, global.constant.farmApiUrls, 'task', 'querylist').then(response => {
        console.log('获取农事任务成功')
        console.log(response)
        if (response.data && response.data.data && response.data.flag === 1) {
          for (let i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].begintime) response.data.data[i].begintime = response.data.data[i].begintime.substring(0, 10)
            if (response.data.data[i].endtime) response.data.data[i].endtime = response.data.data[i].endtime.substring(0, 10)
          }
          this.tasks = response.data.data
        }
      }).catch((response) => {
        console.log('获取农事任务fail')
        console.log(response)
      })

      /* 农事计划 */
      let paramPlan = {
        planparticsuserid: this.userid,
        createuserid: this.userid,
        currentPage: 1,
        pageSize: 5,
        delstate: 1
      }
      excuteApis(paramPlan, global.constant.farmApiUrls, 'plan', 'querylist').then(response => {
        console.log('获取农事计划成功')
        console.log(response)
        if (response.data && response.data.data && response.data.flag === 1) {
          for (let i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].begintime) response.data.data[i].begintime = response.data.data[i].begintime.substring(0, 10)
            if (response.data.data[i].endtime) response.data.data[i].endtime = response.data.data[i].endtime.substring(0, 10)
          }
          this.plans = response.data.data
        }
      })
    }
  },
  watch: {
    '$route': 'getServiceData' // 切换路由时
  }
}
</script>]
<style scoped>
.nongzi {
  background-image: url('../../../static/nongzi.png');
  background-position: center;
  height: 100px;
}
.nongshi {
  background-image: url('../../../static/nongshi.png');
  background-position: center;
  height: 100px;
}
.nongye {
  background-image: url('../../../static/nongye.png');
  background-position: center;
  height: 100px;
}
.topPage-wrapper {
  margin-bottom: 20px;
}
.topPage-wrapper > .flex {
  height: 100px;
}
.topPage-wrapper > .flex:not(:last-child) {
  margin-right: 20px;
}
.topPage1 {
  background-color: #0ab6df;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
.topPage2 {
  background-color: #478af6;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
.topPage3 {
  background-color: #eca32d;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
.topPage-text {
  padding-left: 30px;
}
.topPage-text > div:first-child {
  margin-top: 15px;
}
.text1 {
  font-size: 24px;
}
.text2 {
  margin-top: 10px;
}
.record-caption {
  line-height: 50px;
}
.more {
  cursor: pointer;
}
.moreVertical {
  line-height: 50px;
}
.pageBottom {
  margin-top: 20px;
  margin-bottom: 100px;
}
.pageBottom > .flex:first-child {
  margin-right: 10px;
}
.pageBottom > .flex:last-child {
  margin-left: 10px;
}
.taskCaption {
  padding: 10px;
}
.recordwrapper {
  border-bottom: 1px solid #a5cce2;
  padding-top: 7px;
  padding-bottom: 7px;
  margin-bottom: 25px;
}
.centerWrapper,
.bottomWrapper {
  padding-left: 20px;
  padding-right: 20px;
}
.bottomWrapper {
  padding-bottom: 15px;
  min-height: 335px;
}
.bottomWrapper > .layout:not(.recordwrapper) {
  margin-bottom: 25px;
}
#addBtn {
  margin-right: 0;
}
.task-text,
.plan-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 80%;
}
.cycle,
.time {
  float: left;
}
.cycle {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  border: 1px solid #4cabf4;
}
.line {
  width: 1px;
  height: 25px;
  background-color: #4cabf4;
  margin-left: 8px;
  margin-top: 5px;
  margin-bottom: 5px;
}
.time {
  margin-left: 20px;
}
.task {
  margin-left: 50px;
  width: 250px;
}
.taskList,
.planList {
  cursor: pointer;
}
.address {
  margin-left: 100px;
  color: #999;
}
.moreWrapper {
  margin-top: 20px;
  margin-bottom: 20px;
}
.listCycle > div {
  float: left;
}
.listCycle > div:first-child {
  width: 8px;
  height: 8px;
  margin-top: 6px;
  border-radius: 50%;
  background-color: #4cabf4;
}
.listCycle > div:last-child {
  margin-left: 30px;
}
.homeWrapper {
  padding: 0 30px 0 30px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.recordMain {
  cursor: pointer;
}
</style>
