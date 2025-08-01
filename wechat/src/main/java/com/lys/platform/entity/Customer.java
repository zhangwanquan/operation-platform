package com.lys.platform.entity;

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
 * @Date 2025/7/26 13:50
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "name")
    private String name;
    // 认证类型，0-表示个人认证，1-品牌认证，商场客户没有认证类型
    @Column(name = "auth_type")
    private Integer authType;
    // 客户类型，0-品牌客户，1-商场客户
    @Column(name = "customer_type")
    private Integer customerType;
    @Column(name = "consultant_id")
    private Integer consultantId;
    // 审核状态 0-未提交，1-未审核，2-已审核，3-已拒绝
    @Column(name = "approve_status")
    private Integer approveStatus;
    @Column(name = "regis_time")
    private Date regisTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
