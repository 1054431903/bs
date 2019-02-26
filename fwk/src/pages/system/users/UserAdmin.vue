<template>
  <v-card class="page-content">
    <v-layout row>
      <v-flex xs3
              md3>
        <!--
        <v-flex>
          <input type="text" style="border: groove" v-model="searchword" placeholder="searchword"/>
          <button type="button" @click="search" style="border:groove; font-size:14px">&nbsp;&nbsp;搜 索&nbsp;&nbsp; </button>
        </v-flex>
        -->
        <!--<v-flex> -->
        <v-tree ref='tree'
                :data='treeData'
                :multiple='false'
                :tpl='tpl'
                :halfcheck='true'
                style="margin-left: 0px"
                v-model="searchword" />
        <!-- </v-flex> -->
      </v-flex>
      <!-- 顶部工具条 -->
      <v-flex xs9
              md9>
        <v-card>
          <v-card-title>
            <span>{{display.displayTitle}}</span>
          </v-card-title>
          <v-card-text>
            <v-layout class="px-0"
                      row
                      wrap>
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
                              append-icon="fa-search"
                              single-line>
                </v-text-field>
              </v-flex>
            </v-layout>
            <v-layout class="px-0"
                      row
                      wrap>
              <v-flex xs12>
                <v-data-table v-model="selected"
                              :headers="headers"
                              :items="recordList"
                              :total-items="pagination.totalItems"
                              :pagination.sync="pagination"
                              no-data-text="暂无数据"
                              no-results-text="暂无数据"
                              hide-actions
                              class="elevation-1">

                  <template slot="items"
                            slot-scope="props">
                    <td class="text-xs-left"
                        v-text="props.item.id"></td>
                    <td class="text-xs-left"
                        v-text="props.item.name"></td>
                    <td class="text-xs-left"
                        v-text="props.item.departmentname"></td>
                    <td class="text-xs-left"
                        v-text="props.item.rolename"></td>
                    <td class="text-xs-left"
                        v-text="props.item.phone"></td>
                    <td class="text-xs-left">

                      <!--<v-btn @click="editUserInfo(props.item.id, globalUser.loginorgid,props.item.rolename)" fab small>-->
                      <v-btn @click="editUserInfo(props.item)"
                             fab
                             small>
                        <v-icon>edit</v-icon>
                      </v-btn>

                    </td>
                  </template>
                </v-data-table>

              </v-flex>
            </v-layout>
            <!-- 分页 -->
            <v-layout>
              <v-flex xs12
                      text-xs-center>
                <v-pagination v-model="pagination.page"
                              :length="pagination.totalPage"></v-pagination>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <!-- Dialog -->
    <v-layout>
      <!-- 编辑用户的弹出框 -->
      <v-dialog v-model="editUserDialog"
                width="600">
        <form ref="formModal">
          <v-card>
            <v-card-title>
              <span>编辑</span>
            </v-card-title>

            <v-card-text>
              <v-container>
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
                                  single-line>
                    </v-text-field>
                  </v-flex>
                </v-layout>

              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-layout row
                        wrap>
                <v-flex xs8
                        text-xs-center>
                  <v-btn flat
                         @click.native="cancelEditUser">取消</v-btn>
                  <v-btn flat
                         @click.native="confirmEditUser">确认</v-btn>
                </v-flex>
                <!--
                <v-flex xs4 text-xs-right>
                  <v-btn @click="openRemoveUserFromOrgDialog()">移除企业</v-btn>
                </v-flex>
                -->
              </v-layout>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>
      <!-- 转交给企业弹出框 -->
      <v-dialog v-model="turnOrgDialog"
                width="450">
        <form ref="formModal">
          <v-card>
            <v-card-title>
              <span>转交企业</span>
            </v-card-title>

            <v-card-text>
              <v-container>
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
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-layout row
                        wrap>
                <v-flex xs12
                        text-xs-center>
                  <v-btn flat
                         @click.native="cancelTurnOrg">取消</v-btn>
                  <v-btn flat
                         @click.native="confirmTurnOrg">确认</v-btn>
                </v-flex>
              </v-layout>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>

      <!-- 移除企业的弹框-->
      <v-dialog v-model="removeUserFromOrgDialog"
                width="450">
        <form ref="formModal">
          <v-card>
            <v-card-title>
              <span>移除企业</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <!-- 编辑用户  -->
                <v-layout row
                          wrap>
                  <v-flex xs12>
                    <v-subheader>确定将 "{{editUser.name}}" 移除企业吗？</v-subheader>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-layout row
                        wrap>
                <v-flex xs12
                        text-xs-center>
                  <v-btn flat
                         @click.native="cancelRemoveUserFromOrg">取消</v-btn>
                  <v-btn flat
                         @click.native="confirmRemoveUserFromOrg">确认</v-btn>
                </v-flex>
              </v-layout>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>

      <!-- 添加子部门、编辑子部门的弹框-->
      <v-dialog v-model="editChildDeptDialog"
                width="450">
        <form ref="formModal">
          <v-card>
            <v-card-title>
              <span>{{childDept.titleDisTxt}}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <!-- 部门名称-->
                <v-layout row
                          wrap>
                  <v-flex xs4>
                    <v-subheader>*&nbsp;部门名称</v-subheader>
                  </v-flex>
                  <v-flex xs6>
                    <v-text-field label="请输入子部门名称"
                                  v-model="childDept.name"
                                  single-line
                                  :error-messages="errors.collect('name')"
                                  v-validate="'required'"
                                  data-vv-name="name"
                                  data-vv-as="部门名称"></v-text-field>
                  </v-flex>
                </v-layout>
                <!-- 部门编号-->
                <v-layout row
                          wrap>
                  <v-flex xs4>
                    <v-subheader>&nbsp;部门编号</v-subheader>
                  </v-flex>
                  <v-flex xs6>
                    <v-text-field label="请输入部门编号"
                                  v-model="childDept.departno"
                                  single-line
                                  :error-messages="errors.collect('departno')"
                                  v-validate="'required'"
                                  data-vv-name="departno"
                                  data-vv-as="部门编号"></v-text-field>
                  </v-flex>
                </v-layout>

                <!-- 上级部门-->
                <v-layout row
                          wrap>
                  <v-flex xs4>
                    <v-subheader>*&nbsp;上级部门</v-subheader>
                  </v-flex>
                  <v-flex xs6>
                    <v-text-field label="请选择上级部门"
                                  v-model="childDept.parentname"
                                  single-line
                                  @focus="leftDeptTreeSelectDeptDialog = !leftDeptTreeSelectDeptDialog"></v-text-field>
                  </v-flex>
                </v-layout>

                <!-- 部门负责人 -->
                <v-layout row
                          wrap>
                  <v-flex xs4>
                    <v-subheader>部门负责人</v-subheader>
                  </v-flex>
                  <v-flex xs6>

                    <v-select v-bind:items="chargeusers"
                              label="请选择用户"
                              v-model="childDept.chargeuserid"
                              item-text="name"
                              item-value="id"
                              single-line>
                    </v-select>
                  </v-flex>
                </v-layout>

              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-layout row
                        wrap>
                <v-flex xs12
                        text-xs-center>
                  <v-btn flat
                         @click.native="cancelEditChildDept">取消</v-btn>
                  <v-btn flat
                         @click.native="confirmEditChildDept">确认</v-btn>
                </v-flex>
              </v-layout>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>
      <!-- 删除子部门的弹出框-->
      <v-dialog v-model="delChildDeptDialog"
                width="450">
        <form ref="formModal">
          <v-card>
            <v-card-title>
              <span>删除子部门</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <!-- 删除子部门  -->
                <v-layout row
                          wrap>
                  <v-flex xs12>
                    <v-subheader>确认要删除 "{{childDept.name}}" 吗？</v-subheader>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-layout row
                        wrap>
                <v-flex xs12
                        text-xs-center>
                  <v-btn flat
                         @click.native="cancelDelChildDept">取消</v-btn>
                  <v-btn flat
                         @click.native="confirmDelChildDept">确认</v-btn>
                </v-flex>
              </v-layout>
            </v-card-actions>
          </v-card>
        </form>
      </v-dialog>
      <!-- 部门树的操作-->
    </v-layout>
    <!-- 编辑部门选择上级部门的弹出框-->
    <v-layout row
              justify-center>
      <v-dialog v-model="leftDeptTreeSelectDeptDialog"
                persistent
                max-width="480">
        <v-card>
          <v-layout row
                    wrap>
            <v-flex xs6>
              <div pl-3
                   class="subheading selectedtitle">选择部门</div>
            </v-flex>
            <v-flex xs6>
              <div pl-3
                   class="text-xs-right">
                <v-btn small
                       @click="leftDeptTreeSelectDeptDialog = false">
                  <v-icon color="red lighten-2"
                          left>remove_circle</v-icon>取&nbsp;消
                </v-btn>
              </div>
            </v-flex>
          </v-layout>
          <v-divider></v-divider>
          <v-layout py-1
                    row
                    wrap>
            <v-flex pl-3
                    xs6>
              <v-text-field label="输入部门名称关键词查询"
                            v-model="editDeptKeyword"
                            single-line
                            hide-details
                            class="search"></v-text-field>
            </v-flex>
            <v-flex xs6
                    v-if="showEditDeptSelectedDeptTips">
              <div class="selectedTips">请选择部门</div>
            </v-flex>
          </v-layout>
          <v-layout row
                    wrap>
            <v-flex xs12>
              <v-tree ref='editDeptTreeDepart'
                      :data='editDeptDialogSelctDeptData'
                      :multiple='false'
                      :tpl='editDeptTpl'
                      :halfcheck='true' />
            </v-flex>
          </v-layout>
        </v-card>
      </v-dialog>
    </v-layout>
    <!-- 编辑用户选择部门的弹出框-->
    <v-layout row
              justify-center>
      <v-dialog v-model="rightUserListSelectDeptDialog"
                persistent
                max-width="480">
        <v-card>
          <v-layout row
                    wrap>
            <v-flex xs6>
              <div pl-3
                   class="subheading selectedtitle">选择部门</div>
            </v-flex>
            <v-flex xs6>
              <div pl-3
                   class="text-xs-right">
                <v-btn small
                       @click="rightUserListSelectDeptDialog = false">
                  <v-icon color="red lighten-2"
                          left>remove_circle</v-icon>取&nbsp;消
                </v-btn>
              </div>
            </v-flex>
          </v-layout>
          <v-divider></v-divider>
          <v-layout py-1
                    row
                    wrap>
            <v-flex pl-3
                    xs6>
              <v-text-field label="输入部门名称关键词查询"
                            v-model="editUserKeyword"
                            single-line
                            hide-details
                            class="search"></v-text-field>
            </v-flex>
            <v-flex xs6
                    v-if="showEditUserSelectedDeptTips">
              <div class="selectedTips">请选择部门</div>
            </v-flex>
          </v-layout>
          <v-layout row
                    wrap>
            <v-flex xs12>
              <v-tree ref='editUserTreeDepart'
                      :data='editUserDialogSelctDeptData'
                      :multiple='false'
                      :tpl='editUserTpl'
                      :halfcheck='true' />
            </v-flex>
          </v-layout>
        </v-card>
      </v-dialog>
    </v-layout>
    <!-- 编辑部门选择的部门负责人弹出框-->
    <v-layout row
              justify-center>
      <v-dialog v-model="selectChargeuserDialog"
                persistent
                max-width="480">
        <v-card>
          <v-card-title class="headline">选择用户树</v-card-title>
          <v-card-text>内容</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1"
                   flat
                   @click.native.stop="selectedUserSure">确定</v-btn>
            <v-btn color="green darken-1"
                   flat
                   @click.native.stop="cancelUserSelected">取消</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-layout>
    <v-snackbar :timeout="toast.timeout"
                :color="toast.color"
                :multi-line="toast.mode === 'multi-line'"
                :vertical="toast.mode === 'vertical'"
                v-model="toast.show"
                right>
      {{ toast.msg }}
      <v-btn dark
             flat
             @click.native="toast.show = false">Close</v-btn>
    </v-snackbar>
  </v-card>
</template>
<script>
import { excuteApis } from '@/apis'
import debounce from 'throttle-debounce/debounce'
// import { orgDepartUserTree, queryRole, querySingleRole, queryUserByOrgDepartment, queryDepartmentDetail, queryAllUserByOrgDepartment, personalDataEdit, turnOrg, removeUserFromOrg, saveDepartment, deleteDepartment } from '@/apis/systems/user'
export default {
  data () {
    return {
      // ================
      searchword: null, // 左边树形结构的关键字搜索
      // ================
      globalUser: null, // 用户信息
      display: { // 右边列表的公司--》部门（人数）
        displayTitle: '',
        orgname: ''
      },
      inputPwd: '', // 移交企业时输入的密码
      departmentid: null, // 选中的部门信息(左边树形结构)
      rolesItem: null, // 选择角色（列表的查询条件）
      roles: [], // 编辑用户所选的角色
      searchTxt: null, // 请输入用户账号，姓名进行搜索
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
      page: {}, // 关于当前页
      /* 分页 */
      pagination: {
        rowsPerPage: 6 // 每页显示几条数据
        // sortBy: 'column', // 按什么排序
        // page: 3, // 当前页
        // totalItems: 100 // 总条目数
      },
      recordList: [], // 用户列表
      treeData: [], // 左边树形结构的数据
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
        id: null // 编号
      },
      // =====弹出框的变量=====
      editUserRoleArr: [], // 编辑用户已经选择的角色
      editUser: {
        userid: null,
        loginorgid: null,
        name: '', // 姓名
        nickname: '', // 昵称
        sex: '',   // 性别
        sexDisText: '',   // 性别
        phone: '', // 手机
        email: '', // 邮箱
        birthday: '', // 出生日期
        departmentid: null, // 归属部门
        departmentname: '', // 归属部门
        isdepartmentbelong: false,
        roleid: '', // 多个以逗号进行分开
        rolename: '',
        jobnumber: '', // 工号
        departFlag: null // 在组织下还是在部门下
      }, // 用户信息
      editUserDialog: false, // 编辑用户的弹框
      turnOrgDialog: false, // 转交企业的弹框
      removeUserFromOrgDialog: false, // 移除企业的弹框
      editChildDeptDialog: false,  // 添加子部门、编辑子部门的弹框
      delChildDeptDialog: false, // 删除子部门的对话框
      chargeusers: [], // 部门下的用户
      currentTreeNode: null, // 当前的节点
      // =======弹出树形结构=====
      editDeptKeyword: '', // 编辑部门弹出树形结构输入的关键字搜索
      editUserKeyword: '', // 编辑用户弹出树形结构输入的关键字搜索
      leftDeptTreeSelectDeptDialog: false, // 编辑部门选择上级部门的弹框
      showEditDeptSelectedDeptTips: false, // 编辑部门选择上级部门的提示
      rightUserListSelectDeptDialog: false, // 编辑用户选择部门的弹框
      showEditUserSelectedDeptTips: false, // 编辑用户选择部门的提示
      selectChargeuserDialog: false, // 编辑部门选择部门负责人的弹框
      toast: {
        show: false,
        color: '',
        mode: 'vertical',
        timeout: 6000,
        msg: ''
      }
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
        // orgDepartUserTree(loginorgid).then(data => {
        let result = data.data
        if (result.flag === 1) {
          this.treeData = result.data
          // this.currentTreeNode = result.data[0]
          // this.recursionTree(result.data)
          this.treeData.forEach(function (item) {
            item.expanded = true
            item.children.forEach(function (item1) {
              item1.expanded = true
            })
          })
          this.display.orgname = result.data[0].name
          this.display.displayTitle = result.data[0].name + '( ' + result.data[0].count + ' 人)'
        }
      })
        .catch((res) => { })
    },
    /**
     * 编辑部门上级部门的数据
     */
    editDeptParentDept (loginorgid) {
      excuteApis(Object.assign({}, { orgid: loginorgid, showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(data => {
        // orgDepartUserTree(loginorgid).then(data => {
        let result = data.data
        if (result.flag === 1) {
          this.editDeptDialogSelctDeptData = result.data
          this.editDeptDialogSelctDeptData.forEach(function (item) {
            item.expanded = true
            item.children.forEach(function (item1) {
              item1.expanded = true
            })
          })
        }
      })
        .catch((res) => { })
    },
    /**
     * 编辑部门上级部门的数据
     */
    editUserOwnDept (loginorgid) {
      excuteApis(Object.assign({}, { orgid: loginorgid, showtype: '', t: new Date().getTime() }), global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(data => {
        // orgDepartUserTree(loginorgid).then(data => {
        let result = data.data
        if (result.flag === 1) {
          this.editUserDialogSelctDeptData = result.data
          this.editUserDialogSelctDeptData.forEach(function (item) {
            item.expanded = true
            item.children.forEach(function (item1) {
              item1.expanded = true
            })
          })
        }
      })
        .catch((res) => { })
    },
    recursionTree (data) {
      for (let i = 0; i < data.length; i++) {
        data[i].expanded = true
        if (data[i].children) {
          this.recursionTree(data[i].children)
        }
      }
    },

    /* 查询角色列表 */
    queryRole (orgid) {
      excuteApis(Object.assign({}, { orgid: orgid }), global.constant.userApisUrls, 'role', 'queryRole').then(res => {
        // queryRole(orgid).then(res => {
        let data = res.data
        if (data.flag === 1) {
          this.roles = data.data
        } else {
          this.openTipsDialog('red lighten-1', res.message)
        }
      })
        .catch((res) => { })
    },
    /* 获取成员记录 */
    getRecordList (rolesItem, searchTxt, currentPage) {
      if (this.globalUser.loginorgid === '') {
        return
      }
      /* 获取成员记录 */
      // queryUserByOrgDepartment(this.globalUser.loginorgid, this.departmentid, rolesItem, searchTxt, currentPage, this.pagination.rowsPerPage)
      excuteApis(Object.assign({}, { orgid: this.globalUser.loginorgid, departmentid: this.departmentid, roleid: rolesItem, name: searchTxt, currentPage: currentPage, pageSize: this.pagination.rowsPerPage }), global.constant.userApisUrls, 'user', 'queryUserByOrgDepartment')
        .then((response) => {
          if (response.data && response.data.flag === 1 && response.data.data) {
            // 分页处理
            if (response.data.page) {
              let page = response.data.page
              this.pagination.totalItems = page.total // 总条目数
              this.pagination.totalPage = page.totalPage // 总页数
              this.pagination.page = page.currentPage // 当前页
              this.pagination.rowsPerPage = page.pageSize // 页大小
            }
            this.recordList = response.data.data
          }
        })
        .catch((response) => { })
    },
    /* 编辑用户 */
    editUserInfo (user) {
      this.editUser = user
      this.editUser.userid = user.id
      this.editUser.phone = user.account
      delete this.editUser.account
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
      }// 用户的性别
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
    tpl (node, ctx) { // 左边树形结构
      let isShow = true
      if (node.sign === '1') {
        isShow = false
      }
      let titleClass = node.selected ? 'node-title node-selected' : 'node-title'
      if (node.searched) titleClass += ' node-searched'
      return <span>
        <span class={titleClass} domPropsInnerHTML={node.name} onClick={() => {
          let selectedNode = ctx.props.node
          ctx.parent.nodeSelected(selectedNode)
          // let currentNode = ctx.parent.getSelectedNodes()[0]
          // this.handleNode(currentNode)
          this.handleNode(node)
        }}></span>
        <v-menu offset-x>
          <v-btn style="margin: 0px; padding: 0px;" class="grey darken-4" small icon slot="activator" dark>
            <v-icon>keyboard_arrow_down</v-icon>
          </v-btn>
          <v-list>

            <v-list-tile-title onClick={() => {
              this.openEditChildOrgDialog('addDept', node)
            }}><v-btn style="margin: 0px; padding: 0px;">添加子部门</v-btn></v-list-tile-title>

            <v-list-tile-title v-show={isShow} onClick={() => {
              this.openEditChildOrgDialog('updateDept', node)
            }}><v-btn style="margin: 0px; padding: 0px;">编辑部门</v-btn></v-list-tile-title>
            <v-list-tile-title v-show={isShow} onClick={() => {
              this.openEditChildOrgDialog('delDept', node)
            }}><v-btn style="margin: 0px; padding: 0px;">删除部门</v-btn></v-list-tile-title>
          </v-list>
        </v-menu>
      </span>
    },
    search () {
      this.$refs.tree.searchNodes(this.searchword)
    },
    editDeptTpl (node, ctx) { // 编辑部门选择上级部门弹出树
      let titleClass = node.editDeptSelected ? 'node-title node-selected' : 'node-title'
      if (node.searched) titleClass += ' node-searched'
      return <span>
        <span class={titleClass} domPropsInnerHTML={node.title} onClick={() => {
          ctx.parent.nodeSelected(ctx.props.node)
          let currentNode = ctx.parent.getSelectedNodes()[0]
          if (currentNode.sign === '2') {
            this.childDept.parentname = ctx.parent.getSelectedNodes()[0].name
            this.childDept.parentid = ctx.parent.getSelectedNodes()[0].id
            this.childDept.parentFlag = ctx.parent.getSelectedNodes()[0].sign
            this.leftDeptTreeSelectDeptDialog = false
            this.showEditDeptSelectedDeptTips = false
            this.fetchUser(this.childDept.parentid)
          } else {
            this.showEditDeptSelectedDeptTips = true
          }
        }}></span>
      </span>
    },
    editUserTpl (node, ctx) { // 编辑用户选择部门弹出树
      let titleClass = node.editUserSelected ? 'node-title node-selected' : 'node-title'
      if (node.searched) titleClass += ' node-searched'
      return <span>
        <span class={titleClass} domPropsInnerHTML={node.title} onClick={() => {
          ctx.parent.nodeSelected(ctx.props.node)
          let currentNode = ctx.parent.getSelectedNodes()[0]
          if (currentNode.sign === '2') { // 部门
            this.editUser.departmentname = ctx.parent.getSelectedNodes()[0].name
            this.editUser.departmentid = ctx.parent.getSelectedNodes()[0].id
            this.editUser.departFlag = ctx.parent.getSelectedNodes()[0].sign
            this.rightUserListSelectDeptDialog = false
            this.showEditUserSelectedDeptTips = false
          } else {
            this.showEditUserSelectedDeptTips = true
          }
        }}></span>
      </span>
    },
    searchEditDeptDepart (text) {
      this.$refs.editDeptTreeDepart.searchNodes(text)
    },
    searchEditUserDepart (text) {
      this.$refs.editUserTreeDepart.searchNodes(text)
    },
    handleNode (node) {
      if (node.sign === '2') { // sign //标识：1：组织，2：部门，3：用户，4：角色
        // console.log(this.orgname + ' > ' + node.name + '( ' + node.count + ' 人)')
        this.display.displayTitle = this.display.orgname + ' > ' + node.name + '( ' + node.count + ' 人)'
        // this.departmentid = node.id
        this.departmentid = node.supportid
      } else if (node.sign === '1') { // sign //标识：1：组织，2：部门，3：用户，4：角色
        this.display.displayTitle = node.name + '( ' + node.count + ' 人)'
        this.departmentid = ''
      }
      this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
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
      excuteApis(Object.assign({}, { orgid: this.globalUser.loginorgid, departmentid: departmentid }), global.constant.userApisUrls, 'user', 'queryUserByOrgDepartment')
        // queryAllUserByOrgDepartment(this.globalUser.loginorgid, departmentid)
        .then((response) => {
          if (response.data && response.data.flag === 1 && response.data.data) {
            this.chargeusers = response.data.data
          }
        })
        .catch((response) => { })
    },
    // ===========部门的操作============================================================================
    // 弹框
    openEditChildOrgDialog (oper, node) {
      this.currentTreeNode = node
      // 清空数据
      this.setChildDeptPropertyValue()
      if (oper === 'addDept') {
        this.childDept.titleDisTxt = '添加子部门'
        this.childDept.parentid = node.id
        this.childDept.parentname = node.name
        this.editChildDeptDialog = true // 1. 打开弹窗
        this.childDept.parentFlag = node.sign

        this.fetchUser(node.id)
        this.editDeptParentDept(this.globalUser.loginorgid)
      } else if (oper === 'updateDept') {
        this.childDept.titleDisTxt = '编辑子部门'
        this.childDept.id = node.id
        this.childDept.parentFlag = node.parent.sign

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
              this.childDept.parentname = node.parent.name
            }
          })
        }
        this.fetchUser(this.childDept.parentid) // 上级部门查询用户下拉数据
        this.editDeptParentDept(this.globalUser.loginorgid)
        this.editChildDeptDialog = true // 1. 打开弹窗
      } else if (oper === 'delDept') {
        this.childDept.name = node.name
        this.childDept.id = node.id
        this.delChildDeptDialog = true // 1. 打开弹窗
      }
    },
    // 添加、编辑子部门
    confirmEditChildDept (oper) {
      this.$validator.validateAll(['name', 'departno']).then((result) => {
        if (result) { // 如果表单验证通过
          console.log(this.childDept)
          excuteApis(Object.assign({}, this.childDept, { parentid: this.childDept.parentFlag === '1' ? '' : this.saveDepartment.parentid }), global.constant.userApisUrls, 'depart', 'saveDepartment').then(data => {
            // saveDepartment(this.childDept).then(data => {
            let res = data.data
            if (res.flag === 1) {
              this.editChildDeptDialog = false // 1. 关闭弹窗
              // 2.刷新树
              if (oper === 'addDept') { // 新增树结构
                // this.$refs.tree.addNode(this.currentTreeNode, this.currentTreeNode)
              } else if (oper === 'updateDept') { // 更新树结构

              }
              this.orgDepartUserTree(this.globalUser.loginorgid)
              this.openTipsDialog('success', res.message)
            } else {
              this.openTipsDialog('red lighten-1', res.message)
            }
          })
        }
      }
      )
    },
    /* 取消编辑部门 */
    cancelEditChildDept () {
      this.editChildDeptDialog = false // 1.  关闭弹窗
      // this.$refs.tree.addNode(this.currentTreeNode, {title: 123, name: 'zhangsan'})
      // this.$refs.tree.drag(this.currentTreeNode, this.currentTreeNode.parent.parent)
    },
    /* 删除部门 */
    confirmDelChildDept () {
      excuteApis(Object.assign({}, { id: this.childDept.id, deptSupportid: this.childDept.supportid }), global.constant.userApisUrls, 'depart', 'deleteDepartment').then(data => {
        // deleteDepartment(this.childDept.id).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.delChildDeptDialog = false // 1.  关闭弹窗
          // 2. 删除部门成功之后，删除树节点
          // this.$refs.tree.delNode(this.currentTreeNode.parent, this.currentTreeNode)
          this.orgDepartUserTree(this.globalUser.loginorgid)
          this.openTipsDialog('success', res.message)
        } else {
          this.openTipsDialog('red lighten-1', res.message)
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
          } else {
            this.editUser.roleid = this.editUser.roleid + ',' + this.editUserRoleArr[tindex].id
          }
        }
      }
      excuteApis(Object.assign({}, this.editUser), global.constant.userApisUrls, 'user', 'addOrUpdate').then(data => {
        // personalDataEdit(this.editUser).then(data => {
        let res = data.data
        if (res.flag === 1) {
          this.openTipsDialog('success', res.message)
          this.editUserDialog = false // 2. 关闭弹窗
          this.editUserRoleArr = []
          this.getRecordList(this.rolesItem, this.searchTxt, this.pagination.page)
        } else {
          this.openTipsDialog('red lighten-1', res.message)
        }
      })
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
          this.openTipsDialog('success', res.message)
        } else {
          this.openTipsDialog('red lighten-1', res.message)
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
          this.openTipsDialog('success', res.message)
        } else {
          this.openTipsDialog('red lighten-1', res.message)
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
    },
    openTipsDialog (color, msg) {
      this.toast.color = color
      this.toast.msg = msg
      this.toast.show = true
    }
  },
  watch: {
    /* 监听查询输入框值改变获取数据 */
    searchTxt: debounce(600, function (val) {
      this.getRecordList(this.rolesItem, val)
    }),
    /* 监听下拉框值改变获取数据 */
    rolesItem (val) {
      this.getRecordList(val, this.searchTxt)
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
    editDeptKeyword: function (val, oldVal) {
      this.searchEditDeptDepart(val)
    },
    editUserKeyword: function (val, oldVal) {
      this.searchEditUserDepart(val)
    },
    globalUser: function (val, oldVal) {
      if (val) {
        this.orgDepartUserTree(this.globalUser.loginorgid)
        this.queryRole(this.globalUser.loginorgid)
        this.childDept.orgid = this.globalUser.loginorgid
      }
    }
  }
}
</script>
<style scoped>
.btn {
  border: 1px solid #ccc;
  cursor: pointer;
}
/* 垂直居中 */
.line-center {
  align-items: center;
}
.selectedtitle {
  line-height: 45px;
}
.search {
  padding-top: 10px;
  top: -10px;
}
.selectedTips {
  text-align: center;
  padding-top: 15px;
  color: red;
}
</style>
