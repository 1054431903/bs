<!--
    ??? 投入品表格字数过多时显示问题, 浏览器缩放显示问题 袋解决

-->

<template @scroll.prevent="true">
  <v-container fluid
               class="containerWrapper">
    <v-card class="page-content">
      <!-- 返回按钮 -->
      <go-back name="返回" />
      <v-form ref="form">
        <!-- * 农事内容 -->
        <v-layout row
                  justify-start>
          <v-flex xs2>
            <v-subheader>*&nbsp;农事内容</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-text-field ref="content"
                          label="请输入农事内容(100字以内)"
                          v-model="content"
                          single-line
                          counter="100"
                          maxlength="100"
                          :rules="[() => !!content || '请填写农事内容']">
            </v-text-field>
          </v-flex>
        </v-layout>

        <!-- * 农事类别 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;农事类别</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-select ref="farmtype"
                      label="请选择农事类别"
                      v-model="farmtype"
                      :items="farmtypeArr"
                      item-text="name"
                      item-value="id"
                      single-line
                      multiple
                      :rules="[() => farmtype.length>0 || '请选择农事类别']"
                      tabindex="0">
            </v-select>
          </v-flex>
        </v-layout>

        <!-- * 开始时间 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;开始时间</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-customdatepicker pickerLabel="请选择开始时间"
                                :selectedDate.sync="begintime"
                                :rules="[() => !!begintime || '请选择开始时间']"
                                :allowedDates="begintimeLimit" />
          </v-flex>
        </v-layout>

        <!-- 结束时间 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;结束时间</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-customdatepicker pickerLabel="请选择结束时间"
                                :selectedDate.sync="endtime"
                                :rules="[() => !!endtime || '请选择结束时间']"
                                :allowedDates="endtimeLimit" />
          </v-flex>
        </v-layout>

        <!-- 地块 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>地块</v-subheader>
          </v-flex>
          <v-flex xs5>
            <!-- <v-select
                label="全部地块(可多选)"
                v-model="recordparcellist"
                :items="recordparcellistArr"
                item-text="name"
                item-value="id"
                :disabled="isEdit"
                multiple
                single-line
              >
              </v-select> -->
            <AllSelect :itemsArr="recordparcellistArr"
                       :selects="recordparcellist"
                       :typeName="typeName"
                       ref="allSelect"
                       v-if="flag"
                       :readonly="$route.query.parcelid ? true : false"></AllSelect>
          </v-flex>
        </v-layout>

        <!-- * 实施人  -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;实施人</v-subheader>
          </v-flex>
          <v-flex xs5>
            <tree-part ref="treePart"
                       :isValDate="true"
                       :label="labelName"
                       @clickNode="clickNode"
                       :editUser="editUser"
                       v-if="isEditUser" />
          </v-flex>
        </v-layout>

        <!-- 投入品 -->
        <v-layout row
                  class="putKind">
          <v-flex xs2><span class="labelColor">投入品</span></v-flex>
          <v-flex xs10>
            <div class="wrapper">
              <span>(&nbsp;可添加多项&nbsp;)&nbsp;&nbsp;</span>
              <v-btn fab
                     small
                     color="primary"
                     @click.stop="clickAdd">
                <v-icon>add</v-icon>
              </v-btn>
            </div>
          </v-flex>
        </v-layout>

        <!-- 投入品下面的表格 -->
        <v-layout row>
          <v-flex xs10
                  offset-xs2>
            <v-data-table :headers="headers"
                          :items="outputlist"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <td v-text="props.item.typename"
                    class="text-xs-left"></td>
                <td v-text="props.item.name"
                    :title="props.item.name"
                    class="text-xs-left hideoverflow"
                    style="max-width:250px;"></td>
                <td v-text="props.item.stand"
                    :title="props.item.stand"
                    class="text-xs-left hideoverflow"
                    style="max-width:240px;"></td>
                <td class="text-xs-left"
                    style="min-width:100px;">
                  <span v-text="props.item.num"></span>
                  <span v-text="props.item.numunitname"></span>
                  <span v-if="!props.item.num && !props.item.numunitname">- -</span>
                </td>
                <td v-text="props.item.facturer || '- -'"
                    :title="props.item.facturer"
                    class="text-xs-left hideoverflow"
                    style="max-width:250px;"></td>
                <td class="text-xs-center outputOperate">
                  <v-btn @click="editModalForm(props.item.id)"
                         fab
                         small
                         icon
                         title="编辑">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn @click="deleteModalForm(props.item.id)"
                         color="error"
                         fab
                         small
                         flat
                         icon
                         title="删除">
                    <v-icon>delete</v-icon>
                  </v-btn>
                </td>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>

        <!-- 投入品成本 -->
        <v-layout row
                  class="putKind">
          <v-flex xs2>
            <v-subheader>投入品成本</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-text-field label=""
                          suffix="元"
                          single-line
                          v-model="cost"
                          maxlength="8"
                          type="number"
                          onkeyup="value=value.replace(/[^\d.]/g,'')"
                          oninput=" if(value.length > 8) value = value.slice(0, 8) "
                          :rules="rules.number">
            </v-text-field>
          </v-flex>
        </v-layout>

        <!-- 附件 -->
        <v-layout row
                  class="marginTop">
          <v-flex xs2><span class="labelColor">附件</span></v-flex>
          <v-flex xs10>
            <v-layout row
                      wrap
                      class="uploadFile-wrapper">
              <v-flex xs12
                      class="uploadFile-text">
                <v-btn id="uploadFile"
                       color="primary"
                       dark>
                  <v-icon dark>backup</v-icon>
                </v-btn>
                <span>点击左侧上传按钮选择文件或将文件拖曳到此上传,支持多文件上传</span>
              </v-flex>
              <v-flex xs12>
                <dropzone ref="sendRecordDropzone"
                          id="dropzone"
                          :options="dropzoneOptions"
                          @vdropzone-success="vsuccess"
                          @vdropzone-error="verror"
                          @vdropzone-removed-file="removeFile"
                          @vdropzone-files-added="addFiles" />
              </v-flex>
              <v-flex xs12
                      v-if="isEdit && oldResourcehistorylist.length > 0">
                <!-- 资源列表下面的表格 ??? -->
                <v-data-table :headers="resourceHeaders"
                              :items="oldResourcehistorylist"
                              no-data-text="暂无数据"
                              no-results-text="暂无数据"
                              hide-actions
                              class="resourceTable">
                  <template slot="items"
                            slot-scope="props">
                    <td v-text="props.item.resourcename"></td>
                    <td v-text="props.item.resourcepath"></td>
                    <td class="text-xs-center">
                      <v-btn color="error"
                             fab
                             small
                             flat
                             icon
                             title="删除"
                             @click="deleteResource(props.item.fileId)">
                        <v-icon>delete</v-icon>
                      </v-btn>
                    </td>
                  </template>
                </v-data-table>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>

        <!-- 备注 -->
        <v-layout row
                  class="marginTop">
          <v-flex xs2
                  class="labelWrapper"><span class="labelColor">备注</span></v-flex>
          <v-flex xs5>
            <v-textarea label="请填写备注(500字以内)"
                        single-line
                        outline
                        v-model="remark"
                        maxlength="500">
            </v-textarea>
          </v-flex>
        </v-layout>

        <!-- 取消 保存 -->
        <v-layout row
                  justify-center>
          <v-btn class="btnSize"
                 @click="cancel">取消</v-btn>
          <v-btn class="btnSize btnColor"
                 @click="save"
                 :disabled="saveDisabled"
                 color="primary">
            保存
            <v-progress-circular v-show="progressShow"
                                 class="progressWrapper"
                                 size="23"
                                 indeterminate
                                 color="primary" />
          </v-btn>
        </v-layout>
      </v-form>
    </v-card>

    <!-- 投入品的弹出框 -->
    <v-modaldialog :isShow="dialog"
                   :title="clickNumber === 1 ? '添加投入品' : '编辑投入品'"
                   :modalMaxWidth="600"
                   :bodyHeight="440">
      <v-container slot>
        <!-- 投入品类别 -->
        <v-layout row
                  wrap>
          <v-flex xs4>
            <v-subheader>*投入品类别</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-select ref="type"
                      label="请选择投入品类别"
                      v-model="type"
                      :items="typeList"
                      item-text="name"
                      item-value="id"
                      single-line
                      :rules="typeRules">
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 投入品名称 -->
        <v-layout row
                  wrap>
          <v-flex xs4>
            <v-subheader>*投入品名称</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field ref="name"
                          label="请输入投入品名称(50字以内)"
                          v-model="name"
                          single-line
                          :rules="nameRules"
                          maxlength="50">
            </v-text-field>
          </v-flex>
        </v-layout>

        <!-- 投入品规格 规格单位   -->
        <v-layout row>
          <v-flex xs4>
            <v-subheader>*规格</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field ref="stand"
                          v-model="stand"
                          label="请填写投入品规格(20个字以内)"
                          :rules="standRules"
                          maxlength="20"
                          single-line>
            </v-text-field>
            <!-- <v-select
              ref="stand"
              label="规格"
              v-model="stand"
              :items="standunit"
              :rules="standRules"
              item-text="name"
              item-value="id"
              single-line
            >
            </v-select> -->
          </v-flex>
        </v-layout>

        <!-- 投入品数量 -->
        <v-layout row>
          <v-flex xs4>
            <v-subheader>投入品数量</v-subheader>
          </v-flex>
          <v-flex xs3
                  class="numWrapper">
            <v-text-field v-model="num"
                          type="number"
                          onkeyup="value=value.replace(/[^\d.]/g,'')"
                          oninput=" if(value.length > 8) value = value.slice(0, 8) "
                          maxlength="8"
                          :rules="rules.number"
                          label="请输入数字"
                          single-line>
            </v-text-field>
          </v-flex>
          <v-flex xs3
                  class="numunitWrapper">
            <v-select label="数量单位"
                      v-model="numunit"
                      :items="numunitList"
                      item-text="name"
                      item-value="id"
                      single-line>
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 生产厂家 -->
        <v-layout row
                  wrap>
          <v-flex xs4>
            <v-subheader>生产厂家</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field label="请输入投入品生产厂家名称(50字以内)"
                          v-model="facturer"
                          maxlength="50"
                          single-line>
            </v-text-field>
          </v-flex>
        </v-layout>
      </v-container>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click="cancelAdd">取消</v-btn>
          <v-btn flat
                 color="primary"
                 @click="submitAdd">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>

    <!-- 消息弹出框 -->
    <v-modaldialog :isShow="dialogMessage">
      <span slot
            v-text="messageResponse"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click="closeMessage">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>
  </v-container>
</template>


<script>
import TreePart from '@/components/TreePart'
import FarmerMixin from '@/pages/farmworks/farmerMixin'
import { excuteApis } from '@/apis'
import AllSelect from '@/components/AllSelect'

export default {
  data () {
    return {
      rules: {
        number: [
          (v) => this.numberRule(v) || '请输入有效数字,小数保留1位小数'
        ]
      },
      labelName: '请选择实施人',
      flag: false,
      typeName: '所有地块',
      menu2: false,
      data: [
        {
          label: '1-1',
          children: [
            {
              label: '2-1'
            },
            {
              label: '2-2'
            }
          ]
        },
        {
          label: '1-2'
        }
      ],
      dialogMessage: false,
      messageResponse: null,
      editUser: [], // 编辑时的参与人
      isEditUser: false,
      nameRules: [() => !!this.name || '请填写投入品名称'],
      typeRules: [() => !!this.type || '请选择投入品类别'],
      standRules: [() => !!this.stand || '请填写投入品规格'],
      numRules: [() => !!this.num || '请输入有效数字'],
      dialog: false,
      content: null, // 农事内容
      disabled: false,
      farmtype: [], // 农事类别
      farmtypeArr: [], // 农事类别列表
      farmtypeList: [],
      begintime: null, // 开始时间
      endtime: null, // 结束时间
      recordparcellist: [], // 地块
      recordparcellistArr: [], // 地块列表
      /*
      * 投入品相关
      * */
      name: '', // 投入品名称
      type: null, // 投入品类别
      typeList: [], // 投入品类型列表
      stand: null, // 规格
      standunit: [], // 投入品规格单位
      num: null, // 数量
      numunit: null, // 数量单位
      numunitList: [], // 数量单位列表
      facturer: null, // 投入品生产厂家
      clickNumber: 1, /* 标识 投入品 新增1 编辑2  */
      outputIndex: -1, /* 当前编辑的是那一个投入品 用id标识 */
      outputlist: [], // 存储投入品数组对象 - value
      outputlistText: [], // 存储投入品数组对象 - text
      cost: null, // 投入品成本
      remark: null,

      count: 0, // 计数投入品条数 唯一标示
      editResource: [], // 资源编辑列表
      headers: [
        { text: '类别', sortable: false, align: 'left' },
        { text: '名称', sortable: false, align: 'left' },
        { text: '规格', sortable: false, align: 'left' },
        { text: '数量', sortable: false, align: 'left' },
        { text: '厂家', sortable: false, align: 'left' },
        { text: '操作', sortable: false, align: 'center' }
      ],
      /* 日期选择 */
      date: null,
      menu: false,
      modal: false,
      progressShow: false, // 转圈
      saveDisabled: false, // 保存按钮是否不可用
      /* 文件上传相关 */
      resourceHeaders: [
        { text: '资源名称', value: 'value1', sortable: false, align: 'left' },
        { text: '访问地址', value: 'value2', sortable: false, align: 'left' },
        { text: '操作', value: 'value6', sortable: false, align: 'center' }
      ],
      resourcehistorylist: [], // 资源列表
      oldResourcehistorylist: [], // 已有资料列表
      fileId: 0, // 上传文件资源标识
      dropzoneOptions: {
        url: global.constant.uploadFileUrl, // ok
        method: 'post', // 文件上传方式 默认post
        // thumbnailWidth: 150, //  设置缩略图的缩略比 配合下面的thumbnailHeight一起使用
        // thumbnailHeight: 150,
        maxFilesize: 20, // 上传大小限制 单位MB
        // uploadMultiple: // 是否 Dropzone 应该在一个请求中发送多个文件
        dictDefaultMessage: '',
        clickable: '#uploadFile', // dropzone 元素本身是否可以点击 默认true,或者设置点击那个元素能上传,可以是数组
        // maxFiles: 10 // 最多处理几个文件
        addRemoveLinks: true,
        dictFileTooBig: '请上传20M以内的文件', // 文件过大时显示的文字信息
        dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        dictRemoveFile: '移除', // 移除按钮自定义文字
        dictResponseError: '上传失败,请重试' // 服务器无响应的显示信息
      }
    }
  },
  mixins: [FarmerMixin],
  components: { TreePart, AllSelect },
  computed: {
    /**
     * 投入品表单必填字段
     */
    outputForm () {
      return {
        name: this.name,
        type: this.type,
        stand: this.stand
      }
    },

    /**
     * 是否是编辑页面
     */
    isEdit () {
      return !!this.$route.query.id || (!!this.$route.query.taskId && !!this.$route.query.recordId)
    },

    /**
     * 是否是从农事任务过来的
     */
    isTask () {
      return this.$route.query.taskId && this.$route.query.recordId
    }
  },
  mounted () {
    this.getServiceData()
    this.disabledSpace()
    let pid = this.$route.query.parcelid
    pid ? this.recordparcellist.push(pid) : null
  },
  methods: {
    numberRule (v) {
      if (v) {
        return (/^\+?[1-9][0-9]*(\.\d{1,1})?$/.test(v)) || (/^0(\.\d{1,1})?$/.test(v))
      }
      return true
    },
    /**
     * 禁止空格键触发页面滚动条滑动
     */
    disabledSpace () {
      // 解决 按下space时, 弹出框中下拉框位置错位问题
      document.onkeydown = function (event) {
        if (event.target.nodeName === 'TEXTAREA' || event.target.nodeName === 'INPUT') return // 输入框,下拉框 return
        if (event.keyCode === 32) {
          event.preventDefault()
        }
      }
    },

    /**
     * 限定开始时间
     */
    begintimeLimit (val) {
      if (this.endtime === null) {
        return true
      } else {
        return val <= this.endtime
      }
    },

    /**
     * 限定结束时间
     */
    endtimeLimit (val) {
      if (this.begintime === null) {
        return true
      } else {
        return val >= this.begintime
      }
    },

    /**
     * 取消
     */
    cancel () {
      // 1 重置页面数据
      // this.$refs.form.reset()
      // 2. 返回上一页
      const router = this.$router
      router.go(-1)
    },

    /**
     * 保存 main
     */
    save () {
      let formHasErrors = this.valdateForm() // 验证表单
      let treeHasError = this.$refs.treePart.valdateForm()
      if (formHasErrors | treeHasError) return
      // 农事类别
      let farmtypeList = []
      if (this.farmtype.length > 0) {
        for (let i = 0; i < this.farmtype.length; ++i) {
          farmtypeList.push({ farmtype: this.farmtype[i] })
        }
      }
      this.farmtypeList = farmtypeList

      // 地块 ?
      let newRecordparcellist = [] // 存储新格式的recordparcellist
      // let editSelectItems = this.$refs.allSelect.selectItems
      let selectItems = []
      if (this.$refs.allSelect.selectItems.length > 0) {
        if (this.$refs.allSelect.selectItems[0].id === 'all') {
          selectItems = this.$refs.allSelect.selectItems.slice(1)
        } else {
          selectItems = this.$refs.allSelect.selectItems
        }
      }
      console.log(selectItems)
      if (selectItems) {
        for (let list of selectItems) {
          if (list.id) {
            newRecordparcellist.push({ parcelid: list.id })
          } else {
            newRecordparcellist.push({ parcelid: list })
          }
        }
      }
      console.log(newRecordparcellist)
      // for (let i = 0; i < selectItems.length; ++i) {
      //   newRecordparcellist.push({parcelid: selectItems[i]})
      // }
      // 附件列表 (编辑时包含已有附件 要整合)
      let newResourcehistorylist = [...this.oldResourcehistorylist, ...this.resourcehistorylist]
      // 编辑、 新增
      this.saveDisabled = true // 在服务响应之前禁用返回按钮
      this.progressShow = true // 显示转圈
      if (this.isEdit) {
        // this.recordparcellist = editSelectItems // 给组件赋值
        let param = {
          content: this.content, // 农事内容
          farmtypelist: this.farmtypeList, // 农事类别
          begintime: this.begintime, // 开始时间
          endtime: this.endtime, // 结束时间
          cost: this.cost || 0.0, // 投入成本
          remark: this.remark, // 备注
          recordparcellist: newRecordparcellist, // 地块列表
          recordpartuserlist: this.partuserlist, // 参与人列表
          outputlist: this.outputlist, // 投入品记录列表
          resourcehistorylist: newResourcehistorylist // 附件列表
        }
        if (this.isTask) param.sourcedictvalue = 'source-task' // 农事任务 设置记录来源为任务
        if (this.$route.query.id) param.id = this.$route.query.id // 农事记录编辑中传的id
        if (this.$route.query.recordId) param.idlist = this.strArrayToIntArray(this.$route.query.recordId.split(',')) // 农事任务编辑传的id
        excuteApis(param, global.constant.farmApiUrls, 'record', 'update').then(res => {
          if (res.data && res.data.flag === 1) {
            // 如果是农事任务
            if (this.isTask) {
              let param = {
                taskid: this.$route.query.taskId,
                recordeid: this.$route.query.recordId
              }
              excuteApis(param, global.constant.farmApiUrls, 'task', 'savetorecord').then(response => {
                if (response.data.flag === 1) {
                  // 跳转至农事任务详情页面
                  // this.$router.push({name: 'farmertasksDetail', query: {id: this.$route.query.taskId, select: 1}})
                  this.$router.go(-1)
                } else {
                  // 保存到农事记录失败
                  this.openMessage('response.data.message')
                }
              }).catch((response) => {
                console.log('农事任务保存到农事记录失败')
                console.log(response)
              })
            } else {
              // 如果是农事记录编辑
              this.$router.push({ name: 'farmerrecords' })
            }
          } else {
            this.openMessage('服务异常,请重试')
          }
        }).catch(result => {
          console.log('修改农事记录失败')
          console.log(result)
          this.openMessage('服务异常,请重试')
        }).finally(() => {
          this.saveDisabled = false // 服务已经响应 启用提交按钮
          this.progressShow = false // 停止转圈
        })
      } else {
        let param = {
          content: this.content, // 农事内容
          farmtypelist: this.farmtypeList, // 农事类别
          begintime: this.begintime, // 开始时间
          endtime: this.endtime, // 结束时间
          cost: this.cost || 0.0, // 投入成本
          remark: this.remark, // 备注
          recordparcellist: newRecordparcellist, // 地块列表
          recordpartuserlist: this.partuserlist, // 参与人列表
          // recordpartuserlist: [{userid: 1}, {userid: 2}, {userid: 3}], // 参与人列表
          outputlist: this.outputlist, // 投入品记录列表
          resourcehistorylist: newResourcehistorylist // 附件列表
        }
        excuteApis(param, global.constant.farmApiUrls, 'record', 'add').then(res => {
          console.log('新增农事记录成功')
          console.log(res)
          if (res.data && res.data.flag === 1) {
            console.log('添加成功!')
            // this.$router.push({name: 'farmerrecords'})
            this.$router.go(-1)
          } else {
            this.openMessage('服务异常,请重试')
          }
        }).catch(result => {
          console.log('新增农事记录失败')
          console.log(result)
          this.openMessage('服务异常,请重试')
        }).finally(() => {
          this.saveDisabled = false // 服务已经响应 启用提交按钮
          this.progressShow = false // 停止转圈
        })
      }
    },

    /**
     * 添加投入品
     */
    clickAdd () {
      this.dialog = true // 打开弹窗
      this.clickNumber = 1 // 用1标识新增
      // this.resetoutputForm() // 重置表单验证
      this.resetModalForm() // 重置表单数据
    },

    /**
     * 取消添加投入品
     */
    cancelAdd () {
      this.dialog = false // 关闭弹窗
    },

    /**
     * 确认添加投入品
     */
    submitAdd () {
      this.valdateoutputForm()
      if (this.num < 0) return
      if (this.outputFormHasErrors) return  // 未通过 return
      this.solveRuleBug() // 解决bug用的
      let clickNumber = this.clickNumber // 当前点击的是新增还是编辑

      /**
       * 如果是从编辑过来并且投入品列表不为空. 添加投入品的id
       * 从 list 最后一个id开始
       */
      console.log(this.outputlist)
      if (this.isEdit && this.outputlist.length > 0) {
        this.count = this.outputlist[this.outputlist.length - 1].id + 1
      }

      /* 一些处理 */
      // 获取typeList的id
      let typeArrId = -1 // typeList 的id
      for (let i = 0; i < this.typeList.length; ++i) {
        if (this.type === this.typeList[i].id) {
          typeArrId = i
          break
        }
      }
      // let standunitId = -1 // standunit 的id
      // for (let i = 0; i < this.standunit.length; ++i) {
      //   if (this.stand === this.standunit[i].id) {
      //     standunitId = i
      //     break
      //   }
      // }
      // 获取numunitList的id
      let numunitArrId = -1 // numunitList 的id
      if (this.numunitList.length > 0 && this.numunit) {
        for (let i = 0; i < this.numunitList.length; ++i) {
          if (this.numunit === this.numunitList[i].id) {
            numunitArrId = i
            break
          }
        }
      }
      // 数据变更
      switch (clickNumber) {
        case 1:
          this.outputlist.push({
            name: this.name, // 投入品名称
            type: this.type, // 投入品类型
            typename: this.typeList[typeArrId].name, // 投入品类别text形式
            // standname: this.standunit[standunitId].name, // 投入品规格text形式
            stand: this.stand, // 投入品规格
            num: this.num || '', // 投入品数量
            numunit: this.numunit || '', // 投入品数量单位
            numunitname: numunitArrId !== -1 ? this.numunitList[numunitArrId].name : '', // 数量单位text形式
            facturer: this.facturer || '', // 厂家
            id: this.count++ // 唯一标示当前对象的属性
          })
          break

        case 2:
          if (this.outputIndex !== -1) {
            this.outputlist[this.outputIndex].name = this.name
            this.outputlist[this.outputIndex].type = this.type
            this.outputlist[this.outputIndex].typename = this.typeList[typeArrId].name // 投入品text形式
            this.outputlist[this.outputIndex].stand = this.stand
            // this.outputlist[this.outputIndex].standname = this.standunit[standunitId].name
            this.outputlist[this.outputIndex].num = this.num || ''
            this.outputlist[this.outputIndex].numunit = this.numunit || ''
            this.outputlist[this.outputIndex].numunitname = numunitArrId !== -1 ? this.numunitList[numunitArrId].name : '' // 数量单位text形式
            this.outputlist[this.outputIndex].facturer = this.facturer || ''
          }
          break
      }
      // 关闭弹窗
      this.dialog = false
      // 恢复校验规则
      this.resetRule()
    },

    /**
     * 表单已经验证通过 错误信息会闪现解决方法
     * 方法: 所有验证规则置空
     */
    solveRuleBug () {
      this.nameRules = []
      this.typeRules = []
      this.standRules = []
    },

    /**
     * 恢复验证规则
     */
    resetRule () {
      this.nameRules = [() => !!this.name || '请填写投入品名称']
      this.typeRules = [() => !!this.type || '请选择投入品类别']
      this.standRules = [() => !!this.stand || '请填写投入品规格']
    },

    /**
     * 删除投入品
     */
    deleteModalForm (id) {
      this.removeByValue(this.outputlist, id)
    },

    /**
     * 编辑投入品
     */
    editModalForm (id) {
      // 获取当前id 所对应的数组下标
      let index = -1 // 当前id 所对应的数组下标
      for (let i = 0; i < this.outputlist.length; ++i) {
        if (this.outputlist[i].id === id) {
          index = i
          break
        }
      }
      this.clickNumber = 2 // 用2来标识编辑
      this.name = this.outputlist[index].name
      this.type = this.outputlist[index].type
      this.stand = this.outputlist[index].stand
      this.num = this.outputlist[index].num
      this.numunit = this.outputlist[index].numunit
      this.facturer = this.outputlist[index].facturer
      this.dialog = true
      this.outputIndex = index // 当前编辑的是哪一个投入品
    },

    /**
     * 重置弹窗表单
     */
    resetModalForm () {
      this.name = null
      this.type = null // 下拉框
      this.stand = null
      this.num = null
      this.numunit = null // 下拉框
      this.facturer = null
    },

    /**
     * 根据元素属性删除对应数组
     */
    removeByValue (arr, val) {
      for (let i = 0; i < arr.length; ++i) {
        if (arr[i].id === val) {
          arr.splice(i, 1)
          break
        }
      }
    },

    /**
     * 获取服务数据
     */
    getServiceData () {
      /* 获取农事类别 */
      excuteApis({ parentcode: 'farmtype' }, global.constant.systemApis, 'dict', 'query').then(data => {
        if (data.data && data.data.data && data.data.flag === 1) {
          console.log('获取农事类别成功--------')
          this.farmtypeArr = data.data.data
          console.log(this.farmtypeArr)
        }
      })

      /* 获取投入品类别 */
      excuteApis({ parentcode: 'agriculturaltype' }, global.constant.systemApis, 'dict', 'query').then(data => {
        if (data.data && data.data.data && data.data.flag === 1) {
          console.log('获取投入品类别成功')
          this.typeList = data.data.data
          console.log(this.typeList)
        }
      })

      /**
       * 获取投入品数量单位
       */
      excuteApis({ parentcode: 'numberunit' }, global.constant.systemApis, 'dict', 'query').then(data => {
        console.log('获取数量单位成功')
        console.log(data)
        if (data.data && data.data.data && data.data.flag === 1) {
          this.numunitList = data.data.data
        }
      })
      /**
       * 获取投入品规格
       */
      excuteApis({ parentcode: 'standunit' }, global.constant.systemApis, 'dict', 'query').then(data => {
        console.log('获取规格单位成功')
        console.log(data)
        if (data.data && data.data.data && data.data.flag === 1) {
          this.standunit = data.data.data
        }
      })

      /**
       * 获取地块
       */
      // this.recordparcellistArr = this.$store.state.orgLands
      this.recordparcellistArr = global.helper.ls.get('orgLands')
      this.flag = true
      // excuteApis({}, global.constant.parcelApis, 'parcel', 'queryParcel').then(data => {
      //   console.log('获取地块成功------')
      //   console.log(data)
      //   this.flag = true
      //   if (data.data && data.data.flag === 1 && data.data.data) {
      //     this.recordparcellistArr = data.data.data
      //   }
      // })

      /**
       * 获取农事记录详情
       */
      if (this.isEdit) {
        this.getRecord(this.$route.query.id)
      } else {
        this.editUser = [] // 实施人
        this.isEditUser = true
      }

      /**
       * 获取农事任务详情
       */
      if (this.isTask) {
        let recordId = this.$route.query.recordId.split(',')
        this.getRecord(recordId[0])
      }
    },

    /**
     * 获取农事记录详情
     */
    getRecord (id) {
      excuteApis({ id: id }, global.constant.farmApiUrls, 'record', 'querybyid').then(res => {
        console.log('获取农事记录详情成功')
        console.log(res)
        if (res.data && res.data.flag === 1 && res.data.data) {
          let record = res.data.data
          this.content = record.content // 农事内容
          // 农事类别
          if (record.farmtypelist) {
            for (let list of record.farmtypelist) {
              this.farmtype.push(list.farmtype)
            }
          }
          this.begintime = record.begintime ? record.begintime.substring(0, 10) : record.begintime // 开始时间
          this.endtime = record.endtime ? record.endtime.substring(0, 10) : record.endtime // 结束时间

          // 地块 ??? 地块没显示出来
          if (this.isTask) { // 农事任务的时候 不取这里的数据
            let parcelids = this.$route.query.parcelids ? this.$route.query.parcelids.split(',') : []
            console.log('农事任务的地块:')
            console.log(parcelids)
            this.recordparcellist = this.strArrayToIntArray(parcelids)
            console.log(this.recordparcellist)
          } else {
            if (record.parcelids) {
              console.log(321)
              console.log(record.parcelids)
              let arr = record.parcelids.split(',')
              console.log(arr)
              this.recordparcellist = this.strArrayToIntArray(arr)
              console.log(this.recordparcellist)
            }
          }
          this.outputlist = record.outputlist // 投入品列表
          this.cost = record.cost // 投入成本

          /* 为每个附件添加id 设置fileId 起始值 */
          if (record.recordresourceEXTList) {
            for (let i = 0; i < record.recordresourceEXTList.length; ++i) {
              record.recordresourceEXTList[i].fileId = i
              this.oldResourcehistorylist.push(record.recordresourceEXTList[i])
            }
          }
          this.remark = record.remark // 备注
          this.editUser = record.recordpartuserlist // 实施人
          this.isEditUser = true
        }
      })
    },

    /**
     * 文件添加到进来时
     * 为每个文件添加id唯一标识
     */
    addFiles (files) {
      for (let file of files) {
        file.fileId = this.fileId++
      }
    },

    /**
     * 上传文件成功
     */
    vsuccess (file, response) {
      if (response.flag === 1 && response.data && response.data[0]) {
        response.data[0].fileId = file.fileId
        this.resourcehistorylist.push(response.data[0])
      }
    },

    /**
     * 上传文件失败
     */
    verror (file, message) {
      if (message === '请上传20M以内的文件') {
        this.openMessage('请上传20M以内的文件')
      } else {
        this.openMessage('服务异常,请稍后重试')
      }
      this.$refs.sendRecordDropzone.removeFile(file) // 移除文件
    },

    /**
     * 移除文件
     * 同时移除数据
     */
    removeFile (file) {
      for (let i = 0; i < this.resourcehistorylist.length; ++i) {
        if (this.resourcehistorylist[i].fileId === file.fileId) {
          this.resourcehistorylist.splice(i, 1)
          break
        }
      }
    },

    /**
     * 删除已有附件列表
     */
    deleteResource (id) {
      for (let i = 0; i < this.oldResourcehistorylist.length; ++i) {
        if (this.oldResourcehistorylist[i].fileId === id) {
          this.oldResourcehistorylist.splice(i, 1)
          break
        }
      }
    },

    /**
     * 验证表单
     */
    valdateForm () {
      return !this.$refs.form.validate()
    },

    /**
     * 验证投入品表单
     */
    valdateoutputForm () {
      this.outputFormHasErrors = false // 表单是否有错误
      Object.keys(this.outputForm).forEach(f => {
        if (!this.outputForm[f]) this.outputFormHasErrors = true
        this.$refs[f].validate(true) // true 验证当前字段, false 不验证
      })
    },

    /**
     * 将表单重置到验证之前 resetform
     */
    resetoutputForm () {
      this.outputFormHasErrors = false
      Object.keys(this.outputForm).forEach(f => {
        this.$refs[f].reset()
      })
    },

    /**
     * 点击tree时获取已经选择的用户
     */
    clickNode (node) {
      this.partuserlist = [] // 清空用户列表
      if (!node || node.length === 0) return
      for (let user of node) {
        this.partuserlist.push({ userid: user.id })
      }
    },

    /**
     * 字符串数组转int数组
     * @param array array
     * return new array
     */
    strArrayToIntArray (array) {
      if (!array || array.length === 0) return []
      let newArray = []
      for (let list of array) {
        newArray.push(parseInt(list))
      }
      return newArray
    },

    /**
     * 设置弹窗消息
     */
    openMessage (text) {
      this.messageResponse = text
      this.dialogMessage = true
    },

    /**
     * 关闭弹窗
     */
    closeMessage () {
      this.messageResponse = null
      this.dialogMessage = false
    }
  }
}
</script>
<style scoped>
.backWrapper {
  cursor: pointer;
}
.sendTask-wrapper {
  margin-left: 20px;
}
.cutLine {
  height: 1px;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: #ccc;
}
form {
  padding-left: 20px;
}
.wrapper {
  display: flex;
}
.wrapper > .btn {
  margin-top: -10px;
  color: white;
}
.btnMargin {
  margin-left: 0;
}
/* 投入品wrapper */
.putKind {
  margin-top: 30px;
}
/* btn大小调整 */
.btnSize {
  min-width: 107px;
}
/* dropzone 边框调整 */
.vue-dropzone {
  border-width: 0;
}
.uploadFile-wrapper {
  border: 2px solid #e5e5e5;
}
.uploadFile-text {
  margin: 15px 0 15px 15px;
}
#dropzone {
  padding: 5px;
}
.progressWrapper {
  position: absolute;
  left: 73px;
}
.tree {
  padding-left: 30px;
  padding-top: 10px;
}
.btnColor {
  color: #fff;
}
.labelColor {
  color: rgba(0, 0, 0, 0.54);
  padding: 0 16px 0 16px;
}
.marginTop {
  margin-top: 30px;
}
.labelWrapper {
  margin-top: 17px;
}
.addWrapper {
  margin-bottom: 20px;
}
.numWrapper {
  padding-right: 5px;
}
.numunitWrapper {
  padding-left: 5px;
}
.containerWrapper {
  padding: 0;
  margin-bottom: 50px;
}
.btnPadding {
  padding-bottom: 20px;
}
svg {
  border: 3px solid red;
}
.resourceTable {
  border-width: 0;
}
.dropzone .dz-preview .dz-error-mark svg {
  display: none;
}
.outputOperate {
  min-width: 170px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
