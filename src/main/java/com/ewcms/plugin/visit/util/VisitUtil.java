/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.common.lang.EmptyUtil;

/**
 * 
 * @author wuzhijun
 */
public class VisitUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(VisitUtil.class);

	private static String districtCodes[];
	private static Object ipRanges[];
	private static Object mutex = new Object();
	private static String langCodeArr[];
	private static String langNameArr[];
	private static int TRANSACTION_ID_LENGTH = 32;
	private static char cs[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
			.toCharArray();

	/**
	 * 域名
	 * 
	 * @param url
	 *            URL地址
	 * @return String
	 */
	public static String getDomain(String url) {
		int index1 = url.indexOf("//") + 2;
		int index2 = url.indexOf("/", index1 + 2);
		if (index2 == -1)
			index2 = url.length();
		return url.substring(index1, index2).toLowerCase();
	}

	/**
	 * 转换IP
	 * 
	 * @param ip
	 *            IP
	 * @return Long
	 */
	public static long convertIP(String ip) {
		try {
			String arr1[] = ip.split(".");
			long t = (Long.parseLong(arr1[0]) * 0x1000000L
					+ Long.parseLong(arr1[1]) * 0x10000L
					+ Long.parseLong(arr1[2]) * 256L + Long.parseLong(arr1[3])) - 1L;
			return t;
		} catch (Exception e) {
			logger.warn("错误的IP地址:" + ip);
			return 0L;
		}
	}

	/**
	 * 区域代码
	 * 
	 * @param strIP
	 * @return
	 */
	public static String getDistrictCode(String strIP) {
		initIPRanges();
		long ip = convertIP(strIP);
		for (int j = 0; j < districtCodes.length; j++) {
			long arr[] = (long[]) ipRanges[j];
			if (ip >= arr[0] && ip <= arr[arr.length - 1]) {
				for (int k = 0; k < arr.length; k += 2) {
					long i1 = arr[k];
					long i2 = arr[k + 1];
					if (ip >= i1 && ip <= i2)
						return districtCodes[j];
				}

			}
		}

		return "000999";
	}

	/**
	 * 操作系统
	 * 
	 * @param useragent
	 * @return
	 */
	public static String getOS(String useragent) {
		if (EmptyUtil.isNull(useragent))
			return "其他";
		if (useragent.indexOf("Windows NT 6.1") > 0)
			return "Windows 7";
		if (useragent.indexOf("Windows NT 6.0") > 0)
			return "Windows Vista";
		if (useragent.indexOf("Windows NT 5.2") > 0)
			return "Windows 2003";
		if (useragent.indexOf("Windows NT 5.1") > 0)
			return "Windows XP";
		if (useragent.indexOf("Windows NT 5.0") > 0)
			return "Windows 2000";
		if (useragent.indexOf("Windows NT") > 0)
			return "Windows NT";
		if (useragent.indexOf("Windows 9") > 0
				|| useragent.indexOf("Windows 4") > 0)
			return "Windows 9x";
		if (useragent.indexOf("Unix") > 0 || useragent.indexOf("SunOS") > 0
				|| useragent.indexOf("BSD") > 0)
			return "Unix";
		if (useragent.indexOf("Linux") > 0)
			return "Linux";
		if (useragent.indexOf("Mac") > 0)
			return "Mac";
		if (useragent.indexOf("Windows CE") > 0)
			return "Windows CE";
		if (useragent.indexOf("iPhone") > 0)
			return "iPhone";
		if (useragent.indexOf("BlackBerry") > 0)
			return "BlackBerry";
		if (useragent.indexOf("SymbianOS") > 0
				|| useragent.indexOf("Series") > 0)
			return "Symbian";
		else
			return "其他";
	}

	/**
	 * 语言
	 * 
	 * @param lang
	 * @return
	 */
	public static String getLanguage(String lang) {
		if (langCodeArr == null)
			synchronized (mutex) {
				if (langCodeArr == null) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("en_us", "英文(美国)");
					map.put("ar", "阿拉伯文");
					map.put("ar_ae", "阿拉伯文(阿拉伯联合酋长国)");
					map.put("ar_bh", "阿拉伯文(巴林)");
					map.put("ar_dz", "阿拉伯文(阿尔及利亚)");
					map.put("ar_eg", "阿拉伯文(埃及)");
					map.put("ar_iq", "阿拉伯文(伊拉克)");
					map.put("ar_jo", "阿拉伯文(约旦)");
					map.put("ar_kw", "阿拉伯文(科威特)");
					map.put("ar_lb", "阿拉伯文(黎巴嫩)");
					map.put("ar_ly", "阿拉伯文(利比亚)");
					map.put("ar_ma", "阿拉伯文(摩洛哥)");
					map.put("ar_om", "阿拉伯文(阿曼)");
					map.put("ar_qa", "阿拉伯文(卡塔尔)");
					map.put("ar_sa", "阿拉伯文(沙特阿拉伯)");
					map.put("ar_sd", "阿拉伯文(苏丹)");
					map.put("ar_sy", "阿拉伯文(叙利亚)");
					map.put("ar_tn", "阿拉伯文(突尼斯)");
					map.put("ar_ye", "阿拉伯文(也门)");
					map.put("be", "白俄罗斯文");
					map.put("be_by", "白俄罗斯文(白俄罗斯)");
					map.put("bg", "保加利亚文");
					map.put("bg_bg", "保加利亚文(保加利亚)");
					map.put("ca", "加泰罗尼亚文");
					map.put("ca_es", "加泰罗尼亚文(西班牙)");
					map.put("ca_es_euro", "加泰罗尼亚文(西班牙,Euro)");
					map.put("cs", "捷克文");
					map.put("cs_cz", "捷克文(捷克共和国)");
					map.put("da", "丹麦文");
					map.put("da_dk", "丹麦文(丹麦)");
					map.put("de", "德文");
					map.put("de_at", "德文(奥地利)");
					map.put("de_at_euro", "德文(奥地利,Euro)");
					map.put("de_ch", "德文(瑞士)");
					map.put("de_de", "德文(德国)");
					map.put("de_de_euro", "德文(德国,Euro)");
					map.put("de_lu", "德文(卢森堡)");
					map.put("de_lu_euro", "德文(卢森堡,Euro)");
					map.put("el", "希腊文");
					map.put("el_gr", "希腊文(希腊)");
					map.put("en_au", "英文(澳大利亚)");
					map.put("en_ca", "英文(加拿大)");
					map.put("en_gb", "英文(英国)");
					map.put("en_ie", "英文(爱尔兰)");
					map.put("en_ie_euro", "英文(爱尔兰,Euro)");
					map.put("en_nz", "英文(新西兰)");
					map.put("en_za", "英文(南非)");
					map.put("es", "西班牙文");
					map.put("es_bo", "西班牙文(玻利维亚)");
					map.put("es_ar", "西班牙文(阿根廷)");
					map.put("es_cl", "西班牙文(智利)");
					map.put("es_co", "西班牙文(哥伦比亚)");
					map.put("es_cr", "西班牙文(哥斯达黎加)");
					map.put("es_do", "西班牙文(多米尼加共和国)");
					map.put("es_ec", "西班牙文(厄瓜多尔)");
					map.put("es_es", "西班牙文(西班牙)");
					map.put("es_es_euro", "西班牙文(西班牙,Euro)");
					map.put("es_gt", "西班牙文(危地马拉)");
					map.put("es_hn", "西班牙文(洪都拉斯)");
					map.put("es_mx", "西班牙文(墨西哥)");
					map.put("es_ni", "西班牙文(尼加拉瓜)");
					map.put("es_pa", "西班牙文(巴拿马)");
					map.put("es_pe", "西班牙文(秘鲁)");
					map.put("es_pr", "西班牙文(波多黎哥)");
					map.put("es_py", "西班牙文(巴拉圭)");
					map.put("es_sv", "西班牙文(萨尔瓦多)");
					map.put("es_uy", "西班牙文(乌拉圭)");
					map.put("es_ve", "西班牙文(委内瑞拉)");
					map.put("et", "爱沙尼亚文");
					map.put("et_ee", "爱沙尼亚文(爱沙尼亚)");
					map.put("fi", "芬兰文");
					map.put("fi_fi", "芬兰文(芬兰)");
					map.put("fi_fi_euro", "芬兰文(芬兰,Euro)");
					map.put("fr", "法文");
					map.put("fr_be", "法文(比利时)");
					map.put("fr_be_euro", "法文(比利时,Euro)");
					map.put("fr_ca", "法文(加拿大)");
					map.put("fr_ch", "法文(瑞士)");
					map.put("fr_fr", "法文(法国)");
					map.put("fr_fr_euro", "法文(法国,Euro)");
					map.put("fr_lu", "法文(卢森堡)");
					map.put("fr_lu_euro", "法文(卢森堡,Euro)");
					map.put("hr", "克罗地亚文");
					map.put("hr_hr", "克罗地亚文(克罗地亚)");
					map.put("hu", "匈牙利文");
					map.put("hu_hu", "匈牙利文(匈牙利)");
					map.put("is", "冰岛文");
					map.put("is_is", "冰岛文(冰岛)");
					map.put("it", "意大利文");
					map.put("it_ch", "意大利文(瑞士)");
					map.put("it_it", "意大利文(意大利)");
					map.put("it_it_euro", "意大利文(意大利,Euro)");
					map.put("iw", "希伯来文");
					map.put("iw_il", "希伯来文(以色列)");
					map.put("ja", "日文");
					map.put("ja_jp", "日文(日本)");
					map.put("ko", "朝鲜文");
					map.put("ko_kr", "朝鲜文(南朝鲜)");
					map.put("lt", "立陶宛文");
					map.put("lt_lt", "立陶宛文(立陶宛)");
					map.put("lv", "拉托维亚文(列托)");
					map.put("lv_lv", "拉托维亚文(列托)(拉脱维亚)");
					map.put("mk", "马其顿文");
					map.put("mk_mk", "马其顿文(马其顿王国)");
					map.put("nl", "荷兰文");
					map.put("nl_be", "荷兰文(比利时)");
					map.put("nl_be_euro", "荷兰文(比利时,Euro)");
					map.put("nl_nl", "荷兰文(荷兰)");
					map.put("nl_nl_euro", "荷兰文(荷兰,Euro)");
					map.put("no", "挪威文");
					map.put("no_no", "挪威文(挪威)");
					map.put("no_no_ny", "挪威文(挪威,Nynorsk)");
					map.put("pl", "波兰文");
					map.put("pl_pl", "波兰文(波兰)");
					map.put("pt", "葡萄牙文");
					map.put("pt_br", "葡萄牙文(巴西)");
					map.put("pt_pt", "葡萄牙文(葡萄牙)");
					map.put("pt_pt_euro", "葡萄牙文(葡萄牙,Euro)");
					map.put("ro", "罗马尼亚文");
					map.put("ro_ro", "罗马尼亚文(罗马尼亚)");
					map.put("ru", "俄文");
					map.put("ru_ru", "俄文(俄罗斯)");
					map.put("sh", "塞波尼斯-克罗地亚文");
					map.put("sh_yu", "塞波尼斯-克罗地亚文(南斯拉夫)");
					map.put("sk", "斯洛伐克文");
					map.put("sk_sk", "斯洛伐克文(斯洛伐克)");
					map.put("sl", "斯洛文尼亚文");
					map.put("sl_si", "斯洛文尼亚文(斯洛文尼亚)");
					map.put("sq", "阿尔巴尼亚文");
					map.put("sq_al", "阿尔巴尼亚文(阿尔巴尼亚)");
					map.put("sr", "塞尔维亚文");
					map.put("sr_yu", "塞尔维亚文(南斯拉夫)");
					map.put("sv", "瑞典文");
					map.put("sv_se", "瑞典文(瑞典)");
					map.put("th", "泰文");
					map.put("th_th", "泰文(泰国)");
					map.put("tr", "土耳其文");
					map.put("tr_tr", "土耳其文(土耳其)");
					map.put("uk", "乌克兰文");
					map.put("uk_ua", "乌克兰文(乌克兰)");
					map.put("zh", "中文");
					map.put("zh_cn", "中文(大陆)");
					map.put("zh_hk", "中文(香港)");
					map.put("zh_tw", "中文(台湾)");
					map.put("zh_sg", "中文(新加坡)");
					map.put("en-us", "英文(美国)");
					map.put("ar-ae", "阿拉伯文(阿拉伯联合酋长国)");
					map.put("ar-bh", "阿拉伯文(巴林)");
					map.put("ar-dz", "阿拉伯文(阿尔及利亚)");
					map.put("ar-eg", "阿拉伯文(埃及)");
					map.put("ar-iq", "阿拉伯文(伊拉克)");
					map.put("ar-jo", "阿拉伯文(约旦)");
					map.put("ar-kw", "阿拉伯文(科威特)");
					map.put("ar-lb", "阿拉伯文(黎巴嫩)");
					map.put("ar-ly", "阿拉伯文(利比亚)");
					map.put("ar-ma", "阿拉伯文(摩洛哥)");
					map.put("ar-om", "阿拉伯文(阿曼)");
					map.put("ar-qa", "阿拉伯文(卡塔尔)");
					map.put("ar-sa", "阿拉伯文(沙特阿拉伯)");
					map.put("ar-sd", "阿拉伯文(苏丹)");
					map.put("ar-sy", "阿拉伯文(叙利亚)");
					map.put("ar-tn", "阿拉伯文(突尼斯)");
					map.put("ar-ye", "阿拉伯文(也门)");
					map.put("be-by", "白俄罗斯文(白俄罗斯)");
					map.put("bg-bg", "保加利亚文(保加利亚)");
					map.put("ca-es", "加泰罗尼亚文(西班牙)");
					map.put("ca-es-euro", "加泰罗尼亚文(西班牙西班牙,Euro)");
					map.put("cs-cz", "捷克文(捷克共和国)");
					map.put("da-dk", "丹麦文(丹麦)");
					map.put("de-at", "德文(奥地利)");
					map.put("de-at-euro", "德文(奥地利,Euro)");
					map.put("de-ch", "德文(瑞士)");
					map.put("de-de", "德文(德国)");
					map.put("de-de-euro", "德文(德国,Euro)");
					map.put("de-lu", "德文(卢森堡)");
					map.put("de-lu-euro", "德文(卢森堡,Euro)");
					map.put("el-gr", "希腊文(希腊)");
					map.put("en-au", "英文(澳大利亚)");
					map.put("en-ca", "英文(加拿大)");
					map.put("en-gb", "英文(英国)");
					map.put("en-ie", "英文(爱尔兰)");
					map.put("en-ie-euro", "英文(爱尔兰,Euro)");
					map.put("en-nz", "英文(新西兰新西兰)");
					map.put("en-za", "英文(南非)");
					map.put("es-bo", "西班牙文(玻利维亚)");
					map.put("es-ar", "西班牙文(阿根廷)");
					map.put("es-cl", "西班牙文(智利)");
					map.put("es-co", "西班牙文(哥伦比亚)");
					map.put("es-cr", "西班牙文(哥斯达黎加)");
					map.put("es-do", "西班牙文(多米尼加共和国)");
					map.put("es-ec", "西班牙文(厄瓜多尔)");
					map.put("es-es", "西班牙文(西班牙)");
					map.put("es-es-euro", "西班牙文(西班牙,Euro)");
					map.put("es-gt", "西班牙文(危地马拉)");
					map.put("es-hn", "西班牙文(洪都拉斯)");
					map.put("es-mx", "西班牙文(墨西哥)");
					map.put("es-ni", "西班牙文(尼加拉瓜)");
					map.put("es-pa", "西班牙文(巴拿马)");
					map.put("es-pe", "西班牙文(秘鲁)");
					map.put("es-pr", "西班牙文(波多黎哥)");
					map.put("es-py", "西班牙文(巴拉圭)");
					map.put("es-sv", "西班牙文(萨尔瓦多)");
					map.put("es-uy", "西班牙文(乌拉圭)");
					map.put("es-ve", "西班牙文(委内瑞拉)");
					map.put("et-ee", "爱沙尼亚文(爱沙尼亚)");
					map.put("fi-fi", "芬兰文(芬兰)");
					map.put("fi-fi-euro", "芬兰文(芬兰,Euro)");
					map.put("fr-be", "法文(比利时)");
					map.put("fr-be-euro", "法文(比利时,Euro)");
					map.put("fr-ca", "法文(加拿大)");
					map.put("fr-ch", "法文(瑞士)");
					map.put("fr-fr", "法文(法国)");
					map.put("fr-fr-euro", "法文(法国,Euro)");
					map.put("fr-lu", "法文(卢森堡)");
					map.put("fr-lu-euro", "法文(卢森堡,Euro)");
					map.put("hr-hr", "克罗地亚文(克罗地亚)");
					map.put("hu-hu", "匈牙利文(匈牙利)");
					map.put("is-is", "冰岛文(冰岛)");
					map.put("it-ch", "意大利文(瑞士)");
					map.put("it-it", "意大利文(意大利)");
					map.put("it-it-euro", "意大利文(意大利,Euro)");
					map.put("iw-il", "希伯来文(以色列)");
					map.put("ja-jp", "日文(日本)");
					map.put("ko-kr", "朝鲜文(南朝鲜)");
					map.put("lt-lt", "立陶宛文(立陶宛)");
					map.put("lv-lv", "拉托维亚文(列托)(拉脱维亚)");
					map.put("mk-mk", "马其顿文(马其顿王国)");
					map.put("nl-be", "荷兰文(比利时)");
					map.put("nl-be-euro", "荷兰文(比利时,Euro)");
					map.put("nl-nl", "荷兰文(荷兰)");
					map.put("nl-nl-euro", "荷兰文(荷兰,Euro)");
					map.put("no-no", "挪威文(挪威)");
					map.put("no-no-ny", "挪威文(挪威,Nynorsk)");
					map.put("pl-pl", "波兰文(波兰)");
					map.put("pt-br", "葡萄牙文(巴西)");
					map.put("pt-pt", "葡萄牙文(葡萄牙)");
					map.put("pt-pt-euro", "葡萄牙文(葡萄牙,Euro)");
					map.put("ro-ro", "罗马尼亚文(罗马尼亚)");
					map.put("ru-ru", "俄文(俄罗斯)");
					map.put("sh-yu", "塞波尼斯-克罗地亚文(南斯拉夫)");
					map.put("sk-sk", "斯洛伐克文(斯洛伐克)");
					map.put("sl-si", "斯洛文尼亚文(斯洛文尼亚)");
					map.put("sq-al", "阿尔巴尼亚文(阿尔巴尼亚)");
					map.put("sr-yu", "塞尔维亚文(南斯拉夫)");
					map.put("sv-se", "瑞典文(瑞典)");
					map.put("th-th", "泰文(泰国)");
					map.put("tr-tr", "土耳其文(土耳其)");
					map.put("uk-ua", "乌克兰文(乌克兰)");
					map.put("zh-cn", "中文(大陆)");
					map.put("zh-hk", "中文(香港)");
					map.put("zh-tw", "中文(台湾)");
					map.put("zh-sg", "中文(新加坡)");

					Object ks[] = map.keySet().toArray();
					Object vs[] = map.values().toArray();
					String arr[] = new String[ks.length];
					langNameArr = new String[ks.length];
					for (int i = 0; i < map.size(); i++) {
						arr[i] = ks[i].toString();
						langNameArr[i] = vs[i].toString();
					}

					langCodeArr = arr;
				}
			}
		if (EmptyUtil.isNull(lang))
			return "其他";
		lang = lang.trim();
		for (int i = 0; i < langCodeArr.length; i++)
			if (lang.equalsIgnoreCase(langCodeArr[i]))
				return langNameArr[i];

		return "其他";
	}

	/**
	 * 分辩率
	 * 
	 * @param screen
	 * @return
	 */
	public static String getScreen(String screen) {
		if (screen == null)
			return "其他";
		if (!screen.equals("1024x768") && !screen.equals("1280x800")
				&& !screen.equals("1440x900") && !screen.equals("1366x768")
				&& !screen.equals("1280x1024") && !screen.equals("1280x768")
				&& !screen.equals("1152x864") && !screen.equals("1600x900")
				&& !screen.equals("1680x1050") && !screen.equals("800x600")
				&& !screen.equals("1280x960") && !screen.equals("1920x1080")
				&& !screen.equals("1280x720"))
			return "其他";
		else
			return screen;
	}

	/**
	 * 浏览器
	 * 
	 * @param useragent
	 * @return
	 */
	public static String getBrowser(String useragent) {
		if (useragent.indexOf("Netscape") > 0)
			return "Netscape";
		if (useragent.indexOf("Firefox/1.") > 0)
			return "Firefox 1.x";
		if (useragent.indexOf("Firefox/2.") > 0)
			return "Firefox 2.x";
		if (useragent.indexOf("Firefox/3.") > 0)
			return "Firefox 3.x";
		if (useragent.indexOf("Safari") > 0)
			return "Safari";
		if (useragent.indexOf("Opera") > 0)
			return "Opera";
		if (useragent.indexOf("Chrome") > 0)
			return "Chrome";
		if (useragent.indexOf("MSIE 8") > 0)
			return "IE8";
		if (useragent.indexOf("MSIE 7") > 0)
			return "IE7";
		if (useragent.indexOf("MSIE 6") > 0)
			return "IE6";
		if (useragent.indexOf("MSIE 5") > 0)
			return "IE5";
		if (useragent.indexOf("MSIE 4") > 0)
			return "IE4";
		if (useragent.indexOf("MSIE 3") > 0)
			return "IE3";
		else
			return "其他";
	}

	/**
	 * 获取唯一编号
	 * 
	 * @return
	 */
	public static String getUniqueID() {
		byte b[] = new byte[TRANSACTION_ID_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(b);
		return toPrintable(b);
	}

	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (EmptyUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (EmptyUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (EmptyUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (EmptyUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (EmptyUtil.isNull(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	private static String toPrintable(byte b[]) {
		char out[] = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % cs.length;
			if (index < 0)
				index += cs.length;
			out[i] = cs[index];
		}

		return new String(out);
	}

	private static void initIPRanges() {
		if (districtCodes == null)
			synchronized (mutex) {
				if (districtCodes == null) {
					// DataTable dt = (new
					// QueryBuilder("select IPRanges,DistrictCode from ZDIPRange")).executeDataTable();
					// String codes[] = new String[dt.getRowCount()];
					// ipRanges = new Object[dt.getRowCount()];
					String codes[] = new String[10];
					ipRanges = new Object[10];
					// for(int i = 0; i < dt.getRowCount(); i++){
					for (int i = 0; i < 10; i++) {
						// String code = dt.getString(i, 1);
						// String ranges = dt.getString(i, 0);
						String code = "1";
						String ranges = "1";
						codes[i] = code;
						String arr[] = ranges.split(",");
						long r[] = new long[arr.length * 2];
						for (int j = 0; j < arr.length; j++) {
							String arr2[] = arr[j].split("+");
							r[2 * j] = Long.parseLong(arr2[0]);
							r[2 * j + 1] = r[2 * j] + Long.parseLong(arr2[1]);
						}

						ipRanges[i] = r;
					}

					districtCodes = codes;
				}
			}
	}

	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		return getCookieValue(request, cookieName, "");
	}

	public static String getCookieValue(HttpServletRequest request,
			String cookieName, String defaultValue) {
		Cookie cookies[] = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookieName.equalsIgnoreCase(cookie.getName())) {
					try {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.warn(e.getLocalizedMessage());
						return defaultValue;
					}
				}
			}
		}
		return defaultValue;
	}

	public static void setCookieValue(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String cValue) {
		setCookieValue(request, response, cookieName, 0x28de80, cValue);
	}

	public static void setCookieValue(HttpServletRequest request,
			HttpServletResponse response, String cookieName, int maxAge,
			String cValue) {
		Cookie cookies[] = request.getCookies();
		boolean cookieexistflag = false;
		String contextPath = request.getContextPath();
		contextPath = contextPath.substring(0, contextPath.length() - 1);
		try {
			cValue = URLEncoder.encode(cValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equalsIgnoreCase(cookie.getName())) {
				cookieexistflag = true;
				cookie.setValue(cValue);
				cookie.setPath(contextPath);
				cookie.setMaxAge(maxAge);
				response.addCookie(cookie);
			}
		}

		if (!cookieexistflag) {
			Cookie cookie = new Cookie(cookieName, cValue);
			cookie.setPath(contextPath);
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		}
	}
}
