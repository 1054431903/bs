<template>
    <div id="landarea" class="landcontainer">
        <div v-if="lanlngs && lanlngs.length > 2"  class="landarea" >
            <svg version="1.2" baseProfile="tiny" class="svg">
              <g>                
                <path stroke="rgb(103,197,234)" stroke-width="2" fill="rgba(103,197,234,0.6)" :d="path"></path>
              </g>
            </svg>
        </div>
        <div row v-else>
          无地形图
        </div>
    </div>
</template>

<script>
import { latLngToCoordi } from '@/utils'
export default {
  props: {
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
  .svg {
    width: 100%;
    height: 100%;
  }
  .landarea {
    margin-top: 15px;
    text-align: center;
  }
</style>
