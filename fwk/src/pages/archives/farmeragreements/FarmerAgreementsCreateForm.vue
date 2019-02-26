<template>
  <v-card class="page-content pt-0">
    <v-form v-model="valid"
            ref="form"
            lazy-validation
            autocomplete="off">
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*协议编号</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field label="请输入协议编号"
                        name="name"
                        single-line
                        v-model="agreement.document"
                        :rules="rules.document"
                        maxlength="20"
                        :count="20"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 协议名称 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>协议名称</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-text-field label="请输入协议名称"
                        name="linkphone"
                        single-line
                        v-model="agreement.name"
                        :rules="rules.name"
                        maxlength="50"
                        :count="50"></v-text-field>
        </v-flex>
      </v-layout>

      <!-- 协议开始时间 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*开始时间</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-customdatepicker datePickerMenu="begintime"
                              pickerLabel="选择开始时间"
                              :selectedDate.sync="agreement.begintime"
                              :rules="rules.required"
                              :allowedDates="begintimeLimit">
          </v-customdatepicker>
          <!--<v-menu-->
          <!--lazy-->
          <!--:close-on-content-click="false"-->
          <!--transition="scale-transition"-->
          <!--offset-y-->
          <!--full-width-->
          <!--:nudge-right="40"-->
          <!--max-width="290px"-->
          <!--min-width="290px"-->
          <!--&gt;-->
          <!--<v-text-field-->
          <!--slot="activator"-->
          <!--label="请选择协议开始时间"-->
          <!--single-line-->
          <!--v-model="agreement.begintime"-->
          <!--append-icon="event"-->
          <!--readonly  -->
          <!--:rules="rules.required"          -->
          <!--&gt;</v-text-field>-->
          <!--<v-date-picker v-model="agreement.begintime" no-title scrollable actions locale="zh-CN" :allowed-dates="begintimeLimit">-->
          <!--<template slot-scope="{ save, cancel }">-->
          <!--<v-card-actions>-->
          <!--<v-spacer></v-spacer>-->
          <!--<v-btn flat @click="cancel">取消</v-btn>-->
          <!--<v-btn flat color="primary" @click="save">确定</v-btn>-->
          <!--</v-card-actions>-->
          <!--</template>-->
          <!--</v-date-picker>-->
          <!--</v-menu>-->
        </v-flex>
      </v-layout>

      <!-- 协议结束时间 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*结束时间</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-customdatepicker datePickerMenu="endtime"
                              pickerLabel="选择结束时间"
                              :selectedDate.sync="agreement.endtime"
                              :rules="rules.required"
                              :allowedDates="endtimeLimit">
          </v-customdatepicker>
          <!--<v-menu-->
          <!--lazy-->
          <!--:close-on-content-click="false"-->
          <!--transition="scale-transition"-->
          <!--offset-y-->
          <!--full-width-->
          <!--:nudge-right="40"-->
          <!--max-width="290px"-->
          <!--min-width="290px"-->
          <!--&gt;-->
          <!--<v-text-field-->
          <!--slot="activator"-->
          <!--label="请选择协议结束时间"-->
          <!--single-line-->
          <!--v-model="agreement.endtime"-->
          <!--append-icon="event"-->
          <!--readonly-->
          <!--:rules="rules.required"-->
          <!--&gt;</v-text-field>-->
          <!--<v-date-picker v-model="agreement.endtime" no-title scrollable actions locale="zh-CN" :allowed-dates="endtimeLimit">-->
          <!--<template slot-scope="{ save, cancel }">-->
          <!--<v-card-actions>-->
          <!--<v-spacer></v-spacer>-->
          <!--<v-btn flat @click="cancel">取消</v-btn>-->
          <!--<v-btn flat :color="theme" @click="save">确定</v-btn>-->
          <!--</v-card-actions>-->
          <!--</template>-->
          <!--</v-date-picker>-->
          <!--</v-menu>-->
        </v-flex>
      </v-layout>

      <!-- 合作农户 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>*合作农户</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-select v-bind:items="peasants"
                    v-model="agreement.peasantid"
                    item-text="label"
                    item-value="id"
                    label="请选择合作农户"
                    single-line
                    no-data-text="无"
                    :rules="rules.required"
                    class="selected">
          </v-select>
        </v-flex>
      </v-layout>

      <!-- 附件 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>附件</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <dropzone ref="agreementsDropzone"
                    id="dropzone"
                    :options="dropzoneOptions"
                    v-on:vdropzone-success="agreementUploadSuccess"
                    v-on:vdropzone-error="agreementUploadError"
                    v-on:vdropzone-file-added="fileAdded"
                    v-on:vdropzone-sending="fileSending">
          </dropzone>
        </v-flex>
      </v-layout>

      <!-- 附件列表 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <div row
               v-for="(item, index) in agreement.resourcehistorylist"
               :key="item.resourcepath">
            <div class="attach"
                 @mouseenter="getSelectedAttach(index)">
              <a :href="item.resourcepath"
                 target="_blank">
                {{ item.resourcename }}
              </a>
              &nbsp;&nbsp;
              <v-avatar class="grey delBtn"
                        size="18px"
                        @click="removeResourece(item)"
                        v-if="selectedAttach === item.resourcepath">
                <img :src="`static/sop/ic_close_white_24px.svg`" />
              </v-avatar>
            </div>
          </div>
        </v-flex>
      </v-layout>

      <!-- 其他说明 -->
      <v-layout row>
        <v-flex xs4
                sm4
                md2
                lg2
                xl1>
          <v-subheader>其他说明(500字内)</v-subheader>
        </v-flex>
        <v-flex xs8
                sm8
                md10
                lg5
                xl5>
          <v-textarea name="explain"
                      v-model="agreement.explain"
                      :count="500"
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
    <v-layout row,
              justify-center>
      <v-modaldialog :isShow="prompt.dialog"
                     :title="prompt.title">
        <div> {{prompt.body}}</div>
        <template slot="btnActions">
          <v-divider></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native.stop="back">取消</v-btn>
            <v-btn v-if="prompt.ok"
                   color="primary"
                   flat
                   @click.native.stop="Ok">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>
    </v-layout>
    <v-layout row,
              justify-center>
      <v-dialog v-model="uploadDialogTips.dialog"
                persistent
                max-width="290">
        <v-card>
          <v-card-title class="headline"> {{ uploadDialogTips.title }} </v-card-title>
          <v-card-text> {{ uploadDialogTips.body }} </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="prompt.ok"
                   :color="theme"
                   flat
                   @click.native="uploadDialogTips.dialog=false"> 确定 </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-layout>
  </v-card>
</template>

<script>
import { excuteApis } from '@/apis'
import { Toast } from '@/models/toast'
import { parseTime } from '@/utils'
export default {
  data () {
    return {
      valid: true,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        name: [
          (v) => v.length <= 50 || '长度不超过50个字符'
        ],
        document: [
          (v) => !!v || '必填项',
          (v) => v.length <= 20 || '长度不超过20个字符'
        ],
        explain: [
          (v) => v.length <= 500 || '长度不超过500个字符'
        ]
      },
      agreement: {
        document: '',
        name: '',
        begintime: null,
        endtime: null,
        peasantid: '',
        explain: '',
        resourcehistorylist: []
      },
      peasants: [],
      peasant: null,
      selectdate: null,
      date: null,
      dateFormatted: null,
      dropzoneOptions: {
        url: global.constant.uploadFileUrl + '?islogin=0',
        thumbnailWidth: 180,
        maxFilesize: 20,
        maxFiles: 1,
        addRemoveLinks: true,
        dictDefaultMessage: "<i class='fa fa-cloud-upload'></i>点击选择文件或拖拽文件至此上传",
        dictFileTooBig: '文件不能大于20M', // 文件过大时显示的文字信息
        dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
        dictRemoveFile: '移除', // 移除按钮自定义文字
        dictInvalidFileType: '不支持该类型文件' // 文件类型无效时提示
      },
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      theme: 'primary',
      selectedAttach: null,
      uploadDialogTips: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      }
    }
  },
  watch: {},
  computed: {},
  created () {
    this.theme = global.helper.ls.get('theme')
    this.toast = new Toast(this, '上传中......', null)
    this.peasant = this.$route.query.peasant
    this.agreement.peasantid = this.peasant ? this.peasant.id : ''
    const today = parseTime(new Date(), 'yyyy-MM-dd')
    this.agreement.begintime = today
    this.fetchPeasant()
  },
  methods: {
    begintimeLimit (val) {
      if (this.agreement.endtime) {
        return val <= this.agreement.endtime
      } else {
        return true
      }
    },

    endtimeLimit (val) {
      if (this.agreement.begintime) {
        return val >= this.agreement.begintime
      } else {
        return true
      }
    },

    removeResourece (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItem = item
    },

    /**
     * 获取合作农户
     */
    fetchPeasant () {
      this.peasants = []
      excuteApis({}, global.constant.archivesApis, 'peasantprotocol', 'peasantlist').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.ps = res.data
          this.ps.map(item => {
            item.label = item.linkphone ? (item.name ? (item.name + '(' + item.linkphone + ')') : '') : item.name
            this.peasants.push(item)
          })
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
    agreementUploadError (file) {
      let self = this
      self.toast.close()
      this.toast.setContent('文件上传出错')
      this.toast.showAutoClose()
      setTimeout(() => {
        self.$refs.agreementsDropzone.removeFile(file)
      }, 2000)
    },
    agreementUploadSuccess (file, response) {
      this.toast.close()
      let res = response
      if (res.flag === 1) {
        this.agreement.resourcehistorylist.push(res.data[0])
      } else if (res.flag === 0) {
        this.toast.setContent('文件上传出错')
        this.toast.showAutoClose()
      }
      this.$refs.agreementsDropzone.removeFile(file)
    },
    Ok () {
      this._.remove(this.agreement.resourcehistorylist, data => {
        return data.resourcename === this.selectedItem.resourcename
      })
      this.prompt.dialog = false
    },
    cancel () {
      this.$router.go(-1)
    },
    back () {
      this.prompt.dialog = false
    },
    save () {
      if (this.$refs.form.validate()) {
        excuteApis(this.agreement, global.constant.archivesApis, 'peasantprotocol', 'add').then(data => {
          let res = data.data
          if (res.flag === 1) {
            this.$router.push({ path: 'successagreementtips', query: { agreement: res.data, opflag: 0 } })
          } else {
            this.prompt.dialog = true
            this.prompt.title = '错误提示信息'
            this.prompt.body = res.message
            this.prompt.ok = false
          }
        })
      }
    },
    getSelectedAttach (index) {
      this.selectedAttach = this.agreement.resourcehistorylist[index].resourcepath
    },
    fileAdded (file) {

    },
    fileSending (file, xhr, formData) {
      this.toast.show()
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
.attach {
  border: 1px solid rgb(216, 210, 210);
  margin-top: 20px;
  padding: 10px 10px;
}
.attach:hover {
  background: rgb(248, 245, 245);
  cursor: pointer;
}
.delBtn {
  float: right;
}
</style>
