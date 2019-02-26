<template>
  <div>
    <div class="loading-init"
         v-if="!isShow">
      <img src="loading.gif" />
    </div>
    <v-container style="padding:0px"
                 v-else>
      <v-layout row
                wrap
                class="skyWeatherContainer"
                :style="{backgroundImage: 'url(' + skyBackPhoto + ')'}">
        <v-flex xs12
                class="skyRuntimePanel">
          <v-layout row
                    wrap>
            <v-flex xs12
                    class="sky_text_size">
              <span v-if="condition.temp">
                {{condition.temp + '℃' || 25 + '℃'}}
              </span>
            </v-flex>
            <!-- <v-flex xs12 class="sky_condition_size">
              <span v-if="condition">
                23℃
              </span>
              <span v-if="condition">
                15℃
              </span>
            </v-flex> -->
          </v-layout>
        </v-flex>
        <v-flex xs12
                class="skyTAndTPanel">
          <v-layout row>
            <v-flex xs6
                    text-xs-center
                    style="color:#fff">
              {{condition.day}}
            </v-flex>
            <v-flex xs6
                    text-xs-center
                    style="color:#fff">
              {{condition.time}} 更新
            </v-flex>
          </v-layout>
        </v-flex>
        <!-- <v-divider light></v-divider> -->
        <v-flex xs12
                class="skyHourPanel">
          <v-layout row
                    wrap>
            <v-flex xs12>
              <div class="sky_weather-container1">
                <div class="skyOneDayWeather1"
                     v-for="item in hourly"
                     :key="item.id">
                  <div class="skyDayDate1">{{item.time}}</div>
                  <div class="skyCurWeatherPic1"><img style="width:35px;height:35px"
                         :src="item.dayiconpath"></div>
                  <div class="skyTem1">{{item.temp + '℃'}}</div>
                </div>
              </div>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <!-- <v-layout row wrap class="skyBgContainer">
        <v-divider light></v-divider>
        <v-flex xs12 style="overflow-x:auto; overflow-y:hidden">
          <v-layout row wrap>
            <v-flex xs12>
              <div class="sky_weather-container1">
                <div class="skyOneDayWeather1" v-for="item in hourly" :key="item.id">
                  <div class="skyDayDate1">{{item.datetime}}</div>
                  <div class="skyCurWeatherPic1"><img style="width:55px;height:55px" :src="item.skycon"></div>
                  <div class="skyTem1">{{item.temperature + '℃'}}</div>
                </div>
              </div>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout> -->
      <v-layout row
                wrap
                class="skyBgContainer"
                style="height:120px">
        <v-flex xs12
                style="height:10px;background-color:#60a3ce">
        </v-flex>
        <v-flex xs12
                style="color:#fff;background-color:#70acd3;font-size:14px">
          <v-layout row
                    wrap>
            <v-flex xs12>
              <span style="text-align:center;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;display:block;">
                {{deviceInfo.name}}
              </span>
            </v-flex>
            <v-flex xs12
                    text-xs-center>
              <v-icon style="font-size: 20px;width:20px;height:20px"
                      color="white">place</v-icon>
              <span style="word-break:break-all">
                {{deviceInfo.location || '--'}}
              </span>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <v-flex xs12
              style="height:1px;background-color:#659abd">
      </v-flex>
      <v-flex xs12
              style="height:1px;background-color:#a9cde5">
      </v-flex>
      <v-layout row
                wrap
                class="skyBgContainer">
        <!-- <v-flex xs12 style="height:20px;background-color:#60a3ce">
        </v-flex> -->
        <v-flex xs12
                style="color:#fff;margin-left:50px;margin-right:50px;margin-bottom:30px">
          <v-layout row>
            <v-flex xs12
                    text-xs-left
                    class="sky_text_center">
              <span>气压</span>
            </v-flex>
            <v-flex xs12
                    text-xs-right
                    class="sky_text_center">
              <span>{{condition.pressure || '--'}}</span>
              <span>hPa</span>
              <img style="margin-left:10px"
                   src="../../../assets/weather/icon/pressure.png">
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs12
                    text-xs-left
                    class="sky_text_center">
              <span>温度</span>
            </v-flex>
            <v-flex xs12
                    text-xs-right
                    class="sky_text_center">
              <span>{{condition.temp || '--'}}</span>
              <span>℃</span>
              <img style="margin-left:10px"
                   src="../../../assets/weather/icon/temperature.png">
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs12
                    text-xs-left
                    class="sky_text_center">
              <span>风速</span>
            </v-flex>
            <v-flex xs12
                    text-xs-right
                    class="sky_text_center">
              <span>{{condition.wind || '--'}}</span>
              <span>m/s</span>
              <img style="margin-left:10px"
                   src="../../../assets/weather/icon/windpower.png">
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs12
                    text-xs-left
                    class="sky_text_center">
              <span>降雨率</span>
            </v-flex>
            <v-flex xs12
                    text-xs-right
                    class="sky_text_center">
              <span>{{condition.rainfall || '--'}}</span>
              <span>%</span>
              <img style="margin-left:10px"
                   src="../../../assets/weather/icon/rainfall.png">
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                class="skyBgContainer">
        <v-flex xs12
                style="height:10px;background-color:#60a3ce">
        </v-flex>
        <v-flex xs12
                style="height:80px;text-align:center;color:#fff;font-size: 20px;padding-top:30px">
          <span>未来5天天气预报</span>
        </v-flex>
        <v-flex xs12
                style="height:1px;background-color:#659abd">
        </v-flex>
        <v-flex xs12
                style="height:1px;background-color:#a9cde5">
        </v-flex>
        <v-flex xs12
                style="overflow-x:auto; overflow-y:hidden">
          <v-layout row
                    wrap>
            <v-flex xs12>
              <div class="sky_weather-five">
                <div class="skyOneDayWeather1"
                     v-for="item in daily"
                     :key="item.id">
                  <div class="skyDayDate1">{{item.predictDate}}</div>
                  <div class="skyCurWeatherPic1"><img style="width:35px;height:35px"
                         :src="item.dayiconpath"></div>
                  <div class="skyTem1">{{item.tempDay + '℃'}}</div>
                </div>
              </div>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap>
        <v-flex xs12
                style="height:20px;color:#000;font-size: 16px;margin:12px 12px 0px;">
          <v-layout row
                    wrap>
            <v-flex xs6
                    text-xs-left
                    class="sky_center">
              <img src="../../../assets/weather/icon/sky.png">
              <span>天空云图</span>
            </v-flex>
            <v-flex xs6
                    text-xs-right
                    class="sky_center">
              <img src="../../../assets/weather/icon/more.png">
              <span style="color:#acacac"
                    @click="morePhotos">查看更多</span>
            </v-flex>
          </v-layout>
        </v-flex>
        <v-flex xs12>
          <!-- <v-layout row wrap style="padding:12px">
            <v-flex v-for="(i, index) in skyPhotos" v-if="index < 6" :key="index" xs4 style="position:relative;padding:2px">
              <img :src="i.path" class="image" alt="lorem" width="100%" height="100%">
              <v-flex xs12 class="skyPhoto">
                <span style="z-index:999">{{i.time}}</span>
              </v-flex>
            </v-flex>
          </v-layout> -->
          <v-container style="padding:0px"
                       v-if="faild">
            <v-flex text-xs-center
                    style="color:rgb(170, 170, 170);font-size:16px;padding-top:50px">暂无数据</v-flex>
          </v-container>
          <v-layout row
                    wrap
                    style="padding:12px">
            <v-flex v-for="(item, index) in vedios"
                    v-if="index < 6"
                    :key="index"
                    xs4
                    style="position:relative;padding:2px">
              <v-card xs4>
                <v-card-media style="width:100%;height:100px;position:relative;line-height:100px"
                              :src="playerOptions[index].poster"
                              @click="playVideo(item.data.v)">
                  <v-container fill-height
                               fluid>
                    <v-layout fill-height>
                      <v-flex xs12
                              align-center
                              style="text-align:center">
                        <img src="iot/list/play.png"
                             style="width:50px;height:50px;border-radius:115px;" />
                      </v-flex>
                    </v-layout>
                  </v-container>
                  <!-- <video-player class="vjs-custom-skin vjs-big-play-centered"
                                v-if="flag"
                                ref="videoPlayer"
                                :options="playerOptions[index]">
                  </video-player> -->
                </v-card-media>
                <v-card-title>
                  {{fmt_Date(item.time.$numberLong)}}
                </v-card-title>
              </v-card>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import moment from 'moment'
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
// import axios from 'axios'
function skycon2Text (skycon) {
  switch (skycon) {
    case 0: return '晴'
    case 1: return '多云'
    case 2: return '阴'
    case 301: return '雨'
    case 302: return '雪'
    case 18: return '雾'
    case 53: return '霾'
    default: break
  }
}
function windirection2Text (windirection) {
  switch (windirection) {
    case 'N':
      return '北风'
    case 'NE':
      return '东北风'
    case 'E':
      return '东风'
    case 'SE':
      return '东南风'
    case 'S':
      return '南风'
    case 'SW':
      return '西南风'
    case 'W':
      return '西风'
    case 'NW':
      return '西北风'
    default:
      break
  }
}
function pre (v) {
  return String(v).length === 1 ? '0' + v : v
}
function fmtTime (time) {
  let d = new Date(+time)
  return `${pre(d.getHours())}:${pre(d.getMinutes())}`
}
export default {
  name: 'bloomsky',
  components: { videoPlayer },
  data () {
    return {
      isShow: false,
      faild: false,
      city: {}, // 地名
      condition: [], // 实时天气
      forecast: {}, // 未来十五天天气预报
      hourly: [], // 24小时天气预报
      daily: [], // 未来五天
      flag: false, // 解决子组件异步数据的问题
      deviceInfo: {}, // 设备信息
      skyBackPhoto: null, // 天空背景图
      skyPhotos: [], // 天空图片
      vedios: [], // 天空视频
      playerOptions: []
    }
  },
  methods: {
    getNewsFn (id) {
      let data = mongo.db('iot').collection('tenant.device').aggregate([{
        $match: {
          _removed: {
            $exists: false
          },
          _id: {
            $oid: id
          }
        }
      }])
      return data
    },
    morePhotos () {
      this.$router.push({ name: 'skyphotos', query: { photos: this.vedios, options: this.playerOptions } })
    },
    fmt_Date (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}-${pre(d.getMonth() + 1)}-${pre(d.getDate())}`
    },
    findVide (id) {
      let time = new Date(new Date().toLocaleDateString()).getTime()
      mongo.db('iot').collection('tenant.device_bloomsky_video_data').find({ filter: { 'device': { '$oid': id }, time: { '$gt': time - 8 * 24 * 60 * 60 * 1000, '$lt': time } }, sort: { time: -1 } }).then(d => {
        if (d.items.length > 0) {
          this.faild = false
          this.vedios = d.items
        } else {
          this.faild = true
        }
        this.flag = true
        let that = this
        this.vedios.forEach(function (element) {
          let options = {}
          // options.sources = [{ type: 'video/mp4', src: element.data.v.startsWith('http') ? element.data.v : 'http://' + element.data.v }]
          // options.autoplay = false
          // options.loop = false
          // options.muted = false
          // options.fluid = false
          // options.autoSetup = false
          // options.language = 'zh-CN'
          // options.width = document.documentElement.clientWidth
          // options.height = '100%'
          // options.notSupportedMessage = '此视频暂无法播放，请稍后再试'
          // options.poster = 'weather/bloomsky/vedio_img.png'
          options.poster = 'weather/bloomsky/vedio_img.png'
          options.src = element.data.v.startsWith('http') ? element.data.v : 'http://' + element.data.v
          that.playerOptions.push(options)
        })
        console.log(that.playerOptions)
      })
    },
    fetchBkHoursForecast (id) {
      Promise.all([
        mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': id }, period: 2 }, sort: { 'time': -1 }, limit: 5 }),
        mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': id }, period: 1 }, sort: { 'time': -1 }, limit: 24 }),
        mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': id }, period: 6 }, sort: { 'time': -1 }, limit: 24 })
      ]).then(data => {
        if (data[0].items.length > 0) {
          let dayforData = []
          let weekDay = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
          data[0].items.reverse()
          data[0].items.forEach(d => {
            let week = new Date(this.fmt_Date(d.time.$numberLong)).getDay()
            dayforData.push({
              predictDate: weekDay[week],
              conditionDay: skycon2Text(d.data['0:71'].v),
              tempDay: d.data['0:61'].c['2'],
              tempNight: d.data['0:61'].c['1'],
              windDirDay: windirection2Text(d.data['0:64'].v),
              windLevelDay: d.data['0:63'].v,
              dayiconpath: `./weather/bloomsky/W${d.data['0:71'].v}-day.png`
            })
          })
          this.daily = dayforData
        }
        if (data[1].items.length > 0) {
          let ft = []
          data[1].items.reverse()
          data[1].items.forEach(d => {
            ft.push({
              temp: d.data['0:61'].v,
              time: fmtTime(d.time.$numberLong),
              rainforce: d.data['0:69'].v,
              humidity: d.data['0:62'].v,
              windforce: d.data['0:63'].v,
              winddicrection: windirection2Text(d.data['0:64'].v),
              dayiconpath: `./weather/bloomsky/W${d.data['0:71'].v}-day.png`
            })
          })
          this.hourly = ft
        }
        if (data[2].items.length > 0) {
          // let ft = []
          // data[2].items.reverse()
          // data[2].items.forEach(d => {
          //   ft.push({
          //     path: d.data['1:65'].v,
          //     time: fmtTime(d.time.$numberLong)
          //   })
          // })
          // this.skyPhotos = ft
        }
      }, err => {
        console.log(err)
      })
    },
    playVideo (video) {
      console.log(video)
      try {
        if (window.native && window.native.player) {
          window.native.player(JSON.stringify({
            url: video
          }))
          return
        } else {
          this.showMessageInfo('HTML播放未实现')
        }
      } catch (e) {
        console.debug(e)
        this.showMessageError(e.message)
      }
    }
  },
  created () {
    let _id = this.$route.query.id
    this.fetchBkHoursForecast(_id)
    this.findVide(_id)
    this.getNewsFn(_id).then(item => {
      console.log(item)
      this.isShow = true
      if (Object.keys(item[0].accessState.data).length > 0) {
        let condition = item[0].accessState
        // this.condition.rainfall = condition.data['1:69'].v // 降雨率
        this.condition.temp = condition.data['0:61'].v // 温度
        this.condition.wind = condition.data['0:63'].v // 风速
        this.condition.pressure = condition.data['0:67'].v // 气压
        this.skyBackPhoto = condition.data['0:65'].v // 天空图片
        this.condition.time = fmtTime(condition.time.$numberLong) // 时间
        this.deviceInfo.name = item[0].name
        this.deviceInfo.location = item[0].location
        moment.locale('cn', {
          months: '一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月'.split('_'),
          monthsShort: '1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月'.split('_'),
          weekdays: '星期日_星期一_星期二_星期三_星期四_星期五_星期六'.split('_'),
          weekdaysShort: '周日_周一_周二_周三_周四_周五_周六'.split('_'),
          weekdaysMin: '日_一_二_三_四_五_六'.split('_'),
          longDateFormat: {
            LT: 'Ah点mm分',
            LTS: 'Ah点m分s秒',
            L: 'YYYY-MM-DD',
            LL: 'YYYY年MMMD日',
            LLL: 'YYYY年MMMD日Ah点mm分',
            LLLL: 'YYYY年MMMD日ddddAh点mm分',
            l: 'YYYY-MM-DD',
            ll: 'YYYY年MMMD日',
            lll: 'YYYY年MMMD日Ah点mm分',
            llll: 'YYYY年MMMD日ddddAh点mm分'
          },
          meridiemParse: /凌晨|早上|上午|中午|下午|晚上/,
          meridiemHour: function (hour, meridiem) {
            if (hour === 12) {
              hour = 0
            }
            if (meridiem === '凌晨' || meridiem === '早上' ||
              meridiem === '上午') {
              return hour
            } else if (meridiem === '下午' || meridiem === '晚上') {
              return hour + 12
            } else {
              // '中午'
              return hour >= 11 ? hour : hour + 12
            }
          },
          meridiem: function (hour, minute, isLower) {
            var hm = hour * 100 + minute
            if (hm < 600) {
              return '凌晨'
            } else if (hm < 900) {
              return '早上'
            } else if (hm < 1130) {
              return '上午'
            } else if (hm < 1230) {
              return '中午'
            } else if (hm < 1800) {
              return '下午'
            } else {
              return '晚上'
            }
          },
          ordinalParse: /\d{1,2}(日|月|周)/,
          ordinal: function (number, period) {
            switch (period) {
              case 'd':
              case 'D':
              case 'DDD':
                return number + '日'
              case 'M':
                return number + '月'
              case 'w':
              case 'W':
                return number + '周'
              default:
                return number
            }
          },
          relativeTime: {
            future: '%s内',
            past: '%s前',
            s: '几秒',
            m: '1 分钟',
            mm: '%d 分钟',
            h: '1 小时',
            hh: '%d 小时',
            d: '1 天',
            dd: '%d 天',
            M: '1 个月',
            MM: '%d 个月',
            y: '1 年',
            yy: '%d 年'
          },
          week: {
            dow: 1,
            doy: 4
          }
        })
        this.condition.day = moment().format('MM-DD dddd')
      }
    })
    let _this = this
    this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    let btnObj = [{ icon: 'swap_horiz', fn () { _this.$router.push({ name: 'weather-switch' }) } }]
    this.$store.commit('set', { name: 'app', k: 'acts', v: btnObj })
  }
}
</script>
<style>
.skyWeatherContainer {
  background-size: 100% 350px;
  background-repeat: no-repeat;
  background-color: #70acd3;
  height: 350px;
}
.skyBgContainer {
  background-color: #70acd3;
  position: relative;
  overflow: hidden;
}
.skyRuntimePanel {
  margin-top: 45px;
  width: 100%;
  height: 100px;
  color: #fff;
}
.skyTAndTPanel {
  margin-top: 25px;
  height: 50px;
  color: #fff;
}
.skyHourPanel {
  overflow-x: auto;
  overflow-y: hidden;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.1);
  height: 130px;
}
.sky_text_center {
  margin-top: 15px;
  height: 20px;
  line-height: 20px;
  text-align: center;
}
.sky_text_center span {
  display: inline-block;
  vertical-align: middle;
}
.sky_text_center img {
  width: 20px;
  height: 20px;
  vertical-align: middle;
}
.sky_center {
  height: 20px;
  width: 20px;
  line-height: 20px;
  text-align: center;
}
.sky_center span {
  display: inline-block;
  vertical-align: middle;
}
.sky_center img {
  vertical-align: middle;
  height: auto;
  max-height: 100%;
}
.sky_text_size {
  font-size: 50px;
  line-height: 50px;
  width: auto;
  height: 50px;
  text-align: center;
  margin-left: 22.5px;
  margin-right: 15px;
}
.sky_condition_size {
  margin-top: 30px;
  font-size: 20px;
  height: 20px;
  line-height: 20px;
  width: 20px;
  text-align: center;
  position: relative;
}
.skyDayDate1 {
  position: absolute;
  left: 0;
  top: 20px;
  width: 100%;
}
.skyOneDayWeather1 {
  position: relative;
  width: 68px;
  height: 150px;
  float: left;
  margin-right: 8px;
  color: #fff;
  text-align: center;
  line-height: 20px;
  /* border-right:2px solid rgba(255,255,255,0.25); */
}
.skyTem1 {
  position: absolute;
  left: 0;
  top: 90px;
  width: 100%;
}
.skyCurWeatherPic1 {
  position: absolute;
  left: 0;
  top: 45px;
  width: 100%;
  height: 35px;
  background-size: contain;
}
.sky_weather-container1 {
  height: 150px;
  width: 2000px;
  margin-left: 25px;
  position: relative;
  overflow: hidden;
}
.sky_weather-five {
  height: 150px;
  width: 380px;
  margin-left: 20px;
  position: relative;
  overflow: hidden;
}
.sky_inner-container {
  overflow-x: scroll;
  overflow-y: hidden;
}
/* for Chrome */
.sky_inner-container::-webkit-scrollbar {
  display: none;
}
.skyPhoto {
  position: absolute;
  width: auto;
  bottom: 2px;
  left: 2px;
  right: 2px;
  color: #fff;
  background-color: rgba(0, 0, 0, 0.2);
  text-align: center;
}
.footerContainer {
  height: 220px;
  background-image: url('../../../assets/weather/weather_bg/18-img.png');
  background-repeat: repeat;
  background-color: #77889d;
}
.page-main-player {
  z-index: 102;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.vjs-poster {
  background-size: 100% 100px;
}
.video-js .vjs-big-play-button {
  /*
    播放按钮换成圆形
  */
  text-align: center;
  height: 2em;
  width: 2em;
  line-height: 2em;
  border-radius: 1em;
}
.vjs-big-play-centered .vjs-big-play-button {
  left: 65%;
}
.video-js {
  width: 113px;
}
.loading-init {
  z-index: 999;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  padding-top: 200px;
}
</style>
