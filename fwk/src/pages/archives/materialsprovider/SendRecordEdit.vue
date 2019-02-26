<!--
  single-line: 表单提示文字 不会上浮
  class = "input-group--focused" 表单下面实现变蓝色 选中

-->

<template>
  <v-card>
    <v-card-text>
      <v-container fluid>
        <!-- 记录编号 -->
        <v-layout row>
          <v-flex xs2><v-subheader>记录编号</v-subheader></v-flex>
          <v-flex xs5>
            <v-text-field
              name="input-1-3"
              label="ABCD001"
              single-line
              disabled
            >
            </v-text-field>
          </v-flex>
        </v-layout>

        <!-- 农资名称 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;农资名称</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-select
              label="请选择农资名称"
              v-model="select"
              :items="items"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 农资类别 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;农资类别</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-select
              label="请选择农资类别"
              v-model="select2"
              :items="items2"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 规格 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;规格</v-subheader>
          </v-flex>
          <v-flex xs3>
            <v-text-field
              name="input-3-4"
              label="请输入规格"
              value=""
              single-line
            >
            </v-text-field>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select3"
              :items="items3"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 数量 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;数量</v-subheader>
          </v-flex>
          <v-flex xs3>
            <v-text-field
              name="input-3-4"
              label="请输入规格"
              value=""
              single-line
            >
            </v-text-field>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select4"
              :items="items4"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 领取人 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;领取人</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-text-field
              name="input-3-5"
              label="请选择领取人"
              value="huchuan"
              single-line
            ></v-text-field>
          </v-flex>
        </v-layout>

        <!-- 领取时间 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>*&nbsp;领取时间</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-menu
              lazy
              :close-on-content-click="false"
              v-model="menu"
              transition="scale-transition"
              offset-y
              full-width
              :nudge-right="40"
              max-width="290px"
              min-width="290px"
            >
              <v-text-field
                slot="activator"
                label="领取时间"
                v-model="date"
                prepend-icon="event"
                readonly
              ></v-text-field>
              <v-date-picker v-model="date" no-title scrollable actions>
                <template slot-scope="{ save, cancel }">
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn flat color="primary" @click="cancel">Cancel</v-btn>
                    <v-btn flat color="primary" @click="save">OK</v-btn>
                  </v-card-actions>
                </template>
              </v-date-picker>
            </v-menu>
          </v-flex>
        </v-layout>

        <!-- 领取地点 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>领取地点</v-subheader>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select5"
              :items="items5"
              single-line
            >
            </v-select>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select5"
              :items="items5"
              single-line
            >
            </v-select>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select5"
              :items="items5"
              single-line
            >
            </v-select>
          </v-flex>
          <v-flex xs2>
            <v-select
              label=""
              v-model="select5"
              :items="items5"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 详细地址 -->
        <v-layout row>

          <v-flex xs5 offset-xs2>
            <v-text-field
              name="input-3-5"
              label="详细地址"
              single-line
            ></v-text-field>
          </v-flex>
        </v-layout>

        <!-- 其他说明 -->
        <v-layout row>
          <v-flex xs2>
            <v-subheader>其他说明</v-subheader>
          </v-flex>
          <v-flex xs5>
            <v-text-field
              name="input-3-5"
              label="请输入其他需要说明的信息"
              single-line
            ></v-text-field>
          </v-flex>
        </v-layout>

        <!-- 取消 保存 -->
        <v-layout row justify-center>
          <v-flex xs2><v-btn>取消</v-btn></v-flex>
          <v-flex xs5><v-btn>保存</v-btn></v-flex>
        </v-layout>



      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
  export default {
    data () {
      return {
        rules: [
          () => 'Username or Password is incorrect'
        ],
        select: '1',
        items: ['1', '2', '3'],
        select2: '2',
        items2: ['1', '2', '3'],
        select3: '3',
        items3: ['1', '2', '3'],
        select4: '1',
        items4: ['1', '2', '3'],
        select5: '1',
        items5: ['1', '2', '3'],
        select6: '1',
        items6: ['1', '2', '3'],
        select7: '1',
        items7: ['1', '2', '3'],
        select8: '1',
        items8: ['1', '2', '3'],
        /* 日期选择 */
        date: null,
        menu: false,
        modal: false
      }
    }
  }
</script>
