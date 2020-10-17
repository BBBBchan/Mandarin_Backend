package com.bbchan.library.config;

import com.bbchan.library.service.BookService;
import com.bbchan.library.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class SimpleScheduleConfig {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BookService bookService;

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    private void configureTasks() {
        historyService.flushAllHistory();
        bookService.flushAllBook();
        System.err.println("执行定时任务1: " + LocalDateTime.now());
    }
}
