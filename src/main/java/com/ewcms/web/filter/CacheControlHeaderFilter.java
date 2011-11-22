package com.ewcms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.ewcms.web.util.ServletUtil;

/**
 * 为Response设置客户端缓存控制Header的Filter
 * 使WebContent/ewcmssource目录下的文件缓存到客户端
 * 
 * @author wu_zhijun
 *
 */
public class CacheControlHeaderFilter implements Filter {
	
	private static final String PARAM_EXPIRES_SECONDS = "expiresSeconds";
	private long expiresSeconds;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String expiresSecondsParam = filterConfig.getInitParameter(PARAM_EXPIRES_SECONDS);
		if (expiresSecondsParam != null) {
			expiresSeconds = Long.valueOf(expiresSecondsParam);
		} else {
			expiresSeconds = ServletUtil.ONE_YEAR_SECONDS;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletUtil.setExpiresHeader((HttpServletResponse) response, expiresSeconds);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}