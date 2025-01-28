package com.bookden.service;


import com.bookden.model.request.BorrowBookRequest;
import com.bookden.model.request.ReturnBookRequest;
import com.bookden.model.response.BorrowResponse;

public interface BorrowService {

    BorrowResponse borrowBook(BorrowBookRequest borrowBookRequestBooks);

    BorrowResponse returnBook(ReturnBookRequest returnBookRequest);

}
