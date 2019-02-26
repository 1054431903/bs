<template>
  <v-card class="page-content">
    <v-layout row>
      <div class="treeClass">
        <v-flex xs12
                md12
                style="margin-bottom: 5px;margin-top: 0px">
          <v-text-field label="输入关键字进行过滤"
                        v-model="filterLeftTreeText"
                        append-icon="search"
                        clearable
                        single-line>
          </v-text-field>
        </v-flex>
        <el-tree class="filter-tree"
                 :data="leftTreeData"
                 :props="defaultProps"
                 default-expand-all
                 :expand-on-click-node="expandOnClick"
                 :filter-node-method="filterTreeNode"
                 ref="leftTree"
                 highlight-current
                 :render-content="renderContent"
                 empty-text="暂无数据">
        </el-tree>
      </div>
      <!-- 顶部工具条 -->
      <v-flex xs9
              md9>
        <v-card class="page-content">
          <v-card-title v-if="display.displayName === ''"
                        style="color:#39bbb0;font-weight:bold;font-size:16px"></v-card-title>
          <v-card-title v-else-if="display.displayTitle !== ''"
                        style="color:#39bbb0;font-weight:bold;font-size:16px">{{display.displayTitle}} </v-card-title>
          <v-card-title v-else
                        style="color:#39bbb0;font-weight:bold;font-size:16px">{{display.displayName}} </v-card-title>
          <v-card-text>
            <v-layout>
              <!--
              <v-flex xs12 md5 >
                <v-select
                  label="请选择角色"
                  v-model="rolesItem"
                  :items="roles"
                  item-text="rolename"
                  item-value="id"
                  single-line
                >

                </v-select>
              </v-flex>
              -->
              <v-flex xs12
                      md12>
                <v-text-field label="输入用户账号，姓名进行搜索..."
                              v-model="searchTxt"
                              append-icon="search"
                              clearable
                              single-line>
                </v-text-field>
              </v-flex>
            </v-layout>
            <v-data-table :headers='headers'
                          :items="recordList"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions>
              <template slot="items"
                        slot-scope="props">
                <td class="text-xs-left">
                  {{props.index + 1}}
                </td>
                <td class="text-xs-left">
                  <span v-if="props.item.name">{{props.item.name}}</span>
                  <span v-else>{{props.item.phone}}</span>
                </td>
                <td class="text-xs-left">
                  <span class="nameLine"
                        :title="props.item.departmentname">
                    {{props.item.departmentname}}
                  </span>
                </td>
                <td class="text-xs-left">
                  <span class="aLine"
                        :title="props.item.rolename">
                    {{props.item.rolename}}
                  </span>
                </td>
                <td class="text-xs-left">
                  <span class="aLine">
                    {{props.item.phone}}
                  </span>
                </td>
                <!-- v-if="$authed('GET', '/system/memberManage')"-->
                <td class="text-xs-left">
                  <!--编辑用户-->
                  <v-btn fab
                         flat
                         small
                         v-if="$authed('PUT', 'system/memberManage/edit')"
                         @click="editUserInfo(props.item)">
                    <v-icon>edit</v-icon>
                  </v-btn>
                </td>
              </template>
            </v-data-table><!-- 分页 -->
            <v-layout>
              <v-flex xs12
                      text-xs-center>
                <!-- <v-pagination v-model="pagination.page" :length="totalPages" circle :color="theme"></v-pagination> -->
                <v-custompagination :pagination.sync="pagination"></v-custompagination>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- Dialog -->
    <v-layout>
      <v-modaldialog :isShow.sync="editUserDialog"
                     :showCloseBtn="true"
                     title="编辑用户"
                     :modalMaxWidth="650"
                     :bodyHeight="600">

        <v-container slot>
          <!-- 编辑用户  -->
          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>&nbsp;姓名</v-subheader>
            </v-flex>
            <v-flex xs4>
              <v-subheader>{{editUser.name}}</v-subheader>
            </v-flex>
            <!--
              <v-flex xs4 text-xs-right>
                <v-btn @click="openTurnOrgDialog()">转交企业</v-btn>
              </v-flex>
              -->
          </v-layout>

          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>&nbsp;昵称</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.nickname}}</v-subheader>
            </v-flex>
          </v-layout>

          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>&nbsp;性别</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.sexDisText}}</v-subheader>
            </v-flex>
          </v-layout>

          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>&nbsp;手机号码</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.phone}}</v-subheader>
            </v-flex>
          </v-layout>

          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>&nbsp;邮箱</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.email}}</v-subheader>
            </v-flex>
          </v-layout>

          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>出生日期</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.birthday}}</v-subheader>
            </v-flex>
          </v-layout>

          <!-- 所属部门 -->
          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>所属部门</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-subheader>{{editUser.departmentname}}</v-subheader>
              <!--
                <v-text-field
                  label="请选择上级部门"
                  v-model="editUser.departmentname"
                  single-line
                  @focus="rightUserListSelectDeptDialog = !rightUserListSelectDeptDialog"
                ></v-text-field>
                -->
            </v-flex>
          </v-layout>

          <!--是所属部门的负责人 -->
          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader></v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-checkbox v-bind:label="`是所属部门的负责人`"
                          v-model="editUser.isdepartmentbelong"
                          light
                          disabled></v-checkbox>
            </v-flex>
          </v-layout>

          <!--角色 -->
          <!-- 所属部门 -->
          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>角色</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-select :items="roles"
                        v-model="editUserRoleArr"
                        label="请选择角色"
                        multiple
                        single-line
                        :menu-props="auto"
                        chips
                        hide-details
                        item-text="rolename"
                        item-value="id"
                        return-object
                        clearable
                        deletable-chips></v-select>
            </v-flex>
          </v-layout>
          <!-- 工号 -->
          <v-layout row
                    wrap>
            <v-flex xs4>
              <v-subheader>工号</v-subheader>
            </v-flex>
            <v-flex xs6>
              <v-text-field label="工号"
                            v-model="editUser.jobnumber"
                            single-line
                            :counter="20"
                            maxlength="20">
              </v-text-field>
            </v-flex>
          </v-layout>
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="cancelEditUser">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click="confirmEditUser">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>

      <!-- 转交给企业弹出框 -->
      <!--<v-dialog v-model="turnOrgDialog" width="450">-->
      <v-modaldialog :isShow.sync="turnOrgDialog"
                     :showCloseBtn="true"
                     title="转交企业"
                     :modalMaxWidth="450"
                     :bodyHeight="400">
        <v-container slot>
          <form ref="formModal">

            <!--<v-card>
            <v-card-title style="margin: 0;padding: 0;" >
              <v-toolbar :color="theme">
                <v-toolbar-title class="white--text">转交企业</v-toolbar-title>
              </v-toolbar>
            </v-card-title>
            <v-card-text>
              <v-container> -->
            <!-- 编辑用户  -->
            <v-layout row
                      wrap>
              <v-flex xs12>
                <v-subheader>确定将企业转交给 "{{editUser.name}}" 吗？转交成功后，您将不再是企业所有者，请您输入登录密码并确认操作！</v-subheader>
              </v-flex>
            </v-layout>
            <v-layout row
                      wrap>
              <v-flex xs2
                      text-xs-center></v-flex>
              <v-flex xs8
                      text-xs-center>
                <v-text-field box
                              label="登陆密码"
                              v-model="inputPwd"
                              single-line>
                </v-text-field>
              </v-flex>
              <v-flex xs2
                      text-xs-center></v-flex>
            </v-layout>
            <!-- </v-container>

        </v-card-text>
        <v-card-actions>
          <v-layout row wrap>
            <v-flex xs12 text-xs-center>
              <v-btn flat @click.native="cancelTurnOrg">取消</v-btn>
              <v-btn flat color="primary" @click.native="confirmTurnOrg">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
      -->
          </form>
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="cancelTurnOrg">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click="confirmTurnOrg">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>
      <!--</v-dialog>-->

      <!-- 移除企业的弹框-->
      <!-- <v-dialog v-model="removeUserFromOrgDialog" width="450">-->
      <v-modaldialog :isShow.sync="removeUserFromOrgDialog"
                     :showCloseBtn="true"
                     title="转交企业"
                     :modalMaxWidth="450"
                     :bodyHeight="400">
        <v-container slot>
          <form ref="formModal">
            <!-- <v-card>
            <v-card-title style="margin: 0;padding: 0;" >
              <v-toolbar :color="theme">
                <v-toolbar-title class="white--text">移除企业</v-toolbar-title>
              </v-toolbar>
            </v-card-title>
            <v-card-text>
              <v-container> -->
            <!-- 编辑用户  -->
            <v-layout row
                      wrap>
              <v-flex xs12>
                <v-subheader>确定将 "{{editUser.name}}" 移除企业吗？</v-subheader>
              </v-flex>
            </v-layout>
            <!-- </v-container>
        </v-card-text>
        <v-card-actions>
          <v-layout row wrap>
            <v-flex xs12 text-xs-center>
              <v-btn flat @click.native="cancelRemoveUserFromOrg">取消</v-btn>
              <v-btn flat color="primary" @click.native="confirmRemoveUserFromOrg">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
-->
          </form>
          <!-- </v-dialog>-->
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="cancelRemoveUserFromOrg">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click="confirmRemoveUserFromOrg">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>

      <!-- 添加子部门、编辑子部门的弹框-->
      <!--<v-dialog v-model="editChildDeptDialog" width="450">-->
      <v-modaldialog :isShow.sync="editChildDeptDialog"
                     :showCloseBtn="true"
                     :title="childDept.titleDisTxt"
                     :modalMaxWidth="600"
                     :bodyHeight="400">
        <v-container slot>
          <form ref="formModal">
            <v-container>
              <!-- 部门名称-->
              <v-layout row
                        wrap>
                <v-flex xs4>
                  <v-subheader>*部门名称</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="请输入子部门名称"
                                v-model="childDept.name"
                                single-line
                                :error-messages="errors.collect('name')"
                                v-validate="'required'"
                                data-vv-name="name"
                                data-vv-as="部门名称"
                                :counter="12"
                                maxlength="12"></v-text-field>
                </v-flex>
              </v-layout>
              <!-- 部门编号-->
              <v-layout row
                        wrap>
                <v-flex xs4>
                  <v-subheader>部门编号</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="请输入部门编号"
                                v-model="childDept.departno"
                                single-line
                                :counter="20"
                                maxlength="20"></v-text-field>
                </v-flex>
              </v-layout>

              <!-- 上级部门-->
              <v-layout row
                        wrap>
                <v-flex xs4>
                  <v-subheader>*上级部门</v-subheader>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="请选择上级部门"
                                v-model="childDept.parentname"
                                single-line
                                @focus="leftDeptTreeSelectDeptDialog = !leftDeptTreeSelectDeptDialog; editDeptSelectedDeptTips = ''"></v-text-field>
                </v-flex>
              </v-layout>

              <!-- 部门负责人 -->
              <v-layout row
                        wrap>
                <v-flex xs4>
                  <v-subheader>部门负责人</v-subheader>
                </v-flex>
                <v-flex xs6>

                  <v-select label="请选择用户"
                            v-model="childDept.chargeuserid"
                            :items="chargeusers"
                            item-text="account"
                            item-value="id"
                            single-line>
                    <template slot="selection"
                              slot-scope="data">
                      <span v-if="typeof data.item.name !== 'undefined'"
                            v-html="data.item.account + ' | ' +data.item.name"></span>
                      <span else
                            v-else
                            v-html="data.item.account + ' | '"></span>
                    </template>
                    <template slot="item"
                              slot-scope="data">
                      <template>
                        <v-list-tile-content>
                          <v-list-tile-title v-if="typeof data.item.name !== 'undefined'"
                                             v-html="data.item.account + ' | ' +data.item.name"></v-list-tile-title>
                          <v-list-tile-title v-else
                                             v-html="data.item.account + ' | '"></v-list-tile-title>
                        </v-list-tile-content>
                      </template>
                    </template>
                  </v-select>
                </v-flex>
              </v-layout>
            </v-container>
          </form>
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="cancelEditChildDept">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click.native="confirmEditChildDept">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>

      <!-- 删除子部门的弹出框-->
      <v-modaldialog :isShow.sync="delChildDeptDialog"
                     :showCloseBtn="true"
                     title="删除子部门"
                     :modalMaxWidth="300"
                     :bodyHeight="120">
        <v-container slot>
          <form ref="formModal">
            <v-container>
              <!-- 删除子部门  -->
              <v-layout row
                        wrap>
                <v-flex xs12
                        text-xs-center>
                  确认要删除 "{{childDept.name}}" 吗？
                </v-flex>
              </v-layout>
            </v-container>
          </form>
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="cancelDelChildDept">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click="confirmDelChildDept">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>
      <!-- 部门树的操作-->
    </v-layout>
    <!-- 编辑部门选择上级部门的弹出框-->
    <v-layout row
              justify-center>
      <!--<v-dialog v-model="leftDeptTreeSelectDeptDialog" persistent max-width="480">-->
      <v-modaldialog :isShow.sync="leftDeptTreeSelectDeptDialog"
                     :showCloseBtn="true"
                     title="选择部门"
                     :modalMaxWidth="650"
                     :bodyHeight="500">
        <v-container slot>
          <!-- <v-card>
          <v-layout row wrap>
              <v-flex xs6 class="selectedtitle">
                <div pl-3 class="subheading">选择部门</div>
              </v-flex>
              <v-flex xs6 class="selectedtitle">
                <div pl-3 class="subheading text-xs-right">
                  <v-btn  @click="() => {leftDeptTreeSelectDeptDialog = false;editDeptSelectedDeptTips = ''}">
                    <v-icon color="red" left>remove_circle</v-icon>取&nbsp;消
                  </v-btn>
                </div>
              </v-flex>
            </v-layout>
            -->
          <v-layout py-1
                    row
                    wrap>
            <v-flex pl-3
                    xs9>
              <v-text-field style="font-size:14px"
                            label="输入关键字进行过滤"
                            v-model="filterEditDeptTreeText"
                            append-icon="search"
                            single-line>
              </v-text-field>
            </v-flex>

            <v-flex xs3
                    v-if="showEditDeptSelectedDeptTips">
              <div class="selectedTips">{{editDeptSelectedDeptTips}}</div>
            </v-flex>
          </v-layout>
          <v-layout row
                    wrap>
            <v-flex xs12>
              <el-tree class="filter-tree"
                       :data="editDeptDialogSelctDeptData"
                       :props="defaultProps"
                       default-expand-all
                       :expand-on-click-node="expandOnClick"
                       :filter-node-method="filterTreeNode"
                       ref="editDeptTreeDept"
                       highlight-current
                       @node-click="handleEditDeptTreeNode"
                       empty-text="暂无数据">
              </el-tree>
            </v-flex>
          </v-layout>
          <!--</v-card>-->
        </v-container>
      </v-modaldialog>
    </v-layout>
    <!-- 编辑用户选择部门的弹出框-->
    <v-layout row
              justify-center>
      <!-- <v-dialog v-model="rightUserListSelectDeptDialog" persistent max-width="480">-->
      <v-modaldialog :isShow.sync="rightUserListSelectDeptDialog"
                     :showCloseBtn="true"
                     title="选择部门"
                     :modalMaxWidth="650"
                     :bodyHeight="500">
        <v-container slot>
          <!--<v-card>
           <v-layout row wrap>
             <v-flex xs6 class="selectedtitle">
               <div pl-3 class="subheading">选择部门</div>
             </v-flex>
             <v-flex xs6 class="selectedtitle">
               <div pl-3 class="subheading text-xs-right">
                 <v-btn  @click="rightUserListSelectDeptDialog = false">
                   <v-icon color="red" left>remove_circle</v-icon>取&nbsp;消
                 </v-btn>
               </div>
             </v-flex>
           </v-layout>
           <v-divider></v-divider>
           -->
          <v-layout py-1
                    row
                    wrap>
            <v-flex pl-3
                    xs9>
              <v-text-field style="font-size:14px"
                            label="输入关键字进行过滤"
                            v-model="filterEditUserTreeText"
                            append-icon="search"
                            single-line>
              </v-text-field>
            </v-flex>
            <v-flex xs3
                    v-if="showEditUserSelectedDeptTips">
              <div class="selectedTips">请选择部门</div>
            </v-flex>
          </v-layout>
          <v-layout row
                    wrap>
            <v-flex xs12>
              <el-tree class="filter-tree"
                       :data="editUserDialogSelctDeptData"
                       :props="defaultProps"
                       default-expand-all
                       :expand-on-click-node="expandOnClick"
                       :filter-node-method="filterTreeNode"
                       ref="editUserTreeDept"
                       highlight-current
                       @node-click="handleEditUserTreeNode"
                       empty-text="暂无数据">
              </el-tree>
            </v-flex>
          </v-layout>
          <!--</v-card>-->
        </v-container>
      </v-modaldialog>
    </v-layout>
    <!-- 编辑部门选择的部门负责人弹出框-->
    <v-layout row
              justify-center>
      <!-- <v-dialog v-model="selectChargeuserDialog" persistent max-width="480">-->
      <v-modaldialog :isShow.sync="selectChargeuserDialog"
                     :showCloseBtn="true"
                     title="选择用户树"
                     :modalMaxWidth="650"
                     :bodyHeight="500">
        <v-container slot>
        </v-container>
        <template slot="btnActions">
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat
                   color="primary"
                   @click.native="selectedUserSure">取消</v-btn>
            <v-btn flat
                   color="primary"
                   @click="cancelUserSelected">确定</v-btn>
          </v-card-actions>
        </template>
      </v-modaldialog>
    </v-layout>
    <!--
    <v-snackbar
      :timeout="toast.timeout"
      :color="toast.color"
      :multi-line="toast.mode === 'multi-line'"
      :vertical="toast.mode === 'vertical'"
      v-model="toast.show"
      right
    >
      {{ toast.msg }}
      <v-btn dark flat @click.native="toast.show = false">Close</v-btn>
    </v-snackbar>
    -->
  </v-card>
</template>
<script>
import { excuteApis } from '@/apis'
import debounce from 'throttle-debounce/debounce'
// import {
//   deleteDepartment, orgDepartUserTree, personalDataEdit, queryAllUserByOrgDepartment, queryDepartmentDetail,
//   queryRole, querySingleRole, queryUserByOrgDepartment, removeUserFromOrg, saveDepartment, turnOrg
// } from '@/apis/systems/user'
import { Toast } from '@/models/toast'
export default {
  data () {
    return {
      theme: 'primary',
      expandOnClick: false,
      // ================
      filterLeftTreeText: '', // 左边树形结构的关键字搜索
      filterEditUserTreeText: '', // 编辑用户弹出树形结构输入的关键字搜索
      filterEditDeptTreeText: '', // 编辑部门弹出树形结构输入的关键字搜索
      // ================
      globalUser: null, // 用户信息
      display: { // 右边列表的公司--》部门（人数）
        displayName: '',
        orgname: '',
        displayTitle: ''
      },
      inputPwd: '', // 移交企业时输入的密码
      departmentid: '', // 选中的部门信息(左边树形结构)
      rolesItem: '', // 选择角色（列表的查询条件）
      roles: [], // 编辑用户所选的角色
      searchTxt: '', // 请输入用户账号，姓名进行搜索
      selected: [], // 树选中的数据
      editDeptSelected: [], // 树选中的数据
      editUserSelected: [], // 树选中的数据
      headers: [ // 表头
        { text: '序号', value: 'id', align: 'left', sortable: false },
        { text: '姓名', value: 'name', align: 'left', sortable: false },
        { text: '所在部门', value: 'dept', align: 'left', sortable: false },
        { text: '角色', value: 'role', align: 'left', sortable: false },
        { text: '手机/邮箱', value: 'phone', align: 'left', sortable: false },
        { text: '操作', value: 'operate', align: 'left', sortable: false }
      ],
      pagination: {
        total: 0,
        page: 1,
        rowsPerPage: 10 // 每页显示几条数据
      },
      // totalPages: 0,
      // total: 0,
      recordList: [], // 用户列表
      leftTreeData: [], // 左边树形结构的数据
      editDeptDialogSelctDeptData: [], // 编辑部门弹出框选择部门的数据
      editUserDialogSelctDeptData: [], // 编辑用户弹出框选择部门的数据
      childDept: {
        parentFlag: '', // 上级部门的标识是组织还是部门
        titleDisTxt: '', // 部门名称
        name: '', // 部门名称
        orgid: null, // 组织编号
        departno: '', // 部门编号
        parentid: null, // 上级部门
        parentname: '', // 上级部门
        chargeuserid: null, // 部门负责人
        id: null, // 编号
        supportid: ''
      },
      // =====弹出框的变量=====
      editUserRoleArr: [], // 编辑用户已经选择的角色
      editUser: {
        userid: '',
        loginorgid: '',
        name: '', // 姓名
        nickname: '', // 昵称
        sex: '',   // 性别
        sexDisText: '',   // 性别
        phone: '', // 手机
        email: '', // 邮箱
        birthday: '', // 出生日期
        departmentid: '', // 归属部门
        departmentname: '', // 归属部门
        isdepartmentbelong: false,
        roleid: '', // 多个以逗号进行分开
        rolename: '',
        jobnumber: '', // 工号
        departFlag: '' // 在组织下还是在部门下
      }, // 用户信息
      editUserDialog: false, // 编辑用户的弹框
      turnOrgDialog: false, // 转交企业的弹框
      removeUserFromOrgDialog: false, // 移除企业的弹框
      editChildDeptDialog: false,  // 添加子部门、编辑子部门的弹框
      delChildDeptDialog: false, // 删除子部门的对话框
      chargeusers: [], // 部门下的用户
      // =======弹出树形结构=====
      leftDeptTreeSelectDeptDialog: false, // 编辑部门选择上级部门的弹框
      showEditDeptSelectedDeptTips: false, // 编辑部门选择上级部门的提示
      editDeptSelectedDeptTips: '',
      rightUserListSelectDeptDialog: false, // 编辑用户选择部门的弹框
      showEditUserSelectedDeptTips: false, // 编辑用户选择部门的提示
      selectChargeuserDialog: false, // 编辑部门选择部门负责人的弹框
      defaultProps: {
        children: 'children',
        name: 'name',
        sign: 'sign',
        count: 'count',
        id: 'id',
        supportid: 'supportid',
        title: 'title',
        parentId: 'parentId',
        expanded: 'expanded',
        label: 'name'
      },
      toast: null
    }
  },
  created () {
    this.globalUser = global.helper.ls.get('user')
  },
  methods: {
    /**
     * 查询组织机构(部门)树形结构数据*
     */
    orgDepartUserTree (loginorgid) {
      excuteApis(Object.assign({}, { orgid: loginorgid, showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(data => {
        // orgDepartUserTree(loginorgid, '').then(data => { // countUser
        let result = data.data
        if (result.flag === 1) {
          if (typeof (result.data) !== 'undefined') {
            this.leftTreeData = result.data
          }
          // this.leftTreeData.forEach(function (item) {
          //   item.expanded = true
          //   item.children.forEach(function (item1) {
          //     item1.expanded = true
          //   })
          // })
          this.display.orgname = result.data[0].name
          // this.display.displayTitle = result.data[0].name + '( ' + result.data[0].count + ' 人)'
          this.display.displayName = result.data[0].name
        }
      })
        .catch((res) => {
        })
    },
    /**
     * 编辑部门上级部门的数据
     */
    editDeptParentDept (loginorgid) {
      excuteApis(Object.assign({}, { orgid: loginorgid, showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(data => {
        let result = data.data
        if (result.flag === 1) {
          if (typeof (result.data) !== 'undefined') {
            this.editDeptDialogSelctDeptData = result.data
          }
          // this.editDeptDialogSelctDeptData.forEach(function (item) {
          //   item.expanded = true
          //   item.children.forEach(function (item1) {
          //     item1.expanded = true
          //   })
          // })
        }
      })
        .catch((res) => {
        })
    },
    /**
     * 编辑部门上级部门的数据
     */
    editUserOwnDept (loginorgid) {
      excuteApis(Object.assign({}, { orgid: loginorgid, showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(data => {
        let result = data.data
        if (result.flag === 1) {
          if (typeof (result.data) !== 'undefined') {
            this.editUserDialogSelctDeptData = result.data
          }
          // this.editUserDialogSelctDeptData.forEach(function (item) {
          //   item.expanded = true
          //   item.children.forEach(function (item1) {
          //     item1.expanded = true
          //   })
          // })
        }
      })
        .catch((res) => {
        })
    },
    /* 查询角色列表 */
    queryRole (orgid) {
      excuteApis(Object.assign({}, { orgid: orgid }), global.constant.userApisUrls, 'role', 'queryRole').then(res => {
        // queryRole(orgid).then(res => {
        let data = res.data
        if (data.flag === 1) {
          this.roles = data.data
        } else {
          // this.openTipsDialog('red lighten-1', '没有查到角色列表' + res.message)
          this.$toast.top('没有查到角色列表' + res.message)
        }
      })
        .catch((res) => {
        })
    },
    /* 获取成员记录 */
    getRecordList (rolesItem, searchTxt, currentPage) {
      if (this.globalUser.loginorgid === '') {
        return
      }
      /* 获取成员记录 */
      this.toast = new Toast(this, null, null)
      this.toast.show()
      // queryUserByOrgDepartment(this.globalUser.loginorgid, this.departmentid, rolesItem, searchTxt, currentPage, this.pagination.rowsPerPage)
      excuteApis(Object.assign({}, { orgid: this.globalUser.loginorgid, departmentid: this.departmentid, roleid: rolesItem, name: searchTxt, currentPage: currentPage, pageSize: this.pagination.rowsPerPage }), global.constant.userApisUrls, 'user', 'queryUserByOrgDepartment')
        .then((response) => {
          if (response.data && response.data.flag === 1 && response.data.data) {
            // 分页处理
            if (response.data.page) {
              let page = response.data.page
              // this.total = page.total // 总条目数
              // this.totalPages = page.totalPage // 总页数
              this.pagination.total = page.total
              this.pagination.page = page.currentPage // 当前页
              this.pagination.rowsPerPage = page.pageSize // 页大小
              // 设置显示的title
              this.display.displayTitle = this.display.displayName + ' ( ' + this.pagination.total + ' 人)'
            }
            this.recordList = response.data.data
          }
          this.toast.close()
        }
        )
        .catch((response) => {
          this.toast.close()
        })
    },
    /* 编辑用户 */
    editUserInfo (user) {
      this.editUser = user
      this.editUser.userid = user.id
      this.editUser.phone = user.account
      // delete this.editUser.account
      this.editUserDialog = true
      this.editUserRoleArr = []
      if (user.roleid) {
        let userRoleArr = user.roleid.toString().split(',') // 分割角色的id
        for (let j = 0, len = userRoleArr.length; j < len; j++) {
          let userRoleId = userRoleArr[j]
          excuteApis(Object.assign({}, { id: userRoleId }), global.constant.userApisUrls, 'role', 'queryRole').then(res => {
            // querySingleRole(this.globalUser.loginorgid, userRoleId).then(res => { // 根据角色的id查询角色
            let data = res.data
            if (data.flag === 1) {
              this.editUserRoleArr.push(data.data[0]) // 将角色的放入已经选择的数组
            }
          })
        }
        console.log(this.editUserRoleArr)
      }
      // 用户的性别
      // 查询用户的性别显示的文本
      let sexRequestParams = {
        dicttype: 'user-sex',
        dictvalue: this.editUser.sex
      }
      excuteApis(sexRequestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.editUser.sexDisText = res.data[0].dictname
        }
      })

      this.editUserOwnDept(this.globalUser.loginorgid) // 查询下拉列表的数据
    },

    // //  =========================================树形结构的操作=========================================================
    filterTreeNode (value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    renderContent (h, { node, data, store }) {
      let isShow = true
      let authAdd = this.$authed('POST', 'system/memberManage')
      let authEdit = this.$authed('PUT', 'system/memberManage')
      let authDel = this.$authed('DELETE', 'system/memberManage')
      let isDelteShow = true
      if (data.sign === '1') {
        isShow = false
      }
      if (node.childNodes.length === 1) {
        isDelteShow = false
      }
      // let levelDistance = node.level * 10
      // let id = 'node-title-' + data.id
      return (<span class="custom-tree-node">
        <span class="custom-tree-node-title" onClick={() => {
          this.handleLeftTreeNode(data, node)
        }}>{data.name}</span>
        <span>
          <v-menu right class="custom-tree-node-menu">
            <v-btn icon right slot="activator" fab style="width:25px;height:25px; margin-top: -26px;">
              <v-icon aria-hidden style="z-index:1;">more_vert</v-icon>
            </v-btn>
            <v-list>
              <v-list-tile v-show={authAdd} onClick={() => {
                this.openEditChildOrgDialog('addDept', node, data)
              }}>
                <v-list-tile-title>添加子部门</v-list-tile-title>
              </v-list-tile>
              <v-list-tile v-show={isShow && authEdit} onClick={() => {
                this.openEditChildOrgDialog('updateDept', node, data)
              }}>
                <v-list-tile-title>编辑部门</v-list-tile-title>
              </v-list-tile>
              <v-list-tile v-show={isShow && isDelteShow && authDel} onClick={() => {
                this.openEditChildOrgDialog('delDept', node, data)
              }}>
                <v-list-tile-title>删除部门</v-list-tile-title>
              </v-list-tile>
            </v-list>
          </v-menu>
        </span>
      </span>)
    },
    handleLeftTreeNode (data, node, com) {
      if (data.sign === '2') { // sign //标识：1：组织，2：部门，3：用户，4：角色
        // console.log(this.orgname + ' > ' + node.name + '( ' + node.count + ' 人)')
        // this.display.displayTitle = this.display.orgname + ' > ' + data.name + '( ' + data.count + ' 人)'
        this.display.displayName = this.display.orgname + ' > ' + data.name
        this.departmentid = data.supportid ? data.supportid : ''
      } else if (data.sign === '1') { // sign //标识：1：组织，2：部门，3：用户，4：角色
        // this.display.displayTitle = data.name + '( ' + data.count + ' 人)'
        this.display.displayName = data.name
        this.departmentid = ''
      }
      this.pagination.page = 1
      this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
    },
    handleEditUserTreeNode (data, node, com) {
      if (data.sign === '2') { // 部门
        this.editUser.departmentname = data.name
        this.editUser.departmentid = data.id
        this.editUser.departFlag = data.sign
        this.rightUserListSelectDeptDialog = false
        this.showEditUserSelectedDeptTips = false
      } else {
        this.showEditUserSelectedDeptTips = true
      }
    },
    handleEditDeptTreeNode (data, node, com) {
      // if (data.sign === '2') {
      this.editDeptSelectedDeptTips = ''
      this.showEditDeptSelectedDeptTips = false
      if (data.supportid === this.childDept.supportid || data.id === this.childDept.id) {
        this.editDeptSelectedDeptTips = '不能选择当前部门作为上级部门'
        this.showEditDeptSelectedDeptTips = true
        return
      }
      this.childDept.parentname = data.name
      this.childDept.parentid = data.id
      this.childDept.parentFlag = data.sign
      this.leftDeptTreeSelectDeptDialog = false
      this.showEditDeptSelectedDeptTips = false
      this.editDeptSelectedDeptTips = ''
      // this.childDept.supportid = data.supportid
      this.fetchUser(data.supportid)
    },
    selectedUserSure () {
      console.log('选择部门负责人')
    },
    cancelUserSelected () {
      console.log('取消选择部门负责人')
      this.selectChargeuserDialog = false
    },
    fetchUser (departmentid) {
      if (this.globalUser.loginorgid === '') {
        return
      }
      /* 获取所有成员 */
      excuteApis(Object.assign({}, { orgid: this.globalUser.loginorgid, departmentid: departmentid }), global.constant.userApisUrls, 'user', 'queryUserByOrgDepartment').then(response => {
        // queryAllUserByOrgDepartment(this.globalUser.loginorgid, departmentid).then((response) => {
        if (response.data && response.data.flag === 1 && response.data.data) {
          this.chargeusers = response.data.data
        }
      }).catch((response) => { })
    },
    // ===========部门的操作============================================================================
    // 弹框
    openEditChildOrgDialog (oper, node, data) {
      // 清空数据
      this.setChildDeptPropertyValue()
      this.$validator.reset()
      if (oper === 'addDept') {
        this.childDept.titleDisTxt = '添加子部门'
        this.childDept.parentid = data.id
        this.childDept.parentname = data.name
        this.editChildDeptDialog = true // 1. 打开弹窗
        this.childDept.parentFlag = data.sign
        // this.childDept.supportid = data.supportid
        this.fetchUser(data.supportid)
        this.editDeptParentDept(this.globalUser.loginorgid)
      } else if (oper === 'updateDept') {
        this.childDept.titleDisTxt = '编辑子部门'
        this.childDept.id = data.id
        this.childDept.parentFlag = node.parent.data.sign
        // 根据id查询部门的信息
        if (this.childDept.id !== '') { //
          excuteApis(Object.assign({}, { departmentid: this.childDept.id }), global.constant.userApisUrls, 'depart', 'queryDepartmentDetail').then(data => {
            // queryDepartmentDetail(this.childDept.id).then(data => {
            let res = data.data
            if (res.flag === 1 && res.data != null) { // 如果请求成功
              this.childDept.name = res.data.name
              this.childDept.departno = res.data.departno
              this.childDept.chargeuserid = res.data.chargeuserid
              this.childDept.parentid = res.data.parentid
              this.childDept.parentname = node.parent.data.name
              this.childDept.supportid = res.data.supportid
            }
          })
        }
        this.fetchUser(node.parent.data.supportid) // 上级部门查询用户下拉数据
        this.editDeptParentDept(this.globalUser.loginorgid)
        this.editChildDeptDialog = true // 1. 打开弹窗
      } else if (oper === 'delDept') {
        this.childDept.name = data.name
        this.childDept.id = data.id
        this.childDept.supportid = data.supportid
        this.delChildDeptDialog = true // 1. 打开弹窗
      }
    },
    // 添加、编辑子部门
    confirmEditChildDept (oper) {
      this.$validator.validateAll(['name']).then((result) => {
        if (result) { // 如果表单验证通过
          console.log(this.childDept)
          excuteApis(Object.assign({}, this.childDept, { parentid: this.childDept.parentFlag === '1' ? '' : this.childDept.parentid }), global.constant.userApisUrls, 'depart', 'saveDepartment').then(data => {
            // saveDepartment(this.childDept).then(data => {
            let res = data.data
            if (res.flag === 1) {
              this.editChildDeptDialog = false // 1. 关闭弹窗
              // 2.刷新树
              if (oper === 'addDept') { // 新增树结构

              } else if (oper === 'updateDept') { // 更新树结构

              }
              this.orgDepartUserTree(this.globalUser.loginorgid)
              // this.openTipsDialog('success', res.message)
              this.$toast.top(res.message)
            } else {
              // this.openTipsDialog('red lighten-1', '保存部门失败!' + res.message)
              this.$toast.top('保存部门失败!' + res.message)
            }
          })
        }
      }
      )
    },
    /* 取消编辑部门 */
    cancelEditChildDept () {
      this.editChildDeptDialog = false // 1.  关闭弹窗
    },
    /* 删除部门 */
    confirmDelChildDept () {
      excuteApis(Object.assign({}, { id: this.childDept.id, deptSupportid: this.childDept.supportid }), global.constant.userApisUrls, 'depart', 'deleteDepartment').then(data => {
        // deleteDepartment(this.childDept.id, this.childDept.supportid).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.delChildDeptDialog = false // 1.  关闭弹窗
          // 2. 删除部门成功之后，删除树节点
          this.orgDepartUserTree(this.globalUser.loginorgid)
          // this.openTipsDialog('success', res.message)
          this.$toast.top(res.message)
        } else {
          // this.openTipsDialog('red lighten-1', '删除部门失败!' + res.message)
          this.$toast.top('删除部门失败!' + res.message)
          this.delChildDeptDialog = false
        }
      })
    },
    /* 取消删除部门 */
    cancelDelChildDept () {
      this.delChildDeptDialog = false // 1.  关闭弹窗
    },
    // ===========编辑用户弹出框============================================================================
    /* 取消编辑用户 */
    cancelEditUser () {
      this.editUserRoleArr = []
      this.editUserDialog = false // 2. 关闭弹窗
    },
    /* 确认编辑用户 */
    confirmEditUser () {
      // 1.提交表单
      this.editUser.roleid = ''
      if (this.editUserRoleArr) {
        for (let tindex in this.editUserRoleArr) {
          if (this.editUser.roleid === '') {
            this.editUser.roleid = this.editUserRoleArr[tindex].id
            this.editUser.rolename = this.editUserRoleArr[tindex].rolename
          } else {
            this.editUser.roleid = this.editUser.roleid + ',' + this.editUserRoleArr[tindex].id
            this.editUser.rolename = this.editUser.rolename + ',' + this.editUserRoleArr[tindex].rolename
          }
        }
      }
      excuteApis(Object.assign({}, this.editUser), global.constant.userApisUrls, 'user', 'addOrUpdate').then(data => {
        // personalDataEdit(this.editUser).then(data => {
        let res = data.data
        if (res.flag === 1) {
          // 将修改的用户信息放在
          if (this.globalUser.id === this.editUser.id) {
            this.editUser.staffLastUpdateTime = res.data.staffLastUpdateTime
            this.setCacheUserInfo(this.editUser)
          }
          // this.openTipsDialog('success', res.message)
          this.$toast.top(res.message)
          this.editUserDialog = false // 2. 关闭弹窗
          this.editUserRoleArr = []
          this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
        } else {
          // this.openTipsDialog('red lighten-1', '编辑用户失败' + res.message)
          this.$toast.top('编辑用户失败' + res.message)
        }
      })
    },
    // 赋值操作
    setCacheUserInfo (editUser) {
      let user = global.helper.ls.get('user')
      user.jobnumber = editUser.jobnumber
      user.roleid = editUser.roleid
      user.rolename = editUser.rolename
      if (editUser.staffLastUpdateTime) {
        user.operateredittime = editUser.staffLastUpdateTime
      }
      this.$store.dispatch('cacheUserInfo', user)
    },
    /* ********************* 转交企业 ************************************/
    openTurnOrgDialog () {
      this.editUserDialog = false
      this.turnOrgDialog = true // 2. 打开弹窗
    },

    cancelTurnOrg () {
      this.turnOrgDialog = false // 2. 关闭弹窗
    },
    confirmTurnOrg () {
      // 判断当前登陆的人是否是企业的拥有者
      // 1.提交表单
      excuteApis(Object.assign({}, { loginuserid: this.globalUser.id, userid: this.editUser.userid, password: this.inputPwd, orgid: this.globalUser.loginorgid }), global.constant.userApisUrls, 'org', 'turnOrg').then(data => {
        // turnOrg(this.globalUser.id, this.editUser.userid, this.inputPwd, this.globalUser.loginorgid).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.turnOrgDialog = false // 2. 关闭弹窗
          this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
          // this.openTipsDialog('success', res.message)
          this.$toast.top(res.message)
        } else {
          // this.openTipsDialog('red lighten-1', '转交企业失败' + res.message)
          this.$toast.top(res.message)
        }
      })
    },
    /* ********************* 移除企业 ************************************/
    openRemoveUserFromOrgDialog () {
      this.editUserDialog = false
      this.removeUserFromOrgDialog = true // 2. 打开弹窗
    },
    cancelRemoveUserFromOrg () {
      this.removeUserFromOrgDialog = false // 2. 关闭弹窗
    },
    confirmRemoveUserFromOrg () {
      console.log(this.editUser)
      // 1.提交表单
      excuteApis(Object.assign({}, { userid: this.editUser.userid, orgid: this.globalUser.loginorgid }), global.constant.userApisUrls, 'user', 'removeUserFromOrg').then(data => {
        // removeUserFromOrg(this.editUser.userid, this.globalUser.loginorgid).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.removeUserFromOrgDialog = false // 2. 关闭弹窗
          this.orgDepartUserTree(this.globalUser.loginorgid)
          this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
          // this.openTipsDialog('success', res.message)
          this.$toast.top(res.message)
        } else {
          // this.openTipsDialog('red lighten-1', '移除企业失败' + res.message)
          this.$toast.top('移除企业失败' + res.message)
        }
      })
    },
    // 清空属性的值
    setChildDeptPropertyValue () {
      this.childDept.titleDisTxt = ''
      this.childDept.name = ''
      this.childDept.departno = ''
      this.childDept.parentid = ''
      this.childDept.parentname = ''
      this.childDept.chargeuserid = ''
      this.childDept.id = ''
      this.childDept.parentFlag = ''
    }
  },
  watch: {
    filterLeftTreeText (val) {
      this.$refs.leftTree.filter(val)
    },
    filterEditUserTreeText (val) {
      this.$refs.editUserTreeDept.filter(val)
    },
    filterEditDeptTreeText (val) {
      this.$refs.editDeptTreeDept.filter(val)
    },
    /* 监听查询输入框值改变获取数据 */
    searchTxt: debounce(600, function (val) {
      this.getRecordList(this.rolesItem, val)
    }),
    /* 监听下拉框值改变获取数据 */
    rolesItem (val) {
      this.getRecordList(val, this.searchTxt)
    },
    'pagination.page' (val) {
      this.getRecordList(this.rolesItem, this.searchTxt, val)
    },
    'pagination.rowsPerPage' (val) {
      this.getRecordList(this.rolesItem, this.searchTxt, val)
    },
    /* 监听分页 */
    pagination: {
      handler: function (val) {
        this.getRecordList(this.rolesItem, this.searchTxt, val.page)
      },
      deep: true
    },
    /* 监听上级部门变化、部门的负责人也随着变化 */
    // childDept (val) {
    //   this.fetchUser(val)
    // }
    globalUser: function (val, oldVal) {
      if (val) {
        this.orgDepartUserTree(this.globalUser.loginorgid)
        this.queryRole(this.globalUser.loginorgid)
        this.childDept.orgid = this.globalUser.loginorgid
        this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
      }
    }
  }
}
</script>
<style scoped>
/* 垂直居中 */
.line-center {
  align-items: center;
}

.selectedtitle {
  line-height: 45px;
  background-color: #39bbb0;
  color: white;
  font-weight: bold;
  padding-left: 10px;
}

.search {
  padding-top: 10px;
  top: -10px;
}

.selectedTips {
  text-align: center;
  padding: 23px 0 0;
  color: red;
}
.hideoverflow {
  position: absolute;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.treeClass {
  width: 300px;
}
</style>
