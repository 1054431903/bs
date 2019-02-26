<template>
  <div class="_page">
    <div class="_loading" v-if="loading">
      <div class="_body">
        <img class="_image" src="loading_chart.gif"/>
        <div class="_text">数据加载中，请稍后...</div>
      </div>
    </div>
    <div v-else>
      <div class="_panel">
        <div class="_title">
          <div class="_time">
            {{farmWorkDetail.beginTimeString?farmWorkDetail.beginTimeString:'--'}} ~ {{farmWorkDetail.endTimeString?farmWorkDetail.endTimeString:'--'}}
          </div>
          <div class="_source">
            来源: {{farmWorkDetail.sourceName?farmWorkDetail.sourceName:'--'}}
          </div>
        </div>
        <div class="_content">
          {{farmWorkDetail.content}}
        </div>
        <div class="_labels">
          <span class="_label" :class="{'_label-1': runMode === 1}" v-if="farmWorkDetail.typeList && farmWorkDetail.typeList.length > 0" v-for="(type,index) in farmWorkDetail.typeList" :key="index">{{type}}</span>
        </div>
        <div class="_images" v-if="farmWorkDetail.images && farmWorkDetail.images.length > 0">
          <img class="_image" v-for="(image, index) in farmWorkDetail.images" :key="index" :src="image.url" v-preview="image.url" preview-title-enable="false" preview-nav-enable="true" />
        </div>
        <audio ref="audio" style="display: none" :src="audioSource" controls @canplaythrough="audioCanPlay" @ended="audioEnded"></audio>
        <v-popup-window ref="audioPlayer" :fullscreen="false" :show-header="false">
          <div class="_audio-player">
            <img src="fwk/play.png" @click="audioIsPlay = false" v-if="audioIsPlay"/>
            <img src="fwk/stop.png" @click="audioIsPlay = true" v-else/>
          </div>
        </v-popup-window>
        <div class="_audio" v-if="farmWorkDetail.audio">
          <div class="_label" @click="showPlayer()">
            <img class="_icon" src="fwk/signal.png"/>
            <span class="_time" v-show="audioLoaded">{{audioDurationDescription}}</span>
          </div>
        </div>
        <div class="_attachments" v-if="farmWorkDetail.others && farmWorkDetail.others.length > 0">
          <div class="_attachment" v-for="(item,index) in farmWorkDetail.others" :key="index">
            <img class="_icon" src="fwk/file_accessory.png"/>
            <span class="_name" @click="download(item.url)">{{item.name}}</span>
          </div>
        </div>
      </div>
      <div class="_separator">&nbsp;</div>
      <div class="_panel">
        <div class="_list">
          <div class="_tile">
            <div class="_line">
              <div class="_title">
                <div class="_label">实施人</div>
                <div class="_content">{{farmWorkDetail.participantsString}}</div>
              </div>
            </div>
          </div>
          <div class="_tile">
            <div class="_line">
              <div class="_title">
                <div class="_label">地块</div>
                <div class="_content">{{farmWorkDetail.farmland.name}}</div>
              </div>
            </div>
          </div>
          <div class="_tile">
            <div class="_line" v-for="(inputRecord,index) in farmWorkDetail.inputRecordList" :key="index">
              <div class="_separator" v-if="index != 0">
                <div class="_label">&nbsp;</div>
                <div class="_content">&nbsp;</div>
              </div>
              <div class="_title">
                <div class="_label">{{index === 0 ?'投入品':''}}</div>
                <div class="_content">
                  {{inputRecord.name}}
                  <span>&nbsp;</span>
                  {{inputRecord.quantity}}
                </div>
              </div>
              <div class="_subtitle">
                <div class="_label">&nbsp;</div>
                <div class="_content">
                  <span class="_span" v-if="inputRecord.typeName && inputRecord.typeName.length > 0">{{inputRecord.typeName}}</span>
                  <span class="_span" v-if="inputRecord.specification && inputRecord.specification.length > 0">{{inputRecord.specification}}</span>
                  <span class="_span" v-if="inputRecord.vender && inputRecord.vender.length > 0">{{inputRecord.vender}}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="_tile">
            <div class="_line">
              <div class="_title">
                <div class="_label">投入成本</div>
                <div class="_content">{{farmWorkDetail.costString}}</div>
              </div>
            </div>
          </div>
          <div class="_tile">
            <div class="_line">
              <div class="_title">
                <div class="_label">备注</div>
                <div class="_content">{{farmWorkDetail.comment}}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from './share'
import Moment from 'moment'
import VPopupWindow from '@/components/PopupWindow'
export default {
  name: 'fwk-farm_work-detail',
  mixins: [Share],
  components: {
    VPopupWindow
  },
  props: {

  },
  data () {
    return {
      loading: false,
      audioLoaded: false,
      audioIsPlay: false,
      audioSource: undefined,
      audioDurationDescription: '',
      farmWorkDetail: {
        farmland: {

        }
      }
    }
  },
  created () {

  },
  mounted () {
    this.load()
  },
  watch: {
    audioIsPlay (newValue) {
      if (newValue) {
        this.$refs.audio.play()
      } else {
        this.$refs.audio.pause()
      }
    }
  },
  computed: {
  },
  methods: {
    async showPlayer () {
      if (!this.audioLoaded) {
        this.showMessageError('文件尚未加载完成或不支持播放该类型文件')
        return
      }
      this.$refs.audioPlayer.show()
    },
    async audioEnded (event) {
      this.audioIsPlay = false
    },
    async audioCanPlay (event) {
      if (event.target.readyState === 4) {
        let m = Math.floor(this.$refs.audio.duration / 60)
        let s = Math.floor(this.$refs.audio.duration % 60)
        this.audioDurationDescription = `${m}'${s}''`
        this.audioLoaded = true
      }
    },
    async load () {
      try {
        this.loading = true
        this.farmWorkDetail = await this.queryFarmWorkDetail(this.$route.params._id)
        if (!this.farmWorkDetail) return
        if (!this.farmWorkDetail.audio) return
        this.audioSource = this.farmWorkDetail.audio.url
      } catch (e) {
        console.error(e)
        this.showMessageError('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    async queryFarmWorkDetail (id) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong,
        id: id
      }
      let response = await this.$http.get('fwk-service-farm/record/querybyid', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      return this.parseFarmWorkDetail(response.data.data)
    },
    parseFarmWorkDetail (data) {
      console.debug(data)
      let result = {}
      result.id = data.id
      result.content = data.content
      result.typeList = data.farmtypenames ? data.farmtypenames.split(',') : []
      result.sourceCode = data.sourcedictvalue
      result.sourceName = data.sourcedictvaluename
      result.cost = data.cost
      result.costUnit = '元'
      result.costString = typeof result.cost === 'number' ? `${result.cost.toFixed(2)}${result.costUnit}` : `-- ${result.costUnit}`
      result.comment = data.remark
      result.participantsString = data.recordpartusernames
      result.beginTime = new Date(data.begintime.replace(' ', 'T') + '.000+08:00')
      result.beginTimeString = Moment(result.beginTime).format('YYYY-MM-DD')
      if (data.endtime) {
        result.endTime = new Date(data.endtime.replace(' ', 'T') + '.000+08:00')
        result.endTimeString = Moment(result.endTime).format('YYYY-MM-DD')
      } else {
        result.endTime = null
        result.endTimeString = null
      }
      result.farmland = {}
      result.farmland.id = data.parcelid
      result.farmland.name = data.parcelidname
      result.inputRecordList = []
      if (data.outputlist && (data.outputlist instanceof Array)) {
        result.inputRecordList = data.outputlist.map(item => {
          let inputRecord = {}
          inputRecord.id = item.id
          inputRecord.name = item.name
          inputRecord.quantity = `${item.num}${item.numunitname || ''}`
          inputRecord.typeName = item.typename
          inputRecord.specification = item.stand
          inputRecord.vender = item.facturer
          return inputRecord
        })
      }
      if (data.audioResourcehistorylist) {
        result.audios = data.audioResourcehistorylist.map(this.parseAttachment).filter(item => {
          const pattern = /^https?:\/\/.*\/.*$/i
          return pattern.test(item.url)
        })
        if (result.audios && result.audios.length > 0) {
          result.audio = result.audios[0]
          // result.audio.url = 'http://sc1.111ttt.cn:8282/2018/1/03m/13/396131203208.m4a?tflag=1519095601&pin=6cd414115fdb9a950d827487b16b5f97#.mp3'
        }
      }
      if (data.imageResourcehistorylist) {
        result.images = data.imageResourcehistorylist.map(this.parseAttachment).filter(item => {
          const pattern = /^https?:\/\/.*\/.*(\.jpg|\.jpeg|\.png|\.gif|\.bmp)$/i
          return pattern.test(item.url)
        })
      }
      if (data.otherResourcehistorylist) {
        result.others = data.otherResourcehistorylist.map(this.parseAttachment).filter(item => {
          const pattern = /^https?:\/\/.*\/.*$/i
          return pattern.test(item.url)
        })
      }
      console.debug(result)
      return result
    }
  }
}
</script>

<style lang="scss" scoped>
  ._loading {
    flex: auto;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
    > ._body {
      flex: auto;
      display: flex;
      flex-flow: column nowrap;
      justify-content: center;
      align-content: center;
      align-items: center;
      > ._image {
        width: 20px;height: 20px
      }
      > .text {
      }
    }
  }
  ._audio-player {
    height: 160px;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;

    img {
      width: 80px;
      height: 80px;
    }
  }
  ._page {
    height: 100%;
    display: flex;
    flex-flow: column nowrap;
    justify-content: stretch;
    align-content: stretch;
    align-items: stretch;
    background-color: white;
    font-size: 15px;
    ._separator {
      background-color: rgb(246, 245, 246);
      height: 10px;
    }
    ._panel {
      padding: 0 15px;
      > ._title {
        font-size: 12px;
        display: flex;
        flex-flow: row nowrap;
        color: #999999;
        padding: 5px 0;
        > ._time {
          display: inline-block;
          flex: none;
          width: 240px;
          overflow: hidden;
          text-overflow: ellipsis;
          word-break: break-all;
          white-space:nowrap;
        }
        > ._source {
          display: inline-block;
          flex: auto;
          overflow: hidden;
          text-overflow: ellipsis;
          word-break: break-all;
          white-space:nowrap;
          text-align: right;
        }
      }
      > ._content {
        font-size: 14px;
        color: #333333;
      }
      > ._labels {
        > ._label {
          display: inline-block;
          margin: 0px 5px 5px 0;
          background-color: #ecf8fa;
          color: #50bed1;
          font-size: 13px;
          padding: 2px 5px;
          text-align: center;
          border-radius: 4px;
        }
        > ._label-1 {
          background-color: rgba(2, 173, 88, .1);
          color: rgb(2, 173, 88);
        }
      }
      > ._images {
        display: flex;
        flex-flow: row wrap;
        justify-content: flex-start;
        align-content: center;
        align-items: center;
        > ._image {
          flex: none;
          object-fit: cover;
          width: 94px;
          height: 94px;
          margin: 1px;
        }
        @media screen and (device-width: 320px) {
          > ._image {
            width: 94px;
            height: 94px;
            margin: 1px;
          }
        }
        @media screen and (device-width: 480px) and (orientation : landscape) {
          > ._image {
            width: 108px;
            height: 108px;
            margin: 2px;
          }
        }
        @media screen and (device-width: 568px) and (orientation : landscape) {
          > ._image {
            width: 103px;
            height: 103px;
            margin: 2px;
          }
        }
        @media screen and (device-width: 360px) {
          > ._image {
            width: 106px;
            height: 106px;
            margin: 2px;
          }
        }
        @media screen and (device-width: 640px) and (orientation : landscape) {
          > ._image {
            width: 116px;
            height: 116px;
            margin: 3px;
          }
        }
        @media screen and (device-width: 375px) {
          > ._image {
            width: 111px;
            height: 111px;
            margin: 2px;
          }
        }
        @media screen and (device-width: 667px) and (orientation : landscape) {
          > ._image {
            width: 121px;
            height: 121px;
            margin: 3px;
          }
        }
        @media screen and (device-width: 411px) {
          > ._image {
            width: 121px;
            height: 121px;
            margin: 3px;
          }
        }
        @media screen and (device-width: 731px) and (orientation : landscape) {
          > ._image {
            width: 132px;
            height: 132px;
            margin: 4px;
          }
        }
        @media screen and (device-width: 414px) {
          > ._image {
            width: 122px;
            height: 122px;
            margin: 3px;
          }
        }
        @media screen and (device-width: 736px) and (orientation : landscape) {
          > ._image {
            width: 133px;
            height: 133px;
            margin: 4px;
          }
        }
        @media screen and (device-width: 812px) and (orientation : landscape) {
          > ._image {
            width: 122px;
            height: 122px;
            margin: 4px;
          }
        }
      }
      > ._audio {
        padding: 10px 0;
        > ._label {
          width: 160px;
          height: 30px;
          background: rgb(229, 247,238);
          display: flex;
          flex-flow: row nowrap;
          align-items: center;
          border-radius: 5px;
          > ._icon {
            width: 15px;
            height: 24px;
            margin: 0 10px;
            transform: rotate(180deg);
          }
          > ._time {
            font-size: 14px;
            color: #02ad58;
            overflow: hidden;
            text-overflow: ellipsis;
            word-break: break-all;
            white-space:nowrap;
          }
        }
      }
      > ._attachments {
        padding: 0 0 15px 0;
        > ._attachment {
          display: flex;
          flex-flow: row nowrap;
          align-items: center;
          margin: 5px 0;
          > ._icon {
            width: 20px;
            height: 20px;
            margin: 0 5px;
          }
          > ._name {
            font-size: 14px;
            color: #000000;
          }
        }
      }
      ._list {
        ._tile {
          margin: 10px 0;
          ._line {
            ._label {
              flex: none;
              color: #999999;
              font-size: 12px;
              width: 60px;
              text-align: right;
              padding: 0 10px 0 0;
            }
            ._title {
              display: flex;
              flex-flow: row nowrap;
              font-size: 14px;
              color: #333333;
              line-height: 21px;
              ._content {

              }
            }
            ._subtitle {
              display: flex;
              flex-flow: row nowrap;
              ._content {
                ._span {
                  color: #999999;
                  font-size: 12px;
                  margin: 0 10px 0 0;
                }
              }
            }
            ._separator {
              display: flex;
              flex-flow: row nowrap;
              background-color: white;
              ._content {

              }
            }
          }
        }
      }
    }
  }
</style>
