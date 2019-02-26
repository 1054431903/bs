/**
 * login info
 */
export default {
  data () {
    return {
      orgid: null // orgid
    }
  },
  created () {
    this.orgid = global.helper.ls.get('user').loginorgid
  }
}
