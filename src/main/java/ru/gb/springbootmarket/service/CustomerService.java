package ru.gb.springbootmarket.service;

import org.springframework.stereotype.Service;
import ru.gb.springbootmarket.converter.CustomerMapper;
import ru.gb.springbootmarket.dto.CustomerDto;
import ru.gb.springbootmarket.model.Customer;
import ru.gb.springbootmarket.repository.CustomerRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Map<Long, Customer> storage;
    private final CustomerMapper mapper;

    public CustomerService(CustomerMapper mapper, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        storage = new HashMap<>();
    }

    @PostConstruct
    void init(){
        List<Customer> customers = customerRepository.getAll();
        customers.forEach(customer -> storage.put(customer.getId(), customer));
    }

    public Customer storeCustomer(CustomerDto customerDto){
        Customer customer = mapper.mapModel(customerDto);
        storage.put(customer.getId(), customer);
        return customer;
    }

    public CustomerDto getCustomerById(Long id){
        if (storage.get(id) == null) throw new RuntimeException("Нет покупателя с таким ID.");
        return mapper.mapDto(storage.get(id));
    }
}
