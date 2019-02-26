<!--
    headers: 表头
    sortable: false/true 控制表头上面的排序功能
    align: 控制表头对齐方式
    hide-actions: 隐藏表操作
    select-all: 表头是否添加选择所有框
    item-key: 用于标识选定项的值

-->

<template>
  <v-container>

    <!-- 顶部工具条 -->
    <v-layout row wrap>
      <v-flex xs12>

        <!-- 第1条 -->
        <v-layout row wrap>

          <v-btn class="btnMargin" :to="{name: 'sendrecord'}">添加农事记录</v-btn>

          <v-flex xs2>
            <v-select
              label="农资类别"
              v-model="type"
              :items="typeListName"
              single-line
            >
            </v-select>
          </v-flex>

          <v-flex xs3>
            <v-text-field
              label="查询"
              v-model="modelItems4"
              prepend-icon="fa-search"
              single-line
            >
            </v-text-field>
          </v-flex>

        </v-layout >

      </v-flex>
    </v-layout>

    <!-- 表格 -->
    <v-layout row wrap>
      <v-flex xs12>
        <v-data-table
          v-model="selected"
          v-bind:headers="headers"
          v-bind:items="items"
          select-all
          v-bind:pagination.sync="pagination"
          item-key="name"
          hide-actions
          class="elevation-1"
        >
          <template slot="headers" slot-scope="props">
            <tr>
              <th>
                <v-checkbox
                  primary
                  hide-details
                  @click.native="toggleAll"
                  :input-value="props.all"
                  :indeterminate="props.indeterminate"
                ></v-checkbox>
              </th>
              <th v-for="header in props.headers" :key="header.text"
                  :class="['column sortable', pagination.descending ? 'desc' : 'asc', header.value === pagination.sortBy ? 'active' : '']"
                  @click="changeSort(header.value)"
              >
                <v-icon>arrow_upward</v-icon>
                {{ header.text }}
              </th>
            </tr>
          </template>
          <template slot="items" slot-scope="props">
            <tr :active="props.selected" @click="props.selected = !props.selected">
              <td>
                <v-checkbox
                  primary
                  hide-details
                  :input-value="props.selected"
                ></v-checkbox>
              </td>
              <td class="text-xs-center">{{ props.item.name }}</td>
              <td class="text-xs-center">{{ props.item.calories }}</td>
              <td class="text-xs-center">{{ props.item.fat }}</td>
              <td class="text-xs-center">{{ props.item.carbs }}</td>
              <td class="text-xs-center">{{ props.item.protein }}</td>
              <td class="text-xs-center">{{ props.item.sodium }}</td>
              <td class="text-xs-center">{{ props.item.calcium }}</td>
              <td class="text-xs-center">
                <span class="query">查看</span>
                <span class="edit">编辑</span>
                <span class="delete">删除</span>
              </td>
            </tr>
          </template>
        </v-data-table>

      </v-flex>

      <v-flex xs12 text-xs-center>
        <v-pagination :length="8"></v-pagination>
      </v-flex>
    </v-layout>

  </v-container>
</template>
<script>
  import { querySystemDict } from '@/apis/systems/dictionary'
  export default {
    name: 'fatmedicinesprovide',
    data () {
      return {
        type: null, // 农资类别绑定的数据
        typeList: [], // 农资类别列表
        typeListName: [], // 农资类别列表(只包含名字)
        createdatestart: null, // 开始时间
        createdateend: null, // 截止时间
        modelItems4: null,
        date2: null,
        date: null,
        search: '',
        pagination: {},
        selected: [],
        headers: [
          {
            text: '记录编号',
            align: 'left',
            sortable: false,
            value: 'id'
          },
          {
            text: '农资名称',
            align: 'left',
            sortable: false,
            value: 'id2'
          },
          {
            text: '农资类别',
            align: 'left',
            sortable: false,
            value: 'id2'
          },
          {
            text: '规格',
            align: 'left',
            sortable: false,
            value: 'id3'
          },
          {
            text: '数量',
            align: 'left',
            sortable: false,
            value: 'id4'
          },
          {
            text: '领取人',
            align: 'left',
            sortable: false,
            value: 'id5'
          },
          {
            text: '领取时间',
            align: 'left',
            sortable: false,
            value: 'id6'
          },
          {
            text: '操作',
            align: 'left',
            sortable: false,
            value: 'id7'
          }
        ],
        items: [
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 11159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87,
            calcium: '14%',
            calcium2: '14%'
          },
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 11159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87,
            calcium: '14%',
            calcium2: '14%'
          }
        ]
      }
    },
    computed: {
      pages () {
        return 20
      }
    },
    mounted () {
      console.log('vue mounted')
      this.getData()
    },
    methods: {

      /* 新增记录 */
      addRecord () {
        const router = this.$router
        router.push({name: 'sendrecord'}) // 跳转

        /* let test = {
          name: 'huchuan',
          age: 18,
          sex: '男'
        }
        sessoinStorage.setItem() */
      },

      /* 导入记录 */
      addRecordFromOut () {
        const router = this.$router
        router.push({name: 'sendrecordedit'})
      },

      toggleAll () {
        if (this.selected.length) this.selected = []
        else this.selected = this.items.slice()
      },

      changeSort (column) {
        if (this.pagination.sortBy === column) {
          this.pagination.descending = !this.pagination.descending
        } else {
          this.pagination.sortBy = column
          this.pagination.descending = false
        }
      },

      /* 获取后台数据 */
      getData  () {
        /* 获取农资类别 */
        querySystemDict('agriculturaltype')
          .then(data => {
            if (data.data && data.data.data && data.data.flag === 1) {
              console.log('=============================')
              console.log(data)
              let serviceData = data.data.data // 返回的农资类别对象数组
              let typeListName = [] // 存储农资类别名称
              for (let currentData of serviceData) {
                typeListName.push(currentData.name)
                this.typeList = data.data.data
                this.typeListName = typeListName
              }
            }
          })
          .catch(result => {
            console.log(result)
            console.log('获取数据失败')
          })
      }
    }
  }
</script>
<style scoped>
  .container{
    padding-top: 0;
  }
  .btnMargin{
    margin-top: 13px;
  }
  .query, .edit, .delete{
    cursor: pointer;
    margin-right: 5px;
  }
</style>
