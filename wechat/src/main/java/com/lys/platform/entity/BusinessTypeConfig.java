package com.lys.platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 13:50
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "business_type_config")
public class BusinessTypeConfig implements Serializable {
    private static final long serialVersionUID = -613368782098907182L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;




}
