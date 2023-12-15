package com.ordering.procurementFlow.services;
import com.ordering.procurementFlow.DTO.ProviderDto;
import com.ordering.procurementFlow.Models.Provider;
import com.ordering.procurementFlow.repositories.ProviderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProviderService {
    private final ModelMapper modelMapper ;
    private final ProviderRepo providerRepo;

    public Optional<ProviderDto> findProviderById(Long providerId){
        if (providerId==0){log.error("Provider is null");}
        Optional<Provider> provider=providerRepo.findById(providerId);
        return provider.map(u->modelMapper.map(u,ProviderDto.class));
    }
    public List<ProviderDto> findAllProviders(){
        List<Provider> providers=providerRepo.findAll();
        return providers.stream().map(u->modelMapper.map(u, ProviderDto.class)).collect(Collectors.toList());
    }

    public ProviderDto addProvider (ProviderDto providerDto){
        Provider provider= modelMapper.map(providerDto,Provider.class);
        Provider savedProvider = providerRepo.save(provider);
        return modelMapper.map(savedProvider,ProviderDto.class);
    }

    public ProviderDto UpdateProvider(ProviderDto providerDto){
        Provider provider= modelMapper.map(providerDto,Provider.class);
        Provider savedProvider = providerRepo.save(provider);
        return modelMapper.map(savedProvider,ProviderDto.class);
    }
    public void DeleteProviderById(long providerId) {
        if (providerId == 0) {
            log.error("Provider is null");
        }
        providerRepo.deleteById(providerId);
    }
}
