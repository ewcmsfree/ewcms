/**
 * 
 */
package com.ewcms.web.action;

import com.ewcms.core.site.model.Site;
import com.ewcms.web.util.EwcmsContextUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 周冬初
 *
 */
public class EwcmsBaseAction extends ActionSupport{  
	/**
	 * 信息输出处理
	 *
	 */
    public void outputInfo(String e){
    	System.out.print(e);
    }
	/**
	 * 获取当前站点
	 *
	 */    
    public Site getCurrentSite(){
    	return EwcmsContextUtil.getCurrentSite();
    }
}
