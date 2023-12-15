package com.ordering.procurementFlow.Models;

import jakarta.persistence.*;
import lombok.*;

@Data

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "Report")
@Entity
public class Report {
    @Id
    @GeneratedValue
    private Long reportId ;
    @NonNull
    @Column(name = "Comment")
    private String Comment ;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user ;

}
