package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
    private final ProductMapper productMapper;
    public ApplicationDto toDto(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .name(application.getName())
                .company(application.getCompany())
                .product(application.getProduct().getName())
                .phone(application.getPhone())
                .email(application.getEmail())
                .createdAt(application.getCreatedAt())
                .source(application.getSource().name())
                .build();
    }

    public Application toEntity(ApplicationDto dto) {
        return Application.builder()
                .id(dto.getId())
                .name(dto.getName())
                .company(dto.getCompany())
                .product(productMapper.toEntity(dto.getProduct()))
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .source(ClientSource.valueOf(dto.getSource()))
                .build();
    }
}
