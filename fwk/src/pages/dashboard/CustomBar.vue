<template>
<div id="barContainer">   
  <div class="barTitle">{{chartSetting.title}}</div>
  <div class="divider"></div>
  <div class="statcontent">地块总数：{{landinfo.landsum}}块, 总面积：{{landinfo.landareas}}亩</div>
  <div class="barContent leaflet-control" ref="barContainer">
    <svg width="100%" :height="barH" version="1.1" xmlns="http://www.w3.org/2000/svg" v-if="chartData.length">
      <template v-for="(data, index) in chartData">
        <text x="0" :y="index * (chartSetting.barheight + chartSetting.bargap) + 15" :fill="chartSetting.fillLabel"> {{ data[chartSetting.dimension].substr(0, 4) }} </text>
        <rect x="60" :y="index * (chartSetting.barheight + chartSetting.bargap)" :rx="1" 
            :width="chartSetting.barmaxwidth * numbertopercent(data[chartSetting.metrics]) + chartSetting.barminwidth" :height="chartSetting.barheight" 
            :style="{fill: chartSetting.barcolor, fillOpacity: isme(data), strokeWidth: 1, stroke: chartSetting.barcolor, strokeOpacity: isme(data)}"
            @mouseover="showme(data, $event)" @mouseleave="leaveme"></rect>
        <text :x="chartSetting.barmaxwidth * numbertopercent(data[chartSetting.metrics]) + chartSetting.barminwidth + 65" :y="index * (chartSetting.barheight + chartSetting.bargap) + 15" :fill="chartSetting.barcolor" :fillOpacity="isme(data)"> {{ data[chartSetting['metrics']] }} </text>
      </template>
    </svg>
  </div>
  <!-- <div v-bind:class="{hidebartips: hidetips, showbartips: !hidetips}" v-bind:style="{top: postiontips.y + 'px', left: postiontips.x + 'px'}">
    <span v-html="showtips()"></span>
  </div> -->
</div>
</template>

<script>
export default {
  name: 'v-custom-bar',
  props: {
    chartData: {
      type: Array,
      default: () => []
    },
    chartSetting: {
      type: Object,
      default: () => {
        return {
          barheight: 20,
          barmaxwidth: 150,
          barminwidth: 0,
          bargap: 10,
          dimension: 'addressname',
          metrics: 'totalarea',
          title: '地块分布(亩)',
          fillLabel: 'white',
          barcolor: 'rgba(245,161,0,1)',
          showtips: 'show' // hidden
        }
      }
    },
    landinfo: {
      type: Object,
      default: () => {
        return Object.assign({}, {landsum: 0, landareas: 0})
      }
    }
  },
  data () {
    this.barnumbers = 1
    return {
      windowSize: {x: 0, y: 0},
      items: [],
      currentbar: null,
      hidetips: true,
      postiontips: { x: 0, y: 0 }
    }
  },
  computed: {
    barH () {
      return this.chartData.length * this.chartSetting['barheight'] + (this.chartData.length - 1) * this.chartSetting['bargap'] + this.chartSetting['bargap']
    }
  },
  watch: {},
  mouted () {},
  methods: {
    showme (data, event) {
      this.currentbar = data
      this.hidetips = false
      Object.assign(this.postiontips, {x: event.offsetX + 40, y: event.offsetY - 10 - this.$refs['barContainer'].offsetHeight})
    },
    leaveme () {
      this.hidetips = true
      this.currentbar = ''
    },
    isme (data) {
      let opacity = 0.8
      if (data && this.currentbar) {
        let curDimension = this.currentbar[this.chartSetting['dimension']]
        if (data[this.chartSetting['dimension']].indexOf(curDimension) >= 0) {
          opacity = 1
        }
      }
      return opacity
    },
    showtips () {
      let html = ''
      let metrics = this.chartSetting['metrics']
      if (this.currentbar && this.currentbar[this.chartSetting['dimension']]) {
        html = '<div>' + metrics + '</div>'
        html += '<p>' + this.currentbar[this.chartSetting['dimension']] + ': ' + this.currentbar[this.chartSetting['metrics']] + '</p>'
      } else {
        html = ''
      }
      return html
    },
    numbertopercent (num) {
      let percent = 0
      if (this.chartData && this.chartData.length > 0) {
        let curarr = []
        this.chartData.forEach(item => {
          let data = item[this.chartSetting['metrics']] + 0
          curarr.push(parseFloat(data))
        })
        let total = curarr.reduce((total, sum) => {
          return total + sum
        })
        percent = num * 1.00 / total
      }
      return percent
    }
  },
  created () {}
}
</script>

<style lang="less" scoped>
#barContainer {
  height: 100%;
  padding-bottom: 20px;
  .barTitle {
    color: white;
    font-size: 18px;
    text-align: center;
    padding-top: 10px;
    padding-bottom: 10px;
  }
  .statcontent {
    color: white;
    font-size: 15px;
    text-align: center;
    line-height: 55px;
  }
  .divider {
    border: 1px solid grey;
    width: 100%
  }
  .barContent {
    overflow-x: hidden;
    overflow-y: hidden;
    height: 65%;
    // padding-bottom: 5px;
    margin-left: 5px!important;
  }
  .barContent:hover {
    overflow-y: auto;
  }
  .barContent::-webkit-scrollbar {
    background: transparent;
    width: 1px;
  }
  .barContent::-webkit-scrollbar-thumb {
    background-color: #bdbdbd;
  }
  .hidebartips {
  display: none;
  }
  .showbartips {
    position: relative;
    width: 120px;
    height: 50px;
    font-size: 14px;
    color: rgb(255, 255, 255);
    background: rgba(0, 0, 0, 0.3);
    border: 1px solid #999;
    display: inline-block;
    z-index: 9999;
  }
}
</style>
