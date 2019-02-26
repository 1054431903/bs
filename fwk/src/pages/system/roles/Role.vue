<template>
  <vc-schema-table style="margin-left:30px;margin-right:20px;"
                   collection="tenant.role"
                   :filter="filter"
                   :schema="schema"
                   :ui-schema="uiSchema"
                   :headers="headers"
                   :item-factory="itemFactory">
    <v-text-field label="名称"
                  clearable
                  single-line
                  v-model="params.keyword"></v-text-field>
  </vc-schema-table>
</template>
<script>

import schema from '@agrithings/common/schema/tenant/role'

import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'

export default {
  name: 'tenant-role-1',
  data () {
    return {
      schema,
      uiSchema: {
        code: { 'ui:readOnly': true, 'ui:writeOnly': true },
        name: { 'ui:validate': { unique: JsonSchemaVuetify.Validate.unique.paramsOf(({ item }) => ({ tenant: item.tenant })) } },
        tenant: { 'ui:readOnly': true, 'ui:writeOnly': true },
        authorities: { 'ui:format': 'tree', 'ui:props': { selectable: true, autoSelectParent: true, isLeaf: item => item.level === 3, isCandidate: () => true }, 'ui:options': { textName: 'title' } },
        status: { 'ui:readOnly': true },
        remark: { 'ui:format': 'textarea' }
      },
      headers: [
        { value: 'name', width: '15%', align: 'left' },
        { value: 'authorities', align: 'left', width: '55%', sortable: false, ui: { 'ui:options': { textName: 'title' } } }
      ],
      params: {
        tenant: global.helper.ls.get('user').loginorgid,
        keyword: null
      }
    }
  },
  methods: {
    filter () {
      let result = {}
      if (this.params.keyword) {
        result.name = { '$regex': this.params.keyword.trim() }
      }
      result.tenant = { $oid: this.params.tenant }
      return result
    },
    itemFactory () {
      return { status: 1, tenant: { $oid: this.params.tenant } }
    }
  }
}
</script>
