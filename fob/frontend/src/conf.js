import {
  env
} from '@vehiclecloud/commons/util/config-helper'

export default {
  // MongoRest连接配置
  Mongo: {
    // 支撑平台数据库配置
    iss: {
      database: env('$AG_MONGO_ISS_DATABASE', 'iov'),
      base: env('$AG_MONGO_ISS_BASE', 'http://mongo.dev.agrithings.cn:29000')
    },
    // 业务系统数据库配置
    default: {
      database: env('$AG_MONGO_APP_DATABASE', 'fob'),
      base: env('$AG_MONGO_APP_BASE', 'http://mongo.dev.agrithings.cn:29000')
    }
  },
  // RedisRest连接配置
  Redis: {
    base: env('$AG_REDIS_BASE', 'http://mongo.dev.agrithings.cn:29100')
  },
  // OAUTH2服务连接参数
  OAuth2: {
    clientId: env('$AG_OAUTH2_CLIENT_ID', '10'),
    cookieName: env('$AG_OAUTH2_COOKIE_NAME', 'fob-token'),
    server: env('$AG_OAUTH2_SERVER', 'http://oauth2.dev.agrithings.cn:29300')
  },
  // OSS代理服务连接配置
  FileServer: {
    server: env('$AG_FILE_SERVER', 'http://file.dev.agrithings.cn:29400')
  },
  // 支撑平台代理服务的配置参数
  IssServer: {
    server: env('$AG_ISS_SERVER', 'http://iss.dev.agrithings.cn:28100')
  },
  // 有赞商城代理服务的配置参数
  YzServer: {
    server: env('$AG_YZ_SERVER', 'http://120.79.50.131:8101')
  },
  // 瞰农代理服务的配置参数
  KnServer: {
    server: env('$AG_KN_SERVER', 'http://test.farm.agrithings.cn')
  }
}
