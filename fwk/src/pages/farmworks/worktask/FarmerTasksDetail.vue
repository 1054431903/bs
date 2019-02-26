
<template>
  <v-container fluid class="containerWrapper">
    <v-card class="page-content">
      <go-back backName="任务详情" comName="farmertasks"/>

      <v-layout class="flexWrapper" row wrap>

        <v-flex xs12 offset-xs1>
          <v-layout row wrap class="typenameWrapper">
            <v-flex xs6>
              <span class="lineHeight-adjust">任务类别&nbsp;:&nbsp;</span><span v-text="taskDetail.typename"></span>
            </v-flex>

            <!--
                显示条件 1. 从我参与的任务 跳过来的 2. 参与状态待确认(后台返回)
            -->
            <v-flex xs6 v-if="parseInt(select) === 1 && user.taskparticsstatus === 'task-partics-status-waitingconfirm' && $authed('PUT','farmertasks/main/farmertasks/farmertasksDetail')" text-xs-right>
              <v-btn @click="submitJoin('task-partics-status-partake')" class="btnSizeSubmitJoin" color="primary" :disabled="disabledSubmitJoin">

                确认参与
                <v-progress-circular
                  v-show="progressShowSubmitJoin"
                  class="progressWrapperSubmitJoin"
                  size="23"
                  indeterminate
                  color="primary"
                />
              </v-btn>
              <v-btn @click="submitJoin('task-partics-status-refuse')" class="btnSizeSubmitJoin" :disabled="disabledSubmitJoin">
                拒绝
                <v-progress-circular
                  v-show="progressShowRejectJoin"
                  class="progressWrapperSubmitJoin"
                  size="23"
                  indeterminate
                  color="primary"
                />
              </v-btn>
            </v-flex>

            <v-flex xs6 text-xs-right v-if="parseInt(select) === 2">
              <v-btn v-if="$authed('DELETE', 'farmertasks/main')" class="btnSize" color="error" @click="dialogDelete = true" outline><v-icon color="error">delete</v-icon>&nbsp;删除</v-btn>
              <v-btn v-if="$authed('PUT','farmertasks/main/farmertasks/farmertasksEdit')" color="primary" class="btnSize" :to="{name: 'farmertasksEdit', query: {id: $route.query.id}}" outline><v-icon color="primary">edit</v-icon>&nbsp;编辑</v-btn>
            </v-flex>


          </v-layout>
        </v-flex>

        <!--  任务内容 -->
        <v-flex xs12 offset-xs1 class="taskContent-wrapper">
          <span>任务内容&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.describe || '- -'"></span>
        </v-flex>

        <!-- 开始时间 -->
        <v-flex xs12 offset-xs1>
          <span>开始时间&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.begintime || '- -'"></span>
        </v-flex>

        <!-- 结束时间 -->
        <v-flex xs12 offset-xs1>
          <span>结束时间&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.endtime || '- -'"></span>
        </v-flex>

        <!-- 发布人 -->
        <v-flex xs12 offset-xs1>
          <span>发布人&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.createuseridname || '- -'"></span>
        </v-flex>

        <!-- 参与人 -->
        <v-flex xs12 offset-xs1>
          <span>参与人&nbsp;:&nbsp;</span>
          <span v-for="(list, index) in taskDetail.taskparticslist" :key="index">
            <span v-text="list.username"></span>
            <span class="statusColor">(&nbsp;{{list.taskparticsstatusname}}&nbsp;)</span>
            <span v-text="'、'" v-if="taskDetail.taskparticslist.length !== (index + 1)"></span>
          </span>
        </v-flex>

        <!-- 地块 约会地点 -->
        <v-flex xs12 offset-xs1 v-if="taskDetail.type === 'task-work'">
          <span>地块&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.parcelidnames || '- -'"></span>
        </v-flex>
        <v-flex xs12 offset-xs1 v-else>
          <span>约会地点:&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.meetingplace || '- -'"></span>
        </v-flex>

        <!-- 抄送人  我发布的任务过来才显示抄送人 -->
        <v-flex xs12 offset-xs1 v-if="select === 2">
          <span>抄送人&nbsp;:&nbsp;</span>
          <span v-for="(list, index) in taskDetail.taskcopylist" :key="index"
                v-text="list.username + '、'"
          >
          </span>
          <span v-if="!taskDetail.taskcopylist || taskDetail.taskcopylist.length===0">- -</span>
        </v-flex>

        <!-- 备注 -->
        <v-flex xs12 offset-xs1>
          <span>备注&nbsp;:&nbsp;</span>
          <span v-text="taskDetail.remark || '- -'"></span>
        </v-flex>

        <v-flex xs12>
          <div class="cutLine"></div>
        </v-flex>
      </v-layout>

      <!-- 执行任务、任务动态 未参与时不显示 已经参与时显示 -->
      <v-layout row
                wrap
                v-if="(user.taskparticsstatus === 'task-partics-status-partake' && parseInt($route.query.select) === 1) || parseInt($route.query.select) !== 1">
        <v-flex xs12>
          <v-tabs v-model="active" color="primary" dark slider-color="white">
              <v-tab
                v-for="tab in tabs"
                :key="tab"
                :href="'#' + tab"
                v-if="(tab === '执行任务' && $authed('POST', 'farmertasks/main/farmertasks/farmertasksDetail')) || tab === '任务动态'"
              >
                {{ tab }}
              </v-tab>

            <v-tabs-items>
              <!-- 执行任务 1的时候显示 -->
              <v-tab-item class="executeTask-wrapper" :id="'执行任务'" v-if="select === 1">
                <!-- 名字 -->
                <v-layout row class="userInfo" style="margin-left: -50px;">
                  <v-flex xs12>
                    <v-avatar size="60px"><img :src="user.userphotourl" /></v-avatar>
                    <span class="userInfo-username" v-text="user.username"></span>
                    <!-- 保存到农事记录按钮 已存为农事记录 显示条件: 不是从抄送我的过来 ??? -->
                    <div class="saveRecord-Wrapper" v-if="this.$route.query.select !== 3">
                      <v-btn class="saveButton"
                             color="primary"
                             v-if="taskDetail.iscansavetorecord && $authed('PUT','farmerrecords/main/farmerrecords/SaveTaskTofarmerrecords')"
                             @click="saveToRecord(taskDetail.id, taskDetail.recordeid, taskDetail.parcelids)"
                      >
                        保存到农事记录
                      </v-btn>
                      <span v-if="taskDetail.savedrecordlabel === '是' ? true : false" class="saveRecord-text">(&nbsp;已存为农事记录&nbsp;)</span>
                    </div>
                  </v-flex>
                </v-layout>

                <v-layout row>
                  <!-- 不可编辑 -->
                  <v-flex v-if="user.taskstatus === 'task-status-finish'" xs12>
                    <!-- 表格 -->
                    <v-layout row>
                      <v-flex xs1>
                        <span>投入品:</span>
                      </v-flex>
                      <v-flex xs11>
                        <v-data-table
                          :headers="headers"
                          :items="recordDetail.outputlist"
                          hide-actions
                          no-data-text="暂无数据"
                          no-data-result="暂无数据"
                          class="elevation-1"
                        >
                          <template slot="items" slot-scope="props">
                            <td class="text-xs-left" v-text="props.item.typename"></td>
                            <td class="text-xs-left" v-text="props.item.name"></td>
                            <td class="text-xs-left" v-text="props.item.stand"></td>
                            <td class="text-xs-left">
                              <span v-text="props.item.num"></span>
                              <span v-text="props.item.numunitname"></span>
                            </td>
                            <td class="text-xs-left" v-text="props.item.facturer"></td>
                          </template>
                        </v-data-table>
                      </v-flex>
                    </v-layout>

                    <!-- 投入成本 -->
                    <v-layout row>
                      <v-flex xs1>投入成本:</v-flex>
                      <v-flex xs11>
                        <span v-text="recordDetail.cost || 0"></span>元
                      </v-flex>
                    </v-layout>

                    <!-- 附件 -->
                    <v-layout row>
                      <v-flex xs1>附件:</v-flex>
                      <v-flex xs11>
                        <a v-for="(list, index) in recordDetail.resourcehistorylist"
                           :key="index"
                           :href="list.resourcepath"
                           v-text="list.resourcename"
                           class="resource"
                           target= "_blank"
                        >
                        </a>
                      </v-flex>
                    </v-layout>

                    <!-- 语音 -->
                    <v-layout row>
                      <v-flex xs1>语音:</v-flex>
                      <v-flex xs11>
                        <audio controls height="100" width="100" v-for="(list, index) in recordDetail.audioResourcehistorylist" :key="index">
                          <source :src="list.resourcepath" type="audio/mp3">
                          <source :src="list.resourcepath" type="audio/mpeg">
                          <source :src="list.resourcepath" type="audio/ogg">
                          <embed :src="list.resourcepath" height="50" width="100">
                        </audio>
                      </v-flex>
                    </v-layout>

                    <!-- 备注 -->
                    <v-layout row>
                      <v-flex xs1>备注:</v-flex>
                      <v-flex xs11 v-text="recordDetail.remark">100元</v-flex>
                    </v-layout>
                  </v-flex>

                  <!-- 可以编辑 -->
                  <v-flex xs12 v-else >
                    <!-- 投入品(表格) -->
                    <v-layout row>
                      <v-flex xs12>

                        <!-- 投入品label -->
                        <v-layout row id="putKind">
                          <v-flex xs1 class="outputWrapper"><span class="labelColor">投入品</span></v-flex>
                          <v-flex xs10>
                            <div class="wrapper">
                              <span>(&nbsp;可添加多项&nbsp;)&nbsp;&nbsp;</span>
                              <v-btn id="addButton" fab small color="primary" @click.stop="clickAdd"><v-icon>add</v-icon></v-btn>
                            </div>
                          </v-flex>
                        </v-layout>

                        <v-layout row>
                          <v-flex xs11 offset-xs1>
                            <v-data-table
                              :headers="headersEdit"
                              :items="outputlist"
                              no-data-text="暂无数据"
                              no-results-text="暂无数据"
                              hide-actions
                              class="tableStyle"
                            >
                              <template slot="items" slot-scope="props">
                                <td class="text-xs-left" v-text="props.item.typename"></td>
                                <td class="text-xs-left hideoverflow" v-text="props.item.name" :title="props.item.name"></td>
                                <td class="text-xs-left" v-text="props.item.stand"></td>
                                <td class="text-xs-left">
                                  <span v-text="props.item.num"></span>
                                  <span v-text="props.item.numunitname"></span>
                                </td>
                                <td class="text-xs-left hideoverflow" v-text="props.item.facturer" :title="props.item.facturer"></td>
                                <td class="text-xs-center">
                                  <v-btn fab small icon title="编辑" @click="editModalForm(props.item.id)">
                                    <v-icon>edit</v-icon>
                                  </v-btn>
                                  <v-btn color="error" fab small flat icon title="删除" @click="deleteModalForm(props.item.id)">
                                    <v-icon>delete</v-icon>
                                  </v-btn>
                                </td>
                              </template>
                            </v-data-table>
                          </v-flex>
                        </v-layout>
                      </v-flex>
                    </v-layout>

                    <!-- 投入成本 -->
                    <v-layout row>
                      <v-flex xs1 class="labelMargin"><span class="labelColor">投入成本</span></v-flex>
                      <v-flex xs5>
                        <v-text-field
                          label="请填写投入成本"
                          type="number"
                          v-model="cost"
                          maxlength="8"
                          onkeyup="value=value.replace(/[^\d.]/g,'')"
                          :rules="rules.number"
                          onKeypress=" return(/[\d.]/.test(String.fromCharCode(event.keyCode))) "
                          oninput=" if(value.length > 8) value = value.slice(0, 8) "
                          suffix="元"
                          single-line
                        >
                        </v-text-field>
                      </v-flex>
                    </v-layout>

                    <!-- 附件 -->
                    <v-layout row>
                      <v-flex xs1><span class="labelColor">附件</span></v-flex>
                      <v-flex xs11>
                        <!-- 文件上传 -->
                        <v-layout row wrap class="uploadFile-wrapper">
                          <v-flex xs12 class="uploadFile-text">
                            <v-btn id="uploadFile" color="primary" dark><v-icon dark>backup</v-icon></v-btn>
                            <span>点击左侧上传按钮选择文件或将文件拖曳到此上传,支持多文件上传</span>
                          </v-flex>
                          <v-flex xs12>
                            <dropzone ref="sendRecordDropzone"
                                      id="dropzone"
                                      :options="dropzoneOptions"
                                      @vdropzone-success="vsuccess"
                                      @vdropzone-error="verror"
                                      @vdropzone-removed-file="removeFile"
                                      @vdropzone-files-added="addFiles"
                            />
                          </v-flex>
                        </v-layout>
                      </v-flex>
                    </v-layout>

                    <!-- 备注 -->
                    <v-layout row>
                      <v-flex xs1><span class="labelColor">备注</span></v-flex>
                      <v-flex xs6>
                        <v-text-field
                          v-model="remark"
                          label="请填写备注"
                          single-line
                          textarea
                          maxlength="500"
                          class="textAreaPadding"
                        >
                        </v-text-field>
                      </v-flex>
                    </v-layout>

                    <!-- 取消 提交 提交并存入农事记录 -->
                    <v-layout row>
                      <v-flex xs12 text-xs-center>
                        <v-btn @click="cancelTask" class="btnSize">取消</v-btn>
                        <v-btn @click="submitTask" class="btnSize" color="primary" :disabled="disabledSubmitTask">
                          提交
                          <v-progress-circular
                            v-show="progressShowSubmitTask"
                            class="progressWrapper"
                            size="23"
                            indeterminate
                            color="primary"
                          />
                        </v-btn>
                        <v-btn @click="submitTaskAndSave"
                               color="primary"
                               :disabled="disabledSubmitTaskAndSave"
                               class="submitTaskAndSave"
                        >
                          提交并存入农事记录
                          <v-progress-circular
                            v-show="progressShowSubmitTaskAndSave"
                            class="progressShowSubmitTaskAndSave"
                            size="23"
                            indeterminate
                            color="primary"
                          />
                        </v-btn>
                      </v-flex>
                    </v-layout>
                  </v-flex>
                </v-layout>
              </v-tab-item>


              <!-- 执行任务 2, 3的时候显示 需要循环 不可编辑 -->
              <v-tab-item class="executeTask-wrapper" :id="'执行任务'" v-else>
                <!-- list -->
                <div v-for="(list, index) in taskDetailList" :key="index">
                  <v-layout row wrap class="userInfo" style="margin-left: -50px;">
                    <v-flex xs12>
                      <v-avatar size="60px"><img :src="list.userphotourl || 'static/peasant/moren.png'" /></v-avatar>
                      <span class="userInfo-username" v-text="list.username"></span>
                      <div class="saveRecord-Wrapper">
                        <span v-text="list.issavedtorecord ? '( 已存为农事记录 )' : '( 未存为农事记录 )'" class="saveRecord-text"></span>
                      </div>
                    </v-flex>
                  </v-layout>
                  <v-layout row>
                    <v-flex xs12>
                    <!-- 表格 -->
                    <v-layout row>
                      <v-flex xs1>
                        <span>投入品:</span>
                      </v-flex>
                      <v-flex xs11>
                        <v-data-table
                          :headers="headers"
                          :items="list.outputlist"
                          hide-actions
                          no-data-text="暂无数据"
                          no-data-result="暂无数据"
                          class="elevation-1"
                        >
                          <template slot="items" slot-scope="props">
                            <td class="text-xs-left" v-text="props.item.typename"></td>
                            <td class="text-xs-left hideoverflow" v-text="props.item.name" :title="props.item.name"></td>
                            <td class="text-xs-left" v-text="props.item.stand"></td>
                            <td class="text-xs-left">
                              <span v-text="props.item.num"></span>
                              <span v-text="props.item.numunitname"></span>
                            </td>
                            <td class="text-xs-left hideoverflow" v-text="props.item.facturer" :title="props.item.facturer"></td>
                          </template>
                        </v-data-table>
                      </v-flex>
                    </v-layout>

                    <!-- 投入成本 -->
                    <v-layout row>
                      <v-flex xs1>投入成本:</v-flex>
                      <v-flex xs11>
                        <span v-text="list.cost || 0"></span>元
                      </v-flex>
                    </v-layout>

                    <!-- 附件 -->
                    <v-layout row>
                      <v-flex xs1>附件:</v-flex>
                      <v-flex xs11>
                        <a v-for="(list2, index2) in list.resourcehistorylist"
                           :key="index2"
                           :href="list2.resourcepath"
                           v-text="list2.resourcename"
                           class="resource"
                           target= "_blank"
                        >
                        </a>
                      </v-flex>
                    </v-layout>

                    <!-- 语音 -->
                    <v-layout row>
                      <v-flex xs1>语音:</v-flex>
                      <v-flex xs11>
                        <audio controls height="100" width="100" v-for="(audio, index) in list.audioResourcehistorylist" :key="index">
                          <source :src="audio.resourcepath" type="audio/mp3">
                          <source :src="audio.resourcepath" type="audio/mpeg">
                          <source :src="audio.resourcepath" type="audio/ogg">
                          <embed :src="audio.resourcepath" height="50" width="100">
                          <span v-text="audio.resourcename"></span>
                        </audio>
                      </v-flex>
                    </v-layout>

                    <!-- 备注 -->
                    <v-layout row>
                      <v-flex xs1>备注:</v-flex>
                      <v-flex xs11 v-text="list.remark">100元</v-flex>
                    </v-layout>
                  </v-flex>
                  </v-layout>
                  <div class="listCut" v-if="(index + 1) !== taskDetailList.length"></div>
                </div>
              </v-tab-item>

              <!-- 任务动态 -->
              <v-tab-item :id="'任务动态'" class="taskDynamic">
                <v-layout row wrap v-for="(list, index) in taskDynamicList" :key="index">
                  <v-flex xs10 offset-xs2>
                    <v-layout row>
                      <v-flex xs1>
                        <v-layout row><v-flex xs12><v-avatar size="60px"><img :src="list.headportraiturl" /></v-avatar></v-flex></v-layout>
                        <v-layout row v-if="taskDynamicList.length !== (index+1)" class="divLine-wrapper">
                          <v-flex xs12>
                            <div class="divLine"></div>
                          </v-flex>
                        </v-layout>
                      </v-flex>
                      <v-flex xs11 class="contentWrapper">
                        <div class="stepText">
                          <div class="textFirst">
                            <span v-if="list.userid === userid" v-text="'我'"></span>
                            <span v-else v-text="list.useridname"></span>
                            <span class="stepDo" v-text="list.title"></span>
                            <span v-text="list.creattime" class="creattime"></span>
                          </div>
                          <div class="stepContent" v-text="list.content"></div>
                        </div>
                      </v-flex>
                    </v-layout>
                  </v-flex>
                </v-layout>

              </v-tab-item>

            </v-tabs-items>
          </v-tabs>
        </v-flex>
        <v-flex xs12><div class="cutLine"></div></v-flex>
      </v-layout>

      <!-- 评论 -->
      <v-layout row wrap>
      <v-flex xs1 class="commentPhoto">
        <v-avatar size="60px"><img :src="user.userphotourl" /></v-avatar>
      </v-flex>
      <v-flex xs11>
        <v-text-field
          label="评论内容"
          v-model="content"
          single-line
          textarea
        >
        </v-text-field>
      </v-flex>

      <v-flex xs12 text-xs-right>
        <v-btn @click="submitComment" :disabled="saveDisabled" class="btnSize" color="primary">
          发送
          <v-progress-circular
            v-show="progressShow"
            class="progressWrapper"
            size="23"
            indeterminate
          >
          </v-progress-circular>
        </v-btn>
      </v-flex>
    </v-layout>
    </v-card>

    <!-- 确认删除弹窗 -->
    <v-modaldialog :isShow="dialogDelete">
      <span slot v-text="message"></span>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat color="primary" @click="dialogDelete = false">取消</v-btn>
          <v-btn flat color="primary" @click="deleteTask($route.query.id)">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>



    <!-- 投入品的弹出框 -->
    <v-modaldialog :isShow="dialogInput"
                   :title="clickNumber === 1 ? '添加投入品' : '编辑投入品'"
                   :modalMaxWidth="600"
                   :bodyHeight="440"
    >
      <v-container slot>
        <!-- 投入品类别 -->
        <v-layout row wrap>
          <v-flex xs4>
            <v-subheader>*&nbsp;投入品类别</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-select
              ref="type"
              label="请选择投入品类别"
              v-model="type"
              :items="typeList"
              item-text="name"
              item-value="id"
              single-line
              :rules="typeRules"
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 投入品名称 -->
        <v-layout row wrap>
          <v-flex xs4>
            <v-subheader>*&nbsp;投入品名称</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field
              ref="name"
              label="请输入投入品名称(50字以内)"
              v-model="name"
              single-line
              :rules="nameRules"
              maxlength="50"
            >
            </v-text-field>
          </v-flex>
        </v-layout>


        <!-- 投入品规格 规格单位   -->
        <v-layout row>
          <v-flex xs4>
            <v-subheader>*&nbsp;规格</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field
              ref="stand"
              v-model="stand"
              label="请填写投入品规格(20个字以内)"
              maxlength="20"
              single-line
              :rules="standRules"
            >
            </v-text-field>
          </v-flex>
        </v-layout>

        <!-- 投入品数量 -->
        <v-layout row>
          <v-flex xs4>
            <v-subheader>投入品数量</v-subheader>
          </v-flex>
          <v-flex xs3 class="numWrapper">
            <v-text-field
              v-model="num"
              type="number"
              onkeyup="value=value.replace(/[^\d.]/g,'')"
              oninput=" if(value.length > 8) value = value.slice(0, 8) "
              :rules="rules.number"
              maxlength="8"
              label="请输入数字"
              single-line
            >
            </v-text-field>
          </v-flex>
          <v-flex xs3 class="numunitWrapper">
            <v-select
              label="数量单位"
              v-model="numunit"
              :items="numunitList"
              item-text="name"
              item-value="id"
              single-line
            >
            </v-select>
          </v-flex>
        </v-layout>

        <!-- 生产厂家 -->
        <v-layout row wrap>
          <v-flex xs4>
            <v-subheader>生产厂家</v-subheader>
          </v-flex>
          <v-flex xs6>
            <v-text-field
              label="请输入投入品生产厂家名称(50字以内)"
              v-model="facturer"
              maxlength="50"
              single-line
            >
            </v-text-field>
          </v-flex>
        </v-layout>
      </v-container>
      <template slot="btnActions">
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat color="primary" @click="cancelAdd">取消</v-btn>
          <v-btn flat color="primary" @click="submitAdd">确定</v-btn>
        </v-card-actions>
      </template>
    </v-modaldialog>

    <!-- 消息弹出框 -->
    <v-dialog v-model="dialogMessage" width="500">
      <v-card>
        <v-card-text>
          <v-layout row wrap text-xs-center>
            <v-flex xs12 v-text="messageResponse"></v-flex>
          </v-layout>
        </v-card-text>

        <v-card-actions class="btnPadding">
          <v-layout row wrap text-xs-center>
            <v-flex xs12>
              <v-btn @click="dialogMessage = false" color="primary" class="btnSize">确认</v-btn>
            </v-flex>
          </v-layout>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>


<script>
  import { excuteApis } from '@/apis'
  import FarmerMixin from '@/pages/farmworks/farmerMixin'
  export default {
    data () {
      return {
        rules: {
          number: [
            (v) => this.numberRule(v) || '请输入有效数字,小数保留1位小数'
          ]
        },
        message: '信息删除后将无法恢复,请谨慎操作,确认删除?',
        nameRules: [() => !!this.name || '请填写投入品名称'],
        typeRules: [() => !!this.type || '请选择投入品类别'],
        standRules: [() => !!this.stand || '请填写投入品规格'],
        dialogMessage: false, // 消息弹出框状态
        messageResponse: null, // 提示信息
        user: {}, // 我的信息
        recordDetail: {}, // 记录详情
        e13: 1,
        taskDetail: {},
        taskDetailList: [],
        dialogDelete: false, // 删除弹出框
        dialogInput: false, // 投入品弹出框
        tabs: ['执行任务', '任务动态'],
        active: null,
        headers: [
          {
            text: '类别',
            align: 'left',
            sortable: false
          },
          {
            text: '名称',
            align: 'left',
            sortable: false
          },
          {
            text: '规格',
            align: 'left',
            sortable: false
          },
          {
            text: '数量',
            align: 'left',
            sortable: false
          },
          {
            text: '厂家',
            align: 'left',
            sortable: false
          }
        ],
        headersEdit: [
          {
            text: '类别',
            align: 'left',
            sortable: false
          },
          {
            text: '名称',
            align: 'left',
            sortable: false
          },
          {
            text: '规格',
            align: 'left',
            sortable: false
          },
          {
            text: '数量',
            align: 'left',
            sortable: false
          },
          {
            text: '厂家',
            align: 'left',
            sortable: false
          },
          {
            text: '操作',
            align: 'center',
            sortable: false
          }
        ],
        fileId: 0, // 上传文件资源标识
        dropzoneOptions: {
          url: global.constant.uploadFileUrl, // ok
          method: 'post', // 文件上传方式 默认post
          // thumbnailWidth: 150, //  设置缩略图的缩略比 配合下面的thumbnailHeight一起使用
          // thumbnailHeight: 150,
          maxFilesize: 20, // 上传大小限制 单位MB
          // uploadMultiple: // 是否 Dropzone 应该在一个请求中发送多个文件
          dictDefaultMessage: '',
          clickable: '#uploadFile', // dropzone 元素本身是否可以点击 默认true,或者设置点击那个元素能上传,可以是数组
          // maxFiles: 10 // 最多处理几个文件
          addRemoveLinks: true,
          dictFileTooBig: '请上传20M以内的文件', // 文件过大时显示的文字信息
          dictCancelUpload: '取消上传', // 取消上传按钮自定义文字
          dictRemoveFile: '移除', // 移除按钮自定义文字
          dictResponseError: '上传失败,请重试' // 服务器无响应的显示信息
        },

        cost: null, // 投入成本
        remark: null, // 备注
        resourcehistorylist: [], // 附件列表
        taskDynamicList: [], // 任务动态
        username: null, // 名字

        /**
         * 投入品相关
         */
        name: '', // 投入品名称
        type: null, // 投入品类别
        typeList: [], // 投入品类型列表
        stand: null, // 规格
        standunit: null, // 投入品规格单位
        num: null, // 数量
        numunit: null, // 数量单位
        numunitList: [], // 数量单位列表
        facturer: null, // 投入品生产厂家
        clickNumber: 0, /* 标识 投入品 新增1 编辑2  */
        outputIndex: -1, /* 当前编辑的是那一个投入品 用id标识 */
        outputlist: [], // 存储投入品数组对象
        count: 0, // 计数投入品条数 唯一标示

        content: null, // 评论内容
        recordId: [], // recordid, 提交农事任务成功以后才有

        /**
         * 转圈
         */
        saveDisabled: false, // 保存
        progressShow: false,
        disabledSubmitJoin: false, // 确认参与
        progressShowSubmitJoin: false,
        progressShowRejectJoin: false,
        disabledSubmitTask: false, // 提交
        progressShowSubmitTask: false,
        disabledSubmitTaskAndSave: false, // 提交并存入农事记录
        progressShowSubmitTaskAndSave: false,
        progressShowDelete: false, // 删除
        deleteDisabled: false
      }
    },
    mixins: [ FarmerMixin ],
    computed: {

      /**
       * 从哪个表格过来
       */
      select () {
        return parseInt(this.$route.query.select)
      },

      /**
       * userid
       */
      userid () {
        return global.helper.ls.get('user') ? global.helper.ls.get('user').id : -1
      },

      /**
       * 投入品表单必填字段
       */
      outputForm () {
        return {
          name: this.name,
          type: this.type,
          stand: this.stand
        }
      }
    },
    mounted () {
      this.getTaskdDetail()
      this.getServiceData()
      this.disabledSpace()
    },
    methods: {
      numberRule (v) {
        if (v) {
          return (/^\+?[1-9][0-9]*(\.\d{1,1})?$/.test(v)) || (/^0(\.\d{1,1})?$/.test(v))
        }
        return true
      },
      /**
       * 禁止空格键触发页面滚动条滑动
       */
      disabledSpace () {
        // 解决 按下space时, 弹出框中下拉框位置错位问题
        document.onkeydown = function (event) {
          if (event.target.nodeName === 'TEXTAREA' || event.target.nodeName === 'INPUT') return // 输入框,下拉框 return
          if (event.keyCode === 32) {
            event.preventDefault()
          }
        }
      },

      /**
       * 根据id获取农事任务详情 (多个)
       */
      getTaskdDetail () {
        this.showLoad()
        excuteApis({id: this.$route.query.id}, global.constant.farmApiUrls, 'task', 'querybyid').then(response => {
          console.log('获取农事任务详情成功')
          console.log(response)
          if (response.data && response.data.flag === 1 && response.data.data) {
            if (response.data.data.begintime) response.data.data.begintime = response.data.data.begintime.substring(0, 10) // 处理时间日期
            if (response.data.data.endtime) response.data.data.endtime = response.data.data.endtime.substring(0, 10)
            this.taskDetail = response.data.data // taskDetail
            this.user = this.getMyInfo(this.userid, this.taskDetail.taskparticslist) // 找到我的信息
            if (this.user) this.getRecord(this.user.taskstatus, this.user.recordeid) // 获取农事记录信息 (因为农事任务信息是存在农事记录里面)

            /* 获取其他参与人任务详情(包括我) */
            if (this.select !== 1 && this.taskDetail.taskparticslist && this.taskDetail.taskparticslist.length > 0) {
              for (let i = 0; i < this.taskDetail.taskparticslist.length; ++i) {
                this.getRecordList(this.taskDetail.taskparticslist[i])
              }
            }
          }
        }).finally(() => {
          this.closeLoad()
        })
      },

      /**
       * 根据id删除农事任务
       * @param id
       */
      deleteTask (id) {
        this.progressShowDelete = true
        this.deleteDisabled = true
        excuteApis({id: id}, global.constant.farmApiUrls, 'task', 'deletebyid').then(response => {
          if (response.data && response.data.flag === 1) {
            console.log('删除农事任务成功')
            this.$router.push({name: 'farmertasks'})
          } else {
            console.log('删除农事任务失败')
            this.openMessage('删除失败,请重试')
          }
          console.log(response)
        }).catch((response) => {
          console.log('删除农事任务失败')
          console.log(response)
          this.openMessage('服务异常')
        }).finally(() => {
          this.progressShowDelete = false
          this.deleteDisabled = false
        })
        this.dialogDelete = false
      },

      /**
       * 确认参与
       */
      submitJoin (status) {
        // 1. 显示对应模块 获取相应数据
        let param = {
          taskid: this.$route.query.id,
          userid: this.userid,
          taskparticsstatus: status
        }
        this.disabledSubmitJoin = true
        if (status === 'task-partics-status-partake') {
          this.progressShowSubmitJoin = true
        } else {
          this.progressShowRejectJoin = true
        }
        excuteApis(param, global.constant.farmApiUrls, 'task', 'confirmtask').then(response => {
          console.log('参与任务成功')
          console.log(response)
          if (response.data && response.data.flag === 1) {
            this.getTaskdDetail() // 刷新
          }
        }).catch((response) => {
        }).finally(() => {
          this.disabledSubmitJoin = false
          this.progressShowSubmitJoin = false
        })
      },

      /**
       * 取消提交任务
       */
      cancelTask () {
        console.log('取消提交任务')
        this.$router.go(-1)
      },

      /**
       * 提交任务
       * 约会类
       * 农事类
       */
      submitTask () {
        if (this.cost < 0) return
        let data = this.taskDetail.type === 'task-work' ? this.taskDetail.taskparcellist : null // 地块 约会地点选择
        let param = {
          content: this.taskDetail.describe, // 农事内容
          farmtypelist: null, // 农事类别 对应不了
          begintime: this.taskDetail.begintime, // 开始时间
          endtime: this.taskDetail.endtime, // 结束时间
          cost: this.cost, // 投入成本
          remark: this.remark, // 备注
          recordparcellist: data, // 地块列表 或者 约会地点
          recordpartuserlist: this.taskDetail.taskparticslist, // 参与人列表
          outputlist: this.outputlist, // 投入品记录列表
          resourcehistorylist: this.resourcehistorylist, // 附件列表
          sourcedictvalue: 'source-task' // 记录来源
        }

        this.disabledSubmitTask = true
        this.progressShowSubmitTask = true
        this.disabledSubmitTaskAndSave = true
        excuteApis(param, global.constant.farmApiUrls, 'record', 'add').then(response => {
          console.log('添加农事记录成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            let recordId = []
            for (let list of response.data.data) {
              recordId.push(list.id)
            } // ???
            this.recordId = recordId
            // 完成任务
            excuteApis({taskid: this.$route.query.id, recordeid: recordId.toString()}, global.constant.farmApiUrls, 'task', 'updateasfinish').then(response => {
              console.log('完成任务success')
              console.log(response)
              if (response.data.flag === 1) {
                this.getTaskdDetail() // 刷新
                this.getTaskDynamic() // 刷新任务动态
              }
            }).catch((response) => {
              console.log('完成任务fail')
              console.log(response)
            })
          }
        }).catch((response) => {
          console.log('添加农事记录fail')
          console.log(response)
        }).finally(() => {
          this.disabledSubmitTask = false
          this.progressShowSubmitTask = false
          this.disabledSubmitTaskAndSave = false
        })
      },

      /**
       * 保存到农事记录
       */
      saveToRecord (taskId, recordId, parcelids) {
        console.log('taskId = ' + taskId)
        console.log('recordId = ' + recordId)
        this.$router.push({name: 'farmerRecordsAdd', query: {taskId: taskId, recordId: recordId, parcelids: parcelids}})
      },

      /**
       * 提交并保存任务到农事记录
       */
      submitTaskAndSave () {
        console.log('提交并保存任务到农事记录')
        let data = this.taskDetail.type === 'task-work' ? this.taskDetail.taskparcellist : null // 地块 约会地点选择

        let param = {
          content: this.taskDetail.describe, // 农事内容
          farmtypelist: null, // 农事类别 对应不了
          begintime: this.taskDetail.begintime, // 开始时间
          endtime: this.taskDetail.endtime, // 结束时间
          cost: this.cost, // 投入成本
          remark: this.remark, // 备注
          recordparcellist: data, // 地块列表 或者 约会地点
          recordpartuserlist: this.taskDetail.taskparticslist, // 参与人列表
          outputlist: this.outputlist, // 投入品记录列表
          resourcehistorylist: this.resourcehistorylist, // 附件列表
          sourcedictvalue: 'source-task' // 记录来源
        }

        this.disabledSubmitTaskAndSave = true
        this.progressShowSubmitTaskAndSave = true
        this.disabledSubmitTask = true
        excuteApis(param, global.constant.farmApiUrls, 'record', 'add').then(response => {
          console.log('添加农事记录成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            let recordId = []
            let parcelids = [] // 地块id
            for (let i = 0; i < response.data.data.length; ++i) {
              recordId.push(response.data.data[i].id)
              parcelids.push(response.data.data[i].parcelid)
            }
            recordId = recordId.toString()
            parcelids = parcelids.toString()
            // 完成任务
            excuteApis({taskid: this.$route.query.id, recordeid: recordId}, global.constant.farmApiUrls, 'task', 'updateasfinish').then(response => {
              console.log('完成任务success')
              console.log(response)
              if (response.data.flag === 1) {
                this.saveToRecord(this.$route.query.id, recordId, parcelids) // 保存到农事记录 parcelidnames
              } else {
                this.openMessage('服务异常,请重试')
              }
            }).catch((response) => {
              console.log('完成任务fail')
              console.log(response)
              this.openMessage('服务异常,请重试')
            }).finally(() => {
              this.disabledSubmitTaskAndSave = false
              this.progressShowSubmitTaskAndSave = false
              this.disabledSubmitTask = false
            })
          } else {
            this.openMessage('服务异常,请重试')
          }
        }).catch((response) => {
          console.log('添加农事记录fail')
          console.log(response)
          this.disabledSubmitTaskAndSave = false
          this.progressShowSubmitTaskAndSave = false
          this.disabledSubmitTask = false
          this.openMessage('服务异常,请重试')
        })
      },

     /**
      * 添加投入品弹窗
      */
      clickAdd () {
        this.dialogInput = true // 打开弹窗
        this.clickNumber = 1 // 用1标识新增
        this.resetoutputForm() // 重置表单验证
        this.resetModalForm() // 重置表单数据
      },

      /**
       * 取消添加投入品
       */
      cancelAdd () {
        this.dialogInput = false
      },

      /**
       * 确认添加投入品
       */
      submitAdd () {
        this.valdateoutputForm()
        if (this.num < 0) return
        if (this.outputFormHasErrors) return  // 未通过 return
        this.solveRuleBug() // 解决bug用的
        let clickNumber = this.clickNumber // 当前点击的是新增还是编辑

        /**
         * 如果是从编辑过来并且投入品列表不为空. 添加投入品的id
         * 从 list 最后一个id开始
         */
        console.log('this.outputlist = ')
        console.log(!!this.outputlist)
        if (this.isEdit && this.outputlist.length > 0) {
          this.count = this.outputlist[this.outputlist.length - 1].id + 1
        }

        /* 一些处理 */
        // 获取typeList的id
        let typeArrId = -1 // typeList 的id
        for (let i = 0; i < this.typeList.length; ++i) {
          if (this.type === this.typeList[i].id) {
            typeArrId = i
            break
          }
        }
        // 获取numunitList的id
        let numunitArrId = -1 // numunitList 的id
        if (this.numunitList.length > 0 && this.numunit) {
          for (let i = 0; i < this.numunitList.length; ++i) {
            if (this.numunit === this.numunitList[i].id) {
              numunitArrId = i
              break
            }
          }
        }
        // 数据变更
        switch (clickNumber) {
          case 1:
            this.outputlist.push({
              name: this.name, // 投入品名称
              type: this.type, // 投入品类型
              typename: this.typeList[typeArrId].name, // 投入品类别text形式
              stand: this.stand, // 投入品规格
              num: this.num || '', // 投入品数量
              numunit: this.numunit || '', // 投入品数量单位
              numunitname: numunitArrId !== -1 ? this.numunitList[numunitArrId].name : '', // 数量单位text形式
              facturer: this.facturer || '', // 厂家
              id: this.count++ // 唯一标示当前对象的属性
            })
            break

          case 2:
            if (this.outputIndex !== -1) {
              this.outputlist[this.outputIndex].name = this.name
              this.outputlist[this.outputIndex].type = this.type
              this.outputlist[this.outputIndex].typename = this.typeList[typeArrId].name // 投入品text形式
              this.outputlist[this.outputIndex].stand = this.stand
              this.outputlist[this.outputIndex].num = this.num || ''
              this.outputlist[this.outputIndex].numunit = this.numunit || ''
              this.outputlist[this.outputIndex].numunitname = numunitArrId !== -1 ? this.numunitList[numunitArrId].name : '' // 数量单位text形式
              this.outputlist[this.outputIndex].facturer = this.facturer || ''
            }
            break
        }
        // 关闭弹窗
        this.dialogInput = false
        // 恢复校验规则
        this.resetRule()
      },

      /**
       * 表单已经验证通过 错误信息会闪现解决方法
       * 方法: 所有验证规则置空
       */
      solveRuleBug () {
        this.nameRules = []
        this.typeRules = []
        this.standRules = []
      },

      /**
       * 恢复验证规则
       */
      resetRule () {
        this.nameRules = [() => !!this.name || '请填写投入品名称']
        this.typeRules = [() => !!this.type || '请选择投入品类别']
        this.standRules = [() => !!this.stand || '请填写投入品规格']
      },

      /**
       * 删除投入品
       */
      deleteModalForm (id) {
        console.log('删除投入品')
        console.log('id = ' + id)
        this.removeByValue(this.outputlist, id)
      },

      /**
       * 编辑投入品
       */
      editModalForm (id) {
        // 获取当前id 所对应的数组下标
        let index = -1 // 当前id 所对应的数组下标
        for (let i = 0; i < this.outputlist.length; ++i) {
          if (this.outputlist[i].id === id) {
            index = i
            break
          }
        }
        this.clickNumber = 2 // 用2来标识编辑
        console.log('编辑投入品')
        console.log('id = ' + id)
        console.log('index = ' + index)
        this.name = this.outputlist[index].name
        this.type = this.outputlist[index].type
        this.stand = this.outputlist[index].stand
        this.num = this.outputlist[index].num
        this.numunit = this.outputlist[index].numunit
        this.facturer = this.outputlist[index].facturer
        this.dialogInput = true
        this.outputIndex = index // 当前编辑的是哪一个投入品
      },

      /**
       * 验证投入品表单
       */
      valdateoutputForm () {
        this.outputFormHasErrors = false // 表单是否有错误
        Object.keys(this.outputForm).forEach(f => {
          if (!this.outputForm[f]) this.outputFormHasErrors = true
          this.$refs[f].validate(true) // true 验证当前字段, false 不验证
        })
      },

      /**
       * 将表单重置到验证之前 resetform
       */
      resetoutputForm () {
        this.outputFormHasErrors = false
        Object.keys(this.outputForm).forEach(f => {
          this.$refs[f].reset()
        })
      },

      /**
       * 重置弹窗里的表单
       */
      resetModalForm () {
        this.name = null
        this.type = null // 下拉框
        this.stand = null
        this.num = null
        this.numunit = null // 下拉框
        this.facturer = null
      },

      /**
       * 根据元素属性删除数组
       */
      removeByValue (arr, val) {
        for (let i = 0; i < arr.length; ++i) {
          if (arr[i].id === val) {
            arr.splice(i, 1)
            break
          }
        }
      },

      /**
       * 文件添加到进来时
       * 为每个文件添加id唯一标识
       */
      addFiles (files) {
        for (let file of files) {
          file.fileId = this.fileId++
        }
      },

      /**
       * 上传文件成功
       */
      vsuccess (file, response) {
        if (response.flag === 1 && response.data && response.data[0]) {
          response.data[0].fileId = file.fileId
          this.resourcehistorylist.push(response.data[0])
        }
      },

      /**
       * 上传文件失败
       */
      verror (file, message) {
        console.log('文件上传失败')
        console.log(message)
        if (message === '请上传20M以内的文件') {
          this.openMessage('请上传20M以内的文件')
        } else {
          this.openMessage('服务异常,请稍后重试')
        }
        this.$refs.sendRecordDropzone.removeFile(file) // 移除文件
      },

      /**
       * 移除文件
       * 同时移除数据
       */
      removeFile (file) {
        for (let i = 0; i < this.resourcehistorylist.length; ++i) {
          if (this.resourcehistorylist[i].fileId === file.fileId) {
            this.resourcehistorylist.splice(i, 1)
            break
          }
        }
      },

      /**
       * 获取服务数据
       */
      getServiceData () {
        /* 获取投入品类别 */
        excuteApis({parentcode: 'agriculturaltype'}, global.constant.systemApis, 'dict', 'query').then(data => {
          if (data.data && data.data.data && data.data.flag === 1) {
            console.log('获取投入品类别成功')
            console.log(data.data.data)
            this.typeList = data.data.data
          }
        })

        /**
         * 获取投入品数量单位
         */
        excuteApis({parentcode: 'numberunit'}, global.constant.systemApis, 'dict', 'query').then(response => {
          console.log('获取数量单位成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.numunitList = response.data.data
          }
        })

        /**
         * 获取任务动态
         */
        this.getTaskDynamic()
      },

      /**
       * 我参与的任务中 状态为 确认参与
       * 获取农事记录
       * @param taskStatus 任务状态
       * @param recordId 记录id 有可能是数组字符串
       */
      getRecord (taskStatus, recordId) {
        if (taskStatus === 'task-status-finish' && parseInt(this.$route.query.select) === 1) {
          excuteApis({id: recordId.split(',')[0]}, global.constant.farmApiUrls, 'record', 'querybyid').then(response => {
            console.log('获取农事记录成功')
            console.log(response)
            if (response.data && response.data.data && response.data.flag === 1) {
              this.recordDetail = response.data.data
            }
          }).catch((response) => {
            console.log('获取农事记录失败')
            console.log(response)
          })
        }
      },

      /**
       * 获取多个农事记录 taskDetailList
       * @param object object
       */
      getRecordList (object) {
        excuteApis({id: object.recordeid || 0}, global.constant.farmApiUrls, 'record', 'querybyid').then(response => {
          if (response.data && response.data.data && response.data.flag === 1) {
            console.log('获取其他参与人的农事任务成功')
            console.log(response.data.data)
            let obj = response.data.data
            for (let key in obj) {
              if (obj.hasOwnProperty(key)) {
                object[key] = obj[key]
              }
            }
          }
          this.taskDetailList.push(object)
          console.log(this.taskDetailList)
        })
      },

      /**
       * 发送评论
       */
      submitComment () {
        // 非空验证
        if (this.content === null) {
          this.openMessage('请输入评论内容')
          return
        }
        let taskid = this.$route.query.id // 农事任务id
        let userid = this.userid // 用户id
        let content = this.content // 评论内容

        this.saveDisabled = true // 在服务响应之前禁用返回按钮
        this.progressShow = true // 显示转圈
        let param = {
          taskid: taskid,
          userid: userid,
          content: content
        }
        excuteApis(param, global.constant.farmApiUrls, 'task', 'addtaskcomment').then(response => {
          console.log('发送评论成功')
          console.log(response)
          if (response.data.flag === 1) {
            this.content = null // 清空输入框
            this.getTaskDynamic() // 刷新任务动态
          }
        }).catch((response) => {
          console.log('发送评论失败')
          console.log(response)
        }).finally(() => {
          this.saveDisabled = false // 服务已经响应 启用提交按钮
          this.progressShow = false // 停止转圈
        })
      },

      /**
       * 从参与人中找出我
       */
      getMyInfo (userid, array) {
        console.log('参与人列表:')
        console.log(array)
        for (let list of array) {
          if (list.userid === userid) {
            // 头像处理
            if (!list.userphotourl) list.userphotourl = 'static/peasant/moren.png'
            return list
          }
        }
        return { userphotourl: 'static/peasant/moren.png' }
      },

      /**
       * 获取任务动态
       */
      getTaskDynamic () {
        let param = { taskid: this.$route.query.id }
        excuteApis(param, global.constant.farmApiUrls, 'task', 'querytaskdynamic').then(response => {
          console.log('获取任务动态成功')
          console.log(response)
          if (response.data && response.data.data && response.data.flag === 1) {
            this.taskDynamicList = this.disposeTaskDynamicData(response.data.data) // 数据处理(头像没有时设置默认头像)
          } else {
            this.taskDynamicList = [] // 置空
          }
        })
      },

      /**
       * 处理任务动态的数据
       * @param array 包含对象的数组
       * @return 处理以后的数据
       */
      disposeTaskDynamicData (array) {
        if (array && array.length > 0) {
          let newArray = []
          for (let item of array) {
            if (!item.headportraiturl) item.headportraiturl = 'static/peasant/moren.png'
            newArray.push(item)
          }
          return newArray
        }
        return []
      },

      /**
       * 消息弹出框内容设置
       */
      openMessage (content) {
        this.messageResponse = content
        this.dialogMessage = true
      }
    },
    watch: {
      recordeid (recordid) {
        console.log('recordid改变,recordid = ' + recordid)
      }
    }
  }
</script>
<style scoped>
  .main{
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 30px;
    margin-bottom: 50px;
  }
  .wrapper{
    height: 48px;
    display: flex;
    align-items: center;
  }
  .back{
    margin-top: 15px;
  }
  .backIcon{
    margin-right: 30px;
    cursor: pointer;
  }
  .lineHeight-adjust{
    display: inline-block;
    margin-top: 15px;
  }
  .flexWrapper>.flex{
    margin-bottom: 15px;
  }
  .cutLine{
    height: 2px;
    width: 100%;
    margin-top: 10px;
    background-color: #CCC;
  }

  .executeTask-wrapper .layout{
    margin-bottom: 15px;
  }
  .stepWrapper{
    padding-left: 170px;
  }
  .myIcon{
    width: 40px;
    height: 40px;
    border: 1px solid #CCC;
    background-color: #CCC;
    border-radius: 50%;
    position: absolute;
    left: 17px;
    top: 24px;
    text-align: center;
    line-height: 40px;
  }
  .stepDo{
    margin-left: 40px;
  }
  .taskTime{
    height: 40px;
    position: absolute;
    right: 10%
  }
  .saveButton{
    margin-left: 40px;
  }
  .executeTask-wrapper{
    padding-left: 200px
  }
  .uploadFile-wrapper{
    border: 2px solid #E5E5E5;
  }
  .uploadFile-text{
    margin: 15px 0 15px 15px;
  }
  #dropzone{
    padding: 5px;
    border-width: 0;
  }
  .input-label{
    line-height: 50px;
  }
  .numunitWrapper{
    padding-left: 8px;
  }
  .numWrapper{
    padding-right: 8px;
  }
  .resource{
    margin-right: 20px;
  }
  .saveRecord-text{
    margin-left: 20px;
    color: #999;
  }
  .stepLeft>div{
    float: left;
  }
  .stepLeft{
    position: relative;
    border: 1px solid red;
    min-height: 20px;
  }
  .textFirst{
    padding-left: 20px;
    padding-top: 5px;
    padding-bottom: 5px;
  }
  .stepContent{
    padding-left: 20px;
    padding-top: 10px;
    padding-right: 200px;
  }
  .stepDo{
    margin-left: 50px;
    color: #999;
  }
  .line{
    width: 1px;
    height: 50px;
    margin-left: 30px;
    background-color: #CCC;
  }
  .taskDynamic{
    padding-bottom: 20px;
    padding-top: 20px;
  }
  .userInfo{
    margin-top: 20px;
  }
  .userInfo-username{
    margin-left: 10px;
  }
  .commentPhoto{
    margin-top: 15px;
  }
  .progressWrapper{
    position: absolute;
    left: 73px;
  }
  .progressShowSubmitTaskAndSave{
    position: absolute;
    left: 170px;
  }
  .btnSize{
    width: 107px;
  }
  .saveRecord-Wrapper{
    display: inline-block;
  }
  .containerWrapper{
    padding: 0;
    margin-bottom: 50px;
  }
  .wrapper{
    display: flex;
  }
  .wrapper>.btn{
    margin-top: -10px;
    color: white;
  }
  .creattime{
    float: right;
    padding-right: 200px;
  }
  .divLine-wrapper{
    height: 100%;
  }
  .divLine{
    width: 1px;
    height: 100%;
    margin-left: 30px;
    background-color: #CCC;
  }
  .contentWrapper{
    padding-bottom: 50px;
    min-height: 150px;
  }
  #putKind{
    margin-bottom: 0;
  }
  .outputWrapper{
    margin-top: 13px;
  }
  #addButton{
    margin-top:3px;
  }
  .labelColor{
    color: rgba(0,0,0,0.54);
  }
  .btnPadding{
    padding-bottom: 30px;
    padding-right: 20px;
  }
  .textAreaPadding{
    padding: 0;
  }
  audio{
    margin-right: 20px;
  }
  .taskContent-wrapper{
    margin-top: -13px;
  }
  .submitTaskAndSave{
    min-width: 200px;
  }
  .btnSizeSubmitJoin{
    min-width: 120px;
  }
  .progressWrapperSubmitJoin{
    position: absolute;
    left: 90px;
  }
  .typenameWrapper{
    min-height: 50px;
  }
  .btnColor{
    color: #FFF;

  }
  .listCut{
    width: 1000%;
    height: 1px;
    background-color: #CCC;
    margin-bottom: 30px;
    margin-left: -500px;
  }
  .hideoverflow {
    word-wrap: break-word;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .labelMargin{
    margin-top: 23px;
  }
  .statusColor{
    color: #999;
  }
</style>
