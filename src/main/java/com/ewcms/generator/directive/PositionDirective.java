/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.core.site.model.Channel;
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
import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.position")
public class PositionDirective implements TemplateDirectiveModel {
    
    private static final String PARAM_NAME_NAME = "name";
    private static final String VARIABLE_NAME_HAS_NEXT="has_next";

    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        try {
            if(EmptyUtil.isNull(body)){
                throw new DirectiveException("没有显示内容");
            }

            Channel currentChannel = getChannel(env);
            List<Channel> grade = getChannelGrade(currentChannel);
            
            if(EmptyUtil.isArrayNotEmpty(loopVars)){
                loopVars[0] = env.getObjectWrapper().wrap(grade);
                body.render(env.getOut());
            }else{
                Writer writer = env.getOut();
                String variable = getParamNameValue(params);
                int len = grade.size();
                for(int i = 0 ; i < len ; ++i){
                    Channel channel = grade.get(i);
                    DirectiveUtil.setVariable(env, variable, channel);
                    boolean has_next = (i != (len-1));
                    DirectiveUtil.setVariable(env, VARIABLE_NAME_HAS_NEXT, has_next);
                    body.render(writer);
                    writer.flush();
                    DirectiveUtil.removeVariable(env, variable);
                    DirectiveUtil.removeVariable(env, VARIABLE_NAME_HAS_NEXT);
                }
                writer.flush();
            }
       } catch (DirectiveException e) {
            e.render(env.getOut());
        } catch(Exception e){
            DirectiveException ex = new DirectiveException(e);
            ex.render(env.getOut());
        }
    }

    private Channel getChannel(final Environment env) throws TemplateModelException, DirectiveException {

        Channel channel = (Channel) DirectiveUtil.getBean(env, DirectiveVariable.CurrentChannel.toString());
        if (EmptyUtil.isNotNull(channel)) {
            return channel;
        }
        throw new DirectiveException("无法获得频道");
    }

    private List<Channel> getChannelGrade(final Channel channel) {
        List<Channel> grades = new ArrayList<Channel>();

        for (Channel c = channel; EmptyUtil.isNotNull(c.getParent()); c = c.getParent()) {
            grades.add(0, c);
        }

        return grades;
    }
    
    /**
     * 得到Name参数值
     *
     * @param params
     * @return
     * @throws TemplateModelException
     */
    private String getParamNameValue(final Map params) throws TemplateModelException {
        String value = DirectiveUtil.getString(params,PARAM_NAME_NAME);
        if (EmptyUtil.isNull(value)) {
            return DirectiveVariable.Channel.toString();
        }
        return value;
    }
}
