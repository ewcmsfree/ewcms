package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.manager.vo.VisitorVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * 忠诚度分析
 * 
 * @author wu_zhijun
 *
 */
public class LoyaltyAction extends VisitBaseAction {

	private static final long serialVersionUID = -5392266304306297302L;

	@Autowired
	private VisitFacable visitFac;
	
	private Long freq;
	
	public Long getFreq() {
		return freq;
	}

	public void setFreq(Long freq) {
		this.freq = freq;
	}

	/*========================== 访问频率 =================================*/
	public void frequencyTable(){
		List<LoyaltyVo> list = visitFac.findFrequencyTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void frequencyReport(){
		Struts2Util.renderHtml(visitFac.findFrequencyReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 访问频率 时间趋势 =================================*/
	public String frequencyTrend(){
		return SUCCESS;
	}
	
	public void frequencyTrendReport(){
		Struts2Util.renderHtml(visitFac.findFrequencyTrendReport(getStartDate(), getEndDate(), getFreq(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 访问深度 =================================*/
	public void depthTable(){
		List<LoyaltyVo> list = visitFac.findDepthTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void depthReport(){
		Struts2Util.renderHtml(visitFac.findDepthReport(getStartDate(), getEndDate(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
	
	/*========================== 访问频率 时间趋势 =================================*/
	public String depthTrend(){
		return SUCCESS;
	}
	
	public void depthTrendReport(){
		Struts2Util.renderHtml(visitFac.findDepthTrendReport(getStartDate(), getEndDate(), getFreq(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}

	/*========================== 访问频率 =================================*/
	public void visitorTable(){
		List<VisitorVo> list = visitFac.findVisitorTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void visitorReport(){
		Struts2Util.renderHtml(visitFac.findVisitorReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}


	/*========================== 访问频率 =================================*/
	public void stickTimeTable(){
		List<VisitorVo> list = visitFac.findStickTimeTable(getStartDate(), getEndDate(), getSiteId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}
	
	public void stickTimeReport(){
		Struts2Util.renderHtml(visitFac.findStickTimeReport(getStartDate(), getEndDate(), getLabelCount(), getSiteId()), "encoding:UTF-8","no-cache:false");
	}
}
