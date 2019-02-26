<template>
  <vc-schema-table-with-confirm style="margin-left:30px;margin-right:20px;"
                                collection="tenant.staff_roles"
                                :filter="filter"
                                :schema="schema"
                                :ui-schema="uiSchema"
                                :headers="headers"
                                :item-factory="itemFactory"
                                :findFn="findFn">
    <vc-schema-form tag="div"
                    :schema="search.schema"
                    :ui-schema="search.uiSchema"
                    v-model="params"></vc-schema-form>
  </vc-schema-table-with-confirm>
</template>
 
<script>

import mongo from '@vehiclecloud/mongo-rest-client'
import schema from '@/schema/tenant/staff_roles'
import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'

import StaffRoles from './mixins/staff-roles'
import ItemDialog from '@vehiclecloud/commons/mixins/item-dialog'

import tenantUtil from '@vehiclecloud/commons/util/tenant'
// import searchUtil from '@vehiclecloud/commons/util/search'
import VSelectedland from './LandSelected.vue'
import { Toast } from '@/models/toast'

function filterStaff (item, ctx) {
  if (!item.tenants) return false
  return item.tenants.some(t => {
    let a = (JSON.stringify(ctx.vm.$store.state['sys.tenant'].allSubTenants).indexOf(JSON.stringify(t)) >= 0)
    return a
  })
}
function filterRole (item, ctx) {
  // 过滤组织所拥有的角色
  if (!item.tenant) return false
  let a = (JSON.stringify(ctx.vm.$store.state['sys.tenant'].allSubTenants).indexOf(JSON.stringify(item.tenant)) >= 0)
  return a
}

// function staffUniqueParams (ctx, el) {
//   let orgid = global.helper.ls.get('user').loginorgid
//   return JsonSchemaVuetify.Validate.paramsOf(ctx, el, 'unique', tableRef => {
//     return value => {
//       let r = { staff: { $oid: value }, tenant: { $oid: orgid } }

//       if (ctx.scope === 3) {
//         r['_id'] = { $ne: tableRef.dialog.form.origin._id }
//       }
//       return r
//     }
//   })
// }

function canEdit (item) {
  let rs = { disabled: false }
  if (item.value && item.value._id) {
    rs.disabled = true
  }
  return rs
}

export default {
  name: 'common-tenant-staff_roles',
  mixins: [StaffRoles, ItemDialog],
  created () {
    this.$store.dispatch('init', { collection: 'sys.tenant' })
    let orgid = global.helper.ls.get('user').loginorgid
    tenantUtil.children(this.tenantDb, orgid).then(tenants => {
      // this.allSubTenants = this.allSubTenants.concat(tenants.map(t => t._id))
      this.$store.commit('set', { name: 'sys.tenant', k: 'allSubTenants', v: this.allSubTenants.concat(tenants.map(t => t._id)) })
    })
    this.queryLandInfoByTenants()
    this.toast = new Toast(this, null, null)
  },
  data () {
    let uiSchema = {
      loginOn: { 'ui:readOnly': true, 'ui:writeOnly': true },
      staff: [{ 'ui:validate': { unique: JsonSchemaVuetify.Validate.unique.paramsOf(({ item }) => ({ tenant: item.tenant })) }, 'ui:title': '账号', 'ui:options': { textName: 'mobile', filter: filterStaff }, 'ui:attrs': canEdit }, { 'ui:title': '姓名', 'ui:options': { textName: 'name' }, 'ui:attrs': { disabled: true } }, { 'ui:title': '昵称', 'ui:options': { textName: 'nickname' }, 'ui:attrs': { disabled: true } }],
      roles: [{ 'ui:validate': { unique: JsonSchemaVuetify.Validate.unique.params }, 'ui:title': '角色', 'ui:options': { textName: 'name', filter: filterRole }, 'ui:attrs': canEdit }],
      tenant: { 'ui:readOnly': true, 'ui:attrs': { disabled: true } }
    }

    if (this.showFarmland) {
      uiSchema.farmlands = {
        'ui:format': 'tree',
        'ui:props': { isLeaf: (item) => { return !!item.parent } },
        'ui:validate': { required: true },
        'ui:options': {
          map: items => {
            let fIds = this.$store.state['tenant.farmland'].allItems.filter(f => !!f.tenant).map(f => f.tenant.$oid)
            let tenants = this.$store.state['sys.tenant'].items
              .filter(t =>
                t.type === 1 && fIds.some(fid => fid === t._id.$oid)
              ).map(t => ({
                _id: t._id, text: t.name, value: t._id.$oid
              }))
            return [].concat(
              tenants,
              this.$store.state['tenant.farmland'].allItems.filter(item => !!item.tenant).map(item => ({ _id: item._id, text: item.name, value: item._id.$oid, parent: item.tenant }))
            )
          }
        }
      }
    } else {
      uiSchema.farmlands = {
        'ui:show': () => false
      }
    }

    return {
      allSubTenants: [{ $oid: global.helper.ls.get('user').loginorgid }], // 取得所有子组织和部门的_id
      schema,
      uiSchema: uiSchema,
      headers: [
        { text: '账号', value: 'staff', width: '15%', align: 'left', sortable: false, ui: { 'ui:options': { textName: 'mobile' } } },
        { text: '姓名', value: 'staff', width: '13%', align: 'left', sortable: false, ui: { 'ui:options': { textName: 'name' } } },
        { text: '昵称', value: 'staff', width: '13%', align: 'left', sortable: false, ui: { 'ui:options': { textName: 'nickname' } } },
        { text: '角色', value: 'roles', width: '30%', align: 'left', sortable: false, ui: { 'ui:options': { textName: 'name' } } }
      ],
      search: {
        schema: {
          type: 'object',
          properties: {
            role: {
              type: 'string',
              $id: '#tenant.role',
              title: '角色'
            },
            keyword: {
              type: 'string',
              title: '账号/姓名'
            }
          }
        },
        uiSchema: {
          'ui:class': { layout: true, row: true },
          role: { 'ui:class': { flex: true, xs5: true, 'pr-5': true }, 'ui:options': { textName: 'name', filter: filterRole }, 'ui:props': { clearable: true } },
          keyword: { 'ui:class': { flex: true, xs6: true } }
        }
      },
      params: {
        tenant: global.helper.ls.get('user').loginorgid,
        role: null,
        keyword: null
      }
    }
  },

  methods: {
    async queryLandInfoByTenants () {
      let match = { _id: { $oid: this.params.tenant } }
      let graphLookup = { from: 'sys.tenant', startWith: '$_id', connectFromField: '_id', connectToField: 'parent', as: 'children' }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [{ $match: match }, { $graphLookup: graphLookup }, { $unwind: unwind }, { $project: project }]
      // 查询当前组织下所有部门
      let allDep = await mongo.db('iss').collection('sys.tenant').aggregate(aggregate)
      let allTenantId = allDep.map((dep) => {
        return dep._id
      })
      allTenantId.push({ $oid: this.params.tenant })
      // 查询当前组织及其下所有部门下的地块
      let filter = {}
      filter.tenant = { $in: allTenantId }
      filter.$or = [{ _removed: { $exists: false } }, { _removed: { $exists: true } }]
      let projection = { code: 1, name: 1, _removed: 1, tenant: 1 }
      let items = await mongo.db('iss').collection('tenant.farmland').aggregate([{ $match: filter }, { $project: projection }])
      items.map(item => {
        let name = item['name'] + '（' + item['code'] + '）'
        Object.assign(item, { name: name })
      })
      this.landlist = items
      this.types = items
    },
    async filter () {
      let result = {}
      let abc = []
      if (this.params.keyword) {
        let allTenantId = await this.queryTenantIdAndChildByTenantId(this.params.tenant)
        await mongo.db('iss').collection('tenant.staff').find({ filter: { tenants: { $in: allTenantId }, $or: [{ name: { '$regex': this.params.keyword } }, { mobile: { '$regex': this.params.keyword } }] } })
          .then(d => {
            if (d.items && d.items.length > 0) {
              let staffs = d.items
              staffs.forEach(item => {
                abc.push(item._id.$oid)
              })
            }
          })
        result.staff = { $in: abc.map(i => ({ $oid: i })) }
      }
      if (this.params.role) {
        result.roles = this.params.role
      }
      result.tenant = { $oid: this.params.tenant }
      console.log(result)
      return result
    },
    itemFactory () {
      return { tenant: { $oid: this.params.tenant } }
    },
    async queryTenantIdAndChildByTenantId (tenantId) {
      if (!tenantId) return []
      let match = { _id: { $oid: this.params.tenant } }
      let graphLookup = { from: 'sys.tenant', startWith: '$_id', connectFromField: '_id', connectToField: 'parent', as: 'children' }
      let unwind = '$children'
      let project = { _id: '$children._id' }
      let aggregate = [{ $match: match }, { $graphLookup: graphLookup }, { $unwind: unwind }, { $project: project }]
      // 查询当前组织下所有部门
      let allDep = await mongo.db('iss').collection('sys.tenant').aggregate(aggregate)
      let allTenantId = allDep.map((dep) => {
        return dep._id
      })
      allTenantId.push({ $oid: this.params.tenant })
      return allTenantId
    }
  },
  computed: {
  },
  components: {
    VSelectedland
  },
  watch: {
  }
}
</script>
