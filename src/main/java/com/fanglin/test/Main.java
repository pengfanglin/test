package com.fanglin.test;

import com.fanglin.test.model.User;
import com.fanglin.test.utils.EncodeUtils;
import com.fanglin.test.utils.JdbcUtils;
import com.fanglin.test.utils.RandomValueUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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
            int number = 200;
            int error=0;
            String password = EncodeUtils.md5("123456");
            List<User> users = new ArrayList<>(number);
            StringBuilder sb = new StringBuilder("insert into user(name,sex,email,phone,password) values ");
            for (int k = 0; k < number; k++) {
                sb.append("(?,?,?,?,?),");
                users.add(new User());
            }
            sb.deleteCharAt(sb.length() - 1);
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            for (int i = 1; i < time; i++) {
                for (int j = 0; j < number; j++) {
                    int sex = j % 6 > 1 ? 0 : 1;
                    User user = users.get(j);
                    user.setSex(sex)
                            .setName(RandomValueUtils.chineseName(sex))
                            .setEmail(RandomValueUtils.email(50))
                            .setPhone(RandomValueUtils.phone())
                            .setPassword(password);
                }
                for (int k = 0; k < users.size(); k++) {
                    User user = users.get(k);
                    ps.setString((k * 5) + 1, user.getName());
                    ps.setInt((k * 5) + 2, user.getSex());
                    ps.setString((k * 5) + 3, user.getEmail());
                    ps.setString((k * 5) + 4, RandomValueUtils.createRandom(11));
                    ps.setString((k * 5) + 5, user.getPassword());
                }
                try {
                    ps.executeUpdate();
                } catch (Exception e) {
                    error++;
                    log.error("执行{}此，失败{}次",i,error);
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
