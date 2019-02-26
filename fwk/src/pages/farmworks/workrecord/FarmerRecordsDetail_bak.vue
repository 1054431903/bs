<template>
  <v-container fluid
               class="containerWrapper">

    <v-card class="page-content pt-1">

      <go-back :name="recordDetail.content">
        <span style="float: right;">
          <v-btn icon
                 small
                 fab
                 title="编辑"
                 v-if="$authed('PUT','farmerrecords/main/farmerrecords/farmerrecordsEdit')"
                 :to="{name: 'farmerRecordsEdit', query: {id: this.$route.query.id}}"
                 :disabled="parseInt(userid) === parseInt(recordDetail.userpermisionscreateuserid) ? false : true">
            <v-icon color="grey darken-1">edit</v-icon>
          </v-btn>
          <v-btn icon
                 small
                 fab
                 title="删除"
                 v-if="$authed('DELETE','farmerrecords/main')"
                 @click.native.stop="dialogDelete = true"
                 :disabled="parseInt(userid) === parseInt(recordDetail.userpermisionscreateuserid) ? false : true">
            <v-icon color="red darken-2">delete</v-icon>
          </v-btn>
        </span>
      </go-back>

      <!-- 编辑 删除按钮 -->
      <!-- <v-layout row v-if="this.$route.query.operateflag !==0">
        <v-flex xs12 text-xs-right>
          <v-btn v-if="$authed('DELETE','farmerrecords/main')" color="error" class="btnSize" @click.native.stop="dialogDelete = true" outline :disabled="parseInt(userid) === parseInt(recordDetail.userpermisionscreateuserid) ? false : true">
            <v-icon dark color="error">delete</v-icon>&nbsp;删除
          </v-btn>
          <v-btn v-if="$authed('PUT','farmerrecords/main/farmerrecords/farmerrecordsEdit')" color="primary" class="btnSize" :to="{name: 'farmerRecordsEdit', query: {id: this.$route.query.id}}" outline :disabled="parseInt(userid) === parseInt(recordDetail.userpermisionscreateuserid) ? false : true">
            <v-icon dark color="primary">edit</v-icon>&nbsp;编辑
          </v-btn>
        </v-flex>
      </v-layout> -->

      <!-- 农事内容 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">
          农事内容&nbsp;:&nbsp;
        </v-flex>
        <v-flex xs9>
          <span v-text="recordDetail.content || '- -'"></span>
        </v-flex>
      </v-layout>

      <!-- 农事类别 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">农事类别&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.farmtypenames || '- -'"></span></v-flex>
      </v-layout>

      <!-- 开始时间 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">开始时间&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.begintime || '- -'"></span></v-flex>
      </v-layout>

      <!-- 结束时间 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">结束时间&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.endtime || '- -'"></span></v-flex>
      </v-layout>

      <!-- 记录来源 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">记录来源&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.sourcedictvaluename || '- -'"></span></v-flex>
      </v-layout>

      <!-- 地块 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">地块&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.parcelnames || '- -'"></span></v-flex>
      </v-layout>

      <!-- 实施人 ? -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">实施人&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.recordpartusernames || '- -'"></span></v-flex>
      </v-layout>

      <!-- 投入品 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent">投入品</v-flex>
        <v-flex xs9>
          <v-data-table :headers="headers"
                        :items="recordDetail.outputlist"
                        no-data-text="暂无数据"
                        no-results-text="暂无数据"
                        hide-actions
                        class="tableStyle">
            <template slot="items"
                      slot-scope="props">
              <td class="text-xs-center"
                  v-text="props.item.typename"></td>
              <td class="text-xs-center hideoverflow"
                  style="max-width:250px;"
                  v-text="props.item.name"
                  :title="props.item.name"></td>
              <td class="text-xs-center hideoverflow"
                  style="max-width:250px;"
                  v-text="props.item.stand "
                  :title="props.item.stand"></td>
              <td class="text-xs-center hideoverflow"
                  style="max-width:250px;">
                <span v-text="props.item.num || ''"></span>
                <span v-text="props.item.numunitname || ''"></span>
                <span v-if="!props.item.num && !props.item.numunitname">- -</span>
              </td>
              <td class="text-xs-center hideoverflow"
                  style="max-width:250px;"
                  v-text="props.item.facturer || '- -'"
                  :title="props.item.facturer"></td>
            </template>
          </v-data-table>
        </v-flex>
      </v-layout>

      <!-- 投入品成本 -->
      <v-layout row
                class="costWrapper">
        <v-flex xs2
                class="labelContent">投入品成本&nbsp;:&nbsp;</v-flex>
        <v-flex xs9><span v-text="recordDetail.cost || 0"></span>元</v-flex>
      </v-layout>

      <!-- 附件 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent"> <span>附件&nbsp;:&nbsp;</span></v-flex>
        <v-flex xs9>
          <a v-for="(list, index) in recordDetail.resourcehistorylist"
             :key="index"
             :href="list.resourcepath"
             v-text="list.resourcename"
             class="accessory"
             target="_blank">
          </a>
          <span v-if="!recordDetail.resourcehistorylist ||recordDetail.resourcehistorylist.length===0">- -</span>
        </v-flex>
      </v-layout>
      <!-- 语音  ??? -->
      <v-layout row
                class="audioWrapper">
        <v-flex xs2
                class="labelContent"><span class="autioName">语音&nbsp;:&nbsp;</span></v-flex>
        <v-flex xs9>
          <audio-player :resourceList="recordDetail.audioResourcehistorylist"></audio-player>
        </v-flex>
      </v-layout>
      <!-- 备注 -->
      <v-layout row>
        <v-flex xs2
                class="labelContent"><span>备注&nbsp;:&nbsp;</span></v-flex>
        <v-flex xs9><span v-text="recordDetail.remark || '- -'"></span></v-flex>
      </v-layout>
    </v-card>
    <!-- 确认删除弹窗 -->
    <v-modaldialog :isShow="dialogDelete">
      <span slot
            v-text="message"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click="dialogDelete = false">取消</v-btn>
          <v-btn flat
                 color="primary"
                 @click.native="submitDelete($route.query.id)">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>
  </v-container>
</template>

<script>
import { excuteApis } from '@/apis'
import FarmerMixin from '@/pages/farmworks/farmerMixin'
import AudioPlayer from '@/components/AudioPlayer.vue'
export default {
  data () {
    return {
      message: '信息删除后将无法恢复,请谨慎操作,确认删除?',
      dialogDelete: false, // 删除弹出框状态
      recordDetail: {}, // 农事记录详情
      headers: [
        {
          text: '类别',
          align: 'center',
          sortable: false
        },
        {
          text: '名称',
          align: 'center',
          sortable: false
        },
        {
          text: '规格',
          align: 'center',
          sortable: false
        },
        {
          text: '数量',
          align: 'center',
          sortable: false
        },
        {
          text: '厂家',
          align: 'center',
          sortable: false
        }
      ]
    }
  },
  computed: {
    userid () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
    }
  },
  mixins: [FarmerMixin],
  mounted () {
    // 异步加载，先加载出player再使用
    this.getRecordDetail()
  },
  methods: {
    /**
     * 根据id获取农事记录详情
     */
    getRecordDetail () {
      this.showLoad()
      let param = { id: this.$route.query.id }
      excuteApis(param, global.constant.farmApiUrls, 'record', 'querybyid').then(res => {
        console.log('获取农事记录详情成功')
        console.log(res)
        if (res.data && res.data.flag === 1 && res.data.data) {
          if (res.data.data.begintime) res.data.data.begintime = res.data.data.begintime.substring(0, 10)
          if (res.data.data.endtime) res.data.data.endtime = res.data.data.endtime.substring(0, 10)
          this.recordDetail = res.data.data
          this.recordDetail.audioResourcehistorylist = this.disposeAudio(this.recordDetail.audioResourcehistorylist) // 处理音频文件
          console.log('处理以后的音频文件:')
          console.log(this.recordDetail.audioResourcehistorylist)
        }
      }).finally(() => {
        this.closeLoad()
      })
    },

    /**
     * 对音频文件进行处理
     * @param array array
     * @return new array
     */
    disposeAudio (array) {
      if (array && array.length > 0) {
        let newAudio = []
        for (let audio of array) {
          audio.title = audio.resourcename
          audio.url = audio.resourcepath
          newAudio.push(audio)
        }
        return newAudio
      }
      return []
    },

    /**
     * 确认删除记录
     */
    submitDelete (id) {
      let param = { id: id }

      excuteApis(param, global.constant.farmApiUrls, 'record', 'deletebyid').then(res => {
        console.log(res)
        if (res.data && res.data.flag === 1) {
          this.$router.replace({ name: 'farmerrecords' }) // 跳转
        } else {
          console.log('删除失败')
        }
      }).catch((response) => {
        console.log('删除失败')
        console.log(response)
      })
      this.dialogDelete = false
    }
  },
  components: {
    AudioPlayer
  }
}
</script>
<style scoped>
.layout {
  margin-bottom: 20px;
}
.audioWrapper {
  position: relative;
  min-height: 30px;
}
.autioName {
  position: absolute;
  top: 7px;
  z-index: 1;
}
audio {
  margin-right: 20px;
}
.accessory {
  margin-right: 20px;
}
.btnColor {
  color: #fff;
}
.main {
  padding-bottom: 30px;
}
.costWrapper {
  margin-top: 30px;
}
.containerWrapper {
  padding: 0;
  margin-bottom: 50px;
}
.btnSize {
  min-width: 107px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.labelContent {
  padding-left: 36px;
}
</style>
