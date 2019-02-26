import Axios from 'axios'
import Conf from '@/conf'
import Moment from 'moment'

export default {
  data () {
    return {
      staff: null,
      tenant: null,
      farmland: null,
      fwkUser: null,
      fwkParcel: null,
      issDatabase: 'iss',
      fwkDatabase: 'fwk',
      fwkServer: (Conf.FWK || {}).fwkServer || '',
      timeout: 10 * 1000
    }
  },
  computed: {
    themeColor () {
      return this.runMode === 1 ? '#02ad58' : 'rgb(77, 188, 209)'
    },
    runMode () {
      return this.$store.state.init.runMode
    }
  },
  created () {
    this.$http = Axios.create({
      baseURL: this.fwkServer,
      timeout: this.timeout
    })
  },
  methods: {
    download (url) {
      if (window.native && window.native.openFile) {
        window.native.openFile(url)
      } else {
        window.open(url, '_blank')
      }
    },
    isControllable (device) {
      switch (device.typeCode) {
        // 动力视讯智能球机
        case 401: {
          return true
        }
        // 海康威视高清球机
        case 402: {
          return true
        }
        // 海康威视高清枪机标准版
        case 405: {
          return false
        }
        default : {
          return false
        }
      }
    },
    async showMessageInfo (...messages) {
      if (window.native && window.native.showToast) {
        window.native.showToast(messages.join(','))
      } else {
        this.$toasted.clear()
        this.$toasted.info(messages.join(','), {
          position: 'bottom-center',
          duration: 1000,
          fullWidth: true,
          fitToScreen: true
        })
      }
    },
    async showMessageSuccess (...messages) {
      if (window.native && window.native.showToast) {
        window.native.showToast(messages.join(','))
      } else {
        this.$toasted.clear()
        this.$toasted.success(messages.join(','), {
          position: 'bottom-center',
          duration: 1000,
          fullWidth: true,
          fitToScreen: true
        })
      }
    },
    async showMessageError (...messages) {
      if (window.native && window.native.showToast) {
        window.native.showToast(messages.join(','))
      } else {
        this.$toasted.clear()
        this.$toasted.error(messages.join(','), {
          position: 'bottom-center',
          duration: 1000,
          fullWidth: true,
          fitToScreen: true
        })
      }
    },
    queryIssStaffId () {
      return this.$store.state.init.issStaffId
    },
    queryIssTenantId () {
      return this.$store.state.init.issTenantId
    },
    queryIssFarmlandId () {
      return this.$store.state.init.issFarmlandId
    },
    queryFwkUserId () {
      return this.$store.state.init.fwkUserId
    },
    queryFwkParcelId () {
      return this.$store.state.init.fwkParcelId
    },
    async queryStaff () {
      if (!this.staff) {
        this.staff = this.$store.state['tenant.staff'].item
        // console.debug(this.staff)
      }
      return this.staff
    },
    async queryTenant () {
      if (!this.tenant) {
        this.tenant = this.$store.state['sys.tenant'].item
        // console.debug(this.tenant)
      }
      return this.tenant
    },
    async queryFarmland () {
      if (!this.farmland) {
        this.farmland = this.$store.state['tenant.farmland'].item
        // console.debug(this.farmland)
      }
      return this.farmland
    },
    async queryFwkUser ({ reload } = {}) {
      if (reload) {
        this.fwkUser = null
        this.$store.commit('set', { name: 'fwkUser', v: null })
      }
      if (!this.fwkUser) {
        this.fwkUser = this.$store.state.fwkUser
        if (!this.fwkUser) {
          let staff = await this.queryStaff()
          if (!staff) return
          let params = {
            islogin: 0,
            account: staff.mobile,
            supportmm: staff.password,
            password: staff.password
          }
          let response = await this.$http.post('fwk-service-user/user/login', params)
          if (!(response.data && response.data.flag === 1)) {
            throw new Error(JSON.stringify(response.data))
          }
          let fwkUser = response.data.data
          console.debug(fwkUser)
          this.fwkUser = fwkUser
          this.$store.commit('set', { name: 'fwkUser', v: fwkUser })
        }
      }
      return this.fwkUser
    },
    async queryFwkParcel ({ reload } = {}) {
      if (reload) {
        this.fwkParcel = null
        this.$store.commit('set', { name: 'fwkParcel', v: null })
      }
      if (!this.fwkParcel) {
        this.fwkParcel = this.$store.state.fwkParcel
        if (!this.fwkParcel) {
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
            id: fwkParcelId
          }
          let response = await this.$http.get('/fwk-service-parcel/parcel/queryParcel', {params: params})
          if (!(response.data && response.data.flag === 1)) {
            throw new Error(JSON.stringify(response.data))
          }
          if (Array.isArray(response.data.data)) {
            let fwkParcel = response.data.data[0]
            console.debug(fwkParcel)
            this.fwkParcel = fwkParcel
            this.$store.commit('set', { name: 'fwkParcel', v: fwkParcel })
          }
        }
      }
      return this.fwkParcel
    },
    async queryParcel () {
      let result = await this.queryFwkParcel()
      if (result) {
        return this.parseParcel(result)
      }
    },
    parseParcel (data) {
      console.debug(data)
      let farmland = {}
      farmland.id = data.id
      farmland.orgId = data.orgid
      farmland.supportId = data.supportid
      farmland.name = data.name
      farmland.code = data.parcelno
      farmland.typeCode = data.type
      farmland.typeName = data.typename
      farmland.provinceCode = data.provincecode
      farmland.cityCode = data.citycode
      farmland.countyCode = data.countycode
      farmland.townCode = data.towncode
      farmland.villageCode = data.villagecode
      farmland.addressCode = data.addresscode
      farmland.addressName = data.addressfullname
      farmland.addressDetails = data.addressdetail
      farmland.occupiedArea = data.area
      farmland.occupiedAreaUnit = '亩'
      farmland.occupiedAreaString = farmland.occupiedArea ? `${farmland.occupiedArea}${farmland.occupiedAreaUnit}` : '--'
      farmland.altitude = data.elevation
      farmland.altitudeUnit = '米'
      farmland.altitudeString = farmland.altitude ? `${farmland.altitude}${farmland.altitudeUnit}` : '--'
      farmland.longitude = data.longitude
      farmland.latitude = data.latitude
      farmland.coordinateString = (farmland.longitude && farmland.latitude) ? `${farmland.longitude},${farmland.latitude}` : '--'
      farmland.belongCode = data.dependenceid
      farmland.belongName = data.dependencename
      farmland.insuranceCode = data.insurance
      farmland.insuranceName = data.insurancename
      farmland.comment = data.introduce
      if (farmland.belongCode !== 'parcel-self' && data.belongpeasant) {
        farmland.ownerId = data.belongpeasant.id || null
        farmland.ownerName = data.belongpeasant.name || null
        farmland.ownerPhone = data.belongpeasant.linkphone || null
      } else {
        farmland.ownerId = null
        farmland.ownerName = null
        farmland.ownerPhone = null
      }
      if (data.responsibleuser) {
        farmland.principals = data.responsibleuser.map(item => {
          return {
            id: item.userid,
            name: item.name,
            phone: item.phone
          }
        })
      }
      if (data.attachments) {
        farmland.attachments = data.attachments.map(this.parseAttachment).filter(item => {
          const pattern = /^https?:\/\/.*\/.*$/i
          return pattern.test(item.url)
        })
      }
      console.debug(farmland)
      return farmland
    },
    parseAttachment (item) {
      let attachment = {}
      attachment.id = item.resourceid
      attachment.name = item.resourcename
      attachment.url = item.resourcepath
      if (!(typeof attachment.name === 'string' && attachment.name.length > 0)) {
        attachment.name = '未命名'
      }
      attachment.$source = item
      return attachment
    },
    async queryParcelPlantListPaging (pageIndex, pageSize) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        delstate: 1,
        parcelid: fwkParcelId,
        currentPage: pageIndex,
        pageSize: pageSize
      }
      let response = await this.$http.get('fwk-service-parcel/parcelPlant/queryParcelPlant', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      return this.parseParcelPlantListPaging(response.data)
    },
    parseParcelPlantListPaging (data) {
      console.debug(data)
      let result = {
        paging: {
          total: data.page.total,
          pageSize: data.page.pageSize,
          pageTotal: data.page.totalPage,
          pageIndex: data.page.currentPage
        },
        data: []
      }
      if (data.data && (data.data instanceof Array)) {
        result.data = data.data.map(this.parseParcelPlantDetails)
      }
      console.debug(result)
      return result
    },
    parseParcelPlantDetails (data) {
      let result = {}
      result.id = data.id
      result.fwkParcelId = data.parcelid
      result.categoryId = data.categoryid
      result.categoryName = data.categoryname || ''
      result.varietyId = data.varieties
      result.varietyName = data.varietiesname || ''
      result.planGrowthcycle = data.preestimategrowthcycle
      result.planGrowthcycleUnit = data.preestimategrowthcycleunit
      result.planGrowthcycleString = result.planGrowthcycle ? `${result.planGrowthcycle}${result.planGrowthcycleUnit}` : ''
      result.plantArea = data.area
      result.plantAreaUnit = '亩'
      result.plantAreaString = result.plantArea ? `${result.plantArea}${result.plantAreaUnit}` : ''
      result.plantCount = data.number
      result.plantCountUnit = data.unit
      result.plantCountString = result.plantCount ? `${result.plantCount}${result.plantCountUnit}` : ''
      result.plantBeginTime = new Date(data.plantbegintime.replace(' ', 'T') + '.000+08:00')
      result.plantBeginTimeString = Moment(result.plantBeginTime).format('YYYY-MM-DD')
      result.plantEndTime = new Date(data.plantbegintime.replace(' ', 'T') + '.000+08:00')
      result.plantEndTimeString = Moment(result.plantBeginTime).format('YYYY-MM-DD')
      result.plantAge = data.plantage
      result.plantAgeUnit = data.plantageunit
      result.plantAgeString = result.plantAge ? `${result.plantAge}${result.plantAgeUnit}` : ''
      result.plantBatch = data.growingbatch
      result.plantBatchString = result.plantBatch ? result.plantBatch : ''
      result.comment = data.introduce
      if (data.attachments) {
        result.attachments = data.attachments.map(item => {
          let attachment = {}
          attachment.id = item.resourceid
          attachment.name = item.resourcename
          attachment.url = item.resourcepath
          if (!(typeof attachment.name === 'string' && attachment.name.length > 0)) {
            attachment.name = '未命名'
          }
          attachment.$source = item
          return attachment
        }).filter(item => {
          const pattern = /^https?:\/\/.*\/.*$/i
          return pattern.test(item.url)
        })
      }
      return result
    },
    async queryFwkFarmWorkListPaging (searchText, pageIndex, pageSize) {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        loginorgid: tenant._id.$oid,
        operateid: fwkUserId,
        delstate: 1,
        parcelid: fwkParcelId,
        orderBySQL: 'creattime',
        ordertype: 'desc',
        currentPage: pageIndex,
        pageSize: pageSize
      }
      if (searchText && searchText.length > 0) {
        params.keyword = searchText
      }
      let response = await this.$http.get('fwk-service-farm/record/querylist', {params: params})
      if (!(response.data && Number(response.data.flag) === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      return response.data
    }
  }
}
