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
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class LeaderDAO extends JpaDAO<Integer, Leader> {
    @SuppressWarnings("unchecked")
	public Integer findLeaderMaxSort(Integer channelId){
    	String hql = "SELECT MAX(a.sort) FROM Leader AS a Where a.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }
    
    @SuppressWarnings("unchecked")
    public Leader findLeaderBySort(Integer sort, Integer channelId){
    	String hql = "FROM Leader AS a WHERE a.sort=? And a.channelId=?";
    	List<Leader> list = this.getJpaTemplate().find(hql,sort, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Leader> findLeaderAllOrderBySort(Integer channelId){
    	String hql = "FROM Leader AS a Where a.channelId=? ORDER BY a.sort";
    	List<Leader> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty()) return new ArrayList<Leader>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<LeaderChannel> findLeaderChannelByLeaderId(Integer leaderId, Integer channelId){
    	String hql = "Select c From Leader As l Right Join l.leaderChannels As c Where l.id=? And l.channelId=? And c.channelId=? Order By l.sort";
    	List<LeaderChannel> list = this.getJpaTemplate().find(hql, leaderId, channelId, channelId);
    	if (list.isEmpty()) return null;
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Leader findLeaderByLeaderIdAndChannelId(Integer leaderId, Integer channelId){
    	String hql = "From Leader As l Where l.id=? And l.channelId=?";
    	List<Leader> list = this.getJpaTemplate().find(hql, leaderId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Leader findLeaderByLeaderNameAndChannelId(String leaderName, Integer channelId){
    	String hql = "From Leader As l Where l.name=? And l.channelId=?";
    	List<Leader> list = this.getJpaTemplate().find(hql, leaderName, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Leader findLeaderByLeaderIdAndLeaderNameAndChannelId(Integer leaderId, String leaderName, Integer channelId){
    	String hql = "From Leader As l Where l.id!=? And l.name=? And l.channelId=?";
    	List<Leader> list = this.getJpaTemplate().find(hql, leaderId, leaderName, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
