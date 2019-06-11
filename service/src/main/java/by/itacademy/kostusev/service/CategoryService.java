package by.itacademy.kostusev.service;

import by.itacademy.kostusev.entity.Category;
import by.itacademy.kostusev.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findBy(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public Category findBy(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.orElse(null);
    }
}
