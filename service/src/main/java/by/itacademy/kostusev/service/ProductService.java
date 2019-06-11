package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.PageDto;
import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.ProductFilterDto;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.entity.QProduct;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto findById(Long id) {
        Optional<Product> user = productRepository.findById(id);
        return user.isPresent() ? productMapper.toDto(user.get()) : ProductDto.builder().build();
    }

    public List<ProductDto> findAll() {
        List<Product> products = Lists.newArrayList(productRepository.findAll());
        return products.stream().map(productMapper::toDto).collect(toList());
    }

    public List<ProductDto> findAll(ProductFilterDto filter) {
        ExpressionBuilder builder = getProductFilter(filter);
        List<Product> products = Lists.newArrayList(productRepository.findAll(builder.getExpression()));
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductDto> findAll(PageDto page) {
        PageRequest pageable = PageRequest.of(page.getPage(), page.getSize());
        List<Product> products = Lists.newArrayList(productRepository.findAll(pageable));
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductDto> findAll(ProductFilterDto filter, PageDto page) {
        ExpressionBuilder builder = getProductFilter(filter);
        PageRequest pageable = PageRequest.of(page.getPage(), page.getSize());
        List<Product> products = Lists.newArrayList(productRepository.findAll(builder.getExpression(), pageable));
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public ExpressionBuilder getProductFilter(ProductFilterDto filter) {
        ExpressionBuilder builder = new ExpressionBuilder();
        builder.add(filter.getMinPrice(), QProduct.product.price::goe);
        builder.add(filter.getMaxPrice(), QProduct.product.price::loe);
        builder.add(filter.getRating(), QProduct.product.rating::goe);
        builder.add(filter.getCategory().getName(), QProduct.product.category.name::eq);
        return builder;
    }
}
