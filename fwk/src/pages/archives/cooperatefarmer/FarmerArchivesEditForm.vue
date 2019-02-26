<template>
  <v-card class="page-content pt-0">
    <v-form v-model="valid"
            ref="form"
            lazy-validation
            autocomplete="off">
      <!-- 姓名 -->
      <v-layout row>
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
        <!--<v-flex v-else xs9 sm10 md5 lg5 xl5 @mouseenter="showBtnDel=true" @mouseleave="showBtnDel=false">-->
        <!--<croppa-->
        <!--v-model="myCroppa"-->
        <!--:accept="'image/png,image/bmp,image/jpg,image/jpeg'"-->
        <!--:width="120"-->
        <!--:height="120"-->
        <!--placeholder="选择照片"-->
        <!--:placeholder-font-size="12"-->
        <!--:disabled="false"-->
        <!--:show-remove-button="showBtnDel"-->
        <!--@image-remove="handleImageRemove"-->
        <!--@file-choose="handleCroppaFileChoose"-->
        <!--&gt;</croppa>-->
        <!--<v-btn class="uploadbtn" v-if="uploadbtnvisible" @click="uploadCroppedImage">点击上传</v-btn>-->
        <!--<div class="errorstips" v-if="uploaderrortips.flag">{{ uploaderrortips.content }}</div>-->
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
      </v-layout>
      <!-- 电话 -->
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
          <v-text-field label="请输入电话"
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
                        :level=5
                        :isReversal="isReversal"
                        @titleChanged="zidiyi($event)"> </v-areaselect>
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
                        maxlength="20"
                        :rules="rules.addressdetail"></v-text-field>
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
                    :menu-props="auto"
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
                    no-data-text="无"
                    :menu-props="auto">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 身份证号 -->
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
                        name="identity"
                        single-line
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
          <!-- <v-menu
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
        </v-menu> -->
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
                    :menu-props="auto"
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
                    :menu-props="auto"
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
                 dark
                 :color="theme">保存</v-btn>
        </v-flex>
      </v-layout>
    </v-form>
    <v-layout row,
              justify-center>
      <v-dialog v-model="prompt.dialog"
                persistent
                max-width="290">
        <v-card>
          <v-card-title class="headline"> {{ prompt.title }} </v-card-title>
          <v-card-text> {{ prompt.body }} </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="prompt.ok"
                   :color="theme"
                   flat
                   @click.native="Ok"> 确定 </v-btn>
            <v-btn flat
                   @click.native.stop="back"> 取消 </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-layout>
    <v-imagecroppedupload :showme.sync="showImageCroppa"
                          :img.sync="peasant.photoidurl"></v-imagecroppedupload>
  </v-card>
</template>

<script>
import { excuteApis } from '@/apis'
import { validPersonID } from '@/utils'

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
          (v) => /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(v) || '手机号输入有误'
        ],
        addressdetail: [
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
        id: '',
        pid: '',
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
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      showBtnDel: false,
      id: null,
      currentaddresscode: '',
      curAreaCode: '',
      isReversal: true
    }
  },
  watch: {
    idCard (val) {
      if (val.length === 15 || val.length === 18) {
        let birthday = this.getBirthdayFromIdCard(val)
        this.peasant.birthday = birthday
      } else {
        this.peasant.birthday = null
      }
    }
  },
  computed: {
    idCard () {
      return this.peasant.identity
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.id = this.$route.query.id
    this.fetchPeasentData()
    this.fetchUserSex()
    this.fetchPeasantPoverty()
    this.fetchPeasantCulture()
    this.fetchPeasantCooperState()
  },
  methods: {
    zidiyi (msg) {
      this.isReversal = msg
    },
    fetchPeasentData () {
      excuteApis(Object.assign({}, { id: this.id }), global.constant.archivesApis, 'peasant', 'queryById').then(
        data => {
          let res = data.data
          if (res.flag === 1) {
            this.peasant = res.data
            if (this.peasant.id) {
              let bd = this.peasant.birthday
              this.peasant.birthday = bd.substring(0, 10)
              this.curAreaCode = this.peasant.addresscode
            }
          } else {
            console.log('合作农户档案查询失败.')
          }
        }
      )
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
    cancel () {
      this.$router.go(-1)
    },
    back () {
      this.prompt.dialog = false
    },
    save () {
      if (this.$refs.form.validate()) {
        excuteApis(Object.assign(this.peasant, { addresscode: this.curAreaCode }), global.constant.archivesApis, 'peasant', 'update').then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.$router.push({ path: 'successfarmertips', query: { id: res.data.id, opflag: 1 } })
          } else {
            this.prompt.dialog = true
            this.prompt.title = '错误提示信息'
            this.prompt.body = res.message
            this.prompt.ok = false
          }
        })
      }
    },
    btnDelImg () {
      this.peasant.photoidurl = ''
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    getBirthdayFromIdCard (idCard) {
      let birthday = ''
      if (idCard !== null && idCard !== '') {
        if (idCard.length === 15) {
          birthday = '19' + idCard.substr(6, 6)
          console.log('------生日-------')
          console.log(birthday)
        } else if (idCard.length === 18) {
          birthday = idCard.substr(6, 8)
        }
        birthday = birthday.replace(/(.{4})(.{2})/, '$1-$2-')
      }
      return birthday
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
