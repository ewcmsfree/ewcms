package com.ewcms.plugin.datasource.manager.web;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.datasource.generate.factory.DataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.factory.init.EwcmsDataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.datasource.manager.BaseDSFacable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author wuzhijun
 *
 */
public class ConnectDSAction extends ActionSupport{

	private static final long serialVersionUID = -8419791411417225073L;
	
	@Autowired
	private BaseDSFacable baseDSFac;
	@Autowired
	private EwcmsDataSourceFactoryable ewcmsDataSourceFactory;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String test() {
		EwcmsDataSourceServiceable service = null;
		Connection con = null;
		String testResults = "测试数据库连接失败,请确认填写的内容正确!";
		try{
			BaseDS alqcDataSource = baseDSFac.findByBaseDS(id);

			DataSourceFactoryable factory = (DataSourceFactoryable) ewcmsDataSourceFactory.getBean(alqcDataSource.getClass());
			service = factory.createService(alqcDataSource);
			con = service.openConnection();
			
			if (!con.isClosed())
				testResults = "测试数据库连接正确,您可以在以后的程序中使用!";
		}catch(Exception e){
		}finally{
			try{
				if (con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
			}
			try{
				if (service != null){
					service.closeConnection();
					service = null;
				}
			}catch(Exception e){
			}
		}
		Struts2Util.renderText(testResults);
		return NONE;
	}
}
