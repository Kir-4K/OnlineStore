package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.mapper.CustomerMapper;
import by.itacademy.kostusev.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

import static java.util.stream.Stream.ofNullable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto findByUsername(String username) {
        return ofNullable(username)
                .map(customerRepository::findByUser_Username)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(customerMapper::toDto)
                .findFirst()
                .orElse(null);
    }

    public CustomerDto getSessionCustomer(Principal principal) {
        return ofNullable(principal)
                .map(Principal::getName)
                .map(this::findByUsername)
                .findFirst()
                .orElse(null);
    }

    public CustomerDto findByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .map(customerMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public Customer saveOrUpdateCustomer(CustomerDto dto) {
        return customerRepository.save(customerMapper.toEntity(dto));
    }
}
