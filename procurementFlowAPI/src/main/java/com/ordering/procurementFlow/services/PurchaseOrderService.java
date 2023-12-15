package com.ordering.procurementFlow.services;

import com.ordering.procurementFlow.DTO.PurchaseOrderDto;
import com.ordering.procurementFlow.Models.PurchaseOrder;
import com.ordering.procurementFlow.repositories.PurchaseOrderRepo;
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
public class PurchaseOrderService {
    private final ModelMapper modelMapper ;
    private final PurchaseOrderRepo purchaseOrderRepo;

    public Optional<PurchaseOrderDto> findPurchaseOrderById(Long purchaseOrderId){
        if (purchaseOrderId==0){log.error("PurchaseOrder is null");}

        Optional<PurchaseOrder> purchaseOrder=purchaseOrderRepo.findById(purchaseOrderId);
        return purchaseOrder.map(u->modelMapper.map(u,PurchaseOrderDto.class));
    }
    public List<PurchaseOrderDto> findAllPurchaseOrder(){
        List<PurchaseOrder> purchaseOrders=purchaseOrderRepo.findAll();
        return purchaseOrders.stream().map(u->modelMapper.map(u,PurchaseOrderDto.class)).collect(Collectors.toList());
    }

    public PurchaseOrderDto addPurchaseOrder (PurchaseOrderDto purchaseOrderDto){
        PurchaseOrder purchaseOrder= modelMapper.map(purchaseOrderDto,PurchaseOrder.class);
        PurchaseOrder savedPO = purchaseOrderRepo.save(purchaseOrder);
        return modelMapper.map(savedPO,PurchaseOrderDto.class);

    }
    public PurchaseOrderDto UpdatePurchaseOrder(PurchaseOrderDto purchaseOrderDto){
        PurchaseOrder purchaseOrder= modelMapper.map(purchaseOrderDto,PurchaseOrder.class);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepo.save(purchaseOrder);
        return modelMapper.map(savedPurchaseOrder,PurchaseOrderDto.class);

    }


    public void DeletePurchaseOrderById(long purchaseOrderId) {
        if (purchaseOrderId == 0) {
            log.error("PurchaseOrder is null");
        }
        purchaseOrderRepo.deleteById(purchaseOrderId);
    }
}
