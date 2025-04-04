package org.example.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class HelloTask {

    //每3秒执行一次
//    @Scheduled(cron ="*/6 * * * * ?")
    private void printHello() {
        System.out.println("hello");
    }

}
