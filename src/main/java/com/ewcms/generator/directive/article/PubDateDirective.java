/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.article;

import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.generator.directive.DirectiveUtil;
import com.ewcms.util.EmptyUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.springframework.stereotype.Service;
/**
 *
 * @author wangwei
 */
@Service("direcitve.article.pubDate")
public class PubDateDirective extends ArticleElementDirective {

    private static final String PARAM_NAME_FORMAT = "format";
    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormat;
    
    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        dateFormat = getDateFormat(params,PARAM_NAME_FORMAT);
        super.execute(env,params,loopVars,body);
    }

    private SimpleDateFormat getDateFormat(final Map params, final String name) throws TemplateModelException {
        String format = DirectiveUtil.getString(params, name);
        if (EmptyUtil.isNull(format)) {
            return DEFAULT_FORMAT;
        }else{
            return new SimpleDateFormat(format);
        }
    }

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        return articleRmc.getPublished() == null ? "" : dateFormat.format(articleRmc.getPublished());
    }
}
