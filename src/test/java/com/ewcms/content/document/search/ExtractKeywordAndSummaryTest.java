/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.search;

import org.junit.Test;

import com.ewcms.common.io.HtmlStringUtil;

/**
 * @author 吴智俊
 */
public class ExtractKeywordAndSummaryTest {

	private String title = "研究发现银河系中心复杂分子味道似山莓";
	private String content = "<p>　　新浪科技讯 北京时间4月22日消息，据英国媒体报道，在太空寻找生命&ldquo;构造单元&rdquo;的天文学家日前收获了一个意外惊喜，他们发现隐藏于银河系中心庞大尘云的复杂分子居然有一股山莓的味道。</p>" + "<p>　　天文学家一直在银河系中心周围的尘云寻找生命构造单元。如今，他们得出了这样一个结论，即这些对生命存在至关重要的复杂分子的味道与山莓相近。这些年来，天文学家通过射电望远镜在漫无边际的尘埃和星云中寻找复杂分子的踪迹，没料到竟然获得一个出人意料的发现。</p>" + "<p>　　对天体物理学家而言，在星际空间发现氨基酸证据的重要性不啻于发现&ldquo;圣杯&rdquo;，因为这可能会提高化学分子在其他星球&ldquo;播种&rdquo;以后生成生命的可能性。在最新研究中，天文学家筛选了人马座B2(Sagittarius B2)释放的数以千计的信号。人马座B2是处于银河系中心的巨大尘云。尽管天文学家未能找到氨基酸的证据，但他们的确发现了一种称为甲酸乙酯的物质，这种化学物质使山莓具有了特别的味道。</p>" + "<p>　　德国马克斯普朗克射电天文学研究所的天文学家阿诺德?贝洛奇(Arnaud Belloche)表示：&ldquo;甲酸乙酯碰巧赋予山莓一种特有的味道，但要制成&lsquo;太空山莓&rsquo;还需要其它许多分子。&rdquo;令人好奇的是，甲酸乙酯还具有另外一个与众不同的特性：它还具有朗姆酒的气味。天文学家利用设在西班牙的IRAM射电望远镜，对人马座B2这个炽热和稠密的区域释放的电磁辐射进行了分析。这个区域处于一颗新生恒星的周围。</p>"
			+ "<p>　　这颗恒星释放的辐射被漂浮在气云周围的分子所吸收，接着，气云又会根据分子的不同类型，以不同能量将其释放出去。贝洛奇的研究小组在对数据进行分析过程中，在同一片星云发现了有害化学分子丙基氰(propyl cyanide)的证据。丙基氰和甲酸乙酯是迄今天文学家在深空发现的数量最多的两种分子。</p>" + "<p>　　贝洛奇和他在美国康内尔大学的同事罗宾?加洛德(Robin Garrod)从人马座B2星云搜集了近4000个与众不同的信号，但只对其中的一半左右完成了分析。贝洛奇说：&ldquo;迄今为止，我们在这项调查中已确认了大约50种分子，其中两种以前从未被发现过。&rdquo;贝洛奇的团队在英国赫特福德大学举行的欧洲天文学与宇宙学周上公布了这项研究结果。</p>" + "<p>　　去年，贝洛奇的研究小组发现了一种称为氨基乙腈(amino acetonitrile)的分子，当时差一点就在太空中找到了氨基酸的证据。氨基乙腈可以用来制造氨基酸。最新一系列发现提升了研究人员的士气，因为新发现的分子同最简单的氨基酸――甘氨酸一般大。氨基酸是组成蛋白质的构造单元，被广泛认为对存在于宇宙各个角落的复杂生命至关重要。</p>" + "<p>　　贝洛奇说：&ldquo;如果未来几年我们可以在太空发现氨基酸，我丝毫不会感到吃惊。&rdquo;以前，天文学家曾经探测到各种各样的大分子，包括乙醇、酸和称为乙醛的化学分子。贝洛奇说：&ldquo;寻找复杂分子的难点在于，最佳的天文来源含有如此多的不同分子，以致于它们的&lsquo;指纹&rsquo;重叠，难以解脱出来。&rdquo;</p>"
			+ "<p>　　一般认为，只有存在于一些尘埃颗粒的化学物质(如乙醇)连接起来，构成更为复杂的链条，才会形成这样的分子。加洛德说：&ldquo;在此过程中形成的分子大小没有明显限制，所以，我们有充分理由认为太空中甚至存在更为复杂的有机分子。&rdquo;(孝文）</p>";

	@Test
	public void getKeyword() {
		System.out.println(HtmlStringUtil.join(ExtractKeywordAndSummary.getKeyword(title + " " + content), " "));
	}

	@Test
	public void getSummary() {
		System.out.println(ExtractKeywordAndSummary.getTextAbstract(title, content));
	}
}
