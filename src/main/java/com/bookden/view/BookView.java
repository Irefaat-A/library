package com.bookden.view;

import com.bookden.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookView extends ApiResponse{
    private String title;
    private String author;
    private String category;
    private Date publicationDate;

    public static BookView map(final Book newBook) {
        return BookView.builder()
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .category(newBook.getCategory())
                .publicationDate(newBook.getPublicationDate())
                .build();
    }
}