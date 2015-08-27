package com.xiaomi.count.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.count.model.Agent;
import org.apache.log4j.Logger;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网吧id跟名称转换
 * Created by lijie on 2015/8/26.
 */
public class BarSeeker {

    private static final Logger LOGGER = Logger.getLogger(BarSeeker.class);

    private Map<Integer, BarInfo> map;

    public void init() {
        try {
            map = new HashMap<Integer, BarInfo>();

            URL url = new URL("http://db.nmenu.cn/wb/search7daynetbarinfo.ashx?ucid=e255cfa8-2c08-4878-9b52-8ac259a697a2&a=NZRi3TPLLHc=");    // 把字符串转换为URL请求地址
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();// 打开连接
            httpURLConnection.connect();// 连接会话

            ObjectMapper objectMapper = new ObjectMapper();

            List<BarInfo> barInfoList = objectMapper.readValue(httpURLConnection.getInputStream(), new TypeReference<List<BarInfo>>() {
            });

            for (BarInfo barInfo : barInfoList) {
                map.put(barInfo.getId(), barInfo);
            }

        } catch (Exception e) {
            LOGGER.error("缓存网吧信息失败...");
        }
    }

    public String getBarname(Object value) {
        if (value == null) return "";
        try {
            BarInfo barInfo = map.get(Integer.valueOf(value.toString()));
            if (barInfo != null) {
                return barInfo.getBarname();
            }
        } catch (Exception e) {

        }
        return "";
    }


}

