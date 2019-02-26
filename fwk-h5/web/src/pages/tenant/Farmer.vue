<template>
  <vc-schema-table collection="tenant.farmer" :schema="schema" :headers="headers">
    <vc-schema-form tag="div" :schema="search.schema" :ui-schema="search.uiSchema" v-model="params"></vc-schema-form>
  </vc-schema-table>
</template>

<script>
import searchUtil from '@vehiclecloud/commons/util/search'

import schema from '@/schema/tenant/farmer'

export default {
  name: 'tenant-farmer',
  data () {
    return {
      schema,
      headers: [
        { value: 'name', aligh: 'left' },
        { value: 'status', aligh: 'left' },
        { value: 'remark', aligh: 'left' }
      ],
      search: {
        schema: {
          type: 'object',
          properties: {
            name: schema.properties.name
          }
        },
        uiSchema: {
          'ui:class': { layout: true, row: true },
          name: searchUtil.flexClass('xs12')
        }
      },
      params: {
        name: null
      }
    }
  }
}
</script>

