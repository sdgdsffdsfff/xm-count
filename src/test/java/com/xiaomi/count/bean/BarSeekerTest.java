package com.xiaomi.count.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lijie on 2015/8/26.
 */
public class BarSeekerTest {


    private Map<Integer, BarInfo> map;

    @Test
    public void testInit() throws Exception {
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

            System.out.print(map.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("缓存网吧信息失败");
        }
    }


}

