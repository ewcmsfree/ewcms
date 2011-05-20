/**
 * 
 */
package com.ewcms.content.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.service.ShareArticleService;

/**
 * @author 周冬初
 *
 */
@Service
public class DataserveFac {
	@Autowired
	private ShareArticleService saService;
    /**
     * 文章共享
     *
     * @param siteId 所要共享到的站点
     * @param idList 要共享的文章id集合
     */    
    public void shareArticle(List<Integer> siteIdList,List<Integer> idList){
    	saService.shareArticle(siteIdList, idList);
    }
    
    public void delShareArticle(Integer id){
    	saService.delShareArticle(id);
    }
    
    /**
     * 共享文章引用
     *
     * @param id 共享文章id
     * @param idList 要引用的频道id集合
     */     
    public void refArticle(List<Integer> articleIds, List<Integer> channelIds){
    	saService.refArticle(articleIds, channelIds);
    }
}
