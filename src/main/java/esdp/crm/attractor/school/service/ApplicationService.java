package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDetailsDto;
import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.ApplicationDetailsAndStatusDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    String[] statuses = {"Новое", "Переговоры", "Принятие решения", "На обслуживании", "Успешно"};
    String success = "Успешно";
    String fail = "Отказ";
    String new_status = "Новое";
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ApplicationStatusService statusService;

    public ApplicationDto save(ApplicationFormDto form) {
        var application = applicationRepository.save(applicationMapper.toEntity(form));
        return applicationMapper.toDto(application);
    }

    public List<ApplicationDto> getAll() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .sorted(Comparator.comparing(Application::getCreatedAt).reversed())
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getAllOperationsByFunnelId(Long id) {
        List<Application> applications = applicationRepository.findAllByEmployeeNotNullAndStatus_Funnel_Id(id);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }
    public List<ApplicationDto> getAllOperationsByEmployeeIdAndFunnelId(Long id, Long funnelId) {
        List<Application> applications = applicationRepository.findAllByEmployeeIdAndEmployeeNotNullAndStatus_Funnel_Id(id, funnelId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> getAllActiveOperationsByFunnelId(Long id) {
        List<Application> applications = applicationRepository.findAllActiveOperationsByFunnelId(id, success, fail);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }
    public List<ApplicationDto> getAllActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId) {
        List<Application> applications = applicationRepository.getAllActiveOperationsByEmployeeIdAndFunnelId(userId, funnelId, success, fail);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> getAllNotActiveOperationsByFunnelId(Long id) {
        List<Application> applications = applicationRepository.findAllNotActiveOperationsByFunnelId(id, success, fail);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }
    public List<ApplicationDto> getAllNotActiveOperationsByEmployeeIdAndFunnelId(Long userId, Long funnelId) {
        List<Application> applications = applicationRepository.getAllNotActiveOperationsByEmployeeIdAndFunnelId(userId, funnelId, success, fail);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
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

    public void updateApplication(ApplicationFormDto application) {
        if (application.getId() != null) application.setCreatedAt
                (applicationRepository.getApplicationById(application.getId()).getCreatedAt()); // TODO Временное решение
        applicationRepository.save(applicationMapper.toEntity(application));
    }

    public void updateStatus(ApplicationStatus applicationStatus, Application application) {
        application.setStatus(applicationStatus);
        applicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public ApplicationDetailsDto getSumAndCountOfApplication() {
        List<Application> applications = applicationRepository.findAll();
        return detailsDto(applications);
    }

    public ApplicationDetailsDto getApplicationByProduct(Long productId) {
        List<Application> applications = applicationRepository.findAllByProduct_Id(productId);
        return detailsDto(applications);
    }
    public ApplicationDetailsDto getApplicationBySource(Long sourceId) {
        List<Application> applications = applicationRepository.findAllBySource_Id(sourceId);
        return detailsDto(applications);
    }

    public ApplicationDetailsDto getApplicationByEmployee(Long id) {
        List<Application> applications = applicationRepository.findAllByEmployee_Id(id);
        return detailsDto(applications);
    }

    public ApplicationDetailsDto getApplicationByStatus(Long id) {
        List<Application> applications = applicationRepository.findAllByStatus_Id(id);
        return detailsDto(applications);
    }

    private ApplicationDetailsDto detailsDto(List<Application> applications) {
        ApplicationDetailsDto dto = new ApplicationDetailsDto();
        dto.setTotalCount(applications.size());
        dto.setSuccessCount((int) applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), success)).count());
        dto.setFailCount((int) applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), fail)).count());
        dto.setTotalSum(applications.stream().filter(a -> a.getPrice() != null).mapToDouble(a -> a.getPrice().doubleValue()).sum());
        dto.setSuccessSum(applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), success)).mapToDouble(a -> a.getPrice().doubleValue()).sum());
        dto.setFailSum(applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), fail)).mapToDouble(a -> a.getPrice().doubleValue()).sum());
        return dto;
    }
    public Application findById(Long id) {
        return applicationRepository.getApplicationById(id);
    }

    public List<ApplicationDetailsAndStatusDto> findAllSumAndCountByApplication(LocalDateTime startDate, LocalDateTime endDate) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(startDate, endDate);
        List<ApplicationDetailsAndStatusDto> list = new ArrayList<>();
        for (String s : statuses) {
            list.add(statusDto(applications, s));
        }
        return list;
    }

    public List<ApplicationDetailsAndStatusDto> findAllSumAndCountByApplicationAndValue(LocalDateTime startDate, LocalDateTime endDate, Long depId) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetweenAndEmployee_Department_Id(startDate, endDate, depId);
        List<ApplicationDetailsAndStatusDto> list = new ArrayList<>();
        for (String s : statuses) {
            list.add(statusDto(applications, s));
        }
        return list;
    }

    public List<ApplicationDetailsAndStatusDto> findAllSumAndCountByApplicationByEmployeeId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetweenAndEmployee_id(startDate, endDate, userId);
        List<ApplicationDetailsAndStatusDto> list = new ArrayList<>();
        for (String s : statuses) {
            list.add(statusDto(applications, s));
        }
        return list;
    }

    private ApplicationDetailsAndStatusDto statusDto(List<Application> applications, String status) {
        ApplicationDetailsAndStatusDto dto = new ApplicationDetailsAndStatusDto();
            dto.setStatus(status);
            dto.setCount((int) applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), status)).count());
            dto.setSum(applications.stream().filter(a -> a.getPrice() != null).filter(a -> Objects.equals(a.getStatus().getName(), status)).mapToDouble(a -> a.getPrice().doubleValue()).sum());
        return dto;
    }

    public List<ApplicationDto> findAllActiveApplicationForToday() {
        List<Application> applications = applicationRepository.findAllActiveApplicationForToday(success, fail);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllActiveApplicationForTodayByUserId(Long userId) {
        List<Application> applications = applicationRepository.findAllActiveApplicationForTodayByUserId(success, fail, userId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllCompletedDealsOnToday() {
        List<Application> applications = applicationRepository.findAllByStatus_NameAndStatusIsNotNullOrderByEmployee(success);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllCompletedDealsOnTodayByUserId(Long userId) {
        List<Application> applications = applicationRepository.findAllByStatus_NameAndStatusIsNotNullAndEmployee_IdAndEmployeeIsNotNullOrderByEmployee(success, userId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllSourceOfApplication() {
        List<Application> applications = applicationRepository.findAllBySourceIsNotNullAndEmployeeIsNotNullOrderBySource();
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllSourceOfApplicationByUserId(Long userId) {
        List<Application> applications = applicationRepository.findAllByEmployee_IdAndSourceIsNotNullAndEmployeeIsNotNullOrderBySource(userId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllDealOfApplication() {
        List<Application> applications = applicationRepository.findAllByEmployeeIsNotNullOrderByEmployee();
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllDealOfApplicationByUserId(Long userId) {
        List<Application> applications = applicationRepository.findAllByEmployee_IdOrderByEmployee(userId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllFailureApplication() {
        List<Application> applications = applicationRepository.findAllFailureApplication();
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllFailureApplicationByUserId(Long userId) {
        List<Application> applications = applicationRepository.findAllFailureApplicationByUserId(userId);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }


    public List<ApplicationDto> getAllApplicationByDate(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortById(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.comparing(Application::getId))
                .map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortByCompanyName(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.comparing(Application::getCompany))
                .map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortByPrice(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.nullsFirst(
                        Comparator.comparing(a -> {
                            BigDecimal price = a.getPrice();
                            return price == null ? BigDecimal.ZERO : price;
                        })
                )).map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortByProduct(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.comparing(a -> a.getProduct().getName()))
                .map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortByStatus(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.comparing(a -> a.getStatus().getName()))
                .map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> sortByEmployee(LocalDateTime date1, LocalDateTime date2) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        return applications
                .stream()
                .sorted(Comparator.nullsFirst(
                        Comparator.comparing(a -> {
                            User employee = a.getEmployee();
                            return employee == null ? "" : employee.getFirstName() + " " + employee.getSurname();
                        }))
                ).map(applicationMapper::toDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> findAllByCreatedAtBetweenAndCompanyStartingWith(LocalDateTime date1, LocalDateTime date2, String text) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetweenAndCompanyStartingWithIgnoreCase(date1, date2, text);
        return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
    }
}
