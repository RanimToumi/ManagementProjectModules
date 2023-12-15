package com.ordering.procurementFlow.services;

import ch.qos.logback.core.spi.ErrorCodes;
import com.ordering.procurementFlow.DTO.ProductDto;
import com.ordering.procurementFlow.DTO.RequisitionDto;
import com.ordering.procurementFlow.Models.Product;
import com.ordering.procurementFlow.Models.Requisition;
import com.ordering.procurementFlow.Models.RequisitionStatus;
import com.ordering.procurementFlow.Models.User;
import com.ordering.procurementFlow.repositories.RequisitionRepo;
import com.ordering.procurementFlow.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequisitionService {

    private final ModelMapper modelMapper ;
    private final RequisitionRepo requisitionRepo ;
    private final UserRepository userRepository ;

    public Optional<RequisitionDto> findRequisitionById(long RequisitionId ){
        if (RequisitionId ==0 ){log.error("RequisitionId is null");}
        Optional<Requisition> requisition= requisitionRepo.findById(RequisitionId);
        return Optional.ofNullable(requisition.map(u -> convertToDto(u)).orElseThrow(() -> new EntityNotFoundException("Requisition" + RequisitionId + "not found")));
    }
    public List<RequisitionDto> findAllRequisitions() {
        List<Requisition> requisitions = requisitionRepo.findAll();
        return requisitions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<RequisitionDto> findApprovedRequisitions() {
        List<Requisition> approvedRequisitions = requisitionRepo.findAll()
                .stream()
                .filter(requisition -> RequisitionStatus.Approved.equals(requisition.getRequisitionStatus()))
                .collect(Collectors.toList());
        return approvedRequisitions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<RequisitionDto> findRequisitionsByUserId(Long userId) {
        List<Requisition> requisitions = requisitionRepo.findAllByUser_Id(userId);
        return requisitions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public RequisitionDto addRequisitionHeader (RequisitionDto requisitionDto){
        Requisition requisition=modelMapper.map(requisitionDto,Requisition.class);
        requisition.setRequisitionDate(LocalDateTime.now());
        User user = userRepository.findById(requisitionDto.getId_user())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + requisitionDto.getId_user() + " not found"));
        requisition.setUser(user);
        Requisition savedRequisition= requisitionRepo.save(requisition);
        return modelMapper.map(savedRequisition,RequisitionDto.class);
    }
    public RequisitionDto addRequisition (Long id){
        Requisition requisition = requisitionRepo.findById(id).get();
        requisition.setRequisitionStatus(RequisitionStatus.Pending);
        Requisition savedRequisition= requisitionRepo.save(requisition);
        return modelMapper.map(savedRequisition,RequisitionDto.class);
    }
    public RequisitionDto updateRequisition(RequisitionDto requisitionDto){
        Requisition requisition=modelMapper.map(requisitionDto,Requisition.class);
        Requisition updatedRequisition= requisitionRepo.save(requisition);
        return convertToDto(updatedRequisition);
    }

    public RequisitionDto approveRequisition(Long id){
        Requisition requisition = requisitionRepo.findById(id).get();
        requisition.setRequisitionStatus(RequisitionStatus.Approved);
        Requisition approvedRequisition = requisitionRepo.save(requisition);
        return convertToDto(approvedRequisition);
    }
    public RequisitionDto rejectRequisition(Long id){
        Requisition requisition = requisitionRepo.findById(id).get();
        requisition.setRequisitionStatus(RequisitionStatus.Rejected);
        Requisition rejectedRequisition = requisitionRepo.save(requisition);
        return convertToDto(rejectedRequisition);
    }

    public void DeleteRequisitionById(long requisitionId) {
        if (requisitionId == 0) {
            log.error("requisitionId is null");
        }
       requisitionRepo.deleteById(requisitionId);
    }

    public boolean isRequisitionApproved(Long requisitionId) {
        Optional<Requisition> requisition=requisitionRepo.findById(requisitionId);
        RequisitionStatus status = requisition.get().getRequisitionStatus();
        return status == RequisitionStatus.Approved;
    }
    public boolean isUserAuthorizedForRequisition(Long userId, Long requisitionId) {
        Requisition requisition = requisitionRepo.findById(requisitionId).orElse(null);
        return requisition != null && requisition.getUser().getId().equals(userId);
    }
    private RequisitionDto convertToDto(Requisition requisition) {
        RequisitionDto requisitionDto = modelMapper.map(requisition, RequisitionDto.class);
        if (requisition.getUser() != null) {
            requisitionDto.setId_user(requisition.getUser().getId());
        }
        return requisitionDto;
    }
    public Requisition getRequisitionFromDTO(RequisitionDto requisitionDto){
        return modelMapper.map(requisitionDto,Requisition.class);
    }

}
