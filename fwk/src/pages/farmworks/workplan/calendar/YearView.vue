<template>
  <div>
    <v-container py-0
                 px-0>
      <v-layout class="fc-header-toolbar"
                data-calendar-month="12">
      </v-layout>

    </v-container>
    <v-container py-0
                 px-0>
      <v-layout xs12>
        <v-flex v-for="(label, index) in monthNames"
                xs1
                :key="index"
                class="yearHeader">
          {{ label }}
        </v-flex>
      </v-layout>
      <v-layout xs12>
        <v-container py-0
                     px-0
                     style="position:relative;border: 1px solid #cfd8dc;">
          <v-layout xs12>
            <v-container py-0
                         px-0>
              <v-layout v-for="(i, index) in monthOfRows"
                        class="monthRow"
                        :key="index">
                <v-flex v-for="(month, weekIndex) in monthOfPeriod"
                        :key="weekIndex"
                        @click="onClickDay(month)"
                        xs1
                        class="monthItem">
                </v-flex>
              </v-layout>
            </v-container>
          </v-layout>
          <v-layout class="monthEvent"
                    v-if="events.length > 0">
            <v-container py-0
                         px-0
                         style="width:100%;height:100%">
              <v-layout v-for="(row, index) in rows"
                        class="eventRow"
                        :key="index">
                <v-flex v-for="e in row.events"
                        class="event"
                        :key="e.id"
                        :style="e.style"
                        @click.stop="onClickEvent(e.id)">
                  <div :title="e.title"
                       class="plan-title">{{e.title}}</div>
                </v-flex>
              </v-layout>
            </v-container>
          </v-layout>
        </v-container>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
import moment from 'moment'
/**
 * 深拷贝数据
 */
function deepClone (obj) {
  let _obj = JSON.stringify(obj)
  let objClone = JSON.parse(_obj)
  return objClone
}
export default {
  name: 'YearView',
  props: {
    events: {
      type: Array,
      default () {
        return []
      }
    },
    mountType: {
      type: Number,
      default () {
        return 0
      }
    }
  },
  data () {
    return {
      monthNames: [
        '一月',
        '二月',
        '三月',
        '四月',
        '五月',
        '六月',
        '七月',
        '八月',
        '九月',
        '十月',
        '十一月',
        '十二月'
      ],
      monthOfPeriod: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      monthOfRows: [],
      rows: [],
      oldRows: [],
      cacheEvents: [],
      initCacheEvents: [],
      eventsNewaa: []
    }
  },
  watch: {
    events (v) {
      if (this.mountType === 1) {
        this.getChange()
      }
    }
  },
  created () {
    console.log(22222222)
    this.cacheEvents = deepClone(this.events)
    this.initCacheEvents = deepClone(this.events)
    this.cutEvents(moment().year(), this.initCacheEvents)
  },
  methods: {
    getChange () {
      console.log(3333333333)
      this.oldRows = []
      this.cacheEvents = deepClone(this.events)
      this.initCacheEvents = deepClone(this.events)
      let year
      if (this.$store.state.planYear) {
        year = this.$store.state.planYear
      } else {
        year = moment().year()
      }
      this.cutEvents(year, this.initCacheEvents)
    },
    transformEvent (eventsNew) {
      this.eventsNewaa = deepClone(eventsNew)
      this.eventsNewaa.forEach(element => {
        this.checkOccupied(element, this.oldRows)
      })
      console.log(this.oldRows)
      let oldRows = deepClone(this.oldRows)
      oldRows.forEach(element => {
        element.events.forEach(item => {
          let startTime = item.begintime.substr(0, 10)
          let endTime = item.endtime.substr(0, 10)
          item.begintime = moment(startTime).month() + 1
          item.endtime = moment(endTime).month() + 1
          let width = item.endtime - item.begintime + 1
          item.style = `left:${(item.begintime - 1) * (100 / 12)}%;width:${width * (100 / 12)}%;background-color:${item.color};color:#fff;border-color:${item.color}`
        })
      })
      this.rows = oldRows
      if (this.rows.length > 0) {
        if (this.rows.length < 10) {
          this.monthOfRows = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        } else {
          this.monthOfRows = this.rows.length
        }
      } else {
        this.monthOfRows = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
      }
    },
    /*
     *检查是否发生重叠
    */
    async checkOccupied (arr, rows) {
      let isBetween = false
      if (rows.length > 0) {
        rows.some(function (element) {
          let j = 0
          console.log('每一行循环开始')
          if (element.events.length > 0) {
            let flag = element.events.some(function (item) {
              let startTime = item.begintime.substr(0, 10)
              let endTime = item.endtime.substr(0, 10)
              console.log(startTime)
              console.log(endTime)
              console.log(arr.begintime)
              console.log(arr.endtime)
              if (moment(arr.begintime).isBetween(moment(startTime), moment(endTime)) ||
                moment(arr.endtime).isBetween(moment(startTime), moment(endTime)) ||
                moment(arr.begintime).isSame(moment(startTime), 'month') ||
                moment(arr.begintime).isSame(moment(endTime), 'month') ||
                moment(arr.endtime).isSame(moment(startTime), 'month') ||
                moment(arr.endtime).isSame(moment(endTime), 'month')) {
                isBetween = true
                return true
              } else if (moment(startTime).isBetween(moment(arr.begintime), moment(arr.endtime)) ||
                moment(endTime).isBetween(moment(arr.begintime), moment(arr.endtime))) {
                isBetween = true
                return true
              } else {
                j++
                if (j === element.events.length) {
                  isBetween = false
                  return true
                } else {
                  return false
                }
              }
            })
            if (flag && j === element.events.length && isBetween === false) {
              element.events.push(arr)
              return true
            }
          } else {
            return true
          }
        })
        if (isBetween === true) {
          let row = {
            events: []
          }
          row.events.push(arr)
          this.oldRows.push(row)
        }
      } else {
        let row = {
          events: []
        }
        row.events.push(arr)
        this.oldRows.push(row)
      }
    },
    onClickEvent (id) {
      this.$emit('event-view', id)
    },
    prev () {
      let year
      let ABC = deepClone(this.cacheEvents)
      this.oldRows = []
      if (this.$store.state.planYear) {
        year = this.$store.state.planYear
        this.$store.state.planYear = moment(year, 'YYYY').subtract(1, 'year').year()
      } else {
        year = moment().year()
        this.$store.state.planYear = moment().subtract(1, 'year').year()
      }
      this.$emit('my-event', this.$store.state.planYear)
      this.cutEvents(this.$store.state.planYear, ABC)
    },
    next () {
      let year
      let ABC = deepClone(this.cacheEvents)
      this.oldRows = []
      if (this.$store.state.planYear) {
        year = this.$store.state.planYear
        this.$store.state.planYear = moment(year, 'YYYY').add(1, 'year').year()
      } else {
        year = moment().year()
        this.$store.state.planYear = moment().add(1, 'year').year()
      }
      this.$emit('my-event', this.$store.state.planYear)
      this.cutEvents(this.$store.state.planYear, ABC)
    },
    /**
     * 按年分割事件
     */
    cutEvents (year, events) {
      console.log(year)
      let yearevents = deepClone(events)
      let yearStart = moment(year, 'YYYY').startOf('year').format('YYYY-MM-DD')
      let yearEnd = moment(year, 'YYYY').endOf('year').format('YYYY-MM-DD')
      let alleEventsInYear = []
      yearevents.forEach(element => {
        let startTime = element.begintime.substr(0, 10)
        let endTime = element.endtime.substr(0, 10)
        if (moment(startTime).isBetween(yearStart, yearEnd) || moment(endTime).isBetween(yearStart, yearEnd) ||
          moment(startTime).isSame(moment(yearStart)) || moment(startTime).isSame(moment(yearEnd)) ||
          moment(endTime).isSame(moment(yearStart)) || moment(endTime).isSame(moment(yearEnd))) {
          if ((moment(startTime).isBetween(yearStart, yearEnd) || moment(startTime).isSame(moment(yearStart)) ||
            moment(startTime).isSame(moment(yearEnd))) && moment(startTime).isSame(moment(yearStart), 'year')) {
            if (moment(endTime).isAfter(moment(yearStart), 'year')) {
              element.endtime = moment(yearEnd).format('YYYY-MM-DD HH:mm:ss')
              alleEventsInYear.push(element)
            } else {
              alleEventsInYear.push(element)
            }
          } else if ((moment(endTime).isBetween(yearStart, yearEnd) || moment(endTime).isSame(yearStart) ||
            moment(endTime).isSame(yearEnd)) && moment(endTime).isSame(moment(yearEnd), 'year')) {
            if (moment(startTime).isBefore(moment(yearStart), 'year')) {
              element.begintime = moment(yearStart).format('YYYY-MM-DD HH:mm:ss')
              alleEventsInYear.push(element)
            } else {
              alleEventsInYear.push(element)
            }
          }
        } else {
          if (moment(startTime).isBefore(yearStart, 'year') && moment(endTime).isAfter(yearEnd, 'year')) {
            element.begintime = moment(yearStart).format('YYYY-MM-DD HH:mm:ss')
            element.endtime = moment(yearEnd).format('YYYY-MM-DD HH:mm:ss')
            alleEventsInYear.push(element)
          }
        }
      })
      console.log('符合的')
      console.log(alleEventsInYear)
      this.transformEvent(alleEventsInYear)
    }
  }
}
</script>

<style scoped>
.yearHeader {
  border: 1px solid #cfd8dc;
  text-align: center;
  height: 20px;
  line-height: 20px;
}
.calendar-view .dayList {
  display: flex;
  flex: 0 0 auto;
  flex-flow: row nowrap;
  border: 1px;
}
.event {
  position: absolute;
  top: 5px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  /* border-width: 1px; */
  border-radius: 3px;
  height: 15px;
  line-height: 15px;
  font-size: 0.85em;
}
.eventRow {
  position: relative;
  height: 25px;
  border: 0;
}
.monthRow {
  position: relative;
  height: 25px;
  border: 0;
}
.monthItem {
  border: 1px solid #cfd8dc;
  border-width: 0 1px;
  height: 25px;
}
.monthEvent {
  position: absolute;
  z-index: 1;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}
.fc-header-toolbar {
  height: 250px;
  margin-bottom: 1em;
  background-image: url(/static/plan/year.png);
  background-size: cover;
  background-repeat: no-repeat;
}
.plan-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: left;
}
</style>
