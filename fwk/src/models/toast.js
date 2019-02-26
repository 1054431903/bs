export function Toast (self, content, position) {
  this.self = self
  this.content = content
  this.position = position
}

Toast.prototype.close = function () {
  this.self.$loading.close()
}

Toast.prototype.show = function () {
  let con = this.content || '加载数据...'
  let pos = this.position
  let self = this.self
  if (typeof pos === 'string' && pos.indexOf('top') === 0) {
    self.$toast.top(con)
  } else if (typeof pos === 'string' && pos.indexOf('center') === 0) {
    self.$toast.center(con)
  } else if (typeof pos === 'string' && pos.indexOf('bottom') === 0) {
    self.$toast.bottom(con)
  } else {
    self.$loading(con)
  }
}

Toast.prototype.showAutoClose = function () {
  let self = this.self
  let con = this.content || '加载数据...'
  let pos = this.position || 'center'
  if (typeof pos === 'string' && pos.indexOf('top') === 0) {
    self.$toast.top(con)
  } else if (typeof pos === 'string' && pos.indexOf('center') === 0) {
    self.$toast.center(con)
  } else if (typeof pos === 'string' && pos.indexOf('bottom') === 0) {
    self.$toast.bottom(con)
  } else {
    self.$loading(con)
    setTimeout(function () {
      self.$loading.close()
    }, 2000)
  }
}

Toast.prototype.setContent = function (content) {
  this.content = content
}

Toast.prototype.setPosition = function (position) {
  this.position = position
}
