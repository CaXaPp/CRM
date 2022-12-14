package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.entity.Product;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductRepository repository;
    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    public Product toEntity(String name) {
        return repository.findByName(name).get();
    }
}
