package com.sos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "survivor_location")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SurvivorLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @CreationTimestamp()
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @OneToOne(mappedBy = "lastLocation")
    private Survivor survivor;

    public SurvivorLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
