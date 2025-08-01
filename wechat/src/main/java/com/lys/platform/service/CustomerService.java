package com.lys.platform.service;

import com.lys.platform.entity.Customer;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 08:57
 * @version: 1.0
 */
public interface CustomerService {

    Customer getCustomerByPhone(String phone);

    void createCustomer(Customer customer);

    void updateCustomerApproveStatus(String phone, int approveStatus);

    Customer getCustomerById(Integer customerId);
}
