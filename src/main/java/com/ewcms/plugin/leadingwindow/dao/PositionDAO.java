/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.Position;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class PositionDAO extends JpaDAO<Integer, Position> {
    @SuppressWarnings("unchecked")
	public Integer findPositionMaxSort(Integer channelId){
    	String hql = "Select Max(p.sort) From Position As p Where p.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }
    
    @SuppressWarnings("unchecked")
	public Integer findPositionMaxSortByPositionId(Integer positionId, Integer channelId){
    	String hql = "Select Max(c.sort) From Position As p Right Join p.children As c Where p.id=? And p.channelId=? And c.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, positionId, channelId, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }

    @SuppressWarnings("unchecked")
	public Leader findLeaderByLeaderIdAndPositionId(Integer leaderId, Integer positionId, Integer channelId){
    	String hql = "Select l From Position As p Left Join p.leaders As l Where l.id=? And p.id=? And p.channelId=? And l.channelId=?";
    	List<Leader> list = this.getJpaTemplate().find(hql, leaderId, positionId, channelId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Position findPositionBySort(Integer parentId, Integer sort, Integer channelId){
    	String hql = "Select p From Position As p Left Join p.parent As a Where a.id=? And p.sort=? And p.channelId=?";
    	List<Position> list = this.getJpaTemplate().find(hql, parentId, sort, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Position findPositionParent(Integer channelId){
    	String hql = "From Position As p Where p.parent is null And p.channelId=?  Order By p.sort";
    	List<Position> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Leader> findLeadersByPositionId(Integer positionId, Integer channelId){
    	String hql = "Select l From Position As p Right Join p.leaders As l Where p.id=? And p.channelId=? And l.channelId=? Order By l.sort";
    	List<Leader> list = this.getJpaTemplate().find(hql,positionId, channelId, channelId);
    	if (list.isEmpty()) return null;
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Position findPositionByPositionIdAndChannelId(Integer positionId, Integer channelId){
    	String hql = "From Position As p Where p.id=? And p.channelId=?";
    	List<Position> list = this.getJpaTemplate().find(hql, positionId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
