package com.bookden.service;

import com.bookden.entity.Book;
import com.bookden.model.request.AddBookRequest;
import com.bookden.model.request.UpdateBookRequest;
import com.bookden.repository.BookRepository;
import com.bookden.view.BookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static com.bookden.service.UserInfoService.getLoggedInUsername;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> searchForBooksFromLibrary(final String searchCriteria, final PageRequest pageRequest) {
        return this.bookRepository.findByTitleOrAuthorOrCategory(searchCriteria, pageRequest);
    }

    @Override
    public BookView addBook(AddBookRequest addBookRequest) {
        Book newBook = this.bookRepository.save(new Book(
                addBookRequest.getTitle(),
                addBookRequest.getAuthor(),
                addBookRequest.getCategory(),
                addBookRequest.getPublicationDate(),
                getLoggedInUsername()));
        return BookView.map(newBook);
    }

    @Override
    public BookView updateBook(UpdateBookRequest updateBookRequest) {
        Optional<Book> book = this.bookRepository.findById(updateBookRequest.getId());

        if (book.isPresent()) {
            book.get().setTitle(StringUtils.hasText(updateBookRequest.getTitle()) ? updateBookRequest.getTitle() : book.get().getTitle());
            book.get().setAuthor(StringUtils.hasText(updateBookRequest.getAuthor()) ? updateBookRequest.getAuthor() : book.get().getAuthor());
            book.get().setCategory(StringUtils.hasText(updateBookRequest.getCategory()) ? updateBookRequest.getCategory() : book.get().getCategory());
            book.get().setPublicationDate(Objects.nonNull(updateBookRequest.getPublicationDate()) ? updateBookRequest.getPublicationDate() : book.get().getPublicationDate());
            book.get().setUpdatedBy(getLoggedInUsername());
        }

        Book updatedBook = this.bookRepository.save(book.get());
        return BookView.map(updatedBook);
    }

    @Override
    public void delete(long id) {
        this.bookRepository.deleteById(id);
    }
}
