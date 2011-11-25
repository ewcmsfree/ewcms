/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.ewcms.plugin.crawler.manager.dao.GatherDAO;
import com.ewcms.plugin.crawler.manager.service.GatherService;
import com.ewcms.plugin.crawler.model.MatchBlock;

/**
 * 测试匹配块和过滤块对HTML进行过滤的结果
 * 
 * @author wu_zhijun
 *
 */
public class BlockIterationTest {
	
	private GatherService gatherService;
	private GatherDAO gatherDAO;
	
	@Before
	public void setUp() {
		gatherService = new GatherService();
		gatherDAO = mock(GatherDAO.class);
		gatherService.setGatherDAO(gatherDAO);
	}
	
	@Test
	public void testMatchBlock(){
		String html="<html>" +
				    "  <head>" +
				    "    <title>测试使用</title>" +
				    "  </head" +
				    "  <body>" +
				    "    <table class='table_1'>" +
				    "      <tr class='tr_1'>" +
				    "        <td class='td_1'>" +
				    "          内容1" +
				    "        </td>" +
				    "      </tr>" +
				    "      <tr class='tr_2'>" +
				    "       <td class='td_2'>" +
				    "         内容2" +
 				    "       </td>" +
				    "      </tr>" +
				    "    </table>" +
				    "    <table class='table_2'>" +
				    "      <tr class='tr_3'>" +
				    "        <td class='td'>" +
				    "          内容3" +
				    "        </td>" +
				    "      </tr>" +
				    "      <tr class='tr_4'>" +
				    "       <td class='td'>" +
				    "         内容4" +
 				    "       </td>" +
				    "      </tr>" +
				    "    </table>" +
				    "    <table class='table_3'>" +
				    "      <tr class='tr_3'>" +
				    "        <td class='td_5'>" +
				    "          内容5" +
				    "        </td>" +
				    "      </tr>" +
				    "      <tr class='tr_4'>" +
				    "       <td class='td'>" +
				    "         内容6" +
 				    "       </td>" +
				    "      </tr>" +
				    "    </table>" +
				    "  </body>" +
				    "</html>";
		
		MatchBlock matchBlock_1 = new MatchBlock();
		matchBlock_1.setId(1L);
		matchBlock_1.setParent(null);
		matchBlock_1.setRegex("table.table_1");
		matchBlock_1.setSort(1L);
		
		MatchBlock matchBlock_2 = new MatchBlock();
		matchBlock_2.setId(2L);
		matchBlock_2.setParent(null);
		matchBlock_2.setRegex("table.table_2");
		matchBlock_2.setSort(2L);
		
		MatchBlock matchBlock_3 = new MatchBlock();
		matchBlock_3.setId(3L);
		matchBlock_3.setParent(matchBlock_1);
		matchBlock_3.setRegex("tr.tr_3");
		matchBlock_3.setSort(3L);
		
		MatchBlock matchBlock_4 = new MatchBlock();
		matchBlock_4.setId(4L);
		matchBlock_4.setParent(matchBlock_1);
		matchBlock_4.setRegex("tr.tr_4");
		matchBlock_4.setSort(4L);
		
		MatchBlock matchBlock_5 = new MatchBlock();
		matchBlock_5.setId(5L);
		matchBlock_5.setParent(matchBlock_3);
		matchBlock_5.setRegex("td.td_5");
		matchBlock_5.setSort(5L);
		
		List<MatchBlock> parents = new ArrayList<MatchBlock>();
		parents.add(matchBlock_1);
		parents.add(matchBlock_2);
		
		List<MatchBlock> matchBlocks_1 = new ArrayList<MatchBlock>();
		matchBlocks_1.add(matchBlock_3);
		matchBlocks_1.add(matchBlock_4);
		when(gatherDAO.findChildMatchBlockByParentId(1L, 1L)).thenReturn(matchBlocks_1);
		
		when(gatherDAO.findChildMatchBlockByParentId(1L, 2L)).thenReturn(new ArrayList<MatchBlock>());
		
		List<MatchBlock> matchBlocks_2 = new ArrayList<MatchBlock>();
		matchBlocks_2.add(matchBlock_5);
		when(gatherDAO.findChildMatchBlockByParentId(1L, 3L)).thenReturn(matchBlocks_2);
		
		Document doc = Jsoup.parse(html);
		StringBuffer sbHtml = new StringBuffer();
		
		childrenMatchBlock(1L, doc, parents, sbHtml);
		
		System.out.println(sbHtml.toString());
	}
	
	private void childrenMatchBlock(Long gatherId, Document doc, List<MatchBlock> matchBlocks, StringBuffer sbHtml) {
		for (MatchBlock matchBlock : matchBlocks) {
			String regex = matchBlock.getRegex();
			Elements elements = doc.select(regex);
			String subHtml = elements.html();

			List<MatchBlock> childrens = gatherDAO.findChildMatchBlockByParentId(gatherId, matchBlock.getId());
			if (!childrens.isEmpty()) {
				Document subDoc = Jsoup.parse(subHtml);
				childrenMatchBlock(gatherId, subDoc, childrens, sbHtml);
			}

			sbHtml.append(subHtml);
		}
	}
}
