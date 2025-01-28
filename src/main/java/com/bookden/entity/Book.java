package com.bookden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "category")
    private String category;

    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

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

    public Book(
            final String title,
            final String author,
            final String category,
            final Date publicationDate,
            final String createdBy) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationDate = publicationDate;
        this.createdBy = createdBy;
    }

    public Book(Long id, String title, String author, String category, Date publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationDate = publicationDate;
    }
}
