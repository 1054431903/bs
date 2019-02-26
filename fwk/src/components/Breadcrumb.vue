<template>
  <div class="py-2">
    <v-breadcrumbs style="justify-content:left">
      <v-icon slot="divider">chevron_right</v-icon>
      <v-breadcrumbs-item v-for="item in levelList"
                          :to="item.path"
                          :key="item.name"
                          :disabled="false"
                          style="color:#676767;">
        {{ item.name }}
      </v-breadcrumbs-item>
    </v-breadcrumbs>
  </div>
</template>

<script>
export default {
  created () {
    this.getBreadcrumb()
  },
  data () {
    return {
      levelList: null
    }
  },
  watch: {
    $route () {
      this.getBreadcrumb()
    }
  },
  methods: {
    removeByValue (arr, val) {
      for (let i = 0; i < arr.length; i++) {
        if (arr[i] === val) {
          arr.splice(i, 1)
          break
        }
      }
      return arr
    },
    getBreadcrumb () {
      let breadList = []
      let newBread = {}
      // let storeBreadList = this.$store.state.breadListState
      let storeBreadList = global.helper.ls.get('breadListState')
      if (storeBreadList) {
        breadList = storeBreadList
      }
      let bread = this.$route.matched.filter(item => item.meta.name)
      newBread.path = bread[0].path
      newBread.name = bread[0].meta.name
      newBread.breadNumber = bread[0].meta.breadNumber
      if (breadList.length > 0) {
        if (newBread.breadNumber === 1 || newBread.breadNumber === 'undefined') {
          breadList = []
        } else {
          breadList.forEach(element => {
            if (element.name === newBread.name) {
              breadList = this.removeByValue(breadList, element)
            } else {
              if (element.breadNumber === 2 || element.breadNumber === 3) {
                breadList = this.removeByValue(breadList, element)
              }
            }
          })
        }
      }
      breadList.push(newBread)
      this.$store.commit('setBreadList', breadList)
      this.levelList = global.helper.ls.get('breadListState')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
