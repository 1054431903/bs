export default {
  Mongo: {
    iss: {
      database: 'iov',
      base: 'http://47.104.162.6:9000'
    },
    iot: {
      database: 'iot',
      base: 'http://47.104.162.6:9000'
    },
    fwk: {
      database: 'fwk',
      base: 'http://47.104.162.6:9000'
    }
  },
  Redis: {
    base: 'http://47.104.162.6:9100'
  },
  Grpc: {
    ws: 'ws://47.104.162.6:9200/api/access/ws'
  },
  OAuth2: {
    server: 'http://47.104.162.6:9300',
    clientId: '3'
  }
}
