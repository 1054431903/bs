package com.agrithings.yz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vehiclecloud.iot.rapidoid.http.AbstractCorsHttpServer;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemcategoriesTagsGetResult.ItemGroupOpenModel;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsOnsaleGetResult.ItemListOpenModel;
import org.rapidoid.buffer.Buf;
import org.rapidoid.config.Conf;
import org.rapidoid.config.Config;
import org.rapidoid.http.HttpStatus;
import org.rapidoid.net.abstracts.Channel;
import org.rapidoid.net.impl.RapidoidHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 有赞云商城API代理服务
 * @version 1.0, 05/06/2018.
 */
public class YzServer extends AbstractCorsHttpServer {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false);
  private static final byte[] PATH_ITEM_LIST = "/api/yz/item/list".getBytes();


  private static Config youzanConf = Conf.section("youzan");
  private static String clientId = youzanConf.entry("clientId").str().get();
  private static String clientSecret = youzanConf.entry("clientSecret").str().get();
  private static long kdtId = youzanConf.entry("kdtId").num().get();

  YzServer() {
  }

  private <T> HttpStatus toJson(Channel ctx, RapidoidHelper req, T response) {
    try {
      String json = OBJECT_MAPPER.writeValueAsString(response);
      return json(ctx, req.isKeepAlive.value, json.getBytes());
    } catch (Exception ex) {
      ctx.write(fullResp(500, ex.toString().getBytes()));
      ctx.closeIf(!req.isKeepAlive.value);
      return HttpStatus.DONE;
    }
  }

  private HttpStatus itemList(Channel ctx, Buf buf, RapidoidHelper req) {
    YzClient client = new YzClient(clientId, clientSecret, kdtId);
    ArrayList<Object> result = new ArrayList<>();
    Arrays.stream(client.getTags().getTags()).forEach(group -> {
      result.add(getGroupInfo(group));
      Arrays.stream(client.getItemsByTag(group.getId()).getItems()).forEach(item -> {
        result.add(getItemInfo(group.getId(), item));
      });
    });
    return toJson(ctx, req, result);
  }

  private HashMap<String, Object> getObjectId(Long id) {
    return id != null ? new HashMap<String, Object>() {{ put("$oid", id.toString()); }} : null;
  }

  private HashMap getGroupInfo(ItemGroupOpenModel group) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("_id", getObjectId(group.getId()));
    map.put("name", group.getName());
    map.put("text", group.getName());
    map.put("alias", group.getAlias());
    map.put("url", group.getTagUrl());
    return map;
  }

  private HashMap getItemInfo(Long groudId, ItemListOpenModel item) {
    HashMap<String, Object> map = new HashMap<>();
    String name = item.getTitle();
    String extra = "";
    Long v = item.getPrice();
    if (v != null) {
      extra = extra.isEmpty() ? extra : extra + "，";
      extra = String.format("单价：%01d.%02d元", v / 100, v % 100);
    }
    v = item.getQuantity();
    if (v != null) {
      extra = extra.isEmpty() ? extra : extra + "，";
      extra += String.format("库存：%d件", v);
    }
    map.put("_id", getObjectId(item.getItemId()));
    map.put("parent", getObjectId(groudId));
    map.put("name", name);
    map.put("text", name + (extra.isEmpty() ? "" : "（" + extra + "）"));
    map.put("alias", item.getAlias());
    map.put("url", item.getDetailUrl());
//    map.put("price", item.getPrice());
//    map.put("quantity", item.getQuantity());
//    map.put("thumbnails", Arrays.stream(item.getItemImgs()).map(ItemImageOpenModel::getThumbnail).toArray(String[]::new));
//    map.put("mediums", Arrays.stream(item.getItemImgs()).map(ItemImageOpenModel::getMedium).toArray(String[]::new));
//    map.put("combines", Arrays.stream(item.getItemImgs()).map(ItemImageOpenModel::getCombine).toArray(String[]::new));
    return map;
  }

  @Override
  protected HttpStatus handle(Channel ctx, Buf buf, RapidoidHelper req) {
    if (matches(buf, req.verb, _POST) || req.isGet.value) {
      HTTP_PARSER.parseParams(buf, req.params.reset(), req.body);
      if (matches(buf, req.path, PATH_ITEM_LIST)) {
        return itemList(ctx, buf, req);
      }
    } else if (matches(buf, req.verb, _OPTIONS)) {
      return cors(ctx, req);
    }
    return HttpStatus.NOT_FOUND;
  }
}
