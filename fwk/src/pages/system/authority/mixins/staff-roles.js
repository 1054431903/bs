import mongo from '@vehiclecloud/mongo-rest-client'

const defaultFindFn = async function (collection, params) {
  if (params.filter && params.filter.$or) {
    let { items, total } = await mongo.db('iss').collection('tenant.staff').find({ filter: { $or: params.filter.$or }, $skip: params.skip, $limit: params.limit })

    if (+total.$numberLong === 0) {
      return { items: [], total: { $numberLong: '0' } }
    }

    let staffIds = items.map(s => s._id)

    let filter = {}
    filter.staff = { $in: staffIds }

    if (params.role) {
      filter.roles = this.params.role
    }

    return collection.find({ filter: filter, $skip: params.skip, $limit: params.limit })
  } else {
    return collection.find(params)
  }
}

export default {
  props: {
    tenantDb: {
      type: String,
      default: 'iss'
    },
    showFarmland: Boolean,
    findFn: {
      type: Function,
      default: defaultFindFn
    }
  }
}
