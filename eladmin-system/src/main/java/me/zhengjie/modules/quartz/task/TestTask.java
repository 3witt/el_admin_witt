/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.quartz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.system.domain.JjData;
import me.zhengjie.modules.system.domain.JjLatestNetWorth;
import me.zhengjie.modules.system.domain.JjRealTimeDynamic;
import me.zhengjie.modules.system.service.JjDataService;
import me.zhengjie.modules.system.service.JjLatestNetWorthService;
import me.zhengjie.modules.system.service.JjRealTimeDynamicService;
import me.zhengjie.modules.system.service.dto.JjDataDto;
import me.zhengjie.modules.system.service.dto.JjLatestNetWorthDto;
import me.zhengjie.modules.system.service.dto.JjRealTimeDynamicDto;
import me.zhengjie.utils.DateUtils;
import me.zhengjie.utils.JjUtil;
import me.zhengjie.utils.ParamsUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 测试用
 *
 * @author Zheng Jie
 * @date 2019-01-08
 */
@Slf4j
@Component
public class TestTask {

    private final JjDataService jjDataService;
    private final JjRealTimeDynamicService jjRealTimeDynamicService;
    private final JjLatestNetWorthService jjLatestNetWorthService;

    public TestTask(JjDataService jjDataService, JjRealTimeDynamicService jjRealTimeDynamicService, JjLatestNetWorthService jjLatestNetWorthService) {
        this.jjDataService = jjDataService;
        this.jjRealTimeDynamicService = jjRealTimeDynamicService;
        this.jjLatestNetWorthService = jjLatestNetWorthService;
    }

    public void run() {
        log.info("run 执行成功");
    }

    public void run1(String str) {
        log.info("run1 执行成功，参数为： {}" + str);
    }

    public void run2() {
        log.info("run2 执行成功");
    }


    /**
     * 更新所有基金信息
     *
     * @author witt
     * @description
     * @date 11:21 2020/8/6
     */
    public void updateAllJj(String params) {
        // 参数处理
        log.info("参数：" + params);
        Map<String, Object> mapParams = ParamsUtil.taskParams(params);

        if (mapParams != null && mapParams.get("删除所有").equals("是")) {
            jjDataService.deleteAll();
        }

        String urlString = "http://fund.eastmoney.com/js/fundcode_search.js";
        String infoString = JjUtil.getJjRealTimeDynamic(urlString);
        if (infoString == null || infoString.length() <= 0 || infoString.equals("var r = []")) {
            return;
        }
        infoString = infoString.replace("var r = ", "");
        infoString = infoString.replace(";", "");
        List<String[]> infoList = JSONArray.parseArray(infoString, String[].class);

        List<JjData> jjDatas = new ArrayList<>();
        for (String[] stringList : infoList) {
            JjData jjData = new JjData();
            jjData.setJjCode(stringList[0]);
            jjData.setJjName(stringList[2]);
            jjData.setJjType(stringList[3]);
            JjData jjData1 = jjDataService.findByJjCode(stringList[0]);
            if (jjData1 != null) {
                continue;
            }
            log.debug(JSON.toJSONString(jjData));
            jjDatas.add(jjData);
//            try {
//                JjDataDto jjDataDto = jjDataService.create(jjData);
//                log.info(JSON.toJSONString(jjDataDto));
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                continue;
//            }
        }
        try {
            long start = System.currentTimeMillis();
            log.info("----------------插入数据数量：" + String.valueOf(jjDatas.size()));
            List<JjData> res = jjDataService.saveAll(jjDatas);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            log.info("耗时：" + timeElapsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取基金今日实际增长值
     *
     * @author witt
     * @description
     * @date 13:36 2020/8/5
     */
    public void getJjTodayValue(String params) {
        // 参数处理
        log.info("参数：" + params);
        Map<String, Object> mapParams = ParamsUtil.taskParams(params);

        if (mapParams != null && mapParams.get("手动").equals("否")) {
            // 判断今天是不是周六日，如果是，直接退出
            try {
                if (DateUtils.isWeekend(new Date())) {
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        log.info("非周末");
        int pageNumber = 0;
        int pageSize = 1000;
        boolean haveData = true;

        while (haveData) {
            // TODO 查询业务端有，渠道端没有的
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
            Page<JjData> jjDatas = jjDataService.findAll(pageable);
            if (jjDatas.getContent() == null || jjDatas.getContent().size() <= 0) {
                haveData = false;
                continue;
            }
            log.debug("businessList");
            for (JjData jjData : jjDatas) {
                String urlString = "http://fundgz.1234567.com.cn/js/" + jjData.getJjCode() + ".js?rt=1463558676006";
                String infoString = JjUtil.getJjRealTimeDynamic(urlString);
                // 返回数据处理
                if (infoString.equals("jsonpgz();")) {
                    continue;
                }
                if (infoString == null || infoString.length() <= 0) {
                    continue;
                }
                infoString = infoString.replace("jsonpgz(", "");
                infoString = infoString.replace(");", "");
                log.debug(infoString);
                JjRealTimeDynamic jjRealTimeDynamic = JSON.parseObject(infoString, JjRealTimeDynamic.class);

                // TODO 判断是不是最新的下午3点更新时间--预留
                String checkTIme = jjRealTimeDynamic.getGztime();
                if (checkTIme.indexOf("15:00") > 1) {
                }

                try {
                    JjRealTimeDynamicDto jjRealTimeDynamicDto = jjRealTimeDynamicService.create(jjRealTimeDynamic);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    continue;
                }

            }
            pageNumber++;
        }
    }

    /**
     * 获取基金昨日增长值
     *
     * @author witt
     * @description
     * @date 10:14 2020/8/7
     */
    public void getYesterdayInfo(String params) {
        // 参数处理
        log.info("参数：" + params);
        Map<String, Object> mapParams = ParamsUtil.taskParams(params);
        if (mapParams != null && mapParams.get("手动").equals("否")) {
            // 判断今天是不是周日，如果是，直接退出
            if (DateUtils.isSunDay()) {
                return;
            }
        }
        log.info("非周末");
        int pageNumber = 0;
        int pageSize = 1000;
        boolean haveData = true;

        while (haveData) {
            // TODO 查询业务端有，渠道端没有的
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
            Page<JjData> jjDatas = jjDataService.findAll(pageable);
            if (jjDatas.getContent() == null || jjDatas.getContent().size() <= 0) {
                haveData = false;
                continue;
            }
            log.debug("businessList");
            for (JjData jjData : jjDatas) {

                String urlString = "http://fund.eastmoney.com/pingzhongdata/" + jjData.getJjCode() + ".js?v=20160518155842";
                String infoString = JjUtil.getJjRealTimeDynamic(urlString);
                if (infoString == null || infoString.equals("") || infoString.length() <= 0) {
                    continue;
                }
                if (!infoString.contains("var Data_netWorthTrend =")) {
                    continue;
                }
                String[] lineList = infoString.split("var Data_netWorthTrend =");
                if (!lineList[1].contains(";/*累计净值走势*/")) {
                    continue;
                }
                String[] newLineList = lineList[1].split(";/\\*累计净值走势\\*/");
                infoString = newLineList[0];
                if (infoString == null || infoString.equals("[]") || infoString.length() <= 0) {
                    continue;
                }
                log.info(infoString);
                List<Map> infoList = JSONArray.parseArray(infoString, Map.class);
                if (infoList == null || infoList.size() <= 0) {
                    continue;
                }
                log.info(JSON.toJSONString(infoList));

                log.info(JSON.toJSONString(infoList.get(infoList.size() - 1)));

                Map map = infoList.get(infoList.size() - 1);
                log.info(map.get("equityReturn").toString());

                String mapString = JSON.toJSONString(map);
                JjLatestNetWorth jjLatestNetWorth = JSON.parseObject(mapString, JjLatestNetWorth.class);
                jjLatestNetWorth.setJjCode(jjData.getJjCode());
                jjLatestNetWorth.setXTime(new Timestamp(new Date(Long.valueOf(map.get("x").toString())).getTime()));

                try {
                    JjLatestNetWorthDto jjLatestNetWorthDto = jjLatestNetWorthService.create(jjLatestNetWorth);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    continue;
                }

            }
            pageNumber++;
        }
    }

    public static void main(String[] a) {
        // 使用哪个方法
        int status = 2;

        switch (status) {
            case 1:
                try {
                    URL url = new URL("http://fund.eastmoney.com/pingzhongdata/002692.js?v=20160518155842");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);

                        line = line.replace("jsonpgz(", "");
                        line = line.replace(");", "");
//                line = line.replace("\"", "\\\"");

                        log.info(line);
                        JjRealTimeDynamic jjRealTimeDynamic = JSON.parseObject(line, JjRealTimeDynamic.class);

                        log.info(JSON.toJSONString(jjRealTimeDynamic));


                        writer.write(line);
                        writer.newLine();
                    }
                    reader.close();
                    writer.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    URL url = new URL("http://fund.eastmoney.com/pingzhongdata/002692.js?v=20160518155842");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);

//                        line = line.replace("jsonpgz(", "");
//                        line = line.replace(");", "");
                        String[] lineList = line.split("var Data_netWorthTrend =");
                        String[] newLineList = lineList[1].split(";/\\*累计净值走势\\*/");
                        String infoString = newLineList[0];
                        System.out.println(infoString);

                        List<Map> infoList = JSONArray.parseArray(infoString, Map.class);
                        System.out.println(JSON.toJSONString(infoList));

                        System.out.println(JSON.toJSONString(infoList.get(infoList.size() - 1)));

                        Map map = infoList.get(infoList.size() - 1);
                        System.out.println(map.get("equityReturn").toString());

                        System.out.println(new Date(Long.valueOf(map.get("x").toString())));

                        writer.write(line);
                        writer.newLine();
                    }
                    reader.close();
                    writer.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

}
