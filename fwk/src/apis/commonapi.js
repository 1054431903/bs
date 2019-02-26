import http from '@/http'
// 图片上传接口
export function croppaImageUpload (requestParams) {
  return http({
    url: global.constant.uplaodBase64Url,
    method: 'post',
    data: requestParams
  })
}
