package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.dto.ClientSourceDto;
import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.repository.ClientSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientSourceService {
    private final ClientSourceRepository clientSourceRepository;

    public List<ClientSourceDto> getAll() {
        List<ClientSource> clientSources = clientSourceRepository.findAll();
        return clientSources.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ClientSourceDto convertToDto(ClientSource clientSource) {
        ClientSourceDto clientSourceDto = new ClientSourceDto();
        clientSourceDto.setId(clientSource.getId());
        clientSourceDto.setName(clientSource.getName());
        return clientSourceDto;
    }
}
