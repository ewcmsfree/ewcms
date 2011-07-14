package com.ewcms.publication.service;

import com.ewcms.core.site.model.Site;

/**
 * 站点接口
 * <br>
 * 发布资源时需要依赖站点信息。
 * 
 * @author wangwei
 */
public interface SitePublishServiceable {

    /**
     * 通过站点编号得到站点
     * 
     * @param id 站点编号
     * @return
     */
    public Site getSite(Integer id);
}
