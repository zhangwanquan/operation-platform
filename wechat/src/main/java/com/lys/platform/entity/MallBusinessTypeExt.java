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
 * @Date 2025/7/30 08:57
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "mall_business_type_ext")
public class MallBusinessTypeExt implements Serializable {
    private static final long serialVersionUID = -2768511978940661431L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mall_id")
    private Integer mallId;
    @Column(name = "business_type_id")
    private Integer businessTypeId;
    @Column(name = "business_type_rate")
    private Double businessTypeRate;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

}
