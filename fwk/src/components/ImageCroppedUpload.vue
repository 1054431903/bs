<template>
  <v-layout row
            v-if="showme">
    <v-modaldialog :isShow="true"
                   title="图像裁剪及上传"
                   :modalMaxWidth="650"
                   :bodyHeight="400">
      <div class="imageContainer">
        <v-imagecropper ref="cropper"
                        :img="option.img"
                        :outputSize="1"
                        outputType="png"
                        :info="true"
                        :full="false"
                        :canMove="true"
                        :canMoveBox="false"
                        :fixedBox="fixedBox"
                        :original="false"
                        :autoCropWidth="cropRect[0]"
                        :autoCropHeight="cropRect[1]"
                        :autoCrop="true"
                        @realTime="realTime"
                        @imgLoad="imgLoad"></v-imagecropper>
      </div>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions>
          <v-layout row>
            <v-flex xs4
                    pt-1>
              <v-checkbox label="默认固定裁剪区"
                          v-model="imutationBox"
                          hide-details
                          color="primary">
              </v-checkbox>
            </v-flex>
            <v-flex x3
                    pt-4
                    class="text-xs-center">
              <!--<v-btn flat color="primary" >点击选择图片</v-btn>-->
              <label class="btn btn--flat primary--text btnselectimg"
                     for="uploads">点击选择图片</label>
              <input type="file"
                     id="uploads"
                     style="position:absolute; clip:rect(0 0 0 0);"
                     accept="image/png, image/jpeg, image/gif, image/jpg"
                     @change="uploadImg($event, 1)">
              <!--<v-btn flat color="primary" @click="changeImg">切换图片</v-btn>-->
            </v-flex>
            <v-flex xs5
                    class="text-xs-right"
                    pt-3>
              <v-btn flat
                     color="primary"
                     @click="croppaCancel">取消</v-btn>
              <v-btn flat
                     color="primary"
                     @click="croppaOk">确定</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </template>
    </v-modaldialog>
  </v-layout>
</template>

<script>
import { excuteApis } from '@/apis'
import { Toast } from '@/models/toast'
export default {
  props: {
    showme: {
      type: Boolean,
      default: false
    },
    cropRect: {
      type: Array,
      default: () => [120, 120]
    },
    img: {
      type: String,
      default: ''
    },
    imgname: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      imutationBox: true,
      fixedBox: true,
      crap: false,
      previews: {},
      option: {
        img: 'static/peasant/moren.png'
        // size: 1,
        // full: false,
        // outputType: 'png',
        // canMove: true,
        // fixedBox: true,
        // autoCrop: true,
        // original: false,
        // canMoveBox: false
        // autoCropWidth: 120,
        // autoCropHeight: 120
      },
      downImg: '#',
      imgName: this.imgname
    }
  },
  watch: {
    imutationBox: function (v, o) {
      this.fixedBox = v
    },
    img: function (v, o) {
      if (v && v.indexOf('http') === 0) {
        v = v.replace('http://fwk-oss.oss-cn-shenzhen.aliyuncs.com/', 'netresource/')
      }
      this.option.img = v || 'static/peasant/moren.png'
    }
  },
  methods: {
    changeImg () {
      // this.option.img = 'http://ofyaji162.bkt.clouddn.com/bg1.jpg'
      this.option.img = 'netresource/ed01f766-e64f-493d-82d8-eaa44feb090f.jpg'
    },
    startCrop () {
      // start
      this.crap = true
      this.$refs.cropper.startCrop()
    },
    stopCrop () {
      //  stop
      this.crap = false
      this.$refs.cropper.stopCrop()
    },
    clearCrop () {
      // clear
      this.$refs.cropper.clearCrop()
    },
    // 实时预览函数
    realTime (data) {
      this.previews = data
    },
    finish (type) {
      // 输出
      // let test = window.open('about:blank')
      // test.document.body.innerHTML = '图片生成中..'
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          // let test = window.open('')
          // test.location.href = window.URL.createObjectURL(data)
          console.log('blob data.....')
          console.log(data)
        })
      } else {
        this.$refs.cropper.getCropData((data) => {
          // test.location.href = data
          this.commitImgData(data)
        })
      }
    },

    down (type) {
      // event.preventDefault()
      var aLink = document.createElement('a')
      aLink.download = 'demo'
      // 输出
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          this.downImg = data
          aLink.href = data
          aLink.click()
        })
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.downImg = data
          aLink.href = data
          aLink.click()
        })
      }
    },

    uploadImg (e, num) {
      let file = e.target.files[0]
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        // alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
        this.toast.setContent('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
        this.toast.showAutoClose()
        return false
      }
      this.imgName = file.name
      let reader = new FileReader()
      reader.onload = (e) => {
        let data
        if (typeof e.target.result === 'object') {
          // 把Array Buffer转化为blob 如果是base64不需要
          console.log(e.target.result)
          data = window.URL.createObjectURL(new Blob([e.target.result]))
        } else {
          console.log(e.target.result)
          data = e.target.result
        }
        if (num === 1) {
          this.option.img = data
        }
        if (e.srcElement.files) {
          this.imgname = e.srcElement.files[0].name
        }
      }
      // 转化为base64
      // reader.readAsDataURL(file)
      // 转化为blob
      reader.readAsArrayBuffer(file)
    },
    imgLoad (msg) {
      console.log('loading img...' + msg)
      // let im = new Image()
      // im.src = 'http://fwk-oss.oss-cn-shenzhen.aliyuncs.com/ed01f766-e64f-493d-82d8-eaa44feb090f.jpg'
      // console.log(im)
      // let reader = new FileReader()
      // reader.readAsArrayBuffer('http://fwk-oss.oss-cn-shenzhen.aliyuncs.com/ed01f766-e64f-493d-82d8-eaa44feb090f.jpg'
    },
    croppaOk () {
      this.finish()
    },
    croppaCancel () {
      this.$emit('update:showme', false)
    },
    commitImgData (imgdata) {
      let requestParams = {
        base64Data: imgdata
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'uploadBase64').then(data => {
        let res = data.data
        if (res.flag === 1) {
          let resourcepath = res.data[0].resourcepath
          console.log('图片上传成功')
          this.$emit('update:img', resourcepath)
          this.$emit('update:imgname', this.imgName)
          this.$emit('update:showme', false)
        } else {
          console.log('图片上传失败')
        }
      }, () => {
        console.log('服务或网络错误')
      })
    }
    // cacheRemoteImg (url) {
    //   let im = new Image()
    //   let canvas = document.createElement('canvas')
    //   let ctx = canvas.getContext('2d')
    //   let src = url // 具体的图片地址
    //   im.crossOrigin = 'Anonymous'
    //   im.onload = function () {
    //     canvas.width = im.width
    //     canvas.height = im.height
    //     ctx.drawImage(im, 0, 0)
    //     localStorage.setItem('savedImageData', canvas.toDataURL('image/png'))
    //   }
    //   im.src = src
    //   //  确保缓存的图片也触发 load 事件
    //   if (im.complete || im.complete === undefined) {
    //     im.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw=='
    //     im.src = src
    //   }
    //   console.log('88888888888888')
    //   return im
    // }
  },
  created () {
    this.toast = new Toast(this, null, null)
  },
  mounted () { }
}
</script>

<style scoped>
.imageContainer {
  height: 100%;
}
.btnselectimg {
  cursor: pointer;
}
/* .btnselectimg:hover {
  background-color: #39bbb0;
} */
.vue-cropper {
  background-repeat: repeat;
}
</style>
