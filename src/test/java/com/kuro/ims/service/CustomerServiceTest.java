package com.kuro.ims.service;

import com.kuro.ims.entity.Customer;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest
{

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;


    @Test
    void getCustomer()
    {
        //given
        Customer customer = getTestCustomer();

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        //when
        Customer foundCustomer = customerService.getCustomer(1L);

        //then
        assertThat(foundCustomer).isEqualToComparingFieldByField(customer);
        verify(customerRepository).findById(eq(1L));
    }


    @Test
    void createCustomer()
    {
        //given
        Customer customer = getTestCustomer();

        //when
        customerService.createCustomer(customer);

        //then
        verify(customerRepository).save(customerArgumentCaptor.capture());
        assertThat(customerArgumentCaptor.getValue()).isEqualTo(customer);
    }


    @Test
    void updateCustomer()
    {
    }


    @Test
    void getCustomerByIdWhenCustomerDoesNotExist()
    {
        //given /then
        assertThatThrownBy(() -> customerService.getCustomer(1L))
            .isInstanceOf(ImsClientException.class)
            .hasMessage("customer not found");
    }


    @Test
    void getCustomerByIdWhenCustomerExist()
    {
        //given
        Customer customer = getTestCustomer();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        Customer foundCustomer = customerService.getCustomer(1L);

        //then
        assertThat(foundCustomer).isEqualToComparingFieldByField(customer);
        verify(customerRepository).findById(eq(1L));
    }


    @Test
    void getCustomerCount()
    {   //given
        when(customerRepository.count()).thenReturn(5L);

        //when
        Long count = customerService.getCustomerCount();

        //then
        assertThat(count).isEqualTo(5L);
        verify(customerRepository).count();
    }


    private Customer getTestCustomer()
    {
        Customer customer = new Customer();
        customer.setName("Marcus");
        return customer;
    }

}
