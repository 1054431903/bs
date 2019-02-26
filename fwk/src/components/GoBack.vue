<template>
  <v-layout row wrap>
    <v-flex xs12>
      <v-btn icon @click="back" title="点击返回">
        <v-icon class="backWrapper" color="primary">chevron_left</v-icon>
      </v-btn>
      <span class="text" v-text="backName"></span>
      <slot></slot>
    </v-flex>
    <v-flex class="mt-2" xs12><v-divider></v-divider></v-flex>
  </v-layout>
</template>

<script>
  export default {
    name: 'go-back',
    data () {
      return {
        backName: ''
      }
    },
    props: {
      name: {
        type: String,
        default: ''
      }
    },
    watch: {
      name (v) {
        this.backName = v
      }
    },
    methods: {
      back () {
        this.$router.go(-1)
      }
    },
    mounted () {
      this.backName = this.name
    },
    create () {}
  }
</script>
<style scoped>
.text {
  vertical-align: middle;
}
.btn {
  margin: 0px 0px
}
</style>
