<template>
  <v-container fluid
               pa-0
               pb-4>
    <v-card class="page-content">
      <!-- <div style="width:100px;height: 40px;background-color: #fff;text-align: center;line-height: 40px;margin-top: 10px">档案</div> -->
      <div style="padding-left:5px;">
        <!-- <div style="border-bottom:1px solid #ccc; ">
        <v-layout row justify-space-between class="text-md-left">
          <v-flex xs3 style="padding-top: 15px">{{sortName}}</v-flex>
          <v-flex xs3 class="text-xs-right text-sm-right text-md-right text-lg-right">
            <v-btn class="primary" @click="backPage">返回</v-btn>
          </v-flex>
        </v-layout>
      </div> -->
        <v-layout row
                  justify-space-between
                  style="padding:20px 0 20px 0">
          <v-flex xs3>
            <v-select v-bind:items="typeItems"
                      v-model="parentid"
                      item-text="name"
                      item-value="id"
                      label="选择类型"
                      single-line
                      no-data-text="无"
                      clearable></v-select>
          </v-flex>
          <v-flex xs3>
            <v-text-field append-icon="search"
                          label="输入名称或描述进行搜索"
                          single-line
                          clearable
                          hide-details
                          @keyup.enter="sortSearch"
                          v-model="search"></v-text-field>
          </v-flex>
          <v-flex xs3
                  pt-1>
            <v-btn color="primary"
                   @click="sortSearch()">查询</v-btn>
          </v-flex>
          <v-flex xs2
                  text-xs-right
                  v-if="$authed('POST', 'sysdictionary/sortadd')">
            <v-btn small
                   fab
                   dark
                   color="red darken-2"
                   title="添加"
                   @click="addPage">
              <v-icon>add</v-icon>
              <!-- <span>添加</span> -->
            </v-btn>
          </v-flex>
        </v-layout>
      </div>
      <v-data-table v-bind:headers="headers"
                    :items="items"
                    :no-data-text="noText"
                    hide-actions>
        <template slot="items"
                  slot-scope="props">
          <tr style="cursor: pointer"
              @click="lookPage(props.item.id, sortName, parentcode, props.item.name, props.item.typeName, props.item.descript, props.item.creattime)">
            <td>
              <v-flex text-xs-left
                      text-sm-left
                      text-md-left
                      text-lg-left>
                <template>
                  <div class="sortLine"
                       :title="props.item.typeName">
                    {{ props.item.typeName }}
                  </div>
                </template>
              </v-flex>
            </td>
            <td>
              <v-flex text-xs-left
                      text-sm-left
                      text-md-left
                      text-lg-left>
                <template>
                  <div class="sortLine"
                       :title="props.item.name">
                    {{ props.item.name }}
                  </div>
                </template>
              </v-flex>
            </td>
            <td>
              <v-flex text-xs-left
                      text-sm-left
                      text-md-left
                      text-lg-left>
                <template>
                  <div class="sortLine"
                       :title="props.item.descript">
                    {{ props.item.descript || '--' }}
                  </div>
                </template>
              </v-flex>
            </td>
            <td>
              <v-flex text-xs-center
                      text-sm-center
                      text-md-center
                      text-lg-center>
                <!--// 查看-->
                <v-btn title="查看"
                       v-if="$authed('GET', 'sysdictionary/sortlook')"
                       fab
                       flat
                       small
                       @click.stop="lookPage(props.item.id, sortName, parentcode, props.item.name, props.item.typeName, props.item.descript, props.item.creattime)">
                  <v-icon>remove_red_eye</v-icon>
                </v-btn>
                <!--// 编辑-->
                <v-btn title="编辑"
                       v-if="$authed('PUT','sysdictionary/sortedit')"
                       fab
                       flat
                       small
                       @click.stop="editPage(props.item.id, props.item.name, sortName, parentcode)">
                  <v-icon>edit</v-icon>
                </v-btn>
                <!--// 删除-->
                <v-btn title="删除"
                       v-if="$authed('DELETE', 'sysdictionary/sortmanage')"
                       fab
                       flat
                       small
                       error
                       @click.stop="deletePage(props.item.id, parentcode)">
                  <v-icon color="red darken-2">delete</v-icon>
                </v-btn>
              </v-flex>
            </td>
          </tr>
        </template>
        <template>
          <no-data-text>暂无数据</no-data-text>
        </template>
      </v-data-table>
      <!--分页-->
      <v-flex xs12
              sm12
              md12
              lg12>
        <v-layout row
                  justify-center>
          <!-- <v-pagination circle v-model="pagination.page" :length="pagination.totalPage" color="primary"></v-pagination> -->
          <v-custompagination :pagination.sync="pagination"></v-custompagination>
        </v-layout>
      </v-flex>

      <v-modaldialog :isShow="dialog"
                     persistent="persistent"
                     max-width="400">
        <span slot
              v-text="message"></span>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="dialog = false">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click.native="affirmDelete(dialog = false)">确定</v-btn>
          </v-card-actions>
        </template>
        <!-- <v-card>
        <v-card-title style="display: block" >
          <v-icon @click="dialog=false" style="display: inline; float: right; cursor: pointer">clear</v-icon>
        </v-card-title>
        <v-card-text class="text-xs-center">信息删除后将不可恢复，确认删除吗？</v-card-text>
        <v-card-actions style="padding: 30px 10px; text-align: center;display: block">
          <v-spacer></v-spacer>
          <v-btn @click.native="affirmDelete(dialog = false)" color="primary">确定</v-btn>
          <v-btn @click.native="dialog = false" style="background-color:#fff;">取消</v-btn>
        </v-card-actions>
      </v-card> -->
      </v-modaldialog>
    </v-card>
  </v-container>
</template>

<script>
// import { querySystemDictTypeDetails, querySystemDictTypeDetailsDelete } from '@/apis/systems/dictionary'
import { excuteApis } from '@/apis'
// import { trim } from '@/utils'
export default {
  data () {
    return {
      // 搜索
      message: '删除后将不可恢复，确认删除吗？',
      search: null,
      noText: '暂无数据',
      // 点击添加的弹出框
      dialog: false,
      // 分页
      sheet: false,
      typeItems: [], // 类型
      sortName: '', // 接收请求回来的类型名称
      parentcode: '', // 接收请求回来的code
      parentid: '', // 类型id
      items: [],
      pagination: {
        total: 0,
        // totalPage: null, // 总页数
        rowsPerPage: 10,  // 每页显示几条数据
        page: 1 // 当前页
        // sortBy: 'column', // 按什么排序
        // descending: false, // 递减
        // totalItems: 100 // 总条目数
      },
      id: '', // 接收点击“删除”时传过来的产品id
      /*  定义数据 */
      adds: [
        {
          value: false,
          align: 'left',
          name: '添加 ',
          calories: ' 搜索 '
        }
      ],
      headers: [
        {
          text: '类型 ',
          align: 'left',
          sortable: false,
          class: 'table-header'
        },
        {
          text: '名称 ',
          align: 'left',
          sortable: false,
          class: 'table-header'
        },
        {
          text: '描述 ',
          align: 'left',
          sortable: false,
          class: 'table-header'
        },
        {
          text: '操作 ',
          align: 'center',
          sortable: false,
          class: 'table-header'
        }
      ]
    }
  },
  watch: {
    // search: function (newVal, oldVal) {
    //   this.sortSearch(trim(newVal))
    // },
    // 'pagination.page': function (val, oldVal) {
    //   this.pagination.page = val
    //   if (val !== oldVal) {
    //     this.fetchData()
    //   }
    // },
    'pagination.page' (val) {
      this.fetchData()
    },
    'pagination.rowsPerPage' (val) {
      this.fetchData()
    }
  },
  created () { // vue 实例化后调用的函数
    this.fetchData() // 定义一个获取数据的方法（实例化后执行该方法）
    // console.log('=============================>')
    // console.log(this.$route.query.code)
  },
  methods: {
    fetchData () {
      let reParams = {}
      excuteApis(reParams, global.constant.systemApis, 'tsdictType', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.typeItems = res.data
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
      let requestParams = {}
      requestParams = this.parentcode ? Object.assign(requestParams, { parentcode: this.parentcode }) : requestParams
      requestParams = this.pagination.page || 1 ? Object.assign(requestParams, { currentPage: this.pagination.page || 1, pageSize: this.pagination.rowsPerPage || 10 }) : requestParams
      requestParams = this.parentid ? Object.assign(requestParams, { parentid: this.parentid }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'query').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.items = res.data
          this.items.forEach(element => {
            this.typeItems.forEach(item => {
              if (element.parentid === item.id) {
                element.typeName = item.name
              }
            })
          })
          // 分页
          if (res.page) {
            let page = res.page
            // this.pagination.totalItems = page.total // 总条目数
            // this.pagination.totalPage = page.totalPage // 总页数
            this.pagination.page = page.currentPage // 当前页
            this.pagination.rowsPerPage = page.pageSize // 每页显示几条数据
            this.pagination.total = page.total
          }
        } else {
          this.$toast.top('数据请求失败！')
          this.items = []
          this.noText = '暂无查询记录'
        }
      })
    },
    // 点击“返回” 返回到类别管理页面
    backPage () {
      const router = this.$router
      router.push({ name: 'sysdictionary' })
    },
    // 搜索
    sortSearch () {
      let requestParams = {}
      requestParams = this.search ? Object.assign(requestParams, { name: this.search }) : requestParams
      requestParams = this.pagination.page || 1 ? Object.assign(requestParams, { currentPage: this.pagination.page || 1 }) : requestParams
      requestParams = this.parentid ? Object.assign(requestParams, { parentid: this.parentid }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data && res.data.length > 0) {
          this.items = res.data
          this.items.forEach(element => {
            this.typeItems.forEach(item => {
              if (element.parentid === item.id) {
                element.typeName = item.name
              }
            })
          })
          // 分页
          if (res.page) {
            let page = res.page
            // this.pagination.totalItems = page.total // 总条目数
            // this.pagination.totalPage = page.totalPage // 总页数
            this.pagination.page = page.currentPage // 当前页
            this.pagination.rowsPerPage = page.pageSize // 每页显示几条数据
            this.pagination.total = page.total
          }
        } else {
          this.items = []
          this.noText = '暂无查询记录'
        }
      })
    },
    // 点击“添加” 跳转到添加页面
    addPage () {
      const router = this.$router
      router.push({ name: 'sortadd' })
    },
    // 点击“查看” 跳转到查看页面
    lookPage (id, sortName, parentcode, name, typeName, descript, creattime) {
      const router = this.$router
      router.push({
        name: 'sortlook',
        query: {
          id: id, // 该类别下的产品id
          sortName: sortName,  // 类别名称
          parentcode: parentcode, // 类型编码
          parentid: this.parentid,
          name: name,  // 该类别下的产品名称
          typeName: typeName,  // 该类别下的产品名称
          descript: descript, // 该类别下的产品描述
          creattime: creattime // 创建时间
        }
      })
    },
    // 点击“编辑” 跳转到编辑页面
    editPage (id, name) {
      const router = this.$router
      router.push({ name: 'sortedit', query: { id: id, name: name } })
    },
    // 点击“删除” 弹出确认框
    deletePage (id, parentcode) {
      this.dialog = true
      this.id = id
    },
    // “删除”弹框里的“确认”操作
    affirmDelete (parentcode) {
      let requestParams = {}
      requestParams = this.id ? Object.assign(requestParams, { id: this.id }) : requestParams
      excuteApis(requestParams, global.constant.systemApis, 'dict', 'delete').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.$toast.top(res.message)
          this.fetchData()
        } else {
          this.$toast.top('数据请求失败！')
        }
      })
    }
  }
}
</script>

<style >
.sortLine {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 300px;
  display: inline-block;
}
</style>
