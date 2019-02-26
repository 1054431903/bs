<template lang="pug">
  v-modaldialog(
    :isShow.sync="viewPlantDialog",
    :title="viewPlantDialogTitle",
    :modalMaxWidth="800",
    :bodyHeight="450",
    :showCloseBtn="true")
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 种植批次:
      v-flex(xs9 text-xs-left)
        v-subheader {{ model.batch || '--' }}
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 播/栽种时间:
      v-flex(xs9)
        v-subheader {{ model.begintime ? model.begintime.substr(0,10) : '--' }}  
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 预计生长周期:
      v-flex(xs9)
        v-subheader {{ model.peroid || '--' }} {{ model.peroidunit || '' }}
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 株龄:
      v-flex(xs9)
        v-subheader {{ model.age || '--' }} {{ model.ageunit || '' }}
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 种植面积:
      v-flex(xs9)
        v-subheader  {{ model.area || '--'}} 亩
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 植株数量:
      v-flex(xs9)
        v-subheader {{ model.number || '--' }}  {{ model.numberunit || '' }} 
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 产量信息:
    v-layout(class="row")
      v-flex(xs12)
        v-data-table(:headers='productcolumns' :items='model.products' hide-actions no-data-text = "暂无数据")
          template(slot='items', slot-scope='props')
            tr
              td(class="text-xs-left hideoverflow") {{ props.item.year ? props.item.year : '--' }}
              td(class="text-xs-left hideoverflow") {{ props.item.output ? props.item.output : '--' }}
              td(class="text-xs-left hideoverflow") {{ props.item.levelname ? props.item.levelname : '--' }}
              td(class="text-xs-left hideoverflow") {{ showPreoutput(props.item.preoutput, props.item.preoutputunitname, props.item.category)}}
              td(class="text-xs-left hideoverflow") {{ props.item.preland ? (props.item.preland + ' ' + props.item.prelandunitname) : '--' }}
              td(class="text-xs-left hideoverflow") {{ props.item.actoutput ? (props.item.actoutput  + ' ' + props.item.actoutputunitname) : '--' }}
              td(class="text-xs-center hideoverflow")
                v-btn(fab small icon title="查看" @click="viewProductive(props.item.id)")
                  v-icon remove_red_eye
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 附件:
      v-flex(xs9 v-if="!model['attachments'] || model.attachments.length === 0")
        v-subheader 无
    v-layout(row v-if="model['attachments'] && model.attachments.length > 0")
      v-flex(xs12)
        span.px-3(v-for="attachment in model.attachments" :key="attachment.id")
          a(:href="attachment.resourcepath" target="_blank") {{attachment.resourcename}}               
    v-layout(class="row")
      v-flex(xs3)
        v-subheader 备注:
      v-flex(xs9).py-3
        span(class="notes") {{ model.introduce || '--' }}
    v-layout(row)
      v-productiveview(:curplant="model" :productiveid="productiveid" :viewDialog.sync="viewProductiveDialog")      
</template>

<script>
import Plant from './plant.js'
import VProductiveview from './productive/ProductiveView.vue'
export default {
  name: 'v-plantview',
  mixins: [Plant],
  methods: {
    // 点击查看产量信息触发
    viewProductive (productiveid) {
      this.productiveid = productiveid
      this.viewProductiveDialog = true
    }
  },
  components: {
    VProductiveview
  }
}
</script>

<style scoped>
.hideoverflow {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.notes {
  color: rgba(0, 0, 0, 0.54);
}
</style>
