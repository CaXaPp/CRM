package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {
    public ApplicationDto toApplicationDto(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .name(application.getName())
                .company(application.getCompany())
                .product(application.getProduct())
                .phone(application.getPhone())
                .email(application.getEmail())
                .date(application.getDate())
                .source(application.getSource())
                .build();
    }
}
