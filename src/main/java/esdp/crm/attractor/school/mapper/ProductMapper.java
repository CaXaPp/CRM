package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.entity.Product;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ProductMapper {
    @Autowired
    protected ProductRepository repository;
    public abstract ProductDto toProductDto(Product product);

    public Optional<Product> toEntity(ProductDto dto) {
        return repository.findByName(dto.getName());
    }

}
