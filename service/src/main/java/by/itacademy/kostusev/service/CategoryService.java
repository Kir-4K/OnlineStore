package by.itacademy.kostusev.service;

import by.itacademy.kostusev.entity.Category;
import by.itacademy.kostusev.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findBy(Long id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    public Category findBy(String name) {
        return categoryRepository.findByName(name)
                .orElse(null);
    }
}
