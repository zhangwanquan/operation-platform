package com.lys.platform.entity;

import io.swagger.annotations.ApiModelProperty;
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
 * @Date 2025/7/29 11:18
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "store_info")
public class StoreInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mall_id")
    private Integer mallId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Integer type;
    @Column(name = "store_area")
    private Integer storeArea;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "rental_status")
    private Integer rentalStatus;
    @Column(name = "cover_url")
    private String coverUrl;
    @Column(name = "business_type")
    private Integer businessType;
    @Column(name = "business_name")
    private String  businessName;
    @Column(name = "floor")
    private String floor;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
