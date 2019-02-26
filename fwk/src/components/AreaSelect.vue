<template>
  <v-form class="row"
          lazy-validation>
    <v-layout row
              justify-space-between>
      <v-flex v-if="showLevel > 0">
        <v-autocomplete v-bind:items="provinces"
                        v-model="province"
                        item-text="name"
                        item-value="_id.$oid"
                        label="省"
                        single-line
                        no-data-text="无"
                        :hide-details="isHideDetails"
                        :rules="level > 0 ? rules.required : rules.noRequired"
                        clearable
                        @input="provinceChange(province)">
        </v-autocomplete>
      </v-flex>
      <v-flex v-bind:class="{'ml-1': isMarginLeft}"
              v-if="showLevel > 1">
        <v-autocomplete v-bind:items="cities"
                        v-model="city"
                        item-text="name"
                        item-value="_id.$oid"
                        label="市"
                        single-line
                        no-data-text="无"
                        :hide-details="isHideDetails"
                        :rules="level > 1 ? rules.required : rules.noRequired"
                        clearable
                        @input="cityChange(city)">
        </v-autocomplete>
      </v-flex>
      <v-flex ml-1
              v-if="showLevel > 2">
        <v-autocomplete v-bind:items="counties"
                        v-model="county"
                        item-text="name"
                        item-value="_id.$oid"
                        label="县(区)"
                        single-line
                        no-data-text="无"
                        :hide-details="isHideDetails"
                        :rules="level > 2 ? rules.required : rules.noRequired"
                        clearable
                        @input="countyChange(county)">
        </v-autocomplete>
      </v-flex>
      <v-flex ml-1
              v-if="showLevel > 3">
        <v-autocomplete v-bind:items="towns"
                        v-model="town"
                        item-text="name"
                        item-value="_id.$oid"
                        label="镇(乡)"
                        single-line
                        no-data-text="无"
                        :hide-details="isHideDetails"
                        :rules="level > 3 ? rules.required : rules.noRequired"
                        clearable
                        @input="townChange(town)">
        </v-autocomplete>
      </v-flex>
      <v-flex ml-1
              v-if="showLevel > 4">
        <v-autocomplete v-bind:items="villages"
                        v-model="village"
                        item-text="name"
                        item-value="_id.$oid"
                        label="村"
                        single-line
                        no-data-text="无"
                        :hide-details="isHideDetails"
                        :rules="level > 4 ? rules.required : rules.noRequired"
                        clearable>
        </v-autocomplete>
      </v-flex>
    </v-layout>
  </v-form>
</template>

<script>
// import { excuteApis } from '@/apis'
import mongo, { config as MongoConfig } from '@vehiclecloud/mongo-rest-client'
import conf from '@/conf'
MongoConfig({
  iss: {
    database: conf.Mongo.iss.database,
    base: conf.Mongo.iss.base
  },
  iot: {
    database: conf.Mongo.iot.database,
    base: conf.Mongo.iot.base
  },
  default: {
    database: conf.Mongo.iss.database,
    base: conf.Mongo.iss.base
  }
})
export default {
  props: {
    curAddressCode: {
      type: String,
      required: true
    },
    isHideDetails: {
      type: Boolean,
      default: true
    },
    isMarginLeft: {
      type: Boolean,
      default: true
    },
    isReversal: {
      type: Boolean,
      default: false
    },
    level: {
      type: Number,
      default: 0
    },
    showLevel: {
      type: Number,
      default: 5
    }
  },
  data () {
    return {
      selectedCode: {
        code: '',
        level: 1,
        parentcode: ''
      },
      provinces: [],
      province: '',
      cities: [],
      city: '',
      counties: [],
      county: '',
      towns: [],
      town: '',
      villages: [],
      village: '',
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        noRequired: []
      },
      flag: true,
      reversalcode: '',
      customFilter (item, queryText, itemText) {
        const hasValue = val => val != null ? val : ''
        const text = hasValue(item.name)
        const query = hasValue(queryText)
        return text.toString()
          .toLowerCase()
          .indexOf(query.toString().toLowerCase()) > -1
      }
    }
  },
  watch: {
    coding: function (v) {
      this.$emit('update:curAddressCode', v || '100000000000100000000000')
      this.areasComponent(v)
    },
    curAddressCode: function (v) {
      this.areasComponent(v)
    }
  },
  computed: {
    coding () {
      let code = this.village || this.town || this.county || this.city || this.province
      return code
    }
  },
  updated: function () {
    this.$emit('titleChanged', false)
  },
  methods: {
    provinceChange (v) {
      if (!v) {
        this.city = ''
        this.cities = []
        this.county = ''
        this.counties = []
        this.town = ''
        this.towns = []
        this.village = ''
        this.villages = []
      }
    },
    cityChange (v) {
      if (!v) {
        this.county = ''
        this.counties = []
        this.town = ''
        this.towns = []
        this.village = ''
        this.villages = []
      }
    },
    countyChange (v) {
      if (!v) {
        this.town = ''
        this.towns = []
        this.village = ''
        this.villages = []
      }
    },
    townChange (v) {
      if (!v) {
        this.village = ''
        this.villages = []
      }
    },
    async areas (condition) {
      let dbClient = mongo.db('iss').collection('base.area')
      let { items } = await dbClient.find(condition)
      return items
    },
    areasComponent (areacode) {
      if (!areacode) return
      let condition
      // 根据实际情况查询
      this.province = areacode.substr(0, 2) === '10' ? null : (areacode.substr(0, 2) + '0000000000' + areacode.substr(0, 2) + '0000000000')
      this.city = areacode.substr(2, 2) === '00' ? null : (areacode.substr(0, 4) + '00000000' + areacode.substr(0, 4) + '00000000')
      this.county = areacode.substr(4, 2) === '00' ? null : (areacode.substr(0, 6) + '000000' + areacode.substr(0, 6) + '000000')
      this.town = areacode.substr(6, 3) === '000' ? null : (areacode.substr(0, 9) + '000' + areacode.substr(0, 9) + '000')
      this.village = areacode.substr(9, 3) === '000' ? null : (areacode.substr(0, 12) + areacode.substr(0, 12))
      // 查询省份
      condition = Object.assign({}, { filter: { level: 2, parent: { $oid: '100000000000100000000000' } } })
      this.areas(condition).then(items => {
        this.provinces = items
      })
      // 城市
      if (this.province) {
        condition = Object.assign({}, { filter: { level: 3, parent: { $oid: this.province } } })
        this.areas(condition).then(items => {
          this.cities = items
        })
      }
      // 县市
      if (this.city) {
        condition = Object.assign({}, { filter: { level: 4, parent: { $oid: this.city } } })
        this.areas(condition).then(items => {
          this.counties = items
        })
      }
      // 乡镇
      if (this.county) {
        condition = Object.assign({}, { filter: { level: 5, parent: { $oid: this.county } } })
        this.areas(condition).then(items => {
          this.towns = items
        })
      }
      // 村
      if (this.town) {
        condition = Object.assign({}, { filter: { level: 6, parent: { $oid: this.town } } })
        this.areas(condition).then(items => {
          this.villages = items
        })
      }
    }
  },
  created () {
    this.areasComponent(this.curAddressCode)
  }
}
</script>

<style scoped>
</style>
