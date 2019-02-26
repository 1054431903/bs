<template>
  <div class="page-content pt-0 pl-0 pr-0 pb-0">
    <v-flex xs12
            sm12
            md12
            lg12
            xl12>
      <v-layout row>
        <v-flex xs12>
          <v-stepper v-model="step">
            <v-stepper-header>
              <v-divider class="ml-4"></v-divider>
              <v-stepper-step step="1">基本信息</v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="2">详细信息</v-stepper-step>
              <v-divider class="mr-4"></v-divider>
            </v-stepper-header>
            <v-stepper-items>
              <v-stepper-content :step="step.no"
                                 v-for="step in steps"
                                 :key="step.no"
                                 class="pt-3">
                <v-card v-if="step.no === 1"
                        flat>
                  <v-form autocomplete="off">
                    <!-- SOP规程名称 -->
                    <v-layout row
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1>
                        <v-subheader>*规程名称</v-subheader>
                      </v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5>
                        <v-text-field single-line
                                      v-model="information.title"
                                      :counter="100"
                                      maxlength="100"
                                      :error-messages="informationTitleErrors"
                                      @change="$v.information.title.$touch()"
                                      @input="$v.information.title.$touch()"></v-text-field>
                      </v-flex>
                    </v-layout>
                    <!-- 品类 -->
                    <v-layout row
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1>
                        <v-subheader>*品类</v-subheader>
                      </v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5>
                        <v-select v-bind:items="producttypes"
                                  v-model="information.categoryid"
                                  item-text="name"
                                  item-value="id"
                                  single-line
                                  no-data-text="无"
                                  :menu-props="auto"
                                  :error-messages="informationProducttypeidErrors"
                                  @change="$v.information.categoryid.$touch()"
                                  @input="$v.information.categoryid.$touch()">
                        </v-select>
                      </v-flex>
                    </v-layout>
                    <!-- 封面 -->
                    <v-layout row
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1>
                        <v-subheader>封面</v-subheader>
                      </v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5
                              text-xs-center
                              v-if="information.resourcepath">
                        <div class="imgDiv"
                             @mouseenter="showBtnDel=true"
                             @mouseleave="showBtnDel=false">
                          <img :src="information.resourcepath"
                               width="200"
                               height="200" />
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
                              text-xs-center
                              xs9
                              sm10
                              md5
                              lg5
                              xl5
                              @mouseenter="showBtnDel=true"
                              @mouseleave="showBtnDel=false">
                        <croppa v-model="croppa"
                                :accept="'image/png,image/bmp,image/jpg,image/jpeg'"
                                :width="200"
                                :height="200"
                                :placeholder="'请选择封面图片'"
                                :placeholder-font-size="12"
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
                  </v-form>
                </v-card>
                <div v-else>
                  <div v-for="(inforDetail, index) in informationDetails"
                       :key="inforDetail.id">
                    <div class="inforDetailShow">
                      <v-layout row
                                wrap>
                        <v-flex xs8>
                          <div class="title detailtitle">{{ inforDetail.name ? inforDetail.name : '--' }}</div>
                        </v-flex>
                        <v-flex xs4
                                text-sm-right>
                          <v-btn title="编辑"
                                 fab
                                 small
                                 flat
                                 @click.native="showAddOrEditForm(inforDetail)">
                            <v-icon>edit</v-icon>
                          </v-btn>
                          <v-btn title="上移"
                                 fab
                                 small
                                 flat
                                 v-if="index !== 0"
                                 @click.native="updatePhaseSort(inforDetail, index, 'up')">
                            <v-icon>arrow_upward</v-icon>
                          </v-btn>
                          <v-btn title="下移"
                                 fab
                                 small
                                 flat
                                 v-if="index !== (informationDetails.length - 1)"
                                 @click.native="updatePhaseSort(inforDetail, index, 'down')">
                            <v-icon>arrow_downward</v-icon>
                          </v-btn>
                          <v-btn title="删除"
                                 fab
                                 small
                                 flat
                                 color="error"
                                 @click.native="deletePhase(inforDetail)">
                            <v-icon>delete</v-icon>
                          </v-btn>
                        </v-flex>
                      </v-layout>
                      <v-layout row
                                wrap>
                        <v-flex xs12
                                class="pr-2">
                          <div v-html="inforDetail.content"
                               class="detailcontent"></div>
                        </v-flex>
                      </v-layout>
                    </div>
                  </div>
                  <div class="inforDetailForm"
                       v-if="editOrAddFormShow">
                    <!-- 阶段名称 -->
                    <v-layout row
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1>
                        <v-subheader>阶段名称</v-subheader>
                      </v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5>
                        <v-text-field single-line
                                      v-model="informationDetail.name"
                                      :counter="20"
                                      maxlength="20"
                                      :error-messages="informationDetailNameErrors"
                                      @change="$v.informationDetail.name.$touch()"
                                      @input="$v.informationDetail.name.$touch()"></v-text-field>
                      </v-flex>
                    </v-layout>
                    <!-- 阶段描述 -->
                    <v-layout row
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1>
                        <v-subheader>阶段描述</v-subheader>
                      </v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5>
                        <quill-editor ref="myTextEditor"
                                      :options="editorOption"
                                      v-model="informationDetail.content"
                                      @blur="onEditorBlur($event)"
                                      @focus="onEditorFocus($event)"
                                      @ready="onEditorReady($event)">
                          <div id="toolbar"
                               slot="toolbar">
                            <select class="ql-size">
                              <option value="small"></option>
                              <option selected></option>
                              <option value="large"></option>
                              <option value="huge"></option>
                            </select>
                            <button class="ql-bold">Bold</button>
                            <button class="ql-italic">Italic</button>
                            <button class="ql-link">Link</button>
                            <!-- <button class="ql-image">image</button>
                            <button class="ql-video">video</button> -->
                            <!-- <button class="ql-indent" value="+1"></button>
                            <button class="ql-indent" value="-1"></button> -->
                            <button class="ql-formats">
                              <select class="ql-align">
                                <option selected="selected"></option>
                                <option value="center"></option>
                                <option value="right"></option>
                                <option value="justify"></option>
                              </select>
                            </button>
                            <button large
                                    flat
                                    id="custom-button"
                                    @click="openUploadForm">
                              <v-icon>file_upload</v-icon>
                            </button>
                          </div>
                        </quill-editor>
                      </v-flex>
                    </v-layout>
                    <v-layout row
                              v-if="!informationDetail.content"
                              justify-center>
                      <v-flex xs3
                              sm2
                              md1
                              lg1
                              xl1></v-flex>
                      <v-flex xs9
                              sm10
                              md5
                              lg5
                              xl5><span style="font-size: 12px;"
                              v-bind:class="{'error--text input-group__details': isError}">请填写阶段描述</span></v-flex>
                    </v-layout>
                    <v-layout row
                              class="pt-2">
                      <v-flex sm12
                              text-xs-center>
                        <v-btn @click="showAddOrEditForm">取消</v-btn>
                        <v-btn :color="theme"
                               dark
                               @click="saveInformationDetial">完成</v-btn>
                      </v-flex>
                    </v-layout>
                  </div>
                  <div class="text-sm-center"
                       v-if="!editOrAddFormShow">
                    <v-btn :color="theme"
                           @click="editOrAddFormShow = true">添加</v-btn>
                  </div>
                  <!-- <div style="margin-bottom: 5px;">&nbsp;&nbsp;</div> -->
                </div>
                <v-divider></v-divider>
                <div class="text-sm-center">
                  <v-btn :to="{name: 'infopublish'}">取消</v-btn>
                  <v-btn v-if="step.no < stepCount"
                         :color="theme"
                         dark
                         @click="nextStep(step.no)">保存并下一步</v-btn>
                  <v-btn :color="theme"
                         dark
                         v-if="step.no > 1"
                         @click="upStep(step.no)">上一步</v-btn>
                  <v-btn color="warning"
                         v-if="informationDetails.length > 0 && step.no === 2 && $authed('PUT','system/infopublish/soppublish')"
                         @click="informationPublish">发布</v-btn>
                </div>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-flex>
      </v-layout>
    </v-flex>
    <v-dialog v-model="fileupload"
              persistent
              max-width="500"
              max-height="500">
      <v-card>
        <v-card-title>上传文件</v-card-title>
        <v-card-text>
          <dropzone ref="fileDropzone"
                    id="dropzone"
                    :options="dropzoneOptions"
                    v-on:vdropzone-success="fileUploadSuccess"
                    v-on:vdropzone-error="sopUploadError"
                    v-on:vdropzone-file-added="fileAdded"
                    v-on:vdropzone-sending="fileSending">
          </dropzone>
          <div><span style="color:red;">{{ uploadresourcelist.length }}</span>&nbsp;&nbsp;个文件上传成功:</div>
          <div v-for="item in uploadresourcelist"
               :key="item.resourcepath">
            {{item.resourcename}}
          </div>
          <div class="text-xs-right">
            <v-btn @click.native="fileupload = false">取消</v-btn>
            <v-btn :color="theme"
                   dark
                   @click.native="uploadfileSave()">确定</v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="isEmpty"
              persistent
              max-width="500"
              max-height="500">
      <v-card>
        <v-card-title>提示信息</v-card-title>
        <v-card-text>
          <div>请完善阶段信息</div>
          <div class="text-xs-center">
            <v-btn :color="theme"
                   dark
                   @click.native="isEmpty=false">确定</v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-dialog v-model="prompt.dialog"
              persistent
              max-width="300"
              max-height="500">
      <v-card>
        <v-card-title class="title">{{prompt.title}}</v-card-title>
        <v-card-text>
          <div>{{prompt.body}}</div>
          <div class="text-xs-center pt-3">
            <v-btn :color="theme"
                   dark
                   @click.native="tipsOk">确定</v-btn>
            <v-btn @click.native.stop="tipsCancel">取消</v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { excuteApis } from '@/apis'
import { validationMixin } from 'vuelidate'
import { required, maxLength } from 'vuelidate/lib/validators'
import { Toast } from '@/models/toast'

export default {
  mixins: [validationMixin],
  validations: {
    information: {
      title: { required, maxLength: maxLength(100) },
      categoryid: { required }
    },
    informationDetail: {
      name: { maxLength: maxLength(20) }
    }
  },
  data () {
    return {
      croppa: {},
      uploadbtnvisible: false,
      uploaderrortips: {
        flag: true,
        content: '支持JPG、PNG、BMP格式'
      },
      step: 1,
      steps: [
        {
          title: '基本信息',
          no: 1
        },
        {
          title: '详细信息',
          no: 2
        }
      ],
      stepCount: 1,
      information: {
        id: null,
        creattime: '',
        title: '',
        categoryid: '',
        pubuserid: '',
        ispub: null,
        pubtime: '',
        resourcepath: '',
        userid: '',
        name: '',
        resourceid: null
      },
      informationDetail: {
        id: '',
        informationid: '',
        name: '',
        sort: '',
        content: '',
        resourcepath: ''
      },
      informationDetails: [
        // {
        //   id: '1',
        //   informationid: '1',
        //   name: '选择品种',
        //   sort: '1',
        //   content: '<p><span style="color: rgb(51, 51, 51);">一般来说，我们种植盆栽苹果都是直接从果场或花卉市场上买回来种植。因此，在选购盆栽苹果的时候，要注意盆栽苹果的品种选择，一般来说现在的盆栽苹果品种主要有寒富、寒香两种。寒富品种的盆栽苹果树具有耐寒、果大、果形好看的特点；寒香品种的盆栽苹果树具有果味香、果皮颜色鲜艳漂亮的特点。</span></p><p><img src="http://139.198.13.100:7010/resource/M00/00/62/wKgAA1p2ZB-ABWnVAAKwjl4Taj8338.jpg" style="display: block; margin: auto; cursor: nwse-resize;" width="301"></p>',
        //   resourcepath: ''
        // },
        // {
        //   id: '2',
        //   informationid: '2',
        //   name: '选择花盆',
        //   sort: '2',
        //   content: '<p><span style="color: rgb(51, 51, 51);">虽然说我们买回来的盆栽苹果已经配好了花盆，但是如何配好的花盆并不适合盆栽苹果树的生长，就应该换一个花盆。用来种植盆栽苹果树的花盆最好选择圆形口径的瓦盆，花盆的口径大小要在40-60厘米左右，盆底要有几个手指大小的小孔，以排水和通气。</span></p><p><img src="http://139.198.13.100:7010/resource/M00/00/62/wKgAA1p2ZI-ARadvAAQdplNCFvo444.jpg" style="display: block; margin: auto; cursor: nwse-resize;" width="301"></p>',
        //   resourcepath: ''
        // },
        // {
        //   id: '3',
        //   informationid: '3',
        //   name: '选择盆土',
        //   sort: '3',
        //   content: '<p><span style="color: rgb(51, 51, 51);">从市场上买回来的盆栽苹果，现有的盆土一般都可以满足苹果树的生长需要。但如果苹果的长势不好，就要更换一些盆土，或者给盆土添加一些营养土。一般选择一些肥沃的泥土，里面添加一些河沙、化肥等搅拌混合在一起，然后放入盆栽苹果树中，以保证苹果树的营养需求。</span></p><p><img src="http://139.198.13.100:7010/resource/M00/00/62/wKgAA1p2ZRSAc0ybAAP12BOWBkk025.jpg" style="display: block; margin: auto; cursor: nwse-resize;" width="301"></p>',
        //   resourcepath: ''
        // },
        // {
        //   id: '4',
        //   informationid: '4',
        //   name: '形状修剪',
        //   sort: '4',
        //   content: '<p><span style="color: rgb(51, 51, 51);">盆栽苹果树除了可以结果，其形状也具有一定的观赏性。因此，在给盆栽苹果塑造形状方面，可以根据自己的喜欢，对盆栽苹果树进行形状修剪，比如半圆形、长三角形、直立多枝形等。在给苹果盆栽修剪形状的时候要注意树枝保留的角度大点，让树枝有一定的生长空间，有利于盆栽苹果树的开花结果。</span></p><p><img src="http://139.198.13.100:7010/resource/M00/00/62/wKgAA1p2ZW6AZP7wAARR9UmKW6E832.jpg" style="display: block; margin: auto; cursor: nwse-resize;" width="301"></p>',
        //   resourcepath: ''
        // }
      ],
      producttypes: [],
      editorOption: {
        modules: {
          toolbar: '#toolbar',
          // toolbar: [
          //   ['bold', 'italic', 'underline', 'strike'],
          //   ['blockquote', 'code-block'],
          //   [{ 'header': 1 }, { 'header': 2 }],
          //   [{ 'list': 'ordered' }, { 'list': 'bullet' }],
          //   [{ 'script': 'sub' }, { 'script': 'super' }],
          //   [{ 'indent': '-1' }, { 'indent': '+1' }],
          //   [{ 'direction': 'rtl' }],
          //   [{ 'size': ['small', false, 'large', 'huge'] }],
          //   [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          //   [{ 'font': [] }],
          //   [{ 'color': [] }, { 'background': [] }],
          //   [{ 'align': [] }],
          //   ['clean'],
          //   ['link', 'image', 'video']
          // ],
          history: {
            delay: 1000,
            maxStack: 50,
            userOnly: false
          },
          imageResize: {
            displayStyles: {
              backgroundColor: 'black',
              border: 'none',
              color: 'white'
            },
            modules: ['Resize', 'DisplaySize', 'Toolbar']
          }
        }
      },
      fileupload: false,
      dropzoneOptions: {
        url: global.constant.uploadFileUrl + '?islogin=0',
        thumbnailWidth: 180,
        maxFilesize: 20,
        maxFiles: 1,
        acceptedFiles: 'image/jpg,image/jpeg,image/bmp,image/png,video/*,application/vnd.ms-excel,application/vnd.ms-word,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        addRemoveLinks: true,
        dictDefaultMessage: "<i class='fa fa-cloud-upload'></i>点击选择文件或拖拽文件至此上传",
        dictFileTooBig: '文件不能大于20M', // 文件过大时显示的文字信息
        dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        dictRemoveFile: '移除', // 移除按钮自定义文字
        dictInvalidFileType: '不支持该类型文件' // 文件类型无效时提示
      },
      resourcehistorylist: [],
      uploadresourcelist: [],
      quillRef: null,
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      selectedItemId: '',
      theme: 'primary',
      isError: false,
      editOrAddFormShow: false,
      isEmpty: false,
      showBtnDel: false
    }
  },
  watch: {},
  methods: {
    fetchProductType () {
      excuteApis(null, global.constant.commonApiUrls, 'category', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.producttypes = res.data
        } else {
          this.producttypes = []
        }
      })
    },
    fetchInformationData () {
      this.toast = new Toast(this, null, null)
      let requestParams = {
        id: this.$route.query.id
      }
      this.toast.show()
      excuteApis(requestParams, global.constant.systemApis, 'information', 'query').then(response => {
        let res = response.data
        if (res.flag === 1) {
          let infor = res.data
          if (infor.length > 0) {
            this.information = infor[0]
            let resourcePath = this.information.resourcepath
            this.$set(this.information, 'resourcepath', resourcePath)
          }
        }
        this.toast.close()
      }, () => {
        this.toast.close()
      })
    },
    fetchData () {
      let requestParams = {
        informationid: this.information.id
      }
      excuteApis(requestParams, global.constant.systemApis, 'informationContent', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.informationDetails = res.data
          if (this.informationDetails && this.informationDetails.length === 0) {
            this.editOrAddFormShow = true
          }
        }
      })
    },
    nextStep (n) {
      // 保存基本信息
      if (n === 1) {
        this.$v.$touch()
        // if (!this.information.resourcepath) {
        //   document.getElementsByClassName('croppa-container')[0].style.border = '1px solid red'
        //   return
        // }
        if (!this.valid) return
        this.information.resourceid = this.information.resourceid
        // this.information.pubuserid = global.helper.ls.get('user').supportid
        excuteApis(this.information, global.constant.systemApis, 'information', 'update').then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.information = res.data
            this.fetchData()
            this.$v.$reset()
            this.step += 1
          } else {
            console.log(res.message)
          }
        })
      }
    },
    upStep (n) {
      if (n === this.step) {
        this.step -= 1
      }
    },
    produceBase64ToUpload (fileDataUrl) {
      let requestParams = {
        base64Data: fileDataUrl
      }
      this.uploadbtnvisible = false
      this.uploaderrortips.content = '正在上传封面,请稍后...'
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.information.resourcepath = res.data[0].resourcepath
          this.uploaderrortips.content = '封面上传成功'
          document.getElementsByClassName('croppa-container')[0].style.border = '1px solid #cecbcb'
        } else {
          this.uploaderrortips.content = res.message
          this.uploadbtnvisible = true
        }
      })
    },
    blobToBase64 (blob, callback) {
      var a = new FileReader()
      a.onload = function (e) { callback(e.target.result) }
      a.readAsDataURL(blob)
    },
    uploadCroppedImage () {
      if (!this.croppa.hasImage()) {
        return
      }
      // let file = this.myCroppa.getChosenFile()
      this.croppa.generateBlob((blob) => {
        // write code to upload the cropped image file (a file is a blob)
        this.blobToBase64(blob, this.produceBase64ToUpload)
      }, 'image/jpeg', 0.8) // 80% compressed jpeg file
    },
    handleImageRemove () {
      this.information.resourcepath = ''
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
    showAddOrEditForm (selectedItem) {
      if (selectedItem.id) {
        this.editOrAddFormShow = true
        this.informationDetail.id = selectedItem.id
        this.informationDetail.name = selectedItem.name
        this.informationDetail.content = selectedItem.content
        this.informationDetail.sort = selectedItem.sort
        this.informationDetail.resourcepath = selectedItem.resourcepath
        this.addOrEdit = true
      } else {
        this.informationDetail.id = null
        this.informationDetail.name = null
        this.informationDetail.content = null
        this.informationDetail.sort = this.informationDetails.length + 1
        this.informationDetail.resourcepath = null
        this.addOrEdit = false
        // this.editOrAddFormShow = false
        this.$v.$reset()
      }
      this.editOrAddFormShow = true
    },
    onEditorBlur (editor) {
      // console.log('editor blur!', editor)
    },
    onEditorFocus (editor) {
      // console.log('editor focus!', editor)
    },
    onEditorReady (editor) {
      // console.log('editor ready!', editor)
    },
    saveInformationDetial () {
      if (!this.informationDetail.name && !this.informationDetail.content && !this.informationDetail.resourcepath) {
        this.isEmpty = true
        return
      }
      this.$v.$touch()
      if (!this.detailValid) return
      this.informationDetail.informationid = this.information.id
      // 修改详情
      if (this.informationDetail.id) {
        excuteApis(this.informationDetail, global.constant.systemApis, 'informationContent', 'update').then(data => {
          let res = data.data
          this.$v.$reset()
          if (res.flag === 1) {
            this.fetchData()
            this.informationDetail.name = ''
            this.informationDetail.content = ''
            this.editOrAddFormShow = false
          }
        })
      } else {
        // 新增详情
        this.informationDetail.sort = this.informationDetails.length + 1
        excuteApis(this.informationDetail, global.constant.systemApis, 'informationContent', 'add').then(data => {
          let res = data.data
          this.$v.$reset()
          if (res.flag === 1) {
            if (res.data) {
              this.fetchData()
              this.informationDetail.name = ''
              this.informationDetail.content = ''
              this.editOrAddFormShow = false
            }
          }
        })
      }
    },
    sopUploadError (file) {
      let self = this
      let mimeType = global.constant.fileMimeType(file.name)
      let filesize = (file.size / 1024 / 1024).toFixed(2)
      let invalidSize = filesize > self.dropzoneOptions.maxFilesize
      let invalid = mimeType !== 'image'
      self.toast.close()
      this.toast.setContent(invalid ? '不支持该文件类型上传' : (invalidSize ? '文件最大不能超过' + self.dropzoneOptions.maxFilesize + 'M，当前文件大小' + filesize + 'M' : '文件上传出错'))
      this.toast.showAutoClose()
      setTimeout(() => {
        self.$refs.fileDropzone.removeFile(file)
      }, 3000)
    },
    fileUploadSuccess (file, response) {
      this.toast.close()
      let res = response
      if (res.flag === 1) {
        this.resourcehistorylist.push(res.data[0])
        this.uploadresourcelist.push(res.data[0])
      } else {
        this.toast.setContent('文件上传出错')
        this.toast.showAutoClose()
      }
      this.$refs.fileDropzone.removeFile(file)
    },
    fileRemove (file, error, xhr) {
      this._.remove(this.resourcehistorylist, (item) => {
        return item.resourcename === file.name
      })
    },
    uploadfileSave () {
      this.fileupload = false
      this.quillRef.focus()
      for (let i = 0; i < this.uploadresourcelist.length; i++) {
        let mimeType = global.constant.fileMimeType(this.uploadresourcelist[i].resourcename)
        if (mimeType.indexOf('video') === 0) {
          this.quillRef.insertEmbed(this.quillRef.getSelection().index, mimeType, '/static/video.html?vid=' + this.uploadresourcelist[i].resourcepath)
        } else if (mimeType.indexOf('image') === 0) {
          this.quillRef.insertEmbed(this.quillRef.getSelection().index, mimeType, this.uploadresourcelist[i].resourcepath)
        } else {
          let attachContent = '&nbsp;<a href="' + this.uploadresourcelist[i].resourcepath + '"&nbsp;&nbsp;class="ql-font-serif">' + this.uploadresourcelist[i].resourcename + '</a>'
          console.log(attachContent)
          this.quillRef.clipboard.dangerouslyPasteHTML(this.quillRef.getSelection().index, attachContent)
        }
      }
    },
    openUploadForm () {
      this.resourcehistorylist = []
      this.uploadresourcelist = []
      this.quillRef = this.$refs.myTextEditor[0].quill
      this.fileupload = true
    },
    updatePhaseSort (detailItem, inx, direction) {
      let params = {}
      if (direction === 'up') {
        params = Object.assign(params, { sourceid: detailItem.id }, { sourcesort: detailItem.sort - 1 }, { targetid: this.informationDetails[inx - 1].id }, { targetsort: detailItem.sort })
      } else {
        params = Object.assign(params, { sourceid: detailItem.id }, { sourcesort: detailItem.sort + 1 }, { targetid: this.informationDetails[inx + 1].id }, { targetsort: detailItem.sort })
      }
      excuteApis(params, global.constant.systemApis, 'informationContent', 'updateSort').then(data => {
        let res = data.data
        if (res.flag !== 1) {
          console.log(res.message)
        } else {
          this.fetchData()
        }
      })
    },
    deletePhase (detailItem) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = detailItem.id
    },
    tipsOk () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.systemApis, 'informationContent', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.fetchData()
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    tipsCancel () {
      this.prompt.dialog = false
    },
    informationPublish () {
      let requestParams = {}
      requestParams = Object.assign(requestParams, { puborgid: global.helper.ls.get('user').loginorgid }, { id: this.information.id }, { ispub: 1 }, { pubuserid: global.helper.ls.get('user').supportid })
      excuteApis(requestParams, global.constant.systemApis, 'information', 'publish').then(data => {
        let res = data.data
        if (res.flag !== 1) { } else {
          this.$router.push({ name: 'sopsuccesstips', query: { id: this.information.id } })
        }
      })
    },
    btnDelImg () {
      this.information.resourcepath = ''
      this.information.resourceid = ''
      this.uploaderrortips.content = '支持JPG、PNG、BMP格式'
    },
    fileAdded (file) {

    },
    fileSending (file, xhr, formData) {
      this.toast.setContent('上传中......')
      this.toast.show()
    }
  },
  computed: {
    informationTitleErrors () {
      let errors = []
      if (!this.$v.information.title.$dirty) return errors
      !this.$v.information.title.maxLength && errors.push('SOP规程名称太长')
      !this.$v.information.title.required && errors.push('请输入SOP规程名称')
      return errors
    },
    informationProducttypeidErrors () {
      let errors = []
      if (!this.$v.information.categoryid.$dirty) return errors
      !this.$v.information.categoryid.required && errors.push('请选择品类')
      return errors
    },
    informationDetailNameErrors () {
      let errors = []
      !this.$v.informationDetail.name.maxLength && errors.push('阶段名称太长')
      return errors
    },
    valid () {
      let errors = []
      if (this.informationTitleErrors.length > 0) {
        for (let i = 0; i < this.informationTitleErrors.length; i++) {
          errors.push(this.informationTitleErrors[i])
        }
      }
      if (this.informationProducttypeidErrors) {
        for (let i = 0; i < this.informationProducttypeidErrors.length; i++) {
          errors.push(this.informationProducttypeidErrors[i])
        }
      }
      if (errors.length > 0) { return false } else { return true }
    },
    detailValid () {
      let errors = this.informationDetailNameErrors
      if (errors.length > 0) { return false } else { return true }
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.toast = new Toast(this, '上传中......', null)
    this.fetchProductType()
    this.fetchInformationData()
    this.stepCount = this.steps.length
  },
  mounted () { }
}
</script>

<style scoped>
.uploadbtn {
  top: -15px;
}
.errorstips {
  font-size: 12px;
  /* color: red; */
  opacity: 0.8;
}
.croppa-container {
  border: 1px solid #cecbcb;
}
.dividerline {
  padding-top: 10px;
  padding-bottom: 10px;
}
.detailtitle {
  padding-top: 12px;
}
.inforDetailShow {
  border: 1px solid rgba(180, 176, 176, 0.54);
  padding-left: 15px;
  margin-bottom: 5px;
}
.inforDetailForm {
  border: 1px solid rgba(180, 176, 176, 0.54);
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 20px;
}
.error {
  color: red;
  font-size: 12px;
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
