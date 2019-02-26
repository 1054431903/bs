<template>
  <div>
    控制设备{{$route.params._id}}
  </div>
</template>

<script>
import Share from './share'
export default {
  name: 'iot-device-controller',
  mixins: [Share],
  data () {
    return {}
  }
}
</script>
