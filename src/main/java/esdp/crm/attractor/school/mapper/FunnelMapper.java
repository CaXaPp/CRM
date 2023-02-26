package esdp.crm.attractor.school.mapper;

import esdp.crm.attractor.school.dto.FunnelDto;
import esdp.crm.attractor.school.entity.Funnel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FunnelMapper {
    FunnelDto toDto(Funnel funnel);
}
