<template>
  <div class="text-xs-center">
    <v-dialog v-model="selectedLandDialog"
              persistent
              scrollable
              max-width="750">
      <v-card class="card--flex-toolbar">
        <v-toolbar card
                   color="grey lighten-4">
          <v-toolbar-title>
            <div>
              选择地块
            </div>
          </v-toolbar-title>
        </v-toolbar>
        <v-divider></v-divider>
        <v-card-text style="height:400px;">
          <v-layout row>
            <v-flex xs6
                    mr-1
                    class="panel">
              <v-layout row>
                <v-flex xs12
                        class="tiplabel">
                  可选地块 ({{tenantFarmLands.length}}块)
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
              <v-layout row>
                <v-flex xs12
                        pl-2
                        pr-3
                        pb-2>
                  <v-text-field append-icon="search"
                                @click:append="searchData"
                                v-model="keywords"
                                label="请输入地块名称/编号进行搜索"
                                hide-details
                                @keyup.enter="searchData"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs12
                        pl-2
                        class="landname">
                  <v-treeview v-model="tree"
                              :items="items"
                              selected-color="primary"
                              open-on-click
                              selectable
                              hoverable
                              expand-icon="keyboard_arrow_down"
                              on-icon="check_box"
                              off-icon="check_box_outline_blank"
                              indeterminate-icon="indeterminate_check_box">
                  </v-treeview>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex xs6
                    class="panel">
              <v-layout row>
                <v-flex xs12
                        class="tiplabel">
                  已选地块({{selections.length}}块)
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
              <v-layout row>
                <v-flex xs12
                        pl-2>
                  <v-scroll-x-transition group
                                         hide-on-leave>
                    <v-chip v-for="(selection, i) in selections"
                            :key="i"
                            close
                            small
                            @input="remove(selection)">
                      {{ selection.name }}
                    </v-chip>
                  </v-scroll-x-transition>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary"
                 flat
                 @click.native="cancel">
            取消
          </v-btn>
          <v-btn color="primary"
                 flat
                 @click.native="save">
            完成
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
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
    selectedLands: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      selectedLandDialog: false,
      tree: [],
      tenantFarmLands: [],
      tmpTenantFarmLands: [],
      items: [],
      // selections: [],
      keywords: null
    }
  },
  created () {
    this.$store.state.tenantFarmLands.map(elem => {
      let land = Object.assign({}, elem)
      Object.assign(land, { id: elem._id.$oid })
      this.tenantFarmLands.push(land)
    })
    this.tmpTenantFarmLands = this.tenantFarmLands
    this.items = [{
      id: 1,
      name: '全部地块',
      children: this.tmpTenantFarmLands
    }]
  },
  computed: {
    // items () {
    //   const children = this.tenantFarmLands
    //   console.log(this.tenantFarmLands)
    //   console.log(this.tree)
    //   return [{
    //     id: 1,
    //     name: '全部地块',
    //     children
    //   }]
    // },
    selections () {
      const selections = []
      for (const leaf of this.tree) {
        const selectedLand = this.tenantFarmLands.find(land => land.id === leaf)
        if (!selectedLand) continue
        selections.push(selectedLand)
      }
      return selections
    },
    tenant () {
      return global.helper.ls.get('user') ? global.helper.ls.get('user').loginorgid : ''
    }
  },
  watch: {
    visible (v) {
      this.selectedLandDialog = v
    },
    selectedLandDialog (v) {
      this.$emit('update:visible', v)
    },
    tree (v) {
      for (let leaf of v) {
        let select = this.tenantFarmLands.find(land => land.id === leaf)
        if (!select) continue
        if (this.selections.includes(select)) continue
        this.selections.push(select)
      }
    }
  },
  methods: {
    save () {
      this.$emit('update:selectedLands', this.selections)
      this.selectedLandDialog = false
    },
    cancel () {
      this.selectedLandDialog = false
    },
    remove (item) {
      if (this.tree.length === this.selections.length + 1) {
        this.tree.splice(this.tree.indexOf(1), 1)
        this.tree.splice(this.tree.indexOf(item.id), 1)
      } else {
        this.tree.splice(this.tree.indexOf(item.id), 1)
      }
    },
    searchData () {
      this.tmpTenantFarmLands = []
      this.tree = []
      this.selections = []
      if (this.keywords && this.keywords.trim()) {
        this.tmpTenantFarmLands = this.tenantFarmLands.filter(elem => {
          return elem.name.indexOf(this.keywords) > -1
        })
        this.items = [{
          id: 1,
          name: '全部地块',
          children: this.tmpTenantFarmLands
        }]
      } else {
        this.tmpTenantFarmLands = this.tenantFarmLands
        this.items = [{
          id: 1,
          name: '全部地块',
          children: this.tmpTenantFarmLands
        }]
      }
    }
  }
}
</script>
<style scoped>
.panel {
  border: 1px solid rgb(235, 228, 228);
  min-height: 360px;
}
.tiplabel {
  padding: 15px 15px;
  color: #32c787;
}
.landname {
  word-wrap: break-word;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
