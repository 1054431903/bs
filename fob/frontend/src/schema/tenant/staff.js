import schema from '@agrithings/common/schema/tenant/staff'
// 注意：由于JSON文件使用双引号作为字符串标识，而JS文件使用单引号作为字符串标识，请将双引号改成单引号
import {
  merge
} from '@vehiclecloud/commons/util/schema-helper'
export default merge(schema, {
  fob: {
    'title': '专家信息',
    'type': 'object',
    'properties': {
      'expertTags': {
        'title': '擅长领域',
        'type': 'array',
        'items': {
          'type': 'string',
          '$id': '#base.expert_tag'
        }
      },
      'categoryTags': {
        'type': 'array',
        'title': '擅长品类',
        'items': {
          'type': 'string',
          '$id': '$base.category'
        }
      },
      'remark': {
        'title': '备注',
        'type': 'string'
      }
    },
    'required': ['expertTags']
  }
})
