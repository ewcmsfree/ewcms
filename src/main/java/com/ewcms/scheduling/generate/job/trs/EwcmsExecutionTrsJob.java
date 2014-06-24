/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.trs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EnterpriseArticle;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.scheduling.generate.job.BaseEwcmsExecutionJob;

/**
 * @author 吴智俊
 */
public class EwcmsExecutionTrsJob extends BaseEwcmsExecutionJob {

	private static final String BUNDLE_NAME = "com.ewcms.scheduling.generate.job.trs.mysql";  
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String TABLE = "table";
	
	@Override
	protected void jobExecute(Long jobId) throws Exception {
		try{
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);   
			String table = bundle.getString(TABLE);
			
			Connection conn = getConnection(bundle.getString(DRIVER), bundle.getString(URL), bundle.getString(USER), bundle.getString(PASSWORD));
			Statement stmt = conn.createStatement();
			String sql = "";
			stmt.execute("DELETE FROM " + table);
			String site = "xunyangqu";
			
			ParticularFacable particularFac = getParticularFac();
			
			String prefixUrl = "http://218.65.14.14:8090/ewcms_project/";
			String bigclassid = "1";
			List<ProjectArticle> pas = particularFac.findProjectArticleAll();
			if (pas != null && !pas.isEmpty()){
				for (ProjectArticle pa : pas){
					try{
						String metadataid = String.valueOf(pa.getId());
						String title =pa.getProjectBasic().getName();
						String content = pa.getContent().getDetail();
						String docpuburl = prefixUrl + "projectdetail.do?articleId=" + metadataid;
						String xmbh = pa.getProjectBasic().getCode();
						sql = "INSERT INTO " + table + "(BIGCLASSID, TITLE, CONTENT, DOCPUBURL, METADATAID, SITE, XMBH) "
						      + " VALUES('" + bigclassid + "','" + title + "','" + content + "','" + docpuburl + "','" + metadataid + "','" + site + "','" + xmbh + "')";
						stmt.executeUpdate(sql);
					}catch(Exception e){
					}
				}
			}
			
			bigclassid = "2";
			List<EnterpriseArticle> eas = particularFac.findEnterpriseArticleAll();
			if (eas != null && !eas.isEmpty()){
				for (EnterpriseArticle ea : eas){
					try{
						String metadataid = String.valueOf(ea.getId());
						String title = ea.getEnterpriseBasic().getName();
						String content = ea.getContent().getDetail();
						String docpuburl = prefixUrl + "enterprisedetail.do?articleId=" + metadataid;
						String xmbh = ea.getEnterpriseBasic().getYyzzzch();
						sql = "INSERT INTO " + table + "(BIGCLASSID, TITLE, CONTENT, DOCPUBURL, METADATAID, SITE, XMBH) "
							      + " VALUES('" + bigclassid + "','" + title + "','" + content + "','" + docpuburl + "','" + metadataid + "','" + site + "','" + xmbh + "')";
						stmt.executeUpdate(sql);
					}catch(Exception e){
					}
				}
			}
			
			bigclassid = "3";
			List<EmployeArticle> mes = particularFac.findEmployeArticleAll();
			if (mes != null && !mes.isEmpty()){
				for (EmployeArticle me : mes){
					try{
						String metadataid = String.valueOf(me.getId());
						String title = me.getEmployeBasic().getName();
						String content = me.getContent().getDetail();
						String docpuburl = prefixUrl + "employedetail.do?articleId=" + metadataid;
						String xmbh = me.getEmployeBasic().getCardCode();
						sql = "INSERT INTO " + table + "(BIGCLASSID, TITLE, CONTENT, DOCPUBURL, METADATAID, SITE, XMBH) "
							      + " VALUES('" + bigclassid + "','" + title + "','" + content + "','" + docpuburl + "','" + metadataid + "','" + site + "','" + xmbh + "')";
						stmt.executeUpdate(sql);
					}catch(Exception e){
					}
				}
			}
			
			stmt.close();
			conn.close();
		}catch(MissingResourceException e){
		}catch(ClassNotFoundException e){
		}catch(SQLException e){
		}
	}

	@Override
	protected void jobClear() {
	}

	private ParticularFacable getParticularFac() {
		return (ParticularFacable) applicationContext.getBean("particularFac");
	}
	
	private Connection getConnection(String driver, String url, String user, String password) throws ClassNotFoundException, SQLException{
//		String driver = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://192.168.18.38:3306/xyqgc";
//		String user = "root";
//		String password = "123456";
		Class.forName(driver);
		return DriverManager.getConnection(url, user, password);
	}
}
