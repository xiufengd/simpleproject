package com.xiufengd.simpleproject.common.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SysTimerTask {

    private int count=0;

//    @Scheduled(cron = "*/6 * * * * ?")
    private void process1(){
        System.out.println("this is scheduler1 task running " + (count++));
    }

//    @Scheduled(cron = "*/5 * * * * ?")
    private void process2(){
        System.out.println("this is scheduler2 task running " + (count++));
    }
}
