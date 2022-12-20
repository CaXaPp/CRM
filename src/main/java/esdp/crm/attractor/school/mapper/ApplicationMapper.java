package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.repository.ApplicationStatusRepository;
import esdp.crm.attractor.school.repository.ClientSourceRepository;
import esdp.crm.attractor.school.repository.ProductRepository;
import esdp.crm.attractor.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ClientSourceRepository clientSourceRepository;
    private final ApplicationStatusRepository applicationStatusRepository;

    public ApplicationDto toDto(Application application) {
        var applicationDto = ApplicationDto.builder()
                .id(application.getId())
                .name(application.getName())
                .company(application.getCompany())
                .product(productMapper.toProductDto(application.getProduct()))
                .phone(application.getPhone())
                .email(application.getEmail())
                .address(application.getAddress())
                .createdAt(application.getCreatedAt())
                .price(application.getPrice())
                .status(application.getStatus())
                .source(application.getSource())
                .build();
        if (application.getEmployee() != null)
            applicationDto.setEmployee(userMapper.toUserDto(application.getEmployee()));
        return applicationDto;
    }

    public Application toEntity(ApplicationFormDto form) {
        var application =  Application.builder()
                .name(form.getName())
                .company(form.getCompany())
                .product(productRepository.getById(form.getProductId()))
                .phone(form.getPhone())
                .email(form.getEmail())
                .address(form.getAddress())
                .status(applicationStatusRepository.getByName("Новое"))
                .price(form.getPrice())
                .source(clientSourceRepository.getById(form.getSourceId()))
                .createdAt(LocalDateTime.now())
                .build();
        if (form.getId() != null) {
            application.setId(form.getId());
            application.setCreatedAt(form.getCreatedAt());
            application.setEmployee(userRepository.getById(form.getEmployeeId()));
            application.setStatus(applicationStatusRepository.getById(form.getStatusId()));
        }
        return application;
    }

    public Application toOperation(ApplicationFormDto form) {
        var application =  Application.builder()
                .name(form.getName())
                .company(form.getCompany())
                .product(productRepository.getById(form.getProductId()))
                .phone(form.getPhone())
                .email(form.getEmail())
                .address(form.getAddress())
                .status(applicationStatusRepository.getById(form.getStatusId()))
                .price(form.getPrice())
                .source(clientSourceRepository.getById(form.getSourceId()))
                .createdAt(form.getCreatedAt())
                .employee(userRepository.getById(form.getEmployeeId()))
                .build();
        application.setId(form.getId());
        return application;
    }
}
