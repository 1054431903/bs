<template>
  <v-layout column>
    <v-flex xs12
            sm12>
      <v-card flat>
        <v-card-media class="white--text"
                      height="165px"
                      src="land/bg.jpg">
          <v-layout row>
            <div class="landno"
                 v-if="item">编号：{{ item.parcelno }}</div>
            <v-flex xs12
                    class="text-xs-center mt-3">
              <v-land-profile :stroke-color="strokeColor"
                              :fill-color="fillColor"
                              :lanlngs="polygon.latlngs"
                              :size="165 * 0.8"></v-land-profile>
            </v-flex>
            <div class="soilreport"
                 :class="{'soilreport-1':runMode === 1}"
                 @click="showSoilReport">测土报告</div>
          </v-layout>
        </v-card-media>
      </v-card>
    </v-flex>
    <!-- <v-flex xs12 sm12 md12 lg12 xl12 class="splitcolor"> -->
    <v-flex xs12
            sm12>
      <v-card flat>
        <v-layout row
                  nowrap
                  class="pt-1">
          <v-flex xs12
                  sm12>
            <div class="position">
              <img src="land/address.png"
                   style="vertical-align: middle; margin-right: 5px;"
                   width="12"
                   height="auto" />
              <span>{{ item && item['addressfullname'] ? item.addressfullname : '暂未获取到位置信息' }}</span>
            </div>
          </v-flex>
        </v-layout>
        <v-layout row
                  nowrap
                  style="padding-bottom:10px">
          <v-flex xs3
                  sm3
                  text-xs-center>
            <div class="category plant">
              <img src="land/pingl.png"
                   style="vertical-align: middle; margin-right: 5px;"
                   width="14"
                   height="14" />
              <span>{{ plant['categoryname'] ? plant['categoryname'] : '--'}}</span>
            </div>
          </v-flex>
          <v-flex xs3
                  sm3
                  text-xs-center>
            <div class="alti plant">
              <img src="land/haib.png"
                   style="vertical-align: middle; margin-right: 5px;"
                   width="14"
                   height="14" />
              <span>{{ item && item['elevation'] ? item.elevation : '--' }}米</span>
            </div>
          </v-flex>
          <v-flex xs3
                  sm3
                  text-xs-center>
            <div class="acreage plant">
              <img src="land/mianj.png"
                   style="vertical-align: middle; margin-right: 5px;"
                   width="14"
                   height="14" />
              <span>{{ item && item['parcelareasum'] ? item.parcelareasum.toFixed(2) : '--' }}亩</span>
            </div>
          </v-flex>
          <v-flex xs3
                  sm3
                  text-xs-center>
            <div class="amount plant">
              <img src="land/shul.png"
                   style="vertical-align: middle; margin-right: 5px;"
                   width="14"
                   height="14" />
              <span>{{ plant['number'] ? plant['number'] : '--'}}株</span>
            </div>
          </v-flex>
        </v-layout>
        <v-layout v-if="runMode === 1"
                  row
                  nowrap
                  @click="showFarmlandDetail()"
                  style="height:40px;line-height:40px; padding-right: 5px; padding-left: 10px;">
          <v-flex xs12
                  sm12>
            <v-divider></v-divider>
            <v-layout>
              <v-flex xs6
                      sm6
                      text-xs-left>
                <span class="details plant"
                      :class="{'details-1':runMode === 1}">查看详细信息</span>
              </v-flex>
              <v-flex xs6
                      sm6
                      text-xs-right>
                <v-icon color="grey">keyboard_arrow_right</v-icon>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-card>
    </v-flex>
    <v-flex xs12
            sm12
            md12
            lg12
            xl12
            class="splitcolor"></v-flex>
    <v-flex xs12
            sm12
            @click="showWeatherDetail()">
      <v-card flat
              class="pb-2">
        <v-layout row
                  style="padding-top: 10px; padding-bottom: 10px; padding-right: 5px;">
          <v-flex xs6
                  sm6
                  text-xs-left>
            <span class="titlelable"
                  style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">{{item['weatherName'] ? item['weatherName'] : '公共天气'}}</span>
          </v-flex>
          <v-flex xs6
                  sm6
                  text-xs-right>
            <v-icon color="grey">keyboard_arrow_right</v-icon>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs12
                  sm12
                  pb-2>
            <v-layout row
                      class="wethercontent"
                      :style="{backgroundImage: 'url(land/35-img.png)'}">
              <v-flex xs6
                      sm6
                      md7
                      text-xs-center
                      pt-3>
                <v-layout row>
                  <v-flex xs12
                          sm12>
                    <img :src="item['iconpath'] ? item['iconpath'] : 'weather/W0.png'"
                         style="vertical-align: middle;"
                         width="auto"
                         height="45" />&nbsp;&nbsp;
                    <span style="line-height: 45px; height: 45px; font-weight: 600; font-size: 20px;">{{item ? item['temp'] : '--'}}℃</span>
                  </v-flex>
                </v-layout>
                <v-layout row>
                  <v-flex xs12
                          sm12>
                    <span style="font-size: 15px;">{{item ? item['tempNight'] : '--'}}℃~{{item ? item['tempDay'] : '--'}}℃</span>&nbsp;&nbsp;
                    <span style="font-size: 15px;">{{item ? item['condition'] : '--'}}</span>
                  </v-flex>
                </v-layout>
              </v-flex>
              <v-flex xs6
                      sm6
                      md5
                      pt-2>
                <table>
                  <tr>
                    <td><img src="land/29-img.png"
                           style="vertical-align: middle;"
                           width="auto"
                           height="18" /></td>
                    <td>
                      <span class="weatherright">{{item ? item['humidity'] : '--'}}%</span>
                    </td>
                    <td>
                      <span>湿度</span>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="land/30-img.png"
                           style="vertical-align: middle;"
                           width="auto"
                           height="18" /></td>
                    <td>
                      <span class="weatherright">{{item ? item['windLevel'] : '--'}}级</span>
                    </td>
                    <td>
                      <span>{{item ? item['windDir'] : '--'}}</span>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="land/31-img.png"
                           style="vertical-align: middle;"
                           width="auto"
                           height="18" /></td>
                    <td>
                      <span class="weatherright">{{item ? item['pressure'] : '--'}}hPa</span>
                    </td>
                    <td>
                      <span>气压</span>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="land/32-img.png"
                           style="vertical-align: middle;"
                           width="auto"
                           height="18" /></td>
                    <td>
                      <span class="weatherright">{{item ? item['precipitation'] : '--'}}mm</span>
                    </td>
                    <td>
                      <span>降水量</span>
                    </td>
                  </tr>
                </table>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-card>
    </v-flex>
    <v-flex xs12
            sm12
            md12
            lg12
            xl12
            class="splitcolor"></v-flex>
    <v-flex xs12
            sm12>
      <v-card flat
              class="pb-2">
        <v-layout row
                  @click="showIotDetail()"
                  style="padding-top: 10px; padding-bottom: 10px; padding-right: 5px;">
          <v-flex xs6
                  sm6
                  text-xs-left>
            <span class="titlelable">种植生长数据
            </span>
          </v-flex>
          <v-flex xs6
                  sm6
                  text-xs-right>
            <v-icon color="grey">keyboard_arrow_right</v-icon>
          </v-flex>
        </v-layout>
        <v-layout row
                  class="pt-2">
          <v-flex xs4
                  sm4
                  text-xs-center
                  @click="showIotDetail(1)">
            <v-layout row>
              <v-flex xs12
                      sm12>
                <v-badge overlap
                         :color="weatherBadgeColor">
                  <span slot="badge"
                        v-if="weatherBadge">{{ weatherBadge }}</span>
                  <img src="land/qix.png"
                       width="40xp"
                       height="40px" />
                </v-badge>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs12
                      sm12>
                <span>微气象({{ totalWeatherDevices['total'] || 0 }})</span>
              </v-flex>
            </v-layout>
          </v-flex>
          <v-flex xs4
                  sm4
                  text-xs-center
                  @click="showIotDetail(2)">
            <v-layout row>
              <v-flex xs12
                      sm12>
                <v-badge overlap
                         :color="soilBadgeColor">
                  <span slot="badge"
                        v-if="soilBadge">{{ soilBadge }}</span>
                  <img src="land/tur.png"
                       width="40xp"
                       height="40px" />
                </v-badge>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs12
                      sm12>
                <span>土壤({{ totalSoilDevices['total'] || 0 }})</span>
              </v-flex>
            </v-layout>
          </v-flex>
          <v-flex xs4
                  sm4
                  text-xs-center
                  @click="showIotDetail(4)">
            <v-layout row>
              <v-flex xs12
                      sm12>
                <v-badge overlap
                         :color="vedioBadgeColor">
                  <span slot="badge"
                        v-if="vedioBadge">{{ vedioBadge }}</span>
                  <img src="land/ship.png"
                       width="40xp"
                       height="40px" />
                </v-badge>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs12
                      sm12>
                视频图像({{ totalVedioDevices['total'] || 0 }})
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-card>
    </v-flex>
    <v-flex xs12
            sm12
            md12
            lg12
            xl12
            class="splitcolor"></v-flex>
    <v-flex v-if="runMode === 1"
            xs12
            sm12
            md12
            lg12
            xl12>
      <v-card flat
              class="pb-2">
        <v-layout row
                  @click="showPlantingList()"
                  style="padding-top: 10px; padding-bottom: 10px; padding-right: 5px;">
          <v-flex xs6
                  sm6
                  text-xs-left>
            <span class="titlelable">种植信息</span>
          </v-flex>
          <v-flex xs6
                  sm6
                  text-xs-right>
            <v-icon color="grey">keyboard_arrow_right</v-icon>
          </v-flex>
        </v-layout>
        <div v-for="(plant,index) in plantList"
             :key="plant['id']"
             v-if="index < (pagination.rowsPerPage || 3)"
             @click="showPlantingDetails(plant['id'])">
          <v-layout row>
            <v-flex xs12
                    sm12
                    md12
                    lg12
                    xl12
                    pl-2
                    pr-2
                    pb-1
                    class="work">
              <div class="separator">&nbsp;</div>
              <div class="plantcontent pt-2 pl-1">
                <span class="planttitle">{{plant['categoryname'] || '--'}}{{plant['varietiesname']?`，${plant['varietiesname']}`:''}}</span>
                <span class="plantcount">{{plant['number'] || '--'}}{{plant['unit']}}</span>
              </div>
              <div class="plantcontent pt-1">
                <span class="plantbegintime">{{plant['plantbegintime']?(plant['plantbegintime'] || '').split(' ')[0]:'--'}}</span>
                <span class="plantcycle">
                  {{typeof plant['plantage'] === 'number'?`${plant['plantage']}${plant['plantageunit']?plant['plantageunit']:''}`:'--'}}
                </span>
              </div>
            </v-flex>
          </v-layout>
        </div>
        <div v-if="plantList && plantList.length <= 0">
          <v-layout row>
            <v-flex xs12
                    sm12
                    md12
                    lg12
                    xl12
                    pl-2
                    pr-2
                    pb-1
                    class="work"
                    text-xs-center>
              <div class="separator">&nbsp;</div>
              <div class="pt-3">
                <span class="worktime">未找到任何记录</span>
              </div>
            </v-flex>
          </v-layout>
        </div>
      </v-card>
    </v-flex>
    <v-flex v-if="$route.query['mode'] === '1'"
            xs12
            sm12
            md12
            lg12
            xl12
            class="splitcolor"></v-flex>
    <v-flex xs12
            sm12
            md12
            lg12
            xl12>
      <v-card flat
              class="pb-2">
        <v-layout row
                  @click="showFarmWorkList()"
                  style="padding-top: 10px; padding-bottom: 10px; padding-right: 5px;">
          <v-flex xs6
                  sm6
                  text-xs-left>
            <span class="titlelable">农事记录</span>
          </v-flex>
          <v-flex xs6
                  sm6
                  text-xs-right>
            <v-icon color="grey">keyboard_arrow_right</v-icon>
          </v-flex>
        </v-layout>
        <div v-for="record in recordList"
             :key="record._id.$oid"
             @click="showFarmWorkDetail(record._id.$oid)">
          <v-layout row>
            <v-flex xs12
                    sm12
                    md12
                    lg12
                    xl12
                    pl-2
                    pr-2
                    pb-1
                    class="work">
              <div class="separator">&nbsp;</div>
              <div class="pt-1">
                <span class="worktime">{{record.startOn && record.startOn.$numberLong && format(Number(record.startOn.$numberLong)) || '--'}}</span>
                <span class="worktype">来源：{{record.source && record.source.name || '--'}}</span>
              </div>
              <div class="workcontent pt-1">{{record.content || '--'}}</div>
              <div class="pt-1 pb-1">
                <span class="pt-1 pb-2"
                      v-for="(category, index) in record.category"
                      :key="index">
                  <span class="workkind"
                        :class="{'workkind-1': runMode === 1}">{{category.name || '--'}}</span>
                </span>
              </div>
            </v-flex>
          </v-layout>
        </div>
        <div v-if="recordList && recordList.length === 0">
          <v-layout row>
            <v-flex xs12
                    sm12
                    md12
                    lg12
                    xl12
                    pl-2
                    pr-2
                    pb-1
                    class="work"
                    text-xs-center>
              <div class="separator">&nbsp;</div>
              <div class="pt-3">
                <span class="worktime">未找到任何记录</span>
              </div>
            </v-flex>
          </v-layout>
        </div>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
import VLandProfile from '@/components/LandProfile'
import mongo from '@vehiclecloud/mongo-rest-client'
import constant from '@/tools/constant'
import { excuteApis } from '@/apis'
import Share from '@/share'
import Moment from 'moment'
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

export default {
  name: 'farmland',
  mixins: [Share],
  data () {
    this.winddir = { 'N': '北风', 'NE': '东北风', 'E': '东风', 'SE': '东南风', 'S': '南风', 'SW': '西南风', 'W': '西风', 'NW': '西北风' }
    this.windSpeed = [[0, 1], [1, 5], [6, 11], [12, 19], [20, 28], [29, 38], [39, 49], [50, 61], [62, 74], [75, 88], [89, 102], [103, 117], [118, 134], [135, 149], [150, 166], [167, 183], [184, 201], [202, 220]]
    return {
      polygon: {
        latlngs: []
      },
      item: {
        id: null,
        name: null,
        weatherName: null,
        addresscode: null,
        addressdetail: null,
        area: null,
        elevation: null,
        dependenceid: null,
        departmentid: null,
        belongpeasantid: null,
        introduce: null,
        dependencename: null,
        departmentname: null,
        belongpeasantname: null,
        responsibleuserid: [],
        responsibleusername: [],
        responsibleuserphone: [],
        parceltypeid: [],
        longitude: 0,
        latitude: 0,
        parcelsum: 0,
        parcelareasum: 0,
        supportid: null,
        categoryname: null,
        hasweatherdata: false,
        weathercityname: null, // 城市名称
        updatetime: null, // 发布时间
        condition: null, // 实时天气
        temp: null, // 温度
        windDir: null, // 风向
        windLevel: null, // 风级
        humidity: null, // 湿度
        icon: null, // 天气icon
        precipitation: null, // 过去一小时累计降雨量
        pressure: null, // 气压
        tips: null, // 一句话提示
        iconpath: null, // 图标路径
        timeday: null, // 星期几
        tempDay: null, // 白天气温
        tempNight: null // 夜间气温
      },
      pagination: {
        page: 1,
        rowsPerPage: 3
      },
      recordList: [],
      plantList: [],
      plant: {},
      types: {
        '1': [],
        '2': [],
        '4': []
      },
      weatherDevices: [], // 此处定义稍显歧义，暗指微气象设备类型
      soilDevices: [], // 此处定义稍显歧义，暗指土壤设备类型
      vedioDevices: [], // 此处定义稍显歧义，暗指土壤设备类型
      // totalWeatherDevices: {},
      // totalSoilDevices: {},
      // totalVedioDevices: {}
      totalWeatherDevices: { alarmNums: 0, faultNums: 0, other: 0, total: 0 },
      totalSoilDevices: { alarmNums: 0, faultNums: 0, other: 0, total: 0 },
      totalVedioDevices: { alarmNums: 0, faultNums: 0, other: 0, total: 0 },
      bloomskyDevList: []
    }
  },
  created () { },
  mounted () {
    this.load()
  },
  computed: {
    app () {
      return this.$store.state.app
    },
    strokeColor () {
      return this.runMode === 1 ? 'rgba(2,173,88,1)' : 'rgba(103,197,234,1)'
    },
    fillColor () {
      return this.runMode === 1 ? 'rgba(2,173,88,0.6)' : 'rgba(103,197,234,0.6)'
    },
    weatherBadge () {
      return this.totalWeatherDevices['alarmNums'] || this.totalWeatherDevices['faultNums'] || 0
    },
    weatherBadgeColor () {
      return this.totalWeatherDevices['alarmNums'] ? 'red' : (this.totalWeatherDevices['faultNums'] ? 'orange' : '')
    },
    soilBadge () {
      return this.totalSoilDevices['alarmNums'] || this.totalSoilDevices['faultNums'] || 0
    },
    soilBadgeColor () {
      return this.totalSoilDevices['alarmNums'] ? 'red' : (this.totalSoilDevices['faultNums'] ? 'orange' : '')
    },
    vedioBadge () {
      return this.totalVedioDevices['alarmNums'] || this.totalVedioDevices['faultNums'] || 0
    },
    vedioBadgeColor () {
      return this.totalVedioDevices['alarmNums'] ? 'red' : (this.totalVedioDevices['faultNums'] ? 'orange' : '')
    }
  },
  watch: {
    item: function (v) {
      if (v) {
        this.$store.commit('set', { name: 'app', k: 'title', v: this.item.name })
      }
    },
    'item.longitude': function (v) {
      let farmland = this.$store.state['tenant.farmland'].item
      let farmlandid = farmland['_id']['$oid']
      this.queryBloomSkyDev(farmlandid).then(res => {
        console.log(res)
        this.bloomskyDevList = res
        this.loadWeather()
      })
    }
  },
  methods: {
    format (value) {
      return Moment(value).format('YYYY-MM-DD')
    },
    async load () {
      this.loadFarmLand()
      this.loadCoords()
      this.loadIotTypes()
      this.loadPlant()
      // this.loadFarmWork()
      this.loadRecord()
    },
    async loadFarmLand () {
      let result = await this.queryFwkParcel()
      this.item = result
    },
    async loadCoords () {
      let farmland = this.$store.state['tenant.farmland'].item
      if (!farmland) return
      let gis = farmland['gis']
      gis.forEach(g => {
        this.polygon.latlngs.push(Object.assign({}, g))
      })
    },
    /*
    获取微气象数据
     */
    async getNewsFn (id) {
      let data = mongo.db('iot').collection('tenant.device').aggregate([{ $match: { _removed: { $exists: false }, _id: { $oid: id } } }])
      return data
    },
    async loadWeather () {
      let farmland = this.$store.state['tenant.farmland'].item
      let farmlandid = farmland['_id']['$oid']
      let data = {}
      if (window.native && window.native.getWeatherTag) {
        data = JSON.parse(window.native.getWeatherTag() || '{}')
      }
      let forecast = Object.assign({}, { tempDay: null, tempNight: null })
      if (Object.keys(data).length > 0) {
        if (data.landid === farmlandid) {
          if (data.flag === 0) {
            // 加载weather数据--公共天气
            if (!this.item || !this.item['longitude'] || !this.item['latitude']) return
            excuteApis(Object.assign({ islogin: 1 }, { lon: this.item['longitude'], lat: this.item['latitude'], weatherCategorys: 'condition,forecast15days' }), constant.commonApiUrls, 'common', 'queryMoJiWeather').then((resWeather) => {
              let weatherData = resWeather['data'] && resWeather['data']['flag'] === 1 ? resWeather['data']['data'] : null
              let condition = weatherData['condition'] // 实时天气数据
              forecast = weatherData['forecast'][1] // 未来15天预报
              this.$set(this.item, 'weatherName', '公共天气') // 天气名称
              this.$set(this.item, 'updatetime', condition.updatetime) // 发布时间
              this.$set(this.item, 'condition', condition.condition) //  实时天气
              this.$set(this.item, 'temp', condition.temp) //  温度
              this.$set(this.item, 'windDir', condition.windDir) //  风向
              this.$set(this.item, 'windLevel', condition.windLevel) //  风级
              this.$set(this.item, 'humidity', condition.humidity) //  湿度
              this.$set(this.item, 'icon', condition.icon) //  天气icon
              this.$set(this.item, 'precipitation', condition.precipitation) //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', condition.pressure) //  气压
              this.$set(this.item, 'tips', condition.tips) //  一句话提示
              this.$set(this.item, 'iconpath', 'weather/W' + condition.icon + '.png') //  图标路径
              this.$set(this.item, 'tempDay', forecast.tempDay) //  最高气温(白天温度)
              this.$set(this.item, 'tempNight', forecast.tempNight) //  最低气温(夜间温度)
            })
          } else {
            mongo.db('iot').collection('tenant.device').aggregate([{ $match: { _removed: { $exists: false }, _id: { $oid: data.id } } }]).then(item => {
              let condition = item[0].accessState
              let windSpeed = condition.data['0:63'].v
              for (let i = 0; i < this.windSpeed.length; i++) {
                if (windSpeed >= this.windSpeed[i][0] && windSpeed <= this.windSpeed[i][1]) windSpeed = i
              }
              this.$set(this.item, 'weatherName', item[0].name) // 天气名称
              this.$set(this.item, 'temp', condition.data['0:61'].v) //  温度
              this.$set(this.item, 'windDir', this.winddir[condition.data['0:64'].v]) //  风向
              this.$set(this.item, 'windLevel', windSpeed) //  风力
              this.$set(this.item, 'humidity', condition.data['0:62'].v) //  湿度
              this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', condition.data['0:67'].v) //  气压
              this.$set(this.item, 'timeday', condition.timeday) //  星期几
            }).catch(v => {
              this.$set(this.item, 'weatherName', '微气象') // 天气名称
              this.$set(this.item, 'temp', '--') //  温度
              this.$set(this.item, 'windDir', '--') //  风向
              this.$set(this.item, 'windLevel', '--') //  风力
              this.$set(this.item, 'humidity', '--') //  湿度
              this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', '--') //  气压
              this.$set(this.item, 'timeday', '--') //  星期几
            })
            mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': data.id }, period: 2 }, sort: { 'time': -1 }, limit: 5 }).then(data => {
              if (data.items.length > 0) {
                this.$set(this.item, 'condition', skycon2Text(data.items[0].data['0:71'].v)) //  实时天气
                this.$set(this.item, 'tempDay', data.items[0].data['0:61'].c['2']) //  最高气温(白天温度)
                this.$set(this.item, 'tempNight', data.items[0].data['0:61'].c['1']) //  最低气温(夜间温度)
                this.$set(this.item, 'iconpath', `./weather/bloomsky/W${data.items[0].data['0:71'].v}-day.png`) //  图标路径
              }
            }).catch(v => {
              this.$set(this.item, 'condition', '--') //  实时天气
              this.$set(this.item, 'tempDay', '--') //  最高气温(白天温度)
              this.$set(this.item, 'tempNight', '--') //  最低气温(夜间温度)
              this.$set(this.item, 'iconpath', `./weather/bloomsky/W0-day.png`) //  图标路径
            })
          }
        } else {
          if (this.bloomskyDevList.length > 0) {
            mongo.db('iot').collection('tenant.device').aggregate([{ $match: { _removed: { $exists: false }, _id: { $oid: this.bloomskyDevList[0]._id.$oid } } }]).then(item => {
              let condition = item[0].accessState
              let windSpeed = condition.data['0:63'].v
              for (let i = 0; i < this.windSpeed.length; i++) {
                if (windSpeed >= this.windSpeed[i][0] && windSpeed <= this.windSpeed[i][1]) windSpeed = i
              }
              this.$set(this.item, 'weatherName', item[0].name) // 天气名称
              this.$set(this.item, 'temp', condition.data['0:61'].v) //  温度
              this.$set(this.item, 'windDir', this.winddir[condition.data['0:64'].v]) //  风向
              this.$set(this.item, 'windLevel', windSpeed) //  风力
              this.$set(this.item, 'humidity', condition.data['0:62'].v) //  湿度
              this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', condition.data['0:67'].v) //  气压
              this.$set(this.item, 'timeday', condition.timeday) //  星期几
            }).catch(v => {
              this.$set(this.item, 'weatherName', '微气象') // 天气名称
              this.$set(this.item, 'temp', '--') //  温度
              this.$set(this.item, 'windDir', '--') //  风向
              this.$set(this.item, 'windLevel', '--') //  风力
              this.$set(this.item, 'humidity', '--') //  湿度
              this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', '--') //  气压
              this.$set(this.item, 'timeday', '--') //  星期几
            })
            mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': this.bloomskyDevList[0]._id.$oid }, period: 2 }, sort: { 'time': -1 }, limit: 5 }).then(data => {
              if (data.items.length > 0) {
                this.$set(this.item, 'condition', skycon2Text(data.items[0].data['0:71'].v)) //  实时天气
                this.$set(this.item, 'tempDay', data.items[0].data['0:61'].c['2']) //  最高气温(白天温度)
                this.$set(this.item, 'tempNight', data.items[0].data['0:61'].c['1']) //  最低气温(夜间温度)
                this.$set(this.item, 'iconpath', `./weather/bloomsky/W${data.items[0].data['0:71'].v}-day.png`) //  图标路径
              }
            }).catch(v => {
              this.$set(this.item, 'condition', '--') //  实时天气
              this.$set(this.item, 'tempDay', '--') //  最高气温(白天温度)
              this.$set(this.item, 'tempNight', '--') //  最低气温(夜间温度)
              this.$set(this.item, 'iconpath', `./weather/bloomsky/W0-day.png`) //  图标路径
            })
          } else {
            // 加载weather数据--公共天气
            if (!this.item || !this.item['longitude'] || !this.item['latitude']) return
            excuteApis(Object.assign({ islogin: 1 }, { lon: this.item['longitude'], lat: this.item['latitude'], weatherCategorys: 'condition,forecast15days' }), constant.commonApiUrls, 'common', 'queryMoJiWeather').then((resWeather) => {
              let weatherData = resWeather['data'] && resWeather['data']['flag'] === 1 ? resWeather['data']['data'] : null
              let condition = weatherData['condition'] // 实时天气数据
              forecast = weatherData['forecast'][1] // 未来15天预报
              this.$set(this.item, 'weatherName', '公共天气') // 天气名称
              this.$set(this.item, 'updatetime', condition.updatetime) // 发布时间
              this.$set(this.item, 'condition', condition.condition) //  实时天气
              this.$set(this.item, 'temp', condition.temp) //  温度
              this.$set(this.item, 'windDir', condition.windDir) //  风向
              this.$set(this.item, 'windLevel', condition.windLevel) //  风级
              this.$set(this.item, 'humidity', condition.humidity) //  湿度
              this.$set(this.item, 'icon', condition.icon) //  天气icon
              this.$set(this.item, 'precipitation', condition.precipitation) //  过去一小时累计降雨量
              this.$set(this.item, 'pressure', condition.pressure) //  气压
              this.$set(this.item, 'tips', condition.tips) //  一句话提示
              this.$set(this.item, 'iconpath', 'weather/W' + condition.icon + '.png') //  图标路径
              this.$set(this.item, 'tempDay', forecast.tempDay) //  最高气温(白天温度)
              this.$set(this.item, 'tempNight', forecast.tempNight) //  最低气温(夜间温度)
            })
          }
        }
      } else {
        console.log(this.bloomskyDevList)
        console.log(this.bloomskyDevList.length)
        if (this.bloomskyDevList.length > 0) {
          mongo.db('iot').collection('tenant.device').aggregate([{ $match: { _removed: { $exists: false }, _id: { $oid: this.bloomskyDevList[0]._id.$oid } } }]).then(item => {
            let condition = item[0].accessState
            let windSpeed = condition.data['0:63'].v
            for (let i = 0; i < this.windSpeed.length; i++) {
              if (windSpeed >= this.windSpeed[i][0] && windSpeed <= this.windSpeed[i][1]) windSpeed = i
            }
            this.$set(this.item, 'weatherName', item[0].name) // 天气名称
            this.$set(this.item, 'temp', condition.data['0:61'].v) //  温度
            this.$set(this.item, 'windDir', this.winddir[condition.data['0:64'].v]) //  风向
            this.$set(this.item, 'windLevel', windSpeed) //  风力
            this.$set(this.item, 'humidity', condition.data['0:62'].v) //  湿度
            this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
            this.$set(this.item, 'pressure', condition.data['0:67'].v) //  气压
            this.$set(this.item, 'timeday', condition.timeday) //  星期几
          }).catch(v => {
            this.$set(this.item, 'weatherName', '微气象') // 天气名称
            this.$set(this.item, 'temp', '--') //  温度
            this.$set(this.item, 'windDir', '--') //  风向
            this.$set(this.item, 'windLevel', '--') //  风力
            this.$set(this.item, 'humidity', '--') //  湿度
            this.$set(this.item, 'precipitation', '--') //  过去一小时累计降雨量
            this.$set(this.item, 'pressure', '--') //  气压
            this.$set(this.item, 'timeday', '--') //  星期几
          })
          mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': this.bloomskyDevList[0]._id.$oid }, period: 2 }, sort: { 'time': -1 }, limit: 5 }).then(data => {
            if (data.items.length > 0) {
              this.$set(this.item, 'condition', skycon2Text(data.items[0].data['0:71'].v)) //  实时天气
              this.$set(this.item, 'tempDay', data.items[0].data['0:61'].c['2']) //  最高气温(白天温度)
              this.$set(this.item, 'tempNight', data.items[0].data['0:61'].c['1']) //  最低气温(夜间温度)
              this.$set(this.item, 'iconpath', `./weather/bloomsky/W${data.items[0].data['0:71'].v}-day.png`) //  图标路径
            }
          }).catch(v => {
            this.$set(this.item, 'condition', '--') //  实时天气
            this.$set(this.item, 'tempDay', '--') //  最高气温(白天温度)
            this.$set(this.item, 'tempNight', '--') //  最低气温(夜间温度)
            this.$set(this.item, 'iconpath', `./weather/bloomsky/W0-day.png`) //  图标路径
          })
        } else {
          // 加载weather数据--公共天气
          if (!this.item || !this.item['longitude'] || !this.item['latitude']) return
          excuteApis(Object.assign({ islogin: 1 }, { lon: this.item['longitude'], lat: this.item['latitude'], weatherCategorys: 'condition,forecast15days' }), constant.commonApiUrls, 'common', 'queryMoJiWeather').then((resWeather) => {
            let weatherData = resWeather['data'] && resWeather['data']['flag'] === 1 ? resWeather['data']['data'] : null
            let condition = weatherData['condition'] // 实时天气数据
            forecast = weatherData['forecast'][1] // 未来15天预报
            this.$set(this.item, 'weatherName', '公共天气') // 天气名称
            this.$set(this.item, 'updatetime', condition.updatetime) // 发布时间
            this.$set(this.item, 'condition', condition.condition) //  实时天气
            this.$set(this.item, 'temp', condition.temp) //  温度
            this.$set(this.item, 'windDir', condition.windDir) //  风向
            this.$set(this.item, 'windLevel', condition.windLevel) //  风级
            this.$set(this.item, 'humidity', condition.humidity) //  湿度
            this.$set(this.item, 'icon', condition.icon) //  天气icon
            this.$set(this.item, 'precipitation', condition.precipitation) //  过去一小时累计降雨量
            this.$set(this.item, 'pressure', condition.pressure) //  气压
            this.$set(this.item, 'tips', condition.tips) //  一句话提示
            this.$set(this.item, 'iconpath', 'weather/W' + condition.icon + '.png') //  图标路径
            this.$set(this.item, 'tempDay', forecast.tempDay) //  最高气温(白天温度)
            this.$set(this.item, 'tempNight', forecast.tempNight) //  最低气温(夜间温度)
          })
        }
      }
    },
    async loadRecord () {
      // let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      console.log(tenant)
      let farmland = await this.queryFarmland()
      console.log(farmland)
      let aggregate = [
        {
          $match: {
            _removed: {
              $exists: false
            },
            tenant: tenant._id,
            farmland: farmland._id
          }
        },
        {
          $sort: {
            startOn: -1,
            endOn: -1,
            updatedOn: -1
          }
        },
        {
          $skip: 0
        },
        {
          $limit: 3
        },
        {
          $lookup: {
            from: 'dict',
            localField: 'source',
            foreignField: 'code',
            as: 'sourceObject'
          }
        },
        {
          $unwind: {
            path: '$sourceObject',
            preserveNullAndEmptyArrays: true
          }
        },
        {
          $unwind: {
            path: '$category',
            preserveNullAndEmptyArrays: true
          }
        },
        {
          $lookup: {
            from: 'farmingCategory',
            let: {
              tenant: '$tenant',
              code: '$category'
            },
            pipeline: [
              {
                $match: {
                  $expr: {
                    $and: [
                      {
                        $not: [
                          {
                            $ifNull: [
                              '$_removed',
                              false
                            ]
                          }
                        ]
                      },
                      {
                        $eq: [
                          '$tenant',
                          '$$tenant'
                        ]
                      },
                      {
                        $eq: [
                          '$code',
                          '$$code'
                        ]
                      }
                    ]
                  }
                }
              }
            ],
            as: 'farmingCategory'
          }
        },
        {
          $unwind: {
            path: '$farmingCategory',
            preserveNullAndEmptyArrays: true
          }
        },
        {
          $group: {
            _id: '$_id',
            content: {
              $first: '$content'
            },
            source: {
              $first: '$sourceObject'
            },
            startOn: {
              $first: '$startOn'
            },
            endOn: {
              $first: '$endOn'
            },
            category: {
              $push: '$farmingCategory'
            }
          }
        },
        {
          $sort: {
            startOn: -1,
            endOn: -1,
            updatedOn: -1
          }
        }
      ]
      try {
        mongo.db('fwk').collection('record').aggregate(aggregate).then(records => {
          this.recordList = records || []
        })
      } catch (err) {
        console.debug(err)
        this.showMessageError('加载农事记录数据服务异常')
      }
    },
    async loadFarmWork () {
      try {
        // 加载农事记录
        let userid = this.queryFwkUserId()
        if (!userid) return
        let parcelid = this.queryFwkParcelId()
        if (!parcelid) return
        let tenantid = this.queryIssTenantId()
        if (!tenantid) return
        this.queryFwkFarmWorkListPaging(undefined, 1, 3).then(resWork => {
          let work = resWork['data']
          this.pagination.total = resWork['page']['total'] // 总页数
          this.pagination.page = resWork['page']['currentPage'] // 当前页
          this.recordList = work || []
          this.recordList.filter(item => (item.creattime || '').trim().match(/(\d{2}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})$/i)).forEach(item => {
            // item.creattime_ = item.creattime.substring(0, item.creattime.lastIndexOf(':'))
            item.creattime_ = item.creattime.split(' ')[0]
          })
          if (resWork['flag'] === 0) {
            this.showMessageError('加载农事记录数据异常')
          }
        })
      } catch (err) {
        console.debug(err)
        this.showMessageError('加载农事记录数据服务异常')
      }
    },
    async loadPlant () {
      try {
        // 加载种植信息
        let parcelid = this.queryFwkParcelId()
        if (!parcelid) return
        const requestParams = Object.assign({ islogin: 1 }, { parcelid: parcelid })
        excuteApis(requestParams, constant.parcelApis, 'parcelPlant', 'query').then((resPlant) => {
          let plants = resPlant['data'] && resPlant['data']['flag'] === 1 ? resPlant['data']['data'] : null
          if (plants && plants.length > 0) {
            this.plantList = plants || []
            this.plant = plants.reduce((p, plant) => {
              p['categoryname'] += plant['categoryname'] ? plant['categoryname'] + ',' : ''
              p['area'] += plant['area'] ? plant['area'] : 0
              p['number'] += plant['number'] ? plant['number'] : 0
              return p
            }, { categoryname: '', area: 0, number: 0 })
            // 剔除最后一个分号
            this.plant['categoryname'] = this.plant['categoryname'].substr(0, this.plant['categoryname'].length - 1)
          }
          if (resPlant['data']['flag'] === 0) {
            this.showMessageError('加载种植数据异常')
          }
        })
      } catch (err) {
        this.showMessageError('加载种植数据服务异常')
      }
    },
    async loadIotTypes () {
      // 4: 视频；2：土壤检测 1：微气象
      let resTypes = await mongo.db('iot').collection('base.device.type').find({ filter: { tag: { $in: [1, 2, 4] }, status: 1, _removed: { $exists: false } } })
      if (!resTypes && !resTypes['items']) return
      resTypes['items'].forEach(props => {
        let type = {}
        type[props['_id']['$oid']] = 0
        this.types[props['tag'] + ''].push(type)
      })
      // 微气象设备列表
      this.types['1'].forEach(type => {
        this.weatherDevices.push(Object.keys(type)[0])
      })
      // 土壤检测设备列表
      this.types['2'].forEach(type => {
        this.soilDevices.push(Object.keys(type)[0])
      })
      // 视频
      this.types['4'].forEach(type => {
        // console.log(Object.keys(type)[0])
        this.vedioDevices.push(Object.keys(type)[0])
      })
      this.statDeviceStatus()
    },
    // 统计3类设备各状态数据
    async statDeviceStatus () {
      if (!this.types && !this.types.length) return
      this.loadIoTDevicesStat(1)
      this.loadIoTDevicesStat(2)
      this.loadIoTDevicesStat(4)
    },
    // 查询地块物联网3类设备(1. 微气象 2. 土壤检测 4 视频)
    async loadIoTDevicesStat (flag) {
      // console.log(this.weatherDevices)
      // console.log(this.soilDevices)
      // console.log(this.vedioDevices)
      let types = []
      let totalDevices = { alarmNums: 0, faultNums: 0, other: 0, total: 0 }
      let deviceTypes = []
      // map deviceTypes
      if (flag === 1) {
        deviceTypes = this.weatherDevices
      } else if (flag === 2) {
        deviceTypes = this.soilDevices
      } else if (flag === 4) {
        deviceTypes = this.vedioDevices
      } else {
        console.log('你妹，这种设备类型不参与统计')
      }
      deviceTypes.forEach(item => {
        types.push(Object.assign({}, { $oid: item }))
      })
      // get deviceNums
      let landid = this.queryIssFarmlandId()
      if (!landid) return
      let { items } = await mongo.db('iot').collection('tenant.device').find({ filter: { farmland: { $oid: landid }, type: { $in: types }, status: 1 } })
      if (items.length === 0) return
      Object.assign(totalDevices, items.reduce((r, item) => {
        let status = item['accessState'] && item['accessState']['status'] ? item['accessState']['status'] : []
        status.includes(2) ? r['alarmNums'] += 1 : (status.includes(3) ? r['faultNums'] += 1 : r['other'] += 1)
        r['total'] += 1
        return r
      }, { alarmNums: 0, faultNums: 0, other: 0, total: 0 }))
      // dispatch deviceNums
      if (flag === 1) {
        Object.assign(this.totalWeatherDevices, totalDevices)
      } else if (flag === 2) {
        Object.assign(this.totalSoilDevices, totalDevices)
      } else if (flag === 4) {
        Object.assign(this.totalVedioDevices, totalDevices)
      } else {
        console.log('你妹，这种设备类型不参与统计')
      }
    },
    showSoilReport () {
      this.$router.push({ name: 'fwk-measure_soil' })
    },
    showPlantingList () {
      // 显示种植信息页
      this.$router.push({ name: 'planting-list' })
    },
    showPlantingDetails (id) {
      // 显示种植信息页
      this.$router.push({ name: 'planting-details', params: { _id: id } })
    },
    showFarmlandDetail () {
      // 显示地块信息页
      this.$router.push({ name: 'farmland-details' })
    },
    showWeatherDetail () {
      // 显示天气详情页
      let data = {}
      if (window.native && window.native.getWeatherTag) {
        data = JSON.parse(window.native.getWeatherTag() || '{}') // 获取默认天气标识
      }
      console.debug(this.data)
      let farmland = this.$store.state['tenant.farmland'].item
      let lon = farmland['lng']
      let lat = farmland['lat']
      let oid = farmland['_id']['$oid']
      this.$store.commit('set', { name: 'app', k: 'lon', v: lon })
      this.$store.commit('set', { name: 'app', k: 'lat', v: lat })
      this.$store.commit('set', { name: 'app', k: 'landid', v: oid })
      if (Object.keys(data).length > 0) {
        if (data.landid === oid) {
          if (data.flag === 0) {
            this.$router.push({ name: 'public-weather', query: { lon: lon, lat: lat, farmland: oid } })
          } else {
            this.$router.push({ name: 'bloomsky', query: { id: data.id } })
          }
        } else {
          console.log(this.bloomskyDevList)
          console.log(this.bloomskyDevList.length)
          if (this.bloomskyDevList.length > 0) {
            this.$router.push({ name: 'bloomsky', query: { id: this.bloomskyDevList[0]._id.$oid } })
          } else {
            this.$router.push({ name: 'public-weather', query: { lon: lon, lat: lat, farmland: oid } })
          }
        }
      } else {
        console.log(this.bloomskyDevList)
        console.log(this.bloomskyDevList.length)
        if (this.bloomskyDevList.length > 0) {
          this.$router.push({ name: 'bloomsky', query: { id: this.bloomskyDevList[0]._id.$oid } })
        } else {
          this.$router.push({ name: 'public-weather', query: { lon: lon, lat: lat, farmland: oid } })
        }
      }
    },
    showIotDetail (categoryId) {
      // 显示物联网设备明细
      this.$router.push({ name: 'iot-list_view', params: { categoryId: categoryId } })
    },
    showFarmWorkList () {
      // 显示农事记录
      // this.$router.push({ name: 'fwk-farm_work' })
      try {
        window.native.listFarmRecord(this.queryIssFarmlandId())
      } catch (e) {
        this.showMessageError(e.message)
      }
    },
    showFarmWorkDetail (id) {
      // 显示农事记录详情
      // this.$router.push({ name: 'fwk-farm_work-detail', params: { _id: id } })
      try {
        window.native.viewFarmRecord(id, this.queryIssStaffId())
      } catch (e) {
        this.showMessageError(e.message)
      }
    },
    async queryBloomSkyDev (id) {
      console.log(id)
      let data = await mongo.db('iot').collection('tenant.device').aggregate([{
        $match: {
          _removed: {
            $exists: false
          },
          farmland: {
            $oid: id
          }
        }
      }, {
        $lookup: {
          from: 'base.device.type',
          localField: 'type',
          foreignField: '_id',
          as: 'type'
        }
      }, {
        $match: {
          'type.code': { '$in': [103, 106] }
        }
      }])
      return data
    }
  },
  components: {
    VLandProfile
  }
}
</script>
<style scoped>
.landno {
  background-color: rgba(55, 55, 55, 0.3);
  border-top-right-radius: 12.5px;
  border-bottom-right-radius: 12.5px;
  color: white;
  font-size: 14px;
  line-height: 25px;
  position: absolute;
  z-index: 1;
  top: 10px;
  left: 0px;
  padding: 0 10px;
  height: 25px;
}
.soilreport {
  background-color: rgb(77, 188, 209);
  border-radius: 4px;
  color: rgb(255, 255, 255);
  position: absolute;
  z-index: 1;
  top: 10px;
  right: 10px;
  font-size: 14px;
  width: 38px;
  height: 43px;
  text-align: center;
}
.soilreport-1 {
  background-color: #02ad58;
}
.position {
  line-height: 32px;
  font-size: 14px;
  color: rgb(51, 51, 51);
  height: 32px;
  padding-left: 5px;
  padding-right: 5px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.plant {
  line-height: 32px;
  font-size: 14px;
  color: rgb(51, 51, 51);
  height: 28px;
  padding-left: 2px;
  padding-right: 10px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.details {
  color: #7fb9e6;
}
.details-1 {
  color: #02ad58;
}
.titlelable {
  font-size: 14px;
  padding-left: 10px;
  font-weight: bold;
  line-height: 24px;
}
.wethercontent {
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  color: white;
  margin-left: 10px;
  margin-right: 10px;
  height: 113px;
}
.weatherright {
  /* border: 1px solid red;
  width: 300px; */
  text-align: left;
  padding-left: 5px;
  margin-right: 10px;
}
.weathercontentback {
  font-size: 13px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  background: -moz-linear-gradient(60deg, #2a3d59, #506b8e);
}
.work {
  width: 100%;
}
.worktime {
  color: rgb(153, 153, 153);
  font-size: 13px;
}
.worktype {
  color: rgb(153, 153, 153);
  font-size: 13px;
  float: right;
}
.workcontent {
  color: rgb(51, 51, 51);
}
.workkind {
  /*background-color: rgb(127, 185, 230);*/
  /*height: 45px;*/
  /*border-radius: 45px;*/
  /*padding: 3px 6px;*/
  /*color: white;*/
  /*font-size: 12px;*/
  /*margin-right: 5px;*/
  display: inline-block;
  margin: 0px 5px 5px 0;
  background-color: #ecf8fa;
  color: #50bed1;
  font-size: 13px;
  padding: 2px 5px;
  text-align: center;
  border-radius: 4px;
}
.workkind-1 {
  background-color: rgba(2, 173, 88, 0.1);
  color: rgb(2, 173, 88);
}
.plantcontent {
  color: rgb(51, 51, 51);
}
.planttitle {
  padding: 0;
  margin: 0;
}
.plantcount {
  /*color: rgb(153, 153, 153);*/
  font-size: 13px;
  float: right;
}
.plantbegintime {
  color: rgb(153, 153, 153);
  font-size: 13px;
}
.plantcycle {
  color: rgb(153, 153, 153);
  font-size: 13px;
  float: right;
}
.splitcolor {
  background-color: rgb(245, 246, 245);
  height: 10px;
  width: 100%;
}
.separator {
  background-color: rgb(246, 245, 246);
  height: 1px;
}
</style>
