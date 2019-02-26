<template>
  <v-dialog :value="visible"
            @input="close"
            scrollable
            max-width="900px">
    <v-card v-if="visible">
      <v-toolbar card
                 color="grey lighten-4">
        <v-toolbar-title>{{title}}
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon
               small
               fab
               title="编辑"
               v-if="$authed('PUT','farmerrecords/main/farmerrecords/farmerrecordsEdit')"
               :disabled="userid"
               @click.native.stop="editRecord()">
          <v-icon color="grey darken-1">edit</v-icon>
        </v-btn>
        <v-btn icon
               small
               fab
               title="删除"
               v-if="$authed('DELETE','farmerrecords/main')"
               @click.native.stop="dialogDelete = true"
               :disabled="userid">
          <v-icon color="red darken-2">delete</v-icon>
        </v-btn>
        <v-btn icon
               small
               @click.native="close">
          <v-icon>close</v-icon>
        </v-btn>
      </v-toolbar>
      <v-divider></v-divider>
      <v-card-text class="px-5">
        <!-- 农事内容 -->
        <v-layout row>
          <v-flex xs1>
            农事内容&nbsp;:&nbsp;
          </v-flex>
          <v-flex xs11>
            <span v-text="recordDetail.content || '- -'"></span>
          </v-flex>
        </v-layout>

        <!-- 农事类别 -->
        <v-layout row>
          <v-flex xs1>农事类别&nbsp;:&nbsp;</v-flex>
          <v-flex xs11><span>{{category}}</span></v-flex>
        </v-layout>

        <!-- 实施时间 -->
        <v-layout row>
          <v-flex xs1>实施时间&nbsp;:&nbsp;</v-flex>
          <v-flex xs11><span>{{time}}</span></v-flex>
        </v-layout>

        <!-- 记录来源 -->
        <!-- <v-layout row>
          <v-flex xs1>记录来源&nbsp;:&nbsp;</v-flex>
          <v-flex xs9><span v-text="recordDetail.source || '- -'"></span></v-flex>
        </v-layout> -->

        <!-- 地块 -->
        <v-layout row>
          <v-flex xs1>实施地块&nbsp;:&nbsp;</v-flex>
          <v-flex xs11><span>{{farmland}}</span></v-flex>
        </v-layout>

        <!-- 实施人 ? -->
        <v-layout row>
          <v-flex xs1>实施人&nbsp;:&nbsp;</v-flex>
          <v-flex xs11><span>{{participators}}</span></v-flex>
        </v-layout>

        <!-- 投入品 -->
        <v-layout row>
          <v-flex xs1>投入品&nbsp;:&nbsp;</v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs12>
            <v-data-table :headers="headers"
                          :items="recordDetail.inputs"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <td class="text-xs-center hideoverflow"
                    v-text="props.item.name"
                    :title="props.item.name"
                    style="max-width:100px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"></td>
                <td class="text-xs-center hideoverflow"
                    style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                    v-text="props.item.category"
                    :title="props.item.category"></td>
                <td class="text-xs-center hideoverflow"
                    style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                    v-text="props.item.specification "
                    :title="props.item.specification"></td>
                <td class="text-xs-center hideoverflow"
                    style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
                  <span v-text="`${props.item.quantity || '--'}${props.item.quantityUnit || ''}`"></span>
                </td>
                <td class="text-xs-center hideoverflow"
                    style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                    v-text="props.item.vender || '- -'"
                    :title="props.item.vender"></td>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>
        <!-- 投入品成本 -->
        <v-layout row
                  class="costWrapper">
          <v-flex xs1>投入成本&nbsp;:&nbsp;</v-flex>
          <v-flex xs11><span v-text="recordDetail.inputCost ? recordDetail.inputCost.toFixed(2) : 0"></span>元</v-flex>
        </v-layout>

        <!-- 图片 -->
        <v-layout row>
          <v-flex xs1> <span>图片&nbsp;:&nbsp;</span></v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs12>
            <v-container fluid
                         grid-list-md
                         pa-0>
              <v-layout row
                        wrap>
                <v-flex v-for="(img, index) in recordDetail.images"
                        xs3
                        :key="index">
                  <v-card flat
                          tile
                          class="d-flex">
                    <v-img :src="img.url"
                           aspect-ratio="1"
                           height="100"
                           class="grey lighten-2"
                           @click="openDialog('detail',{img,index,list:[...recordDetail.images.slice(index), ...recordDetail.images.slice(0, index)]})">
                      <v-layout slot="placeholder"
                                fill-height
                                align-center
                                justify-center
                                ma-0>
                        <v-progress-circular indeterminate
                                             color="grey lighten-5"></v-progress-circular>
                      </v-layout>
                    </v-img>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-container>
          </v-flex>
          <photo-carousel :show.sync="dialog.detail"
                          :item="dialog.item"></photo-carousel>
        </v-layout>

        <!-- 附件 -->
        <v-layout row>
          <v-flex xs1> <span>附件&nbsp;:&nbsp;</span></v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs12>
            <template v-for="(list,index) in recordDetail.attachments">
              <v-layout row
                        :key="index">
                <v-flex class="recordAttachment">
                  <v-img :src="getIcon(list.name)"
                         width="28"
                         style="vertical-align: middle; margin-right: 10px;"></v-img>
                  <a :href="list.url"
                     v-text="list.name"
                     class="accessory"
                     target="_blank">
                  </a>
                  <span v-if="!recordDetail.attachments ||recordDetail.attachments.length===0">- -</span>
                </v-flex>
              </v-layout>
            </template>
          </v-flex>
        </v-layout>
        <!-- 语音  ??? -->
        <v-layout row
                  class="audioWrapper">
          <v-flex xs1><span class="autioName">语音&nbsp;:&nbsp;</span></v-flex>
          <v-flex xs11>
            <audio-player :resourceList="recordDetail.audios"></audio-player>
          </v-flex>
        </v-layout>
        <!-- 备注 -->
        <v-layout row>
          <v-flex xs1><span>备注&nbsp;:&nbsp;</span></v-flex>
          <v-flex xs11><span v-text="recordDetail.remark || '- -'"></span></v-flex>
        </v-layout>
      </v-card-text>
      <!-- 确认删除弹窗 -->
      <v-modaldialog :isShow="dialogDelete"
                     title="农事记录删除提示"
                     :modalMaxWidth="400"
                     :bodyHeight="100">
        <span slot
              v-text="message"></span>
        <template slot="btnActions">
          <v-divider></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click="dialogDelete = false">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click.native="submitDelete()">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>
      <v-farmerrecordsedit :visible.sync="editRecordDialog"
                           :title="editRecordDialogTitle"
                           :recordid="id"
                           @callback="refreshData">
      </v-farmerrecordsedit>
    </v-card>
  </v-dialog>
</template>

<script>
import FarmerMixin from '@/pages/farmworks/farmerMixin'
import AudioPlayer from '@/components/AudioPlayer.vue'
import mongo from '@vehiclecloud/mongo-rest-client'
import PhotoCarousel from './PhotoCarousel'
import ItemDialog from '@vehiclecloud/commons/mixins/item-dialog'
import moment from 'moment'
import { getIconPath } from '@/utils'
import VFarmerrecordsedit from './FarmerRecordsEdit.vue'

export default {
  mixins: [FarmerMixin, ItemDialog],
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    title: {
      type: String,
      defulat: ''
    },
    id: {
      type: String,
      defulat: ''
    }
  },
  data () {
    return {
      editRecordDialog: false,
      editRecordDialogTitle: '编辑农事记录',
      message: '农事记录删除后将无法恢复，是否确认删除？',
      dialogDelete: false, // 删除弹出框状态
      recordDetail: {}, // 农事记录详情
      dialog: {
        detail: false
      },
      headers: [
        {
          text: '名称',
          align: 'center',
          sortable: false
        },
        {
          text: '类别',
          align: 'center',
          sortable: false
        },
        {
          text: '规格',
          align: 'center',
          sortable: false
        },
        {
          text: '用量',
          align: 'center',
          sortable: false
        },
        {
          text: '厂家',
          align: 'center',
          sortable: false
        }
      ],
      allUsers: [],
      allFarmlands: []
    }
  },
  watch: {
    visible: function (nid, oid) {
      if (nid === true) {
        this.getRecordDetail(this.id)
      }
    }
  },
  computed: {
    time () {
      return `${this.recordDetail.start || '- -'} 至 ${this.recordDetail.end || '- -'}`
    },
    participators () {
      return this.recordDetail.participators ? this.recordDetail.participators.join(',') : '- -'
    },
    farmland () {
      return this.recordDetail.farmland ? this.recordDetail.farmland.join(',') : '- -'
    },
    category () {
      return this.recordDetail.category ? this.recordDetail.category.join(',') : '- -'
    },
    userid () {
      if (this.recordDetail.creator) {
        if (this.recordDetail.creator.$oid === global.helper.ls.get('user').supportid) {
          return false
        } else {
          return true
        }
      }
    }
  },
  mounted () {
    this.allFarmlands = this.$store.state.allFarmLands
    this.allUsers = this.$store.state.allUsers
  },
  methods: {
    getIcon (resourcename) {
      return getIconPath(resourcename)
    },
    close () {
      this.$emit('update:visible', false)
      this.$emit('callback')
    },

    editRecord () {
      this.editRecordDialog = true
      this.editRecordDialogTitle = '编辑农事记录'
    },
    refreshData () {
      if (!this.id) return
      this.getRecordDetail(this.id)
    },
    /**
     * 根据id获取农事记录详情
     */
    async getRecordDetail (id) {
      let match = {}
      match._removed = { $exists: false }
      match._id = { $oid: id }
      let lookup = {}
      lookup.from = 'record.input'
      lookup.let = { 'recordid': '$_id' }
      let eq = ['$record', '$$recordid']
      let not = [{ $ifNull: ['$_removed', false] }]
      let and = [{ $eq: eq }, { $not: not }]
      let expr = { $expr: { $and: and } }
      let pipelineMatch = { $match: expr }
      lookup.pipeline = [pipelineMatch]
      lookup.as = 'inputs'
      await mongo.db().collection('record').aggregate([{ $match: match }, { $lookup: lookup }]).then((data) => {
        let ndata = data[0]
        this.recordDetail = ndata
        let arr = [] // 农事类别
        let participators = [] // 实施人
        let farmland = [] // 地块
        if (ndata.category) {
          ndata.category.forEach(async element => {
            await mongo.db().collection('farmingCategory').find({ filter: { tenant: data[0].tenant, code: element } }).then((data) => {
              if (data.items.length > 0) {
                arr.push(data.items[0].name)
              } else {
                arr.push('--')
              }
            })
          })
        }
        if (ndata.category) {
          ndata.participators.forEach(async element => {
            await mongo.db('iss').collection('tenant.staff').find({ filter: { _id: element } }).then((data) => {
              if (data.items.length > 0) {
                participators.push(data.items[0].name || data.items[0].mobile)
              } else {
                participators.push(this.queryParticipatorsNameByIds(element) || '--')
              }
            })
          })
        }
        if (ndata.farmland) {
          if (ndata.farmland.length > 1) {
            ndata.farmland.forEach(async element => {
              await mongo.db('iss').collection('tenant.farmland').find({ filter: { tenant: data[0].tenant, _id: element } }).then((data) => {
                if (data.items.length > 0) {
                  farmland.push(data.items[0].name)
                } else {
                  farmland.push(this.queryFarmlandNameById(element.$oid) || '--')
                }
              })
            })
          } else {
            mongo.db('iss').collection('tenant.farmland').find({ filter: { tenant: data[0].tenant, _id: ndata.farmland } }).then((data) => {
              if (data.items.length > 0) {
                farmland.push(data.items[0].name)
              } else {
                farmland.push(this.queryFarmlandNameById(ndata.farmland.$oid) || '--')
              }
            })
          }
        }
        if (ndata.inputs) {
          ndata.inputs.forEach(async element => {
            await mongo.db().collection('inputCategory').find({ filter: { tenant: data[0].tenant, code: element.category } }).then((data) => {
              if (data.items.length > 0) {
                element.category = data.items[0].name
              } else {
                element.category = '--'
              }
            })
            await mongo.db().collection('dict').find({ filter: { code: element.quantityUnit } }).then((data) => {
              if (data.items.length > 0) {
                element.quantityUnit = data.items[0].name
              } else {
                element.quantityUnit = '--'
              }
            })
          })
        }
        this.recordDetail.category = arr
        this.recordDetail.participators = participators
        this.recordDetail.farmland = farmland
        this.recordDetail.start = moment(parseInt(this.recordDetail.startOn.$numberLong)).format('YYYY-MM-DD')
        this.recordDetail.end = moment(parseInt(this.recordDetail.endOn.$numberLong)).format('YYYY-MM-DD')
      })
    },

    /**
     * 对音频文件进行处理
     * @param array array
     * @return new array
     */
    disposeAudio (array) {
      if (array && array.length > 0) {
        let newAudio = []
        for (let audio of array) {
          audio.title = audio.resourcename
          audio.url = audio.resourcepath
          newAudio.push(audio)
        }
        return newAudio
      }
      return []
    },

    /**
       * 确认删除记录
       */
    submitDelete () {
      // let param = { id: id }
      mongo.db().collection('record').remove({ filter: { _id: this.recordDetail._id } }).then(() => {
        this.dialogDelete = false
      })
      this.$emit('update:visible', false)
      this.$emit('callback')
    },
    queryFarmlandNameById (id) {
      let land = this.allFarmlands.find((elem) => {
        return elem._id.$oid === id
      })
      return land ? land.name || '--' : '--'
    },
    queryParticipatorsNameByIds (id) {
      let user = this.allUsers.find(elem => {
        return elem._id.$oid === id.$oid
      })
      return user ? user.name || user.mobile : null
    }
  },
  components: {
    AudioPlayer,
    PhotoCarousel,
    VFarmerrecordsedit
  }
}
</script>
<style scoped>
.recordAttachment {
  line-height: 45px;
  background-color: #f5f5f5;
  padding: 10px 10px;
}
.layout {
  margin-bottom: 20px;
}
.audioWrapper {
  position: relative;
  min-height: 30px;
}
.autioName {
  position: absolute;
  top: 7px;
  z-index: 1;
}
audio {
  margin-right: 20px;
}
.recordAttachment .accessory {
  display: inline-block;
}
.recordAttachment .v-image {
  display: inline-block;
  vertical-align: middle;
}
.btnColor {
  color: #fff;
}
.main {
  padding-bottom: 30px;
}
.costWrapper {
  margin-top: 30px;
}
.containerWrapper {
  padding: 0;
  margin-bottom: 50px;
}
.btnSize {
  min-width: 107px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
