package com.fanglin.test.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户信息
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/6/11 15:15
 **/
@Data
@Accessors(chain = true)
public class User {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除
     */
    private Integer isDelete;
}
