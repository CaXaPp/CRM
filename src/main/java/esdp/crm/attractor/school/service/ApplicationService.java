package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ApplicationDetailsDto;
import esdp.crm.attractor.school.dto.ApplicationDto;
import esdp.crm.attractor.school.dto.ApplicationDetailsAndStatusDto;
import esdp.crm.attractor.school.dto.PlanSumDto;
import esdp.crm.attractor.school.dto.request.ApplicationFormDto;
import esdp.crm.attractor.school.entity.Application;
import esdp.crm.attractor.school.entity.ApplicationStatus;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.ApplicationMapper;
import esdp.crm.attractor.school.repository.ApplicationRepository;
import esdp.crm.attractor.school.repository.ProductRepository;
import esdp.crm.attractor.school.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    String[] statuses = {"Новое", "Переговоры", "Принятие решения", "На обслуживании", "Успешно"};
    String success = "Успешно";
    String fail = "Отказ";
    String new_status = "Новое";
    String allApplication = "infinity";
    String activeApplication = "all_active";
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ApplicationStatusService statusService;
    private final LogsService logsService;
    private final Javers javers;
    private final StatusRepository statusRepository;
    private final ProductRepository productRepository;
    private final PlanSumService planSumService;

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

    public List<ApplicationDto> getAllSortedById() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .sorted(Comparator.comparing(Application::getId))
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getAllActive() {
        List<Application> applications = applicationRepository.findAllByStatus_NameNotIn(Arrays.asList(success, fail));
        return applications.stream()
                .sorted(Comparator.comparing(Application::getCreatedAt).reversed())
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getAllByUserId(Long id) {
        List<Application> applications = applicationRepository.findAllByEmployee_Id(id);
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
                (applicationRepository.getApplicationById(application.getId()).getCreatedAt());
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

    private ApplicationDetailsDto detailsDto(List<Application> applications) {
        List<PlanSumDto> planSum = planSumService.findAll();
        ApplicationDetailsDto dto = new ApplicationDetailsDto();
        LocalDateTime lastMonth = LocalDateTime.now().minusDays(30);

        dto.setTotalCount(applications.size());
        dto.setSuccessCount((int) applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), success)).count());
        dto.setFailCount((int) applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), fail)).count());
        double totalSum = applications.stream().filter(a -> a.getPrice() != null).mapToDouble(a -> a.getPrice().doubleValue()).sum();
        dto.setTotalSum(Double.parseDouble(String.format("%.2f", totalSum).replace(",", ".")));
        double successSum = applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), success)).mapToDouble(a -> a.getPrice().doubleValue()).sum();
        dto.setSuccessSum(Double.parseDouble(String.format("%.2f", successSum).replace(",", ".")));
        double failSum = applications.stream().filter(a -> Objects.equals(a.getStatus().getName(), fail)).mapToDouble(a -> a.getPrice().doubleValue()).sum();
        dto.setFailSum(Double.parseDouble(String.format("%.2f", failSum).replace(",", ".")));
        dto.setPlanSum(planSum);
        double sumOfLastMonth = applications.stream().filter(a -> a.getPrice() != null && a.getStatus().getName().equals(success) && a.getCreatedAt().isAfter(lastMonth)).mapToDouble(a -> a.getPrice().doubleValue()).sum();
        dto.setLastMonthSum(Double.parseDouble(String.format("%.2f", sumOfLastMonth).replace(",", ".")));
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
        double sum = applications.stream()
                .filter(a -> a.getPrice() != null)
                .filter(a -> Objects.equals(a.getStatus().getName(), status))
                .mapToDouble(a -> a.getPrice().doubleValue())
                .sum();
        String formattedSum = String.format("%.2f", sum).replace(",", ".");
        dto.setSum(Double.parseDouble(formattedSum));
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


    public List<ApplicationDto> getAllApplicationByDate(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications.stream()
                    .filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName()))
                    .map(applicationMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortById(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications.stream().sorted(Comparator.comparing(Application::getId)).map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications.stream().filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName())).sorted(Comparator.comparing(Application::getId)).map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortByCompanyName(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications.stream().sorted(Comparator.comparing(Application::getCompany)).map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications.stream().filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName())).sorted(Comparator.comparing(Application::getCompany)).map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortByPrice(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications
                    .stream()
                    .sorted(Comparator.nullsFirst(
                            Comparator.comparing(a -> {
                                BigDecimal price = a.getPrice();
                                return price == null ? BigDecimal.ZERO : price;
                            })
                    )).map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications
                    .stream()
                    .filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName()))
                    .sorted(Comparator.nullsFirst(
                            Comparator.comparing(a -> {
                                BigDecimal price = a.getPrice();
                                return price == null ? BigDecimal.ZERO : price;
                            })
                    )).map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortByProduct(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications
                    .stream()
                    .sorted(Comparator.comparing(a -> a.getProduct().getName()))
                    .map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications
                    .stream()
                    .filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName()))
                    .sorted(Comparator.comparing(a -> a.getProduct().getName()))
                    .map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortByStatus(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications
                    .stream()
                    .sorted(Comparator.comparing(a -> a.getStatus().getName()))
                    .map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications
                    .stream()
                    .filter(a -> !success.equals(a.getStatus().getName()) && !fail.equals(a.getStatus().getName()))
                    .sorted(Comparator.comparing(a -> a.getStatus().getName()))
                    .map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> sortByEmployee(LocalDateTime date1, LocalDateTime date2, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetween(date1, date2);
        if (status.equals(allApplication)) {
            return applications
                    .stream()
                    .sorted(Comparator.nullsFirst(
                            Comparator.comparing(a -> {
                                User employee = a.getEmployee();
                                return employee == null ? "" : employee.getFirstName() + " " + employee.getSurname();
                            }))
                    ).map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications
                    .stream()
                    .sorted(Comparator.nullsFirst(
                            Comparator.comparing(a -> {
                                User employee = a.getEmployee();
                                return employee == null ? "" : employee.getFirstName() + " " + employee.getSurname();
                            }))
                    ).map(applicationMapper::toDto).collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> findAllByCreatedAtBetweenAndCompanyStartingWith(LocalDateTime date1, LocalDateTime date2, String text, String status) {
        List<Application> applications = applicationRepository.findAllByCreatedAtBetweenAndCompanyStartingWithIgnoreCase(date1, date2, text);
        if (status.equals(allApplication)) {
            return applications.stream().map(applicationMapper::toDto).collect(Collectors.toList());
        } else {
            return applications.stream()
                    .filter(application -> !success.equals(application.getStatus().getName()) && !fail.equals(application.getStatus().getName()))
                    .map(applicationMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> getAllSuccessfulAppsByToday() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Changes changes = javers.findChanges(QueryBuilder.byClass(Application.class)
                .withChangedProperty("Статус").to(tomorrow).from(today).build());

        List<ApplicationDto> dtos = new ArrayList<>();
        changes.getPropertyChanges("Статус").forEach(propertyChange -> {
            if (statusRepository.findById
                            (logsService.getIdFromPropertyStr
                                    (propertyChange.getRight().toString()))
                    .get().getName().equals("Успешно")) {
                dtos.add(applicationMapper.toDto
                        (applicationRepository.getApplicationById
                                (Long.valueOf(propertyChange.getAffectedLocalId().toString()))));
            }
        });
        return dtos.stream().distinct().collect(Collectors.toList());
    }

    public List<ApplicationDto> getSuccessfulAppByEmployeeToday(User user) {
        return getAllSuccessfulAppsByToday().stream()
                .filter(app -> app.getEmployee().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
