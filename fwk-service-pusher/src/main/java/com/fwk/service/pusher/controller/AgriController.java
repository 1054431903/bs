package com.fwk.service.pusher.controller;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fwk.service.pusher.service.AgriService;

@RestController
@RequestMapping("agri")
public class AgriController {

	@Autowired
	private AgriService agriService;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AgriController.class);

	@PostMapping("publish")
	public void publish(@Validated @RequestBody PublishRequest request) throws Exception {
		agriService.publish(request.getTenant(), request.getTitle(), request.getSubTitle(), request.getArticleId(),
		        request.getArticleType());
	}

	public static class PublishRequest {

		/**
		 * 组织机构Id
		 */
		@NotBlank(message = "tenant not allow blank")
		private String tenant;

		/**
		 * 文章类型（original=原创、reference=引用）
		 */
		@NotBlank(message = "articleType not allow blank")
		@Pattern(regexp = "^(original)|(reference)$", message = "articleType only original or reference")
		private String articleType;

		/**
		 * 文章Id
		 */
		@NotBlank(message = "articleId not allow blank")
		private String articleId;

		/**
		 * 消息标题
		 */
		@NotBlank(message = "title not allow blank")
		private String title;

		/**
		 * 消息副标题
		 */
		@NotBlank(message = "subTitle not allow blank")
		private String subTitle;

		public String getTenant() {
			return tenant;
		}

		public void setTenant(String tenant) {
			this.tenant = tenant;
		}

		public String getArticleType() {
			return articleType;
		}

		public void setArticleType(String articleType) {
			this.articleType = articleType;
		}

		public String getArticleId() {
			return articleId;
		}

		public void setArticleId(String articleId) {
			this.articleId = articleId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubTitle() {
			return subTitle;
		}

		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
	}
}