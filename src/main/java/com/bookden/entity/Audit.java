package com.bookden.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String description;

    private String memberId;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp()
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public Audit(String action, String description, String memberId) {
        this.action = action;
        this.description = description;
        this.memberId = memberId;
    }
}
