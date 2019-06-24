package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.mapper.OnlineOrderMapper;
import by.itacademy.kostusev.repository.OnlineOrderRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public OnlineOrderDto findByPhone(LocalDateTime dateTime) {
        return onlineOrderRepository.findByDate(dateTime)
                .map(onlineOrderMapper::toDto)
                .orElse(null);
    }

    public List<OnlineOrderDto> findAll() {
        return Lists.newArrayList(onlineOrderRepository.findAll())
                .stream()
                .map(onlineOrderMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public void saveNewOrder(OnlineOrderDto dto) {
        onlineOrderRepository.save(onlineOrderMapper.toEntity(dto));
    }
}
