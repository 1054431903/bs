<template lang="pug">
div.pa-2
  v-flex(xs12 sm12 md12 lg12)
    v-card
      v-container(fluid)
        v-layout(row)
          div(v-if="opflag === 0" class="tips") 合作农户信息添加完成，
            v-btn(flat color="primary" class="btntips" :to="{name: 'viewfarmer', query: {id: id}}") 点击查看
          div(v-else class="tips") 合作农户信息修改完成，
            v-btn(flat color="primary" class="btntips" :to="{name: 'viewfarmer', query: {id: id}}") 点击查看
</template>
<script>
export default {
  data () {
    return {
      id: null,
      opflag: 0
    }
  },
  watch: {},
  methods: {},
  computed: {},
  created () {
    this.id = this.$route.query.id
    this.opflag = this.$route.query.opflag
  }
}
</script>
<style scoped>
.tips {
  padding: 180px;
  margin-left: auto; 
  margin-right: auto;
}
.btntips {
  left: -20px;
}
</style>
