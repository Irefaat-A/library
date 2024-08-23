package com.sos.entities;


import com.sos.models.enums.ItemType;
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
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ItemType type;

    @Column(name = "quantity")
    private int quantity;

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
