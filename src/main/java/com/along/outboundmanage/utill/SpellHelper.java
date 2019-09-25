package com.along.outboundmanage.utill;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Auther: why
 * @Data:2019/9/21
 * @Deacription:
 */
public class SpellHelper {
	public static void main(String[] args) {
		SpellHelper hanyuPinyinHelper = new SpellHelper() ;
		System.out.println(hanyuPinyinHelper.toHanyuPinyin("德州市德州市德州市德州市德州市德州市德州市德州市").toUpperCase());
		/*System.out.println(getFirstLettersUp("旅查一12"));
		System.out.println(getFirstLettersLo("旅查一12"));
		System.out.println(getPinyinString("旅查一12"));*/
		//专门为名字翻译
		System.out.println("汉".toUpperCase());
		System.out.println("07001E57363638422D56312E30303B4D323A31302C3132303B4F4E3A312".length());
	}

	public static  void print(){

	}

	/**
	 * 将文字转为汉语拼音
	 *
	 */
	public static String toHanyuPinyin(String ChineseLanguage){
		char[] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出拼音全部小写
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 不带声调
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		//特殊拼音格式
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
		try {
			for (int i=0; i<cl_chars.length; i++){
				if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")){
					// 如果字符是中文,则将中文转为汉语拼音
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
				} else {// 如果字符不是中文,则不转换
					hanyupinyin += cl_chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
		}
		return hanyupinyin;
	}

	public static String getFirstLettersUp(String ChineseLanguage){
		return getFirstLetters(ChineseLanguage ,HanyuPinyinCaseType.UPPERCASE);
	}

	public static String getFirstLettersLo(String ChineseLanguage){
		return getFirstLetters(ChineseLanguage ,HanyuPinyinCaseType.LOWERCASE);
	}

	public static String getFirstLetters(String ChineseLanguage,HanyuPinyinCaseType caseType) {
		char[] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出拼音全部大写
		defaultFormat.setCaseType(caseType);
		// 不带声调
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			for (int i = 0; i < cl_chars.length; i++) {
				String str = String.valueOf(cl_chars[i]);
				if (str.matches("[\u4e00-\u9fa5]+")) {
					// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1);
				} else if (str.matches("[0-9]+")) {
					// 如果字符是数字,取数字
					hanyupinyin += cl_chars[i];
				} else if (str.matches("[a-zA-Z]+")) {
					// 如果字符是字母,取字母
					hanyupinyin += cl_chars[i];
				} else {
					// 否则不转换
					//如果是标点符号的话，带着
					hanyupinyin += cl_chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
		}
		return hanyupinyin;
	}

	public static String getPinyinString(String ChineseLanguage){
		char[] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出拼音全部大写
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 不带声调
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			for (int i = 0; i < cl_chars.length; i++) {
				String str = String.valueOf(cl_chars[i]);
				if (str.matches("[\u4e00-\u9fa5]+")) {
					// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
							cl_chars[i], defaultFormat)[0];
				} else if (str.matches("[0-9]+")) {
					// 如果字符是数字,取数字
					hanyupinyin += cl_chars[i];
				} else if (str.matches("[a-zA-Z]+")) {
                   //字符是字母,取字母
					hanyupinyin += cl_chars[i];
				} else {
					// 否则不转换
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
		}
		return hanyupinyin;
	}
	/**
	 * 取第一个汉字的第一个字符
	 * @Title: getFirstLetter
	 * @Description: TODO
	 * @return String
	 * @throws
	 */
	public static String getFirstLetter(String ChineseLanguage){
		char[] cl_chars = ChineseLanguage.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出拼音全部大写
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		// 不带声调
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			String str = String.valueOf(cl_chars[0]);
			if (str.matches("[\u4e00-\u9fa5]+")) {
				// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
				hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
						cl_chars[0], defaultFormat)[0].substring(0, 1);;
			} else if (str.matches("[0-9]+")) {
				// 如果字符是数字,取数字
				hanyupinyin += cl_chars[0];
			} else if (str.matches("[a-zA-Z]+")) {
				// 如果字符是字母,取字母
				hanyupinyin += cl_chars[0];
			} else {
				// 否则不转换
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
		}
		return hanyupinyin;
	}



	//将中文转换为英文
	public static String getEname(char name) {
		HanyuPinyinOutputFormat pyFormat = new  HanyuPinyinOutputFormat();
		pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			if (String.valueOf(name).matches("[\u4e00-\u9fa5]+")){
				// 如果字符是中文,则将中文转为汉语拼音
				return PinyinHelper.toHanyuPinyinStringArray(name, pyFormat)[0];
			} else {// 如果字符不是中文,则不转换
				return name+"";
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
			return "X";
		}
	}

	//姓、名的第一个字母需要为大写
	public static String getUpEname(String name) {
		char[] strs = name.toCharArray();
		String newname = null;

		//名字的长度
		if (strs.length == 2) {
			newname = toUpCase(getEname(strs[0])) + " "
					+ toUpCase(getEname(strs[1]));
		} else if (strs.length == 3){
			newname = toUpCase(getEname(strs[0])) + " "
					+ toUpCase(getEname(strs[1]))
					+ toLowCase(getEname(strs[2]));
		}else if (strs.length == 4){
			newname = toUpCase(getEname(strs[0]))
					+ toLowCase(getEname(strs[1])) + " "
					+ toUpCase(getEname(strs[2]))
					+ toLowCase(getEname(strs[3]));
		} else{
			newname = toUpCase(getEname(strs[0]))+" ";
			for (int i=1;i<strs.length;i++){
				newname+=  toLowCase(getEname(strs[i]));
			}

		}
		return newname;
	}

	//首字母大写
	private static String toUpCase(String str) {
		StringBuffer newstr = new StringBuffer();
		newstr.append((str.substring(0, 1)).toUpperCase()).append(
				str.substring(1, str.length()));
		return newstr.toString();
	}
	private static String toLowCase(String str) {
		StringBuffer newstr = new StringBuffer();
		newstr.append(str);
		return newstr.toString();
	}
}