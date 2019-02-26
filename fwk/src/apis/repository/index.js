/**
 * 农业知识库相关接口
 */

import http from '@/http'

/**
 * 查询知识库分类列表
 */
export function repositoryTypeList () {
  return http({
    url: '/agrithings/mobile/agrithings/treasure/knowledge/knowledge_category',
    method: 'get',
    headers: { 'Content-Type': 'application/json' }
  })
}
