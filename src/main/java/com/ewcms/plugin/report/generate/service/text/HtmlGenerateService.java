/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.service.text;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.BaseRuntimeException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

/**
 * html格式报表
 * 
 * @author 吴智俊
 */
public class HtmlGenerateService extends BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(HtmlGenerateService.class);

	protected byte[] generate(JasperPrint jasperPrint,
			HttpServletResponse response, HttpServletRequest request) {
		try {
//			Integer pageIndex = 0;
//			Integer lastPageIndex = 0;
//			if (jasperPrint.getPages() != null) {
//				lastPageIndex = jasperPrint.getPages().size() - 1;
//			}
//
//			try {
//				String pageStr = request.getParameter("page");
//				pageIndex = Integer.valueOf(pageStr);
//			} catch (Exception e) {
//
//			}
//			if (pageIndex < 0) {
//				pageIndex = 0;
//			}
//			if (pageIndex > lastPageIndex) {
//				pageIndex = lastPageIndex;
//			}

			String HTML_HEADER = ""
					+ "<html>\n"
					+ "	<head>\n"
					+ "		<title>HTML报表</title>\n"
					+ "		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>\n"
					+ " 	<meta http-equiv='imagetoolbar' content='no'/>\n"
					+ "		<style type='text/css'>\n"
					+ "			a {text-decoration: none}\n"
					+ "		</style>\n"
//					+ "		<script language='javascript' type='text/javascript'>"
//					+ "			function pageJump(page){"
//					+ "				document.getElementById('page').value = page;"
//					+ "				document.forms[0].submit();"
//					+ "			}"
//					+ "		</script>"
					+ "	</head>\n"
					+ "	<body text='#000000' link='#000000' alink='#000000' vlink='#000000'>\n"
//					+ "    <form action='build.do'>"
					+ "		<table width='100%' cellpadding='0' cellspacing='0' border='0'>\n"
				;

//			if (lastPageIndex > 0){
//				HTML_HEADER += ""
//					+ "		<tr>\n"
//					+ "			<td width='50%'>&nbsp;</td>\n"
//					+ "			<td align='left'>\n"
//					+ "				<hr size='1' color='#000000'>\n"
//					+ "				<table width='100%' cellpadding='0' cellspacing='0' border='0'>\n"
//					+ "					<tr>\n"
//				;
//				
//				if (pageIndex > 0) {
//					HTML_HEADER += ""
//							+ "<td align='center'><button name='first' value='first' onclick='pageJump(0)'>第一页</button></td>\n"
//							+ "<td align='center'><button name='previous' value='previous' onclick='pageJump("
//							+ (pageIndex - 1) + ")'>上一页</button></td>\n";
//				} else {
//					HTML_HEADER += ""
//							+ "<td align='center'><button name='first' value='first'>第一页</button></td>\n"
//							+ "<td align='center'><button name='previous' value='previous'>上一页</button></td>\n";
//				}
//				if (pageIndex < lastPageIndex) {
//					HTML_HEADER += ""
//							+ "<td align='center'><button name='next' value='next' onclick='pageJump("
//							+ (pageIndex + 1)
//							+ ")'>下一页</button></td>\n"
//							+ "<td align='center'><button name='last' value='last' onclick='pageJump("
//							+ (lastPageIndex) + ")'>最后页</button></td>\n";
//				} else {
//					HTML_HEADER += ""
//							+ "<td align='center'><button name='next' value='next'>下一页</button></td>\n"
//							+ "<td align='center'><button name='last' value='last'>最后页</button></td>\n";
//				}
//				HTML_HEADER += ""
//					+ "					</tr>\n" 
//					+ "				</table>\n"
//					+ "				<hr size='1' color='#000000'>\n" 
//					+ "			</td>\n"
//					+ "			<td width='50%'>&nbsp;</td>\n" 
//					+ "		</tr>\n"
//				;
//			}

			HTML_HEADER += "" 
					+ "		<tr>\n" 
					+ "			<td width='50%'>&nbsp;</td>\n"
					+ "			<td align='center' valign='top'>\n";

			String HTML_FOOTER = "" 
					+ "			</td>\n"
					+ "			<td width='50%'>&nbsp;</td>\n" 
					+ "		</tr>\n"
					+ "	</table>\n"
//					+ " <input type='hidden' name='page' id='page'/>"
//					+ " <input type='hidden' name='reportType' value='text'/>"
//					+ " </form>"
					+ "	</body>\n" 
					+ "</html>\n";
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			JRHtmlExporter exporter = new JRHtmlExporter();

			exporter
					.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,
					HTML_HEADER);
			// 翻页的处理
			exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
					"<br style='page-break-before:always;'>");
			// 不显示周边不相关的图片
			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.FALSE);
			// 删除记录最下面的空行
			exporter.setParameter(
					JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);	

			exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,
					jasperPrint);
			exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
					"image?image=");
			
//			if (lastPageIndex>0){
//				exporter.setParameter(JRExporterParameter.PAGE_INDEX, Integer.valueOf(pageIndex));
//			}
			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,
					HTML_FOOTER);

			exporter.exportReport();

			byte[] bytes = byteArrayOutputStream.toByteArray();

			if (response != null) {
				response.setContentLength(bytes.length);
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
			}
			return bytes;
		} catch (JRException e) {
			logger.error("Html Generate Exception", e);
			throw new BaseRuntimeException(e.toString());
		}
	}

}
