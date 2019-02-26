<template>
  <v-dialog :value="show"
            @input="close"
            scrollable
            max-width="900px">
    <v-card v-if="show">
      <v-toolbar card
                 color="grey lighten-4">
        <v-toolbar-title>消息详情
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon
               small
               @click.native="close">
          <v-icon>close</v-icon>
        </v-btn>
      </v-toolbar>
      <v-divider></v-divider>
      <v-card-text class="px-5">
        <v-list subheader>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">消息标题</v-flex>
            <v-list-tile-content>
              <v-list-tile-title :title="itemDetail.param.title"
                                 v-html="itemDetail.param.title"></v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              消息副标题
            </v-flex>
            <v-list-tile-content>
              <v-list-tile-title :title="itemDetail.param.subTitle">{{itemDetail.param.subTitle}}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              消息接收组织
            </v-flex>
            <v-list-tile-content>
              <v-list-tile-title>{{itemDetail.tenant}}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              消息内容类型
            </v-flex>
            <v-list-tile-content>
              <v-list-tile-title>{{itemDetail.type}}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              消息发布时间
            </v-flex>
            <v-list-tile-content>
              <v-list-tile-title>{{itemDetail.createdOn.$numberLong}}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              消息发布人
            </v-flex>
            <v-list-tile-content>
              <v-list-tile-title>{{itemDetail.staff || '--'}}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-layout v-if="isAgri">
            <v-subheader xs2
                         pl-3
                         class="grey--text text--darken-1 text--detail-label">
              消息内容
            </v-subheader>
          </v-layout>
          <v-layout v-if="isAgri">
            <v-flex xs12>
              <div class="ql-container ql-snow">
                <div class="ql-editor"
                     v-html="itemDetail.param.content">
                </div>
              </div>
            </v-flex>
          </v-layout>
          <v-list-tile v-else>
            <v-flex xs2
                    class="grey--text text--darken-1 text--detail-label">
              引用超链接地址
            </v-flex>
            <v-flex xs10>
              <a :href="itemDetail.param.url"
                 target="_blank">{{itemDetail.param.url}}</a>
            </v-flex>
            <!-- <v-list-tile-content>
              <v-list-tile-title>{{itemDetail.param.url}}</v-list-tile-title>
            </v-list-tile-content> -->
          </v-list-tile>
        </v-list>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import moment from 'moment'
/**
 * 深拷贝数据
 */
function deepClone (obj) {
  let _obj = JSON.stringify(obj)
  let objClone = JSON.parse(_obj)
  return objClone
}
export default {
  name: 'agri-detail-dialog',
  props: {
    show: {
      type: Boolean,
      required: true
    },
    item: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      isAgri: true,
      itemDetail: {}
    }
  },
  computed: {
  },
  watch: {
    item: function (newQuestion, oldQuestion) {
      this.itemDetail = deepClone(this.item)

      mongo.db('iss').collection('tenant.staff').find({ filter: { '_id': { $oid: this.itemDetail.staff.$oid } } }).then((data) => {
        console.log(data)
        if (data.items.length > 0) {
          this.itemDetail.staff = data.items[0].name
        } else {
          this.itemDetail.staff = '--'
        }
      })
      mongo.db('iss').collection('sys.tenant').find({ filter: { '_id': { $oid: this.itemDetail.tenant.$oid } } }).then((data) => {
        if (data.items.length > 0) {
          this.itemDetail.tenant = data.items[0].name
        } else {
          this.itemDetail.tenant = '--'
        }
      })

      if (this.itemDetail.type === 'original') {
        this.isAgri = true
        this.itemDetail.type = '原创内容'
        this.itemDetail.param.content = this.escapeStringHTML(this.itemDetail.param.content)
      } else {
        this.isAgri = false
        this.itemDetail.type = '第三方引用'
      }
      this.itemDetail.createdOn.$numberLong = moment(parseInt(this.itemDetail.createdOn.$numberLong)).format('YYYY-MM-DD HH:mm:ss').slice(0, 16)
      console.log(this.itemDetail)
    }
  },
  methods: {
    close () {
      this.$emit('update:show', false)
    },
    submit () {
      this.$emit('update:show', false)
    },
    escapeStringHTML (str) {
      str = str.replace(/&lt;/g, '<')
      str = str.replace(/&gt;/g, '>')
      return str
    }
  }
}
</script>

<style lang="scss">
.ql-container .ql-snow {
  border: none;
}
</style>
