package com.bookden.service;

import com.bookden.entity.Borrow;
import com.bookden.exception.MaxBorrowLimitExceededException;
import com.bookden.model.request.BorrowBookRequest;
import com.bookden.model.request.ReturnBookRequest;
import com.bookden.model.response.BorrowResponse;
import com.bookden.repository.BorrowRepository;
import com.bookden.view.BorrowView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bookden.model.Status.*;
import static com.bookden.service.UserInfoService.getLoggedInUsername;


@Service
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;

    @Value( "${borrow.limit.max}" )
    private int maxBorrowLimit;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public BorrowResponse borrowBook(final BorrowBookRequest borrowBookRequest) {
        checkBorrowLimit(borrowBookRequest);

        List<BorrowView> borrowResponse = borrowBookRequest.getBorrowItems().stream().map(borrowItem -> {
            Optional<Borrow> borrow = borrowRepository.findById(borrowItem.getBookId());
            if (borrow.isPresent() &&
                    borrow.get().getStatus().getDescription().equalsIgnoreCase(AVAILABLE.getDescription())) {
                borrow.get().setMemberId(borrowBookRequest.getMemberId());
                borrow.get().setStatus(BORROW);
                borrow.get().setFromDate(borrowItem.getFromDate());
                borrow.get().setToDate(borrowItem.getToDate());
                borrowRepository.save(borrow.get());
            }

            if(borrow.isEmpty()){
                Borrow newBorrow = borrow.orElse(new Borrow(
                        borrowItem.getBookId(),
                        borrowBookRequest.getMemberId(),
                        BORROW,
                        borrowItem.getFromDate(),
                        borrowItem.getToDate(),
                        getLoggedInUsername()));
                borrowRepository.save(newBorrow);

                return new BorrowView(
                        borrowItem.getTitle(),
                        newBorrow.getStatus().getDescription(),
                        newBorrow.getMemberId(),
                        newBorrow.getFromDate(),
                        newBorrow.getToDate());
            }

            if (borrow.isPresent() && borrow.get().getStatus().getDescription().equalsIgnoreCase(BORROW.getDescription())) {
                return new BorrowView(borrowItem.getTitle(), UNAVAILABLE.getDescription(), borrowBookRequest.getMemberId(), borrowItem.getFromDate(), borrowItem.getToDate());
            }

            return new BorrowView(borrowItem.getTitle(), borrow.get().getStatus().getDescription(), borrow.get().getMemberId(), borrow.get().getFromDate(), borrow.get().getToDate());
        }).collect(Collectors.toList());

        return new BorrowResponse(borrowResponse);
    }

    @Override
    public BorrowResponse returnBook(final ReturnBookRequest returnBookRequest) {
        List<BorrowView> borrowResponse = returnBookRequest.getReturnItems().stream().map(borrowItem -> {
            Optional<Borrow> borrow = borrowRepository.findById(borrowItem.getBookId());
            if (borrow.isPresent() && borrow.get().getStatus().getDescription().equalsIgnoreCase(BORROW.getDescription())) {
                borrow.get().setMemberId(null);
                borrow.get().setStatus(AVAILABLE);
                borrow.get().setFromDate(null);
                borrow.get().setToDate(null);
                borrowRepository.save(borrow.get());
            }
            return new BorrowView(returnBookRequest.getMemberId(), borrow.get().getStatus().getDescription(), null, null, null);
        }).collect(Collectors.toList());

        return new BorrowResponse(borrowResponse);
    }

    private void checkBorrowLimit(BorrowBookRequest borrowBookRequest) {
        if (maxBorrowLimit < borrowBookRequest.getBorrowItems().size()) {
            throw new MaxBorrowLimitExceededException(maxBorrowLimit);
        }
    }
}
