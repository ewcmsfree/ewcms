package com.ewcms.pinyin4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * This classes define how the Hanyu Pinyin should be outputted.
 * 
 * <p>
 * The output feature includes:
 * <ul>
 * <li>Output format of character 'ü';
 * <li>Output format of Chinese tones;
 * <li>Cases of letters in outputted string
 * </ul>
 * 
 * <p>
 * Default values of these features are listed below:
 * 
 * <p>
 * HanyuPinyinVCharType := WITH_U_AND_COLON <br>
 * HanyuPinyinCaseType := LOWERCASE <br>
 * HanyuPinyinToneType := WITH_TONE_NUMBER <br>
 * 
 * <p>
 * <b>Some combinations of output format options are meaningless. For example,
 * WITH_TONE_MARK and WITH_U_AND_COLON.</b>
 * 
 * <p>
 * The combination of different output format options are listed below. For
 * example, '吕'
 * 
 * <table border="1">
 * <tr>
 * <th colspan="4"> LOWERCASE </th>
 * </tr>
 * <tr>
 * <th>Combination</th>
 * <th>WITH_U_AND_COLON</th>
 * <th>WITH_V</th>
 * <th>WITH_U_UNICODE</th>
 * </tr>
 * <tr>
 * <th>WITH_TONE_NUMBER</th>
 * <td>lu:3</td>
 * <td>lv3</td>
 * <td>lü3</td>
 * </tr>
 * <tr>
 * <th>WITHOUT_TONE</th>
 * <td>lu:</td>
 * <td>lv</td>
 * <td>lü</td>
 * </tr>
 * <tr>
 * <th>WITH_TONE_MARK</th>
 * <td><font color="red">throw exception</font></td>
 * <td><font color="red">throw exception</font></td>
 * <td>lǚ</td>
 * </tr>
 * </table>
 * 
 * <table border="1">
 * <tr>
 * <th colspan="4"> UPPERCASE </th>
 * </tr>
 * <tr>
 * <th>Combination</th>
 * <th>WITH_U_AND_COLON</th>
 * <th>WITH_V</th>
 * <th>WITH_U_UNICODE</th>
 * </tr>
 * <tr>
 * <th>WITH_TONE_NUMBER</th>
 * <td>LU:3</td>
 * <td>LV3</td>
 * <td>LÜ3</td>
 * </tr>
 * <tr>
 * <th>WITHOUT_TONE</th>
 * <td>LU:</td>
 * <td>LV</td>
 * <td>LÜ</td>
 * </tr>
 * <tr>
 * <th>WITH_TONE_MARK</th>
 * <td><font color="red">throw exception</font></td>
 * <td><font color="red">throw exception</font></td>
 * <td>LǙ</td>
 * </tr>
 * </table>
 * 
 * @author wuzhijun
 * 
 */
public class ConvertToPinYinTest {

	private static final Logger logger = LoggerFactory.getLogger(ConvertToPinYinTest.class);
	
	@Test
	public void converterToPinYin() {
		String chinese = "就业信息2";
		String pinyinString = "";
		char[] charArray = chinese.toCharArray();
		// 根据需要定制输出格式，我用默认的即可
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] > 128) {
					pinyinString += PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat)[0];
				} else {
					pinyinString += charArray[i];
				}
			}
			logger.info(pinyinString);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
	}
}