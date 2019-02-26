module.exports = {
  devServer: {
    port: 8080,
    proxy: {
      '/api/forecast': {
        target: 'http://bloomskybackend-env.cn-north-1.eb.amazonaws.com.cn/',
        changeOrigin: true
      }
    } // string | Object
  }
}
