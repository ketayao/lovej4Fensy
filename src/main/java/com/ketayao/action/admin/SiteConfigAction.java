package com.ketayao.action.admin;

import java.lang.reflect.InvocationTargetException;

import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.SiteConfig;
import com.ketayao.system.Constants;

public class SiteConfigAction {
	private final String UPDATE = "admin/site/siteConfig-read";
	
	public String pu() {
		return UPDATE;
	}

	public String u(WebContext rc) throws IllegalAccessException, InvocationTargetException {
		SiteConfig siteConfig = SiteConfig.INSTANCE.get(1);
		rc.populate(siteConfig);
		
		siteConfig.updateAttrs(new String[]{"about", "url", "contactDescription", "icp", "name"},
				new Object[] { siteConfig.getAbout(), siteConfig.getUrl(),
				siteConfig.getContactDescription(),
				siteConfig.getIcp(), siteConfig.getName()});
		
		rc.getContext().setAttribute(Constants.SITE_CONFIG, siteConfig);
		
		rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);
		return UPDATE;
	}
}
