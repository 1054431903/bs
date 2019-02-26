import Vue from 'vue'
// import inflector from 'i'

const storage = window.sessionStorage
let helper = {}

/**
 * string processor
 */
// helper.i = inflector()

/**
 * sessionStorage
 */
helper.ls = {
  set (key, value) {
    value = JSON.stringify(value)
    storage.setItem(key, value)
  },
  get (key, value, defaultValue) {
    let v = storage.getItem(key, value)
    if (v === null || v === 'undefined' || v === '') {
      v = defaultValue
    } else {
      v = JSON.parse(v)
    }
    return v
  }
}
/**
 * a wrapper for helper.ls
 */
helper.store = (key, value) => {
  if (arguments.length < 2) {
    return helper.ls.get(key)
  } else {
    return helper.ls.set(key, value)
  }
}
Vue.directive('back', (el, binding) => {
  el.onclick = () => window.history.go(-1)
})

export default helper
