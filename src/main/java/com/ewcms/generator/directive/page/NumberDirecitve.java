/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.page;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.generator.directive.DirectiveException;
import com.ewcms.generator.directive.DirectiveUtil;
import com.ewcms.generator.directive.DirectiveVariable;
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
import org.springframework.util.Assert;

/**
 *
 * @author wangwei
 */
@Service("direcitve.pageNumber")
public class NumberDirecitve implements TemplateDirectiveModel {

    private static final String PARAM_NAME_NUMBER = "number";
    private static final String PARAM_NAME_LAST = "last";
    private static final String PARAM_NAME_ONLYINFO = "onlyInfo";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            if (EmptyUtil.isNull(body)) {
                throw new DirectiveException("没有显示内容");
            }
            PageParam pageParam = getPageParamVariable(env);
            boolean info = isInfoParam(params, PARAM_NAME_ONLYINFO);
            List<Page> pages;
            if (info) {
                pages = pageInfo(pageParam);
            } else {
                int number = getNumberParam(params, PARAM_NAME_NUMBER);
                boolean last = isLastParam(params, PARAM_NAME_LAST);
                number = number < 3 ? 3 : number;
                number = last && number < 5 ? 5 : number;
                pages = pageList(pageParam, number, last);
            }

            if (EmptyUtil.isArrayNotEmpty(loopVars)) {
                loopVars[0] = env.getObjectWrapper().wrap(pages);
                body.render(env.getOut());
            } else {
                Writer writer = env.getOut();
                for (Page page : pages) {
                    DirectiveUtil.setVariable(env, DirectiveVariable.Page.toString(), page);
                    body.render(writer);
                    DirectiveUtil.removeVariable(env, DirectiveVariable.Page.toString());
                }
                writer.flush();
            }
        } catch (DirectiveException e) {
            if (DirectiveUtil.isDebug(env)) {
                e.render(env.getOut());
            }
        }
    }

    private PageParam getPageParamVariable(Environment env) throws TemplateModelException, DirectiveException {
        PageParam param = (PageParam) DirectiveUtil.getBean(env, DirectiveVariable.PageParam.toString());
        Assert.notNull(param, "PageParam is null");
        return param;
    }

    private int getNumberParam(final Map params, final String name) throws TemplateException {
        Integer page = DirectiveUtil.getInteger(params, name);
        return page == null ? 5 : page;
    }

    private boolean isLastParam(final Map params, final String name) throws TemplateException {
        Boolean last = DirectiveUtil.getBoolean(params, name);
        return last == null ? false : last;
    }

    private boolean isInfoParam(final Map params, final String name) throws TemplateException {
        Boolean info = DirectiveUtil.getBoolean(params, name);
        return info == null ? false : info;
    }

    List<Page> pageInfo(final PageParam pageParam) {

        List<Page> pages = new ArrayList<Page>();

        Page page = new Page();
        page.setEnabled(false);
        page.setLabel(String.format("%d/%d", pageLabel(pageParam.getPage()), pageParam.getCount()));
        page.setUrl("");
        pages.add(page);

        return pages;
    }

    private int pageLabel(int page) {
        return page + 1;
    }

    List<Page> pageList(final PageParam pageParam, final int number, final boolean islast) {

        List<Page> pages = new ArrayList<Page>();
        if(pageParam.getCount() == 1){
            return pages;
        }

        int[] domains = pageNumberDomains(pageParam.getCount(), number, islast);

        int front = domains[0];
        int middle = domains[1];
        int last = domains[2];
        firstDomainPageList(pages, pageParam, front);
        middleDomainPageList(pages, pageParam, front, middle, last);

        lastDomainPageList(pages, pageParam, last);

        return pages;
    }

    /**
     * 页数显示区分为前显示、主显示和后显示三个区。
     *
     * @param count  页数
     * @param number 显示数
     * @param last   显示最后一页
     * @return
     */
    private int[] pageNumberDomains(int count, int number, boolean last) {

        if (count <= number) {
            return new int[]{0, count, 0};
        }

        int firstDomain = 0;
        int middleDomain = 0;
        int lastDomain = 0;
        if (last) {
            firstDomain = (number - 3) / 2;
            firstDomain = firstDomain > 2 ? 2 : firstDomain;
            lastDomain = firstDomain;
        } else {
            firstDomain = (number - 3);
            firstDomain = firstDomain > 2 ? 2 : firstDomain;
            lastDomain = 0;
        }
        middleDomain = number - firstDomain - lastDomain;
        return new int[]{firstDomain, middleDomain, lastDomain};

    }

    private void firstDomainPageList(final List<Page> pages, final PageParam pageParam, final int front) {
        for (int i = 0; i < front; ++i) {
            pages.add(createPage(pageParam, i));
        }
    }

    private void middleDomainPageList(final List<Page> pages, final PageParam pageParam,
            final int front, final int middle, final int last) {

        int pageNumber = pageParam.getPage();
        int pageCount = pageParam.getCount();
        int start = pageNumber - (middle - 1) / 2;
        start = (start < 0 ? 0 : start);
        boolean islast = false;
        if (start + middle + last >= pageCount) {
            islast = true;
            start = pageCount - (middle + last);
        }
        boolean isfront = false;
        if (start <= front) {
            start = front;
            isfront = true;
        }
        if (!isfront) {
            pages.add(createMissPage());
        }
        for (int i = 0; i < middle; ++i) {
            pages.add(createPage(pageParam, (start + i)));
        }
        if (!islast) {
            pages.add(createMissPage());
        }
    }

    private void lastDomainPageList(final List<Page> pages, final PageParam pageParam, final int last) {
        int pageCount = pageParam.getCount();
        for (int i = 0; i < last; ++i) {
            int index = pageCount - last + i;
            pages.add(createPage(pageParam, index));
        }
    }

    private Page createPage(final PageParam pageParam, int currentPage) {
        Page page = new Page();
        page.setEnabled(pageParam.getPage() != currentPage);
        page.setLabel(String.valueOf(pageLabel(currentPage)));
        page.setUrl(String.format(pageParam.getUrlPattern(), currentPage));

        return page;
    }

    private Page createMissPage() {
        Page page = new Page();
        page.setEnabled(false);
        page.setLabel("..");
        page.setUrl("");

        return page;
    }
}
