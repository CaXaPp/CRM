package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ProductDto;
import esdp.crm.attractor.school.dto.request.ProductFormDto;
import esdp.crm.attractor.school.entity.Product;
import esdp.crm.attractor.school.mapper.ProductMapper;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
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

    public void save(ProductFormDto form) throws EntityExistsException {
        if (productRepository.existsByName(form.getName())) throw new EntityExistsException("Продукт с атким именем существует");
        productRepository.save(productMapper.formToEntity(form));
    }

    public List<ProductDto> getProductNameAndId() {
        return productRepository.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<ProductDto> getProductByDepartmentId(Long id) {
        return productRepository.findAllByDepartment_id(id).stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }
}
