package com.bookden.controller;

import com.bookden.entity.Book;
import com.bookden.model.request.AddBookRequest;
import com.bookden.model.request.UpdateBookRequest;
import com.bookden.service.BookService;
import com.bookden.view.BookView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "bookden/v1/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Retrieve all books in the library in alphabetical order.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Display all the books in the library in alphabetical order. With pagination",
                    content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = BookView.class))
            })})
    @GetMapping()
    public PagedModel<Book> getAllBooksInLibrary(@RequestParam("searchCriteria") String searchCriteria,
                                                 @RequestParam("pageNumber") int pageNumber,
                                                 @RequestParam("pageSize") int pageSize) {
        return new PagedModel<>(bookService.searchForBooksFromLibrary(searchCriteria, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"))));
    }

    @Operation(summary = "Add book to the online library.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Add the book to the online library.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddBookRequest.class))
            })})
    @PostMapping()
    public ResponseEntity<BookView> addBookToLibrary(@RequestBody @Valid  AddBookRequest addBookRequest) {
        return ResponseEntity.ok(bookService.addBook(addBookRequest));
    }

    @Operation(summary = "Update book properties.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Update book properties.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateBookRequest.class))
            })})
    @PutMapping()
    public ResponseEntity<BookView> updateBook(@RequestBody @Valid UpdateBookRequest updateBookRequest) {
        return ResponseEntity.ok(bookService.updateBook(updateBookRequest));
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Delete book from online library.")})
    @DeleteMapping()
    public ResponseEntity deleteBook(@RequestParam("id") long id) {
        bookService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }
}
