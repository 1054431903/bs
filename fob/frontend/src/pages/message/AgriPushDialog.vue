<template>
  <v-dialog :value="show"
            @input="close"
            scrollable
            max-width="900px">
    <v-card v-if="show">
      <v-toolbar card
                 color="grey lighten-4">
        <v-toolbar-title>新增消息
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon
               small
               @click.native="close">
          <v-icon>close</v-icon>
        </v-btn>
      </v-toolbar>
      <v-divider></v-divider>
      <v-card-text class="px-5">
        <v-form ref="form">
          <v-text-field v-model="title"
                        v-validate="'required|max:50'"
                        data-vv-name="title"
                        :counter="50"
                        label="消息标题"
                        :error-messages="errors.collect('title')"
                        required></v-text-field>
          <v-text-field v-model="subTitle"
                        v-validate="'required|max:100'"
                        data-vv-name="subTitle"
                        :counter="100"
                        label="消息副标题"
                        :error-messages="errors.collect('subTitle')"
                        required></v-text-field>
          <v-autocomplete v-model="selectedFruits"
                          data-vv-name="selectedFruits"
                          :items="fruits"
                          label="消息接收组织"
                          chips
                          multiple
                          v-validate="'required'"
                          :error-messages="errors.collect('selectedFruits')"
                          item-text="name"
                          item-value="_id.$oid"
                          no-data-text="无匹配的数据">
            <v-list-tile slot="prepend-item"
                         ripple
                         @click="toggle">
              <v-list-tile-action>
                <v-icon :color="selectedFruits.length > 0 ? 'indigo darken-4' : ''">{{ icon }}</v-icon>
              </v-list-tile-action>
              <v-list-tile-title>全选</v-list-tile-title>
            </v-list-tile>
            <template slot="selection"
                      slot-scope="data">
              <v-chip :selected="data.selected"
                      close
                      @input="remove(data.item)">
                <strong>{{ data.item.name }}</strong>
              </v-chip>
            </template>
          </v-autocomplete>
          <v-select v-model="selectedItem"
                    :items="type"
                    label="消息类型"
                    data-vv-name="selectedItem"
                    :error-messages="errors.collect('selectedItem')"
                    item-text="name"
                    item-value="value">
          </v-select>
          <v-layout row
                    v-if="isAgri"
                    pt-2>
            <v-flex style="min-height:600px;height:auto">
              <Upload :show-upload-list="false"
                      :on-success="handleSuccess"
                      :on-exceeded-size="handleMaxSize"
                      :format="['jpg','jpeg','png','gif']"
                      :max-size="5120"
                      multiple
                      action="http://test.farm.agrithings.cn:5762/fwk-service-archives/common/fileservice/upload?islogin=1">
                <Button icon="ios-cloud-upload-outline"></Button>
              </Upload>
              <quill-editor v-model="content"
                            ref="QuillEditor"
                            :options="editorOption">
              </quill-editor>
            </v-flex>
          </v-layout>
          <v-text-field v-model="url"
                        label="引用超链接地址"
                        :rules="urlRules"
                        v-else></v-text-field>
        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn flat
               color="primary"
               @click="close">取消</v-btn>
        <v-btn flat
               color="primary"
               @click="submit">确定</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import axios from 'axios'
import store from '../../store'

// 工具栏配置
const toolbarOptions = [
  [{ 'header': 1 }, { 'header': 2 }], // custom button values
  [{ 'list': 'ordered' }, { 'list': 'bullet' }],
  [{ 'size': ['small', false, 'large', 'huge'] }], // custom dropdown
  [{ 'color': [] }, { 'background': [] }], // dropdown with defaults from theme
  [{ 'font': [] }],
  [{ 'align': [] }],
  ['link', 'image'],
  ['clean'] // remove formatting button
]
let Expression = /^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[/=?%\-&_~`@[\]':+!]*([^<>""])*$/
let objExp = new RegExp(Expression)
export default {
  name: 'agri-push-dialog',
  $_veeValidate: {
    validator: 'new'
  },
  props: {
    show: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      search: null,
      isAgri: true,
      title: '',
      subTitle: '',
      url: '',
      urlRules: [
        v => !!v || '请输入超链接地址',
        v => !!objExp.test(v) || '请输入合法的超链接地址'
      ],
      type: [
        { name: '原创内容', value: 'original' },
        { name: '第三方引用', value: 'reference' }
      ],
      fruits: [],
      newfruits: [],
      selectedFruits: [],
      selectedItem: 'original',
      content: '',
      editorOption: {
        placeholder: '编辑消息内容',
        modules: {
          toolbar: {
            container: toolbarOptions, // 工具栏
            handlers: {
              'image': function (value) {
                if (value) {
                  // 调用iview图片上传
                  document.querySelector('.ivu-upload .ivu-upload-input').click()
                } else {
                  this.quill.format('image', false)
                }
              }

            },
            imageDrop: true,
            imageResize: {
              displayStyles: {
                backgroundColor: 'black',
                border: 'none',
                color: 'white'
              },
              modules: ['Resize', 'DisplaySize', 'Toolbar']
            }
          }
        }
      },
      dictionary: {
        custom: {
          title: {
            required: () => '请输入标题',
            max: '标题长度不能超过50个字符'
            // custom messages
          },
          subTitle: {
            required: () => '请输入副标题',
            max: '副标题长度不能超过100个字符'
            // custom messages
          },
          selectedFruits: {
            required: '请选择接收组织'
          },
          selectedItem: {
            required: '请选择消息类型'
          },
          url: {
            required: '请输入超链接地址',
            url: '请输入正确的超链接地址'
          }
        }
      }
    }
  },
  created () {
    let item = this.$store.state['sys.tenant'].items
    this.fruits = item.filter(item => item.type === 1)
    // this.newfruits = item.filter(item => item.type === 1)
    this.content = ''
  },
  mounted () {
    this.$validator.localize('zn', this.dictionary)
  },
  computed: {
    likesAllOrg () {
      return this.selectedFruits.length === this.fruits.length
    },
    likesSomeOrg () {
      return this.selectedFruits.length > 0 && !this.likesAllOrg
    },
    icon () {
      console.log(this.likesAllOrg)
      console.log(this.likesSomeOrg)
      if (this.likesAllOrg) return 'check_box'
      if (this.likesSomeOrg) return 'indeterminate_check_box'
      return 'check_box_outline_blank'
    }
  },
  watch: {
    // 如果 `selectedItem` 发生改变，这个函数就会运行
    selectedItem: function (newQuestion, oldQuestion) {
      console.log(newQuestion)
      console.log(newQuestion === 'original')
      if (newQuestion === 'original') {
        this.isAgri = true
      } else {
        this.isAgri = false
      }
    },
    show: function (newQuestion, oldQuestion) {
      console.log(1111111)
      console.log(newQuestion)
      if (newQuestion === true) {
        this.selectedItem = 'original'
        this.isAgri = true
      }
    },
    // :search-input.sync="search"
    search (val) {
      val && val !== this.select && this.querySelections(val)
    }
  },
  methods: {
    querySelections (v) {
      // Simulated ajax query
      setTimeout(() => {
        console.log((v || '').toLowerCase())
        this.newfruits = this.fruits.filter(e => {
          console.log((e.name || '').toLowerCase())
          return (e.name || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1
        })
      }, 500)
    },
    handleMaxSize (file) {
      this.$toasted.global.error()
    },
    handleSuccess (res) {
      // 获取富文本组件实例
      let quill = this.$refs.QuillEditor.quill
      // 如果上传成功
      if (res) {
        console.log(res.data[0].resourcepath)
        // 获取光标所在位置
        let length = quill.getSelection().index
        // 插入图片，res为服务器返回的图片链接地址
        quill.insertEmbed(length, 'image', res.data[0].resourcepath)
        // 调整光标到最后
        quill.setSelection(length + 1)
      }
    },
    async queryStaffTenant (id) {
      let { items } = await (mongo.db('iss').collection('tenant.staff').find({ filter: { _id: { $oid: id } } }))
      console.log(items[0].tenants)
    },

    toggle () {
      this.$nextTick(() => {
        if (this.likesAllOrg) {
          console.log(11111111)
          this.selectedFruits = []
          // this.newfruits = this.fruits.slice()
        } else {
          console.log(22222222222)
          this.selectedFruits = this.fruits.slice()
          // this.fruits.forEach(element => {
          //   this.selectedFruits.push(element._id.$oid)
          // })
        }
      })
    },
    close () {
      this.title = ''
      this.subTitle = ''
      this.selectedFruits = []
      this.content = ''
      this.url = ''
      this.$validator.reset()
      this.$emit('update:show', false)
    },
    submit () {
      this.$validator.validateAll().then((result) => {
        let messageArr = []
        if (result) {
          if (this.$refs.form.validate()) {
            if (this.selectedFruits.length >= 1) {
              this.selectedFruits.forEach(element => {
                let message = {}
                let param = {}
                if (element._id) {
                  Object.assign(message, { tenant: { $oid: element._id.$oid } })
                } else {
                  Object.assign(message, { tenant: { $oid: element } })
                }
                console.log(11111111)
                console.log(this.selectedItem)
                console.log(this.selectedItem === 'reference')
                if (this.selectedItem === 'reference') {
                  console.log(11111111)
                  Object.assign(param, { url: this.url })
                }
                Object.assign(param, { title: this.title })
                Object.assign(param, { subTitle: this.subTitle })
                Object.assign(param, { content: this.content })
                Object.assign(message, { param: param })
                Object.assign(message, { type: this.selectedItem })
                Object.assign(message, { staff: { $oid: this.$store.state['tenant.staff'].current._id.$oid } })
                messageArr.push(message)
              })
              mongo.db().collection('message').insert(messageArr).then((data) => {
                console.log(data)
                data.forEach(async element => {
                  let obj = {}
                  await mongo.db().collection('message').find({ filter: { '_id': { $oid: element._id.$oid } } }).then((data) => {
                    obj.articleId = data.items[0]._id.$oid
                    obj.title = data.items[0].param.title
                    obj.subTitle = data.items[0].param.subTitle
                    obj.articleType = data.items[0].type
                    obj.tenant = data.items[0].tenant.$oid
                    console.log(obj)
                    axios({
                      method: 'post',
                      url: `${store.state.config.knServer}/fwk-service-pusher/agri/publish`,
                      data: obj
                    }).then((res) => {

                    })
                  })
                })
                this.title = ''
                this.subTitle = ''
                this.selectedFruits = []
                this.content = ''
                this.url = ''
                this.$validator.reset()
                this.$emit('update:show', false)
                this.$emit('refresh')
              })
            }
          }
        }
      })
    },
    onEditorBlur (quill) {
      console.log('editor blur!', quill)
    },
    onEditorFocus (quill) {
      console.log('editor focus!', quill)
    },
    onEditorReady (quill) {
      console.log('editor ready!', quill)
    },
    onEditorChange ({ quill, html, text }) {
      console.log('editor change!', quill, html, text)
      this.content = html
    },
    remove (item) {
      console.log(this.selectedFruits)
      console.log(item)
      console.log(this.findIndex(this.selectedFruits, item))
      if (this.selectedFruits[0]._id) {
        const index = this.findIndex(this.selectedFruits, item)
        if (index >= 0) this.selectedFruits.splice(index, 1)
      } else {
        const index = this.selectedFruits.indexOf(item._id.$oid)
        if (index >= 0) this.selectedFruits.splice(index, 1)
      }
    },
    /**
     *判断对象在数组中的位置
     */
    findIndex (l, o) {
      var objStr = JSON.stringify(o)
      return l.reduce((index, ele, i) => {
        if (JSON.stringify(ele) === objStr) {
          return i
        } else {
          return index
        }
      }, -1)
    }
  }
}
</script>

<style lang="scss">
.quill-editor {
  height: 500px;
}
.ql-snow .ql-formats .ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}

.ql-snow .ql-formats .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-formats .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-label[data-value="small"]::before,
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-label[data-value="large"]::before,
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-label[data-value="huge"]::before,
.ql-snow
  .ql-formats
  .ql-picker.ql-size
  .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}
.ql-snow .ql-formats .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-formats .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}
.ql-snow
  .ql-formats
  .ql-picker.ql-font
  .ql-picker-label[data-value="serif"]::before,
.ql-snow
  .ql-formats
  .ql-picker.ql-font
  .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}
.ql-snow
  .ql-formats
  .ql-picker.ql-font
  .ql-picker-label[data-value="monospace"]::before,
.ql-snow
  .ql-formats
  .ql-picker.ql-font
  .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
.ivu-upload {
  display: none;
}
</style>
