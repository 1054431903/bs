<template lang="pug">
div.pa-2()
  v-flex(xs12 sm12 md12 lg12)
    v-card.pb-3(flat)
      v-container.pt-2(fluid)
        v-layout(row wrap)
          //- v-flex(xs12 sm7 md8 lg10 xl10 text-xs-left) 
          //-   span(style="line-height: 45px;" v-if="parcel.parcelno") {{ parcel.name || '--' }} （编号： {{ parcel.parcelno || '--' }}）
          //- v-flex(xs12 sm5 md4 lg2 xl2 text-xs-right)
          //-   v-btn(router icon small fab :to="{name: 'editland', query: {id: parcel.supportid}}" v-if="$authed('PUT','lands/editland')" title="编辑")
          //-     v-icon(color="grey darken-1") edit
          //-   v-btn(fab small icon @click="removeParcel(parcel)" v-if="$authed('DELETE','lands/deleteland')" title="删除")
          //-     v-icon(color="red darken-2") delete
          v-flex(xs12)
            go-back(:name="viewTitle")
              span(style="float: right")
                v-btn(router icon small fab :to="{name: 'editland', query: {id: parcel.supportid}}" v-if="$authed('PUT','lands/editland')" title="编辑")
                  v-icon(color="grey darken-1") edit
                v-btn(fab small icon @click="removeParcel(parcel)" v-if="$authed('DELETE','lands/deleteland')" title="删除")
                  v-icon(color="red darken-2") delete   
        .py-1
        v-layout(row wrap)
          v-flex(xs12 sm4 md4 lg3 xl2)
            div(class="profilecontainer" :style="{backgroundImage: 'url(static/land/list/ditu.png)'}")
              div(class="parcelno")
                v-flex(xs12 sm12 md12 lg12) {{parcel.parcelno}}
              v-landprofile(:lanlngs="coords" :size="100" class="profile" v-bind:class="{noprofile: !coords}")
          v-flex(xs12 sm8 md8 lg9 xl10)
            v-layout(row class="lineheight")
              v-flex(xs6) 地块占地面积：
                span(class="parcelcontent") {{ parcel.area ? parcel.area.toFixed(2)  + '亩' : '--'}}
              v-flex(xs6 hideoverflow) 地块负责人：
                span(:title="showResponsibleUserInfo(parcel.responsibleuser)" class="parcelcontent") {{ showResponsibleUserInfo(parcel.responsibleuser) }}
            v-layout(row class="lineheight")
              // v-flex(xs6 class="hideoverflow") 地块从属：
              //   span(class="parcelcontent") {{ parcel.dependencename ? parcel.dependencename : '--' }}（权属人：{{ showBelongPeasantInfo(parcel.belongpeasant) }}）
              v-flex(xs6 class="hideoverflow") 地块类型：
                span(class="parcelcontent") {{ parcel.typename }}
              v-flex(xs6 hideoverflow) 地块位置：
                span(:title="parcel.addressfullname" class="parcelcontent") {{ parcel.addressfullname }}
            v-layout(row class="lineheight")              
              v-flex(xs6) 地块经纬度：
                span(class="parcelcontent") {{ parcel.longitude ? (parcel.longitude + '，' +  parcel.latitude)  : '--' }}
              v-flex(xs6) 地块海拔：
                span(class="parcelcontent") {{ parcel.elevation ? parcel.elevation + '米' : '--' }}
            v-layout(row class="lineheight")
              v-flex(xs6 class="hideoverflow") 地块从属：
                span(class="parcelcontent") {{ parcel.dependencename ? parcel.dependencename : '--' }}（权属人：{{ showBelongPeasantInfo(parcel.belongpeasant) }}）
              v-flex(xs6 hideoverflow) 地块参保状态：
                span(:title="parcel.addressfullname" class="parcelcontent") {{ parcel.insurancename }}  
            v-layout(row class="lineheight")
              v-flex(xs12) 相关附件：
                span(:title="parcel.addressfullname" class="parcelcontent" v-for="attachment in parcel.attachments" :key="attachment.id")
                  a(:href="attachment.resourcepath" target="_blank") {{attachment.resourcename}}     
            v-layout(row class="lineheight")
              v-flex(xs10) 其他说明：
                span(:title="parcel.introduce" class="parcelcontent") {{ parcel.introduce ? parcel.introduce : '--'}}
              v-flex(xs2 text-xs-right)
                div.mr-3(@click="showMeasureSoilDialog" title="测土报告" class="cetu" v-if="$authed('GET','lands/measuresoil')") 
                  img(src="static/land/cetu.png" width="31px" height="31px")
                  //- div(class="cetutext") 测土记录         
  .py-1
  v-flex(xs12 sm12 md12 lg12)
    v-card(flat)
      v-container(fluid)
        v-layout(row)
          v-flex(xs6 class="text-xs-left")
            span(style="line-height: 45px;") 种植信息
          v-flex(xs12 class="text-xs-right")
            v-btn(@click="showAddPlantForm" small fab dark color="red darken-2" title="添加种植记录" v-if="$authed('POST','lands/addplant')") 
              v-icon add
              //- span 添加种植信息
        v-layout(row)
          v-flex(xs12)   
            v-data-table(:headers='plantrecordscolumns', :items='plantrecords', hide-actions, no-data-text="暂无数据",:loading="loading")
              template(slot='items', slot-scope='props')
                tr
                  td(class="text-xs-left") {{ props.item.categoryname || '--' }}
                  td(class="text-xs-left") {{ props.item.varietiesname || '--' }}
                  td(class="text-xs-left") {{ props.item.area ? props.item.area : '--' }}
                  td(class="text-xs-left") {{ props.item.number ? props.item.number : '--' }} {{ props.item.unit ? props.item.unit : '' }}
                  td(class="text-xs-left") {{ props.item.plantbegintime ? props.item.plantbegintime.substr(0,10) : '--' }}
                  td(class="text-xs-left") {{ props.item.preestimategrowthcycle ? props.item.preestimategrowthcycle : '--' }} {{ props.item.preestimategrowthcycleunit ? props.item.preestimategrowthcycleunit : '' }}
                  td(class="text-xs-center hideoverflow")
                    v-btn(title="查看" fab small icon  v-if="$authed('GET','lands/viewplant')" @click="showViewPlant(props.item)")
                      v-icon() remove_red_eye
                    v-btn(title="编辑" fab small icon v-if="$authed('PUT','lands/editplant')"  @click="showEditPlantForm(props.item)")
                      v-icon() edit
                    v-btn(title="删除" fab small icon v-if="$authed('DELETE','lands/deleteplant')"  @click="removePlant(props.item)")
                      v-icon(color="error") delete
        v-layout(row justify-center)             
          v-flex(xs12 sm12 md8 lg8 xl8)
            v-custompagination(:pagination.sync="paginationplant")          
  .py-1
  v-flex(xs12 sm12 md12 lg12)
    v-card(flat)
      v-container(fluid)
        v-layout(row)
          v-flex(xs12)
            button(class="fbClz" :class="gsClz" @click="setGTabs" v-if="$authed('GET','lands/weather')") 公共气象信息
            button(class="hbClz"  :class="wsClz" @click="setWTabs" v-if="$authed('GET','lands/bloomsky')") 微气象信息
            button(class="fbClz"  :class="nsClz" @click="setNTabs" v-if="$authed('GET','farmerrecords/main/farmerrecords/farmerrecordsDetail')") 农事记录
            button(class="fbClz"  :class="isClz" @click="setITabs" v-if="$authed('GET','lands/iot')") 种植生长数据
            div(v-if="currentTab === 'weather'")
              //- v-layout(row)
              //-   v-flex.mb-2.mr-3(xs1 offset-xs11)
              //-     v-select(
              //-       v-bind:items="weathers"
              //-       v-model="currentWeather"
              //-       item-text="name"
              //-       item-value="id"
              //-       hide-details=true
              //-       single-line
              //-       no-data-text="无"
              //-       class="hideoverflow currentWeather"
              //-     )
              div(v-if="hasrealcommonweather")
                //- v-layout(row)
                //-   v-flex.pa-2(xs12) {{ showObjValue(realcommonweather, 'tips', '当前无天气告警信息') }}
                div(class="commweather weather" :style="{backgroundImage: 'url(static/land/list/tianqibeijing.png)'}")
                  v-layout(row)
                    v-flex(xs12 text-xs-left)
                      v-avatar.ml-3(size="14px")
                        img(src=`static/land/list/zuobiao.png` class="positionImg")
                      span &nbsp;&nbsp;{{realcommonweather.weathercityname}}（{{realcommonweather['updatetime'].substr(11, 5)}}实况）
                  v-layout.pa-2(row)
                    v-flex(xs6 text-xs-right)
                      span(class="weatherValue") {{ showObjValue(realcommonweather, 'temp', '--') }}°
                    v-flex(xs6 text-xs-left)
                      div(style="margin-left: 15px; line-height: 67px;")
                        v-avatar(:size="25")
                          img(:src="realcommonweather.iconpath")
                        span &nbsp;&nbsp;{{realcommonweather.condition}}
                  v-layout.pb-4.mt-2(row)
                    v-flex(xs12 text-xs-center)
                      v-avatar.ml-4(size="15px")
                        img(src=`static/land/fengsu.png` style="back")
                      span &nbsp;&nbsp;{{ showObjValue(realcommonweather, 'windDir', '--') }}&nbsp;&nbsp;&nbsp;&nbsp;{{ showObjValue(realcommonweather, 'windLevel', '--') }}级
                      v-avatar.ml-4(size="15px")
                        img(src=`static/land/yuliang.png`)
                      span &nbsp;&nbsp;降雨量&nbsp;&nbsp;&nbsp;&nbsp;{{ showObjValue(realcommonweather, 'precipitation', '--') }}mm
                      v-avatar.ml-4(size="15px")
                        img(src=`static/land/shidu.png`)
                      span &nbsp;&nbsp;湿度&nbsp;&nbsp;&nbsp;&nbsp;{{ showObjValue(realcommonweather, 'humidity', '--') }}%
                      v-avatar.ml-4(size="15px")
                        img(src=`static/land/qiya.png`)
                      span &nbsp;&nbsp;气压&nbsp;&nbsp;&nbsp;&nbsp;{{ showObjValue(realcommonweather, 'pressure', '--') }}hPa
                  v-layout.pb-4(row v-if="true")
                    v-flex(xs12 text-xs-center)
                      div.ml-3.mr-3(style="color: yellow; text-align-last: start;") {{showObjValue(realcommonweather, 'alert_content', '')}}  
                div(class="scrollbar")    
                  div.mt-2
                    v-layout.pa-2(row)
                      v-flex(xs12) <span style="font-weight: 520; font-size: 15px;"> 24小时预报 </span>
                        v-24hforecast(:forecast="realcommonweather.forecast")
                  div.mt-2(style="border-bottom: none;")
                    v-layout.pa-2(row)
                      v-flex(xs12) <span style="font-weight: 520; font-size: 15px;"> 未来7天预报 </span>
                        v-4dforecast(:dayforecast="realcommonweather.dayforecast")
              div(v-else class="noweather")
                div(style="text-align: center;")
                  img(src=`static/default.png` style="width:126px;height:89px;")
                span(style="font-size:16px;color:#999999;font-family:Microsoft Yahei;display:block") 暂无公共天气数据
            div(flat v-show="currentTab === 'bloomsky'")
              v-layout(row v-if="bkDevice")
                v-flex.xs12
                  v-select(:items="bk_items" item-value="id" item-text="name" v-model="bk_device_name" @change="change"  style="width:220px;float:right;margin-left:10px;")
                  div(style="float:right;margin-top:27px;" class="mr-1") 安装位置: {{ bkDevice && bkDevice.location ? bkDevice.location : '---'}} &nbsp;&nbsp;&nbsp; 海拔: {{bkDevice && bkDevice.alti ? bkDevice.alti : '---'}}米 &nbsp;&nbsp;&nbsp;
              //- div(v-if="curr_bk_data" class="bk")
              div(class="bk" v-if="bkDevice")
                div(class="bk_left_img")
                  img(:src="bk_src || `/static/land/bk_weather/zanwu.png`" style="width:100%;height:100%")
                  p(style="position: absolute;left: 10%;bottom: 0px;font-size:14px;color:#fff") {{bk_date_time || '--'}}
                div(class="bk_weather" :style="{backgroundImage: 'url(static/land/list/diwewn.png)'}")
                  div(class="bk_weather_1")
                    p(class="mt-2") 微气象实况(更新时间: {{bk_date_time || '--'}})
                    p(class="bk_weatherValue") {{(curr_bk_data && curr_bk_data['0:61'] && curr_bk_data['0:61'].v) ? (curr_bk_data && curr_bk_data['0:61'] && curr_bk_data['0:61'].v) : '--'}}℃
                  div(class="bk_weather_2") 
                    p(class="bk_p") 
                      img(src=`static/land/bk_weather/current/wd.png` style="vertical-align:middle")
                      v-avatar.ml-1(size="5px")
                      span(style="vertical-align:middle") 空气温度: {{(curr_bk_data && curr_bk_data['0:61'] && curr_bk_data['0:61'].v) ? (curr_bk_data && curr_bk_data['0:61'] && curr_bk_data['0:61'].v) : '--'}} (℃)
                    p
                      img(src=`static/land/bk_weather/current/qiya.png` style="vertical-align:middle")
                      v-avatar.ml-1(size="5px")
                      span(style="vertical-align:middle") 气   压: {{(curr_bk_data && curr_bk_data['0:67'] && curr_bk_data['0:67'].v) ? (curr_bk_data && curr_bk_data['0:67'] && curr_bk_data['0:67'].v) : '--'}} (hPa)
                  div(class="bk_weather_2") 
                    p(class="bk_p")
                      img(src=`static/land/bk_weather/current/shidu.png` style="vertical-align:middle")
                      v-avatar.ml-1(size="5px")
                      span(style="vertical-align:middle") 空气湿度: {{(curr_bk_data && curr_bk_data['0:62'] && curr_bk_data['0:62'].v) ? (curr_bk_data && curr_bk_data['0:62'] && curr_bk_data['0:62'].v) : '--'}} (%)
                    p
                      img(src=`static/land/bk_weather/current/jiangyuliang.png` style="vertical-align:middle")
                      v-avatar.ml-1(size="5px")
                      span(style="vertical-align:middle") 降雨率:  {{(curr_bk_data && curr_bk_data['0:69'] && curr_bk_data['0:69'].v) ? (curr_bk_data && curr_bk_data['0:69'] && curr_bk_data['0:69'].v) : '--'}}  (%)
                  div(class="bk_weather_3") 
                    p(class="bk_p")
                      img(src=`static/land/bk_weather/current/fengxiang.png` style="vertical-align:middle")
                      v-avatar.ml-1(size="5px")
                      span(style="vertical-align:middle") 风速风向: {{win_sdirection || '--'}}&nbsp;&nbsp;{{curr_bk_data && curr_bk_data['0:63'] && curr_bk_data['0:63'].v}} m/s
              div(v-else style="height: 300px; text-align: center;padding-top: 80px;")
                div(style="text-align: center;")
                  img(src=`static/default.png` style="width:126px;height:89px;")
                span(style="font-size:16px;color:#999999;font-family:Microsoft Yahei;display:block") 暂未接入微气象监测设备
              v-layout(row v-if="bkDevice")
                v-flex(xs12)
                  button(class="fbClz" :class="forcastClz" @click="setFTabs") 微气象预报
                  button(class="hbClz"  :class="historyClz" @click="setHTabs") 历史数据报表
                  button(class="fbClz"  :class="vedioClz" @click="setVTabs") 历史天空视频
                  div(v-if="bk_currentTab === 'forcast'")
                    div(class="scrollbar")
                      v-layout.pa-2(row)
                        v-flex(xs12) <span style="font-weight: 520; font-size: 15px;"> 24小时预报 </span>
                          v-Bk24hforecast(:forecast="bk_forecast")
                      v-layout.pa-2(row)
                        v-flex(xs12) <span style="font-weight: 520; font-size: 15px;"> 未来5天预报 </span>
                          v-bk4dforecast(:dayforecast="bk_dayforecast")
                  div(v-if="bk_currentTab === 'history'")
                    BkWeather(:device="bkDevice")
                  div(v-show="bk_currentTab === 'vedio'")
                    BkVedio(:device="vedios" :clz="clz")
            div(flat v-show="currentTab === 'farmwork'")
              v-layout(row)
                v-flex(xs4)
                  v-text-field(
                    append-icon="search"
                    v-model="farmrecordkeyword"
                    label="请输入农事内容查询"
                    single-line
                    clearable
                    hide-details
                    @keyup.enter="queryFarmRecordByCondition"
                    )
                v-flex.pa-2(xs1)
                  v-btn(@click="queryFarmRecordByCondition" color="primary" title="查询") 查询
                v-flex(xs7 text-xs-right)
                  v-btn(@click="addRecord" small fab dark icon color="red darken-2" title="新增农事记录")
                    v-icon() add
              v-layout(row)
                v-flex.pa-2(xs12)
                  span 农事记录：{{ farmrecordpagination.total }} 条
              v-layout(row)
                v-flex.pa-2(xs12)
                  v-data-table(:headers='farmrecordcolumns' :items='farmrecords' hide-actions no-data-text = "暂无农事记录"  :loading="loading")
                    template(slot='items', slot-scope='props')
                      tr(@click="viewFarmrecordDetial(props.item.id)")
                        td(class="text-xs-left hideoverflow") {{ props.item.startOnStr ?  props.item.startOnStr : '--'}}
                        //- td(class="text-xs-left hideoverflow") {{ props.item.farmtypenames }}
                        td(class="text-xs-left")
                          template
                            div(class="text-xs-left hideoverflow farmcontent" :title="props.item.content") &nbsp;&nbsp;{{ props.item.content }}
                        td(class="text-xs-left hideoverflow") {{ props.item.participatorsName }}
              v-farmerrecordsdetail(:visible.sync="viewRecordDialog"
                           :title="viewRecordDialogTitle"
                           :id="curRecordId"
                           @callback="queryFarmRecordByCondition")
              v-farmerrecordsadd(:visible.sync="addRecordDialog" 
                           :title="addRecordDialogTitle"
                           :isrecordlistadd="false"
                           :parcelid="parcel.supportid"
                           @callback="queryFarmRecordByCondition")
              v-layout(row)
                v-flex(xs12 text-xs-center)
                  v-custompagination(:pagination.sync="farmrecordpagination")
            div(flat v-if="currentTab === 'iot'")
                    v-layout(row)
                      LandGis(:parcel="parcelData")
  v-layout(row, justify-center)
    v-plantaddedit(:addOrEditDialog.sync="isPlantAdd" :plantid="plantid" :parcelid="id")
  v-layout(row, justify-center)
    v-plantaddedit(:addOrEditDialog.sync="isPlantEdit" :plantid="plantid" :parcelid="id")
  v-layout(row, justify-center)
    v-plantview(:viewDialog.sync="isPlantView" :plantid="plantid" :parcelid="id")
  v-layout(row, justify-center)
    v-soillist(:listDialog.sync="isListDialog" :parcelid="id")
  v-layout(row, justify-center)
    v-modaldialog(
      :isShow="prompt.dialog",
      :title="prompt.title",
      :modalMaxWidth="290",
      :bodyHeight="80"
    )
      div {{ prompt.body }}
      template(slot="btnActions")
        v-divider
        v-card-actions
          v-spacer
          v-btn(flat, color="primary", @click.native.stop="operateCancel") 取消
          v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="operateOk") 确定
</template>

<script>
import { excuteApis } from '@/apis'
import { isArray, getObjValue, getChinesesDay, parseTime, addHour } from '@/utils'
import { CLand } from '@/models/land'
import { Toast } from '@/models/toast'
import LandGis from './LandGis'
import mongo from '@vehiclecloud/mongo-rest-client'
import BkVedio from './BkVedio'
import BkWeather from './BkWeather'
import VueAliplayer from 'vue-aliplayer'
import VPlantaddedit from './plant/PlantAddEdit.vue'
import VPlantview from './plant/PlantView.vue'
import VSoillist from './measuresoil/MeasuresoilList.vue'
import VFarmerrecordsdetail from '@/pages/farmworks/workrecord/FarmerRecordsDetail.vue'
import moment from 'moment'
import VFarmerrecordsadd from '../farmworks/workrecord/FarmerRecordsAdd.vue'

function skycon2Text (skycon) {
  switch (skycon) {
    case 0: return '晴'
    case 1: return '多云'
    case 2: return '阴'
    case 300: return '雨'
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
function fmtDay (time) {
  let d = new Date(+time)
  return `${pre(d.getFullYear())}-${pre(d.getMonth() + 1)}-${pre(d.getDate())} ${pre(d.getHours())}:${pre(d.getMinutes())}`
}
export default {
  data () {
    return {
      allUsers: [],
      viewRecordDialog: false,
      viewRecordDialogTitle: '查看农事记录',
      addRecordDialog: false,
      addRecordDialogTitle: '新建农事记录',
      curRecordId: null,
      clz: 'wqx',
      forecastFlag: true,
      bk_items: [],
      bk_device_name: '',
      vedios: [],
      win_sdirection: '',
      bkDevice: null,
      parcelData: {},
      parcel: {
        id: null,
        name: '',
        addresscode: '',
        addressdetail: '',
        addressfullname: '',
        area: null,
        elevation: null,
        dependenceid: null,
        departmentid: null,
        belongpeasantid: null,
        introduce: '',
        dependencename: '',
        departmentname: '',
        belongpeasantname: '',
        responsibleuserid: [],
        responsibleusername: [],
        responsibleuserphone: [],
        parceltypeid: [],
        weatherinfo: null
      },
      plants: [],
      temPlants: [],
      isPlantMore: false,
      prompt: {
        id: '',
        data: null,
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      categoryid: '',
      producttypes: [],
      menuBegin: false,
      menuEnd: false,
      menuBeginAdd: false,
      menuEndAdd: false,
      menuMeastimeAdd: false,
      viewPlantDialog: false,
      addPlantDialog: false,
      editPlantDialog: false,
      measureSoilDialog: false,
      viewMeasureSoilDialog: false,
      addMeasureSoilDialog: false,
      editMeasureSoilDialog: false,
      loading: false,
      measuresoils: [],
      currentMeasureSoil: null,
      qualities: [],
      actions: {},
      pagination: {
        total: 0,
        page: 1,
        rowsPerPage: 5
      },
      totalPages: 0,
      total: 0,
      tabs: [
        {
          id: 'weather',
          title: '公共气象'
        },
        {
          id: 'bloomsky',
          title: '微气象信息'
        },
        {
          id: 'farmwork',
          title: '农事记录'
        },
        {
          id: 'iot',
          title: '物联网信息'
        }
      ],
      bk_tabs: [
        {
          id: 'forcast',
          title: '微气象预报'
        },
        {
          id: 'history',
          title: '历史数据报表'
        },
        {
          id: 'vedio',
          title: '历史天空视频'
        }
      ],
      currentTab: '',
      bk_currentTab: 'forcast',
      weathers: [{
        id: '1',
        name: '公共气象'
      }],
      currentWeather: {
        id: '1',
        name: '公共气象'
      },
      measureSoilValid: true,
      addMeasureSoilValid: true,
      menuMeastime: false,
      addPlantFormValid: true,
      editPlantFormValid: true,
      plantrecords: [],
      paginationplant: {
        total: 0,
        page: 1,
        rowsPerPage: 5
      },
      plantrecordscolumns: [
        {
          text: '品类',
          align: 'left',
          sortable: false,
          value: 'categoryid'
        },
        {
          text: '品种',
          align: 'left',
          sortable: false,
          value: 'breed'
        },
        {
          text: '种植面积（亩）',
          align: 'left',
          sortable: false,
          value: 'area'
        },
        {
          text: '植株数量',
          align: 'left',
          sortable: false,
          value: 'number'
        },
        {
          text: '播/栽种时间',
          align: 'left',
          sortable: false,
          value: 'plantbegintime'
        },
        {
          text: '计划生长周期',
          align: 'left',
          sortable: false,
          value: 'preestimategrowthcycle'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: ''
        }
      ],
      farmrecords: [],
      farmrecordcolumns: [
        {
          text: '实施时间',
          align: 'left',
          sortable: false,
          value: 'begintime'
        },
        {
          text: '农事内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '实施人',
          align: 'left',
          sortable: false,
          value: 'recordpartusernames'
        }
      ],
      farmtypes: [],
      selectedfarmtypes: [],
      currentselectedfarmtypes: [],
      farmrecordpagination: {
        total: 0,
        page: 1,
        rowsPerPage: 5
      },
      farmrecordpages: 0,
      farmrecordstotal: 0,
      farmrecordkeyword: '',
      hasrealcommonweather: false,
      bk_dayforecast: [],
      bk_forecast: {
        temp: [{
          // temp: 20, // 温度
          // time: '10:00', // 时间
          // rainrate: '10%', // 降雨概率
          // humidity: 20,  // 湿度
          // pressure: 10,  // 压力
          // windforce: 1  // 风力，此有问题(风速+风力)
        }]
      },
      realcommonweather: {
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
        timeday: '', // 星期几
        tempDay: '', // 日间温度
        tempNight: '', // 夜间温度
        hourly: [], // 24小时天气
        alert: [],
        forecast: {
          temp: [{
            temp: 20, // 温度
            time: '10:00', // 时间
            rainrate: '10%', // 降雨概率
            humidity: 20,  // 湿度
            pressure: 10,  // 压力
            windforce: 1  // 风力，此有问题(风速+风力)
          }]
        },
        dayforecast: [{
          predictDate: '2018-03-22',
          conditionDay: '晴',
          tempDay: '20',
          tempNight: '9',
          windDirDay: '北风',
          windLevelDay: '1'
        },
        {
          predictDate: '2018-03-23',
          conditionDay: '晴',
          tempDay: '20',
          tempNight: '9',
          windDirDay: '北风',
          windLevelDay: '1'
        },
        {
          predictDate: '2018-03-24',
          conditionDay: '晴',
          tempDay: '20',
          tempNight: '9',
          windDirDay: '北风',
          windLevelDay: '1'
        },
        {
          predictDate: '2018-03-25',
          conditionDay: '晴',
          tempDay: '20',
          tempNight: '9',
          windDirDay: '北风',
          windLevelDay: '1'
        }] // 未来四天天气
      },
      coords: false, // 地块坐标集合
      id: null,
      curr_bk_data: null,
      bk_src: '',
      bk_data: {},
      bk_time: '',
      bk_date_time: '',
      historyClz: 'hClz',
      forcastClz: 'fsClz',
      vedioClz: 'vClz',
      gsClz: 'fsClz',
      wsClz: 'fClz',
      nsClz: 'fClz',
      isClz: 'fClz',
      isPlantAdd: 'IS_NONE',
      isPlantEdit: 'IS_NONE',
      isPlantView: false,
      plantid: null,
      isListDialog: false,
      toast: null
    }
  },
  computed: {
    viewTitle () {
      let title = ''
      this.parcel.name ? (title = this.parcel.name) : ''
      this.parcel.parcelno ? (title = title + '（' + this.parcel.parcelno + '）') : ''
      return title
    }
  },
  watch: {
    selectedfarmtypes: function (val) {
      this.currentselectedfarmtypes = []
      for (let i = 0; i < val.length; i++) {
        let obj = {}
        Object.assign(obj, { farmtype: val[i] })
        this.currentselectedfarmtypes.push(obj)
      }
      if (this.currentselectedfarmtypes.length === 0) this.queryFarmRecordByCondition()
    },
    'farmrecordpagination.page': function (val) {
      this.fetchFarmRecordsData()
    },
    'farmrecordpagination.rowsPerPage': function (val) {
      this.fetchFarmRecordsData()
    },
    'paginationplant.page': function (val) {
      this.fetchPlantData()
    },
    'paginationplant.rowsPerPage' (val) {
      this.fetchPlantData()
    },
    // farmrecordkeyword: deferInvoke(function (val, oldVal) {
    //   if (val !== oldVal) {
    //     this.fetchFarmRecordsData()
    //   }
    // }),
    currentTab: function (v) {
      let tabs = { 'weather': 'setGTabs', 'bloomsky': 'setWTabs', 'farmwork': 'setNTabs', 'iot': 'setITabs' }
      this[tabs[v]]()
    },
    isPlantEdit: function (v) {
      v === 'IS_NONE' ? this.fetchPlantData() : null
    },
    isPlantAdd: function (v) {
      v === 'IS_NONE' ? this.fetchPlantData() : null
    }
  },
  methods: {
    change (v) {
      this.bk_date_time = '--'
      this.reloadBkDevice(v)
    },
    setFTabs () {
      this.bk_currentTab = 'forcast'
      this.forcastClz = 'fsClz'
      this.historyClz = 'hClz'
      this.vedioClz = 'vClz'
    },
    setHTabs () {
      this.bk_currentTab = 'history'
      this.forcastClz = 'fClz'
      this.historyClz = 'hsClz'
      this.vedioClz = 'vClz'
    },
    setVTabs () {
      this.bk_currentTab = 'vedio'
      this.forcastClz = 'fClz'
      this.historyClz = 'hClz'
      this.vedioClz = 'vsClz'
    },
    setGTabs () {
      this.currentTab = 'weather'
      this.gsClz = 'fsClz'
      this.wsClz = 'fClz'
      this.nsClz = 'fClz'
      this.isClz = 'fClz'
    },
    setWTabs () {
      this.currentTab = 'bloomsky'
      this.gsClz = 'fClz'
      this.wsClz = 'fsClz'
      this.nsClz = 'fClz'
      this.isClz = 'fClz'
    },
    setNTabs () {
      this.currentTab = 'farmwork'
      this.gsClz = 'fClz'
      this.wsClz = 'fClz'
      this.nsClz = 'fsClz'
      this.isClz = 'fClz'
    },
    setITabs () {
      this.currentTab = 'iot'
      this.gsClz = 'fClz'
      this.wsClz = 'fClz'
      this.nsClz = 'fClz'
      this.isClz = 'fsClz'
    },
    async fetchBkHoursForecast (id) {
      // mongo.db('iot').collection('tenant.report.device_access_data_summary').find({filter: {device: {'$oid': id}, period: 1, time: {'$numberLong': {'$gte': dateStartLong, '$lte': dateEndLong}}}, sort: {'time': -1}, limit: 5}),
      // mongo.db('iot').collection('tenant.report.device_access_data_summary').find({filter: {device: {'$oid': id}, period: 1, time: {'$numberLong': {'$gte': dateStartLong, '$lte': timeEndLong}}}, sort: {'time': -1}, limit: 24})
      // let dateStart = new Date()
      // let dateStartLong = dateStart.getTime()
      // let dateEnd = new Date(addDate(dateStart, 5))
      // let dateEndLong = dateEnd.getTime()
      // let timeEnd = new Date(addHour(dateStart, 24))
      // let timeEndLong = timeEnd.getTime()
      // mongo.db('iot').collection('tenant.report.device_access_data_summary').find({filter: {device: {'$oid': id}, period: 2}, sort: {'time': -1}, limit: 5}),
      // mongo.db('iot').collection('tenant.report.device_access_data_summary').find({filter: {device: {'$oid': id}, period: 1}, sort: {'time': -1}, limit: 24})
      Promise.all([
        mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': id }, period: 2 }, sort: { 'time': -1 }, limit: 5 }),
        mongo.db('iot').collection('tenant.report.device_access_data_summary').find({ filter: { device: { '$oid': id }, period: 1 }, sort: { 'time': -1 }, limit: 24 })
      ]).then(data => {
        this.forecastFlag = true
        this.bk_dayforecast = []
        if (data[0].items.length > 0) {
          let dayforData = []
          data[0].items.reverse()
          data[0].items.forEach(d => {
            let now = parseTime(new Date(), 'yyyy-MM-dd')
            let cur = this.fmt_Date(d.time.$numberLong)
            if (cur >= now) {
              dayforData.push({
                predictDate: cur,
                conditionDay: skycon2Text(d.data['0:71'].v),
                tempDay: d.data['0:61'].c['2'],
                tempNight: d.data['0:61'].c['1'],
                windDirDay: windirection2Text(d.data['0:64'].v),
                windLevelDay: d.data['0:63'].v,
                dayiconpath: `/static/land/bk_weather/W${d.data['0:71'].v}-day.png`
              })
            }
          })
          this.bk_dayforecast = dayforData.length >= 5 ? dayforData : []
        } else {
          this.forecastFlag = false
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
              winddicrection: windirection2Text(d.data['0:64'].v)
            })
          })
          this.bk_forecast.temp = ft
        } else {
          this.forecastFlag = false
        }
      }, err => {
        this.forecastFlag = false
        console.log(err)
      })
    },
    async fetchData () {
      this.toast = new Toast(this, null, null)
      this.toast.show()
      await excuteApis(Object.assign({}, { id: this.id }), global.constant.parcelApis, 'parcel', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data.length > 0) {
          this.parcel = res.data[0]
          this.findBkDeviceByFarlandId(this.parcel.supportid)
          mongo.db('iss').collection('tenant.farmland').find({ filter: { '_id': { '$oid': this.parcel.supportid } } }).then(d => {
            this.parcelData = d.items[0]
          })
          if (this.parcel.id) {
            this.fetchProfileData()
            this.fetchPlantData()
            this.fetchFarmRecordsData()
            // this.fetchrealcommonweather()
            this.fetchQualities()
          }
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    async fetchPlantData () {
      try {
        let requestParams = {
          currentPage: this.paginationplant.page || 1,
          pageSize: this.paginationplant.rowsPerPage || 5
        }
        Object.assign(requestParams, { parcelid: this.parcel.id })
        const response = await excuteApis(requestParams, global.constant.parcelApis, 'parcelPlant', 'query')
        const res = response.data
        if (res.flag === 1) {
          this.plants = []
          let ps = res.data
          if (ps && ps.length > 0) {
            ps.forEach((item) => {
              let begintime = getObjValue(item, 'plantbegintime', '')
              let endtime = getObjValue(item, 'plantendtime', '')
              let p = Object.assign(item, { plantbegintime: begintime, plantendtime: endtime })
              this.plants.push(p)
            })
          }
          this.plantrecords = this.plants
          this.paginationplant.page = res.page['currentPage']
          this.paginationplant.total = res.page['total']
          this.paginationplant.rowsPerPage = res.page['pageSize']
        }
      } catch (err) {
        console.log(err)
      }
    },
    async fetchrealcommonweather () {
      try {
        const requestParams = Object.assign({}, { lon: this.parcel.longitude, lat: this.parcel.latitude, weatherCategorys: 'condition,forecast15days,alert,forecast24hours' })
        const response = await excuteApis(requestParams, global.constant.commonApiUrls, 'common', 'queryMoJiWeather')
        const res = response.data
        if (res.flag === 1) {
          let weatherData = res.data
          if (weatherData.city) {
            this.$set(this.realcommonweather, 'weathercityname', weatherData.city.pname + weatherData.city.name)
          }
          if (weatherData.condition) {
            this.hasrealcommonweather = true
            this.$set(this.realcommonweather, 'updatetime', weatherData.condition.updatetime)
            this.$set(this.realcommonweather, 'condition', weatherData.condition.condition)
            this.$set(this.realcommonweather, 'temp', weatherData.condition.temp)
            this.$set(this.realcommonweather, 'windDir', weatherData.condition.windDir)
            this.$set(this.realcommonweather, 'windLevel', weatherData.condition.windLevel)
            this.$set(this.realcommonweather, 'humidity', weatherData.condition.humidity)
            this.$set(this.realcommonweather, 'icon', weatherData.condition.icon)
            this.$set(this.realcommonweather, 'precipitation', weatherData.condition.precipitation ? weatherData.condition.precipitation : '--')
            this.$set(this.realcommonweather, 'pressure', weatherData.condition.pressure ? weatherData.condition.pressure : '--')
            this.$set(this.realcommonweather, 'tips', weatherData.condition.tips ? weatherData.condition.tips : '--')
            this.$set(this.realcommonweather, 'iconpath', 'static/land/weather/W' + weatherData.condition.icon + '.png')
            this.$set(this.realcommonweather, 'timeday', weatherData.condition.updatetime ? getChinesesDay(new Date(weatherData.condition.updatetime)) : '未知')
          }
          if (weatherData.alert && weatherData.alert.length > 0) {
            let alert = weatherData.alert[0]
            if (alert) {
              this.$set(this.realcommonweather, 'alert_content', alert.content || '--')
              this.$set(this.realcommonweather, 'alert_infoid', alert.infoid || '--')
              this.$set(this.realcommonweather, 'alert_level', alert.level || '--')
              this.$set(this.realcommonweather, 'alert_name', alert.name || '--')
              this.$set(this.realcommonweather, 'alert_pub_time', alert.pub_time || '--')
              this.$set(this.realcommonweather, 'alert_title', alert.title || '--')
              this.$set(this.realcommonweather, 'alert_update_time', alert.update_time || '--')
              this.$set(this.realcommonweather, 'alert_type', alert.type || '--')
              let alertContent = this.realcommonweather['alert_content']
              console.log(alertContent)
              let alertDefenseStrPos = alertContent.indexOf('防御指南')
              if (alertDefenseStrPos >= 0) {
                let alertDefenseStr = alertContent.match(/(\S*)\u9632\u5FA1\u6307\u5357/)[1]
                this.$set(this.realcommonweather, 'alert_content', alertDefenseStr)
              }
            }
          }
          if (weatherData.forecast && weatherData.forecast.length > 2) {
            this.$set(this.realcommonweather, 'tempDay', weatherData.forecast[1].tempDay)
            this.$set(this.realcommonweather, 'tempNight', weatherData.forecast[1].tempNight)
            let dayforecast = []
            for (let i = 1; i < 8; i++) {
              // console.log(getChinesesDay(new Date(weatherData.forecast[i].predictDate)))
              let fourForecast = Object.assign({}, {
                predictDate: weatherData.forecast[i].predictDate,
                conditionDay: weatherData.forecast[i].conditionDay,
                tempDay: weatherData.forecast[i].tempDay,
                tempNight: weatherData.forecast[i].tempNight,
                windDirDay: weatherData.forecast[i].windDirDay,
                windLevelDay: weatherData.forecast[i].windLevelDay,
                dayiconpath: 'static/land/weather/W' + weatherData.forecast[i].conditionIdDay + '.png'
              })
              dayforecast.push(fourForecast)
            }
            this.$set(this.realcommonweather, 'dayforecast', dayforecast)
          }
          if (weatherData.hourly && weatherData.hourly.length > 0) {
            let temp = []
            let hourly = weatherData.hourly
            for (let i = 2; i < hourly.length; i++) {
              let hh = Object.assign({},
                { temp: hourly[i].temp }, // 温度
                { time: hourly[i].hour + ':00' }, // 时间
                { rainrate: hourly[i].pop + '%' }, // 降雨概率
                { humidity: hourly[i].humidity },  // 湿度
                { pressure: hourly[i].pressure },  // 压力
                { windforce: hourly[i].windSpeed },  // 风力，此有问题(风速+风力)
              )
              temp.push(hh)
            }
            this.$set(this.realcommonweather, 'forecast', Object.assign({}, { temp }))
          }
          if (weatherData.alert && weatherData.alert.length > 0) {
            this.$set(this.realcommonweather, 'tips', weatherData.alert[0].content)
            // weatherData.alert.forEach(item => {
            //   console.log(item.content)
            // })
          }
        }
      } catch (err) {
        console.log(err)
      }
    },
    showMeasureSoilDialog () {
      this.isListDialog = true
    },
    fetchProductType () {
      excuteApis({}, global.constant.commonApiUrls, 'category', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.producttypes = res.data
        }
      })
    },
    fetchQualities () {
      let requestParams = {
        dicttype: 'soil-texture'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.qualities = res.data
        }
      })
    },
    showAddPlantForm () {
      this.isPlantAdd = 'IS_ADD'
      this.plantid = null
    },
    showEditPlantForm (item) {
      this.isPlantEdit = 'IS_EDIT'
      this.plantid = item.id
    },
    showViewPlant (item) {
      this.plantid = item.id
      this.isPlantView = true
    },
    showResponsibleUserInfo (user) {
      let str = ''
      if (isArray(user)) {
        for (let i = 0; i < user.length; i++) {
          let username = user[i].name ? user[i].name : '--'
          username += user[i].phone ? '（' + user[i].phone + '）' : ''
          str += username + (i !== user.length - 1 ? ',' : '')
        }
      }
      return str || '--'
    },
    showBelongPeasantInfo (user) {
      let str = ''
      if (user) {
        let username = user.name
        username += user.linkphone ? '（' + user.linkphone + '）' : ''
        str += username
      }
      return str || '--'
    },
    // 显示对象属性值
    showObjValue (obj, key, defalutValue) {
      return getObjValue(obj, key, defalutValue)
    },
    async fetchFarmTypesData () {
      try {
        const requestParams = Object.assign({}, { parentcode: 'farmtype' })
        const response = await excuteApis(requestParams, global.constant.systemApis, 'dict', 'query')
        const res = response.data
        if (res.flag === 1) {
          this.farmtypes = res.data
        }
      } catch (err) {
        console.log(err)
      }
    },
    async fetchFarmRecordsData () {
      // console.log(this.parcel.supportid)
      this.toast = new Toast(this, '加载数据...', '')
      try {
        let filterParam = {}
        let filter = {}
        filter.farmland = { $oid: this.parcel.supportid }
        if (this.farmrecordkeyword && this.farmrecordkeyword.trim() !== '') {
          filter.content = { $regex: this.escapeCharacter(this.farmrecordkeyword.trim()) }
        }
        filterParam.filter = filter
        filterParam.sort = { startOn: -1 }
        filterParam.skip = (this.farmrecordpagination.page - 1) * this.farmrecordpagination.rowsPerPage
        filterParam.limit = this.farmrecordpagination.rowsPerPage
        let data = await mongo.db().collection('record').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.farmrecordpagination.total = Number(data.total.$numberLong)
        await this.bindData(items)
      } catch (err) {
        console.log(err)
        this.toast.setContent('查询数据出错，请联系系统管理员！')
      } finally {
        this.toast.close()
      }
    },
    // 绑定记录列表数据
    bindData (items) {
      this.farmrecords = []
      for (let item of items) {
        item.id = item._id.$oid
        item.creatorId = item.creator ? item.creator.$oid : ''
        item.startOnStr = item.startOn ? moment(Number(item.startOn.$numberLong)).format('YYYY-MM-DD') : '--'
        item.participatorsName = item.participators ? this.queryParticipatorsNameByIds(item.participators) : '--'
        this.farmrecords.push(item)
      }
      // console.log(this.farmrecords)
    },
    queryParticipatorsNameByIds (ids) {
      let names = ids.map(id => {
        let user = this.allUsers.find(elem => {
          return elem._id.$oid === id.$oid
        })
        return user ? user.name || user.mobile : null
      }).filter(elem => {
        return elem
      })
      return names.join(',') || '--'
    },
    // 转义字符串：判断字符串中是否包含正则表达式需要转义的特殊字符，如果存在，则加上转义字符后返回
    escapeCharacter (str) {
      if (!str) return
      let result = []
      let tmpStrs = str.toString().split('')
      let specialChars = ['$', '(', ')', '*', '+', '.', '?', '\\', '/', '^', '{', '}', '|']
      for (let char of tmpStrs) {
        let tmp = specialChars.find((item) => {
          return item === char
        })
        result.push(tmp ? '\\' + char : char)
      }
      return result.join('')
    },
    queryFarmRecordByCondition () {
      if (this.farmrecordpagination.page === 1) {
        this.fetchFarmRecordsData()
      } else {
        this.farmrecordpagination.page = 1
      }
    },
    goParcelList () {
      // 返回地块列表界面
      // this.$router.push({name: 'lands'})
      this.$store.state['viewLand']['currentTab'] = 'weather'
      this.$router.go(-1)
    },
    fetchProfileData () {
      // 查地块轮廓数据
      let self = this
      let land = new CLand(self.parcel)
      // this.parcel.longitude = land.getLongitude()
      // this.parcel.latitude = land.getLatitude()
      // this.fetchrealcommonweather()
      land.landProfile().then(response => {
        let res = response.data
        if (res.flag === 1) {
          if (res.data && res.data.length > 0) {
            let coords = []
            let points = res.data
            points.forEach((point) => {
              let coord = Object.assign({},
                { longitude: point.longitude },
                { latitude: point.latitude },
              )
              coords.push(coord)
            })
            if (coords.length > 2) {
              this.coords = coords
            }
            // self.$set(self.parcel, 'longitude', coords[0].longitude)
            // self.$set(self.parcel, 'latitude', coords[0].latitude)
            this.fetchrealcommonweather()
          }
        }
      })
    },
    operateOk () {
      // 删除地块信息
      if (this.prompt.id.indexOf('del-parcel') === 0) {
        let parcel = this.prompt.data
        excuteApis(Object.assign({},
          { id: parcel.id, supportid: parcel.supportid }),
          global.constant.parcelApis, 'parcel', 'delete').then(data => {
            let res = data.data
            this.prompt.dialog = false
            this.prompt.data = null
            if (res.flag === 1) {
              this.$toast.top('地块删除成功.')
              this.$router.push({ name: 'lands' })
            } else {
              this.$toast.top(res.message)
            }
            this.openLoading()
          })
      }
      // 删除种植信息
      if (this.prompt.id.indexOf('del-plant') === 0) {
        let plant = this.prompt.data
        excuteApis(Object.assign({}, { 'id': plant.id }),
          global.constant.parcelApis, 'parcelPlant', 'delete').then(response => {
            let res = response.data
            this.prompt.dialog = false
            this.prompt.data = null
            this.viewPlantDialog = false
            if (res.flag === 1) {
              this.$toast.top('种植信息删除成功.')
              this.fetchPlantData()
            } else {
              this.$toast.top(res.message)
            }
            this.openLoading()
          })
      }
    },
    operateCancel () {
      this.prompt.dialog = false
      this.prompt.id = ''
      this.prompt.title = ''
      this.prompt.body = ''
      this.prompt.data = null
    },
    removeParcel (parcel) {
      this.prompt.id = 'del-parcel'
      this.prompt.data = parcel
      this.prompt.dialog = true
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该地块信息吗？'
      this.prompt.ok = true
    },
    removePlant (plant) {
      this.prompt.id = 'del-plant'
      this.prompt.data = plant
      this.prompt.dialog = true
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该种植信息吗？'
      this.prompt.ok = true
    },
    openLoading () {
      let self = this
      setTimeout(function () {
        self.closeLoading()
      }, 2000)
    },
    closeLoading () {
      this.$loading.close()
    },
    plantToggle () {
      this.isPlantMore = !this.isPlantMore
      if (this.isPlantMore) {
        this.temPlants = this.plants
      } else {
        this.temPlants = this.plants.slice(0, 5)
      }
    },
    viewFarmrecordDetial (id) {
      if (!id) return
      this.curRecordId = id
      this.viewRecordDialog = true
    },
    chinesesDay (datestr) {
      let date = new Date(datestr)
      return getChinesesDay(date)
    },
    findBkData (id) {
      this.curr_bk_data = null
      this.bk_src = ''
      this.bk_time = '--'
      this.win_sdirection = '--'
      let p = mongo.db('iot').collection('tenant.device').find({ filter: { _id: { '$oid': id } } })
      p.then(items => {
        if (JSON.stringify(items.items[0].accessState.data) !== '{}') {
          let currenttime = fmtDay(items.items[0].accessState.time.$numberLong)
          let recentlyDateTimeStr = addHour(new Date(currenttime), 1)
          let nowDateTimeStr = addHour(new Date(), 0)
          if (recentlyDateTimeStr >= nowDateTimeStr) { // 取最近一小时内数据作为实况
            this.curr_bk_data = items.items[0].accessState.data
            this.bk_time = fmtTime(items.items[0].updatedOn.$numberLong)
            this.bk_date_time = fmtDay(items.items[0].accessState.time.$numberLong)
            this.bk_src = this.curr_bk_data['0:65'].v || '/static/land/bk_weather/zanwu.png'
            this.win_sdirection = windirection2Text(this.curr_bk_data['0:64'].v)
          } else { // 如果没有实况则显示为空
            this.curr_bk_data = null
            this.bk_src = '/static/land/bk_weather/zanwu.png'
            this.bk_time = '--'
            this.win_sdirection = '--'
          }
        } else {
          this.curr_bk_data = null
          this.bk_src = '/static/land/bk_weather/zanwu.png'
          this.bk_time = '--'
          this.win_sdirection = '--'
        }
      })
      this.fetchBkHoursForecast(id)
    },
    findBkDeviceByFarlandId (fid) {
      let typesPot = this.$store.state['base.device.type'].items.filter(t => t.code === 106)
      if (typesPot) {
        let typeIds = []
        typesPot.forEach(d => {
          typeIds.push({ '$oid': d._id.$oid })
        })
        let p = mongo.db('iot').collection('tenant.device').find({ filter: { 'farmland': { '$oid': fid }, 'type': { '$in': typeIds } } })
        p.then(d => {
          this.bkDevice = d.items[0]
          for (let i = 0; i < d.items.length; i++) {
            this.bk_items.push(Object.assign({}, { id: d.items[i]._id.$oid, name: d.items[i].name + `(${d.items[i].code})`, type: d.items[i].type.$oid }))
          }
          this.bk_device_name = (this.bk_items[0] && this.bk_items[0].id) ? this.bk_items[0].id : ''
          let time = new Date(new Date().toLocaleDateString()).getTime()
          mongo.db('iot').collection('tenant.device_bloomsky_video_data').find({ filter: { 'device': { '$oid': (this.bkDevice && this.bkDevice._id) ? this.bkDevice._id.$oid : null }, time: { '$gt': time - 8 * 24 * 60 * 60 * 1000, '$lt': time } }, sort: { time: -1 } }).then(d => {
            this.vedios = d.items
          })
          if (this.bkDevice) this.findBkData(this.bkDevice._id.$oid)
        })
      }
    },
    reloadBkDevice (did) {
      let time = new Date(new Date().toLocaleDateString()).getTime()
      Promise.all([
        mongo.db('iot').collection('tenant.device').find({ filter: { '_id': { '$oid': did } } }),
        mongo.db('iot').collection('tenant.device_bloomsky_video_data').find({ filter: { 'device': { '$oid': this.bkDevice._id.$oid }, time: { '$gt': time - 8 * 24 * 60 * 60 * 1000, '$lt': time } }, sort: { time: -1 } })
      ]).then(res => {
        this.bkDevice = res[0].items[0]
        this.bkDevice && this.findBkData(this.bkDevice._id.$oid)
        this.vedios = res[1].items
      })
    },
    fmt_Date (time) {
      let d = new Date(+time)
      return `${d.getFullYear()}-${pre(d.getMonth() + 1)}-${pre(d.getDate())}`
    },
    /**
      * 新增农事记录
      */
    addRecord () {
      if (!this.parcel.supportid) return
      this.addRecordDialog = true
      this.addRecordDialogTitle = '新建农事记录'
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.id = Number(this.$route.query.id)
    this.fetchData()
    this.fetchProductType()
    this.fetchFarmTypesData()
    this.currentTab = this.$store.state['viewLand']['currentTab']
    this.allUsers = this.$store.state.allUsers
  },
  components: {
    LandGis,
    BkVedio,
    BkWeather,
    VueAliplayer,
    VPlantaddedit,
    VPlantview,
    VSoillist,
    VFarmerrecordsdetail,
    VFarmerrecordsadd
  }
}
</script>

<style scoped>
.bk_vedio {
  background-color: #fff;
  height: 700px;
  width: 100%;
}
.bk_vedio_1 {
  width: 100%;
  background-color: #fff;
  cursor: pointer;
}
.bk_vedio_2 {
  float: left;
  margin-left: 10px;
}
.bk_vedio_3 {
  width: 40%;
}
.bk {
  display: flex;
}
.bk_left_img {
  position: relative;
  margin-top: 10px;
  width: 220px;
  height: 220px;
}
.bk_weather {
  display: flex;
  flex: 1;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  color: white;
  margin-top: 10px;
  /* padding-top: 20px; */
  /* padding-bottom: 10px; */
  margin-left: 10px;
  height: 220px;
}
.bk_p {
  height: 22px;
  margin-top: 15%;
}
.bk_weather_1 {
  flex: 1;
  width: 180px;
  height: 200px;
  margin-left: 30px;
  padding-top: 12px;
}
.bk_weather_2 {
  flex: 1;
  width: 180px;
  height: 200px;
  padding-top: 35px;
}
.bk_weather_3 {
  flex: 1;
  width: 180px;
  height: 200px;
  padding-top: 35px;
}
.bk_right_div_1 {
  margin-left: 30px;
  flex: 1;
  height: 220px;
}
.bk_right_div_1_font {
  font-size: 48px;
}
.parcelname {
  line-height: 45px;
}
.plant {
  border: 1px dashed #e0e0e0;
  padding: 10px 10px;
  cursor: pointer;
}
.viewPlantTitle {
  line-height: 55px;
}

.weatherPosition {
  line-height: 52px;
}
.weatherImg {
  margin-top: 45px;
  height: 30px;
  width: auto;
}
.weatherValue {
  font-size: 45px;
  color: white;
}
.bk_weatherValue {
  font-size: 40px;
  font-weight: 1;
  color: white;
  margin-top: 20%;
  margin-left: 20px;
}
.highttemp {
  margin-left: 20px;
}
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.farmcontent {
  width: 250px;
}
.editLabel {
  line-height: 58px;
  padding-right: 5px;
}
.editAttachmentDisplay {
  line-height: 75px;
}
.weather {
  /* background-image: url("../../../static/land/list/tianqibeijing.png"); */
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  color: white;
  margin-top: 10px;
  padding-top: 20px;
  padding-bottom: 10px;
}
.scrollbar {
  overflow-y: hidden;
  overflow-x: hidden;
}
.scrollbar:hover {
  overflow-x: auto;
}
.profilecontainer {
  /* border: 1px solid red; */
  position: relative;
  height: 180px;
  width: 180px;
  /* background-image: url("../../../static/land/list/ditu.png"); */
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
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
.btnmesuresoil {
  align-self: flex-end;
}
.noweather {
  height: 200px;
  text-align: center;
  margin: 0 auto;
  /* background-color: #84c8e4; */
}
.noiot {
  line-height: 200px;
  text-align: center;
  margin: 0 auto;
}
.currentWeather {
  padding-top: 10px;
}
.responsibleuser {
  border: 1px solid red;
}
.dialog__content {
  height: 100px;
}
li.subheader {
  margin-top: 0px;
}
.addMeasureSoilDialog {
  /* border: 1px solid red; */
  height: 450px;
}
.parcelcontent {
  font-style: 14px;
  color: #999999;
  padding-left: 14px;
}
.lineheight {
  line-height: 30px;
}
.btnblock {
  position: relative;
  padding-top: 120px;
}
.attachfile {
  cursor: pointer;
  text-decoration: underline;
  color: #39bbb0 !important;
}
.cetu {
  color: #39bbb0 !important;
  cursor: pointer;
}
.cetutext {
  position: relative;
  z-index: 999999;
}
.divider {
  width: 280px;
}
.fbClz {
  width: 100px;
  height: 50px;
}
.hbClz {
  width: 120px;
  height: 50px;
}
.fClz {
  border-bottom: solid 1px #e0e0e0;
}
.fsClz {
  border-bottom: solid 2px #39b4a8;
}
.hClz {
  border-bottom: solid 1px #e0e0e0;
}
.hsClz {
  border-bottom: solid 2px #39b4a8;
}
.vClz {
  border-bottom: solid 1px #e0e0e0;
}
.vsClz {
  border-bottom: solid 2px #39b4a8;
}
button {
  outline: none;
}
</style>
