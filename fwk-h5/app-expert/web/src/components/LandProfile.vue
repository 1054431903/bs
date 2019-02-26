<template>
    <div id="landarea" class="landcontainer">
        <div v-if="lanlngs && lanlngs.length > 2"  class="landarea" >
            <svg version="1.2" baseProfile="tiny" class="svg" :height="size" :width="size">
              <g>
                <path :stroke="strokeColor" stroke-width="2" :fill="fillColor" :d="path"></path>
              </g>
            </svg>
        </div>
        <div row v-else>
          无地形图
        </div>
    </div>
</template>

<script>
import { latLngToCoordi } from '../tools/utils'
export default {
  name: 'v-land-profile',
  props: {
    strokeColor: {
      type: String,
      default: 'rgb(103,197,234)'
    },
    fillColor: {
      type: String,
      default: 'rgba(103,197,234,0.6)'
    },
    lanlngs: {
      type: [Boolean, Array],
      default: false
    },
    size: {
      type: [Boolean, Number],
      default: false
    }
  },
  data () {
    return {}
  },
  computed: {
    path () {
      return latLngToCoordi(this.lanlngs, this.size)
    }
  },
  methods: {},
  created () {}
}
</script>

<style scoped>
</style>
