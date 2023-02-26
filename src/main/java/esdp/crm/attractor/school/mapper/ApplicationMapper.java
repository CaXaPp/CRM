package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.repository.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ApplicationMapper {
    @Autowired
    protected ProductMapper productMapper;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ClientSourceRepository clientSourceRepository;
    @Autowired
    protected ApplicationStatusRepository applicationStatusRepository;

    @Mapping(target = "product", expression = "java(productMapper.toProductDto(application.getProduct()))")
    @Mapping(target = "employee", expression = "java(userMapper.toUserDto(application.getEmployee()))")
    public abstract ApplicationDto toDto(Application application);

    @Mapping(target = "product", expression = "java(productRepository.getById(form.getProductId()))")
    @Mapping(target = "status",
            expression = "java(form.getStatusId() != null ? " +
                    "applicationStatusRepository.getById(form.getStatusId()) : applicationStatusRepository.getByName(\"Новое\").get(0))")
    @Mapping(target = "source", expression = "java(clientSourceRepository.getById(form.getSourceId()))")
    @Mapping(target = "createdAt", expression = "java(form.getCreatedAt() != null ? form.getCreatedAt() : java.time.LocalDateTime.now())")
    @Mapping(target = "employee", expression = "java(form.getEmployeeId() != null ? userRepository.getById(form.getEmployeeId()) : null)")
    public abstract Application toEntity(ApplicationFormDto form);

    public Application toEntity(ApplicationDto dto) {
        return applicationRepository.getApplicationById(dto.getId());
    }

    @Mapping(target = "product", expression = "java(productRepository.getById(form.getProductId()))")
    @Mapping(target = "status", expression = "java(applicationStatusRepository.getById(form.getStatusId()))")
    @Mapping(target = "source", expression = "java(clientSourceRepository.getById(form.getSourceId()))")
    @Mapping(target = "employee", expression = "java(userRepository.getById(form.getEmployeeId()))")
    public abstract Application toOperation(ApplicationFormDto form);
}
