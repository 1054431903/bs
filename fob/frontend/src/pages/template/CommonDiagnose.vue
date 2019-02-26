<template>
  <vc-schema-table-with-confirm collection="template.common_diagnose"
                                :filter="filter"
                                :schema="schema"
                                :ui-schema="uiSchema"
                                :headers="headers"
                                :item-factory="itemFactory"
                                :sort="{ updatedOn: -1 }">
    <vc-schema-form tag="div"
                    :schema="search.schema"
                    :ui-schema="search.uiSchema"
                    v-model="search.params"></vc-schema-form>
  </vc-schema-table-with-confirm>
</template>
<script>
import searchUtil from '@vehiclecloud/commons/util/search'
import yzUtil from '../yz_util'

import schema from '@/schema/template/common_diagnose'

export default {
  name: 'template-common_diagnose',
  // 预先查询有赞商城商品列表
  created () {
    yzUtil.getItems().then(items => {
      this.items = items
      console.log(this.items)
    })
  },
  data: function () {
    return {
      schema,
      uiSchema: {
        diagnose: { 'ui:format': 'textarea' },
        formula: { 'ui:format': 'textarea' },
        staff: { 'ui:readOnly': true },
        recommends: { 'ui:format': 'tree', 'ui:props': () => ({ multiple: true, isLeaf: item => item.parent, items: this.items }) },
        updatedOn: { 'ui:readOnly': true }
      },
      items: [],
      headers: [
        { value: 'name', align: 'left' },
        { value: 'type', align: 'left', text: '品类' },
        { value: 'staff', align: 'left' },
        { value: 'updatedOn', align: 'left' }
      ],
      search: {
        schema: {
          type: 'object',
          properties: {
            type: schema.properties.type,
            keyword: { type: 'string', title: '关键字' },
            staff: schema.properties.staff
          }
        },
        uiSchema: {
          'ui:class': { layout: true, row: true },
          type: { ...searchUtil.refsSelectUiSchema('xs3'), 'ui:props': { autocomplete: true } },
          keyword: { ...searchUtil.flexClass('xs4') },
          staff: { ...searchUtil.refsSelectUiSchema('xs4') }
        },
        params: {
          type: null,
          keyword: null,
          staff: null
        }
      }
    }
  },
  methods: {
    filter () {
      return searchUtil.FilterBuilder.of(this.search.params).regex('keyword').oneOf('keyword', 'name', 'diagnose', 'formula').build()
    },
    itemFactory () {
      return { staff: this.$store.state['tenant.staff'].current._id }
    }
  }
}
</script>
