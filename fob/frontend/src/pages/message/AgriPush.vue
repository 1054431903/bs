<template>
  <div>
    <vc-schema-table-with-confirm collection="message"
                                  :schema="schema"
                                  :ui-schema="uiSchema"
                                  :headers="headers"
                                  :add-fn="add"
                                  :select-fn="select"
                                  :filter="filter"
                                  :editable="false"
                                  :revert-fn="revert"
                                  :sort="{ createdOn: -1 }"
                                  ref="addForm">
      <vc-schema-form tag="div"
                      :schema="search.schema"
                      :ui-schema="search.uiSchema"
                      v-model="search.params"></vc-schema-form>
    </vc-schema-table-with-confirm>
    <agri-push-dialog :show.sync="dialog.add"
                      @refresh="reload"></agri-push-dialog>
    <agri-detail-dialog :show.sync="dialog.detail"
                        :item="selectItem"></agri-detail-dialog>
  </div>

</template>
<script>
import searchUtil from '@vehiclecloud/commons/util/search'
import schema from '@/schema/message/push_message'
import ItemDialog from '@vehiclecloud/commons/mixins/item-dialog'
import AgriPushDialog from './AgriPushDialog'
import AgriDetailDialog from './AgriDetailDialog'
import mongo from '@vehiclecloud/mongo-rest-client'

export default {
  name: 'message-agri_push',
  mixins: [ItemDialog],
  created () {
  },
  data: function () {
    return {
      schema,
      uiSchema: {
        title: { 'ui:readOnly': true },
        type: { 'ui:readOnly': true },
        tenant: { 'ui:readOnly': true },
        staff: { 'ui:readOnly': true },
        createdOn: { 'ui:readOnly': true }
      },
      headers: [
        { value: 'param.title', align: 'left' },
        { value: 'type', align: 'left', ui: { 'ui:filter': (ctx, el, item) => item.type === 'original' ? '原创内容' : '第三方引用' } },
        { value: 'tenant', align: 'left' },
        { value: 'staff', align: 'left' },
        { value: 'createdOn', align: 'left', ui: { 'ui:filter': (ctx, el, item) => item.createdOn.$numberLong } }
      ],
      search: {
        schema: {
          type: 'object',
          properties: {
            keyword: { type: 'string', title: '请输入消息标题进行搜索' }
          }
        },
        uiSchema: {
          'ui:class': { layout: true, row: true },
          keyword: { ...searchUtil.flexClass('xs6') }
        },
        params: {
          keyword: null
        }
      },
      dialog: {
        add: false,
        detail: false
      },
      selectItem: {}
    }
  },
  methods: {
    filter () {
      return searchUtil.FilterBuilder.of(this.search.params).regex('keyword').oneOf('keyword', 'param.title').build()
    },
    itemFactory () {
      return { status: 1, accessState: { status: [1], code: [1] }, staff: this.$store.state['tenant.staff'].current }
    },
    add () {
      this.openDialog('add', true)
    },
    select (item) {
      this.selectItem = item
      this.openDialog('detail', true)
    },
    reload () {
      this.$refs.addForm._afterInsert()
    },
    revert (item) {
      console.log(11111111)
      console.log(item)
      mongo.db().collection('message').update({ filter: { _id: item._id, _removed: { $exists: true } }, update: { $unset: { _removed: '' } } }).then(() => {
        this.$refs.addForm._afterInsert()
      })
    }
  },
  components: {
    AgriPushDialog,
    AgriDetailDialog
  }
}
</script>
