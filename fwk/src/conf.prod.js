export default {
  Mongo: {
    iss: {
      database: 'iov',
      base: 'http://zc.agrithings.cn:9090'
    },
    iot: {
      database: 'iot',
      base: 'http://iot.agrithings.cn:9490'
    },
    fwk: {
      database: 'fwk',
      base: 'http://fwkmongo.zc.agrithings.cn:9090'
    }
  },
  Redis: {
    base: 'http://47.104.162.6:9100'
  },
  Grpc: {
    ws: 'ws://iot.agrithings.cn:9690/api/access/ws'
  },
  OAuth2: {
    server: 'http://oauth.agrithings.cn',
    clientId: '105'
  }
}