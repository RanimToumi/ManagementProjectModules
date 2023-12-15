package com.ordering.procurementFlow.controllers;
import com.ordering.procurementFlow.DTO.RequisitionDto;
import com.ordering.procurementFlow.DTO.RequisitionLineDto;
import com.ordering.procurementFlow.Models.User;
import com.ordering.procurementFlow.services.RequisitionLineService;
import com.ordering.procurementFlow.services.RequisitionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/requisition")
@RequiredArgsConstructor
public class RequisitionController {
    private final RequisitionService requisitionService;
    private final RequisitionLineService requisitionLineService;
    @GetMapping("/all")
    public ResponseEntity<List<RequisitionDto>> getAllRequisitions(){
        List<RequisitionDto> requisitionDto= requisitionService.findAllRequisitions();
        return new ResponseEntity<>(requisitionDto, HttpStatus.OK);
    }
    @GetMapping("/approvedRequisitions")
    @PreAuthorize("hasRole('PURCHASE_MANAGER')")
    public ResponseEntity<List<RequisitionDto>> getApprovedRequisitions(){
        List<RequisitionDto> requisitionDto= requisitionService.findApprovedRequisitions();
        return new ResponseEntity<>(requisitionDto, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<RequisitionDto> getRequisitionById( @PathVariable("id")Long requisitionId){
       Optional<RequisitionDto> requisitionDto= requisitionService.findRequisitionById(requisitionId);
        if (requisitionDto.isPresent()) {
            return new ResponseEntity<>(requisitionDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addHeader")
    public  ResponseEntity<RequisitionDto> addRequisitionHeader(@RequestBody RequisitionDto requisitionDto, HttpServletResponse response){
        RequisitionDto newRequisition =requisitionService.addRequisitionHeader(requisitionDto)  ;
        return new ResponseEntity<>(newRequisition,HttpStatus.CREATED );
    }
    @PostMapping("{id}/send")
    public  ResponseEntity<RequisitionDto> createRequisition(@PathVariable("id") Long id){
        RequisitionDto newRequisition =requisitionService.addRequisition(id)  ;
        return new ResponseEntity<>(newRequisition,HttpStatus.CREATED );
    }
    @PutMapping("/update")
    public ResponseEntity<RequisitionDto> updateRequisition(@RequestBody RequisitionDto requisitionDto , HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        if (!requisitionService.isUserAuthorizedForRequisition(authenticatedUserId, requisitionService.getRequisitionFromDTO(requisitionDto).getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        RequisitionDto updateRequisition = requisitionService.updateRequisition(requisitionDto);
        return new ResponseEntity<>(updateRequisition, HttpStatus.OK);
    }
    @PutMapping("/approve/{id}")
    @PreAuthorize("hasRole('PURCHASE_MANAGER')")
    public ResponseEntity<RequisitionDto> approveRequisition(@PathVariable("id") Long id){
        return new ResponseEntity<RequisitionDto>(requisitionService.approveRequisition(id), HttpStatus.OK);
    }
    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('PURCHASE_MANAGER')")
    public ResponseEntity<RequisitionDto> rejectRequisition(@PathVariable("id") Long id){
        return new ResponseEntity<RequisitionDto>(requisitionService.rejectRequisition(id), HttpStatus.OK);
    }
    @PutMapping("/isRequisitionApproved/{id}")
    public ResponseEntity<Boolean> isRequisitionApproved(@PathVariable("id") Long id){
        boolean isRequisitionApproved = requisitionService.isRequisitionApproved(id);
        return new ResponseEntity<Boolean>(isRequisitionApproved, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteRequisition(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        if (!requisitionService.isUserAuthorizedForRequisition(authenticatedUserId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        requisitionService.DeleteRequisitionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}/requisitionLines")
    public ResponseEntity<List<RequisitionLineDto>> getAllRequisitionLinesForRequisition(@PathVariable("id") Long requisitionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        if (!requisitionService.isUserAuthorizedForRequisition(authenticatedUserId, requisitionId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<RequisitionLineDto> requisitionLines = requisitionLineService.getAllRequisitionLinesForRequisition(requisitionId);
        return new ResponseEntity<>(requisitionLines, HttpStatus.OK);
    }
    @PostMapping("/{requisitionId}/addRequisitionLine")
    public ResponseEntity<RequisitionLineDto> addRequisitionLineToRequisition(@PathVariable("requisitionId") Long requisitionId, @RequestBody RequisitionLineDto requisitionLineDto, HttpServletResponse response) {
        RequisitionLineDto newRequisitionLine = requisitionLineService.addRequisitionLineToRequisition(requisitionId, requisitionLineDto);
        return new ResponseEntity<>(newRequisitionLine, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RequisitionDto>> getRequisitionsByUserId(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long  authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        if (!authenticatedUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<RequisitionDto> requisitions = requisitionService.findRequisitionsByUserId(userId);
        return ResponseEntity.ok(requisitions);
    }

}
