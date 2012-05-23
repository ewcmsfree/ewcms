package com.ewcms.plugin.visit.manager.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.visit.model.Visit;

@Repository
public class VisitDAO extends JpaDAO<Long, Visit> {
}
