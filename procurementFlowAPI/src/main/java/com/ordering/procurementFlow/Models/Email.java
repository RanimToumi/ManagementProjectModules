package com.ordering.procurementFlow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue
    private Long id;
    private String toEmail;
    private String subject;
    private String body;
}
