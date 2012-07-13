/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.dao.OrganDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.plugin.online.dao.MatterDAO;
import com.ewcms.plugin.online.dao.WorkingBodyDAO;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.plugin.online.model.WorkingBody;
import com.ewcms.publication.WebPublishFacable;
import com.ewcms.web.vo.TreeNode;

/**
 *
 * @author 吴智俊
 */
@Service
public class WorkingBodyService implements WorkingBodyServiceable {
	@Autowired
	private WorkingBodyDAO workingBodyDAO;
	@Autowired
	private MatterDAO matterDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private ArticleMainDAO articleMainDAO;
	@Autowired
	private WebPublishFacable webPublish;
	@Autowired
	private OrganDAO organDAO;
	
	private final static String ORGAN_NAME_BEGIN = "<font color='red'>[";
	private final static String ORGAN_NAME_END = "]</font>";
	private final static String CITIZEN_NAME_BEGIN = "<font color='blue'>[";
	private final static String CITIZEN_NAME_END = "]</font>";

	@Override
	public void addMatterToWorkingBody(Integer workingBodyId,List<Integer> matterIds, Integer channelId) {
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return;
		List<Matter> matters = workingBody.getMatters();
		
		for (Integer matterId : matterIds){
			Matter matter = workingBodyDAO.findMatterByMatterIdAndWorkingBodyId(matterId, workingBodyId, channelId);
			if (matter == null){
				matter = matterDAO.get(matterId);
				if (matter == null) continue;
				matters.add(matter);
			}
		}
		
		workingBody.setMatters(matters);
		workingBodyDAO.merge(workingBody);
	}

	@Override
	public Integer addWorkingBody(Integer parentId, String name, Integer channelId) {
		WorkingBody workingBody_parent = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(parentId, channelId);
		if (workingBody_parent == null) return null;
		WorkingBody workingBody = new WorkingBody();
		workingBody.setName(name);
		workingBody.setParent(workingBody_parent);
		Long sort = workingBodyDAO.findWorkingBodyMaxSortByWorkingBodyId(parentId, channelId) + 1;
		workingBody.setSort(sort);
		workingBody.setChannelId(channelId);
		workingBodyDAO.persist(workingBody);
		return workingBody.getId();
	}

	@Override
	public void delWorkingBody(Integer workingBodyId, Integer channelId) {
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody != null) workingBodyDAO.remove(workingBody);
	}

	@Override
	public void downWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId) {
		if (workingBodyId == null) return;
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return;
		Long sort = workingBody.getSort();
		Long maxSort = workingBodyDAO.findWorkingBodyMaxSort(channelId);
		if (sort == null || sort.intValue() >= maxSort.intValue()) return;
		WorkingBody workingBody_next = workingBodyDAO.findWorkingBodyBySort(parentId, sort + 1, channelId);
		if (workingBody_next == null) return;
		workingBody.setSort(sort + 1);
		workingBodyDAO.merge(workingBody);
		workingBody_next.setSort(sort);
		workingBodyDAO.merge(workingBody_next);
	}

	@Override
	public WorkingBody getWorkingBody(Integer workingBodyId, Integer channelId) {
		return workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
	}

	@Override
	public void removeMatterFromWorkingBody(Integer workingBodyId, Integer matterId, Integer channelId) {
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return;
		Matter matter = matterDAO.get(matterId);
		if (matter == null) return;
		List<Matter> matters = workingBody.getMatters();
		if (matters.isEmpty()) return;
		matters.remove(matter);
		workingBody.setMatters(matters);
		workingBodyDAO.merge(workingBody);

	}

	@Override
	public Integer renameWorkingBody(Integer workingBodyId, String name, Integer channelId) {
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		workingBody.setName(name);
		workingBodyDAO.merge(workingBody);
		return workingBody.getId();
	}

	@Override
	public void upWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId) {
		if (workingBodyId == null) return;
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return;
		Long sort = workingBody.getSort();
		if (sort == null || sort.intValue() <= 1) return;
		WorkingBody workingBody_prv = workingBodyDAO.findWorkingBodyBySort(parentId, sort - 1, channelId);
		if (workingBody_prv == null) return;
		workingBody.setSort(sort - 1);
		workingBodyDAO.merge(workingBody);
		workingBody_prv.setSort(sort);
		workingBodyDAO.merge(workingBody_prv);
	}

	@Override
	public TreeNode getWorkingBodyWindowTree(Integer channelId, Boolean isMatter) {
		if (channelId == null) return null;
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyParent(channelId);
		if (workingBody == null) return null;
		TreeNode treeNode = new TreeNode();
		convertMenuNode(treeNode, workingBody, channelId, isMatter);
		return treeNode;
	}
	
    private void convertMenuNode(TreeNode treeNode, WorkingBody workingBody, Integer channelId, Boolean isMatter) {
        treeNode.setId(workingBody.getId().toString());
        treeNode.setChecked(false);
        if (workingBody.getParent() == null || workingBody.getChildren().isEmpty()) {
        	treeNode.setState("open");
		} else {
			treeNode.setState("closed");
		}        
        String organName = "";
        List<Organ> organs = workingBody.getOrgans();
        if (organs != null && !organs.isEmpty()){
	        for (Organ organ : organs){
	        	organName = organName + "," + organ.getName();
	        }
	        organName = organName.substring(1);
        	organName = ORGAN_NAME_BEGIN + organName + ORGAN_NAME_END;
        }
        treeNode.setText(workingBody.getName() + organName);
        treeNode.getAttributes().put("type", "workingbody");
        treeNode.getAttributes().put("organ", organName);
        treeNode.getAttributes().put("citizen", "");
        if (workingBody.getParent() == null){
        	treeNode.setText(workingBody.getName());
        	treeNode.getAttributes().put("type", "component");
        	treeNode.getAttributes().put("organ", "");
        	treeNode.getAttributes().put("citizen", "");
        }
        if (treeNode.getChildren() == null) {
            treeNode.setChildren(new ArrayList<TreeNode>());
        }
        if (isMatter){
	        List<Matter> matters = workingBodyDAO.findMattersByWorkingBodyId(workingBody.getId(),channelId);
	        if (matters != null && !matters.isEmpty()){
		        for (Matter matter : matters){
		        	TreeNode child = new TreeNode();
		        	covertMatterNode(child,matter);
		        	treeNode.getChildren().add(child);
		        }
	        }
        }
        if (workingBody.getChildren() != null && !workingBody.getChildren().isEmpty()){
	        for (int i = 0; i < workingBody.getChildren().size(); i++) {
	            TreeNode child = new TreeNode();
	            treeNode.getChildren().add(child);
	            convertMenuNode(child, workingBody.getChildren().get(i), channelId, isMatter);
	        }
        }
    }
	
    private void covertMatterNode(TreeNode treeNode, Matter matter){
    	String organName = "";
    	Organ organ = matter.getOrgan();
    	if (organ != null) {
    		organName = ORGAN_NAME_BEGIN + organ.getName() + ORGAN_NAME_END;
    	}
        String citizenName = "";
        List<Citizen> citizens = matter.getCitizens();
        if (citizens != null && !citizens.isEmpty()){
	        for (Citizen citizen : citizens){
	        	citizenName = citizenName + "," + citizen.getName();
	        }
	        citizenName = citizenName.substring(1);
	        citizenName = CITIZEN_NAME_BEGIN + citizenName + CITIZEN_NAME_END;
        }
        treeNode.setId(matter.getId().toString());
    	treeNode.setChecked(false);
    	treeNode.setState("open");
    	treeNode.setText(matter.getName() + organName + citizenName);
    	treeNode.getAttributes().put("type", "matter");
    	treeNode.getAttributes().put("organ", organName);
    	treeNode.getAttributes().put("citizen", citizenName);
    }
    
    @Override
    public void addWorkingBodyRoot(Integer channelId){
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyParent(channelId);
		if (workingBody == null){
			Channel channel = channelDAO.get(channelId);
			if (channel == null) return;
			workingBody = new WorkingBody();
			workingBody.setChannelId(channelId);
			workingBody.setName(channel.getName());
			workingBodyDAO.persist(workingBody);
		}
    }
    
    @Override
    public Long addArticleRmcToWorkingBody(Article article, Integer workingBodyId, Integer channelId, Date published){
    	ArticleMain articleRmc = new ArticleMain();
    	
    	if (published != null){
    		articleRmc.getArticle().setPublished(published);
    	}
    	
    	clearStyleSpace(article);
		articleRmc.setArticle(article);
    	
		WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return null;
		List<ArticleMain> articleRmcs = workingBody.getArticleMains();
		if (articleRmcs == null || articleRmcs.isEmpty()) articleRmcs = new ArrayList<ArticleMain>();
		
		articleMainDAO.persist(articleRmc);
		
		articleRmcs.add(articleRmc);
		workingBody.setArticleMains(articleRmcs);
		
		workingBodyDAO.merge(workingBody);
		return articleRmc.getId();
    }
    
    @Override
    public Long updArticleRmcToWorkingBody(Long articleRmcId, Article article, Date published){
//    	ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
//		
//    	if (published != null){
//    		articleRmc.setPublished(published);
//    	}
//    	
//		clearStyleSpace(article);
//		
//		if (articleRmc.getStatus() == ArticleRmcStatus.RELEASE || articleRmc.getStatus() == ArticleRmcStatus.PRERELEASE){
//			articleRmc.setStatus(ArticleRmcStatus.REEDIT);
//		}
//		
//		Article article_old = articleRmc.getArticle();
//		if (article.getType() == ArticleStatus.GENERAL){
//			Integer current_version = 1;
//			List<ContentHistory> contentHistories = null;
//			if (article_old.getContentHistories().isEmpty()){
//				contentHistories = new ArrayList<ContentHistory>();
//			}else{
//				current_version = article_old.getContentHistories().get(0).getVersion() + 1;
//				contentHistories = article_old.getContentHistories();
//			}
//			
//			List<Content> contents = article_old.getContents();
//	
//			ContentHistory contentHistory = null;
//			for (Content content : contents){
//				contentHistory = new ContentHistory();
//			
//				contentHistory.setDetail(content.getDetail());
//				contentHistory.setPage(content.getPage());
//				contentHistory.setVersion(current_version);
//				contentHistory.setHistoryTime(new Date(Calendar.getInstance().getTime().getTime()));
//				
//				contentHistories.add(contentHistory);
//			}
//			article.setContentHistories(contentHistories);
//		}else if (article.getType() == ArticleStatus.TITLE){
//			if (article_old.getContentHistories() != null && !article_old.getContentHistories().isEmpty()){
//				article.setContentHistories(article_old.getContentHistories());
//			}
//			if (article_old.getContents() != null && !article_old.getContents().isEmpty()){
//				article.setContents(article_old.getContents());
//			}
//		}
//		articleRmc.setArticle(article);
//		articleRmc.setModified(new Date(Calendar.getInstance().getTime().getTime()));
//		
//		articleRmcDAO.merge(articleRmc);
//		return articleRmc.getId();
        return 0L;
    }
    
    private void clearStyleSpace(Article article){
//		if (article.getTitleStyle() != null && article.getTitleStyle().length() > 0){
//			article.setTitleStyle(article.getTitleStyle().trim());
//		}
//		if (article.getShortTitleStyle() != null && article.getShortTitleStyle().length() > 0){
//			article.setShortTitleStyle(article.getShortTitleStyle().trim());
//		}
//		if (article.getSubTitleStyle() != null && article.getSubTitleStyle().length() > 0){
//			article.setSubTitleStyle(article.getSubTitleStyle().trim());
//		}
	}
    
    @Override
    public void delArticleRmcToWorkingBody(Integer workingBodyId, Long articleRmcId, Integer channelId){
    	WorkingBody workingBody = workingBodyDAO.findWorkingBodyByWorkingBodyIdAndChannelId(workingBodyId, channelId);
		if (workingBody == null) return;
		List<ArticleMain> articleRmcs = workingBody.getArticleMains();
		if (articleRmcs == null || articleRmcs.isEmpty()) return;
		ArticleMain articleRmc = articleMainDAO.get(new Long(articleRmcId));
		articleRmcs.remove(articleRmc);
		workingBody.setArticleMains(articleRmcs);
		workingBodyDAO.merge(workingBody);
		articleMainDAO.removeByPK(new Long(articleRmcId));
    }
    
    @Override
    public void addOrganToWorkingBody(Integer workingBodyId, List<Integer> organIds){
    	if (workingBodyId == null) return;
    	if (organIds == null || organIds.isEmpty()) return;
    	WorkingBody workingBody = workingBodyDAO.get(workingBodyId);
    	List<Organ> organs = new ArrayList<Organ>();
    	for (Integer organId : organIds){
    		Organ organ = organDAO.get(organId);
    		organs.add(organ);
    	}
    	workingBody.setOrgans(organs);
    	workingBodyDAO.merge(workingBody);
    }
    
    @Override
    public void removeOrganFromWorkingBOdy(Integer workingBodyId){
    	if (workingBodyId == null) return;
    	WorkingBody workingBody = workingBodyDAO.get(workingBodyId);
    	workingBody.setOrgans(null);
    	workingBodyDAO.merge(workingBody);
    }
    
	@Override
	public Boolean preReleaseArticleRmc(Long articleRmcId, Integer channelId) {
		ArticleMain articleRmc = articleMainDAO.get(new Long(articleRmcId));
		if (articleRmc.getArticle().getStatus() == Article.Status.DRAFT || articleRmc.getArticle().getStatus()== Article.Status.REEDIT){
			articleRmc.getArticle().setStatus(Article.Status.PRERELEASE);
			if (articleRmc.getArticle().getPublished() == null){
				articleRmc.getArticle().setPublished(new Date(Calendar.getInstance().getTime().getTime()));
			}
			articleMainDAO.merge(articleRmc);
			try {
				webPublish.publishChannel(channelId, false, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean moveArticleRmcToWorkingBody(List<Long> articleRmcIds, Integer workingBodyId, Integer moveWorkingBodyId){
		WorkingBody workingBody = workingBodyDAO.get(workingBodyId);
		WorkingBody moveWorkingBody = workingBodyDAO.get(moveWorkingBodyId);
		if (workingBody == null || moveWorkingBody == null) return false;
		List<ArticleMain> articleRmcs = workingBody.getArticleMains();
		if (articleRmcs == null || articleRmcs.isEmpty()) return false;
		List<ArticleMain> moveArticleRmcs = moveWorkingBody.getArticleMains();
		if (moveArticleRmcs == null || moveArticleRmcs.isEmpty()) moveArticleRmcs = new ArrayList<ArticleMain>();
		ArticleMain articleRmc = null;
		for (Long articleRmcId : articleRmcIds){
			articleRmc = articleMainDAO.get(articleRmcId);
			articleRmcs.remove(articleRmc);
			moveArticleRmcs.add(articleRmc);
		}
		workingBody.setArticleMains(articleRmcs);
		moveWorkingBody.setArticleMains(moveArticleRmcs);
		workingBodyDAO.merge(workingBody);
		workingBodyDAO.merge(moveWorkingBody);
		return true;	
	}
}
