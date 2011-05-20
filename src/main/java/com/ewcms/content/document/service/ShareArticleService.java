/**
 * 
 */
package com.ewcms.content.document.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ArticleRmcDAO;
import com.ewcms.content.document.dao.ShareArticleDAO;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.ShareArticle;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.util.EwcmsContextUtil;

/**
 * @author 周冬初
 * 
 */
@Service
public class ShareArticleService {
	@Autowired
	private ShareArticleDAO saDAO;
	@Autowired
	private ArticleRmcDAO arDAO;
	@Autowired
	private ChannelDAO channelDao;

	/**
	 * 文章共享
	 * 
	 * @param siteId
	 *            所要共享到的站点
	 * @param idList
	 *            要共享的文章id集合
	 */
	public void shareArticle(List<Integer> siteIdList, List<Integer> idList) {
		ShareArticle vo;
		ArticleRmc arVo;
		for (Integer siteId : siteIdList) {
			for (Integer id : idList) {
				if (!saDAO.shared(siteId, id)) {
					vo = new ShareArticle();
					vo.setSiteId(siteId);
					arVo = arDAO.get(id);
					vo.setArticleRmc(arVo);
					vo.setChannelName(arVo.getChannel().getName());
					vo.setArticleTitle(arVo.getArticle().getTitle());
					saDAO.persist(vo);
				}
			}
		}
	}

	public void delShareArticle(Integer id) {
		saDAO.removeByPK(id);
	}

	/**
	 * 共享文章引用
	 * 
	 * @param id
	 *            共享文章id
	 * @param idList
	 *            要引用的频道id集合
	 */
	public void refArticle(List<Integer> shareIds, List<Integer> channelIds) {
		for (Integer shareId : shareIds) {
			ShareArticle shareVo = saDAO.get(shareId);
			if (shareVo == null)
				continue;
			ArticleRmc vo = arDAO.get(shareVo.getArticleRmc().getId());
			if (vo == null)
				continue;
			for (Integer channelId : channelIds) {
				Channel channelVo = channelDao.get(channelId);
				if (channelVo == null)
					break;
				if (vo.getRefChannel().contains(channelVo)) {
					break;
				}
				vo.getRefChannel().add(channelVo);
			}
			arDAO.merge(vo);
			shareVo.setRefed(true);
			saDAO.merge(shareVo);

		}
	}
}
