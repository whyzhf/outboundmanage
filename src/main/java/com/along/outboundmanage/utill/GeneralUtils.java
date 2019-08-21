package com.along.outboundmanage.utill;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: why
 * @Data:2019/4/29
 * @Deacription:
 */
public class GeneralUtils {

    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj){
        return (null == obj) ? true : false;
    }

    /**
     * 判断输入的字符串参数是否为空
     * @return boolean 空则返回true,非空则false
     */
    public static boolean isEmpty(String input) {
        return null==input || 0==input.length() || 0==input.replaceAll("\\s", "").length();
    }
   /**
    *首字母转小写
    */
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
     *首字母转大写
     */
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
     * json字符串转 Json格式
     * */
    public static JSON getJson(String jsonStr){
       return JSON.parseObject(jsonStr);
    }
    /**
     * 对象转json字符串
     * */
    public static String getJsonStr(Object obj){
        return  JSONObject.toJSONString(obj);
    }

}
