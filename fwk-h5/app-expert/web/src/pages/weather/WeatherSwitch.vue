<template>
  <div>
    <v-list>
      <v-list-tile :to="{ name: 'public-weather', query: {lon: this.$store.state.app.lon, lat: this.$store.state.app.lat} }"
                   @click="setFlag(0, '9527', '公共天气')"
                   style="height:50px;">
        <v-list-tile-content style="height:50px;border:0.5px rgb(224,224,224);border-bottom-style:solid">
          <v-list-tile-sub-title style="height:50px;font-size:15px;line-height:50px;color:rgb(62,62,62)">公共天气</v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>
      <!-- <v-divider light></v-divider> -->
      <v-list-tile style="height:50px"
                   :to="{ name: 'bloomsky', query: {id: item._id.$oid} }"
                   @click="setFlag(1, item._id.$oid, item.name)"
                   v-for="(item, index) in bloomskyDevList"
                   :key="index">
        <v-list-tile-content style="height:50px;border:0.5px rgb(224,224,224);border-bottom-style:solid">
          <v-list-tile-sub-title style="height:50px;font-size:15px;line-height:50px;color:rgb(62,62,62)">{{item.name}}</v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </div>
</template>
<script>
import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  name: 'weather-switch',
  data () {
    return {
      bloomskyDevList: []
    }
  },
  created () {
    let landid = this.$store.state.app.landid
    if (landid) {
      this.init(landid).then(res => {
        this.bloomskyDevList = res
      })
    }
  },
  methods: {
    async init (id) {
      console.log(id)
      let data = await mongo.db('iot').collection('tenant.device').aggregate([{
        $match: {
          _removed: {
            $exists: false
          },
          farmland: {
            $oid: id
          }
        }
      }, {
        $lookup: {
          from: 'base.device.type',
          localField: 'type',
          foreignField: '_id',
          as: 'type'
        }
      }, {
        $match: {
          'type.code': { '$in': [103, 106] }
        }
      }])
      return data
    },
    setFlag (val, id, name) {
      let arr = {}
      arr.landid = this.$store.state.app.landid
      arr.flag = val
      arr.id = id
      arr.name = name
      let str = JSON.stringify(arr)
      if (window.native && window.native.saveWeatherTag) {
        window.native.saveWeatherTag(str)
      }
    }
  }
}
</script>
