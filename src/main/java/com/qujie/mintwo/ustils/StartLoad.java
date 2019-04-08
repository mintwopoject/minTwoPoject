package com.qujie.mintwo.ustils;



import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 启动加载
 */
@Component
public class StartLoad implements CommandLineRunner {

    @Override
    @Async
    public void run(String... var1)  {
        System.out.println("启动加载");
    }

}
