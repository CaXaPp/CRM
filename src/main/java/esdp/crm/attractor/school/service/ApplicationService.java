package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
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
    String status1 = "Успешно";
    String status2 = "Отказ";
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

    public List<Application> getAllOperationsByFunnelId(Long id) {
        return applicationRepository.findAllByEmployeeNotNullAndStatus_Funnel_Id(id);
    }
    public List<Application> getAllOperationsByEmployeeIdAndFunnelId(Long id, Long funnelId) {
        return applicationRepository.findAllByEmployeeIdAndEmployeeNotNullAndStatus_Funnel_Id(id, funnelId);
    }

    public List<Application> getAllActiveOperationsByFunnelId(Long id) {
        return applicationRepository.findAllActiveOperationsByFunnelId(id, status1, status2);
    }
    public List<Application> getAllActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId) {
        return applicationRepository.getAllActiveOperationsByEmployeeIdAndFunnelId(userId, funnelId, status1, status2);
    }

    public List<Application> getAllNotActiveOperationsByFunnelId(Long id) {
        return applicationRepository.findAllNotActiveOperationsByFunnelId(id, status1, status2);
    }
    public List<Application> getAllNotActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId) {
        return applicationRepository.getAllNotActiveOperationsByEmployeeIdAndFunnelId(userId, funnelId, status1, status2);
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

    public void updateStatus(ApplicationStatus applicationStatus, Application application) {
        application.setStatus(applicationStatus);
        applicationRepository.save(application);
    }

    public Application findById(Long id) {
        return applicationRepository.getApplicationById(id);
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
    public List<Object[]> findAllSumAndCountByApplication(LocalDateTime startDate, LocalDateTime endDate, Long statusId) {
        return applicationRepository.findAllSumAndCountByApplication(startDate, endDate, statusId);
    }

    public List<Object[]> findAllSumAndCountByApplicationByEmployeeId(LocalDateTime startDate, LocalDateTime endDate, Long statusId, Long userId) {
        return applicationRepository.findAllSumAndCountByApplicationByEmployeeId(startDate, endDate, statusId, userId);
    }

    public List<Object[]> findAllActiveApplicationForToday() {
        String status1 = "Отказ";
        String status2 = "Успешно";
        return applicationRepository.findAllActiveApplicationForToday(status1, status2);
    }

    public List<Object[]> findAllActiveApplicationForTodayByUserId(Long userId) {
        String status1 = "Отказ";
        String status2 = "Успешно";
        return applicationRepository.findAllActiveApplicationForTodayByUserId(status1, status2, userId);
    }

    public List<Object[]> findAllComplatedDealsOnToday() {
        String status = "Успешно";
        return applicationRepository.findAllCompletedDealsOnToday(status);
    }

    public List<Object[]> findAllComplatedDealsOnTodayByUserId(Long userId) {
        String status = "Успешно";
        return applicationRepository.findAllCompletedDealsOnTodayByUserId(status, userId);
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

    public List<Application> sortByCompanyName(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.sortByCompanyName(date1, date2);
    }

    public List<Application> sortByPrice(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.sortByPrice(date1, date2);
    }

    public List<Application> sortByProduct(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.sortByProduct(date1, date2);
    }

    public List<Application> sortByStatus(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.sortByStatus(date1, date2);
    }

    public List<Application> sortByEmployee(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.sortByEmployee(date1, date2);
    }

    public List<Application> getAllApplicationByDate(LocalDateTime date1, LocalDateTime date2) {
        return this.applicationRepository.findAllByCreatedAtBetween(date1, date2);
    }

    public List<Application> findAllByCreatedAtBetweenAndCompanyStartingWith(LocalDateTime date1, LocalDateTime date2, String text) {
        return this.applicationRepository.findAllByCreatedAtBetweenAndCompanyStartingWithIgnoreCase(date1, date2, text);
    }
}
