<template>
  <v-card class="page-content pt-0">
    <v-form v-model="valid"
            ref="form"
            lazy-validation
            autocomplete="off">
      <!-- 姓名 -->
      <v-layout row
                nowrap>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*农户姓名</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field label="请输入姓名(20字以内)"
                        name="name"
                        single-line
                        v-model="peasant.name"
                        :rules="rules.name"
                        maxlength="20"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 照片 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>农户照片</v-subheader>
        </v-flex>
        <!--<v-flex xs9 sm10 md5 lg5 xl5 v-if="peasant.photoidurl">-->
        <!--<div class="imgDiv"  @mouseenter="showBtnDel=true" @mouseleave="showBtnDel=false">-->
        <!--<img :src="peasant.photoidurl" width="120" height="120" />-->
        <!--<div @click="btnDelImg" v-if="showBtnDel">-->
        <!--<v-avatar class="delImg red" size="18px">-->
        <!--<img :src="`static/sop/ic_close_white_24px.svg`"/>-->
        <!--</v-avatar>-->
        <!--</div>-->
        <!--</div>-->
        <!--</v-flex>-->
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <span class="photoimg"
                v-if="peasant.photoidurl">
            <img :src="peasant.photoidurl"
                 width="120"
                 height="120" />
          </span>
          <span>
            <!--<v-btn @click="showImageCroppa = true">点击选择头像</v-btn>-->
            <label class="btn btn--flat primary--text selectImgbtn"
                   for="uploads">点击选择图片</label>
            <input type="text"
                   id="uploads"
                   style="position:absolute; clip:rect(0 0 0 0);"
                   accept="image/png, image/jpeg, image/gif, image/jpg"
                   @click="showImageCroppa = true">
          </span>
        </v-flex>
        <!--<v-flex v-else xs9 sm10 md5 lg5 xl5 @mouseenter="showBtnDel=true" @mouseleave="showBtnDel=false">-->
        <!--<croppa-->
        <!--v-model="myCroppa"-->
        <!--:accept="'image/png,image/bmp,image/jpg,image/jpeg'"-->
        <!--:width="120"-->
        <!--:height="120"-->
        <!--placeholder="选择照片"-->
        <!--:placeholder-font-size="14"-->
        <!--:remove-button-size="22"-->
        <!--:disabled="false"-->
        <!--:show-remove-button="showBtnDel"-->
        <!--@image-remove="handleImageRemove"-->
        <!--@file-choose="handleCroppaFileChoose"-->
        <!--&gt;</croppa>-->
        <!--<v-btn class="uploadbtn" v-if="uploadbtnvisible" @click="uploadCroppedImage">点击上传</v-btn>-->
        <!--<div class="errorstips" v-if="uploaderrortips.flag">{{ uploaderrortips.content }}</div>-->
        <!--</v-flex>-->
      </v-layout>
      <!-- 电话 type="number" ?? -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*联系电话</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field label="请输入电话(手机号)"
                        name="linkphone"
                        single-line
                        v-model="peasant.linkphone"
                        :rules="rules.linkphone"
                        maxlength="11"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 地址 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*联系地址</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-areaselect :curAddressCode.sync="curAreaCode"
                        :level="5"
                        :isHideDetails="false"></v-areaselect>
        </v-flex>
      </v-layout>

      <!-- 详细地址 -->
      <v-layout row>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5
                offset-xs4
                offset-sm4
                offset-md2
                offset-lg2
                offset-xl1>
          <v-text-field label="详细地址"
                        name="addressdetail"
                        single-line
                        v-model="peasant.addressdetail"
                        counter="20"
                        :rules="rules.addresscode"
                        maxlength="20"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 性别 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*农户性别</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-select v-bind:items="sexs"
                    v-model="peasant.sex"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择性别"
                    single-line
                    no-data-text="无"
                    :rules="rules.required">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 是否贫困户 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>是否贫困户</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-select v-bind:items="povertys"
                    v-model="peasant.ispoverty"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择是否贫困"
                    single-line
                    no-data-text="无">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 身份证号 type="number" ?? -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*身份证号</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field label="请输入身份证号"
                        maxlength="18"
                        name="identity"
                        single-line
                        v-focus
                        v-model="peasant.identity"
                        :rules="rules.identity"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 出生日期 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*出生日期</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field v-model="peasant.birthday"
                        :disabled="true"></v-text-field>
          <!--<v-menu
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
            v-model="peasant.birthday"
            append-icon="event"
            readonly
            :rules = "rules.birthday"
          ></v-text-field>
          <v-date-picker v-model="peasant.birthday" no-title scrollable locale="zh-CN" actions>
          </v-date-picker>
        </v-menu>-->
        </v-flex>
      </v-layout>
      <!-- 年龄(岁) -->
      <!-- <v-layout row>
      <v-flex xs3 sm2 md1 lg1 xl1>
        <v-subheader>年龄(岁):</v-subheader>
      </v-flex>
      <v-flex xs9 sm10 md5 lg5 xl5>
        <v-text-field
          label="请输入年龄"
          name="age"
          single-line
          v-model="peasant.age"
          :rules = "[rules.required]"
        ></v-text-field>
      </v-flex>
    </v-layout>  -->
      <!-- 文化程度 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*文化程度</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-select v-bind:items="cultures"
                    v-model="peasant.culture"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择文化程度"
                    single-line
                    no-data-text="无"
                    :rules="rules.required">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 责任部门 -->
      <!-- <v-layout row>
      <v-flex xs3 sm2 md1 lg1 xl1>
        <v-subheader>责任部门</v-subheader>
      </v-flex>
      <v-flex xs9 sm10 md5 lg5 xl5>
        <v-select
          label="请选择责任部门"
          v-model="peasant.orgid"
          :items="orgs"
          item-text="orgname"
          item-value="orgid"
          single-line
        >
        </v-select>
      </v-flex>
    </v-layout> -->

      <!-- 当前合作状态 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*合作状态</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-select v-bind:items="cooperstates"
                    v-model="peasant.cooperstate"
                    item-text="dictname"
                    item-value="dictvalue"
                    label="请选择当前合作状态"
                    single-line
                    no-data-text="无"
                    :rules="rules.required">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 其他说明 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>其他说明</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-textarea name="explain"
                      v-model="peasant.explain"
                      counter="500"
                      maxlength="500"
                      :rules="rules.explain"></v-textarea>
        </v-flex>
      </v-layout>

      <!-- 取消 保存 -->
      <v-layout row>
        <v-flex xs12
                sm12
                md12
                lg7
                xl6
                class="text-xs-center">
          <v-btn @click="cancel">取消</v-btn>
          <v-btn @click="save"
                 :color="theme"
                 dark>保存</v-btn>
        </v-flex>
      </v-layout>
    </v-form>
    <v-imagecroppedupload :showme.sync="showImageCroppa"
                          :img.sync="peasant.photoidurl"></v-imagecroppedupload>
  </v-card>
</template>

<script>
import { excuteApis } from '@/apis'
import { validPersonID, validLinkPhone } from '@/utils'
import { Toast } from '@/models/toast'
export default {
  data () {
    return {
      showImageCroppa: false,
      theme: 'primary',
      valid: true,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        text: [
          (v) => v.length <= 50 || '长度不超过50个汉字'
        ],
        name: [
          (v) => !!v || '必填项',
          (v) => !!v || '长度不超过50个字符'
        ],
        linkphone: [
          (v) => !!v || '必填项',
          (v) => v.length === 11 || '手机号为11位',
          (v) => validLinkPhone(v) || '手机号输入有误'
        ],
        addresscode: [
          (v) => v.length <= 20 || '长度不超过20个汉字'
        ],
        identity: [
          (v) => !!v || '必填项',
          (v) => v.length === 18 || '身份证号码为18位',
          (v) => validPersonID(v) || '身份证号码非法'
        ],
        birthday: [
          (v) => !!v || '必填项',
          (v) => ((new Date(v.replace(/-/g, '/'))) <= (new Date())) || '出生日期非法'
        ],
        explain: [
          (v) => v.length <= 500 || '长度不超过500个汉字'
        ]
      },
      peasant: {
        name: '',
        photoidurl: '',
        linkphone: '',
        addresscode: '',
        addressdetail: '',
        sex: '',
        ispoverty: '',
        identity: '',
        birthday: '',
        culture: '',
        cooperstate: '',
        explain: ''
      },
      provinces: [],
      province: 0,
      cities: [],
      city: 0,
      counties: [],
      county: 0,
      towns: [],
      town: 0,
      villages: [],
      village: 0,
      sexs: [],
      povertys: [],
      cultures: [],
      cooperstates: [],
      orgs: [{ orgid: 1, orgname: '部门1' }, { orgid: 2, orgname: '部门2' }],
      selectdate: null,
      date: null,
      dateFormatted: null,
      menu: false,
      myCroppa: {},
      uploadbtnvisible: false,
      uploaderrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      showBtnDel: false,
      curAreaCode: '100000000000100000000000'
    }
  },
  watch: {
    // province: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(1, val)
    //     this.city = null
    //     this.cities = []
    //     this.county = null
    //     this.counties = []
    //     this.town = null
    //     this.towns = []
    //     this.village = null
    //     this.villages = []
    //     this.peasant.addresscode = val
    //   }
    // },
    // city: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(2, val)
    //     this.county = null
    //     this.counties = []
    //     this.town = null
    //     this.towns = []
    //     this.village = null
    //     this.villages = []
    //     this.peasant.addresscode = val
    //   }
    // },
    // county: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(3, val)
    //     this.town = null
    //     this.towns = []
    //     this.village = null
    //     this.villages = []
    //     this.peasant.addresscode = val
    //   }
    // },
    // town: function (val, oldVal) {
    //   if (val) {
    //     this.fetchPosition(4, val)
    //     this.village = null
    //     this.villages = []
    //     this.peasant.addresscode = val
    //   }
    // },
    // village: function (val, oldVal) {
    //   this.peasant.addresscode = val
    // },
    /**
     * 监听身份证号码获取出生日期
     */
    idCard (val) {
      if (val.length === 15 || val.length === 18) {
        let birthday = this.getBirthdayFromIdCard(val)
        this.peasant.birthday = birthday
      } else {
        this.peasant.birthday = null
      }
    },
    'peasant.photoidurl' (v, o) {
      console.log(v)
    }
  },
  computed: {
    /**
     * 身份证号
     */
    idCard () {
      return this.peasant.identity
    }
  },
  directives: {
    focus: {
      // 指令的定义
      inserted: function (el) {
        el.focus()
      },
      bind: function () {
        console.log('指令绑到元素上时调用')
      }
    },
    number: {
      bind: function () {
        this.handler = function () {
          this.el.value = this.el.value.replace(/\D+/, '')
        }.bind(this)
        this.el.addEventListener('input', this.handler)
      },
      unbind: function () {
        this.el.removeEventListener('input', this.handler)
      }
    }
  },
  created () {
    this.src = '../static/sop/face.png'
    this.toast = new Toast(this, '这是提示信息内容', 'top')
    this.theme = global.helper.ls.get('theme')
    // this.fetchPosition(0, 0)
    this.fetchUserSex()
    this.fetchPeasantPoverty()
    this.fetchPeasantCulture()
    this.fetchPeasantCooperState()
  },
  methods: {
    fetchPosition (level, parentCode) {
      let position = []
      let requestParams = { 'parentCode': parentCode }
      excuteApis(requestParams, global.constant.archivesApis, 'position', 'query').then(data => {
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
            case 3: // 镇
              this.towns = position
              break
            case 4: // 村
              this.villages = position
              break
            default:
          }
        }
      })
    },
    fetchUserSex () {
      let requestParams = {
        dicttype: 'user-sex'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.sexs = res.data
        }
      })
    },
    fetchPeasantPoverty () {
      let requestParams = {
        dicttype: 'peasant-poverty'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.povertys = res.data
        }
      })
    },
    fetchPeasantCulture () {
      let requestParams = {
        dicttype: 'peasant-culture'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.cultures = res.data
        }
      })
    },
    fetchPeasantCooperState () {
      let requestParams = {
        dicttype: 'peasant-cooperstate'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.cooperstates = res.data
        }
      })
    },
    formatDate (date) {
      if (!date) {
        return null
      }
      const [year, month, day] = date.split('-')
      return `${year}-${month}-${day}`
    },
    parseDate (date) {
      if (!date) {
        return null
      }
      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    },
    cancel () {
      this.$router.go(-1)
    },
    /**
     * 保存
     */
    save () {
      if (this.$refs.form.validate()) {
        this.peasant.addresscode = this.curAreaCode
        excuteApis(this.peasant, global.constant.archivesApis, 'peasant', 'add').then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.toast.setContent('农户档案保存成功')
            this.toast.setPosition('top')
            this.toast.showAutoClose()
            this.$router.push({ path: 'successfarmertips', query: { id: res.data.id, opflag: 0 } })
          } else {
            this.toast.setContent('农户档案保存失败')
            this.toast.setPosition('top')
            this.toast.showAutoClose()
          }
        }, () => {
          this.toast.setContent('服务或网络异常，请联系管理员')
          this.toast.setPosition('top')
          this.toast.showAutoClose()
        })
      }
    },
    produceBase64ToUpload (fileDataUrl) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.peasant.photoidurl = res.data[0].resourcepath
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
      this.peasant.photoidurl = ''
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

    /**
     * 通过身份证号码获取出生日期
     */
    getBirthdayFromIdCard (idCard) {
      let birthday = ''
      if (idCard !== null && idCard !== '') {
        if (idCard.length === 15) {
          birthday = '19' + idCard.substr(6, 6)
        } else if (idCard.length === 18) {
          birthday = idCard.substr(6, 8)
        }
        birthday = birthday.replace(/(.{4})(.{2})/, '$1-$2-')
      }
      return birthday
    },
    btnDelImg () {
      this.peasant.photoidurl = ''
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    }
  }
}
</script>

<style scoped>
.selected {
  overflow: hidden; /*超出的部分隐藏起来。*/
  white-space: nowrap; /*不显示的地方用省略号...代替*/
  text-overflow: ellipsis; /* 支持 IE */
}
.uploadbtn {
  top: -15px;
}
.errorstips {
  font-size: 12px;
  /* color: red; */
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
.selectImgbtn {
  cursor: pointer;
}
</style>
