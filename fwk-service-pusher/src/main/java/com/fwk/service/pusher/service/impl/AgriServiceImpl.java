package com.fwk.service.pusher.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fwk.service.pusher.service.AgriService;

@Primary
@Service
public class AgriServiceImpl extends AbstractServiceImpl implements AgriService {

	private static final Logger logger = LoggerFactory.getLogger(AgriServiceImpl.class);
	protected static final String TYPE_ORIGINAL = "notice.agri.original";
	protected static final String TYPE_REFERENCE = "notice.agri.reference";

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@SuppressWarnings("serial")
	@Override
	public void publish(String tenant, String title, String subTitle, String articleId, String articleType)
	        throws Exception {
		Collection<String> staffIds = queryAllStaffIdByTenantId(tenant);
		if (CollectionUtils.isEmpty(staffIds)) {
			return;
		}
		StringBuilder builder = new StringBuilder(getFwkBaseUrl());
		builder.append(MESSAGE_API_ADDR);
		for (final String staffId : staffIds) {
			if (StringUtils.isBlank(staffId)) {
				continue;
			}
			// @formatter:off
			Map<String, Object> data = new LinkedHashMap<String, Object>() {{
				put("type", "original".equals(articleType) ? TYPE_ORIGINAL : TYPE_REFERENCE);
				put("param", new LinkedHashMap<String, Object>() {{
					put("article", articleId);
					put("title", title);
					put("subTitle", subTitle);
				}});
				put("tenant", new LinkedHashMap<String, Object>() {{
					put("$oid", tenant);
				}});
				put("staff", new LinkedHashMap<String, Object>() {{
					put("$oid", staffId);	
				}});
				put("readed", false);
				put("stage", "creation");
			}};
			// @formatter:on
			postForObject(builder.toString(), data, Map.class);
		}
	}

	private Collection<String> queryAllStaffIdByTenantId(String tenant) throws Exception {
		Collection<String> staffIds = queryStaffIdsByTenantId(tenant);
		Collection<String> tenantIds = queryChildrenTenantIdsByTenantId(tenant);
		if (CollectionUtils.isNotEmpty(tenantIds)) {
			for (String tenantId : tenantIds) {
				Collection<String> sIds = queryAllStaffIdByTenantId(tenantId);
				if (CollectionUtils.isNotEmpty(sIds)) {
					staffIds.addAll(sIds);
				}
			}
		}
		return staffIds;
	}

	@SuppressWarnings("unchecked")
	private Collection<String> queryStaffIdsByTenantId(String tenant) throws Exception {
		StringBuilder builder = new StringBuilder(getIssBaseUrl());
		builder.append(TENANT_STAFF_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        			put("_removed", new LinkedHashMap<String, Object>() {{
        		    	put("$exists", false);
        		    }});
        			put("status", 1);
        		    put("tenants", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", tenant);
        		    }});
        		}});
    	    }});
    	    add(new LinkedHashMap<String, Object>() {{
    	    	put("$project", new LinkedHashMap<String, Object>() {{
        			put("_id", 1);
        		}});
    	    }});
    	}};
    	// @formatter:on
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return null;
		}
		Collection<String> staffIds = new HashSet<>();
		for (Map<String, Object> staff : response) {
			Map<String, Object> _id = (Map<String, Object>) staff.get("_id");
			if (MapUtils.isEmpty(_id)) {
				continue;
			}
			String $oid = (String) _id.get("$oid");
			if (StringUtils.isBlank($oid)) {
				continue;
			}
			staffIds.add($oid);
		}
		return staffIds;
	}

	@SuppressWarnings("unchecked")
	private Collection<String> queryChildrenTenantIdsByTenantId(String tenant) throws Exception {
		StringBuilder builder = new StringBuilder(getIssBaseUrl());
		builder.append(TENANT_AGGREGATE_API_ADDR);
		// @formatter:off
    	@SuppressWarnings("serial")
    	ArrayList<?> aggregate = new ArrayList<Map<String, Object>>() {{
    	    add(new LinkedHashMap<String, Object>() {{
        		put("$match", new LinkedHashMap<String, Object>() {{
        			put("_removed", new LinkedHashMap<String, Object>() {{
        		    	put("$exists", false);
        		    }});
        			// put("status", 1);
        		    put("parent", new LinkedHashMap<String, Object>() {{
        		    	put("$oid", tenant);
        		    }});
        		}});
    	    }});
    	    add(new LinkedHashMap<String, Object>() {{
    	    	put("$project", new LinkedHashMap<String, Object>() {{
        			put("_id", 1);
        		}});
    	    }});
    	}};
    	// @formatter:on
		Map<String, Object>[] response = postForObject(builder.toString(), aggregate, Map[].class);
		if (ObjectUtils.isEmpty(response)) {
			return null;
		}
		Collection<String> tenantIds = new HashSet<>();
		for (Map<String, Object> staff : response) {
			Map<String, Object> _id = (Map<String, Object>) staff.get("_id");
			if (MapUtils.isEmpty(_id)) {
				continue;
			}
			String $oid = (String) _id.get("$oid");
			if (StringUtils.isBlank($oid)) {
				continue;
			}
			tenantIds.add($oid);
		}
		return tenantIds;
	}
}
