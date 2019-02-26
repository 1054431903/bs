<template lang="pug">
div
  v-modaldialog(
    :isShow="toDoDialog",
    :title="doSomethingTitle",
    :modalMaxWidth="600",
    :bodyHeight="300")
    v-layout(row, wrap)
      v-flex(xs12)
        v-textarea.pt-0(
          :placeholder="placeholder"
          v-model="todo.content"
          rows="5"
          auto-grow
          hide-details
          counter="500"
          maxlength="500")
    v-layout(row, wrap)
      v-flex(xs12 class="miniattach")
        v-minidropzoneupload(:files.sync="todo.attachments")  
      //- v-flex(xs11)
      //-   div(class="attachlabel") 最多添加10个文件，单个文件控制在10M以内
    v-layout(row, wrap)
      v-flex(xs12)
        div(row v-for="(item, index) in todo.attachments" :key="item.resourcepath")
          div(class="attach" @mouseenter="getSelectedAttach(index)" @mouseleave="leaveSelectedAttach")
            img(:src="getIcon(item.resourcename)" width="auto" height="20" style="vertical-align: middle; margin-right: 10px;")
            a(:href="item.resourcepath" target="_blank") {{ item.resourcename }}&nbsp;&nbsp;
            v-btn(title="删除" fab small icon @click="removeResourece(item)" v-if="selectedAttach === item.resourcepath")
              v-icon(color="red darken-2") delete           
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat color="primary" @click.native="cancel") 取消
        v-btn(flat color="primary" @click.native="save") 完成
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
import VMinidropzoneupload from './DropzoneUpload.vue'
import mongo from '@vehiclecloud/mongo-rest-client'
import { getIconPath } from '@/utils'
import { excuteApis } from '@/apis'
export default {
  name: 'v-farmertaskstodo',
  props: {
    visible: {
      type: String,
      default: 'IS_NOE'
    },
    curtask: {
      type: Object,
      default: () => { }
    }
  },
  data () {
    const TASK_TO_DO = {
      'IS_DONE': true,
      'IS_ACCEPT': true,
      'IS_REJECT': true,
      'IS_FINISH': true,
      'IS_NONE': false
    }
    return {
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '保存成功',
        ok: true
      },
      toDoDialog: false,
      taskToDo: TASK_TO_DO,
      doSomethingTitle: '',
      placeholder: '',
      todo: {
        content: '',
        attachments: []
      },
      user: null,
      isSaveClick: false, // 是否已经点击表单完成(保存)按钮
      selectedItem: null,
      selectedAttach: null
    }
  },
  computed: {},
  mounted () { },
  watch: {
    visible: function (v) {
      this.disposeModalDialog(v)
    }
  },
  methods: {
    getIcon (rescourcename) {
      return getIconPath(rescourcename)
    },
    getSelectedAttach (index) {
      this.selectedAttach = this.todo.attachments[index].resourcepath
    },
    leaveSelectedAttach () {
      this.selectedAttach = null
    },
    removeResourece (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItem = item
    },
    Ok () {
      let index = this._.findIndex(this.todo.attachments, data => {
        return data.resourcename === this.selectedItem.resourcename
      })
      this.todo.attachments.splice(index, 1)
      this.prompt.dialog = false
    },
    back () {
      this.prompt.dialog = false
    },
    save () {
      if (this.visible === 'IS_NONE') return
      let curtime = new Date().getTime()
      let content = Object.assign({}, { content: this.todo.content })
      let attachments = []
      this.todo.attachments.map(attachment => {
        attachments.push(Object.assign({}, { name: attachment.resourcename, url: attachment.resourcepath }))
      })
      attachments.length > 0 ? (Object.assign(content, { attachments })) : null
      // 存储task业务逻辑
      let stages = {
        'IS_DONE': Object.assign({}, { stage: 'completion', finish: true, finishOn: curtime, finishContent: content }),
        'IS_ACCEPT': Object.assign({}, { stage: 'execution', approval: 'accepted', approvalOn: curtime, approvalContent: content }),
        'IS_REJECT': Object.assign({}, { stage: 'approval', approval: 'rejected', finish: true, approvalOn: curtime, approvalContent: content }),
        'IS_FINISH': Object.assign({}, { finish: true, finishOn: curtime, finishContent: content })
      }
      let filter = { _id: { $oid: this.curtask.getId() } }
      let update = { $set: stages[this.visible] }
      // 存储task.trends业务逻辑
      // let trendsfilter = {
      //   'IS_DONE': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'completion' }),
      //   'IS_ACCEPT': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'execution' }),
      //   'IS_REJECT': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'approval' }),
      //   'IS_FINISH': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'suspension' })
      // }
      let trends = {
        'IS_DONE': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'completion', content: content.content, attachments: content.attachments, creator: { $oid: this.user.supportid } }),
        'IS_ACCEPT': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'approval', approval: 'accepted', content: content.content, attachments: content.attachments, creator: { $oid: this.user.supportid } }),
        'IS_REJECT': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'approval', approval: 'rejected', content: content.content, attachments: content.attachments, creator: { $oid: this.user.supportid } }),
        'IS_FINISH': Object.assign({}, { task: { $oid: this.curtask.getId() }, type: 'suspension', content: content.content, attachments: content.attachments, creator: { $oid: this.user.supportid } })
      }
      // console.log(trends[this.visible])
      mongo.db().collection('task').update({ filter: filter, update: update }).then(response => {
        if (!response.writeError && !response.writeConcernError) {
          // console.log('to do task successful.')
          // 存储 task.trends
          // let trendsupdate = { $set: trends[this.visible] }
          mongo.db().collection('task.trends').insert(trends[this.visible]).then(response => {
            // console.log('to do task.trends successful.')
            this.$emit('update:visible', 'IS_NONE')
          })
          // 发消息
          // 审批通过消息推送
          if (this.visible === 'IS_ACCEPT') {
            excuteApis(this.curtask.getId(), global.constant.pusherApisUrls, 'task', 'execution').then(data => {
              // let res = data.data
              // if (res.flag === 1 && res.data.length > 0) {
              //   this.ownerships = this.ownerships.concat(res.data)
              // }
              console.log(data)
            })
          }
        }
      }).catch((err) => {
        console.log(err)
      })
      this.isSaveClick = true
    },
    cancel () {
      this.$emit('update:visible', 'IS_NONE')
    },
    disposeModalDialog (v) {
      this.isSaveClick = false
      this.toDoDialog = this.taskToDo[v]
      this.todo.content = ''
      this.todo.attachments = []
      if (v === 'IS_DONE') {
        this.doSomethingTitle = '任务完成情况汇报'
        this.placeholder = '填写任务完成情况'
      } else if (v === 'IS_ACCEPT') {
        this.doSomethingTitle = '审批意见'
        this.placeholder = '填写审批(通过)意见'
      } else if (v === 'IS_REJECT') {
        this.doSomethingTitle = '审批意见'
        this.placeholder = '填写审批(不通过)意见'
      } else if (v === 'IS_FINISH') {
        this.doSomethingTitle = '中止原因'
        this.placeholder = '填写中止原因'
      } else {
        this.doSomethingTitle = ''
        this.placeholder = ''
      }
    }
  },
  created () {
    this.user = global.helper.ls.get('user')
  },
  components: {
    VMinidropzoneupload
  }
}
</script>
<style scoped>
.miniattach {
  line-height: 30px;
}
.attachlabel {
  line-height: 30px;
  height: 30px;
  color: rgba(92, 64, 64, 0.47);
}
.attach {
  border: 1px solid rgb(216, 210, 210);
  margin-top: 5px;
  padding: 10px 10px;
  width: 100%;
}
.attach:hover {
  background: rgb(248, 245, 245);
  cursor: pointer;
}
</style>
