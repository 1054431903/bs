<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow="addOrEditPlanDialog"
                   :title="addOrEditPlanTitle"
                   :modalMaxWidth="600"
                   :bodyHeight="270">
      <v-form v-model="planFormValid"
              ref="planForm"
              lazy-validation
              autocomplete="off">
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-text-field label="计划名称"
                          v-model="planName"
                          counter="50"
                          maxlength="50"
                          required
                          :rules="rules.name">
            </v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <div style="color: rgba(0, 0, 0, 0.47);">计划参与人：只有被添加的成员才能看见此计划</div>
          </v-flex>
          <v-flex xs12
                  py-2>
            <v-divider></v-divider>
          </v-flex>
          <v-flex xs12
                  py-2>
            <v-layout row
                      wrap>
              <v-flex xs1
                      mr-2
                      mb-2>
                <div :title="user.name || user.mobile">
                  <v-avatar size="50">
                    <img class="zengjia"
                         :src="user.avatar || `/static/task/fuzenren.png`"
                         @click="showSelectStaffDialog" />
                  </v-avatar>
                </div>
              </v-flex>
              <template v-for="(participator, index) in selectedStaffList">
                <v-flex xs1
                        mr-2
                        mb-2
                        class="staff"
                        :key="participator.id">
                  <div :title="participator.name"
                       @mouseenter="getSelectedStaff(index)"
                       @mouseleave="curStaffId = null">
                    <v-avatar size="50">
                      <img :src="participator.avatar || `/static/task/fuzenren.png`"
                           @click="showSelectStaffDialog" />
                      <div class="remove"
                           @click="removeStaff(index)"
                           v-if="curStaffId === participator.id">
                        <v-avatar class="grey"
                                  size="15px">
                          <img class="zengjia"
                               :src="`static/sop/ic_close_white_24px.svg`" />
                        </v-avatar>
                      </div>
                    </v-avatar>
                  </div>
                </v-flex>
              </template>
              <v-flex xs1
                      mb-2>
                <v-avatar size="50">
                  <img class="zengjia"
                       src="static/task/zengjia.png"
                       @click="showSelectStaffDialog" />
                </v-avatar>
              </v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-layout row
                  wrap>
          <v-flex xs12>
            <v-autocomplete label="适用品类"
                            v-model="categoryId"
                            :items="categorylistExcludeRemoved"
                            item-text="name"
                            item-value="id"
                            no-data-text="未过滤到匹配的数据"
                            clearable
                            single-line>
            </v-autocomplete>
          </v-flex>
        </v-layout>

      </v-form>
      <template slot="btnActions">
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary"
                 flat
                 @click.native="cancel">
            取消
          </v-btn>
          <v-btn color="primary"
                 flat
                 @click.native="save">
            完成
          </v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>
    <v-selectedstaff :visible.sync="selectStaffDialog"
                     title="选择参与人"
                     :issearch="true"
                     :staffs.sync="selectedStaffIdList"
                     searchlabel="请输入参与人姓名进行搜索"
                     :stafflist="userListExcludeLonginUser"
                     :multiselected="true"
                     :showKeystaffBtn="false">
    </v-selectedstaff>
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  name: 'v-farmerplansadd',
  props: {
    visible: {
      type: String,
      default: 'IS_NONE'
    },
    title: {
      type: String,
      default: ''
    },
    // 当前组织下所有人员对象列表
    userlist: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 当前系统所有品类列表
    categorylist: {
      type: Array,
      default: () => {
        return []
      }
    },
    planid: {
      type: String,
      default: null
    },
    // 是否是从计划年视图缺省页点击添加
    isdefaultenter: {
      type: Boolean,
      default: false
    },
    refreshFn: {
      type: Function,
      default: () => { }
    }
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_COPY': true,
      'IS_NONE': false
    }
    return {
      addOrEditPlanDialog: false,
      addOrEdit: ADD_OR_EDIT,
      addOrEditPlanTitle: '新建计划',
      planFormValid: true,
      planName: null,
      // 当前登录用户对象
      user: null,
      // 当前选择的品类ID
      categoryId: null,
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        name: [
          (v) => !!v || '必填项',
          (v) => v && v.length <= 50 || '不超过50个字符'
        ]
      },
      // 选择参与人弹窗
      selectStaffDialog: false,
      // 已选择的参与人ID列表
      selectedStaffIdList: [],
      // 已选择的参与人对象列表
      selectedStaffList: [],
      // 当前选中的人员ID
      curStaffId: null,
      // 当前组织下所有人员对象列表排除当前登录用户
      userListExcludeLonginUser: [],
      // 当前操作方式
      curOperate: 'IS_NONE',
      // 排除已删除的品类列表
      categorylistExcludeRemoved: []
    }
  },
  created () {
    this.user = global.helper.ls.get('user')
  },
  computed: {
    tenantId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  watch: {
    visible (v) {
      this.curOperate = v
      this.disposeModalDialog(v)
    },
    selectedStaffIdList: function (v) {
      let tmp = []
      // 提取参与人信息
      for (let i = 0; i < v.length; i++) {
        tmp.push(this.getStaffInfoById(v[i]))
      }
      this.selectedStaffList = tmp
    },
    categoryId (v) {
      this.categoryId = v
    },
    userlist (v) {
      this.getUserInfoExcludeLoginUser()
    },
    categorylist (v) {
      this.getCategoryListExcludeRemoved()
    }
  },
  mounted () {
    this.getCategoryListExcludeRemoved()
    this.getUserInfoExcludeLoginUser()
  },
  methods: {
    save () {
      if (this.$refs['planForm'].validate()) {
        let participators = []
        let plan = {}
        switch (this.curOperate) {
          case 'IS_ADD':
            plan.tenant = { $oid: this.tenantId }
            if (this.categoryId) {
              plan.category = { $oid: this.categoryId }
            }
            plan.name = this.planName ? this.planName.trim() : ''
            plan.creator = { $oid: this.user.supportid }
            // 把当前计划创建人(当前登录人)一起插入计划参与人列表中
            participators.push({ $oid: this.user.supportid })
            this.selectedStaffIdList.map((id) => {
              participators.push({ $oid: id })
            })
            plan.participators = participators
            mongo.db().collection('plan').insert(plan).then(res => {
              console.log(res)
              // 是否从计划年视图缺省页触发
              if (this.isdefaultenter) {
                // 更新计划年视图
                this.$parent.refreshData(res._id.$oid)
              }
              this.$emit('update:visible', 'IS_NONE')
            }).catch(err => {
              console.log(err)
            })
            break
          case 'IS_EDIT':
            let filter = { _id: { $oid: this.planid } }
            let setColumon = {}
            let unsetColumon = {}
            setColumon.name = this.planName ? this.planName.trim() : ''
            setColumon.tenant = { $oid: this.tenantId }
            if (this.categoryId) {
              setColumon.category = { $oid: this.categoryId }
            } else {
              unsetColumon.category = ''
            }
            setColumon.creator = { $oid: this.user.supportid }
            // 把当前计划创建人(当前登录人)一起插入计划参与人列表中
            participators.push({ $oid: this.user.supportid })
            this.selectedStaffIdList.map((id) => {
              participators.push({ $oid: id })
            })
            setColumon.participators = participators
            let update = Object.keys(unsetColumon).length === 0 ? { $set: setColumon } : { $set: setColumon, $unset: unsetColumon }
            mongo.db().collection('plan').update({ filter: filter, update: update }).then(res => {
              console.log(res)
              this.$emit('update:visible', 'IS_NONE')
            }).catch(err => {
              console.log(err)
            })
            break
          case 'IS_COPY':
            plan.tenant = { $oid: this.tenantId }
            if (this.categoryId) {
              plan.category = { $oid: this.categoryId }
            }
            plan.name = this.planName ? this.planName.trim() : ''
            plan.creator = { $oid: this.user.supportid }
            // 把当前计划创建人(当前登录人)一起插入计划参与人列表中
            participators.push({ $oid: this.user.supportid })
            this.selectedStaffIdList.map((id) => {
              participators.push({ $oid: id })
            })
            plan.participators = participators
            mongo.db().collection('plan').insert(plan).then(res => {
              console.log(res)
              if (res._id.$oid) {
                let addPlanId = res._id
                let createdOn = res.createdOn
                // 查询当前复制的计划的计划内容
                let filter = { plan: { $oid: this.planid }, _removed: { $exists: false } }
                console.log(filter)
                mongo.db().collection('plan.content').find({ filter: filter }).then(data => {
                  let planContents = data.items
                  // 如果当前计划有计划内容，则计划内容也需要复制
                  if (planContents.length > 0) {
                    let tmpPlanContents = []
                    for (let planContent of planContents) {
                      let tmpPlanContent = {}
                      tmpPlanContent.plan = addPlanId
                      tmpPlanContent.content = planContent.content
                      tmpPlanContent.remark = planContent.remark
                      tmpPlanContent.stage = planContent.stage
                      tmpPlanContent.color = planContent.color
                      tmpPlanContent.startOn = planContent.startOn
                      tmpPlanContent.endOn = planContent.endOn
                      tmpPlanContent.createdOn = createdOn
                      tmpPlanContent.updatedOn = createdOn
                      tmpPlanContents.push(tmpPlanContent)
                      // delete planContent._id
                    }
                    mongo.db().collection('plan.content').insert(tmpPlanContents).then(r => {
                      console.log(r)
                    }).catch(err => {
                      console.log(err)
                    })
                  }
                }).catch(err => {
                  console.log(err)
                })
              } else {
                console.log(res)
              }
              this.$emit('update:visible', 'IS_NONE')
            }).catch(err => {
              console.log(err)
            })
            break
          default:
            this.$emit('update:visible', 'IS_NONE')
            break
        }
      }
    },
    cancel () {
      this.$emit('update:visible', 'IS_NONE')
    },
    getUserInfoExcludeLoginUser () {
      this.userListExcludeLonginUser = this.userlist.filter((item) => {
        return item._id.$oid !== this.user.supportid && !item._removed
      })
    },
    // 获取排除已删除的品类列表
    getCategoryListExcludeRemoved () {
      this.categorylistExcludeRemoved = this.categorylist.filter((item) => {
        return !item._removed
      })
    },
    getCategoryById (id) {
      if (!id) return
      let category = this.categorylistExcludeRemoved.find((item) => {
        return item._id.$oid === id
      })
      return category ? category._id.$oid : null
    },
    getStaffInfoById (staffId) {
      if (!staffId) return
      let staff = this._.find(this.userlist, (staff) => {
        return staff.id === staffId
      })
      return staff ? Object.assign({}, { id: staff.id, name: staff.name, avatar: staff.avatar }, staff._removed ? { _removed: staff._removed } : null) : null
    },
    async queryPlanInfoById () {
      if (!this.planid) return
      let filter = {}
      filter._id = { $oid: this.planid }
      filter._removed = { '$exists': false }
      let { items } = await mongo.db().collection('plan').find({ filter: filter })
      return items
    },
    updateUserInfo () {
      let tmpUser = this._.find(this.userlist, (item) => {
        return item._id.$oid === this.user.supportid
      })
      return Object.assign(tmpUser, { supportid: tmpUser._id.$oid, loginorgid: this.tenantId })
    },
    showSelectStaffDialog () {
      this.selectStaffDialog = true
    },
    getSelectedStaff (index) {
      this.curStaffId = this.selectedStaffList[index].id
    },
    removeStaff (index) {
      this.selectedStaffIdList.splice(index, 1)
    },
    disposeModalDialog (v) {
      switch (v) {
        case 'IS_NONE':
          this.refreshFn()
          break
        case 'IS_ADD':
          this.addOrEditPlanTitle = '新建计划'
          this.user = this.updateUserInfo()
          // this.$nextTick(() => {
          this.categoryId = null
          this.planName = null
          this.selectedStaffIdList = []
          this.selectedStaffList = []
          // })
          break
        case 'IS_EDIT':
          this.addOrEditPlanTitle = '编辑计划'
          this.user = this.updateUserInfo()
          // 根据当前计划ID查询计划相关信息
          this.queryPlanInfoById().then(planList => {
            let plan = planList[0]
            this.planName = plan.name
            this.selectedStaffIdList = []
            this.selectedStaffList = []
            // 排除参与人中为当前计划创建人(当前登录用户)的记录
            let tmpParticipators = plan.participators.filter((item) => {
              return item.$oid !== plan.creator.$oid
            })
            tmpParticipators.map((item) => {
              let staffId = item.$oid
              let staffInfo = this.getStaffInfoById(staffId)
              if (staffInfo && !staffInfo._removed) {
                this.selectedStaffIdList.push(staffId)
                this.selectedStaffList.push(staffInfo)
              }
            })
            this.categoryId = plan.category ? this.getCategoryById(plan.category.$oid) : null
          })
          break
        case 'IS_COPY':
          this.addOrEditPlanTitle = '复制计划'
          this.user = this.updateUserInfo()
          // 根据被复制计划ID查询计划相关信息
          this.queryPlanInfoById().then(planList => {
            let plan = planList[0]
            this.planName = plan.name
            this.selectedStaffIdList = []
            this.selectedStaffList = []
            // 排除参与人中为当前计划创建人(当前登录用户)的记录
            let tmpParticipators = plan.participators.filter((item) => {
              return item.$oid !== plan.creator.$oid
            })
            tmpParticipators.map((item) => {
              let staffId = item.$oid
              let staffInfo = this.getStaffInfoById(staffId)
              if (staffInfo && !staffInfo._removed) {
                this.selectedStaffIdList.push(staffId)
                this.selectedStaffList.push(staffInfo)
              }
            })
            this.categoryId = plan.category ? this.getCategoryById(plan.category.$oid) : null
          })
          break
        default:
          this.addOrEditPlanTitle = ''
          break
      }
      this.addOrEditPlanDialog = this.addOrEdit[v]
    }
  }
}
</script>
<style scoped>
.zengjia {
  cursor: pointer;
}
.staff {
  cursor: pointer;
  width: 130px;
}
.remove {
  position: absolute;
  right: 0;
  top: -5px;
}
</style>

