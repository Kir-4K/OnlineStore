package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.utilityDto.PageDto;
import by.itacademy.kostusev.dto.utilityDto.ProductFilterDto;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.mapper.ProductMapper;
import by.itacademy.kostusev.repository.ProductRepository;
import by.itacademy.kostusev.util.ExpressionBuilder;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.itacademy.kostusev.entity.QProduct.product;
import static java.util.stream.Collectors.toList;

@Log4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Cacheable(value = "product", unless = "#result == null")
    public ProductDto findById(Long id) {
        return Stream.ofNullable(id)
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(productMapper::toDto)
                .findFirst()
                .orElse(null);
    }

    public ProductDto findByName(String name) {
        return Stream.ofNullable(name)
                .map(productRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(productMapper::toDto)
                .findFirst()
                .orElse(null);
    }

    @Cacheable(value = "product_list")
    public List<ProductDto> findAll() {
        return Lists.newArrayList(productRepository.findAll())
                .stream()
                .map(productMapper::toDto)
                .collect(toList());
    }

    @Cacheable(value = "product_filter", key = "#filter", unless = "#result == null ")
    public List<ProductDto> findAll(ProductFilterDto filter, PageDto page) {
        ExpressionBuilder builder = getProductFilter(filter);
        PageRequest pageable = PageRequest.of(page.getPage(), page.getSize());
        return Lists.newArrayList(productRepository.findAll(builder.getExpression(), pageable))
                .stream()
                .map(productMapper::toDto)
                .sorted(Comparator.comparing(ProductDto::getName))
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

    @CachePut("update_product")
    @Transactional
    public Product saveOrUpdateProduct(ProductDto dto) {
        return productRepository.save(productMapper.toEntity(dto));
    }
}
