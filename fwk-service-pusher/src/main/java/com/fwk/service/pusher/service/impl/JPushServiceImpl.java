package com.fwk.service.pusher.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fwk.service.pusher.service.PushService;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

@Primary
@Service
public class JPushServiceImpl extends AbstractServiceImpl implements PushService {

	private static final Logger logger = LoggerFactory.getLogger(JPushServiceImpl.class);

	@Value("${jpush.master-secret}")
	private String jpushMasterSecret;

	@Value("${jpush.app-key}")
	private String jpushAppKey;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@PostConstruct
	public void init() throws Exception {
		logger.info("JPUSH_MASTER_SECRET={}, JPUSH_APP_KEY={}", jpushMasterSecret, jpushAppKey);
	}

	@Override
	public void push(String tenantId, String tenantStaffId, String title, String subTitle, String category)
	        throws Exception {
		push(tenantId, tenantStaffId, title, subTitle, category, null);
	}

	@Override
	public void push(String tenantId, String tenantStaffId, String title, String subTitle, String category,
	        Map<String, String> params) throws Exception {
		// 标题不允许为空或者空字符串
		if (StringUtils.isBlank(title)) {
			throw new IllegalArgumentException("title not allow null or blank");
		}

		// Message
		Message.Builder mBuilder = Message.newBuilder();
		mBuilder.setTitle(title);
		mBuilder.setMsgContent(subTitle);
		mBuilder.setContentType(category);
		if (MapUtils.isNotEmpty(params)) {
			mBuilder.addExtras(params);
		}

		// PushPayload
		PushPayload.Builder pBuilder = PushPayload.newBuilder();
		pBuilder.setMessage(mBuilder.build());
		pBuilder.setPlatform(Platform.all());
		pBuilder.setAudience(Audience.tag_and("TID$$".concat(tenantId), "SID$$".concat(tenantStaffId)));
		// pBuilder.setNotification(nBuilder.build());
		JPushClient jpushClient = new JPushClient(jpushMasterSecret, jpushAppKey, null, ClientConfig.getInstance());
		try {
			PushResult result = jpushClient.sendPush(pBuilder.build());
			logger.info("Push Result:" + result);
		} catch (APIRequestException e) {
			switch (e.getErrorCode()) {
				case 1011: {
					// 找不到沒有指定的alias, 无视这个错误
					logger.warn("Push Result:" + e.getMessage());
					break;
				}
				default: {
					throw e;
				}
			}
		} finally {
			jpushClient.close();
		}
	}
}