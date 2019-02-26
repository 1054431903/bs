<template>
  <v-container fluid class="containerWrapper">
    <v-card class="page-content">
      <!-- 问题 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader>*&nbsp;问题</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-text-field
            ref="communioncontent"
            v-model="communioncontent"
            label="请输入问题"
            textarea
            single-line
            maxlength="500"
            :rules="[() => !!communioncontent || '请填写问题内容']"
          >
          </v-text-field>
        </v-flex>
      </v-layout>

      <!-- 图片 -->
      <v-layout row class="pictureWrapper">
        <v-flex xs2>
          <v-subheader>图片</v-subheader>
        </v-flex>
        <v-flex xs6>
          <!-- 文件上传 -->
          <v-layout row wrap class="uploadFile-wrapper">
            <v-flex xs12 class="uploadFile-text">
              <v-btn id="uploadFile" color="primary" dark><v-icon dark>backup</v-icon></v-btn>
              <span>点击左侧上传按钮选择图片或将图片拖曳到此上传,支持多图片上传</span>
            </v-flex>
            <v-flex xs12>
              <dropzone ref="sendRecordDropzone"
                        id="dropzone"
                        :options="dropzoneOptions"
                        @vdropzone-success="vsuccess"
                        @vdropzone-error="verror"
                        @vdropzone-removed-file="removeFile"
                        @vdropzone-files-added="addFiles"
              />
            </v-flex>
          </v-layout>

        </v-flex>
      </v-layout>



      <!-- 品类 -->
      <v-layout row class="typeWrapper">
        <v-flex xs2>
          <v-subheader>*&nbsp;品类</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-select placeholder="请选择品类"
                    ref="communioncategorylist"
                    v-model="communioncategorylist"
                    :items="communioncategorylistArr"
                    item-text="name"
                    item-value="id"
                    multiple
          >
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 地块 -->
      <v-layout row>
        <v-flex xs2>
          <v-subheader>地块</v-subheader>
        </v-flex>
        <v-flex xs6>
          <v-select placeholder="请选择地块"
                    v-model="communionparcellist"
                    :items="communionparcellistArr"
                    item-text="name"
                    item-value="id"
          >

          </v-select>
        </v-flex>
      </v-layout>

      <!-- 提交 -->
      <v-layout row>
      <v-flex xs6 offset-xs2>
        <v-btn @click="submit" class="btnSize btnColor" :disabled="disabled" color="primary">
          提交
          <v-progress-circular
            v-show="progressShow"
            class="progressWrapper"
            size="23"
            indeterminate
            color="primary"
          >
          </v-progress-circular>
        </v-btn>
      </v-flex>
    </v-layout>
    </v-card>

    <!-- 消息弹出框 -->
    <v-dialog v-model="dialogMessage" width="500">
      <v-card>
        <v-card-text>
          <v-layout row wrap text-xs-center>
            <v-flex xs12 v-text="messageResponse"></v-flex>
          </v-layout>
        </v-card-text>

        <v-card-actions class="btnPadding">
          <v-layout row wrap text-xs-center>
            <v-flex xs12>
              <v-btn @click="dialogMessage = false" color="primary" class="btnSize">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>
<script>
  import { excuteApis } from '@/apis'
  export default {
    name: 'ask',
    data () {
      return {
        /**
         * 业务字段
         */
        communioncontent: null, // 问题内容
        communioncategorylist: [], //	品类列表—来源于支撑平台
        communionparcellist: [], // 地块列表
        resourcehistorylist: [],	// 附件列表
        location: null, // 发布人的地理位置
        /**
         * 页面渲染字段
         */
        messageResponse: null,
        dialogMessage: false,
        communioncategorylistArr: [], // 品类列表
        communionparcellistArr: [], //	地块列表
        progressShow: false,
        disabled: false,
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
          acceptedFiles: '.JPG, .PNG, .BMP', // 允许上传的文件类型
          dictFileTooBig: '请上传20M以内的文件', // 文件过大时显示的文字信息
          dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
          dictRemoveFile: '移除', // 移除按钮自定义文字
          dictResponseError: '上传失败,请重试' // 服务器无响应的显示信息
        }
      }
    },
    computed: {
      /**
       * 表单必填字段
       */
      form () {
        return {
          communioncontent: this.communioncontent
        }
      }
    },
    mounted () {
      this.getServiceData()
    },
    methods: {
      /**
       * 提交
       */
      submit () {
        this.valdateForm() // 验证表单
        if (this.formHasErrors) return
        // 品类数据处理
        let newCommunioncategorylist = []
        for (let id of this.communioncategorylist) {
          newCommunioncategorylist.push({ categoryid: id, categoryname: id })
        }

        this.progressShow = true
        this.disabled = true
        let param = {
          communioncontent: this.communioncontent, // 问题
          communioncategorylist: newCommunioncategorylist, // 品类
          communionparcellist: { parcelid: this.communionparcellist }, // 地块
          resourcehistorylist: this.resourcehistorylist, // 资源
          location: this.location // 位置
        }
        excuteApis(param, global.constant.farmApiUrls, 'communion', 'add').then(response => {
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.$router.push({name: 'chat'})
          } else {
            // 弹窗
          }
        }).catch((response) => {
          console.log(response)
        }).finally(() => {
          this.progressShow = false
          this.disabled = false
        })
      },

      /**
       * 验证表单
       */
      valdateForm () {
        this.formHasErrors = false // 表单是否有错误
        Object.keys(this.form).forEach(f => {
          if (!this.form[f] || this.form[f].length === 0) this.formHasErrors = true
          this.$refs[f].validate(true)
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
        console.log('文件上传失败')
        console.log(message)
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
       * 获取后台数据
       */
      getServiceData () {
        /**
         * 获取地块
         */
        excuteApis({}, global.constant.parcelApis, 'parcel', 'query').then(response => {
          console.log('获取地块成功------')
          console.log(response)
          if (response.data && response.data.flag === 1 && response.data.data) {
            this.communionparcellistArr = response.data.data
          }
        })

        /**
         * 获取品类
         */
        excuteApis({}, global.constant.commonApiUrls, 'category', 'query').then(response => {
          console.log('获取品类成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.communioncategorylistArr = response.data.data
          }
        }).catch((response) => {
          console.log('获取品类失败')
          console.log(response)
        })
      },

      /**
       * 消息弹出框内容设置
       */
      openMessage (content) {
        this.messageResponse = content
        this.dialogMessage = true
      }
    }
  }
</script>
<style scoped>
  .pictureWrapper{
    margin-top: 10px;
  }
  .typeWrapper{
    margin-top: 10px;
  }
  /* dropzone 边框调整 */
  .vue-dropzone{
    border-width: 0;
  }
  .uploadFile-wrapper{
    border: 2px solid #E5E5E5;
  }
  .uploadFile-text{
    margin: 15px 0 15px 15px;
  }
  #dropzone{
    padding: 5px;
  }
  .btnSize{
    width: 107px
  }
  .progressWrapper{
    position: absolute;
    left: 73px
  }
  .containerWrapper{
    padding: 0;
  }
  .btnColor{
    color: #FFF;
  }
</style>
