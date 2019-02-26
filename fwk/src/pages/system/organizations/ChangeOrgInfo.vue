<template>
  <v-container fluid>
    <v-layout row
              wrap
              style="height: 44px;margin-top: 0">
      <v-flex xsl2>
        您当前已经加入{{tenants.items.length}}个组织，点击如下组织名称可切换到其他组织进行操作
      </v-flex>
    </v-layout>
    <v-layout row
              wrap>
      <v-container fluid
                   grid-list-md
                   class="change_scrollbar"
                   style="height: calc(100% - 65px);margin-left: 0px;padding-left: 0px">
        <v-layout row
                  wrap>
          <v-flex xs4
                  v-for="tenant in tenants.items"
                  :key="tenant._id.$oid"
                  style="margin: 0;cursor:pointer"
                  @click="changeOrg(tenant)">
            <v-card :color="tenant.checked ? 'primary': ''"
                    :class="{ 'white--text': tenant.checked }"
                    style="height:34px;margin-right: 28px">
              <v-layout row
                        wrap
                        style="height:34px;margin:0px">
                <v-flex xs10
                        style="margin-top:4px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
                  <span :title="tenant.name">{{ tenant.name }}</span>
                </v-flex>
                <v-flex xs2
                        style="">
                  <v-card-actions style="margin:0px;padding: 0px;height:30px">
                    <v-btn icon
                           dark
                           style="margin:0px;padding: 0px;height:30px">
                      <v-icon color="white"
                              style="height:10px;width:10px;margin:0px;padding: 0px;"
                              v-if="tenant.checked">check_circle</v-icon>
                      <v-icon style="height:10px;width:10px;margin:0px;padding: 0px;"
                              v-else
                              class="grey--text">check_circle</v-icon>
                    </v-btn>
                  </v-card-actions>
                </v-flex>
              </v-layout>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-layout>
    <!-- 确认删除弹窗 -->
    <v-modaldialog :isShow="isChangeOrg">
      <span slot
            v-text="message"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat
                 color="primary"
                 @click.native="isChangeOrg = false">取消</v-btn>
          <v-btn flat
                 color="primary"
                 @click="callbackOkFn">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>
  </v-container>
</template>
<script>
// import OAuth2 from '@vehiclecloud/oauth2-client'
import Oauth2Helper from '@agrithings/common/helper/oauth2'
import mongo from '@vehiclecloud/mongo-rest-client'
import { excuteApis } from '@/apis'
export default {
  name: 'org-change_orgInfo',
  props: {
    showDialog: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
  },
  data () {
    return {
      message: '切换组织后系统将重新登录,是否确定切换?',
      isChangeOrg: false, // 确认删除弹框
      globalUser: null, // 用户信息
      changeOrgid: '',
      tenants: {
        items: [],
        editable: false
      },
      user: {
        userid: '',
        loginorgid: ''
      }
    }
  },
  watch: {
  },
  created () {
    // this.globalUser = global.helper.ls.get('user')
    this.globalUser = global.helper.ls.get('user')
    this.user.userid = this.globalUser.id
    this.user.loginorgid = this.globalUser.loginorgid ? this.globalUser.loginorgid : ''
    this.queryStaffTenant()
  },
  methods: {
    async queryStaffTenant () {
      let { items } = await (mongo.db('iss').collection('tenant.staff').find({ filter: { _id: { $oid: this.globalUser.supportid } } }))
      items.forEach(staff => {
        this.queryTenant(staff.tenants)
      })
    },
    async queryTenant (tenants) {
      let { items } = await (mongo.db('iss').collection('sys.tenant').find({ filter: { _id: { $in: tenants } } }))
      items.forEach(orgOrDept => {
        // console.log(orgOrDept)
        if (orgOrDept.type === 1) {
          this.$set(orgOrDept, 'checked', this.staffHasCurrTenant(orgOrDept))
          this.tenants.items.push(orgOrDept)
        } else {
          this.recursive(orgOrDept.parent)
        }
      })
    },
    async recursive (parent) {
      let { items } = await (mongo.db('iss').collection('sys.tenant').find({ filter: { _id: parent } }))
      items.forEach(recursiveOrgOrDept => {
        // console.log(recursiveOrgOrDept)
        if (recursiveOrgOrDept.type === 1) {
          this.$set(recursiveOrgOrDept, 'checked', this.staffHasCurrTenant(recursiveOrgOrDept))
          // console.log(JSON.stringify(this.tenants.items))
          // console.log(JSON.stringify(recursiveOrgOrDept))
          // console.log(JSON.stringify(this.tenants.items).indexOf(JSON.stringify(recursiveOrgOrDept)))
          if (JSON.stringify(this.tenants.items).indexOf(JSON.stringify(recursiveOrgOrDept)) !== -1) {
            return
          } else {
            // this.$set(recursiveOrgOrDept, 'checked', this.staffHasCurrTenant(recursiveOrgOrDept))
            this.tenants.items.push(recursiveOrgOrDept)
          }
        } else {
          this.recursive(recursiveOrgOrDept.parent)
        }
        // console.log(this.tenants.items)
      })
      // console.log(this.tenants.items)
    },
    staffHasCurrTenant (tenant) {
      return tenant && (this.globalUser.loginorgid === tenant._id.$oid)
    },
    changeOrg (tenant) {
      if (this.user.loginorgid === tenant._id.$oid) {
        this.$toast.top('您目前已经在【' + tenant.name + '】这个组织下，不需要切换')
        return
      } else {
        let tk = localStorage.getItem('atk', JSON.stringify(tk))
        let payload = atob(tk.split('.')[1])
        let jwt = JSON.parse(payload)
        if (!jwt.roles[tenant._id.$oid]) {
          this.$toast.top('该组织管理员暂未给您分配系统使用权限，请联系组织管理员开通')
          return
        }
        console.log(tenant._id.$oid)
        console.log(jwt.roles[tenant._id.$oid])
        mongo.db().collection('tenant.role').find({
          filter: { status: 1, _id: { $in: jwt.roles[tenant._id.$oid].map(id => ({ $oid: id })) }, '_removed': { '$exists': false } }
        }).then(d => {
          if (d.items && d.items.length > 0 && d.items[0].authorities) {
            console.log(tenant._id.$oid)
            this.isChangeOrg = true
            this.changeOrgid = tenant._id.$oid
          } else {
            this.$toast.top('该组织管理员暂未给您分配系统使用权限，请联系组织管理员开通')
          }
        })
      }
      // 设置状态管理
      // this.tenants.items.forEach(tenantT => {
      //   if (tenant._id.$oid === tenantT._id.$oid) {
      //     tenantT.checked = true
      //   } else {
      //     tenantT.checked = false
      //   }
      // })
      // 调用后台服务设为默认组织
      // TODO ===========
      // this.user.loginorgid = tenant._id.$oid
      // console.log(this.user)
      // this.setCacheUserInfo(tenant, this.user)
      // excuteApis(Object.assign({}, this.user), global.constant.userApisUrls, 'user', 'addOrUpdate').then(data => {
      //   let res = data.data
      //   if (res.flag === 1) {
      //     this.user.staffLastUpdateTime = res.data.staffLastUpdateTime
      //     // =================
      //     this.setCacheUserInfo(tenant, this.user)
      //     this.$emit('update:showDialog', false)
      //     this.$toast.top('您已切换到【' + tenant.name + '】')
      //     this.$router.push({name: 'home', query: {isRefreshed: '1', flag: this.user.loginorgid + new Date().getTime()}})
      //   } else {
      //     this.$toast.top('组织切换失败，' + res.message)
      //   }
      // })
    },
    /**
     * 确认
     */
    callbackOkFn () {
      this.isChangeOrg = false // 关闭弹框
      this.user.loginorgid = this.changeOrgid
      console.log(88888888888888888)
      console.log(this.changeOrgid)
      this.switchTenant(this.changeOrgid)
      // OAuth2.cookies.set('fwk-token-tenant', this.changeOrgid)
      excuteApis(Object.assign({}, this.user), global.constant.userApisUrls, 'user', 'addOrUpdate').then(data => {
        let res = data.data
        if (res.flag === 1) {
          console.log(res)
          let user = null
          let menu = null
          let bread = []
          localStorage.setItem('atk', null)
          localStorage.setItem('sys-auth', null)
          this.$store.commit('setUser', user)
          this.$store.commit('setMenu', menu)
          this.$store.commit('setBreadList', bread)
          this.$router.push({ name: 'login' })
        } else {
          this.$toast.top('组织切换失败，' + res.message)
        }
      })
    },
    switchTenant (tenant) {
      Oauth2Helper.switchTenant(tenant)
    },
    // 赋值操作
    setCacheUserInfo (tenant, changeUser) {
      let user = global.helper.ls.get('user')
      user.loginorgid = tenant._id.$oid
      user.orgName = tenant.name
      if (changeUser.staffLastUpdateTime) {
        user.operateredittime = changeUser.staffLastUpdateTime
      }
      this.$store.dispatch('cacheUserInfo', user)
    }
  },
  components: {
  }
}
</script>

<style lang="scss">
.change_scrollbar {
  overflow-y: hidden;
  &:hover {
    overflow-y: auto;
  }
  &::-webkit-scrollbar {
    background: transparent;
    width: 4px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #bdbdbd;
  }
}
</style>

