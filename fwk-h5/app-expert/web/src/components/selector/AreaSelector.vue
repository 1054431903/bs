<template>
  <div class="_selector">
    <div class="_navi">
      <v-breadcrumbs :data="stack" @itemClick="breadcrumbsItemClick"></v-breadcrumbs>
    </div>
    <div class="_list">
      <div class="_item" v-for="(item,index) in shows" :key="index" @click="itemClick(item)">
        <div class="_separator">&nbsp;</div>
        <div class="_content">{{formatter ? formatter(item, index, stack.length) : item}}</div>
      </div>
    </div>
  </div>

</template>

<script>
import Share from '@/share'
import VBreadcrumbs from '@/components/Breadcrumbs'
const EVENT_CONFIRM = 'confirm'
export default {
  name: 'v-area-selector',
  mixins: [Share],
  components: {
    VBreadcrumbs
  },
  props: {
    formatter: {
      type: Function
    },
    data: {
      type: Array,
      default () {
        return []
      }
    }
  },
  data () {
    return {
      shows: [],
      stack: []
    }
  },
  computed: {
    item () {
      return this.stack.length > 0 ? this.stack[this.stack.length - 1] : {items: this.data}
    }
  },
  watch: {
    item (value) {
      this.refresh()
    }
  },
  mounted () {
    this.refresh()
  },
  methods: {
    async back () {
      this.stack.pop()
    },
    breadcrumbsItemClick (item, index) {
      this.stack = this.stack.filter((_, i) => i <= index)
      this.shows = item.items
    },
    async itemClick (item) {
      this.stack.push(item)
    },
    async refresh () {
      let item = this.item
      if (Array.isArray(item.items)) {
        this.shows = item.items
      }
      if (typeof item.items === 'function') {
        this.shows = await item.items(item)
      }
      if (!Array.isArray(this.shows) || this.shows.length <= 0) {
        this.$emit(EVENT_CONFIRM, this.stack)
        this.stack = []
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  ._selector {
    width: 100%;

    ._navi {
      width: 100%;
      overflow: hidden;
      padding: 0 10px;
    }

    ._list {
      width: 100%;
    }

    ._item {

      ._separator {
        height: 1px;
        background-color:#ebebeb;
      }

      ._content {
        height: 44px;
        line-height: 44px;
        padding: 0 20px;
      }
    }
  }
</style>
