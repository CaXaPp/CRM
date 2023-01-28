package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ApplicationStatusService statusService;

    public ApplicationDto save(ApplicationFormDto form) {
        var application = applicationRepository.save(applicationMapper.toEntity(form));
        return applicationMapper.toDto(application);
    }

    public List<Application> getAll() {
        return this.applicationRepository.findAll();
    }

    public ApplicationDto getApplicationById(Long id) {
        var application = applicationRepository.findById(id);
        if (application.isEmpty())
            throw new NotFoundException("Application with id: " + id + " not found!");
        return applicationMapper.toDto(application.get());
    }

    public List<ApplicationDto> getAllFreeApplications() {
        var applications = applicationRepository.findAllFree();
        return applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Application updateApplication(ApplicationFormDto application) {
        if (application.getId() != null) application.setCreatedAt
                (applicationRepository.getApplicationById(application.getId()).getCreatedAt()); // TODO Временное решение
        return applicationRepository.save(applicationMapper.toEntity(application));
    }

    public List<Object[]> getSumAndCountOfApplication() {
        return this.applicationRepository.findSumAndCountOfApplication();
    }

    public List<Object[]> getApplicationByProduct(Long productId) {
        return this.applicationRepository.findApplicationByProductId(productId);
    }

    public List<Object[]> getApplicationBySource(Long sourceId) {
        return this.applicationRepository.findApplicationBySourceId(sourceId);
    }

    public List<Object[]> getApplicationByEmployee(Long id) {
        return this.applicationRepository.findApplicationByEmployeeId(id);
    }

    public List<Application> getApplicationByStatus(Long id) {
        return this.applicationRepository.findApplicationByStatusId(id);
    }

    public Float getSumOfAllApplication(LocalDateTime start, LocalDateTime end, Long status) {
        if (this.applicationRepository.getPriceByDateAndStatus(start,end,status) != null) {
            return this.applicationRepository.getPriceByDateAndStatus(start, end, status);
        } else {
            return 0F;
        }
    }

    public List<Object[]> findObjectV2(LocalDateTime startDate, LocalDateTime endDate, Long statusId) {
        return applicationRepository.findObjectV2(startDate, endDate, statusId);
    }

    public Integer getCountAllApplication(LocalDateTime start, LocalDateTime end, Long status) {
        if (this.applicationRepository.getCountByDateAndStatus(start, end, status) != null) {
            return this.applicationRepository.getCountByDateAndStatus(start, end, status);
        } else {
            return 0;
        }
    }

    public Float getApplicationByPriceAndStatusAndEmployeeId(LocalDateTime start, LocalDateTime end, Long status, Long id) {
        if (this.applicationRepository.getPriceByDateAndStatusAndEmployeeId(start, end, status, id) != null) {
            return this.applicationRepository.getPriceByDateAndStatusAndEmployeeId(start, end, status, id);
        }else {
            return 0F;
        }
    }

    public Integer getApplicationCountAndEmployeeId(LocalDateTime start, LocalDateTime end, Long status, Long id) {
        if (this.applicationRepository.getCountByDateAndStatusAndEmployeeId(start, end, status, id) != null) {
            return this.applicationRepository.getCountByDateAndStatusAndEmployeeId(start, end, status, id);
        } else {
            return 0;
        }
    }

    public List<Object[]> findAllActiveApplicationForToday() {
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        String status1 = "Отказ";
        String status2 = "Успешно";
        return applicationRepository.findAllActiveApplicationForToday(today, status1, status2);
    }

    public List<Object[]> findAllActiveApplicationForTodayByUserId(Long userId) {
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        String status1 = "Отказ";
        String status2 = "Успешно";
        return applicationRepository.findAllActiveApplicationForTodayByUserId(today, status1, status2, userId);
    }

    public List<Object[]> findAllSourceOfApplication() {
        return applicationRepository.findAllSourceOfApplication();
    }

    public List<Object[]> findAllSourceOfApplicationByUserId(Long userId) {
        return applicationRepository.findAllSourceOfApplicationByUserId(userId);
    }

    public List<Object[]> findAllDealOfApplication() {
        return applicationRepository.findAllDealOfApplication();
    }

    public List<Object[]> findAllDealOfApplicationByUserId(Long userId) {
        return applicationRepository.findAllDealOfApplicationByUserId(userId);
    }

    public List<Object[]> findAllFailureApplication() {
        return applicationRepository.findAllFailureApplication();
    }

    public List<Object[]> findAllFailureApplicationByUserId(Long userId) {
        return applicationRepository.findAllFailureApplicationByUserId(userId);
    }
}
