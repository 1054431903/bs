<template>
  <v-card class="page-content">
    <v-layout style="border-bottom: 2px solid #DDDDDD;margin-left: 20px">
      <v-flex style="margin-top: 25px;margin-bottom: 25px">
        <span @click="cancelBack"
              style="cursor:pointer;">
          <v-icon dark
                  left
                  color="primary">keyboard_backspace</v-icon>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编辑个人资料
        </span>
      </v-flex>
    </v-layout>
    <form ref="form">
      <v-layout row>
        <v-flex xs12>&nbsp;</v-flex>
      </v-layout>
      <!-- 头像 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">头像照片</v-subheader>
        </v-flex>
        <v-flex xs2
                v-if="user.photoURL">
          <div class="imgDiv"
               @mouseenter="showBtnDel=true"
               @mouseleave="showBtnDel=false">
            <img :src="user.photoURL"
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
        <v-flex xs2
                v-else
                @mouseenter="showBtnDel=true"
                @mouseleave="showBtnDel=false">
          <croppa v-model="myCroppa"
                  :accept="'image/png,image/bmp,image/jpg,image/jpeg'"
                  :width="200"
                  :height="200"
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

      <!-- 姓名 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">用户名:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-subheader>{{user.account}}</v-subheader>
        </v-flex>
      </v-layout>

      <!-- 手机 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">手机:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-subheader>{{user.phone}}</v-subheader>
        </v-flex>
      </v-layout>
      <!-- 电子邮箱 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">电子邮箱:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-subheader>{{user.email}}</v-subheader>
        </v-flex>
      </v-layout>
      <!-- 昵称 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">昵称:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-text-field label="请输入昵称"
                        single-line
                        v-model="user.nickname"
                        :error-messages="errors.collect('nickname')"
                        data-vv-name="nickname"
                        data-vv-as="昵称"
                        :counter="20"
                        maxlength="20"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 性别 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">性别:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-select v-bind:items="sexs"
                    v-model="user.sex"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择性别"
                    single-line
                    no-data-text="无"
                    :menu-props="auto"
                    :error-messages="errors.collect('sex')"
                    data-vv-name="sex"
                    data-vv-as="性别">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 出生日期 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">出生日期:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-customdatepicker pickerLabel="选择出生日期"
                              :selectedDate.sync="user.birthday"
                              :allowedDates="birthdayLimit">
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
              label="请选择出生日期"
              single-line
              v-model="user.birthday"
              append-icon="event"
              readonly
              :error-messages="errors.collect('birthday')"
              data-vv-name="birthday"
              data-vv-as="出生日期"
            ></v-text-field>
            <v-date-picker v-model="user.birthday" :allowed-dates="allowedDates" no-title scrollable actions locale="zh-cn" :first-day-of-week="0">
              <template slot-scope="{ save, cancel }">
                <v-card-actions>
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
      <!-- 所属部门 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">所属部门</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-subheader>{{user.departmentname}}</v-subheader>
        </v-flex>
      </v-layout>

      <!-- 工号 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">工号:</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-subheader>{{user.jobnumber}}</v-subheader>
        </v-flex>
      </v-layout>

      <!-- 地址 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader class="textlen">居住地址:</v-subheader>
        </v-flex>
        <v-flex xs1>
          <v-select v-bind:items="provinces"
                    v-model="province"
                    item-text="name"
                    item-value="code"
                    label="省"
                    single-line
                    no-data-text="无"
                    :menu-props="auto"
                    class="selected"
                    :error-messages="errors.collect('province')"
                    data-vv-name="province"
                    data-vv-as="省">
          </v-select>
        </v-flex>
        <v-flex xs1>
          <v-select v-bind:items="cities"
                    v-model="city"
                    item-text="name"
                    item-value="code"
                    label="市"
                    single-line
                    no-data-text="无"
                    :menu-props="auto"
                    class="selected"
                    :error-messages="errors.collect('city')"
                    data-vv-name="city"
                    data-vv-as="市">
          </v-select>
        </v-flex>
        <v-flex xs1>
          <v-select v-bind:items="counties"
                    v-model="county"
                    item-text="name"
                    item-value="code"
                    label="县(区)"
                    single-line
                    no-data-text="无"
                    :menu-props="auto"
                    class="selected"
                    :error-messages="errors.collect('county')"
                    data-vv-name="county"
                    data-vv-as="县(区)">
          </v-select>
        </v-flex>
        <!-- 详细地址 -->
        <v-flex xs3>
          <v-text-field label="详细地址"
                        single-line
                        v-model="user.addressdetail"
                        counter="20"
                        maxlength="20"
                        :error-messages="errors.collect('addressdetail')"
                        data-vv-name="addressdetail"
                        data-vv-as="详细地址"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 取消 保存 -->
      <v-layout row
                justify-center>
        <v-flex xs2>
          <v-btn @click="cancelBack">取消</v-btn>
        </v-flex>
        <v-flex xs6>
          <v-btn color="primary"
                 @click="save">保存</v-btn>
        </v-flex>
      </v-layout>
    </form>
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
  </v-card>
</template>

<script>
import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'

// import {queryCommonDict, queryPositionByParentCode, reversalQueryPosition} from '@/apis/systems/dictionary'
// import {personalDataEdit} from '@/apis/personalcenter/personal'
export default {
  data () {
    return {
      valid: true,
      user: {
        id: '', // ID
        userid: '',
        loginorgid: '', // ID
        photoURL: '', // 头像
        photoFileName: '', // 头像的文件名
        account: '', // 用户名
        nickname: '', // 昵称
        phone: '', // 手机
        email: '', // 邮箱
        name: '',  // 姓名
        sex: '',   // 性别
        birthday: '', // 出生日期
        addresscode: '',
        addressdetail: '', // 地址
        isdelphotourl: '', // 是否删除了头像
        departmentid: '', // 归属部门
        departmentname: '', // 归属部门
        jobnumber: '' // 工号
      },
      provinces: [],
      province: 0,
      cities: [],
      city: 0,
      counties: [],
      county: 0,
      sexs: [],
      selectdate: null,
      date: null,
      dateFormatted: null,
      menu: false,
      /* allowedDates: null,
      lastFiveDays: {
        min: null,
        max: null
      },
      */
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
      }
      */
      myCroppa: {},
      uploadbtnvisible: false,
      uploaderrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      showBtnDel: false,
      today: parseTime(new Date(), 'yyyy-MM-dd')
    }
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
  watch: {
    province: function (val, oldVal) {
      if (val) {
        this.fetchPosition(1, val)
        this.user.addresscode = val
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
        this.user.addresscode = val
      }
      if (oldVal) {
        this.county = null
        this.counties = []
      }
    },
    county: function (val, oldVal) {
      if (val) {
        this.fetchPosition(3, val)
        this.user.addresscode = val
      }
    }
  },
  computed: {},
  created () {
    // this.user = this.$route.query.user
    // this.globalUser = global.helper.ls.get('user')
    this.setValue(global.helper.ls.get('user'))
    // this.user.birthday = a
    this.fetchUserSex()
    this.reversalFetchPosition(this.user.addresscode)
  },
  methods: {
    birthdayLimit (val) {
      if (this.user.birthday) {
        return val <= this.today
      } else {
        return true
      }
    },
    // 通过上级查下级
    fetchPosition (level, parentCode) {
      let position = []
      excuteApis(Object.assign({}, { parentCode: parentCode }), global.constant.archivesApis, 'position', 'query').then(data => {
        // queryPositionByParentCode(parentCode).then(data => {
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
    // 通过下级查上级
    reversalFetchPosition (code) {
      if (code) {
        excuteApis(Object.assign({}, { code: code }), global.constant.archivesApis, 'common', 'reversalQueryPosition').then(data => {
          // reversalQueryPosition(code).then(data => {
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
              default:
            }
            this.findPosition(level, code, positions)
          }
        })
      } else {
        this.fetchPosition(0, 0)
      }
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
    fetchUserSex () {
      excuteApis(Object.assign({}, { dicttype: 'user-sex', delstate: 1 }), global.constant.commonApis, 'common', 'querydictlist').then(data => {
        // queryCommonDict('user-sex').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.sexs = res.data
        }
      })
    },
    // =============================
    // 图片上传的处理 头像
    /*
    photoUploadSuccess (file, response) {
      let res = response
      if (res.flag === 1) {
        this.user.photoURL = res.data[0].resourcepath
        this.user.photoFileName = file.name
      }
    },
    photoFileRemove (file, error, xhr) {
      if (file.name === this.user.photoFileName) {
        this.user.photoURL = ''
        this.user.photoFileName = ''
      }
    },
    */
    produceBase64ToUpload (fileDataUrl) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.user.photoURL = res.data[0].resourcepath
          this.uploaderrortips.content = '封面上传成功'
          this.uploadbtnvisible = false
        } else {
          this.uploaderrortips.content = res.message
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
    handleImageRemove () {
      this.user.photoURL = ''
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
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
    btnDelImg () {
      this.user.photoURL = ''
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    // =============================
    cancel () {
      this.$router.go(-1)
    },
    save () {
      // this.$validator.validateAll(['nickname', 'sex', 'birthday', 'province', 'city', 'county', 'addressdetail']).then((result) => {
      //   if (result) { // 如果表单验证通过
      console.log(this.user)
      // 配合后端修改头像，当删掉头像时，传默认值1
      if (typeof (this.user.photoURL) === 'undefined' || this.user.photoURL === '') {
        this.user.isdelphotourl = '1'
      }
      excuteApis(Object.assign({}, this.user), global.constant.userApisUrls, 'user', 'addOrUpdate').then(data => {
        // personalDataEdit(this.user).then(data => {
        let res = data.data
        if (res.flag === 1) {
          // this.openTipsDialog('success', res.message)
          this.$toast.top(res.message)
          let router = this.$router
          this.user.staffLastUpdateTime = res.data.staffLastUpdateTime
          let editUser = this.user
          this.setCacheUserInfo(editUser)
          setTimeout(function () {
            router.push({ name: 'personalDataEditSuccessTips', query: { user: editUser } })
          }, 2000) // 延迟2 秒
        } else {
          // this.openTipsDialog('red lighten-1', res.message)
          this.$toast.top(res.message)
        }
      })
      //   }
      // }
      // )
    },
    // 赋值操作
    setValue (user) {
      this.user.id = user.id
      this.user.userid = user.id
      this.user.loginorgid = user.loginorgid ? user.loginorgid : ''
      this.user.photoURL = user.photoURL ? user.photoURL : ''
      this.user.account = user.account ? user.account : ''
      this.user.nickname = user.nickname ? user.nickname : ''
      this.user.phone = user.phone ? user.phone : ''
      this.user.email = user.email ? user.email : ''
      this.user.name = user.name ? user.name : ''
      this.user.sex = user.sex ? user.sex : ''
      // this.user.birthday = user.birthday ? user.birthday : ''
      // this.user.birthday = this.formatDateYYYYMMDD(user.birthday)
      // this.$set(this.user, 'birthday', this.formatDateYYYYMMDD(user.birthday))
      this.$nextTick(() => {
        this.$set(this.user, 'birthday', this.formatDateYYYYMMDD(user.birthday))
      })
      this.user.addresscode = user.addresscode ? user.addresscode : ''
      this.user.addressdetail = user.addressdetail ? user.addressdetail : ''
      this.user.departmentid = user.departmentid ? user.departmentid : ''
      this.user.departmentname = user.departmentname ? user.departmentname : ''
      this.user.jobnumber = user.jobnumber ? user.jobnumber : ''
    },
    // 赋值操作
    setCacheUserInfo (editUser) {
      let user = global.helper.ls.get('user')
      user.photoURL = editUser.photoURL
      user.nickname = editUser.nickname
      user.sex = editUser.sex
      user.birthday = editUser.birthday
      user.addresscode = editUser.addresscode
      user.addressdetail = editUser.addressdetail
      if (editUser.staffLastUpdateTime) {
        user.operateredittime = editUser.staffLastUpdateTime
      }
      this.$store.dispatch('cacheUserInfo', user)
    },
    cancelBack () {
      this.$router.push({ name: 'personalCenter', query: { tabIndex: '0' } })
      // this.$router.go(-1)
    },
    formatDateYYYYMMDD (value) {
      if (value) {
        return new Date(value).format('yyyy-MM-dd')
      } else {
        return ''
      }
    }
    /*
    openTipsDialog (color, msg) {
      this.toast.color = color
      this.toast.msg = msg
      this.toast.show = true
    }
     */
  }
}
</script>

<style scoped>
.uploadbtn {
  top: -5px;
  left: -8px;
  width: 120px;
  opacity: 0.5;
}

.selected {
  overflow: hidden; /*超出的部分隐藏起来。*/
  white-space: nowrap; /*不显示的地方用省略号...代替*/
  text-overflow: ellipsis; /* 支持 IE */
}

.errorstips {
  font-size: 12px;
  color: red;
  opacity: 0.8;
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
.textlen {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
