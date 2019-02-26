<template>
  <v-card class="page-content">
    <v-layout xs12
              row
              style="margin-bottom: 20px">
      <v-flex xs12
              sm12
              md3
              lg3
              xl3>
        <v-text-field append-icon="search"
                      label="请输入角色名称进行搜索"
                      single-line
                      clearable
                      hide-details
                      v-model="search"></v-text-field>
      </v-flex>
      <v-flex xs12
              sm12
              md9
              lg9
              xl9
              class="text-sm-right">
        <v-btn dark
               color="primary"
               @click="addPage(orgid)">
          <v-icon dark>add</v-icon>
          <span>添加角色</span>
        </v-btn>
      </v-flex>
    </v-layout>
    <v-data-table :headers='headers'
                  :items="items"
                  hide-actions
                  :no-data-text="noText">
      <template slot="items"
                slot-scope="props">
        <td class="text-xs-left">
          {{props.index + 1}}
        </td>
        <!-- <td class="text-xs-left">
            <span>
              {{props.item.id}}
            </span>
          </td> -->
        <td class="text-xs-left">
          <span class="nameLine">
            {{props.item.rolename}}
          </span>
        </td>
        <td class="text-xs-left">
          <span class="aLine"
                :title="props.item.permissionnames">
            {{props.item.permissionnames}}
          </span>
        </td>
        <td class="text-xs-center">
          <!--查看角色-->
          <v-btn fab
                 flat
                 small
                 @click="lookPage(props.item.id, orgid)">
            <v-icon>remove_red_eye</v-icon>
          </v-btn>
          <!--编辑角色-->
          <v-btn fab
                 flat
                 small
                 @click="editPage(props.item.id, orgid)">
            <v-icon>edit</v-icon>
          </v-btn>
          <!--删除角色-->
          <v-btn fab
                 flat
                 small
                 error
                 @click="deletePage(props.item.id)">
            <v-icon>delete</v-icon>
          </v-btn>
        </td>

      </template>
      <!--<v-flex class="follow">{{props.item.permissionnames}}</v-flex>-->
    </v-data-table>
    <!--角色管理分页-->
    <v-flex xs12
            sm12
            md12
            lg12>
      <v-layout row
                justify-center>
        <v-pagination circle
                      v-model="pagination.currentPage"
                      :length="pagination.totalPage"
                      color="primary"></v-pagination>
      </v-layout>
    </v-flex>

    <!--删除弹框-->
    <v-dialog v-model="dialog"
              persistent="persistent"
              max-width="400">
      <v-card>
        <v-card-title class="headerline"
                      style="display: block">
          <v-icon @click="dialog=false"
                  style="display: inline; float: right; cursor: pointer">clear</v-icon>
        </v-card-title>
        <v-card-text class="text-xs-center">信息删除后将无法恢复，请谨慎操作，确定删除？</v-card-text>
        <v-card-actions style="padding: 30px 10px; text-align: center;display: block">
          <v-btn @click="dialog=false">取消</v-btn>
          <v-btn @click="affirmDelete(id)"
                 color="primary">确认</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import { excuteApis } from '@/apis'
import { trim } from '@/utils'
export default {
  data () {
    return {
      id: null, // 角色编号
      orgid: '',
      search: null,
      // 分页
      pagination: {
        currentPage: null,
        pageSize: null,
        totalPage: null
      },
      noText: '暂无数据',
      dialog: false,
      headers: [
        {
          text: '序号 ',
          align: 'left',
          sortable: false,
          value: ''
        },
        // {
        //   text: '角色ID ',
        //   align: 'left',
        //   sortable: false,
        //   value: ''
        // },
        {
          text: '角色名称 ',
          align: 'left',
          sortable: false,
          value: ''
        },
        {
          text: '权限配置',
          align: 'left',
          sortable: false,
          value: ''
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: ''
        }
      ],
      items: []
    }
  },
  watch: {
    search: function (val, oldVal) {
      this.searchRole(trim(val))
    },
    'pagination.currentPage': function (val, oldVal) {
      if (val !== oldVal) {
        this.fetchData()
      }
    }
  },
  created () {
    this.orgid = global.helper.ls.get('user').loginorgid
    this.fetchData()
  },
  mounted () {
  },
  methods: {
    fetchData () {
      let requestParams = {}
      requestParams = this.pagination.currentPage ? Object.assign(requestParams, { currentPage: this.pagination.currentPage || 1 }) : requestParams
      excuteApis(requestParams, global.constant.userApisUrls, 'role', 'queryRole').then(data => {
        let res = data.data
        let resPage = res.page
        if (res.flag === 1 && res.data && res.data.length > 0) {
          this.items = res.data
          if (resPage) {
            this.pagination.pageSize = resPage.pageSize
            this.pagination.currentPage = resPage.currentPage
            this.pagination.totalPage = resPage.totalPage
          }
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // 输入ID或角色名称进行搜索
    searchRole (val) {
      let requestParams = {}
      requestParams = this.pagination.currentPage ? Object.assign(requestParams, { currentPage: this.pagination.currentPage || 1 }) : requestParams
      requestParams = val ? Object.assign(requestParams, { searchval: val }) : requestParams
      excuteApis(requestParams, global.constant.userApisUrls, 'role', 'queryRole').then(data => {
        let res = data.data
        let resPage = res.page
        if (res.flag === 1 && res.data && res.data.length > 0) {
          this.items = res.data
          if (resPage) {
            this.pagination.pageSize = resPage.pageSize
            this.pagination.currentPage = resPage.currentPage
            this.pagination.totalPage = resPage.totalPage
          }
        } else {
          this.items = []
          this.noText = '暂无查询记录'
        }
      })
    },
    // 新增角色
    addPage (orgid) {
      this.$router.push({ name: 'rolesadd', query: { orgid: orgid } })
    },
    // 查看角色详情
    lookPage (id, orgid) {
      this.$router.push({ name: 'roleslook', query: { id: id, orgid: orgid } })
    },
    // 编辑角色
    editPage (id, orgid) {
      this.$router.push({ name: 'rolesedit', query: { id: id, orgid: orgid } })
    },
    // “删除”弹框
    deletePage (id) {
      this.dialog = true
      this.id = id
    },
    // 确认删除角色
    affirmDelete (id) {
      let requestParams = {}
      requestParams = id ? Object.assign(requestParams, { id: id }) : requestParams
      excuteApis(requestParams, global.constant.userApisUrls, 'role', 'deleteRole').then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.dialog = false
          setTimeout(function () {
            alert(res.message)
          }, 200)
          this.fetchData()
        }
      })
    }
  }
}
</script>

<style>
.option {
  width: 30px;
  height: 30px;
}
.nameLine {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 120px;
  display: inline-block;
}
.aLine {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 300px;
  display: inline-block;
}
</style>
