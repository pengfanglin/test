package com.fanglin.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 彭方林
 * @version 1.0
 * @date 2019/6/11 18:09
 **/
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<10;i++){
            final int finalI=i;
            new Thread(() -> {
                Main.start();
            }).start();

        }
    }
}
