<template>
  <div>
    水体检测{{$route.params._id}}
  </div>
</template>

<script>
import Share from './share'
export default {
  name: 'iot-device-water',
  mixins: [Share],
  data () {
    return {}
  }
}
</script>
