package me.zhengjie.modules.quartz.task;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.system.service.JjDataService;
import me.zhengjie.modules.system.service.JjLatestNetWorthService;
import me.zhengjie.modules.system.service.JjRealTimeDynamicService;
import org.springframework.stereotype.Component;

/**
 * @Authoor: witt
 * @Decsription:
 * @Date: Created in 17:21 2020/8/10
 * @Modified:
 **/
@Slf4j
@Component
public class CalculationTask {


    private final JjDataService jjDataService;
    private final JjRealTimeDynamicService jjRealTimeDynamicService;
    private final JjLatestNetWorthService jjLatestNetWorthService;

    public CalculationTask(JjDataService jjDataService, JjRealTimeDynamicService jjRealTimeDynamicService, JjLatestNetWorthService jjLatestNetWorthService) {
        this.jjDataService = jjDataService;
        this.jjRealTimeDynamicService = jjRealTimeDynamicService;
        this.jjLatestNetWorthService = jjLatestNetWorthService;
    }

    /**
     * 对比今日估算值与实际值的大小
     *
     * @author witt
     * @description
     * @date 17:22 2020/8/10
     */
    public void calculation(String params) {

    }
}
