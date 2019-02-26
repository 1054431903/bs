<template>
<v-card class="page-content pt-2">
  <v-layout row  class="pl-0">
    <!-- <v-flex xs12 sm12 md2 lg2 xl1 >
      <v-btn @click="goBackList()" :color="theme" small flat outline>
        <v-icon>keyboard_backspace</v-icon>
        <span>返回</span>
      </v-btn>
    </v-flex>
    <v-flex class="text-xs-center" v-bind:class="titleClass">
      <div class="infortitle" :title="information.title"> {{information.title}} </div>
    </v-flex>
    <v-flex xs12 sm12 md2 lg2 xl2 class="text-xs-right"  v-if="!isview">
      <div>
        <v-btn small outline :color="theme" @click="editInformation(information)" v-if="$authed('PUT','infopublish/sopedit')">
          <v-icon>edit</v-icon>
          <span class="pl-2">编辑</span>
        </v-btn>
        <v-btn small outline color="error" @click="removeInformation" v-if="$authed('DELETE','system/infopublish/main')">
          <v-icon>delete</v-icon>
          <span class="pl-2">删除</span>
        </v-btn>
      </div>
    </v-flex> -->
    <go-back :name="viewTitle">
      <span style="float: right;">
        <v-btn icon small fab  title="编辑" @click="editInformation(information)" v-if="$authed('PUT','infopublish/sopedit')">
          <v-icon color="red darken-1">edit</v-icon>
        </v-btn>
        <v-btn icon small fab  title="删除" @click="removeInformation" v-if="$authed('DELETE','system/infopublish/main')">
          <v-icon color="red darken-2">delete</v-icon>
        </v-btn>
      </span>
    </go-back>
  </v-layout>
  <v-divider></v-divider>
  <v-layout row class="pt-1 pb-1">
    <v-flex sm6>
      <v-avatar size="40px">
        <img v-if="information.photopath" :src="information.photopath"/>
        <img v-else src='/static/moren.png'/>
      </v-avatar>
      <span class="pubusername"> {{information.pubusername ? information.pubusername : '--' }}&nbsp;&nbsp;{{information.puborgname ? '(' + information.puborgname + ')' : ''}} </span>
    </v-flex>
    <v-flex sm6 class="pt-2">
      <div class="text-xs-right"> {{information.pubtime ? information.pubtime.substr(0, 10) : ''}} </div>
    </v-flex>
  </v-layout>
  <v-divider></v-divider>
  <v-layout row class="pt-1">
    <v-flex sm1>
      <div class="phaselabel"> 当前阶段:</div>
    </v-flex>
    <v-flex sm3>
      <v-select
          v-bind:items="phases"
          v-model="phase"
          item-text="name"
          item-value="id"
          single-line
          no-data-text="无"
      ></v-select>
    </v-flex>
  </v-layout>
  <div v-for="(inforDetail, index) in informationDetails" :key="inforDetail.id">
    <v-layout row>
      <v-flex xs1>
        <v-avatar v-bind:class="{ 'primary': inforDetail.id === phase, 'grey': inforDetail.id !== phase }"><span class="white--text headline">{{index + 1}}</span></v-avatar>
        <div class="labelline" v-if="index !== informationDetails.length - 1"></div>
      </v-flex>
      <v-flex xs11>
        <v-card :raised="inforDetail.id === phase" class="pa-2 ma-1">
          <v-layout row wrap>
            <v-flex xs12>{{inforDetail.name ? inforDetail.name : '--'}}</v-flex>
          </v-layout>
          <v-divider class="mt-2"></v-divider>
          <v-layout row wrap class="py-2">
            <v-flex xs12>
              <div v-html="inforDetail.content" :ref="'content' + inforDetail.id"  v-bind:class="{ content: false }"></div>
            </v-flex>
          </v-layout>
          <!-- <v-layout row wrap class="py-2" :ref="'btn' + inforDetail.id">
            <v-btn @click="isHide = !isHide">{{isHide?'显示更多':'向上收起'}}</v-btn>
          </v-layout> -->
        </v-card>
      </v-flex>
    </v-layout>
  </div>
  <v-modaldialog v-model="prompt.dialog" :title="prompt.title">
      <div> {{ prompt.body }} </div>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn v-if="prompt.ok" :color="theme"  flat  @click.native="tipsOk"> 确定 </v-btn>
          <v-btn flat @click.native.stop="tipsCancel"> 取消 </v-btn>
        </v-card-actions>
      </template>
  </v-modaldialog>
</v-card>
</template>

<script>
import { excuteApis } from '@/apis'
export default {
  data () {
    return {
      phase: 1,
      information: {},
      informationDetails: [],
      isHide: true,
      phases: [],
      prompt: {
        dialog: false,
        title: '提示信息',
        body: '你确定要删除该记录吗？',
        ok: true
      },
      btns: null,
      theme: 'primary',
      backFlag: 0, // 1: 'gridList' 0: 'cardList'
      id: null
    }
  },
  methods: {
    goBackList () {
      if (this.backFlag) {
        this.$router.push({name: 'sop'})
      } else {
        this.$router.push({name: 'infopublish'})
      }
    },
    fetchDataInformation () {
      let requestParams = {
        id: this.id
      }
      excuteApis(requestParams, global.constant.systemApis, 'information', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          if (res.data.length > 0) {
            this.information = res.data[0]
          }
        }
      })
    },
    fetchDataContent () {
      let requestParams = {
        informationid: this.id
      }
      excuteApis(requestParams, global.constant.systemApis, 'informationContent', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.informationDetails = res.data
          if (this.informationDetails.length > 0) {
            for (let i = 0; i < this.informationDetails.length; i++) {
              let infordetail = {
                id: this.informationDetails[i].id,
                name: this.informationDetails[i].name ? this.informationDetails[i].name : '--'
              }
              this.phases.push(infordetail)
            }
            this.phase = this.phases[0].id
          }
        }
      })
    },
    editInformation (information) {
      this.$router.push({name: 'sopedit', query: {id: information.id}})
    },
    removeInformation () {
      this.prompt.title = '提示信息'
      this.prompt.body = '你确定要删除该记录吗？'
      this.prompt.dialog = true
      this.prompt.ok = true
    },
    tipsOk () {
      let requestParams = {
        id: this.information.id
      }
      excuteApis(requestParams, global.constant.systemApis, 'information', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.prompt.dialog = false
          this.$router.go(-1)
        } else {
          this.prompt.title = '提示信息'
          this.prompt.body = res.message
          this.prompt.ok = false
        }
      })
    },
    tipsCancel () {
      this.prompt.dialog = false
    }
  },
  watch: {},
  computed: {
    titleClass: function () {
      return {
        xs12: true,
        sm12: true,
        md9: !this.isview,
        lg9: !this.isview,
        xl9: !this.isview,
        md11: this.isview,
        lg11: this.isview,
        xl11: this.isview
      }
    },
    viewTitle () {
      let title = ''
      this.information.title ? (title = this.information.title) : ''
      return title
    }
  },
  created () {
    this.theme = global.helper.ls.get('theme')
    this.backFlag = this.$route.query.backFlag
    this.id = this.$route.query.id
    // this.information = this.$route.query.information
    this.isview = this.$route.query.isview
    this.fetchDataInformation()
    this.fetchDataContent()
  },
  mounted: function () {},
  updated: function () {
  //   if (this.$refs) {
  //     for (var ref in this.$refs) {
  //       if (ref.indexOf('content') !== -1) {
  //         let refContentHeight = this.$refs[ref][0].clientHeight
  //         if (refContentHeight > 150) {
  //           let btnContent = ref.replace('content', 'btn')
  //           this.$refs[btnContent][0].style.display = false
  //         } else {
  //           let btnContent = ref.replace('content', 'btn')
  //           this.$refs[btnContent][0].style.display = true
  //         }
  //       }
  //     }
  //   }
  }
}
</script>

<style scoped>
  .backbtn {
    cursor: pointer;
  }
  .rowone {
    padding: 0 0;
  }
  .titletext {
    padding-top: 10px;
  }
  .pubusername {
    margin-left: 10px;
  }
  .phaselabel {
    padding-top: 5px;
  }
  .input-group {
    padding: 1px;
  }
  .content {
    max-height: 150px;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
  }
  .labelline {
    border: 1px solid rgba(180, 176, 176, 0.54);
    width: 1px;
    height: 100%;
    margin-left: 21px;
  }
  .infortitle {
    line-height: 45px;
    font-weight: 350;
    font-size: 15px;
    white-space:normal;
    word-break:break-all;
    overflow:hidden;
    TEXT-ALIGN: justify
  }
</style>
