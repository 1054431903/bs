package com.fwk.service.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fwk.service.common.service.AccessService;
import com.fwk.service.common.service.FileService;
import com.fwk.service.common.service.FileUploadDownloadService;
import com.fwk.service.common.service.ResourceService;
import com.fwk.service.common.support.SupportCategoryService;
import com.fwk.service.common.support.SupportFarmLandService;
import com.fwk.service.common.support.SupportOrgDepartUserService;
import com.fwk.service.common.support.SupportOrgService;
import com.fwk.service.common.support.SupportService;
import com.fwk.service.common.support.SupportUserService;

@Configuration
@Import({ SupportService.class, SupportFarmLandService.class, SupportCategoryService.class,
		SupportOrgDepartUserService.class, SupportOrgService.class, SupportUserService.class, AccessService.class,
		FileService.class, FileUploadDownloadService.class, ResourceService.class,CommonsConfig.class })
public class CommonServiceConfigurer {

}
