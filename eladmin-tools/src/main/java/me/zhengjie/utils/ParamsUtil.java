package me.zhengjie.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Authoor: witt
 * @Decsription:
 * @Date: Created in 10:41 2020/8/11
 * @Modified:
 **/
public class ParamsUtil {

    public static Map<String, Object> taskParams(String paramsString) {
        Map<String, Object> resParams = new LinkedHashMap<>();
        if (paramsString.contains("{") && paramsString.contains("}")) {
            resParams = JSONObject.parseObject(paramsString, Map.class);
        } else {
            resParams.put("params", paramsString);
        }
        return resParams;
    }
}
