package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.ClientSource;
import esdp.crm.attractor.school.repository.ClientSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientSourceService {
    private final ClientSourceRepository clientSourceRepository;

    public List<ClientSource> getAll() {
        return clientSourceRepository.findAll();
    }
}
