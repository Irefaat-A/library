package com.sos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContaminationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "accused_id")
    private Long survivorId;

    @Column(name = "reporter_id")
    private Long reporterId;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "survivor_id")
    private Survivor survivor;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @CreationTimestamp()
    @Column(name = "created_date", updatable = false)
    private Date createdDate;
}
