import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  methods: {
    async queryTaskInfoById (taskid) {
      let filter = {}
      filter._id = Object.assign({}, { $oid: taskid })
      let { items } = await mongo
        .db()
        .collection('task')
        .find({ filter: filter })
      return items
    },
    async querUserInfoByIds (userids) {
      let filter = {}
      filter._id = { $in: userids }
      filter.$or = [
        { _removed: { $exists: false } },
        { _removed: { $exists: true } }
      ]
      let projection = { code: 1, name: 1 }
      let items = await mongo
        .db('iss')
        .collection('tenant.staff')
        .aggregate([{ $match: filter }, { $project: projection }])
      return items
      // let filter = {}
      // filter._id = Object.assign({}, { $in: userids })
      // let { items } = await mongo
      //   .db('iss')
      //   .collection('tenant.staff')
      //   .find({ filter: filter, projection: { name: 1, mobile: 1, avatar: 1 } })
      // return items
    },
    async querUserInfoById (userid) {
      let filter = {}
      filter._id = { $oid: userid }
      filter.$or = [
        { _removed: { $exists: false } },
        { _removed: { $exists: true } }
      ]
      let projection = { code: 1, name: 1 }
      let items = await mongo
        .db('iss')
        .collection('tenant.staff')
        .aggregate([{ $match: filter }, { $project: projection }])
      return items
      // let filter = {}
      // filter._id = Object.assign({}, { $oid: userid })
      // let { items } = await mongo
      //   .db('iss')
      //   .collection('tenant.staff')
      //   .find({ filter: filter, projection: { name: 1, mobile: 1, avatar: 1 } })
      // return items
    },
    async queryLandInfoByIds (landids) {
      let filter = {}
      filter._id = { $in: landids }
      filter.$or = [
        { _removed: { $exists: false } },
        { _removed: { $exists: true } }
      ]
      let projection = { code: 1, name: 1 }
      let items = await mongo
        .db('iss')
        .collection('tenant.farmland')
        .aggregate([{ $match: filter }, { $project: projection }])
      return items
      // let filter = {}
      // filter._id = Object.assign({}, { $in: landids })
      // let { items } = await mongo
      //   .db('iss')
      //   .collection('tenant.farmland')
      //   .find({ filter: filter, projection: { code: 1, name: 1 } })
      // return items
    }
  }
}
