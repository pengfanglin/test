package com.fanglin.test.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * jdbc连接
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/4/3 16:33
 **/
@Slf4j
public class JdbcUtils {
    private static Connection con;

    static {
        String url = "jdbc:mysql://192.168.48.100:3306/es?useSSL=false";
        String username = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        return con;
    }

    /**
     * 关闭数据库资源
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
