<template>
	<div class="calendar-view" :class="[
			'locale-' + languageCode(displayLocale),
			'locale-' + displayLocale,
			'y' + periodStart.getFullYear(),
			'm' + paddedMonth(periodStart),
			'period-' + displayPeriodUom,
			'periodCount-' + displayPeriodCount,
			{
				past: isPastMonth(periodStart),
				future: isFutureMonth(periodStart),
				noIntl: !supportsIntl,
			}]"
  >

		<!-- 插槽 -->
		<slot name="header">
			<div class="header">
				<!--<div class="nav">

					<v-btn class="previousYear"
							@click="onIncrementPeriod(-12)"
							:disabled="!isPeriodIncrementAllowed(-12)"
					/>
					<v-btn class="previousPeriod"
							@click="onIncrementPeriod(-displayPeriodCount)"
							:disabled="!isPeriodIncrementAllowed(-displayPeriodCount)"
					/>
					<v-btn class="nextPeriod"
							@click="onIncrementPeriod(displayPeriodCount)"
							:disabled="!isPeriodIncrementAllowed(displayPeriodCount)"
					/>
					<v-btn class="nextYear"
							@click="onIncrementPeriod(12)"
							:disabled="!isPeriodIncrementAllowed(12)"
					/>
					<v-btn class="currentPeriod"
							@click="onClickCurrentPeriod"
					/>
				</div>-->

        <!-- 月 周 日 年 按钮 -->
        <v-btn-toggle class="dateWrapper" v-model="toggleBtn">
          <v-btn flat @click="cutWeek" ref="week">周</v-btn>
          <v-btn flat @click="cutMonth">月</v-btn>
          <v-btn flat @click="cutYear" ref="year">年</v-btn>
        </v-btn-toggle>

        <!-- 当前日期 -->
				<div class="periodLabel"
             id="periodLabel"
					   :class="{
						 singleYear: periodStart.getFullYear() === periodEnd.getFullYear(),
						 singleMonth: isSameMonth(periodStart, periodEnd) }"
        >
          <!-- 时间后退按钮 -->
          <v-icon class="previousPeriod prevIcon"
                  @click="onIncrementPeriod(-displayPeriodCount)"
                  :disabled="!isPeriodIncrementAllowed(-displayPeriodCount)"
                  large
          >
            keyboard_arrow_left
          </v-icon>

					<div class="startMonth">{{ monthNames[periodStart.getMonth()] }}</div>
					<div class="startDay">{{ periodStart.getDate() }}</div>
					<div class="startYear">{{ periodStart.getFullYear() }}</div>
					<div class="endMonth">{{ monthNames[periodEnd.getMonth()] }}</div>
					<div class="endDay">{{ periodEnd.getDate() }}</div>
					<div class="endYear">{{ periodEnd.getFullYear() }}</div>

          <!-- 时间前进按钮 -->
          <v-icon class="nextPeriod nextIcon"
                  @click="onIncrementPeriod(displayPeriodCount)"
                  :disabled="!isPeriodIncrementAllowed(displayPeriodCount)"
                  large
          >
            keyboard_arrow_right
          </v-icon>

				</div>

        <!-- 搜索框 -->
        <div class="searchWrapper">
          <v-text-field placeholder="输入计划内容进行搜索"
                        v-model="searchData"
                        append-icon="fa_search"
          >
          </v-text-field>
        </div>

        <!-- 添加按钮 -->
        <v-btn @click="happenClick" color="primary"><v-icon>add</v-icon>添加农事计划</v-btn>
			</div>
		</slot>
		<div v-if="displayPeriodUom !== 'year'">
			<div class="dayList">
				<template v-for="(label, index) in weekdayNames">
					<slot name="dayHeader" :index="index" :label="label">
						<div class="day" :key="index" :class="'dow'+index">{{ label }}</div>
					</slot>
				</template>
			</div>
			<div class="weeks">
				<div v-for="(weekStart, weekIndex) in weeksOfPeriod"
					:key="weekIndex"
					class="week">
					<div v-for="(day, dayIndex) in daysOfWeek(weekStart)" class="day"
						:key="dayIndex"
						:class="[
							'dow' + day.getDay(),
							'd' + isoYearMonthDay(day),
							'd' + isoMonthDay(day),
							'd' + paddedDay(day),
							'instance' + instanceOfMonth(day),
							{
								outsideOfMonth: !isSameMonth(day, defaultedShowDate),
								today: isSameDate(day, today()),
								past: isInPast(day),
								future: isInFuture(day),
								last: isLastDayOfMonth(day),
								lastInstance: isLastInstanceOfMonth(day),
							}
						]"
						@click="onClickDay(day)"
						@drop.prevent="onDrop(day, $event)"
						@dragover.prevent="onDragOver(day)"
						@dragenter.prevent="onDragEnter(day, $event)"
						@dragleave.prevent="onDragLeave(day, $event)">
						<div class="content">
							<slot name="dayContent" :day="day">
								<div class="date">{{ day.getDate() }}</div>
							</slot>
						</div>
					</div>
					<template v-for="e in getWeekEvents(weekStart)">
						<slot name="event" :event="e" :weekStartDate="weekStart">
							<div
								class="event"
								:key="e.id"
								:draggable="enableDragDrop"
								:class="e.classes"
								:title="e.title"
								:style="'z-index:' + ((weekIndex + 1) * 2 + 1)"
								@dragstart="onDragStart(e)"
								@click.stop="onClickEvent(e)"
								v-html="getEventTitle(e)"/>
						</slot>
					</template>
				</div>
			</div>
		</div>
		<div v-else>
			<div class="dayList">
				<template v-for="(label, index) in monthNames">
					<slot name="dayHeader" :index="index" :label="label">
						<div class="day" :key="index" :class="'dow'+index">{{ label }}</div>
					</slot>
				</template>
			</div>
			<v-layout xs12>
				<v-flex v-for="(month, weekIndex) in monthOfPeriod"
					:key="weekIndex"
					@click="onClickDay(month)" xs1 style="text-align:center">
					<v-flex>
						<slot name="dayContent" :day="month">
							<div class="date">{{ month }}</div>
						</slot>
					</v-flex>
					<!-- <template v-for="e in getWeekEvents(weekStart)">
						<slot name="event" :event="e" :weekStartDate="weekStart">
							<div
								class="event"
								:key="e.id"
								:draggable="enableDragDrop"
								:class="e.classes"
								:title="e.title"
								:style="'z-index:' + ((weekIndex + 1) * 2 + 1)"
								@dragstart="onDragStart(e)"
								@click.stop="onClickEvent(e)"
								v-html="getEventTitle(e)"/>
						</slot>
					</template> -->
				</v-flex>
			</v-layout>
		</div>
		
	</div>
</template>

<script>
import CalendarMathMixin from './CalendarMathMixin'
import debounce from 'throttle-debounce/debounce'

export default {
  name: 'CalendarView',

  mixins: [CalendarMathMixin],

  props: {
    showDate: {
      type: Date,
      default () {
        return undefined
      }
    },
    displayPeriodUom: {
      type: String,
      default () {
        return 'month'
      }
    },
    /**
     * 显示的时间个数
     */
    displayPeriodCount: {
      type: Number,
      default () {
        return 1
      }
    },
    locale: {
      type: String,
      default () {
        return undefined
      }
    },
    monthNameFormat: {
      type: String,
      default () {
        return 'long'
      }
    },
    weekdayNameFormat: {
      type: String,
      default () {
        return 'short'
      }
    },
    showEventTimes: {
      type: Boolean,
      default () {
        return false
      }
    },
    timeFormatOptions: {
      type: Object,
      default () {
        return {}
      }
    },
    disablePast: {
      type: Boolean,
      default () {
        return false
      }
    },
    disableFuture: {
      type: Boolean,
      default () {
        return false
      }
    },
    enableDragDrop: {
      type: Boolean,
      default () {
        return false
      }
    },
    startingDayOfWeek: {
      type: Number,
      default () {
        return 0
      }
    },
    events: {
      type: Array,
      default () {
        return []
      }
    }
  },

  data: function () {
    return {
      currentDragEvent: null,
      toggleBtn: 1,
      searchData: null, // 输入框
      count: 0, // 计数执行次数
      monthOfPeriod: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    }
  },

  computed: {
		/*
		Props cannot default to computed/method returns, so create defaulted version of this
		property and use it rather than the bare prop (Vue Issue #6013).
		*/
    displayLocale () {
      return this.locale || this.getDefaultBrowserLocale()
    },

		/*
		ShowDate, but defaulted to today. Needed both for periodStart below and for the
		'outside of month' class.
		*/
    defaultedShowDate () {
      return this.showDate || this.today()
    },

		/*
		  由于showDate默认为今天，因此计算日期的开始和结束日期。
		*/
    periodStart () {
      return this.beginningOfPeriod(
				this.defaultedShowDate,
				this.displayPeriodUom,
				this.startingDayOfWeek
			)
    },

    periodEnd () {
      return this.addDays(
				this.incrementPeriod(
					this.periodStart,
					this.displayPeriodUom,
					this.displayPeriodCount
				),
				-1
			)
    },

		/*
		  对于月份和年份视图，网格中显示的第一个和最后一个日期可能不是
      与期望的期间相同，因为期间可能不会在本周的开始日均匀开始和止损
		*/
    displayFirstDate () {
      return this.beginningOfWeek(this.periodStart, this.startingDayOfWeek)
    },

    displayLastDate () {
      return this.endOfWeek(this.periodEnd, this.startingDayOfWeek)
    },

		/*
		  创建一个日期数组，其中每个日期代表应该在当前时间段的视图中呈现的一周的开始时间。
		*/
    weeksOfPeriod () {
			// Returns an array of object representing the date of the beginning of each week
			// included in the view.
      const numWeeks = Math.floor(
				(this.dayDiff(this.displayFirstDate, this.displayLastDate) + 1) / 7
			)
      return Array(numWeeks)
				.fill()
				.map((_, i) => this.addDays(this.displayFirstDate, i * 7))
    },

		/* Cache the names based on current locale and format settings */
    monthNames () {
      return this.getFormattedMonthNames(
				this.displayLocale,
				this.monthNameFormat
			)
    },
    weekdayNames () {
      return this.getFormattedWeekdayNames(
				this.displayLocale,
				this.weekdayNameFormat,
				this.startingDayOfWeek
			)
    },

		/*
		Before doing with with events, we need a normalized version of each event that has a
		parsed startDate, a parsed or defaulted endDate, and a defaulted title and id. We
		also need a guarantee that the `classes` attribute is an array, not a single class
		string or null. A reference to the original event is kept.
		*/
    fixedEvents () {
      var vm = this
      return this.events.map(function (event) {
				// Classes may be a string, an array, or null. Coalesce to an array
        let fixedClasses = []
        if (event.classes) {
          if (Array.isArray(event.classes)) {
            fixedClasses = [...event.classes]
          } else {
            fixedClasses = [event.classes]
          }
        }
        return {
          originalEvent: event,
          startDate: vm.toLocalDate(event.startDate),
          endDate: vm.toLocalDate(event.endDate || event.startDate),
          classes: fixedClasses,
          title: event.title || 'Untitled',
          id:
						event.id ||
						'e' +
							Math.random()
								.toString(36)
								.substr(2, 10)
        }
      })
    }
  },
  mounted () {
    // 传递 当前月份时间到 父组件
    let begintime = this.periodStart
    let endtime = this.periodEnd
    this.$emit('currentDate', {begintime: begintime, endtime: endtime})
  },
  methods: {
		// ******************************
		// UI Events
		// ******************************

    onClickDay (day) {
      if (this.disablePast && this.isInPast(day)) return
      if (this.disableFuture && this.isInFuture(day)) return
      this.$emit('click-date', day)
      this.$emit('clickDay', day) // Deprecated
    },

    onClickEvent (e, day) {
      this.$emit('clickEvent', e.originalEvent, day) // Deprecated
      this.$emit('click-event', e.originalEvent, day)
    },

    onClickCurrentPeriod () {
      const newValue = this.beginningOfPeriod(
				this.today(),
				this.displayPeriodUom,
				this.startingDayOfWeek
			)
      this.$emit('setShowDate', newValue) // Deprecated
      this.$emit('show-date-change', newValue)
    },

		// ******************************
		// Date Periods
		// ******************************

    /**
     * 点击时间前进按钮切换日期
     */
    onIncrementPeriod (count) {
      const d = this.getIncrementedPeriod(count, 'next')
      if (d != null) {
        this.$emit('setShowDate', d) // Deprecated
        this.$emit('show-date-change', d)
        this.$emit('changeTime', {begintime: d, endtime: this.endtime})
      }
      this.searchData = null // 清空输入框值
    },

		/**
		 * 返回给定数量的当前显示单位向前或向后移动的当前显示日期的日期 如果所述移动将导致a返回null
     * 不允许显示期间。
     * @param select(next) 标识它是由哪个方法调用的
		 */
    getIncrementedPeriod (count, select) {
      const newStartDate = this.incrementPeriod(
				this.periodStart,
				this.displayPeriodUom,
				count
			)
      const newEndDate = this.incrementPeriod(
				newStartDate,
				this.displayPeriodUom,
				this.displayPeriodCount
			)
      if (select === 'next') { // 自己处理
        this.endtime = newEndDate
      }
      if (this.disablePast && newEndDate <= this.today()) return null
      if (this.disableFuture && newStartDate > this.today()) return null
      return newStartDate
    },
    isPeriodIncrementAllowed (count) {
      return this.getIncrementedPeriod(count) !== null
    },
		// ******************************
		// Drag and drop events
		// ******************************

    onDragStart (calendarEvent) {
      if (!this.enableDragDrop) return false
			// Not using dataTransfer.setData to store the event ID because it (a) doesn't allow access to the data being
			// dragged during dragover, dragenter, and dragleave events, and because storing an ID requires an unnecessary
			// lookup. This does limit the drop zones to areas within this instance of this component.
      this.currentDragEvent = calendarEvent
      this.$emit('dragEventStart', calendarEvent.originalEvent, calendarEvent) // Deprecated
      this.$emit('drag-start', calendarEvent.originalEvent, calendarEvent)
      return true
    },

    handleEvent (bubbleEventName, bubbleParam) {
      if (!this.enableDragDrop) return false
      if (!this.currentDragEvent) return false // shouldn't happen
      this.$emit(
				bubbleEventName,
				this.currentDragEvent.originalEvent,
				bubbleParam
			)
      return true
    },

    onDragOver (day) {
      this.handleEvent('dragEventOverDate', day) // Deprecated
      this.handleEvent('drag-over-date', day)
    },

    onDragEnter (day, windowEvent) {
      if (!this.handleEvent('dragEventEnterDate', day)) return // Deprecated
      if (!this.handleEvent('drag-enter-date', day)) return
      windowEvent.target.classList.add('draghover')
    },

    onDragLeave (day, windowEvent) {
      if (!this.handleEvent('dragEventLeaveDate', day)) return // Deprecated
      if (!this.handleEvent('drag-leave-date', day)) return
      windowEvent.target.classList.remove('draghover')
    },

    onDrop (day, windowEvent) {
      if (!this.handleEvent('dropEventOnDate', day)) return // Deprecated
      if (!this.handleEvent('drop-on-date', day)) return
      windowEvent.target.classList.remove('draghover')
    },

		// ******************************
		// Calendar Events
		// ******************************

    findAndSortEventsInWeek (weekStart) {
			// Return a list of events that INCLUDE any day of a week starting on a
			// particular day. Sorted so the events that start earlier are always
			// shown first.
      const events = this.fixedEvents
      .filter(
        event =>
          event.startDate < this.addDays(weekStart, 7) &&
          event.endDate >= weekStart,
        this
      )
      .sort((a, b) => {
        if (a.startDate < b.startDate) return -1
        if (b.startDate < a.startDate) return 1
        if (a.endDate > b.endDate) return -1
        if (b.endDate > a.endDate) return 1
        return a.id < b.id ? -1 : 1
      })
      return events
    },
    getMonthEvents (monthStart) {
    },
    getWeekEvents (weekStart) {
			// Return a list of events that CONTAIN the week starting on a day.
			// Sorted so the events that start earlier are always shown first.
      console.log(weekStart)
      const events = this.findAndSortEventsInWeek(weekStart)
      const results = []
			// Surely there's a better way, Prettier?
      const eventRows = [
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[],
				[]
      ]
      for (let i = 0; i < events.length; i++) {
        const ep = Object.assign({}, events[i], {
          classes: [...events[i].classes],
          eventRow: 0
        })
        const continued = ep.startDate < weekStart
        const startOffset = continued
					? 0
					: this.dayDiff(weekStart, ep.startDate)
        const span = Math.min(
					7 - startOffset,
					this.dayDiff(this.addDays(weekStart, startOffset), ep.endDate) + 1
				)
        if (continued) ep.classes.push('continued')
        if (this.dayDiff(weekStart, ep.endDate) > 6) ep.classes.push('toBeContinued')
        if (ep.originalEvent.url) ep.classes.push('hasUrl')
        for (let d = 0; d < 7; d++) {
          if (d === startOffset) {
            for (let s = 0; s < 20; s++) {
              if (!eventRows[d][s]) {
                ep.eventRow = s
                eventRows[d][s] = true
                break
              }
            }
          } else if (d < startOffset + span) {
            eventRows[d][ep.eventRow] = true
          }
        }
        ep.classes.push(`offset${startOffset}`)
        ep.classes.push(`span${span}`)
        ep.classes.push(`eventRow${ep.eventRow + 1}`)
        results.push(ep)
      }
      return results
    },

		/*
		Creates the HTML to prefix the event title showing the event's start and/or
		end time. Midnight is not displayed.
		*/
    getFormattedTimeRange (e) {
      const startTime = this.formattedTime(
				e.startDate,
				this.displayLocale,
				this.timeFormatOptions
			)
      const endTime = this.isSameDateTime(e.startDate, e.endDate)
				? ''
				: this.formattedTime(
						e.endDate,
						this.displayLocale,
						this.timeFormatOptions
					)
      const hasStart = startTime !== ''
      const hasEnd = endTime !== ''
      return (
				(hasStart
					? `<span class='startTime${
							hasEnd ? ' hasEndTime' : ''
						}'>${startTime}</span>`
					: '') +
				(hasEnd
					? `<span class='endTime${
							hasStart ? ' hasStartTime' : ''
						}'>${endTime}</span>`
					: '')
      )
    },

    getEventTitle (e) {
      if (!this.showEventTimes) return e.title
      return this.getFormattedTimeRange(e) + e.title
    },

    /**
     * 点击新增农事计划 向外传播点击事件
     */
    happenClick () {
      this.$emit('addPlan')
    },

    /**
     * 点击 月 周 年 向外传播点击事件
     */
    cutMonth () {
      this.searchData = null
      this.$emit('cutMonth')
    },
    cutWeek () {
      this.searchData = null
      this.$emit('cutWeek')
    },
    cutYear () {
      this.searchData = null
      this.$emit('cutYear')
    }
  },

  watch: {
    searchData: debounce(600, function (val) {
      if (val === null) return // 切换时间阻止触发
      let begintime = this.periodStart.format('yyyy-MM-dd hh:mm:ss')
      let endtime = this.periodEnd.format('yyyy-MM-dd hh:mm:ss')
      this.$emit('inputChange', {keyword: val, begintime: begintime, endtime: endtime})
    })
  }
}
</script>
<!--

The CSS below represents only the CSS required for proper rendering (positioning, etc.) and
minimalist default borders and colors. Special-day colors, holiday emoji, event colors,
and decorations like border-radius should be part of a theme.

-->
<style type="text/css">
/* Position/Flex */

/* Make the calendar flex vertically */
.calendar-view {
	display: flex;
	flex-direction: column;
}

.calendar-view .header {
   display: flex;
   flex: 0 1 auto;
   flex-flow: row nowrap;
   align-items: center;
   min-height: 4.5em;
 }

.calendar-view .header .periodLabel {
	display: flex;
	flex: 1 1 auto;
	flex-flow: row nowrap;
	min-height: 1.2em;
}

.calendar-view .dayList {
	display: flex;
	flex: 0 0 auto;
	flex-flow: row nowrap;
}

.calendar-view .dayList .day {
	display: flex;
	flex: 1 1 0;
	flex-flow: row nowrap;
	align-items: center;
	justify-content: center;
}

/* The calendar grid should take up the remaining vertical space */
.calendar-view .weeks {
	display: flex;
	flex: 1 1 auto;
	flex-flow: column nowrap;
	/* Allow grid to scroll if there are too may weeks to fit in the view */
	overflow-y: scroll;
	-ms-overflow-style: none;
}

/* Use flex basis of 0 on week row so all weeks will be same height regardless of content */
.calendar-view .week {
	display: flex;
	flex: 1 1 0;
	flex-flow: row nowrap;
	min-height: 3em;
	/* Allow week events to scroll if they are too tall */
	position: relative;
	width: 100%;
	overflow-y: scroll;
	-ms-overflow-style: none;
}

.calendar-view .weeks::-webkit-scrollbar,
.calendar-view .week::-webkit-scrollbar {
	width: 0px; /* remove scrollbar space */
	background: transparent; /* optional: just make scrollbar invisible */
}

.calendar-view .week .day {
	display: flex;
	flex: 1 1 0;
	/* When week's events are scrolled, keep the day content fixed */
	position: sticky;
	top: 0;
}

.calendar-view .week .day .content {
	position: absolute;
	left: 0;
	top: 0;
	bottom: 0;
	right: 0;
}

.calendar-view .week .day .date {
	float: right;
	clear: both;
}

/* 任务条条 */
.calendar-view .week .event {
	position: absolute;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
  cursor: pointer;
}

/* Header */

.calendar-view .periodLabel .startDay::before,
.calendar-view .periodLabel .endDay::before,
.calendar-view.period-month .periodLabel .startYear::before,
.calendar-view.period-month .periodLabel .endYear::before,
.calendar-view.period-year .periodLabel .endYear::before {
	content: "\00A0";
}

.calendar-view .periodLabel .endMonth::before,
.calendar-view.period-year:not(.periodCount-1) .periodLabel .endYear::before,
.calendar-view.period-week .periodLabel.singleMonth .endDay::before {
	content: "\00A0\2013\00A0";
}

.calendar-view.period-week .periodLabel .startYear::before,
.calendar-view.period-week .periodLabel .endYear::before {
	content: ",\00A0";
}

.calendar-view .periodLabel.singleYear .startYear,
.calendar-view .periodLabel.singleMonth .endMonth,
.calendar-view.period-month .periodLabel .startDay,
.calendar-view.period-month .periodLabel .endDay,
.calendar-view.period-year .periodLabel .startDay,
.calendar-view.period-year .periodLabel .endDay,
.calendar-view.period-year .periodLabel .startMonth,
.calendar-view.period-year .periodLabel .endMonth,
.calendar-view.period-month.periodCount-1 .periodLabel .endMonth,
.calendar-view.period-month.periodCount-1 .periodLabel .startYear,
.calendar-view.period-year.periodCount-1 .periodLabel .startYear {
	display: none;
}

/* Header navigation buttons */

.calendar-view .header .nav .previousPeriod::after {
	content: "<";
}

.calendar-view .header .nav .nextPeriod::after {
	content: ">";
}

.calendar-view .header .nav .previousYear::after {
	content: "<<";
}

.calendar-view .header .nav .nextYear::after {
	content: ">>";
}

.calendar-view .header .nav .currentPeriod {
	display: none;
}

.calendar-view.past .header .nav .currentPeriod,
.calendar-view.future .header .nav .currentPeriod {
	display: inline-block;
}

.calendar-view.past .header .nav .currentPeriod::after {
	content: "\21BB";
}

.calendar-view.future .header .nav .currentPeriod::after {
	content: "\21BA";
}

/* Colors */

.calendar-view .period .day,
.calendar-view .header button {
	background-color: #fff;
}

.calendar-view .header,
.calendar-view .dayList .day {
	background-color: #f0f0f0;
}

.calendar-view .event {
	background-color: #f7f7f7;
}

.calendar-view .header,
.calendar-view .header button,
.calendar-view .dayList,
.calendar-view .weeks,
.calendar-view .week,
.calendar-view .day,
.calendar-view .event {
	border-color: #ddd;
}

/* Event Times */

.calendar-view .event .startTime,
.calendar-view .event .endTime {
	font-weight: bold;
	color: #666;
}

.calendar-view .event .startTime:not(.hasEndTime),
.calendar-view .event .endTime {
	margin-right: 0.4em;
}

.calendar-view .event .endTime::before {
	content: "-";
}

/* Internal Metrics */

.calendar-view,
.calendar-view div,
.calendar-view button {
	box-sizing: border-box;
	line-height: 1em;
	font-size: 1em;
}

.calendar-view .dayList div,
.calendar-view .date,
.calendar-view .event {
	padding: 0.2em;
}

.calendar-view .header .nav,
.calendar-view .header .periodLabel {
	margin: 0.4em 0.6em;
}

.calendar-view .header .nav button,
.calendar-view .header .periodLabel {
	padding: 0.4em 0.6em;
}

/* Allows emoji icons or labels (such as holidays) to be added more easily to specific dates by having the margin set already. */
.calendar-view .day .date::before {
	margin-right: 0.5em;
}

/* Borders */

.calendar-view .weeks {
	border-style: solid;
	border-width: 0 0 1px 1px;
}
.calendar-view .header {
	border-style: solid;
	border-width: 0.05em 0.05em 0 0.05em;
}
.calendar-view .dayList {
	border-style: solid;
	border-width: 0 0 0 1px;
}
.calendar-view .day {
	border-style: solid;
	border-width: 0.05em 0.05em 0 0;
}
.calendar-view .header button,
.calendar-view .event {
	border-style: solid;
	border-width: 0.05em;
}

/* Positioning for event eventRows */

.calendar-view .event.eventRow1 {
	top: 1.4em;
}
.calendar-view .event.eventRow2 {
	top: calc(2 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow3 {
	top: calc(3 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow4 {
	top: calc(4 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow5 {
	top: calc(5 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow6 {
	top: calc(6 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow7 {
	top: calc(7 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow8 {
	top: calc(8 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow9 {
	top: calc(9 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow10 {
	top: calc(10 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow11 {
	top: calc(11 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow12 {
	top: calc(12 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow13 {
	top: calc(13 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow14 {
	top: calc(14 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow15 {
	top: calc(15 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow16 {
	top: calc(16 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow17 {
	top: calc(17 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow18 {
	top: calc(18 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow19 {
	top: calc(19 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow20 {
	top: calc(20 * 1.4em + 0.1em);
}
.calendar-view .event.eventRow0 {
	display: none;
} /* More than 10 eventRows not currently supported */

.calendar-view .event.offset0 {
	left: calc(0.05em);
}
.calendar-view .event.offset1 {
	left: calc((100% / 7) + 0.05em);
}
.calendar-view .event.offset2 {
	left: calc((200% / 7) + 0.05em);
}
.calendar-view .event.offset3 {
	left: calc((300% / 7) + 0.05em);
}
.calendar-view .event.offset4 {
	left: calc((400% / 7) + 0.05em);
}
.calendar-view .event.offset5 {
	left: calc((500% / 7) + 0.05em);
}
.calendar-view .event.offset6 {
	left: calc((600% / 7) + 0.05em);
}

/* Metrics for events spanning dates */

.calendar-view .event.span1 {
	width: calc((100% / 7) - 0.05em);
}
.calendar-view .event.span2 {
	width: calc((200% / 7) - 0.05em);
}
.calendar-view .event.span3 {
	width: calc((300% / 7) - 0.05em);
	text-align: center;
}
.calendar-view .event.span4 {
	width: calc((400% / 7) - 0.05em);
	text-align: center;
}
.calendar-view .event.span5 {
	width: calc((500% / 7) - 0.05em);
	text-align: center;
}
.calendar-view .event.span6 {
	width: calc((600% / 7) - 0.05em);
	text-align: center;
}
.calendar-view .event.span7 {
	width: calc((700% / 7) - 0.05em);
	text-align: center;
}

/* Misc */

.calendar-view .dayList .day {
	text-align: center;
}

.calendar-view .event.hasUrl:hover {
	text-decoration: underline;
}

.calendar-view .event.continued {
	border-left-style: none;
	border-top-left-radius: 0;
	border-bottom-left-radius: 0;
}
/* 以下是我自己定义的样式 */
.menu{
  cursor: pointer;
  margin-right: 10px;
}
.searchWrapper{
  margin-right: 15px;
  margin-top: 12px;
  min-width: 100px;
}
.prevIcon{
  margin-right: 15px;
  cursor: pointer;
}
.nextIcon{
  margin-left: 15px;
  cursor: pointer;
}
#periodLabel{
  justify-content: center;
}
#periodLabel>div{
  line-height: 3em;
}
.dateWrapper{
  margin-left: 15px;
}
</style>
