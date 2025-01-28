package com.bookden.api;

import com.bookden.Application;
import com.bookden.entity.Book;
import com.bookden.entity.UserInfo;
import com.bookden.repository.BookRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class BookIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserInformationRepository userInformationRepository;
    @Autowired
    private JwtService jwtService;

    public void initializeData() {
        bookRepository.deleteAll();
        userInformationRepository.deleteAll();

        UserInfo userInfo = new UserInfo();
        userInfo.setName("John");
        userInfo.setRoles("Admin");
        userInfo.setEmail("john@bookden.com");
        userInfo.setPassword("test123");
        userInformationRepository.save(userInfo);

        Date publicationDate = Date.from(Instant.now());
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
    }

    @Test
    public void searchForBookThatMatchTitle_caseInsensitive() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("aBc"));
        requestParameters.put("pageNumber", List.of("0"));
        requestParameters.put("pageSize", List.of("5"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "title": "ABC",
                                    "author": "JK Rowling",
                                    "category": "Fiction",
                                    "updatedBy": null,
                                    "createdBy": "Thomas"
                                }
                            ],
                            "page": {
                                "size": 5,
                                "number": 0,
                                "totalElements": 1,
                                "totalPages": 1
                            }
                        }
                        """));
    }

    @Test
    public void searchForBookThatMatchTitle_caseSensitive() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("AB"));
        requestParameters.put("pageNumber", List.of("0"));
        requestParameters.put("pageSize", List.of("5"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "title": "ABC",
                                    "author": "JK Rowling",
                                    "category": "Fiction",
                                    "updatedBy": null,
                                    "createdBy": "Thomas"
                                }
                            ],
                            "page": {
                                "size": 5,
                                "number": 0,
                                "totalElements": 1,
                                "totalPages": 1
                            }
                        }
                        """));
    }

    @Test
    public void searchForBookThatMatch_author() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("Fran"));
        requestParameters.put("pageNumber", List.of("0"));
        requestParameters.put("pageSize", List.of("5"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""                        
                                {
                                    "content": [
                                        {
                                            "title": "Mighty mouse",
                                            "author": "Franky",
                                            "category": "Adventure",
                                            "updatedBy": null,
                                            "createdBy": "Thomas"
                                        }
                                    ],
                                    "page": {
                                        "size": 5,
                                        "number": 0,
                                        "totalElements": 1,
                                        "totalPages": 1
                                    }
                                }
                        """));
    }

    @Test
    public void searchForBookThatMatch_category() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("fiction"));
        requestParameters.put("pageNumber", List.of("0"));
        requestParameters.put("pageSize", List.of("5"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "title": "ABC",
                                    "author": "JK Rowling",
                                    "category": "Fiction",
                                    "updatedBy": null,
                                    "createdBy": "Thomas"
                                }
                            ],
                            "page": {
                                "size": 5,
                                "number": 0,
                                "totalElements": 1,
                                "totalPages": 1
                            }
                        }
                        """));
    }

    @Test
    public void searchForBook_multipleMatches() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("a"));
        requestParameters.put("pageNumber", List.of("0"));
        requestParameters.put("pageSize", List.of("5"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                             "content": [
                                 {
                                     "title": "ABC",
                                     "author": "JK Rowling",
                                     "category": "Fiction",
                                     "createdBy": "Thomas"
                                 },
                                 {
                                     "title": "King of hearts",
                                     "author": "Queens",
                                     "category": "Romance",
                                     "createdBy": "Thomas"
                                 },
                                 {
                                     "title": "Mighty mouse",
                                     "author": "Franky",
                                     "category": "Adventure",
                                     "createdBy": "Thomas"
                                 }
                             ],
                             "page": {
                                 "size": 5,
                                 "number": 0,
                                 "totalElements": 3,
                                 "totalPages": 1
                             }
                         }
                        """));
    }

    @Test
    public void requestPageTwoOf_bookResults() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.put("searchCriteria", List.of("a"));
        requestParameters.put("pageNumber", List.of("2"));
        requestParameters.put("pageSize", List.of("1"));
        mvc.perform(get("/bookden/v1/api/books")
                        .params(requestParameters)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                             "content": [
                                 {
                                     "title": "Mighty mouse",
                                     "author": "Franky",
                                     "category": "Adventure",
                                     "createdBy": "Thomas"
                                 }
                             ],
                             "page": {
                                 "size": 1,
                                 "number": 2,
                                 "totalElements": 3,
                                 "totalPages": 3
                             }
                         }
                        """));

    }

    @Test
    public void addBookRequest_successfully() throws Exception {
        initializeData();

        String token = jwtService.generateToken("John");
        String addBookRequestJson = """
                {
                    "title":"Im a book",
                    "author":"King",
                    "category":"Adventure",
                    "publicationDate":"2025-03-22"
                }
                """;

        mvc.perform(post("/bookden/v1/api/books")
                        .content(addBookRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                         "title": "Im a book",
                         "author": "King",
                         "category": "Adventure"
                         }
                        """));
    }

    @Test
    public void addBookRequest_missingTitle() throws Exception {
        String token = jwtService.generateToken("John");
        String missingTitleAddBookRequestJson = """
                {
                    "author":"King",
                    "category":"Adventure",
                    "publicationDate":"2025-03-22"
                }""";

        mvc.perform(post("/bookden/v1/api/books")
                        .content(missingTitleAddBookRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "status": "BAD_REQUEST",
                        "errors": [
                            "title: Title field must not be empty"
                        ]
                    }"""
                ));
    }


    @Test
    public void addBookRequest_missingAuthor() throws Exception {
        String token = jwtService.generateToken("John");
        String missingTitleAddBookRequestJson = """
                {
                    "title":"The book",
                    "category":"Adventure",
                    "publicationDate":"2025-03-22"
                }""";

        mvc.perform(post("/bookden/v1/api/books")
                        .content(missingTitleAddBookRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "status": "BAD_REQUEST",
                        "errors": [
                            "author: Author field must not be empty"
                        ]
                    }"""
                ));
    }

    @Test
    public void updateBookRequest_title_change() throws Exception {
        initializeData();
        Optional<Book> firstMatch = bookRepository.findAll().stream().findFirst();
        Long id = firstMatch.get().getId();
        String token = jwtService.generateToken("John");
        String updateBookRequestJson =
                "{\n\"id\": " + id +  "\n," +
                """
                 "title": "Cat house",
                 "author": "Bony",
                 "category": "Dance",
                 "publicationDate":"2025-03-11"
                }""";

        mvc.perform(put("/bookden/v1/api/books")
                        .content(updateBookRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "title": "Cat house",
                        "author": "Bony",
                        "category": "Dance"
                    }
                    """
                ));

        Optional<Book> book = bookRepository.findById(id);

        assertThat(book.isPresent(), is(true));
        assertThat(book.get().getTitle(), is("Cat house"));
        assertThat(book.get().getAuthor(), is("Bony"));
        assertThat(book.get().getCategory(), is("Dance"));
    }

    @Test
    public void updateBookRequest_empty_title() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        String updateBookRequestJson = """
                {
                 "id": 1,
                 "title": "",
                 "author": "Bony",
                 "category": "Dance",
                 "publicationDate":"2025-03-11"
                }""";

        mvc.perform(put("/bookden/v1/api/books")
                        .content(updateBookRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {
                        "status": "BAD_REQUEST",
                        "errors": [
                            "title: must not be empty"
                        ]
                    }"""
                ));
    }

    @Test
    public void delete_book() throws Exception {
        initializeData();
        String token = jwtService.generateToken("John");
        mvc.perform(delete("/bookden/v1/api/books")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
}
