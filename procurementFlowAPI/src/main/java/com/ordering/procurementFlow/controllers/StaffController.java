package com.ordering.procurementFlow.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @GetMapping
    public String get(){
        return "GET:: staff controller";
    }
    @PostMapping
    public String post(){
        return "POST:: staff controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: staff controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: staff controller";
    }
}
