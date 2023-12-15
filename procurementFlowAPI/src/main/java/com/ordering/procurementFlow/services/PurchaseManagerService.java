package com.ordering.procurementFlow.services;
import com.ordering.procurementFlow.repositories.UserRepository;
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
public class PurchaseManagerService {
    private final ModelMapper modelMapper ;
    private final UserRepository userRepository;

}
