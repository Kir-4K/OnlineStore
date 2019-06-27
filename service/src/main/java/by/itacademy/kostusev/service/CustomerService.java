package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.mapper.CustomerMapper;
import by.itacademy.kostusev.repository.CustomerRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    public CustomerDto findByUsername(String username) {
        return customerRepository.findByUser_Username(username)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    public CustomerDto findByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    public List<CustomerDto> findAll() {
        List<Customer> customers = Lists.newArrayList(customerRepository.findAll());
        return customers.stream().map(customerMapper::toDto).collect(toList());
    }

    @Transactional
    public Customer saveNewCustomer(CustomerDto dto) {
        return customerRepository.save(customerMapper.toEntity(dto));
    }
}
