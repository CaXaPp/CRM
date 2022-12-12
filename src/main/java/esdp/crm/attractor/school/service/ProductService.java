package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.mapper.ProductMapper;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProductsDto() {
        return productRepository.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }
}
