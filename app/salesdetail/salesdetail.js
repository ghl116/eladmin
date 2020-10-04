import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/salesdetail',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/salesdetail/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/salesdetail',
    method: 'put',
    data
  })
}

export default { add, edit, del }
