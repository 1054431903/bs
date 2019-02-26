<template>
  <div class="text-xs-center">
    <v-modaldialog :isShow="examinationTaskDialog"
                   :title="examinationTaskDialogTitle"
                   :modalMaxWidth="600"
                   :bodyHeight="250">
      <v-layout row
                wrap>
        <v-flex xs12>
          <div style="color:rgba(0,0,0,.57);font-size:14px;">好评度</div>
        </v-flex>
      </v-layout>
      <v-layout row
                nowrap
                py-1>
        <v-flex xs3>
          <div class="star"
               @click="setCurstar(1)">
            <v-icon :color="existsStar(1) !== -1 ? 'red' : ''">{{existsStar(1) !== -1 ? 'star' : 'star_border' }}</v-icon>
          </div>
          <div class="star"
               @click="setCurstar(2)">
            <v-icon :color="existsStar(2) !== -1 ? 'red' : ''">{{existsStar(2) !== -1 ? 'star' : 'star_border' }}</v-icon>
          </div>
          <div class="star"
               @click="setCurstar(3)">
            <v-icon :color="existsStar(3) !== -1 ? 'red' : ''">{{existsStar(3) !== -1 ? 'star' : 'star_border' }}</v-icon>
          </div>
          <div class="star"
               @click="setCurstar(4)">
            <v-icon :color="existsStar(4) !== -1 ? 'red' : ''">{{existsStar(4) !== -1 ? 'star' : 'star_border' }}</v-icon>
          </div>
          <div class="star"
               @click="setCurstar(5)">
            <v-icon :color="existsStar(5) !== -1 ? 'red' : ''">{{existsStar(5) !== -1 ? 'star' : 'star_border' }}</v-icon>
          </div>
        </v-flex>
        <v-flex xs6>
          <div :class="{remark: curStar > 0, remarkError: curStar === 0}">
            {{ setRemark() }}
          </div>
        </v-flex>
      </v-layout>
      <v-layout row
                wrap
                py-1>
        <v-flex xs12>
          <v-textarea label="评语"
                      v-model="comment"
                      counter="500"
                      maxlength="500"></v-textarea>
        </v-flex>
      </v-layout>
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
  </div>
</template>

<script>
import mongo from '@vehiclecloud/mongo-rest-client'

export default {
  name: 'v-farmertasksexamination',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    curtask: {
      type: Object,
      default: () => { }
    },
    refreshFn: {
      type: Function,
      default: () => { }
    }
  },
  data () {
    return {
      examinationTaskDialog: false,
      examinationTaskDialogTitle: '任务考核',
      curStar: null,
      curStars: [],
      comment: null,
      user: null
    }
  },
  watch: {
    visible (v) {
      this.examinationTaskDialog = v
      if (!v) {
        this.curStar = null
        this.curStars = []
        this.comment = null
      }
    },
    examinationTaskDialog (v) {
      this.$emit('update:visible', v)
    },
    examinationTaskDialogTitle (v) {
      this.examinationTaskDialogTitle = v
    }
  },
  methods: {
    save () {
      if (!this.curStar) {
        this.curStar = 0
        this.setRemark()
        return
      }
      let curTime = new Date().getTime()
      let filter = { _id: { $oid: this.curtask.id } }
      let column = {}
      column.stage = 'completion'
      column.finish = true
      column.finishOn = curTime
      column.examination = true
      column.examinationOn = curTime
      column.examinationContent = {}
      column.examinationContent.evaluation = this.curStar || 0
      column.examinationContent.content = this.comment || ''
      let update = { $set: column }
      mongo.db().collection('task').update({ filter: filter, update: update }).then(response => {
        if (!response.writeError && !response.writeConcernError) {
          this.user = global.helper.ls.get('user')
          let trends = Object.assign({}, { task: { $oid: this.curtask.id }, type: 'examination', content: this.comment, evaluation: this.curStar || 0, creator: { $oid: this.user.supportid } })
          mongo.db().collection('task.trends').insert(trends).then(response => {
            // console.log('to do task.trends successful.')
            this.examinationTaskDialog = false
            this.refreshFn()
          })
        }
      }).catch((err) => {
        console.log(err)
      })
    },
    cancel () {
      this.examinationTaskDialog = false
    },
    setCurstar (star) {
      this.curStar = star
      this.curStars = []
      let i = star
      while (i > 0) {
        this.curStars.push(i)
        i--
      }
    },
    existsStar (star) {
      return this.curStars.indexOf(star)
    },
    setRemark () {
      switch (this.curStar) {
        case 5:
          return '好'
        case 4:
        case 3:
          return '中'
        case 2:
        case 1:
          return '差'
        case 0:
          return '请选择任务好评度！'
        default:
          return ''
      }
    }
  }
}
</script>
<style scoped>
.star {
  cursor: pointer;
  display: inline;
}
.remark {
  line-height: 24px;
  margin-left: 10px;
}
.remarkError {
  line-height: 24px;
  margin-left: 10px;
  color: red;
}
</style>

