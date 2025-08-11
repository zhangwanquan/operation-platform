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
@Table(name = "mall_info")
public class MallInfo implements Serializable {
    private static final long serialVersionUID = -2768511978940661431L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Integer type;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "status")
    private Integer status;
    @Column(name = "level")
    private Integer level;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "address")
    private String address;
    @Column(name = "cover_url")
    private String coverUrl;
    @Column(name = "open_time")
    private Date openTime;
    @Column(name = "investment_area")
    private Integer investmentArea;
    @Column(name = "main_brand_store")
    private String mainBrandStore;
    @Column(name = "shop_scale")
    private Integer shopScale;
    @Column(name = "mall_area")
    private Integer mallArea;
    @Column(name = "parking_space_number")
    private Integer parkingSpaceNumber;
    @Column(name = "property_fee")
    private Double propertyFee;
    @Column(name = "mall_operator")
    private String mallOperator;
    @Column(name = "mall_investor")
    private String mallInvestor;
    @Column(name = "features")
    private String features;
    @Column(name = "plan_url")
    private String planUrl;
    @Column(name = "approve_status")
    private Integer approveStatus;
    @Column(name = "score")
    private Double score;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

}
