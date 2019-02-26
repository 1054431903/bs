<template>
  <v-card class="page-content">
    <div class="alert" @click="close" transition="scale-transition" origin="center center" v-if="showAlert">
      <div class="wrap" @click="show">
        <div class="body">
          {{msg}}
        </div>
        <div class="foot">
            <v-btn id="save" @click="save">确定</v-btn>
            <v-btn id="cancel" @click="close">取消</v-btn>
        </div>
      </div>
    </div>
  </v-card>
</template>
<script>
  export default {
    name: 'sort-delete',
    data () {
      return {
        showAlert: true,
        msg: '信息删除后将不可恢复，确认删除吗？'
      }
    },
    methods: {
      show (event) {
        this.showAlert = true
        // 阻止点击冒泡
        event.stopPropagation()
      },
      close (event) {
        this.showAlert = false
        event.stopPropagation()
      }
    }
  }

</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang='scss'>
  .alert {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0,0,0, 0.4);
    z-index: 1000;
    transition: all .3s ease-in-out;
  }
  .wrap {
    position: absolute;
    z-index: 1002;
    min-width: 400px;
    background: #fff;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    border-radius: 4px;
    padding-bottom: 20px;
  }
  .head {
    height: 40px;
    line-height: 40px;
    border-bottom: 1px solid #dedede;
    padding-left: 10px;
    color: #333;
  }
  .body {
    padding: 40px 20px;
    text-align: center;
    color: #000 ;
  }
  .foot {
    height: 50px;
    text-align: center;
    button {
      margin-right: 20px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
  #save {
    background-color: #B2AFB3;
    color: #fff;
  }
  #cancel {
    background-color: #666666;
    color: #fff;
  }
</style>
