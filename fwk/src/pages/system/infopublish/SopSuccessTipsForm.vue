<template lang="pug">
div.pa-2
  v-flex(xs12 sm12 md12 lg12)
    v-card
      v-container(fluid)
        v-layout(row)
          div(class="tips") SOP规程发布完成，&nbsp;&nbsp;
            v-btn(flat color="primary" class="btntips" :to="{name: 'sopview', query: {id: id}}") 点击查看
</template>
<script>
export default {
  data () {
    return {
      id: null
    }
  },
  watch: {},
  methods: {},
  computed: {},
  created () {
    this.id = this.$route.query.id
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
