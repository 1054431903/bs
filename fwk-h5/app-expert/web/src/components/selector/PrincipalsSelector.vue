<template>
  <div class="_selector">
    <div class="_navi">
      <v-breadcrumbs :data="stack" @itemClick="breadcrumbsItemClick"></v-breadcrumbs>
    </div>
    <div class="_list">
      <div class="_item" v-for="(item,index) in shows" :key="index">
        <div class="_separator"></div>
        <div class="_content">
          <div class="_mark" @click="mark(item)">
            <!--
            <v-icon>{{item.mark?'radio_button_checked':'radio_button_unchecked'}}</v-icon>
            -->
            <img style="width: 20px;height: 20px;" :src="item.mark?'selected_box.png':'select_box.png'"/>
          </div>
          <div class="_icon">
            <img :src="item.iconUrl" width="36" height="36" />
          </div>
          <div class="_name">{{formatter ? formatter(item, index, stack.length) : item}}</div>
          <div class="_deep">
            <!--
            <v-icon  @click="show(item)">view_list</v-icon>
            -->
            <div class="_button" v-if="item.deep" @click="show(item)">
              <img style="width: 16px;height: 16px;" src="next_level.png"/>
              <span>&nbsp;</span>
              <span>下级</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Share from '@/share'
import VBreadcrumbs from '@/components/Breadcrumbs'
export default {
  name: 'v-principals-selector',
  mixins: [Share],
  components: {
    VBreadcrumbs
  },
  props: {
    formatter: {
      type: Function
    }
  },
  data () {
    return {
      root: null,
      stack: [],
      shows: []
    }
  },
  computed: {
    items () {
      return this.lookup(this.root)
    }
  },
  watch: {
  },
  mounted () {
    this.load()
  },
  methods: {
    mark (item) {
      item.mark = !item.mark
    },
    show (item) {
      this.stack.push(item)
      this.shows = item.items
    },
    breadcrumbsItemClick (item, index) {
      this.stack = this.stack.filter((_, i) => i <= index)
      this.shows = item.items
    },
    lookup (node) {
      let items = []
      switch (node.type) {
        case '1':
        case '2': {
          (node.items || []).map(this.lookup).filter(item => item.length > 0).forEach(item => {
            items.push(...item)
          })
          break
        }
        case '3': {
          if (node.mark) {
            items.push(node)
            break
          }
        }
      }
      return items
    },
    async load () {
      this.root = await this.query()
      if (!this.root) return
      this.show(this.root)
    },
    async query () {
      let staff = await this.queryStaff()
      if (!staff) return
      let tenant = await this.queryTenant()
      if (!tenant) return
      let fwkUser = await this.queryFwkUser()
      if (!fwkUser) return
      let params = {
        loginerid: staff._id.$oid,
        operateid: fwkUser.id,
        operateredittime: staff.updatedOn.$numberLong,
        orgid: fwkUser.loginorgid,
        showtype: 'user'
      }
      let response = await this.$http.get('/fwk-service-user/pub/OrgDepartUserTree', {params: params})
      if (!(response.data && response.data.flag === 1)) {
        throw new Error(JSON.stringify(response.data))
      }
      if (Array.isArray(response.data.data)) {
        return this.parse(response.data.data[0])
      }
    },
    parse (data) {
      let result = {}
      console.debug(data)
      result = this.parseTree(data)
      console.debug(result)
      return result
    },
    parseTree (data) {
      // 1 组织
      // 2 部门
      // 3 人员
      // 4 角色
      switch (String((data || {}).sign)) {
        case '1': {
          return this.parseOrgAndDepart(data)
        }
        case '2': {
          return this.parseOrgAndDepart(data)
        }
        case '3': {
          return this.parseUser(data)
        }
      }
    },
    parseOrgAndDepart (data) {
      return {
        id: data.id,
        name: data.name,
        type: String(data.sign),
        deep: true,
        items: (data.children || []).map(this.parseTree),
        get mark () {
          if (Array.isArray(this.items) && this.items.length > 0) {
            return !this.items.find(item => !item.mark)
          } else {
            return false
          }
        },
        set mark (value) {
          (this.items || []).forEach(item => { item.mark = value })
        },
        iconUrl: 'organization.jpg'
      }
    },
    parseUser (data) {
      return {
        id: data.id,
        name: data.name,
        type: String(data.sign),
        mark: false,
        deep: false,
        phone: data.phone,
        iconUrl: data.phoneurl ? data.phoneurl.trim() : 'avatar.png'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  ._selector {
    width: 100%;

    ._navi {
      width: 100%;
      overflow: hidden;
      padding: 0 10px;
    }

    ._list {
      display: flex;
      flex-flow: column nowrap;

      ._item {

        ._separator {
          background-color: rgb(246, 245, 246);
          height: 1px;
        }

        ._content {
          flex: none;
          width: 100%;
          height: 44px;
          margin: 5px 0;
          display: flex;
          flex-flow: row nowrap;
          align-content: stretch;

          ._mark {
            flex: none;
            width: 44px;
            display: flex;
            flex-flow: column nowrap;
            justify-content: center;
            align-content: center;
            align-items: center;
          }

          ._icon {
            flex: none;
            width: 44px;
            display: flex;
            flex-flow: column nowrap;
            justify-content: center;
            align-content: center;
            align-items: center;

            img {
              border-radius: 100%;
            }
          }

          ._name {
            flex: auto;
            line-height: 44px;
          }

          ._deep {
            flex: none;
            width: 80px;
            display: flex;
            flex-flow: column nowrap;
            justify-content: center;
            align-content: center;
            align-items: center;

            ._button {
              display: flex;
              flex-flow: row nowrap;
              justify-content: center;
              align-content: center;
              align-items: center;
              color: #02ad58;
            }
          }
        }
      }
    }
  }
</style>
