<template>
  <div>
    <div class="loading-init" v-if="!isShow">
      <img src="loading.gif"/>
    </div>
    <v-container style="padding:0px" v-else-if="failed">
      <v-flex text-xs-center style="color:rgb(170, 170, 170);font-size:16px;padding-top:50px">暂无数据</v-flex>
    </v-container>
    <v-container style="padding:0px" v-else>
      <v-layout row wrap :class="{sunnyContainerDay: isSunnyDay, sunnyContainerNight: isSunnyNight, rainContainerDay: isRainDay, rainContainerNight: isRainNight, snowContainerDay: isSnowyDay, snowContainerNight: isSnowyNight}">
        <v-flex xs12 class="runtimePanel">
          <v-layout row justify-center style="height:68px">
            <v-flex xs3 class="text_center">
              <span v-if="condition.icon">
                <img style="width: 68px;height:68px;line-height:68px" :src="condition.icon">
              </span>
            </v-flex>
            <v-flex xs4 class="text_size">
              <span v-if="condition">
                {{condition.temp + '℃'}}
              </span>
            </v-flex>
            <v-flex xs2 class="condition_size">
              <span v-if="condition" style="position:absolute;bottom:13px">
                {{condition.condition}}
              </span>
            </v-flex>
          </v-layout>
        </v-flex>
        <v-flex xs12 class="runtimePanel2">
          <v-layout row justify-center>
            <v-flex xs6 text-xs-center>
              <v-icon style="font-size: 20px;width:20px;height:20px" color="white">place</v-icon>
              <span v-if="city">
                {{city.pname + city.name}}
              </span>
            </v-flex>
            <v-flex xs2 style="width:108px">
              <v-layout row wrap>
                <v-flex xs12 class="text_second" text-xs-left>
                  <img src="@/assets/weather/icon/humidity.png" style="width:15px;height:15px"/>
                  <span v-if="condition">
                    {{condition.humidity + '%'}}
                  </span>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex xs2 style="width:108px">
              <v-layout row wrap>
                <v-flex xs12 class="text_second">
                  <img src="@/assets/weather/icon/wind.png" style="width:15px;height:15px;left:0px"/>
                  <span v-if="condition.windLevel">
                    {{condition.windLevel + '级'}}
                  </span>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex xs2>
            </v-flex>
          </v-layout>
          <!-- <v-layout style="padding-top:20px">
            <v-flex xs6></v-flex>
            <v-flex xs6 text-xs-left>
              <v-icon style="font-size: 20px;width:20px;height:20px" color="white">place</v-icon>
              <span v-if="city">
                {{city.pname + city.name}}
              </span>
            </v-flex>
          </v-layout> -->
          <!-- <v-layout row justify-center>
            <v-flex xs4 style="width:108px">
              <v-layout row wrap>
                <v-flex xs12 class="text_second">
                  <span v-if="todayAndTomorrow[0]">
                    {{todayAndTomorrow[0].tempNight + '~' + todayAndTomorrow[0].tempDay}}
                  </span>
                  <span v-if="todayAndTomorrow[0]">
                    {{todayAndTomorrow[0].conditionDay}}
                  </span>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex xs4 style="width:108px">
              <v-layout row wrap>
                <v-flex xs12 class="text_second">
                  <span v-if="condition">
                    {{condition.humidity + '%'}}
                  </span>
                  <span v-if="condition">
                    湿度
                  </span>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex xs4 style="width:108px">
              <v-layout row wrap>
                <v-flex xs12 class="text_second">
                  <span v-if="condition.windLevel">
                    {{condition.windLevel + '级'}}
                  </span>
                  <span v-if="condition">
                    {{condition.windDir}}
                  </span>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout> -->
        </v-flex>
      </v-layout>
      <v-layout row :class="{sunnyday_container: isSunnyDay, sunnynight_container: isSunnyNight, rainday_container: isRainDay, rainnight_container: isRainNight, snowyday_container: isSnowyDay, snowynight_container: isSnowyNight, toanto :true}">
        <v-flex xs6 style="color:#fff;font-size:14px" v-for="item in todayAndTomorrow" :key="item.id">
          <v-layout row>
            <v-flex xs4 style="text-align:left">
              {{item.name}}
            </v-flex>
            <v-flex xs8 style="text-align:center">
              {{item.tempDay + '℃~' + item.tempNight + '℃'}}
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs4 style="text-align:left;line-height:30px">
              {{item.conditionDay}}
            </v-flex>
            <v-flex xs8 style="text-align:center;height:30px;width:30px;line-height:30px;">
              <img :src="item.conditionIdDay" style="vertical-align: middle;height: auto;max-height: 100%;"/>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <v-layout row wrap :class="{sunnyday_container: isSunnyDay, sunnynight_container: isSunnyNight, rainday_container: isRainDay, rainnight_container: isRainNight, snowyday_container: isSnowyDay, snowynight_container: isSnowyNight}">
        <v-flex xs12 :class="{sunnyday: isSunnyDay, sunnynight: isSunnyNight, rainday: isRainDay, rainnigh: isRainNight, snowyday: isSnowyDay, snowynight: isSnowyNight}">
        </v-flex>
        <v-flex xs12 style="height:60px;text-align:center;color:#fff;font-size: 18px;padding-top:15px">
          <span>未来24小时预报</span>
        </v-flex>
        <v-divider light></v-divider>
        <v-flex xs12 style="overflow-x:auto; overflow-y:hidden">
          <v-layout row wrap>
            <v-flex xs12>
              <div class="weather-container1">
                <div class="oneDayWeather1" v-for="item in xhourly" :key="item.id">
                  <div class="dayDate1">{{item.hour}}</div>
                  <div class="curWeatherPic1"><img style="width:35px;height:35px" :src="item.iconDay"></div>
                  <div class="tem1">{{item.temp + '℃'}}</div>
                </div>
              </div>
            </v-flex>
            <v-flex xs12>
              <ve-histogram :data="chartData" width="1860px" height="100px" :grid="grid" :tooltip="tooltip" :extend="chartExtend" :legend-visible="false" v-if="flag"></ve-histogram>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>
      <v-layout row wrap :class="{sunnyday_container: isSunnyDay, sunnynight_container: isSunnyNight, rainday_container: isRainDay, rainnight_container: isRainNight, snowyday_container: isSnowyDay, snowynight_container: isSnowyNight}">
        <v-flex xs12 :class="{sunnyday: isSunnyDay, sunnynight: isSunnyNight, rainday: isRainDay, rainnigh: isRainNight, snowyday: isSnowyDay, snowynight: isSnowyNight}">
        </v-flex>
        <v-flex xs12 style="overflow-x:auto; overflow-y:hidden;height:350px;margin:15px 0px">
          <forecastFifDay :fifteenforecasts="forecast" v-if="flag"></forecastFifDay>
        </v-flex>
      </v-layout>
      <v-layout column :class="{sunnyDayfooter: isSunnyDay, sunnyNightfooter: isSunnyNight, rainDayfooter: isRainDay, rainNightfooter: isRainNight, snowyDayfooter: isSnowyDay, snowyNightfooter: isSnowyNight}">
        <v-flex xs12 class="alarmClass">
          <!-- <v-layout row>
            <v-flex xs12> -->
          <v-icon></v-icon>
          <span>{{alarm.name || ''}}</span>
          <span>{{alarm.level || '无'}}</span>
          <span>预警</span>
            <!-- </v-flex>
          </v-layout> -->
        </v-flex>
        <v-flex xs12 class="alarmContent" v-if="alarm.content">
          <!-- <span style="color:#ffffff;margin:5px 0px 5px 15px;font-size:12px">{{alarm.content}}</span> -->
          <p style="color:#ffffff;padding:15px 15px 0px;font-size:14px">{{alarm.content}}</p>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import forecastFifDay from '@/pages/weather/pweather/NextFifteenDaysForecast'
// import axios from 'axios'
import { excuteApis } from '@/apis'
import constant from '@/tools/constant'
export default {
  name: 'public-weather',
  data () {
    this.windSpeed = [[0, 1], [1, 5], [6, 11], [12, 19], [20, 28], [29, 38], [39, 49], [50, 61], [62, 74], [75, 88], [89, 102], [103, 117], [118, 134], [135, 149], [150, 166], [167, 183], [184, 201], [202, 220]]
    return {
      isShow: false,
      failed: false,
      isSunnyDay: false,
      isSunnyNight: false,
      isRainDay: false,
      isRainNight: false,
      isSnowyDay: false,
      isSnowyNight: false,
      city: {}, // 地名
      condition: [], // 实时天气
      forecast: {}, // 未来十五天天气预报
      hourly: [], // 24小时天气预报
      xhourly: [], // 24小时天气预报
      todayAndTomorrow: [], // 今明两天天气预报
      alarm: [], // 天气预警
      flag: false // 解决子组件异步数据的问题
    }
  },
  methods: {
    async getNewsFn () {
      let lon = this.$route.query.lon || this.$store.state.app.lon
      let lat = this.$route.query.lat || this.$store.state.app.lat
      let sunnyDay = ['0', '1', '18', '20', '29', '45']
      let sunnyNight = ['30', '31', '32', '35', '36', '46']
      let rainDay = ['2', '3', '4', '5', '6', '7', '8', '9', '10', '19']
      let rainnight = ['33']
      let snowyDay = ['5', '13', '14', '15', '16', '17']
      await excuteApis(Object.assign({}, {lon: lon, lat: lat, weatherCategorys: 'condition,alert,forecast15days,forecast24hours'}),
        constant.commonApiUrls, 'common', 'queryMoJiWeather').then((res) => {
        if (res.data.flag === 1) {
          console.log(res)
          this.isShow = true
          this.city = res.data.data.city
          this.forecast = res.data.data.forecast
          for (let i = 2; i < 4; i++) {
            let day = []
            day.id = i
            if (i === 2) {
              day.name = '今天'
            } else {
              day.name = '明天'
            }
            day.conditionIdDay = './weather/W' + this.forecast[i].conditionIdDay + '.png'
            day.tempDay = this.forecast[i].tempDay
            day.tempNight = this.forecast[i].tempNight
            day.conditionDay = this.forecast[i].conditionDay
            this.todayAndTomorrow.push(day)
          }
          let day = res.data.data.condition
          if (sunnyDay.includes(res.data.data.condition.icon)) {
            this.isSunnyDay = true
            this.isSunnyNight = false
            this.isRainDay = false
            this.isRainNight = false
            this.isSnowyDay = false
            this.isSnowyNight = false
          }
          if (sunnyNight.includes(res.data.data.condition.icon)) {
            this.isSunnyNight = true
            this.isSunnyDay = false
            this.isRainDay = false
            this.isRainNight = false
            this.isSnowyDay = false
            this.isSnowyNight = false
          }
          if (rainDay.includes(res.data.data.condition.icon)) {
            console.log('afafafaf')
            this.isRainDay = true
            this.isSunnyNight = false
            this.isSunnyDay = false
            this.isRainNight = false
            this.isSnowyDay = false
            this.isSnowyNight = false
          }
          if (rainnight.includes(res.data.data.condition.icon)) {
            this.isRainNight = true
            this.isRainDay = false
            this.isSunnyNight = false
            this.isSunnyDay = false
            this.isSnowyDay = false
            this.isSnowyNight = false
          }
          if (snowyDay.includes(res.data.data.condition.icon)) {
            this.isSnowyDay = true
            this.isRainNight = false
            this.isRainDay = false
            this.isSunnyNight = false
            this.isSunnyDay = false
            this.isSnowyNight = false
          }
          if (res.data.data.alert) {
            this.alarm = res.data.data.alert[0]
          }
          day.icon = './weather/W' + day.icon + '.png'
          this.condition = day
          let hourData = res.data.data.hourly
          for (let i = 2; i < hourData.length; i++) {
            let hourArr = {}
            let hour = {}
            hourArr.hour = hourData[i].hour
            let windSpeed = hourData[i].windSpeed
            for (let i = 0; i < this.windSpeed.length; i++) {
              if (hourData[i].windSpeed >= this.windSpeed[i][0] && hourData[i].windSpeed <= this.windSpeed[i][1]) windSpeed = i
            }
            hourArr.windSpeed = windSpeed
            hour.hour = hourData[i].hour + '时'
            hour.temp = hourData[i].temp
            hour.iconDay = './weather/W' + hourData[i].iconDay + '.png'
            this.xhourly.push(hour)
            this.hourly.push(hourArr)
          }
          console.log(this.xhourly)
          console.log(this.hourly)
          this.flag = true
        } else {
          this.isShow = true
          this.failed = true
        }
      }).catch(v => {
        this.isShow = true
        this.failed = true
      })
    }
  },
  components: {
    forecastFifDay
  },
  created () {
    let _this = this
    this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    let btnObj = [{ icon: 'swap_horiz', fn () { _this.$router.push({name: 'weather-switch'}) } }]
    this.$store.commit('set', { name: 'app', k: 'acts', v: btnObj })
    this.getNewsFn() // 获取数据
    this.chartData = {
      columns: ['hour', 'windSpeed'],
      rows: this.hourly
    }
    this.tooltip = {
      show: false
      // trigger: 'item',
      // position: 'top',
      // alwaysShowContent: true,
      // formatter: '{c0}' + '级'
    }
    this.grid = {
      left: 0
    }
    this.yAxis = {
      show: false,
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      }
    }
    this.chartExtend = {
      series: {
        type: 'bar',
        barWidth: 68,
        barCategoryGap: '1%',
        itemStyle: {
          color: '#ffffff',
          opacity: 0.6,
          barBorderRadius: [5, 5, 0, 0]
        },
        label: {
          show: true,
          position: 'top',
          formatter: '{c0}' + '级'
        }
      },
      xAxis: {
        axisLabel: {
          show: false
        },
        axisTick: {
          show: false
        }
      },
      yAxis: {
        show: false,
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        }
      }
    }
  }
}
</script>
<style>
.sunnyContainerDay {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/15-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.sunnyContainerNight {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/14-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.rainContainerDay {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/13-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.rainContainerNight {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/12-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.snowContainerDay {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/16-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.snowContainerNight {
  background-size:100% 250px;
  background-image: url('../../../assets/weather/weather_bg/17-img.png');
  background-repeat: no-repeat;
  height: 250px;
}
.sunnyday_container {
  background-color: #1d91c5;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.sunnynight_container {
  background-color: #577c9a;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.rainday_container {
  background-color: #6c7b8d;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.rainnight_container {
  background-color: #435c7a;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.snowyday_container {
  background-color: #55b1d5;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.snowynight_container {
  background-color: #6c7b8d;
  /* padding: 0 25px; */
  position: relative;
  overflow: hidden;
}
.sunnyday {
  height:10px;
  background-color:#20a1db
}
.sunnynight {
  height:10px;
  background-color:#618aac
}
.rainday {
  height:10px;
  background-color:#78899d
}
.rainnight {
  height:10px;
  background-color:#4b6788
}
.snowyday {
  height:10px;
  background-color:#5fc5ed
}
.snowynight {
  height:10px;
  background-color:#78899d
}
.toanto {
  padding: 10px 25px 10px 25px;
}
.runtimePanel {
  margin-top: 70px;
  height: 55px;
  color: #fff;
}
.runtimePanel2 {
  height: 122px;
  margin-top: 10px;
  color: #fff;
}
.text_center {
  width: 55px;
  height:55px;
  line-height:55px;
  text-align:center
}
.text_second {
  text-align:center;
  color: #fff;
  font-size: 15px;
}
.text_size {
  font-size: 55px;
  line-height: 55px;
  width: auto;
  height:55px;
  text-align: center;
  margin-left: 22.5px;
  margin-right: 15px
}
.condition_size {
  font-size: 17px;
  width: 17px;
  /* text-align: center; */
  position:relative
}
.dayDate1{
  position: absolute;
  left:0;
  top: 20px;
  width:100%;
}
.oneDayWeather1 {
  position: relative;
  width:68px;
  height:150px;
  float:left;
  margin-right: 8px;
  color: #fff;
  text-align: center;
  line-height: 20px;
  /* border-right:2px solid rgba(255,255,255,0.25); */
}
.tem1{
  position: absolute;
  left:0;
  top: 80px;
  width:100%;
}
.curWeatherPic1{
  position: absolute;
  left:0;
  top:40px;
  width:100%;
  height:35px;
  background-size:contain;
}
.weather-container1 {
  height: 100px;
  width: 1824px;
  margin-left: 25px;
  position: relative;
  overflow:hidden;
}
.inner-container {
  overflow-x: scroll;
  overflow-y: hidden;
}
 /* for Chrome */
.inner-container::-webkit-scrollbar {
    display: none;
}
.sunnyDayfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/19-img.png');
  background-repeat: no-repeat;
  background-color: #1d91c5
}
.sunnyNightfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/18-img.png');
  background-repeat: no-repeat;
  background-color: #577c9a
}
.rainDayfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/18-img.png');
  background-repeat: no-repeat;
  background-color: #6c7b8d
}
.rainNightfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/18-img.png');
  background-repeat: no-repeat;
  background-color: #435c7a
}
.snowyDayfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/20-img.png');
  background-repeat: no-repeat;
  background-color: #55b1d5
}
.snowyNightfooter {
  background-size:100% 100%;
  min-height: 150px;
  height: 100%;
  background-image: url('../../../assets/weather/weather_bg/21-img.png');
  background-repeat: no-repeat;
  background-color: #6c7b8d
}
.alarmClass {
  color:#fcc85b;
  height: 42.5px;
  text-align: center;
  line-height: 42.5px;
  font-size: 15px;
}
.alarmContent {
  margin: 0px 0px 20px;
  background-color: #93a1b1;
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
