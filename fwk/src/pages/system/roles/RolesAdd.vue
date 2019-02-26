<template>
  <v-card class="page-content">
    <!-- <v-container style="border:1px solid #D6D6D6;background-color: #fff;"> -->
      <v-layout row style="border-bottom: 2px solid #DDDDDD; margin-top: 0;margin-bottom: 50px">
        <v-flex xs12 sm12 md12 lg12 xl12 style="padding-bottom: 10px">
          <v-flex >
            <span @click="back" style="cursor: pointer">
              <v-icon dark left color="primary">arrow_back</v-icon>添加角色
            </span>
          </v-flex>
        </v-flex>
      </v-layout>

      <!--角色名称-->
      <v-layout row justify-center>
        <v-flex xs8 sm8 md2 lg2 xl2>
          <v-subheader>角色名称 *</v-subheader>
        </v-flex>
        <v-flex xs8 sm8 md6 lg6 xl6>
          <v-text-field
            single-line
            label="请输入角色名称"
            v-model="rolename"
          ></v-text-field>
          <span v-show="isShow" style="color: red; position: relative; top: -20px">* 必填项</span>
        </v-flex>
      </v-layout>

      <!--权限配置-->
      <v-layout row justify-center>
        <v-flex xs8 sm8 md2 lg2 xl2>
          <v-subheader text-xs-right >权限配置</v-subheader>
        </v-flex>
        <v-flex xs8 sm8 md6 lg6 x6 style="padding-top: 10px;padding-bottom: 20px">
          <v-flex v-for="(one, index0) in permissionList" :key="one.id" >
            <div style="margin-top: 10px;">
              <v-expansion-panel expand >
                <v-expansion-panel-content class="grey lighten-3">
                  <div slot="header" >
                    <!--第一级权限-->
                    <v-flex xs12>
                      <input type="checkbox" :ref="one.id+''" :name="one.rootid+''" :value="one.id"  @change="selectedOrUnselecterdChildren(index0, one, $event)" @click="stop">
                      <span >{{one.permissionname}}</span>
                    </v-flex>
                  </div>
                   <v-card class="py-2">
                    <!--如果第一级有子配置项和操作项==>即二级 -->
                    <v-flex xs12  v-if="one.children.length > 0 && one.functions.length > 0">
                      <v-flex xs12  >
                        <!--二级配置项-->
                        <span v-for="(twoChildren, index1) in one.children" :key="twoChildren.id" class="nape">
                          <input type="checkbox" :ref="twoChildren.id+''" :name="twoChildren.rootid+''" :value="twoChildren.id"  @change="twoSelected(index1, twoChildren, $event)">
                          <span >{{twoChildren.permissionname}}</span>
                        </span>
                        <!--二级操作项-->
                        <span v-for="(twoFuc, index1) in one.functions" :key="twoFuc.id" class="nape">
                          <input type="checkbox" :ref="twoFuc.id+''" :name="twoFuc.rootid+''" :value="twoFuc.id" @change="twoSelected(index1, twoFuc, $event)">
                          <span >{{twoFuc.permissionname}}</span>
                        </span>

                        <!--判断二级配置项下面是有还有配置项和操作项==>即三级-->
                        <!--如果三级配置项有配置项和操作项-->
                        <v-flex xs12 v-if="twoChildren.children.length > 0 && twoChildren.functions.length > 0">
                          <v-flex xs12 v-for="(treeChildren, index2) in twoChildren.children" :key="treeChildren.id" style="margin-left: 40px">
                          <span>
                            <input type="checkbox" :ref="treeChildren.id+''" :name="treeChildren.rootid+''" :value="treeChildren.id" @change="treeSelected(twoChildren, index2, treeChildren, $event)">
                            <span>{{treeChildren.permissionname}}</span>
                          </span>
                            <span v-for="(treeFuc, index2) in twoChildren.functions" :key="treeFuc.id">
                              <input type="checkbox" :ref="treeFuc.id+''" :name="treeFuc.rootid+''" :value="treeFuc.id" @change="treeSelected(twoChildren, index2, treeFuc, $event)">
                              <span>{{treeFuc.permissionname}}</span>
                            </span>

                            <!--判断三级配置项下面是否还有第四级的配置项和操作项-->
                            <!--如果有第四级配置项-->
                            <v-flex xs12 v-if="treeChildren.children.length > 0">
                              <v-flex xs12 v-for="(fourChildren, index3) in treeChildren.children" :key="fourChildren.id" class="nape" style="margin-left: 20px">
                                <input type="checkbox" :ref="fourChildren.id">
                                <span>{{fourChildren.permissionname}}</span>
                              </v-flex>
                            </v-flex>
                            <!--如果有第四级操作项-->
                            <v-flex xs12 v-else-if="treeChildren.functions.length > 0">
                              <v-flex style="margin-left: 20px">
                              <span v-for="(fourFuc, index3) in treeChildren.functions" :key="fourFuc.id" class="nape fourNape">
                                <input type="checkbox" :ref="fourFuc.id" @change="fourSelected(treeChildren, index3, fourFuc, $event)">
                                <span>{{fourFuc.permissionname}}</span>
                              </span>
                              </v-flex>

                            </v-flex>

                          </v-flex>
                        </v-flex>

                        <!--如果三级配置项有配置项，但没有操作项-->
                        <v-flex xs12 v-else-if="twoChildren.children.length > 0 && twoChildren.functions.length <= 0">
                          <v-flex xs12 v-for="(treeChildren, index2) in twoChildren.children" :key="treeChildren.id" style="margin-left: 40px">
                            <v-flex class="nape">
                              <input type="checkbox" :ref="treeChildren.id+''" :name="treeChildren.rootid+''" :value="treeChildren.id" @change="treeSelected(twoChildren, index2, treeChildren, $event)">
                              <span>{{treeChildren.permissionname}}</span>
                            </v-flex>

                            <!--判断三级配置项下面是否还有第四级的配置项和操作项-->
                            <!--如果有第四级配置项-->
                            <v-flex xs12 v-if="treeChildren.children.length > 0">
                              <v-flex xs12 v-for="(fourChildren, index3) in treeChildren.children" :key="fourChildren.id" class="nape" style="margin-left: 20px">
                                <input type="checkbox" :ref="fourChildren.id">
                                <span>{{fourChildren.permissionname}}</span>
                              </v-flex>
                            </v-flex>
                            <!--如果有第四级操作项-->
                            <v-flex xs12 v-else-if="treeChildren.functions.length > 0">
                              <v-flex style="margin-left: 20px">
                              <span xs12 v-for="(fourFuc, index3) in treeChildren.functions" :key="fourFuc.id" class="nape fourNape">
                                <input type="checkbox" :ref="fourFuc.id" @change="fourSelected(treeChildren, index3, fourFuc, $event)">
                                <span>{{fourFuc.permissionname}}</span>
                              </span>
                              </v-flex>

                            </v-flex>

                          </v-flex>
                        </v-flex>


                        <!--如果二级配置项有操作项，但没有配置项-->
                        <v-flex xs12 v-else-if="twoChildren.children.length <= 0 && twoChildren.functions.length > 0">
                          <span xs12 v-for="(treeFuc, index2) in twoChildren.functions" :key="treeFuc.id" style="margin-left: 40px">
                            <input type="checkbox" :ref="treeFuc.id+''" :name="treeFuc.rootid+''" :value="treeFuc.id" @change="treeSelected(twoChildren, index2, treeFuc, $event)">
                            <span>{{treeFuc.permissionname}}</span>
                          </span>
                        </v-flex>


                      </v-flex>
                    </v-flex>

                    <!--如果第一级只有子配置项，没有操作项==>即二级配置项 -->
                    <v-flex v-else-if="one.children.length > 0 && one.functions.length <= 0" v-for="(twoChildren, index1) in one.children" :key="twoChildren.id" class="check">
                      <v-flex xs12 class="nape" >
                        <input type="checkbox" :ref="twoChildren.id+''" :name="twoChildren.rootid+''" :value="twoChildren.id" @change="twoSelected(index1, twoChildren, $event)">
                        <span >{{twoChildren.permissionname}}</span>
                      </v-flex>

                      <!--判断二级配置项下面是有还有配置项和操作项==>即三级-->
                      <!--如果三级配置项有配置项和操作项-->
                      <v-flex xs12 v-if="twoChildren.children.length > 0 && twoChildren.functions.length > 0">
                        <v-flex xs12 v-for="(treeChildren, index2) in twoChildren.children" :key="treeChildren.id" style="margin-left: 40px">
                          <span class="nape">
                            <input type="checkbox" :ref="treeChildren.id+''" :name="treeChildren.rootid+''" :value="treeChildren.id" @change="treeSelected(twoChildren, index2, treeChildren, $event)">
                            <span>{{treeChildren.permissionname}}</span>
                          </span>
                          <span class="nape" v-for="(treeFuc, index2) in twoChildren.functions">
                            <input type="checkbox" :ref="treeFuc.id+''" :name="treeFuc.rootid+''" :value="treeFuc.id" @change="treeSelected(twoChildren, index2, treeFuc, $event)">
                            <span>{{treeFuc.permissionname}}</span>
                          </span>


                          <!--判断三级配置项下面是否还有第四级的配置项和操作项-->
                          <!--如果有第四级配置项-->
                          <v-flex xs12 v-if="treeChildren.children.length > 0">
                            <v-flex xs12 v-for="(fourChildren, index3) in treeChildren.children" :key="fourChildren.id"  class="nape"  style="margin-left: 20px">
                              <input type="checkbox" :ref="fourChildren.id">
                              <span>{{fourChildren.permissionname}}</span>
                            </v-flex>
                          </v-flex>
                          <!--如果有第四级操作项-->
                          <v-flex xs12 v-else-if="treeChildren.functions.length > 0">
                            <v-flex style="margin-left: 20px">
                              <span v-for="(fourFuc, index3) in treeChildren.functions" :key="fourFuc.id" class="nape fourNape">
                                <input type="checkbox" :ref="fourFuc.id" @change="fourSelected(treeChildren, index3, fourFuc, $event)">
                                <span>{{fourFuc.permissionname}}</span>
                              </span>
                            </v-flex>

                          </v-flex>

                        </v-flex>
                      </v-flex>

                      <!--如果三级配置项有配置项，但没有操作项-->
                      <v-flex xs12 v-else-if="twoChildren.children.length > 0 && twoChildren.functions.length <= 0">
                        <v-flex xs12 v-for="(treeChildren, index2) in twoChildren.children" :key="treeChildren.id" style="margin-left: 40px">
                          <v-flex class="nape">
                            <input type="checkbox" :ref="treeChildren.id+''"  :name="treeChildren.rootid+''" :value="treeChildren.id" @change="treeSelected(twoChildren, index2, treeChildren, $event)">
                            <span>{{treeChildren.permissionname}}</span>
                          </v-flex>

                          <!--判断三级配置项下面是否还有第四级的配置项和操作项-->
                          <!--如果有第四级配置项-->
                          <v-flex xs12 v-if="treeChildren.children.length > 0">
                            <v-flex xs12 v-for="(fourChildren, index3) in treeChildren.children" :key="fourChildren.id" class="nape" style="margin-left: 20px">
                              <input type="checkbox" :ref="fourChildren.id">
                              <span>{{fourChildren.permissionname}}</span>
                            </v-flex>
                          </v-flex>
                          <!--如果有第四级操作项-->
                          <v-flex xs12 v-else-if="treeChildren.functions.length > 0">
                            <v-flex style="margin-left: 20px">
                              <span xs12 v-for="(fourFuc, index3) in treeChildren.functions" :key="fourFuc.id" class="nape fourNape">
                                <input type="checkbox" :ref="fourFuc.id" @change="fourSelected(treeChildren, index3, fourFuc, $event)">
                                <span>{{fourFuc.permissionname}}</span>
                              </span>
                            </v-flex>

                          </v-flex>


                        </v-flex>
                      </v-flex>


                      <!--如果三级配置项有操作项，但没有配置项-->
                      <v-flex xs12 v-else-if="twoChildren.children.length <= 0 && twoChildren.functions.length > 0">
                        <span v-for="(treeFuc, index2) in twoChildren.functions" :key="treeFuc.id" class="nape" style="margin-left: 40px">
                          <input type="checkbox" :ref="treeFuc.id+''":name="treeFuc.rootid+''" :value="treeFuc.id" class="xxxxxxx" @change="treeSelected(twoChildren, index2, treeFuc, $event)">
                          <span>{{treeFuc.permissionname}}</span>
                        </span>
                      </v-flex>


                    </v-flex>

                    <!--如果第一级没有子配置项，只有操作项==>即二级配置项 -->
                    <v-flex v-else-if="one.children.length <= 0 && one.functions.length > 0" class="check">
                      <span v-for="(twoFuc, index1) in permissionList[index0].functions" :key="twoFuc.id" class="nape twoNape" >
                        <input type="checkbox" :ref="twoFuc.id+''" @change="twoSelected(index1, twoFuc, $event)">
                        <span >{{twoFuc.permissionname}}</span>
                      </span>
                    </v-flex>
                  </v-card>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </div>

          </v-flex>


        </v-flex>
      </v-layout>

      <!--确认、取消-->
      <v-layout row>
        <v-flex xs12 class="text-xs-center">
          <v-btn @click="cancel">取消</v-btn>
          <v-btn @click="save" color="primary" :disabled="saveDisabled">
            确认
            <v-progress-circular
              v-show="progressShow"
              class="progressWrapper"
              size="23"
              indeterminate
              color="primary"
            >
            </v-progress-circular>
          </v-btn>
        </v-flex>
      </v-layout>


    <!-- </v-container> -->
  </v-card>
</template>


<script>
  import { excuteApis } from '@/apis'
  export default {
    data () {
      return {
        orgid: '',
        rolename: null,
        permissionList: [],
        allID: null,
        isShow: false,
        progressShow: false,
        saveDisabled: false
      }
    },
    watch: {
      rolename: function (val, oldVal) {
        if (val) {
          this.isShow = false
        } else {
          this.isShow = true
        }
      }
    },
    created () {
      this.orgid = global.helper.ls.get('user').loginorgid
      this.fetchData()
    },
    methods: {
      // 获取权限配置项
      fetchData () {
        // 获取当前登录用户的组织id
        // 获取该角色的所有权限项
        excuteApis('', global.constant.userApisUrls, 'role', 'queryPermissionTree').then(data => {
          // console.log('所有权限项:')
          // console.log(data)
          let res = data.data
          if (res.flag === 1 && res.data) {
            this.permissionList = res.data
          } else {
            alert(res.message)
          }
        }).catch((response) => {
          alert(response)
        })
      },
      // 当点击第一级时，防止发生冒泡
      stop (e) {
        e.stopPropagation()
      },
      // 点击第一级时，他所有子节点的选中状态
      selectedOrUnselecterdChildren (index, item, event) {
        // 判断第一级是否有子配置项和操作项
        if (item.children.length > 0) {  // 如果第一级有子配置项
          for (let j = 0; j < item.children.length; j++) {
            this.$refs[item.children[j].id + ''][0].checked = event.target.checked
            let treeC = item.children[j].children.length
            let treeF = item.children[j].functions.length
            // 判断第二级是否有子配置项和操作项
            if (treeC > 0) { // 如果第二级有子配置项
              for (let a = 0; a < treeC; a++) {
                this.$refs[item.children[j].children[a].id + ''][0].checked = event.target.checked
                let fourC = item.children[j].children[a].children.length
                let fourF = item.children[j].children[a].functions.length
                // 判断第三级是否有子配置项和操作项
                if (fourC > 0) { // 如果第三级有子配置项
                  for (let c = 0; c < fourC; c++) {
                    this.$refs[item.children[j].children[a].children[c].id + ''][0].checked = event.target.checked
                  }
                  // console.log('第三级有子配置项')
                } else if (fourF > 0) { // 如果第三级有子操作项
                  for (let d = 0; d < fourF; d++) {
                    this.$refs[item.children[j].children[a].functions[d].id + ''][0].checked = event.target.checked
                  }
                  // console.log('第三级有子操作项')
                }
              }
              for (let b = 0; b < treeF; b++) {
                this.$refs[item.children[j].functions[b].id + ''][0].checked = event.target.checked
              }
            } else { // 如果第二级有子操作项
              for (let b = 0; b < treeF; b++) {
                this.$refs[item.children[j].functions[b].id + ''][0].checked = event.target.checked
              }
            }
          }
        } else if (item.functions.length > 0) { // 如果第一级有子操作项
          for (let i = 0; i < item.functions.length; i++) {
            this.$refs[item.functions[i].id + ''][0].checked = event.target.checked
          }
        }
      },

      // 点击第二级的配置项时，他所有子节点和父节点的选中状态
      twoSelected (index, item, event) {
        // 先判断点击的是二级配置项还是二级操作项
        if (item.children) {  // 有children的是配置项
          // 设置二级的父级的选中状态
          this.$refs[item.rootid + ''][0].checked = true
          // 判断二级是否有子配置项和操作项
          if (item.children.length > 0) {  // 如果第二级下有配置项
            for (let j = 0; j < item.children.length; j++) {
              this.$refs[item.children[j].id + ''][0].checked = event.target.checked
              let treeC = item.children[j].children.length
              let treeF = item.children[j].functions.length
              // 判断三级级是否有子配置项和操作项
              if (treeC > 0) { // 如果三级下配置项
                for (let a = 0; a < treeC; a++) {
                  this.$refs[item.children[j].children[a].id + ''][0].checked = event.target.checked
                }
                for (let b = 0; b < treeF; b++) {
                  this.$refs[item.children[j].functions[b].id + ''][0].checked = event.target.checked
                }
              } else if (treeF > 0) { // 如果三级下操作项
                for (let b = 0; b < treeF; b++) {
                  this.$refs[item.children[j].functions[b].id + ''][0].checked = event.target.checked
                }
              }
            }
          } else if (item.functions.length > 0) { // 如果第二级下有操作项
            for (let i = 0; i < item.functions.length; i++) {
              this.$refs[item.functions[i].id + ''][0].checked = event.target.checked
            }
          }
        } else { // 没有children的就是操作项
          // 设置他的父级的选中状态
          this.$refs[item.rootid + ''][0].checked = true
        }
      },

      // 点击第三级,他所有子节点和父节点的选中状态
      treeSelected (parentList, index, item, event) {
        // 先判断他自身是配置项还是操作项
        if (item.children) { // 有children的是配置项
          // 判断三级是否有子配置项和子操作项
          if (item.children.length > 0) {  // 第三级有配置项
            for (let j = 0; j < item.children.length; j++) {
              this.$refs[item.children[j].id + ''][0].checked = event.target.checked
            }
          } else if (item.functions.length > 0) { // 第三级有操作项
            // console.log('第三级的父级')
            // console.log(parentList)
            // 第三级的子级的状态
            for (let i = 0; i < item.functions.length; i++) {
              this.$refs[item.functions[i].id + ''][0].checked = event.target.checked
            }
            // 设置第三级的父级的状态
            this.$refs[parentList.id + ''][0].checked = true
            // 设置第一级的状态
            let oneID = null
            for (let one of this.permissionList) {
              if (one.id === parentList.rootid) {
                oneID = one.id
              }
            }
            this.$refs[oneID + ''][0].checked = true
          }
        } else { // 没有children的是操作项
          // console.log('三级操作的父级')
          // console.log(parentList)
          let oneID = null
          this.$refs[parentList.id + ''][0].checked = true // 设置第二级的状态
          // 设置第一级的状态
          for (let one of this.permissionList) {
            if (one.id === parentList.rootid) {
              oneID = one.id
            }
          }
          this.$refs[oneID + ''][0].checked = true
        }
      },

      // 点击第四级,他所有子节点和父节点的选中状态
      fourSelected (parentList, index, item, event) {
        // console.log(parentList)
        // console.log(item)
        let oneID = null
        this.$refs[item.rootid + ''][0].checked = true // 第三级的状态
        this.$refs[parentList.rootid + ''][0].checked = true  // 第二级的状态
        for (let one of this.permissionList) {
          for (let oneC of one.children) {
            if (oneC.id === parentList.rootid) {
              oneID = oneC.rootid
            }
          }
        }
        this.$refs[oneID + ''][0].checked = true // 第一级的状态
      },

      // 获取页面中被选中的项的id
      stateID (permissionList) {
        if (permissionList && permissionList.length > 0) {
          for (let i = 0; i < permissionList.length; i++) {
            if (this.$refs[permissionList[i].id + ''][0].checked) {
              this.allID.push(permissionList[i].id)
            }
            if (permissionList[i].functions && permissionList[i].functions.length > 0) {
              for (let j = 0; j < permissionList[i].functions.length; j++) {
                if (this.$refs[permissionList[i].functions[j].id + ''][0].checked) {
                  this.allID.push(permissionList[i].functions[j].id)
                }
              }
            }
            if (permissionList[i].children && permissionList[i].children.length > 0) {
              this.stateID(permissionList[i].children)
            }
          }
        } else {
          return
        }
      },
      // 确认
      save () {
        this.allID = []
        this.stateID(this.permissionList)
        // this.allID = this.allID.join(',')
        if (!!this.rolename && !!this.allID && this.allID.length > 0) {
          this.saveDisabled = true // 在服务响应之前禁用返回按钮
          this.progressShow = true // 显示转圈
          this.allID = this.allID.join(',')
          let requestParams = {}
          requestParams = this.rolename ? Object.assign(requestParams, {rolename: this.rolename}) : requestParams
          requestParams = this.allID ? Object.assign(requestParams, {permissions: this.allID}) : requestParams
          requestParams = this.orgid ? Object.assign(requestParams, {orgid: this.orgid}) : requestParams
          excuteApis(requestParams, global.constant.userApisUrls, 'role', 'saveRole').then(data => {
            let res = data.data
            if (res.flag === 1) {
              this.$toast.top(res.message)
              this.$router.push({name: 'roles'})
            } else {
              this.$toast.top(res.message)
            }
          }).finally(() => {
            this.saveDisabled = false // 服务已经响应 启用提交按钮
            this.progressShow = false // 停止转圈
          }).catch(data => {
            this.$toast.top('服务异常')
          })
        } else if (!this.rolename) {
          this.isShow = true
        } else if (this.allID && this.allID.length <= 0) {
          this.$toast.top('请选择权限')
        }
      },
      // 取消
      cancel () {
        this.$router.push({name: 'roles'})
      },
      // 返回到角色管理首页
      back () {
        this.$router.push({name: 'roles'})
      }
    }
  }
</script>

<style scoped>
  .check {
    margin: 7px 40px;
  }
  .nape {
    margin: 5px auto 5px auto;
    display: inline-block;
  }
  .twoNape {
    margin-right: 30px;
  }
  .fourNape {
    margin-left: 10px;
    margin-right: 20px;
  }
</style>
