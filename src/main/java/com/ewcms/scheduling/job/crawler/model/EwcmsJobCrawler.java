package com.ewcms.scheduling.job.crawler.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ewcms.crawler.model.Gather;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 
 * @author wuzhijun
 *
 */
@Entity
@Table(name = "job_crawler_gather")
@PrimaryKeyJoinColumn(name = "info_id")
public class EwcmsJobCrawler extends JobInfo {

	private static final long serialVersionUID = 5330778673168838760L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "gather_id")
	private Gather gather;

	public Gather getGather() {
		return gather;
	}

	public void setGather(Gather gather) {
		this.gather = gather;
	}
}
