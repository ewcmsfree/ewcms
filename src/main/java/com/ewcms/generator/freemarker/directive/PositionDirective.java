/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.freemarker.FreemarkerUtil;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 指定生成页面所在位置标签
 * 
 * 
 * @author wangwei
 */
public class PositionDirective implements TemplateDirectiveModel {
    
    private final static Logger logger = LoggerFactory.getLogger(PositionDirective.class);

    private static final String NAME_PARAM_NAME = "name";
    private static final String MARK_PARAM_NAME = "mark";
    private static final String DEFAULT_MARK = "&gt;";

    private String nameParam = NAME_PARAM_NAME;
    private String markParam = MARK_PARAM_NAME;

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {

        List<Channel> levels = getChannelLevels(getChannel(env));

        if (EmptyUtil.isArrayNotEmpty(loopVars)) {
            loopVars[0] = env.getObjectWrapper().wrap(levels);
            if (EmptyUtil.isNull(body)) {
                logger.warn("Position body is null");
            } else {
                body.render(env.getOut());
            }
        } else if (EmptyUtil.isNotNull(body)) {
            Writer writer = env.getOut();
            String name = getNameValue(params);
            String mark = getMarkValue(params);
            for (Iterator<Channel> iterator = levels.iterator(); iterator.hasNext();) {
                Channel channel = iterator.next();
                FreemarkerUtil.setVariable(env, name, channel);
                body.render(writer);
                if (!iterator.hasNext()) {
                    writer.write(mark);
                }
                writer.flush();
                FreemarkerUtil.removeVariable(env, name);
            }
        } else {
            StringBuilder builder = new StringBuilder();
            String mark = getMarkValue(params);
            for (Iterator<Channel> iterator = levels.iterator(); iterator.hasNext();) {
                Channel channel = iterator.next();
                builder.append("<a href=\"").append(channel.getAbsUrl()).append("\">");
                builder.append(channel.getName());
                builder.append("</a>");
                if (!iterator.hasNext()) {
                    builder.append(mark);
                }
            }
            Writer writer = env.getOut();
            writer.write(builder.toString());
            writer.flush();
        }
    }

    /**
     * 得到当前频道
     * 
     * @param env
     *            Freemarker 环境对象
     * @return
     * @throws TemplateException
     */
    private Channel getChannel(final Environment env) throws TemplateException {
        Channel channel = (Channel) FreemarkerUtil.getBean(env,GlobalVariable.CHANNEL.toString());
        if (EmptyUtil.isNull(channel)) {
            logger.error("Current channel is null");
            throw new TemplateModelException("Current channel is null");
        }
        return channel;
    }

    /**
     * 得到各级频道
     * 
     * @param channel
     *            当前频道
     * @return
     */
    private List<Channel> getChannelLevels(final Channel channel) {
        List<Channel> levels = new ArrayList<Channel>();
        for (Channel c = channel; EmptyUtil.isNotNull(c.getParent()); c = c.getParent()) {   
            levels.add(0, c);
        }
        return levels;
    }

    /**
     * 得到变量名参数值
     * 
     * @param params
     *            标签参数
     * @return
     * @throws TemplateModelException
     */
    @SuppressWarnings("rawtypes")
    private String getNameValue(Map params) throws TemplateException {
        String value = FreemarkerUtil.getString(params, nameParam);
        if (EmptyUtil.isNull(value)) {
            return GlobalVariable.CHANNEL.toString();
        }
        return value;
    }

    /**
     * 得到分割符
     * 
     * @param params
     *            标签参数
     * @return 分割符
     * @throws TemplateException
     */
    @SuppressWarnings("rawtypes")
    private String getMarkValue(Map params) throws TemplateException {
        String value = FreemarkerUtil.getString(params, markParam);
        if (EmptyUtil.isNull(value)) {
            return DEFAULT_MARK;
        }
        return value;
    }

    /**
     * 频道变量名的参数名设置
     * 
     * @param nameParam
     *            参数名
     */
    public void setNameParam(String nameParam) {
        this.nameParam = nameParam;
    }

    /**
     * 频道间分割符参数名设置
     * 
     * @param markParam
     *            参数名
     */
    public void setMarkParam(String markParam) {
        this.markParam = markParam;
    }
}
