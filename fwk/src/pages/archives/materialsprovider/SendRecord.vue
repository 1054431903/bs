<!--
    表单select v-model 只能绑定字符串?
-->
<template>
  <v-card>
    <v-card-text>
      <v-container fluid>
        <form ref="form">
          <!-- 农事内容 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>*&nbsp;农事内容</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-text-field
                label="请输入农事内容(100字以内)"
                v-model="content"
                textarea
                single-line
              >
              </v-text-field>
            </v-flex>
          </v-layout>

          <!-- 农资类别 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>*&nbsp;农资类别</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-select
                label="请选择农资类别"
                v-model="type"
                :items="typeList"
                item-text="name"
                item-value="id"
                single-line
              >
              </v-select>
            </v-flex>
          </v-layout>

          <!-- 领取人 -->
          <!--<v-layout row>
            <v-flex xs2>
              <v-subheader>*&nbsp;领取人</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-select
                label="请选择领取人"
                v-model="drowuserid"
                :items="drowuseridList"
                item-text="name"
                item-value="id"
                single-line
              >
              </v-select>
            </v-flex>
          </v-layout>-->

          <!-- 开始时间 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>*&nbsp;开始时间</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-menu
                lazy
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                full-width
                :nudge-right="40"
                max-width="290px"
                min-width="290px"
              >
                <v-text-field
                  slot="activator"
                  label="请选择开始时间"
                  v-model="drowtime"
                  prepend-icon="event"
                  single-line
                  readonly
                ></v-text-field>
                <v-date-picker locale="zh-cn" v-model="drowtime" no-title scrollable actions>
                  <template slot-scope="{ save, cancel }">
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn flat color="primary" @click="cancel">取消</v-btn>
                      <v-btn flat color="primary" @click="save">确定</v-btn>
                    </v-card-actions>
                  </template>
                </v-date-picker>
              </v-menu>
            </v-flex>
          </v-layout>

          <!-- 结束时间 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>结束时间</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-menu
                lazy
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                full-width
                :nudge-right="40"
                max-width="290px"
                min-width="290px"
              >
                <v-text-field
                  slot="activator"
                  label="请选择结束时间"
                  v-model="drowtime2"
                  prepend-icon="event"
                  single-line
                  readonly
                ></v-text-field>
                <v-date-picker locale="zh-cn" v-model="drowtime2" no-title scrollable actions>
                  <template slot-scope="{ save, cancel }">
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn flat color="primary" @click="cancel">取消</v-btn>
                      <v-btn flat color="primary" @click="save">确定</v-btn>
                    </v-card-actions>
                  </template>
                </v-date-picker>
              </v-menu>
            </v-flex>
          </v-layout>

          <!-- 地块 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>地块</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-select
                label="全部地块(可多选)"
                v-model="dikuai"
                :items="dikuaiList"
                multiple
                single-line
              >
              </v-select>
            </v-flex>
          </v-layout>

          <!-- 参与人 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>参与人</v-subheader>
            </v-flex>
            <v-flex xs5>
              <div class="wrapper">
                <v-btn class="btnMargin" fab dark color="primary" small>
                  <v-icon dark>add</v-icon>
                </v-btn>
              </div>
            </v-flex>
          </v-layout>

          <!-- 投入品 -->
          <v-layout row class="putKind">
            <v-flex xs2>
              <v-subheader>投入品</v-subheader>
            </v-flex>
            <v-flex xs10>
              <div class="wrapper">
                <span>(可添加多项)&nbsp;&nbsp;<span @click.stop="clickAdd">添加</span></span>
              </div>
            </v-flex>
          </v-layout>

          <!-- 投入品下面的表格 -->
          <v-layout row>
            <v-flex xs10 offset-xs2>
              <v-data-table
                v-bind:headers="headers"
                :items="medicineintoList[1]"
                hide-actions
                class="elevation-1"
              >
                <template slot="items" slot-scope="props">
                  <td>{{ props.item.tourupin }}</td>
                  <td>{{ props.item.tourupinType }}</td>
                  <td>{{ props.item.specific }}{{ props.item.specificunit }}</td>
                  <td>{{ props.item.num }}{{ props.item.numberunit }}</td>
                  <td>{{ props.item.tourupinChangJia }}</td>
                  <td class="text-xs-center">
                    <v-btn fab small @click="editModalForm(props.item.id)">
                      <v-icon>edit</v-icon>
                    </v-btn>
                    <v-btn fab small @click="deleteModalForm(props.item.id)">
                      <v-icon>delete</v-icon>
                    </v-btn>
                  </td>
                </template>
              </v-data-table>
            </v-flex>
          </v-layout>

          <!-- 投入品成本 -->
          <v-layout row class="putKind">
            <v-flex xs2>
              <v-subheader>投入品成本</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-text-field
                label=""
                suffix="元"
                single-line
              >
              </v-text-field>
            </v-flex>
          </v-layout>

          <!-- 附件 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>附件</v-subheader>
            </v-flex>
            <v-flex xs5>
              <!-- 文件上传 -->
              <dropzone ref="sendRecordDropzone"
                        id="dropzone"
                        :options="dropzoneOptions"
                        @vdropzone-file-added="vfileAdded"
                        @vdropzone-success="vsuccess"
                        @vdropzone-error="verror"
              />
            </v-flex>
          </v-layout>

          <!-- 备注 -->
          <v-layout row>
            <v-flex xs2>
              <v-subheader>备注</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-text-field
                label="请填写备注(多少字以内)"
                single-line
                textarea
              >
              </v-text-field>
            </v-flex>
          </v-layout>


          <!-- 投入品 -->
          <!--<v-layout row class="putKind">
            <v-flex xs2>
              <v-subheader></v-subheader>
            </v-flex>
            <v-flex xs10>
              <div class="wrapper">
                <v-data-table
                      v-bind:headers="headers"
                      :items="items"
                      hide-actions
                      class="elevation-1"
                    >
                      <template slot="items" slot-scope="props">
                        <td>{{ props.item.name }}</td>
                        <td class="text-xs-right">{{ props.item.calories }}</td>
                        <td class="text-xs-right">{{ props.item.fat }}</td>
                        <td class="text-xs-right">{{ props.item.carbs }}</td>
                        <td class="text-xs-right">{{ props.item.protein }}</td>
                        <td class="text-xs-right">{{ props.item.sodium }}</td>
                        <td class="text-xs-right">{{ props.item.calcium }}</td>
                        <td class="text-xs-right">{{ props.item.iron }}</td>
                      </template>
                    </v-data-table>
              </div>
            </v-flex>
          </v-layout>-->

          <!-- 领取地点 -->
          <!--<v-layout row>
            <v-flex xs2>
              <v-subheader>领取地点</v-subheader>
            </v-flex>
            <v-flex xs2>
              <v-select
                label="省"
                v-model="province"
                :items="provinceList"
                item-text="name"
                item-value="code"
                single-line
              >
              </v-select>
            </v-flex>
            <v-flex xs2>
              <v-select
                label="市"
                v-model="city"
                :items="cityList"
                item-text="name"
                item-value="code"
                single-line
              >
              </v-select>
            </v-flex>
            <v-flex xs2>
              <v-select
                label="县"
                v-model="area"
                :items="areaList"
                item-text="name"
                item-value="code"
                single-line
              >
              </v-select>
            </v-flex>
            <v-flex xs2>
              <v-select
                label="镇"
                v-model="street"
                :items="streetList"
                item-text="name"
                item-value="code"
                single-line
              >
              </v-select>
            </v-flex>
          </v-layout>-->

          <!-- 详细地址 -->
         <!-- <v-layout row>

            <v-flex xs5 offset-xs2>
              <v-text-field
                name="input-3-5"
                v-model="drowaddressdetail"
                label="详细地址"
                single-line
              ></v-text-field>
            </v-flex>
          </v-layout>-->

          <!-- 其他说明 -->
          <!--<v-layout row>
            <v-flex xs2>
              <v-subheader>其他说明</v-subheader>
            </v-flex>
            <v-flex xs5>
              <v-text-field
                name="input-3-5"
                v-model="explain"
                label="请输入其他需要说明的信息"
                single-line
              ></v-text-field>
            </v-flex>
          </v-layout>-->

          <!-- 取消 保存 -->
          <v-layout row justify-center>
            <v-btn class="btnSize" @click="cancel">取消</v-btn>
            <v-btn class="btnSize" @click="save">保存</v-btn>
          </v-layout>
        </form>
      </v-container>
    </v-card-text>

    <!-- 投入品的弹出框 -->
    <v-dialog v-model="dialog" width="600">
      <form ref="formModal">
        <v-card>
          <v-card-title>
            <span>添加投入品</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <!-- 投入品名称 tourupin tourupinType specific specificunit num numberunit tourupinChangJia -->
              <v-layout row wrap>
                <v-flex xs4>
                  <v-subheader>*&nbsp;投入品名称</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    label="请输入投入品名称"
                    v-model="tourupin"
                    single-line
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>

              <!-- 投入品类别 -->
              <v-layout row wrap>
                <v-flex xs4>
                  <v-subheader>*&nbsp;投入品类别</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-select
                    label="请选择投入品类别"
                    v-model="tourupinType"
                    :items="tourupinTypeList"
                    single-line
                  >
                  </v-select>
                </v-flex>
              </v-layout>

              <!-- 投入品规格   -->
              <v-layout row>
                <v-flex xs4>
                  <v-subheader>*&nbsp;规格</v-subheader>
                </v-flex>
                <v-flex xs3>
                  <v-text-field
                    v-model="specific"
                    label="请输入数字"
                    single-line
                  >
                  </v-text-field>
                </v-flex>
                <v-flex xs3>
                  <v-select
                    label="规格单位"
                    v-model="specificunit"
                    :items="specificunitList"
                    item-text="name"
                    item-value="id"
                    single-line
                  >
                  </v-select>
                </v-flex>
              </v-layout>

              <!-- 投入品数量 -->
              <v-layout row>
                <v-flex xs4>
                  <v-subheader>投入品数量</v-subheader>
                </v-flex>
                <v-flex xs3>
                  <v-text-field
                    v-model="num"
                    label="请输入数字"
                    single-line
                  >
                  </v-text-field>
                </v-flex>
                <v-flex xs3>
                  <v-select
                    label="数量单位"
                    v-model="numberunit"
                    :items="numberunitList"
                    item-text="name"
                    item-value="id"
                    single-line
                  >
                  </v-select>
                </v-flex>
              </v-layout>

              <!-- 生产厂家 -->
              <v-layout row wrap>
                <v-flex xs4>
                  <v-subheader>生产厂家</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    label="请输入投入品生产厂家名称"
                    v-model="tourupinChangJia"
                    single-line
                  >
                  </v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat @click.native="cancelAdd">取消</v-btn>
            <v-btn flat @click.native="submitAdd">确认</v-btn>
          </v-card-actions>
        </v-card>
      </form>
    </v-dialog>

  </v-card>
</template>


<script>
  import { querySystemDict, queryPositionByParentCode } from '@/apis/systems/dictionary'
  import { queryAgricname, queryUserByLoginUserOrg } from '@/apis/archives/sendRecord'
  export default {
    data () {
      return {
        /* dropzone 配置 */
        dropzoneOptions: {
          url: 'http://10.88.20.66:5762/fwk-service-archives/common/fileservice/upload', // 文件上传地址
          method: 'post', // 文件上传方式 默认post
          // thumbnailWidth: 150, //  设置缩略图的缩略比 配合下面的thumbnailHeight一起使用
          // thumbnailHeight: 150,
          maxFilesize: 0.5, // 上传大小限制
          dictDefaultMessage: 'hello'
        },
        dialog: false,
        content: null, // 农事内容
        nameid: null, // 农资名称绑定的数据
        nameidList: [], // 农资名称对象列表
        type: null, // 农资类别绑定的数据
        typeList: [], // 农资类别列表
        numberunit: null, // 数量单位绑定的数据
        numberunitList: [], // 数量单位列表
        specific: null, // 规格
        specificunit: null, // 规格单位
        specificunitList: [], // 规格单位列表
        num: null, // 数量
        numunit: null, // 数量单位
        drowuserid: null, // 绑定的领取人
        drowuseridList: [], // 领取人列表
        drowtime: null, // 开始时间
        drowtime2: null, // 结束时间
        dikuai: null, // 绑定的地块
        dikuaiList: ['地块1', '地块2', '地块3'], // 地块对象
        drowaddresscode: null, // 领取地点行政区划编号
        province: null, // 省
        provinceList: [], // 省列表
        city: null, // 市
        cityList: [], // 市列表
        area: null, // 县
        areaList: [], // 县列表
        street: null, // 镇
        streetList: [], // 镇列表
        drowaddressdetail: null, // 领取地详细地址
        explain: null, // 其他说明
        tourupin: '', // 投入品名称
        tourupinType: null, // 投入品类型
        tourupinTypeList: ['投入品1', '投入品2', '投入品3'], // 投入品类型列表
        tourupinChangJia: null, // 投入品生产厂家
        medicineintoList: [[], []], // 投入品对象数组 0-value 1-text
        count: 0, // 计数投入品条数 唯一标示
        count2: 0, // 同上
        headers: [
          { text: '名称', value: 'value1', sortable: false, align: 'left' },
          { text: '类别', value: 'value2', sortable: false, align: 'left' },
          { text: '规格', value: 'value3', sortable: false, align: 'left' },
          { text: '数量', value: 'value4', sortable: false, align: 'left' },
          { text: '厂家', value: 'value5', sortable: false, align: 'left' },
          { text: '操作', value: 'value6', sortable: false, align: 'center' }
        ],
        itemsTable: [
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87
          },
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87
          },
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87
          },
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87
          }
        ],
        items: ['1', '2', '3'],
        items2: ['1', '2', '3'],
        select3: null,
        items3: ['1', '2', '3'],
        items4: ['1', '2', '3'],
        items5: ['1', '2', '3'],
        items6: ['1', '2', '3'],
        items7: ['1', '2', '3'],
        items8: ['1', '2', '3'],
        /* 日期选择 */
        date: null,
        menu: false,
        modal: false
        /* nameRules: [
          (value) => !!value || '请填写农资民称'
        ] */
      }
    },
    computed: {
    },
    mounted () {
      /* 获取系统字典 */
      this.getSysDictionary()
    },
    methods: {
      vfileAdded (file) {
        console.log('====================================================')
        console.log(file)
      },

      /* 文件上传成功 file 文件 response 响应 */
      vsuccess (file, response) {
        console.log('文件上传成功')
        console.log(response)
      },
      verror (file, message, xhr) {
        console.log('文件上传失败')
        console.log(message)
        console.log(xhr)
      },
      /* 取消 */
      cancel () {
        console.log('取消')
        // 1 重置页面数据
        this.$refs.form.reset()
        // 2. 返回上一页
        const router = this.$router
        router.go(-1)
      },

      /* 保存 */
      save () {
        if (!this.nameid) {
          console.log('请填写农资名称')
          return
        }
      },

      /* 点击添加投入品 */
      clickAdd () {
        this.dialog = true // 打开弹窗
      },
      /* 取消添加投入品 */
      cancelAdd () {
        // 1. 重置表单
        this.resetModalForm()
        this.dialog = false // 2. 关闭弹窗
      },

      /* 确认添加投入品  */
      submitAdd () {
        // 1. 必填数据监测 待做
        // 2. 存数据 [0]存的是value [1]存的是text 待做.
        this.medicineintoList[0].push({
          tourupin: this.tourupin, // 投入品名称
          tourupinType: this.tourupinType, // 投入品类型
          specific: this.specific, // 投入品规格
          specificunit: this.specificunit, // 投入品规格单位
          num: this.num, // 投入品数量
          numberunit: this.numberunit, // 投入品数量单位
          tourupinChangJia: this.tourupinChangJia, // 厂家
          id: this.count++ // 唯一标示当前对象的属性
        })

        /* 规格单位text */
        let specificunitText = null
        for (let specificunit of this.specificunitList) {
          if (specificunit.id === this.specificunit) {
            specificunitText = specificunit.name // 规格单位文本值
          }
        }
        /* 数量单位text */
        let numberunitText = null
        for (let numberunit of this.numberunitList) {
          if (numberunit.id === this.numberunit) {
            numberunitText = numberunit.name // 规格单位文本值
          }
        }
        this.medicineintoList[1].push({
          tourupin: this.tourupin, // 投入品名称
          tourupinType: this.tourupinType, // 投入品类型
          specific: this.specific, // 投入品规格
          specificunit: specificunitText, // 投入品规格单位
          num: this.num, // 投入品数量
          numberunit: numberunitText, // 投入品数量单位
          tourupinChangJia: this.tourupinChangJia, // 厂家
          id: this.count2++
        })

        // 3. 重置表单
        this.resetModalForm()
        // 4. 关闭弹窗
        this.dialog = false
      },

      /* 编辑投入品 未完 */
      editModalForm (id) {
        console.log('编辑投入品')
        console.log('id = ' + id)
        /* 当前id 在数组中所对应的数组下标 */
        let arrId = -1 // 数组下标
        for (let i = 0; i < this.medicineintoList[1].length; ++i) {
          if (this.medicineintoList[1][i].id === id) {
            arrId = i
            break
          }
        }
        console.log('arrId = ' + arrId)
        this.tourupin = this.medicineintoList[1][arrId].tourupin
        this.tourupinType = this.medicineintoList[1][arrId].tourupinType
        this.specific = this.medicineintoList[1][arrId].specific
        this.specificunit = this.medicineintoList[1][arrId].specificunit
        this.num = this.medicineintoList[1][arrId].num
        this.numberunit = this.medicineintoList[1][arrId].numberunit
        this.tourupinChangJia = this.medicineintoList[1][arrId].tourupinChangJia
        this.dialog = true
      },

      /* 删除投入品 */
      deleteModalForm (id) {
        console.log('删除投入品')
        console.log('id = ' + id)
        this.removeByValue(this.medicineintoList[0], id) // 删除value
        this.removeByValue(this.medicineintoList[1], id) // 删除text
      },

      /* 重置弹窗里的表单 */
      resetModalForm () {
        // this.$refs.formModal.reset() // 重置不了 原因未知
        this.tourupin = null
        this.tourupinType = null
        this.specific = null
        this.specificunit = null
        this.num = null
        this.numberunit = null
        this.tourupinChangJia = null
      },

      /* 根据元素属性删除数组 */
      removeByValue (arr, val) {
        for (let i = 0; i < arr.length; ++i) {
          if (arr[i].id === val) {
            arr.splice(i, 1)
            break
          }
        }
      },

     /* 获取系统字典数据 */
      getSysDictionary () {
        /* 获取农资名称 */
        queryAgricname(1)
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              this.nameidList = data.data.data
            }
          })
          .catch(result => {
            console.log(result)
            console.log('获取数据失败')
          })

        /* 获取农资类别 */
        querySystemDict('agriculturaltype')
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              console.log('获取投入品类别成功')
              this.typeList = data.data.data
              console.log(this.typeList)
            }
          })
          .catch(result => {
            console.log(result)
            console.log('获取数据失败')
          })

        /* 获取规格单位 */
        querySystemDict('standunit')
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              console.log('获取规格单位成功')
              this.specificunitList = data.data.data
              console.log(this.specificunitList)
            }
          })
          .catch(result => {
            console.log(result)
            console.log('获取数据失败')
          })

        /* 获取数量单位 */
        querySystemDict('numberunit')
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              this.numberunitList = data.data.data
            }
          })
          .catch(result => {
            console.log(result)
            console.log('获取数据失败')
          })

        /* 获取领取人 */
        queryUserByLoginUserOrg(1)
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              this.drowuseridList = data.data.data
            }
          })
          .catch(result => {
            console.log(result)
          })

        /* 获取省级行政区划 */
        queryPositionByParentCode(0)
          .then(data => {
            console.log('获取省级行政区划成功')
            console.log(data)
            if (data.data && data.data.data && data.data.flag === 1) {
              this.provinceList = data.data.data
            }
          })
          .catch(result => {
            console.log('获取省级行政区划失败')
          })
      }
    }
  }
</script>
<style scoped>
  .wrapper{
    height: 48px;
    display: flex;
    align-items: center;
    border: 1px solid #CCC;
  }
  .btnMargin{
    margin-left: 0;
  }
  /* 投入品wrapper */
  .putKind{
    margin-top: 30px;
  }
  /* btn大小调整 */
  .btnSize{
    min-width: 107px;
  }
</style>
