import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  data () {
    return {
      staffs: null,
      user: null
    }
  },
  methods: {
    async getTasktrendsByTaskid (taskid) {
      let filter = {}
      filter.task = Object.assign({}, { $oid: taskid })
      // filter.type = 'trends'
      let { items } = await mongo
        .db()
        .collection('task.trends')
        .find({ filter: filter })
      return items
    },
    async queryUserInfoByTenants (tenantid) {
      // 此处查所有用户，不考虑组织及删除情况
      let filter = [
        {
          $match: {
            // _id: { $in: userIds },
            $or: [
              { _removed: { $exists: true } },
              { _removed: { $exists: false } }
            ]
          }
        }
      ]
      let items = await mongo
        .db('iss')
        .collection('tenant.staff')
        .aggregate(filter)
      this.staffs = items.map(item => {
        return Object.assign(item, {
          id: item['_id'].$oid
        })
      })
    }
  },
  created () {
    this.user = global.helper.ls.get('user')
    this.queryUserInfoByTenants(this.user.loginorgid)
  }
}
