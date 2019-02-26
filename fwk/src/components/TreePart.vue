<!-- 参与人 tree -->
<template>
  <v-layout row class="treeRow">
    <v-flex xs12 text-xs-center>

      <!-- 输入框 -->
      <v-text-field :label="label"
                    ref="selectUserText"
                    v-model="selectUserText"
                    class="treeInput"
                    @focus="openTree"
                    @blur="blurTree"
                    :rules="isRules ? rulesTree : []"
                    readonly
                    single-line
      />
      <v-card tabindex="0"
              class="treeCard"
              hidefocus="true"
              v-show="isDisplay"
              @focus="focusTree"
              @blur="blurTree"
              @mouseenter="inTree"
              @mouseleave="leaveTree"
      >
        <!-- tree -->
        <v-jstree :data="treeData"
                  @item-click="itemClick"
                  class="treeWrapper"
                  show-checkbox
                  multiple
                  allow-batch
                  whole-row
                  draggable
        />

        <!-- 数据获取中... -->
        <v-layout row v-if="isLack" class="loadWrapper">
          <v-flex xs12 text-xs-center class="TreeLack">
            <div>
              <v-progress-circular
                class="progressWrapper"
                size="23"
                indeterminate
                color="primary"
              />
              <span class="TreeLack-text">&nbsp;&nbsp;数据获取中...</span>
            </div>
          </v-flex>
        </v-layout>

        <!-- 暂无数据 -->
        <v-layout row class="loadWrapper" v-if="treeData.length === 0 && !isLack">
          <v-flex xs12 text-xs-center class="TreeLack">
            <div>
             <span>暂无数据</span>
            </div>
          </v-flex>
        </v-layout>

      </v-card>
    </v-flex>
  </v-layout>
</template>
<script>
  import { excuteApis } from '@/apis'
  export default {
    data () {
      return {
        refreshCount: 0, // 刷新次数
        count: 0, // 计数器
        // rulesTree: [() => this.selectUser.length > 0 || this.label], // tree验证规则
        rulesTree: this.isValDate ? [() => this.selectUser.length > 0 || this.label] : [],
        isLack: true,
        selectUser: [], // 已经选择的用户
        selectUserText: [], // 已经选择的用户 text
        treeData: [], // tree数据
        isDisplay: false, // tree 是否显示
        isEditFlag: true
      }
    },
    computed: {
      isEdit () {
        return !!this.$route.query.id // ???
      }
    },
    props: {
      label: {
        type: String,
        default: '请选择参与人'
      },
      isValDate: {
        type: Boolean,
        default: false
      },
      editUser: {
        type: Array
      },
      isRules: {
        type: Boolean,
        default: true
      },
      /**
       * 弹窗bug解决所用
       * 是否是弹窗
       */
      isDialog: {
        type: Boolean,
        default: false
      },

      /**
       * 选中用户时是否显示手机号码 张三(138888)
       */
      showPhone: {
        type: Boolean,
        default: false
      }
    },
    mounted () {
      // if (!this.isEdit)
      this.getTreeData(this.editUser) // 如果是编辑 则不在此处获取数据,而是等editUser 变化以后获取
    },
    methods: {

      /**
       * openTree
       */
      openTree () {
        this.isDisplay = true
      },

      focusTree () {
        this.rulesTree = [] // 清空验证规则
      },

      /**
       * blurTree
       */
      blurTree () {
        if (this.isCloseTree) {
          if (this.isValDate) this.rulesTree = [() => this.selectUser.length > 0 || this.label]
          else this.rulesTree = []
          this.isDisplay = false
        }
      },

      /**
       * 鼠标进入tree区域 设置 关闭tree 标识
       */
      inTree () {
        this.isCloseTree = false // 是否能关闭tree
      },

      /**
       * 鼠标离开tree区域 设置 关闭tree 标识
       */
      leaveTree () {
        this.isCloseTree = true
      },

      /**
       * 点击node绑定输入框数据
       */
      itemClick (node) {
        let treeNode = node.model
        console.log(treeNode)
        if (!treeNode.children || treeNode.children.length === 0) {
          // if (parseInt(treeNode.type) !== 2) return // type === 2才能选
          if (treeNode.selected) {
            if (!this.findNode(treeNode, this.selectUser)) {
              this.selectUser.push(treeNode)
              this.showPhone ? this.selectUserText.push(treeNode.text + '(' + treeNode.phone + ')') : this.selectUserText.push(treeNode.text)
            }
          } else {
            let index = this.findId(treeNode, this.selectUser)
            if (index !== -1) {
              this.selectUser.splice(index, 1)
              this.selectUserText.splice(index, 1)
            }
          }
        } else {
          let iteratorTree = function (iteratorNode, self) {
            console.log(iteratorNode)
            console.log(self)
            for (let node of iteratorNode) {
              if (node.children && node.children.length > 0) {
                iteratorTree(node.children, self)
              } else {
                // 最后一级
                if (node.disabled) continue // 跳过禁用node
                if (node.selected) {
                  if (self) {
                    if (!self.findNode(node, self.selectUser)) { // 遍历器里面用不了外面的方法
                      self.selectUser.push(node)
                      self.showPhone ? self.selectUserText.push(node.text + '(' + node.phone + ')') : self.selectUserText.push(node.text)
                    }
                  }
                } else {
                  if (self) {
                    let index = self.findId(node, self.selectUser)
                    if (index !== -1) {
                      self.selectUser.splice(index, 1)
                      self.selectUserText.splice(index, 1)
                    }
                  }
                }
              }
            }
          }
          iteratorTree(treeNode.children, this)
        }
      },

      /**
       * findNode
       * @param node
       * @param allUser
       * @return booelan 用户里是否存在该节点
       */
      findNode (node, allUser) {
        if (!node) return
        for (let user of allUser) {
          if (user.id === node.id) return true
        }
        return false
      },

      /**
       * findId
       * @param node
       * @param allUser
       * @return 该node 在allUser数组中所对应的数组下标
       */
      findId (node, allUser) {
        if (!node) return -1
        for (let i = 0; i < allUser.length; ++i) {
          if (allUser[i].id === node.id) return i
        }
        return -1
      },

      /**
       * 获取Tree数据
       * @param editUser 编辑中传进来的已经选中的用户
       */
      getTreeData (editUser) {
        let param = {
          showtype: 'user'
        }
        // excuteApis(null, global.constant.userApisUrls, 'user', 'queryUserAndDepartByLoginUserOrg').then(response => {
        excuteApis(param, global.constant.userApisUrls, 'pub', 'OrgDepartUserTree').then(response => {
          this.refreshCount = 0 // 刷新次数置0
          if (response.data && response.data.flag === 1 && response.data.data) {
            this.treeData = this.disposeTreeData(response.data.data, editUser)
            if (editUser && editUser.length > 0) this.setSelectUser(editUser) // 编辑中 数据上赋值
          } else {
            this.treeData = []
          }
        }).catch(() => {
        }).finally(() => {
          this.isLack = false
        })
      },

      /**
       * 处理返回的tree数据
       * @param array
       * @param editUser 编辑中勾选的用户
       * @return array
       */
      disposeTreeData (array, editUser) {
        if (!array || array.length === 0) return
        for (let i = 0; i < array.length; ++i) {
          array[i].text = array[i].name || array[i].phone // name
          array[i].value = array[i].id // id
          array[i].icon = 'fa' // 隐藏节点图标
          array[i].opened = false // 节点是否展开
          array[i].selected = false // 节点是否选中
          array[i].disabled = false // 节点是否禁用
          array[i].loading = false // loadiing
          if (array[i].children && array[i].children.length > 0) {
            for (let j = 0; j < array[i].children.length; ++j) {
              if (editUser && editUser.length > 0) {
                for (let user of editUser) {
                  if (user.userid === array[i].children[j].id) {
                    array[i].selected = true
                  }
                }
              }
            }
            this.disposeTreeData(array[i].children, editUser)
          } else {
            // 节点id为空时禁用该节点
            if (!array[i].id) {
              array[i].disabled = true
              array[i].text = array[i].name + ' ( 非法用户 )'
            }
            // 编辑中 勾选用户
            if (editUser && editUser.length > 0) {
              // 样式上勾选
              for (let user of editUser) {
                if (user.userid === array[i].id) {
                  array[i].selected = true
                }
              }
            }
          }
        }
        return array
      },

      /**
       * 清除tree (勾选状态清除, 数据清除)
       */
      clearSelectUser () {
        this.selectUser = []
        this.selectUserText = []
        let treeData = this.treeData
        if (!treeData || treeData.length === 0) return
        for (let i = 0; i < treeData.length; ++i) {
          treeData[i].opened = false // 节点是否展开
          treeData[i].selected = false // 节点是否选中
          treeData[i].loading = false // loadiing
          if (treeData[i].children && treeData[i].children.length > 0) {
            this.disposeTreeData(treeData[i].children)
          } else {
          }
        }
      },

      /**
       * 编辑中 设置selectUser值
       * @param editUser array
       * @retrun array
       */
      setSelectUser (editUser) {
        this.selectUser = editUser // value
        for (let i = 0; i < editUser.length; ++i) {
          let username = editUser[i].username || editUser[i].name
          if (this.showPhone) username = username || '--' + '(' + editUser[i].phone + ')'
          this.selectUserText.push(username)
          this.selectUser[i].id = editUser[i].userid
        }
      },

      /**
       * 手动验证表单
       * @return true(tree有错) false(tree没错)
       */
      valdateForm () {
        this.rulesTree = [() => this.selectUser.length > 0 || this.label] // tree验证规则设置
        this.$refs.selectUserText.validate(true)
        if (this.selectUserText.length === 0) return true
        else return false
      },

      /**
       * 清除tree校验规则
       */
      clearValdate () {
        this.rulesTree = [] // tree验证规则设置
      },

      /**
       * count次数置0
       */
      resetCount () {
        this.count = 0
      }
    },
    watch: {
      /**
       * 选择的用户改变 向外触发事件
       */
      selectUser: {
        handler: function (val) {
          this.$emit('clickNode', val)
        },
        deep: true
      }
      /**
       * 编辑中 参与人返回获取tree数据
       */
      // editUser (editUser) {
      //   console.log('触发editUser变化-------')
      //   this.getTreeData(editUser)
      //   this.isEditFlag = false
      //   console.log('触发editUser变化*******')
      // }
    }
  }
</script>
<style scoped>
  .treeInput{
    margin-bottom: -23px;
  }
  .treeWrapper{
    padding: 20px;
  }
  .TreeLack{
    padding-top: 20px;
    padding-bottom: 30px;
  }
  .TreeLack>div{
    position: relative;
  }
  .TreeLack-text{
    margin-left: 30px;
  }
  .progressWrapper{
    position: absolute;
    top: -2px;
  }
  .loadWrapper{
    margin-top: -35px;
  }
  .treeCard{
    outline: 0;
  }
  .treeRow{
    padding-bottom: 30px;
  }
</style>
