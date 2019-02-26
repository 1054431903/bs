<template>
  <v-container fluid
               pa-0
               pb-4>
    <v-card class="page-content">
      <!-- <v-layout>
        <v-flex xs12>
          <go-back :name="viewTitle"> </go-back>
        </v-flex>
      </v-layout> -->
      <!--组织名称-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          *组织名称
        </v-flex>
        <v-flex xs7>
          <v-text-field type="text"
                        single-line
                        v-model="orgInformation.name"
                        label="请输入组织名称"
                        :counter="20"
                        maxlength="20">
          </v-text-field>
          <span class="orgName"
                v-show="orgName">* 组织名称为必填项</span>
        </v-flex>
      </v-layout>
      <!--组织性质-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          组织性质
        </v-flex>
        <v-flex xs7>
          <v-select v-bind:items="natureDropdown"
                    v-model="orgInformation.nature"
                    item-text="dictname"
                    item-value="dictvalue"
                    single-line
                    label="请选择组织性质"
                    no-data-text="无"></v-select>
        </v-flex>
      </v-layout>
      <!--组织法人-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          组织法人
        </v-flex>
        <v-flex xs7>
          <v-text-field type="text"
                        :counter="10"
                        maxlength="10"
                        v-model="orgInformation.legal"
                        single-line
                        label="请输入组织法人"></v-text-field>
          <!--<span class="legal" v-show="legal">请输入中英文字符</span>
          <span class="orgName" v-show="orgLegal">请输入组织法人</span>-->
        </v-flex>
      </v-layout>
      <!--成立时间-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          成立时间
        </v-flex>
        <v-flex xs7>
          <v-customdatepicker pickerLabel="请选择日期"
                              :selectedDate.sync="orgInformation.eastablishtime"
                              :allowedDates="eastablishtimeLimit">
          </v-customdatepicker>
        </v-flex>
      </v-layout>
      <!--组织logo-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          组织logo
        </v-flex>
        <v-flex xs7
                v-if="orgInformation.logourl">
          <div class="imgDiv"
               @mouseenter="showBtnDel=true"
               @mouseleave="showBtnDel=false">
            <img :src="orgInformation.logourl"
                 width="120"
                 height="120" />
            <div @click="btnDelImg"
                 v-if="showBtnDel">
              <v-avatar class="delImg red"
                        size="18px">
                <img :src="`static/sop/ic_close_white_24px.svg`" />
              </v-avatar>
            </div>
          </div>
        </v-flex>
        <v-flex v-else
                xs7
                @mouseenter="showBtnDel=true"
                @mouseleave="showBtnDel=false">
          <croppa v-model="myCroppa"
                  :accept="'image/png,image/bmp,image/jpg,image/jpeg'"
                  :width="120"
                  :height="120"
                  placeholder="选择照片"
                  :placeholder-font-size="12"
                  :disabled="false"
                  :show-remove-button="showBtnDel"
                  @image-remove="handleImageRemove"
                  @file-choose="handleCroppaFileChoose"></croppa>
          <v-btn class="uploadbtn"
                 v-if="uploadbtnvisible"
                 @click="uploadCroppedImage">点击上传</v-btn>
          <div class="errorstips"
               v-if="uploaderrortips.flag">{{ uploaderrortips.content }}</div>
        </v-flex>
      </v-layout>
      <!--注册资金-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          注册资金(万元)
        </v-flex>
        <v-flex xs7>
          <v-text-field onkeyup="value=value.replace(/[^\d.]/g,'')"
                        :counter="8"
                        type="number"
                        maxlength="8"
                        v-model="orgInformation.regisfund"
                        single-line
                        label="请输入注册资金"></v-text-field>
          <span class="orgName"
                v-show="orgRegisfund">请输入数字</span>
        </v-flex>
      </v-layout>
      <!--人员规模-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          人员规模(人)
        </v-flex>
        <v-flex xs7>
          <v-select v-bind:items="scaleDropdown"
                    v-model="orgInformation.scale"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择人员规模"
                    single-line
                    no-data-text="无"></v-select>
          <!--<span class="orgName" v-show="orgScale">请选择人员规模</span>-->
        </v-flex>
      </v-layout>
      <!--营业执照-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          营业执照
        </v-flex>
        <v-flex xs7
                v-if="orgInformation.permiturl">
          <div class="imgDiv"
               @mouseenter="showBtnDelPermit=true"
               @mouseleave="showBtnDelPermit=false">
            <img :src="orgInformation.permiturl"
                 width="120"
                 height="120" />
            <div @click="btnDelImgPermit"
                 v-if="showBtnDelPermit">
              <v-avatar class="delImg red"
                        size="18px">
                <img :src="'static/sop/ic_close_white_24px.svg'" />
              </v-avatar>
            </div>
          </div>
        </v-flex>
        <v-flex v-else
                xs7
                @mouseenter="showBtnDelPermit=true"
                @mouseleave="showBtnDelPermit=false">
          <croppa v-model="myCroppaPermit"
                  :accept="'image/png,image/bmp,image/jpg,image/jpeg'"
                  :width="120"
                  :height="120"
                  placeholder="选择照片"
                  :placeholder-font-size="12"
                  :disabled="false"
                  :show-remove-button="showBtnDelPermit"
                  @image-remove="handleImageRemovePermit"
                  @file-choose="handleCroppaFileChoosePermit"></croppa>
          <v-btn class="uploadbtn"
                 v-if="uploadbtnvisiblePermit"
                 @click="uploadCroppedImagePermit">点击上传</v-btn>
          <div class="errorstips"
               v-if="uploaderrortipsPermit.flag">{{ uploaderrortipsPermit.content }}</div>
        </v-flex>
      </v-layout>
      <!--注册地址-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          注册地址
        </v-flex>
        <v-flex xs7>
          <v-layout>
            <v-flex xs2>
              <v-select :items="provinces"
                        v-model="province"
                        item-text="name"
                        item-value="code"
                        single-line
                        label="省"></v-select>
            </v-flex>
            <v-flex xs2
                    style="margin-left: 15px">
              <v-select :items="cities"
                        v-model="city"
                        item-text="name"
                        item-value="code"
                        single-line
                        label="市"></v-select>
            </v-flex>
            <v-flex xs2
                    style="margin-left: 15px">
              <v-select :items="counties"
                        v-model="county"
                        item-text="name"
                        item-value="code"
                        single-line
                        label="县"></v-select>
            </v-flex>
            <v-flex xs6
                    style="margin-left: 15px">
              <v-text-field placeholder="详细地址"
                            v-model="orgInformation.addressdetail"
                            counter="20"
                            maxlength="20"
                            @focus="handInput(3)"></v-text-field>
              <!-- <span v-show="addressdetailText" class="orgName">字数超出限制范围</span> -->
            </v-flex>
          </v-layout>
          <span v-show="orgAddress"
                class="orgName">请选择注册地址</span>
        </v-flex>
      </v-layout>
      <!--组织简介-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          组织简介
        </v-flex>
        <v-flex xs7>
          <v-textarea type="text"
                      v-model="orgInformation.introduce"
                      rows="3"
                      no-resize
                      label="请输入组织简介"
                      counter="2000"
                      maxlength="2000"
                      @focus="handInput(1)"></v-textarea>
        </v-flex>
      </v-layout>
      <!--业务范围-->
      <v-layout row>
        <v-flex xs1
                mt-4>
          业务范围
        </v-flex>
        <v-flex xs7>
          <v-textarea type="text"
                      v-model="orgInformation.scope"
                      no-resize
                      rows="2"
                      label="请输入业务范围"
                      counter="100"
                      maxlength="100"
                      @focus="handInput(2)"></v-textarea>
          <!-- <span v-show="scopeText" class="isFund">输入字数超出限制范围</span> -->
        </v-flex>
      </v-layout>
      <v-layout row>
        <v-flex xs1></v-flex>
        <v-flex xs7
                text-xs-left>
          <v-btn @click="colse">取消</v-btn>
          <v-btn @click="save(orgInformation.id, orgInformation.name, orgInformation)"
                 color="primary">保存</v-btn>
        </v-flex>
      </v-layout>
    </v-card>
  </v-container>
</template>

<script>
// import {
//   organizationInfo,
//   organizationNature,
//   organizationAddress,
//   orgAddressDropdown,
//   organizationEditInfo
// } from '@/apis/systems/organization'   // 查询组织信息
import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'
export default {
  data () {
    return {
      viewTitle: null,
      loginorgid: null,
      isFund: false,
      legal: false,
      orgName: false,
      menu: false,
      introduceText: false,
      scopeText: false,
      isLogo: false,
      oneLogo: [], // 组织logo第一次选择的图片
      orgNature: false,
      orgLegal: false,
      orgEastablishtime: false,
      orgRegisfund: false,
      orgScale: false,
      orgAddress: false,
      addressdetailText: false,
      myCroppa: {},
      uploadbtnvisible: false,
      myCroppaPermit: {},
      uploadbtnvisiblePermit: false,
      uploaderrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      uploaderrortipsPermit: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      logoErr: {
        state: null,
        message: null
      },
      permitErr: {
        state: null,
        message: null
      },
      // 只允许选择今天之前的日期
      date: null,
      /*
      allowedDates: null,
      lastFiveDays: {
        min: null,
        max: null
      },
      */
      orgInformation: {
        addressProvince: '',
        addressCity: '',
        addressCounty: '',
        id: null, // 主键id
        name: null, // 组织名称
        nature: null, // 组织性质
        legal: null, // 组织法人
        eastablishtime: null, // 成立时间
        logourl: null, // 企业logo图片地址
        regisfund: null, // 注册资金
        scale: null, // 人员规模
        addressdetail: null, // 详细地址
        introduce: null, // 简介
        scope: null, // 业务范围
        permiturl: null, // 营业执照图片地址
        addressCode: null // 行政地址最下级的code
      },
      natureDropdown: [], // 组织性质下拉选项
      scaleDropdown: [], // 人员规模下拉选项
      dicttypeNature: 'org-nature', // 字典类型--组织性质
      dicttype11Scale: 'org-scale', // 字典类型--人员规模
      provinces: [],
      province: 0,
      cities: [],
      county: 0,
      counties: [],
      city: 0,
      towns: [],
      town: 0,
      villages: [],
      village: 0,
      // 图片上传
      dropzoneOptions: {
        url: '/fwk-service-archives/common/fileservice/upload',
        // parallelUploads: 1, // 有对少文件将上载并行处理
        maxFilesize: 0.2, // 大小限制，单位为MB
        // clickable: '#changLogo',
        acceptedFiles: '.JPG, .PNG, .BMP', // 允许上传的文件类型
        // maxFile: 1, // 一次性上传的文件数量上限
        dictDefaultMessage: '拖动图片至此或点击选择图片', // 获取显示之前的任何文件被删除的消息
        dictInvalidFileType: '请上传JPG、PNG、BMP类型的图片',
        paramName: 'orgLogo', // 上传的FILE名称，即服务端可以通过此来获取上传的图片
        dictFileTooBig: '上传的图片不能大于200K',
        addRemoveLinks: true, // 是否有删除图片的功能
        dictRemoveFile: '取消', // 删除图片的文字
        thumbnailWidth: 200,
        thumbnailHeight: 200,
        maxFiles: 1
        // dictMaxFilesExceeded: '只能上传一张图片'
      },
      showBtnDel: false,
      showBtnDelPermit: false,
      today: parseTime(new Date(), 'yyyy-MM-dd')
    }
  },
  watch: {
    province: function (newVal, oldVal) {
      if (newVal) {
        this.fetchPosition(1, newVal)
        this.orgInformation.addressCode = newVal
        this.orgInformation.addressProvince = newVal
      }
      if (oldVal) {
        this.city = null
        this.cities = []
        this.county = null
        this.counties = []
      }
    },
    city: function (newVal, oldVal) {
      if (newVal) {
        this.fetchPosition(2, newVal)
        this.orgInformation.addressCode = newVal
        this.orgInformation.addressCity = newVal
      }
      if (oldVal) {
        this.county = null
        this.counties = []
      }
    },
    county: function (newVal, oldVal) {
      if (newVal) {
        this.orgInformation.addressCode = newVal
        this.orgInformation.addressCounty = newVal
        this.orgAddress = false
      } else {
        this.orgAddress = true
      }
    },
    // 组织名称
    'orgInformation.name': function (newVal, oldVal) {
      if (newVal) {
        this.orgName = false
      } else {
        this.orgName = true
      }
    },
    // 组织性质
    /*
    'orgInformation.nature': function (newVal, oldVal) {
      if (newVal) {
        this.orgNature = false
      } else {
        this.orgNature = true
      }
    },
    */
    // 组织法人
    'orgInformation.legal': function (newVal, oldVal) {
      if (newVal) {
        this.orgLegal = false
        let regLegal = /^[a-zA-Z\u4e00-\u9fa5]+$/
        if (regLegal.test(newVal)) {
          this.legal = true
        } else {
          this.legal = false
        }
      }
      /* else {
        this.orgLegal = true
      } */
    },
    // 注册时间
    /*
   'orgInformation.eastablishtime': function (newVal, oldVal) {
     if (newVal) {
       this.orgEastablishtime = false
     } else {
       this.orgEastablishtime = true
     }
   }, */
    // 注册资金
    'orgInformation.regisfund': function (newVal, oldVal) {
      if (newVal) {
        let reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/
        if (!reg.test(newVal)) {
          this.orgRegisfund = true
        } else {
          this.orgRegisfund = false
        }
      } /* else {
          this.orgRegisfund = true
        } */
      // if (!reg.test(newVal) && newVal) {
      //   this.isFund = true
      //   // this.regisfund = false
      // } else {
      //   this.isFund = false
      // }
    },
    // 人员规模
    /*
   'orgInformation.scale': function (newVal, oldVal) {
     if (newVal) {
       this.orgScale = false
     } else {
       this.orgScale = true
     }
   }, */
    // 详细地址
    'orgInformation.addressdetail': function (val, oldVal) {
      if (oldVal) {
        if (this.orgInformation.addressdetail.length >= 20) {
          this.addressdetailText = true
        } else {
          this.addressdetailText = false
        }
      }
    },
    // 组织简介
    'orgInformation.introduce': function (val, oldVal) {
      if (oldVal) {
        if (this.orgInformation.introduce.length >= 2000) {
          this.introduceText = true
        } else {
          this.introduceText = false
        }
      }
    },
    // 业务范围
    'orgInformation.scope': function (val, oldVal) {
      if (oldVal) {
        if (this.orgInformation.scope.length >= 100) {
          this.scopeText = true
        } else {
          this.scopeText = false
        }
      }
    }
  },
  created () {
    this.viewTitle = '编辑组织机构'
    this.$validator.reset()
    this.fetchData()
  },
  mounted () {
    /*
    const date = new Date()
    const year = date.getFullYear()
    const oldDate = new Date(year - 1000, 1, 1)
    this.lastFiveDays.min = oldDate.toISOString()
    this.lastFiveDays.max = date.toISOString()
    this.allowedDates = this.lastFiveDays
    */
  },
  methods: {
    eastablishtimeLimit (val) {
      if (this.orgInformation.eastablishtime) {
        return val <= this.today
      } else {
        return true
      }
    },
    fetchData () {
      // 获取从organization传过来的当前登录用户的组织编号、组织名称、主键id·
      this.loginorgid = global.helper.ls.get('user').loginorgid
      this.orgInformation.name = global.helper.ls.get('user').name
      this.orgInformation.id = global.helper.ls.get('user').id
      // 发送请求，查询登录用户的组织信息
      excuteApis(Object.assign({}, { loginorgid: this.loginorgid }), global.constant.userApisUrls, 'org', 'queryOrgByOperateid').then(data => {
        // organizationInfo(this.loginorgid).then(data => {
        let res = data.data
        if (res.flag === 1) { // 如果请求成功
          this.orgInformation = res.data[0]
          this.reversalFetchPosition(this.orgInformation.addressCode)
          // 查出所有省份
          this.fetchPosition(0, 0)
          // 发送请求，查询组织性质 dicttype: dicttype,
          excuteApis(Object.assign({}, { dicttype: this.dicttypeNature, dictvalue: this.nature }), global.constant.commonApis, 'common', 'querydictlist').then(data => {
            // organizationNature(this.dicttypeNature, this.nature).then(data => {
            let resNature = data.data
            if (resNature.flag === 1) {
              this.natureDropdown = resNature.data
            } else {
              alert(resNature.message)
            }
          })
          // 发送请求，查询组织人员规模
          excuteApis(Object.assign({}, { dicttype: this.dicttype11Scale, dictvalue: this.scale }), global.constant.commonApis, 'common', 'querydictlist').then(data => {
            // organizationNature(this.dicttype11Scale, this.scale).then(data => {
            let resScale = data.data
            if (resScale.flag === 1) {
              this.scaleDropdown = resScale.data
            }
          })
        } else {
          alert(res.message)
        }
      }).finally(() => {
      })
    },
    // 获取上级code查询下一级地址(上级查下级)
    fetchPosition (level, parentcode) {
      let position = []
      excuteApis(Object.assign({}, { parentCode: parentcode }), global.constant.archivesApis, 'position', 'query').then(data => {
        // orgAddressDropdown(parentcode).then(data => {
        let res = data.data
        if (res.flag === 1) {
          position = res.data
          switch (level) {
            case 0: // 省
              this.provinces = position
              break
            case 1: // 市
              this.cities = position
              break
            case 2: // 县
              this.counties = position
              break
            default:
          }
        }
      })
    },
    // 从后台获取行政地址作为默认值渲染到页面
    reversalFetchPosition (code) {
      excuteApis(Object.assign({}, { code: code }), global.constant.archivesApis, 'common', 'reversalQueryPosition').then(data => {
        // organizationAddress(code).then(data => {
        let res = data.data
        if (res.flag === 1) {
          let positions = res.data
          let level = positions.length
          switch (level) {
            case 1: // 省
              this.provinces = positions[0]
              break
            case 2: // 市
              this.provinces = positions[0]
              this.cities = positions[1]
              break
            case 3: // 县
              this.provinces = positions[0]
              this.cities = positions[1]
              this.counties = positions[2]
              break
            case 4: // 镇
              this.provinces = positions[0]
              this.cities = positions[1]
              this.counties = positions[2]
              this.towns = positions[3]
              break
            case 5: // 村
              this.provinces = positions[0]
              this.cities = positions[1]
              this.counties = positions[2]
              this.towns = positions[3]
              this.villages = positions[4]
              break
            default:
          }

          this.findPosition(level, code, positions)
        }
      })
    },
    findPosition (level, code, positions) {
      let middleCode = code
      for (let i = level; i > 0; i--) {
        let currentPosition = this._.find(positions[i - 1], ['code', middleCode])
        let selectedPosition = currentPosition.code
        middleCode = currentPosition.parentcode
        switch (i) {
          case 1:
            this.province = selectedPosition
            break
          case 2:
            this.city = selectedPosition
            break
          case 3:
            this.county = selectedPosition
            break
          default:
        }
      }
    },
    // 选择组织logo
    addLogo (file) {
      let logoLengthOK = this.$refs.MyDropzone.getAcceptedFiles().length
      let logoLengthNO = this.$refs.MyDropzone.getRejectedFiles().length
      if (logoLengthOK > 0) {
        this.$refs.MyDropzone.removeFile(this.$refs.MyDropzone.getAcceptedFiles()[0])
      }
      if (logoLengthNO > 1) {
        this.$refs.MyDropzone.removeFile(this.$refs.MyDropzone.getRejectedFiles()[0])
      }
    },
    /* logo图片上传成功 */
    logoSuccess (file, response) {
      if (response.flag === 1 && response.data && response.data[0]) {
        this.orgInformation.logourl = response.data[0].resourcepath
        this.logoErr.state = null
      }
    },
    /* logo图片上传失败 */
    logoError (file, error, xhr) {
      this.logoErr.state = 1
      if (xhr) {
        this.logoErr.message = '组织logo上传失败，请检查服务是否异常！'
      } else {
        this.logoErr.message = error
      }
    },
    // 选择营业执照
    addPermit (file) {
      let permitLengthOK = this.$refs.permitDropzone.getAcceptedFiles().length
      let permitLengthNO = this.$refs.permitDropzone.getRejectedFiles().length
      if (permitLengthOK > 0) {
        this.$refs.permitDropzone.removeFile(this.$refs.permitDropzone.getAcceptedFiles()[0])
      }
      if (permitLengthNO > 1) {
        this.$refs.permitDropzone.removeFile(this.$refs.permitDropzone.getRejectedFiles()[0])
      }
    },
    /* permit图片上传成功 */
    permitSuccess (file, response) {
      if (response.flag === 1 && response.data && response.data[0]) {
        this.orgInformation.permiturl = response.data[0].resourcepath
        this.permitErr.state = null
      }
    },
    /* permit图片上传失败 */
    permitError (file, error, xhr) {
      this.permitErr.state = 1
      if (xhr) {
        this.permitErr.message = '营业执照上传失败，请检查服务是否异常！'
      } else {
        this.permitErr.message = error
      }
    },
    // 组织介绍、业务范围输入超出范围提示
    handInput (event) {
      if (event === 1) {
        let length = this.orgInformation.introduce.length
        if (length >= 2000) {
          this.introduceText = true
        } else {
          this.introduceText = false
        }
      } else if (event === 2) {
        let length = this.orgInformation.scope.length
        if (length >= 100) {
          this.scopeText = true
        } else {
          this.scopeText = false
        }
      } else if (event === 3) {
        let length = this.orgInformation.addressdetail.length
        if (length >= 20) {
          this.addressdetailText = true
        } else {
          this.addressdetailText = false
        }
      }
    },
    // 修改信息后保存修改信息发起的请求
    save (id, name, orgInformation) {
      // 获取选择的行政区域的最后一级的code
      delete this.orgInformation['creattime']
      delete this.orgInformation['edittime']
      // if (this.county) { // 如果第三级有值，则addressCode为第三级的code
      //   this.orgInformation.addressCode = this.county
      // } else { // 如果第三级没有值，则addressCode为第二级的code
      //   this.orgInformation.addressCode = this.city
      // }
      if (!this.orgInformation.name) {
        this.orgName = true
        /* else if (!this.orgInformation.nature) {
          this.orgNature = true
        } else if (!this.orgInformation.legal) {
          this.orgLegal = true
        } else if (!this.orgInformation.eastablishtime) {
          this.orgEastablishtime = true
        } else if (this.logoErr.state === 1) {
          this.$toast.top(this.logoErr.message)
          let logoLength = this.$refs.MyDropzone.getRejectedFiles().length
          if (logoLength > 0) {
            this.$refs.MyDropzone.removeFile(this.$refs.MyDropzone.getRejectedFiles()[0])
          }
        } else if (this.permitErr.state === 1) {
          this.$toast.top(this.permitErr.message)
          let permitLength = this.$refs.permitDropzone.getRejectedFiles().length
          if (permitLength > 0) {
            this.$refs.permitDropzone.removeFile(this.$refs.permitDropzone.getRejectedFiles()[0])
          }
        } else if (!this.orgInformation.logourl) {
          this.$toast.top('请上传组织logo！')
        } else if (!this.orgInformation.permiturl) {
          this.$toast.top('请上传营业执照！')
        } else if (!this.orgInformation.regisfund) {
          this.orgRegisfund = true
        } else if (!this.orgInformation.scale) {
          this.orgScale = true
        } else if (!this.orgInformation.addressProvince || !this.orgInformation.addressCity || !this.orgInformation.addressCounty) {
          this.orgAddress = true
        */
      } else {
        console.log(orgInformation)
        excuteApis(Object.assign({}, orgInformation), global.constant.userApisUrls, 'org', 'improveOrgInfo').then(data => {
          // organizationEditInfo(orgInformation).then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.$router.push({ name: 'editorgsuccess' })
            let user = {}
            user = global.helper.ls.get('user')
            user.orgName = this.orgInformation.name
            console.log(user)
            this.$store.dispatch('cacheUserInfo', user)
          } else {
            this.$toast.top(res.message)
          }
        })
      }
    },
    // 取消
    colse () {
      this.$router.push({ name: 'organizations' })
    },
    // 返回组织信息查看
    back () {
      this.$router.push({ name: 'organizations' })
    },
    btnDelImg () {
      this.orgInformation.logourl = ''
      this.orgInformation.logo = null
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    btnDelImgPermit () {
      this.orgInformation.permiturl = ''
      this.orgInformation.permit = null
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    produceBase64ToUpload (fileDataUrl) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.orgInformation.logourl = res.data[0].resourcepath
          this.uploaderrortips.content = '封面上传成功'
          this.uploadbtnvisible = false
        } else {
          this.uploaderrortips.content = res.message
        }
      })
    },
    produceBase64ToUploadPermit (fileDataUrl) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.orgInformation.permiturl = res.data[0].resourcepath
          this.uploaderrortipsPermit.content = '封面上传成功'
          this.uploadbtnvisiblePermit = false
        } else {
          this.uploaderrortipsPermit.content = res.message
        }
      })
    },
    blobToBase64 (blob, callback) {
      var a = new FileReader()
      a.onload = function (e) { callback(e.target.result) }
      a.readAsDataURL(blob)
    },
    uploadCroppedImage () {
      if (!this.myCroppa.hasImage()) {
        return
      }
      // let file = this.myCroppa.getChosenFile()
      this.myCroppa.generateBlob((blob) => {
        // write code to upload the cropped image file (a file is a blob)
        this.blobToBase64(blob, this.produceBase64ToUpload)
      }, 'image/jpeg', 0.8) // 80% compressed jpeg file
    },
    uploadCroppedImagePermit () {
      if (!this.myCroppaPermit.hasImage()) {
        return
      }
      // let file = this.myCroppa.getChosenFile()
      this.myCroppaPermit.generateBlob((blob) => {
        // write code to upload the cropped image file (a file is a blob)
        this.blobToBase64(blob, this.produceBase64ToUploadPermit)
      }, 'image/jpeg', 0.8) // 80% compressed jpeg file
    },
    handleImageRemove () {
      this.orgInformation.logourl = ''
      this.orgInformation.logo = null
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    handleImageRemovePermit () {
      this.orgInformation.permiturl = ''
      this.orgInformation.permit = null
      this.uploaderrortipsPermit.content = '支持JPG、PNG、BMP格式'
    },
    handleCroppaFileChoose (file) {
      let filename = file.name
      filename = filename.toLocaleUpperCase()
      let extname = filename.substring(filename.lastIndexOf('.') + 1, filename.length)
      if (extname === 'PNG' || extname === 'JPG' || extname === 'BMP') {
        this.uploadbtnvisible = true
      } else {
        this.uploaderrortips.content = '请选择JPG、PNG、BMP格式文件'
      }
    },
    handleCroppaFileChoosePermit (file) {
      let filename = file.name
      filename = filename.toLocaleUpperCase()
      let extname = filename.substring(filename.lastIndexOf('.') + 1, filename.length)
      if (extname === 'PNG' || extname === 'JPG' || extname === 'BMP') {
        this.uploadbtnvisiblePermit = true
      } else {
        this.uploaderrortipsPermit.content = '请选择JPG、PNG、BMP格式文件'
      }
    }
  }
}
</script>
<style scoped>
.orgList {
  margin: 20px auto;
}
.orgLogo {
  border: 1px solid #000;
  max-width: 300px;
  max-height: 300px;
  padding: 0;
  text-align: center;
}
.isFund,
.legal,
.orgName {
  color: red;
  display: flex;
  margin-top: -20px;
}
.orgImage {
  width: 240px;
  height: 240px;
  display: inline-block;
  /*margin-top: 20px*/
}
#dropzone,
#permitDropzone {
  float: left;
  margin-left: 50px;
  min-width: 300px;
  height: 240px;
  padding: 0;
}
#orgAlign {
  margin-top: 0.9rem;
}
.imgDiv {
  display: inline-block;
  position: relative;
}
.imgDiv .delImg {
  position: absolute;
  top: 0px;
  right: 0px;
  cursor: pointer;
}
</style>

