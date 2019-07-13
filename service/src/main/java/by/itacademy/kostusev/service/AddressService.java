package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.mapper.AddressMapper;
import by.itacademy.kostusev.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressDto findById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElse(null);
    }

    public AddressDto findByCustomersPhone(String phone) {
        return addressRepository.findByCustomersPhone(phone)
                .map(addressMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public Address saveOrUpdateAddress(AddressDto dto) {
        return addressRepository.save(addressMapper.toEntity(dto));
    }
}
