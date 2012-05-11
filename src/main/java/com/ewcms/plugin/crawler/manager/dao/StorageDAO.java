package com.ewcms.plugin.crawler.manager.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.crawler.model.Storage;

@Repository
public class StorageDAO extends JpaDAO<Long, Storage> {
}
