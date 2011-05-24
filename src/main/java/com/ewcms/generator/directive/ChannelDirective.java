/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author wangwei
 */
@Service("direcitve.channel")
public class ChannelDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_VALUE = "value";
    private static final String PARAM_NAME_ROW = "row";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_CHILD="child";

    @Autowired
    protected GeneratorDAOable dao;

    public void setGeneratorDAO(GeneratorDAOable dao) {
        this.dao = dao;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        boolean debug = DirectiveUtil.isDebug(env);
        try {
            if (EmptyUtil.isNull(body)) {
                throw new DirectiveException("没有显示内容");
            }
            boolean isChild = getParamNameChild(params,PARAM_NAME_CHILD);
            List<Channel> channels = showChannel(env,params, PARAM_NAME_VALUE, isChild,debug);
            int row = getParamRowValue(params, PARAM_NAME_ROW);
            row = isShowAll(row) ? channels.size() : row;
            if(row != channels.size()){
                channels = channels.subList(0, row);
            }
            if (EmptyUtil.isArrayEmpty(loopVars)) {
                String name = getParamNameValue(params, PARAM_NAME_NAME);
                Writer writer = env.getOut();
                for (int i = 0 ; i < channels.size(); i++) {
                    Channel channel = channels.get(i);
                    DirectiveUtil.setVariable(env, name, channel);
                    DirectiveUtil.setVariable(env, DirectiveVariable.ListIndex.toString(), i + 1);
                    body.render(writer);
                    DirectiveUtil.removeVariable(env, DirectiveVariable.ListIndex.toString());
                    DirectiveUtil.removeVariable(env, name);
                }
                writer.flush();
            } else {
                loopVars[0] = env.getObjectWrapper().wrap(channels);
                body.render(env.getOut());
            }
       } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    /**
     * 得到当前站点
     *
     * @param env Environment
     * @return
     * @throws TemplateModelException
     */
    private Site getCurrentSite(final Environment env) throws TemplateModelException {
        String current = DirectiveVariable.CurrentSite.toString();
        Site site = (Site) DirectiveUtil.getBean(env, current);
        Assert.notNull(site);
        return site;
    }

    /**
     * 得到当前频道
     *
     * @param env Environment
     * @return
     * @throws TemplateModelException
     */
    private Channel getCurrentChannel(final Environment env) throws TemplateModelException {
        String currentChannel = DirectiveVariable.CurrentChannel.toString();
        Channel channel = (Channel) DirectiveUtil.getBean(env, currentChannel);
        Assert.notNull(channel);
        return channel;
    }

    /**
     * 得到显示频道
     * 
     * @param env
     * @param params
     * @param name   参数名称
     * @param isChild 显示子频道
     * @return
     * @throws TemplateModelException
     * @throws DirectiveException
     */
    private List<Channel> showChannel(final Environment env, final Map params,
            final String name, final boolean isChild,final boolean debug)
            throws TemplateModelException, DirectiveException {

        Integer id = DirectiveUtil.getInteger(params, name);
        if (EmptyUtil.isNotNull(id)) {
            return filterChannel(getShowChannel(id, isChild),debug);
        }

        Site site = getCurrentSite(env);
        String url = DirectiveUtil.getString(params, name);
        if (EmptyUtil.isStringNotEmpty(url)) {
            url = url.trim();
            return filterChannel(getShowChannel(site.getId(), url, isChild),debug);
        }

        Channel channel = (Channel) DirectiveUtil.getBean(params, name);
        if (EmptyUtil.isNotNull(channel)) {
            return filterChannel(getShowChannel(id, isChild),debug);
        }

        Object[] objects = (Object[]) DirectiveUtil.getSequence(params, name);
        if (EmptyUtil.isNotNull(objects)) {
            return filterChannel(getShowChannel(site.getId(), objects, isChild),debug);
        }

        channel = getCurrentChannel(env);
        return filterChannel(getShowChannel(channel, isChild),debug);
    }

    private List<Channel> getShowChannel(final int siteId, final Object[] values, final boolean isChild) {
        List<Channel> channels = new ArrayList<Channel>();
        for (Object value : values) {
            if (value instanceof Integer) {
                Integer id = (Integer) value;
                channels.addAll(getShowChannel(id, isChild));
            }
            if (value instanceof String) {
                String url = (String) value;
                channels.addAll(getShowChannel(siteId, url, isChild));
            }
        }
        return channels;
    }

    private List<Channel> getShowChannel(final int id, final boolean isChild) {
        Channel channel = dao.getChannel(id);
        return getShowChannel(channel, isChild);
    }

    private List<Channel> getShowChannel(final int siteId, final String url, final boolean isChild) {
        Channel channel = dao.getChannelByUrlOrDir(siteId, url);
        return getShowChannel(channel, isChild);
    }

    private List<Channel> getShowChannel(final Channel channel, final boolean isChild) {
        List<Channel> channels = new ArrayList<Channel>();
        if (isChild) {
            //TODO children field already remove 
            //channels.addAll(channel.getChildren());
        } else {
            channels.add(channel);
        }
        return channels;
    }

    List<Channel> filterChannel(final List<Channel> list,final boolean debug){
        if(debug){
            return list;
        }
        List<Channel> channels = new ArrayList<Channel>();
        for(Channel channel : list){
            if(channel.getPublicenable()){
                channels.add(channel);
            }
        }
        return channels;
    }

    private int getParamRowValue(final Map params, String name) throws TemplateModelException {
        Integer rows = DirectiveUtil.getInteger(params, name);
        return rows == null ? -1 : rows;
    }

    private boolean isShowAll(final int row){
        return row == -1;
    }

    private String getParamNameValue(final Map params, final String name) throws TemplateModelException {
        String value = DirectiveUtil.getString(params, name);
        return value == null ? DirectiveVariable.Channel.toString() : value;
    }

     private boolean getParamNameChild(final Map params, final String name) throws TemplateModelException {
        Boolean value = DirectiveUtil.getBoolean(params, name);
        return value == null ? false : value;
    }
}
