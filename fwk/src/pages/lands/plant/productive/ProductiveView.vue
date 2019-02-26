<template lang="pug">
  v-modaldialog(
    :isShow.sync="viewProductiveDialog",
    title="查看产量信息详情",
    :modalMaxWidth="680",
    :bodyHeight="400",
    :showCloseBtn="true")
    v-layout(class="row")
      v-flex(xs3)
          v-subheader 产出年份:
      v-flex(xs9 text-xs-left)
          v-subheader {{ model.year || '--' }}
    v-layout(class="row")
      v-flex(xs3)
          v-subheader 产出物:
      v-flex(xs9)
          v-subheader {{ model.output || '--'}}
    v-layout(class="row")
      v-flex(xs3)
          v-subheader 产出物等级:
      v-flex(xs9)
          v-subheader {{ model.levelname || '--' }}
    v-layout(class="row")
      v-flex(xs3)
          v-subheader {{model.categoryname || '预估单产产量'}}：
      v-flex(xs9)
          v-subheader {{ model.preoutput || '--' }} {{ model.preoutputunitname || ''}} 
    v-layout(class="row")
      v-flex(xs3)
          v-subheader 预估地块产量:
      v-flex(xs9)
          v-subheader {{ model.preland || '--' }} {{ model.prelandunitname || '' }} 
    v-layout(class="row")
      v-flex(xs3)
          v-subheader 实际产量:
      v-flex(xs9)
          v-subheader {{ model.actoutput || '--' }} {{ model.actoutputunitname || '' }}
</template>

<script>
import Productive from './productive.js'
export default {
  name: 'v-productiveview',
  mixins: [Productive]
}

</script>
