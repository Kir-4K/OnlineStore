package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.mapper.OnlineOrderMapper;
import by.itacademy.kostusev.repository.OnlineOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OnlineOrderService {

    private final OnlineOrderRepository onlineOrderRepository;
    private final OnlineOrderMapper onlineOrderMapper;

    public OnlineOrderDto findById(Long id) {
        return onlineOrderRepository.findById(id)
                .map(onlineOrderMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public OnlineOrder saveOrUpdateOrder(OnlineOrderDto dto) {
        return onlineOrderRepository.save(onlineOrderMapper.toEntity(dto));
    }
}
