package com.bookden.api;

import com.bookden.Application;
import com.bookden.entity.Book;
import com.bookden.entity.Borrow;
import com.bookden.entity.UserInfo;
import com.bookden.repository.BookRepository;
import com.bookden.repository.BorrowRepository;
import com.bookden.repository.UserInformationRepository;
import com.bookden.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static com.bookden.model.Status.AVAILABLE;
import static com.bookden.model.Status.BORROW;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class BorrowIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private JwtService jwtService;

    public void initializeData() {
        bookRepository.deleteAll();
        borrowRepository.deleteAll();

        Date publicationDate = Date.from(Instant.now());
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        bookRepository.save(new Book(
                "ABC",
                "JK Rowling",
                "Fiction",
                publicationDate,
                "Thomas"
        ));

        bookRepository.save(new Book(
                "Mighty mouse",
                "Franky",
                "Adventure",
                publicationDate,
                "Thomas"
        ));

        bookRepository.save(new Book(
                "King of hearts",
                "Queens",
                "Romance",
                publicationDate,
                "Thomas"
        ));

        borrowRepository.save(new Borrow(
                2L,
                "memberId",
                AVAILABLE,
                null,
                null,
                "Thomas"
        ));
    }

    @Test
    public void requestToBorrowBook_available() throws Exception {
        initializeData();

        userInformationRepository.deleteAll();

        UserInfo userInfo = new UserInfo();
        userInfo.setName("John");
        userInfo.setRoles("Admin");
        userInfo.setEmail("john@bookden.com");
        userInfo.setPassword("test123");
        userInformationRepository.save(userInfo);

        String token = jwtService.generateToken("John");
        String rentBookRequest = """
                {
                "memberId":"jolly",
                "borrowItems":[{
                    "bookId":"2",
                    "title":"ABC",
                    "fromDate":"2025-03-22",
                    "toDate":"2025-03-27"
                }]}""";

        mvc.perform(post("/bookden/v1/api/borrow/books")
                        .content(rentBookRequest)
                                                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                     "books": [
                         {
                             "title": "ABC",
                             "status": "Borrow",
                             "memberId": "jolly",
                             "fromDate": "2025-03-22T00:00:00.000+00:00",
                             "toDate": "2025-03-27T00:00:00.000+00:00"
                         }
                     ]
                 }
                """));
    }

    @Test
    public void requestToBorrowBook_maxLimitExceeded() throws Exception {
        String rentBookRequest = """
                {
                       "memberId": "john",
                       "borrowItems": [
                           {
                               "bookId": 2,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           },
                           {
                               "bookId": 1,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           },
                           {
                               "bookId": 3,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           },
                           {
                               "bookId": 4,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           },
                           {
                               "bookId": 5,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           },
                           {
                               "bookId": 6,
                               "title": "ABC",
                               "fromDate": "2025-03-22",
                               "toDate": "2025-03-27"
                           }
                       ]
                   }
                   """;

        userInformationRepository.deleteAll();

        UserInfo userInfo = new UserInfo();
        userInfo.setName("John");
        userInfo.setRoles("Admin");
        userInfo.setEmail("john@bookden.com");
        userInfo.setPassword("test123");
        userInformationRepository.save(userInfo);


        String token = jwtService.generateToken("John");

        mvc.perform(post("/bookden/v1/api/borrow/books")
                        .content(rentBookRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "status": "BAD_REQUEST",
                        "errors": [
                            "Maximum borrow limit exceed. You are allowed a maximum of 5"
                        ]
                    }
                    """));
    }

    @Test
    public void returnBook() throws Exception {
        Date fromDate = Date.from(Instant.now());
        Date toDate = Date.from(Instant.now());
        String token = jwtService.generateToken("John");

        borrowRepository.save(new Borrow(
                1L,
                "memberId",
                BORROW,
                fromDate,
                toDate,
                "Thomas"
        ));

        String returnBookRequest = """
                {
                "memberId":"jolly",
                "returnItems":[{
                    "bookId":"1",
                    "title":"ABC",
                    "fromDate":"2025-03-22",
                    "toDate":"2025-03-27"
                }]}""";

        mvc.perform(put("/bookden/v1/api/borrow/books")
                        .content(returnBookRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "books": [
                        {
                            "title": "jolly",
                            "status": "Available",
                            "memberId": null,
                            "fromDate": null,
                            "toDate": null
                        }
                    ]
                }"""));
    }
}
