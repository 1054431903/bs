package com.agrithings.yz;

import com.youzan.open.sdk.api.AbstractAPI;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.OAuth;
import com.youzan.open.sdk.client.oauth.OAuthContext;
import com.youzan.open.sdk.client.oauth.OAuthFactory;
import com.youzan.open.sdk.client.oauth.OAuthType;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.exception.KDTException;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;

/**
 * Author gebing
 * Date: 2018/06/05
 */
public class YzClient {
  private static Token accessToken = null;
  private static long expireTime;
  private OAuthContext authContext;
  private DefaultYZClient yzClient;

  public YzClient(String clientId, String clientSecret, long kdtId) {
    authContext = new OAuthContext(clientId, clientSecret, kdtId);
    getClient();
  }

  private YZClient getClient() {
    if (accessToken == null || System.currentTimeMillis() + 5000 > expireTime) {
      OAuth auth = OAuthFactory.create(OAuthType.SELF, authContext);
      OAuthToken token = auth.getToken();
      accessToken = new Token(token.getAccessToken());
      expireTime = System.currentTimeMillis() + token.getExpiresIn() * 1000;
      yzClient = new DefaultYZClient(accessToken);
    } else if (yzClient == null) {
      yzClient = new DefaultYZClient(accessToken);
    }
    return yzClient;
  }

  private <T> T invokeClient(AbstractAPI request) {
    try {
      return getClient().invoke(request);
    } catch (KDTException kdtException) {
      accessToken = null;
      return getClient().invoke(request);
    }
  }

  /**
   * 获取商品分组列表
   * @return 商品分组列表
   */
  YouzanItemcategoriesTagsGetResult getTags() {
    YouzanItemcategoriesTagsGetParams params = new YouzanItemcategoriesTagsGetParams();
    params.setIsSort(true);
    return invokeClient(new YouzanItemcategoriesTagsGet(params));
  }

  /**
   * 根据商品分组ID获取商品列表
   * @param tagId   商品分组ID
   * @param orderBy 排序方式。格式为column:asc/desc，目前排序字段：1.创建时间：created_time，2.更新时间：update_time，3.价格：price，4.销量：sold_num
   * @return 商品列表
   */
  YouzanItemsOnsaleGetResult getItemsByTag(long tagId, String... orderBy) {
    YouzanItemsOnsaleGetParams params = new YouzanItemsOnsaleGetParams();
    params.setTagId(tagId);
    if (orderBy != null && orderBy.length > 0 && !orderBy[0].isEmpty()) {
      params.setOrderBy(orderBy[0]);
    }
    return invokeClient(new YouzanItemsOnsaleGet(params));
  }

  /**
   * 根据商品ID获取商品详情
   * @param itemId 商品ID
   * @return 商品详情
   */
  YouzanItemGetResult getItemInfo(long itemId) {
    YouzanItemGetParams params = new YouzanItemGetParams();
    params.setItemId(itemId);
    return invokeClient(new YouzanItemGet(params));
  }
}
