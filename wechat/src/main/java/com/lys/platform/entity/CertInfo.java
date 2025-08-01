package com.lys.platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 13:50
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "cert_info")
public class CertInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "phone")
    private String phone;
 /*   @Column(name = "status")
    private Integer status;*/
    @Column(name = "cert_type")
    private Integer certType;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "position")
    private Integer position;
    @Column(name = "id_cart_back")
    private String idCartBack;
    @Column(name = "id_cart_front")
    private String idCartFront;
    @Column(name = "business_license")
    private String businessLicense;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;




}
