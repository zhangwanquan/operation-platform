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
 * @Date 2025/7/29 10:24
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "requirement")
public class Requirement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "region_code")
    private String regionCode;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "county_code")
    private String countyCode;
    @Column(name = "county_name")
    private String countyame;

    @Column(name = "brand_type")
    private Integer brandType;
    @Column(name = "brand_unit_price")
    private String brandUnitPrice;
    @Column(name = "store_number")
    private Integer storeNumber;
    @Column(name = "operation_mode")
    private Integer operationMode;
    @Column(name = "site_selection_time")
    private Integer siteSelectionTime;
    @Column(name = "business_type")
    private Integer businessType;
    @Column(name = "site_selection_area")
    private Integer siteSelectionArea;
    @Column(name = "rent_budget")
    private Integer rentBudget;
    @Column(name = "contact_phone")
    private String contact_phone;
    @Column(name = "contact_flg")
    private Integer contact_flg;
    @Column(name = "consultant_id")
    private Integer consultantId;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
