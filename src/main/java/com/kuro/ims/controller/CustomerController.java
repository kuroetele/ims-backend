package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.Customer;
import com.kuro.ims.service.CustomerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController
{
    private final CustomerService customerService;


    @GetMapping
    public Response<List<Customer>> getCustomers()
    {
        return Response.<List<Customer>>builder()
            .data(customerService.getCustomers())
            .build();
    }


    @RequestMapping("/{id}")
    public Response<Customer> getCustomer(@PathVariable Long id)
    {
        return Response.<Customer>builder()
            .data(customerService.getCustomer(id))
            .build();
    }


    @PostMapping
    public void createCustomer(@RequestBody Customer customer)
    {
        customerService.createCustomer(customer);
    }


    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody Customer customer)
    {
        customerService.updateCustomer(id, customer);
    }

}
