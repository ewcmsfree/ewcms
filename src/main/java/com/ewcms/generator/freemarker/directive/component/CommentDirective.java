/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.component;

import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.generator.freemarker.directive.DirectiveException;
import com.ewcms.generator.freemarker.directive.DirectiveUtil;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.ElementDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author wangwei
 */
public class CommentDirective extends ElementDirective<ArticleRmc> {

    private static final String PARAM_CALLBACK_VALUE = "callback";
    private static final String DEFAULT_CALLBACK_NAME = "comment_count_callback";
    
    private String callback;
    private boolean isJquery;

    @Override
    public void execute(final Environment env,
            final Map params, final TemplateModel[] loopVars,
            final TemplateDirectiveBody body) throws TemplateException, IOException {

        try {
            callback = getParamValue(params, PARAM_CALLBACK_VALUE);
            isJquery = isImportJquery(env);
        } catch (DirectiveException e) {
            //错误信息
        }
        super.execute(env, params, loopVars, body);
        setImportJquery(env);
    }

    private String getParamValue(final Map params, final String name) throws TemplateModelException, DirectiveException {
        return DirectiveUtil.getString(params, name);
    }

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {

        StringBuilder builder = new StringBuilder();
        builder.append("<div id=comment-warpper></div>\n");
        String url = getUrl();
        builder.append("<script type='text/javascript' src='")
                .append(url).append("/js/cmnt.js").append("'>");
        builder.append("</script>\n");
        if(isJquery){
            builder.append("<script type='text/javascript' src='")
                .append(url).append("/js/jquery-1.4.2.min.js").append("'>");
        }
        builder.append("</script>\n");

        builder.append(createInitFunction(articleRmc.getArticle().getId(),url));
        
        return builder.toString();
    }

    private String createInitFunction(final Integer articleId,final String url){
        StringBuilder builder = new StringBuilder();

        builder.append("<script type='text/javascript'>\n");
        if(callback == null){
            builder.append(createDefaultCallBackFunction());
            callback  = DEFAULT_CALLBACK_NAME;
        }

        builder.append("comment_init('").append(url).append("','");
        builder.append(articleId).append("',");
        builder.append(callback).append(");\n");
        builder.append("</script>\n");

        return builder.toString();
    }

    private String createDefaultCallBackFunction() {
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(DEFAULT_CALLBACK_NAME).append("(commentCount,personCount){\n");
        builder.append("var element = document.getElementById('").append("comment-count-warpper").append("')\n");
        builder.append("element.innerHTML = '已有<span style=\"color:red\">' + commentCount + '</span>人参与评论';\n");
        builder.append("}\n\n");

        return builder.toString();
    }

    private String getUrl(){
        //TODO get comment url
        return "http://127.0.0.1:8080";
    }

    private boolean isImportJquery(final Environment env)throws TemplateModelException {
        Boolean jquery = DirectiveUtil.getBoolean(env, DirectiveVariable.JQuery.toString());
        return jquery == null ? true : false;
    }

    private void setImportJquery(final Environment env)throws TemplateModelException {
        DirectiveUtil.setVariable(env, DirectiveVariable.JQuery.toString(), true);
    }

    @Override
    protected String defaultVariable() {
        return DirectiveVariable.Article.toString();
    }
}
