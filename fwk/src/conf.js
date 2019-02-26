export default {
  Mongo: {
    iss: {
      database: 'iov',
      base: 'http://mongo.dev.agrithings.cn:29000'
    },
    iot: {
      database: 'iot',
      base: 'http://mongo.dev.agrithings.cn:29000'
    },
    fwk: {
      database: 'fwk',
      base: 'http://mongo.dev.agrithings.cn:29000'
    }
  },
  Redis: {
    base: 'http://mongo.dev.agrithings.cn:29100'
  },
  Grpc: {
    ws: 'ws://47.104.162.6:9200/api/access/ws'
  },
  OAuth2: {
    server: 'http://oauth2.dev.agrithings.cn:29300',
    clientId: '3'
  },
  Power: {
    server: 'http://pv.dev.agrithings.cn:8080'
  }
}
