/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleRmcDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.ArticleRmcStatus;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.Content;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.GeneratorServiceable;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.security.manage.service.UserServiceable;

@Service
public class ArticleRmcServiceWrapping {

    @Autowired
    private ArticleRmcDAO articleRmcDAO;
    @Autowired
    private ChannelDAO channelDAO;
    @Autowired
    private GeneratorServiceable generatorService;
	@Autowired
	private UserServiceable userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public Integer addArticleRmc(Article article, Integer channelId,Date published) {
        ArticleRmc articleRmc = new ArticleRmc();

        if (published != null) {
            articleRmc.setPublished(published);
        }

        clearStyleSpace(article);
        articleRmc.setArticle(article);

        Channel channel = channelDAO.get(channelId);
        articleRmc.setChannel(channel);

        articleRmcDAO.persist(articleRmc);
        return articleRmc.getId();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public Integer updArticleRmc(Integer articleRmcId, Article article,Integer channelId, Date published) {
        ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);

        if (published != null) {
            articleRmc.setPublished(published);
        }

        clearStyleSpace(article);

        if (articleRmc.getStatus() == ArticleRmcStatus.RELEASE
                || articleRmc.getStatus() == ArticleRmcStatus.PRERELEASE) {
            articleRmc.setStatus(ArticleRmcStatus.REEDIT);
        }

        Article article_old = articleRmc.getArticle();
        if (article.getType() == ArticleStatus.GENERAL) {
        } else if (article.getType() == ArticleStatus.TITLE) {
            if (article_old.getContents() != null
                    && !article_old.getContents().isEmpty()) {
                article.setContents(article_old.getContents());
            } else {
                article.setContents(new ArrayList<Content>());
            }
        }
        articleRmc.setArticle(article);
        articleRmc.setModified(new Date(Calendar.getInstance().getTime()
                .getTime()));

        articleRmcDAO.merge(articleRmc);
        return articleRmc.getId();
    }

    private void clearStyleSpace(Article article) {
        if (article.getTitleStyle() != null
                && article.getTitleStyle().length() > 0) {
            article.setTitleStyle(article.getTitleStyle().trim());
        }
        if (article.getShortTitleStyle() != null
                && article.getShortTitleStyle().length() > 0) {
            article.setShortTitleStyle(article.getShortTitleStyle().trim());
        }
        if (article.getSubTitleStyle() != null
                && article.getSubTitleStyle().length() > 0) {
            article.setSubTitleStyle(article.getSubTitleStyle().trim());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','READ') "  
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public ArticleRmc getArticleRmc(ArticleRmc articleRmc, Integer channelId) {
        return articleRmc;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public void delArticleRmc(ArticleRmc articleRmc, Integer channelId) {
        articleRmcDAO.remove(articleRmc);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public void delArticleRmcToRecycleBin(ArticleRmc articleRmc,
            Integer channelId, String userName) {
        articleRmc.setDeleteTime(new Date(Calendar.getInstance().getTime().getTime()));
        articleRmc.setDeleteFlag(true);
        articleRmc.setDeleteAuthor(userName);
        articleRmcDAO.merge(articleRmc);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public void restoreArticleRmc(ArticleRmc articleRmc, Integer channelId,String userName) {
        articleRmc.setStatus(ArticleRmcStatus.REEDIT);
        articleRmc.setDeleteFlag(false);
        articleRmc.setRestoreAuthor(userName);
        articleRmcDAO.merge(articleRmc);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','WRITE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public Boolean submitReviewArticleRmc(ArticleRmc articleRmc,Integer channelId, Boolean isStatus) {
    	if (isStatus){
            if (articleRmc.getStatus() == ArticleRmcStatus.DRAFT || articleRmc.getStatus() == ArticleRmcStatus.REEDIT) {
                articleRmc.setStatus(ArticleRmcStatus.REVIEW);
                if (articleRmc.getPublished() == null) {
                    articleRmc.setPublished(new Date(Calendar.getInstance().getTime().getTime()));
                }
                articleRmcDAO.merge(articleRmc);
                return true;
            }
    	}else{
            articleRmc.setStatus(ArticleRmcStatus.REVIEW);
            if (articleRmc.getPublished() == null) {
                articleRmc.setPublished(new Date(Calendar.getInstance().getTime().getTime()));
            }
            articleRmcDAO.merge(articleRmc);
            return true;
    	}
        return false;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') " 
            +"or hasPermission(#channel,'WRITE') " 
            +"or hasPermission(#channel,'PUBLISH') " 
            +"or hasPermission(#channel,'CREATE') " 
            +"or hasPermission(#channel,'UPDATE') "
            +"or hasPermission(#channel,'DELETE') " 
            +"or hasPermission(#channel,'ADMIN') ")
    public void copyArticleRmcToChannel(ArticleRmc articleRmc,Channel channel){
        if (!articleRmc.getChannel().equals(channel)){
            ArticleRmc articleRmc_target = new ArticleRmc();
            
            articleRmc_target.setStatus(ArticleRmcStatus.DRAFT);
            articleRmc_target.setPublished(null);
            articleRmc_target.setUrl(null);
            articleRmc_target.setRefChannel(null);
            articleRmc_target.setChannel(channel);
            articleRmc_target.setDeleteFlag(articleRmc.getDeleteFlag());
            articleRmc_target.setDeleteTime(articleRmc.getDeleteTime());
            
            Article article_target = new Article();
            Article article_source = articleRmc.getArticle();
            
            List<Content> contents = article_source.getContents();
            List<Content> contents_target = new ArrayList<Content>();
            for (Content content : contents){
                Content content_target = new Content();
                content_target.setDetail(content.getDetail());
                content_target.setPage(content.getPage());
                
                contents_target.add(content_target);
            }
            article_target.setContents(contents_target);
            
            article_target.setTitle(article_source.getTitle());
            article_target.setTitleStyle(article_source.getTitleStyle());
            article_target.setShortTitle(article_source.getShortTitle());
            article_target.setShortTitleStyle(article_source.getShortTitleStyle());
            article_target.setSubTitle(article_source.getSubTitle());
            article_target.setSubTitleStyle(article_source.getSubTitleStyle());
            article_target.setAuthor(article_source.getAuthor());
            article_target.setOrigin(article_source.getOrigin());
            article_target.setKeyword(article_source.getKeyword());
            article_target.setTag(article_source.getTag());
            article_target.setSummary(article_source.getSummary());
            article_target.setImage(article_source.getImage());
            article_target.setTopFlag(article_source.getTopFlag());
            article_target.setCommentFlag(article_source.getCommentFlag());
            article_target.setImageFlag(article_source.getImageFlag());
            article_target.setVideoFlag(article_source.getVideoFlag());
            article_target.setAnnexFlag(article_source.getAnnexFlag());
            article_target.setHotFlag(article_source.getHotFlag());
            article_target.setRecommendFlag(article_source.getRecommendFlag());
            article_target.setCopyFlag(true);
            article_target.setType(article_source.getType());
            article_target.setLinkAddr(article_source.getLinkAddr());
            
            articleRmc_target.setArticle(article_target);
            articleRmcDAO.persist(articleRmc_target);
            
            article_source.setCopyoutFlag(true);
            articleRmc.setArticle(article_source);
            articleRmcDAO.merge(articleRmc);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') " 
            +"or hasPermission(#channel,'WRITE') " 
            +"or hasPermission(#channel,'PUBLISH') " 
            +"or hasPermission(#channel,'CREATE') " 
            +"or hasPermission(#channel,'UPDATE') "
            +"or hasPermission(#channel,'DELETE') " 
            +"or hasPermission(#channel,'ADMIN') ")
    public void moveArticleRmcToChannel(ArticleRmc articleRmc, Channel channel){
        if (!articleRmc.getChannel().equals(channel)){
         articleRmc.setChannel(channel);
         articleRmcDAO.merge(articleRmc);
        }
    }
    
    public void setArticleRmcDAO(ArticleRmcDAO articleRmcDAO) {
        this.articleRmcDAO = articleRmcDAO;
    }

    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public void pubChannel(Integer channelId) throws ReleaseException{
    	if (channelId != null){
    		generatorService.generator(channelId);
    	}
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','PUBLISH') " 
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','CREATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','UPDATE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','DELETE') "
            + "or hasPermission(#channelId,'com.ewcms.core.site.model.Channel','ADMIN') ")
    public void reviewArticle(ArticleRmc articleRmc,Integer channelId, Integer review, String eauthor){
    	if (channelId != null && articleRmc != null && articleRmc.getChannel().getId() == channelId){
	    	if (review == 0 && articleRmc.getStatus() == ArticleRmcStatus.REVIEW){//通过
	    		articleRmc.setStatus(ArticleRmcStatus.PRERELEASE);
	    		articleRmc.getArticle().setEauthor(eauthor);
	    		articleRmc.getArticle().setEauthorReal(userService.getUserRealName());
	    	}else if (review == 1){//不通过
	    		articleRmc.setStatus(ArticleRmcStatus.REEDIT);
	    		articleRmc.getArticle().setEauthor(eauthor);
	    		articleRmc.getArticle().setEauthorReal(userService.getUserRealName());
	    	}
	    	articleRmcDAO.merge(articleRmc);
    	}
    }
}
