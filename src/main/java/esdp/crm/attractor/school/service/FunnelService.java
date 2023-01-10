package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.FunnelDto;
import esdp.crm.attractor.school.entity.Funnel;
import esdp.crm.attractor.school.mapper.FunnelMapper;
import esdp.crm.attractor.school.repository.FunnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunnelService {
    private final FunnelRepository funnelRepository;
    private final FunnelMapper funnelMapper;

    public List<FunnelDto> findAll() {
        List<Funnel> funnels = funnelRepository.findAll();
        return funnels.stream()
                .map(funnelMapper::toDto)
                .collect(Collectors.toList());
    }
}
