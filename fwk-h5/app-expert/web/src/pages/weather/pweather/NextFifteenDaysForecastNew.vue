<template>
  <div>
    <v-container class="container">
      <v-layout row wrap>
        <v-flex xs12>
          <ve-line :data="chartDataLine" :settings="chartSettingsLine" :xAxis="xAxisLine" :yAxis="yAxisLine"  :legend-visible="false" :tooltip-visible="false"></ve-line>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'public-day-weather',
  created () {
    this.chartDataLine = {
      columns: ['日期', '高温', '低温'],
      rows: [
        { '日期': '1月1日', '高温': 33, '低温': 19 },
        { '日期': '1月2日', '高温': 34, '低温': 21 },
        { '日期': '1月3日', '高温': 33, '低温': 22 },
        { '日期': '1月4日', '高温': 32, '低温': 14 },
        { '日期': '1月5日', '高温': 30, '低温': 28 },
        { '日期': '1月6日', '高温': 28, '低温': 22 },
        { '日期': '1月7日', '高温': 36, '低温': 23 },
        { '日期': '1月7日', '高温': 36, '低温': 23 },
        { '日期': '1月7日', '高温': 36, '低温': 23 },
        { '日期': '1月7日', '高温': 36, '低温': 23 },
        { '日期': '1月7日', '高温': 36, '低温': 23 },
        { '日期': '1月7日', '高温': 36, '低温': 23 }
      ]
    }
    this.chartSettingsLine = {
      metrics: ['高温', '低温'],
      // dimension: ['日期', '日期'],
      label: {
        normal: {
          show: true
        }
      }
    }
    this.xAxisLine = [{
      show: true,
      type: 'category',
      axisLabel: {
        inside: true
      },
      data: ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '3日', '4日', '5日', '6日', '7日']
    },
    {
      // offset: -250,
      show: true,
      type: 'category',
      data: ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '3日', '4日', '5日', '6日', '7日'],
      splitArea: {
        interval: 1,
        show: true
      },
      axisLabel: {
        inside: true,
        margin: 20,
        formatter: '{value}'
        // formatter: function (value) {
        //   return '{value|' + value + '}'
        // },
        // rich: {
        //   value: {
        //     lineHeight: 30,
        //     align: 'center'
        //   },
        //   day: {
        //     height: 40,
        //     width: 40,
        //     backgroundColor: {
        //       image: 'img/background.23c5f618.jpg'
        //     }
        //   }
        // }
      }
    }]
    this.yAxisLine = {
      show: false,
      min: 13,
      max: 40,
      interval: 10000
    }
    this.dataZoomLine = [
      {
        type: 'inside',
        show: true
      },
      {
        type: 'inside',
        show: true
      }
    ]
  }
}
</script>
