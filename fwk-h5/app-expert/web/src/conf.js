import { env } from '@vehiclecloud/commons/util/config-helper'

export default {
  Mongo: {
    iss: {
      database: env('$AG_MONGO_ISS_DATABASE', 'iov'),
      base: env('$AG_MONGO_ISS_BASE', 'http://mongo.dev.agrithings.cn:29000')
    },
    iot: {
      database: env('$AG_MONGO_IOT_DATABASE', 'iot'),
      base: env('$AG_MONGO_IOT_BASE', 'http://mongo.dev.agrithings.cn:29000')
    },
    fwk: {
      database: env('$AG_MONGO_FWK_DATABASE', 'fwk'),
      base: env('$AG_MONGO_FWK_BASE', 'http://mongo.dev.agrithings.cn:29000')
    }
  },
  Redis: {
    base: env('$AG_REDIS_BASE', 'http://mongo.dev.agrithings.cn:29100')
  },
  Grpc: {
    ws: env('$AG_GRPC_WS', 'ws://47.104.162.6:9200/api/access/ws')
  },
  OAuth2: {
    server: env('$AG_OAUTH2_SERVER', 'http://oauth2.dev.agrithings.cn:29300'),
    clientId: env('$AG_OAUTH2_CLIENT_ID', '1')
  },
  FS: {
    server: env('$AG_FS_SERVER', 'http://localhost:9400')
  },
  ISS: {
    issServer: env('$AG_ISS_SERVER', 'http://localhost:8100'),
    province: {
      _id: { $oid: env('$AG_ISS_PROVINCE_ID', '530000000000530000000000') },
      level: 2,
      name: env('$AG_ISS_PROVINCE_NAME', '云南'),
      code: env('$AG_ISS_PROVINCE_CODE', '530000000000'),
      center: { lat: env('$AG_ISS_PROVINCE_LAT', 24.267), lng: env('$AG_ISS_PROVINCE_LNG', 101.7554) }
    }
  },
  FWK: {
    // fwkServer: env('$AG_FWK_SERVER', 'http://localhost:80')
    fwkServer: env('$AG_FWK_SERVER', 'http://test.farm.agrithings.cn:80')
  },
  YS7: {
    ys7Server: env('$AG_YS7_SERVER', 'http://api.agrithings.cn/'),
    ys7AppKey: env('$AG_YS7_APP_KEY', '81e5a657f0b04402a1b40a8cd53b9cf9'),
    ys7AppSecret: env('$AG_YS7_APP_SECRET', '60778daf62e8691d313c22db7ace5fa1')
  }
}
