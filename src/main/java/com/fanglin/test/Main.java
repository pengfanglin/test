package com.fanglin.test;

import com.fanglin.test.model.User;
import com.fanglin.test.utils.EncodeUtils;
import com.fanglin.test.utils.JdbcUtils;
import com.fanglin.test.utils.RandomValueUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 启动类
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/6/11 14:58
 **/
@Slf4j
public class Main {
    public static void start() {
        long start = System.currentTimeMillis();
        try {
            Connection connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);
            int time = 2000000;
            int number = 500;
            String password = EncodeUtils.md5("123456");
            User user = new User();
            PreparedStatement ps = connection.prepareStatement("insert into user(name,sex,email,phone,password) values(?,?,?,?,?)");
            for (int i = 1; i < time; i++) {
                for (int j = 0; j < number; j++) {
                    int sex = j % 6 > 1 ? 0 : 1;
                    user.setSex(sex)
                        .setName(RandomValueUtils.chineseName(sex))
                        .setEmail(RandomValueUtils.email(50))
                        .setPhone(RandomValueUtils.phone())
                        .setPassword(password);
                    ps.setString(1, user.getName());
                    ps.setInt(2, user.getSex());
                    ps.setString(3, user.getEmail());
                    ps.setString(4, user.getPhone());
                    ps.setString(5, user.getPassword());
                    try {
                        ps.executeUpdate();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                connection.commit();
                if (i % 100 == 0) {
                    log.info("第{}次导入，剩余{}次", i, time - i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("总耗时:{}", (System.currentTimeMillis() - start) / 1000);
    }
}
