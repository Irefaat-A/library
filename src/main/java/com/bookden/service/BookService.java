package com.bookden.service;

import com.bookden.entity.Book;
import com.bookden.model.request.AddBookRequest;
import com.bookden.model.request.UpdateBookRequest;
import com.bookden.view.BookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookService {
    Page<Book> searchForBooksFromLibrary(String searchCriteria, PageRequest pageRequest);
    BookView addBook(AddBookRequest addBookRequest);

    BookView updateBook(UpdateBookRequest updateBookRequest);

    void delete(long id);
}
