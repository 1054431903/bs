<template lang="pug">
v-card.pt-1.mb-4(class="page-content")
  v-flex(xs12 sm12 md12 lg12)
    v-layout(row)
      v-flex(xs12 sm12 md2 lg2 xl1)
        v-select(
            v-bind:items="ownerships"
            v-model="ownership"
            item-text="dictname"
            item-value="dictvalue"
            label="地块从属"
            single-line
            no-data-text="无"
            class="hideoverflow"
        )
      v-flex.ml-1(xs12 sm12 md10 lg5 xl5)
        v-areaselect(:curAddressCode.sync="curAreaCode")
      v-flex.ml-1(xs3 sm3 md2 lg2 xl2)
        v-text-field(
            append-icon="search"
            v-model="keywords"
            title="输入地块编号及名称关键词查询"
            label="输入地块编号及名称关键词查询"
            single-line
            hide-details
            clearable
            @keyup.enter="fetchData(1)"
          )
      v-flex
          .pt-1
          v-btn(color="primary" @click="fetchData(1)") 查询    
      v-flex(class="text-sm-right")
          .pt-1
          v-btn(flat icon color="grey darken-1" @click="showImportDialog" title="批量导入地块信息" v-if="$authed('POST','lands/batchimport')")
            v-icon folder
          v-btn(router small fab dark color="red darken-1" :to="{name: 'addland'}", v-if="$authed('POST','lands/addland')" title="新增地块")
            v-icon add
    v-flex(xs12 sm12 md12 lg12)
      v-layout(row)
        v-flex(xs6 sm6 md6 lg6 class="text-sm-left") <span style="line-height: 50px;">地块总数量：{{ totalSum }}&nbsp;&nbsp;块&nbsp;&nbsp;&nbsp;&nbsp;<span v-if="totalArea">地块总面积：{{ totalArea.toFixed(2) }}&nbsp;&nbsp;亩</span></span>
    v-flex.py-1(xs12 sm12 md12 lg12 xl12)
      v-data-table(:headers='columns', :items='items', no-data-text="暂无数据", hide-actions)
        template(slot='items', slot-scope='props')
          tr(style="cursor: pointer"  @click="showLandInfo(props.item.id)")
            td
              template
                v-layout(row)
                  v-flex.pa-3(xs12 sm12 md12 lg12)
                    div(class="profilecontainer" :style="{backgroundImage: 'url(static/land/list/ditu.png)'}")
                      div(class="parcelno")
                        v-flex(xs12 sm12 md12 lg12) {{props.item.parcelno}}
                      v-landprofile(:lanlngs="props.item.coords" class="profile" v-bind:class="{noprofile: !props.item.coords}" :size="100")
            td
              template
                v-layout.py-1(row)
                  v-flex(sm12 lg12)
                    span(style="font-weight:bold;") {{ props.item.parcelname }}
                v-layout.py-1(row)
                  v-flex(sm12 lg12)
                    template(v-for="note in props.item.notes")
                      div(v-if="note['color'] === 'orange'" :style="[orange, noteStyle]" :title="note['title']") {{ note['title'] }}
                      div(v-else-if="note['color'] === 'yellow'" :style="[yellow, noteStyle]" :title="note['title']") {{ note['title'] }}
                      div(v-else-if="note['color'] === 'blue'" :style="[blue, noteStyle]" :title="note['title']") {{ note['title'] }}
                      div(v-else-if="note['color'] === 'white'" :style="[white, noteStyle]" :title="note['title']") {{ note['title'] }}
                      div(v-else :style="[red, noteStyle]" :title="note['title']") {{ note['title'] }}
                // v-layout.py-1(row v-if="false")
                //   v-avatar(size="25px")
                //     v-icon power_settings_new
                v-layout.py-1(row)
                  v-flex(sm6 lg5 xl3)  地块从属:
                  v-flex(sm10 lg10 xl10) {{ props.item.dependencename ? props.item.dependencename : '--'}}
                v-layout.py-1(row)
                  v-flex(sm6 lg5 xl3)  占地面积:
                  v-flex(sm10 lg10 xl10) {{ props.item.area ? props.item.area.toFixed(2) : '--'}}&nbsp;&nbsp;亩
                v-layout.py-1(row)
                  v-flex(sm6 lg5 xl3)  品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:
                  v-flex(sm10 lg10 xl10) {{ props.item.categoryname ? props.item.categoryname : '--'}}
                v-layout.py-1(row)
                  v-flex(sm6 lg5 xl3)  地块负责人:
                  v-flex(sm10 lg10 xl10) <div :title="showResponsibleUserInfo(props.item.responsibleuser)" class="landpos">{{ showResponsibleUserInfo(props.item.responsibleuser) }}</div>
                v-layout.py-1(row)
                  v-flex(sm6 lg5 xl3)  地块位置:
                  v-flex(sm10 lg10 xl10) <div :title="props.item.addressfullname" class="landpos">{{ props.item.addressfullname }}</div>  
            td
              template
                div(v-if="props.item.hasweatherdata === true" class="weather")
                  v-layout(row)
                    v-flex(xs12 sm12 md12 lg12 text-xs-left)
                      v-avatar(size="14px")
                        img(src=`static/land/list/weizhi.png` class="positionImg")
                      span &nbsp;&nbsp;{{props.item.weathercityname}}({{chinesesDay(props.item.updatetime)}}更新)
                  v-layout(row)
                    v-flex(xs12 sm12 md6 lg4 xl3)
                      span(class="display-3") {{props.item.temp}}°
                    v-flex.pt-4(xs12 sm6 md3 lg3 xl2)  
                      //- v-avatar(size="40px" class="light-blue")
                      //-   img(:src="props.item.iconpath")
                      <div class="imgContainer">
                        <img :src="props.item.iconpath" width="auto" height="30px" style="margin: 5px 5px;"/> 
                      </div>
                    v-flex.pt-2.mt-4(xs12 sm12 md3 lg4 xl4)
                      span(class="display-0") {{props.item.condition}}   
                  v-layout(row)
                    v-flex(xs12 sm12 md12 lg12)
                      v-avatar(size="15px")
                        img(src=`static/land/list/fengdu.png`)
                      span &nbsp;&nbsp;{{props.item.windDir}}&nbsp;&nbsp;{{props.item.windLevel}} 级
                      v-avatar.ml-4(size="15px")
                        img(src=`static/land/list/shidu.png`)
                      span &nbsp;&nbsp;湿度&nbsp;&nbsp;{{props.item.humidity}}%
                div(v-else class="weather")
                  v-layout(row)
                    v-flex(xs5)
                      div(class="noweather") 暂无天气数据
            td
              template
                v-layout(row justify-center)
                  v-flex(xs12 sm12 md12 lg12 text-xs-center)
                    // v-btn(title="查看" fab small icon :to="{name: 'viewland', query: {id: props.item.id}}" v-if="$authed('GET','lands/viewland')")
                    v-btn(title="查看" fab small icon @click.stop="showLandInfo(props.item.id)" v-if="$authed('GET','lands/viewland')")
                      v-icon() remove_red_eye
                v-layout(row justify-center)
                  v-flex(xs12 sm12 md12 lg12 text-xs-center)
                    // v-btn(title="编辑" fab small icon :to="{name: 'editland', query: {id: props.item.supportid}}" v-if="$authed('PUT','lands/editland')")
                    v-btn(title="编辑" fab small icon @click.stop="editLandInfo(props.item.id)" v-if="$authed('PUT','lands/editland')")
                      v-icon() edit
                v-layout(row justify-center)
                  v-flex(xs12 sm12 md12 lg12 text-xs-center)    
                    v-btn(title="删除" fab small icon, @click.stop="removeParcel(props.item)" v-if="$authed('DELETE','lands/deleteland')")
                      v-icon(color="red darken-2") delete
    v-flex(xs12)
      v-layout(row justify-center)
        v-flex(xs12 sm12 md9 lg9)              
          v-custompagination(:pagination.sync="pagination")
    v-layout(row, justify-center)
      v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
        div {{prompt.body}}
        template(slot="btnActions")
          v-divider
          v-card-actions
            v-spacer
            v-btn(flat, color="primary", @click.native.stop="Cancel") 取消
            v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定
    v-layout(row, justify-center)
      v-modaldialog(:isShow.sync="excelimport" title="批量导入地块信息" :showCloseBtn="true" :modalMaxWidth="600" :bodyHeight="180")
        v-layout(row)
          v-flex(xs12) 1.下载地块信息导入模板，批量填写地块信息：
            span
              a(href="/static/land/template/农事系统地块信息批量导入模版.xlsx" target="_blank") 点击下载导入模板
        .py-2
        v-layout(row)
          v-flex(xs12) 2.上传填写好的地块信息表：
            v-btn(icon small flat @click="readCsvFile")
              v-icon cloud_upload
            span(class="underline" @click="readCsvFile") 点击上传文件
            v-progress-circular(v-if="importFarmland.processing" indeterminate color="primary")
        v-layout(row)
          input(type="file" name="file" accept=".xlsx,.xls" @change="importByCsv" style="display: none;" ref="input")
          textarea(id="convertJsonContainor" @click="saveIntoDb" style="display: none;" ref="containor")
        .py-1
        v-layout(row)
          v-flex(xs12)
            span(class="errortips") {{errortips}}
</template>

<script>
import { excuteApis } from '@/apis'
import { isArray, getChinesesDay, formatStr, deAreasCode } from '@/utils'
import { CLand } from '@/models/land'
import { Toast } from '@/models/toast'
import XLSX from 'xlsx'
import mongo, { config as MongoConfig } from '@vehiclecloud/mongo-rest-client'
import redis, { config as RedisConfig } from '@vehiclecloud/redis-rest-client'
import conf from '@/conf'

MongoConfig({
  iss: {
    database: conf.Mongo.iss.database,
    base: conf.Mongo.iss.base
  },
  iot: {
    database: conf.Mongo.iot.database,
    base: conf.Mongo.iot.base
  },
  default: {
    database: conf.Mongo.iss.database,
    base: conf.Mongo.iss.base
  }
})

RedisConfig(conf.Redis.base)

const COLORS_MAP = {
  '黄色': 'yellow',
  '红色': 'red',
  '蓝色': 'blue',
  '橙色': 'orange',
  '白色': 'white'
}

export default {
  data () {
    return {
      errortips: '',
      checkValid: true,
      excelimport: false,
      importFarmland: {
        dialog: false,
        processing: false,
        finished: false,
        result: {
          total: 0,
          success: 0
        }
      },
      theme: 'primary',
      ownerships: [],
      ownership: null,
      provinces: [],
      province: null,
      cities: [],
      city: null,
      counties: [],
      county: null,
      towns: [],
      town: null,
      villages: [],
      village: null,
      parcel: {
        id: null,
        name: null,
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
        supportid: '',
        categoryname: '',
        hasweatherdata: false,
        weathercityname: '', // 城市名称
        updatetime: '', // 发布时间
        condition: '', // 实时天气
        temp: '', // 温度
        windDir: '', // 风向
        windLevel: '', // 风级
        humidity: '', // 湿度
        icon: '', // 天气icon
        precipitation: '', // 过去一小时累计降雨量
        pressure: '', // 气压
        tips: '', // 一句话提示
        iconpath: '', // 图标路径
        timeday: '' // 星期几
      },
      parcels: [],
      categoryid: [],
      producttypes: [],
      keywords: null,
      pagination: {
        total: 0,
        page: 1,
        rowsPerPage: 5
      },
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      selectedItemId: null,
      options: {
        create: true,
        update: true,
        delete: true
      },
      latlngs: false,
      /*
        [{latitude: 39.897555, longtitude: 116.410703},
        {latitude: 39.920255, longtitude: 116.403322},
        {latitude: 39.892353, longtitude: 116.402292},
        {latitude: 39.891365, longtitude: 116.389846}]
      */
      totalArea: 0,
      totalSum: 0,
      parcelss: [],
      addresscode: '',
      curAreaCode: '',
      columns: [
        {
          text: '地块编号',
          align: 'center',
          sortable: false
        },
        {
          text: '地块基本信息',
          align: 'center',
          sortable: false
        },
        {
          text: '区域天气实况',
          align: 'center',
          sortable: false
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      items: [],
      totalItems: 0,
      pageItems: [{
        text: '5条',
        value: 5
      },
      {
        text: '10条',
        value: 10
      },
      {
        text: '20条',
        value: 20
      },
      {
        text: '50条',
        value: 50
      }],
      noteStyle: {
        fontSize: '13px',
        color: 'white',
        borderRadius: '5px',
        maxWidth: '30px',
        display: 'inline',
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        padding: '5px 5px'
      },
      orange: {
        backgroundColor: '#ff9800'
      },
      red: {
        backgroundColor: '#F44336'
      },
      yellow: {
        backgroundColor: '#fbc629'
      },
      blue: {
        backgroundColor: '#03A9F4'
      },
      white: {
        backgroundColor: '#cdcdcd'
      },
      notes: [],
      cacheinfo: null,
      isReversal: false
    }
  },
  watch: {
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    }
  },
  methods: {
    fetchLandOwership () {
      let requestParams = {
        dicttype: 'parcel-dependence'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.ownerships = this.ownerships.concat(res.data)
        }
      })
    },
    fetchProductType () {
      excuteApis({}, global.constant.commonApiUrls, 'category', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.producttypes = res.data
          // console.log(this.producttypes)
        }
      })
    },
    async fetchData (page) {
      let requestParams = {
        currentPage: this.pagination.page || 1,
        pageSize: this.pagination.rowsPerPage || 5
      }
      !this.curAreaCode || this.curAreaCode === '100000000000100000000000' ? null : Object.assign(requestParams, deAreasCode(this.curAreaCode))
      this.ownership ? Object.assign(requestParams, { dependenceid: this.ownership }) : null
      this.keywords ? Object.assign(requestParams, { keywords: this.keywords }) : null
      page ? Object.assign(requestParams, { currentPage: page }) : null
      this.toast = new Toast(this, null, null)
      this.toast.show()
      await excuteApis(Object.assign({}, requestParams), global.constant.parcelApis, 'parcel', 'query').then(response => {
        let res = response.data
        if (res.flag === 1) {
          this.parcels = res.data
          this.pagination.page = res.page.currentPage
          this.pagination.rowsPerPage = res.page.pageSize
          this.pagination.total = res.page.total
          if (res.data[0] && res.data[0].parcelareasum) {
            this.totalArea = res.data[0].parcelareasum
          } else {
            this.totalArea = 0
          }
          if (res.data[0] && res.data[0].parcelsum) {
            this.totalSum = res.data[0].parcelsum
          } else {
            this.totalSum = 0
          }
          this.parcels.forEach((tempParcel, index) => {
            // 初始化告警存储空间
            this.$set(this.parcels[index], 'notes', [])
            this.$set(this.parcels[index], 'parcelname', tempParcel['name'])
            // 查地块轮廓
            let land = new CLand(tempParcel)
            let coords = []
            // 查找地库轮廓数据
            land.landProfile().then(response => {
              let res = response.data
              if (res.flag === 1) {
                if (res.data && res.data.length > 0) {
                  let points = res.data
                  points.forEach((point) => {
                    let coord = Object.assign({},
                      { longitude: point.longitude },
                      { latitude: point.latitude },
                    )
                    coords.push(coord)
                  })
                  // console.log(coords)
                  this.$set(this.parcels[index], 'coords', coords.length > 2 ? coords : false)
                }
              }
              // 如果地块轮廓数据不为空或地块坐标存在则查询公共天气数据
              let curLon = (coords[0] && coords[0].longitude) &&
                (coords[0] && coords[0].longitude) ? coords[0].longitude : tempParcel.longitude
              let curLat = (coords[0] && coords[0].latitude) &&
                (coords[0] && coords[0].latitude) ? coords[0].latitude : tempParcel.latitude
              this.$set(this.parcels[index], 'hasweatherdata', false) // 初始化天气组件不显示
              let weatherinfo = Object.assign({},
                {
                  hasweatherdata: false,
                  weathercityname: null,
                  updatetime: null,
                  condition: null,
                  temp: null,
                  windDir: null,
                  windLevel: null,
                  humidity: null,
                  icon: null,
                  precipitation: null,
                  pressure: null,
                  tips: null,
                  iconpath: null,
                  timeday: null,
                  content: null,
                  infoid: null,
                  level: null,
                  name: null,
                  pub_time: null,
                  title: null,
                  update_time: null,
                  type: null
                })
              if (curLon && curLat) {
                excuteApis(Object.assign({}, { lon: curLon, lat: curLat, weatherCategorys: 'condition,alert' }),
                  global.constant.commonApiUrls, 'common', 'queryMoJiWeather').then(data => {
                    let res = data.data
                    if (res.flag === 1) {
                      // 记录缓存数据
                      // this.$store.state['listLand']['pagination'] = this.pagination
                      // this.$store.state['listLand']['ownership'] = this.ownership
                      // this.$store.state['listLand']['keywords'] = this.keywords
                      // this.$store.state['listLand']['curAreaCode'] = this.curAreaCode
                      if (res.data) {
                        let weatherData = res.data
                        if (weatherData.city) {
                          weatherinfo['weathercityname'] = weatherData.city.pname + weatherData.city.name // 城市名称
                          this.$set(this.parcels[index], 'weathercityname', weatherinfo['weathercityname'])
                        }
                        if (weatherData.condition) {
                          weatherinfo['hasweatherdata'] = true // 是否有天气数据
                          weatherinfo['updatetime'] = weatherData.condition.updatetime // 发布时间
                          weatherinfo['condition'] = weatherData.condition.condition // 实时天气
                          weatherinfo['temp'] = weatherData.condition.temp // 温度
                          weatherinfo['windDir'] = weatherData.condition.windDir // 风向
                          weatherinfo['windLevel'] = weatherData.condition.windLevel // 风级
                          weatherinfo['humidity'] = weatherData.condition.humidity // 湿度
                          weatherinfo['icon'] = weatherData.condition.icon // 天气icon
                          weatherinfo['precipitation'] = weatherData.condition.precipitation // 过去一小时累计降雨量
                          weatherinfo['pressure'] = weatherData.condition.pressure // 气压
                          weatherinfo['tips'] = weatherData.condition.tips // 一句话提示
                          weatherinfo['iconpath'] = weatherData.condition.iconpath // 图标路径
                          weatherinfo['timeday'] = weatherData.condition.timeday // 星期几
                          this.$set(this.parcels[index], 'hasweatherdata', weatherinfo['hasweatherdata'])
                          this.$set(this.parcels[index], 'updatetime', weatherinfo['updatetime'])
                          this.$set(this.parcels[index], 'condition', weatherinfo['condition'])
                          this.$set(this.parcels[index], 'temp', weatherinfo['temp'])
                          this.$set(this.parcels[index], 'windDir', weatherinfo['windDir'])
                          this.$set(this.parcels[index], 'windLevel', weatherinfo['windLevel'])
                          this.$set(this.parcels[index], 'humidity', weatherinfo['humidity'])
                          this.$set(this.parcels[index], 'icon', weatherinfo['icon'])
                          this.$set(this.parcels[index], 'precipitation', weatherinfo['precipitation'])
                          this.$set(this.parcels[index], 'pressure', weatherinfo['pressure'])
                          this.$set(this.parcels[index], 'tips', weatherinfo['tips'])
                          this.$set(this.parcels[index], 'iconpath', 'static/land/weather/W' + weatherinfo['icon'] + '.png')
                          this.$set(this.parcels[index], 'timeday', weatherinfo['updatetime'] ? getChinesesDay(new Date(weatherinfo['updatetime'])) : '未知')
                        }
                        if (weatherData.alert && weatherData.alert.length > 0) {
                          let alert = weatherData.alert[0]
                          weatherinfo['content'] = alert.content
                          weatherinfo['infoid'] = alert.infoid
                          weatherinfo['level'] = alert.level
                          weatherinfo['name'] = alert.name
                          weatherinfo['pub_time'] = alert.pub_time
                          weatherinfo['title'] = alert.title
                          weatherinfo['update_time'] = alert.update_time
                          weatherinfo['type'] = alert.type
                          this.$set(this.parcels[index], 'alert_content', weatherinfo['content'])
                          this.$set(this.parcels[index], 'alert_infoid', weatherinfo['infoid'])
                          this.$set(this.parcels[index], 'alert_level', weatherinfo['level'])
                          this.$set(this.parcels[index], 'alert_name', weatherinfo['name'])
                          this.$set(this.parcels[index], 'alert_pub_time', weatherinfo['pub_time'])
                          this.$set(this.parcels[index], 'alert_title', weatherinfo['title'])
                          this.$set(this.parcels[index], 'alert_update_time', weatherinfo['update_time'])
                          this.$set(this.parcels[index], 'alert_type', weatherinfo['type'])
                          let cncolor = weatherinfo['level']
                          let note = Object.assign({}, { color: COLORS_MAP[cncolor], title: weatherinfo['title'] })
                          let tmpNote = this.parcels[index]['notes'] || []
                          tmpNote.push(note)
                          this.$set(this.parcels[index], 'notes', tmpNote)
                        }
                      }
                    }
                  })
              } else {
                Object.assign(this.parcels[index], weatherinfo)
              }
            })
          })
          this.items = this.parcels
        } else {
          this.pagination.total = 0
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    removeParcel (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = item
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId.id,
        supportid: this.selectedItemId.supportid
      }
      excuteApis(requestParams, global.constant.parcelApis, 'parcel', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.fetchData()
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    Cancel () {
      this.prompt.dialog = false
    },
    showResponsibleUserInfo (user) {
      let str = ''
      if (isArray(user)) {
        for (let i = 0; i < user.length; i++) {
          let username = user[i].name ? user[i].name : '--'
          username += user[i].phone ? '(' + user[i].phone + ')' : ''
          str += username + ((i < user.length - 1) ? ',' : '')
        }
      }
      return str || '--'
    },
    millierConvertion (lat, lng) {

    },
    readCsvFile () {
      this.$refs.input.value = null
      this.$refs.input.click()
    },
    findTreeRootNodes (map) {
      let key = Object.keys(map).find(k => map[k].item && map[k].item.parent && (!map[map[k].item.parent.$oid]))
      if (key !== undefined) {
        return [map[key]]
      }
      if (map[null]) return (map[null] && map[null].children) || []

      key = '100000000000100000000000'
      return (map[key] && map[key].children) || []
    },
    async buildAdressTree (areaNames) {
      let dbClient = mongo.db('iss').collection('base.area')
      let { items } = await dbClient.find({ filter: { name: { $in: areaNames } } })
      // let map = JsonSchemaVuetify.util.toTreeMap(items)
      let map = this.toTreeMap(items)
      // let treeRootNodeMap = JsonSchemaVuetify.util.findTreeRootNodes(map).reduce((r, n) => {
      let treeRootNodeMap = this.findTreeRootNodes(map).reduce((r, n) => {
        r[n.item._id.$oid] = n
        return r
      }, {})

      items.forEach(area => {
        if (area.level === 2) treeRootNodeMap[area.name] = area._id.$oid
      })
      return treeRootNodeMap
    },
    importByCsv (fe) {
      const obj = fe.target
      if (!obj.files) return
      this.checkValid = true
      this.errortips = ''
      // 读取完成的数据
      let wb
      let f = obj.files[0]
      let filename = f.name
      let pos = filename.lastIndexOf('.')
      let filetype = filename.substr(pos + 1, filename.length - pos + 1)
      let valid = ['xls', 'xlsx'].includes(filetype)
      this.importFarmland.processing = valid
      this.errortips = this.importFarmland.processing ? '' : formatStr('文件格式不符合导入要求，请按照模板格式要求填写上传')
      if (!valid) return
      let reader = new FileReader()
      reader.onload = function (e) {
        let data = e.target.result
        wb = XLSX.read(data, { type: 'binary' })
        // wb.SheetNames[0]是获取Sheets中第一个Sheet的名字
        // wb.Sheets[Sheet名]获取第一个Sheet的数据
        let contentJson = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
        document.getElementById('convertJsonContainor').innerHTML = JSON.stringify(contentJson)
        // change 不能触发，换成click手动触发
        document.getElementById('convertJsonContainor').click()
      }
      reader.readAsBinaryString(f)
    },
    toTreeMap (items, base) {
      if (!items || !items.length) return {}
      return items.reduce((r, item) => {
        let childNode
        if (r[item._id.$oid]) {
          childNode = r[item._id.$oid]
          childNode.item = item
        } else {
          childNode = { item, children: [], ...base }
          r[item._id.$oid] = childNode
        }

        let parent = r[(item.parent && item.parent.$oid) || null]
        if (parent) {
          parent.children.push(childNode)
        } else {
          r[(item.parent && item.parent.$oid) || null] = { item: null, children: [childNode], ...base }
        }
        return r
      }, {})
    },
    async saveIntoDb () {
      let str = this.$refs.containor.innerHTML
      if (!str) return

      let contentJson = JSON.parse(str)
      this.importFarmland.result.total = contentJson.length
      let areas = []
      contentJson.forEach(f => {
        if (!!f['省'] && f['省'] !== '-' && areas.indexOf(f['省']) < 0) areas.push(f['省'])
        if (!!f['市'] && f['市'] !== '-' && areas.indexOf(f['市']) < 0) areas.push(f['市'])
        if (!!f['区/县'] && f['区/县'] !== '-' && areas.indexOf(f['区/县']) < 0) areas.push(f['区/县'])
        if (!!f['乡/镇'] && f['乡/镇'] !== '-' && areas.indexOf(f['乡/镇']) < 0) areas.push(f['乡/镇'])
        if (!!f['村'] && f['村'] !== '-' && areas.indexOf(f['村']) < 0) areas.push(f['村'])
      })
      let addressTree = null
      if (areas.length) {
        addressTree = await this.buildAdressTree(areas)
      }
      let mongoJson = []
      for (let i = 0; i < contentJson.length; i++) {
        let f = contentJson[i]
        let res = {}

        let province = f['省']
        let city = f['市']
        let district = f['区/县']
        let town = f['乡/镇']
        let village = f['村']

        let provinceId = 0
        if (!!province && province !== '-') {
          provinceId = addressTree[province]
          if (provinceId) {
            res.provincecode = {}
            // res.provencecode = { $oid: provinceId }
            res.provincecode = provinceId
          } else {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【省】内容: ' + f['省'] + ' 有误，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【省】必填，请核查!')
          this.checkValid = false
          break
        }

        let cityNode = {}
        if (!!city && city !== '-' && res.provincecode) {
          let cityNodes = addressTree[provinceId].children.filter(cityNode => cityNode.item.name === city)
          if (cityNodes.length) {
            // res.citycode = { $oid: cityNodes[0].item._id.$oid }
            res.citycode = cityNodes[0].item._id.$oid
            cityNode = cityNodes[0]
          } else {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【市】内容: ' + f['市'] + ' 有误，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【市】必填，请核查!')
          this.checkValid = false
          break
        }

        let districtNode = {}
        if (!!district && district !== '-' && res.citycode) {
          let districtNodes = cityNode.children.filter(districtNode => districtNode.item.name === district)
          if (districtNodes.length) {
            // res.countycode = { $oid: districtNodes[0].item._id.$oid }
            res.countycode = districtNodes[0].item._id.$oid
            districtNode = districtNodes[0]
            await redis.incr('v:farmland:' + districtNode.item.code + ':code', {}).then(({ data }) => {
              res.parcelno = districtNode.item.code + '-' + (parseInt(data) + 100)
            })
          } else {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【区/县】内容: ' + f['区/县'] + ' 有误，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【区/县】必填，请核查!')
          this.checkValid = false
          break
        }

        let townNode = {}
        if (!!town && town !== '-' && res.countycode) {
          let townNodes = districtNode.children.filter(townNode => townNode.item.name === town)
          if (townNodes.length) {
            // res.towncode = { $oid: townNodes[0].item._id.$oid }
            res.towncode = townNodes[0].item._id.$oid
            townNode = townNodes[0]
          } else {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【乡/镇】内容: ' + f['乡/镇'] + ' 有误，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【乡/镇】必填，请核查!')
          this.checkValid = false
          break
        }

        if (!!village && village !== '-' && res.towncode) {
          let villageNodes = townNode.children.filter(villageNode => villageNode.item.name === village)
          if (villageNodes.length) {
            res.villagecode = villageNodes[0].item._id.$oid
          }
        }

        // if (!!village && village !== '-' && res.towncode) {
        //   let villageNodes = townNode.children.filter(villageNode => villageNode.item.name === village)
        //   if (villageNodes.length) {
        //     res.villagecode = villageNodes[0].item._id.$oid
        //   } else {
        //     this.errortips = formatStr('第 ', i + 2, ' 行,列【村】内容: ' + f['村'] + ' 有误，请核查!')
        //     this.checkValid = false
        //     break
        //   }
        // } else {
        //   this.errortips = formatStr('第 ', i + 2, ' 行,列【村】必填，请核查!')
        //   this.checkValid = false
        //   break
        // }
        // let tenant = f['所属组织']
        // if (!!tenant && tenant !== '-') {
        //   tenant = this.$store.state['sys.tenant'].items.filter(t => t.name === tenant)
        //   if (tenant.length) {
        //     tenant = tenant[0]._id
        //   }
        //   if (tenant && tenant.$oid) {
        //     res.orgid = tenant.$oid
        //   } else {
        //     this.errortips = formatStr('第 ', i + 2, ' 行,列【所属组织】内容: ' + f['所属组织'] + ' 有误，请核查!')
        //     this.checkValid = false
        //     break
        //   }
        // } else {
        //   this.errortips = formatStr('第 ', i + 2, ' 行,列【所属组织】必填，请核查!')
        //   this.checkValid = false
        //   break
        // }
        let dependence = f['地块从属'] || 'parcel-self'
        if (dependence) {
          dependence = this.ownerships.filter(d => d.dictname === dependence)
          if (dependence.length) {
            dependence = dependence[0]
            res.dependenceid = dependence.dictvalue
          } else {
            dependence = this.ownerships.filter(d => d.dictname === '自建')
            res.dependenceid = dependence.dictvalue
          }
        }
        if (!!f['详细地址'] && f['详细地址'] !== '-') {
          res.addressdetail = f['详细地址']
          if (res.addressdetail.length > 20) {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【详细地址】内容: ' + res.addressdetail.substr(0, 10) + '... 有误，不能超过20，请核查!')
            this.checkValid = false
            break
          }
        }
        if (!!f['面积(亩)'] && f['面积(亩)'] !== '-') {
          res.area = f['面积(亩)']
          if (res.area > 1000000 || res.area <= 0) {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【面积(亩)】内容: ' + f['面积(亩)'] + ' 有误，大于0且不能超过1000000，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【面积(亩)】必填，请核查!')
          this.checkValid = false
          break
        }
        if (!!f['海拔(米)'] && f['海拔(米)'] !== '-') {
          res.elevation = f['海拔(米)']
          if (res.elevation > 10000) {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【海拔(米)】内容: ' + f['海拔(米)'] + ' 有误，不能超过10000，请核查!')
            this.checkValid = false
            break
          }
        }
        if (!!f['其他说明'] && f['其他说明'] !== '-') {
          res.introduce = f['其他说明']
          if (res.introduce && res.introduce.length > 500) {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【其他说明)】内容: ' + res.introduce.substr(0, 10) + '...有误，不能超过500，请核查!')
            this.checkValid = false
            break
          }
        }
        if (!!f['地块名称'] && f['地块名称'] !== '-') {
          res.name = f['地块名称']
          if (res.name.length > 20) {
            this.errortips = formatStr('第 ', i + 2, ' 行,列【地块名称】内容: ' + res.name.substr(0, 10) + '... 有误，不能超过20字符，请核查!')
            this.checkValid = false
            break
          }
        } else {
          this.errortips = formatStr('第 ', i + 2, ' 行,列【地块名称】必填，请核查!')
          this.checkValid = false
          break
        }
        // res.status = 1
        res.orgid = global.helper.ls.get('user').loginorgid
        mongoJson.push(res)
      }
      if (!this.checkValid) {
        this.importFarmland.processing = false
        return
      }
      // this.$refs.farmland.getCollection().insert(mongoJson)
      excuteApis(Object.assign({}, { parcels: mongoJson }), global.constant.parcelApis, 'parcel', 'batchSaveParcel').then(response => {
        let res = response.data
        if (res.flag === 1) {
          this.importFarmland.processing = false
          this.importFarmland.finished = true
          this.importFarmland.result.success = mongoJson.length
          this.errortips = formatStr('导入成功，共', mongoJson.length, '条')
          this.fetchData()
        } else {
          this.importFarmland.processing = false
          this.importFarmland.finished = true
          this.importFarmland.result.success = mongoJson.length
          this.errortips = formatStr('服务出错，原因: ', res.msg)
        }
      }, () => {
        this.importFarmland.processing = false
        this.importFarmland.finished = true
        this.importFarmland.result.success = mongoJson.length
        this.errortips = formatStr('服务器或网络出错')
      })
    },
    showImportDialog () {
      this.errortips = ''
      this.importFarmland.processing = false
      this.excelimport = !this.excelimport
    },
    chinesesDay (datestr) {
      let date = new Date(datestr)
      return getChinesesDay(date)
    },
    showLandInfo (landid) {
      this.$router.push({ name: 'viewland', query: { id: landid } })
    },
    editLandInfo (landid) {
      this.$router.push({ name: 'editland', query: { id: landid } })
    }
  },
  created () {
    // 初始化组织机构信息
    global.store.dispatch('init', { collection: 'sys.tenant' })
    // 获取缓存信息
    // this.cacheinfo = this.$store.state['listLand']
    // this.cacheinfo['pagination'] ? (this.pagination = this.cacheinfo['pagination']) : null
    // this.cacheinfo['curAreaCode'] ? (this.curAreaCode = this.cacheinfo['curAreaCode']) : null
    // this.cacheinfo['keywords'] ? (this.keywords = this.cacheinfo['keywords']) : null
    // this.cacheinfo['ownership'] ? (this.ownership = this.cacheinfo['ownership']) : null
    // this.theme = global.helper.ls.get('theme')
    // this.curAreaCode ? (this.isReversal = true) : '100000000000100000000000'
    this.curAreaCode = '100000000000100000000000'
    this.fetchLandOwership()
    this.fetchProductType()
    this.fetchData()
  },
  computed: {}
}

</script>

<style scoped>
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.unit {
  font-size: 14px;
}
.landImg {
  height: 250px;
  width: auto;
}
.tips {
  padding: 100px 100px;
  margin-left: auto;
  margin-right: auto;
}
.weather {
  /* background-color: #84c8e4; */
  /* margin-right: 5px; */
  /* padding: 10px 10px; */
  min-width: 280px;
  padding-left: 50px;
}
.noweather {
  text-align: center;
  line-height: 140px;
  margin: 0 auto;
}
.landpos {
  /* max-width: 100%; */
  max-width: 380px;
  /* border: 1px solid red; */
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.profilecontainer {
  /* border: 1px solid red; */
  position: relative;
  height: 180px;
  width: 180px;
  /* cursor: pointer; */
  /* background-image: url("../../../static/land/list/ditu.png"); */
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
}
.profile {
  /* border: 1px solid red; */
  text-align: center;
  width: 100px;
  height: 100px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.noprofile {
  /* border: 1px solid red; */
  line-height: 100px;
}
.devicealarm {
  line-height: 25px;
  background-color: #39bbb0;
  padding: 5px 5px;
}
.cardborder {
  border: 1px #c7c3c3 solid;
}
.underline {
  color: #32c787;
  text-decoration: underline;
  cursor: pointer;
}
.errortips {
  color: red;
}
.parcelno {
  position: relative;
  top: 10px;
  text-align: left;
  padding: 4px 4px;
  padding-right: 8px;
  background-color: rgb(0, 0, 0, 0.4);
  width: 165px;
  border-top-right-radius: 2em;
  border-bottom-right-radius: 2em;
  color: white;
}
.imgContainer {
  background-color: #03a9f4 !important;
  border-color: #03a9f4 !important;
  border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  height: 40px;
  width: 40px;
  /* margin-left: 75px; */
}
</style>
