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

// import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;

/**
 * XML格式报表
 * 
 * @author 吴智俊
 */
public class XmlGenerateService extends BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(XmlGenerateService.class);

    protected byte[] generate(JasperPrint jasperPrint,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // JasperExportManager.exportReportToXmlStream(jasperPrint, out);
            JRXmlExporter exporter = new JRXmlExporter();
            exporter.setParameter(JRXmlExporterParameter.JASPER_PRINT,
                    jasperPrint);
            exporter.setParameter(JRXmlExporterParameter.OUTPUT_STREAM,
                    byteArrayOutputStream);
            exporter.exportReport();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            if (response != null) {
                response.setContentLength(bytes.length);
                response.setContentType("application/xml");
            }
            return bytes;
        } catch (Exception e) {
            logger.error("Xml Generate Exception", e);
            throw new BaseRuntimeException(e.toString());
        }
    }
}
