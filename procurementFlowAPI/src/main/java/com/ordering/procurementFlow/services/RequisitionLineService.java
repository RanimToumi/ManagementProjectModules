package com.ordering.procurementFlow.services;
import com.ordering.procurementFlow.DTO.RequisitionLineDto;
import com.ordering.procurementFlow.Models.Product;
import com.ordering.procurementFlow.Models.Requisition;
import com.ordering.procurementFlow.Models.RequisitionLine;
import com.ordering.procurementFlow.repositories.ProductRepo;
import com.ordering.procurementFlow.repositories.RequisitionLineRepo;
import com.ordering.procurementFlow.repositories.RequisitionRepo;
import jakarta.persistence.EntityNotFoundException;
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
public class RequisitionLineService {
    private final ModelMapper modelMapper ;
    private final RequisitionLineRepo requisitionLineRepo;
    private final RequisitionRepo requisitionRepo;
    private final ProductRepo productRepo;

    public Optional<RequisitionLineDto> findRequisitionLineById(Long requisitionLineId){
        if (requisitionLineId==0){log.error("RequisitionLine is null");}

            Optional<RequisitionLine> requisitionLine=requisitionLineRepo.findById(requisitionLineId);
            return requisitionLine.map(u->modelMapper.map(u,RequisitionLineDto.class));
    }
     public RequisitionLineDto addRequisitionLineToRequisition(Long requisitionId, RequisitionLineDto requisitionLineDto) {
         Optional<Requisition> requisitionOptional = requisitionRepo.findById(requisitionId);
         if (requisitionOptional.isPresent()) {
             Requisition requisition = requisitionOptional.get();
             RequisitionLine requisitionLine = modelMapper.map(requisitionLineDto, RequisitionLine.class);
             requisitionLine.setRequisition(requisition);
             Product product = productRepo.findById(requisitionLineDto.getProductId())
                     .orElseThrow(() -> new EntityNotFoundException("Product with ID " + requisitionLineDto.getProductId() + " not found"));
             requisitionLine.setUnitPrice(product.getPrice());
             requisitionLine.setTotalAmount(requisitionLine.getQuantity() * product.getPrice());
             RequisitionLine savedRequisitionLine = requisitionLineRepo.save(requisitionLine);
             double total = calculateRequisitionTotal(requisition);
             requisition.setAmount(total);
             requisitionRepo.save(requisition);
             return modelMapper.map(savedRequisitionLine, RequisitionLineDto.class);
         } else {
             throw new EntityNotFoundException("Requisition with ID " + requisitionId + " not found");
         }
     }
    private double calculateRequisitionTotal(Requisition requisition) {
        return requisition.getRequisitionLines().stream()
                .mapToDouble(requisitionLine -> requisitionLine.getTotalAmount())
                .sum();
    }
     // Get all for a requisition
     public List<RequisitionLineDto> getAllRequisitionLinesForRequisition(Long requisitionId) {
         Optional<Requisition> requisitionOptional = requisitionRepo.findById(requisitionId);
         if (requisitionOptional.isPresent()) {
             Requisition requisition = requisitionOptional.get();
             List<RequisitionLine> requisitionLines = requisition.getRequisitionLines();
             return requisitionLines.stream()
                     .map(requisitionLine -> modelMapper.map(requisitionLine, RequisitionLineDto.class))
                     .collect(Collectors.toList());
         } else {
             throw new EntityNotFoundException("Requisition with ID " + requisitionId + " not found");
         }
     }
     public RequisitionLineDto UpdateRequisitionLine(RequisitionLineDto requisitionLineDto){
         RequisitionLine requisitionLine= modelMapper.map(requisitionLineDto,RequisitionLine.class);
       RequisitionLine savedRequisitionLine = requisitionLineRepo.save(requisitionLine);
         return modelMapper.map(savedRequisitionLine,RequisitionLineDto.class);
     }
    public void DeleteRequisitionLineById(long requisitionLineId) {
        if (requisitionLineId == 0) {
            log.error("requisitionLineId is null");
        }
        requisitionLineRepo.deleteById(requisitionLineId);
    }



}
