<template>
  <vc-schema-tree-list-with-confirm collection="base.expert_tag" :filter="filter" :schema="schema" :ui-schema="uiSchema" :is-leaf="isLeaf" :item-factory="itemFactory" :sort="sort" class="flex xs11 md10">
  </vc-schema-tree-list-with-confirm>
</template>
<script>
import schema from '@/schema/base/expert_tag'

import JsonSchemaVuetify from '@vehiclecloud/json-schema-vuetify'

export default {
  name: 'base-expert_tag',
  data () {
    return {
      schema,
      uiSchema: {
        name: { 'ui:validate': { unique: JsonSchemaVuetify.Validate.unique.params } },
        parent: { 'ui:widget': null },
        level: { 'ui:widget': null }
      },
      sort: { createdOn: 1 }
    }
  },
  methods: {
    filter (keyword) {
      return keyword && { name: { '$regex': keyword } }
    },
    isLeaf (item) {
      return item.level === 2
    },
    itemFactory (params) {
      return { level: 1, ...params }
    }
  }
}
</script>
