<template>
  <v-app id="app">
    <v-toolbar  v-if="inited" class="theme" :class="{'theme-1': runMode === 1}" xs12 app flat height="44px">
      <v-flex xs2 style="margin-left:10px;height:20px"  @click="historyBack">
        <!-- <v-btn style="margin-left:0" class="toobar-text" :class="{'toobar-text-1':runMode === 1}" xs3 icon @click="historyBack"> -->
          <!-- <v-icon left color="#666666">keyboard_arrow_left</v-icon> -->
          <img style="width:11px;height:20px" src="@/assets/back.png">
          <!-- <span>返回</span> -->
        <!-- </v-btn> -->
      </v-flex>
      <v-flex xs8>
        <v-spacer>
          <v-toolbar-title style="color:#000;width:100%;margin-left:0;font-size:18px;text-align:center;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;display:block">{{app.title}}</v-toolbar-title>
        </v-spacer>
      </v-flex>
      <v-flex justify-end icon xs2 style="margin-right:10px" :class="{'toobar-text-1':runMode === 1}" v-for="(act, ai) in app.acts" v-if="app.btnType" :key="ai" @click="act.fn()">
        <!-- <v-btn depressed class="toobar-text" :class="{'toobar-text-1':runMode === 1}" icon v-for="(act, ai) in app.acts" style="width:auto;margin-right:0" v-if="app.btnType" :key="ai" @click.native.stop="act.fn()"> -->
          <v-icon v-if="act.icon" color="grey darken-2">{{ act.icon }}</v-icon>
          <img v-else-if="act.src" :src="act.src" :width="act.w" :height="act.h">
          <span style="font-size: 14px" v-else>{{act.text}}</span>
        <!-- </v-btn> -->
      </v-flex>
    </v-toolbar>
    <v-content v-if="inited" style="background-color:#fff">
      <lg-preview></lg-preview>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import Share from '@/share'
import mongo from '@vehiclecloud/mongo-rest-client'
import helper from '@/tools/helper'
export default {
  name: 'app',
  mixins: [Share],
  data () {
    return {
      inited: false
    }
  },
  computed: {
    app () {
      return this.$store.state.app
    }
  },
  created () {
    this.updateTitleByRoute()
    this.load()
  },
  watch: {
    '$route' () {
      var _this = this
      this.updateTitleByRoute(_this)
    }
  },
  methods: {
    updateTitleByRoute (_this) {
      this.$store.commit('set', { name: 'app', k: 'title', v: this.$route.meta.title })
      if (this.$route.name === 'public-weather' || this.$route.name === 'bloomsky') {
        this.$store.commit('set', { name: 'app', k: 'btnType', v: true })
        let btnObj = [{ icon: 'swap_horiz', fn () { _this.$router.push({name: 'weather-switch'}) } }]
        this.$store.commit('set', { name: 'app', k: 'acts', v: btnObj })
      } else {
        this.$store.commit('set', { name: 'app', k: 'btnType', v: false })
      }
    },
    historyBack () {
      if (this.$route.path !== '/') {
        if (this.$route.path === '/public/weather' || this.$route.path === '/bloomsky') {
          this.$router.push('/')
        } else {
          this.$router.go(-1)
        }
      } else {
        // TODO: back to app
        if (this.$route.path === '/') {
          if (window.native && window.native.back) {
            window.native.back()
          }
        }
      }
    },
    async load () {
      Promise.all([
        this.loadStaff(),
        this.loadTenant(),
        this.loadFarmland()
      ]).then(this.loadParams).then(this.saveParams).then(this.loaded)
    },
    async loadStaff () {
      let staff = this.$store.state['tenant.staff'].item
      if (staff) return
      await mongo.db('iss').collection('tenant.staff').first({ filter: { _id: { $oid: this.$route.query['staff._id'] } } }).then(staff => {
        if (!staff) return
        this.$store.commit('set', { name: 'tenant.staff', k: 'item', v: staff })
      })
    },
    async loadTenant () {
      let tenant = this.$store.state['sys.tenant'].item
      if (tenant) return
      await mongo.db('iss').collection('sys.tenant').first({ filter: { _id: { $oid: this.$route.query['tenant._id'] } } }).then(tenant => {
        if (!tenant) return
        this.$store.commit('set', { name: 'sys.tenant', k: 'item', v: tenant })
      })
    },
    async loadFarmland () {
      let farmland = this.$store.state['tenant.farmland'].item
      if (farmland) return
      await mongo.db('iss').collection('tenant.farmland').first({ filter: { _id: { $oid: this.$route.query._id } } }).then(farmland => {
        if (!farmland) return
        this.$store.commit('set', { name: 'tenant.farmland', k: 'item', v: farmland })
      })
    },
    async loadParams () {
      let finished = this.$store.state.init.finished
      if (finished) return

      // 获取查询参数
      let runMode = Number(this.$route.query['mode'])
      if (runMode) {
        this.$store.commit('set', { name: 'init', k: 'runMode', v: runMode })
      }
      let issStaffId = this.$route.query['staff._id']
      if (issStaffId) {
        this.$store.commit('set', { name: 'init', k: 'issStaffId', v: issStaffId })
      }
      let issTenantId = this.$route.query['tenant._id']
      if (issTenantId) {
        this.$store.commit('set', { name: 'init', k: 'issTenantId', v: issTenantId })
      }
      let issFarmlandId = this.$route.query['_id']
      if (issFarmlandId) {
        this.$store.commit('set', { name: 'init', k: 'issFarmlandId', v: issFarmlandId })
      }
      // 获取环境参数
      let parms
      if (window.native && window.native.getParams) {
        try {
          parms = window.native.getParams()
          if (typeof parms === 'string') {
            parms = JSON.parse(parms)
          }
        } catch (e) {
          console.debug(`加载环境参数失败:${e.message}`)
          parms = {}
        }
      }
      parms = parms || {}
      // 获取农事用户信息
      let fwkUser = parms.fwkUser || parms.userInfo || parms.user
      let fwkUserId
      if (fwkUser) {
        fwkUserId = fwkUser.id
        if (!fwkUser.reduced) {
          this.$store.commit('set', { name: 'fwkUser', v: fwkUser })
        }
      } else {
        fwkUserId = this.$route.query['fwk.user.id']
        if (fwkUserId) {
          fwkUser = undefined
        } else {
          fwkUser = await this.queryFwkUser({reload: true})
          fwkUserId = fwkUser.id
        }
      }
      if (fwkUserId) {
        this.$store.commit('set', { name: 'init', k: 'fwkUserId', v: fwkUserId })
      }
      // 获取农事地块信息
      let fwkParcel = parms.fwkParcel || parms.parcelInfo || parms.parcel
      let fwkParcelId
      if (fwkParcel) {
        fwkParcelId = fwkParcel.id
        if (!fwkParcel.reduced) {
          this.$store.commit('set', { name: 'fwkParcel', v: fwkParcel })
        }
      } else {
        fwkParcelId = this.$route.query['fwk.parcel.id']
        if (fwkParcelId) {
          fwkParcel = undefined
        } else {
          fwkParcel = await this.queryFwkParcel({reload: true})
          fwkParcelId = fwkParcel.id
        }
      }
      if (fwkParcelId) {
        this.$store.commit('set', { name: 'init', k: 'fwkParcelId', v: fwkParcelId })
      }
      this.$store.commit('set', { name: 'init', k: 'finished', v: true })
    },
    async loaded () {
      this.inited = true
      if (window.native && window.native.onLoaded) {
        window.native.onLoaded()
      }
    },
    async saveParams () {
      let staff = await this.queryStaff()
      let tenant = await this.queryTenant()
      let farmland = await this.queryFarmland()
      let fwkUserId = await this.queryFwkUserId()
      let fwkParcelId = await this.queryFwkParcelId()

      // 缓存用户信息
      let mode = this.$route.query['mode']
      let landid = farmland._id.$oid
      let loginerid = staff._id.$oid
      let orgid = tenant._id.$oid
      let loginorgid = tenant._id.$oid
      let operateid = fwkUserId
      let operateredittime = staff.updatedOn.$numberLong
      let parcelid = fwkParcelId

      this.$store.commit('set', {name: 'queryParams', k: 'orgid', v: orgid})
      this.$store.commit('set', {name: 'queryParams', k: 'loginorgid', v: loginorgid})
      this.$store.commit('set', {name: 'queryParams', k: 'operateid', v: operateid})
      this.$store.commit('set', {name: 'queryParams', k: 'loginerid', v: loginerid})
      this.$store.commit('set', {name: 'queryParams', k: 'operateredittime', v: operateredittime})
      this.$store.commit('set', {name: 'queryParams', k: 'landid', v: landid})
      this.$store.commit('set', {name: 'queryParams', k: 'parcelid', v: parcelid})
      this.$store.commit('set', {name: 'queryParams', k: 'mode', v: mode})
      let user = this.$store.state['queryParams']
      helper.ls.set('user', user)
    }
  }
}
</script>
