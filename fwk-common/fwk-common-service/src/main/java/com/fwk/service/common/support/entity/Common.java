package com.fwk.service.common.support.entity;

import java.sql.Array;

/**
 * iov-common-schema
 * */
public class Common {


}
//{"title":"iov-common-schema","definitions":{"address":{"title":"地址","dependencies":{"district":["province","city"],"city":["province"]},"properties":{"street":{"title":"街道","type":"string"},"province":{"title":"省","type":"string","$id":"#base.area"},"district":{"title":"区","type":"string","$id":"#base.area"},"city":{"title":"市","type":"string","$id":"#base.area"}},"format":"address","type":"object"},"status":{"title":"状态","default":1,"enum":[0,1],"enumNames":["无效","有效"],"type":"integer"}},"$id":"common"}
