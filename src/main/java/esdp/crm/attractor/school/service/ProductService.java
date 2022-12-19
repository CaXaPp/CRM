package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.entity.Product;
import esdp.crm.attractor.school.mapper.ProductMapper;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAll() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return productMapper.toProductDto(product.get());
    }

    public void save(ProductDto dto) {
        productRepository.save(Product.builder().name(dto.getName()).build());
    }
}
