package com.ketayao.action.admin;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.ketayao.fensy.mvc.RequestContext;
import com.ketayao.pojo.SiteConfig;
import com.ketayao.system.Constants;

public class SiteConfigAction {
	private final String UPDATE = "admin/site/siteConfig-read";
	
	public String pu() {
		return UPDATE;
	}

	public String u(RequestContext rc) throws IllegalAccessException, InvocationTargetException {
		SiteConfig siteConfig = SiteConfig.INSTANCE.get(1);
		BeanUtils.populate(siteConfig, rc.getParameterMap());
		
		siteConfig.updateAttrs(new String[]{"about", "url", "contactDescription", "icp", "name"},
				new Object[] { siteConfig.getAbout(), siteConfig.getUrl(),
				siteConfig.getContactDescription(),
				siteConfig.getIcp(), siteConfig.getName()});
		
		rc.context().setAttribute(Constants.SITE_CONFIG, siteConfig);
		
		rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		return UPDATE;
	}
}
