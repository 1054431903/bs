<template>
  <div class="_breadcrumbs">
    <div class="_scroll" ref="scroll">
      <div class="_items" ref="items">
        <div class="_item" v-for="(item, index) in data" :key="index">
          <span :style="{color:index !== data.length - 1 ? '#02ad58' : 'gray'}"
                @click="itemClick(item,index)"
          >
            {{item.name}}
          </span>
          <span v-if="index !== data.length - 1">
            /
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const EVENT_ITEM_CLICK = 'itemClick'
export default {
  name: 'v-breadcrumbs',
  props: {
    data: {
      type: Array,
      required: true,
      default () {
        return []
      }
    }
  },
  watch: {
    data () {
    }
  },
  mounted () {
  },
  methods: {
    itemClick (item, index) {
      console.debug('itemClick')
      this.$emit(EVENT_ITEM_CLICK, item, index)
    }
  }
}
</script>

<style lang="scss" scoped>
  ._breadcrumbs {
    width: 100%;
    height: 38px;
    line-height: 38px;
    display: flex;
    flex-flow: row nowrap;
    ._scroll {
      flex: auto;
      overflow-x: scroll;
      overflow-y: hidden;
      ._items {
        white-space: nowrap;
        ._item {
          display: inline-block;
          span {
            margin: auto 2px;
          }
        }
      }
    }
    ._scroll::-webkit-scrollbar {
      width: 0px;
      height: 0px;
    }
  }
</style>
