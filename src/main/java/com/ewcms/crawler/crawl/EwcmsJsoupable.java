package com.ewcms.crawler.crawl;

public interface EwcmsJsoupable {
	public void parse(Long gatherId, Integer channelId, String url, int timeOut);
}
