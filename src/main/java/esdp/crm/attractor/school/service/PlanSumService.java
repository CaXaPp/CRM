package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.PlanSumDto;
import esdp.crm.attractor.school.dto.request.PlanSumRequest;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.mapper.PlanSumMapper;
import esdp.crm.attractor.school.repository.PlanSumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlanSumService {
    private final PlanSumRepository planSumRepository;
    private final PlanSumMapper planSumMapper;

    public List<PlanSumDto> findAll() {
        var list = planSumRepository.findAll();
        return list.stream()
                .map(planSumMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlanSumDto updateSum(PlanSumRequest form) throws NotFoundException {
        var planSum = planSumRepository.findById(form.getId())
                .orElseThrow(() -> new NotFoundException("Sum for plan with id " + form.getId() + " not found!"));
        var updatedSum = planSumMapper.toEntity(form, planSum);
        var savedSum = planSumRepository.save(updatedSum);
        return planSumMapper.toDto(savedSum);
    }

    public PlanSumDto findById(Long id) {
        var planSum = planSumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sum for plan with id " + id + " not found!"));
        return planSumMapper.toDto(planSum);
    }
}
