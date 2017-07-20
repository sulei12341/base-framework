/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ydhl.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class MapUtilPlus {

    /**
     * 判断指定键-值映射是否为空
     *
     * @param map 键-值映射
     * @return true 为空， false 不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return org.apache.commons.collections4.MapUtils.isEmpty(map);
    }

    /**
     * 判断指定键-值映射是否不为空
     *
     * @param map 键-值映射
     * @return true 为空， false 不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return org.apache.commons.collections4.MapUtils.isNotEmpty(map);
    }

    /**
     * 把map转换成url请求参数
     *
     * @param params 待组装参数
     * @return 组装后结果
     */
    public static String getUrlParamForSign(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        String urlParamStr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                urlParamStr = urlParamStr + key + "=" + value;
            } else {
                urlParamStr = urlParamStr + key + "=" + value + "&";
            }
        }
        return urlParamStr;
    }

    /**
     * 把map转换成url请求参数
     *
     * @param params 待组装参数
     * @return 组装后结果
     */
    public static String getUrlParam(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder urlParamStr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            try {
                value = StringUtilPlus.isEmpty(value) ? "" : URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                value = "";
            }

            urlParamStr.append(key);
            urlParamStr.append("=");
            urlParamStr.append(value);
            if (i != keys.size() - 1) {// 拼接时，不包括最后一个&字符
                urlParamStr.append("&");
            }
        }
        return urlParamStr.toString();
    }
}
