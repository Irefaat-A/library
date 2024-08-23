package com.sos.entities;

import com.sos.models.Item;
import com.sos.models.enums.Gender;
import com.sos.models.enums.ItemType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Survivor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "identity")
    private String identity;

    @Column(name = "is_infected")
    @Setter
    private boolean isInfected;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private SurvivorLocation lastLocation;

    @OneToMany(mappedBy = "survivor",fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Inventory> allInventory;

    @OneToMany(mappedBy = "survivor",fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<ContaminationReport> contaminationReport;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @CreationTimestamp()
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public Survivor(final String name, final int age, final String gender,final String identity, final String latitude, final String longitude) {
        this.name = name;
        this.age = age;
        this.gender = Gender.getGender(gender);
        this.identity = identity;
        this.isInfected = false;
        this.lastLocation = new SurvivorLocation(latitude,longitude);
    }

    public void addInventory(final Collection<Item> supplies) {
        if(!supplies.isEmpty()){
            if (this.allInventory == null) {
                this.allInventory = new ArrayList<>();
            }
            supplies.forEach(item ->
                this.allInventory.add(Inventory.builder()
                        .description(item.getDescription())
                        .type(ItemType.getItemType(item.getItemType()))
                        .quantity(item.getQuantity())
                        .survivor(this)
                        .build()
                ));
        }
    }

    public void addContaminationReporter(final Long reportId) {
        if(this.contaminationReport == null){
            this.contaminationReport = new ArrayList<>();
        }
            this.contaminationReport.add(ContaminationReport
                    .builder()
                    .reporterId(reportId)
                    .survivorId(id)
                    .survivor(this)
                    .build()
            );
    }

    public byte getAccusedCount(){
        return (byte) this.contaminationReport.size();
    }
}
