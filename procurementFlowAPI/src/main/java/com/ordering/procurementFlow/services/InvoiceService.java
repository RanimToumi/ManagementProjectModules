package com.ordering.procurementFlow.services;



import com.ordering.procurementFlow.DTO.InvoiceDto;
import com.ordering.procurementFlow.Models.Invoice;
import com.ordering.procurementFlow.repositories.InvoiceRepo;
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
public class InvoiceService {
    private final ModelMapper modelMapper ;
    private final InvoiceRepo invoiceRepo;

    public Optional<InvoiceDto> findInvoiceById(Long invoiceId){
        if (invoiceId==0){log.error("InvoiceId is null");}
        Optional<Invoice> invoice=invoiceRepo.findById(invoiceId);
        return invoice.map(u->modelMapper.map(u, InvoiceDto.class));
    }

    public List<InvoiceDto> findAllInvoices(){
        List<Invoice> invoices=invoiceRepo.findAll();
        return invoices.stream().map(u->modelMapper.map(u,InvoiceDto.class)).collect(Collectors.toList());
    }

    public InvoiceDto addInvoice (InvoiceDto invoiceDto){
        Invoice invoice= modelMapper.map(invoiceDto,Invoice.class);
        Invoice savedInvoice = invoiceRepo.save(invoice);
        return modelMapper.map(savedInvoice,InvoiceDto.class);
    }

    public InvoiceDto UpdateInvoice(InvoiceDto invoiceDto){
        Invoice invoice= modelMapper.map(invoiceDto,Invoice.class);
        Invoice savedInvoice = invoiceRepo.save(invoice);
        return modelMapper.map(savedInvoice,InvoiceDto.class);
    }
    public void DeleteInvoiceById(long invoiceId) {
        if (invoiceId == 0) {
            log.error("invoiceId is null");
        }
        invoiceRepo.deleteById(invoiceId);
    }
}
