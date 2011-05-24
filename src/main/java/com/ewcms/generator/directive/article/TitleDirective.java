/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.article;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.generator.directive.DirectiveUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;

import static com.ewcms.common.lang.EmptyUtil.isStringEmpty;

/**
 * 显示文章标题标签<br/>
 *
 * <@article.title value="arti"/>
 *
 * @author wangwei
 */
@Service("direcitve.article.title")
public class TitleDirective extends ArticleElementDirective {

    private static final String PARAM_NAME_STYLE = "style";
    private static final String PARAM_NAME_LENGTH = "length";
    protected boolean style = false;
    protected int length = -1;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        style = getStyle(params);
        length = getLength(params);
        super.execute(env, params, loopVars, body);
    }

    @Override
    protected String constructOutValue(ArticleRmc articleRmc) {
        Article article = this.getArticle(articleRmc);
        return constructOutValue(article.getTitle(), article.getTitleStyle());

    }

    protected String constructOutValue(String value, String styleValue) {
        if (value == null) {
            return "";
        }
        
        if (length != -1) {
            if(value.length() > length){
                value = value.substring(0, length)+"...";
            }
        }

        if (style) {
            return fontStyle(value, styleValue);
        } else {
            return value;
        }
    }

    protected String fontStyle(final String title, final String style) {
        if (isStringEmpty(style)) {
            return title;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<font style='").append(style).append("'>");
        builder.append(title);
        builder.append("</font>");
        return builder.toString();
    }

    public boolean getStyle(final Map params) throws TemplateModelException {
        Boolean s = DirectiveUtil.getBoolean(params, PARAM_NAME_STYLE);
        return s == null ? false : s;
    }

    public int getLength(final Map params) throws TemplateModelException {
        Integer len = DirectiveUtil.getInteger(params, PARAM_NAME_LENGTH);
        return len == null ? -1 : len;
    }
}
