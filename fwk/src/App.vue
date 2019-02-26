<template>
  <div>
    <transition mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
// import OAuth2 from '@vehiclecloud/oauth2-client'
export default {
  data () {
    return {

    }
  },
  methods: { },
  created () {
    this.$store.dispatch('init', { collection: 'tenant.staff' })
    this.$store.dispatch('init', { collection: 'sys.tenant' })
    this.$store.dispatch('init', { collection: 'base.device.type' })
    this.$store.dispatch('init', { collection: 'base.sensor.type' })
    this.$store.dispatch('init', { collection: 'tenant.farmland' })
  }
}
</script>
