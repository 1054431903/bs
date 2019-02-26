<template>
  <!-- <div> -->
    <!-- <v-container class="container"> -->
      <v-layout row wrap>
        <v-flex xs12 style="overflow-x:auto; overflow-y:hidden">
          <v-layout row wrap class="weather-container">
            <v-flex xs12 style="position:absolute;top:125px">
              <ve-line :grid="grid" :data="chartData" :extend="extend" width="1120px" height="100px" :legend-visible="false"></ve-line>
            </v-flex>
            <v-flex xs12>
              <div class="oneDayWeather" v-for="item in fifteenDayForecasts" :key="item.id">
                <div class="weekDay">{{item.weekDay}}</div>
                <div class="dayDate">{{item.predictDate}}</div>
                <div class="curWeather">{{item.conditionDay}}</div>
                <div class="curWeatherPic"><img style="width:30px;height:30px" :src="item.conditionIdDay"></div>
                <!-- <div class="highTempNum" :id="'highTempNum' + item.id">{{item.tempDay}}</div>
                <div class="highTemp" :id="'highTemp' + item.id"></div>
                <div class="lowTemp" :id="'lowTemp' + item.id"></div>
                <div class="lowTempNum" :id="'lowTempNum' + item.id">{{item.tempNight}}</div> -->
                <div class="nightWeatherPic"><img style="width:30px;height:30px" :src="item.conditionIdNight"></div>
                <div class="nightWeather">{{item.conditionNight}}</div>
                <div class="nightWeather1">{{item.windDirNight}}</div>
                <div class="nightWeather2">{{item.windLevelNight}}</div>
              </div>
            </v-flex>
          </v-layout>
          <!-- <div class="weather-container">
            <canvas id="myCanvas" width="1650" height="150"></canvas>
            <div class="oneDayWeather" v-for="item in fifteenDayForecasts" :key="item.id">
              <div class="weekDay">周二</div>
              <div class="dayDate">{{item.predictDate}}</div>
              <div class="curWeather">{{item.conditionDay}}</div>
              <div class="curWeatherPic"><img style="width:55px;height:55px" :src="item.conditionIdDay"></div>
              <div class="highTempNum" :id="'highTempNum' + item.id">{{item.tempDay}}</div>
              <div class="highTemp" :id="'highTemp' + item.id"></div>
              <div class="lowTemp" :id="'lowTemp' + item.id"></div>
              <div class="lowTempNum" :id="'lowTempNum' + item.id">{{item.tempNight}}</div>
              <div class="nightWeatherPic"><img style="width:55px;height:55px" :src="item.conditionIdNight"></div>
              <div class="nightWeather">{{item.conditionNight}}</div>
              <div class="nightWeather1">{{item.windDirNight}}</div>
              <div class="nightWeather2">{{item.windLevelNight}}</div>
            </div>
          </div> -->
        </v-flex>
      </v-layout>
    <!-- </v-container> -->
  <!-- </div> -->
</template>

<script>
export default {
  name: 'public-weather',
  props: {
    fifteenforecasts: {}
  },
  data () {
    return {
      temp: [],
      fifteenDayForecasts: [],
      fifteendayHighTemperature: [],
      fifteendayLowTemperature: []
    }
  },
  created () {
    let fifteenForecast = this.fifteenforecasts
    for (let i = 0; i < fifteenForecast.length; i++) {
      // this.fifteendayHighTemperature.push(fifteenForecast[i].tempDay)
      // this.fifteendayLowTemperature.push(fifteenForecast[i].tempNight)
      let date = {}
      date.predictDate = fifteenForecast[i].predictDate
      date.tempDay = fifteenForecast[i].tempDay
      date.tempNight = fifteenForecast[i].tempNight
      this.temp.push(date)
      let newDate = fifteenForecast[i].predictDate
      let dateArr = newDate.split('-')
      let weekDay = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
      let week = new Date(fifteenForecast[i].predictDate).getDay()
      fifteenForecast[i].weekDay = weekDay[week]
      fifteenForecast[i].predictDate = dateArr[1] + '-' + dateArr[2]
      fifteenForecast[i].conditionIdDay = './weather/W' + fifteenForecast[i].conditionIdDay + '.png'
      fifteenForecast[i].conditionIdNight = './weather/W' + fifteenForecast[i].conditionIdNight + '.png'
      fifteenForecast[i].id = i
    }
    this.fifteenDayForecasts = fifteenForecast
    this.chartData = {
      columns: ['predictDate', 'tempDay', 'tempNight'],
      rows: this.temp
    }
    this.grid = {
      left: '1%',
      right: '3%',
      top: '20%',
      bottom: '1%'
    }
    this.extend = {
      series (v) {
        v.forEach(i => {
          console.log(i)
          if (i.name === 'tempDay') {
            i.label = {
              normal: {
                show: true,
                color: '#fff',
                position: 'top',
                fontSize: 14
                // formatter: '{c0}' + '℃'
              }
            }
            i.smooth = false
            i.lineStyle = {
              color: '#fff'
            }
            i.itemStyle = {
              color: '#fff'
            }
          } else {
            i.label = {
              normal: {
                show: true,
                color: '#fff',
                position: 'bottom',
                fontSize: 14
                // formatter: '{c}' + '℃'
              }
            }
            i.smooth = false
            i.lineStyle = {
              color: '#fff'
            }
            i.itemStyle = {
              color: '#fff'
            }
          }
        })
        return v
      },
      // series: {
      //   label: {
      //     normal: {
      //       show: true,
      //       color: '#fff',
      //       position: 'top'
      //       // formatter: '{c}' + '℃'
      //     }
      //   },
      //   smooth: false,
      //   lineStyle: {
      //     color: '#fff'
      //   },
      //   itemStyle: {
      //     color: '#fff'
      //   }
      // },
      xAxis: {
        show: false,
        boundaryGap: false
        // axisLabel: {
        //   show: false
        // },
        // axisTick: {
        //   show: false
        // }
      },
      yAxis: {
        show: false
      // axisTick: {
      //   show: false
      // },
      // splitLine: {
      //   show: false
      // }
      }
    }
  },
  mounted () {
    // this.init()
  },
  methods: {
    init () {
      let canvas = document.getElementById('myCanvas')
      let canvasTop = canvas.offsetTop
      let ctx = canvas.getContext('2d')
      let sixdayHighTemperature = this.fifteendayHighTemperature
      let sixdayLowTemperature = this.fifteendayLowTemperature
      let picHeight = 150
      let maxHighTemp = this.maxNum(sixdayHighTemperature)
      let minLowTemp = this.minNum(sixdayLowTemperature)
      let maxRange = maxHighTemp - minLowTemp
      ctx.strokeStyle = '#fff'
      ctx.lineWidth = 2
      this.drawHighTemp(ctx, sixdayHighTemperature, canvasTop, maxHighTemp, picHeight, maxRange)
      this.drawLowTemp(ctx, sixdayLowTemperature, canvasTop, minLowTemp, picHeight, maxRange)
    },
    maxNum (arr) {
      var maxNum = 0
      if (arr.length) {
        for (var i = 0, len = arr.length; i < len; i++) {
          if (i === 0) {
            maxNum = arr[0]
          } else if (maxNum < arr[i]) {
            maxNum = arr[i]
          }
        }
      }
      return maxNum
    },
    minNum (arr) {
      var minNum = 0
      if (arr.length) {
        for (var i = 0, len = arr.length; i < len; i++) {
          if (i === 0) {
            minNum = arr[0]
          } else if (minNum > arr[i]) {
            minNum = arr[i]
          }
        }
      }
      return minNum
    },
    drawHighTemp (ctx, temps, canvasTop, maxHighTemp, picHeight, maxRange) {
      let drawX = 0
      let drawY = 0
      let distance = Math.floor(picHeight / maxRange)
      for (let i = 0, len = temps.length; i < len; i++) {
        drawX = i * 102 + 50
        drawY = (maxHighTemp - temps[i]) * distance
        document.getElementById('highTemp' + i).style.top = (canvasTop + drawY + 265) + 'px'
        document.getElementById('highTempNum' + i).style.top = (canvasTop + drawY - 25) + 'px'
        if (i === 0) {
          ctx.moveTo(drawX, drawY)
        } else {
          ctx.lineTo(drawX, drawY)
        }
      }
      ctx.stroke()
      for (let i = 0, len = temps.length; i < len; i++) {
        drawX = i * 102 + 50
        drawY = (maxHighTemp - temps[i]) * distance
        this.drawArc(ctx, drawX, drawY)
      }
    },
    drawArc (ctx, x, y) {
      ctx.beginPath()
      ctx.arc(x, y, 3, 0, Math.PI * 2)
      ctx.fillStyle = 'white'
      ctx.fill()
      ctx.closePath()
    },
    drawLowTemp (ctx, temps, canvasTop, minLowTemp, picHeight, maxRange) {
      let drawX = 0
      let drawY = 0
      let distance = Math.floor(picHeight / maxRange)
      for (let i = 0, len = temps.length; i < len; i++) {
        drawX = i * 102 + 50
        drawY = picHeight - ((temps[i] - minLowTemp) * distance)
        document.getElementById('lowTemp' + i).style.top = (canvasTop + drawY + 265) + 'px'
        document.getElementById('lowTempNum' + i).style.top = (canvasTop + drawY + 5) + 'px'
        if (i === 0) {
          ctx.moveTo(drawX, drawY)
        } else {
          ctx.lineTo(drawX, drawY)
        }
      }
      ctx.stroke()
      for (let i = 0, len = temps.length; i < len; i++) {
        drawX = i * 102 + 50
        drawY = picHeight - ((temps[i] - minLowTemp) * distance)
        this.drawArc(ctx, drawX, drawY)
      }
    }
  }
}
</script>
<style>
.main {
  padding: 0.1rem;
  color: #fff;
  border: 2px solid rgba(255,255,255,0.25);
}
.weather-container {
  height: 500px;
  width: 1120px;
  margin: auto;
  position: relative
}
.oneDayWeather {
  position: relative;
  width:70px;
  height:500px;
  float:left;
  color: #fff;
  text-align: center;
  line-height: 20px;
}
.oneDayWeather:last-child{
  border:none;
}
.oneDayWeather:active{
  background:rgba(0,0,0,0.2);
}
.pastDay{
  opacity: 0.6;
}
.weekDay{
  position: absolute;
  left:0;
  top:0;
  width:100%;
}
.curWeather{
  position: absolute;
  top:40px;
  width:100%;
}
.nightWeather{
  position: absolute;
  bottom:200px;
  width:100%;
}
.nightWeather1{
  position: absolute;
  left:0;
  bottom:180px;
  width:100%;
}
.nightWeather2{
  position: absolute;
  left:0;
  bottom:160px;
  width:100%;
}
.curWeatherPic{
  position: absolute;
  left:0;
  top:70px;
  width:100%;
  height:30px;
  background-size:contain;
}
.nightWeatherPic{
  position: absolute;
  left:0;
  bottom:230px;
  width:100%;
  height:30px;
  background-size:contain;
}
.runtime{
  position: absolute;
  left:0;
  top:-240px;
  width:55px;
  height:10px;
}
.runtimeNum{
  position: absolute;
  left:0;
  top:-20em;
  width:100%;
  height:20px;
  text-indent: 15px
}
.highTemp,.lowTemp{
  position: absolute;
  left:0;
  top:-240px;
  width:100%;
  height:10px;
}
.highTempNum,.lowTempNum{
  position: absolute;
  left:0;
  top:-20em;
  width:100%;
  height:20px;
  text-indent: 15px
}
.dayWeatherPic{
  position: absolute;
  left:0;
  bottom:40px;
  width:100%;
  height:30px;
  background-size:contain;
}
.dayWeather{
  position: absolute;
  left:0;
  bottom:20px;
  width:100%;
}
.dayDate{
  position: absolute;
  left:0;
  top: 20px;
  width:100%;
}
#myCanvas{
  position:absolute;
  top:125px;
  left:0;
}
#hourCanvas{
  position:absolute;
  top:25px;
  left:0;
}
</style>
