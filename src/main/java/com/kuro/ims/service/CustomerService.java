package com.kuro.ims.service;

import com.kuro.ims.entity.Customer;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.CustomerRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService
{

    private final CustomerRepository customerRepository;


    public Customer getCustomer(Long id)
    {
        return customerRepository.findById(id).orElseThrow(() -> new ImsClientException("customer not found"));
    }


    public void createCustomer(Customer customer)
    {
        customerRepository.save(customer);
    }


    public void updateCustomer(Long id, Customer customer)
    {
        customer.setId(id);
        customerRepository.save(customer);
    }


    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }


    public Long getCustomerCount()
    {
        return customerRepository.count();
    }
}

