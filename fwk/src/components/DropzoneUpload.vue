<template lang="pug">
div
  v-layout(row, wrap) 
    v-flex(xs12 sm12 md12 lg12 xl12)
      span 附件：
      span(style="color: grey;") &nbsp;&nbsp;最多添加10个文件 ，且单个不大于10M，支持文件格式包括图片、Word、Excel及PDF
    v-flex(xs12 sm12 md12 lg12 xl12 py-2 v-if="attachments.length < maxfilenumbers")
      dropzone(
        ref="filedropzone"
        id="dropzone"
        :options="options"
        v-on:vdropzone-success="uploadSuccess"
        v-on:vdropzone-error="uploadError"
        v-on:vdropzone-file-added="fileAdded"
        v-on:vdropzone-sending="fileSending")
  v-layout(row, wrap)
    v-flex(xs12 sm12 md12 lg12 xl12)
      div(row v-for="(item, index) in attachments" :key="item.resourcepath")
        div(class="attach" @mouseenter="getSelectedAttach(index)" @mouseleave="leaveSelectedAttach")
          img(:src="getIcon(item.resourcename)" width="auto" height="20" style="vertical-align: middle; margin-right: 10px;")
          a(:href="item.resourcepath" target="_blank") {{ item.resourcename }}&nbsp;&nbsp;
          v-avatar(class="grey delBtn" size="18" @click="removeResourece(item)" v-if="selectedAttach === item.resourcepath")
            <img :src="`static/close.png`" />
          //- v-btn(title="删除" fab small icon @click="removeResourece(item)" v-if="selectedAttach === item.resourcepath")
          //-   v-icon(color="red darken-2") delete
  v-layout(row, justify-center)
    v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
      div {{prompt.body}}
      template(slot="btnActions")
        v-divider
        v-card-actions
          v-spacer
          v-btn(flat, color="primary", @click.native.stop="back") 取消
          v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定
</template>

<script>
import { Toast } from '@/models/toast'
import { getIconPath } from '@/utils'
export default {
  name: 'v-dropzoneupload',
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
      default: () => ['jpg', 'jpeg', 'bmp', 'png', 'tif', 'gif', 'xls', 'xlsx', 'doc', 'docx', 'pdf', 'ppt', 'pptx', 'txt', 'wps', 'rm', 'rmvb', 'mtv', 'wmv', 'avi', '3gp', 'amv', 'dmv', 'flv', 'mp3', 'mp4', 'mkv', 'mov', 'amr']
    }
  },
  data () {
    const types = this.fileTypes.map(type => '.' + type.toLowerCase())
    return {
      toast: null,
      attachments: [],
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      options: {
        url: global.constant.uploadFileUrl,
        thumbnailWidth: 180,
        maxFilesize: 10,
        maxFiles: 1,
        acceptedFiles: types.join(','),
        addRemoveLinks: true,
        // dictDefaultMessage: "<i class='fa fa-cloud-upload'></i>点击选择文件或拖拽文件至此上传",
        dictDefaultMessage: "<img src='static/yun.png' style='margin-right: 5px' />点击选择文件或拖拽文件至此上传",
        dictFileTooBig: '文件不能大于10M', // 文件过大时显示的文字信息
        dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        dictRemoveFile: '移除', // 移除按钮自定义文字
        dictInvalidFileType: '不支持该类型文件' // 文件类型无效时提示
      },
      selectedAttach: null
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
    getIcon (rescourcename) {
      return getIconPath(rescourcename)
    },
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
    getSelectedAttach (index) {
      this.selectedAttach = this.attachments[index].resourcepath
    },
    leaveSelectedAttach () {
      this.selectedAttach = null
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
    },
    removeResourece (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItem = item
    },
    Ok () {
      this._.remove(this.attachments, data => {
        return data.resourcename === this.selectedItem.resourcename
      })
      this.prompt.dialog = false
    },
    back () {
      this.prompt.dialog = false
    }
  },
  created () {
    this.toast = new Toast(this, '上传中......', null)
  }
}

</script>

<style scoped>
.attach {
  border: 1px solid rgb(216, 210, 210);
  margin-top: 5px;
  padding: 10px 10px;
}
.attach:hover {
  background: rgb(248, 245, 245);
}
.delBtn {
  float: right;
  cursor: pointer;
}
</style>
