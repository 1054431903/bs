<!-- 文件上传 - 农事 -->

<template>
  <v-layout row wrap class="uploadFile-wrapper">
    <v-flex xs12 class="uploadFile-text">
      <v-btn id="uploadFile" color="primary" dark><v-icon dark>backup</v-icon></v-btn>
      <span>点击左侧上传按钮选择文件或将文件拖曳到此上传,支持多文件上传</span>
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
    <v-flex xs12 v-if="edit">
      <!-- 资源列表下面的表格 -->
      <v-data-table
        :headers="resourceHeaders"
        :items="oldResourcehistorylist"
        no-data-text="暂无数据"
        no-results-text="暂无数据"
        hide-actions
        class="elevation-1"
      >
        <template slot="items" slot-scope="props">
          <td v-text="props.item.resourcename"></td>
          <td v-text="props.item.resourcepath"></td>
          <td class="text-xs-center">
            <v-btn fab small @click="deleteResource(props.item.fileId)">
              <v-icon>delete</v-icon>
            </v-btn>
          </td>
        </template>
      </v-data-table>
    </v-flex>
  </v-layout>
</template>
<script>
  export default {
    name: 'file-upload',
    data () {
      return {
        fileId: 0, // 上传文件资源标识
        // resourcehistorylist: [], // 附件列表
        // oldResourcehistorylist: [], // 已有的附件列表
        // resource header
        resourceHeaders: [
          { text: '资源名称', value: 'value1', sortable: false, align: 'left' },
          { text: '访问地址', value: 'value2', sortable: false, align: 'left' },
          { text: '操作', value: 'value6', sortable: false, align: 'center' }
        ],
        dropzoneOptions: {
          url: 'http://10.88.20.66:5762/fwk-service-archives/common/fileservice/upload', // 文件上传地址
          method: 'post', // 文件上传方式 默认post
          // thumbnailWidth: 150, //  设置缩略图的缩略比 配合下面的thumbnailHeight一起使用
          // thumbnailHeight: 150,
          maxFilesize: 20, // 上传大小限制 单位MB
          // uploadMultiple: // 是否 Dropzone 应该在一个请求中发送多个文件
          dictDefaultMessage: '',
          clickable: '#uploadFile', // dropzone 元素本身是否可以点击 默认true,或者设置点击那个元素能上传,可以是数组
          // maxFiles: 10 // 最多处理几个文件
          addRemoveLinks: true,
          dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
          dictRemoveFile: '移除' // 移除按钮自定义文字
        }
      }
    },
    methods: {
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
      verror (file, message, xhr) {
        console.log('文件上传失败')
        console.log(message)
        console.log(xhr)
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
       * 删除已有附件列表
       */
      deleteResource (id) {
        console.log('当前附件id = ' + id)
        for (let i = 0; i < this.oldResourcehistorylist.length; ++i) {
          if (this.oldResourcehistorylist[i].fileId === id) {
            this.oldResourcehistorylist.splice(i, 1)
            break
          }
        }
      }
    }
  }
</script>
<style scoped>
  .uploadFile-wrapper{
    border: 2px solid #E5E5E5;
  }
  .uploadFile-text{
    margin: 15px 0 15px 15px;
  }
  #dropzone{
    padding: 5px;
  }
</style>
