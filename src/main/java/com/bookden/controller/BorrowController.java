package com.bookden.controller;

import com.bookden.model.request.BorrowBookRequest;
import com.bookden.model.request.ReturnBookRequest;
import com.bookden.model.response.ApiResponse;
import com.bookden.model.response.BorrowResponse;
import com.bookden.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "bookden/v1/api/borrow/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(final BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @Operation(summary = "Request to borrow book/s.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Request to borrow book/s",
                    content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = BorrowResponse.class))
            })})
    @PostMapping
    public ResponseEntity<ApiResponse> requestToBorrow(@RequestBody @Valid BorrowBookRequest borrowBookRequest) {
        BorrowResponse borrowResponse = borrowService.borrowBook(borrowBookRequest);
        return ResponseEntity.ok(borrowResponse);
    }

    @Operation(summary = "Return borrowed book/s.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Return borrowed book/s",
                    content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = BorrowResponse.class))
            })})
    @PutMapping
    public ResponseEntity<ApiResponse> returnBook(@RequestBody @Valid ReturnBookRequest returnBookRequest) {
        BorrowResponse returnBookResponse = borrowService.returnBook(returnBookRequest);
        return ResponseEntity.ok(returnBookResponse);
    }

}
