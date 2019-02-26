<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow.sync="editRecordDialog"
                   :title="title || editRecordDialogTitle"
                   :modalMaxWidth="700"
                   :bodyHeight="600">
      <v-form v-model="recordFormValid"
              ref="recordForm"
              lazy-validation
              autocomplete="off">
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-autocomplete label="农事类别"
                            v-model="curCategory"
                            :items="farmingCategorys"
                            item-text="name"
                            item-value="code"
                            no-data-text="暂无数据"
                            multiple
                            clearable
                            :rules="rules.farmingCategory">
            </v-autocomplete>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-textarea label="农事内容"
                        v-model="content"
                        :rules="rules.content"
                        counter="500"
                        maxlength="500">
            </v-textarea>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap
                  py-1>
          <v-flex xs12>
            <span :class="{isInvalid: !isStaffValid}">实施人：</span>
            <span :class="{isValid: isStaffValid, isInvalid: !isStaffValid}">实施人不能为空</span>
          </v-flex>
          <v-flex xs12
                  py-2>
            <v-layout>
              <template v-for="(participator, index) in selectedStaffList">
                <v-flex xs1
                        mr-2
                        mb-2
                        style="cursor: pointer;width: 130px;"
                        :key="participator.id">
                  <div :title="participator.name"
                       @mouseenter="getSelectedStaff(index)"
                       @mouseleave="curStaffId = null">
                    <v-avatar size="50">
                      <img :src="participator.avatar || `/static/task/fuzenren.png`"
                           @click="showSelectStaffDialog" />
                      <div style="position: absolute;right: 0;top: -5px;"
                           @click="removeStaff(index)"
                           v-if="curStaffId === participator.id">
                        <v-avatar class="grey"
                                  size="15px">
                          <img style="cursor: pointer;"
                               :src="`/static/record/close.png`" />
                        </v-avatar>
                      </div>
                    </v-avatar>
                  </div>
                </v-flex>
              </template>
              <v-flex xs1
                      mb-2>
                <v-avatar size="50">
                  <img style="cursor: pointer;"
                       src="static/task/zengjia.png"
                       @click="showSelectStaffDialog" />
                </v-avatar>
              </v-flex>
            </v-layout>
          </v-flex>
          <v-flex xs12>
            <v-divider></v-divider>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-autocomplete v-model="selectedLands"
                            :items="tenantFarmLands"
                            label="实施地块"
                            item-text="name"
                            item-value="id"
                            no-data-text="暂无数据"
                            chips
                            disabled
                            multiple>
              <template slot="selection"
                        slot-scope="data">
                <v-chip :selected="data.selected"
                        class="chip--select-multi"
                        @input="removeLand(data.item)">
                  {{ data.item.name }}
                </v-chip>
              </template>
              <v-list-tile slot="prepend-item"
                           ripple
                           @click="toggle">
                <v-list-tile-action>
                  <v-icon :color="selectedLands.length > 0 ? 'primary' : ''">{{ icon }}</v-icon>
                </v-list-tile-action>
                <v-list-tile-title>
                  全部地块
                </v-list-tile-title>
              </v-list-tile>
              <v-divider slot="prepend-item"
                         class="mt-2">
              </v-divider>
              <v-divider slot="append-item"
                         class="mb-2">
              </v-divider>
            </v-autocomplete>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="开始时间"
                          v-model="startOnStr"
                          readonly
                          :rules="rules.startdatetime"
                          ref="startDateTime"
                          @click="selectedStartDateTime"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="结束时间"
                          v-model="endOnStr"
                          readonly
                          :rules="rules.enddatetime"
                          ref="endDateTime"
                          @click="selectedEndDateTime"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs3
                  py-3>
            <span>投入品：</span>
            <span class="isValid">(可添加多项)</span>
          </v-flex>
          <v-flex sx9
                  text-xs-right>
            <v-btn @click="addInput"
                   small
                   fab
                   dark
                   color="red darken-2"
                   class="btnAdd"
                   title="添加投入品">
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap
                  py-2>
          <v-flex xs-12>
            <v-data-table :headers="tableHeader"
                          :items="inputItems"
                          no-data-text="暂无数据"
                          no-results-text="未查询到符合条件的记录"
                          hide-actions
                          disable-initial-sort>
              <template slot="items"
                        slot-scope="props">
                <tr style="cursor:pointer;">
                  <td class="text-xs-left hideoverflow"
                      style="max-width: 100px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                      :title="props.item.name">
                    {{ props.item.name }}
                  </td>
                  <td class="text-xs-left hideoverflow"
                      style=""
                      :title="props.item.categoryName">
                    {{ props.item.categoryName }}
                  </td>
                  <td class="text-xs-left hideoverflow"
                      :title="props.item.specification">
                    {{ props.item.specification }}
                  </td>
                  <td class="text-xs-left hideoverflow"
                      :title="props.item.quantityStr">
                    {{ props.item.quantityStr }}
                  </td>
                  <td class="text-xs-left hideoverflow"
                      :title="props.item.vender">
                    {{ props.item.vender }}
                  </td>
                  <td class="text-xs-center operateMinWidth">
                    <v-btn fab
                           small
                           icon
                           title="编辑"
                           @click.stop="editInput(props.item)">
                      <v-icon>create</v-icon>
                    </v-btn>
                    <v-btn fab
                           small
                           icon
                           title="删除"
                           @click.stop="deleteInput(props.item)">
                      <v-icon color="red">delete</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs4>
            <v-text-field label="投入成本"
                          v-model="inputCost"
                          :rules="rules.inputCost">
            </v-text-field>
          </v-flex>
          <v-flex xs8
                  px-2
                  py-4>
            <span>元</span>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <span>图片：</span>
          </v-flex>
          <v-flex xs12
                  py-2>
            <v-layout row
                      wrap>
              <template v-for="(imgObj, index) in imageList">
                <v-flex xs2
                        mb-2
                        :key="index">
                  <v-img class="text-xs-right mr-1"
                         :src="imgObj.url"
                         :title="imgObj.name"
                         height="100"
                         width="100"
                         @mouseenter="getSelectedImage(imgObj)"
                         @mouseleave="curSelectedImage = null">
                    <div v-if="curSelectedImage === imgObj">
                      <v-avatar size="20px">
                        <img style="cursor: pointer;"
                             :src="`/static/record/delete.png`"
                             @click="removeImage(index)" />
                      </v-avatar>
                    </div>
                  </v-img>
                </v-flex>
              </template>
              <v-flex xs2
                      mb-2
                      v-if="imageList.length < 9">
                <div class="defaultImg"
                     @click="addImage">
                  <img :src="`/static/record/add.png`" />
                </div>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-dropzoneupload :files.sync="attachments">
            </v-dropzoneupload>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap
                  py-3>
          <v-flex xs1>
            <span>语音：</span>
          </v-flex>
          <v-flex xs11>
            <audio-player :resourceList.sync="audios"
                          :isEdit="true"></audio-player>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-textarea label="备注"
                        v-model="remark"
                        counter="500"
                        maxlength="500">
            </v-textarea>
          </v-flex>
        </v-layout>
      </v-form>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary"
                 flat
                 @click.native="cancel">
            取消
          </v-btn>
          <v-btn color="primary"
                 flat
                 @click.native="save">
            完成
          </v-btn>
        </v-card-actions>
      </template>
      <!-- 确认删除弹窗 -->
      <v-modaldialog :isShow.sync="prompt.dialog"
                     :title="prompt.title"
                     :showCloseBtn="true"
                     :modalMaxWidth="400"
                     :bodyHeight="100">
        <div>{{ prompt.body }}</div>
        <template slot="btnActions">
          <v-divider></v-divider>
          <v-card-actions class="text-xs-right">
            <v-spacer>
              <v-btn flat
                     color="primary"
                     @click.native.stop="Esc">取消</v-btn>
              <v-btn flat
                     color="primary"
                     @click.native.stop="Ok">确定</v-btn>
            </v-spacer>
          </v-card-actions>
        </template>
      </v-modaldialog>
    </v-modaldialog>
    <v-selectedstaff :visible.sync="selectStaffDialog"
                     title="选择参与人"
                     :issearch="true"
                     :staffs.sync="selectedStaffIdList"
                     searchlabel="请输入参与人姓名进行搜索"
                     :stafflist="tenantUsers"
                     :multiselected="true"
                     :showKeystaffBtn="false">
    </v-selectedstaff>
    <v-datetimepicker :visible.sync="startdatetimepicker"
                      :datetime.sync="startdatetime"
                      :isend="false"
                      :showtime="false"
                      :bodyheight="410">
    </v-datetimepicker>
    <v-datetimepicker :visible.sync="enddatetimepicker"
                      :datetime.sync="enddatetime"
                      :isend="true"
                      :showtime="false"
                      :bodyheight="410">
    </v-datetimepicker>
    <v-imagecroppedupload :showme.sync="showImageCroppaDialog"
                          :img.sync="imageUrl"
                          :imgname.sync="imageName">
    </v-imagecroppedupload>
    <v-inputaddoredit :visible.sync="inputAddOrEditDialog"
                      :inputitem.sync="addInputItem"
                      :editinput.sync="editInputItem"
                      :editedinput.sync="editedInputItem">
    </v-inputaddoredit>
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import VDatetimepicker from '../worktask/DateTimePickers.vue'
import VDropzoneupload from '@/components/DropzoneUpload.vue'
import VInputaddoredit from './InputAddOrEdit.vue'
import AudioPlayer from '@/components/AudioPlayer.vue'
import moment from 'moment'
import { Toast } from '@/models/toast'
export default {
  name: 'v-farmerrecordsedit',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    source: {
      type: String,
      default: 'source-record'
    },
    recordid: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      editRecordDialog: false,
      editRecordDialogTitle: '编辑农事记录',
      recordFormValid: true,
      // 农事类别列表
      farmingCategorys: [],
      // 投入品类别列表
      inputCategorys: [],
      // 投入品用量单位列表
      quantityUnits: [],
      // 当前农事类别
      curCategory: null,
      // 农事内容
      content: null,
      // 实施人必选标识
      isStaffValid: true,
      // 是否已经点击表单完成(保存)按钮
      isSaveClick: false,
      // 投入成本
      inputCost: null,
      // 备注
      remark: null,
      rules: {
        farmingCategory: [
          (v) => (v && v.length > 0) || '必填项'
        ],
        content: [
          (v) => !!v && !!(v.trim()) || '必填项'
        ],
        inputCost: [
          (v) => {
            let reg = /^\d+(\.\d+)?$/
            if (v) {
              return (reg.test(v) && v < 99999999999.99) || '输入错误,请输入正确的数字'
            } else {
              return true
            }
          }
        ],
        startdatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetimeToStart()) || '计划开始时间不能大于任务结束时间'
        ],
        enddatetime: [
          (v) => !!v || '必填项',
          (v) => (!!v && this.invalidDatetimeToEnd()) || '计划结束时间不能小于任务开始时间'
        ]
      },
      // 选择参与人弹窗
      selectStaffDialog: false,
      // 已选择的参与人ID列表
      selectedStaffIdList: [],
      // 已选择的参与人对象列表
      selectedStaffList: [],
      // 当前选中的人员ID
      curStaffId: null,
      // 当前组织下的所有用户
      tenantUsers: [],
      // 当前组织下的地块
      tenantFarmLands: [],
      // 已选地块
      selectedLands: [],
      // 开始时间字符串
      startOnStr: null,
      // 结束时间字符串
      endOnStr: null,
      // 开始时间弹出框
      startdatetimepicker: false,
      // 结束时间弹出框
      enddatetimepicker: false,
      // 时间控件返回的计划开始时间(字符串: 2018-11-10 00:00)
      startdatetime: null,
      // 时间控件返回的计划结束时间(字符串: 2018-11-10 23:59)
      enddatetime: null,
      tableHeader: [
        {
          text: '名称',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        {
          text: '类别',
          align: 'left',
          sortable: false,
          value: 'categoryName'
        },
        {
          text: '规格',
          align: 'left',
          sortable: false,
          value: 'specification'
        },
        {
          text: '用量',
          align: 'left',
          sortable: false,
          value: 'quantityStr'
        },
        {
          text: '厂家',
          align: 'left',
          sortable: false,
          value: 'vender'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      // 投入品记录列表
      inputItems: [],
      // 当前添加的投入品对象
      addInputItem: {},
      // 当前待编辑的投入品对象
      editInputItem: {},
      // 当前编辑完成后的投入品对象
      editedInputItem: {},
      // 当前待删除的投入品对象
      deleteInputItem: {},
      // 需要删除的投入品ID列表
      deleteInputIds: [],
      // 图片裁剪窗口
      showImageCroppaDialog: false,
      // 图片上传完成后返回的Url
      imageUrl: null,
      // 图片上传完成后返回的文件名
      imageName: null,
      // 已上传的图片对象集合（[{name:xxx,url:xxx}]）
      imageList: [],
      // 当前选择的图片
      curSelectedImage: null,
      // 附件列表
      attachments: [],
      // 语音列表
      audios: [],
      // 添加或编辑投入口窗口
      inputAddOrEditDialog: 'IS_NONE',
      prompt: {
        dialog: false,
        title: '投入品删除提示',
        body: '投入品删除后将无法恢复，是否确认删除？'
      }
    }
  },
  components: {
    VDatetimepicker,
    VDropzoneupload,
    VInputaddoredit,
    AudioPlayer
  },
  created () {
    this.toast = new Toast(this, null, null)
    if (!this.inputItems.length) this.inputItems.push({ id: 1 })
  },
  computed: {
    user () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    },
    tenant () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    },
    likesAllLand () {
      return this.selectedLands.length === this.tenantFarmLands.length
    },
    likesSomeLand () {
      return this.selectedLands.length > 0 && !this.likesAllLand
    },
    icon () {
      if (this.likesAllLand) return 'check_box'
      if (this.likesSomeLand) return 'indeterminate_check_box'
      return 'check_box_outline_blank'
    }
  },
  watch: {
    visible (v) {
      v ? this.queryRecord() : null
      this.editRecordDialog = v
    },
    editRecordDialog (v) {
      this.$emit('update:visible', v)
    },
    addInputItem (v) {
      v ? this.showInput(v) : null
    },
    editedInputItem (v) {
      v ? this.showInput(v) : null
    },
    selectedStaffIdList: function (v) {
      let tmp = []
      // 提取实施人信息
      for (let i = 0; i < v.length; i++) {
        tmp.push(this.getStaffInfoById(v[i]))
      }
      this.selectedStaffList = tmp
      this.isStaffValid = (this.selectedStaffIdList.length > 0 || !this.isSaveClick)
    },
    startdatetime (v) {
      v ? this.startOnStr = moment(v, 'YYYY-MM-DD HH:mm').format('YYYY-MM-DD') : null
    },
    enddatetime (v) {
      v ? this.endOnStr = moment(v, 'YYYY-MM-DD HH:mm').format('YYYY-MM-DD') : null
    },
    imageUrl (v) {
      if (v) {
        let image = {}
        image.name = this.imageName ? this.imageName : v.split('.')[1]
        image.url = v
        this.imageList.push(image)
      }
    }
  },
  mounted () {
    this.getFarmingCategorys()
    this.getInputCategorys()
    this.getQuantityUnits()
    this.removeInitInputData()
  },
  methods: {
    save () {
      this.isSaveClick = true
      this.isStaffValid = this.selectedStaffIdList.length > 0
      if (this.$refs['recordForm'].validate() && this.isStaffValid) {
        this.saveRecord(this.selectedLands)
      }
    },
    cancel () {
      this.editRecordDialog = false
    },
    async saveRecord (lands) {
      try {
        this.toast.setContent('正在保存数据...')
        this.toast.show()
        let times = lands && lands.length > 0 ? lands.length : 1
        for (let i = 0; i < times; i++) {
          let filter = {}
          filter._id = { $oid: this.recordid }
          let setColumn = {}
          let unsetColumn = {}
          if (this.curCategory && this.curCategory.length) {
            setColumn.category = this.curCategory
          } else {
            unsetColumn.category = ''
          }
          if (this.content && this.content.trim()) {
            setColumn.content = this.content.trim()
          } else {
            unsetColumn.content = ''
          }
          if (this.selectedStaffIdList.length) {
            setColumn.participators = this.selectedStaffIdList.map(elem => { return { $oid: elem } })
          } else {
            unsetColumn.participators = ''
          }
          if (this.source && this.source.trim()) {
            setColumn.source = this.source.trim()
          } else {
            unsetColumn.source = ''
          }
          if (this.startOnStr) {
            setColumn.startOn = { $numberLong: moment(this.startOnStr, 'YYYY-MM-DD').valueOf().toString() }
          } else {
            unsetColumn.startOn = ''
          }
          if (this.endOnStr) {
            setColumn.endOn = { $numberLong: moment(this.endOnStr, 'YYYY-MM-DD').hour(23).minute(59).second(59).valueOf().toString() }
          } else {
            unsetColumn.endOn = ''
          }
          if (lands && lands.length) {
            setColumn.farmland = { $oid: lands[i]._id ? lands[i]._id.$oid : lands[i] }
          } else {
            unsetColumn.farmland = ''
          }
          if (this.inputCost) {
            setColumn.inputCost = Number(Number(this.inputCost).toFixed(2)) + 0.001
          } else {
            unsetColumn.inputCost = ''
          }
          if (this.remark && this.remark.trim()) {
            setColumn.remark = this.remark.trim()
          } else {
            unsetColumn.remark = ''
          }
          if (this.imageList.length) {
            setColumn.images = this.imageList
          } else {
            unsetColumn.images = ''
          }
          if (this.audios && this.audios.length) {
            setColumn.audios = this.audios
          } else {
            unsetColumn.audios = ''
          }
          if (this.attachments.length) {
            let tmpAttachments = this.attachments.map(elem => {
              return { name: elem.resourcename, url: elem.resourcepath }
            })
            setColumn.attachments = tmpAttachments
          } else {
            unsetColumn.attachments = ''
          }
          let update = Object.keys(unsetColumn).length ? { $set: setColumn, $unset: unsetColumn } : { $set: setColumn }
          await mongo.db().collection('record').update({ filter: filter, update: update })
          // 插入新增的投入品
          let addItems = this.inputItems.filter(elem => {
            return !elem._id
          })
          if (addItems.length) {
            let tmpItems = []
            for (let item of addItems) {
              let input = {}
              input.record = { $oid: this.recordid }
              input.category = item.category
              input.name = item.name
              input.quantity = item.quantity
              input.quantityUnit = item.quantityUnit
              input.creator = item.creator
              input.specification = item.specification
              input.vender = item.vender
              tmpItems.push(input)
            }
            await mongo.db().collection('record.input').insert(tmpItems)
          }
          // 修改编辑过的投入品
          let editItems = this.inputItems.filter(elem => {
            return elem._id
          })
          if (editItems.length) {
            for (let item of editItems) {
              let inputFilter = { _id: item._id }
              let inputSetColumn = {}
              let inputUnsetColumn = {}
              if (item.category && item.category.length) {
                inputSetColumn.category = item.category
              } else {
                inputUnsetColumn.category = ''
              }
              if (item.name && item.name.trim()) {
                inputSetColumn.name = item.name.trim()
              } else {
                inputUnsetColumn.name = ''
              }
              if (item.quantity) {
                inputSetColumn.quantity = item.quantity
              } else {
                inputUnsetColumn.quantity = ''
              }
              if (item.quantityUnit && item.quantityUnit.trim()) {
                inputSetColumn.quantityUnit = item.quantityUnit.trim()
              } else {
                inputUnsetColumn.quantityUnit = ''
              }
              if (item.specification && item.specification.trim()) {
                inputSetColumn.specification = item.specification.trim()
              } else {
                inputUnsetColumn.specification = ''
              }
              if (item.vender && item.vender.trim()) {
                inputSetColumn.vender = item.vender.trim()
              } else {
                inputUnsetColumn.vender = ''
              }
              if (item.creator) {
                inputSetColumn.creator = item.creator
              } else {
                inputUnsetColumn.creator = ''
              }
              let inputUpdate = Object.keys(inputUnsetColumn).length ? { $set: inputSetColumn, $unset: inputUnsetColumn } : { $set: inputSetColumn }
              await mongo.db().collection('record.input').update({ filter: inputFilter, update: inputUpdate })
            }
          }
          // 从数据库中删除已经标记为删除的投入品
          if (this.deleteInputIds.length) {
            await mongo.db().collection('record.input').remove({ filter: { _id: { $in: this.deleteInputIds } }, multi: true })
          }
        }
      } catch (err) {
        console.log(err)
        this.toast.close()
        this.toast.setContent('保存数据出错，请联系系统管理员！')
        this.toast.show()
      } finally {
        this.toast.close()
        this.editRecordDialog = false
        // 更新父页面记录数据
        this.$emit('callback')
      }
    },
    Ok () {
      let index = this.inputItems.findIndex(elem => {
        return elem.id === this.deleteInputItem.id
      })
      if (index > -1) this.inputItems.splice(index, 1)
      this.prompt.dialog = false
      this.deleteInputItem._id ? this.deleteInputIds.push(this.deleteInputItem._id) : null
      this.deleteInputItem = {}
    },
    Esc () {
      this.prompt.dialog = false
      this.deleteInputItem = {}
    },
    removeInitInputData () {
      if (this.inputItems.length) {
        let index = this.inputItems.findIndex(elem => {
          return elem.id === 1
        })
        if (index > -1) this.inputItems.splice(index, 1)
      }
    },
    async queryRecord () {
      if (!this.tenantUsers.length) {
        this.cacheTenantUsers()
      }
      if (!this.tenantFarmLands.length) {
        this.cacheTenantFarmLands()
      }
      if (!this.recordid) return
      let match = {}
      match._removed = { $exists: false }
      match._id = { $oid: this.recordid }
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
      let records = await mongo.db().collection('record').aggregate([{ $match: match }, { $lookup: lookup }])
      if (records.length) {
        let record = records[0]
        this.initVariables(record)
      }
    },
    initVariables (record) {
      this.$nextTick(() => {
        this.curCategory = record.category
        this.content = record.content
        this.selectedLands = []
        this.selectedStaffIdList = []
        if (record.participators && record.participators.length) {
          let users = this.tenantUsers.filter(elem => {
            return record.participators.find(oid => {
              return oid.$oid === elem._id.$oid
            })
          })
          this.selectedStaffList = users
          this.selectedStaffIdList = users.map(elem => { return elem._id.$oid })
        }
        this.selectedLands = record.farmland ? [record.farmland.$oid] : []
        this.startOnStr = record.startOn ? moment(Number(record.startOn.$numberLong)).format('YYYY-MM-DD') : null
        this.endOnStr = record.endOn ? moment(Number(record.endOn.$numberLong)).format('YYYY-MM-DD') : null
        this.inputItems = record.inputs.length ? this.loadInput(record.inputs) : []
        this.inputCost = record.inputCost ? Number(record.inputCost).toFixed(2) : null
        this.imageUrl = null
        this.imageName = null
        this.imageList = (record.images && record.images.length) ? record.images : []
        if (record.attachments && record.attachments.length) {
          this.attachments = record.attachments.map(elem => {
            return { resourcename: elem.name, resourcepath: elem.url }
          })
        }
        this.audios = (record.audios && record.audios.length) ? record.audios : []
        this.remark = record.remark
        this.isStaffValid = true
        this.isSaveClick = false
      })
    },
    loadInput (inputs) {
      let tmpInputs = []
      for (let input of inputs) {
        input.id = new Date().getTime()
        input.categoryName = this.getInputCategoryName(input.category)
        let unitName = this.getInputQuantityUnitName(input.quantityUnit)
        input.quantityStr = `${input.quantity}${unitName}`
        tmpInputs.push(input)
      }
      return tmpInputs
    },
    getInputCategoryName (code) {
      if (!code) return '--'
      let inputCategory = this.inputCategorys.find(elem => {
        return elem.code === code
      })
      return (inputCategory && inputCategory.name) ? inputCategory.name : '--'
    },
    getInputQuantityUnitName (code) {
      if (!code) return '--'
      let unit = this.quantityUnits.find(elem => {
        return elem.code === code
      })
      return (unit && unit.name) ? unit.name : '--'
    },
    showInput (item) {
      // 先查询当前列表中是否存在该记录，如果存在，则先删除后再插入
      let index = this.inputItems.findIndex(elem => {
        return elem.id === item.id
      })
      if (index > -1) this.inputItems.splice(index, 1)
      this.inputItems.push(item)
    },
    getFarmingCategorys () {
      let match = {}
      match._removed = { $exists: false }
      match.tenant = { $oid: this.tenant }
      let sort = { sort: 1 }
      mongo.db().collection('farmingCategory').aggregate([{ $match: match }, { $sort: sort }]).then(res => {
        this.farmingCategorys = res
      })
    },
    getInputCategorys () {
      let match = {}
      match._removed = { $exists: false }
      match.tenant = { $oid: this.tenant }
      let sort = { sort: 1 }
      mongo.db().collection('inputCategory').aggregate([{ $match: match }, { $sort: sort }]).then(res => {
        this.inputCategorys = res
      })
    },
    getQuantityUnits () {
      let match = {}
      match._removed = { $exists: false }
      match.dict = 'input-quantity-unit'
      let sort = { sort: 1 }
      mongo.db().collection('dict').aggregate([{ $match: match }, { $sort: sort }]).then(res => {
        this.quantityUnits = res
      })
    },
    addInput () {
      this.inputAddOrEditDialog = 'IS_ADD'
    },
    editInput (item) {
      if (!item || !item.id) return
      this.editInputItem = item
      this.inputAddOrEditDialog = 'IS_EDIT'
    },
    deleteInput (item) {
      if (!item || !item.id) return
      this.deleteInputItem = item
      this.prompt.dialog = true
    },
    getSelectedStaff (index) {
      this.curStaffId = this.selectedStaffList[index].id
    },
    removeStaff (index) {
      this.selectedStaffIdList.splice(index, 1)
    },
    showSelectStaffDialog () {
      this.selectStaffDialog = true
    },
    getStaffInfoById (staffId) {
      if (!staffId) return
      let staff = this._.find(this.tenantUsers, (staff) => {
        return staff.id === staffId
      })
      return staff ? Object.assign({}, { id: staff.id, name: staff.name, avatar: staff.avatar }) : null
    },
    removeLand (item) {
      if (this.selectedLands && this.selectedLands.length > 0) {
        // 如果是点过全选，则已选择的列表中存的是对象，反之存的是对象ID
        let idx = this.selectedLands.findIndex(elem => {
          if (elem._id) {
            return elem._id.$oid === item._id.$oid
          } else {
            return elem === item._id.$oid
          }
        })
        if (idx > -1) this.selectedLands.splice(idx, 1)
      }
    },
    toggle () {
      this.$nextTick(() => {
        if (this.likesAllLand) {
          this.selectedLands = []
        } else {
          this.selectedLands = this.tenantFarmLands.slice()
        }
      })
    },
    selectedStartDateTime () {
      this.startdatetime = this.formatDate(this.startOn)
      this.startdatetimepicker = true
    },
    selectedEndDateTime () {
      this.enddatetime = this.formatDate(this.endOn)
      this.enddatetimepicker = true
    },
    formatDate (object) {
      if (!object || !object.$numberLong) return null
      return moment(Number(object.$numberLong)).format('YYYY-MM-DD')
    },
    invalidDatetimeToStart: function () {
      let start = new Date(this.startOnStr || '1970-1-1')
      let end = new Date(this.endOnStr || '2118-1-1')
      let bool = start.getTime() <= end.getTime()
      return bool
    },
    invalidDatetimeToEnd: function () {
      let start = new Date(this.startOnStr || '1970-1-1')
      let end = new Date(this.endOnStr || '2118-1-1')
      let bool = start.getTime() <= end.getTime()
      return bool
    },
    addImage () {
      this.showImageCroppaDialog = true
    },
    getSelectedImage (item) {
      this.curSelectedImage = this.imageList.find(elem => {
        return elem.url === item.url
      })
    },
    removeImage (index) {
      this.imageList.splice(index, 1)
    },
    cacheTenantUsers () {
      if (!this.tenantUsers.length) {
        this.$store.state.tenantUsers.map(elem => {
          let user = Object.assign({}, elem)
          Object.assign(user, { id: user._id.$oid }, { name: user.name || user.mobile })
          this.tenantUsers.push(user)
        })
      }
    },
    cacheTenantFarmLands () {
      if (!this.tenantFarmLands.length) {
        this.$store.state.tenantFarmLands.map(elem => {
          let land = Object.assign({}, elem)
          Object.assign(land, { id: land._id.$oid })
          this.tenantFarmLands.push(land)
        })
      }
    }
  }
}
</script>
<style scoped>
.isValid {
  color: rgba(0, 0, 0, 0.47);
}
.isInvalid {
  color: #b71c1c !important;
}
.defaultImg {
  background-color: #f2f2f2;
  height: 100px;
  width: 100px;
  text-align: center;
  padding-top: 36px;
  cursor: pointer;
}
.hideoverflow {
  max-width: 100px;
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.operateMinWidth {
  min-width: 150px;
}
</style>
