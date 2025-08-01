package com.lys.platform.service.impl;

import com.lys.platform.dao.CustomerMapper;
import com.lys.platform.entity.Customer;
import com.lys.platform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 08:59
 * @version: 1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer getCustomerByPhone(String phone) {
        return customerMapper.selectOne(new Customer().setPhone(phone));
    }

    @Override
    public void createCustomer(Customer customer) {
        customerMapper.insertUseGeneratedKeys(customer);

    }

    @Override
    public void updateCustomerApproveStatus(String phone, int approveStatus) {
        Customer customer = new Customer().setApproveStatus(approveStatus);
        Example example = new Example(Customer.class);
        example.createCriteria().andEqualTo("phone", phone);
        customerMapper.updateByExampleSelective(customer, example);
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }
}
