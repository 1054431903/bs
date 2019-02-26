import axios from 'axios'
import store from '../store'

function getItems () {
  return axios.get(`${store.state.config.yzServer}/api/yz/item/list`).then(({
    data
  }) => data.map(i => Object.assign(i, {
    value: i._id.$oid
  })))
}

export default {
  getItems
}
