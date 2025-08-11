package com.lys.platform.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 09:54
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "admin_user")
public class AdminUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "name")
    private String name;
    @Column(name = "avatar_addr")
    private String avatarAddr;
    @Column(name = "password")
    private String password;
    @Column(name = "reset_password_flag")
    private Integer resetPasswordFlag;
    @Column(name = "status")
    private Integer status;
    @Column(name = "reliever_id")
    private Integer relieverId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
