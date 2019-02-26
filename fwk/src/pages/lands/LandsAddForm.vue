<template lang="pug">
  v-card.pt-0(class="page-content")
    v-form(v-model="valid" ref="form" lazy-validation autocomplete="off")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="name"
            v-model="parcel.name"
            counter="20"
            :rules = "rules.name"
            maxlength="20"
            required
            label="地块名称")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-autocomplete(
            label="地块类型"
            v-bind:items="parceltypes"
            v-model="parcel.type"
            item-text="dictname"
            item-value="dictvalue"
            no-data-text="无"
            )    
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          v-text-field(
            name="area"
            v-model="parcel.area"
            type="number"
            label="地块占地面积（亩）"
            required
            :rules ="rules.area")      
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
         span(style="color: grey;") 地块位置
        v-flex(xs12 sm12 md8 lg8 xl8)
          v-areaselect(:curAddressCode.sync="curAreaCode" :level = "5" :isHideDetails="false")
        v-flex.pl-1(xs12 sm12 md4 lg4 xl4)
          v-text-field(
            label="详细地址"
            name="addressdetail"
            v-model="parcel.addressdetail"
            counter="20"
            :rules = "rules.name1"
            maxlength="20")
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12)
          span(style="color: grey;") 地块GIS地形图
        v-flex.py-3(xs12 sm12 md10 lg5 xl5)
          div(v-if="polygon.latlngs.length < 3")
            v-avatar(class="grey lighten-4" tile=true size="120")
              v-icon(title="手工点选绘制" aria-hidden="true" class="dark cusorstyle" @click="showDrawProfileDialog") gesture
              div(style="border: 1px solid rgba(0,0,0,.2); height: 40px;")
              v-icon(title="导入勘测数据" aria-hidden="true" class="dark cusorstyle" @click="gpxSelectorClick") wallpaper 
              input(hidden type="file" @change="gpxFileSelected" accept=".gpx" ref="gpxInput")
          div(v-else)
            v-avatar(class="grey lighten-4" tile=true size="120" @mouseenter="isShowCover = true" @mouseleave="isShowCover = false")
              v-landprofile(:lanlngs="convertLngLat(polygon.latlngs)" :size="120")
              div(v-bind:style="isShowCover ? showCover : noneCover")
                v-icon(aria-hidden="true" class="dark cusorstyle" @click="showDrawProfileDialog") gesture
                v-icon(aria-hidden="true" class="dark cusorstyle" @click="recoverDrawProfile") delete_forever
      v-layout(row, wrap, justify-space-between)
        v-flex(xs5 sm5 md5 lg5 xl5)
          v-text-field(
            label="经度"
            name="longitude"
            v-model="parcel.longitude"
            disabled)
        v-flex(xs5 sm5 md5 lg5 xl5)
          v-text-field(
            label="纬度"
            name="latitude"
            v-model="parcel.latitude"
            disabled)
      v-layout(row, wrap)
        v-flex(xs12 sm12 md12 lg12 xl12 class="btnMore text-xs-center" @click="isExpand = !isExpand")
          v-icon() {{isExpand ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}}
          span() {{isExpand ? '点击收起更多内容' : '点击填写更多内容'}}
      div(v-show="isExpand")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-text-field(
              name="elevation"
              label="海拔（米）"
              v-model="parcel.elevation"
              type="number"
              :rules ="rules.elevation")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-select(
                label="地块从属"
                v-bind:items="ownerships"
                v-model="parcel.dependenceid"
                item-text="dictname"
                item-value="dictvalue"
                no-data-text="无")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-autocomplete(
                label="地块权属人"
                v-bind:items="belongpeasants"
                v-model="parcel.belongpeasantid"
                item-text="name"
                item-value="id"
                no-data-text="无"
                :disabled="editBelongpeasant"
                )
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-autocomplete(
                label="地块参保状态"
                v-bind:items="insurances"
                v-model="parcel.insurance"
                item-text="dictname"
                item-value="dictvalue"
                no-data-text="无"
                )             
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-text-field(
              label="地块负责人"
              v-model="userStateName"
              @focus="showSelectUerTree")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-dropzoneupload(:files.sync="parcel.attachments")
        v-layout(row, wrap)
          v-flex(xs12 sm12 md12 lg12 xl12)
            v-textarea(
              label="其他说明"
              v-model="parcel.introduce"
              counter="500"
              maxlength="500"
              :rules = "rules.introduce")
    v-layout.pt-2(row)
      v-flex(xs12 sm12 md12 lg12 xl12 class="text-xs-center")
        v-btn(@click="cancelParcel") 取消
        v-btn(@click="saveParcel" :color="theme" dark) 保存
    v-layout(row, justify-center)
      v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
        div {{prompt.body}}
        template(slot="btnActions")
          v-divider
          v-card-actions
            v-spacer
            v-btn(flat, color="primary", @click.native.stop="back") 取消
            v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定
    v-layout(row, justify-center)
      v-modaldialog(
        :isShow.sync="selectUserTree"
        title="选择用户",
        :modalMaxWidth="400",
        :bodyHeight="300"
        )
        v-elementtree(
          :treedata="departUserTreeData",
          :isFilter="false",
          :filterText="filterText",
          :isMultiSelect="true",
          :checkedCallbackFn="nodeUsercheckclick",
          :renderContent="renderUserContent",
          :iniDataState="userState",
          :resetDataState="resetDataState"
          treeRef="userTreeRef")
        template(slot="btnActions")
          v-divider
          v-card-actions
            v-spacer
            v-btn(flat, color="primary", @click.native.stop="cancelUserSelected") 取消
            v-btn(color="primary", flat, @click.native.stop="selectedUserSure") 确定
    v-layout(row, justify-center)
      v-modaldialog(
        :isShow.sync="drawProfileDialog"
        title="手工描绘地形",
        :modalMaxWidth="990",
        :bodyHeight="635",
        :showCloseBtn="true"
        )
        template(slot="toolbarItem")
          v-toolbar-items
            v-btn(flat small @click.native="startDrawProfile") {{ this.polygon.editing ? '停止绘制' : '开始绘制' }}
            v-btn(flat small @click.native="recoverDrawProfile" v-if="polygon.editing") 删除
            v-btn(flat small @click.native="btnSaveDrawProfile") 确定
        c-map-layers(v-bind="map.layers" class="curMapView" v-if="drawProfileDialog")
          c-polygon(:latlngs.sync="polygon.latlngs" :editing="polygon.editing")
    v-modaldialog(:isShow="isOverrideCoords")
      div {{tipContent}}
      template(slot="btnActions")
        v-divider
        v-card-actions
          v-spacer
          v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="okCoordsOverride") 确定
          v-btn(flat, color="primary", @click.native.stop="closeCoordsOverrideTips") 取消
</template>

<script>

import { excuteApis } from '@/apis'
import CMapLayers from '@agrithings/cmap/CMapLayers'
import CPolygon from '@agrithings/cmap/overlay/CPolygon'
import { types } from '@agrithings/cmap/util'
import { CLand } from '@/models/land'
import TreePart from '@/components/TreePart'
import { centerOf, deAreasCode } from '@/utils'
import { Toast } from '@/models/toast'
import VDropzoneupload from '@/components/DropzoneUpload.vue'

import mongo, { config as MongoConfig } from '@vehiclecloud/mongo-rest-client'
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

export default {
  data () {
    return {
      isMultiSelect: true,
      isFilter: true,
      filterText: '',
      treedata: [],
      leftTreeData: [], // 左边树形结构的数据
      editUser: null, // 编辑时的参与人
      drawProfileDialog: false,
      ownerships: [],
      ownership: null,
      provinces: [],
      province: 0,
      cities: [],
      city: 0,
      counties: [],
      county: 0,
      towns: [],
      town: 0,
      villages: [],
      village: 0,
      valid: true,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        name: [
          (v) => !!v || '必填项',
          (v) => v.length <= 20 || '不超过20个汉字'
        ],
        type: [
          (v) => !!v || '必填项'
        ],
        name1: [
          (v) => this.rulesName1(v)
        ],
        elevation: [
          (v) => this.elevationRule(v)
        ],
        introduce: [
          (v) => v.length <= 500 || '不超过500个字符'
        ],
        area: [
          (v) => !!v || '必填项',
          (v) => v && v >= 0 && v.valueOf() <= 1000000 && ((/^\+?[1-9][0-9]*(\.\d{1,2})?$/.test(v)) || (/^0(\.\d{1,2})?$/.test(v))) || '请输入有效数字,最大为1000000,小数保留两位'
        ]
      },
      parcel: {
        id: null,
        name: '',
        type: 'parcel-type-farmland',
        addresscode: '',
        addressdetail: '',
        area: null,
        elevation: null,
        dependenceid: 'parcel-self',
        belongpeasantid: '',
        introduce: '',
        longitude: null,
        latitude: null,
        coords: [],
        insurance: 'parcel-insurance-uninsured',
        attachments: []
      },
      parcels: [],
      parceltypes: [],
      insurances: [],
      categoryid: '',
      producttypes: [
        {
          id: null,
          name: '全部'
        }
      ],
      keyword: null,
      pagination: {},
      pages: 0,
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      selectedItemId: null,
      isExpand: false,
      selectUserTree: false,
      departTreeData: [],
      dependencename: null,
      belongpeasants: [],
      belongpeasantname: '',
      belongpeasantphone: '',
      departUserTreeData: [],
      selectedUser: [],
      cacheSelectedUser: [],
      resetDataState: false,
      centerLatLng: null,
      map: {
        layers: {
          mapTypes: [types.YCMIXMAP, types.VECTORMAP],
          mapType: types.YCMIXMAP,
          center: { lat: 36.87817, lng: 110.19415 },
          zoom: 18,
          // zoomControl: { position: 'topright' },
          scaleControl: true,
          overviewControl: false,
          largeMapControl: { position: 'topright', styleNS: 'leaflet-control-zoomslider' },
          mapTypeControl: false,
          latLngControl: false
        }
      },
      polygon: {
        latlngs: [],
        editing: false
      },
      lastLatlngs: [],
      isFocused: false,
      theme: 'primary',
      orgid: '',
      importTitle: '',
      curAreaCode: '',
      isOverrideCoords: false,
      btnFlag: '',
      tipContent: '',
      countyname: '',
      isShowCover: false,
      showCover: {
        display: 'inherit',
        position: 'absolute',
        width: '100%',
        height: '100%',
        background: 'rgba(0, 0, 0, 0.3)',
        borderRadius: '5px'
      },
      noneCover: {
        display: 'none',
        position: 'absolute',
        width: '100%',
        height: '100%',
        background: 'rgba(0, 0, 0, 0.3)',
        borderRadius: '5px'
      }
    }
  },
  watch: {
    'parcel.belongpeasantid': function (val) {
      if (val && this.belongpeasants instanceof Array && this.belongpeasants.length > 0) {
        let peasent = this._.find(this.belongpeasants, ['id', val])
        this.belongpeasantphone = peasent.linkphone
      } else {
        this.belongpeasantphone = null
      }
    },
    curAreaCode: function (val, oldval) {
      if (val && val.substr(4, 2) !== '00') {
        let countycode = val.substr(0, 6) + '000000' + val.substr(0, 6) + '000000'
        this.areaName(countycode)
      }
    },
    countyname: function (val, oldval) {
      if (val) {
        this.fetchParcelposition(val)
      }
    },
    'polygon.latlngs': function (v) {
      if (v.length >= 3) {
        let lng = this.map.layers.center.lng
        let lat = this.map.layers.center.lat
        this.parcel.longitude = Number(lng).toFixed(7)
        this.parcel.latitude = Number(lat).toFixed(7)
      }
    }
  },
  methods: {
    fetchParcelposition (countyname) {
      let requestParams = Object.assign({}, { name: countyname })
      excuteApis(requestParams, global.constant.commonApiUrls, 'poslatlng', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data.length > 0) {
          let data = res.data[0]
          let curCenterLatLng = Object.assign({}, { lat: data.lat, lng: data.lng })
          this.map.layers.center = curCenterLatLng
        }
      })
    },
    async areaName (areaCode) {
      let dbClient = mongo.db('iss').collection('base.area')
      let { items } = await dbClient.find({ filter: { _id: { $oid: areaCode } } })
      if (items && items.length) this.countyname = items[0].name
    },
    closeCoordsOverrideTips () {
      this.isOverrideCoords = false
      if (this.btnFlag.indexOf('manualDraw') === 0) {
        this.polygon.editing = !this.polygon.editing
      }
    },
    okCoordsOverride () {
      this.isOverrideCoords = false
      if (this.btnFlag.indexOf('gpxImport') === 0) {
        this.$refs.gpxInput.value = null
        this.$refs.gpxInput.click()
      } else {
        this.saveDrawProfile()
      }
    },
    rulesName1 (v) {
      if (v) {
        return (v.length <= 20 || '不超过20个汉字')
      }
      return true
    },
    elevationRule (v) {
      if (v) {
        return (v && v >= 0 && v.valueOf() <= 10000 || '请输入有效数字,0~10000')
      }
      return true
    },
    gpxSelectorClick () {
      // 导入勘测数据
      this.btnFlag = 'gpxImport'
      this.tipContent = '已有地形数据将被覆盖，是否继续？'
      if (this.polygon.latlngs.length > 2) {
        this.isOverrideCoords = true
      } else {
        this.$refs.gpxInput.value = null
        this.$refs.gpxInput.click()
      }
    },
    extractGPX (event) {
      let gpxRegex = /lon="([^"]*)" lat="([^"]*)"/g
      let gpxRegex0 = /lat="([^"]*)" lon="([^"]*)"/g
      let m
      let m0
      let result = []
      while ((m = gpxRegex.exec(event.target.result)) !== null) {
        result.push({ lng: m[1], lat: m[2] })
      }
      while ((m0 = gpxRegex0.exec(event.target.result)) !== null) {
        result.push({ lat: m0[1], lng: m0[2] })
      }
      return result
    },
    gpxFileSelected (e) {
      const files = e.target.files
      if (!files) return
      let file = files[0]
      let reader = new FileReader()
      let self = this
      reader.onload = event => {
        // let gpxRegex = /lon="([^"]*)" lat="([^"]*)"/g
        // let m
        // let result = []
        // while ((m = gpxRegex.exec(event.target.result)) !== null) {
        //   result.push({ lng: m[2], lat: m[1] })
        // }
        // self.setValue(result)
        self.setValue(self.extractGPX(event))
      }
      reader.readAsText(file)
    },
    setValue (v) {
      this.$emit('input', v)
      if (v && v.length > 2) {
        this.polygon.latlngs = v
        this.map.layers.center = centerOf(v)
        this.lastLatlngs = v
        this.importTitle = '(已完成)'
      }
    },
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
          this.producttypes = this.producttypes.concat(res.data)
        }
      })
    },
    fetchParceltype () {
      let requestParams = {
        dicttype: 'parcel-type'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.parceltypes = res.data
        }
      })
    },
    fetchInsurance () {
      let requestParams = {
        dicttype: 'parcel-insurance'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.insurances = res.data
        }
      })
    },
    recursionTree (data) {
      // 增加节点显示属性
      if (data && data.length > 0) {
        for (let i = 0; i < data.length; i++) {
          if (data[i].label) return
          data[i].label = data[i].name ? data[i].name : '--(' + data[i].phone + ')'
          if (data[i].children) {
            this.recursionTree(data[i].children)
          }
        }
      }
    },
    fetchDepart () {
      let requestParams = {}
      excuteApis(Object.assign(requestParams, { showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(response => {
        let res = response.data
        let data = res.data
        if (res.flag === 1 && data && data.length > 0) {
          this.recursionTree(data)
          this.departTreeData = data
        }
      })
    },
    fetchPeasant () {
      let requestParams = {}
      this.belongpeasants = []
      excuteApis(requestParams, global.constant.archivesApis, 'peasantprotocol', 'peasantlist').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data && res.data.length > 0) {
          let data = res.data
          for (let i = 0; i < data.length; i++) {
            this.belongpeasants.push(Object.assign(data[i], { name: (data[i].name ? data[i].name : '--') + '(' + data[i].linkphone + ')' }))
          }
        }
      })
    },
    saveParcel () {
      let land = new CLand(this.parcel)
      if (this.$refs.form.validate(this.parcel)) {
        let { provincecode, citycode, countycode, towncode, villagecode } = deAreasCode(this.curAreaCode)
        this.parcel.longitude = this.lastLatlngs.length >= 3 ? this.map.layers.center.lng : null
        this.parcel.latitude = this.lastLatlngs.length >= 3 ? this.map.layers.center.lat : null
        land.setResponsibleuserids(this.formatedSelectUserIds(this.selectedUser))
        if (this.lastLatlngs.length > 0) {
          this.lastLatlngs.forEach((latlng, index) => {
            this.parcel.coords[index] = Object.assign({}, { longitude: latlng.lng, latitude: latlng.lat, sort: index })
          })
        }
        // land.setAddresscode(this.parcel.addresscode)
        land.setLongitude(this.parcel.longitude)
        land.setLatitude(this.parcel.latitude)
        land.setCoords(this.parcel.coords)
        land.setProvincecode(provincecode)
        land.setCitycode(citycode)
        land.setCountycode(countycode)
        land.setTowncode(towncode)
        land.setVillagecode(villagecode)
        land.addLand().then(response => {
          let res = response.data
          if (res.flag === 1) {
            // this.$router.push({ path: 'successlandstips', query: { id: res.data.id, opflag: 0 } })
            this.$store.dispatch('getOrgLands') // 更新缓存
            this.$router.push({ name: 'lands' })
          } else {
            this.prompt.dialog = true
            this.prompt.title = '错误提示信息'
            this.prompt.body = res.message
            this.prompt.ok = false
          }
        })
        // 为农事记录模块提前缓存数据(add by zhangyq on 2018-11-08)
        this.$store.dispatch('getAllFarmLands')
        this.$store.dispatch('getTenantFarmLands', this.orgid)
      }
    },
    cancelParcel () {
      this.$router.go(-1)
    },
    showDrawProfileDialog () {
      this.drawProfileDialog = true
    },
    startDrawProfile () {
      this.polygon.editing = !this.polygon.editing
    },
    recoverDrawProfile () {
      this.lastLatlngs = []
      this.$set(this.polygon, 'latlngs', [])
      this.parcel.longitude = 0
      this.parcel.latitude = 0
      this.$set(this.polygon, 'editing', false)
    },
    btnSaveDrawProfile () {
      if (!this.polygon.editing) {
        this.drawProfileDialog = false
        return
      }
      // 弹出提示框
      this.btnFlag = 'manualDraw'
      this.tipContent = '已有地形数据将被覆盖，是否继续？'
      if (this.lastLatlngs.length > 2) {
        this.isOverrideCoords = true
      } else {
        this.saveDrawProfile()
      }
    },
    saveDrawProfile () {
      this.lastLatlngs = this.polygon.latlngs
      this.polygon.editing = false
      this.drawProfileDialog = false
      if (this.polygon.latlngs.length > 2) {
        this.importTitle = '(已完成)'
        this.map.layers.center = centerOf(this.polygon.latlngs)
      }
    },
    drawProfileDialogClose () {
      this.recoverDrawProfile()
      this.drawProfileDialog = false
    },
    renderContent (h, { node, data, store }) {
      if (data.sign === '1') {
        return (
          <span class="custom-tree-node">
            <span style="font-weight: bold;">{node.label}</span>
          </span>
        )
      } else {
        return (
          <span class="custom-tree-node">
            <span>{node.label}</span>
          </span>
        )
      }
    },
    // 获取用户树数据
    fetchUsers () {
      excuteApis({}, global.constant.userApisUrls, 'user', 'queryUserAndDepartByLoginUserOrg').then(response => {
        let res = response.data
        if (res.flag === 1) {
          let data = res.data
          if (data && data.length > 0) {
            this.recursionTree(data)
            this.departUserTreeData = data
          }
        }
      })
    },
    // 1.0 格式化用户选择所显示的信息内容
    formatedSelectUser (users) {
      let userphone = []
      users.forEach(user => {
        if (user.type === '1') return
        let uname = user.name ? user.name : '--'
        let uphone = user.phone ? '(' + user.phone + ')' : ''
        let up = uname + uphone
        userphone.push(up)
      })
      return userphone.join(',')
    },
    // 1.1 显示用户选择树
    showSelectUerTree () {
      this.resetDataState = false
      this.selectUserTree = !this.selectUserTree
    },
    // 1.2 点击树节点回调函数
    // nodeUserclick (data, node, tree) {
    //   node.checked = !node.checked
    //   // if (node.type === '1') return
    //   let userdata = node.data
    //   node.checked ? (userdata.type === '2' ? this.cacheSelectedUser.push(data) : () => {
    //     let that = this
    //     let curindex = that.cacheSelectedUser.forEach((user, index) => {
    //       if (user.id === node.id) {
    //         return index
    //       }
    //     })
    //     that.cacheSelectedUser.splice(curindex, 1)
    //   }) : ''
    // },
    // 1.3 点击树节点复选框回调函数
    nodeUsercheckclick (data, treestate) {
      this.cacheSelectedUser = treestate.checkedNodes.filter((node) => {
        return node.type === '2'
      })
    },
    // 1.4 渲染节点dom
    renderUserContent (h, { node, data, store }) {
      if (data.type === '2') {
        return (
          <span class="custom-tree-node">
            <span>{node.label}</span>
          </span>
        )
      } else {
        return (
          <span class="custom-tree-node">
            <span style="font-weight: bold;">{node.label}</span>
          </span>
        )
      }
    },
    // 1.5 点击用户树选择模态框确定按钮回调函数
    selectedUserSure () {
      this.selectedUser = this.cacheSelectedUser
      this.formatedSelectUser(this.selectedUser)
      this.selectUserTree = false
    },
    // 1.6 点击用户树选择模态框取消按钮回调函数
    cancelUserSelected () {
      this.resetDataState = true
      this.selectUserTree = false
    },
    // 1.7 获取所选用户ids,返回逗号分隔的ids
    formatedSelectUserIds (users) {
      let ids = []
      if (users && users.length > 0) {
        users.forEach(user => {
          ids.push(user.id)
        })
      }
      return ids
    },
    // convert lng to longitude, lat to latitude
    convertLngLat (lngLat) {
      let longLatLng = []
      if (lngLat && lngLat.length >= 3) {
        lngLat.forEach(item => {
          let ll = Object.assign({}, { longitude: item.lng, latitude: item.lat })
          longLatLng.push(ll)
        })
      }
      return longLatLng
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.orgid = global.helper.ls.get('user').loginorgid
    this.curAreaCode = '100000000000100000000000'
    this.fetchLandOwership()
    this.fetchProductType()
    this.fetchParceltype()
    this.fetchInsurance()
    this.fetchDepart()
    this.fetchUsers()
    this.fetchPeasant()
    this.lastLatlngs = this.polygon.latlngs // 保存上次所绘制的轮廓
    this.toast = new Toast(this, '上传中......', null)
  },
  mounted () { },
  computed: {
    userState: function () {
      let uids = []
      this.selectedUser.forEach(user => {
        uids.push(user.id)
      })
      return uids
    },
    userStateName: function () {
      let users = this.selectedUser
      let usersname = this.formatedSelectUser(users)
      return usersname
    },
    editBelongpeasant: function () {
      (this.parcel.dependenceid === 'parcel-self') ? (this.parcel.belongpeasantid = '') : null
      return this.parcel.dependenceid === 'parcel-self'
    }
  },
  components: {
    CMapLayers,
    CPolygon,
    TreePart,
    VDropzoneupload
  }
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
.weatherImg {
  height: 100px;
  width: auto;
}
.weatherValue {
  font-size: 45px;
  line-height: 160px;
}
.gisBtn {
  width: 100%;
}
.btnMore {
  background-color: #fafafa;
  cursor: pointer;
  padding-top: 10px;
  padding-bottom: 10px;
}
.fieldlabel {
  text-align: right;
}
.selectedtitle {
  line-height: 45px;
}
.search {
  padding-top: 10px;
  top: -10px;
}
.selectedTips {
  text-align: right;
  color: red;
}
.curMapView {
  width: 960px;
  height: 600px;
}
.cusorstyle {
  cursor: pointer;
}
.img-cover {
  display: none;
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 5px;
}
</style>
