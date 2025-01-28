package com.bookden.entity;

import com.bookden.model.Status;
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
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "member_id")
    private String memberId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp()
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    public Borrow(Long bookId, String memberId, Status status, Date fromDate, Date toDate, String updatedBy) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.updatedBy = updatedBy;
    }
}
