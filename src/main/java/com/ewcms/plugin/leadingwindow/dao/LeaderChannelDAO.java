/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class LeaderChannelDAO extends JpaDAO<Integer, LeaderChannel> {
	@SuppressWarnings("unchecked")
	public Boolean findByName(Integer leaderId, String leaderChannelName, Integer channelId){
		String hql = "Select c From Leader o Left Join o.leaderChannels c Where o.id=? AND c.name=? And o.channelId=? And c.channelId=?";
		List<LeaderChannel> list = this.getJpaTemplate().find(hql,leaderId,leaderChannelName, channelId, channelId);
		if (list.isEmpty()) return true;
		return false;
	}
	
    @SuppressWarnings("unchecked")
    public LeaderChannel findLeaderChannelBySort(Integer leaderId, Integer sort, Integer channelId){
    	String hql = "Select a FROM Leader As l Right Join l.leaderChannels AS a Where l.id=? And a.sort=? And a.channelId=?";
    	List<LeaderChannel> list = this.getJpaTemplate().find(hql, leaderId, sort, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }

    @SuppressWarnings("unchecked")
	public List<LeaderChannel> findLeaderChannelByLeaderIdOrderBySort(Integer leaderId, Integer channelId){
    	String hql = "Select a From Leader As o Left Join o.leaderChannels AS a Where o.id=? And o.channelId=? And a.channelId=? ORDER BY a.sort";
    	List<LeaderChannel> list = this.getJpaTemplate().find(hql, leaderId, channelId, channelId);
    	if (list.isEmpty()) return new ArrayList<LeaderChannel>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Integer findLeaderChannelMaxSort(Integer leaderId, Integer channelId){
    	String hql = "Select MAX(c.sort) FROM Leader As l Left Join l.leaderChannels AS c Where l.id=? And c.channelId=? And l.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, leaderId, channelId, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }

    @SuppressWarnings("unchecked")
	public List<ArticleRmc> findArticleRmcByLeaderChannelId(Integer leaderChannelId, Integer channelId){
    	String hql = "Select r From LeaderChannel As c Right Join c.articleRmcs As r Where c.id=? And c.channelId=? And r.channelId=? Order By r.id Desc";
    	List<ArticleRmc> list = this.getJpaTemplate().find(hql, leaderChannelId, channelId, channelId);
    	if (list.isEmpty()) return new ArrayList<ArticleRmc>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Boolean findIsArticleRmcByArticleRmcIdAndLeaderChannelId(Integer leaderChannelId, Integer articleRmcId, Integer channelId){
    	String hql = "Select r From LeaderChannel As c Right Join c.articleRmcs As r Where c.id=? And r.id=? And c.channelId=? And r.channelId=?";
    	List<ArticleRmc> list = this.getJpaTemplate().find(hql, leaderChannelId, articleRmcId, channelId, channelId);
    	if (list.isEmpty()) return true;
    	return false;
    }
    
    @SuppressWarnings("unchecked")
	public LeaderChannel findLeaderChannelByLeaderChannelIdAndChannelId(Integer leaderChannelId, Integer channelId){
    	String hql = "From LeaderChannel As c Where c.id=? And c.channelId=?";
    	List<LeaderChannel> list = this.getJpaTemplate().find(hql, leaderChannelId, channelId);
    	if (list.isEmpty()) return  null;
    	return list.get(0);
    }
}
