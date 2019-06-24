package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.utilityDto.PageDto;
import by.itacademy.kostusev.dto.utilityDto.ProductFilterDto;
import by.itacademy.kostusev.mapper.ProductMapper;
import by.itacademy.kostusev.repository.ProductRepository;
import by.itacademy.kostusev.util.ExpressionBuilder;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.itacademy.kostusev.entity.QProduct.product;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElse(null);
    }

    public List<ProductDto> findAll() {
        return Lists.newArrayList(productRepository.findAll())
                .stream()
                .map(productMapper::toDto)
                .collect(toList());
    }

    public List<ProductDto> findAll(ProductFilterDto filter) {
        ExpressionBuilder builder = getProductFilter(filter);
        return Lists.newArrayList(productRepository.findAll(builder.getExpression()))
                .stream()
                .map(productMapper::toDto)
                .collect(toList());
    }

    public List<ProductDto> findAll(PageDto page) {
        PageRequest pageable = PageRequest.of(page.getPage(), page.getSize());
        return Lists.newArrayList(productRepository.findAll(pageable))
                .stream()
                .map(productMapper::toDto)
                .collect(toList());
    }

    public List<ProductDto> findAll(ProductFilterDto filter, PageDto page) {
        ExpressionBuilder builder = getProductFilter(filter);
        PageRequest pageable = PageRequest.of(page.getPage(), page.getSize());
        return Lists.newArrayList(productRepository.findAll(builder.getExpression(), pageable))
                .stream()
                .map(productMapper::toDto)
                .collect(toList());
    }

    private ExpressionBuilder getProductFilter(ProductFilterDto filter) {
        ExpressionBuilder builder = new ExpressionBuilder();
        builder.add(filter.getMinPrice(), product.price::goe);
        builder.add(filter.getMaxPrice(), product.price::loe);
        builder.add(filter.getRating(), product.rating::goe);
        builder.add(filter.getCategory(), product.category.name::eq);
        return builder;
    }
}
