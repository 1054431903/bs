<template>
  <div class="main-container">
    <div class="panel">
      <div class="edit-table">
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required">*</span>
            <span class="edit-table-cell-label-span">地块名称</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input" placeholder="请输入地块名称" type="string" v-model="name" />
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required">*</span>
            <span class="edit-table-cell-label-span">地块类型</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.typePicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块类型" readonly type="string" v-model="type" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="typePicker" :color-confirm="themeColor" :data="typePickerData" :formatter="typePickerFormatter" @confirm="typePickerConfirm"></v-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required">*</span>
            <span class="edit-table-cell-label-span">占地面积</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input" placeholder="请输入占地面积" type="number" v-model="occupiedArea" />
          </div>
          <div class="edit-table-cell edit-table-cell-unit">
            亩
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required">*</span>
            <span class="edit-table-cell-label-span">地块位置</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.addressPicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块类型" readonly type="请选择地块位置" v-model="address" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-area-picker ref="addressPicker" :color-confirm="themeColor" text-title="请选择地址" :data="addressPickerData" :formatter="addressPickerFormatter" @confirm="addressPickerConfirm"></v-area-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">详细地址</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input" placeholder="请输入详细地址" type="string" v-model="addressDetails" />
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span readonly">地块编号</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input readonly" placeholder="请输入地块编号" readonly type="string" v-model="code" />
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">地块海拔</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input" placeholder="请输入地块海拔" type="number" v-model="altitude" />
          </div>
          <div class="edit-table-cell edit-table-cell-unit">
            米
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span readonly">地块经纬度</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            <input class="edit-table-cell-input readonly" placeholder="请输入地块纬度" type="string" readonly v-model="coordinate" />
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">地块负责人</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.principalsPicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块负责人" type="string" readonly v-model="principals" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-principals-picker ref="principalsPicker" :color-confirm="themeColor" text-title="请选择地块负责人" :formatter="principalsPickerFormatter" @confirm="principalsPickerConfirm"></v-principals-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">地块从属</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.belongPicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块从属" type="string" readonly v-model="belong" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="belongPicker" :color-confirm="themeColor" :data="belongPickerData" :formatter="belongPickerFormatter" @confirm="belongPickerConfirm"></v-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">地块权属人</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.ownerPicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块权属人" type="string" readonly v-model="owner" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="ownerPicker" :color-confirm="themeColor" :data="ownerPickerData" :formatter="ownerPickerFormatter" @confirm="ownerPickerConfirm"></v-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">地块保险</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content" @click="showPicker($refs.insurancePicker)">
            <input class="edit-table-cell-input" placeholder="请选择地块保险" type="string" readonly v-model="insurance" />
          </div>
          <div class="edit-table-cell edit-table-cell-icon">
            <v-icon>keyboard_arrow_right</v-icon>
          </div>
        </div>
        <v-picker ref="insurancePicker" :color-confirm="themeColor" :data="insurancePickerData" :formatter="insurancePickerFormatter" @confirm="insurancePickerConfirm"></v-picker>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span readonly">相关附件</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            &nbsp;
          </div>
        </div>
        <div class="edit-table-row edit-table-row-padding2">
          <div class="edit-table-cell edit-table-cell-attachments">
            <a style="margin-right: 10px;color: #02ad58;text-decoration:none;"
                 v-for="(attachment,index) in farmlandDetails.attachments"
                 :key="index" href="javascript:void(0);"
                 :download="attachment.name"
                 @click="download(attachment.url)">
                {{attachment.name?attachment.name:'--'}}
            </a>
          </div>
        </div>
        <div class="edit-table-row-separator">&nbsp;</div>
        <div class="edit-table-row edit-table-row-padding1">
          <div class="edit-table-cell edit-table-cell-label">
            <span class="edit-table-cell-label-required hidden">*</span>
            <span class="edit-table-cell-label-span">备注</span>
          </div>
          <div class="edit-table-cell edit-table-cell-content">
            &nbsp;
          </div>
        </div>
        <div class="edit-table-row edit-table-row-padding2">
          <div class="edit-table-cell edit-table-cell-comment">
            <textarea class="edit-table-cell-textarea" placeholder="请输入备注" rows="5" v-model="comment"></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from './share.js'
import VPicker from '@/components/picker/Picker'
import VAreaPicker from '@/components/picker/AreaPicker'
import VPrincipalsPicker from '@/components/picker/PrincipalsPicker'
export default {
  name: 'farmland-details_edit',
  mixins: [Share],
  components: {
    VPicker,
    VAreaPicker,
    VPrincipalsPicker
  },
  data () {
    return {
      $types: [],
      $principals: [],
      $belongs: [],
      $insurances: [],
      $owners: [],
      $addresses: [],
      typePickerData: [[]],
      typePickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      },
      addressPickerData: [],
      addressPickerFormatter (item, itemIndex, levelIndex) {
        return item.name
      },
      principalsPickerData: [[]],
      principalsPickerFormatter (item, itemIndex, levelIndex) {
        return item.deep ? `${item.name} (${item.items.length})` : item.name
      },
      belongPickerData: [[]],
      belongPickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      },
      ownerPickerData: [[]],
      ownerPickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      },
      insurancePickerData: [[]],
      insurancePickerFormatter (item, itemIndex, wheelIndex) {
        return item.name
      }
    }
  },
  computed: {
    name: {
      get () {
        return this.farmlandDetails.name
      },
      set (value) {
        this.farmlandDetails.name = value
      }
    },
    code: {
      get () {
        return this.farmlandDetails.code
      },
      set (value) {
        this.farmlandDetails.code = value ? value.trim() : undefined
      }
    },
    occupiedArea: {
      get () {
        return this.farmlandDetails.occupiedArea
      },
      set (value) {
        this.farmlandDetails.occupiedArea = value ? Number(value) : undefined
      }
    },
    altitude: {
      get () {
        if (this.farmlandDetails.altitude) {
          return this.farmlandDetails.altitude
        }
      },
      set (value) {
        this.farmlandDetails.altitude = value ? Number(value) : undefined
      }
    },
    longitude: {
      get () {
        return this.farmlandDetails.longitude
      },
      set (value) {
        this.farmlandDetails.longitude = value ? Number(value) : undefined
      }
    },
    latitude: {
      get () {
        return this.farmlandDetails.latitude
      },
      set (value) {
        this.farmlandDetails.latitude = value ? Number(value) : undefined
      }
    },
    coordinate: {
      get () {
        if (typeof this.farmlandDetails.longitude === 'number' && typeof this.farmlandDetails.latitude) {
          return `${this.farmlandDetails.longitude},${this.farmlandDetails.latitude}`
        } else {
          return '--'
        }
      },
      set (longitude, latitude) {
        this.farmlandDetails.longitude = longitude ? Number(longitude) : undefined
        this.farmlandDetails.latitude = latitude ? Number(latitude) : undefined
      }
    },
    type: {
      get () {
        return this.farmlandDetails.typeName
      },
      set ({code, name}) {
        this.farmlandDetails.typeCode = code
        this.farmlandDetails.typeName = name
      }
    },
    address: {
      get () {
        return this.farmlandDetails.addressName
      },
      set (value = []) {
        this.farmlandDetails.provinceCode = value[0] ? value[0].code : undefined
        this.farmlandDetails.provinceName = value[0] ? value[0].name : undefined
        this.farmlandDetails.cityCode = value[1] ? value[1].code : undefined
        this.farmlandDetails.cityName = value[1] ? value[1].name : undefined
        this.farmlandDetails.countyCode = value[2] ? value[2].code : undefined
        this.farmlandDetails.countyName = value[2] ? value[2].name : undefined
        this.farmlandDetails.townCode = value[3] ? value[3].code : undefined
        this.farmlandDetails.townName = value[3] ? value[3].name : undefined
        this.farmlandDetails.villageCode = value[4] ? value[4].code : undefined
        this.farmlandDetails.villageName = value[4] ? value[4].name : undefined
        this.farmlandDetails.addressCode = (value.filter(item => item).shift() || {}).code
        this.farmlandDetails.addressName = value.filter(item => item.name).map(item => item.name.trim()).join('')
      }
    },
    addressDetails: {
      get () {
        return this.farmlandDetails.addressDetails
      },
      set (value) {
        this.farmlandDetails.addressDetails = value
      }
    },
    principals: {
      get () {
        return (this.farmlandDetails.principals || []).map(item => {
          if (item.name) {
            if (item.phone) {
              return `${item.name}(${item.phone})`
            } else {
              return item.name
            }
          }
        }).join(' ')
      },
      set (value) {
        this.farmlandDetails.principals = value
      }
    },
    belong: {
      get () {
        return this.farmlandDetails.belongName
      },
      set ({code, name} = {}) {
        if (this.farmlandDetails.belongCode !== code) {
          this.owner = undefined
          this.farmlandDetails.belongCode = code
          this.farmlandDetails.belongName = name
        }
      }
    },
    owner: {
      get () {
        if (this.farmlandDetails.belongCode !== 'parcel-self') {
          if (this.farmlandDetails.ownerName) {
            if (this.farmlandDetails.ownerPhone) {
              return `${this.farmlandDetails.ownerName}(${this.farmlandDetails.ownerPhone})`
            } else {
              return this.farmlandDetails.ownerName
            }
          }
        }
      },
      set ({id, name, phone} = {}) {
        if (this.farmlandDetails.belongCode === 'parcel-self') {
          this.farmlandDetails.ownerId = null
          this.farmlandDetails.ownerName = null
          this.farmlandDetails.ownerPhone = null
        } else {
          this.farmlandDetails.ownerId = id
          this.farmlandDetails.ownerName = name
          this.farmlandDetails.ownerPhone = phone
        }
      }
    },
    insurance: {
      get () {
        return this.farmlandDetails.insuranceName
      },
      set ({code, name}) {
        this.farmlandDetails.insuranceCode = code
        this.farmlandDetails.insuranceName = name
      }
    },
    comment: {
      get () {
        return this.farmlandDetails.comment
      },
      set (value) {
        this.farmlandDetails.comment = value
      }
    }
  },
  mounted () {
    this.setIcon()
    this.load()
  },
  methods: {
    setIcon () {
      this.$store.commit('set', { name: 'app', k: 'acts', v: [{ text: '确定', fn: this.done }] })
      this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
    },
    showPicker (picker) {
      if (picker === this.$refs.ownerPicker) {
        if (this.farmlandDetails.belongCode === 'parcel-self') {
          return
        }
      }
      if (picker && typeof picker.show === 'function') {
        picker.show()
      }
    },
    check (model) {
      if (!model.name) {
        this.showMessageError('请输入地块名称')
        return
      }
      if (!model.code) {
        this.showMessageError('请输入地块编号')
        return
      }
      if (!model.typeCode) {
        this.showMessageError('请选择地块类型')
        return
      }
      if (!model.addressCode) {
        this.showMessageError('请选择地块位置')
        return
      }
      if (!model.occupiedArea) {
        this.showMessageError('请输入占地面积')
        return
      }
      if (model.occupiedArea < 0 || model.occupiedArea > 1000000) {
        this.showMessageError('请输入有效数字,最大为1000000,小数保留两位')
        return
      }
      if (!Number.isInteger(model.occupiedArea)) {
        model.occupiedArea = Number((Math.round(model.occupiedArea * 100) / 100).toFixed(2))
      }
      if (model.altitude) {
        if (model.altitude < 0 || model.altitude > 10000) {
          // this.showMessageError('请输入有效数字,最大为10000,小数保留两位')
          this.showMessageError('请输入有效数字,0~10000')
          return
        }
      }
      return true
    },
    done () {
      if (!this.check(this.farmlandDetails)) return
      this.updateParcel(this.farmlandDetails).then(result => {
        this.planting = {}
        this.showMessageSuccess('编辑地块信息成功')
        setTimeout(() => {
          this.$store.commit('set', { name: 'fwkParcel', v: null })
          this.$router.back(-1)
        }, 500)
      }).catch(error => {
        this.showMessageError(error.message)
      })
    },
    typePickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.type = item.value
    },
    addressPickerConfirm (items) {
      if (!items) return
      this.address = items
    },
    principalsPickerConfirm (items) {
      console.debug(items)
      if (!items) return
      this.principals = items
    },
    belongPickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.belong = item.value
    },
    ownerPickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.owner = item.value
    },
    insurancePickerConfirm (items) {
      console.debug(items)
      let item = items[0]
      if (!item.value) return
      this.insurance = item.value
    },
    async load () {
      this.$types = await this.queryTypeList()
      if (!this.$types) return
      this.typePickerData = [this.$types.filter(item => !item.isDeleted)]

      this.$addresses = await this.queryAreaListLevel(0, 0)
      if (!this.$addresses) return
      this.addressPickerData = this.$addresses

      this.$belongs = await this.queryBelongList()
      if (!this.$belongs) return
      this.belongPickerData = [this.$belongs.filter(item => !item.isDeleted)]

      this.$owners = await this.queryOwnerList()
      if (!this.$owners) return
      this.ownerPickerData = [this.$owners]

      this.$insurances = await this.queryInsuranceList()
      if (!this.$insurances) return
      this.insurancePickerData = [this.$insurances.filter(item => !item.isDeleted)]

      let result = await this.queryParcel()
      if (!result) return
      this.farmlandDetails = result
    },
    async queryTypeList () {
      return this.queryDict('parcel-type')
    },
    async queryBelongList () {
      return this.queryDict('parcel-dependence')
    },
    async queryOwnerList () {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong

      }
      let response = await this.$http.get('/fwk-service-archives/peasantprotocol/querypeasantlist', {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (Array.isArray(response.data.data)) {
        return this.parseOwnerList(response.data.data)
      }
      return []
    },
    parseOwnerList (data) {
      console.debug(data)
      let result = []
      result.push(...data.map(this.parseOwner))
      console.debug(result)
      return result
    },
    parseOwner (data) {
      return {
        id: data.id,
        name: data.name,
        phone: data.linkphone
      }
    },
    async queryInsuranceList () {
      return this.queryDict('parcel-insurance')
    },
    parseDict (data) {
      console.debug(data)
      let result = []
      result.push(...data.map(this.parseDictItem))
      console.debug(result)
      return result
    },
    parseDictItem (data) {
      return {
        id: data.id,
        type: data.dicttype,
        code: data.dictvalue,
        name: data.dictname,
        sort: Number(data.sort),
        isDeleted: data.delstate === 1 ? 0 : 1,
        comment: data.memo
      }
    },
    async queryDict (type) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong,
        orgid: tenant._id.$oid,
        dicttype: type
      }
      let response = await this.$http.get('/fwk-service-archives/common/dictservice/querydictlist', {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (Array.isArray(response.data.data)) {
        return this.parseDict(response.data.data)
      }
      return []
    },
    async queryAreaList (parent) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong,
        parentCode: parent
      }
      let response = await this.$http.get('/fwk-service-system/position/queryPositionByParentCode', {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (Array.isArray(response.data.data)) {
        return this.parseAreaList(response.data.data)
      }
      return []
    },
    parseAreaList (data) {
      console.debug(data)
      let result = []
      result.push(...data.map(this.parseArea))
      console.debug(result)
      return result
    },
    parseArea (data) {
      return {
        code: data.code,
        name: data.name
      }
    },
    async queryAreaListLevel (parent, level) {
      return ((await this.queryAreaList(parent)) || []).map(item => {
        item.level = level
        item.items = (item) => {
          return this.queryAreaListLevel(item.code, item.level + 1)
        }
        return item
      })
    },
    async updateParcel (parcel) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()
      let params = {
        loginerid: staff._id.$oid,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        operateredittime: staff.updatedOn.$numberLong,
        orgid: tenant._id.$oid,
        parcelid: fwkParcelId
      }
      let data = {
        orgid: parcel.orgId,
        supportid: parcel.supportId,
        id: parcel.id,
        name: parcel.name,
        // code: parcel.code,
        type: parcel.typeCode,
        provincecode: parcel.provinceCode,
        citycode: parcel.cityCode,
        countycode: parcel.countyCode,
        towncode: parcel.townCode,
        villagecode: parcel.villageCode,
        area: parcel.occupiedArea,
        introduce: parcel.comment
      }
      if (parcel.addressDetails) {
        data.addressdetail = parcel.addressDetails
      }
      // 海拔
      if (typeof parcel.altitude === 'number') {
        data.elevation = parcel.altitude
      }
      // 从属
      if (parcel.belongCode) {
        data.dependenceid = parcel.belongCode
      }
      // 权属
      if (parcel.ownerId) {
        data.belongpeasantid = parcel.ownerId
      }
      // 保险
      if (parcel.insuranceCode) {
        data.insurance = parcel.insuranceCode
      }
      // 负责人列表
      if (Array.isArray(parcel.principals)) {
        data.responsibleuserids = parcel.principals.map(item => item.id)
      }
      if (Array.isArray(parcel.attachments)) {
        data.attachments = parcel.attachments.map(item => item.$source)
      }
      let response = await this.$http.post('/fwk-service-parcel/parcel/updateParcel', data, {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(response.data.message)
      }
      if (Array.isArray(response.data.data)) {
        return this.parseVarietyList(response.data.data)
      }
      return []
    }
  }
}
</script>

<style scoped>
  .hidden {
    visibility: hidden;
  }
  .readonly {
    color: #999999 !important;
  }
  .main-container {
    background-color: #ffffff;
  }
  .panel-separator {
    height: 10px;
    background-color: rgb(246, 245, 246);
  }
  .panel {
    margin: 0;
    padding: 0;
  }
  .edit-table {
  }
  .edit-table-row-separator {
    height: 1px;
    background-color: rgb(246, 245, 246);
  }
  .edit-table-row {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
    align-items: stretch;
    align-content: center;
  }
  .edit-table-row-padding1 {
    padding: 10px 10px 10px 5px;
  }
  .edit-table-row-padding2 {
    padding: 0px 10px 10px 10px;
  }
  .edit-table-cell {
    margin: 0;
    padding: 0;
  }
  .edit-table-cell-label {
    flex: none;
    width: 100px;
  }
  .edit-table-cell-label-required {
    color: red;
  }
  .edit-table-cell-label-span {
    text-align: left;
    padding-right: 10px;
    padding-left: 4px;
    font-size: 14px;
    color: #333333;
  }
  .edit-table-cell-content {
    flex: auto;
    font-size: 14px;
    text-align: right;
  }
  .edit-table-cell-attachments {
    margin: 0;
    padding-left: 5px;
  }
  .edit-table-cell-comment {
    flex: auto;
    font-size: 14px;
    text-align: left;
  }
  .edit-table-cell-input {
    width: 100%;
    text-align: right;
    border: 0px;
    border: none;
    outline: none;
  }
  .edit-table-cell-icon,.edit-table-cell-unit {
    width: 24px;
    font-size: 14px;
    text-align: center;
  }
  .edit-table-cell-input::-webkit-input-placeholder {
    font-size: 14px;
    color: #999999;
    text-align: right;
  }
  .edit-table-cell-textarea {
    width: 100%;
    border: 0px;
    border: none;
    outline: none;
    padding-left: 5px;
  }
  .edit-table-cell-textarea::-webkit-input-placeholder {
    font-size: 14px;
    color: #999999;
  }
  .select-dialog {
    position: absolute;
    top: 0;
    left: 0;
    background:rgba(0, 0, 0, .3);
    width: 100%;
    height: 100%;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-content: center;
    align-items: flex-end;
  }
  .select-dialog-content {
    width: 100%;
    background: #ffffff;
  }
</style>
