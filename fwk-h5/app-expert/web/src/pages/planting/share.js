import Share from '@/share/index.js'
import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  mixins: [Share],
  created () {
  },
  methods: {
    async queryCategoryList () {
      let data = await mongo.db('iss').collection('base.category').find({filter: {_removed: {$exists: false}}})
      return this.parseCategoryList(data.items)
    },
    async queryVarietyList (categoryId) {
      let staff = await this.queryStaff()
      if (!staff) return
      let fwkUser = await this.queryFwkUser()
      if (!fwkUser) return
      let params = {
        loginerid: staff._id.$oid,
        operateredittime: staff.updatedOn.$numberLong,
        orgid: fwkUser.loginorgid,
        delstate: 1
      }
      if (categoryId) {
        params.categoryid = categoryId
      }
      let response = await this.$http.get('/fwk-service-system/varieties/queryVarieties', {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (Array.isArray(response.data.data)) {
        return this.parseVarietyList(response.data.data)
      }
      return []
    },
    parseCategoryList (data) {
      console.debug(data)
      let result = []
      result.push(...data.map(this.parseCategory))
      console.debug(result)
      return result
    },
    parseCategory (data) {
      return {
        id: data._id.$oid,
        name: data.name,
        level: data.level
      }
    },
    parseVarietyList (data) {
      console.debug(data)
      let result = []
      result.push(...data.map(this.parseVariety))
      console.debug(result)
      return result
    },
    parseVariety (data) {
      return {
        id: data.id,
        name: data.name,
        categoryId: data.categoryid,
        categoryName: data.categoryname,
        isDeleted: data.delstate === 1 ? 0 : 1
      }
    }
  }
}
