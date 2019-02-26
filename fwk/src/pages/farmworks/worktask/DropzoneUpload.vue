<template lang="pug">
div(v-if="attachments.length < maxfilenumbers" style="line-height: 30px;" title="点击图标选择上传附件")
  div(style="line-height: 30px;") 
    v-icon() attachment
    span(style="line-height: 30px;margin-left: 10px; color: rgba(0, 0, 0, .47)") 最多添加{{ maxfilenumbers }}个文件，单个文件控制在10M以内
  dropzone(
    ref="filedropzone"
    id="dropzone"
    :options="options"
    v-on:vdropzone-success="uploadSuccess"
    v-on:vdropzone-error="uploadError"
    v-on:vdropzone-file-added="fileAdded"
    v-on:vdropzone-sending="fileSending")
</template>

<script>
import { Toast } from '@/models/toast'
export default {
  name: 'v-minidropzoneupload',
  props: {
    files: {
      type: Array,
      required: true,
      default: () => []
    },
    maxfilenumbers: {
      type: Number,
      required: false,
      default: 10
    },
    fileTypes: {
      type: Array,
      required: false,
      default: () => ['jpg', 'jpeg', 'bmp', 'png', 'tif', 'gif', 'xls', 'xlsx', 'doc', 'docx', 'pdf', 'mp3', 'mp4', 'txt']
    }
  },
  data () {
    const types = this.fileTypes.map(type => '.' + type.toLowerCase())
    return {
      toast: null,
      attachments: [],
      options: {
        url: global.constant.uploadFileUrl, // global.constant.uploadFileUrl
        thumbnailHeight: 30,
        thumbnailWidth: 30,
        maxFilesize: 10,
        maxFiles: 1,
        acceptedFiles: types.join(','),
        addRemoveLinks: false,
        dictDefaultMessage: '',
        // dictFileTooBig: '文件不能大于10M', // 文件过大时显示的文字信息
        // dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        // dictRemoveFile: '移除', // 移除按钮自定义文字
        // dictInvalidFileType: '不支持该类型文件', // 文件类型无效时提示
        previewTemplate: '<div></div>'
      }
    }
  },
  watch: {
    files (v) {
      this.attachments = v
    },
    attachments (v) {
      this.$emit('update:files', v)
    }
  },
  methods: {
    uploadSuccess (file, response) {
      this.toast.close()
      let res = response
      if (res.flag === 1) {
        this.attachments.push(res.data[0])
      } else if (res.flag === 0) {
        this.toast.setContent('文件上传出错')
        this.toast.showAutoClose()
      }
      this.$refs.filedropzone.removeFile(file)
    },
    uploadError (file) {
      let self = this
      let filename = file.name
      let filesize = (file.size / 1024 / 1024).toFixed(2)
      let invalidSize = filesize > self.options.maxFilesize
      let curType = filename.substring(filename.lastIndexOf('.') + 1, filename.length)
      let invalidType = this.fileTypes.indexOf(curType) < 0
      let tipscontent = invalidSize ? ('文件大小不能超过' + self.options.maxFilesize + 'M，当前文件大小约为' + filesize + 'M') : (invalidType ? '不支持该文件类型上传' : '无效的文件')
      self.toast.close()
      this.toast.setContent(tipscontent)
      this.toast.showAutoClose()
      setTimeout(() => {
        self.$refs.filedropzone.removeFile(file)
      }, 3000)
    },
    fileAdded (file) {
      // 增加了同个文件多次上传的限制
      let self = this
      self.toast.setContent('上传中...')
      let filename = file.name
      let filelist = self.attachments.filter(item => {
        return item.resourcename === filename
      })
      if (filelist.length > 0) {
        let tipscontent = '该文件已上传，请重新选择'
        self.toast.close()
        self.toast.setContent(tipscontent)
        self.toast.showAutoClose()
        setTimeout(() => {
          self.$refs.filedropzone.removeFile(file)
        }, 3000)
      }
      filelist.length > 0 ? self.$refs.filedropzone.removeFile(file) : null
    },
    fileSending (file, xhr, formData) {
      this.toast.show()
    }
  },
  created () {
    this.toast = new Toast(this, '上传中......', null)
  }
}

</script>

<style scoped>
.dropzone {
  width: 23px;
  height: 20px;
  margin-top: 0px;
  background: none;
  min-height: 0px;
  opacity: 0.7;
  position: relative;
  left: 0px;
  top: -23px;
  padding: 5px 5px;
}
.vue-dropzone {
  border: 1px dashed #fafafa;
}
</style>
