<template lang="pug">
v-card.pt-2(class="page-content")
  v-layout(row)
    //- v-flex.py-2(xs6)
    //-   v-btn(router, :to="{name: 'farmeragreements'}" small outline :color="theme" )
    //-     v-icon keyboard_backspace
    //-     span 返回
    //- v-flex.py-2(xs6 class="text-xs-right" )
    //-   v-btn(router small icon :to="{name: 'editagreement', query: {id: id}}" v-if="$authed('PUT','farmeragreements/main/farmeragreements/editagreement')")
    //-     v-icon(color="grey darken-1") edit
    //-   v-btn(small icon @click="removeagreement(agreement)" v-if="$authed('DELETE','farmeragreements/main')")
    //-     v-icon(color="red darken-2") delete
    v-flex(xs12)
      go-back(:name="viewTitle")
        span(style="float: right")
          v-btn(router fab small icon :to="{name: 'editagreement', query: {id: id}}" v-if="$authed('PUT','farmeragreements/main/farmeragreements/editagreement')")
            v-icon(color="grey darken-1") edit
          v-btn(fab small icon @click="removeagreement(agreement)" v-if="$authed('DELETE','farmeragreements/main')")
            v-icon(color="red darken-2") delete
  v-divider
  v-layout.pt-3(row)
    v-flex(xs12 class="labelContent") 协议编号：{{ agreement.document  }}
  v-layout.pt-4(row)
    v-flex(xs12 class="labelContent") 协议名称：{{ agreement.name }}
  v-layout.pt-4(row)
    v-flex(xs12 class="labelContent") 开始时间：{{ getFormtedTime(agreement.begintime) }}
  v-layout.pt-4(row)
    v-flex(x12 class="labelContent") 结束时间：{{ getFormtedTime(agreement.endtime) }}
  v-layout.pt-4(row)
    v-flex(x12 class="labelContent") 协议执行状态：{{ agreement.actstatename }}
  v-layout.pt-4(row)
    v-flex(x12 class="labelContent") 合作农户：{{ (agreement.peasantidname ? agreement.peasantidname : '--')  + (agreement.peasantidlinkphone ? '(' + agreement.peasantidlinkphone + ')' : '')}}
  v-layout.pt-4(row)
    v-flex(xs2 class="labelContent") 附件列表：
    v-flex(xs5)
      div(row v-for="item in agreement.resourcehistorylist" :key="item.resourcepath")
        div(class="attach")
          a(:href="item.resourcepath" target="_blank") {{ item.resourcename }}
  v-layout.pt-3(row)
    v-flex(xs12 class="labelContent") 其他说明： {{ agreement.explain ? agreement.explain : '--' }}
  v-modaldialog(:isShow="prompt.dialog" :title="prompt.title")
    div {{prompt.body}}
    template(slot="btnActions")
      v-divider
      v-card-actions
        v-spacer
        v-btn(flat, color="primary", @click.native.stop="Cancel") 取消
        v-btn(v-if="prompt.ok", color="primary", flat, @click.native.stop="Ok") 确定    
</template>

<script>
import { excuteApis } from '@/apis'
import { parseTime } from '@/utils'
export default {
  data () {
    return {
      agreement: {
        document: '',
        name: '',
        begintime: '',
        endtime: '',
        peasantid: '',
        explain: '',
        resourcehistorylist: []
      },
      datestartend: [],
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      id: null
    }
  },
  watch: {},
  computed: {
    viewTitle () {
      let title = ''
      this.agreement.name ? (title = this.agreement.name) : ''
      return title
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.id = this.$route.query.id
    this.fetchAgreementData()
  },
  methods: {
    fetchAgreementData () {
      excuteApis(Object.assign({}, {id: this.id}), global.constant.archivesApis, 'peasantprotocol', 'queryById').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.agreement = res.data
          if (this.agreement) {
            let btime = this.agreement.begintime
            let etime = this.agreement.endtime
            this.agreement.begintime = this.getFormtedTime(btime)
            this.agreement.endtime = this.getFormtedTime(etime)
          }
        }
      })
    },
    downloadfile (selectedItem) {
      window.open(selectedItem.resourcepath)
    },
    getFormtedTime (time) {
      try {
        if (time) {
          time = new Date(time)
          return parseTime(time, 'yyyy-MM-dd')
        } else {
          return '--'
        }
      } catch (err) {
        return '--'
      }
    },
    removeagreement (item) {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
      this.selectedItemId = this.agreement.id
    },
    Ok () {
      let requestParams = {
        id: this.selectedItemId
      }
      excuteApis(requestParams, global.constant.archivesApis, 'peasantprotocol', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.$router.push({name: 'farmeragreements'})
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    Cancel () {
      this.prompt.dialog = false
    }
  }
}
</script>

<style scoped>
  .list__tile {
    padding: 0 0;
  }
  .resource {
    color: rgba(0,0,0,0.54);
  }
  .resource:hover {
    cursor: pointer;
    border: 1px solid rgb(226, 217, 217);
    color: black;
  }
  .backbtn {
    cursor: pointer;
    height: 35px;
  }
  .attach {
    border: 1px solid rgb(216, 210, 210);
    margin-top: 5px;
    padding: 10px 10px;
  }
  .attach:hover {
    background: rgb(248, 245, 245);
    cursor: pointer;
  }
  .labelContent {
    padding-left: 36px;
  }
</style>
