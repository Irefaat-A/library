package com.bookden.model.response;

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
public class BookApiResponse extends ApiResponse {
    private String title;
    private String author;
    private String category;
    private Date publicationDate;
    private String updatedBy;
    private Date updatedDate;
    private Date createdDate;
}
