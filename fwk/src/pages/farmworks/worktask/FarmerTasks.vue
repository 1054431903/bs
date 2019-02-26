<template>
  <v-container fluid
               pa-0
               pb-4>
    <v-card class="page-content">
      <v-tabs v-model="active"
              left
              icons-and-text
              hide-slider>
        <v-tabs-slider color="primary"></v-tabs-slider>

        <v-tab href="#execute"
               v-if="active === 'execute'"
               class="activeTab">
          <span style="margin-top: 10px;">我执行的任务</span>
          <img :src="`/static/task/execute_selected.png`" />
        </v-tab>
        <v-tab href="#execute"
               v-else>
          <span style="margin-top: 10px;">我执行的任务</span>
          <img :src="`/static/task/execute.png`" />
        </v-tab>

        <v-tab href="#create"
               v-if="active === 'create'"
               class="activeTab">
          <span style="margin-top: 10px;">我创建的任务</span>
          <img :src="`/static/task/create_selected.png`" />
        </v-tab>
        <v-tab href="#create"
               v-else>
          <span style="margin-top: 10px;">我创建的任务</span>
          <img :src="`/static/task/create.png`" />
        </v-tab>

        <v-tab href="#approval"
               v-if="active === 'approval'"
               class="activeTab">
          <span style="margin-top: 10px;">我审批的任务</span>
          <img :src="`/static/task/approval_selected.png`" />
        </v-tab>
        <v-tab href="#approval"
               v-else>
          <span style="margin-top: 10px;">我审批的任务</span>
          <img :src="`/static/task/approval.png`" />
        </v-tab>

        <v-tab href="#examination"
               v-if="active === 'examination'"
               class="activeTab">
          <span style="margin-top: 10px;">我考核的任务</span>
          <img :src="`/static/task/examination_selected.png`" />
        </v-tab>
        <v-tab href="#examination"
               v-else>
          <span style="margin-top: 10px;">我考核的任务</span>
          <img :src="`/static/task/examination.png`" />
        </v-tab>

        <v-tab-item v-for="tab in tabs"
                    :value="tab"
                    :key="tab">

          <v-card flat
                  v-if="active === 'execute'">
            <!-- 工具条 -->
            <v-layout row
                      wrap>
              <v-flex xs2
                      class="tool-wrapper">
                <v-autocomplete label="请选择任务状态"
                                v-model="status"
                                :items="statusList"
                                item-text="name"
                                item-value="code"
                                clearable
                                single-line>
                </v-autocomplete>
              </v-flex>

              <v-flex xs4
                      class="tool-wrapper">
                <v-text-field label="请输入任务内容进行搜索"
                              v-model="keywords"
                              clearable
                              append-icon="search"
                              @keyup.enter="queryTaskByCondition"
                              single-line>
                </v-text-field>
              </v-flex>

              <v-flex xs1
                      class="btn-serach">
                <v-btn color="primary"
                       @click="queryTaskByCondition">
                  查询
                </v-btn>
              </v-flex>

              <!-- 添加任务按钮 -->
              <v-flex xs5
                      class="text-xs-right tool-wrapper"
                      v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksAdd')">
                <v-btn class="btnMargin"
                       small
                       fab
                       dark
                       color="red darken-2"
                       title="新增农事任务"
                       @click="createTask">
                  <v-icon>add</v-icon>
                </v-btn>
              </v-flex>

            </v-layout>

            <!-- 记录数 -->
            <div class="taskNumber">共有任务&nbsp;:&nbsp;
              <span v-text="sumItems"></span>&nbsp;条
            </div>

            <!-- 我执行的任务数据列表 -->
            <v-data-table :headers="headerExecute"
                          :items="dataItems"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          must-sort
                          disable-initial-sort
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <tr style="cursor: pointer"
                    @click="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail') ? showTaskDetail(props.item.id, props.item.status, 'selexecution') : ''">
                  <td class="text-xs-left hideoverflow"
                      style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                      :title="props.item.content">
                    {{ props.item.content }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.startTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.endTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div v-bind:class="{overdue: props.item.status === '已逾期', executing: props.item.status === '进行中', 
                  begin: props.item.status === '即将开始', completed: props.item.status === '已完成', examined: props.item.status === '已考核', 
                  stopped: props.item.status === '已中止' , unknown: props.item.status === '未知状态'}">{{ props.item.status }}</div>
                    </template>
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.creator }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div class="hideoverflow"
                           :title="props.item.participators">
                        <span> {{ props.item.representative}} </span>
                        <span style="color:#32c787">(负责人)</span>
                        <span v-if="props.item.executor">,{{ props.item.executor }} </span>
                      </div>
                    </template>
                  </td>
                  <td class="text-xs-center operateMinWidth">
                    <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"
                           fab
                           small
                           icon
                           @click.stop="showTaskDetail(props.item.id, props.item.status, 'selexecution')"
                           title="查看">
                      <v-icon>remove_red_eye</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="saveToRecord(props.item.id)"
                           :disabled="!(props.item.representativeId === supportUserId && !props.item.recordCreated && props.item.finish === true && props.item.stage === 'completion')"
                           title="同步到农事记录">
                      <v-icon>save</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </template>
            </v-data-table>
            <!-- 分页 -->
            <v-flex class="paginationWrapper"
                    text-xs-center
                    v-show="showPagination">
              <v-custompagination :pagination.sync="paginationExecute"></v-custompagination>
            </v-flex>
          </v-card>
          <v-card flat
                  v-if="active === 'create'">
            <!-- 工具条 -->
            <v-layout row
                      wrap>
              <v-flex xs2
                      class="tool-wrapper">
                <v-select label="请选择任务状态"
                          v-model="status"
                          :items="statusList"
                          item-text="name"
                          item-value="code"
                          clearable
                          single-line>
                </v-select>
              </v-flex>

              <v-flex xs4
                      class="tool-wrapper">
                <v-text-field label="请输入任务内容进行搜索"
                              v-model="keywords"
                              clearable
                              append-icon="search"
                              @keyup.enter="queryTaskByCondition"
                              single-line>
                </v-text-field>
              </v-flex>

              <v-flex xs1
                      class="btn-serach">
                <v-btn color="primary"
                       @click="queryTaskByCondition">
                  查询
                </v-btn>
              </v-flex>

              <!-- 添加任务按钮 -->
              <v-flex xs5
                      class="text-xs-right tool-wrapper"
                      v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksAdd')">
                <v-btn class="btnMargin"
                       small
                       fab
                       dark
                       color="red darken-2"
                       title="新增农事任务"
                       @click="createTask">
                  <v-icon>add</v-icon>
                </v-btn>
              </v-flex>

            </v-layout>

            <!-- 记录数 -->
            <div class="taskNumber">共有任务&nbsp;:&nbsp;
              <span v-text="sumItems"></span>&nbsp;条
            </div>

            <!-- 我创建的任务数据列表 -->
            <v-data-table :headers="headerCreate"
                          :items="dataItems"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          disable-initial-sort
                          must-sort
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <tr style="cursor: pointer"
                    @click="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail') ? showTaskDetail(props.item.id, props.item.status, 'selcreate') : ''">
                  <td class="text-xs-left hideoverflow"
                      style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                      :title="props.item.content">
                    {{ props.item.content }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.startTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.endTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div v-bind:class="{overdue: props.item.status === '已逾期', executing: props.item.status === '进行中', 
                  begin: props.item.status === '即将开始', completed: props.item.status === '已完成', examined: props.item.status === '已考核', 
                  stopped: props.item.status === '已中止', tobeapproved:  props.item.status === '待审批', rejected:  props.item.status === '审批不通过', 
                  unknown: props.item.status === '未知状态'}">{{ props.item.status }}</div>
                    </template>
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div class="hideoverflow"
                           :title="props.item.participators">
                        <span> {{ props.item.representative}} </span>
                        <span style="color:#32c787">(负责人)</span>
                        <span v-if="props.item.executor">,{{ props.item.executor }} </span>
                      </div>
                    </template>
                  </td>

                  <td class="text-xs-center operateMinWidth">
                    <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"
                           fab
                           small
                           icon
                           @click.stop="showTaskDetail(props.item.id, props.item.status, 'selcreate')"
                           title="查看">
                      <v-icon>remove_red_eye</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="copyTask(props.item)"
                           v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksCopy')"
                           title="复制任务">
                      <v-icon>library_books</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="stopTask(props.item)"
                           :disabled="props.item.finish"
                           title="中止任务">
                      <v-icon>not_interested</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </template>
            </v-data-table>
            <!-- 分页 -->
            <v-flex class="paginationWrapper"
                    text-xs-center
                    v-show="showPagination">
              <v-custompagination :pagination.sync="paginationCreate"></v-custompagination>
            </v-flex>
          </v-card>
          <v-card flat
                  v-if="active === 'approval'">
            <!-- 工具条 -->
            <v-layout row
                      wrap>
              <v-flex xs2
                      class="tool-wrapper">
                <v-autocomplete label="请选择任务状态"
                                v-model="status"
                                :items="statusList"
                                item-text="name"
                                item-value="code"
                                clearable
                                single-line>
                </v-autocomplete>
              </v-flex>

              <v-flex xs4
                      class="tool-wrapper">
                <v-text-field label="请输入任务内容进行搜索"
                              v-model="keywords"
                              clearable
                              append-icon="search"
                              @keyup.enter="queryTaskByCondition"
                              single-line>
                </v-text-field>
              </v-flex>

              <v-flex xs1
                      class="btn-serach">
                <v-btn color="primary"
                       @click="queryTaskByCondition">
                  查询
                </v-btn>
              </v-flex>

              <!-- 添加任务按钮 -->
              <v-flex xs5
                      class="text-xs-right tool-wrapper"
                      v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksAdd')">
                <v-btn class="btnMargin"
                       small
                       fab
                       dark
                       color="red darken-2"
                       title="新增农事任务"
                       @click="createTask">
                  <v-icon>add</v-icon>
                </v-btn>
              </v-flex>

            </v-layout>

            <!-- 记录数 -->
            <div class="taskNumber">共有任务&nbsp;:&nbsp;
              <span v-text="sumItems"></span>&nbsp;条
            </div>
            <!-- 我审批的任务数据列表 -->
            <v-data-table :headers="headerApproval"
                          :items="dataItems"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          disable-initial-sort
                          must-sort
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <tr style="cursor: pointer"
                    @click="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail') ? showTaskDetail(props.item.id, props.item.status, 'selapproval') : ''">
                  <td class="text-xs-left hideoverflow"
                      style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                      :title="props.item.content">
                    {{ props.item.content }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.startTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.endTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.creator }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div class="hideoverflow"
                           :title="props.item.participators">
                        <span> {{ props.item.representative}} </span>
                        <span style="color:#32c787">(负责人)</span>
                        <span v-if="props.item.executor">,{{ props.item.executor }} </span>
                      </div>
                    </template>
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div v-bind:class="{tobeapproved:  props.item.status === '待审批', approved: props.item.status === '审批通过', 
                  rejected:  props.item.status === '审批不通过', unknown: props.item.status === '未知状态'}">{{ props.item.status }}</div>
                    </template>
                  </td>
                  <td class="text-xs-center operateMinWidth">
                    <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"
                           fab
                           small
                           icon
                           @click.stop="showTaskDetail(props.item.id, props.item.status, 'selapproval')"
                           title="查看">
                      <v-icon>remove_red_eye</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="approvalTask(props.item, 'accept')"
                           :disabled="props.item.approval === 'accepted' || props.item.approval === 'rejected'"
                           title="审批通过">
                      <v-icon>speaker_notes</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="approvalTask(props.item, 'reject')"
                           :disabled="props.item.approval === 'accepted' || props.item.approval === 'rejected'"
                           title="审批不通过">
                      <v-icon>speaker_notes_off</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </template>
            </v-data-table>
            <!-- 分页 -->
            <v-flex class="paginationWrapper"
                    text-xs-center
                    v-show="showPagination">
              <v-custompagination :pagination.sync="paginationApproval"></v-custompagination>
            </v-flex>
          </v-card>
          <v-card flat
                  v-if="active === 'examination'">
            <!-- 工具条 -->
            <v-layout row
                      wrap>
              <v-flex xs2
                      class="tool-wrapper">
                <v-autocomplete label="请选择任务状态"
                                v-model="status"
                                :items="statusList"
                                item-text="name"
                                item-value="code"
                                clearable
                                single-line>
                </v-autocomplete>
              </v-flex>

              <v-flex xs4
                      class="tool-wrapper">
                <v-text-field label="请输入任务内容进行搜索"
                              v-model="keywords"
                              clearable
                              append-icon="search"
                              @keyup.enter="queryTaskByCondition"
                              single-line>
                </v-text-field>
              </v-flex>

              <v-flex xs1
                      class="btn-serach">
                <v-btn color="primary"
                       @click="queryTaskByCondition">
                  查询
                </v-btn>
              </v-flex>

              <!-- 添加任务按钮 -->
              <v-flex xs5
                      class="text-xs-right tool-wrapper"
                      v-if="$authed('POST','farmertasks/main/farmertasks/farmertasksAdd')">
                <v-btn class="btnMargin"
                       small
                       fab
                       dark
                       color="red darken-2"
                       title="新增农事任务"
                       @click="createTask">
                  <v-icon>add</v-icon>
                </v-btn>
              </v-flex>

            </v-layout>

            <!-- 记录数 -->
            <div class="taskNumber">共有任务&nbsp;:&nbsp;
              <span v-text="sumItems"></span>&nbsp;条
            </div>

            <!-- 我考核的任务数据列表 -->
            <v-data-table :headers="headerExamination"
                          :items="dataItems"
                          no-data-text="暂无数据"
                          no-results-text="暂无数据"
                          hide-actions
                          disable-initial-sort
                          must-sort
                          class="tableStyle">
              <template slot="items"
                        slot-scope="props">
                <tr style="cursor: pointer"
                    @click="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail') ? showTaskDetail(props.item.id, props.item.status, 'selcheck') : ''">
                  <td class="text-xs-left hideoverflow"
                      style="max-width:250px;word-wrap: break-word;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"
                      :title="props.item.content">
                    {{ props.item.content }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.startTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.endTime }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    {{ props.item.creator }}
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div class="hideoverflow"
                           :title="props.item.participators">
                        <span> {{ props.item.representative}} </span>
                        <span style="color:#32c787">(负责人)</span>
                        <span v-if="props.item.executor">,{{ props.item.executor }} </span>
                      </div>
                    </template>
                  </td>
                  <td class="text-xs-left hideoverflow">
                    <template>
                      <div v-bind:class="{examined: props.item.status === '已考核', tobeexamined: props.item.status === '待考核', 
                  unknown: props.item.status === '未知状态'}">{{ props.item.status }}</div>
                    </template>
                  </td>
                  <td class="text-xs-center operateMinWidth">
                    <v-btn v-if="$authed('GET','farmertasks/main/farmertasks/farmertasksDetail')"
                           fab
                           small
                           icon
                           @click.stop="showTaskDetail(props.item.id, props.item.status, 'selcheck')"
                           title="查看">
                      <v-icon>remove_red_eye</v-icon>
                    </v-btn>
                    <v-btn small
                           icon
                           fab
                           @click.stop="examinationTask(props.item)"
                           :disabled=props.item.examination
                           title="考核">
                      <v-icon size="20">spellcheck</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </template>
            </v-data-table>
            <!-- 分页 -->
            <v-flex class="paginationWrapper"
                    text-xs-center
                    v-show="showPagination">
              <v-custompagination :pagination.sync="paginationExamination"></v-custompagination>
            </v-flex>
          </v-card>
        </v-tab-item>
        <v-farmertasksadd :visible.sync="addOrEditTaskDialog"
                          :title="addOrEditDialogTitle"
                          :curtask="curtask"></v-farmertasksadd>
        <v-farmertasksexamination :visible.sync="examinationTaskDialog"
                                  :title="examinationTaskDialogTitle"
                                  :curtask="curtask"
                                  :refreshFn="queryTaskForExamination"></v-farmertasksexamination>
        <v-farmertasksview :visible.sync="taskViewDialog"
                           :title="taskViewDialogTitle"
                           :taskid="curtaskid"
                           :seldo="seldo"></v-farmertasksview>
        <v-farmertaskstodo :visible.sync="todoDialog"
                           :curtask="curtask"></v-farmertaskstodo>
        <v-farmerrecordsadd :visible.sync="addRecordDialog"
                            :title="addRecordDialogTitle"
                            source="source-task"
                            :taskid="curtaskid"
                            :isrecordlistadd="false"
                            @callback="queryTaskByCondition">
        </v-farmerrecordsadd>
      </v-tabs>
    </v-card>
  </v-container>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
import { Toast } from '@/models/toast'
import { CTask } from '@/models/task.js'
import VFarmertasksadd from './FarmerTasksAdd.vue'
import VFarmertasksexamination from './FarmerTasksExamination.vue'
import VFarmertasksview from './FarmerTasksView.vue'
import VFarmertaskstodo from './FarmerTasksToDo.vue'
import { MessageBox } from 'element-ui'
import VFarmerrecordsadd from '../workrecord/FarmerRecordsAdd.vue'

export default {
  data () {
    const TODOS = {
      finish: 'IS_FINISH',
      accept: 'IS_ACCEPT',
      reject: 'IS_REJECT',
      done: 'IS_DONE',
      check: 'IS_CHECK'
    }
    return {
      todos: TODOS,
      todoDialog: 'IS_NONE',
      curtask: null,
      active: null,
      status: null, // 状态
      userInfoList: [],
      statusList: [], // 状态列表
      keywords: null, // 查询关键字
      tabs: ['execute', 'create', 'approval', 'examination'],
      paginationExecute: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10,
        sortBy: 'endTime',
        descending: false
      },
      paginationCreate: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10,
        sortBy: 'endTime',
        descending: false
      },
      paginationApproval: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10,
        sortBy: 'endTime',
        descending: false
      },
      paginationExamination: {
        total: 0,
        page: 1, // page 当前页
        rowsPerPage: 10,
        sortBy: 'endTime',
        descending: false
      },
      showPagination: true,
      // 我执行的任务表头
      headerExecute: [
        {
          text: '任务内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '任务开始时间',
          align: 'left',
          sortable: true,
          value: 'startTime'
        },
        {
          text: '任务截止时间',
          align: 'left',
          sortable: true,
          value: 'endTime'
        },
        {
          text: '任务执行状态',
          align: 'left',
          sortable: false,
          value: 'status'
        },
        {
          text: '任务创建人',
          align: 'left',
          sortable: false,
          value: 'creator'
        },
        {
          text: '任务执行人',
          align: 'left',
          sortable: false,
          value: 'executor'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      // 我创建的任务表头
      headerCreate: [
        {
          text: '任务内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '任务开始时间',
          align: 'left',
          sortable: true,
          value: 'startTime'
        },
        {
          text: '任务截止时间',
          align: 'left',
          sortable: true,
          value: 'endTime'
        },
        {
          text: '任务完成状态',
          align: 'left',
          sortable: false,
          value: 'status'
        },
        {
          text: '任务执行人',
          align: 'left',
          sortable: false,
          value: 'executor'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      // 我审批的任务表头
      headerApproval: [
        {
          text: '任务内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '任务开始时间',
          align: 'left',
          sortable: true,
          value: 'startTime'
        },
        {
          text: '任务截止时间',
          align: 'left',
          sortable: true,
          value: 'endTime'
        },
        {
          text: '任务创建人',
          align: 'left',
          sortable: false,
          value: 'creator'
        },
        {
          text: '任务执行人',
          align: 'left',
          sortable: false,
          value: 'executor'
        },
        {
          text: '任务审批状态',
          align: 'left',
          sortable: false,
          value: 'status'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      // 我考核的任务表头
      headerExamination: [
        {
          text: '任务内容',
          align: 'left',
          sortable: false,
          value: 'content'
        },
        {
          text: '任务开始时间',
          align: 'left',
          sortable: true,
          value: 'startTime'
        },
        {
          text: '任务截止时间',
          align: 'left',
          sortable: true,
          value: 'endTime'
        },
        {
          text: '任务创建人',
          align: 'left',
          sortable: false,
          value: 'creator'
        },
        {
          text: '任务执行人',
          align: 'left',
          sortable: false,
          value: 'executor'
        },
        {
          text: '任务考核状态',
          align: 'left',
          sortable: false,
          value: 'status'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false
        }
      ],
      // 任务列表数据
      dataItems: [],
      // 当前选中的tab页
      currentTable: 'execute',
      // 总记录数
      sumItems: 0,
      addOrEditTaskDialog: 'IS_NONE',
      addOrEditDialogTitle: '',
      examinationTaskDialog: false,
      examinationTaskDialogTitle: '',
      taskViewDialog: false,
      taskViewDialogTitle: '任务详情',
      curtaskid: '',
      taskstatus: '',
      seldo: '',
      addRecordDialog: false,
      addRecordDialogTitle: '新建农事记录'

    }
  },
  computed: {
    supportUserId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').supportid : ''
    },
    tenantId () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  created () {
    this.toast = new Toast(this, null, null)
    this.isValidUser()
  },
  watch: {
    active: function (tab) {
      this.currentTable = tab
      this.dataItems = []
      this.sumItems = 0
      this.keywords = null
      this.status = null
      this.changeTaskStatusList(tab)
      this.queryTask(this.currentTable, this.status, this.keywords)
    },
    status: function (status) {
      this.status = status
    },
    'paginationExecute.page' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationExecute.rowsPerPage' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationCreate.page' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationCreate.rowsPerPage' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationApproval.page' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationApproval.rowsPerPage' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationExamination.page' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    'paginationExamination.rowsPerPage' (val) {
      this.queryTask(this.currentTable, this.status, this.keywords)
    },

    addOrEditTaskDialog (val) {
      val === 'IS_NONE' ? this.queryTask(this.currentTable, this.status, this.keywords) : null
    },

    taskViewDialog (val) {
      if (!val) {
        this.queryTask(this.currentTable, this.status, this.keywords)
      }
    },
    todoDialog: function (v) {
      console.log(v)
      // v === 'IS_NONE' ? this.disposeDialog() : null
      v === 'IS_NONE' ? this.queryTask(this.currentTable, this.status, this.keywords) : null
    }
  },
  methods: {
    // 判断当前登录用户是否有效
    isValidUser () {
      let user = global.helper.ls.get('user')
      if (!user) return this.showMessageBox()
      mongo.db('iss').collection('tenant.staff').find({ filter: { _id: { $oid: user.supportid } } }).then(res => {
        if (res.items.length > 0) {
          let tmpUser = res.items[0]
          let dbUpdateTime = Number(tmpUser.updatedOn.$numberLong)
          let curUpdateTime = Number(user.operateredittime)
          if (dbUpdateTime > curUpdateTime) {
            this.showMessageBox()
          }
        } else {
          this.showMessageBox()
        }
      })
    },
    showMessageBox () {
      MessageBox.alert('用户信息已变更,请重新登录！', '提示', {
        confirmButtonText: '确定',
        callback: action => {
          let user = null
          let bread = []
          this.$store.commit('setUser', user)
          this.$store.commit('setBreadList', bread)
          this.$router.push({ name: 'login' })
        }
      })
    },
    // 任务流转处理
    toDo (doname) {
      console.log(doname)
      if (doname === 'check') {
        this.examinationTaskDialog = true
      } else {
        this.todoDialog = this.todos[doname]
      }
    },
    // 根据当前活动的tab页动态绑定任务状态列表
    changeTaskStatusList (tab) {
      switch (tab) {
        case 'execute':
          this.statusList = [{ code: 'all', name: '全部状态' }, { code: 'overdue', name: '已逾期' }, { code: 'executing', name: '进行中' }, { code: 'begin', name: '即将开始' }, { code: 'completed', name: '已完成' }, { code: 'examined', name: '已考核' }, { code: 'stopped', name: '已中止' }]
          break
        case 'create':
          this.statusList = [{ code: 'all', name: '全部状态' }, { code: 'overdue', name: '已逾期' }, { code: 'tobeapproved', name: '待审批' }, { code: 'rejected', name: '审批不通过' }, { code: 'executing', name: '进行中' }, { code: 'begin', name: '即将开始' }, { code: 'completed', name: '已完成' }, { code: 'examined', name: '已考核' }, { code: 'stopped', name: '已中止' }]
          break
        case 'approval':
          this.statusList = [{ code: 'all', name: '全部状态' }, { code: 'tobeapproved', name: '待审批' }, { code: 'approved', name: '审批通过' }, { code: 'rejected', name: '审批不通过' }]
          break
        case 'examination':
          this.statusList = [{ code: 'all', name: '全部状态' }, { code: 'tobeexamined', name: '待考核' }, { code: 'examined', name: '已考核' }]
          break
        default:
          this.statusList = [{ code: 'all', name: '全部状态' }, { code: 'overdue', name: '已逾期' }, { code: 'executing', name: '进行中' }, { code: 'begin', name: '即将开始' }, { code: 'completed', name: '已完成' }, { code: 'examined', name: '已考核' }, { code: 'stopped', name: '已中止' }]
          break
      }
    },

    showTaskDetail (id, status, seldo) {
      this.curtaskid = id
      this.taskstatus = status
      this.seldo = seldo
      this.taskViewDialog = true
    },

    // 保存任务到农事记录
    saveToRecord (id) {
      if (!id) return
      this.curtaskid = id
      this.addRecordDialog = true
      this.addRecordDialogTitle = '新建农事记录'
    },

    // 复制任务
    copyTask (task) {
      this.curtask = new CTask(task)
      this.addOrEditDialogTitle = '复制任务'
      this.addOrEditTaskDialog = 'IS_COPY'
    },

    // 中止任务
    stopTask (task) {
      this.curtask = new CTask(task)
      this.toDo('finish')
    },

    // 审批任务
    approvalTask (task, doname) {
      this.curtask = new CTask(task)
      this.toDo(doname)
    },

    // 考核任务
    examinationTask (task) {
      this.curtask = task
      this.examinationTaskDialogTitle = '任务考核'
      this.examinationTaskDialog = true
    },

    // 根据过滤条件查询任务
    queryTaskByCondition () {
      switch (this.currentTable) {
        case 'execute':
          this.paginationExecute.page = 1
          this.paginationExecute.rowsPerPage = 10
          break
        case 'create':
          this.paginationCreate.page = 1
          this.paginationCreate.rowsPerPage = 10
          break
        case 'approval':
          this.paginationApproval.page = 1
          this.paginationApproval.rowsPerPage = 10
          break
        case 'examination':
          this.paginationExamination.page = 1
          this.paginationExamination.rowsPerPage = 10
          break
        default:
          break
      }
      this.queryTask(this.currentTable, this.status, this.keywords)
    },
    // 查询数据
    queryTask (currentTable, status, keywords) {
      switch (currentTable) {
        case 'execute':
          this.queryTaskForExecute(status, keywords)
          break
        case 'create':
          this.queryTaskForCreate(status, keywords)
          break
        case 'approval':
          this.queryTaskForApproval(status, keywords)
          break
        case 'examination':
          this.queryTaskForExamination(status, keywords)
          break
        default:
          break
      }
    },

    // 查询我执行的任务
    async queryTaskForExecute (status, keywords) {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.participators = { $in: [{ $oid: this.supportUserId }] }
        filter.tenant = { $oid: this.tenantId }
        if (keywords) {
          filter.content = { $regex: this.escapeCharacter(keywords.trim()) }
        }
        switch (status) {
          case 'overdue':
            filter.stage = 'execution'
            filter.finish = false
            filter.endOn = { $lt: Date.parse(new Date()) }
            break
          case 'executing':
            filter.stage = 'execution'
            filter.finish = false
            filter.startOn = { $lte: Date.parse(new Date()) }
            filter.endOn = { $gte: Date.parse(new Date()) }
            break
          case 'begin':
            filter.stage = 'execution'
            filter.finish = false
            filter.startOn = { $gt: Date.parse(new Date()) }
            break
          case 'completed':
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = { $exists: false }
            break
          case 'examined':
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = { $exists: true }
            filter.examiner = { $exists: true }
            break
          case 'stopped':
            let or = []
            or.push({ stage: 'execution', approval: 'accepted', finish: true })
            or.push({ stage: 'execution', approval: { $exists: false }, finish: true })
            filter.$and = [{ $or: or }]
            filter.finish = true
            break
          default:
            filter.stage = { $ne: 'approval' }
            break
        }
        filterParam.filter = filter
        filterParam.skip = (this.paginationExecute.page - 1) * this.paginationExecute.rowsPerPage
        filterParam.limit = this.paginationExecute.rowsPerPage
        filterParam.sort = { endOn: 1 }
        let data = await mongo.db().collection('task').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.paginationExecute.total = Number(data.total.$numberLong)
        let userIdList = this.getUserIdsFromArray(items)
        this.userInfoList = await this.queryUserInfoByIds(userIdList)
        this.bindData(items)
      } catch (err) {
        console.log(err)
        this.sumItems = 0
      } finally {
        this.toast.close()
      }
    },

    // 查询我创建的任务
    async queryTaskForCreate (status, keywords) {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.creator = { $oid: this.supportUserId }
        filter.tenant = { $oid: this.tenantId }
        if (keywords) {
          filter.content = { $regex: this.escapeCharacter(keywords.trim()) }
        }
        switch (status) {
          case 'tobeapproved':
            filter.approver = { $exists: true }
            filter.stage = 'approval'
            filter.finish = false
            filter.approval = { $exists: false }
            break
          case 'rejected':
            filter.approver = { $exists: true }
            filter.stage = 'approval'
            filter.finish = true
            filter.approval = 'rejected'
            break
          case 'overdue':
            filter.stage = 'execution'
            filter.finish = false
            filter.endOn = { $lt: Date.parse(new Date()) }
            break
          case 'executing':
            filter.stage = 'execution'
            filter.finish = false
            filter.startOn = { $lte: Date.parse(new Date()) }
            filter.endOn = { $gte: Date.parse(new Date()) }
            break
          case 'begin':
            filter.stage = 'execution'
            filter.finish = false
            filter.startOn = { $gt: Date.parse(new Date()) }
            break
          case 'completed':
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = { $exists: false }
            break
          case 'examined':
            filter.examiner = { $exists: true }
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = { $exists: true }
            break
          case 'stopped':
            let or = []
            or.push({ stage: 'execution', approval: 'accepted' })
            or.push({ stage: 'execution', approval: { $exists: false } })
            or.push({ stage: 'approval', approval: { $exists: false } })
            filter.$and = [{ $or: or }]
            filter.finish = true
            break
          default:
            break
        }
        filterParam.filter = filter
        filterParam.skip = (this.paginationCreate.page - 1) * this.paginationCreate.rowsPerPage
        filterParam.limit = this.paginationCreate.rowsPerPage
        filterParam.sort = { endOn: 1 }
        let data = await mongo.db().collection('task').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.paginationCreate.total = Number(data.total.$numberLong)
        let userIdList = this.getUserIdsFromArray(items)
        this.userInfoList = await this.queryUserInfoByIds(userIdList)
        this.bindData(items)
      } catch (err) {
        console.log(err)
        this.sumItems = 0
      } finally {
        this.toast.close()
      }
    },

    // 查询我审批的任务
    async queryTaskForApproval (status, keywords) {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.approver = { $oid: this.supportUserId }
        filter.tenant = { $oid: this.tenantId }
        if (keywords) {
          filter.content = { $regex: this.escapeCharacter(keywords.trim()) }
        }
        switch (status) {
          case 'tobeapproved':
            filter.stage = 'approval'
            filter.finish = { $ne: true }
            filter.approval = { $exists: false }
            break
          case 'approved':
            filter.approval = 'accepted'
            break
          case 'rejected':
            filter.stage = 'approval'
            filter.finish = true
            filter.approval = 'rejected'
            break
          default:
            let or = []
            or.push({ stage: 'approval', finish: { $ne: true }, approval: { $exists: false } })
            or.push({ approval: 'accepted' })
            or.push({ stage: 'approval', finish: true, approval: 'rejected' })
            filter.$and = [{ $or: or }]
            break
        }
        filterParam.filter = filter
        filterParam.skip = (this.paginationApproval.page - 1) * this.paginationApproval.rowsPerPage
        filterParam.limit = this.paginationApproval.rowsPerPage
        filterParam.sort = { endOn: 1 }
        let data = await mongo.db().collection('task').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.paginationApproval.total = Number(data.total.$numberLong)
        let userIdList = this.getUserIdsFromArray(items)
        this.userInfoList = await this.queryUserInfoByIds(userIdList)
        this.bindData(items)
      } catch (err) {
        console.log(err)
        this.sumItems = 0
      } finally {
        this.toast.close()
      }
    },

    // 查询我考核的任务
    async queryTaskForExamination (status, keywords) {
      try {
        this.toast.show()
        let filterParam = {}
        let filter = {}
        filter.examiner = { $oid: this.supportUserId }
        filter.tenant = { $oid: this.tenantId }
        if (keywords) {
          filter.content = { $regex: this.escapeCharacter(keywords.trim()) }
        }
        switch (status) {
          case 'tobeexamined':
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = { $exists: false }
            break
          case 'examined':
            filter.stage = 'completion'
            filter.finish = true
            filter.examination = true
            break
          default:
            filter.finish = true
            filter.stage = 'completion'
            break
        }
        filterParam.filter = filter
        filterParam.skip = (this.paginationExamination.page - 1) * this.paginationExamination.rowsPerPage
        filterParam.limit = this.paginationExamination.rowsPerPage
        filterParam.sort = { endOn: 1 }
        let data = await mongo.db().collection('task').find(filterParam)
        let items = data.items
        this.sumItems = Number(data.total.$numberLong)
        this.paginationExamination.total = Number(data.total.$numberLong)
        let userIdList = this.getUserIdsFromArray(items)
        this.userInfoList = await this.queryUserInfoByIds(userIdList)
        this.bindData(items)
      } catch (err) {
        console.log(err)
        this.sumItems = 0
      } finally {
        this.toast.close()
      }
    },

    // 转换状态
    convertStatus (task) {
      let curTime = Date.parse(new Date())
      switch (this.currentTable) {
        case 'execute':
          // 任务完成的情况
          if (task.finish) {
            if (task.stage !== 'completion') {
              return '已中止'
            } else {
              // 不存在考核人的情况
              if (!task.examiner) {
                return '已完成'
              } else {
                if (task.examination === true) {
                  return '已考核'
                } else {
                  // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                  return '已完成'
                }
              }
            }
          } else { // 任务未完成的情况
            if (Number(curTime) < Number(task.startOn.$numberLong)) {
              return '即将开始'
            }
            if (Number(curTime) > Number(task.endOn.$numberLong)) {
              return '已逾期'
            }
            if (Number(curTime) >= Number(task.startOn.$numberLong) && Number(curTime) <= Number(task.endOn.$numberLong)) {
              return '进行中'
            }
          }
          return '未知状态'
        case 'create':
          // 存在审批人的情况
          if (task.approver) {
            // 不存在审批状态
            if (!task.approval) {
              if (task.finish) {
                return '已中止'
              } else {
                return '待审批'
              }
            } else {
              if (task.approval === 'rejected') {
                return '审批不通过'
              } else {
                // 任务完成的情况
                if (task.finish) {
                  if (task.stage !== 'completion') {
                    return '已中止'
                  } else {
                    if (!task.examiner) {
                      return '已完成'
                    } else {
                      if (task.examination === true) {
                        return '已考核'
                      } else {
                        // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                        return '已完成'
                      }
                    }
                  }
                } else {
                  if (Number(curTime) < Number(task.startOn.$numberLong)) {
                    return '即将开始'
                  }
                  if (Number(curTime) > Number(task.endOn.$numberLong)) {
                    return '已逾期'
                  }
                  if (Number(curTime) >= Number(task.startOn.$numberLong) && Number(curTime) <= Number(task.endOn.$numberLong)) {
                    return '进行中'
                  }
                }
              }
            }
          } else { // 不存在审批人的情况
            // 任务完成
            if (task.finish) {
              if (task.stage !== 'completion') {
                return '已中止'
              } else {
                if (!task.examiner) {
                  return '已完成'
                } else {
                  if (task.examination === true) {
                    return '已考核'
                  } else {
                    // 此处本为待考核状态，但在【我执行的任务】中没有此状态，显示为已完成
                    return '已完成'
                  }
                }
              }
            } else {  // 任务未完成
              if (Number(curTime) < Number(task.startOn.$numberLong)) {
                return '即将开始'
              }
              if (Number(curTime) > Number(task.endOn.$numberLong)) {
                return '已逾期'
              }
              if (Number(curTime) >= Number(task.startOn.$numberLong) && Number(curTime) <= Number(task.endOn.$numberLong)) {
                return '进行中'
              }
            }
          }
          return '未知状态'
        case 'approval':
          // 不存在审批状态的情况
          if (!task.approval) {
            return '待审批'
          } else { // 存在审批状态的情况分为：审批通过、审批不通过
            if (task.approval === 'accepted') {
              return '审批通过'
            }
            if (task.approval === 'rejected') {
              return '审批不通过'
            }
          }
          return '未知状态'
        case 'examination':
          if (task.finish === true && task.examination === true && task.stage === 'completion') {
            return '已考核'
          }
          if (task.finish === true && !task.examination && task.stage === 'completion') {
            return '待考核'
          }
          return '未知状态'
        default:
          return '未知状态'
      }
    },

    // 绑定数据
    bindData (taskList) {
      this.dataItems = []
      for (let taskData of taskList) {
        let task = {}
        // 任务ID
        task._id = taskData._id
        task.id = taskData._id.$oid
        // 任务是否已同步到农事记录
        task.recordCreated = taskData.recordCreated
        // 任务完成状态
        task.finish = taskData.finish || false
        // 当前任务所处阶段 approval/execution/completion
        task.stage = taskData.stage
        // 任务内容
        task.content = taskData.content
        // 任务开始时间（已格式化）
        task.startTime = this.dateFormat(taskData.startOn.$numberLong, 'Y-m-d H:i')
        // 任务开始时间（时间戳）
        task.startTimeLong = taskData.startOn.$numberLong
        // 任务结束时间（已格式化）
        task.endTime = this.dateFormat(taskData.endOn.$numberLong, 'Y-m-d H:i')
        // 任务结束时间（时间戳）
        task.endTimeLong = taskData.endOn.$numberLong
        // 任务创建人名称
        task.creator = this.getUserNameById(taskData.creator.$oid)
        // 任务创建人ID
        task.creatorId = taskData.creator.$oid
        // 任务执行人(参与人)名称（排除任务负责人）
        task.executor = this.getExecutorsNamesById(taskData.participators, taskData.representative.$oid)
        // 任务执行人(参与人)ID(排除任务负责人)
        task.executorIds = taskData.participators ? (taskData.participators.filter(function (elem) { return elem.$oid !== taskData.representative.$oid })).map(function (elem) { return elem.$oid }) : null
        // 任务负责人名称
        task.representative = taskData.representative ? this.getUserNameById(taskData.representative.$oid) : null
        // 任务负责人ID
        task.representativeId = taskData.representative ? taskData.representative.$oid : null
        // 任务状态名称
        task.status = this.convertStatus(taskData)
        // 任务考核状态 true/false/null
        task.examination = taskData.examination || false
        // 任务考核人名称
        task.examiner = taskData.examiner ? this.getUserNameById(taskData.examiner.$oid) : null
        // 任务考核人ID
        task.examinerId = taskData.examiner ? taskData.examiner.$oid : null
        // 任务审批人名称
        task.approver = taskData.approver ? this.getUserNameById(taskData.approver.$oid) : null
        // 任务审批人ID
        task.approverId = taskData.approver ? taskData.approver.$oid : null
        // 任务审批状态 accepted/rejected/null
        task.approval = taskData.approval || false
        // 任务参与人名称(包含任务负责人)
        task.participators = this.getExecutorsNamesById(taskData.participators, null)
        // 任务参与人ID(包含任务负责人)
        task.participatorsId = taskData.participators ? taskData.participators.map(function (elem) { return elem.$oid }) : null
        // 任务地块ID
        task.farmlandIds = taskData.farmlands ? taskData.farmlands.map(function (elem) { return elem.$oid }) : null
        this.dataItems.push(task)
      }
    },

    // 根据用户ID查询用户信息，用户ID为{"$oid":"xxxx"}对象
    async queryUserInfoByIds (userIds) {
      let filter = [{ $match: { _id: { $in: userIds }, $or: [{ _removed: { $exists: true } }, { _removed: { $exists: false } }] } }]
      let items = await mongo.db('iss').collection('tenant.staff').aggregate(filter)
      return items
    },

    // 根据ID获取执行人名称,排除负责人
    getExecutorsNamesById (array, representative) {
      let tmpArray = []
      for (let item of array) {
        let user = this.userInfoList.find(function (elem) {
          return elem._id.$oid === item.$oid
        })
        if (user && user._id.$oid !== representative) {
          tmpArray.push(user.name || user.mobile)
        }
      }
      return tmpArray.join(',')
    },

    // 根据ID获取用户名称
    getUserNameById (userId) {
      let user = this.userInfoList.find(function (elem) {
        return elem._id.$oid === userId
      })
      return user ? user.name || user.mobile : '--'
    },

    // 从数组中获取用户ID对象
    getUserIdsFromArray (array) {
      let tmpUserIdArray = []
      for (let item of array) {
        if (item.creator) {
          tmpUserIdArray.push(item.creator)
        }
        if (item.examiner) {
          tmpUserIdArray.push(item.examiner)
        }
        if (item.approver) {
          tmpUserIdArray.push(item.approver)
        }
        if (item.representative) {
          tmpUserIdArray.push(item.representative)
        }
        if (item.participators) {
          item.participators.forEach((it) => {
            tmpUserIdArray.push(it)
          })
        }
      }
      return tmpUserIdArray
    },

    // 格式化日期
    dateFormat (timestamp, formats) {
      // formats格式包括
      // 1. Y-m-d
      // 2. Y-m-d H:i:s
      // 3. Y年m月d日
      // 4. Y年m月d日 H时i分
      formats = formats || 'Y-m-d'
      let zero = function (value) {
        if (value < 10) {
          return '0' + value
        }
        return value
      }
      let myDate = Number(timestamp) ? new Date(Number(timestamp)) : new Date()
      let year = myDate.getFullYear()
      let month = zero(myDate.getMonth() + 1)
      let day = zero(myDate.getDate())
      let hour = zero(myDate.getHours())
      let minite = zero(myDate.getMinutes())
      let second = zero(myDate.getSeconds())
      return formats.replace(/Y|m|d|H|i|s/ig, function (matches) {
        return ({ Y: year, m: month, d: day, H: hour, i: minite, s: second })[matches]
      })
    },

    createTask () {
      this.addOrEditDialogTitle = '新建任务'
      this.addOrEditTaskDialog = 'IS_ADD'
    },
    escapeCharacter (str) {
      if (!str) return
      let result = []
      let tmpStrs = str.toString().split('')
      let specialChars = ['$', '(', ')', '*', '+', '.', '?', '\\', '/', '^', '{', '}', '|']
      for (let char of tmpStrs) {
        let tmp = specialChars.find((item) => {
          return item === char
        })
        result.push(tmp ? '\\' + char : char)
      }
      return result.join('')
    }
  },
  components: {
    VFarmertasksadd,
    VFarmertasksexamination,
    VFarmertasksview,
    VFarmertaskstodo,
    VFarmerrecordsadd
  }
}
</script>
<style scoped>
.main {
  padding-left: 20px;
  padding-right: 20px;
}
.tool-wrapper:not(:last-child) {
  padding-right: 20px;
  margin-top: 20px;
}
.paginationWrapper {
  margin-top: 20px;
  margin-bottom: 50px;
}
.btnMargin {
  margin-left: 0;
  margin-right: 0;
  margin-top: 13px;
}
.taskNumber {
  padding-top: 15px;
  padding-bottom: 15px;
}
.hideoverflow {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.tableStyle td {
  max-width: 250px;
}
.operateMinWidth {
  min-width: 230px;
}
.btnPadding {
  padding-bottom: 20px;
}
.btnSize {
  min-width: 107px;
}
.btn-serach {
  margin-top: 26px;
  margin-left: -15px;
}
.overdue,
.rejected,
.unknown {
  color: red;
}
.executing {
  color: #1699f4;
}
.begin,
.stopped {
  color: #a6a6a6;
}
.completed,
.approved,
.examined {
  color: #32c787;
}
.tobeexamined,
.tobeapproved {
  color: #333;
}
</style>
<style>
.activeTab a {
  color: #32c787;
}
</style>

