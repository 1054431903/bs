// 正式环境的WebPack打包配置
const webpack = require('webpack')
module.exports = {
  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      config.devtool = false
      config.entry.conf = ['./src/conf.js']
      config.plugins.push(new webpack.optimize.CommonsChunkPlugin({
        name: 'conf',
        chunks: ['app', 'conf']
      }))
    }
  }
}
