<template>
  <!-- 带确认提示的Table组件 -->
  <vc-schema-table-with-confirm database="iss"
                                collection="tenant.staff"
                                :schema="schema"
                                :ui-schema="uiSchema"
                                :headers="headers"
                                :addable="true"
                                :editable="true"
                                :removeable="true"
                                :filter="filter"
                                ref="tableref">
    <!-- 查询条件组件 -->
    <vc-schema-form tag="div"
                    :schema="search.schema"
                    :ui-schema="search.uiSchema"
                    v-model="search.params"></vc-schema-form>
  </vc-schema-table-with-confirm>
</template>

<script>
import addressUtil from '@vehiclecloud/commons/util/address'
import searchUtil from '@vehiclecloud/commons/util/search'
import schema from '@/schema/tenant/staff'

export default {
  name: 'tenant-expert',
  data () {
    return {
      // 数据库表结构定义
      schema,
      // 详情、新增、编辑界面的UI定义
      uiSchema: {
        address: addressUtil.uiSchema(),
        password: { 'ui:show': false },
        fob: {
          expertTags: { 'ui:format': 'tree', 'ui:props': { isLeaf: item => item.level === 2 } }
        }
      },
      // 详情、新增、编辑界面的UI定义
      headers: [
        { value: 'tenants', align: 'left', text: '组织' },
        { value: 'mobile', align: 'left' },
        { value: 'name', align: 'left' },
        { value: 'fob.expertTags', align: 'left' },
        { value: 'fob.categoryTags', align: 'left', text: '品类' }
      ],
      // 查询条件组件的定义
      search: {
        // 查询条件的数据结构定义
        schema: {
          type: 'object',
          properties: {
            'fob.categoryTags': schema.properties.fob.properties.categoryTags,
            'fob.expertTags': schema.properties.fob.properties.expertTags,
            keyword: { type: 'string', title: '姓名/手机号' }
          }
        },
        // 查询条件的UI定义
        uiSchema: {
          'ui:class': { layout: true, row: true },
          'fob.categoryTags': searchUtil.flexClass('xs4'),
          'fob.expertTags': { ...searchUtil.flexClass('xs4'), 'ui:format': 'tree', 'ui:props': { isLeaf: item => item.level === 2 } },
          keyword: searchUtil.flexClass('xs4')
        },
        // 查询条件的返回参数
        params: {
          title: 1,
          keyword: null,
          'fob.categoryTags': [],
          'fob.expertTags': []
        }
      }
    }
  },
  methods: {
    // 生成Mongo查询条件的函数
    filter () {
      return searchUtil.FilterBuilder.of(this.search.params).in('fob.categoryTags').in('fob.expertTags').regex('keyword').oneOf('keyword', 'mobile', 'name').build()
    }
  }
}
</script>
