package com.tsingtec.mini.aop.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author lj
 * @Date 2020/3/21 14:23
 * @Version 1.0
 * 定时任务
 */
@Component
@EnableScheduling
public class Tasks {


    /**
     * 定时统计
     * 3,6,9,12月的1,2,3日1点1分1秒进行一次统计(一个月三次只是为了防止未进行统计)
     * https://qqe2.com/cron 在线生成cron
     */
    @Scheduled(cron = "1 1 1 1,2,3 3,6,9,12 ? ")
    public void statistics(){

    }


}
