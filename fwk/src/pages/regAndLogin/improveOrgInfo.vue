<template>
  <v-app>
    <v-card pt-0>
      <!-- <div class="py-2"> -->
      <v-container fluid>
        <v-layout style="border-bottom: 2px solid #DDDDDD; margin-top: 0;margin-bottom: 50px">
          <v-flex xs12
                  sm12
                  md12
                  lg12
                  xl12
                  style="padding-bottom: 10px">
            <v-flex>
              <span @click="colse"
                    style="cursor:pointer">
                <v-icon dark
                        left
                        color="primary">arrow_back</v-icon>完善组织信息
              </span>
            </v-flex>
          </v-flex>
        </v-layout>
        <!--组织名称-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>*组织名称</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-text-field type="text"
                          single-line
                          v-model="org.name"
                          label="请输入组织名称"
                          :rules="rules.required"
                          :error-messages="errors.collect('name')"
                          v-validate="'required|min:1'"
                          data-vv-name="name"
                          data-vv-as="组织名称"
                          required>
            </v-text-field>
          </v-flex>
        </v-layout>
        <!--组织性质-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>*组织性质</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-select v-bind:items="natureDropdown"
                      v-model="org.nature"
                      item-text="dictname"
                      item-value="dictvalue"
                      single-line
                      label="请选择组织性质"
                      no-data-text="无"
                      :rules="rules.required"
                      :error-messages="errors.collect('nature')"
                      v-validate="'required'"
                      data-vv-name="nature"
                      data-vv-as="组织性质"
                      required></v-select>
          </v-flex>
        </v-layout>
        <!--组织法人-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>组织法人</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-text-field type="text"
                          counter
                          v-model="org.legal"
                          single-line
                          label="请输入组织法人"></v-text-field>
          </v-flex>
        </v-layout>
        <!--成立时间-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>成立时间</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-customdatepicker pickerLabel="请选择日期"
                                :selectedDate.sync="org.eastablishtime"
                                :allowedDates="eastablishtimeLimit">
            </v-customdatepicker>
            <!--
          <v-menu
            lazy
            :close-on-content-click="false"
            v-model="menu"
            transition="scale-transition"
            offset-y
            full-width
            :nudge-right="40"
            max-width="290px"
            min-width="290px"
          >
            <v-text-field
              slot="activator"
              v-model="org.eastablishtime"
              label="请选择日期"
              single-line
              append-icon="event"
              readonly
            ></v-text-field>
            <v-date-picker :allowed-dates="allowedDates" v-model="org.eastablishtime" no-title scrollable actions locale="zh-cn" :first-day-of-week="0">
              <template slot-scope="{ save, cancel }">
                <v-card-actions >
                  <span>
                    <v-btn style="margin-left: 55px" flat color="primary" @click="cancel">取消</v-btn>
                    <v-btn  flat color="primary" @click="save">确定</v-btn>
                  </span>
                </v-card-actions>
              </template>
            </v-date-picker>
          </v-menu>
          -->
          </v-flex>
        </v-layout>
        <!--组织logo-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>组织logo</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6
                  @mouseenter="showLogoBtnDel=true"
                  @mouseleave="showLogoBtnDel=false">
            <croppa v-model="logoCroppa"
                    :accept="'image/png,image/bmp,image/jpg,image/gif,image/jpeg'"
                    :width="200"
                    :height="200"
                    style="z-index: 0"
                    placeholder="选择照片"
                    :placeholder-font-size="12"
                    :disabled="false"
                    :show-remove-button="showLogoBtnDel"
                    @image-remove="handleImageRemove('logo')"
                    @file-choose="handleLogoCroppaFileChoose"></croppa>
            <v-btn class="uploadbtn"
                   v-if="uploadlogobtnvisible"
                   @click="uploadCroppedImage('logo')">点击上传</v-btn>
            <div class="errorstips"
                 v-if="uploadlogoerrortips.flag">{{uploadlogoerrortips.content }}</div>
          </v-flex>
        </v-layout>
        <!--注册资金-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>注册资金（万元）</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-text-field type="number"
                          onkeyup="value=value.replace(/[^\d]/g,'') "
                          v-model="org.regisfund"
                          single-line
                          label="请输入注册资金，只能输入数字"></v-text-field>
            <span class="orgName"
                  v-show="orgRegisfund">* 请输入数字</span>
          </v-flex>
        </v-layout>
        <!--人员规模-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>人员规模（人）</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-select v-bind:items="scaleDropdown"
                      v-model="org.scale"
                      item-text="dictname"
                      item-value="dictvalue"
                      label="请选择人员规模"
                      single-line
                      no-data-text="无"></v-select>
          </v-flex>
        </v-layout>
        <!--营业执照-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>营业执照</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6
                  @mouseenter="showPermitBtnDel=true"
                  @mouseleave="showPermitBtnDel=false">
            <!--
          <dropzone
            ref="permitDropzone"
            id="permitDropzone"
            :options="dropzoneOptions"
            v-on:vdropzone-success="permitUploadSuccess"
            v-on:vdropzone-removed-file="permitFileRemove"
          >
          </dropzone>-->
            <croppa v-model="permitCroppa"
                    :accept="'image/png,image/bmp,image/jpg,image/gif,image/jpeg'"
                    :width="200"
                    :height="200"
                    placeholder="选择照片"
                    :placeholder-font-size="12"
                    :disabled="false"
                    :show-remove-button="showPermitBtnDel"
                    @image-remove="handleImageRemove('permit')"
                    @file-choose="handlePermitCroppaFileChoose"></croppa>
            <v-btn class="uploadbtn"
                   style="margin-top: 10px"
                   v-if="uploadpermitbtnvisible"
                   @click="uploadCroppedImage('permit')">点击上传</v-btn>
            <div class="errorstips"
                 v-if="uploadpermiterrortips.flag">{{uploadpermiterrortips.content}}</div>
          </v-flex>
        </v-layout>
        <!--注册地址-->

        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>注册地址</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-layout>
              <v-flex xs2
                      sm2
                      md2
                      lg2
                      xl2>
                <v-select :items="provinces"
                          v-model="province"
                          item-text="name"
                          item-value="code"
                          single-line
                          label="省"
                          no-data-text="无"
                          :menu-props="auto"
                          :rules="rules.required"
                          class="selected"></v-select>
              </v-flex>
              <v-flex xs2
                      sm2
                      md2
                      lg2
                      xl2
                      style="margin-left: 15px">
                <v-select :items="cities"
                          v-model="city"
                          item-text="name"
                          item-value="code"
                          single-line
                          label="市"
                          no-data-text="无"
                          :menu-props="auto"
                          :rules="rules.required"
                          class="selected"></v-select>
              </v-flex>
              <v-flex xs2
                      sm2
                      md2
                      lg2
                      xl2
                      style="margin-left: 15px">
                <v-select :items="counties"
                          v-model="county"
                          item-text="name"
                          item-value="code"
                          single-line
                          label="县"
                          no-data-text="无"
                          :menu-props="auto"
                          :rules="rules.required"
                          class="selected"></v-select>
              </v-flex>
              <v-flex xs6
                      sm6
                      md6
                      lg6
                      xl6
                      style="margin-left: 15px">
                <v-text-field label="详细地址"
                              name="addressdetail"
                              single-line
                              v-model="org.addressdetail"
                              counter="20"
                              maxlength="20"></v-text-field>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <!--组织简介-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>组织简介</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-textarea type="text"
                        v-model="org.introduce"
                        single-line
                        label="请输入组织简介"
                        :counter="2000"
                        maxlength="2000">
            </v-textarea>
          </v-flex>
        </v-layout>
        <!--业务范围-->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md2
                  lg2
                  xl2>
            <v-subheader>业务范围</v-subheader>
          </v-flex>
          <v-flex xs12
                  sm12
                  md6
                  lg6
                  xl6>
            <v-textarea type="text"
                        v-model="org.scope"
                        single-line
                        label="请输入业务范围"
                        :counter="100"
                        maxlength="100"></v-textarea>
          </v-flex>
        </v-layout>
        <!--
      <v-snackbar
        :timeout="toast.timeout"
        :color="toast.color"
        :multi-line="toast.mode === 'multi-line'"
        :vertical="toast.mode === 'vertical'"
        v-model="toast.show"
        right
      >
        {{ toast.msg }}
        <v-btn dark flat @click.native="toast.show = false">Close</v-btn>
      </v-snackbar>
      -->
        <v-layout row
                  justify-center>
          <v-flex xs12
                  sm12
                  md12
                  lg12
                  xl12
                  text-xs-center>
            <v-btn @click="colse">取消</v-btn>
            <v-btn color="primary"
                   @click="save()">确认</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
      <!-- </div> -->
    </v-card>
  </v-app>
</template>

<script>
import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'
// import {getDictByTypeAndValue, queryPositionByParentCode, improveOrgInfo} from '@/apis/regAndLogin/regAndLogin'
export default {
  data () {
    return {
      valid: true,
      rules: {
        required: [
          (v) => !!v || '必填项.'
        ]
      },
      globalUser: null, // 用户信息
      menu: false, // 成立时间
      // 只允许选择今天之前的日期
      // date: null,
      /*
      allowedDates: null,
      lastFiveDays: {
        min: null,
        max: null
      },
      */
      orgRegisfund: false,
      org: {
        // id: null, // 主键id
        name: null, // 组织名称
        nature: null, // 组织性质
        legal: null, // 组织法人
        eastablishtime: null, // 成立时间
        logourl: null, // 企业logo图片地址
        logoFileName: null, // 企业logo图片地址
        regisfund: null, // 注册资金
        scale: null, // 人员规模
        addressdetail: null, // 详细地址
        introduce: null, // 简介
        scope: null, // 业务范围
        permiturl: null, // 营业执照图片地址
        permitFileName: null, // 营业执照图片地址
        addresscode: null, // 行政地址最下级的code
        userid: null,
        addressProvince: null,
        addressCity: null,
        addressCounty: null
      },
      natureDropdown: [], // 组织性质下拉选项
      scaleDropdown: [], // 人员规模下拉选项
      dicttypeNature: 'org-nature', // 字典类型--组织性质
      dicttype11Scale: 'org-scale', // 字典类型--人员规模
      provinces: [],
      province: 0,
      cities: [],
      city: 0,
      counties: [],
      county: 0,
      /*
      toast: {
        show: false,
        color: '',
        mode: 'vertical',
        timeout: 6000,
        msg: ''
      },
      */
      // 图片上传的处理
      /*
      dropzoneOptions: {
        url: global.constant.uploadFileUrl,
        thumbnailWidth: 180,
        maxFiles: 1,
        maxFilesize: 0.2,
        addRemoveLinks: true,
        dictDefaultMessage: "<i class='fa fa-cloud-upload'></i>点击选择文件或拖拽文件至此上传",
        dictFileTooBig: '文件不能大于200K', // 文件过大时显示的文字信息
        dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        dictRemoveFile: '移除', // 移除按钮自定义文字
        dictMaxFilesExceeded: '只能上传一张图片'
      },
      */
      logoCroppa: {},
      uploadlogobtnvisible: false,
      uploadlogoerrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      permitCroppa: {},
      uploadpermitbtnvisible: false,
      uploadpermiterrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      showLogoBtnDel: false,
      showPermitBtnDel: false,
      today: parseTime(new Date(), 'yyyy-MM-dd')
    }
  },
  created () {
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
      if (this.org.eastablishtime) {
        return val <= this.today
      } else {
        return true
      }
    },
    fetchData () {
      this.globalUser = global.helper.ls.get('user')
      this.org.userid = this.globalUser.id // 完善谁的组织信息
      // 查出省的数据
      this.fetchPosition(0, 0)
      // 发送请求，查询组织性质
      excuteApis(Object.assign({}, { dicttype: this.dicttypeNature, dictvalue: this.nature }), global.constant.commonApis, 'common', 'querydictlist').then(data => {
        // getDictByTypeAndValue(this.dicttypeNature, this.nature).then(data => {
        let resNature = data.data
        if (resNature.flag === 1) {
          this.natureDropdown = resNature.data
        } else {
          // this.openTipsDialog('red lighten-1', resNature.message)
          this.$toast.top(resNature.message)
        }
      })
      // 发送请求，查询组织人员规模
      excuteApis(Object.assign({}, { dicttype: this.dicttype11Scale, dictvalue: this.scale }), global.constant.commonApis, 'common', 'querydictlist').then(data => {
        // getDictByTypeAndValue(this.dicttype11Scale, this.scale).then(data => {
        let resScale = data.data
        if (resScale.flag === 1) {
          this.scaleDropdown = resScale.data
        }
      })
    },
    // 选择省份---》获取省份code查询下一级地址(上级查下级)
    fetchPosition (level, parentcode) {
      let position = []
      excuteApis(Object.assign({}, { parentCode: parentcode }), global.constant.archivesApis, 'position', 'query').then(data => {
        // queryPositionByParentCode(parentcode).then(data => {
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

    // 修改信息后保存修改信息发起的请求
    save () {
      this.$validator.validateAll(['name', 'nature']).then((result) => {
        if (result) { // 如果表单验证通过
          // 获取选择的行政区域的最后一级的code
          excuteApis(Object.assign({}, this.org), global.constant.userApisUrls, 'org', 'improveOrgInfo').then(data => {
            // improveOrgInfo(this.org).then(data => {
            let res = data.data
            if (res.flag === 1) {
              let user = {}
              user = global.helper.ls.get('user')
              user.loginorgid = res.data.orgid
              user.orgName = this.org.name
              if (res.data.staffLastUpdateTime) {
                user.operateredittime = res.data.staffLastUpdateTime
              }
              this.$store.dispatch('cacheUserInfo', user)
              this.$toast.top(res.message)
              this.$router.push({ name: 'home' })
            } else {
              this.$toast.top(res.message)
            }
          })
        }
      })
    },
    // 取消
    colse () {
      let user = null
      this.$store.commit('setUser', user)
      this.$router.push({ name: 'login' })
    },
    // 图片上传的处理 logo
    /*
    logoUploadSuccess (file, response) {
      let res = response
      if (res.flag === 1) {
        this.org.logourl = res.data[0].resourcepath
        this.org.logoFileName = file.name
      }
    },
    logoFileRemove (file, error, xhr) {
      if (file.name === this.org.logoFileName) {
        this.org.logourl = ''
        this.org.logoFileName = ''
      }
    },
    // 图片上传的处理 营业执照
    permitUploadSuccess (file, response) {
      let res = response
      if (res.flag === 1) {
        this.org.permiturl = res.data[0].resourcepath
        this.org.permitFileName = file.name
      }
    },
    permitFileRemove (file, error, xhr) {
      if (file.name === this.org.permitFileName) {
        this.org.permiturl = ''
        this.org.permitFileName = ''
      }
    },
    */
    produceBase64ToUploadLogo (fileDataUrl, type) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          // 组织的logo
          this.org.logourl = res.data[0].resourcepath
          this.uploadlogoerrortips.content = 'logo上传成功'
          this.uploadlogobtnvisible = false
        } else {
          // 组织的logo
          this.uploadlogoerrortips.content = res.message
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
          // 营业执照
          this.org.permiturl = res.data[0].resourcepath
          this.uploadpermiterrortips.content = '营业执照上传成功'
          this.uploadpermitbtnvisible = false
        } else {
          // 营业执照
          this.uploadpermiterrortips.content = res.message
        }
      })
    },
    blobToBase64 (blob, callback) {
      var a = new FileReader()
      a.onload = function (e) { callback(e.target.result) }
      a.readAsDataURL(blob)
    },

    uploadCroppedImage (type) {
      if (type === 'logo') { // 组织的logo
        if (!this.logoCroppa.hasImage()) {
          return
        }
        this.logoCroppa.generateBlob((blob) => {
          this.blobToBase64(blob, this.produceBase64ToUploadLogo)
        }, 'image/jpeg', 0.8) // 80% compressed jpeg file
      } else if (type === 'permit') { // 营业执照
        if (!this.permitCroppa.hasImage()) {
          return
        }
        this.permitCroppa.generateBlob((blob) => {
          this.blobToBase64(blob, this.produceBase64ToUploadPermit)
        }, 'image/jpeg', 0.8) // 80% compressed jpeg file
      }
    },
    handleImageRemove (type) { // 点击XX按钮
      if (type === 'logo') { // 组织的logo
        this.org.logourl = ''
        this.uploadlogoerrortips.content = '支持JPG、PNG、BMP格式'
      } else if (type === 'permit') { // 营业执照
        this.org.permiturl = ''
        this.uploadpermiterrortips.content = '支持JPG、PNG、BMP格式'
      }
    },
    handleLogoCroppaFileChoose (file) { // 选择文件返回时调用
      let filename = file.name
      filename = filename.toLocaleUpperCase()
      let extname = filename.substring(filename.lastIndexOf('.') + 1, filename.length)
      if (extname === 'PNG' || extname === 'JPG' || extname === 'BMP') {
        this.uploadlogobtnvisible = true
      } else {
        this.uploadlogoerrortips.content = '请选择JPG、PNG、BMP格式文件'
      }
    },
    handlePermitCroppaFileChoose (file) { // 选择文件返回时调用
      let filename = file.name
      filename = filename.toLocaleUpperCase()
      let extname = filename.substring(filename.lastIndexOf('.') + 1, filename.length)
      if (extname === 'PNG' || extname === 'JPG' || extname === 'BMP') {
        this.uploadpermitbtnvisible = true
      } else {
        this.uploadpermiterrortips.content = '请选择JPG、PNG、BMP格式文件'
      }
    }
  },
  watch: {
    province: function (val, oldVal) {
      if (val) {
        this.fetchPosition(1, val)
        this.org.addresscode = val
        this.org.addressProvince = val
      }
      if (oldVal) {
        this.city = null
        this.cities = []
        this.county = null
        this.counties = []
      }
    },
    city: function (val, oldVal) {
      if (val) {
        this.fetchPosition(2, val)
        this.org.addresscode = val
        this.org.addressCity = val
      }
      if (oldVal) {
        this.county = null
        this.counties = []
      }
    },
    county: function (val, oldVal) {
      if (val) {
        this.fetchPosition(3, val)
        this.org.addresscode = val
        this.org.addressCounty = val
      }
    },
    // 注册资金
    'org.regisfund': function (newVal, oldVal) {
      let reg = /^[0-9]+(\.[0-9]{0,})?$/
      if (newVal) {
        this.orgRegisfund = false
      } else {
        this.orgRegisfund = true
      }
      if (!reg.test(newVal) && newVal) {
        this.isFund = true
        // this.regisfund = false
      } else {
        this.isFund = false
      }
    }
  }
}
</script>
<style scoped>
.orgList {
  margin: 20px auto;
}
</style>

