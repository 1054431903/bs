<template>
  <v-modaldialog :isShow="selectedLandDialog"
                 :title="selectedLandTitle"
                 :modalMaxWidth="750"
                 :bodyHeight="550">
    <v-layout row>
      <v-flex>用户账号</v-flex>
      <v-flex>姓名</v-flex>
      <v-flex>角色</v-flex>
    </v-layout>
    <v-layout>
      <v-flex>
        <v-card-text>
          <v-treeview v-model="tree"
                      :load-children="fetch"
                      :items="items"
                      activatable
                      active-class="grey lighten-4 indigo--text"
                      selected-color="indigo"
                      open-on-click
                      selectable
                      expand-icon="mdi-chevron-down"
                      on-icon="mdi-bookmark"
                      off-icon="mdi-bookmark-outline"
                      indeterminate-icon="mdi-bookmark-minus">
          </v-treeview>
        </v-card-text>
      </v-flex>

      <v-divider vertical></v-divider>

      <v-flex xs12
              md6>
        <v-card-text>
          <div v-if="selections.length === 0"
               key="title"
               class="title font-weight-light grey--text pa-3 text-xs-center">
            Select your favorite breweries
          </div>

          <v-scroll-x-transition group
                                 hide-on-leave>
            <v-chip v-for="(selection, i) in selections"
                    :key="i"
                    color="grey"
                    dark
                    small>
              <v-icon left
                      small>mdi-beer</v-icon>
              {{ selection.name }}
            </v-chip>
          </v-scroll-x-transition>
        </v-card-text>
      </v-flex>
    </v-layout>
    <v-divider></v-divider>
    <v-btn flat
           color="primary"
           @click.native="cancel">取消</v-btn>
    <v-btn flat
           color="primary"
           @click.native="save">完成</v-btn>
  </v-modaldialog>
</template>

<script>
// import mongo from '@vehiclecloud/mongo-rest-client'
export default {
  name: 'v-selectedland',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    lands: {
      type: Array,
      default: () => []
    },
    landlist: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      selectedLandTitle: '',
      selectedLandDialog: false,
      breweries: [],
      isLoading: false,
      tree: [],
      types: []
    }
  },
  computed: {
    items () {
      if (this.types.length > 0) {
        const children = this.types.map(type => ({
          // id: type._id.$oid,
          name: type.name
        }))

        return [{
          id: 1,
          name: '所有地块',
          children
        }]
      } else {
        return [{
          id: 1,
          name: '所有地块'
        }]
      }
    },
    selections () {
      const selections = []

      for (const leaf of this.tree) {
        const brewery = this.breweries.find(brewery => brewery.id === leaf)

        if (!brewery) continue

        selections.push(brewery)
      }

      return selections
    },
    shouldShowTree () {
      return this.breweries.length > 0 && !this.isLoading
    }
  },

  watch: {
    selectedLandDialog: function (v) {
      this.selected = this.lands
      this.filterText = ''
      // this.selectedAll = null
      this.$emit('update:visible', v)
    },
    visible: function (v) {
      this.selectedLandDialog = v
    },
    breweries (val) {
      this.types = val.reduce((acc, cur) => {
        const type = cur.brewery_type

        if (!acc.includes(type)) acc.push(type)

        return acc
      }, []).sort()
    }
  },

  methods: {
    save () {
      this.$emit('update:lands', this.selected)
      this.selectedLandDialog = false
    },
    cancel () {
      this.selectedLandDialog = false
    },
    fetch () {
      if (this.breweries.length) return
      this.breweries = this.landlist
    },
    getChildren (type) {
      const breweries = []

      for (const brewery of this.breweries) {
        if (brewery.brewery_type !== type) continue

        breweries.push({
          ...brewery,
          name: this.getName(brewery.name)
        })
      }

      return breweries.sort((a, b) => {
        return a.name > b.name ? 1 : -1
      })
    },
    getName (name) {
      return `${name.charAt(0).toUpperCase()}${name.slice(1)}`
    }
  },
  created () {
    this.selectedLandDialog = this.visible
    this.selectedLandTitle = this.title
    this.types = this.landlist
    console.log(this.types)
  }
}
</script>
<style scoped>
</style>
